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
import cn.com.shopec.core.equipment.model.AuxiliaryEquipment;
import cn.com.shopec.core.equipment.model.EquipmentBrand;
import cn.com.shopec.core.equipment.model.EquipmentCategory;
import cn.com.shopec.core.equipment.model.EquipmentModel;
import cn.com.shopec.core.equipment.service.AuxiliaryEquipmentService;
import cn.com.shopec.core.equipment.service.EquipmentBrandService;
import cn.com.shopec.core.equipment.service.EquipmentCategoryService;
import cn.com.shopec.core.equipment.service.EquipmentModelService;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 共享健身房-辅助设备控制层
 * @author LiHuan
 * Date 2017年10月24日 上午10:17:44
 */
@Controller
@RequestMapping("/auxiliaryEquipment")
public class AuxiliaryEquipmentController extends BaseController {
	
	private static final Log log = LogFactory.getLog(AuxiliaryEquipmentController.class);

	@Resource
	private AuxiliaryEquipmentService auxiliaryEquipmentService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private EquipmentBrandService equipmentBrandService;
	@Resource
	private StoreService storeService;
	@Resource
	private EquipmentModelService equipmentModelService;
	@Resource
	private EquipmentCategoryService equipmentCategoryService;
	
	/**
	 * 进入辅助设备列表
	 */
	@RequestMapping("toAuxiliaryEquipmentList")
	public String toAuxiliaryEquipmentList(ModelMap model){
		log.info("加载辅助设备列表");
		//查询所有设备类型
		EquipmentCategory equipmentCategory = new EquipmentCategory();
		equipmentCategory.setSortType(1);
		List<EquipmentCategory> listEquipmentCategory = equipmentCategoryService.getEquipmentCategoryList(new Query(equipmentCategory));
		if(listEquipmentCategory != null && listEquipmentCategory.size()>0){
			model.put("listEquipmentCategory", listEquipmentCategory);
		}
		return "/equipment/auxiliary_equipment_list";
	}
	
