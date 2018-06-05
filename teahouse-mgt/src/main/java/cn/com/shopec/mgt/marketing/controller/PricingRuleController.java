package cn.com.shopec.mgt.marketing.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.PricingRule;
import cn.com.shopec.core.finace.service.PricingRuleService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 计费规则
 */
@Controller
@RequestMapping("pricingRule")
public class PricingRuleController extends BaseController {
	@Resource
	private PricingRuleService pricingRuleService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 跳转列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toPricingRuleList")
	public String toPricingRuleList() {
		return "marketing/pricingRule_list";
	}

	/**
	 * 列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/pageListPricingRule")
	@ResponseBody
	public PageFinder<PricingRule> pageListPricingRule(@ModelAttribute("pricingRule") PricingRule pricingRule,
			Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), pricingRule);
		return pricingRuleService.getPricingRulePagedList(q);
	}

	/**
	 * 跳转详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toPricingRuleView")
	public String toPricingRuleView(String ruleNo, ModelMap modelMap) {
		PricingRule pricingRule = pricingRuleService.getPricingRule(ruleNo);
		modelMap.put("pricingRule", pricingRule);
		if (pricingRule.getCencorId() != null) {
			SysUser sysUser = sysUserService.detail(pricingRule.getCencorId());
			if (sysUser != null) {
				modelMap.put("cencor", sysUser.getRealName());
			}
		}
		return "marketing/pricingRule_view";
	}

	/**
	 * 跳转新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddPricingRule")
	public String toAddPricingRule(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "marketing/pricingRule_add";
	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping("/addPricingRule")
	@ResponseBody
	public ResultInfo<PricingRule> addPricingRule(@ModelAttribute("pricingRule") PricingRule pricingRule) {
		if (pricingRule.getCityId() != null && pricingRule.getCityId().length() != 0) {

			PricingRule pricingRuleQuery = new PricingRule();
			pricingRuleQuery.setCityId(pricingRule.getCityId());
			List<PricingRule> pricingRuleList = pricingRuleService.getPricingRuleList(new Query(pricingRuleQuery));
			if (pricingRuleList != null && pricingRuleList.size() != 0) {
				ResultInfo<PricingRule> result = new ResultInfo<PricingRule>();
				result.setCode(Constant.FAIL);
				result.setMsg("该城市的计费规则已经添加！");
				return result;
			}

			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(pricingRule.getCityId());
			if (dataDictItem != null) {
				pricingRule.setCityName(dataDictItem.getItemValue());
			}
		}
		return pricingRuleService.addPricingRule(pricingRule, getOperator());
	}

	/**
	 * 跳转修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEditPricingRule")
	public String toEditPricingRule(String ruleNo, ModelMap modelMap) {
		PricingRule pricingRule = pricingRuleService.getPricingRule(ruleNo);
		modelMap.put("pricingRule", pricingRule);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "marketing/pricingRule_edit";
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping("/updatePricingRule")
	@ResponseBody
	public ResultInfo<PricingRule> updatePricingRule(@ModelAttribute("pricingRule") PricingRule pricingRule) {
		if (pricingRule.getCityId() != null && pricingRule.getCityId().length() != 0) {

			PricingRule pricingRuleQuery = new PricingRule();
			pricingRuleQuery.setCityId(pricingRule.getCityId());
			List<PricingRule> pricingRuleList = pricingRuleService.getPricingRuleList(new Query(pricingRuleQuery));
			if (pricingRuleList != null && pricingRuleList.size() != 0) {
				for (PricingRule temp : pricingRuleList) {
					if (!temp.getRuleNo().equals(pricingRule.getRuleNo())) {
						ResultInfo<PricingRule> result = new ResultInfo<PricingRule>();
						result.setCode(Constant.FAIL);
						result.setMsg("该城市的计费规则已经添加！");
						return result;
					}
				}
			}

			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(pricingRule.getCityId());
			if (dataDictItem != null) {
				pricingRule.setCityName(dataDictItem.getItemValue());
			}
		}
		Operator operator = this.getOperator();
		if (operator != null && pricingRule.getCencorStatus() != null) {
			pricingRule.setCencorTime(new Date());
			pricingRule.setCencorId(operator.getOperatorId());
		}
		if (pricingRule.getCencorStatus() != null && pricingRule.getCencorStatus() == 2) {
			pricingRule.setCencorStatus(0);
		}
		return pricingRuleService.updatePricingRule(pricingRule, operator);
	}

	/**
	 * 审核
	 * 
	 * @return
	 */
	@RequestMapping("/cencorPricingRule")
	@ResponseBody
	public ResultInfo<PricingRule> cencorPricingRule(@ModelAttribute("pricingRule") PricingRule pricingRule) {

		return pricingRuleService.updatePricingRule(pricingRule, this.getOperator());
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/deletePricingRule")
	@ResponseBody
	public ResultInfo<PricingRule> deletePricingRule(@ModelAttribute("pricingRule") PricingRule pricingRule) {
		return pricingRuleService.delPricingRule(pricingRule.getRuleNo(), getOperator());
	}

}
