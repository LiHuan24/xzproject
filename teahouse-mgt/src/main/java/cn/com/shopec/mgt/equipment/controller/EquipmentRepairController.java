package cn.com.shopec.mgt.equipment.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
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
import cn.com.shopec.core.equipment.model.EquipmentCategory;
import cn.com.shopec.core.equipment.model.EquipmentRepair;
import cn.com.shopec.core.equipment.model.FitnessEquipment;
import cn.com.shopec.core.equipment.service.EquipmentCategoryService;
import cn.com.shopec.core.equipment.service.EquipmentRepairService;
import cn.com.shopec.core.equipment.service.FitnessEquipmentService;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 共享健身房：设备维修记录控制层
 * 
 * @author LiHuan Date 2017年10月30日 下午2:03:55
 */
@Controller
@RequestMapping("equipmentRepair")
public class EquipmentRepairController extends BaseController {

	private static final Log log = LogFactory.getLog(EquipmentRepairController.class);

	@Resource
	private EquipmentRepairService equipmentRepairService;
	@Resource
	private StoreService storeService;
	@Resource
	private FitnessEquipmentService fitnessEquipmentService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private EquipmentCategoryService equipmentCategoryService;
	@Value("${res_img_path}")
	private String resImgPath;

	/**
	 * 加载设备维修记录列表页
	 */
	@RequestMapping("toEquipmentRepairList")
	public String toEquipmentRepairList(ModelMap model) {
		log.info("加载设备维修记录列表");
		// 数据字典项----维修类型
		List<DataDictItem> maintenanceTypes = dataDictItemService.getDataDictItemListByCatCode("MAINTENANCE_TYPES");
		model.put("maintenanceTypes", maintenanceTypes);
		return "/equipment/equipment_repair_list";
	}

	/**
	 * 设备维修记录分页查询列表
	 * 
	 * @param equipmentRepair
	 * @param pageSize
	 * @param pageNo
	 */
	@RequestMapping("equipmentRepairPageList")
	@ResponseBody
	public PageFinder<EquipmentRepair> equipmentRepairPageList(
			@ModelAttribute("equipmentRepair") EquipmentRepair equipmentRepair, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		log.info("获取设备维修记录列表分页数据");
		Query q = new Query(pageNo, pageSize, equipmentRepair);
		return equipmentRepairService.pageEquipmentRepairList(q);
	}

