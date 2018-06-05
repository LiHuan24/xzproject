package cn.com.shopec.mgt.finace.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.pay.aliPay.AlipayConfig;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.ECRandomUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.dao.DepositOrderDao;
import cn.com.shopec.core.finace.dao.DepositRefundDao;
import cn.com.shopec.core.finace.dao.DepositStatusDao;
import cn.com.shopec.core.finace.dao.PaymentRecordDao;
import cn.com.shopec.core.finace.model.Accounts;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.DepositStatus;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.service.AccountsService;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.finace.service.DepositRefundService;
import cn.com.shopec.core.finace.service.DepositStatusService;
import cn.com.shopec.core.finace.service.PaymentRecordService;
import cn.com.shopec.core.member.dao.MemberBalanceDao;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.service.DataDictCatService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 押金退款
 */

@Controller
@RequestMapping("/depositRefund")
public class DepositRefundController extends BaseController {

	private static final Log log = LogFactory.getLog(DepositRefundController.class);

	@Resource
	public DepositRefundService depositRefundService;
	@Resource
	private DataDictCatService dataDictCatService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysParamService sysParamService;

	@Resource
	private MemberService memberService;

	@Resource
	private DepositOrderService depositOrderService;
	@Resource
	private PaymentRecordService paymentRecordService;
	@Resource
	private HttpServletRequest request;
	@Resource
	private DepositRefundDao depositRefundDao;
	@Resource
	private DepositOrderDao depositOrderDao;
	@Resource
	private PaymentRecordDao paymentRecordDao;
	@Resource
	private MemberDao memberDao;
	@Resource
	private MemberBalanceDao memberBalanceDao;
	@Resource
	private DepositStatusDao depositStatusDao;
	@Resource
	private AccountsService accountsService;
	@Resource
	private DepositStatusService depositStatusService;

	/*
	 * 进入押金退款列表页面
	 */
	@RequestMapping("/toDepositRefundList")
	public String mainPage(ModelMap modelMap) {
		return "finace/depositRefund_list";
	}

	/*
	 * 显示押金退款列表信息
	 */
	@RequestMapping("/pageListDepositRefund")
	@ResponseBody
	public PageFinder<DepositRefund> pageListInvoice(@ModelAttribute("depositRefund") DepositRefund depositRefund,
			Query query) throws ParseException {
		Query q = new Query(query.getPageNo(), query.getPageSize(), depositRefund);
		return depositRefundService.getDepositRefundPagedList(q);
	}

	/*
	 * 进入退款详情页
	 */
	@RequestMapping("/toDepositRefundView")
	public String toDepositRefundView(String refundNo, ModelMap modelMap) {
		DepositRefund depositRefund = depositRefundService.getDepositRefund(refundNo);
		modelMap.put("depositRefund", depositRefund);
		return "finace/depositRefund_view";
	}

	/*
	 * 进入退款审核页
	 */
	@RequestMapping("/toDepositRefundCencor")
	public String toDepositRefundCencor(String refundNo, ModelMap modelMap) {
		DepositRefund depositRefund = depositRefundService.getDepositRefund(refundNo);
		modelMap.put("depositRefund", depositRefund);
		return "finace/depositRefund_cencor";
	}

	/*
	 * 退款信息审核提交
	 */
	@RequestMapping("/cencorDepositRefund")
	@ResponseBody
	public ResultInfo<DepositRefund> cencorDepositRefund(@ModelAttribute("depositRefund") DepositRefund depositRefund) {
		// 审核通过，进行退款操作
		// 审核不通过，将保证金支付表信息还原
		if (depositRefund.getCencorStatus().equals(3)) {
			DepositRefund dR = depositRefundService.getDepositRefund(depositRefund.getRefundNo());
			if (dR != null) {
				DepositOrder dOrder = depositOrderService.getDepositOrder(dR.getDepositOrderNo());
				if (dOrder.getFrozenAmount() == null) {
					dOrder.setFrozenAmount(0d);
				}
				dOrder.setFrozenAmount(
						ECNumberUtils.roundDoubleWithScale(dOrder.getFrozenAmount() - dR.getRefundAmount(), 2));
				depositOrderService.updateDepositOrder(dOrder, getOperator());
				DepositStatus ds = depositStatusService.getDepositStatus(dR.getMemberNo());
				ds.setDepositStatus(1);
				depositStatusService.updateDepositStatus(ds, null);
			}
		}
		return depositRefundService.updateDepositRefund(depositRefund, getOperator());
	}

