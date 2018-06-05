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
import cn.com.shopec.core.equipment.model.EquipmentBrand;
import cn.com.shopec.core.equipment.model.EquipmentCategory;
import cn.com.shopec.core.equipment.service.EquipmentBrandService;
import cn.com.shopec.core.equipment.service.EquipmentCategoryService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 共享健身房-设备品牌控制层
 * @author LiHuan
 * Date 2017-10-12
 */
@Controller
@RequestMapping("/equipmentBrand")
public class EquipmentBrandController extends BaseController{
	
	private static final Log log = LogFactory.getLog(EquipmentBrandController.class);
	
	@Resource
	private EquipmentBrandService equipmentBrandService;
	@Resource
	private EquipmentCategoryService equipmentCategoryService;
	
	/**
	 * 进入设备品牌列表
	 */
	@RequestMapping("toEquipmentBrandList")
	public String toEquipmentBrandList(){
		log.info("加载设备品牌列表");
		return "/equipment/equipment_brand_list";
	}
	
	/**
	 * 设备品牌分页列表
	 */
	@RequestMapping("equipmentBrandPageList")
	@ResponseBody
	public PageFinder<EquipmentBrand> equipmentBrandPageList(@ModelAttribute("equipmentBrand") EquipmentBrand equipmentBrand, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize){
		log.info("获取设备品牌列表分页数据");
		Query q = new Query(pageNo, pageSize, equipmentBrand);
		return equipmentBrandService.getEquipmentBrandPagedList(q);
	}
	
	/**
	 * 进入设备品牌新增页面
	 */
	@RequestMapping("toEquipmentBrandAdd")
	public String toEquipmentBrandAdd(ModelMap model) {
		//获取设备分类数据
		EquipmentCategory equipmentCategory = new EquipmentCategory();
		List<EquipmentCategory> listEquipmentCategory = equipmentCategoryService.getEquipmentCategoryList(new Query(equipmentCategory));
		model.put("listEquipmentCategory", listEquipmentCategory);
		return "/equipment/equipment_brand_add";
	}
	/**
	 * 进入设备品牌编辑页面
	 */
	@RequestMapping("toEquipmentBrandEdit")
	public String toEquipmentBrandEdit(String brandNo, ModelMap model) {
		EquipmentBrand equipmentBrand = equipmentBrandService.getEquipmentBrand(brandNo);
		model.put("equipmentBrand", equipmentBrand);
		//获取设备分类数据
		EquipmentCategory equipmentCategory = new EquipmentCategory();
		List<EquipmentCategory> listEquipmentCategory = equipmentCategoryService.getEquipmentCategoryList(new Query(equipmentCategory));
		model.put("listEquipmentCategory", listEquipmentCategory);
		return "/equipment/equipment_brand_edit";
	}
	
	/**
	 * 设备品牌新增或编辑更新
	 */
	@RequestMapping("saveOrUpdateEquipmentBrand")
	@ResponseBody
	public ResultInfo<EquipmentBrand> saveOrUpdateEquipmentBrand(@ModelAttribute("equipmentBrand") EquipmentBrand equipmentBrand) {
		ResultInfo<EquipmentBrand> resultInfo = new ResultInfo<EquipmentBrand>();
		if (null == equipmentBrand.getBrandNo()) {
			//新增
			resultInfo = equipmentBrandService.addEquipmentBrand(equipmentBrand, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		} else {
			//编辑
			resultInfo = equipmentBrandService.updateEquipmentBrand(equipmentBrand, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		}
	}
	
	/**
	 * 新增或编辑时验证设备品牌名称是否唯一
	 */
	@RequestMapping("uniqueBrandName")
	@ResponseBody
	public ResultInfo<EquipmentBrand> uniqueBrandName(String brandNo,String brandName){
		ResultInfo<EquipmentBrand> resultInfo = new ResultInfo<EquipmentBrand>();
		if(StringUtils.isBlank(brandNo)){
			//新增验证
			EquipmentBrand equipmentBrand = new EquipmentBrand();
			if(StringUtils.isNotBlank(brandName)){
				equipmentBrand.setBrandName(brandName);
				List<EquipmentBrand> listBrand = equipmentBrandService.getEquipmentBrandList(new Query(equipmentBrand));
				if(listBrand.size() > 0){
					resultInfo.setCode(Constant.SECCUESS);
				}
			}
		}else{
			//编辑验证
			List<EquipmentBrand> listEquipment = equipmentBrandService.queryUniqueBrandList(brandNo);
			if(listEquipment.size() > 0){
				for (EquipmentBrand equipmentBrand : listEquipment) {
					if(equipmentBrand.getBrandName().equals(brandName)){
						resultInfo.setCode(Constant.SECCUESS);
					}
				}
			}
		}
		return resultInfo;
	}
}