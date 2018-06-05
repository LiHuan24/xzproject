package cn.com.shopec.core.finace.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.pay.handler.alipay.AlipayBaseUtil;
import cn.com.shopec.common.pay.handler.alipay.AlipayTransferUtil;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.common.BalanceTransferConstant;
import cn.com.shopec.core.finace.dao.TransferRecordDao;
import cn.com.shopec.core.finace.model.Accounts;
import cn.com.shopec.core.finace.model.TransferRecord;
import cn.com.shopec.core.finace.service.AccountsService;
import cn.com.shopec.core.finace.service.TransferRecordService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.member.service.MemberBalanceService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;

/**
 * 转账记录表 服务实现类
 */
@Service
public class TransferRecordServiceImpl implements TransferRecordService {

	private static final Log log = LogFactory.getLog(TransferRecordServiceImpl.class);

	@Resource
	private MemberService memberService;
	@Resource
	private TransferRecordDao transferRecordDao;
	@Resource
	private PrimarykeyService primarykeyService;
	@Resource
	private MemberBalanceService memberBalanceService;
	@Resource
	private AccountsService accountsService;

	/**
	 * 根据查询条件，查询并返回TransferRecord的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<TransferRecord> getTransferRecordList(Query q) {
		List<TransferRecord> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = transferRecordDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<TransferRecord>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回TransferRecord的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<TransferRecord> getTransferRecordPagedList(Query q) {
		PageFinder<TransferRecord> page = new PageFinder<TransferRecord>();

		List<TransferRecord> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = transferRecordDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = transferRecordDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<TransferRecord>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个TransferRecord对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public TransferRecord getTransferRecord(String id) {
		TransferRecord obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = transferRecordDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组TransferRecord对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<TransferRecord> getTransferRecordByIds(String[] ids) {
		List<TransferRecord> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = transferRecordDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<TransferRecord>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的TransferRecord记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<TransferRecord> delTransferRecord(String id, Operator operator) {
		ResultInfo<TransferRecord> resultInfo = new ResultInfo<TransferRecord>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = transferRecordDao.delete(id);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条TransferRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param transferRecord
	 *            新增的TransferRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<TransferRecord> addTransferRecord(TransferRecord transferRecord, Operator operator) {
		ResultInfo<TransferRecord> resultInfo = new ResultInfo<TransferRecord>();

		if (transferRecord == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " transferRecord = " + transferRecord);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (transferRecord.getTransferNo() == null) {
					transferRecord.setTransferNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					transferRecord.setOperatorType(operator.getOperatorType());
					transferRecord.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				transferRecord.setCreateTime(now);
				transferRecord.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(transferRecord);

				// 调用Dao执行插入操作
				transferRecordDao.add(transferRecord);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(transferRecord);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	/**
	 * 根据主键，更新一条TransferRecord记录
	 * 
	 * @param transferRecord
	 *            更新的TransferRecord数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<TransferRecord> updateTransferRecord(TransferRecord transferRecord, Operator operator) {
		ResultInfo<TransferRecord> resultInfo = new ResultInfo<TransferRecord>();

		if (transferRecord == null || transferRecord.getTransferNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " transferRecord = " + transferRecord);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					transferRecord.setOperatorType(operator.getOperatorType());
					transferRecord.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				transferRecord.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = transferRecordDao.update(transferRecord);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(transferRecord);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK() {
		return "Z" + primarykeyService.getValueByBizType(PrimarykeyConstant.recharge);
	}

	/**
	 * 为TransferRecord对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(TransferRecord obj) {
		if (obj != null) {
			if (obj.getTransferStatus() == null) {
				obj.setTransferStatus(0);
			}
			if (obj.getCencorStatus() == null) {
				obj.setCencorStatus(0);
			}
		}
	}

	/**
	 * 审核提现
	 */
	@Transactional
	@Override
	public ResultInfo<TransferRecord> cencorTransferRecord(TransferRecord submitData, Operator operator) {
		ResultInfo<TransferRecord> resultInfo = new ResultInfo<TransferRecord>();
		try {
			TransferRecord transferRecord = new TransferRecord();
			transferRecord.setTransferNo(submitData.getTransferNo());
			transferRecord.setCencorMemo(submitData.getCencorMemo());
			transferRecord.setCencorStatus(submitData.getCencorStatus());
			if (operator != null) {
				transferRecord.setCencorId(operator.getOperatorId());
			}
			resultInfo = this.updateTransferRecord(transferRecord, operator);
			if (resultInfo.getCode().equals(Constant.SECCUESS)) {
				if (submitData.getCencorStatus().equals(2)) {
					this.cancelBalanceTransfer(submitData, false);// 审核不通过时，直接取消提现申请
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 取消余额提现
	 */
	@Override
	@Transactional
	public ResultInfo<TransferRecord> cancelBalanceTransfer(TransferRecord submitData, boolean isUserOperate) {
		ResultInfo<TransferRecord> resultInfo = new ResultInfo<TransferRecord>();
		resultInfo.setCode(Constant.FAIL);// 默认失败
		try {
			TransferRecord transferRecord = transferRecordDao.get(submitData.getTransferNo());
			/*
			 * if (transferRecord == null || transferRecord.getTransferStatus()
			 * != 0) {// 未转账(申请中)
			 * resultInfo.setMsg(BalanceTransferConstant.MEMBER_NO_TRANSFER);
			 * return resultInfo; }
			 */

			MemberBalance memberBalance = memberBalanceService.getMemberBalance(transferRecord.getMemberNo());
			if (memberBalance == null) {
				resultInfo.setMsg(BalanceTransferConstant.MEMBER_IS_NOT_EXISTENCE);
				return resultInfo;
			}

			if (isUserOperate) {
				transferRecord.setTransferStatus(3);// 用户取消
			} else {
				transferRecord.setTransferStatus(4);// 后台取消
				transferRecord.setCancelReason(submitData.getCancelReason());
			}

			if (this.updateTransferRecord(transferRecord, null).getCode().equals(Constant.SECCUESS)) {

				// 开始添加余额记录，并且改变会员余额
				Accounts accounts = new Accounts();
				accounts.setBusinessNo(transferRecord.getTransferNo());
				accounts.setBusinessType(9);// 提现取消
				accounts.setMemberNo(transferRecord.getMemberNo());
				accounts.setAccountType(2);// 入账
				accounts.setAccountMoney(transferRecord.getTransferAmount());
				accounts.setAccountTime(transferRecord.getTransferTime());
				// accounts.setAccountTime(transferRecord.getTransferTime());
				// accounts.setAccountBeforeMoney(memberBalance.getMemberBalance());
				// BigDecimal bigDecimal1 = new
				// BigDecimal(transferRecord.getTransferAmount());
				// BigDecimal bigDecimal2 = new
				// BigDecimal(transferRecord.getTransferAmount());
				// double overMoney =
				// ECNumberUtils.roundDoubleWithScale(bigDecimal1.add(bigDecimal2).doubleValue(),
				// 2);
				// accounts.setAccountOverMoney(overMoney);
				accountsService.addAccounts(accounts, null);

				memberBalance.setMemberBalance(
						ECCalculateUtils.add(memberBalance.getMemberBalance(), transferRecord.getTransferAmount()));
				memberBalanceService.updateMemberBalance(memberBalance, null);

				resultInfo.setCode(Constant.SECCUESS);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 转账
	 */
	@Override
	@Transactional
	public ResultInfo<TransferRecord> transfer(String transferNo, Operator operator) {
		ResultInfo<TransferRecord> resultInfo = new ResultInfo<TransferRecord>();
		resultInfo.setCode(Constant.FAIL);
		// 获取转账单
		TransferRecord transferRecord = this.getTransferRecord(transferNo);
		if (transferRecord == null) {
			resultInfo.setMsg(BalanceTransferConstant.RECORD_IS_NOT_EXISTENCE);
			return resultInfo;
		}
		// 校验会员身份
		Member member = memberService.getMember(transferRecord.getMemberNo());
		if (member != null) {
			if (member.getIsBlacklist() != null && member.getIsBlacklist() == 1) {
				resultInfo.setMsg(BalanceTransferConstant.MEMBER_IS_BLACKLIST);
				return resultInfo;
			}
		} else {
			resultInfo.setMsg(BalanceTransferConstant.MEMBER_IS_NOT_EXISTENCE);
			return resultInfo;
		}

		// 开始转账并且维护数据库中数据
		if (transferRecord.getTransferMethod() == 1) {// 支付宝
			resultInfo = alipayTransfer(transferRecord, operator);
		} else if (transferRecord.getTransferMethod() == 2) {// 微信(预留)

		} else {
			resultInfo.setMsg(BalanceTransferConstant.RECORD_IS_NOT_EXISTENCE);
		}
		return resultInfo;
	}

	/**
	 * 支付宝转账
	 * 
	 * @param memberBalance
	 */
	@Override
	@Transactional
	public ResultInfo<TransferRecord> alipayTransfer(TransferRecord transferRecord, Operator operator) {
		ResultInfo<TransferRecord> resultInfo = new ResultInfo<TransferRecord>();
		resultInfo.setCode(Constant.FAIL);
		boolean transferResult = false;// 转账结果默认失败
		AlipayFundTransToaccountTransferResponse transferResponse = null;
		try {
			// 根据支付宝账号开始转账
			transferResponse = AlipayTransferUtil.transferToResponse(transferRecord.getTransferNo(), // 业务单号，唯一
					transferRecord.getTransferAccount(), // 转入账户（会员填写的账户）
					AlipayTransferUtil.ALIPAY_LOGONID, // 账号类型
					"" + transferRecord.getTransferAmount(), // 金额
					BalanceTransferConstant.ALIPAY_BUSINESS_NAME, // 支付方名称
					BalanceTransferConstant.ALIPAY_REMARK// 备注
			);

		} catch (AlipayApiException e) {
			e.printStackTrace();
			resultInfo.setMsg(BalanceTransferConstant.INVOKE_THIRD_PARTY_ERROR);
			return resultInfo;
		}

		if (transferResponse.getCode().equals(AlipayBaseUtil.SUCCESS_CODE)) {// 成功
			transferResult = true;
		} else if (transferResponse.getSubCode().equals("PAYMENT_INFO_INCONSISTENCY")) {
			// 两次请求商户单号一样。如果是请求单号一样时，则直接当成已经转账成功
			transferResult = true;
		}

		// 处理响应结果，并且维护数据库中数据
		if (transferResult) {
			transferRecord.setTransferStatus(1);
			// 有可能会因"PAYMENT_INFO_INCONSISTENCY"原因，导致OrderId为null，这时需要去请求查询接口
			if (transferResponse.getOrderId() == null) {
				AlipayFundTransOrderQueryResponse queryResponse;
				try {
					queryResponse = AlipayTransferUtil.transferQueryToResponse(transferRecord.getTransferNo());
					transferRecord.setFlowNo(queryResponse.getOrderId());
					Date transferTime = ECDateUtils.parseDate(queryResponse.getPayDate(), ECDateUtils.Format_DateTime);
					transferRecord.setTransferTime(transferTime);
				} catch (AlipayApiException e) {
					log.error(e.getMessage(), e);
					e.printStackTrace();
				}
			} else {
				transferRecord.setFlowNo(transferResponse.getOrderId());
				Date transferTime = ECDateUtils.parseDate(transferResponse.getPayDate(), ECDateUtils.Format_DateTime);
				transferRecord.setTransferTime(transferTime);
			}
			resultInfo.setCode(Constant.SECCUESS);
		} else {
			transferRecord.setTransferStatus(2);
			transferRecord.setErrorCode(transferResponse.getSubCode());
			transferRecord.setErrorMsg(transferResponse.getSubMsg());
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(BalanceTransferConstant.THIRD_PARTY_RESPONSE_ERROR);
		}
		resultInfo = this.updateTransferRecord(transferRecord, operator);
		if (transferResult == false) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(transferResponse.getSubMsg());
		}
		return resultInfo;
	}

	/**
	 * 新增转账记录，并且减去会员余额
	 */
	@Transactional
	@Override
	public ResultInfo<TransferRecord> addTransferRecordForApplyBalance(TransferRecord transferRecord,
			Operator operator) {
		ResultInfo<TransferRecord> resultInfo = new ResultInfo<TransferRecord>();
		resultInfo.setCode(Constant.FAIL);
		try {

			// 校验会员余额
			MemberBalance memberBalance = memberBalanceService.getMemberBalance(transferRecord.getMemberNo());
			BigDecimal bigDecimal = new BigDecimal(transferRecord.getTransferAmount());
			if (memberBalance == null || bigDecimal.compareTo(new BigDecimal(memberBalance.getMemberBalance())) == 1) {
				resultInfo.setMsg(BalanceTransferConstant.MEMBER_AMOUNT_IS_NOT_SUFFICIENT);
				return resultInfo;
			}

			// 新增记录
			if (this.addTransferRecord(transferRecord, operator).getCode().equals(Constant.SECCUESS)) {
				Accounts accounts = new Accounts();
				accounts.setBusinessNo(transferRecord.getTransferNo());
				accounts.setBusinessType(8);// 提现取消
				accounts.setMemberNo(transferRecord.getMemberNo());
				accounts.setAccountType(1);// 出账
				accounts.setAccountMoney(transferRecord.getTransferAmount());
				accounts.setAccountTime(new Date());
				// accounts.setAccountTime(transferRecord.getTransferTime());
				accounts.setAccountBeforeMoney(memberBalance.getMemberBalance());
				BigDecimal bigDecimal1 = new BigDecimal(memberBalance.getMemberBalance());
				BigDecimal bigDecimal2 = new BigDecimal(transferRecord.getTransferAmount());
				double overMoney = ECNumberUtils.roundDoubleWithScale(bigDecimal1.subtract(bigDecimal2).doubleValue(),
						2);
				accounts.setAccountOverMoney(overMoney);
				accountsService.addAccounts(accounts, operator);

				memberBalance.setMemberBalance(overMoney);
				memberBalanceService.updateMemberBalance(memberBalance, operator);

				resultInfo.setCode(Constant.SECCUESS);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

}
