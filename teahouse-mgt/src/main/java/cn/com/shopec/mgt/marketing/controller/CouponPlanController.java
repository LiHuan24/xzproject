package cn.com.shopec.mgt.marketing.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 优惠卷方案
 * 
 * @author admin
 *
 */
@Controller
@RequestMapping("couponPlan")
public class CouponPlanController extends BaseController {
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 优惠卷列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCouponPlanList")
	public String toCouponPlanList() {
		return "marketing/couponPlan_list";
	}

	/**
	 * 优惠卷列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListCouponPlan")
	@ResponseBody
	public PageFinder<CouponPlan> pageListCouponPlan(@ModelAttribute("couponPlan") CouponPlan couponPlan, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), couponPlan);
		return couponPlanService.getCouponPlanPagedList(q);
	}

	/**
	 * 优惠卷列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListCouponPlanNs")
	@ResponseBody
	public PageFinder<CouponPlan> pageListCouponPlanNs(@ModelAttribute("couponPlan") CouponPlan couponPlan,
			Query query) {
		couponPlan.setAvailableTime2Start(new Date());
		couponPlan.setRedeemQuantity(0);
		Query q = new Query(query.getPageNo(), query.getPageSize(), couponPlan);
		return couponPlanService.getCouponPlanPagedList(q);
	}

	/**
	 * 优惠卷详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCouponPlanView")
	public String toCouponPlanView(String planNo, ModelMap modelMap) {
		CouponPlan couponPlan = couponPlanService.getCouponPlan(planNo);
		if (couponPlan != null) {
			SysUser sysUser = sysUserService.detail(couponPlan.getCensorId());
			if (sysUser != null) {
				couponPlan.setCensorName(sysUser.getUserName());
			}
			if (couponPlan.getCityName() != null) {
				couponPlan.setCityName(couponPlan.getCityName().replaceAll(",", ", "));
			}
		}
		modelMap.put("couponPlan", couponPlan);
		return "marketing/couponPlan_view";
	}

	/**
	 * 优惠卷列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageViewCoupon")
	@ResponseBody
	public CouponPlan pageViewCoupon(@ModelAttribute("coupon") CouponPlan coupon, Query query) {
		return couponPlanService.getCouponPlan(coupon.getPlanNo());
	}

	/**
	 * 优惠卷增加页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddCouponPlan")
	public String toAddCouponPlan() {
		return "marketing/couponPlan_add";
	}

	/**
	 * 优惠卷添加
	 * 
	 * @return
	 */
	@RequestMapping("/addCouponPlan")
	@ResponseBody
	public ResultInfo<CouponPlan> addCouponPlan(@ModelAttribute("couponPlan") CouponPlan couponPlan) {
		if (couponPlan != null && couponPlan.getAvailableDays() != null) {
			couponPlan.setAvailableTime1(ECDateUtils.getCurrentDateTime());
			Date vailableTime2 = ECDateUtils.getDateAfter(couponPlan.getAvailableTime1(),
					couponPlan.getAvailableDays());
			couponPlan.setAvailableTime2(vailableTime2);
		}
		if (couponPlan.getCouponMethod() == 3) {
			couponPlan.setFreeFitnessTime("1");
		} else if (couponPlan.getCouponMethod() == 4) {
			couponPlan.setFreeCourseNumber("1");
		}
		return couponPlanService.addCouponPlan(couponPlan, getOperator());
	}

	/**
	 * 优惠卷修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEditCouponPlan")
	public String toEditCouponPlan(String planNo, ModelMap modelMap) {
		CouponPlan couponPlan = couponPlanService.getCouponPlan(planNo);
		modelMap.put("couponPlan", couponPlan);
		return "marketing/couponPlan_edit";
	}

	/**
	 * 优惠卷修改
	 * 
	 * @return
	 */
	@RequestMapping("/updateForBusiness")
	@ResponseBody
	public ResultInfo<CouponPlan> updateForBusiness(@ModelAttribute("couponPlan") CouponPlan couponPlan) {
		if (couponPlan != null && couponPlan.getAvailableDays() != null) {
			couponPlan.setAvailableTime1(ECDateUtils.getCurrentDateTime());
			Date vailableTime2 = ECDateUtils.getDateAfter(couponPlan.getAvailableTime1(),
					couponPlan.getAvailableDays());
			couponPlan.setAvailableTime2(vailableTime2);
		}
		if (couponPlan.getCouponMethod() == 3) {
			couponPlan.setFreeFitnessTime("1");
		} else if (couponPlan.getCouponMethod() == 4) {
			couponPlan.setFreeCourseNumber("1");
		}
		if (couponPlan.getCensorStatus() == 2) {
			couponPlan.setCensorStatus(0);
		}

		return couponPlanService.updateForBusiness(couponPlan, getOperator());
	}

	/**
	 * 优惠卷审核页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCensorCouponPlan")
	public String toCensorCouponPlan(String planNo, ModelMap modelMap) {
		CouponPlan couponPlan = couponPlanService.getCouponPlan(planNo);
		if (couponPlan != null) {
			if (couponPlan.getCityName() != null) {
				couponPlan.setCityName(couponPlan.getCityName().replaceAll(",", ", "));
			}
		}
		modelMap.put("couponPlan", couponPlan);
		return "marketing/couponPlan_censor";
	}

	/**
	 * 优惠卷启动、审核
	 * 
	 * @return
	 */
	@RequestMapping("/updateCouponPlan")
	@ResponseBody
	public ResultInfo<CouponPlan> updateCouponPlan(@ModelAttribute("coupon") CouponPlan couponPlan) {
		if (couponPlan.getCensorStatus() != null) {
			// 设置审核完成的时间和审核人
			Date now = new Date();
			couponPlan.setCensorTime(now);
			if (this.getOperator() != null) {
				couponPlan.setCensorId(this.getOperator().getOperatorId());
			}
			// 审核完成后默认为启用状态
			if (couponPlan.getCensorStatus() == 1) {
				couponPlan.setIsAvailable(1);
			} else {
				couponPlan.setIsAvailable(0);
			}

			couponPlan.setAvailableUpdateTime(now);
		}

		return couponPlanService.update(couponPlan, getOperator());
	}

	/**
	 * 优惠卷删除
	 * 
	 * @return
	 */
	@RequestMapping("/deleteCouponPlan")
	@ResponseBody
	public ResultInfo<CouponPlan> deleteCouponPlan(@ModelAttribute("coupon") CouponPlan couponPlan) {
		return couponPlanService.delCouponPlan(couponPlan.getPlanNo(), getOperator());
	}
}
