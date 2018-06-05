package cn.com.shopec.mapi.order.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.hardware.HardwareUtils;
import cn.com.shopec.common.hardware.HwData;
import cn.com.shopec.common.hardware.HwUrl;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.EquipmentRecord;
import cn.com.shopec.core.equipment.model.FitnessEquipment;
import cn.com.shopec.core.equipment.service.EquipmentRecordService;
import cn.com.shopec.core.equipment.service.FitnessEquipmentService;
import cn.com.shopec.core.finace.common.DepositConstant;
import cn.com.shopec.core.finace.model.CustomPricingRule;
import cn.com.shopec.core.finace.model.DepositStatus;
import cn.com.shopec.core.finace.model.PricingRule;
import cn.com.shopec.core.finace.service.AccountsService;
import cn.com.shopec.core.finace.service.CustomPricingRuleService;
import cn.com.shopec.core.finace.service.DepositStatusService;
import cn.com.shopec.core.finace.service.PricingRuleService;
import cn.com.shopec.core.marketing.dao.CouponDao;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.member.common.MemberConstant;
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
import cn.com.shopec.core.order.model.CommunityOrder;
import cn.com.shopec.core.order.service.CommunityOrderService;
import cn.com.shopec.core.order.vo.OrderDetailsVo;
import cn.com.shopec.core.order.vo.OrderListVo;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.order.vo.CyCouponVo;
import cn.com.shopec.mapi.order.vo.CyOrderPayVo;
import cn.com.shopec.mapi.order.vo.CyOrderVo;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app/communityOrder")
public class CommunityOrderController extends BaseController {
	// 创建日志文件
	private static Logger log = Logger.getLogger(CommunityOrderController.class);
	@Resource
	private DepositStatusService depositStatusService;
	@Resource
	private CommunityOrderService communityOrderService;
	@Resource
	private MemberService memberService;
	@Resource
	private MemberGymCardService memberGymCardService;
	@Resource
	private FitnessEquipmentService fitnessEquipmentService;
	@Resource
	private EquipmentRecordService equipmentRecordService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private PricingRuleService pricingRuleService;
	@Resource
	private CouponService couponService;
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private MemberBalanceService memberBalanceService;
	@Resource
	private AccountsService accountsService;
	@Resource
	private CustomPricingRuleService customPricingRuleService;
	@Resource
	private MemberPointsRuleService memberPointsRuleService;
	@Resource
	private MemberPointsRecordService memberPointsRecordService;
	@Resource
	private MemberLevelService memberLevelService;
	@Resource
	private StoreService storeService;
	@Resource
	private CouponDao couponDao;
	@Value("${city_coding}")
	private String city_coding;

	/**
	 * 扫一扫
	 * 
	 */