	/**
	 * 进入设备维修记录新增页
	 */
	@RequestMapping("toEquipmentRepairAdd")
	public String toEquipmentRepairAdd(ModelMap model) {
		log.info("进入设备维修记录新增页");
		// 数据字典项：获取城市
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			model.put("cities", cities);
		}
		// 数据字典项：维修类型
		List<DataDictItem> maintenanceTypes = dataDictItemService.getDataDictItemListByCatCode("MAINTENANCE_TYPES");
		if (maintenanceTypes != null && maintenanceTypes.size() > 0) {
			model.put("maintenanceTypes", maintenanceTypes);
		}
		return "/equipment/equipment_repair_add";
	}
	
	/**
	 * 进入设备维修记录编辑页
	 */
	@RequestMapping("toEquipmentRepairEdit")
	public String toEquipmentRepairEdit(String equipmentRepairNo, ModelMap model){
		log.info("进入设备维修记录编辑页");
		EquipmentRepair equipmentRepair = equipmentRepairService.getEquipmentRepair(equipmentRepairNo);
		model.put("equipmentRepair", equipmentRepair);
		//数据字典项：获取城市
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			model.put("cities", cities);
		}
		// 数据字典项：维修类型
		List<DataDictItem> maintenanceTypes = dataDictItemService.getDataDictItemListByCatCode("MAINTENANCE_TYPES");
		if (maintenanceTypes != null && maintenanceTypes.size() > 0) {
			model.put("maintenanceTypes", maintenanceTypes);
		}
		//门店
		Store store = new Store();
		store.setCityId(equipmentRepair.getCityId());
		List<Store> listStore = storeService.getStoreList(new Query(store));
		if(listStore != null && listStore.size()>0){
			model.put("listStore", listStore);
		}
		//门店下的设备
		FitnessEquipment fitnessEquipment = new FitnessEquipment();
		fitnessEquipment.setStoreNo(equipmentRepair.getStoreNo());
		List<FitnessEquipment> listFitness = fitnessEquipmentService.getFitnessEquipmentList(new Query(fitnessEquipment));
		if(listFitness != null && listFitness.size()>0){
			model.put("listFitness", listFitness);
		}
		//设备类型
		EquipmentCategory equipmentCategory = new EquipmentCategory();
		equipmentCategory.setSortType(EquipmentConstant.FITNESS_EQUIPMENT);
		List<EquipmentCategory> listSort = equipmentCategoryService.getEquipmentCategoryList(new Query(equipmentCategory));
		if(listSort != null && listSort.size() > 0){
			model.put("listSort", listSort);
		}
		return "/equipment/equipment_repair_edit";
	}

	/**
	 * 进入设备维修记录详情页
	 */
	@RequestMapping("toEquipmentRepairView")
	public String toEquipmentRepairView(String equipmentRepairNo, ModelMap model) {
		log.info("进入设备维修记录详情页");
		EquipmentRepair equipmentRepair = equipmentRepairService.getEquipmentRepair(equipmentRepairNo);
		model.put("equipmentRepair", equipmentRepair);
		return "/equipment/equipment_repair_view";
	}
	
	/**
	 * 新增或编辑设备维修记录数据
	 */
	@RequestMapping("/saveOrUpdateEquipmentRepair")
	@ResponseBody
	public ResultInfo<EquipmentRepair> saveOrUpdateEquipmentRepair(
			@ModelAttribute("equipmentRepair") EquipmentRepair equipmentRepair) {
		ResultInfo<EquipmentRepair> resultInfo = new ResultInfo<>();
		// set城市、门店、设备和设备类型名称、维修类型名称
		DataDictItem dataDictItemCity = dataDictItemService.getDataDictItem(equipmentRepair.getCityId());
		if (dataDictItemCity != null) {
			equipmentRepair.setCityName(dataDictItemCity.getItemValue());
		}
		Store store = storeService.getStore(equipmentRepair.getStoreNo());
		if (store != null) {
			equipmentRepair.setStoreName(store.getStoreName());
		}
		FitnessEquipment fitnessEquipment = fitnessEquipmentService.getFitnessEquipment(equipmentRepair.getFitnessEquipmentNo());
		if (fitnessEquipment != null) {
			equipmentRepair.setSortName(fitnessEquipment.getSortName());
		}
		DataDictItem dataDictItemRepair = dataDictItemService.getDataDictItem(equipmentRepair.getRepairId());
		if (dataDictItemRepair != null) {
			equipmentRepair.setRepairTypeName(dataDictItemRepair.getItemValue());
		}
		if (null == equipmentRepair.getEquipmentRepairNo()) {
			// 新增
			resultInfo = equipmentRepairService.addEquipmentRepair(equipmentRepair, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		} else {
			// 编辑
			resultInfo = equipmentRepairService.updateEquipmentRepair(equipmentRepair, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		}
	}

	/**
	 * 根据城市ID查询社区馆数据
	 */
	@RequestMapping("getStoreByCityId")
	@ResponseBody
	public ResultInfo<List<Store>> getStoreByCityId(String cityId){
		ResultInfo<List<Store>> resultInfo = new ResultInfo<List<Store>>();
		if(StringUtils.isNotBlank(cityId)){
			Store store = new Store();
			store.setCityId(cityId);
			store.setStoreType(0);//设备维修记录只给社区馆增加
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
	 * 根据门店选择设备、根据设备编号获取该健身设备对应的设备类型
	 */
	@RequestMapping("getEquipmentByStoreNo")
	@ResponseBody
	public ResultInfo<List<FitnessEquipment>> getEquipmentByStoreNo(String storeNo, String fitnessEquipmentNo) {
		ResultInfo<List<FitnessEquipment>> resultInfo = new ResultInfo<List<FitnessEquipment>>();
		if (StringUtils.isNotBlank(storeNo)) {
			FitnessEquipment fitnessEquipment = new FitnessEquipment();
			fitnessEquipment.setStoreNo(storeNo);
			List<FitnessEquipment> listFitnessEquipment = fitnessEquipmentService.getFitnessEquipmentList(new Query(fitnessEquipment));
			if (listFitnessEquipment != null && listFitnessEquipment.size() > 0) {
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(listFitnessEquipment);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("该门店下暂无健身设备！");
			}
		} else if (StringUtils.isNotBlank(fitnessEquipmentNo)) {
				FitnessEquipment fitnessEquipment = new FitnessEquipment();
				fitnessEquipment.setFitnessEquipmentNo(fitnessEquipmentNo);
				List<FitnessEquipment> listFitnessEquipment = fitnessEquipmentService.getFitnessEquipmentList(new Query(fitnessEquipment));
				if (listFitnessEquipment != null && listFitnessEquipment.size() > 0) {
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(listFitnessEquipment);
				}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择门店！");
		}
		return resultInfo;
	}
}