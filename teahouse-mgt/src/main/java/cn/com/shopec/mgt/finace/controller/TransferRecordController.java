package cn.com.shopec.mgt.finace.controller;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.TransferRecord;
import cn.com.shopec.core.finace.service.TransferRecordService;
import cn.com.shopec.core.member.service.MemberBalanceService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 余额提现记录
 */
@Controller
@RequestMapping("/transferRecord")
public class TransferRecordController extends BaseController{
	
	//private static final Log log = LogFactory.getLog(TransferRecordController.class);
	
	@Resource
	public TransferRecordService transferRecordService;
	@Resource
	private MemberBalanceService memberBalanceService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private MemberService memberService;
	
	/**
	 * 进入余额提现记录列表页面
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/toTransferRecordList")
	public String mainPage(ModelMap modelMap) {
		return "finace/transferRecord_list";
	}
	
	/**
	 * 显示余额提现记录列表信息
	 * @param transferRecord
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/pageListTransferRecord")
	@ResponseBody
	public PageFinder<TransferRecord> pageListInvoice(@ModelAttribute("transferRecord") TransferRecord transferRecord,Query query) throws ParseException {
		Query q = new Query(query.getPageNo(),query.getPageSize(),transferRecord);
		return transferRecordService.getTransferRecordPagedList(q);
	}
	
	/**
	 * 进入余额提现记录详情页
	 * @param transferNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/toTransferRecordView")
	public String toTransferRecordView(String transferNo,ModelMap modelMap) {
		TransferRecord transferRecord = transferRecordService.getTransferRecord(transferNo);
		if(transferRecord.getCencorId() != null){
			SysUser user = sysUserService.detail(transferRecord.getCencorId());
			if(user != null){
				modelMap.put("cencorName", user.getRealName());
			}
		}
		modelMap.put("transferRecord", transferRecord);
		return "finace/transferRecord_view";
	}
	
	/**
	 * 进入余额提现记录审核页
	 * @param transferNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/toTransferRecordCencor")
	public String toTransferRecordCencor(String transferNo,ModelMap modelMap) {
		TransferRecord transferRecord = transferRecordService.getTransferRecord(transferNo);
		modelMap.put("transferRecord", transferRecord);
		return "finace/transferRecord_cencor";
	}
	
	/**
	 * 审核余额提现记录
	 * @param transferNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/cencorTransferRecord")
	@ResponseBody
	public ResultInfo<TransferRecord> cencorTransferRecord(TransferRecord transferRecord,ModelMap modelMap) {
		ResultInfo<TransferRecord> resultInfo =  transferRecordService.cencorTransferRecord(transferRecord, this.getOperator());
		return resultInfo;
	}
	
	/**
	 * 转账
	 * @param transferNo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/transfer")
	@ResponseBody
	public ResultInfo<TransferRecord> transfer(String transferNo,ModelMap modelMap) {
		return transferRecordService.transfer(transferNo, this.getOperator());
	}
	
	/**
	 * 取消
	 * @param transferNo
	 * @param cancelReason
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/cancel")
	@ResponseBody
	public ResultInfo<TransferRecord> cancel(String transferNo,String cancelReason,ModelMap modelMap) {
		TransferRecord transferRecord = new TransferRecord();
		transferRecord.setTransferNo(transferNo);
		transferRecord.setCancelReason(cancelReason);
		return transferRecordService.cancelBalanceTransfer(transferRecord,false);
	}
}
