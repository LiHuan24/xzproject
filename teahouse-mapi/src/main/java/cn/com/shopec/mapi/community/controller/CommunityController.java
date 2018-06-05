package cn.com.shopec.mapi.community.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.LocationUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.FitnessEquipment;
import cn.com.shopec.core.equipment.service.FitnessEquipmentService;
import cn.com.shopec.core.order.model.CommunityOrder;
import cn.com.shopec.core.order.service.CommunityOrderService;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.community.vo.CyVo;
import cn.com.shopec.mapi.community.vo.FeVo;
import cn.com.shopec.mapi.theme.vo.ThemeAndOrderVo;
import cn.com.shopec.mapi.theme.vo.ThemeVo;

@Controller
@RequestMapping("/app/community")
public class CommunityController extends BaseController {
	@Resource
	private StoreService storeService;
	@Resource
	private FitnessEquipmentService fitnessEquipmentService;
	@Resource
	private CommunityOrderService communityOrderService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Value("${image_path}")
	private String imgPath;
	@Value("${city_coding}")
	private String city_coding;

	/**
	 * 进入社区馆门店列表
	 * 
	 */
	@RequestMapping("toCommunity")
	@ResponseBody
	public ResultInfo<ThemeAndOrderVo> toEquipment(Integer page, String lat, String lng, String memberNo,
			String cityCoding) {
		ResultInfo<ThemeAndOrderVo> result = new ResultInfo<ThemeAndOrderVo>();
		ThemeAndOrderVo v = new ThemeAndOrderVo();
		try {
			if (memberNo != null && !memberNo.equals("")) {
				CommunityOrder or = new CommunityOrder();
				or.setMemberNo(memberNo);
				or.setOrderStatus(0);
				List<CommunityOrder> orlist = communityOrderService.getCommunityOrderList(new Query(or));
				if (orlist.size() > 0) {
					v.setCommunityOrderNo(orlist.get(0).getCommunityOrderNo());
				}
			}

			// 查询社区馆数据
			// 查看之前根据城市编码过滤当前城市的门店数据
			// if(cityCoding == null || cityCoding.equals("")){
			cityCoding = "233";
			// }
			// 查询当前城市编码
			DataDictItem m = new DataDictItem();
			m.setDataDictCatCode("CITY");
			m.setInfo1(cityCoding);
			List<DataDictItem> ms = dataDictItemService.getDataDictItemList(new Query(m));
			if (ms.size() == 1) {
				Store store = new Store();
				store.setCityId(ms.get(0).getDataDictItemId());
				store.setStoreType(0);
				store.setStoreStatus(0);
				if (page != null) {
					Query q = new Query(page, 10, store);// 每页查询10条
					PageFinder<Store> pageList = storeService.getStorePagedList(q);
					List<Store> listStore = pageList.getData();
					List<ThemeVo> listThemeVo = new ArrayList<ThemeVo>();
					if (listStore != null && listStore.size() > 0) {
						for (Store st : listStore) {
							ThemeVo vo = new ThemeVo();
							vo.setThemeId(st.getStoreNo());
							vo.setStoresName(st.getStoreName());
							vo.setThemeAdress(st.getAddrStreet());

							if (StringUtils.isNotBlank(st.getStorePictureUrl1())) {
								String[] pic = st.getStorePictureUrl1().split(",");
								if (pic != null) {
									vo.setThemePhotourl(imgPath + "/" + pic[0]);
								}
							} else {
								vo.setThemePhotourl("");
							}

							// 计算定位到主题馆的距离
							if (StringUtils.isNotBlank(lat)) {
								double lat2 = Double.parseDouble(st.getLatitude());
								double lng2 = Double.parseDouble(st.getLongitude());
								Double distance = LocationUtils.getDistance(Double.parseDouble(lat),
										Double.parseDouble(lng), lat2, lng2);
								vo.setDistance(Math.round(distance / 100d) / 10d);
							}
							listThemeVo.add(vo);
						}
						v.setVs(listThemeVo);
						result.setData(v);
						result.setCode(Constant.SECCUESS);
						result.setMsg(Constant.YES_RECORD);
					} else {
						result.setCode(Constant.NO_RESULT);
						result.setMsg(Constant.NO_RECORD);
					}
				}
			} else {
				result.setCode(Constant.FAIL);
				result.setMsg("获取城市信息异常");
			}
		} catch (NumberFormatException e) {
			result.setCode(Constant.FAIL);
			result.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 社区馆门店详情
	 * 
	 */
	@RequestMapping("communityDetails")
	@ResponseBody
	public ResultInfo<CyVo> communityDetails(String themeId) {
		ResultInfo<CyVo> result = new ResultInfo<CyVo>();
		CyVo v = new CyVo();
		List<FeVo> fvs = new ArrayList<FeVo>();

		try {
			Store se = storeService.getStore(themeId);
			v.setStoreName(se.getStoreName());
			v.setAddrStreet(se.getAddrStreet());
			v.setLongitude(se.getLongitude());
			v.setLatitude(se.getLatitude());
			String openTime = ECDateUtils.farmatDateToHM(se.getStoreOpenDate());
			String closeTime = ECDateUtils.farmatDateToHM(se.getStoreColseDate());
			v.setOpenTime(openTime + "-" + closeTime);

			if (StringUtils.isNotBlank(se.getStorePictureUrl1())) {
				String[] pic = se.getStorePictureUrl1().split(",");
				if (pic != null) {
					List<String> pics = new ArrayList<String>();
					for (int i = 0; i < pic.length; i++) {
						pics.add(imgPath + "/" + pic[i]);
					}
					v.setStorePic(pics);
				}
			} else {
				v.setStorePic(null);
			}

			List<String> ls = fitnessEquipmentService.getFeBysortNo(themeId);
			if (ls != null && ls.size() > 0) {
				for (String s : ls) {

					FitnessEquipment f = new FitnessEquipment();
					f.setStoreNo(themeId);
					f.setOnlineStatus(1);
					f.setIsDeleted(0);
					f.setSortNo(s);
					// 计算当前门店设备总数量
					List<FitnessEquipment> fs = fitnessEquipmentService.getFitnessEquipmentList(new Query(f));
					FeVo fv = new FeVo();
					if (fs.size() > 0) {
						fv.setFeNumber(fs.size() + "");
					}
					// 计算当前门店设备空闲数量
					f.setUseStatus(0);
					List<FitnessEquipment> fes = fitnessEquipmentService.getFitnessEquipmentList(new Query(f));
					if (fs.size() > 0) {
						fv.setFreeNumber(fes.size() + "");
					}
					fv.setSortName(fs.get(0).getSortName());
					fvs.add(fv);

				}
				v.setFeList(fvs);
			}

			result.setData(v);
			result.setCode(Constant.SECCUESS);
			result.setMsg(Constant.YES_RECORD);
		} catch (ParseException e) {
			result.setCode(Constant.FAIL);
			result.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}
		return result;

	}

}