	/**
	 * 获取辅助设备分页列表
	 * @param auxiliaryEquipment
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("auxiliaryEquipmentPageList")
	@ResponseBody
	public PageFinder<AuxiliaryEquipment> auxiliaryEquipmentPageList(@ModelAttribute("auxiliaryEquipment") AuxiliaryEquipment auxiliaryEquipment, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize){
		log.info("获取辅助设备列表分页数据");
		Query q = new Query(pageNo, pageSize, auxiliaryEquipment);
		return auxiliaryEquipmentService.getAuxiliaryEquipmentPagedList(q);
	}
	
	/**
	 * 加载辅助设备新增页
	 */
	@RequestMapping("toAuxiliaryEquipmentAdd")
	public String toAuxiliaryEquipmentAdd(ModelMap model){
		log.info("进入辅助设备新增页");
		//获取城市
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			model.put("cities", cities);
		}
		//获取设备品牌为辅助类型的数据
		EquipmentBrand equipmentBrand = new EquipmentBrand();
		equipmentBrand.setBrandType(EquipmentConstant.ASSIST_EQUIPMENT);
		List<EquipmentBrand> listBrand = equipmentBrandService.getEquipmentBrandList(new Query(equipmentBrand));
		if(listBrand != null && listBrand.size()>0){
			model.put("listBrand", listBrand);
		}
		return "/equipment/auxiliary_equipment_add";
	}
	
	/**
	 * 加载辅助设备编辑页
	 */
	@RequestMapping("toAuxiliaryEquipmentEdit")
	public String toAuxiliaryEquipmentEdit(ModelMap model,String auxiliaryEquipmentNo){
		log.info("进入辅助设备编辑页");
		AuxiliaryEquipment auxiliaryEquipment = auxiliaryEquipmentService.getAuxiliaryEquipment(auxiliaryEquipmentNo);
		model.put("auxiliaryEquipment", auxiliaryEquipment);
		//获取城市
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			model.put("cities", cities);
		}
		//默认获取该辅助设备所选城市下的所有门店
		Store store = new Store();
		store.setStoreStatus(Constant.STORE_START_STATUS);//已启动
		store.setCityId(auxiliaryEquipment.getCityId());
		List<Store> listStore = storeService.getStoreList(new Query(store));
		if(listStore != null && listStore.size()>0){
			model.put("listStore", listStore);
		}
		//获取设备品牌为辅助类型的数据
		EquipmentBrand equipmentBrand = new EquipmentBrand();
		equipmentBrand.setBrandType(EquipmentConstant.ASSIST_EQUIPMENT);
		List<EquipmentBrand> listBrand = equipmentBrandService.getEquipmentBrandList(new Query(equipmentBrand));
		if(listBrand != null && listBrand.size()>0){
			model.put("listBrand", listBrand);
		}
		//默认获取该辅助设备已选的设备型号和设备分类
		EquipmentModel equipmentModel = new EquipmentModel();
		equipmentModel.setBrandNo(auxiliaryEquipment.getBrandNo());
		List<EquipmentModel> listEquipmentModel = equipmentModelService.getEquipmentModelList(new Query(equipmentModel));
		if(listEquipmentModel != null && listEquipmentModel.size()>0){
			model.put("listEquipmentModel", listEquipmentModel);
		}
		EquipmentCategory equipmentCategory = new EquipmentCategory();
		equipmentCategory.setSortNo(auxiliaryEquipment.getSortNo());
		List<EquipmentCategory> listEquipmentCategory = equipmentCategoryService.getEquipmentCategoryList(new Query(equipmentCategory));
		if(listEquipmentCategory != null && listEquipmentCategory.size() > 0){
			model.put("listEquipmentCategory", listEquipmentCategory);
		}
		return "/equipment/auxiliary_equipment_edit";
	}
	
	/**
	 * 加载辅助设备详情页
	 */
	@RequestMapping("toAuxiliaryEquipmentView")
	public String toAuxiliaryEquipmentView(ModelMap model,String auxiliaryEquipmentNo){
		log.info("加载辅助设备详情页");
		AuxiliaryEquipment auxiliaryEquipment = auxiliaryEquipmentService.getAuxiliaryEquipment(auxiliaryEquipmentNo);
		model.put("auxiliaryEquipment", auxiliaryEquipment);
		return "/equipment/auxiliary_equipment_view";
	}
	
	/**
	 * 根据所选城市获取已经启用的门店数据
	 * one to many 一对多
	 * @param cityId 城市ID
	 */
	@RequestMapping("getStoreByCityId")
	@ResponseBody
	public ResultInfo<List<Store>> getStoreByCityId(String cityId){
		ResultInfo<List<Store>> resultInfo = new ResultInfo<List<Store>>();
		if(StringUtils.isNotBlank(cityId)){
			Store store = new Store();
			store.setCityId(cityId);
			store.setStoreStatus(Constant.STORE_START_STATUS);
			List<Store> listStore = storeService.getStoreList(new Query(store));
			if(listStore != null && listStore.size()>0){
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(listStore);
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("查询该城市下暂无门店");
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择城市!");
		}
		return resultInfo;
	}
	
	/**
	 * 根据所选的辅助设备品牌获取该品牌下的品牌型号
	 * one to many
	 * @param brandId 设备品牌ID
	 */
	@RequestMapping("getModelByBrandId")
	@ResponseBody
	public ResultInfo<List<EquipmentModel>> getModelByBrandId(String brandId){
		ResultInfo<List<EquipmentModel>> resultInfo = new ResultInfo<List<EquipmentModel>>();
		if(StringUtils.isNotBlank(brandId)){
			EquipmentModel equipmentModel = new EquipmentModel();
			equipmentModel.setBrandNo(brandId);
			List<EquipmentModel> listEquipmentModel = equipmentModelService.getEquipmentModelList(new Query(equipmentModel));
			if(listEquipmentModel != null && listEquipmentModel.size()>0){
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(listEquipmentModel);
			}else{
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("查询该设备品牌下暂无设备型号");
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择设备品牌!");
		}
		return resultInfo;
	}
	
	/**
	 * 根据所选型号获取该设备型号的分类数据
	 * one to one
	 * @param modelNo 型号ID
	 */
	@RequestMapping("getEquipmentCategoryByModelId")
	@ResponseBody
	public ResultInfo<List<EquipmentCategory>> getEquipmentCategoryByModelId(String modelId){
		ResultInfo<List<EquipmentCategory>> resultInfo = new ResultInfo<List<EquipmentCategory>>();
		if(StringUtils.isNotBlank(modelId)){
			EquipmentModel equipmentModel = equipmentModelService.getEquipmentModel(modelId);
			if(equipmentModel != null){
				EquipmentCategory equipmentCategory = new EquipmentCategory();
				equipmentCategory.setSortNo(equipmentModel.getSortNo());
				List<EquipmentCategory> listEquipmentCategory = equipmentCategoryService.getEquipmentCategoryList(new Query(equipmentCategory));
				if(listEquipmentCategory != null && listEquipmentCategory.size()>0){
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(listEquipmentCategory);
				}else{
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("该设备型号无对应的分类");
				}
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请先选择设备型号！");
		}
		return resultInfo;
	}
	
	
	/**
	 * 辅助设备新增或编辑
	 */
	@RequestMapping("saveOrUpdateAuxiliaryEquipment")
	@ResponseBody
	public ResultInfo<AuxiliaryEquipment> saveOrUpdateAuxiliaryEquipment(@ModelAttribute("auxiliaryEquipment") AuxiliaryEquipment auxiliaryEquipment) {
		ResultInfo<AuxiliaryEquipment> resultInfo = new ResultInfo<AuxiliaryEquipment>();
		//set保存城市、门店、品牌、型号、和分类名称
		DataDictItem dataDictItem = dataDictItemService.getDataDictItem(auxiliaryEquipment.getCityId());
		if(dataDictItem != null){
			auxiliaryEquipment.setCityName(dataDictItem.getItemValue());
		}
		Store store = storeService.getStore(auxiliaryEquipment.getStoreNo());
		if(store != null){
			auxiliaryEquipment.setStoreName(store.getStoreName());
		}
		EquipmentBrand equipmentBrand = equipmentBrandService.getEquipmentBrand(auxiliaryEquipment.getBrandNo());
		if(equipmentBrand != null){
			auxiliaryEquipment.setBrandName(equipmentBrand.getBrandName());
		}
		EquipmentModel equipmentModel = equipmentModelService.getEquipmentModel(auxiliaryEquipment.getModelNo());
		if(equipmentModel != null){
			auxiliaryEquipment.setModelName(equipmentModel.getModelName());
		}
		EquipmentCategory equipmentCategory = equipmentCategoryService.getEquipmentCategory(auxiliaryEquipment.getSortNo());
		if(equipmentCategory != null){
			auxiliaryEquipment.setSortName(equipmentCategory.getSortName());
		}
		if (null == auxiliaryEquipment.getAuxiliaryEquipmentNo()) {
			//新增
			resultInfo = auxiliaryEquipmentService.addAuxiliaryEquipment(auxiliaryEquipment, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		} else {
			//编辑
			resultInfo = auxiliaryEquipmentService.updateAuxiliaryEquipment(auxiliaryEquipment, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		}
	}
}
