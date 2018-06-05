package cn.com.shopec.core.pay.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.JDOMException;
import org.springframework.stereotype.Service;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.cache.CommonCacheUtil;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.pay.aliPay.AlipayConfig;
import cn.com.shopec.common.pay.aliPay.AlipayNotify;
import cn.com.shopec.common.pay.aliPay.OrderInfoUtil2_0;
import cn.com.shopec.common.pay.wxPay.CommonUtil;
import cn.com.shopec.common.pay.wxPay.PayCommonUtil;
import cn.com.shopec.common.pay.wxPay.ResponseHandler;
import cn.com.shopec.common.pay.wxPay.TenpayUtil;
import cn.com.shopec.common.pay.wxPay.WxpayConfig;
import cn.com.shopec.common.pay.wxPay.XMLUtil;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.common.utils.ECRandomUtils;
import cn.com.shopec.common.utils.Uint32;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.common.DepositConstant;
import cn.com.shopec.core.finace.model.Accounts;
import cn.com.shopec.core.finace.model.CoursePackage;
import cn.com.shopec.core.finace.model.CoursePackageOrder;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositStatus;
import cn.com.shopec.core.finace.model.GymCard;
import cn.com.shopec.core.finace.model.GymCardOrder;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.model.Recharge;
import cn.com.shopec.core.finace.model.RechargeOrder;
import cn.com.shopec.core.finace.service.AccountsService;
import cn.com.shopec.core.finace.service.CoursePackageOrderService;
import cn.com.shopec.core.finace.service.CoursePackageService;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.finace.service.DepositStatusService;
import cn.com.shopec.core.finace.service.GymCardOrderService;
import cn.com.shopec.core.finace.service.GymCardService;
import cn.com.shopec.core.finace.service.PaymentRecordService;
import cn.com.shopec.core.finace.service.RechargeOrderService;
import cn.com.shopec.core.finace.service.RechargeService;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.member.model.MemberGymCard;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.model.MemberPointsRecord;
import cn.com.shopec.core.member.model.MemberPointsRule;
import cn.com.shopec.core.member.service.MemberBalanceService;
import cn.com.shopec.core.member.service.MemberGymCardService;
import cn.com.shopec.core.member.service.MemberLevelService;
import cn.com.shopec.core.member.service.MemberPointsRecordService;
import cn.com.shopec.core.member.service.MemberPointsRuleService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.dao.ThemeOrderDao;
import cn.com.shopec.core.order.model.CommunityOrder;
import cn.com.shopec.core.order.model.PayThemeOrderVo;
import cn.com.shopec.core.order.model.ThemeOrder;
import cn.com.shopec.core.order.service.CommunityOrderService;
import cn.com.shopec.core.order.service.ThemeOrderService;
import cn.com.shopec.core.pay.PayService;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.system.dao.SysParamDao;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.themepavilion.dao.BespeakDao;
import cn.com.shopec.core.themepavilion.model.ArrayCourse;
import cn.com.shopec.core.themepavilion.model.Bespeak;
import cn.com.shopec.core.themepavilion.service.ArrayCourseService;
import cn.com.shopec.core.themepavilion.service.BespeakService;
import cn.com.shopec.core.themepavilion.service.LineUpService;

/**
 * 支付
 */
@Service
public class PayServiceImpl implements PayService {
	@Resource
	private CommunityOrderService communityOrderService;
	@Resource
	private SysParamDao sysParamDao;
	@Resource
	private ThemeOrderService themeOrderService;
	@Resource
	private ThemeOrderDao themeOrderDao;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private MemberBalanceService memberBalanceService;
	@Resource
	private DepositOrderService depositOrderService;
	@Resource
	private RechargeService rechargeService;
	@Resource
	private MemberService memberService;
	@Resource
	private RechargeOrderService rechargeOrderService;
	@Resource
	private GymCardService gymCardService;
	@Resource
	private GymCardOrderService gymCardOrderService;
	@Resource
	private CoursePackageService coursePackageService;
	@Resource
	private CoursePackageOrderService coursePackageOrderService;
	@Resource
	private MemberPointsRuleService memberPointsRuleService;
	@Resource
	private MemberPointsRecordService memberPointsRecordService;
	@Resource
	private MemberLevelService memberLevelService;
	@Resource
	private AccountsService accountsService;
	@Resource
	private MemberGymCardService memberGymCardService;
	@Resource
	private PaymentRecordService paymentRecordService;
	@Resource
	private DepositStatusService depositStatusService;
	@Resource
	private ArrayCourseService arrayCourseService;
	@Resource
	private StoreService storeService;
	@Resource
	private CouponService couponService;
	@Resource
	private BespeakService bespeakService;
	@Resource
	private BespeakDao bespeakDao;
	@Resource
	private LineUpService lineUpService;
	@Resource
	private CommonCacheUtil cacheUtil;

	private static final Log log = LogFactory.getLog(PayServiceImpl.class);

