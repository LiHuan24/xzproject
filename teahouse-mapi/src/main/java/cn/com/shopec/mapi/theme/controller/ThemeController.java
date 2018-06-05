package cn.com.shopec.mapi.theme.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.LocationUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.service.AccountsService;
import cn.com.shopec.core.marketing.dao.CouponDao;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.member.service.MemberBalanceService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.dao.ThemeOrderDao;
import cn.com.shopec.core.order.model.ThemeOrder;
import cn.com.shopec.core.order.service.ThemeOrderService;
import cn.com.shopec.core.pay.PayService;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.system.dao.DataDictItemDao;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.themepavilion.common.ThemePavilionConstant;
import cn.com.shopec.core.themepavilion.common.ThemePavilionEnum;
import cn.com.shopec.core.themepavilion.dao.ArrayCourseDao;
import cn.com.shopec.core.themepavilion.dao.BespeakDao;
import cn.com.shopec.core.themepavilion.model.ArrayCourse;
import cn.com.shopec.core.themepavilion.model.Bespeak;
import cn.com.shopec.core.themepavilion.model.Coach;
import cn.com.shopec.core.themepavilion.model.Course;
import cn.com.shopec.core.themepavilion.service.ArrayCourseService;
import cn.com.shopec.core.themepavilion.service.BespeakService;
import cn.com.shopec.core.themepavilion.service.CoachService;
import cn.com.shopec.core.themepavilion.service.CourseLabelService;
import cn.com.shopec.core.themepavilion.service.CourseService;
import cn.com.shopec.core.themepavilion.service.LineUpService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.theme.vo.CouponVo;
import cn.com.shopec.mapi.theme.vo.CourseAppoinPayVo;
import cn.com.shopec.mapi.theme.vo.CourseAppoinVo;
import cn.com.shopec.mapi.theme.vo.CourseDetailVo;
import cn.com.shopec.mapi.theme.vo.CourseListVo;
import cn.com.shopec.mapi.theme.vo.CourseOrderVo;
import cn.com.shopec.mapi.theme.vo.StoreCourseVo;
import cn.com.shopec.mapi.theme.vo.ThemeHomeVo;
import cn.com.shopec.mapi.theme.vo.ThemeOrderDetailVo;
import cn.com.shopec.mapi.theme.vo.ThemeStoreDetail;
import cn.com.shopec.mapi.theme.vo.ThemeVo;

/**
 * APP主题馆业务接口
 * 
 * @author LiHuan Date 2017年11月9日 下午2:25:25
 */
@Controller
@RequestMapping("/app/theme")
public class ThemeController extends BaseController {
	private static Logger log = Logger.getLogger(ThemeController.class);

	@Resource
	private StoreService storeService;
	@Resource
	private ThemeOrderService themeOrderService;
	@Resource
	private CourseService courseService;
	@Resource
	private ArrayCourseService arrayCourseService;
	@Resource
	private ArrayCourseDao arrayCourseDao;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private CoachService coachService;
	@Resource
	private MemberBalanceService memberBalanceService;
	@Resource
	private CouponService couponService;
	@Resource
	private CouponDao couponDao;
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private MemberService memberService;
	@Resource
	private PayService payService;
	@Resource
	private DataDictItemDao dataDictItemDao;
	@Resource
	private ThemeOrderDao themeOrderDao;
	@Resource
	private BespeakService bespeakService;
	@Resource
	private BespeakDao bespeakDao;
	@Resource
	private LineUpService lineUpService;
	@Resource
	private AccountsService accountsService;
	@Resource
	private CourseLabelService courseLabelService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Value("${image_path}")
	private String imgPath;
	@Value("${city_coding}")
	private String city_coding;