	/**
	 * 导出excel文档
	 * 
	 * @param depositRefund
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("toDepositRefundExport")
	public void toDepositRefundExport(@ModelAttribute("depositRefund") DepositRefund depositRefund,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			@SuppressWarnings("deprecation")
			String path = request.getRealPath("/") + "res" + File.separator + "depositRefund.xls";// 声明工作薄
			InputStream is = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);// 生成表格

			List<DepositRefund> list = depositRefundService.getDepositRefundListForManagePage(new Query(depositRefund));
			int rowIndex = 1;
			for (DepositRefund depositRefundData : list) {
				sheet.createRow(rowIndex);
				String censorStatus = "";
				if (depositRefundData.getCencorStatus().equals(0)) {
					censorStatus = "未审核";
				} else if (depositRefundData.getCencorStatus().equals(1)) {
					censorStatus = "已审核";
				} else if (depositRefundData.getCencorStatus().equals(2)) {
					censorStatus = "待审核";
				} else if (depositRefundData.getCencorStatus().equals(3)) {
					censorStatus = "未通过";
				}

				String refundStatus = "";
				if (depositRefundData.getRefundStatus().equals(0)) {
					refundStatus = "未退款";
				} else if (depositRefundData.getRefundStatus().equals(1)) {
					refundStatus = "已退款";
				} else if (depositRefundData.getRefundStatus().equals(2)) {
					refundStatus = "退款失败";
				}

				String refundMethod = "";
				if (depositRefundData.getRefundMethod().equals(1)) {
					refundMethod = "支付宝";
				} else if (depositRefundData.getRefundMethod().equals(2)) {
					refundMethod = "微信";
				} else if (depositRefundData.getRefundMethod().equals(3)) {
					refundMethod = "线下退款";
				}

				int columnIndex = 0;
				setColumnValue(sheet, rowIndex, columnIndex++,
						ECStringUtils.toStringForNull(depositRefundData.getMemberName()));// 客户名称
				setColumnValue(sheet, rowIndex, columnIndex++,
						ECStringUtils.toStringForNull(depositRefundData.getRefundAmount()));// 退款金额
				setColumnValue(sheet, rowIndex, columnIndex++,
						ECDateUtils.formatTime(depositRefundData.getApplyTime()));// 申请时间
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(censorStatus));// 审核状态
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(refundStatus));// 退款状态
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(refundMethod));// 退款方式
				setColumnValue(sheet, rowIndex, columnIndex++,
						ECStringUtils.toStringForNull(depositRefundData.getRefundFlowNo()));// 退款流水号
				setColumnValue(sheet, rowIndex, columnIndex++,
						ECDateUtils.formatTime(depositRefundData.getRefundTime()));// 退款时间
				rowIndex++;
			}

			response.reset();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=depositRefund.xls");
			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据退款记录编号得到退款记录
	 * 
	 * @param depositRefundNo
	 * @return
	 */
	@RequestMapping("/getDepositRefundStatus")
	@ResponseBody
	public ResultInfo<DepositRefund> getDepositRefundStatus(String depositRefundNo) {
		ResultInfo<DepositRefund> resultInfo = new ResultInfo<DepositRefund>();
		if (depositRefundNo != null && !depositRefundNo.equals("")) {
			DepositRefund depositRefund = depositRefundService.getDepositRefund(depositRefundNo);
			if (depositRefund != null) {
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(depositRefund);
				return resultInfo;
			}
		}
		resultInfo.setCode(Constant.FAIL);
		return resultInfo;
	}

	/**
	 * 线下退款
	 * 
	 * @param depositRefund
	 * @return
	 */
	@RequestMapping("/refundDepositByOffline")
	@ResponseBody
	public ResultInfo<DepositRefund> depositRefundPayMemo(DepositRefund depositRefund) {
		DepositRefund dRefund = depositRefundService.getDepositRefund(depositRefund.getRefundNo());
		dRefund.setRefundMemo(depositRefund.getRefundMemo());
		return depositRefundService.refundDepositByOffline(dRefund);
	}

	/**
	 * 微信退款
	 * 
	 * @param depositRefundNo
	 * @return
	 */
	@RequestMapping("/toDepositRefundWXPay")
	@ResponseBody
	public ResultInfo<DepositRefund> refundDepositByWX(String depositRefundNo) {
		DepositRefund dRefund = depositRefundService.getDepositRefund(depositRefundNo);
		DepositOrder dOrder = depositOrderService.getDepositOrder(dRefund.getDepositOrderNo());
		if (dRefund != null && dOrder != null) {
			return depositRefundService.refundDepositByWX(dRefund, dOrder);// 退款功能
		}
		return null;
	}

