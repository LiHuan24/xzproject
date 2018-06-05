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
import cn.com.shopec.core.finace.model.GymCard;
import cn.com.shopec.core.finace.service.GymCardService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 健身卡
 */
@Controller
@RequestMapping("gymCard")
public class GymCardController extends BaseController {
	@Resource
	private GymCardService gymCardService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 跳转列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toGymCardList")
	public String toGymCardList() {
		return "marketing/gymCard_list";
	}

	/**
	 * 列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/pageListGymCard")
	@ResponseBody
	public PageFinder<GymCard> pageListGymCard(@ModelAttribute("gymCard") GymCard gymCard, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), gymCard);
		return gymCardService.getGymCardPagedList(q);
	}

	/**
	 * 跳转详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toGymCardView")
	public String toGymCardView(String gymCardNo, ModelMap modelMap) {
		GymCard gymCard = gymCardService.getGymCard(gymCardNo);
		modelMap.put("gymCard", gymCard);
		if (gymCard.getCencorId() != null) {
			SysUser sysUser = sysUserService.detail(gymCard.getCencorId());
			if (sysUser != null) {
				modelMap.put("cencor", sysUser.getRealName());
			}
		}
		return "marketing/gymCard_view";
	}

	/**
	 * 跳转新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddGymCard")
	public String toAddGymCard(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "marketing/gymCard_add";
	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping("/addGymCard")
	@ResponseBody
	public ResultInfo<GymCard> addGymCard(@ModelAttribute("gymCard") GymCard gymCard) {

		if (gymCard.getGymCardType() == null) {// 目前业务只有包月的卡
			gymCard.setGymCardType(1);
		}
		if (gymCard.getCityId() != null && gymCard.getCityId().length() != 0) {
			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(gymCard.getCityId());
			if (dataDictItem != null) {
				gymCard.setCityName(dataDictItem.getItemValue());
			}
		}
		return gymCardService.addGymCard(gymCard, getOperator());
	}

	/**
	 * 跳转修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEditGymCard")
	public String toEditGymCard(String gymCardNo, ModelMap modelMap) {
		GymCard gymCard = gymCardService.getGymCard(gymCardNo);
		modelMap.put("gymCard", gymCard);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "marketing/gymCard_edit";
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping("/updateGymCard")
	@ResponseBody
	public ResultInfo<GymCard> updateGymCard(@ModelAttribute("gymCard") GymCard gymCard) {
		if (gymCard.getCityId() != null && gymCard.getCityId().length() != 0) {
			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(gymCard.getCityId());
			if (dataDictItem != null) {
				gymCard.setCityName(dataDictItem.getItemValue());
			}
		}

		if (gymCard.getCencorStatus() != null && gymCard.getCencorStatus() == 2) {
			gymCard.setCencorStatus(0);
		}
		return gymCardService.updateGymCard(gymCard, getOperator());
	}

	/**
	 * 审核
	 * 
	 * @return
	 */
	@RequestMapping("/updateGymCardNs")
	@ResponseBody
	public ResultInfo<GymCard> updateGymCardNs(@ModelAttribute("gymCard") GymCard gymCard) {
		Operator operator = this.getOperator();
		if (operator != null && gymCard.getCencorStatus() != null) {
			gymCard.setCencorTime(new Date());
			gymCard.setCencorId(operator.getOperatorId());
		}

		return gymCardService.updateGymCard(gymCard, operator);
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/deleteGymCard")
	@ResponseBody
	public ResultInfo<GymCard> deleteGymCard(@ModelAttribute("gymCard") GymCard gymCard) {
		return gymCardService.delGymCard(gymCard.getGymCardNo(), getOperator());
	}

	/**
	 * 新增健身卡校验
	 * 
	 * @return
	 */
	@RequestMapping("/checkGymCard")
	@ResponseBody
	public ResultInfo<String> checkGymCard(GymCard gymCard) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		List<GymCard> ls = gymCardService.getGymCardList(new Query(gymCard));
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
	@RequestMapping("/checkGymCardUp")
	@ResponseBody
	public ResultInfo<String> checkGymCardUp(String gymCardNo, String cityId) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		GymCard g = gymCardService.getGymCard(gymCardNo);
		if (g.getCityId().equals(cityId)) {
			resultInfo.setCode("0");
		} else {
			GymCard gymCard = new GymCard();
			gymCard.setCityId(cityId);
			List<GymCard> ls = gymCardService.getGymCardList(new Query(gymCard));
			if (ls.size() > 0) {
				resultInfo.setCode("1");
			} else {
				resultInfo.setCode("0");
			}
		}

		return resultInfo;
	}

}
