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
import cn.com.shopec.core.equipment.model.EquipmentOnlineLog;
import cn.com.shopec.core.equipment.model.FitnessEquipment;
import cn.com.shopec.core.equipment.service.EquipmentBrandService;
import cn.com.shopec.core.equipment.service.EquipmentCategoryService;
import cn.com.shopec.core.equipment.service.EquipmentModelService;
import cn.com.shopec.core.equipment.service.EquipmentOnlineLogService;
import cn.com.shopec.core.equipment.service.FitnessEquipmentService;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.themepavilion.common.ThemePavilionConstant;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 共享健身房-健身设备控制层
 * 
 * @author LiHuan Date 2017-10-12
 */
@Controller
@RequestMapping("fitnesseEquipment")
public class FitnesseEquipmentController extends BaseController {

	private static final Log log = LogFactory.getLog(FitnesseEquipmentController.class);

	@Resource
	private FitnessEquipmentService fitnessEquipmentService;
	@Resource
	private EquipmentBrandService equipmentBrandService;
	@Resource
	private EquipmentModelService equipmentModelService;
	@Resource
	private EquipmentCategoryService equipmentCategoryService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private StoreService storeService;
	@Resource
	private EquipmentOnlineLogService equipmentOnlineLogService;

	/**
	 * 健身设备列表
	 */
	@RequestMapping("toFitnesseEquipmentList")
	public String toFitnesseEquipmentList(ModelMap model) {
		log.info("加载健身设备列表");
		// 查询设备类型-分类
		EquipmentCategory equipmentCategory = new EquipmentCategory();
		equipmentCategory.setSortType(0);
		model.put("ec", equipmentCategoryService.getEquipmentCategoryList(new Query(equipmentCategory)));
		// 数据字典项：健身设备上下线理由
		List<DataDictItem> equipmentUpWhy = dataDictItemService.getDataDictItemListByCatCode("EQUIPMENT_UPWHY");// 健身设备上线理由
		List<DataDictItem> equipmentDownWhy = dataDictItemService.getDataDictItemListByCatCode("EQUIPMENT_DOWNWHY");// 健身设备下线理由
		model.put("equipmentUpWhy", equipmentUpWhy);
		model.put("equipmentDownWhy", equipmentDownWhy);
		return "/equipment/fitness_equipment_list";
	}