	/**
	 * 微信支付回调方法
	 */
	@Override
	public void wxUpdateOrder(HttpServletRequest request, HttpServletResponse response, Operator operator,
			String trigger) {
		String out_trade_no = "";
		synchronized (this) {
			try {
				String appkey = WxpayConfig.key;// 商户平台上那个自己生成的KEY
				ResponseHandler resHandler = new ResponseHandler(request, response);
				resHandler.setKey(appkey);
				Map postdata = resHandler.getSmap();
				if (resHandler.isWechatSign()) {// 是否微信签名
					// 商户订单号 即 附带随机码的平台内部支付流水号
					String part_trade_flow_no = (String) postdata.get("out_trade_no");
					// 支付订单号
					String trade_no = part_trade_flow_no.substring(0, part_trade_flow_no.lastIndexOf("_"));
					// 微信支付订单号
					String transaction_id = (String) postdata.get("transaction_id");
					// 金额,以分为单位
					Double totalFee = Double.valueOf(postdata.get("total_fee") + "");
					totalFee = totalFee / 100; // 这块需转换
					// 支付完成时间
					String time_end = (String) postdata.get("time_end");
					// 支付结果 业务结果
					String trade_state = (String) postdata.get("result_code");

					// 微信用户openId
					String openId = (String) postdata.get("openid");

					String trade_type = (String) postdata.get("trade_type");
					/*
					 * 支付记录表添加数据
					 */
					PaymentRecord pr = new PaymentRecord();

					// 判断签名及结果
					if ("SUCCESS".equals(trade_state)) {
						// 即时到账处理业务开始
						String flag = trade_no.substring(0, 1);
						if (flag.equals("S")) {// 社区订单
							CommunityOrder order = communityOrderService.getCommunityOrder(trade_no);
							if (null != order && order.getPayStatus() == 0) {
								order.setPayStatus(1);// 支付状态为已支付
								order.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								if ("JSAPI".equals(trade_type)) {
									order.setPaymentMethod(3);
								}
								order.setPaymentFlowNo(transaction_id);// 支付流水号（微信支付订单号）
								order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
								if (time_end != null) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
									String dstr = time_end;
									Date nodate = sdf.parse(dstr);
									order.setPaymentTime(nodate);
								} else {
									order.setPaymentTime(new Date());
								}
								communityOrderService.updateCommunityOrder(order, null);
								// 判断是否使用余额
								if (order.getBalanceDiscountAmount() > 0) {
									MemberBalance mc = memberBalanceService.getMemberBalance(order.getMemberNo());
									if (mc != null) {
										mc.setMemberBalance(ECCalculateUtils.sub(mc.getMemberBalance(),
												order.getBalanceDiscountAmount()));
										memberBalanceService.updateMemberBalance(mc, null);
									}

								}

								// 修改优惠券
								if (StringUtils.isNotBlank(order.getCouponNo())) {
									Coupon coupon = couponService.getCoupon(order.getCouponNo());
									if (coupon != null) {
										Coupon cp = new Coupon();
										cp.setCouponNo(coupon.getCouponNo());
										cp.setUsedStatus(1);// 设置为已使用
										couponService.updateCoupon(cp, null);
									}
								}

								// 记录会员交易明细
								Accounts accounts = new Accounts();
								accounts.setAccountNo(accountsService.generatePK());
								accounts.setMemberNo(order.getMemberNo());
								accounts.setBusinessNo(order.getCommunityOrderNo());
								accounts.setBusinessType(1);// 社区订单
								accounts.setAccountType(1);// 出账 -
								accounts.setAccountMoney(order.getPayableAmount());
								accounts.setAccountTime(new Date());
								accountsService.addAccounts(accounts, null);

								pr.setBizObjType(1);// 订单
								pr.setBizObjNo(order.getCommunityOrderNo());
								pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
								pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

								payOverAddRecordOrder(order);
								// 更新会员支付总额
								// memberService.updateMemberRealAmount(order.getMemberNo(),
								// ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(),
								// 2));
							} else {
								log.info("重复请求");
								return;
							}

						} else if (flag.equals("Z")) {// 主题订单
							ThemeOrder order = themeOrderService.getThemeOrder(trade_no);
							if (null != order && order.getPayStatus() == 0) {
								// 根据订单中的排课编号统计属于该课程的预约人数
								long appionNumber = themeOrderService.statCourseAppoinNumber(order.getArrangeNo());
								// 查询该课程的排课人数
								ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(order.getArrangeNo());
								if (String.valueOf(appionNumber) == null) {
									order.setOrderStatus(0);// 已预约
								} else {
									if (appionNumber >= arrayCourse.getPeopleNumber()) {
										order.setOrderStatus(4);// 预约人数已大于等于课程排课人数，此时修改订单状态为：排队中
									} else {
										order.setOrderStatus(0);// 已预约
									}
								}

								order.setPayStatus(1);// 支付状态为已支付
								order.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								if ("JSAPI".equals(trade_type)) {
									order.setPaymentMethod(3);
								}
								order.setPaymentFlowNo(transaction_id);// 支付流水号（微信支付订单号）
								order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
								if (time_end != null) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
									String dstr = time_end;
									Date nodate = sdf.parse(dstr);
									order.setPaymentTime(nodate);
								} else {
									order.setPaymentTime(new Date());
								}
								themeOrderService.updateThemeOrder(order, null);

								// 修改会员余额表
								MemberBalance memberBalance = memberBalanceService.getMemberBalance(order.getMemberNo());
								if (memberBalance != null) {
									MemberBalance mb = new MemberBalance();
									mb.setMemberNo(memberBalance.getMemberNo());

									if (order.getCourseBagNum() != null && order.getPeopleNumber() != null) {
										if (order.getPeopleNumber() > memberBalance.getCourseNumber()) {
											mb.setCourseNumber(0);
										} else {
											int surplusCourseBag = memberBalance.getCourseNumber() - order.getCourseBagNum();
											mb.setCourseNumber(surplusCourseBag);
										}
									}
									if (order.getBalanceDiscountAmount() != null) {
										if (memberBalance.getMemberBalance().compareTo(order.getBalanceDiscountAmount()) >= 0) {
											double surplusAmount = ECCalculateUtils.sub(memberBalance.getMemberBalance(), order.getBalanceDiscountAmount());
											mb.setMemberBalance(surplusAmount);
										} else {
											mb.setMemberBalance(0d);
										}
									}
									memberBalanceService.updateMemberBalance(mb, null);
								}

								// 修改优惠券为已使用
								if (StringUtils.isNotBlank(order.getCouponNo())) {
									Coupon coupon = couponService.getCoupon(order.getCouponNo());
									if (coupon != null) {
										Coupon cp = new Coupon();
										cp.setCouponNo(coupon.getCouponNo());
										cp.setUsedStatus(1);// 设置为已使用
										couponService.updateCoupon(cp, null);
									}
								}

								// 根据统计属于该课程的订单预约排队人数小于0 生成预约表
								if (arrayCourse != null) {
									long lineNum = themeOrderDao.statCourseLineNumber(order.getArrangeNo());
									if (lineNum <= 0) {
										Bespeak bespeakBean = bespeakDao.getBespeakByArrayCourseNo(arrayCourse.getArrayCourseNo());
										if (bespeakBean == null) {
											// 主题馆订单预约支付后生成预约表
											Bespeak bespeak = new Bespeak();
											bespeak.setBespeakNo(bespeakService.generatePK());
											bespeak.setArrayCourseNo(arrayCourse.getArrayCourseNo());
											bespeak.setBespeakDate(new Date());
											bespeak.setPeopleNumber(arrayCourse.getPeopleNumber());
											bespeak.setReservation(order.getPeopleNumber());
											bespeakService.addBespeak(bespeak, null);
										} else {
											Bespeak bp = new Bespeak();
											bp.setBespeakNo(bespeakBean.getBespeakNo());
											int appionNum = order.getPeopleNumber();
											bp.setReservation(appionNum + bespeakBean.getReservation());
											bespeakService.updateBespeak(bp, null);
										}

										// 生成预约表后更新排课表中预约人数
										/*ArrayCourse course = new ArrayCourse();
										course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
										int reservation = 0;
										reservation = order.getPeopleNumber();
										course.setReservation(reservation + arrayCourse.getReservation());
										arrayCourseService.updateArrayCourse(course, null);*/
									} 
									/*else {
										// 否则更新排课表排课人数
										ArrayCourse course = new ArrayCourse();
										course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
										int linNum = 0;
										linNum = order.getPeopleNumber();
										course.setLineUp(linNum + arrayCourse.getLineUp());
										arrayCourseService.updateArrayCourse(course, null);
									}*/
								}

								// 记录会员交易明细
								Accounts accounts = new Accounts();
								accounts.setAccountNo(accountsService.generatePK());
								accounts.setMemberNo(order.getMemberNo());
								accounts.setBusinessNo(order.getThemeOrderNo());
								accounts.setBusinessType(2);// 主题馆课程订单
								accounts.setAccountType(1);// 出账 -
								accounts.setAccountMoney(order.getPayableAmount());
								accounts.setAccountTime(new Date());
								accountsService.addAccounts(accounts, null);

								pr.setBizObjType(2);// 订单
								pr.setBizObjNo(order.getThemeOrderNo());
								pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
								pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							} else {
								log.info("重复请求");
								return;
							}
						} else if (flag.equals("Y")) {// 押金订单
							DepositOrder dOrder = depositOrderService.getDepositOrder(trade_no);
							if (null != dOrder && dOrder.getPayStatus() == 0) {
								dOrder.setPayStatus(1);// 已支付
								dOrder.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								if ("JSAPI".equals(trade_type)) {
									dOrder.setPaymentMethod(3);
								}
								if (time_end != null) {
									SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
									String dstr = time_end;
									Date nodate = sdf.parse(dstr);
									dOrder.setPaymentTime(nodate);
								} else {
									dOrder.setPaymentTime(new Date());
								}
								dOrder.setRemainAmount(
										ECNumberUtils.roundDoubleWithScale(dOrder.getDepositAmount(), 2));
								dOrder.setPaymentFlowNo(transaction_id);
								dOrder.setPartTradeFlowNo(part_trade_flow_no); // 设置内部支付流水号编号
								dOrder.setIsAvailable(1);
								depositOrderService.updateDepositOrder(dOrder, null);

								pr.setBizObjType(3);// 押金
								pr.setBizObjNo(dOrder.getDepositOrderNo());
								pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
								pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
								MemberBalance me = memberBalanceService.getMemberBalance(dOrder.getMemberNo());
								if (me.getMemberDeposit() == null) {
									me.setMemberDeposit(0D);
								}

								me.setMemberDeposit(
										ECCalculateUtils.add(me.getMemberDeposit(), dOrder.getPayableAmount()));
								memberBalanceService.updateMemberBalance(me, null);
								// 修改会员押金状态信息
								DepositStatus ds = depositStatusService.getDepositStatus(dOrder.getMemberNo());
								ds.setDepositStatus(1);
								ds.setPaymentType(1);
								depositStatusService.updateDepositStatus(ds, null);

								// 记录会员交易明细
								Accounts accounts = new Accounts();
								accounts.setAccountNo(accountsService.generatePK());
								accounts.setMemberNo(dOrder.getMemberNo());
								accounts.setBusinessNo(dOrder.getDepositOrderNo());
								accounts.setBusinessType(3);// 交押金
								accounts.setAccountType(1);
								accounts.setAccountTime(new Date());
								accounts.setAccountMoney(dOrder.getDepositAmount());
								accountsService.addAccounts(accounts, null);

							} else {
								log.info("重复请求");
								return;
							}

						} else if (flag.equals("C")) {// 充值订单

							RechargeOrder order = rechargeOrderService.getRechargeOrder(trade_no);
							if (null != order && order.getPayStatus() == 0) {
								order.setPayStatus(1);// 已支付
								order.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								if ("JSAPI".equals(trade_type)) {
									order.setPaymentMethod(3);
								}
								order.setIsAvailable(1);// 可用
								order.setPaymentFlowNo(transaction_id);
								order.setPartTradeFlowNo(part_trade_flow_no); // 设置内部支付流水号编号
								rechargeOrderService.updateRechargeOrder(order, null);

								// 查询我的余额
								MemberBalance me = memberBalanceService.getMemberBalance(order.getMemberNo());
								if (me.getMemberBalance() == null) {
									me.setMemberBalance(0D);
								}

								// 充值成功后给记账表添加记录
								Accounts ac = new Accounts();
								ac.setMemberNo(order.getMemberNo());
								ac.setBusinessNo(order.getRechargeOrderNo());
								ac.setBusinessType(5);
								ac.setAccountType(2);
								ac.setAccountMoney(order.getPayableAmount());
								ac.setAccountBeforeMoney(me.getMemberBalance());
								ac.setAccountOverMoney(
										ECCalculateUtils.add(order.getPayableAmount(), ac.getAccountMoney()));
								ac.setAccountTime(new Date());
								accountsService.addAccounts(ac, null);

								me.setMemberBalance(
										ECCalculateUtils.add(me.getMemberBalance(), order.getRechargeAmount()));
								memberBalanceService.updateMemberBalance(me, null);

								pr.setBizObjType(4);
								pr.setBizObjNo(order.getRechargeOrderNo());
								pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
								pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
							} else {
								log.info("重复请求");
								return;
							}
						} else if (flag.equals("J")) {// 健身卡

							GymCardOrder order = gymCardOrderService.getGymCardOrder(trade_no);
							if (null != order && order.getPayStatus() == 0) {
								order.setPayStatus(1);// 已支付
								order.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								if ("JSAPI".equals(trade_type)) {
									order.setPaymentMethod(3);
								}
								order.setIsAvailable(1);// 可用
								order.setPaymentFlowNo(transaction_id);
								order.setPartTradeFlowNo(part_trade_flow_no); // 设置内部支付流水号编号
								gymCardOrderService.updateGymCardOrder(order, null);

								// 查看会员是否有正在使用的健身包月卡
								MemberGymCard mc = new MemberGymCard();
								mc.setMemberNo(order.getMemberNo());
								mc.setGymcardStatus(1);
								mc.setGymCardType(1);
								List<MemberGymCard> ms = memberGymCardService.getMemberGymCardList(new Query(mc));
								if (ms.size() > 0) {
									MemberGymCard mg = ms.get(0);

									Date d = mg.getEndTime();
									mg.setEndTime(ECDateUtils.getDateAfter(d, 30));
									memberGymCardService.updateMemberGymCard(mg, null);

								} else {
									Member m = memberService.getMember(order.getMemberNo());

									MemberGymCard mg = new MemberGymCard();
									mg.setMemberNo(m.getMemberNo());
									mg.setMemberName(m.getMemberName());
									mg.setGymCardNo(order.getGymCardNo());
									mg.setGymCardName(order.getGymCardName());
									mg.setGymCardType(1);
									mg.setGymcardStatus(1);
									mg.setStartTime(new Date());
									mg.setEndTime(ECDateUtils.getDateAfter(new Date(), 30));
									memberGymCardService.addMemberGymCard(mg, null);

								}

								// 记录会员交易明细
								Accounts accounts = new Accounts();
								accounts.setAccountNo(accountsService.generatePK());
								accounts.setMemberNo(order.getMemberNo());
								accounts.setBusinessNo(order.getGymCardNo());
								accounts.setBusinessType(6);// 购买健身卡
								accounts.setAccountType(1);// + 入账
								accounts.setAccountMoney(order.getPayableAmount());
								accounts.setAccountTime(new Date());
								accountsService.addAccounts(accounts, null);

								pr.setBizObjType(5);
								pr.setBizObjNo(order.getGymCardOrderNo());
								pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
								pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

							} else {
								log.info("重复请求");
								return;
							}
						} else if (flag.equals("K")) {// 课程包订单

							CoursePackageOrder order = coursePackageOrderService.getCoursePackageOrder(trade_no);
							if (null != order && order.getPayStatus() == 0) {
								order.setPayStatus(1);// 已支付
								order.setPaymentMethod(2);// 支付方式（1.支付宝，2.微信）
								if ("JSAPI".equals(trade_type)) {
									order.setPaymentMethod(3);
								}
								order.setIsAvailable(1);// 可用
								order.setPaymentFlowNo(transaction_id);
								order.setPartTradeFlowNo(part_trade_flow_no); // 设置内部支付流水号编号
								coursePackageOrderService.updateCoursePackageOrder(order, null);
								// 查询课程包节数
								CoursePackage cg = coursePackageService.getCoursePackage(order.getCoursePackageNo());

								// 修改会员的课程节数
								MemberBalance me = memberBalanceService.getMemberBalance(order.getMemberNo());
								if (me.getCourseNumber() == null) {
									me.setCourseNumber(0);
								}

								me.setCourseNumber(me.getCourseNumber() + cg.getCourseNumber());
								memberBalanceService.updateMemberBalance(me, null);

								// 记录会员交易明细
								Accounts accounts = new Accounts();
								accounts.setAccountNo(accountsService.generatePK());
								accounts.setMemberNo(order.getMemberNo());
								accounts.setBusinessNo(order.getRechargeOrderNo());
								accounts.setBusinessType(7);// 购买课程包
								accounts.setAccountType(1);// - 出账
								accounts.setAccountMoney(order.getPayableAmount());
								accounts.setAccountTime(new Date());
								accountsService.addAccounts(accounts, null);

								pr.setBizObjType(6);
								pr.setBizObjNo(order.getRechargeOrderNo());
								pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
								pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

							} else {
								log.info("重复请求");
								return;
							}
						}
						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(totalFee, 2));
						pr.setPayStatus(1);
						pr.setPayType(2);
						if ("JSAPI".equals(trade_type)) {
							pr.setPayType(3);
						}
						pr.setPayFlowNo(transaction_id); // 外部支付流水号
						pr.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						pr.setPaidTime(new Date());
						// 设置支付账户标识
						pr.setBuyerId(openId);
						paymentRecordService.addPaymentRecord(pr, null);

						resHandler.sendToCFT("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
					} else {// sha1签名失败
						resHandler.sendToCFT("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
					}
				} else {// MD5签名失败
					resHandler.sendToCFT("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
				}

			} catch (Exception e) {
				log.info("exeption");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 微信统一下单 param key:arrayCourseNo(排课编号) 应付金额(payAmount) 实付金额(realPayAmount)
	 * 预约人数(appoinNumber) discountAmount(优惠金额) balanceDiscountAmount(余额抵扣金额)
	 * couponNo(优惠券编号) courseBag(课程包)
	 */
	@Override
	// type:1、社区订单，2、主题订单，3、押金，4、充值包，5、健身卡，6、课程包
	public ResultInfo<SortedMap<String, Object>> getCodeUrl(HttpServletRequest request, HttpServletResponse response, String memberNo, String businessNo, Integer type, Integer tag, PayThemeOrderVo param) {
		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 总金额以分为单位，不带小数点
		int total_fee = 0;
		// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。处理完调用的路径
		String notify_url = "";
		// 商品描述根据情况修改
		String body = "";
		// 商户订单号
		String out_trade_no = "";
		if (type.equals(1)) {
			CommunityOrder order = communityOrderService.getCommunityOrder(businessNo);
			if (order.getPayStatus().equals(1)) {
				resultInfo.setCode(OrderConstant.alreday_pay);
				resultInfo.setMsg(OrderConstant.alreday_pay_msg);
				return resultInfo;
			}
			if (StringUtils.isNotBlank(param.getRealPayAmount())) {
				order.setPayableAmount(Double.parseDouble(param.getRealPayAmount()));
			} else {
				order.setPayableAmount(0D);
			}
			if (StringUtils.isNotBlank(param.getDiscountAmount())) {
				order.setDiscountAmount(Double.parseDouble(param.getDiscountAmount()));
			} else {
				order.setDiscountAmount(0D);
			}
			if (StringUtils.isNotBlank(param.getBalanceDiscountAmount())) {
				order.setBalanceDiscountAmount(Double.parseDouble(param.getBalanceDiscountAmount()));
			} else {
				order.setBalanceDiscountAmount(0D);
			}

			if (StringUtils.isNotBlank(param.getCouponNo())) {
				order.setCouponNo(param.getCouponNo());
			}
			communityOrderService.updateCommunityOrder(order, null);

			total_fee = (int) (order.getPayableAmount() * 100);
			notify_url = WxpayConfig.notify_url;
			// 商品描述根据情况修改
			SysParam sys = sysParamDao.getByParamKey("APP_NAME");
			if (sys != null) {
				body = sys.getParamValue() + "-社区订单支付";
			} else {
				body = "易湃运动-社区订单支付";
			}

			out_trade_no = businessNo;
		} else if (type.equals(2)) {
			ThemeOrder order = themeOrderService.getThemeOrder(businessNo);
			if (order != null) {
				if (order.getPayStatus().equals(1)) {
					resultInfo.setCode(OrderConstant.alreday_pay);
					resultInfo.setMsg(OrderConstant.alreday_pay_msg);
					return resultInfo;
				}
				total_fee = (int) (order.getPayableAmount() * 100);
				notify_url = WxpayConfig.notify_url;
				// 商品描述根据情况修改
				SysParam sys = sysParamDao.getByParamKey("APP_NAME");
				if (sys != null) {
					body = sys.getParamValue() + "-主题订单支付";
				} else {
					body = "易湃运动-主题订单支付";
				}

				out_trade_no = businessNo;
			} else {
				// 微信支付之前生成主题馆课程订单数据
				String arrayCourseNo = param.getArrayCourseNo();
				String oppionNumber = param.getAppoinNumber();
				String payStatus = param.getPayStatus();
				synchronized (this) {
					try {
						//生成订单之前校验课程预约人数
						ResultInfo<String> result = this.checkAppoinArrayCourse(memberNo, arrayCourseNo, oppionNumber, payStatus);
						if(result.getCode().equals("0")){
							resultInfo.setCode(result.getCode());
							resultInfo.setMsg(result.getMsg());
							return resultInfo;
						}else{
							if(StringUtils.isBlank(param.getThemeOrderNo())){
								if (StringUtils.isNotBlank(arrayCourseNo)) {
									ArrayCourse queryArrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
									if(queryArrayCourse != null){
										if(queryArrayCourse.getPeopleNumber() == queryArrayCourse.getReservation()){
											//如果当前预约人数已经等于该课程上课人数，生成该课程排队信息
											ArrayCourse updateArrayCourse = new ArrayCourse();
											updateArrayCourse.setArrayCourseNo(queryArrayCourse.getArrayCourseNo());
											int linNum = Integer.parseInt(oppionNumber);
											updateArrayCourse.setLineUp(linNum + queryArrayCourse.getLineUp());
											arrayCourseService.updateArrayCourse(updateArrayCourse, null);
										}else{
											ArrayCourse updateArrayCourse = new ArrayCourse();
											updateArrayCourse.setArrayCourseNo(queryArrayCourse.getArrayCourseNo());
											int reservation = Integer.parseInt(oppionNumber);
											updateArrayCourse.setReservation(reservation+queryArrayCourse.getReservation());
											arrayCourseService.updateArrayCourse(updateArrayCourse, null);
										}
									}
								}
								ThemeOrder themeOrder = new ThemeOrder();
								themeOrder.setThemeOrderNo(themeOrderService.generatePK());
								if (StringUtils.isNotBlank(param.getArrayCourseNo())) {
									// 查询该预约课程的信息
									ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(param.getArrayCourseNo());
									if (arrayCourse != null) {
										themeOrder.setArrangeNo(arrayCourse.getArrayCourseNo());
										// 根据门店编号、行号和排课类型查询排课时间
										ArrayCourse courseBean = arrayCourseService.getStoreCourseTimeByFtl(arrayCourse.getStoreNo(),arrayCourse.getFtlRow(), arrayCourse.getCourseType());
										if (courseBean != null) {
											Date corseDate = ECDateUtils.formatDateToDateNew(arrayCourse.getCourseDate());
											String classTime = ECDateUtils.getCurrentDateAsString(corseDate);
											String startTime = ECDateUtils.farmatDateToHM(courseBean.getCourseStart());
											String endTime = ECDateUtils.farmatDateToHM(courseBean.getCourseEnd());
											themeOrder.setClassTime(classTime + " " + startTime + "-" + endTime);
										}
										themeOrder.setCoachNo(arrayCourse.getCoachNo());
										themeOrder.setCoachName(arrayCourse.getCoachName());
										themeOrder.setCourseNo(arrayCourse.getCourseNo());
										themeOrder.setPeopleNumber(Integer.parseInt(param.getAppoinNumber()));
										themeOrder.setPayStatus(0);// 未支付
										themeOrder.setIsSendMsg(0);// 未发送短信
										themeOrder.setIsDeleted(0);// 未删除
										themeOrder.setOrderAmount(Double.parseDouble(param.getPayAmount()));// 应付金额
										themeOrder.setPayableAmount(Double.parseDouble(param.getRealPayAmount()));// 实付金额
										if (Integer.parseInt(param.getCourseBag()) > 0) {
											if (Integer.parseInt(param.getCourseBag()) > Integer.parseInt(param.getAppoinNumber())) {
												themeOrder.setCourseBagNum(Integer.parseInt(param.getAppoinNumber()));
											} else {
												themeOrder.setCourseBagNum(Integer.parseInt(param.getCourseBag()));
											}
										} else {
											themeOrder.setCourseBagNum(0);// 如果没有选课程包
										}
										Store store = storeService.getStore(arrayCourse.getStoreNo());// 门店
										if (store != null) {
											if (store.getStoreStatus().equals(0)) {
												themeOrder.setStoreNo(store.getStoreNo());
												themeOrder.setStoreName(store.getStoreName());
												themeOrder.setCityId(store.getCityId());
												themeOrder.setCityName(store.getCityName());
											}
										}
										
										if (StringUtils.isNotBlank(param.getDiscountAmount())) {
											themeOrder.setDiscountAmount(Double.parseDouble(param.getDiscountAmount()));// 优惠金额
										} else {
											// 计算优惠金额
											themeOrder.setDiscountAmount(0d);
										}
										// 余额抵扣
										if (StringUtils.isNotBlank(param.getBalanceDiscountAmount())) {
											themeOrder.setBalanceDiscountAmount(Double.parseDouble(param.getBalanceDiscountAmount()));
										} else {
											themeOrder.setBalanceDiscountAmount(0d);
										}
										
										Member member = memberService.getMember(memberNo);
										if (member != null) {
											themeOrder.setMemberNo(memberNo);
											themeOrder.setMobilePhone(member.getMobilePhone());
										}
										
										if (StringUtils.isNotBlank(param.getCouponNo())) {
											themeOrder.setCouponNo(param.getCouponNo());
										}
										themeOrderService.addThemeOrder(themeOrder, null);

										total_fee = (int) (themeOrder.getPayableAmount() * 100);
										notify_url = WxpayConfig.notify_url;
										// 商品描述根据情况修改
										SysParam sys = sysParamDao.getByParamKey("APP_NAME");
										if (sys != null) {
											body = sys.getParamValue() + "-主题订单支付";
										} else {
											body = "易湃运动-主题订单支付";
										}

										out_trade_no = themeOrder.getThemeOrderNo();
									}
								}
							}else{
								//更新订单
								ThemeOrder theme = themeOrderService.getThemeOrder(param.getThemeOrderNo());
								if(theme != null){
									ThemeOrder themeOrder = new ThemeOrder();
									themeOrder.setThemeOrderNo(theme.getThemeOrderNo());
									if (StringUtils.isNotBlank(param.getArrayCourseNo())) {
										// 查询该预约课程的信息
										ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(param.getArrayCourseNo());
										if (arrayCourse != null) {
											themeOrder.setArrangeNo(arrayCourse.getArrayCourseNo());
											// 根据门店编号、行号和排课类型查询排课时间
											ArrayCourse courseBean = arrayCourseService.getStoreCourseTimeByFtl(arrayCourse.getStoreNo(),arrayCourse.getFtlRow(), arrayCourse.getCourseType());
											if (courseBean != null) {
												Date corseDate = ECDateUtils.formatDateToDateNew(arrayCourse.getCourseDate());
												String classTime = ECDateUtils.getCurrentDateAsString(corseDate);
												String startTime = ECDateUtils.farmatDateToHM(courseBean.getCourseStart());
												String endTime = ECDateUtils.farmatDateToHM(courseBean.getCourseEnd());
												themeOrder.setClassTime(classTime + " " + startTime + "-" + endTime);
											}
											themeOrder.setCoachNo(arrayCourse.getCoachNo());
											themeOrder.setCoachName(arrayCourse.getCoachName());
											themeOrder.setCourseNo(arrayCourse.getCourseNo());
											themeOrder.setPeopleNumber(Integer.parseInt(param.getAppoinNumber()));
											themeOrder.setPayStatus(0);// 未支付
											themeOrder.setIsSendMsg(0);// 未发送短信
											themeOrder.setIsDeleted(0);// 未删除
											themeOrder.setOrderAmount(Double.parseDouble(param.getPayAmount()));// 应付金额
											themeOrder.setPayableAmount(Double.parseDouble(param.getRealPayAmount()));// 实付金额
											if (Integer.parseInt(param.getCourseBag()) > 0) {
												if (Integer.parseInt(param.getCourseBag()) > Integer.parseInt(param.getAppoinNumber())) {
													themeOrder.setCourseBagNum(Integer.parseInt(param.getAppoinNumber()));
												} else {
													themeOrder.setCourseBagNum(Integer.parseInt(param.getCourseBag()));
												}
											} else {
												themeOrder.setCourseBagNum(0);// 如果没有选课程包
											}
											Store store = storeService.getStore(arrayCourse.getStoreNo());// 门店
											if (store != null) {
												if (store.getStoreStatus().equals(0)) {
													themeOrder.setStoreNo(store.getStoreNo());
													themeOrder.setStoreName(store.getStoreName());
													themeOrder.setCityId(store.getCityId());
													themeOrder.setCityName(store.getCityName());
												}
											}
											
											if (StringUtils.isNotBlank(param.getDiscountAmount())) {
												themeOrder.setDiscountAmount(Double.parseDouble(param.getDiscountAmount()));// 优惠金额
											} else {
												// 计算优惠金额
												themeOrder.setDiscountAmount(0d);
											}
											// 余额抵扣
											if (StringUtils.isNotBlank(param.getBalanceDiscountAmount())) {
												themeOrder.setBalanceDiscountAmount(Double.parseDouble(param.getBalanceDiscountAmount()));
											} else {
												themeOrder.setBalanceDiscountAmount(0d);
											}
											
											Member member = memberService.getMember(memberNo);
											if (member != null) {
												themeOrder.setMemberNo(memberNo);
												themeOrder.setMobilePhone(member.getMobilePhone());
											}
											
											if (StringUtils.isNotBlank(param.getCouponNo())) {
												themeOrder.setCouponNo(param.getCouponNo());
											}
											themeOrderService.updateThemeOrder(themeOrder, null);

											total_fee = (int) (themeOrder.getPayableAmount() * 100);
											notify_url = WxpayConfig.notify_url;
											// 商品描述根据情况修改
											SysParam sys = sysParamDao.getByParamKey("APP_NAME");
											if (sys != null) {
												body = sys.getParamValue() + "-主题订单支付";
											} else {
												body = "易湃运动-主题订单支付";
											}
											out_trade_no = themeOrder.getThemeOrderNo();
										}
									}
								}
							}
						}

					} catch (Exception e) {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg(e.getMessage());
					}
				}
			}
		} else if (type.equals(3)) {
			DepositOrder dOrder = new DepositOrder();
			dOrder.setMemberNo(memberNo);

			SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);
			Double amount = Double.parseDouble(sysParam.getParamValue());

			// 查询该用户的剩余保证金
			Double syAmount = 0d;
			MemberBalance mc = memberBalanceService.getMemberBalance(memberNo);
			if (mc != null && mc.getMemberDeposit() != null) {
				syAmount = mc.getMemberDeposit();
			}
			dOrder.setDepositAmount(ECNumberUtils.roundDoubleWithScale(amount - syAmount, 2));
			dOrder.setPayableAmount(ECNumberUtils.roundDoubleWithScale(amount - syAmount, 2));// 测试
			dOrder.setPayStatus(0);
			dOrder.setRemainAmount(0d);
			dOrder.setDeductedAmount(0d);
			dOrder.setRefundAmount(0d);
			dOrder.setFrozenAmount(0d);
			if (memberNo != null) {
				dOrder = depositOrderService.addDepositOrder(dOrder, null).getData();
			}

			total_fee = (int) (dOrder.getPayableAmount() * 100);
			notify_url = WxpayConfig.notify_url;
			SysParam sys = sysParamDao.getByParamKey("APP_NAME");
			if (sys != null) {
				body = sys.getParamValue() + "-押金支付";
			} else {
				body = "易湃运动-押金支付";
			}

			out_trade_no = dOrder.getDepositOrderNo();
		} else if (type.equals(4)) {
			Recharge re = rechargeService.getRecharge(businessNo);
			Member m = memberService.getMember(memberNo);

			RechargeOrder ro = new RechargeOrder();
			ro.setMemberNo(m.getMemberNo());
			ro.setMemberName(m.getMemberName());
			ro.setMobilePhone(m.getMobilePhone());
			ro.setRechargeNo(re.getRechargeNo());
			ro.setRechargeName(re.getRechargeName());
			ro.setRechargeAmount(re.getRechargeAmount());
			ro.setPayableAmount(re.getPrice());
			ro.setPayStatus(0);
			// ro.setPaymentMethod(1);
			rechargeOrderService.addRechargeOrder(ro, null);

			total_fee = (int) (ro.getPayableAmount() * 100);
			notify_url = WxpayConfig.notify_url;
			// 商品描述根据情况修改
			SysParam sys = sysParamDao.getByParamKey("APP_NAME");
			if (sys != null) {
				body = sys.getParamValue() + "-充值支付";
			} else {
				body = "易湃运动-充值支付";
			}

			out_trade_no = ro.getRechargeOrderNo();
		} else if (type.equals(5)) {
			// 健身卡
			GymCard gd = gymCardService.getGymCard(businessNo);
			Member m = memberService.getMember(memberNo);

			GymCardOrder ro = new GymCardOrder();
			ro.setMemberNo(m.getMemberNo());
			ro.setMemberName(m.getMemberName());
			ro.setMobilePhone(m.getMobilePhone());
			ro.setGymCardNo(gd.getGymCardNo());
			ro.setGymCardName(gd.getGymCardName());
			ro.setRechargeAmount(gd.getRechargeAmount());
			ro.setPayableAmount(gd.getPrice());
			ro.setPayStatus(0);
			// ro.setPaymentMethod(1);
			gymCardOrderService.addGymCardOrder(ro, null);

			total_fee = (int) (ro.getPayableAmount() * 100);
			notify_url = WxpayConfig.notify_url;
			// 商品描述根据情况修改
			SysParam sys = sysParamDao.getByParamKey("APP_NAME");
			if (sys != null) {
				body = sys.getParamValue() + "-健身卡支付";
			} else {
				body = "易湃运动-健身卡支付";
			}

			out_trade_no = ro.getGymCardOrderNo();
		} else if (type.equals(6)) {
			// 课程包
			CoursePackage gd = coursePackageService.getCoursePackage(businessNo);
			Member m = memberService.getMember(memberNo);

			CoursePackageOrder ro = new CoursePackageOrder();
			ro.setMemberNo(m.getMemberNo());
			ro.setMemberName(m.getMemberName());
			ro.setMobilePhone(m.getMobilePhone());
			ro.setCoursePackageNo(gd.getCoursePackageNo());
			ro.setCoursePackageName(gd.getCoursePackageName());
			// ro.setRechargeAmount(gd.getRechargeAmount());
			ro.setPayableAmount(gd.getPrice());
			ro.setPayStatus(0);
			// ro.setPaymentMethod(1);
			coursePackageOrderService.addCoursePackageOrder(ro, null);

			total_fee = (int) (ro.getPayableAmount() * 100);
			notify_url = WxpayConfig.notify_url;
			// 商品描述根据情况修改
			SysParam sys = sysParamDao.getByParamKey("APP_NAME");
			if (sys != null) {
				body = sys.getParamValue() + "-课程包支付";
			} else {
				body = "易湃运动-课程包支付";
			}

			out_trade_no = ro.getRechargeOrderNo();
		}
		SysParam sysp = sysParamService.getByParamKey(OrderConstant.IS_Correct_OrderAmount);
		if (sysp != null && sysp.getParamValue() != null && sysp.getParamValue().equals("0")) {
			total_fee = (int) (1);
		}

		// 调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
		// 请求参数准备
		String appid = WxpayConfig.appID;// 微信开放平台审核通过的应用APPID
		String mch_id = WxpayConfig.mchID;// 微信支付分配的商户号
		String device_info = "";// 设备号 非必输

		// 生成随机字符串
		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());// 8位日期
		String strRandom = TenpayUtil.buildRandom(4) + "";// 四位随机数
		String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
		String nonce_str = strReq;

		// 商品描述
		// String body = describe;

		// 附加数据
		// String attach = userId;

		// 订单生成的机器 IP
		String spbill_create_ip = "";
		// 订 单 生 成 时 间 非必输
		String time_start = "";
		// 订单失效时间 非必输
		String time_expire = "";
		// 商品标记 非必输
		String goods_tag = "";

		String trade_type = "APP";// 交易类型

		if (tag.intValue() == 10) {
			appid = WxpayConfig.h5_appID;
			mch_id = WxpayConfig.h5_mchID;
			trade_type = "JSAPI";
		}

		// 生成签名
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		// packageParams.put("attach", attach);

		// 商户交易单号，之前业务为将业务单号作为交易单号传递到微信平台，后发现此种做法有问题会引发二次唤起微信支付失败或者订单失效无法支付的问题，解决方式为每次唤起支付的时候都在支付记录中添加新纪录，新增内部支付流水单号，格式为业务单号_8位随机数
		out_trade_no = out_trade_no + "_" + ECRandomUtils.getRandomAlphamericStr(8);

		packageParams.put("out_trade_no", out_trade_no);
		// TODO 这里写的金额为1 分 通过系统参数变量来控制支付金额通道- 参数建 IS_Correct_OrderAmount
		packageParams.put("total_fee", "" + total_fee);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", trade_type);
		if (tag.intValue() == 10) {
			String openid = getWxAuthOpenid(memberNo);
			packageParams.put("openid", openid);
			System.out.println("pay openid+++++++++++ " + openid);
		}

		String sign = PayCommonUtil.createSign("UTF-8", packageParams);// 生成签名
		packageParams.put("sign", sign);
		packageParams.remove("key");// 调用统一下单无需key（商户应用密钥）

		String requestXml = PayCommonUtil.getRequestXml(packageParams);// 生成Xml格式的字符串
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String result = CommonUtil.httpsRequest(createOrderURL, "POST", requestXml);// 以post请求的方式调用统一下单接口

		System.out.println("pay request+++++++++++ " + requestXml);
		System.out.println("pay result++++++++++++ " + result);
		Map map;
		try {
			map = XMLUtil.doXMLParse(result);
			String return_code = (String) map.get("return_code");
			String prepay_id = null;
			if (return_code.contains("SUCCESS")) {
				prepay_id = (String) map.get("prepay_id");// 获取到prepay_id
				String timeStamp = ECDateUtils.formatStringTimeWX(new Date());
				if (tag.equals(0)) {// 安卓
					// 2.签名返回信息
					resultInfo = getCodeUrlApp(request, response, prepay_id, nonce_str, timeStamp);
				} else if (tag.equals(1)) {// ios
					Long time = System.currentTimeMillis() / 1000;
					Uint32 timeStamp1 = new Uint32(time);
					// 2.签名返回信息
					resultInfo = getCodeUrlAppIOS(request, response, prepay_id, nonce_str, timeStamp1);
				} else if (tag == 10) {

					resultInfo = getCodeUrlH5(request, response, prepay_id, nonce_str,
							"" + new Date().getTime() / 1000);
				}

				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	@Override
	public ResultInfo<SortedMap<String, Object>> getCodeUrlApp(HttpServletRequest request, HttpServletResponse response,
			String prepay_id, String nonceStr, String timeStamp) {
		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 请求参数准备
		String appid = WxpayConfig.appID;// 微信开放平台审核通过的应用APPID
		String partnerId = WxpayConfig.mchID;// 微信支付分配的商户号
		// 生成签名
		SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
		packageParams.put("appid", appid);
		packageParams.put("partnerid", partnerId);
		packageParams.put("prepayid", prepay_id);
		packageParams.put("package", "Sign=WXPay");
		packageParams.put("noncestr", nonceStr);
		packageParams.put("timestamp", timeStamp);
		String sign = PayCommonUtil.createSignIOS("UTF-8", packageParams);
		packageParams.put("sign", sign);
		packageParams.put("packageStr", "Sign=WXPay");// app端package关键字
		if (sign != null && !sign.equals("")) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(packageParams);
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	public ResultInfo<SortedMap<String, Object>> getCodeUrlAppIOS(HttpServletRequest request,
			HttpServletResponse response, String prepay_id, String nonceStr, Uint32 timeStamp) {
		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 请求参数准备
		String appid = WxpayConfig.appID;// 微信开放平台审核通过的应用APPID
		String partnerId = WxpayConfig.mchID;// 微信支付分配的商户号

		// 生成签名
		SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
		packageParams.put("appid", appid);
		packageParams.put("partnerid", partnerId);
		packageParams.put("prepayid", prepay_id);
		packageParams.put("package", "Sign=WXPay");
		packageParams.put("noncestr", nonceStr);
		packageParams.put("timestamp", timeStamp);
		String sign = PayCommonUtil.createSignIOS("UTF-8", packageParams);
		packageParams.put("sign", sign);
		packageParams.put("packageStr", "Sign=WXPay");// app端package关键字
		packageParams.put("timestamp", timeStamp.toString());
		if (sign != null && !sign.equals("")) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(packageParams);
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	public ResultInfo<SortedMap<String, Object>> getCodeUrlH5(HttpServletRequest request, HttpServletResponse response,
			String prepay_id, String nonceStr, String timeStamp) {
		ResultInfo<SortedMap<String, Object>> resultInfo = new ResultInfo<SortedMap<String, Object>>();
		// 请求参数准备
		String appid = WxpayConfig.h5_appID;// 微信开放平台审核通过的应用APPID
		String partnerId = WxpayConfig.h5_mchID;// 微信支付分配的商户号

		// 生成签名
		SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
		packageParams.put("appId", appid);
		packageParams.put("timeStamp", timeStamp);
		packageParams.put("nonceStr", nonceStr);
		packageParams.put("package", "prepay_id=" + prepay_id);
		// packageParams.put("prepay_id", prepay_id);
		packageParams.put("signType", "MD5");
		String sign = PayCommonUtil.createSignIOS("UTF-8", packageParams);
		packageParams.put("sign", sign);
		packageParams.put("appid", appid);
		packageParams.put("partnerid", partnerId);
		packageParams.put("prepayid", prepay_id);
		packageParams.put("noncestr", nonceStr);
		packageParams.put("timestamp", timeStamp);
		packageParams.put("packageStr", "Sign=WXPay");// app端package关键字
		packageParams.put("signType", "MD5");
		if (sign != null && !sign.equals("")) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(packageParams);
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	/**
	 * 调用微信查询订单支付结果的接口
	 */
	@Override
	public ResultInfo<String> wxGetOrderPayResult(HttpServletRequest request, HttpServletResponse response,
			String orderNo) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		// 生成随机字符串
		String currTime = TenpayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());// 8位日期
		String strRandom = TenpayUtil.buildRandom(4) + "";// 四位随机数
		String strReq = strTime + strRandom;// 10位序列号,可以自行调整。
		String nonce_str = strReq;
		String flag = orderNo.substring(0, 1);

		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", WxpayConfig.appID);
		packageParams.put("mch_id", WxpayConfig.mchID);
		if (flag.equals("S")) {
			CommunityOrder order = communityOrderService.getCommunityOrder(orderNo);
			if (order != null && order.getPartTradeFlowNo() != null) {
				packageParams.put("out_trade_no", order.getPartTradeFlowNo());
			}
		} else if (flag.equals("Z")) {
			ThemeOrder torder = themeOrderService.getThemeOrder(orderNo);
			if (torder != null && torder.getPartTradeFlowNo() != null) {
				packageParams.put("out_trade_no", torder.getPartTradeFlowNo());
			}
		} else if (flag.equals("Y")) {
			DepositOrder dorder = depositOrderService.getDepositOrder(orderNo);
			if (dorder != null && dorder.getPartTradeFlowNo() != null) {
				packageParams.put("out_trade_no", dorder.getPartTradeFlowNo());
			}
		} else if (flag.equals("C")) {
			RechargeOrder ro = rechargeOrderService.getRechargeOrder(orderNo);
			if (ro != null && ro.getPartTradeFlowNo() != null) {
				packageParams.put("out_trade_no", ro.getPartTradeFlowNo());
			}
		} else if (flag.equals("J")) {
			GymCardOrder go = gymCardOrderService.getGymCardOrder(orderNo);
			if (go != null && go.getPartTradeFlowNo() != null) {
				packageParams.put("out_trade_no", go.getPartTradeFlowNo());
			}
		} else if (flag.equals("K")) {
			CoursePackageOrder co = coursePackageOrderService.getCoursePackageOrder(orderNo);
			if (co != null && co.getPartTradeFlowNo() != null) {
				packageParams.put("out_trade_no", co.getPartTradeFlowNo());
			}
		}
		packageParams.put("nonce_str", nonce_str);
		String sign = PayCommonUtil.createSign("UTF-8", packageParams);// 生成签名
		packageParams.put("sign", sign);
		packageParams.remove("key");// 调用统一下单无需key（商户应用密钥）
		String requestXml = PayCommonUtil.getRequestXml(packageParams);// 生成Xml格式的字符串
		String createOrderURL = "https://api.mch.weixin.qq.com/pay/orderquery";
		String result = CommonUtil.httpsRequest(createOrderURL, "POST", requestXml);// 以post请求的方式调用统一下单接口
		Map map;
		String trade_state = "";
		try {
			map = XMLUtil.doXMLParse(result);
			String return_code = (String) map.get("return_code");
			String result_code = (String) map.get("result_code");
			if (return_code.contains("SUCCESS")) {
				if (result_code.contains("SUCCESS")) {
					trade_state = (String) map.get("trade_state");

					/*
					 * SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭
					 * REVOKED—已撤销（刷卡支付） USERPAYING--用户支付中
					 * PAYERROR--支付失败(其他原因，如银行返回失败)
					 */
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(trade_state);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	/**
	 * 支付宝商户后台返回orderStr给app端
	 * 
	 * 2017-11-28日新增 新加参数 param 如下： arrayCourseNo(排课编号) 应付金额(payAmount)
	 * 实付金额(realPayAmount) 预约人数(appoinNumber) discountAmount(优惠金额)
	 * balanceDiscountAmount(余额抵扣金额) couponNo(优惠券编号) courseBag(课时包)
	 */
	@Override
	public ResultInfo<String> alipayGetOrderStr(HttpServletRequest request, HttpServletResponse response,String memberNo, String businessNo, Integer type, PayThemeOrderVo param) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		String subject = "";// (商品名称)
		String out_trade_no = "";// 订单号
		String total_amount = "";// 订单总金额，单位为元，精确到小数点后两位
		if (type.equals(1)) {// 社区订单
			CommunityOrder order = communityOrderService.getCommunityOrder(businessNo);
			if (order.getPayStatus().equals(1)) {
				resultInfo.setCode(OrderConstant.alreday_pay);
				resultInfo.setMsg(OrderConstant.alreday_pay_msg);
				return resultInfo;
			}

			if (StringUtils.isNotBlank(param.getRealPayAmount())) {
				order.setPayableAmount(Double.parseDouble(param.getRealPayAmount()));
			} else {
				order.setPayableAmount(0D);
			}
			if (StringUtils.isNotBlank(param.getDiscountAmount())) {
				order.setDiscountAmount(Double.parseDouble(param.getDiscountAmount()));
			} else {
				order.setDiscountAmount(0D);
			}
			if (StringUtils.isNotBlank(param.getBalanceDiscountAmount())) {
				order.setBalanceDiscountAmount(Double.parseDouble(param.getBalanceDiscountAmount()));
			} else {
				order.setBalanceDiscountAmount(0D);
			}

			if (StringUtils.isNotBlank(param.getCouponNo())) {
				order.setCouponNo(param.getCouponNo());
			}
			communityOrderService.updateCommunityOrder(order, null);

			subject = order.getCommunityOrderNo();// (商品名称)
			out_trade_no = order.getCommunityOrderNo();// 订单号
			total_amount = order.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
		} else if (type.equals(2)) {
			ThemeOrder order = themeOrderService.getThemeOrder(businessNo);
			if (order != null) {
				if (order.getPayStatus().equals(1)) {
					resultInfo.setCode(OrderConstant.alreday_pay);
					resultInfo.setMsg(OrderConstant.alreday_pay_msg);
					return resultInfo;
				}
				subject = order.getThemeOrderNo();// (商品名称)
				out_trade_no = order.getThemeOrderNo();// 订单号
				total_amount = order.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
			} else {
				//查询课程校验
				String arrayCourseNo = param.getArrayCourseNo();
				String oppionNumber = param.getAppoinNumber();
				String payStatus = param.getPayStatus();
				//运用同步锁 按顺序进入代码块，防止并发，同时校验实时上课已预约人数情况。
				synchronized (this) {
					try {
						ResultInfo<String> result = this.checkAppoinArrayCourse(memberNo, arrayCourseNo, oppionNumber, payStatus);
						if(result.getCode().equals("0")){
							resultInfo.setCode(result.getCode());
							resultInfo.setMsg(result.getMsg());
							return resultInfo;
						}else{
							if(StringUtils.isBlank(param.getThemeOrderNo())){
								// 生成主题馆订单之前先更新该课程预约人数
								if (StringUtils.isNotBlank(arrayCourseNo)) {
									ArrayCourse queryArrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
									if(queryArrayCourse != null){
										if(queryArrayCourse.getPeopleNumber() == queryArrayCourse.getReservation()){
											//如果当前预约人数已经等于该课程上课人数，生成该课程排队信息
											ArrayCourse updateArrayCourse = new ArrayCourse();
											updateArrayCourse.setArrayCourseNo(queryArrayCourse.getArrayCourseNo());
											int linNum = Integer.parseInt(oppionNumber);
											updateArrayCourse.setLineUp(linNum + queryArrayCourse.getLineUp());
											arrayCourseService.updateArrayCourse(updateArrayCourse, null);
										}else{
											ArrayCourse updateArrayCourse = new ArrayCourse();
											updateArrayCourse.setArrayCourseNo(queryArrayCourse.getArrayCourseNo());
											int reservation = Integer.parseInt(oppionNumber);
											updateArrayCourse.setReservation(reservation+queryArrayCourse.getReservation());
											arrayCourseService.updateArrayCourse(updateArrayCourse, null);
										}
									}
								}
								// 业务类型为2时，生成主题馆课程订单数据
								ThemeOrder themeOrder = new ThemeOrder();
								themeOrder.setThemeOrderNo(themeOrderService.generatePK());
								if (StringUtils.isNotBlank(arrayCourseNo)) {
									// 查询该预约课程的信息
									ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
									if (arrayCourse != null) {
										themeOrder.setArrangeNo(arrayCourse.getArrayCourseNo());
										// 根据门店编号、行号和排课类型查询排课时间
										ArrayCourse courseBean = arrayCourseService.getStoreCourseTimeByFtl(arrayCourse.getStoreNo(),arrayCourse.getFtlRow(), arrayCourse.getCourseType());
										if (courseBean != null) {
											Date corseDate = ECDateUtils.formatDateToDateNew(arrayCourse.getCourseDate());
											String classTime = ECDateUtils.getCurrentDateAsString(corseDate);
											String startTime = ECDateUtils.farmatDateToHM(courseBean.getCourseStart());
											String endTime = ECDateUtils.farmatDateToHM(courseBean.getCourseEnd());
											themeOrder.setClassTime(classTime + " " + startTime + "-" + endTime);
										}
										
										themeOrder.setCoachNo(arrayCourse.getCoachNo());
										themeOrder.setCoachName(arrayCourse.getCoachName());
										themeOrder.setCourseNo(arrayCourse.getCourseNo());
										themeOrder.setPeopleNumber(Integer.parseInt(param.getAppoinNumber()));
										themeOrder.setIsSendMsg(0);// 未发送短信
										themeOrder.setPayStatus(0);// 未支付
										themeOrder.setIsDeleted(0);// 未删除
										themeOrder.setPayableAmount(Double.parseDouble(param.getRealPayAmount()));// 实付金额
										themeOrder.setOrderAmount(Double.parseDouble(param.getPayAmount()));// 应付金额
										
										if (Integer.parseInt(param.getCourseBag()) > 0) {
											if (Integer.parseInt(param.getCourseBag()) > Integer.parseInt(param.getAppoinNumber())) {
												themeOrder.setCourseBagNum(Integer.parseInt(param.getAppoinNumber()));
											} else {
												themeOrder.setCourseBagNum(Integer.parseInt(param.getCourseBag()));
											}
										} else {
											themeOrder.setCourseBagNum(0);// 如果没有选课程包
										}
										Store store = storeService.getStore(arrayCourse.getStoreNo());// 门店
										if (store != null) {
											if (store.getStoreStatus().equals(0)) {
												themeOrder.setStoreNo(store.getStoreNo());
												themeOrder.setStoreName(store.getStoreName());
												themeOrder.setCityId(store.getCityId());
												themeOrder.setCityName(store.getCityName());
											}
										}
										if (StringUtils.isNotBlank(param.getDiscountAmount())) {
											themeOrder.setDiscountAmount(Double.parseDouble(param.getDiscountAmount()));// 优惠金额
										} else {
											themeOrder.setDiscountAmount(0d);
										}
										if (StringUtils.isNotBlank(param.getBalanceDiscountAmount())) {
											themeOrder.setBalanceDiscountAmount(Double.parseDouble(param.getBalanceDiscountAmount()));// 余额抵扣
										} else {
											themeOrder.setBalanceDiscountAmount(0d);
										}
										//查询会员数据
										Member member = memberService.getMember(memberNo);
										if (member != null) {
											themeOrder.setMemberNo(memberNo);
											themeOrder.setMobilePhone(member.getMobilePhone());
										}
										//优惠券
										if (StringUtils.isNotBlank(param.getCouponNo())) {
											themeOrder.setCouponNo(param.getCouponNo());
										}
										themeOrderService.addThemeOrder(themeOrder, null);

										subject = themeOrder.getThemeOrderNo();// (商品名称)
										out_trade_no = themeOrder.getThemeOrderNo();// 订单号
										total_amount = themeOrder.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
									}
								}
							}else{
								//更新订单 重新用支付宝或者微信支付
								// 业务类型为2时，生成主题馆课程订单数据
								ThemeOrder theme = themeOrderService.getThemeOrder(param.getThemeOrderNo());
								if(theme != null){
									ThemeOrder themeOrder = new ThemeOrder();
									themeOrder.setThemeOrderNo(theme.getThemeOrderNo());
									if (StringUtils.isNotBlank(arrayCourseNo)) {
										// 查询该预约课程的信息
										ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
										if (arrayCourse != null) {
											themeOrder.setArrangeNo(arrayCourse.getArrayCourseNo());
											// 根据门店编号、行号和排课类型查询排课时间
											ArrayCourse courseBean = arrayCourseService.getStoreCourseTimeByFtl(arrayCourse.getStoreNo(),arrayCourse.getFtlRow(), arrayCourse.getCourseType());
											if (courseBean != null) {
												Date corseDate = ECDateUtils.formatDateToDateNew(arrayCourse.getCourseDate());
												String classTime = ECDateUtils.getCurrentDateAsString(corseDate);
												String startTime = ECDateUtils.farmatDateToHM(courseBean.getCourseStart());
												String endTime = ECDateUtils.farmatDateToHM(courseBean.getCourseEnd());
												themeOrder.setClassTime(classTime + " " + startTime + "-" + endTime);
											}
											
											themeOrder.setCoachNo(arrayCourse.getCoachNo());
											themeOrder.setCoachName(arrayCourse.getCoachName());
											themeOrder.setCourseNo(arrayCourse.getCourseNo());
											themeOrder.setPeopleNumber(Integer.parseInt(param.getAppoinNumber()));
											themeOrder.setIsSendMsg(0);// 未发送短信
											themeOrder.setPayStatus(0);// 未支付
											themeOrder.setIsDeleted(0);// 未删除
											themeOrder.setPayableAmount(Double.parseDouble(param.getRealPayAmount()));// 实付金额
											themeOrder.setOrderAmount(Double.parseDouble(param.getPayAmount()));// 应付金额
											
											if (Integer.parseInt(param.getCourseBag()) > 0) {
												if (Integer.parseInt(param.getCourseBag()) > Integer.parseInt(param.getAppoinNumber())) {
													themeOrder.setCourseBagNum(Integer.parseInt(param.getAppoinNumber()));
												} else {
													themeOrder.setCourseBagNum(Integer.parseInt(param.getCourseBag()));
												}
											} else {
												themeOrder.setCourseBagNum(0);// 如果没有选课程包
											}
											Store store = storeService.getStore(arrayCourse.getStoreNo());// 门店
											if (store != null) {
												if (store.getStoreStatus().equals(0)) {
													themeOrder.setStoreNo(store.getStoreNo());
													themeOrder.setStoreName(store.getStoreName());
													themeOrder.setCityId(store.getCityId());
													themeOrder.setCityName(store.getCityName());
												}
											}
											if (StringUtils.isNotBlank(param.getDiscountAmount())) {
												themeOrder.setDiscountAmount(Double.parseDouble(param.getDiscountAmount()));// 优惠金额
											} else {
												themeOrder.setDiscountAmount(0d);
											}
											if (StringUtils.isNotBlank(param.getBalanceDiscountAmount())) {
												themeOrder.setBalanceDiscountAmount(Double.parseDouble(param.getBalanceDiscountAmount()));// 余额抵扣
											} else {
												themeOrder.setBalanceDiscountAmount(0d);
											}
											//查询会员数据
											Member member = memberService.getMember(memberNo);
											if (member != null) {
												themeOrder.setMemberNo(memberNo);
												themeOrder.setMobilePhone(member.getMobilePhone());
											}
											//优惠券
											if (StringUtils.isNotBlank(param.getCouponNo())) {
												themeOrder.setCouponNo(param.getCouponNo());
											}
											themeOrderService.updateThemeOrder(themeOrder, null);

											subject = themeOrder.getThemeOrderNo();// (商品名称)
											out_trade_no = themeOrder.getThemeOrderNo();// 订单号
											total_amount = themeOrder.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
										}
									}
								}
							}
						}
					} catch (Exception e) {
						log.info(e.getMessage());
					}
				}
			}
		} else if (type.equals(3)) {// 押金
			DepositOrder dOrder = new DepositOrder();
			dOrder.setMemberNo(memberNo);

			SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);
			Double amount = Double.parseDouble(sysParam.getParamValue());

			// 查询该用户的剩余保证金
			Double syAmount = 0d;
			MemberBalance mc = memberBalanceService.getMemberBalance(memberNo);
			if (mc != null && mc.getMemberDeposit() != null) {
				syAmount = mc.getMemberDeposit();
			}
			dOrder.setDepositAmount(ECNumberUtils.roundDoubleWithScale(amount - syAmount, 2));
			dOrder.setPayableAmount(ECNumberUtils.roundDoubleWithScale(amount - syAmount, 2));// 测试
			dOrder.setPayStatus(0);
			dOrder.setRemainAmount(0d);
			dOrder.setDeductedAmount(0d);
			dOrder.setRefundAmount(0d);
			dOrder.setFrozenAmount(0d);
			if (memberNo != null) {
				dOrder = depositOrderService.addDepositOrder(dOrder, null).getData();
			}

			subject = dOrder.getDepositOrderNo();// (商品名称)
			out_trade_no = dOrder.getDepositOrderNo();// 订单号
			total_amount = dOrder.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
		} else if (type.equals(4)) {// 充值
			Recharge re = rechargeService.getRecharge(businessNo);
			Member m = memberService.getMember(memberNo);

			RechargeOrder ro = new RechargeOrder();
			ro.setMemberNo(m.getMemberNo());
			ro.setMemberName(m.getMemberName());
			ro.setMobilePhone(m.getMobilePhone());
			ro.setRechargeNo(re.getRechargeNo());
			ro.setRechargeName(re.getRechargeName());
			ro.setRechargeAmount(re.getRechargeAmount());
			ro.setPayableAmount(re.getPrice());
			ro.setPayStatus(0);
			// ro.setPaymentMethod(2);
			rechargeOrderService.addRechargeOrder(ro, null);
			subject = ro.getRechargeOrderNo();// (商品名称)
			out_trade_no = ro.getRechargeOrderNo();// 订单号
			total_amount = ro.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
		} else if (type.equals(5)) {
			// 健身卡
			GymCard gd = gymCardService.getGymCard(businessNo);
			Member m = memberService.getMember(memberNo);

			GymCardOrder ro = new GymCardOrder();
			ro.setMemberNo(m.getMemberNo());
			ro.setMemberName(m.getMemberName());
			ro.setMobilePhone(m.getMobilePhone());
			ro.setGymCardNo(gd.getGymCardNo());
			ro.setGymCardName(gd.getGymCardName());
			ro.setRechargeAmount(gd.getRechargeAmount());
			ro.setPayableAmount(gd.getPrice());
			ro.setPayStatus(0);
			// ro.setPaymentMethod(2);
			gymCardOrderService.addGymCardOrder(ro, null);
			subject = ro.getGymCardOrderNo();// (商品名称)
			out_trade_no = ro.getGymCardOrderNo();// 订单号
			total_amount = ro.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
		} else if (type.equals(6)) {
			// 课程包
			CoursePackage gd = coursePackageService.getCoursePackage(businessNo);
			Member m = memberService.getMember(memberNo);

			CoursePackageOrder ro = new CoursePackageOrder();
			ro.setMemberNo(m.getMemberNo());
			ro.setMemberName(m.getMemberName());
			ro.setMobilePhone(m.getMobilePhone());
			ro.setCoursePackageNo(gd.getCoursePackageNo());
			ro.setCoursePackageName(gd.getCoursePackageName());
			// ro.setRechargeAmount(gd.getRechargeAmount());
			ro.setPayableAmount(gd.getPrice());
			ro.setPayStatus(0);
			// ro.setPaymentMethod(2);
			coursePackageOrderService.addCoursePackageOrder(ro, null);
			subject = ro.getRechargeOrderNo();// (商品名称)
			out_trade_no = ro.getRechargeOrderNo();// 订单号
			total_amount = ro.getPayableAmount().toString();// 订单总金额，单位为元，精确到小数点后两位
		}

		SysParam sysp = sysParamService.getByParamKey(OrderConstant.IS_Correct_OrderAmount);
		if (sysp != null && sysp.getParamValue() != null && sysp.getParamValue().equals("0")) {
			total_amount = 0.01 + "";// 订单总金额，单位为元，精确到小数点后两位
		}

		// 商户交易单号，之前业务为将业务单号作为交易单号传递到支付宝平台，后发现此种做法有问题会引发二次唤起支付失败或者订单失效无法支付的问题，解决方式为每次唤起支付的时候都在支付记录中添加新纪录，新增内部支付流水单号，格式为业务单号_8位随机数
		out_trade_no = out_trade_no + "_" + ECRandomUtils.getRandomAlphamericStr(8);

		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildOrderParamMap(AlipayConfig.appId, out_trade_no,
				AlipayConfig.notify_url, total_amount, subject);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
		String sign = OrderInfoUtil2_0.getSign(authInfoMap, AlipayConfig.rsa_private);
		final String orderInfo = orderParam + "&" + sign;
		String result = orderInfo;
		if (result != null && !result.equals("")) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(result);
		} else {
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}

	/**
	 * 支付宝支付-异步回调后台地址
	 */
	@Override
	public void alipayUpdateOrder(HttpServletRequest request, HttpServletResponse response, String trigger)
			throws Exception {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		// 商户订单号 即 附带随机码的平台内部支付流水号
		String part_trade_flow_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 商户订单号
		String shop_order_no = part_trade_flow_no.substring(0, part_trade_flow_no.lastIndexOf("_"));// 交易状态
		// 支付宝交易流水号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		// 购买时间
		String notify_time = new String(request.getParameter("notify_time").getBytes("ISO-8859-1"), "UTF-8");

		// 买家支付时间
		String gmt_payment = request.getParameter("gmt_payment") != null
				? new String(request.getParameter("gmt_payment").getBytes("ISO-8859-1"), "UTF-8") : null;

		Date paidTime = null;
		if (gmt_payment != null && !gmt_payment.equals("")) { // 使用gmt_payment作为支付时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			paidTime = sdf.parse(gmt_payment);
		}
		paidTime = paidTime == null ? new Date() : paidTime;
		// 买家支付宝账号
		String buyerEmail = new String(request.getParameter("buyer_logon_id").getBytes("ISO-8859-1"), "UTF-8");

		// 买家支付宝账户号
		String buyerId = new String(request.getParameter("buyer_id").getBytes("ISO-8859-1"), "UTF-8");
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		if (AlipayNotify.verify(params)) {// 验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			// 请在这里加上商户的业务逻辑程序代码

			// ——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			log.info("pay sign success,begin transition");
			log.info("the trade_status is " + trade_status);
			if (trade_status.equals("TRADE_FINISHED")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				// 该种交易状态只在两种情况下出现
				// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				/*
				 * 支付记录表添加数据
				 */
				PaymentRecord pr = new PaymentRecord();

				// flag 表示支付的业务类型， 代表订单D， Y 代表押金，T 代表套餐
				String flag = shop_order_no.substring(0, 1);
				if (flag.equals("S")) {

					CommunityOrder order = communityOrderService.getCommunityOrder(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						order.setPayStatus(1);// 支付状态为已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						order.setPaymentTime(paidTime);

						communityOrderService.updateCommunityOrder(order, null);

						// 判断是否使用余额
						if (order.getBalanceDiscountAmount() > 0) {
							MemberBalance mc = memberBalanceService.getMemberBalance(order.getMemberNo());
							if (mc != null) {
								mc.setMemberBalance(
										ECCalculateUtils.sub(mc.getMemberBalance(), order.getBalanceDiscountAmount()));
								memberBalanceService.updateMemberBalance(mc, null);
							}

						}

						// 修改优惠券
						if (StringUtils.isNotBlank(order.getCouponNo())) {
							Coupon coupon = couponService.getCoupon(order.getCouponNo());
							if (coupon != null) {
								Coupon cp = new Coupon();
								cp.setCouponNo(coupon.getCouponNo());
								cp.setUsedStatus(1);// 设置为已使用
								couponService.updateCoupon(cp, null);
							}
						}

						Accounts accounts = new Accounts();
						accounts.setAccountNo(accountsService.generatePK());
						accounts.setMemberNo(order.getMemberNo());
						accounts.setBusinessNo(order.getCommunityOrderNo());
						accounts.setBusinessType(1);// 社区订单
						accounts.setAccountType(1);// 出账 -
						accounts.setAccountMoney(order.getPayableAmount());
						accounts.setAccountTime(new Date());
						accountsService.addAccounts(accounts, null);

						pr.setBizObjType(1);// 订单
						pr.setBizObjNo(order.getCommunityOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						payOverAddRecordOrder(order);

					} else {
						log.info("重复请求");
						return;
					}
				} else if (flag.equals("Z")) {
					// 查询订单，修改订单
					ThemeOrder order = themeOrderService.getThemeOrder(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						// 根据订单中的排课编号统计属于该课程的预约人数
						long appionNumber = themeOrderService.statCourseAppoinNumber(order.getArrangeNo());
						// 查询该课程的排课人数
						ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(order.getArrangeNo());
						if (String.valueOf(appionNumber) == null) {
							order.setOrderStatus(0);// 已预约
						} else {
							if (appionNumber >= arrayCourse.getPeopleNumber()) {
								order.setOrderStatus(4);// 预约人数已大于等于课程排课人数，此时修改订单状态为：排队中
							} else {
								order.setOrderStatus(0);// 已预约
							}
						}
						order.setPayStatus(1);// 支付状态为已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						order.setPaymentTime(paidTime);// 支付时间

						themeOrderService.updateThemeOrder(order, null);

						// 修改会员余额表
						MemberBalance memberBalance = memberBalanceService.getMemberBalance(order.getMemberNo());
						if (memberBalance != null) {
							MemberBalance mb = new MemberBalance();
							mb.setMemberNo(memberBalance.getMemberNo());
							if (order.getCourseBagNum() != null && order.getPeopleNumber() != null) {
								if (order.getPeopleNumber() > memberBalance.getCourseNumber()) {
									mb.setCourseNumber(0);
								} else {
									int surplusCourseBag = memberBalance.getCourseNumber() - order.getCourseBagNum();
									mb.setCourseNumber(surplusCourseBag);
								}
							}
							if (order.getBalanceDiscountAmount() != null) {
								if (memberBalance.getMemberBalance().compareTo(order.getBalanceDiscountAmount()) >= 0) {
									double surplusAmount = ECCalculateUtils.sub(memberBalance.getMemberBalance(),
											order.getBalanceDiscountAmount());
									mb.setMemberBalance(surplusAmount);
								} else {
									mb.setMemberBalance(0d);
								}
							}
							memberBalanceService.updateMemberBalance(mb, null);
						}

						// 修改优惠券
						if (StringUtils.isNotBlank(order.getCouponNo())) {
							Coupon coupon = couponService.getCoupon(order.getCouponNo());
							if (coupon != null) {
								Coupon cp = new Coupon();
								cp.setCouponNo(coupon.getCouponNo());
								cp.setUsedStatus(1);// 设置为已使用
								couponService.updateCoupon(cp, null);
							}
						}

						// 根据统计属于该课程的订单预约排队人数小于0 生成预约表
						if (arrayCourse != null) {
							long lineNum = themeOrderDao.statCourseLineNumber(order.getArrangeNo());
							if (lineNum <= 0) {
								Bespeak bespeakBean = bespeakDao.getBespeakByArrayCourseNo(arrayCourse.getArrayCourseNo());
								if (bespeakBean == null) {
									// 主题馆订单预约支付后生成预约表
									Bespeak bespeak = new Bespeak();
									bespeak.setBespeakNo(bespeakService.generatePK());
									bespeak.setArrayCourseNo(arrayCourse.getArrayCourseNo());
									bespeak.setBespeakDate(new Date());
									bespeak.setPeopleNumber(arrayCourse.getPeopleNumber());
									bespeak.setReservation(order.getPeopleNumber());
									bespeakService.addBespeak(bespeak, null);
								} else {
									Bespeak bp = new Bespeak();
									bp.setBespeakNo(bespeakBean.getBespeakNo());
									int appionNum = order.getPeopleNumber();
									bp.setReservation(appionNum + bespeakBean.getReservation());
									bespeakService.updateBespeak(bp, null);
								}

								// 生成预约表后更新排课表中预约人数
								/*ArrayCourse course = new ArrayCourse();
								course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
								int reservation = 0;
								reservation = order.getPeopleNumber();
								course.setReservation(reservation + arrayCourse.getReservation());
								arrayCourseService.updateArrayCourse(course, null);*/
							} 
							/*else {
								// 否则更新排课表排课人数
								ArrayCourse course = new ArrayCourse();
								course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
								int linNum = 0;
								linNum = order.getPeopleNumber();
								course.setLineUp(linNum + arrayCourse.getLineUp());
								arrayCourseService.updateArrayCourse(course, null);
							}*/
						}

						// 记录会员交易明细
						Accounts accounts = new Accounts();
						accounts.setAccountNo(accountsService.generatePK());
						accounts.setMemberNo(order.getMemberNo());
						accounts.setBusinessNo(order.getThemeOrderNo());
						accounts.setBusinessType(2);// 主题馆课程订单
						accounts.setAccountType(1);
						accounts.setAccountMoney(order.getPayableAmount());
						accounts.setAccountTime(new Date());
						accountsService.addAccounts(accounts, null);

						pr.setBizObjType(2);// 订单
						pr.setBizObjNo(order.getThemeOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						// payOverAddRecordzOrder(order);
					} else {
						log.info("重复请求");
						return;
					}
				} else if (flag.equals("Y")) {
					log.info("the trade_type is dist");
					DepositOrder dOrder = depositOrderService.getDepositOrder(shop_order_no);
					dOrder.setPayStatus(1);// 已支付
					dOrder.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
					dOrder.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
					dOrder.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
					dOrder.setPaymentTime(paidTime);
					dOrder.setRemainAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getDepositAmount(), 2));
					dOrder.setIsAvailable(1);
					depositOrderService.updateDepositOrder(dOrder, null);

					pr.setBizObjType(3);// 押金
					pr.setBizObjNo(dOrder.getDepositOrderNo());
					pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
					pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
					pr.setPaidTime(dOrder.getPaymentTime());

					MemberBalance me = memberBalanceService.getMemberBalance(dOrder.getMemberNo());
					if (me.getMemberDeposit() == null) {
						me.setMemberDeposit(0D);
					}

					me.setMemberDeposit(ECCalculateUtils.add(me.getMemberDeposit(), dOrder.getPayableAmount()));
					memberBalanceService.updateMemberBalance(me, null);

					// 修改会员押金状态信息
					DepositStatus ds = depositStatusService.getDepositStatus(dOrder.getMemberNo());
					ds.setDepositStatus(1);
					ds.setPaymentType(1);
					depositStatusService.updateDepositStatus(ds, null);

					Accounts accounts = new Accounts();
					accounts.setAccountNo(accountsService.generatePK());
					accounts.setMemberNo(dOrder.getMemberNo());
					accounts.setBusinessNo(dOrder.getDepositOrderNo());
					accounts.setBusinessType(3);// 交押金
					accounts.setAccountType(1);
					accounts.setAccountMoney(dOrder.getDepositAmount());
					accounts.setAccountTime(new Date());
					accountsService.addAccounts(accounts, null);

				} else if (flag.equals("C")) {

					RechargeOrder order = rechargeOrderService.getRechargeOrder(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						order.setPayStatus(1);// 已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setIsAvailable(1);// 可用
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						rechargeOrderService.updateRechargeOrder(order, null);

						// 查询我的余额
						MemberBalance me = memberBalanceService.getMemberBalance(order.getMemberNo());
						if (me.getMemberBalance() == null) {
							me.setMemberBalance(0D);
						}

						// 充值成功后给记账表添加记录
						Accounts ac = new Accounts();
						ac.setMemberNo(order.getMemberNo());
						ac.setBusinessNo(order.getRechargeOrderNo());
						ac.setBusinessType(5);
						ac.setAccountType(2);
						ac.setAccountMoney(order.getPayableAmount());
						ac.setAccountBeforeMoney(me.getMemberBalance());
						ac.setAccountOverMoney(ECCalculateUtils.add(order.getPayableAmount(), ac.getAccountMoney()));
						ac.setAccountTime(new Date());
						accountsService.addAccounts(ac, null);

						me.setMemberBalance(ECCalculateUtils.add(me.getMemberBalance(), order.getRechargeAmount()));
						memberBalanceService.updateMemberBalance(me, null);

						pr.setBizObjType(4);
						pr.setBizObjNo(order.getRechargeOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

					} else {
						log.info("重复请求");
						return;
					}

				} else if (flag.equals("J")) {

					GymCardOrder order = gymCardOrderService.getGymCardOrder(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						order.setPayStatus(1);// 已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setIsAvailable(1);// 可用
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						gymCardOrderService.updateGymCardOrder(order, null);

						// 查看会员是否有正在使用的健身包月卡
						MemberGymCard mc = new MemberGymCard();
						mc.setMemberNo(order.getMemberNo());
						mc.setGymcardStatus(1);
						mc.setGymCardType(1);
						List<MemberGymCard> ms = memberGymCardService.getMemberGymCardList(new Query(mc));
						if (ms.size() > 0) {
							MemberGymCard mg = ms.get(0);

							Date d = mg.getEndTime();
							mg.setEndTime(ECDateUtils.getDateAfter(d, 30));
							memberGymCardService.updateMemberGymCard(mg, null);

						} else {
							Member m = memberService.getMember(order.getMemberNo());

							MemberGymCard mg = new MemberGymCard();
							mg.setMemberNo(m.getMemberNo());
							mg.setMemberName(m.getMemberName());
							mg.setGymCardNo(order.getGymCardNo());
							mg.setGymCardName(order.getGymCardName());
							mg.setGymCardType(1);
							mg.setGymcardStatus(1);
							mg.setStartTime(new Date());
							mg.setEndTime(ECDateUtils.getDateAfter(new Date(), 30));
							memberGymCardService.addMemberGymCard(mg, null);

						}

						Accounts accounts = new Accounts();
						accounts.setAccountNo(accountsService.generatePK());
						accounts.setMemberNo(order.getMemberNo());
						accounts.setBusinessNo(order.getGymCardNo());
						accounts.setBusinessType(6);// 购买健身卡
						accounts.setAccountType(1);
						accounts.setAccountMoney(order.getPayableAmount());
						accounts.setAccountTime(new Date());
						accountsService.addAccounts(accounts, null);

						pr.setBizObjType(5);
						pr.setBizObjNo(order.getGymCardOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

					} else {
						log.info("重复请求");
						return;
					}

				} else if (flag.equals("K")) {

					CoursePackageOrder order = coursePackageOrderService.getCoursePackageOrder(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						order.setPayStatus(1);// 已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setIsAvailable(1);// 可用
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						coursePackageOrderService.updateCoursePackageOrder(order, null);
						// 查询课程包节数
						CoursePackage cg = coursePackageService.getCoursePackage(order.getCoursePackageNo());

						// 修改会员的课程节数
						MemberBalance me = memberBalanceService.getMemberBalance(order.getMemberNo());
						if (me.getCourseNumber() == null) {
							me.setCourseNumber(0);
						}

						me.setCourseNumber(me.getCourseNumber() + cg.getCourseNumber());
						memberBalanceService.updateMemberBalance(me, null);

						Accounts accounts = new Accounts();
						accounts.setAccountNo(accountsService.generatePK());
						accounts.setMemberNo(order.getMemberNo());
						accounts.setBusinessNo(order.getRechargeOrderNo());
						accounts.setBusinessType(7);// 购买课程包
						accounts.setAccountType(1);
						accounts.setAccountMoney(order.getPayableAmount());
						accounts.setAccountTime(new Date());
						accountsService.addAccounts(accounts, null);

						pr.setBizObjType(6);
						pr.setBizObjNo(order.getRechargeOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

					} else {
						log.info("重复请求");
						return;
					}
				}
				// pr.setPaidAmount(totalFee);
				pr.setPayStatus(1);
				pr.setPayType(1);
				pr.setPayFlowNo(trade_no);
				pr.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
				pr.setBuyerId(buyerId);
				pr.setBuyerAccount(buyerEmail);
				paymentRecordService.addPaymentRecord(pr, null);
				// out.println("success"); //请不要修改或删除

				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				PrintWriter out = response.getWriter();
				out.println("success");
				out.flush();
				out.close();
				// System.out.println("success");
				// 1、开通了普通即时到账，买家付款成功后。
				// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				// 判断该笔订单是否在商户网站中已经做过处理
				// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
				// 如果有做过处理，不执行商户的业务程序
				// 该种交易状态只在两种情况下出现
				// ——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
				/*
				 * 支付记录表添加数据
				 */
				PaymentRecord pr = new PaymentRecord();

				// flag 表示支付的业务类型， 代表订单D， Y 代表押金，T 代表套餐
				String flag = shop_order_no.substring(0, 1);
				if (flag.equals("S")) {

					CommunityOrder order = communityOrderService.getCommunityOrder(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						order.setPayStatus(1);// 支付状态为已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						order.setPaymentTime(paidTime);

						communityOrderService.updateCommunityOrder(order, null);

						Accounts accounts = new Accounts();
						accounts.setAccountNo(accountsService.generatePK());
						accounts.setMemberNo(order.getMemberNo());
						accounts.setBusinessNo(order.getCommunityOrderNo());
						accounts.setBusinessType(1);// 社区馆订单
						accounts.setAccountType(1);
						accounts.setAccountMoney(order.getPayableAmount());
						accounts.setAccountTime(new Date());
						accountsService.addAccounts(accounts, null);

						pr.setBizObjType(1);// 订单
						pr.setBizObjNo(order.getCommunityOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						payOverAddRecordOrder(order);

					} else {
						log.info("重复请求");
						return;
					}

				} else if (flag.equals("Z")) {
					// 查询订单，修改订单
					ThemeOrder order = themeOrderService.getThemeOrder(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						// 根据订单中的排课编号统计属于该课程的预约人数
						long appionNumber = themeOrderService.statCourseAppoinNumber(order.getArrangeNo());
						// 查询该课程的排课人数
						ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(order.getArrangeNo());
						if (String.valueOf(appionNumber) == null) {
							order.setOrderStatus(0);// 已预约
						} else {
							if (appionNumber >= arrayCourse.getPeopleNumber()) {
								order.setOrderStatus(4);// 预约人数已大于等于课程排课人数，此时修改订单状态为：排队中
							} else {
								order.setOrderStatus(0);// 已预约
							}
						}
						order.setPayStatus(1);// 支付状态为已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						order.setPaymentTime(paidTime);// 支付时间

						themeOrderService.updateThemeOrder(order, null);

						// 修改会员余额表
						MemberBalance memberBalance = memberBalanceService.getMemberBalance(order.getMemberNo());
						if (memberBalance != null) {
							MemberBalance mb = new MemberBalance();
							mb.setMemberNo(memberBalance.getMemberNo());
							if (order.getCourseBagNum() != null && order.getPeopleNumber() != null) {
								if (order.getPeopleNumber() > memberBalance.getCourseNumber()) {
									mb.setCourseNumber(0);
								} else {
									int surplusCourseBag = memberBalance.getCourseNumber() - order.getCourseBagNum();
									mb.setCourseNumber(surplusCourseBag);
								}
							}
							if (order.getBalanceDiscountAmount() != null) {
								if (memberBalance.getMemberBalance().compareTo(order.getBalanceDiscountAmount()) >= 0) {
									double surplusAmount = ECCalculateUtils.sub(memberBalance.getMemberBalance(),
											order.getBalanceDiscountAmount());
									mb.setMemberBalance(surplusAmount);
								} else {
									mb.setMemberBalance(0d);
								}
							}
							memberBalanceService.updateMemberBalance(mb, null);
						}

						// 修改优惠券为已使用
						if (StringUtils.isNotBlank(order.getCouponNo())) {
							Coupon coupon = couponService.getCoupon(order.getCouponNo());
							if (coupon != null) {
								Coupon cp = new Coupon();
								cp.setCouponNo(coupon.getCouponNo());
								cp.setUsedStatus(1);// 设置为已使用
								couponService.updateCoupon(cp, null);
							}
						}
						// 根据统计属于该课程的订单预约排队人数小于0 生成预约表
						if (arrayCourse != null) {
							long lineNum = themeOrderDao.statCourseLineNumber(order.getArrangeNo());
							if (lineNum <= 0) {
								Bespeak bespeakBean = bespeakDao
										.getBespeakByArrayCourseNo(arrayCourse.getArrayCourseNo());
								if (bespeakBean == null) {
									// 主题馆订单预约支付后生成预约表
									Bespeak bespeak = new Bespeak();
									bespeak.setBespeakNo(bespeakService.generatePK());
									bespeak.setArrayCourseNo(arrayCourse.getArrayCourseNo());
									bespeak.setBespeakDate(new Date());
									bespeak.setPeopleNumber(arrayCourse.getPeopleNumber());
									bespeak.setReservation(order.getPeopleNumber());
									bespeakService.addBespeak(bespeak, null);
								} else {
									Bespeak bp = new Bespeak();
									bp.setBespeakNo(bespeakBean.getBespeakNo());
									int appionNum = order.getPeopleNumber();
									bp.setReservation(appionNum + bespeakBean.getReservation());
									bespeakService.updateBespeak(bp, null);
								}

								// 生成预约表后更新排课表中预约人数
								/*ArrayCourse course = new ArrayCourse();
								course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
								int reservation = 0;
								reservation = order.getPeopleNumber();
								course.setReservation(reservation + arrayCourse.getReservation());
								arrayCourseService.updateArrayCourse(course, null);*/
							} 
							/*else {
								// 否则更新排课表排课人数
								ArrayCourse course = new ArrayCourse();
								course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
								int linNum = 0;
								linNum = order.getPeopleNumber();
								course.setLineUp(linNum + arrayCourse.getLineUp());
								arrayCourseService.updateArrayCourse(course, null);
							}*/
						}

						// 记录会员交易明细
						Accounts accounts = new Accounts();
						accounts.setAccountNo(accountsService.generatePK());
						accounts.setMemberNo(order.getMemberNo());
						accounts.setBusinessNo(order.getThemeOrderNo());
						accounts.setBusinessType(2);// 主题馆课程订单
						accounts.setAccountType(1);
						accounts.setAccountMoney(order.getPayableAmount());
						accounts.setAccountTime(new Date());
						accountsService.addAccounts(accounts, null);

						pr.setBizObjType(2);// 订单
						pr.setBizObjNo(order.getThemeOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));
						// payOverAddRecordzOrder(order);
					} else {
						log.info("重复请求");
						return;
					}
				} else if (flag.equals("Y")) {
					log.info("the trade_type is dist");
					DepositOrder dOrder = depositOrderService.getDepositOrder(shop_order_no);
					dOrder.setPayStatus(1);// 已支付
					dOrder.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
					dOrder.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
					dOrder.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
					dOrder.setPaymentTime(paidTime);
					dOrder.setRemainAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getDepositAmount(), 2));
					dOrder.setIsAvailable(1);// 设置成可用
					depositOrderService.updateDepositOrder(dOrder, null);

					// 押金缴纳后 新增交易明细记录
					Accounts accounts = new Accounts();
					accounts.setAccountNo(accountsService.generatePK());
					accounts.setMemberNo(dOrder.getMemberNo());
					accounts.setBusinessNo(dOrder.getDepositOrderNo());
					accounts.setBusinessType(3);// 交押金
					accounts.setAccountType(1);
					accounts.setAccountMoney(dOrder.getDepositAmount());
					accounts.setAccountTime(new Date());
					accountsService.addAccounts(accounts, null);

					pr.setBizObjType(3);// 押金
					pr.setBizObjNo(dOrder.getDepositOrderNo());
					pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
					pr.setPaidAmount(ECNumberUtils.roundDoubleWithScale(dOrder.getPayableAmount(), 2));
					pr.setPaidTime(dOrder.getPaymentTime());

					MemberBalance me = memberBalanceService.getMemberBalance(dOrder.getMemberNo());
					if (me.getMemberDeposit() == null) {
						me.setMemberDeposit(0D);
					}

					me.setMemberDeposit(ECCalculateUtils.add(me.getMemberDeposit(), dOrder.getPayableAmount()));
					memberBalanceService.updateMemberBalance(me, null);

					// 修改会员押金状态信息
					DepositStatus ds = depositStatusService.getDepositStatus(dOrder.getMemberNo());
					ds.setDepositStatus(1);
					ds.setPaymentType(1);
					depositStatusService.updateDepositStatus(ds, null);

				} else if (flag.equals("C")) {

					RechargeOrder order = rechargeOrderService.getRechargeOrder(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						order.setPayStatus(1);// 已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setIsAvailable(1);// 可用
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						rechargeOrderService.updateRechargeOrder(order, null);

						// 查询我的余额
						MemberBalance me = memberBalanceService.getMemberBalance(order.getMemberNo());
						if (me.getMemberBalance() == null) {
							me.setMemberBalance(0D);
						}

						// 充值成功后给记账表添加记录
						Accounts ac = new Accounts();
						ac.setMemberNo(order.getMemberNo());
						ac.setBusinessNo(order.getRechargeOrderNo());
						ac.setBusinessType(5);
						ac.setAccountType(2);
						ac.setAccountMoney(order.getPayableAmount());
						ac.setAccountBeforeMoney(me.getMemberBalance());
						ac.setAccountOverMoney(ECCalculateUtils.add(order.getPayableAmount(), ac.getAccountMoney()));
						ac.setAccountTime(new Date());
						accountsService.addAccounts(ac, null);

						me.setMemberBalance(ECCalculateUtils.add(me.getMemberBalance(), order.getRechargeAmount()));
						memberBalanceService.updateMemberBalance(me, null);

						pr.setBizObjType(4);
						pr.setBizObjNo(order.getRechargeOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

					} else {
						log.info("重复请求");
						return;
					}

				} else if (flag.equals("J")) {

					GymCardOrder order = gymCardOrderService.getGymCardOrder(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						order.setPayStatus(1);// 已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setIsAvailable(1);// 可用
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						gymCardOrderService.updateGymCardOrder(order, null);

						// 查看会员是否有正在使用的健身包月卡
						MemberGymCard mc = new MemberGymCard();
						mc.setMemberNo(order.getMemberNo());
						mc.setGymcardStatus(1);
						mc.setGymCardType(1);
						List<MemberGymCard> ms = memberGymCardService.getMemberGymCardList(new Query(mc));
						if (ms.size() > 0) {
							MemberGymCard mg = ms.get(0);

							Date d = mg.getEndTime();
							mg.setEndTime(ECDateUtils.getDateAfter(d, 30));
							memberGymCardService.updateMemberGymCard(mg, null);

						} else {
							Member m = memberService.getMember(order.getMemberNo());

							MemberGymCard mg = new MemberGymCard();
							mg.setMemberNo(m.getMemberNo());
							mg.setMemberName(m.getMemberName());
							mg.setGymCardNo(order.getGymCardNo());
							mg.setGymCardName(order.getGymCardName());
							mg.setGymCardType(1);
							mg.setGymcardStatus(1);
							mg.setStartTime(new Date());
							mg.setEndTime(ECDateUtils.getDateAfter(new Date(), 30));
							memberGymCardService.addMemberGymCard(mg, null);

						}

						// 购买健身卡之后 新增交易记录
						Accounts accounts = new Accounts();
						accounts.setAccountNo(accountsService.generatePK());
						accounts.setMemberNo(order.getMemberNo());
						accounts.setBusinessNo(order.getGymCardNo());
						accounts.setBusinessType(6);// 健身卡
						accounts.setAccountType(1);
						accounts.setAccountMoney(order.getPayableAmount());
						accounts.setAccountTime(new Date());
						accountsService.addAccounts(accounts, null);

						pr.setBizObjType(5);
						pr.setBizObjNo(order.getGymCardOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

					} else {
						log.info("重复请求");
						return;
					}

				} else if (flag.equals("K")) {

					CoursePackageOrder order = coursePackageOrderService.getCoursePackageOrder(shop_order_no);
					if (null != order && order.getPayStatus() == 0) {
						order.setPayStatus(1);// 已支付
						order.setPaymentMethod(1);// 支付方式（1.支付宝，2.微信）
						order.setIsAvailable(1);// 可用
						order.setPaymentFlowNo(trade_no);// 支付流水号（支付宝交易流水号）
						order.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
						coursePackageOrderService.updateCoursePackageOrder(order, null);
						// 查询课程包节数
						CoursePackage cg = coursePackageService.getCoursePackage(order.getCoursePackageNo());

						// 修改会员的课程节数
						MemberBalance me = memberBalanceService.getMemberBalance(order.getMemberNo());
						if (me.getCourseNumber() == null) {
							me.setCourseNumber(0);
						}

						me.setCourseNumber(me.getCourseNumber() + cg.getCourseNumber());
						memberBalanceService.updateMemberBalance(me, null);

						// 购买课程包之后生成交易明细记录
						Accounts accounts = new Accounts();
						accounts.setAccountNo(accountsService.generatePK());
						accounts.setMemberNo(order.getMemberNo());
						accounts.setBusinessNo(order.getRechargeOrderNo());
						accounts.setBusinessType(7);// 课程包
						accounts.setAccountType(1);
						accounts.setAccountMoney(order.getPayableAmount());
						accounts.setAccountTime(new Date());
						accountsService.addAccounts(accounts, null);

						pr.setBizObjType(6);
						pr.setBizObjNo(order.getRechargeOrderNo());
						pr.setPayableAmount(ECNumberUtils.roundDoubleWithScale(order.getPayableAmount(), 2));

					} else {
						log.info("重复请求");
						return;
					}
				}
				// pr.setPaidAmount(totalFee);
				pr.setPayStatus(1);
				pr.setPayType(1);
				pr.setPayFlowNo(trade_no);
				pr.setPartTradeFlowNo(part_trade_flow_no); // 内部支付流水号
				pr.setBuyerId(buyerId);
				pr.setBuyerAccount(buyerEmail);
				paymentRecordService.addPaymentRecord(pr, null);
				// out.println("success"); //请不要修改或删除

				response.setContentType("text/html;charset=UTF-8");
				response.setCharacterEncoding("UTF-8");
				response.setStatus(HttpServletResponse.SC_OK);
				PrintWriter out = response.getWriter();
				out.println("success");
				out.flush();
				out.close();
				// System.out.println("success");
				// 1、开通了普通即时到账，买家付款成功后。
				// 2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
			}

			// 注意：
			//////////////////////////////////////////////////////////////////////////////////////////
		} else {// 验证失败
			log.error("transtion error,because the sign fail");
			System.out.println("fail");
		}
	}

	/**
	 * 订单支付完成后，添加积分记录，修改会员积分和等级信息
	 */
	private void payOverAddRecordOrder(CommunityOrder order) {
		// 订单支付完成后，获得积分，记录到积分记录表中，并修改会员表的当前积分值和会员等级id
		MemberPointsRecord mpr = new MemberPointsRecord();
		mpr.setBusinessType(1);// 订单支付
		mpr.setPointsType(0);// 会员经验积分
		mpr.setOpType(1);// 增加积分
		// 获取订单支付积分规则
		Query q = new Query();
		MemberPointsRule mpRule = new MemberPointsRule();
		mpRule.setBusinessType(1);
		mpRule.setIsAvailable(1);
		// mpRule.setIsDefault(1);
		mpRule.setIsDeleted(0);
		q.setQ(mpRule);
		List<MemberPointsRule> mpRuleResultList = memberPointsRuleService.getMemberPointsRuleList(q);
		Integer pointsValue = 0;
		if (mpRuleResultList != null && mpRuleResultList.size() > 0) {
			Double amount = order.getPayableAmount();
			String val = amount.toString().substring(0, amount.toString().lastIndexOf("."));
			Integer amount1 = Integer.parseInt(val);
			if (amount1 == 0) {
				amount1 = 1;
			}
			pointsValue = mpRuleResultList.get(0).getPointsValue() * amount1;
		}
		mpr.setPointsValue(pointsValue);
		mpr.setBusinessData(order.getCommunityOrderNo());
		mpr.setMemberNo(order.getMemberNo());
		mpr.setRecordMemo("社区订单支付获得积分");
		if (mpr.getPointsValue() > 0) {
			List<MemberPointsRecord> list = memberPointsRecordService.getMemberPointsRecordList(new Query(mpr));
			if (list == null || list.size() <= 0) {
				memberPointsRecordService.addMemberPointsRecord(mpr, null);
				Member member = memberService.getMember(order.getMemberNo());
				if (member != null) {
					// 会员当前积分值
					if (member.getMemberPointsValue() == null) {
						member.setMemberPointsValue(0);
					}
					member.setMemberPointsValue(member.getMemberPointsValue() + mpr.getPointsValue());
					Integer pointsValueLevel = memberLevelService.getNowLevelPoints(member.getMemberPointsValue());
					MemberLevel mLevelSearch = new MemberLevel();
					mLevelSearch.setIsAvailable(1);
					mLevelSearch.setIsDeleted(0);
					mLevelSearch.setUpgradePoint(pointsValueLevel);
					List<MemberLevel> mLevelList = memberLevelService.getMemberLevelList(new Query(mLevelSearch));
					if (mLevelList != null && mLevelList.size() > 0) {
						// 会员等级
						member.setMemberLevelId(mLevelList.get(0).getMemberLevelId());
					}
					Member mUp = new Member();
					mUp.setMemberNo(member.getMemberNo());
					mUp.setMemberPointsValue(member.getMemberPointsValue());
					mUp.setMemberLevelId(member.getMemberLevelId());
					memberService.updateMember(mUp, null);
				}
			}
		}
	}

	/**
	 * 订单支付完成后，添加积分记录，修改会员积分和等级信息
	 */
	@Override
	public void payOverAddRecordzOrder(ThemeOrder order) {
		// 订单支付完成后，获得积分，记录到积分记录表中，并修改会员表的当前积分值和会员等级id
		MemberPointsRecord mpr = new MemberPointsRecord();
		mpr.setBusinessType(2);// 订单支付
		mpr.setPointsType(0);// 会员经验积分
		mpr.setOpType(1);// 增加积分
		// 获取订单支付积分规则
		Query q = new Query();
		MemberPointsRule mpRule = new MemberPointsRule();
		mpRule.setBusinessType(2);
		mpRule.setIsAvailable(1);
		// mpRule.setIsDefault(1);
		mpRule.setIsDeleted(0);
		q.setQ(mpRule);
		List<MemberPointsRule> mpRuleResultList = memberPointsRuleService.getMemberPointsRuleList(q);
		Integer pointsValue = 0;
		if (mpRuleResultList != null && mpRuleResultList.size() > 0) {
			Double amount = order.getPayableAmount();
			String val = amount.toString().substring(0, amount.toString().lastIndexOf("."));
			Integer amount1 = Integer.parseInt(val);
			if (amount1 == 0) {
				amount1 = 1;
			}
			pointsValue = mpRuleResultList.get(0).getPointsValue() * amount1;
		}
		mpr.setPointsValue(pointsValue);
		mpr.setBusinessData(order.getThemeOrderNo());
		mpr.setMemberNo(order.getMemberNo());
		mpr.setRecordMemo("主题订单支付获得积分");
		if (mpr.getPointsValue() > 0) {
			List<MemberPointsRecord> list = memberPointsRecordService.getMemberPointsRecordList(new Query(mpr));
			if (list == null || list.size() <= 0) {
				memberPointsRecordService.addMemberPointsRecord(mpr, null);
				Member member = memberService.getMember(order.getMemberNo());
				if (member != null) {
					// 会员当前积分值
					if (member.getMemberPointsValue() == null) {
						member.setMemberPointsValue(0);
					}
					member.setMemberPointsValue(member.getMemberPointsValue() + mpr.getPointsValue());
					Integer pointsValueLevel = memberLevelService.getNowLevelPoints(member.getMemberPointsValue());
					MemberLevel mLevelSearch = new MemberLevel();
					mLevelSearch.setIsAvailable(1);
					mLevelSearch.setIsDeleted(0);
					mLevelSearch.setUpgradePoint(pointsValueLevel);
					List<MemberLevel> mLevelList = memberLevelService.getMemberLevelList(new Query(mLevelSearch));
					if (mLevelList != null && mLevelList.size() > 0) {
						// 会员等级
						member.setMemberLevelId(mLevelList.get(0).getMemberLevelId());
					}
					Member mUp = new Member();
					mUp.setMemberNo(member.getMemberNo());
					mUp.setMemberPointsValue(member.getMemberPointsValue());
					mUp.setMemberLevelId(member.getMemberLevelId());
					memberService.updateMember(mUp, null);
				}
			}
		}
	}

	private String getWxAuthOpenid(String memberNo) {
		if (cacheUtil.getObject("wxauth_" + memberNo) != null) {
			Map<String, String> authMap = (Map<String, String>) cacheUtil.getObject("wxauth_" + memberNo);

			return authMap.get("openid");
		}
		return "";
	}
	
	/**
	 * 订单支付前校验预约课程人数
	 */
	private ResultInfo<String> checkAppoinArrayCourse(String memberNo, String arrayCourseNo, String oppionNumber, String payStatus) {
		ResultInfo<String> result = new ResultInfo<String>();
			try {
				// 查询会员是否为黑名单
				Member member = memberService.getMember(memberNo);
				if (member != null && member.getIsBlacklist() == 0) {
					//查询会员是否存在未支付的订单
					if(StringUtils.isBlank(payStatus)){
						ThemeOrder themeOrder = new ThemeOrder();
						themeOrder.setMemberNo(member.getMemberNo());
						themeOrder.setPayStatus(0);//未支付
						List<ThemeOrder> listThemeOrder = themeOrderService.getThemeOrderList(new Query(themeOrder));
						if(listThemeOrder != null && listThemeOrder.size() > 0){
							result.setCode(Constant.FAIL);
							result.setMsg("很抱歉、当前有待支付的订单，请跳转至主题订单列表进行支付！");
						}else{
							ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
							SysParam param = sysParamService.getByParamKey("COURSE_LINE_UP");
							int paramNum = Integer.parseInt(param.getParamValue());
							if(arrayCourse != null){
								if (arrayCourse.getReservation() != null) {
									if(arrayCourse.getReservation() == arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() == 0){
										result.setCode(Constant.FAIL);
										result.setMsg("此课程预约人数已满，请您去预约排队等候...");
									}else if(arrayCourse.getReservation() == arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() == paramNum){
										result.setCode(Constant.FAIL);
										result.setMsg("对不起、此课程预约人数和排队人数都已满，请您选择其他课程预约...");
									}else{
										result.setCode(Constant.SECCUESS);
									}
								}else {
									result.setCode(Constant.SECCUESS);
								}
							}
						}
					}else{
						ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
						SysParam param = sysParamService.getByParamKey("COURSE_LINE_UP");
						int paramNum = Integer.parseInt(param.getParamValue());
						if(arrayCourse != null){
							if (arrayCourse.getReservation() != null) {
								if(arrayCourse.getReservation() == arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() == 0){
									result.setCode(Constant.FAIL);
									result.setMsg("此课程预约人数已满，请您去预约排队等候...");
								}else if(arrayCourse.getReservation() == arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() == paramNum){
									result.setCode(Constant.FAIL);
									result.setMsg("很抱歉、此课程预约人数和排队人数都已满，请您选择其他课程预约...");
								}else{
									result.setCode(Constant.SECCUESS);
								}
							}else {
								result.setCode(Constant.SECCUESS);
							}
						}
					}
				} else {
					result.setCode(Constant.FAIL);
					result.setMsg("对不起、您已经被移入黑名单，暂时不能预约课程，请联系客服...");
				}
			} catch (Exception e) {
				result.setCode(Constant.FAIL);
				result.setMsg(e.getMessage());
			}
		return result;
	}
}