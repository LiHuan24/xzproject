package cn.com.shopec.mgt.marketing.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.CustomPricingRule;
import cn.com.shopec.core.finace.service.CustomPricingRuleService;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 自定义计费规则
 */
@Controller
@RequestMapping("customPricingRule")
public class CustomPricingRuleController extends BaseController {
	@Resource
	private CustomPricingRuleService customPricingRuleService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
    private SysUserService sysUserService;
	
	/**
	 * 列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/pageListCustomPricingRule")
	@ResponseBody
	public PageFinder<CustomPricingRule> pageListCustomPricingRule(@ModelAttribute("customPricingRule") CustomPricingRule customPricingRule, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), customPricingRule);
		return customPricingRuleService.getCustomPricingRulePagedList(q);
	}
	
	/**
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping("/addorUpdateCustomPricingRule")
	@ResponseBody
	public ResultInfo<CustomPricingRule> addCustomPricingRule(@ModelAttribute("customPricingRule") CustomPricingRule customPricingRule,String customDateStr) {
		ResultInfo<CustomPricingRule> resultInfo = new ResultInfo<CustomPricingRule>();
		String[] dateArray = customDateStr.split(",");
		if(dateArray != null && dateArray.length > 0){
			for (String dateTemp : dateArray) {
				//不能新增或者修改当天日期的自定义规则
				String nowDate = ECDateUtils.getCurrentDateTimeAsString();//当前日期
				if(nowDate.equals(dateTemp)){
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("当天日期不能添加！");  
					return resultInfo;
				}
				
				//不能重复添加同一天的自定义规则日期
				CustomPricingRule customQuery = new CustomPricingRule();
				customQuery.setCustomDate(ECDateUtils.parseDate(dateTemp));
				customQuery.setRuleNo(customPricingRule.getRuleNo());
				List<CustomPricingRule> customList = customPricingRuleService.getCustomPricingRuleList(new Query(customQuery));
				
				if(customList!=null && customList.size()>0){
					if(customPricingRule.getCustomNo() == null || "".equals(customPricingRule.getCustomNo())){
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("["+dateTemp+"]已经添加，请勿重复添加！");  
						return resultInfo;
					}else{
						for (CustomPricingRule customTemp : customList) {
							if(!customPricingRule.getCustomNo().equals(customTemp.getCustomNo())){
								resultInfo.setCode(Constant.FAIL);
								resultInfo.setMsg("["+dateTemp+"]已经存在，无法进行修改！");  
								return resultInfo;
							}
						}
					}
				}
			}
			
			try {
				for (String dateTemp : dateArray) {
					//新增or修改
					if(customPricingRule.getCustomNo() == null || "".equals(customPricingRule.getCustomNo())){
							CustomPricingRule customAdd = new CustomPricingRule();
							customAdd.setCustomNo(customPricingRuleService.generatePK());
							customAdd.setCityId(customPricingRule.getCityId());
							customAdd.setCityName(customPricingRule.getCityName());
							customAdd.setRuleNo(customPricingRule.getRuleNo());
							customAdd.setRuleName(customPricingRule.getRuleName());
							customAdd.setHourPrice(customPricingRule.getHourPrice());
							customAdd.setCustomDate(ECDateUtils.parseDate(dateTemp));
							customPricingRuleService.addCustomPricingRule(customAdd, getOperator());
						
					}else{
						CustomPricingRule customUpdate = new CustomPricingRule();
						customUpdate.setCustomNo(customPricingRule.getCustomNo());
						customUpdate.setHourPrice(customPricingRule.getHourPrice());
						customUpdate.setCustomDate(ECDateUtils.parseDate(dateTemp));
						customPricingRuleService.updateCustomPricingRule(customUpdate, getOperator());
					}
				}
				resultInfo.setCode(Constant.SECCUESS);
				return resultInfo;
			} catch (Exception e) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("");  
				return resultInfo;
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择自定义日期！");  
			return resultInfo;
		}
	}

	
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/deleteCustomPricingRule")
	@ResponseBody
	public ResultInfo<CustomPricingRule> deleteCustomPricingRule(@ModelAttribute("customPricingRule") CustomPricingRule customPricingRule) {
		return customPricingRuleService.delCustomPricingRule(customPricingRule.getCustomNo(), getOperator());
	}
	
}