	/**
	 * 微信转账
	 * 
	 * @param depositRefundNo
	 * @return
	 */
	@RequestMapping("/toDepositRefundWXPayZ")
	@ResponseBody
	public ResultInfo<DepositRefund> transAccountByWX(String depositRefundNo) {
		DepositRefund dRefund = depositRefundService.getDepositRefund(depositRefundNo);
		if (dRefund != null) {
			DepositOrder dOrder = depositOrderService.getDepositOrder(dRefund.getDepositOrderNo());

			if (dOrder != null && dOrder.getPaymentFlowNo() != null) {

				PaymentRecord paymentRecord = paymentRecordService.getPaymentRecordByFlowNo(dOrder.getPaymentFlowNo());
				if (paymentRecord != null) {
					return depositRefundService.transAccountByWX(dRefund, dOrder, paymentRecord, request);// 退款功能
				}
			}
		}
		return null;
	}

	/**
	 * 押金退款 -- 支付宝方式，首先判断是否支持退款，否则进行转账
	 * 
	 * @param depositRefundNo
	 * @param request
	 * @param response
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/toDepositRefundPay")
	@ResponseBody
	public ResultInfo<String> refundDepositByAlipay(String depositRefundNo, HttpServletRequest request, ModelMap model)
			throws Exception {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		Operator operator = getOperator();
		if (null == operator) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("登录信息过期，请重新登录后进行操作");

			return resultInfo;
		}
		// 验证当前订单是否在有效期内
		Map<String, String> tradeInfo = new HashMap<String, String>();
		ResultInfo<String> result = depositRefundService.checkSignOrderByAlipay(depositRefundNo, tradeInfo);

		log.info("## tradeInfo=" + tradeInfo);

		if (result == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("系统异常，请稍后再试");

			return resultInfo;

		}
		log.info("result.code = " + result.getCode() + ",result.data=" + result.getData() + ",result.msg="
				+ result.getMsg());
		// 查询成功，在有效期，进入退款页面
		if (Constant.SECCUESS.equals(result.getCode()) && "TRADE_SUCCESS".equals(result.getData())) {
			ResultInfo<String> info = cancelOrderRquestAlipay(depositRefundNo);
			if (info.getCode().equals(Constant.SECCUESS)) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			return resultInfo;
			// 查询成功，不在有效期，进入转账
		} else if (Constant.SECCUESS.equals(result.getCode())
				&& ("TRADE_FINISHED".equals(result.getData()) || "TRADE_HAS_FINISHED".equals(result.getData()))) {
			ResultInfo<String> info = transferAccountsAlipay(depositRefundNo);
			if (info.getCode().equals(Constant.SECCUESS)) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			return resultInfo;

		} else if (Constant.FAIL.equals(result.getCode())) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("系统异常，请稍后再试");

			return resultInfo;
		}
		return resultInfo;

	}

	// 支付宝退款完成后的回调方法
	@RequestMapping("/depositRefundAlipayUpdate")
	@ResponseBody
	public void alipayRefundCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		depositRefundService.alipayRefundCallback(request, response);
	}

	/**
	 * 支付宝转账
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public ResultInfo<String> transferAccountsAlipay(String depositRefundNo) {

		ResultInfo<String> resultInfo = new ResultInfo<String>();

		try {
			DepositRefund dr = depositRefundDao.get(depositRefundNo);
			// 根据退款记录查询押金支付记录中的支付宝交易号
			DepositOrder dOrder = depositOrderDao.get(dr.getDepositOrderNo());
			PaymentRecord record = paymentRecordDao.getBydepositRefundNo(depositRefundNo);

			Double orderRefundAmount = dr.getRefundAmount();// 实际退款金额
			String appId = AlipayConfig.appId;// 账户ID
			String privateKey = AlipayConfig.rsa_private;// 私钥
			String publicKey = AlipayConfig.rsa_public;// 公钥
			String outBizNo = dOrder.getPartTradeFlowNo();// 订单支付时传入的商户订单号

			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId,
					privateKey, "json", "GBK", publicKey, "RSA");
			AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
			request.setBizContent("{" + "\"out_biz_no\":\"" + outBizNo + "\"," + "\"payee_type\":\"ALIPAY_USERID\","
					+ "\"payee_account\":\"" + record.getBuyerId() + "\"," + "\"amount\":\"" + orderRefundAmount + "\","
					+ "\"remark\":\"押金转账退款\"" + "}");
			AlipayFundTransToaccountTransferResponse response = null;
			try {
				response = alipayClient.execute(request);
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (response.isSuccess()) {

				System.out.println("调用成功");
				// 对保证金退款记录表进行修改，对保证金支付表进行修改
				if (dr != null) {
					dr.setRefundFlowNo(dOrder.getPartTradeFlowNo());// 退款流水号
					dr.setRefundTime(new Date());
					dr.setRefundStatus(1);// 退款状态1.已退款
					dr.setUpdateTime(dr.getRefundTime());
					depositRefundDao.update(dr);
					// DepositOrder dOrder =
					// depositOrderDao.get(dr.getDepositOrderNo());
					if (dOrder != null) {
						dOrder.setRemainAmount(ECNumberUtils
								.roundDoubleWithScale(dOrder.getRemainAmount() - dOrder.getFrozenAmount(), 2));
						if (dOrder.getRefundAmount() == null) {
							dOrder.setRefundAmount(0d);
						}
						dOrder.setRefundAmount(ECNumberUtils
								.roundDoubleWithScale(dOrder.getRefundAmount() + dOrder.getFrozenAmount(), 2));
						;
						dOrder.setFrozenAmount(0d);
						dOrder.setUpdateTime(new Date());
						dOrder.setIsAvailable(0);// 不可用
						depositOrderDao.update(dOrder);
						Member member = memberDao.get(dOrder.getMemberNo());
						if (member != null) {
							// phoneNo = member.getMobilePhone();

							// 修改会员余额
							MemberBalance ml = memberBalanceDao.get(member.getMemberNo());
							if (ml != null) {
								ml.setMemberDeposit(0D);
								memberBalanceDao.update(ml);
							}
							// 修改押金状态
							DepositStatus ds = depositStatusDao.get(member.getMemberNo());
							if (ds != null) {
								ds.setDepositStatus(0);
								depositStatusDao.update(ds);

							}

							// 记录会员交易明细
							Accounts accounts = new Accounts();
							accounts.setAccountNo(accountsService.generatePK());
							accounts.setMemberNo(dOrder.getMemberNo());
							accounts.setBusinessNo(dr.getRefundNo());
							accounts.setBusinessType(4);
							accounts.setAccountType(2);
							accounts.setAccountMoney(dr.getRefundAmount());
							accounts.setAccountTime(new Date());
							accountsService.addAccounts(accounts, null);

						}
					}
				}
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				System.out.println("调用失败");
				resultInfo.setCode(Constant.FAIL);
			}
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			e.printStackTrace();
		}

		return resultInfo;

	}

	/**
	 * 支付宝退款
	 */
	public ResultInfo<String> cancelOrderRquestAlipay(String depositRefundNo) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		DepositRefund dr = depositRefundDao.get(depositRefundNo);
		// 根据退款记录查询押金支付记录中的支付宝交易号
		DepositOrder dOrder = depositOrderDao.get(dr.getDepositOrderNo());

