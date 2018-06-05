package cn.com.shopec.mgt.marketing.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.JsonUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.model.RedeemCode;
import cn.com.shopec.core.marketing.model.RedeemCouponPlan;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.RedeemCodeService;
import cn.com.shopec.core.marketing.service.RedeemCouponPlanService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 兑换码
 */
@Controller
@RequestMapping("redeemCode")
public class RedeemCodeController extends BaseController {
	@Resource
	private RedeemCodeService redeemCodeService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private RedeemCouponPlanService redeemCouponPlanService;

	/**
	 * 跳转列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toRedeemCodeList")
	public String toRedeemCodeList() {
		return "marketing/redeemCode_list";
	}

	/**
	 * 列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/pageListRedeemCode")
	@ResponseBody
	public PageFinder<RedeemCode> pageListRedeemCode(@ModelAttribute("redeemCode") RedeemCode redeemCode, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), redeemCode);
		return redeemCodeService.getRedeemCodePagedList(q);
	}

	/**
	 * 跳转详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toRedeemCodeView")
	public String toRedeemCodeView(String redeemCode, ModelMap modelMap) {
		modelMap.put("redeemCode", redeemCodeService.getRedeemCode(redeemCode));
		return "marketing/redeemCode_view";
	}

	/**
	 * 跳转新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddRedeemCode")
	public String toAddRedeemCode(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "marketing/redeemCode_add";
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	@RequestMapping("/addRedeemCode")
	@ResponseBody
	public ResultInfo<RedeemCode> addRedeemCode(@ModelAttribute("redeemCode") RedeemCode redeemCode,
			String planString) {
		if (planString != null && !"".equals(planString)) {
			List<RedeemCouponPlan> redeemCouponPlans = JsonUtils.parse2ListObject(planString, RedeemCouponPlan.class);
			if (redeemCouponPlans != null) {
				for (RedeemCouponPlan rcp : redeemCouponPlans) {
					if (rcp.getPlanNo() != null) {// 判断优惠券方案限制数量和已经发放的数量
						CouponPlan couponPlan = couponPlanService.getCouponPlan(rcp.getPlanNo());
						if (couponPlan != null && couponPlan.getMaxQuantity() != null) {
							if (couponPlan.getExistingQuantity() == null) {
								couponPlan.setExistingQuantity(0);
							}
							Integer n = couponPlan.getMaxQuantity() - couponPlan.getExistingQuantity();
							if (n < rcp.getRedeemQutity().intValue()) {
								ResultInfo<RedeemCode> resultInfo = new ResultInfo<RedeemCode>();
								resultInfo.setCode(Constant.FAIL);
								resultInfo.setMsg(
										"优惠券方案[" + couponPlan.getTitle() + "]的兑换数量不能大于可发放的数量，当前可兑换数量为" + n + "！");
								return resultInfo;
							}

						}
					}
				}
			}
			redeemCode.setRedeemCouponPlans(redeemCouponPlans);
		}
		return redeemCodeService.addRedeemCode(redeemCode, getOperator());
	}

	/**
	 * 跳转修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEditRedeemCode")
	public String toEditRedeemCode(RedeemCode redeemCode, ModelMap modelMap) {
		RedeemCode redeemCodeData = redeemCodeService.getRedeemCode(redeemCode.getRedeemCode());
		modelMap.put("redeemCode", redeemCodeData);

		RedeemCouponPlan redeemCouponPlan = new RedeemCouponPlan();
		redeemCouponPlan.setRedeemCode(redeemCode.getRedeemCode());
		List<RedeemCouponPlan> redeemCouponPlans = redeemCouponPlanService
				.getRedeemCouponPlanList(new Query(redeemCouponPlan));
		if (redeemCouponPlans != null && !redeemCouponPlans.isEmpty()) {
			modelMap.put("redeemCouponPlans", JSON.toJSONString(redeemCouponPlans));
		} else {
			modelMap.put("redeemCouponPlans", "");
		}
		return "marketing/redeemCode_edit";
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping("/updateRedeemCode")
	@ResponseBody
	public ResultInfo<RedeemCode> updateRedeemCode(@ModelAttribute("redeemCode") RedeemCode redeemCode,
			String planString) {
		if (planString != null && !"".equals(planString)) {
			List<RedeemCouponPlan> redeemCouponPlans = JsonUtils.parse2ListObject(planString, RedeemCouponPlan.class);
			if (redeemCouponPlans != null) {
				for (RedeemCouponPlan rcp : redeemCouponPlans) {
					if (rcp.getPlanNo() != null) {// 判断优惠券方案限制数量和已经发放的数量
						CouponPlan couponPlan = couponPlanService.getCouponPlan(rcp.getPlanNo());
						if (couponPlan != null && couponPlan.getMaxQuantity() != null) {
							if (couponPlan.getExistingQuantity() == null) {
								couponPlan.setExistingQuantity(0);
							}
							Integer n = couponPlan.getMaxQuantity() - couponPlan.getExistingQuantity();
							if (n < rcp.getRedeemQutity().intValue()) {
								ResultInfo<RedeemCode> resultInfo = new ResultInfo<RedeemCode>();
								resultInfo.setCode(Constant.FAIL);
								resultInfo.setMsg(
										"优惠券方案[" + couponPlan.getTitle() + "]的兑换数量不能大于可发放的数量，当前可兑换数量为" + n + "！");
								return resultInfo;
							}

						}
					}
				}
			}
			redeemCode.setRedeemCouponPlans(redeemCouponPlans);
		}

		Operator operator = this.getOperator();
		if (operator != null && redeemCode.getCensorStatus() != null) {
			redeemCode.setCensorTime(new Date());
			redeemCode.setCensorId(operator.getOperatorId());
		}

		return redeemCodeService.updateRedeemCode(redeemCode, operator);
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/deleteRedeemCode")
	@ResponseBody
	public ResultInfo<RedeemCode> deleteRedeemCode(@ModelAttribute("redeemCode") RedeemCode redeemCode) {
		return redeemCodeService.delRedeemCode(redeemCode.getRedeemCode(), getOperator());
	}

	/**
	 * 兑换卷详情下的优惠卷列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/getPageForRedeemmCode")
	@ResponseBody
	public PageFinder<CouponPlan> getPageForRedeemmCode(String redeemCode, Query query) {
		PageFinder<CouponPlan> page = new PageFinder<CouponPlan>();
		List<CouponPlan> list = null;
		if (redeemCode != null && !redeemCode.equals("")) {
			list = redeemCodeService.getPageForRedeemmCode(redeemCode);
			page.setData(list);
			page.setRowCount(list.size());
		}
		if (list == null) {
			page.setData(new ArrayList<CouponPlan>());
			page.setRowCount(0);
		}
		return page;
	}

	/**
	 * 兑换码审核页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCensorRedeemCode")
	public String toCensorRedeemCode(String redeemCode, ModelMap modelMap) {
		modelMap.put("redeemCode", redeemCodeService.getRedeemCode(redeemCode));
		return "marketing/redeemCode_censor";
	}
}
