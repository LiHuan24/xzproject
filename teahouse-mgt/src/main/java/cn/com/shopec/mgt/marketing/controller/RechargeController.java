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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.Recharge;
import cn.com.shopec.core.finace.service.RechargeService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 充值包
 */
@Controller
@RequestMapping("recharge")
public class RechargeController extends BaseController {
	@Resource
	private RechargeService rechargeService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 跳转列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toRechargeList")
	public String toRechargeList() {
		return "marketing/recharge_list";
	}

	/**
	 * 列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/pageListRecharge")
	@ResponseBody
	public PageFinder<Recharge> pageListRecharge(@ModelAttribute("recharge") Recharge recharge, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), recharge);
		return rechargeService.getRechargePagedList(q);
	}

	/**
	 * 跳转详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toRechargeView")
	public String toRechargeView(String rechargeNo, ModelMap modelMap) {
		Recharge recharge = rechargeService.getRecharge(rechargeNo);
		modelMap.put("recharge", recharge);
		if (recharge.getCencorId() != null) {
			SysUser sysUser = sysUserService.detail(recharge.getCencorId());
			if (sysUser != null) {
				modelMap.put("cencor", sysUser.getRealName());
			}
		}
		return "marketing/recharge_view";
	}

	/**
	 * 跳转新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddRecharge")
	public String toAddRecharge(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "marketing/recharge_add";
	}

	/**
	 * 新增充值卡校验
	 * 
	 * @return
	 */
	@RequestMapping("/checkRecharge")
	@ResponseBody
	public ResultInfo<String> checkRecharge(Recharge recharge) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		List<Recharge> ls = rechargeService.getRechargeList(new Query(recharge));
		if (ls.size() > 0) {
			resultInfo.setCode("1");
		} else {
			resultInfo.setCode("0");
		}

		return resultInfo;
	}

	/**
	 * 编辑健身卡校验
	 * 
	 * @return
	 */
	@RequestMapping("/checkRechargeUp")
	@ResponseBody
	public ResultInfo<String> checkRechargeUp(String rechargeNo, String cityId, Double price) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		Recharge g = rechargeService.getRecharge(rechargeNo);
		if (g.getPrice().equals(price)) {
			resultInfo.setCode("0");
		} else {
			Recharge r = new Recharge();
			r.setCityId(cityId);
			r.setPrice(price);
			List<Recharge> ls = rechargeService.getRechargeList(new Query(r));
			if (ls.size() > 0) {
				resultInfo.setCode("1");
			} else {
				resultInfo.setCode("0");
			}
		}

		return resultInfo;
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	@RequestMapping("/addRecharge")
	@ResponseBody
	public ResultInfo<Recharge> addRecharge(@ModelAttribute("recharge") Recharge recharge) {
		if (recharge.getCityId() != null && recharge.getCityId().length() != 0) {
			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(recharge.getCityId());
			if (dataDictItem != null) {
				recharge.setCityName(dataDictItem.getItemValue());
			}
		}
		return rechargeService.addRecharge(recharge, getOperator());
	}

	/**
	 * 跳转修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEditRecharge")
	public String toEditRecharge(String rechargeNo, ModelMap modelMap) {
		Recharge recharge = rechargeService.getRecharge(rechargeNo);
		modelMap.put("recharge", recharge);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "marketing/recharge_edit";
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping("/updateRecharge")
	@ResponseBody
	public ResultInfo<Recharge> updateRecharge(@ModelAttribute("recharge") Recharge recharge) {
		if (recharge.getCityId() != null && recharge.getCityId().length() != 0) {
			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(recharge.getCityId());
			if (dataDictItem != null) {
				recharge.setCityName(dataDictItem.getItemValue());
			}
		}
		Operator operator = this.getOperator();
		if (recharge.getCencorStatus() != null && recharge.getCencorStatus() == 2) {
			recharge.setCencorStatus(0);
		}
		return rechargeService.updateRecharge(recharge, operator);
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping("/updateRechargeNs")
	@ResponseBody
	public ResultInfo<Recharge> updateRechargeNs(@ModelAttribute("recharge") Recharge recharge) {

		Operator operator = this.getOperator();
		if (operator != null && recharge.getCencorStatus() != null) {
			recharge.setCencorTime(new Date());
			recharge.setCencorId(operator.getOperatorId());
		}
		return rechargeService.updateRecharge(recharge, operator);
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/deleteRecharge")
	@ResponseBody
	public ResultInfo<Recharge> deleteRecharge(@ModelAttribute("recharge") Recharge recharge) {
		return rechargeService.delRecharge(recharge.getRechargeNo(), getOperator());
	}

}