		Double orderRefundAmount = dr.getRefundAmount();// 实际退款金额
		String appId = AlipayConfig.appId;// 账户ID
		String privateKey = AlipayConfig.rsa_private;// 私钥
		String publicKey = AlipayConfig.rsa_public;// 公钥
		String outBizNo = dOrder.getPartTradeFlowNo();// 订单支付时传入的商户订单号
		String paymentFlowNo = dOrder.getPaymentFlowNo();// 支付宝交易号

		String outRequestNo = ECRandomUtils.getRandomHexStr(16);// 自定义内部退款流水号

		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", appId, privateKey,
				"json", "UTF-8", publicKey, "RSA");
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizContent("{" + "\"out_trade_no\":\"" + outBizNo + "\"," + "\"trade_no\":\"" + paymentFlowNo + "\","
				+ "\"refund_amount\":\"" + orderRefundAmount + "\"," + "\"refund_reason\":\"" + "押金退款" + "\","
				+ "\"out_request_no\":\"" + outRequestNo + "\"}");

		try {
			AlipayTradeRefundResponse alipayResponse = alipayClient.execute(request);
			log.info("response:" + alipayResponse);// 获取退款结果
			if (alipayResponse.isSuccess()) { // 调用退款接口成功
				if ("10000".equals(alipayResponse.getCode())) {
					// themeOrder.setPartTradeFlowNo(outBizNo);
					// themeOrder.setPaymentFlowNo(paymentFlowNo);
					// themeOrder.setRefundFlowNo("ALIPAY_REFUND_" +
					// themeOrder.getThemeOrderNo());
					// themeOrder.setOutRefundFlowNo(outRequestNo);
					// themeOrder.setPayableAmount(ECCalculateUtils.sub(themeOrder.getPayableAmount(),
					// orderRefundAmount));// 修改实际支付金额
					// resultInfo.setData(themeOrder);
					// 对保证金退款记录表进行修改，对保证金支付表进行修改
					if (dr != null) {
						dr.setRefundFlowNo(outBizNo);// 退款流水号
						dr.setRefundTime(new Date());
						dr.setRefundStatus(1);// 退款状态1.已退款
						dr.setUpdateTime(dr.getRefundTime());
						depositRefundDao.update(dr);
						// DepositOrder dOrder =
						// depositOrderDao.get(dr.getDepositOrderNo());
						if (dOrder != null) {
							dOrder.setRemainAmount(ECNumberUtils
									.roundDoubleWithScale(dOrder.getRemainAmount() - dOrder.getFrozenAmount(), 2));
							if (dOrder.getRefundAmount() == null) {
								dOrder.setRefundAmount(0d);
							}
							dOrder.setRefundAmount(ECNumberUtils
									.roundDoubleWithScale(dOrder.getRefundAmount() + dOrder.getFrozenAmount(), 2));
							;
							dOrder.setFrozenAmount(0d);
							dOrder.setUpdateTime(new Date());
							dOrder.setIsAvailable(0);// 不可用
							depositOrderDao.update(dOrder);
							Member member = memberDao.get(dOrder.getMemberNo());
							if (member != null) {
								// phoneNo = member.getMobilePhone();

								// 修改会员余额
								MemberBalance ml = memberBalanceDao.get(member.getMemberNo());
								if (ml != null) {
									ml.setMemberDeposit(0D);
									memberBalanceDao.update(ml);
								}
								// 修改押金状态
								DepositStatus ds = depositStatusDao.get(member.getMemberNo());
								if (ds != null) {
									ds.setDepositStatus(0);
									depositStatusDao.update(ds);

								}

								// 记录会员交易明细
								Accounts accounts = new Accounts();
								accounts.setAccountNo(accountsService.generatePK());
								accounts.setMemberNo(dOrder.getMemberNo());
								accounts.setBusinessNo(dr.getRefundNo());
								accounts.setBusinessType(4);
								accounts.setAccountType(2);
								accounts.setAccountMoney(dr.getRefundAmount());
								accounts.setAccountTime(new Date());
								accountsService.addAccounts(accounts, null);

							}
						}
					}
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setMsg("退款成功");
				} else {
					// 支付宝退款查询失败原因
					try {
						AlipayTradeFastpayRefundQueryRequest requestInfo = new AlipayTradeFastpayRefundQueryRequest();
						requestInfo.setBizContent("{" + "\"out_trade_no\":\"" + outBizNo + "\"" + ","
								+ "\"out_request_no\":\"" + outRequestNo + "\"}");
						AlipayTradeFastpayRefundQueryResponse getInfoResponse = alipayClient.execute(requestInfo);
						log.info("getInfoResponse:" + getInfoResponse);
						if (getInfoResponse.isSuccess()) {
							if (!"10000".equals(getInfoResponse.getCode())) {
								// themeOrder.setRefundFailInfo(getInfoResponse.getRefundReason());
								log.error("query transAccount fail ,out_biz_no is" + outBizNo + "the info is"
										+ getInfoResponse.getRefundReason());
							}
						} else {
							// themeOrder.setRefundFailInfo(getInfoResponse.getRefundReason());
							log.error("query transAccount fail ,out_biz_no is" + outBizNo + "the info is"
									+ getInfoResponse.getRefundReason());
						}
					} catch (Exception e) {
						log.info(e.getMessage());
					}
					resultInfo.setCode(Constant.FAIL);
					// resultInfo.setData(themeOrder);
					resultInfo.setMsg("退款失败");
				}
			} else {
				// themeOrder.setRefundFailInfo(alipayResponse.getMsg());
				resultInfo.setCode(Constant.FAIL);
				// resultInfo.setData(themeOrder);
				resultInfo.setMsg("退款请求失败，失败原因为:" + alipayResponse.getMsg());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return resultInfo;
	}
}