	/**
	 * 进入主题馆首页
	 * @param page页码
	 * @param memberId会员ID
	 * @param lat1经度
	 * @param lng1纬度
	 */
	@RequestMapping("toThemeHome")
	@ResponseBody
	public ResultInfo<ThemeHomeVo> toThemeHome(@RequestParam("page") Integer page, String memberId, String lat,String lng, String cityCoding) {
		log.info("进入主题馆首页");
		ResultInfo<ThemeHomeVo> result = new ResultInfo<ThemeHomeVo>();
		ThemeHomeVo themeHomeVo = new ThemeHomeVo();
		// 查询主题馆订单数据
		try {
			if (StringUtils.isNotBlank(memberId)) {
				ThemeOrder themeOrder = new ThemeOrder();
				themeOrder.setMemberNo(memberId);
				themeOrder.setOrderStatus(Integer.parseInt(OrderConstant.yuyueOrder_code));// 订单已预约
				List<ThemeOrder> listThemeOrder = themeOrderService.getAppThemeOrderList(new Query(themeOrder));
				List<CourseOrderVo> listCourseOrderVo = new ArrayList<CourseOrderVo>();
				if (listThemeOrder != null && listThemeOrder.size() > 0) {
					for (ThemeOrder theme : listThemeOrder) {
						CourseOrderVo courseOrderVo = new CourseOrderVo();
						courseOrderVo.setCourseId(theme.getThemeOrderNo());
						// 查询课程名称
						Course course = courseService.getCourse(theme.getCourseNo());
						if (course != null) {
							courseOrderVo.setCourseName(course.getChineseName());
						}
						courseOrderVo.setClassTime(theme.getClassTime());
						courseOrderVo.setStoresName(theme.getStoreName());
						courseOrderVo.setCourseStatus(theme.getOrderStatus());
						courseOrderVo.setMemberId(memberId);
						listCourseOrderVo.add(courseOrderVo);
					}
					themeHomeVo.setListCourseOrderVo(listCourseOrderVo);
				}
			}
			// 查询主题馆数据
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
				store.setStoreType(ThemePavilionConstant.THEME_STORE);
				store.setStoreStatus(ThemePavilionConstant.THEME_ENABLE);
				if (page != null) {
					int pageNo = page;
					Query q = new Query(pageNo, 10, store);// 每页查询10条
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
							if (StringUtils.isNotBlank(lat) && StringUtils.isNotBlank(lng)) {
								double lat2 = Double.parseDouble(st.getLatitude());
								double lng2 = Double.parseDouble(st.getLongitude());
								Double distance = LocationUtils.getDistance(Double.parseDouble(lat),Double.parseDouble(lng), lat2, lng2);
								vo.setDistance(Math.round(distance / 100d) / 10d);
							}
							listThemeVo.add(vo);
						}
						themeHomeVo.setListThemeVo(listThemeVo);
					}
				}
			} else {
				result.setCode(Constant.FAIL);
				result.setMsg("获取城市信息异常");
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		result.setCode(Constant.SECCUESS);
		result.setMsg(Constant.YES_RECORD);
		result.setData(themeHomeVo);
		return result;
	}

	/**
	 * 门店：主题馆详情
	 * 
	 * @param themeId主题馆ID
	 */
	@RequestMapping("toThemeStoreDetail")
	@ResponseBody
	public ResultInfo<ThemeStoreDetail> viewThemeStoreDetail(String themeId) {
		ResultInfo<ThemeStoreDetail> result = new ResultInfo<ThemeStoreDetail>();
		// 查询主题馆对象
		try {
			if (StringUtils.isNotBlank(themeId)) {
				Store store = storeService.getStore(themeId);
				if (store != null) {
					ThemeStoreDetail themeStoreDetail = new ThemeStoreDetail();
					themeStoreDetail.setThemeId(store.getStoreNo());
					themeStoreDetail.setStoresName(store.getStoreName());
					themeStoreDetail.setThemeAdress(store.getAddrStreet());
					themeStoreDetail.setLatitude(store.getLatitude());
					themeStoreDetail.setLongitude(store.getLongitude());
					String openTime = ECDateUtils.farmatDateToHM(store.getStoreOpenDate());
					String closeTime = ECDateUtils.farmatDateToHM(store.getStoreColseDate());
					themeStoreDetail.setOpenTime(openTime + "-" + closeTime);
					themeStoreDetail.setSynopsis(store.getSynopsis());
					if (StringUtils.isNotBlank(store.getStorePictureUrl1())) {
						String[] pic = store.getStorePictureUrl1().split(",");
						if (pic != null) {
							List<String> pics = new ArrayList<String>();
							for (int i = 0; i < pic.length; i++) {
								pics.add(imgPath + "/" + pic[i]);
							}
							themeStoreDetail.setStorePic(pics);
						}
					} else {
						themeStoreDetail.setStorePic(null);
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg(Constant.YES_RECORD);
					result.setData(themeStoreDetail);
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return result;
	}

	/**
	 * 单个主题馆排课信息
	 * 
	 * @param page 页码
	 * @param storeNo 门店编号
	 * @param week 星期X 数字1-7 代表周一至周日
	 * @param dateTime 当前日期时间戳
	 * @param labelNo 课程标签编号
	 */
	@RequestMapping("toThemeStoreCourse")
	@ResponseBody
	public ResultInfo<List<CourseListVo>> toThemeStoreCourse(Integer page, String storeNo, String week, Long dateTime,String labelNo) {
		ResultInfo<List<CourseListVo>> result = new ResultInfo<List<CourseListVo>>();
		Store store = null;
		if (StringUtils.isNotBlank(storeNo)) {
			store = storeService.getStore(storeNo);
		}
		try {
			if (store != null) {
				if (StringUtils.isBlank(week) && StringUtils.isBlank(labelNo)) {
					// 查询日期和类型为空，默认查询当前主题馆下的所有排课数据
					List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
					ArrayCourse course = new ArrayCourse();
					course.setStoreNo(storeNo);
					course.setCourseDate(ECDateUtils.formatDateToDate(new Date()));
					// 根据当前时间获取周X中文
					course.setCourseWeek(ECDateUtils.getWeekDayByCurrentDate(new Date()));
					course.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
					Query q = new Query(page, 15, course);// 每页查询15条

					PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
					if (pageList.getData() != null && pageList.getData().size() > 0) {
						CourseListVo courseList = new CourseListVo();
						List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
						for (ArrayCourse arrayCourseBean : pageList.getData()) {
							StoreCourseVo storeCourseVo = new StoreCourseVo();
							storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
							storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
							storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
							storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
							// 查询该课程标签、分类、图片
							Course courseBean = courseService.getCourse(arrayCourseBean.getCourseNo());
							if (courseBean != null) {
								storeCourseVo.setCourseLabel(courseBean.getLabelName());
								if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
									String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
									storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
								} else {
									storeCourseVo.setCoursePictureUrl1("");
								}
								storeCourseVo.setCourseType(courseBean.getSortName());
								storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));
							}
							// 查询该课程的排课日期
							ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
							if (courseDateBean != null) {
								// 根据门店编号、课程行号和排课类型获取该课程的排课时间段起始和结束
								ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),courseDateBean.getCourseType());
								if (courseBeans != null) {
									String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
									String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
									storeCourseVo.setCourseTime(startTime + "-" + endTime);
									// 判断课程状态 是否还能继续预约
									String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
									String classTime = cTime.substring(0, 10)+" 00:00:00";
									String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
									SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
									Date cDate = ECDateUtils.parseTime(nowTime);
									Long times = ECDateUtils.differMinutes(new Date(), cDate);
									if (times < Integer.parseInt(param.getParamValue())) {
										// 课程上课时间和当前时间相差60分钟结束预约
										storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
									} else if (new Date().getTime() > cDate.getTime()) {
										// 当前时间已经超过排课时间 结束预约
										storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
									} else {
										// 获取系统参数课程可排队人数
										int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
										if (courseDateBean.getReservation() != null) {
											if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() < lineNum) {
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
											} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
											} else {
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
											}
										} else {
											storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
										}
									}
								}
							}
							listStoreCourseVo.add(storeCourseVo);
						}
						if (listStoreCourseVo.size() > 0) {
							// 获取地区名称
							DataDictItem dataDictItem = dataDictItemDao.getDataDictItemByInfo(store.getAddrRegion2Id());
							if (dataDictItem != null) {
								courseList.setAreaName(dataDictItem.getItemValue());
							} else {
								courseList.setAreaName("");
							}
							courseList.setStoreName(store.getStoreName());
							courseList.setListStoreCourseVo(listStoreCourseVo);
							courseVo.add(courseList);
							result.setData(courseVo);
						} else {
							courseList.setStoreName("");
							courseList.setAreaName("");
						}
					}
					if (result.getData() != null) {
						result.setCode(Constant.SECCUESS);
						result.setMsg("请求成功");
					} else {
						result.setCode(Constant.NO_RESULT);
						result.setMsg(Constant.NO_RECORD);
					}
				} else if (StringUtils.isNotBlank(week) && StringUtils.isNotBlank(labelNo)) {
					// 查询条件为日期和课程分类
					Course course = new Course();
					course.setCourseLabelNo(labelNo);
					course.setIsEnable(1);// 可用
					List<Course> list = courseService.queryCourseList(new Query(course));
					List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
					if (list != null && list.size() > 0) {
						CourseListVo courseList = new CourseListVo();
						List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
						if (checkStore(storeNo)) {
							// 判断如果门店启用才进行下一步查询
							for (Course courseBean : list) {
								ArrayCourse arrayCourse = new ArrayCourse();
								arrayCourse.setStoreNo(storeNo);// 门店编号
								arrayCourse.setCourseNo(courseBean.getCourseNo());// 课程
								arrayCourse.setCourseDate(ECDateUtils.longTimeToDateTime(dateTime));
								// 根据当前页面传入星期X获取周中文
								arrayCourse.setCourseWeek(ThemePavilionEnum.getName(Integer.parseInt(week)));
								arrayCourse.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
								Query q = new Query(page, 15, arrayCourse);// 每页查询15条
								PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
								if (pageList.getData() != null && pageList.getData().size() > 0) {
									for (ArrayCourse arrayCourseBean : pageList.getData()) {
										StoreCourseVo storeCourseVo = new StoreCourseVo();
										storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
										storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
										storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
										storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
										storeCourseVo.setCourseLabel(courseBean.getLabelName());
										if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
											String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
											storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
										} else {
											storeCourseVo.setCoursePictureUrl1("");
										}
										storeCourseVo.setCourseType(courseBean.getSortName());
										storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));
										// 查询该课程的排课日期
										ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
										if (courseDateBean != null) {
											// 根据门店编号、课程行号和课程排课类型查询当前课程的起始和结束时间段
											ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(
													courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),
													courseDateBean.getCourseType());
											if (courseBeans != null) {
												String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
												String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
												storeCourseVo.setCourseTime(startTime + "-" + endTime);
												// 判断课程状态 是否还能继续预约
												String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
												String classTime = cTime.substring(0, 10)+" 00:00:00";
												String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
												SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
												Date cDate = ECDateUtils.parseTime(nowTime);
												Long times = ECDateUtils.differMinutes(new Date(), cDate);
												if (times < Integer.parseInt(param.getParamValue())) {
													// 课程上课时间和当前时间相差60分钟结束预约
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
												} else if (new Date().getTime() > cDate.getTime()) {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
												} else {
													// 获取系统参数课程可排队人数
													int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
													if (courseDateBean.getReservation() != null) {
														if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() < lineNum) {
															storeCourseVo.setCourseStatus( ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
														} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
														} else {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
														}
													} else {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
													}
												}
											}
										}
										listStoreCourseVo.add(storeCourseVo);
									}
								}
							}
							if (listStoreCourseVo.size() > 0) {
								// 获取地区名称
								DataDictItem dataDictItem = dataDictItemDao
										.getDataDictItemByInfo(store.getAddrRegion2Id());
								if (dataDictItem != null) {
									courseList.setAreaName(dataDictItem.getItemValue());
								} else {
									courseList.setAreaName("");
								}
								courseList.setStoreName(store.getStoreName());
								courseList.setListStoreCourseVo(listStoreCourseVo);
								courseVo.add(courseList);
								result.setData(courseVo);
							} else {
								courseList.setStoreName("");
								courseList.setAreaName("");
							}
						}
					}
					if (result.getData() != null) {
						result.setCode(Constant.SECCUESS);
						result.setMsg("请求成功");
					} else {
						result.setCode(Constant.NO_RESULT);
						result.setMsg(Constant.NO_RECORD);
					}
				} else if (StringUtils.isBlank(week) && StringUtils.isNotBlank(labelNo)) {
					// 选择了分类 没有选日期
					Course course = new Course();
					course.setCourseLabelNo(labelNo);
					course.setIsEnable(1);
					List<Course> list = courseService.queryCourseList(new Query(course));
					List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
					if (list != null && list.size() > 0) {
						if (checkStore(storeNo)) {
							CourseListVo courseList = new CourseListVo();
							List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
							for (Course courseBean : list) {
								ArrayCourse arrayCourse = new ArrayCourse();
								arrayCourse.setStoreNo(storeNo);
								arrayCourse.setCourseNo(courseBean.getCourseNo());// 课程
								arrayCourse.setCourseDate(ECDateUtils.formatDateToDate(new Date()));// 默认当前日期
								arrayCourse.setCourseWeek(ECDateUtils.getWeekDayByCurrentDate(new Date()));// 根据当前时间获取周X中文
								arrayCourse.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
								Query q = new Query(page, 15, arrayCourse);// 每页查询15条
								PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
								if (pageList.getData() != null && pageList.getData().size() > 0) {
									for (ArrayCourse arrayCourseBean : pageList.getData()) {
										StoreCourseVo storeCourseVo = new StoreCourseVo();
										storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
										storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
										storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
										storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
										storeCourseVo.setCourseLabel(courseBean.getLabelName());
										if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
											String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
											storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
										} else {
											storeCourseVo.setCoursePictureUrl1("");
										}
										storeCourseVo.setCourseType(courseBean.getSortName());
										storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));
										// 查询课程日期
										ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
										if (courseDateBean != null) {
											ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(), courseDateBean.getCourseType());
											if (courseBeans != null) {
												String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
												String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
												storeCourseVo.setCourseTime(startTime + "-" + endTime);
												// 判断课程状态 是否还能继续预约
												String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
												String classTime = cTime.substring(0, 10)+" 00:00:00";
												String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
												SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
												Date cDate = ECDateUtils.parseTime(nowTime);
												Long times = ECDateUtils.differMinutes(new Date(), cDate);
												if (times < Integer.parseInt(param.getParamValue())) {
													// 课程上课时间和当前时间相差60分钟结束预约
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
												} else if (new Date().getTime() > cDate.getTime()) {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
												} else {
													// 获取系统参数课程可排队人数
													int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
													if (courseDateBean.getReservation() != null) {
														if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() < lineNum) {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
														} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
														} else {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
														}
													} else {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
													}
												}
											}
										}
										listStoreCourseVo.add(storeCourseVo);
									}
								}
							}
							if (listStoreCourseVo.size() > 0) {
								// 获取地区名称
								DataDictItem dataDictItem = dataDictItemDao
										.getDataDictItemByInfo(store.getAddrRegion2Id());
								if (dataDictItem != null) {
									courseList.setAreaName(dataDictItem.getItemValue());
								} else {
									courseList.setAreaName("");
								}
								courseList.setStoreName(store.getStoreName());
								courseList.setListStoreCourseVo(listStoreCourseVo);
								courseVo.add(courseList);
								result.setData(courseVo);
							} else {
								courseList.setStoreName("");
								courseList.setAreaName("");
							}
						}
					}
					if (result.getData() != null) {
						result.setCode(Constant.SECCUESS);
						result.setMsg("请求成功");
					} else {
						result.setCode(Constant.NO_RESULT);
						result.setMsg(Constant.NO_RECORD);
					}
				} else if (StringUtils.isNotBlank(week) && StringUtils.isBlank(labelNo)) {
					// 查询条件为日期、分类未选择
					List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
					if (checkStore(storeNo)) {
						ArrayCourse course = new ArrayCourse();
						course.setStoreNo(storeNo);
						course.setCourseDate(ECDateUtils.longTimeToDateTime(dateTime));
						// 根据当前页面传入星期X获取周中文
						course.setCourseWeek(ThemePavilionEnum.getName(Integer.parseInt(week)));
						course.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
						Query q = new Query(page, 15, course);// 每页查询15条
						// 根据条件查询该馆的课程
						PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
						if (pageList.getData() != null && pageList.getData().size() > 0) {
							CourseListVo courseList = new CourseListVo();
							List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
							for (ArrayCourse arrayCourseBean : pageList.getData()) {
								StoreCourseVo storeCourseVo = new StoreCourseVo();
								storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
								storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
								storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
								storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
								// 查询该课程标签、分类、图片
								Course courseBean = courseService.getCourse(arrayCourseBean.getCourseNo());
								if (courseBean != null) {
									storeCourseVo.setCourseLabel(courseBean.getLabelName());
									if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
										String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
										storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
									} else {
										storeCourseVo.setCoursePictureUrl1("");
									}
									storeCourseVo.setCourseType(courseBean.getSortName());
									storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));
								}

								// 查询排课日期
								ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
								if (courseDateBean != null) {
									// 根据门店编号、行号和课程排课类型查询课程开课时间段
									ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),courseDateBean.getCourseType());
									if (courseBeans != null) {
										String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
										String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
										storeCourseVo.setCourseTime(startTime + "-" + endTime);
										// 判断课程状态 是否还能继续预约
										String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
										String classTime = cTime.substring(0, 10)+" 00:00:00";
										String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
										SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
										Date cDate = ECDateUtils.parseTime(nowTime);
										Long times = ECDateUtils.differMinutes(new Date(), cDate);

										if (times < Integer.parseInt(param.getParamValue())) {
											// 课程上课时间和当前时间相差60分钟结束预约
											storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
										} else if (new Date().getTime() > cDate.getTime()) {
											storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
										} else {
											// 获取系统参数课程可排队人数
											int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
											if (courseDateBean.getReservation() != null) {
												if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber()&& courseDateBean.getLineUp() < lineNum) {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
												} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
												} else {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
												}
											} else {
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
											}
										}
									}
								}
								listStoreCourseVo.add(storeCourseVo);
							}
							if (listStoreCourseVo.size() > 0) {
								// 获取地区名称
								DataDictItem dataDictItem = dataDictItemDao.getDataDictItemByInfo(store.getAddrRegion2Id());
								if (dataDictItem != null) {
									courseList.setAreaName(dataDictItem.getItemValue());
								} else {
									courseList.setAreaName("");
								}
								courseList.setStoreName(store.getStoreName());
								courseList.setListStoreCourseVo(listStoreCourseVo);
								courseVo.add(courseList);
								result.setData(courseVo);
							} else {
								courseList.setStoreName("");
								courseList.setAreaName("");
							}
						}
					}
					if (result.getData() != null) {
						result.setCode(Constant.SECCUESS);
						result.setMsg("请求成功");
					} else {
						result.setCode(Constant.NO_RESULT);
						result.setMsg(Constant.NO_RECORD);
					}
				}
			} else {
				result.setCode(Constant.NO_RESULT);
				result.setMsg(Constant.NO_RECORD);
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.toString());
		}
		return result;
	}

	// 判断门店是否启用
	private boolean checkStore(String storeNo) {
		boolean flag = false;
		Store store = storeService.getStore(storeNo);
		if (store != null) {
			if (store.getStoreStatus() == ThemePavilionConstant.THEME_ENABLE) {
				flag = true;
			} else {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * 查看全部主题馆课程
	 * 
	 * @param page页码
	 * @param week星期X 1-7代表星期一至星期日
	 * @param dateTime时间戳
	 * @param labelNo课程标签编号
	 * @param cityCode市区编码
	 * @param cityCoding城市编码
	 */
	@RequestMapping("toStoreCourse")
	@ResponseBody
	public ResultInfo<List<CourseListVo>> toStoreCourse(Integer page, String week, Long dateTime, String labelNo,String cityCode, String cityCoding) {
		ResultInfo<List<CourseListVo>> result = new ResultInfo<List<CourseListVo>>();
		// if (cityCoding == null || cityCoding.equals("")) {
		cityCoding = "233";
		// }
		try {
			if (StringUtils.isBlank(week) && StringUtils.isBlank(labelNo) && StringUtils.isBlank(cityCode)) {
				// 日期、课程标签、城市区编码未选查询
				List<ArrayCourse> listArrayCourse = new ArrayList<ArrayCourse>();
				DataDictItem m = new DataDictItem();
				m.setDataDictCatCode("CITY");
				m.setInfo1(cityCoding);
				List<DataDictItem> listCity = dataDictItemService.getDataDictItemList(new Query(m));
				if (listCity.size() == 1) {
					// 分组查询排课表一共有多少个主题馆，根据城市编码过滤，目前只考虑西安
					String cityNo = listCity.get(0).getDataDictItemId();
					listArrayCourse = arrayCourseDao.listStoreNameGroupBy(cityNo);
				}

				if (listArrayCourse != null && listArrayCourse.size() > 0) {
					List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
					for (ArrayCourse arrayCourse : listArrayCourse) {
						// 判断该排课信息关联的门店是否已经启用
						if (checkStore(arrayCourse.getStoreNo())) {
							ArrayCourse course = new ArrayCourse();
							course.setCourseDate(ECDateUtils.formatDateToDate(new Date()));
							// 根据当前时间获取周X中文
							String currtTime = ECDateUtils.getWeekDayByCurrentDate(new Date());
							course.setCourseWeek(currtTime);
							course.setStoreName(arrayCourse.getStoreName());
							course.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
							Query q = new Query(page, 15, course);// 每页查询15条
							// 根据条件查询该馆的课程
							PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
							if (pageList.getData() != null && pageList.getData().size() > 0) {
								CourseListVo courseList = new CourseListVo();
								List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
								for (ArrayCourse arrayCourseBean : pageList.getData()) {
									StoreCourseVo storeCourseVo = new StoreCourseVo();
									storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
									storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
									storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
									storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
									// 查询该课程标签、分类、图片
									Course courseBean = courseService.getCourse(arrayCourseBean.getCourseNo());
									if (courseBean != null) {
										storeCourseVo.setCourseLabel(courseBean.getLabelName());
										if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
											String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
											storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
										} else {
											storeCourseVo.setCoursePictureUrl1("");
										}
										storeCourseVo.setCourseType(courseBean.getSortName());
										storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));
									}
									// 查询排课日期
									ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
									if (courseDateBean != null) {
										ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),courseDateBean.getCourseType());
										if (courseBeans != null) {
											String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
											String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
											storeCourseVo.setCourseTime(startTime + "-" + endTime);
											// 判断课程状态 是否还能继续预约
											String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
											String classTime = cTime.substring(0, 10)+" 00:00:00";
											String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
											SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
											Date cDate = ECDateUtils.parseTime(nowTime);
											Long times = ECDateUtils.differMinutes(new Date(), cDate);
											if (times < Integer.parseInt(param.getParamValue())) {
												// 课程上课时间和当前时间相差60分钟结束预约
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);// 已结束
											} else if (new Date().getTime() > cDate.getTime()) {
												// 当前时间已经超过排课时间 结束预约
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);// 已结束
											} else {
												// 获取系统参数课程可排队人数
												int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
												if (courseDateBean.getReservation() != null) {
													if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber()&& courseDateBean.getLineUp() < lineNum) {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
													} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
													} else {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
													}
												} else {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
												}
											}
										}
									}
									listStoreCourseVo.add(storeCourseVo);
								}
								if (listStoreCourseVo.size() > 0) {
									courseList.setStoreName(arrayCourse.getStoreName());
									courseList.setListStoreCourseVo(listStoreCourseVo);
									courseVo.add(courseList);
									result.setData(courseVo);
								} else {
									courseList.setStoreName("");
								}
							}
						}
					}
				}
				if (result.getData() != null) {
					result.setCode(Constant.SECCUESS);
					result.setMsg("请求成功");
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} else if (StringUtils.isNotBlank(week) && StringUtils.isBlank(cityCode) && StringUtils.isBlank(labelNo)) {
				// 如果只选择日期查询
				List<ArrayCourse> listArrayCourse = new ArrayList<ArrayCourse>();
				DataDictItem m = new DataDictItem();
				m.setDataDictCatCode("CITY");
				m.setInfo1(cityCoding);
				List<DataDictItem> listCity = dataDictItemService.getDataDictItemList(new Query(m));
				if (listCity.size() == 1) {
					// 分组查询排课表一共有多少个主题馆，根据城市编码过滤，目前只考虑西安
					String cityNo = listCity.get(0).getDataDictItemId();
					listArrayCourse = arrayCourseDao.listStoreNameGroupBy(cityNo);
				}
				if (listArrayCourse != null && listArrayCourse.size() > 0) {
					List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
					for (ArrayCourse arrayCourse : listArrayCourse) {
						if (checkStore(arrayCourse.getStoreNo())) {
							ArrayCourse course = new ArrayCourse();
							course.setCourseDate(ECDateUtils.longTimeToDateTime(dateTime));
							// 根据当前页面传入星期X获取周中文
							String currtDate = ThemePavilionEnum.getName(Integer.parseInt(week));
							course.setCourseWeek(currtDate);
							course.setStoreName(arrayCourse.getStoreName());
							course.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
							Query q = new Query(page, 15, course);// 每页查询15条
							// 根据条件查询该馆的课程
							PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
							if (pageList != null && pageList.getData().size() > 0) {
								CourseListVo courseList = new CourseListVo();
								List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
								for (ArrayCourse arrayCourseBean : pageList.getData()) {
									StoreCourseVo storeCourseVo = new StoreCourseVo();
									storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
									storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
									storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
									storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
									// 查询该课程标签、分类、图片
									Course courseBean = courseService.getCourse(arrayCourseBean.getCourseNo());
									if (courseBean != null) {
										storeCourseVo.setCourseLabel(courseBean.getLabelName());
										if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
											String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
											storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
										} else {
											storeCourseVo.setCoursePictureUrl1("");
										}
										storeCourseVo.setCourseType(courseBean.getSortName());
										storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));
									}
									// 查询课程排课日期
									ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
									if (courseDateBean != null) {
										// 根据门店编号、课程行号和课程排课类型查询排课时间段
										ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),courseDateBean.getCourseType());
										if (courseBeans != null) {
											String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
											String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
											storeCourseVo.setCourseTime(startTime + "-" + endTime);
											// 判断课程状态 是否还能继续预约
											String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
											String classTime = cTime.substring(0, 10)+" 00:00:00";
											String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
											SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
											Date cDate = ECDateUtils.parseTime(nowTime);
											Long times = ECDateUtils.differMinutes(new Date(), cDate);
											if (times < Integer.parseInt(param.getParamValue())) {
												// 课程上课时间和当前时间相差60分钟结束预约
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
											} else if (new Date().getTime() > cDate.getTime()) {
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
											} else {
												// 获取系统参数课程可排队人数
												int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
												if (courseDateBean.getReservation() != null) {
													if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() < lineNum) {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
													} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
													} else {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
													}
												} else {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
												}
											}
										}
									}
									listStoreCourseVo.add(storeCourseVo);
								}
								if (listStoreCourseVo.size() > 0) {
									courseList.setStoreName(arrayCourse.getStoreName());
									courseList.setListStoreCourseVo(listStoreCourseVo);
									courseVo.add(courseList);
									result.setData(courseVo);
								} else {
									courseList.setStoreName("");
								}
							}
						}
					}
				}
				if (result.getData() != null) {
					result.setCode(Constant.SECCUESS);
					result.setMsg("请求成功");
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} else if (StringUtils.isNotBlank(week) && StringUtils.isNotBlank(labelNo) && StringUtils.isBlank(cityCode)) {
				// 选择日期和课程标签查询
				Course course = new Course();
				course.setCourseLabelNo(labelNo);
				course.setIsEnable(1);// 已经启用
				List<Course> list = courseService.queryCourseList(new Query(course));
				if (list != null && list.size() > 0) {
					List<ArrayCourse> listArrayCourse = new ArrayList<ArrayCourse>();
					DataDictItem m = new DataDictItem();
					m.setDataDictCatCode("CITY");
					m.setInfo1(cityCoding);
					List<DataDictItem> listCity = dataDictItemService.getDataDictItemList(new Query(m));
					if (listCity.size() == 1) {
						// 分组查询排课表一共有多少个主题馆，根据城市编码过滤，目前只考虑西安
						String cityNo = listCity.get(0).getDataDictItemId();
						listArrayCourse = arrayCourseDao.listStoreNameGroupBy(cityNo);
					}
					if (listArrayCourse != null && listArrayCourse.size() > 0) {
						List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
						for (ArrayCourse courses : listArrayCourse) {
							CourseListVo courseList = new CourseListVo();
							List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
							if (checkStore(courses.getStoreNo())) {
								for (Course courseBean : list) {
									ArrayCourse arrayCourse = new ArrayCourse();
									arrayCourse.setStoreNo(courses.getStoreNo());
									arrayCourse.setCourseNo(courseBean.getCourseNo());// 课程
									arrayCourse.setCourseDate(ECDateUtils.longTimeToDateTime(dateTime));
									// 根据当前页面传入星期X获取周中文
									String currtDate = ThemePavilionEnum.getName(Integer.parseInt(week));
									arrayCourse.setCourseWeek(currtDate);
									arrayCourse.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
									Query q = new Query(page, 15, arrayCourse);// 每页查询15条
									PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
									if (pageList.getData() != null && pageList.getData().size() > 0) {
										for (ArrayCourse arrayCourseBean : pageList.getData()) {
											StoreCourseVo storeCourseVo = new StoreCourseVo();
											storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
											storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
											storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
											storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
											storeCourseVo.setCourseLabel(courseBean.getLabelName());
											if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
												String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
												storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
											} else {
												storeCourseVo.setCoursePictureUrl1("");
											}
											storeCourseVo.setCourseType(courseBean.getSortName());
											storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));
											// 查询排课日期
											ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
											if (courseDateBean != null) {
												// 根据门店编号、课程行号和排课类型查询上课时间段
												ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),courseDateBean.getCourseType());
												if (courseBeans != null) {
													String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
													String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
													storeCourseVo.setCourseTime(startTime + "-" + endTime);
													// 判断课程状态 是否还能继续预约
													String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
													String classTime = cTime.substring(0, 10)+" 00:00:00";
													String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
													SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
													Date cDate = ECDateUtils.parseTime(nowTime);
													Long times = ECDateUtils.differMinutes(new Date(), cDate);
													if (times < Integer.parseInt(param.getParamValue())) {
														// 课程上课时间和当前时间相差60分钟结束预约
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
													} else if (new Date().getTime() > cDate.getTime()) {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
													} else {
														// 获取系统参数课程可排队人数
														int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
														if (courseDateBean.getReservation() != null) {
															if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() < lineNum) {
																storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
															} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
																storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
															} else {
																storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
															}
														} else {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
														}
													}
												}
											}
											listStoreCourseVo.add(storeCourseVo);
										}
									}
								}
								if (listStoreCourseVo.size() > 0) {
									courseList.setStoreName(courses.getStoreName());
									courseList.setListStoreCourseVo(listStoreCourseVo);
									courseVo.add(courseList);
									result.setData(courseVo);
								} else {
									courseList.setStoreName("");
								}
							}
						}
					}
				}
				if (result.getData() != null) {
					result.setCode(Constant.SECCUESS);
					result.setMsg("请求成功");
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} else if (StringUtils.isNotBlank(cityCode) && StringUtils.isNotBlank(week)
					&& StringUtils.isBlank(labelNo)) {
				// 选择了区编码和日期
				List<ArrayCourse> listArrayCourse = arrayCourseDao.listStoreByAreaCode(cityCode);
				if (listArrayCourse != null && listArrayCourse.size() > 0) {
					List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
					for (ArrayCourse arrayCourse : listArrayCourse) {
						if (checkStore(arrayCourse.getStoreNo())) {
							// week为空时查询所有的主题馆课程数据、默认显示当前日期的课程
							ArrayCourse course = new ArrayCourse();
							course.setCourseDate(ECDateUtils.longTimeToDateTime(dateTime));
							// 根据当前页面传入星期X获取周中文
							String currtDate = ThemePavilionEnum.getName(Integer.parseInt(week));
							course.setCourseWeek(currtDate);
							course.setStoreName(arrayCourse.getStoreName());
							course.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
							Query q = new Query(page, 15, course);// 每页查询15条
							// 根据条件查询该馆的课程
							PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
							if (pageList.getData() != null && pageList.getData().size() > 0) {
								CourseListVo courseList = new CourseListVo();
								List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
								for (ArrayCourse arrayCourseBean : pageList.getData()) {
									StoreCourseVo storeCourseVo = new StoreCourseVo();
									storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
									storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
									storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
									storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
									// 查询该课程标签、分类、图片
									Course courseBean = courseService.getCourse(arrayCourseBean.getCourseNo());
									if (courseBean != null) {
										storeCourseVo.setCourseLabel(courseBean.getLabelName());
										if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
											String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
											storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
										} else {
											storeCourseVo.setCoursePictureUrl1("");
										}
										storeCourseVo.setCourseType(courseBean.getSortName());
										storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));
									}
									// 查询排课时间
									ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
									// 根据门店编号、行号和排课类型获取属于该课程的上课时间段
									if (courseDateBean != null) {
										ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),courseDateBean.getCourseType());
										if (courseBeans != null) {
											String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
											String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
											storeCourseVo.setCourseTime(startTime + "-" + endTime);
											// 判断课程状态 是否还能继续预约
											String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
											String classTime = cTime.substring(0, 10)+" 00:00:00";
											String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
											SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
											Date cDate = ECDateUtils.parseTime(nowTime);
											Long times = ECDateUtils.differMinutes(new Date(), cDate);
											if (times < Integer.parseInt(param.getParamValue())) {
												// 课程上课时间和当前时间相差60分钟结束预约
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
											} else if (new Date().getTime() > cDate.getTime()) {
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
											} else {
												// 获取系统参数课程可排队人数
												int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
												if (courseDateBean.getReservation() != null) {
													if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() < lineNum) {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
													} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
													} else {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
													}
												} else {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
												}
											}
										}
									}
									listStoreCourseVo.add(storeCourseVo);
								}
								if (listStoreCourseVo.size() > 0) {
									courseList.setStoreName(arrayCourse.getStoreName());
									courseList.setListStoreCourseVo(listStoreCourseVo);
									courseVo.add(courseList);
									result.setData(courseVo);
								} else {
									courseList.setStoreName("");
								}
							}
						}
					}
				}
				if (result.getData() != null) {
					result.setCode(Constant.SECCUESS);
					result.setMsg("请求成功");
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} else if (StringUtils.isNotBlank(cityCode) && StringUtils.isNotBlank(labelNo) && StringUtils.isBlank(week)) {
				// 选择区和课程标签
				Course course = new Course();
				course.setCourseLabelNo(labelNo);
				course.setIsEnable(1);// 已经启用
				List<Course> list = courseService.queryCourseList(new Query(course));
				List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
				if (list != null && list.size() > 0) {
					ArrayCourse storeCourse = arrayCourseDao.getArrayForCourse(cityCode);
					if (storeCourse != null) {
						if (checkStore(storeCourse.getStoreNo())) {
							CourseListVo courseList = new CourseListVo();
							List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
							for (Course courseBean : list) {
								ArrayCourse arrayCourse = new ArrayCourse();
								arrayCourse.setStoreName(storeCourse.getStoreName());// 门店
								arrayCourse.setCourseNo(courseBean.getCourseNo());// 课程
								arrayCourse.setCourseDate(ECDateUtils.formatDateToDate(new Date()));// 当前排课时间
								arrayCourse.setCourseWeek(ECDateUtils.getWeekDayByCurrentDate(new Date()));// 默认今天的星期X
								arrayCourse.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
								Query q = new Query(page, 15, arrayCourse);// 每页查询15条
								PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
								if (pageList.getData() != null && pageList.getData().size() > 0) {
									for (ArrayCourse arrayCourseBean : pageList.getData()) {
										StoreCourseVo storeCourseVo = new StoreCourseVo();
										storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
										storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
										storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
										storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
										storeCourseVo.setCourseLabel(courseBean.getLabelName());
										if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
											String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
											storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
										} else {
											storeCourseVo.setCoursePictureUrl1("");
										}
										storeCourseVo.setCourseType(courseBean.getSortName());
										storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));

										// 查询排课时间
										ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
										// 根据行号该课程的行号获取属于该课程的时间
										if (courseDateBean != null) {
											ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),courseDateBean.getCourseType());
											if (courseBeans != null) {
												String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
												String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
												storeCourseVo.setCourseTime(startTime + "-" + endTime);
												// 判断课程状态 是否还能继续预约
												String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
												String classTime = cTime.substring(0, 10)+" 00:00:00";
												String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
												SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
												Date cDate = ECDateUtils.parseTime(nowTime);
												Long times = ECDateUtils.differMinutes(new Date(), cDate);
												if (times < Integer.parseInt(param.getParamValue())) {
													// 课程上课时间和当前时间相差60分钟结束预约
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
												} else if (new Date().getTime() > cDate.getTime()) {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
												} else {
													// 获取系统参数课程可排队人数
													int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
													if (courseDateBean.getReservation() != null) {
														if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() < lineNum) {
															storeCourseVo.setCourseStatus(
																	ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
														} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
														} else {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
														}
													} else {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
													}
												}
											}
										}
										listStoreCourseVo.add(storeCourseVo);
									}
								}
							}
							if (listStoreCourseVo.size() > 0) {
								courseList.setStoreName(storeCourse.getStoreName());
								courseList.setListStoreCourseVo(listStoreCourseVo);
								courseVo.add(courseList);
								result.setData(courseVo);
							} else {
								courseList.setStoreName("");
							}
						}
					}
				}
				if (result.getData() != null) {
					result.setCode(Constant.SECCUESS);
					result.setMsg("请求成功");
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} else if (StringUtils.isNotBlank(cityCode) && StringUtils.isBlank(week) && StringUtils.isBlank(labelNo)) {
				// 只选区编码
				ArrayCourse storeCourse = arrayCourseDao.getArrayForCourse(cityCode);
				if (storeCourse != null) {
					if (checkStore(storeCourse.getStoreNo())) {
						List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
						ArrayCourse arrayCourse = new ArrayCourse();
						arrayCourse.setStoreNo(storeCourse.getStoreNo());
						arrayCourse.setCourseDate(ECDateUtils.formatDateToDate(new Date()));// 默认当前日期
						arrayCourse.setCourseWeek(ECDateUtils.getWeekDayByCurrentDate(new Date()));// 根据当前时间获取周X中文
						arrayCourse.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
						Query q = new Query(page, 15, arrayCourse);// 每页查询15条
						PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
						if (pageList.getData() != null && pageList.getData().size() > 0) {
							CourseListVo courseList = new CourseListVo();
							List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
							for (ArrayCourse arrayCourseBean : pageList.getData()) {
								StoreCourseVo storeCourseVo = new StoreCourseVo();
								storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
								storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
								storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
								storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
								Course courseBean = courseService.getCourse(arrayCourseBean.getCourseNo());
								storeCourseVo.setCourseLabel(courseBean.getLabelName());
								if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
									String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
									storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
								} else {
									storeCourseVo.setCoursePictureUrl1("");
								}
								storeCourseVo.setCourseType(courseBean.getSortName());
								storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));

								// 查询排课时间
								ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
								// 根据行号该课程的行号获取属于该课程的时间
								if (courseDateBean != null) {
									ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),courseDateBean.getCourseType());
									if (courseBeans != null) {
										String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
										String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
										storeCourseVo.setCourseTime(startTime + "-" + endTime);
										// 判断课程状态 是否还能继续预约
										String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
										String classTime = cTime.substring(0, 10)+" 00:00:00";
										String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
										SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
										Date cDate = ECDateUtils.parseTime(nowTime);
										Long times = ECDateUtils.differMinutes(new Date(), cDate);
										if (times < Integer.parseInt(param.getParamValue())) {
											// 课程上课时间和当前时间相差60分钟结束预约
											storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
										} else if (new Date().getTime() > cDate.getTime()) {
											storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
										} else {
											// 获取系统参数课程可排队人数
											int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
											if (courseDateBean.getReservation() != null) {
												if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() < lineNum) {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
												} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
												} else {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
												}
											} else {
												storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
											}
										}
									}
								}
								listStoreCourseVo.add(storeCourseVo);
							}
							if (listStoreCourseVo.size() > 0) {
								courseList.setStoreName(storeCourse.getStoreName());
								courseList.setListStoreCourseVo(listStoreCourseVo);
								courseVo.add(courseList);
								result.setData(courseVo);
							} else {
								courseList.setStoreName("");
							}
						}
					}

					if (result.getData() != null) {
						result.setCode(Constant.SECCUESS);
						result.setMsg("请求成功");
					} else {
						result.setCode(Constant.NO_RESULT);
						result.setMsg(Constant.NO_RECORD);
					}
				}
			} else if (StringUtils.isNotBlank(labelNo) && StringUtils.isBlank(cityCode) && StringUtils.isBlank(week)) {
				// 只选课程类型
				Course course = new Course();
				course.setCourseLabelNo(labelNo);
				course.setIsEnable(1);// 已经启用
				List<Course> list = courseService.queryCourseList(new Query(course));
				if (list != null && list.size() > 0) {
					// 分组查询当前排课的主题馆数据
					List<ArrayCourse> listArrayCourse = new ArrayList<ArrayCourse>();
					DataDictItem m = new DataDictItem();
					m.setDataDictCatCode("CITY");
					m.setInfo1(cityCoding);
					List<DataDictItem> listCity = dataDictItemService.getDataDictItemList(new Query(m));
					if (listCity.size() == 1) {
						// 分组查询排课表一共有多少个主题馆，根据城市编码过滤，目前只考虑西安
						String cityNo = listCity.get(0).getDataDictItemId();
						listArrayCourse = arrayCourseDao.listStoreNameGroupBy(cityNo);
					}
					if (listArrayCourse != null && listArrayCourse.size() > 0) {
						List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
						for (ArrayCourse courses : listArrayCourse) {
							CourseListVo courseList = new CourseListVo();
							List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
							// 判断该排课信息关联的门店是否已经启用
							if (checkStore(courses.getStoreNo())) {
								for (Course courseBean : list) {
									ArrayCourse arrayCourse = new ArrayCourse();
									arrayCourse.setStoreNo(courses.getStoreNo());// 门店
									arrayCourse.setCourseNo(courseBean.getCourseNo());// 课程
									arrayCourse.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
									arrayCourse.setCourseDate(ECDateUtils.formatDateToDate(new Date()));
									// 根据当前时间获取周X中文
									String currtTime = ECDateUtils.getWeekDayByCurrentDate(new Date());
									arrayCourse.setCourseWeek(currtTime);
									Query q = new Query(page, 15, arrayCourse);// 每页查询15条
									PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
									if (pageList.getData() != null && pageList.getData().size() > 0) {
										for (ArrayCourse arrayCourseBean : pageList.getData()) {
											StoreCourseVo storeCourseVo = new StoreCourseVo();
											storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
											storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
											storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
											storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
											storeCourseVo.setCourseLabel(courseBean.getLabelName());
											if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
												String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
												storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
											} else {
												storeCourseVo.setCoursePictureUrl1("");
											}
											storeCourseVo.setCourseType(courseBean.getSortName());
											storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));

											// 查询排课时间
											ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
											// 根据行号该课程的行号获取属于该课程的时间
											if (courseDateBean != null) {
												ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),courseDateBean.getCourseType());
												if (courseBeans != null) {
													String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
													String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
													storeCourseVo.setCourseTime(startTime + "-" + endTime);
													// 判断课程状态 是否还能继续预约
													String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
													String classTime = cTime.substring(0, 10)+" 00:00:00";
													String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
													SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
													Date cDate = ECDateUtils.parseTime(nowTime);
													Long times = ECDateUtils.differMinutes(new Date(), cDate);
													if (times < Integer.parseInt(param.getParamValue())) {
														// 课程上课时间和当前时间相差60分钟结束预约
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
													} else if (new Date().getTime() > cDate.getTime()) {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
													} else {
														// 获取系统参数课程可排队人数
														int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
														if (courseDateBean.getReservation() != null) {
															if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() < lineNum) {
																storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
															} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
																storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
															} else {
																storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
															}
														} else {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
														}
													}
												}
											}
											listStoreCourseVo.add(storeCourseVo);
										}
									}
								}
								if (listStoreCourseVo.size() > 0) {
									courseList.setStoreName(courses.getStoreName());
									courseList.setListStoreCourseVo(listStoreCourseVo);
									courseVo.add(courseList);
									result.setData(courseVo);
								} else {
									courseList.setStoreName("");
								}
							}
						}
					}
				}
				if (result.getData() != null) {
					result.setCode(Constant.SECCUESS);
					result.setMsg("请求成功");
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} else if (StringUtils.isNotBlank(cityCode) && StringUtils.isNotBlank(week) && StringUtils.isNotBlank(labelNo)) {
				// 全选
				Course course = new Course();
				course.setCourseLabelNo(labelNo);
				course.setIsEnable(1);// 已经启用
				List<Course> list = courseService.queryCourseList(new Query(course));
				List<CourseListVo> courseVo = new ArrayList<CourseListVo>();
				if (list != null && list.size() > 0) {
					ArrayCourse storeCourse = arrayCourseDao.getArrayForCourse(cityCode);
					if (storeCourse != null) {
						if (checkStore(storeCourse.getStoreNo())) {
							CourseListVo courseList = new CourseListVo();
							List<StoreCourseVo> listStoreCourseVo = new ArrayList<StoreCourseVo>();
							for (Course courseBean : list) {
								ArrayCourse arrayCourse = new ArrayCourse();
								arrayCourse.setStoreNo(storeCourse.getStoreNo());
								arrayCourse.setCourseNo(courseBean.getCourseNo());// 课程
								arrayCourse.setCourseDate(ECDateUtils.longTimeToDateTime(dateTime));
								// 根据当前页面传入星期X获取周中文
								arrayCourse.setCourseWeek(ThemePavilionEnum.getName(Integer.parseInt(week)));
								arrayCourse.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);// 已发布
								Query q = new Query(page, 15, arrayCourse);// 每页查询15条
								PageFinder<ArrayCourse> pageList = arrayCourseService.pageStoreAllCourseList(q);
								if (pageList.getData() != null && pageList.getData().size() > 0) {
									for (ArrayCourse arrayCourseBean : pageList.getData()) {
										StoreCourseVo storeCourseVo = new StoreCourseVo();
										storeCourseVo.setStoreNo(arrayCourseBean.getStoreNo());
										storeCourseVo.setCourseNo(arrayCourseBean.getCourseNo());
										storeCourseVo.setArrayCourseNo(arrayCourseBean.getArrayCourseNo());
										storeCourseVo.setCourseName(arrayCourseBean.getChineseName());
										storeCourseVo.setCourseLabel(courseBean.getLabelName());
										if (StringUtils.isNotBlank(courseBean.getCoursePictureUrl1())) {
											String[] coursePic = courseBean.getCoursePictureUrl1().split(",");
											storeCourseVo.setCoursePictureUrl1(imgPath + "/" + coursePic[0]);
										} else {
											storeCourseVo.setCoursePictureUrl1("");
										}
										storeCourseVo.setCourseType(courseBean.getSortName());
										storeCourseVo.setCoursePrice(String.valueOf(courseBean.getPrice()));

										// 查询排课时间
										ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(arrayCourseBean.getArrayCourseNo());
										// 根据行号该课程的行号获取属于该课程的时间
										if (courseDateBean != null) {
											ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(), courseDateBean.getFtlRow(),courseDateBean.getCourseType());
											if (courseBeans != null) {
												String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
												String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
												storeCourseVo.setCourseTime(startTime + "-" + endTime);
												// 判断课程状态 是否还能继续预约
												String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
												String classTime = cTime.substring(0, 10)+" 00:00:00";
												String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
												SysParam param = sysParamService.getByParamKey("COURSE_MAKE_DAY");
												Date cDate = ECDateUtils.parseTime(nowTime);
												Long times = ECDateUtils.differMinutes(new Date(), cDate);
												if (times < Integer.parseInt(param.getParamValue())) {
													// 课程上课时间和当前时间相差60分钟结束预约
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STOP_APPION);
												} else if (new Date().getTime() > cDate.getTime()) {
													storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_OVER);
												} else {
													// 获取系统参数课程可排队人数
													int lineNum = Integer.parseInt(sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue());
													if (courseDateBean.getReservation() != null) {
														if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() < lineNum) {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_LINE);// 排队中
														} else if (courseDateBean.getReservation() >= courseDateBean.getPeopleNumber() && courseDateBean.getLineUp() >= lineNum) {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_FULL);// 预约已满、排队已满
														} else {
															storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
														}
													} else {
														storeCourseVo.setCourseStatus(ThemePavilionConstant.COURSE_STATS_MAKE);// 正常预约
													}
												}
											}
										}
										listStoreCourseVo.add(storeCourseVo);
									}
								}
							}
							if (listStoreCourseVo.size() > 0) {
								courseList.setStoreName(storeCourse.getStoreName());
								courseList.setListStoreCourseVo(listStoreCourseVo);
								courseVo.add(courseList);
								result.setData(courseVo);
							} else {
								courseList.setStoreName("");
							}
						}
					}
				}
				if (result.getData() != null) {
					result.setCode(Constant.SECCUESS);
					result.setMsg("请求成功");
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.toString());
		}
		return result;
	}

	/**
	 * 查看主题馆排课课程详情
	 * @param arrayCourseNo 排课课程编号
	 * @param courseStatus 排课课程状态
	 */
	@RequestMapping("toArrayCourseDetail")
	@ResponseBody
	public ResultInfo<CourseDetailVo> toArrayCourseDetail(String arrayCourseNo, String courseStatus) {
		ResultInfo<CourseDetailVo> result = new ResultInfo<CourseDetailVo>();
		try {
			ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
			if (arrayCourse != null) {
				// 查询教练数据
				Coach coach = coachService.getCoach(arrayCourse.getCoachNo());
				// 查询门店
				Store store = storeService.getStore(arrayCourse.getStoreNo());
				// 查询课程
				Course course = courseService.getCourse(arrayCourse.getCourseNo());
				CourseDetailVo courseDetailVo = new CourseDetailVo();
				courseDetailVo.setArrayCourseNo(arrayCourseNo);
				courseDetailVo.setCourseStatus(courseStatus);
				courseDetailVo.setCoachName(arrayCourse.getCoachName());
				if (coach != null) {
					String coachSynopsis = coach.getSynopsis().replaceAll("\r|\n|\t", "").replace(" ", "");
					courseDetailVo.setCoachSynopsis(coachSynopsis);
					if (StringUtils.isNotBlank(coach.getMemberPhotoUrl())) {
						courseDetailVo.setMemberPhotoUrl(imgPath + "/" + coach.getMemberPhotoUrl());
					} else {
						courseDetailVo.setMemberPhotoUrl("");
					}
				}
				courseDetailVo.setStoreName(arrayCourse.getStoreName());
				if (store != null) {
					courseDetailVo.setLatitude(store.getLatitude());
					courseDetailVo.setLongitude(store.getLongitude());
					courseDetailVo.setAddrStreet(store.getAddrStreet());
				}
				if (course != null) {
					courseDetailVo.setCoursePrice(String.valueOf(course.getPrice()));
					String courseSynopsis = course.getSynopsis().replaceAll("\r|\n|\t", "").replace(" ", "");
					courseDetailVo.setCourseSynopsis(courseSynopsis);
					courseDetailVo.setEffect(course.getEffect().replaceAll("\r|\n|\t", "").replace(" ", ""));
					courseDetailVo.setSuit(course.getSuit().replaceAll("\r|\n|\t", "").replace(" ", ""));
					courseDetailVo.setBeCareful(course.getBeCareful().replaceAll("\r|\n|\t", "").replace(" ", ""));
					if (StringUtils.isNotBlank(course.getCoursePictureUrl1())) {
						String[] coursePic = course.getCoursePictureUrl1().split(",");
						if (coursePic != null) {
							List<String> pics = new ArrayList<String>();
							for (int i = 0; i < coursePic.length; i++) {
								pics.add(imgPath + "/" + coursePic[i]);
							}
							courseDetailVo.setCoursePhotoUrl(pics);
						}
					} else {
						courseDetailVo.setCoursePhotoUrl(null);
					}
					// 查询该课程的具体排课时间
					ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(arrayCourse.getStoreNo(),arrayCourse.getFtlRow(), arrayCourse.getCourseType());
					if (courseBeans != null) {
						String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
						String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
						courseDetailVo.setCourseTime(startTime + "-" + endTime);
					}
				}
				result.setCode(Constant.SECCUESS);
				result.setMsg(Constant.YES_RECORD);
				result.setData(courseDetailVo);
			} else {
				result.setCode(Constant.NO_RESULT);
				result.setMsg(Constant.NO_RECORD);
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return result;
	}

	/**
	 * 加载主题馆课程预约界面
	 * @param arrayCourseNo 排课课程编号
	 * @param courseStatus 排课课程状态
	 */
	@RequestMapping("toArrayCourseAppoin")
	@ResponseBody
	public ResultInfo<CourseAppoinVo> toArrayCourseAppoin(String arrayCourseNo, String courseStatus) {
		ResultInfo<CourseAppoinVo> result = new ResultInfo<CourseAppoinVo>();
		try {
			if (ThemePavilionConstant.COURSE_STATS_MAKE.equals(courseStatus)) {
				// 能正常预约
				ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
				if (arrayCourse != null) {
					CourseAppoinVo courseAppoinVo = new CourseAppoinVo();
					courseAppoinVo.setArrayCourseNo(arrayCourseNo);
					courseAppoinVo.setCourseName(arrayCourse.getChineseName());
					// 查询门店地址
					Store store = storeService.getStore(arrayCourse.getStoreNo());
					courseAppoinVo.setStoreName(store.getStoreName());
					courseAppoinVo.setStoreAdress(store.getAddrStreet());
					// 查询课程单价 APP页面根据选择人数计算总价
					Course course = courseService.getCourse(arrayCourse.getCourseNo());
					if (course != null) {
						courseAppoinVo.setCoursePrice(String.valueOf(course.getPrice()));
					}
					// 查询课程上课时间
					ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(arrayCourse.getStoreNo(),arrayCourse.getFtlRow(), arrayCourse.getCourseType());
					if (courseBeans != null) {
						Date corseDate = ECDateUtils.formatDateToDateNew(arrayCourse.getCourseDate());
						String courseTime = ECDateUtils.getCurrentDateAsString(corseDate);
						String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
						String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
						courseAppoinVo.setCourseTime(courseTime + " " + startTime + "-" + endTime);
					}
					courseAppoinVo.setPeopleNumber(String.valueOf(arrayCourse.getPeopleNumber()));
					// 剩余人数
					if (arrayCourse.getReservation() != null) {
						int surplusNumber = arrayCourse.getPeopleNumber() - arrayCourse.getReservation();
						courseAppoinVo.setSurplusNumber(String.valueOf(surplusNumber));
						courseAppoinVo.setCourseDescribe("本课名额" + String.valueOf(arrayCourse.getPeopleNumber()) + "," + "目前剩余名额" + String.valueOf(surplusNumber) + "人");
					} else {
						courseAppoinVo.setSurplusNumber(String.valueOf(arrayCourse.getPeopleNumber()));
						courseAppoinVo.setCourseDescribe("本课名额" + String.valueOf(arrayCourse.getPeopleNumber()) + "," + "目前还未有人预约");
					}
					courseAppoinVo.setCourseStatus(ThemePavilionConstant.APP_COURSE_MAKE);// 正常预约
					result.setCode(Constant.SECCUESS);
					result.setMsg(Constant.YES_RECORD);
					result.setData(courseAppoinVo);
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} else if (ThemePavilionConstant.COURSE_STATS_LINE.equals(courseStatus)) {
				// 主题馆课程 排队中
				ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
				if (arrayCourse != null) {
					CourseAppoinVo courseAppoinVo = new CourseAppoinVo();
					courseAppoinVo.setArrayCourseNo(arrayCourseNo);
					courseAppoinVo.setCourseName(arrayCourse.getChineseName());
					// 查询门店地址
					Store store = storeService.getStore(arrayCourse.getStoreNo());
					courseAppoinVo.setStoreName(store.getStoreName());
					courseAppoinVo.setStoreAdress(store.getAddrStreet());
					Course course = courseService.getCourse(arrayCourse.getCourseNo());
					if (course != null) {
						courseAppoinVo.setCoursePrice(String.valueOf(course.getPrice()));
					}
					// 查询课程上课时间
					ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(arrayCourse.getStoreNo(),arrayCourse.getFtlRow(), arrayCourse.getCourseType());
					if (courseBeans != null) {
						Date corseDate = ECDateUtils.formatDateToDateNew(arrayCourse.getCourseDate());
						String courseTime = ECDateUtils.getCurrentDateAsString(corseDate);
						String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
						String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
						courseAppoinVo.setCourseTime(courseTime + " " + startTime + "-" + endTime);
					}
					// 最多排队人数
					String surplusLineNum = sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue();
					// 判断排队中的数据
					if (arrayCourse.getReservation() != null) {
						if (arrayCourse.getReservation() >= arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() < Integer.parseInt(surplusLineNum)) {
							// 预约人数大于等于20并且排队人数小于10人 排队中
							courseAppoinVo.setLineUpNumber(String.valueOf(arrayCourse.getLineUp()));// 排队人数
							int surNum = Integer.parseInt(surplusLineNum) - arrayCourse.getLineUp();
							courseAppoinVo.setSurplusLineNum(String.valueOf(surNum));// 排队剩余人数
							if (arrayCourse.getLineUp() > 0) {
								courseAppoinVo.setCourseDescribe(arrayCourse.getLineUp() + "人排队中");
							} else {
								courseAppoinVo.setCourseDescribe("目前暂无人排队");
							}

							courseAppoinVo.setCourseStatus(ThemePavilionConstant.APP_COURSE_MAKE);// 排队未满可预约
						}
					} else {
						courseAppoinVo.setCourseDescribe("已预约" + arrayCourse.getReservation() + "人，目前暂无人排队");
						courseAppoinVo.setCourseStatus(ThemePavilionConstant.APP_COURSE_MAKE);// 排队未满可预约
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg(Constant.YES_RECORD);
					result.setData(courseAppoinVo);
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} else if (ThemePavilionConstant.COURSE_STATS_FULL.equals(courseStatus)) {
				// 课程满员
				ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
				if (arrayCourse != null) {
					CourseAppoinVo courseAppoinVo = new CourseAppoinVo();
					courseAppoinVo.setCourseName(arrayCourse.getChineseName());
					// 查询门店地址
					Store store = storeService.getStore(arrayCourse.getStoreNo());
					courseAppoinVo.setStoreName(store.getStoreName());
					courseAppoinVo.setStoreAdress(store.getAddrStreet());
					Course course = courseService.getCourse(arrayCourse.getCourseNo());
					if (course != null) {
						courseAppoinVo.setCoursePrice(String.valueOf(course.getPrice()));
					}
					// 查询课程上课时间
					ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(arrayCourse.getStoreNo(),arrayCourse.getFtlRow(), arrayCourse.getCourseType());
					if (courseBeans != null) {
						Date corseDate = ECDateUtils.formatDateToDateNew(arrayCourse.getCourseDate());
						String courseTime = ECDateUtils.getCurrentDateAsString(corseDate);
						String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
						String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
						courseAppoinVo.setCourseTime(courseTime + " " + startTime + "-" + endTime);
					}
					// 最多排队人数
					String surplusLineNum = sysParamService.getByParamKey("COURSE_LINE_UP").getParamValue();
					// 判断排队中的数据
					if (arrayCourse.getReservation() != null && arrayCourse.getLineUp() != null) {
						if (arrayCourse.getReservation() >= arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() > Integer.parseInt(surplusLineNum)) {
							// 满员
							courseAppoinVo.setCourseDescribe("当前课程排队人数已满，不能再预约排队");
							courseAppoinVo.setCourseStatus(ThemePavilionConstant.APP_COURSE_FULL);// 排队已满
						}
					} else {
						courseAppoinVo.setCourseDescribe("已预约" + arrayCourse.getReservation() + "人，目前暂无人排队");
						courseAppoinVo.setCourseStatus(ThemePavilionConstant.APP_COURSE_MAKE);// 排队未满可预约
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg(Constant.YES_RECORD);
					result.setData(courseAppoinVo);
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} else {
				// 课程已结束
				ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
				if (arrayCourse != null) {
					CourseAppoinVo courseAppoinVo = new CourseAppoinVo();
					courseAppoinVo.setArrayCourseNo(arrayCourseNo);
					courseAppoinVo.setCourseName(arrayCourse.getChineseName());
					// 查询门店地址
					Store store = storeService.getStore(arrayCourse.getStoreNo());
					if (store != null) {
						courseAppoinVo.setStoreName(store.getStoreName());
						courseAppoinVo.setStoreAdress(store.getAddrStreet());
					}

					// 课程单价
					Course course = courseService.getCourse(arrayCourse.getCourseNo());
					if (course != null) {
						courseAppoinVo.setCoursePrice(String.valueOf(course.getPrice()));
					}
					// 查询课程上课时间
					ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(arrayCourse.getStoreNo(),arrayCourse.getFtlRow(), arrayCourse.getCourseType());
					if (courseBeans != null) {
						Date corseDate = ECDateUtils.formatDateToDateNew(arrayCourse.getCourseDate());
						String courseTime = ECDateUtils.getCurrentDateAsString(corseDate);
						String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
						String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
						courseAppoinVo.setCourseTime(courseTime + " " + startTime + "-" + endTime);
					}
					courseAppoinVo.setCourseDescribe("当前课程已结束");
					courseAppoinVo.setCourseStatus(ThemePavilionConstant.APP_COURSE_FULL);// 已结束
					result.setCode(Constant.SECCUESS);
					result.setMsg(Constant.YES_RECORD);
					result.setData(courseAppoinVo);
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return result;
	}

	/**
	 * 跳转至支付界面前进行一下课程预约校验
	 * @param memberNo
	 * @param arrayCourseNo排课编号
	 * @param oppionNumber选择的预约人数
	 */
	@RequestMapping("checkAppoinCourse")
	@ResponseBody
	public ResultInfo<String> checkAppoinCourse(String memberNo, String arrayCourseNo, String oppionNumber) {
		ResultInfo<String> result = new ResultInfo<String>();
		synchronized (this) {
			try {
				// 查询会员是否为黑名单
				Member member = memberService.getMember(memberNo);
				if (member != null && member.getIsBlacklist() == 0) {
					ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
					SysParam param = sysParamService.getByParamKey("COURSE_LINE_UP");
					if (arrayCourse != null) {
						if (arrayCourse.getReservation() != null) {
							if (arrayCourse.getReservation() >= arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() < Integer.parseInt(param.getParamValue())) {
								result.setCode(Constant.SECCUESS);
								result.setMsg("此课程预约人数已满，请您去预约排队等候...");
								result.setData(String.valueOf(arrayCourse.getLineUp()));
							} else if (arrayCourse.getReservation() >= arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() > Integer.parseInt(param.getParamValue())) {
								result.setCode(Constant.FAIL);
								result.setMsg("对不起、此课程预约人数和排队人数都已满，请您选择其他课程预约...");
								result.setData(String.valueOf(arrayCourse.getLineUp()));
							} else if (arrayCourse.getReservation() != arrayCourse.getPeopleNumber()) {
								// 实时查询目前剩余可预约人数
								int syrs = arrayCourse.getPeopleNumber() - arrayCourse.getReservation();
								if (syrs >= Integer.parseInt(oppionNumber)) {
									result.setCode(Constant.SECCUESS);
									result.setData(String.valueOf(syrs));
								} else if (syrs < Integer.parseInt(oppionNumber)) {
									result.setCode(Constant.FAIL);
									result.setMsg("该课程可预约人数目前剩余为" + String.valueOf(syrs) + "，请您重新选择预约人数！");
									result.setData(String.valueOf(syrs));
								}
							} else {
								result.setCode(Constant.SECCUESS);
							}
						} else {
							result.setCode(Constant.SECCUESS);
						}
					}
				} else {
					result.setCode(Constant.FAIL);
					result.setMsg("对不起、您已经被移入黑名单，暂时不能预约课程，请联系客服...");
				}
			} catch (Exception e) {
				result.setCode(Constant.FAIL);
				result.setMsg(e.getMessage());
			}
		}
		return result;
	}

	/**
	 * 预约课程订单支付界面
	 * @param memberNo 会员编号
	 * @param arrayCourseNo 预约课程编号
	 * @param appoinNumber 预约人数
	 * @param coursePrice  预约价钱 人数*价钱 从预约订单界面计算而来
	 * @param themeOrderNo  订单编号 只有待支付订单有值，其余入口为空字符串
	 */
	@RequestMapping("toAppoinCoursePay")
	@ResponseBody
	public ResultInfo<CourseAppoinPayVo> toAppoinCoursePay(String memberNo, String arrayCourseNo, String appoinNumber,String coursePrice,String themeOrderNo) {
		ResultInfo<CourseAppoinPayVo> result = new ResultInfo<CourseAppoinPayVo>();
		try {
			if (StringUtils.isNotBlank(memberNo)) {
				if(!appoinNumber.equals("0")){
					// 根据会员编号查询该会员的课程包节数
					MemberBalance memberBalance = memberBalanceService.getMemberBalance(memberNo);
					// 会员课时包
					String memberCourseBag = null;
					// 会员余额
					double memberBalances = 0d;
					if (memberBalance != null) {
						if (memberBalance.getCourseNumber() != null) {
							memberCourseBag = String.valueOf(memberBalance.getCourseNumber());
						} else {
							memberCourseBag = "";
						}

						if (memberBalance.getMemberBalance() != null) {
							memberBalances = memberBalance.getMemberBalance();
						} else {
							memberBalances = 0d;
						}
					}
					// 计算课程单价
					double oneCoursePrice = ECCalculateUtils.div(Double.parseDouble(coursePrice),Double.parseDouble(appoinNumber), 2);
					// 需要抵用的课程包
					CourseAppoinPayVo cap = new CourseAppoinPayVo();
					cap.setMemberNo(memberNo);
					cap.setArrayCourseNo(arrayCourseNo);
					cap.setPayAmount(coursePrice);
					cap.setAppoinNumber(appoinNumber);
					if (StringUtils.isNotBlank(memberCourseBag)) {
						cap.setSurplusCourseBag(memberCourseBag);
					} else {
						cap.setSurplusCourseBag(memberCourseBag);
					}
					cap.setBalance(memberBalances);
					cap.setRealPayAmount(coursePrice);
					cap.setOneCoursePrice(oneCoursePrice);
					cap.setThemeOrderNo(themeOrderNo);
					// 根据会员编号查询该会员是否有会员优惠券
					List<CouponVo> listCoupon = this.getMemberCoupon(memberNo, coursePrice);
					if (listCoupon != null && listCoupon.size() > 0) {
						cap.setListCouponVo(listCoupon);
					} else {
						cap.setListCouponVo(null);
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg(Constant.YES_RECORD);

					result.setData(cap);
				}else{
					result.setCode(Constant.NO_RESULT);
					result.setMsg("课程订单数据有误！");
				}
			} else {
				result.setCode(Constant.NO_RESULT);
				result.setMsg(Constant.NO_RECORD);
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.toString());
		}
		return result;
	}

	/**
	 * 课程支付界面若选择优惠券，根据会员编号查询该会员拥有的优惠券数据
	 * @param memberNo 会员编号
	 */
	private List<CouponVo> getMemberCoupon(String memberNo, String coursePrice) {
		List<CouponVo> listCouponVo = new ArrayList<CouponVo>();
		try {
			if (StringUtils.isNotBlank(memberNo)) {
				Coupon coupon = new Coupon();
				coupon.setMemberNo(memberNo);
				coupon.setIsAvailable(1);// 已启用
				coupon.setUsedStatus(0);// 未使用
				coupon.setAvailableTime2Start(new Date());
				List<Coupon> listCoupon = couponService.getCouponList(new Query(coupon));

				if (listCoupon != null && listCoupon.size() > 0) {
					for (Coupon couponBean : listCoupon) {
						// 判断优惠券是否已经过期，过期的不给APP页展示
						CouponVo couponVo = new CouponVo();
						couponVo.setMemberNo(memberNo);
						couponVo.setCouponNo(couponBean.getCouponNo());
						couponVo.setCouponName(couponBean.getTitle());
						if (couponBean.getCouponMethod() == 1) {
							couponVo.setConponType(String.valueOf(couponBean.getCouponMethod()));
							couponVo.setDiscountRate(couponBean.getDiscount());
							listCouponVo.add(couponVo);
						} else if (couponBean.getCouponMethod() == 2) {// 直减
							// 订单金额满此优惠券满减金额才可进行满减
							CouponPlan couponPlan = couponPlanService.getCouponPlan(couponBean.getPlanNo());
							if (couponPlan != null) {
								if (couponPlan.getCensorStatus() == 1 && couponPlan.getIsAvailable() == 1) {
									Double orderAmount = Double.parseDouble(coursePrice);
									if (orderAmount.compareTo(couponPlan.getConsumeAmount()) >= 0) {
										// 直减金额（满减金额）
										Double discountAmount = couponPlan.getDiscountAmount();
										couponVo.setConponType(String.valueOf(couponBean.getCouponMethod()));
										couponVo.setLapseAmount(String.valueOf(discountAmount));
										listCouponVo.add(couponVo);
									} else if (couponPlan.getConsumeAmount() == 0) {
										Double discountAmount = couponPlan.getDiscountAmount();
										couponVo.setConponType(String.valueOf(couponBean.getCouponMethod()));
										couponVo.setLapseAmount(String.valueOf(discountAmount));
										listCouponVo.add(couponVo);
									}
								}
							}
						} else if (couponBean.getCouponMethod() == 4) {// 课程体验券
							couponVo.setConponType(String.valueOf(couponBean.getCouponMethod()));
							couponVo.setFreeCourseNumber(couponBean.getFreeCourseNumber());
							listCouponVo.add(couponVo);
						}
					}
				}
			}
		} catch (Exception e) {
			log.info(e.toString());
		}
		return listCouponVo;
	}

	/**
	 * 当实际支付金额为0.0时，生成主题馆课程订单
	 */
	private void generateOrders(String memberNo, String appoinNumber, String couponNo, String courseBag, double balance, String payAmount, String arrayCourseNo, String themeOrderNo) {
		try {
			if (StringUtils.isNotBlank(memberNo)) {
				if(StringUtils.isBlank(themeOrderNo)){
					//初次新增订单
					ThemeOrder themeOrder = new ThemeOrder();
					themeOrder.setThemeOrderNo(themeOrderService.generatePK());
					themeOrder.setPayStatus(1);// 已支付
					themeOrder.setIsSendMsg(0);// 未发送短信
					// 根据订单中的排课编号统计属于该课程的预约人数
					long appionNumber = themeOrderDao.statCourseAppoinNumber(arrayCourseNo);
					// 查询该课程的排课人数
					ArrayCourse course = arrayCourseService.getArrayCourse(arrayCourseNo);
					if (String.valueOf(appionNumber) == null) {
						themeOrder.setOrderStatus(0);// 已预约
					} else {
						if (course.getReservation() >= course.getPeopleNumber()) {
							themeOrder.setOrderStatus(4);// 预约人数已大于等于课程排课人数，此时修改订单状态为：排队中
						} else {
							themeOrder.setOrderStatus(0);// 已预约
						}
					}
					// 根据门店编号、行号和排课类型查询排课时间
					ArrayCourse courseBean = arrayCourseService.getStoreCourseTimeByFtl(course.getStoreNo(),course.getFtlRow(), course.getCourseType());
					if (courseBean != null) {
						try {
							Date corseDate = ECDateUtils.formatDateToDateNew(course.getCourseDate());
							String classTime = ECDateUtils.getCurrentDateAsString(corseDate);
							String startTime = ECDateUtils.farmatDateToHM(courseBean.getCourseStart());
							String endTime = ECDateUtils.farmatDateToHM(courseBean.getCourseEnd());
							themeOrder.setClassTime(classTime + " " + startTime + "-" + endTime);
						} catch (ParseException e) {
							log.info(e.toString());
						}
					}
					themeOrder.setPaymentMethod(3);// 其他支付
					themeOrder.setPaymentTime(new Date());
					themeOrder.setIsDeleted(0);
					ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
					if (arrayCourse != null) {
						themeOrder.setArrangeNo(arrayCourse.getArrayCourseNo());
						themeOrder.setCoachNo(arrayCourse.getCoachNo());
						themeOrder.setCoachName(arrayCourse.getCoachName());
						themeOrder.setCourseNo(arrayCourse.getCourseNo());
						Store store = storeService.getStore(arrayCourse.getStoreNo());// 门店
						if (store != null && store.getStoreStatus().equals(0)) {
							themeOrder.setStoreNo(store.getStoreNo());
							themeOrder.setStoreName(store.getStoreName());
							themeOrder.setCityId(store.getCityId());
							themeOrder.setCityName(store.getCityName());
						}
					}
					themeOrder.setPeopleNumber(Integer.parseInt(appoinNumber));
					themeOrder.setOrderAmount(Double.parseDouble(payAmount));// 应付金额
					themeOrder.setPayableAmount(0d);// 实付金额
					Member member = memberService.getMember(memberNo);
					if (member != null) {
						themeOrder.setMemberNo(memberNo);
						themeOrder.setMobilePhone(member.getMobilePhone());
					}

					if (StringUtils.isNotBlank(couponNo) && Integer.parseInt(courseBag) == 0 && balance == 0) {
						// 选择了优惠券全部抵扣
						// 计算优惠金额
						themeOrder.setCouponNo(couponNo);
						Coupon couponBeans = couponService.getCoupon(couponNo);
						if (couponBeans != null) {
							if (couponBeans.getCouponMethod() == 1) {// 折扣
								double zkAmount = ECCalculateUtils.mul(Double.parseDouble(payAmount),couponBeans.getDiscount());
								themeOrder.setDiscountAmount(zkAmount);
							} else if (couponBeans.getCouponMethod() == 2) {// 直减
								if(couponBeans.getDiscountAmount() >= Integer.parseInt(payAmount)){
									themeOrder.setDiscountAmount(Double.parseDouble(payAmount));
								}else{
									themeOrder.setDiscountAmount(couponBeans.getDiscountAmount());
								}
							} else if (couponBeans.getCouponMethod() == 4) {// 课程体验券
								// 课程单价
								double coursedj = ECCalculateUtils.div(Double.parseDouble(payAmount),Double.parseDouble(appoinNumber), 2);
								// 课时体验券优惠券金额
								double courseAmount = ECCalculateUtils.mul(coursedj,Double.parseDouble(couponBeans.getFreeCourseNumber()));
								themeOrder.setDiscountAmount(courseAmount);
							}
						}
						themeOrder.setBalanceDiscountAmount(0d);
						themeOrder.setCourseBagNum(0);
					} else if (StringUtils.isNotBlank(courseBag) && StringUtils.isBlank(couponNo) && balance == 0) {
						// 选择了课程包全部抵扣
						// 预约抵用的课时包
						if (Integer.parseInt(courseBag) > Integer.parseInt(appoinNumber)) {
							themeOrder.setCourseBagNum(Integer.parseInt(appoinNumber));
						} else {
							themeOrder.setCourseBagNum(Integer.parseInt(courseBag));
						}
						themeOrder.setDiscountAmount(0d);
						themeOrder.setBalanceDiscountAmount(0d);
					} else if (balance != 0 && StringUtils.isBlank(couponNo) && courseBag.equals("0")) {
						// 选择余额直接全部抵扣
						themeOrder.setDiscountAmount(0d);
						themeOrder.setBalanceDiscountAmount(balance);
						themeOrder.setCourseBagNum(0);
					} else if (StringUtils.isNotBlank(couponNo) && balance != 0 && courseBag.equals("0")) {
						// 选择优惠券和余额直接全部抵扣
						themeOrder.setCouponNo(couponNo);
						Coupon couponBeans = couponService.getCoupon(couponNo);
						if (couponBeans != null) {
							if (couponBeans.getCouponMethod() == 1) {// 折扣
								double zkAmount = ECCalculateUtils.mul(Double.parseDouble(payAmount),couponBeans.getDiscount());
								double diKouAmount = ECCalculateUtils.sub(Double.parseDouble(payAmount), zkAmount);
								themeOrder.setDiscountAmount(diKouAmount);
							} else if (couponBeans.getCouponMethod() == 2) {// 直减
								if(couponBeans.getDiscountAmount() >= Integer.parseInt(payAmount)){
									themeOrder.setDiscountAmount(Double.parseDouble(payAmount));
								}else{
									themeOrder.setDiscountAmount(couponBeans.getDiscountAmount());
								}
							} else if (couponBeans.getCouponMethod() == 4) {// 课程体验券
								// 课程单价
								double coursedj = ECCalculateUtils.div(Double.parseDouble(payAmount), Double.parseDouble(appoinNumber), 2);
								// 课时体验券优惠券金额
								double courseAmount = ECCalculateUtils.mul(coursedj, Double.parseDouble(couponBeans.getFreeCourseNumber()));
								themeOrder.setDiscountAmount(courseAmount);
							}
						}
						themeOrder.setBalanceDiscountAmount(balance);
						themeOrder.setCourseBagNum(0);
					} else if (StringUtils.isNotBlank(courseBag) && balance != 0 && StringUtils.isBlank(couponNo)) {
						// 选择课课程包和余额直接全部抵扣
						// 预约抵用的课时包
						if (Integer.parseInt(courseBag) > Integer.parseInt(appoinNumber)) {
							themeOrder.setCourseBagNum(Integer.parseInt(appoinNumber));
						} else {
							themeOrder.setCourseBagNum(Integer.parseInt(courseBag));
						}
						themeOrder.setDiscountAmount(0d);
						themeOrder.setBalanceDiscountAmount(balance);
					}
					themeOrderService.addThemeOrder(themeOrder, getOperator());

					// 根据统计属于该课程的订单预约排队人数小于0 生成预约表
					if (arrayCourse != null) {
						long lineNum = themeOrderDao.statCourseLineNumber(themeOrder.getArrangeNo());
						if (lineNum <= 0) {
							Bespeak bespeakBean = bespeakDao.getBespeakByArrayCourseNo(arrayCourse.getArrayCourseNo());
							if (bespeakBean == null) {
								// 主题馆订单预约支付后生成预约表
								Bespeak bespeak = new Bespeak();
								bespeak.setBespeakNo(bespeakService.generatePK());
								bespeak.setArrayCourseNo(arrayCourse.getArrayCourseNo());
								bespeak.setBespeakDate(new Date());
								bespeak.setPeopleNumber(arrayCourse.getPeopleNumber());
								bespeak.setReservation(Integer.parseInt(appoinNumber));
								bespeakService.addBespeak(bespeak, null);
							} else {
								Bespeak bp = new Bespeak();
								bp.setBespeakNo(bespeakBean.getBespeakNo());
								int appionNum = Integer.parseInt(appoinNumber);
								bp.setReservation(appionNum + bespeakBean.getReservation());
								bespeakService.updateBespeak(bp, null);
							}

							// 生成预约表后更新排课表中预约人数
							ArrayCourse courses = new ArrayCourse();
							courses.setArrayCourseNo(arrayCourse.getArrayCourseNo());
							int reservation = 0;
							reservation = Integer.parseInt(appoinNumber);
							courses.setReservation(reservation + arrayCourse.getReservation());
							arrayCourseService.updateArrayCourse(courses, null);
						} else {
							// 否则更新排课表排课人数
							ArrayCourse courses = new ArrayCourse();
							courses.setArrayCourseNo(arrayCourse.getArrayCourseNo());
							int linNum = 0;
							linNum = themeOrder.getPeopleNumber();
							courses.setLineUp(linNum + arrayCourse.getLineUp());
							arrayCourseService.updateArrayCourse(courses, null);
						}
					}
				}else{
					ThemeOrder theme = themeOrderService.getThemeOrder(themeOrderNo);
					if(theme != null){
						ThemeOrder themeOrder = new ThemeOrder();
						themeOrder.setThemeOrderNo(theme.getThemeOrderNo());
						themeOrder.setPayStatus(1);// 已支付
						themeOrder.setIsSendMsg(0);// 未发送短信
						// 根据订单中的排课编号统计属于该课程的预约人数
						long appionNumber = themeOrderDao.statCourseAppoinNumber(arrayCourseNo);
						// 查询该课程的排课人数
						ArrayCourse course = arrayCourseService.getArrayCourse(arrayCourseNo);
						if (String.valueOf(appionNumber) == null) {
							themeOrder.setOrderStatus(0);// 已预约
						} else {
							if (course.getReservation() > course.getPeopleNumber()) {
								themeOrder.setOrderStatus(4);// 预约人数已大于等于课程排课人数，此时修改订单状态为：排队中
							} else {
								themeOrder.setOrderStatus(0);// 已预约
							}
						}
						// 根据门店编号、行号和排课类型查询排课时间
						ArrayCourse courseBean = arrayCourseService.getStoreCourseTimeByFtl(course.getStoreNo(),course.getFtlRow(), course.getCourseType());
						if (courseBean != null) {
							try {
								Date corseDate = ECDateUtils.formatDateToDateNew(course.getCourseDate());
								String classTime = ECDateUtils.getCurrentDateAsString(corseDate);
								String startTime = ECDateUtils.farmatDateToHM(courseBean.getCourseStart());
								String endTime = ECDateUtils.farmatDateToHM(courseBean.getCourseEnd());
								themeOrder.setClassTime(classTime + " " + startTime + "-" + endTime);
							} catch (ParseException e) {
								log.info(e.toString());
							}
						}
						themeOrder.setPaymentMethod(3);// 其他支付
						themeOrder.setPaymentTime(new Date());
						themeOrder.setIsDeleted(0);
						ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
						if (arrayCourse != null) {
							themeOrder.setArrangeNo(arrayCourse.getArrayCourseNo());
							themeOrder.setCoachNo(arrayCourse.getCoachNo());
							themeOrder.setCoachName(arrayCourse.getCoachName());
							themeOrder.setCourseNo(arrayCourse.getCourseNo());
							Store store = storeService.getStore(arrayCourse.getStoreNo());// 门店
							if (store != null && store.getStoreStatus().equals(0)) {
								themeOrder.setStoreNo(store.getStoreNo());
								themeOrder.setStoreName(store.getStoreName());
								themeOrder.setCityId(store.getCityId());
								themeOrder.setCityName(store.getCityName());
							}
						}
						themeOrder.setPeopleNumber(Integer.parseInt(appoinNumber));
						themeOrder.setOrderAmount(Double.parseDouble(payAmount));// 应付金额
						themeOrder.setPayableAmount(0d);// 实付金额
						Member member = memberService.getMember(memberNo);
						if (member != null) {
							themeOrder.setMemberNo(memberNo);
							themeOrder.setMobilePhone(member.getMobilePhone());
						}

						if (StringUtils.isNotBlank(couponNo) && Integer.parseInt(courseBag) == 0 && balance == 0) {
							// 选择了优惠券全部抵扣
							// 计算优惠金额
							themeOrder.setCouponNo(couponNo);
							Coupon couponBeans = couponService.getCoupon(couponNo);
							if (couponBeans != null) {
								if (couponBeans.getCouponMethod() == 1) {// 折扣
									double zkAmount = ECCalculateUtils.mul(Double.parseDouble(payAmount),couponBeans.getDiscount());
									themeOrder.setDiscountAmount(zkAmount);
								} else if (couponBeans.getCouponMethod() == 2) {// 直减
									if(couponBeans.getDiscountAmount() >= Integer.parseInt(payAmount)){
										themeOrder.setDiscountAmount(Double.parseDouble(payAmount));
									}else{
										themeOrder.setDiscountAmount(couponBeans.getDiscountAmount());
									}
								} else if (couponBeans.getCouponMethod() == 4) {// 课程体验券
									// 课程单价
									double coursedj = ECCalculateUtils.div(Double.parseDouble(payAmount),Double.parseDouble(appoinNumber), 2);
									// 课时体验券优惠券金额
									double courseAmount = ECCalculateUtils.mul(coursedj,Double.parseDouble(couponBeans.getFreeCourseNumber()));
									themeOrder.setDiscountAmount(courseAmount);
								}
							}
							themeOrder.setBalanceDiscountAmount(0d);
							themeOrder.setCourseBagNum(0);
						} else if (StringUtils.isNotBlank(courseBag) && StringUtils.isBlank(couponNo) && balance == 0) {
							// 选择了课程包全部抵扣
							// 预约抵用的课时包
							if (Integer.parseInt(courseBag) > Integer.parseInt(appoinNumber)) {
								themeOrder.setCourseBagNum(Integer.parseInt(appoinNumber));
							} else {
								themeOrder.setCourseBagNum(Integer.parseInt(courseBag));
							}
							themeOrder.setDiscountAmount(0d);
							themeOrder.setBalanceDiscountAmount(0d);
						} else if (balance != 0 && StringUtils.isBlank(couponNo) && courseBag.equals("0")) {
							// 选择余额直接全部抵扣
							themeOrder.setDiscountAmount(0d);
							themeOrder.setBalanceDiscountAmount(balance);
							themeOrder.setCourseBagNum(0);
						} else if (StringUtils.isNotBlank(couponNo) && balance != 0 && courseBag.equals("0")) {
							// 选择优惠券和余额直接全部抵扣
							themeOrder.setCouponNo(couponNo);
							Coupon couponBeans = couponService.getCoupon(couponNo);
							if (couponBeans != null) {
								if (couponBeans.getCouponMethod() == 1) {// 折扣
									double zkAmount = ECCalculateUtils.mul(Double.parseDouble(payAmount),couponBeans.getDiscount());
									double diKouAmount = ECCalculateUtils.sub(Double.parseDouble(payAmount), zkAmount);
									themeOrder.setDiscountAmount(diKouAmount);
								} else if (couponBeans.getCouponMethod() == 2) {// 直减
									if(couponBeans.getDiscountAmount() >= Integer.parseInt(payAmount)){
										themeOrder.setDiscountAmount(Double.parseDouble(payAmount));
									}else{
										themeOrder.setDiscountAmount(couponBeans.getDiscountAmount());
									}
								} else if (couponBeans.getCouponMethod() == 4) {// 课程体验券
									// 课程单价
									double coursedj = ECCalculateUtils.div(Double.parseDouble(payAmount), Double.parseDouble(appoinNumber), 2);
									// 课时体验券优惠券金额
									double courseAmount = ECCalculateUtils.mul(coursedj, Double.parseDouble(couponBeans.getFreeCourseNumber()));
									themeOrder.setDiscountAmount(courseAmount);
								}
							}
							themeOrder.setBalanceDiscountAmount(balance);
							themeOrder.setCourseBagNum(0);
						} else if (StringUtils.isNotBlank(courseBag) && balance != 0 && StringUtils.isBlank(couponNo)) {
							// 选择课课程包和余额直接全部抵扣
							// 预约抵用的课时包
							if (Integer.parseInt(courseBag) > Integer.parseInt(appoinNumber)) {
								themeOrder.setCourseBagNum(Integer.parseInt(appoinNumber));
							} else {
								themeOrder.setCourseBagNum(Integer.parseInt(courseBag));
							}
							themeOrder.setDiscountAmount(0d);
							themeOrder.setBalanceDiscountAmount(balance);
						}
						themeOrderService.updateThemeOrder(themeOrder, getOperator());
					}
				}
			}
		} catch (Exception e) {
			log.info(e.toString());
		}
	}

	/**
	 * 用户页面点击支付后 若实付款金额为0.0 则不跳转第三方接口支付，直接根据入参修改相关表的数据
	 * @param memberNo(会员编号)、appoinNumber(预约人数)、couponNo(选择的优惠券编号)、courseBag(课时包)
	 * balance(余额) payAmount(应付款)、arrayCourseNo（排课编号）、支付状态 payStatus、订单编号 themeOrderNo
	 */
	@RequestMapping("updateCourseCorrelateData")
	@ResponseBody
	public ResultInfo<String> updateCourseCorrelateData(String memberNo, String appoinNumber, String couponNo, String courseBag, 
			double balance, String payAmount, String arrayCourseNo, String payStatus, String themeOrderNo) {
		ResultInfo<String> result = new ResultInfo<String>();
		synchronized (this) {
			//支付生成订单之前校验课程目前预约情况
			ResultInfo<String> resultInfo = this.checkAppoinArrayCourse(memberNo, arrayCourseNo, appoinNumber,payStatus);
			if(resultInfo.getCode().equals("0")){
				result.setCode(Constant.FAIL);
				result.setMsg(resultInfo.getMsg());
				return result;
			}// 判断当实付款未0.0时，应分5种情况去修改表数据
			try {
				// 用户选择的人数转换为课时包节数
				int oppionBag = Integer.parseInt(appoinNumber);
				// 1、选择优惠券后，实付金额为0.0
				if (StringUtils.isNotBlank(couponNo) && courseBag.equals("0") && balance == 0) {
					// 生成订单
					generateOrders(memberNo, appoinNumber, couponNo, courseBag, balance, payAmount, arrayCourseNo,themeOrderNo);
					// 修改优惠券
					Coupon coupon = couponService.getCoupon(couponNo);
					if (coupon != null) {
						Coupon couponBean = new Coupon();
						couponBean.setCouponNo(coupon.getCouponNo());
						couponBean.setUsedStatus(1);// 修改为已使用
						couponService.updateCoupon(couponBean, getOperator());
						result.setCode(OrderConstant.success_code);
						result.setMsg("会员使用优惠券已全额抵扣成功！无需支付其他费用。");
					}
				} else if (StringUtils.isBlank(couponNo) && StringUtils.isNotBlank(courseBag) && balance == 0) {
					// 2、只选择课时包，课时包足够时 实付金额为0.0
					// 生成订单
					this.generateOrders(memberNo, appoinNumber, couponNo, courseBag, balance, payAmount, arrayCourseNo,themeOrderNo);
					// 查询会员课时包
					MemberBalance memberBalance = memberBalanceService.getMemberBalance(memberNo);
					if (memberBalance != null) {
						MemberBalance balanceBean = new MemberBalance();
						balanceBean.setMemberNo(memberBalance.getMemberNo());
						// 计算剩余课时包
						int surplusBag = Integer.parseInt(courseBag) - Integer.parseInt(appoinNumber);
						balanceBean.setCourseNumber(surplusBag);
						memberBalanceService.updateMemberBalance(balanceBean, getOperator());
						result.setCode(OrderConstant.success_code);
						result.setMsg("会员使用课时包抵扣成功！无需支付其他费用。");
					}
				} else if (StringUtils.isBlank(couponNo) && courseBag.equals("0") && balance != 0) {
					// 3.当选择余额足够抵扣课程费用时，实际付款为0.0
					// 生成订单
					this.generateOrders(memberNo, appoinNumber, couponNo, courseBag, balance, payAmount, arrayCourseNo,themeOrderNo);

					MemberBalance memberBalance = memberBalanceService.getMemberBalance(memberNo);
					if (memberBalance != null) {
						MemberBalance balanceBean = new MemberBalance();
						balanceBean.setMemberNo(memberBalance.getMemberNo());
						balanceBean.setMemberBalance(ECCalculateUtils.sub(memberBalance.getMemberBalance(), balance));
						memberBalanceService.updateMemberBalance(balanceBean, getOperator());
						result.setCode(OrderConstant.success_code);
						result.setMsg("会员使用余额抵扣成功！无需支付其他费用。");
					}
				} else if (StringUtils.isNotBlank(couponNo) && courseBag.equals("0") && balance != 0) {
					// 4.当选择优惠券和余额后，实际支付金额为0.0时
					// 生成订单
					generateOrders(memberNo, appoinNumber, couponNo, courseBag, balance, payAmount, arrayCourseNo,themeOrderNo);

					Coupon coupon = couponService.getCoupon(couponNo);
					if (coupon != null) {
						Coupon couponBean = new Coupon();
						couponBean.setCouponNo(coupon.getCouponNo());
						couponBean.setUsedStatus(1);// 修改为已使用
						couponService.updateCoupon(couponBean, getOperator());
					}
					MemberBalance memberBalance2 = memberBalanceService.getMemberBalance(memberNo);
					if (memberBalance2 != null) {
						MemberBalance balanceBean = new MemberBalance();
						balanceBean.setMemberNo(memberBalance2.getMemberNo());
						balanceBean.setMemberBalance(ECCalculateUtils.sub(memberBalance2.getMemberBalance(), balance));
						memberBalanceService.updateMemberBalance(balanceBean, getOperator());
					}
					result.setCode(OrderConstant.success_code);
					result.setMsg("会员使用优惠券和余额抵扣成功！无需支付其他费用。");
				} else if (StringUtils.isBlank(couponNo) && StringUtils.isNotBlank(courseBag) && balance != 0) {
					// 会员选择课时包和余额抵扣，实际支付金额为0.0
					// 生成订单
					generateOrders(memberNo, appoinNumber, couponNo, courseBag, balance, payAmount, arrayCourseNo,themeOrderNo);
					MemberBalance memberBalance3 = memberBalanceService.getMemberBalance(memberNo);
					if (memberBalance3 != null) {
						MemberBalance balanceBean = new MemberBalance();
						balanceBean.setMemberNo(memberBalance3.getMemberNo());
						// 计算剩余课时包
						// 抵用的课时包
						int surplusBag = oppionBag - Integer.parseInt(courseBag);
						balanceBean.setCourseNumber(memberBalance3.getCourseNumber() - surplusBag);
						// 计算剩余余额
						balanceBean.setMemberBalance(ECCalculateUtils.sub(memberBalance3.getMemberBalance(), balance));
						memberBalanceService.updateMemberBalance(balanceBean, getOperator());
					}
					result.setCode(OrderConstant.success_code);
					result.setMsg("会员使用课时包和余额抵扣成功！无需支付其他费用。");
				} else {
					result.setCode(OrderConstant.fail_code);
					result.setMsg("支付失败！优惠券和课程包不能同时选择！");
				}
			} catch (Exception e) {
				result.setCode(Constant.FAIL);
				result.setMsg(e.toString());
			}
		}
		return result;
	}

	/**
	 * 主题馆订单详情
	 * @param themeOrderNo 订单编号
	 */
	@RequestMapping("viewThemeOrderDetail")
	@ResponseBody
	public ResultInfo<ThemeOrderDetailVo> viewThemeOrderDetail(String themeOrderNo) {
		ResultInfo<ThemeOrderDetailVo> result = new ResultInfo<ThemeOrderDetailVo>();
		try {
			ThemeOrder themeOrder = themeOrderService.getThemeOrder(themeOrderNo);
			if (themeOrder != null) {
				ThemeOrderDetailVo orderDetail = new ThemeOrderDetailVo();
				orderDetail.setThemeOrderNo(themeOrderNo);
				orderDetail.setArrayCourseNo(themeOrder.getArrangeNo());
				orderDetail.setStoreName(themeOrder.getStoreName());
				orderDetail.setClassTime(themeOrder.getClassTime());
				orderDetail.setAppionNumber(String.valueOf(themeOrder.getPeopleNumber()));
				orderDetail.setCouponAmount(themeOrder.getDiscountAmount());
				orderDetail.setBalanceOffset(themeOrder.getBalanceDiscountAmount());
				Course course = courseService.getCourse(themeOrder.getCourseNo());
				if (course != null) {
					orderDetail.setCourseName(course.getChineseName());
				}
				orderDetail.setOrderStatus(String.valueOf(themeOrder.getOrderStatus()));
				orderDetail.setPayMethod(String.valueOf(themeOrder.getPaymentMethod()));
				orderDetail.setRealPayAmount(themeOrder.getPayableAmount());
				orderDetail.setTotalAmount(themeOrder.getOrderAmount());
				if (themeOrder.getCancelNumber() == null) {
					orderDetail.setCancelNumber(0);
				} else {
					orderDetail.setCancelNumber(themeOrder.getCancelNumber());
				}
				if (themeOrder.getRefundAmount() == null) {
					orderDetail.setRefundAmount(0d);
				} else {
					orderDetail.setRefundAmount(themeOrder.getRefundAmount());
				}
				if (themeOrder.getCourseBagNum() == null || themeOrder.getCourseBagNum() == 0) {
					orderDetail.setCourseBag(0);
				} else {
					orderDetail.setCourseBag(themeOrder.getCourseBagNum());
				}

				result.setCode(Constant.SECCUESS);
				result.setMsg(Constant.YES_RECORD);
				result.setData(orderDetail);
			} else {
				result.setCode(Constant.NO_RESULT);
				result.setMsg(Constant.NO_RECORD);
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.toString());
		}
		return result;
	}
	
	/**
	 * 订单支付前校验预约课程人数
	 */
	private ResultInfo<String> checkAppoinArrayCourse(String memberNo, String arrayCourseNo, String oppionNumber, String payStatus) {
		ResultInfo<String> result = new ResultInfo<String>();
			try {
				// 查询会员是否为黑名单
				Member member = memberService.getMember(memberNo);
				if (member != null && member.getIsBlacklist() == 0) {
					//查询会员是否存在未支付的订单
					if(StringUtils.isBlank(payStatus)){
						ThemeOrder themeOrder = new ThemeOrder();
						themeOrder.setMemberNo(member.getMemberNo());
						themeOrder.setPayStatus(0);//未支付
						List<ThemeOrder> listThemeOrder = themeOrderService.getThemeOrderList(new Query(themeOrder));
						if(listThemeOrder != null && listThemeOrder.size() > 0){
							result.setCode(Constant.FAIL);
							result.setMsg("很抱歉、当前有待支付的订单，请跳转至主题订单列表进行支付！");
						}else{
							ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
							SysParam param = sysParamService.getByParamKey("COURSE_LINE_UP");
							int paramNum = Integer.parseInt(param.getParamValue());
							if(arrayCourse != null){
								if (arrayCourse.getReservation() != null) {
									if(arrayCourse.getReservation() == arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() == 0){
										result.setCode(Constant.SECCUESS);
									}else if(arrayCourse.getReservation() == arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() == paramNum){
										result.setCode(Constant.FAIL);
										result.setMsg("很抱歉、此课程预约人数和排队人数都已满，请您选择其他课程预约...");
									}else if(arrayCourse.getReservation() == arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() < paramNum){
										result.setCode(Constant.SECCUESS);
									}else{
										result.setCode(Constant.SECCUESS);
									}
								}else {
									result.setCode(Constant.SECCUESS);
								}
							}
						}
					}else{
						//待支付订单校验
						ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
						SysParam param = sysParamService.getByParamKey("COURSE_LINE_UP");
						int paramNum = Integer.parseInt(param.getParamValue());
						if(arrayCourse != null){
							if (arrayCourse.getReservation() != null) {
								if(arrayCourse.getReservation() == arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() == 0){
									result.setCode(Constant.FAIL);
									result.setMsg("此课程预约人数已满，请您去预约排队等候...");
								}else if(arrayCourse.getReservation() == arrayCourse.getPeopleNumber() && arrayCourse.getLineUp() == paramNum){
									result.setCode(Constant.FAIL);
									result.setMsg("很抱歉、此课程预约人数和排队人数都已满，请您选择其他课程预约...");
								}else{
									result.setCode(Constant.SECCUESS);
								}
							}else {
								result.setCode(Constant.SECCUESS);
							}
						}
					}
				} else {
					result.setCode(Constant.FAIL);
					result.setMsg("对不起、您已经被移入黑名单，暂时不能预约课程，请联系客服...");
				}
			} catch (Exception e) {
				result.setCode(Constant.FAIL);
				result.setMsg(e.getMessage());
			}
		return result;
	}
}