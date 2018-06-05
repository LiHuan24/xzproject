package cn.com.shopec.mgt.equipment.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import cn.com.shopec.core.equipment.common.EquipmentConstant;
import cn.com.shopec.core.equipment.model.EquipmentBrand;
import cn.com.shopec.core.equipment.model.EquipmentCategory;
import cn.com.shopec.core.equipment.model.EquipmentModel;
import cn.com.shopec.core.equipment.service.EquipmentBrandService;
import cn.com.shopec.core.equipment.service.EquipmentCategoryService;
import cn.com.shopec.core.equipment.service.EquipmentModelService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 共享健身房---设备型号控制层
 * 
 * @author LiHuan Date 2017年10月16日 下午4:16:43
 */
@Controller
@RequestMapping("/equipmentModel")
public class EquipmentModelController extends BaseController {

	private static final Log log = LogFactory.getLog(EquipmentModelController.class);

	@Resource
	private EquipmentModelService equipmentModelService;
	@Resource
	private EquipmentBrandService equipmentBrandService;
	@Resource
	private EquipmentCategoryService equipmentCategoryService;

	/**
	 * 进入设备型号列表
	 */
	@RequestMapping("toEquipmentModelList")
	public String toEquipmentModelList() {
		log.info("加载设备品牌列表");
		return "/equipment/equipment_model_list";
	}

	/**
	 * 设备型号分页列表
	 */
	@RequestMapping("equipmentModelPageList")
	@ResponseBody
	public PageFinder<EquipmentModel> equipmentModelPageList(
			@ModelAttribute("equipmentModel") EquipmentModel equipmentModel, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		log.info("获取设备型号列表分页数据");
		Query q = new Query(pageNo, pageSize, equipmentModel);
		return equipmentModelService.getEquipmentModelPagedList(q);
	}

	/**
	 * 进入设备型号新增页面
	 */
	@RequestMapping("toEquipmentModelAdd")
	public String toEquipmentModelAdd(ModelMap model) {
		// 获取设备品牌和设备类型
		EquipmentBrand equipmentBrand = new EquipmentBrand();
		//equipmentBrand.setBrandType(0);
		List<EquipmentBrand> listBrand = equipmentBrandService.getEquipmentBrandList(new Query(equipmentBrand));
		EquipmentCategory equipmentCategory = new EquipmentCategory();
		//equipmentCategory.setSortType(0);
		List<EquipmentCategory> listCategory = equipmentCategoryService.getEquipmentCategoryList(new Query(equipmentCategory));
		if (listBrand != null && listBrand.size() > 0) {
			model.put("listBrand", listBrand);
		}
		if (listCategory != null && listCategory.size() > 0) {
			model.put("listCategory", listCategory);
		}
		return "/equipment/equipment_model_add";
	}

	/**
	 * 进入设备型号编辑页面
	 */
	@RequestMapping("toEquipmentModelEdit")
	public String toEquipmentModelEdit(String modelNo, ModelMap model) {
		EquipmentModel equipmentModel = equipmentModelService.getEquipmentModel(modelNo);
		model.put("equipmentModel", equipmentModel);
		// 获取设备品牌和设备类型
		EquipmentBrand equipmentBrand = new EquipmentBrand();
		equipmentBrand.setBrandType(0);
		List<EquipmentBrand> listBrand = equipmentBrandService.getEquipmentBrandList(new Query(equipmentBrand));
		EquipmentCategory equipmentCategory = new EquipmentCategory();
		equipmentCategory.setSortType(0);
		List<EquipmentCategory> listCategory = equipmentCategoryService
				.getEquipmentCategoryList(new Query(equipmentCategory));
		if (listBrand != null && listBrand.size() > 0) {
			model.put("listBrand", listBrand);
		}
		if (listCategory != null && listCategory.size() > 0) {
			model.put("listCategory", listCategory);
		}
		return "/equipment/equipment_model_edit";
	}

