package cn.com.shopec.mgt.storemanagement.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.LocationUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.PrimarykeyService;
import cn.com.shopec.mgt.common.BaseController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 共享健身房-门店管理-门店业务控制层
 * 
 * @author LiHuan Date 2017年10月24日 下午5:28:27
 */
@Controller
@RequestMapping("/store")
public class StoreController extends BaseController {

	private static final Log log = LogFactory.getLog(StoreController.class);

	@Resource
	private StoreService storeService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private PrimarykeyService primarykeyService;
	@Value("${share_path}")
	private String sharePath;
	@Value("${res_img_path}")
	private String resImgPath;

	// 方法
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("HH:mm"), true));

	}

	/**
	 * 进入门店列表
	 */
	@RequestMapping("toStoreList")
	public String toStoreList() {
		log.info("加载门店列表");
		return "/store/store_list";
	}

	/**
	 * 加载门店分页数据列表
	 */
	@RequestMapping("storePageList")
	@ResponseBody
	public PageFinder<Store> storePageList(@ModelAttribute("store") Store store, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		log.info("获取辅助设备列表分页数据");
		Query q = new Query(pageNo, pageSize, store);
		return storeService.getStorePagedList(q);
	}

	/**
	 * 去门店新增页
	 */
	@RequestMapping("toStoreAdd")
	public String toStoreAdd(ModelMap model) {
		// 获取数据字典项 城市信息
		log.info("加载门店新增页面");
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			model.put("cities", cities);
		}
		return "/store/store_add";
	}

	/**
	 * 去门店编辑页 toStoreEdit
	 */
	@RequestMapping("toStoreEdit")
	public String toStoreEdit(String storeNo, ModelMap model) {
		log.info("加载门店编辑页面");
		Store store = storeService.getStore(storeNo);
		model.put("store", store);
		// 获取数据字典项 城市信息
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			model.put("cities", cities);
		}
		return "/store/store_edit";
	}

	/**
	 * 去门店详情页
	 */
	@RequestMapping("toStoreView")
	public String toStoreView(String storeNo, ModelMap model) {
		// 获取数据字典项 城市信息
		log.info("加载门店详情页面");
		Store store = storeService.getStore(storeNo);
		model.put("store", store);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			model.put("cities", cities);
		}
		if (store.getStorePictureUrl1() != null) {
			model.put("picUrls", store.getStorePictureUrl1().split(","));
		}
		return "/store/store_view";
	}

	/**
	 * 新增或编辑门店信息
	 */
	@RequestMapping("saveOrUpdateStore")
	@ResponseBody
	public ResultInfo<Store> saveOrUpdateStore(@ModelAttribute("store") Store store) {
		log.info("进行门店新增或者编辑操作");
		// 根据经纬度获取城市区名称和CODE
		String areaRegion = this.getAddRessByLonLat(store.getLongitude(), store.getLatitude());
		if (store.getStorePictureUrl1() != null && store.getStorePictureUrl1().equals("")) {
			store.setStorePictureUrl1(null);
		}
		if (StringUtils.isNotBlank(areaRegion)) {
			store.setAddrRegion2Name(areaRegion);
			String code = this.getAreaCode(areaRegion);
			store.setAddrRegion2Id(code);
		}
		// 获取城市名称
		DataDictItem dataDictItem = dataDictItemService.getDataDictItem(store.getCityId());
		if (dataDictItem != null) {
			store.setCityName(dataDictItem.getItemValue());
		}
		ResultInfo<Store> resultInfo = new ResultInfo<Store>();
		if (null == store.getStoreNo()) {
			// 新增
			// 新增之前生成门店主键
			String storeNo = "ST" + primarykeyService.getValueByBizType(PrimarykeyConstant.storeNo);
			store.setStoreNo(storeNo);
			// 默认设置该门店未停用状态
			store.setStoreStatus(Constant.STORE_BLOCK_STATUS);
			store.setEntranceStatus(Constant.STORE_START_STATUS);//门禁默认下线状态
			resultInfo = storeService.addStore(store, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		} else {
			resultInfo = storeService.updateStore(store, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		}
	}

	/**
	 * 调用工具类获取市二级区名称
	 */
	private String getAddRessByLonLat(String log, String lat) {
		String areaAdd = "";
		String addRess = LocationUtils.getAreaAddressByLogLat(log, lat);
		JSONObject jsonObject = JSONObject.fromObject(addRess);
		JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("addrList"));
		JSONObject j_2 = JSONObject.fromObject(jsonArray.get(0));
		String allAdd = j_2.getString("admName");
		String arr[] = allAdd.split(",");
		areaAdd = arr[2];
		System.out.println("省:" + arr[0] + "\n市:" + arr[1] + "\n区:" + arr[2]);
		return areaAdd;
	}

	/**
	 * 获取数据字典项市区CODE
	 */
	private String getAreaCode(String areaName) {
		String code = "";
		List<DataDictItem> areas = dataDictItemService.getDataDictItemListByCatCode("AREA");
		if (areas != null && areas.size() > 0) {
			for (DataDictItem dataDictItem : areas) {
				if (areaName.equals(dataDictItem.getItemValue())) {
					code = dataDictItem.getInfo1();
				}
			}
		}
		return code;
	}

	/**
	 * 门店启用、停用查询门店对象
	 */
	@RequestMapping("/toStore")
	@ResponseBody
	public Store toStore(String storeNo) {
		Store store = null;
		if (storeNo != null && storeNo.length() != 0) {
			store = storeService.getStore(storeNo);
		}
		return store;
	}

	/**
	 * 门店启用、停用操作
	 */
	@RequestMapping("changeStoreState")
	@ResponseBody
	public ResultInfo<Store> changeStoreState(@ModelAttribute("store") Store store) {
		ResultInfo<Store> resultInfo = new ResultInfo<Store>();
		if (store.getStoreNo() != null && store.getStoreNo().length() != 0) {
			Store s = storeService.getStore(store.getStoreNo());
			store.setStorePictureUrl1(s.getStorePictureUrl1());
			resultInfo = storeService.updateStore(store, getOperator());
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("门店编号不能为空");
		}
		return resultInfo;
	}

	/**
	 * 获取门店图片
	 */
	@RequestMapping("/getStorePitureUrl")
	@ResponseBody
	public String[] getStorePitureUrl(String storeNo) {
		if (storeNo != null && storeNo.length() != 0) {
			Store store = storeService.getStore(storeNo);
			if (store.getStorePictureUrl1() != null && !store.getStorePictureUrl1().equals("")) {
				return store.getStorePictureUrl1().split(",");
			}
		}
		return null;
	}
	
	/**
	 * 新增校验输入门禁编号是否重复
	 */
	@RequestMapping("checkAddEntranceCode")
	@ResponseBody
	public ResultInfo<String> checkAddEntranceCode(String entranceCode){
		ResultInfo<String> result = new ResultInfo<String>();
		Store store = new Store();
		store.setEntranceCode(entranceCode);
		List<Store> listStore = storeService.getStoreList(new Query(store));
		if(listStore != null && listStore.size() >0){
			result.setCode(Constant.SECCUESS);
		}else{
			result.setCode(Constant.FAIL);
		}
		return result;
	}
	
	/**
	 * 编辑校验输入门禁编号是否重复
	 */
	@RequestMapping("checkEditEntranceCode")
	@ResponseBody
	public ResultInfo<String> checkEditEntranceCode(String storeId, String entranceCode){
		ResultInfo<String> result = new ResultInfo<String>();
		Store storeBean = storeService.getStore(storeId);
		if(StringUtils.isNotBlank(storeBean.getEntranceCode())){
			if(storeBean.getEntranceCode().equals(entranceCode)){
				result.setCode(Constant.FAIL);
			}else{
				Store store = new Store();
				store.setEntranceCode(entranceCode);
				List<Store> listStore = storeService.getStoreList(new Query(store));
				if(listStore != null && listStore.size() >0){
					result.setCode(Constant.SECCUESS);
				}else{
					result.setCode(Constant.FAIL);
				}
			}
		}else{
			result.setCode(Constant.FAIL);
		}
		return result;
	}
}