	/**
	 * 健身设备分页查询
	 * 
	 * @param fitnessEquipment
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("fitnessEquipmentPageList")
	@ResponseBody
	public PageFinder<FitnessEquipment> fitnessEquipmentPageList(
			@ModelAttribute("fitnessEquipment") FitnessEquipment fitnessEquipment, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		log.info("获取健身设备列表分页数据");
		Query q = new Query(pageNo, pageSize, fitnessEquipment);
		return fitnessEquipmentService.getFitnessEquipmentPagedList(q);
	}

	/**
	 * 进入健身设备新增
	 */
	@RequestMapping("toFitnessEquipmentAdd")
	public String toFitnessEquipmentAdd(ModelMap model) {
		log.info("加载健身设备新增页");
		// 获取城市
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			model.put("cities", cities);
		}
		// 获取设备品牌为健身设备的数据
		EquipmentBrand equipmentBrand = new EquipmentBrand();
		equipmentBrand.setBrandType(EquipmentConstant.FITNESS_EQUIPMENT);
		List<EquipmentBrand> listBrand = equipmentBrandService.getEquipmentBrandList(new Query(equipmentBrand));
		if (listBrand != null && listBrand.size() > 0) {
			model.put("listBrand", listBrand);
		}
		return "/equipment/fitness_equipment_add";
	}

	/**
	 * 进入健身设备编辑页面
	 * 
	 */
	@RequestMapping("toFitnessEquipmentEdit")
	public String toFitnessEquipmentEdit(String fitnessEquipmentNo, ModelMap model) {
		FitnessEquipment fitnessEquipment = fitnessEquipmentService.getFitnessEquipment(fitnessEquipmentNo);
		model.put("fitnessEquipment", fitnessEquipment);

		// 获取城市
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			model.put("cities", cities);
		}
		// 默认获取该辅助设备所选城市下的所有门店  社区馆
		Store store = new Store();
		store.setStoreStatus(Constant.STORE_START_STATUS);//已启动
		store.setStoreType(ThemePavilionConstant.COMMUNITY_STORE);
		store.setCityId(fitnessEquipment.getCityId());
		List<Store> listStore = storeService.getStoreList(new Query(store));
		if (listStore != null && listStore.size() > 0) {
			model.put("listStore", listStore);
		}
		// 获取设备品牌为健身类型的数据
		EquipmentBrand equipmentBrand = new EquipmentBrand();
		equipmentBrand.setBrandType(EquipmentConstant.FITNESS_EQUIPMENT);
		List<EquipmentBrand> listBrand = equipmentBrandService.getEquipmentBrandList(new Query(equipmentBrand));
		if (listBrand != null && listBrand.size() > 0) {
			model.put("listBrand", listBrand);
		}
		// 默认获取该辅助设备已选的设备型号和设备分类
		EquipmentModel equipmentModel = new EquipmentModel();
		equipmentModel.setBrandNo(fitnessEquipment.getBrandNo());
		List<EquipmentModel> listEquipmentModel = equipmentModelService
				.getEquipmentModelList(new Query(equipmentModel));
		if (listEquipmentModel != null && listEquipmentModel.size() > 0) {
			model.put("listEquipmentModel", listEquipmentModel);
		}
		EquipmentCategory equipmentCategory = new EquipmentCategory();
		equipmentCategory.setSortNo(fitnessEquipment.getSortNo());
		List<EquipmentCategory> listEquipmentCategory = equipmentCategoryService
				.getEquipmentCategoryList(new Query(equipmentCategory));
		if (listEquipmentCategory != null && listEquipmentCategory.size() > 0) {
			model.put("listEquipmentCategory", listEquipmentCategory);
		}
		return "/equipment/fitness_equipment_edit";
	}

	/**
	 * 健身设备新增或者编辑
	 */
	@RequestMapping("/saveOrUpdateFitnessEquipment")
	@ResponseBody
	public ResultInfo<FitnessEquipment> saveOrUpdateFitnessEquipment(
			@ModelAttribute("fitnessEquipment") FitnessEquipment fitnessEquipment) {
		ResultInfo<FitnessEquipment> resultInfo = new ResultInfo<FitnessEquipment>();
		// set保存城市、门店、品牌、型号、和分类名称
		DataDictItem dataDictItem = dataDictItemService.getDataDictItem(fitnessEquipment.getCityId());
		if (dataDictItem != null) {
			fitnessEquipment.setCityName(dataDictItem.getItemValue());
		}
		Store store = storeService.getStore(fitnessEquipment.getStoreNo());
		if (store != null) {
			fitnessEquipment.setStoreName(store.getStoreName());
		}
		EquipmentBrand equipmentBrand = equipmentBrandService.getEquipmentBrand(fitnessEquipment.getBrandNo());
		if (equipmentBrand != null) {
			fitnessEquipment.setBrandName(equipmentBrand.getBrandName());
		}
		EquipmentModel equipmentModel = equipmentModelService.getEquipmentModel(fitnessEquipment.getModelNo());
		if (equipmentModel != null) {
			fitnessEquipment.setModelName(equipmentModel.getModelName());
		}
		EquipmentCategory equipmentCategory = equipmentCategoryService
				.getEquipmentCategory(fitnessEquipment.getSortNo());
		if (equipmentCategory != null) {
			fitnessEquipment.setSortName(equipmentCategory.getSortName());
		}
		if (null == fitnessEquipment.getFitnessEquipmentNo()) {
			// 新增
			resultInfo = fitnessEquipmentService.addFitnessEquipment(fitnessEquipment, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		} else {
			// 编辑
			resultInfo = fitnessEquipmentService.updateFitnessEquipment(fitnessEquipment, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		}
	}

	/**
	 * 进入健身设备详情页面
	 * 
	 */
	@RequestMapping("toFitnessEquipmentView")
	public String toFitnessEquipmentView(String fitnessEquipmentNo, ModelMap map) {
		FitnessEquipment fitnessEquipment = fitnessEquipmentService.getFitnessEquipment(fitnessEquipmentNo);
		map.put("fitnessEquipment", fitnessEquipment);
		return "/equipment/fitness_equipment_view";
	}

	/**
	 * 健身设备查询对象
	 */
	@RequestMapping("/getFitnessEquipmentById")
	@ResponseBody
	public FitnessEquipment getFitnessEquipmentById(String fitnessEquipmentNo) {
		FitnessEquipment fitnessEquipment = null;
		if (fitnessEquipmentNo != null && fitnessEquipmentNo.length() != 0) {
			fitnessEquipment = fitnessEquipmentService.getFitnessEquipment(fitnessEquipmentNo);
		}
		return fitnessEquipment;
	}

	/**
	 * 健身设备上下线保存更新
	 */
	@RequestMapping("changeEquipmentOnLineStatus")
	@ResponseBody
	public ResultInfo<FitnessEquipment> changeEquipmentOnLineStatus(
			@ModelAttribute("fitnessEquipment") FitnessEquipment fitnessEquipment) {
		ResultInfo<FitnessEquipment> resultInfo = new ResultInfo<FitnessEquipment>();
		if (fitnessEquipment.getFitnessEquipmentNo() != null
				&& fitnessEquipment.getFitnessEquipmentNo().length() != 0) {
			resultInfo = fitnessEquipmentService.updateFitnessEquipment(fitnessEquipment, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(fitnessEquipment);
			if (resultInfo.getData() != null) {
				if (Constant.SECCUESS.equals(resultInfo.getCode())) {
					// 执行健身设备上下线日志保存更新
					EquipmentOnlineLog equipmentOnlineLog = new EquipmentOnlineLog();
					equipmentOnlineLog.setFitnessEquipmentNo(resultInfo.getData().getFitnessEquipmentNo());
					equipmentOnlineLog.setOpType(resultInfo.getData().getOnlineStatus());
					equipmentOnlineLog.setUpdownWhy(resultInfo.getData().getOnOffLineReason());
					equipmentOnlineLog.setMemo(resultInfo.getData().getMemo());
					equipmentOnlineLogService.addEquipmentOnlineLog(equipmentOnlineLog, getOperator());
				}
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("健身设备编号不能为空");
		}
		return resultInfo;
	}

	/**
	 * 查询健身设备上下线分页列表
	 */
	@RequestMapping("/toEquipmentOnlineLogList")
	public String toEquipmentOnlineLogList(String fitnessEquipmentNo, ModelMap model) {
		model.addAttribute("fitnessEquipmentNo", fitnessEquipmentNo);
		return "equipment/equipment_online_Log_list";
	}

	@RequestMapping("/pageEquipmentOnlineLog")
	@ResponseBody
	public PageFinder<EquipmentOnlineLog> pageEquipmentOnlineLog(
			@ModelAttribute("equipmentOnlineLog") EquipmentOnlineLog equipmentOnlineLog, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), equipmentOnlineLog);
		return equipmentOnlineLogService.getEquipmentOnlineLogPagedList(q);
	}

	/**
	 * 根据城市选择该城市下的社区馆数据
	 */
	@RequestMapping("getStoreByCityId")
	@ResponseBody
	public ResultInfo<List<Store>> getStoreByCityId(String cityId) {
		ResultInfo<List<Store>> resultInfo = new ResultInfo<List<Store>>();
		if (StringUtils.isNotBlank(cityId)) {
			Store store = new Store();
			store.setCityId(cityId);
			store.setStoreStatus(Constant.STORE_START_STATUS);
			store.setStoreType(ThemePavilionConstant.COMMUNITY_STORE);// 社区馆
			List<Store> listStore = storeService.getStoreList(new Query(store));
			if (listStore != null && listStore.size() > 0) {
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(listStore);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("该城市下暂无门店！");
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请选择城市!");
		}
		return resultInfo;
	}

	/**
	 * 增加设备时验证devId
	 */
	@RequestMapping("checkDevId")
	@ResponseBody
	public ResultInfo<String> checkDevId(String devId) {

		ResultInfo<String> resultInfo = new ResultInfo<String>();

		FitnessEquipment f = new FitnessEquipment();
		f.setDevId(devId);
		List<FitnessEquipment> ls = fitnessEquipmentService.getFitnessEquipmentList(new Query(f));
		if (ls.size() > 0) {
			resultInfo.setCode("1");
		} else {
			resultInfo.setCode("0");
		}

		return resultInfo;

	}

	/**
	 * 编辑设备时验证devId
	 */
	@RequestMapping("checkDevIdUp")
	@ResponseBody
	public ResultInfo<String> checkDevIdUp(String devId, String fitnessEquipmentNo) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		FitnessEquipment fe = fitnessEquipmentService.getFitnessEquipment(fitnessEquipmentNo);
		if (fe.getDevId().equals(devId)) {
			resultInfo.setCode("0");
		} else {
			FitnessEquipment f = new FitnessEquipment();
			f.setDevId(devId);
			List<FitnessEquipment> ls = fitnessEquipmentService.getFitnessEquipmentList(new Query(f));
			if (ls.size() > 0) {
				resultInfo.setCode("1");
			} else {
				resultInfo.setCode("0");
			}
		}

		return resultInfo;

	}

}