	/**
	 * 设备型号新增或编辑
	 */
	@RequestMapping("saveOrUpdateEquipmentModel")
	@ResponseBody
	public ResultInfo<EquipmentModel> saveOrUpdateEquipmentModel(
			@ModelAttribute("equipmentModel") EquipmentModel equipmentModel) {
		ResultInfo<EquipmentModel> resultInfo = new ResultInfo<EquipmentModel>();
		// 查询设备名称
		if (StringUtils.isNotBlank(equipmentModel.getBrandNo())) {
			EquipmentBrand equipmentBrand = equipmentBrandService.getEquipmentBrand(equipmentModel.getBrandNo());
			equipmentModel.setBrandName(equipmentBrand.getBrandName());
		}
		if (equipmentModel.getModelPictureUrl1() != null && equipmentModel.getModelPictureUrl1().equals("")) {
			equipmentModel.setModelPictureUrl1(null);
		}
		if (null == equipmentModel.getModelNo()) {
			// 新增
			resultInfo = equipmentModelService.addEquipmentModel(equipmentModel, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		} else {
			// 编辑
			resultInfo = equipmentModelService.updateEquipmentModel(equipmentModel, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		}
	}

	/**
	 * 根据所选品牌去匹配该品牌对于的设备类型下的类型名称
	 */
	@RequestMapping("getEquipmentCategoryByBrandNo")
	@ResponseBody
	public ResultInfo<List<EquipmentCategory>> getEquipmentCategoryByBrandNo(String brandId) {
		ResultInfo<List<EquipmentCategory>> resultInfo = new ResultInfo<List<EquipmentCategory>>();
		if (StringUtils.isNotBlank(brandId)) {
			EquipmentBrand equipmentBrand = equipmentBrandService.getEquipmentBrand(brandId);
			if (equipmentBrand != null) {
				EquipmentCategory equipmentCategory = new EquipmentCategory();
				if (equipmentBrand.getBrandType() == EquipmentConstant.FITNESS_EQUIPMENT) {
					equipmentCategory.setSortType(EquipmentConstant.FITNESS_EQUIPMENT);// 健身器材类型
					List<EquipmentCategory> listEquipmentCategory = equipmentCategoryService
							.getEquipmentCategoryList(new Query(equipmentCategory));
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(listEquipmentCategory);
				} else {
					equipmentCategory.setSortType(EquipmentConstant.ASSIST_EQUIPMENT);// 辅助设备类型
					List<EquipmentCategory> listEquipmentCategory = equipmentCategoryService
							.getEquipmentCategoryList(new Query(equipmentCategory));
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(listEquipmentCategory);
				}
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("查询失败！");
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择设备品牌！");
		}

		return resultInfo;
	}

	/**
	 * 增加设备型号校验
	 */
	@RequestMapping("checkEquipmentModel")
	@ResponseBody
	public ResultInfo<String> checkEquipmentModel(EquipmentModel equipmentModel) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		List<EquipmentModel> ls = equipmentModelService.getEquipmentModelList(new Query(equipmentModel));
		if (ls.size() > 0) {
			resultInfo.setCode("1");
		} else {
			resultInfo.setCode("0");
		}

		return resultInfo;
	}

	/**
	 * 编辑设备型号校验
	 */
	@RequestMapping("checkEquipmentModelUp")
	@ResponseBody
	public ResultInfo<String> checkEquipmentModelUp(String modelNo, String brandNo, String sortNo, String modelName) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		EquipmentModel e = equipmentModelService.getEquipmentModel(modelNo);
		if (e.getBrandNo().equals(brandNo) && e.getSortNo().equals(sortNo) && e.getModelName().equals(modelName)) {
			resultInfo.setCode("0");
		} else {
			EquipmentModel equipmentModel = new EquipmentModel();
			equipmentModel.setBrandNo(brandNo);
			equipmentModel.setSortNo(sortNo);
			equipmentModel.setModelName(modelName);
			List<EquipmentModel> ls = equipmentModelService.getEquipmentModelList(new Query(equipmentModel));
			if (ls.size() > 0) {
				resultInfo.setCode("1");
			} else {
				resultInfo.setCode("0");
			}
		}

		return resultInfo;
	}

}