	@RequestMapping("scan")
	@ResponseBody
	public ResultInfo<CyOrderVo> scan(String memberNo, String devId) {
		ResultInfo<CyOrderVo> resultInfo = new ResultInfo<CyOrderVo>();
		CyOrderVo v = new CyOrderVo();
		try {
			Member m = memberService.getMember(memberNo);
			if (m.getIsBlacklist() != null && m.getIsBlacklist() == 1) {
				resultInfo.setCode(MemberConstant.fail_code);
				resultInfo.setMsg("会员已被移入黑名单，暂不能使用设备。");
				return resultInfo;
			}
			// 使用扫码功能进行开启设备
			JSONObject jo = new JSONObject();

			// 会员在平台的id
			jo.put("mid", m.getMid());
			jo.put("devId", devId);

			Store s = storeService.getStoreById(devId);
			if (s != null) {
				ResultInfo<HwData> info = HardwareUtils.post(jo, HwUrl.start);
				if (info.getData().getCode().equals("0")) {
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setMsg(info.getData().getDesc());
					return resultInfo;
				} else {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(info.getData().getDesc());
					return resultInfo;
				}
			}

			// 根据平台设备ID查找设备
			FitnessEquipment fe = fitnessEquipmentService.getFeByDevId(devId);
			if (fe == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("未能获取设备信息，请联系客服");
				return resultInfo;
			}

			// 判断是否有进行中的订单
			CommunityOrder cod = new CommunityOrder();
			cod.setMemberNo(memberNo);
			cod.setOrderStatus(0);// 进行中

			List<CommunityOrder> odLs = communityOrderService.getCommunityOrderList(new Query(cod));
			if (odLs.size() > 0) {

				try {
					ResultInfo<HwData> info = HardwareUtils.post(jo, HwUrl.start);
					if (info.getData().getCode().equals("0")) {
						cod = odLs.get(0);
						v.setCommunityOrderNo(cod.getCommunityOrderNo());
						v.setStartTime(ECDateUtils.formatDate(cod.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
						v.setGymCardType(cod.getConsumeType() + "");
						v.setDevId(devId);
						v.setSortName(fe.getSortName());

						// 判断会员是否有正在使用中的健身设备
						EquipmentRecord rd = new EquipmentRecord();
						rd.setMemberNo(memberNo);
						rd.setFitnessEquipmentNo(fe.getFitnessEquipmentNo());
						rd.setUseStatus(0);
						List<EquipmentRecord> rs = equipmentRecordService.getEquipmentRecordList(new Query(rd));
						if (rs.size() <= 0) {
							// 增加设备使用记录
							EquipmentRecord r = new EquipmentRecord();
							r.setRecordId(String.valueOf(System.nanoTime()));
							r.setOrderNo(cod.getCommunityOrderNo());
							r.setCityId(cod.getCityId());
							r.setCityName(cod.getCityName());
							r.setStoreNo(cod.getStoreNo());
							r.setStoreName(cod.getStoreName());
							r.setMemberNo(cod.getMemberNo());
							r.setMobilePhone(cod.getMobilePhone());
							r.setFitnessEquipmentNo(fe.getFitnessEquipmentNo());
							r.setSortName(fe.getSortName());
							r.setStartTime(new Date());
							r.setUseStatus(0);
							r.setUseType(cod.getConsumeType());
							r.setIsDeleted(0);
							equipmentRecordService.addEquipmentRecord(r, null);
						}

						resultInfo.setCode(Constant.SECCUESS);
						resultInfo.setData(v);
						resultInfo.setMsg("获取订单信息成功");
					} else {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg(info.getData().getDesc());
					}

				} catch (IOException e) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(Constant.fail_msg);
					e.printStackTrace();
				}

			} else {

				// 查看会员是否缴纳押金
				DepositStatus ds = depositStatusService.getDepositStatus(memberNo);
				if (ds.getDepositStatus() != 1) {
					resultInfo.setCode("6");
					resultInfo.setMsg(DepositConstant.no_pay_deposit);
					return resultInfo;
				}
				// 查看会员是否有未支付的订单
				CommunityOrder o = new CommunityOrder();
				o.setMemberNo(memberNo);
				o.setOrderStatus(1);// 已结束
				o.setPayStatus(0);// 未支付

				List<CommunityOrder> ls = communityOrderService.getCommunityOrderList(new Query(o));
				if (ls.size() > 0) {
					resultInfo.setCode("7");
					resultInfo.setMsg(ls.get(0).getCommunityOrderNo());
					return resultInfo;
				}

				EquipmentRecord rd = new EquipmentRecord();
				rd.setMemberNo(memberNo);
				rd.setFitnessEquipmentNo(fe.getFitnessEquipmentNo());
				rd.setUseStatus(0);
				List<EquipmentRecord> rs = equipmentRecordService.getEquipmentRecordList(new Query(rd));
				if (rs.size() > 0) {
					for (EquipmentRecord p : rs) {
						p.setUseStatus(1);
						// 修改设备记录
						equipmentRecordService.updateEquipmentRecord(p, null);
					}

				}

				v.setDevId(devId);
				v.setSortName(fe.getSortName());

				// 查询会员是否有月卡
				MemberGymCard mc = new MemberGymCard();
				mc.setMemberNo(memberNo);
				mc.setGymCardType(1);

				mc.setEndTimeNs(ECDateUtils.formatDateToDateNew(new Date()));

				List<MemberGymCard> mcList = memberGymCardService.getMemberGymCardList(new Query(mc));
				if (mcList.size() > 0) {
					// 会员有健身包月卡
					v.setGymCardType("1");

				} else {
					// 查找会员是否有包天卡
					mc.setGymCardType(0);
					mcList = memberGymCardService.getMemberGymCardList(new Query(mc));
					if (mcList.size() > 0) {
						// 会员有健身包天卡
						v.setGymCardType("1");
					} else {
						v.setGymCardType("0");
					}
				}

				// ResultInfo<HwData> info = HardwareUtils.post(jo,
				// HwUrl.start);

				// 创建订单
				CommunityOrder od = new CommunityOrder();
				od.setMemberNo(memberNo);
				od.setMobilePhone(m.getMobilePhone());
				od.setCityId(fe.getCityId());
				od.setCityName(fe.getCityName());
				od.setStoreNo(fe.getSortNo());
				od.setStoreName(fe.getStoreName());
				od.setStartTime(new Date());
				od.setConsumeType(Integer.parseInt(v.getGymCardType()));
				od.setOrderStatus(0);
				od.setPayStatus(0);
				od.setIsDeleted(0);

				ResultInfo<CommunityOrder> r = communityOrderService.addOrder(od, jo, HwUrl.start, fe);
				if (r.getCode().equals(Constant.SECCUESS)) {
					v.setCommunityOrderNo(r.getData().getCommunityOrderNo());
					v.setStartTime(ECDateUtils.formatDate(od.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(v);
					resultInfo.setMsg("启动设备成功");
				} else {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(r.getMsg());
				}
			}
			return resultInfo;

		} catch (ParseException e) {
			e.printStackTrace();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		} catch (IOException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;

	}

	/**
	 * 社区馆列表进入正在进行中的订单
	 * 
	 */
	@RequestMapping("getCommunityOrder")
	@ResponseBody
	public ResultInfo<CyOrderVo> getCommunityOrder(String communityOrderNo) {
		ResultInfo<CyOrderVo> resultInfo = new ResultInfo<CyOrderVo>();
		CyOrderVo v = new CyOrderVo();

		try {
			CommunityOrder o = communityOrderService.getCommunityOrder(communityOrderNo);
			v.setCommunityOrderNo(communityOrderNo);
			// 订单开始时间
			v.setStartTime(ECDateUtils.formatDate(o.getStartTime(), "yyyy-MM-dd HH:mm:ss"));

			EquipmentRecord rd = new EquipmentRecord();

			rd.setOrderNo(o.getCommunityOrderNo());
			rd.setUseStatus(0);
			List<EquipmentRecord> rs = equipmentRecordService.getEquipmentRecordList(new Query(rd));
			if (rs.size() > 0) {
				EquipmentRecord r = rs.get(0);
				FitnessEquipment fe = fitnessEquipmentService.getFitnessEquipment(r.getFitnessEquipmentNo());
				v.setGymCardType(r.getUseType() + "");
				v.setDevId(fe.getDevId());
				v.setSortName(fe.getSortName());
				v.setIsUse("0");
			} else {
				v.setIsUse("1");
				v.setGymCardType(o.getConsumeType() + "");
			}

			resultInfo.setData(v);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg(Constant.YES_RECORD);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;

	}

	/**
	 * 停止设备
	 * 
	 */
	@RequestMapping("scanStop")
	@ResponseBody
	public ResultInfo<String> scanStop(String memberNo, String devId) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		try {
			// 根据平台设备ID查找设备
			FitnessEquipment fe = fitnessEquipmentService.getFeByDevId(devId);

			EquipmentRecord r = new EquipmentRecord();
			r.setMemberNo(memberNo);
			r.setFitnessEquipmentNo(fe.getFitnessEquipmentNo());
			r.setUseStatus(0);
			List<EquipmentRecord> rs = equipmentRecordService.getEquipmentRecordList(new Query(r));
			if (rs.size() > 0) {
				for (EquipmentRecord p : rs) {
					p.setUseStatus(1);
					p.setEndTime(new Date());
					Long time = ECDateUtils.differMinutes(p.getStartTime(), p.getEndTime());
					p.setWhenLong(time + "");

					// 修改设备记录
					equipmentRecordService.updateEquipmentRecord(p, null);
				}

			}

			Member m = memberService.getMember(memberNo);

			// 使用扫码功能进行开启设备
			JSONObject jo = new JSONObject();

			// 会员在平台的id
			jo.put("mid", m.getMid());
			jo.put("devId", devId);

			ResultInfo<HwData> info = HardwareUtils.post(jo, HwUrl.stop);
			if (info.getData().getCode().equals("0")) {
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg("停止设备成功");
				return resultInfo;
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(info.getData().getDesc());
				return resultInfo;
			}
		} catch (ParseException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		} catch (IOException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;

	}

	/**
	 * 订单转包天
	 * 
	 */
	@RequestMapping("upOrder")
	@ResponseBody
	public ResultInfo<EquipmentRecord> upOrder(String orderNo) {
		ResultInfo<EquipmentRecord> resultInfo = new ResultInfo<EquipmentRecord>();
		CommunityOrder o = communityOrderService.getCommunityOrder(orderNo);

		if (o == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("未获取订单信息");
			return resultInfo;

		} else {

			if (o.getConsumeType() == 0) {
				// o.setConsumeType(1);

				// 查看会员是否有正在使用的健身包月卡
				MemberGymCard mc = new MemberGymCard();
				mc.setMemberNo(o.getMemberNo());
				mc.setGymcardStatus(1);
				mc.setGymCardType(1);
				List<MemberGymCard> ms = memberGymCardService.getMemberGymCardList(new Query(mc));
				if (ms.size() > 0) {
					MemberGymCard mg = ms.get(0);

					Date d = mg.getEndTime();
					mg.setEndTime(ECDateUtils.getDateAfter(d, 1));
					memberGymCardService.updateMemberGymCard(mg, null);

				} else {
					Member m = memberService.getMember(o.getMemberNo());

					MemberGymCard mg = new MemberGymCard();
					mg.setMemberNo(m.getMemberNo());
					mg.setMemberName(m.getMemberName());
					mg.setGymCardType(1);
					mg.setGymcardStatus(1);
					mg.setStartTime(new Date());
					mg.setEndTime(ECDateUtils.getDateAfter(new Date(), 1));
					memberGymCardService.addMemberGymCard(mg, null);

				}

				ResultInfo<CommunityOrder> res = communityOrderService.updateCommunityOrder(o, null);

				if (res.getCode().equals(Constant.SECCUESS)) {

					// 把正在进行中的设备改为包月

					EquipmentRecord rd = new EquipmentRecord();

					rd.setOrderNo(o.getCommunityOrderNo());
					rd.setUseStatus(0);
					List<EquipmentRecord> rs = equipmentRecordService.getEquipmentRecordList(new Query(rd));
					if (rs.size() > 0) {
						EquipmentRecord ed = rs.get(0);
						ed.setUseType(1);
						resultInfo = equipmentRecordService.updateEquipmentRecord(ed, null);
					} else {
						resultInfo.setCode(Constant.SECCUESS);
						resultInfo.setMsg("操作成功。");
					}
				} else {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("请求重复");
				}

			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.fail_msg);
			}

		}

		return resultInfo;
	}

	/**
	 * 社区订单进入支付页面
	 * 
	 */
	@RequestMapping("toPayOrder")
	@ResponseBody
	public ResultInfo<CyOrderPayVo> toPayOrder(String orderNo) {
		ResultInfo<CyOrderPayVo> resultInfo = new ResultInfo<CyOrderPayVo>();

		try {
			CommunityOrder o = communityOrderService.getCommunityOrder(orderNo);
			CyOrderPayVo v = new CyOrderPayVo();

			v.setOrderNo(o.getCommunityOrderNo());
			v.setType(o.getConsumeType() + "");
			v.setOrderAmount(o.getOrderAmount() + "");
			// 查询会员余额
			MemberBalance ml = memberBalanceService.getMemberBalance(o.getMemberNo());

			v.setBalance(ml.getMemberBalance());
			// 查找会员优惠券
			List<CyCouponVo> listCoupon = this.getMemberCoupon(o.getMemberNo(), o.getOrderAmount());
			if (listCoupon != null && listCoupon.size() > 0) {
				v.setListCouponVo(listCoupon);
			} else {
				v.setListCouponVo(null);
			}
			resultInfo.setData(v);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg(Constant.YES_RECORD);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;
	}

	/**
	 * 结束订单
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	@RequestMapping("finishOrder")
	@ResponseBody
	public ResultInfo<CyOrderPayVo> finishOrder(String orderNo) throws ParseException, IOException {
		ResultInfo<CyOrderPayVo> resultInfo = new ResultInfo<CyOrderPayVo>();
		CyOrderPayVo v = new CyOrderPayVo();
		CommunityOrder o = communityOrderService.getCommunityOrder(orderNo);
		if (o == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("获取订单信息异常，请联系客服。");
			return resultInfo;
		}
		if (o.getOrderStatus() == 1) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("订单已经结束。");
			return resultInfo;
		}
		try {

			EquipmentRecord rd = new EquipmentRecord();

			rd.setOrderNo(o.getCommunityOrderNo());
			rd.setUseStatus(0);
			List<EquipmentRecord> rs = equipmentRecordService.getEquipmentRecordList(new Query(rd));
			if (rs.size() > 0) {
				// EquipmentRecord e = rs.get(0);
				// e.setUseStatus(1);
				// equipmentRecordService.updateEquipmentRecord(e, null);
				// 查找设备
				FitnessEquipment fe = fitnessEquipmentService.getFitnessEquipment(rs.get(0).getFitnessEquipmentNo());
				if (fe != null && fe.getDevId() != null) {
					// 停止设备
					scanStop(o.getMemberNo(), fe.getDevId());
					// 清除用户信息
					JSONObject jo = new JSONObject();

					jo.put("devId", fe.getDevId());

					HardwareUtils.post(jo, HwUrl.devClearUser);
				}
			}

			// 按小时计费
			if (o.getConsumeType() == 0) {

				Double hourPrice = 0D;

				// 查找计费规则
				PricingRule pr = new PricingRule();
				pr.setCityId(o.getCityId());
				pr.setIsEnable(1);
				pr.setCencorStatus(1);
				List<PricingRule> prList = pricingRuleService.getPricingRuleList(new Query(pr));
				if (prList.size() <= 0) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("获取计费规则异常");
					return resultInfo;
				}
				// 订单开始时间
				Date st = ECDateUtils.formatDateToDateNew(o.getStartTime());
				// 查找自定义计费规则
				CustomPricingRule r = new CustomPricingRule();
				r.setRuleNo(prList.get(0).getRuleNo());
				r.setCustomDate(st);
				List<CustomPricingRule> crList = customPricingRuleService.getCustomPricingRuleList(new Query(r));
				if (crList.size() > 0) {
					hourPrice = crList.get(0).getHourPrice();
				} else {
					hourPrice = prList.get(0).getHourPrice();
				}

				// 订单开始时间
				String start = ECDateUtils.formatDate(o.getStartTime(), "yyyy-MM-dd HH:mm:ss");
				// 订单结束时间
				o.setEndTime(new Date());
				String end = ECDateUtils.formatDate(o.getEndTime(), "yyyy-MM-dd HH:mm:ss");
				Integer n = ECDateUtils.getMinutes(start, end);
				if (n == 0) {
					// 0分钟算一分钟
					n = 1;
				}

				// 不足1小时按1小时计算
				if (n <= 60) {
					// Double money = ECCalculateUtils.mul(hourPrice,
					// Double.parseDouble("1"));
					// 设置订单总金额
					o.setOrderAmount(ECCalculateUtils.round(hourPrice, 2));

				} else {
					// 超过1小时的一律按2小时计算
					Double money = ECCalculateUtils.mul(hourPrice, Double.parseDouble("2"));
					// 设置订单总金额
					o.setOrderAmount(money);
				}

				// 应付金额
				o.setPayableAmount(o.getOrderAmount());

				// 查询会员余额
				MemberBalance ml = memberBalanceService.getMemberBalance(o.getMemberNo());

				v.setBalance(ml.getMemberBalance());
				v.setOrderAmount(o.getOrderAmount() + "");
				v.setType("0");

				// 查找会员优惠券
				List<CyCouponVo> listCoupon = this.getMemberCoupon(o.getMemberNo(), o.getOrderAmount());
				if (listCoupon != null && listCoupon.size() > 0) {
					v.setListCouponVo(listCoupon);
				} else {
					v.setListCouponVo(null);
				}

			} else {
				// 订单结束时间
				o.setEndTime(new Date());
				// 设置订单总金额
				o.setOrderAmount(0D);

				// 应付金额

				o.setPayableAmount(0D);
				v.setType("1");

			}
			o.setOrderStatus(1);
			// 订单时长
			Long time = ECDateUtils.differMinutes(o.getStartTime(), o.getEndTime());
			o.setWhenLong(time + "");
			// 修改订单
			ResultInfo<CommunityOrder> res = communityOrderService.updateCommunityOrder(o, null);

			if (res.getCode().equals(Constant.FAIL)) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("修改订单时异常，请联系客服。");
				return resultInfo;
			}
			v.setOrderNo(orderNo);
			resultInfo.setData(v);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg(Constant.YES_RECORD);

		} catch (NumberFormatException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;

	}

	/**
	 * 社区订单支付
	 * 
	 */
	@RequestMapping("paymentCyOrder")
	@ResponseBody
	private ResultInfo<CommunityOrder> paymentCyOrder(String orderNo, String couponNo, String balance) {
		ResultInfo<CommunityOrder> result = new ResultInfo<CommunityOrder>();
		CommunityOrder o = communityOrderService.getCommunityOrder(orderNo);
		if (o == null) {
			result.setCode(Constant.FAIL);
			result.setMsg("获取订单信息异常，请联系客服。");
			return result;
		}
		try {
			// 优惠券已经抵消全部金额
			if (StringUtils.isNotBlank(couponNo) && StringUtils.isBlank(balance)) {
				// 修改优惠券
				Coupon coupon = couponService.getCoupon(couponNo);
				if (coupon != null) {

					coupon.setUsedStatus(1);// 修改为已使用
					couponService.updateCoupon(coupon, getOperator());
					// 优惠金额
					o.setDiscountAmount(o.getOrderAmount());
					result.setCode(OrderConstant.success_code);
					result.setMsg("会员使用优惠券已全额抵扣成功！无需支付其他费用。");
				}
			} else if (StringUtils.isNotBlank(couponNo) && StringUtils.isNotBlank(balance)) {
				// 同时使用优惠券和余额
				Coupon coupon = couponService.getCoupon(couponNo);

				if (coupon != null) {
					if (coupon.getCouponMethod() == 1) {
						Double z = ECCalculateUtils.sub(1, coupon.getDiscount());
						// 打折
						Double m = ECCalculateUtils.mul(o.getOrderAmount(), z);
						o.setDiscountAmount(ECCalculateUtils.round(m, 2));
					} else if (coupon.getCouponMethod() == 2) {
						// 直减
						if (coupon.getDiscountAmount() >= o.getOrderAmount()) {
							o.setDiscountAmount(o.getOrderAmount());
						} else {
							o.setDiscountAmount(coupon.getDiscountAmount());
						}

					}

					coupon.setUsedStatus(1);// 修改为已使用
					couponService.updateCoupon(coupon, getOperator());
					// 查找会员余额
					MemberBalance ml = memberBalanceService.getMemberBalance(o.getMemberNo());
					ml.setMemberBalance(ECCalculateUtils.sub(ml.getMemberBalance(), Double.parseDouble(balance)));
					memberBalanceService.updateMemberBalance(ml, null);

					o.setBalanceDiscountAmount(Double.parseDouble(balance));

				}

			} else if (StringUtils.isBlank(couponNo) && StringUtils.isNotBlank(balance)) {
				// 查找会员余额
				MemberBalance ml = memberBalanceService.getMemberBalance(o.getMemberNo());
				ml.setMemberBalance(ECCalculateUtils.sub(ml.getMemberBalance(), Double.parseDouble(balance)));
				memberBalanceService.updateMemberBalance(ml, null);

				o.setBalanceDiscountAmount(Double.parseDouble(balance));
			} else if (StringUtils.isBlank(couponNo) && StringUtils.isBlank(balance)) {
				// 会员使用健身卡
				o.setConsumeType(1);
			}

			// 修改订单
			o.setPayStatus(1);
			o.setPaymentMethod(3);
			o.setPaymentTime(new Date());

			// 记录会员交易明细
			// Accounts accounts = new Accounts();
			// accounts.setAccountNo(String.valueOf(System.nanoTime()));
			// accounts.setMemberNo(o.getMemberNo());
			// accounts.setBusinessNo(o.getCommunityOrderNo());
			// accounts.setBusinessType(1);// 主题馆课程订单
			// accounts.setAccountType(1);
			// accounts.setAccountMoney(o.getPayableAmount());
			// accounts.setAccountTime(new Date());
			// accountsService.addAccounts(accounts, getOperator());
			o.setPayableAmount(0D);
			result = communityOrderService.updateCommunityOrder(o, null);
			if (result.getCode().equals(Constant.SECCUESS)) {
				payOverAddRecordOrder(o);
				result.setMsg("支付成功。");
			}
		} catch (NumberFormatException e) {
			result.setCode(Constant.FAIL);
			result.setMsg("获取订单信息异常，请联系客服。");
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * 课程支付界面若选择优惠券，根据会员编号查询该会员拥有的优惠券数据
	 * 
	 * @param memberNo
	 *            会员编号
	 */
	private List<CyCouponVo> getMemberCoupon(String memberNo, Double amount) {
		List<CyCouponVo> listCouponVo = new ArrayList<CyCouponVo>();
		try {
			if (StringUtils.isNotBlank(memberNo)) {
				Coupon coupon = new Coupon();
				coupon.setMemberNo(memberNo);
				coupon.setIsAvailable(1);// 已启用
				coupon.setUsedStatus(0);// 未使用
				coupon.setAvailableTime2Start(new Date());
				List<Coupon> listCoupon = couponService.getCouponList(new Query(coupon));

				if (listCoupon != null && listCoupon.size() > 0) {
					for (Coupon couponBean : listCoupon) {
						if (couponBean.getCouponMethod() != 4) {
							// 判断优惠券是否已经过期，过期的不给APP页展示
							CyCouponVo couponVo = new CyCouponVo();
							couponVo.setMemberNo(memberNo);
							couponVo.setCouponNo(couponBean.getCouponNo());
							couponVo.setCouponName(couponBean.getTitle());
							if (couponBean.getCouponMethod() == 1) {
								couponVo.setConponType(String.valueOf(couponBean.getCouponMethod()));
								couponVo.setDiscountRate(couponBean.getDiscount());
								listCouponVo.add(couponVo);
							} else if (couponBean.getCouponMethod() == 2) {

								// 直减
								// 订单金额满此优惠券满减金额才可进行满减
								CouponPlan couponPlan = couponPlanService.getCouponPlan(couponBean.getPlanNo());
								if (couponPlan != null) {
									if (couponPlan.getCensorStatus() == 1 && couponPlan.getIsAvailable() == 1) {

										if (amount.compareTo(couponPlan.getConsumeAmount()) >= 0) {
											// 直减金额（满减金额）
											Double discountAmount = couponPlan.getDiscountAmount();
											couponVo.setConponType(String.valueOf(couponBean.getCouponMethod()));
											couponVo.setLapseAmount(String.valueOf(discountAmount));
											listCouponVo.add(couponVo);
										} else if (couponPlan.getConsumeAmount() == 0) {
											Double discountAmount = couponPlan.getDiscountAmount();
											couponVo.setConponType(String.valueOf(couponBean.getCouponMethod()));
											couponVo.setLapseAmount(String.valueOf(discountAmount));
											listCouponVo.add(couponVo);
										}
									}
								}

								// couponVo.setConponType(String.valueOf(couponBean.getCouponMethod()));
								// couponVo.setLapseAmount(String.valueOf(couponBean.getDiscountAmount()));
							} else if (couponBean.getCouponMethod() == 3) {
								couponVo.setConponType(String.valueOf(couponBean.getCouponMethod()));
								couponVo.setFreeFitnessTime(couponBean.getFreeFitnessTime());
								listCouponVo.add(couponVo);
							}

						}
					}
				}
			}
		} catch (Exception e) {
			log.info(e.toString());
		}
		return listCouponVo;
	}

	/**
	 * 订单列表
	 * 
	 */
	@RequestMapping("getOrderList")
	@ResponseBody
	public ResultInfo<List<OrderListVo>> getOrderList(String memberNo, Integer pageNo) {
		ResultInfo<List<OrderListVo>> resultInfo = new ResultInfo<List<OrderListVo>>();

		CommunityOrder o = new CommunityOrder();
		o.setMemberNo(memberNo);
		List<OrderListVo> ls = communityOrderService.getOrderList(new Query(pageNo, 10, o));
		if (ls.size() > 0) {
			resultInfo.setData(ls);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg(Constant.YES_RECORD);

		} else {
			resultInfo.setCode(Constant.NO_RESULT);
			resultInfo.setMsg(Constant.NO_RECORD);
		}
		return resultInfo;
	}

	/**
	 * 订单详情
	 * 
	 */
	@RequestMapping("getOrderDetails")
	@ResponseBody
	public ResultInfo<OrderDetailsVo> getOrderDetails(String orderNo) {
		ResultInfo<OrderDetailsVo> resultInfo = new ResultInfo<OrderDetailsVo>();

		OrderDetailsVo v = communityOrderService.getOrderDetails(orderNo);
		if (v != null) {
			resultInfo.setData(v);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg(Constant.YES_RECORD);
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
		}
		return resultInfo;

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

}
