package cn.com.shopec.mgt.equipment.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.EquipmentCategory;
import cn.com.shopec.core.equipment.service.EquipmentCategoryService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 共享健身房-设备分类控制层
 * 
 * @author LiHuan Date 2017年10月13日 下午2:34:55
 */
@Controller
@RequestMapping("/equipmentCategory")
public class EquipmentCategoryController extends BaseController {

	private static final Log log = LogFactory.getLog(EquipmentCategoryController.class);

	@Resource
	private EquipmentCategoryService equipmentCategoryService;

	/**
	 * 进入设备分类列表
	 */
	@RequestMapping("toEquipmentCategoryList")
	public String toEquipmentCategoryList() {
		log.info("加载设备分类列表");
		return "/equipment/equipment_category_list";
	}

	/**
	 * 设备分类分页列表
	 */
	@RequestMapping("equipmentCategoryPageList")
	@ResponseBody
	public PageFinder<EquipmentCategory> equipmentCategoryPageList(
			@ModelAttribute("equipmentCategory") EquipmentCategory equipmentCategory,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		log.info("获取设备分类列表分页数据");
		Query q = new Query(pageNo, pageSize, equipmentCategory);
		return equipmentCategoryService.getEquipmentCategoryPagedList(q);
	}

	/**
	 * 进入设备分类新增页面
	 */
	@RequestMapping("toEquipmentCategoryAdd")
	public String toEquipmentCategoryAdd(ModelMap model) {
		return "/equipment/equipment_category_add";
	}

	/**
	 * 进入设备分类编辑页面
	 */
	@RequestMapping("toEquipmentCategoryEdit")
	public String toEquipmentCategoryEdit(String sortNo, ModelMap model) {
		EquipmentCategory equipmentCategory = equipmentCategoryService.getEquipmentCategory(sortNo);
		model.put("equipmentCategory", equipmentCategory);
		return "/equipment/equipment_category_edit";
	}

	/**
	 * 设备分类保存、更新
	 */
	@RequestMapping("saveOrUpdateEquipmentCategory")
	@ResponseBody
	public ResultInfo<EquipmentCategory> saveOrUpdateEquipmentCategory(
			@ModelAttribute("equipmentCategory") EquipmentCategory equipmentCategory) {
		ResultInfo<EquipmentCategory> resultInfo = new ResultInfo<EquipmentCategory>();
		if (null == equipmentCategory.getSortNo()) {
			// 新增
			resultInfo = equipmentCategoryService.addEquipmentCategory(equipmentCategory, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		} else {
			// 编辑
			resultInfo = equipmentCategoryService.updateEquipmentCategory(equipmentCategory, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		}
	}

	/**
	 * 进入设备分类查看详情页
	 */
	@RequestMapping("toEquipmentCategoryView")
	public String toEquipmentCategoryView(String sortNo, ModelMap model) {
		EquipmentCategory equipmentCategory = equipmentCategoryService.getEquipmentCategory(sortNo);
		model.put("equipmentCategory", equipmentCategory);
		return "/equipment/equipment_category_view";
	}

	/**
	 * 设备分类删除
	 */
	@RequestMapping("deleteEquipmentCategory")
	@ResponseBody
	public ResultInfo<EquipmentCategory> deleteEquipmentCategory(@RequestParam("sortNo") String sortNo) {
		ResultInfo<EquipmentCategory> resultInfo = new ResultInfo<EquipmentCategory>();
		resultInfo = equipmentCategoryService.delEquipmentCategory(sortNo, getOperator());
		resultInfo.setCode(Constant.SECCUESS);
		return resultInfo;
	}

	/**
	 * 新增校验设备名称
	 */
	@RequestMapping("checkSortName")
	@ResponseBody
	public ResultInfo<String> checkSortName(EquipmentCategory e) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		List<EquipmentCategory> ls = equipmentCategoryService.getEquipmentCategoryList(new Query(e));
		if (ls.size() > 0) {
			resultInfo.setCode("1");
		} else {
			resultInfo.setCode("0");
		}

		return resultInfo;
	}

	/**
	 * 编辑校验设备名称
	 */
	@RequestMapping("checkSortNameUp")
	@ResponseBody
	public ResultInfo<String> checkSortNameUp(String sortNo, Integer sortType, String sortName) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		EquipmentCategory ey = equipmentCategoryService.getEquipmentCategory(sortNo);
		if (ey.getSortType().equals(sortType) && ey.getSortName().equals(sortName)) {
			resultInfo.setCode("0");
		} else {
			EquipmentCategory e = new EquipmentCategory();
			e.setSortType(sortType);
			e.setSortName(sortName);
			List<EquipmentCategory> ls = equipmentCategoryService.getEquipmentCategoryList(new Query(e));
			if (ls.size() > 0) {
				resultInfo.setCode("1");
			} else {
				resultInfo.setCode("0");
			}
		}

		return resultInfo;
	}

}
