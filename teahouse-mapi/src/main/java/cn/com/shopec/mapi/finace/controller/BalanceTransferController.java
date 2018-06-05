package cn.com.shopec.mapi.finace.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.common.BalanceTransferConstant;
import cn.com.shopec.core.finace.model.TransferRecord;
import cn.com.shopec.core.finace.service.TransferRecordService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.member.service.MemberBalanceService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.finace.vo.BalanceTransferVo;

/**
 * 余额提现
 */
@Controller
@RequestMapping("/app/balanceTransfer")
public class BalanceTransferController extends BaseController {

	private static Logger log = Logger.getLogger(BalanceTransferController.class);

	@Resource
	private MemberService memberService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private MemberBalanceService memberBalanceService;
	@Resource
	private TransferRecordService transferRecordService;

	/**
	 * 进入余额退款页面
	 */
	@RequestMapping("/toRefunds")
	@ResponseBody
	public ResultInfo<String> toRefunds(String memberNo) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		MemberBalance b = memberBalanceService.getMemberBalance(memberNo);
		if (b != null) {
			resultInfo.setData(b.getMemberBalance() + "");
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg(Constant.YES_RECORD);
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
		}

		return resultInfo;

	}

	/**
	 * 客户端申请提现余额
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("/applyBalanceTransfer")
	@ResponseBody
	public ResultInfo<TransferRecord> applyBalanceTransfer(BalanceTransferVo balanceTransfer) throws ParseException {

		Member member = memberService.getMember(balanceTransfer.getMemberNo());
		// 开始校验提交的数据
		ResultInfo<TransferRecord> resultInfo = validateForApplyBalanceTransfer(balanceTransfer, member);
		if (Constant.SECCUESS.equals(resultInfo.getCode())) {// 校验通过构建并且新增一条提现的记录
			TransferRecord transferRecord = createRecordForApplyBalanceTransfer(balanceTransfer, member);
			resultInfo = transferRecordService.addTransferRecordForApplyBalance(transferRecord, this.getOperator());
		}
		return resultInfo;
	}

	/**
	 * 校验客户端提交的申请提现的数据
	 * 
	 * @param balanceTransfer
	 * @param member
	 * @return
	 * @throws ParseException
	 */
	private ResultInfo<TransferRecord> validateForApplyBalanceTransfer(BalanceTransferVo balanceTransfer, Member member)
			throws ParseException {
		ResultInfo<TransferRecord> resultInfo = new ResultInfo<TransferRecord>();
		resultInfo.setCode(Constant.FAIL);

		// 校验账户
		if (balanceTransfer.getTransferAccount() == null || balanceTransfer.getTransferAccount().equals("")) {
			resultInfo.setMsg(BalanceTransferConstant.ACCOUNT_IS_NOT_EXISTENCE);
			return resultInfo;
		}

		// 开始校验会员身份
		if (member == null) {
			resultInfo.setMsg(BalanceTransferConstant.MEMBER_IS_NOT_EXISTENCE);
			return resultInfo;
		} else if (member.getIsBlacklist() != null && member.getIsBlacklist() == 1) {
			resultInfo.setMsg(BalanceTransferConstant.MEMBER_IS_ILLEGAL);
			return resultInfo;
		}

		// 校验会员是否已经拥有了提现申请
		TransferRecord recordQuery = new TransferRecord();
		recordQuery.setMemberNo(balanceTransfer.getMemberNo());
		List<TransferRecord> transferRecordList = transferRecordService.getTransferRecordList(new Query(recordQuery));
		for (TransferRecord transferRecord : transferRecordList) {
			if (transferRecord.getTransferStatus() == 0 || transferRecord.getTransferStatus() == 2) {
				resultInfo.setMsg(BalanceTransferConstant.MEMBER_HAVE_TRANSFER_APPLING);
				return resultInfo;
			}
		}

		// 校验该会员提现申请次数是否已达到今日提现申请次数限制值
		SysParam sysParam = sysParamService.getByParamKey(BalanceTransferConstant.SYS_TRANSFER_APPLICATION_MAXIMUM);
		if (sysParam != null) {
			recordQuery = new TransferRecord();
			recordQuery.setMemberNo(balanceTransfer.getMemberNo());
			recordQuery.setApplyTimeStart(ECDateUtils.getCurrentDate());
			recordQuery.setApplyTimeEnd(ECDateUtils.getCurrentDateEndTime());
			transferRecordList = transferRecordService.getTransferRecordList(new Query(recordQuery));
			if (transferRecordList != null) {// 如果今天之内的申请记录大于系统配置则不能再次发起申请
				if (transferRecordList.size() >= Integer.valueOf(sysParam.getParamValue())) {
					resultInfo.setMsg(BalanceTransferConstant.APPLICATION_TIMES_GREATER_MAXIMUM);
					return resultInfo;
				}
			}
		}

		// 开始校验申请提现金额，和会员当前的余额
		if (balanceTransfer.getTransferAmount() == null) {
			resultInfo.setMsg(BalanceTransferConstant.AMOUNT_NO_EXISTENCE);
			return resultInfo;
		} else {
			// 校验金额数值
			BigDecimal transferBigDecimal = new BigDecimal(balanceTransfer.getTransferAmount());
			if (transferBigDecimal.compareTo(new BigDecimal(0)) != 1) {
				resultInfo.setMsg(BalanceTransferConstant.AMOUNT_IS_ILLEGAL);
				return resultInfo;
			}

			// 开始校验会员单次申请提现金额是否大于等于系统设置的限制金额
			sysParam = sysParamService.getByParamKey(BalanceTransferConstant.SYS_TRANSFER_AMOUNT_MAXIMUM);
			if (sysParam != null) {
				BigDecimal maxAmount = new BigDecimal(sysParam.getParamValue());
				if (transferBigDecimal.compareTo(maxAmount) == 1) {
					resultInfo.setMsg(
							String.format(BalanceTransferConstant.AMOUNT_GREATER_MAXIMUM, maxAmount.toString()));
					return resultInfo;
				}
			}
		}

		resultInfo.setCode(Constant.SECCUESS);
		return resultInfo;
	}

	/**
	 * 构建申请余额提现的记录
	 * 
	 * @param balanceTransfer
	 * @param member
	 */
	private TransferRecord createRecordForApplyBalanceTransfer(BalanceTransferVo balanceTransfer, Member member) {

		TransferRecord transferRecord = new TransferRecord();
		transferRecord.setMemberNo(member.getMemberNo());
		transferRecord.setMemberName(member.getMemberName());
		transferRecord.setMobilePhone(member.getMobilePhone());
		transferRecord.setTransferAmount(balanceTransfer.getTransferAmount());
		transferRecord.setTransferMethod(balanceTransfer.getTransferMethod());
		transferRecord.setTransferAccount(balanceTransfer.getTransferAccount());
		transferRecord.setApplyTime(new Date());
		return transferRecord;
	}

	/**
	 * 取消余额提现
	 */
	@RequestMapping("/cancelBalanceTransfer")
	@ResponseBody
	public ResultInfo<TransferRecord> cancelBalanceTransfer(TransferRecord transferRecord) {
		return transferRecordService.cancelBalanceTransfer(transferRecord, true);
	}

	/**
	 * 查询会员提现记录列表
	 */
	@RequestMapping("/getBalanceTransferList")
	@ResponseBody
	public ResultInfo<List<BalanceTransferVo>> getBalanceTransferList(String memberNo, Integer page, Integer pageSize) {
		ResultInfo<List<BalanceTransferVo>> result = new ResultInfo<List<BalanceTransferVo>>();
		if (pageSize == null) {
			pageSize = 10;
		}
		TransferRecord transferRecord = new TransferRecord();
		transferRecord.setMemberNo(memberNo);
		Query query = new Query(page, pageSize, transferRecord);
		try {
			PageFinder<TransferRecord> recordPage = transferRecordService.getTransferRecordPagedList(query);
			if (recordPage != null && recordPage.getData() != null && !recordPage.getData().isEmpty()) {
				result.setData(convertBalanceTransferVo(recordPage));
				result.setCode(Constant.SECCUESS);
				result.setMsg(Constant.YES_RECORD);
			} else {
				result.setCode(Constant.NO_RESULT);
				result.setMsg(Constant.NO_RECORD);
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.toString());
			log.error(e);
		}
		return result;
	}

	/**
	 * TransferRecord转换成BalanceTransferVo类
	 * 
	 * @param recordPage
	 * @return
	 */
	private List<BalanceTransferVo> convertBalanceTransferVo(PageFinder<TransferRecord> recordPage) {
		List<BalanceTransferVo> transferVoList = new ArrayList<BalanceTransferVo>();
		for (TransferRecord record : recordPage.getData()) {
			BalanceTransferVo transferVo = new BalanceTransferVo();
			transferVo.setApplyTime(ECDateUtils.formatDate(record.getApplyTime()));
			transferVo.setTransferTime(ECDateUtils.formatDate(record.getTransferTime()));
			transferVo.setCencorStatus(record.getCencorStatus());

			// 在给予app数据展示时，无需告知失败，原失败状态改为未转账（申请中）。
			if (record.getTransferStatus() == 2) {
				transferVo.setTransferStatus(0);
			} else {
				transferVo.setTransferStatus(record.getTransferStatus());
			}

			String transferAccount = record.getTransferAccount();// 账号(敏感信息)需要替换中间字符为*
			if (transferAccount != null && transferAccount.length() > 2) {
				transferAccount = transferAccount.replaceAll("(?<=\\d{" + transferAccount.length() / 3 + "})\\d(?=\\d{"
						+ transferAccount.length() / 3 + "})", "*");
			} else {
				transferAccount = transferAccount.substring(0, 1) + "*";
			}
			transferVo.setTransferAccount(transferAccount);
			transferVo.setTransferMethod(record.getTransferMethod());
			transferVo.setCancelReason(record.getCancelReason());
			transferVo.setTransferNo(record.getTransferNo());
			transferVo.setTransferAmount(record.getTransferAmount());
			transferVoList.add(transferVo);
		}
		return transferVoList;
	}

}