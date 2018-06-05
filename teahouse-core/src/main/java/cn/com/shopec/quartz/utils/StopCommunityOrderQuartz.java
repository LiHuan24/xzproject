package cn.com.shopec.quartz.utils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.ParseException;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.EquipmentRecord;
import cn.com.shopec.core.equipment.service.EquipmentRecordService;
import cn.com.shopec.core.finace.model.CustomPricingRule;
import cn.com.shopec.core.finace.model.PricingRule;
import cn.com.shopec.core.finace.service.CustomPricingRuleService;
import cn.com.shopec.core.finace.service.PricingRuleService;
import cn.com.shopec.core.order.model.CommunityOrder;
import cn.com.shopec.core.order.service.CommunityOrderService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;

/**
 * 扫描会员进行中订单的健身记录
 * 
 */
public class StopCommunityOrderQuartz {

	private Log log = LogFactory.getLog(StopCommunityOrderQuartz.class);

	@Resource
	private SysParamService sysParamService;
	@Resource
	private CommunityOrderService communityOrderService;
	@Resource
	private EquipmentRecordService equipmentRecordService;
	@Resource
	private PricingRuleService pricingRuleService;
	@Resource
	private CustomPricingRuleService customPricingRuleService;

	public void quartzStart() throws Exception {
		// 获取结束社区订单时间值
		SysParam param = sysParamService.getByParamKey("stop_communityOrder_time");
		if (param != null && param.getParamValue() != null) {
			// 查找正在进行中的订单
			CommunityOrder o = new CommunityOrder();
			o.setOrderStatus(0);// 进行中
			List<CommunityOrder> ls = communityOrderService.getCommunityOrderList(new Query(o));
			if (ls.size() > 0) {
				for (CommunityOrder c : ls) {
					// 查看健身记录，已停止
					EquipmentRecord er = equipmentRecordService.getErMs(c.getCommunityOrderNo(), 1);
					if (er != null) {
						EquipmentRecord er2 = equipmentRecordService.getErMs(c.getCommunityOrderNo(), 0);
						if (er2 == null) {
							if (er.getEndTime() != null) {
								Long m = ECDateUtils.differMinutes(er.getEndTime(), new Date());
								if (m > Long.parseLong(param.getParamValue())) {
									// 结束订单
									finishOrder(c.getCommunityOrderNo());
								}
							}

						}
					}
				}

			}
		}
	}

	protected void execute(String arg0) throws Exception {
		try {
			log.info("--------扫描会员进行中订单的健身记录，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			log.info("--------扫描会员进行中订单的健身记录定时任务完成...");
		} catch (Exception e) {
			log.error("--------扫描会员进行中订单的健身记录，错误信息：" + e.getMessage(), e);
		}
	}

	public ResultInfo<String> finishOrder(String orderNo) throws ParseException, IOException {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		CommunityOrder o = communityOrderService.getCommunityOrder(orderNo);
		if (o == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("获取订单信息异常，请联系客服。");
			return resultInfo;
		}
		try {

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

			} else {
				// 订单结束时间
				o.setEndTime(new Date());
				// 设置订单总金额
				o.setOrderAmount(0D);

				// 应付金额

				o.setPayableAmount(0D);

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

}