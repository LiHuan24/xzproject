package cn.com.shopec.mgt.themepavilion.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.themepavilion.common.ThemePavilionConstant;
import cn.com.shopec.core.themepavilion.model.ArrayCourse;
import cn.com.shopec.core.themepavilion.model.Coach;
import cn.com.shopec.core.themepavilion.model.CourseBean;
import cn.com.shopec.core.themepavilion.model.TemplateCourse;
import cn.com.shopec.core.themepavilion.service.ArrayCourseService;
import cn.com.shopec.core.themepavilion.service.CoachService;
import cn.com.shopec.core.themepavilion.service.TemplateCourseService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 共享健身房：主题馆-排课列表
 * 
 * @author LiHuan Date 
 * 2017年11月6日 下午4:17:21
 */
@Controller
@RequestMapping("arrayCourse")
public class ArrayCourseController extends BaseController {

	private static final Log log = LogFactory.getLog(TemplateCourseController.class);

	@Resource
	private ArrayCourseService arrayCourseService;
	@Resource
	private TemplateCourseService templateCourseService;
	@Resource
	private StoreService storeService;
	@Resource
	private CoachService coachService;

	/**
	 * 进入排课列表
	 */
	@RequestMapping("toArrayCourseList")
	public String toArrayCourseList(ModelMap modelMap, String storeNo) throws Exception {
		log.info("进入排课列表");
		// 查询门店信息
		Store store = new Store();
		store.setStoreStatus(Constant.STORE_START_STATUS);
		store.setStoreType(ThemePavilionConstant.THEME_STORE);
		List<Store> listStore = storeService.getStoreList(new Query(store));
		if (listStore != null && listStore.size() > 0) {
			modelMap.put("listStore", listStore);
		}
		if (StringUtils.isNotBlank(storeNo)) {
			modelMap.put("storeNo", storeNo);
			// 查看主题馆本周和下周的排课课程列表数据
			ArrayCourse arrayCourse = new ArrayCourse();
			// 本周
			arrayCourse.setStoreNo(storeNo);
			arrayCourse.setCourseType(ThemePavilionConstant.THIS_WEEK);

			// 查询该主题馆本周是否已排课
			List<ArrayCourse> listArrayCourse = arrayCourseService.getArrayCourseListRow(new Query(arrayCourse));
			modelMap.put("listArrayCourse", listArrayCourse);

			List<CourseBean> listThisArrayCourse = this.listArrayCourse(arrayCourse, storeNo);
			if (listThisArrayCourse != null && listThisArrayCourse.size() > 0) {
				modelMap.put("listThisArrayCourse", listThisArrayCourse);
			}
			// 下周
			arrayCourse.setStoreNo(storeNo);
			arrayCourse.setCourseType(ThemePavilionConstant.NEXT_WEEK);
			List<CourseBean> listNextArrayCourse = this.listArrayCourse(arrayCourse, storeNo);
			if (listNextArrayCourse != null && listNextArrayCourse.size() > 0) {
				modelMap.put("listNextArrayCourse", listNextArrayCourse);
			}
		}
		return "/themepavilion/array_course_list";
	}

	// 依次根据过滤调条件查询主题馆的本周课程和下周课程
	private List<CourseBean> listArrayCourse(ArrayCourse arrayCourse, String storeNo) throws Exception {
		List<CourseBean> ls = new ArrayList<CourseBean>();
		// 首先根据保存的行号分组查询一共多少行
		List<ArrayCourse> listArrayCourse = arrayCourseService.getArrayCourseListRow(new Query(arrayCourse));
		if (listArrayCourse != null && listArrayCourse.size() > 0) {
			for (int i = 0; i < listArrayCourse.size(); i++) {
				ArrayCourse course = new ArrayCourse();
				course.setStoreNo(storeNo);
				course.setFtlRow(listArrayCourse.get(i).getFtlRow());
				if (arrayCourse.getCourseType() == ThemePavilionConstant.THIS_WEEK) {
					course.setCourseType(ThemePavilionConstant.THIS_WEEK);
				} else {
					course.setCourseType(ThemePavilionConstant.NEXT_WEEK);
				}

				// 再根据行号循环获取一行的所有数据
				List<ArrayCourse> list = arrayCourseService.getArrayCourseList(new Query(course));
				CourseBean c = new CourseBean();
				if (list.size() > 0) {
					for (ArrayCourse t : list) {
						if (StringUtils.isNotBlank(t.getCourseWeek())) {
							if (t.getCourseWeek().equals("周一")) {
								c.setMonName(t.getChineseName());
								c.setMonId(t.getArrayCourseNo());
							} else if (t.getCourseWeek().equals("周二")) {
								c.setTueName(t.getChineseName());
								c.setTueId(t.getArrayCourseNo());
							} else if (t.getCourseWeek().equals("周三")) {
								c.setWedName(t.getChineseName());
								c.setWedId(t.getArrayCourseNo());
							} else if (t.getCourseWeek().equals("周四")) {
								c.setThuName(t.getChineseName());
								c.setThuId(t.getArrayCourseNo());
							} else if (t.getCourseWeek().equals("周五")) {
								c.setFriName(t.getChineseName());
								c.setFriId(t.getArrayCourseNo());
							} else if (t.getCourseWeek().equals("周六")) {
								c.setSatName(t.getChineseName());
								c.setSatId(t.getArrayCourseNo());
							} else if (t.getCourseWeek().equals("周日")) {
								c.setSunName(t.getChineseName());
								c.setSunId(t.getArrayCourseNo());
							}
						}
						if (StringUtils.isBlank(t.getChineseName())) {
							String showDate = "";
							String startTime = ECDateUtils.farmatDateToHM(t.getCourseStart());
							String endTime = ECDateUtils.farmatDateToHM(t.getCourseEnd());
							showDate = startTime + "至" + endTime;
							c.setShowDate(showDate);
						}
						if (arrayCourse.getCourseType() == 0) {
							c.setWeek(ThemePavilionConstant.THIS_WEEK);
						} else {
							c.setWeek(ThemePavilionConstant.NEXT_WEEK);
						}
					}
				}
				ls.add(c);
			}
		}
		return ls;
	}

	/**
	 * 计算排课周期的日期
	 * 
	 * @throws ParseException
	 */
	private Date getCourseWeekDate(int week, String courseWeek) throws ParseException {
		Date courseDate = null;
		if (courseWeek.equals("周一")) {
			Calendar cal = ECDateUtils.getDateByThisDay(week, 1);
			courseDate = ECDateUtils.formatDateToDate(cal.getTime());
		} else if (courseWeek.equals("周二")) {
			Calendar cal = ECDateUtils.getDateByThisDay(week, 2);
			courseDate = ECDateUtils.formatDateToDate(cal.getTime());
		} else if (courseWeek.equals("周三")) {
			Calendar cal = ECDateUtils.getDateByThisDay(week, 3);
			courseDate = ECDateUtils.formatDateToDate(cal.getTime());
		} else if (courseWeek.equals("周四")) {
			Calendar cal = ECDateUtils.getDateByThisDay(week, 4);
			courseDate = ECDateUtils.formatDateToDate(cal.getTime());
		} else if (courseWeek.equals("周五")) {
			Calendar cal = ECDateUtils.getDateByThisDay(week, 5);
			courseDate = ECDateUtils.formatDateToDate(cal.getTime());
		} else if (courseWeek.equals("周六")) {
			Calendar cal = ECDateUtils.getDateByThisDay(week, 6);
			courseDate = ECDateUtils.formatDateToDate(cal.getTime());
		} else if (courseWeek.equals("周日")) {
			Calendar cal = ECDateUtils.getDateByThisDay(week, 7);
			courseDate = ECDateUtils.formatDateToDate(cal.getTime());
		}

		return courseDate;
	}

	/**
	 * 管理员进行主题馆课程排课操作
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("saveArrayCourse")
	@ResponseBody
	public ResultInfo<ArrayCourse> saveArrayCourse(@ModelAttribute("arrayCourse") ArrayCourse arrayCourse)throws ParseException {
		ResultInfo<ArrayCourse> resultInfo = new ResultInfo<ArrayCourse>();
		// 查询门店信息
		Store store = storeService.getStore(arrayCourse.getStoreNo());
		//排课之前根据门店编号查询该门店的排课课程模板是否有数据，若有则继续生产排课数据，否则不能
		TemplateCourse templates = new TemplateCourse();
		templates.setStoreNo(arrayCourse.getStoreNo());
		List<TemplateCourse> listTemplate = templateCourseService.getTemplateCourseList(new Query(templates));
		if(listTemplate != null && listTemplate.size() > 0){
			// 若本周课程已排，不能重复排课，管理员到每个周五进行下周的排课操作
			if (arrayCourse.getCourseType().equals(ThemePavilionConstant.THIS_WEEK)) {
				ArrayCourse arrayCourseBean = new ArrayCourse();
				arrayCourseBean.setCourseType(ThemePavilionConstant.THIS_WEEK);
				arrayCourseBean.setStoreNo(arrayCourse.getStoreNo());
				List<ArrayCourse> listArrayCourse = arrayCourseService.getArrayCourseList(new Query(arrayCourseBean));
				if (listArrayCourse.size() > 0) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("该主题馆本周课程已经排好，不能重复排课！");
				} else {
					TemplateCourse templateCourse = new TemplateCourse();
					templateCourse.setStoreNo(arrayCourse.getStoreNo());
					// 查询排课模板表
					List<TemplateCourse> listTemplateCourse = templateCourseService.getTemplateCourseList(new Query(templateCourse));
					if (listTemplateCourse != null && listTemplateCourse.size() > 0) {
						for (TemplateCourse template : listTemplateCourse) {
							ArrayCourse course = new ArrayCourse();
							course.setCourseNo(template.getCourseNo());
							course.setChineseName(template.getChineseName());
							course.setCourseWeek(template.getCourseWeek());
							// 处理日期
							if (template.getCourseStart() == null) {
								// 根据当前星期几获取当前日期
								Date thisWeekDate = this.getCourseWeekDate(ThemePavilionConstant.THIS_WEEK,template.getCourseWeek());
								course.setCourseDate(thisWeekDate);
								// 设置课程的当前日期 开始时间、结束时间
								String startDate = ECDateUtils.toTimeString(template.getCourseStart());
								String endDate = ECDateUtils.toTimeString(template.getCourseEnd());
								String weekDate = ECDateUtils.toString(thisWeekDate);
								String courseStartDate = weekDate + " " + startDate;
								String courseEndDate = weekDate + " " + endDate;
								course.setCourseStart(ECDateUtils.parseDate(courseStartDate));
								course.setCourseEnd(ECDateUtils.parseDate(courseEndDate));

							} else {
								course.setCourseDate(template.getCourseDate());
								course.setCourseStart(template.getCourseStart());
								course.setCourseEnd(template.getCourseEnd());
							}
							course.setCoachNo(template.getCoachNo());
							course.setCoachName(template.getCoachName());
							course.setPeopleNumber(template.getPeopleNumber());
							course.setReservation(0);
							course.setLineUp(0);
							course.setFtlRow(template.getFtlRow());
							course.setCourseType(arrayCourse.getCourseType());
							course.setStoreNo(arrayCourse.getStoreNo());
							if (store != null) {
								course.setStoreName(store.getStoreName());
								course.setCityId(store.getCityId());
								course.setStoreAreaCode(store.getAddrRegion2Id());
							}
							course.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_NO);
							resultInfo = arrayCourseService.addArrayCourse(course, getOperator());
							resultInfo.setCode(Constant.SECCUESS);
						}
					}
				}
			} else {
				// 进行下周排课
				ArrayCourse arrayCourseBean = new ArrayCourse();
				arrayCourseBean.setCourseType(ThemePavilionConstant.NEXT_WEEK);
				arrayCourseBean.setStoreNo(arrayCourse.getStoreNo());
				List<ArrayCourse> listArrayCourse = arrayCourseService.getArrayCourseList(new Query(arrayCourseBean));
				if (listArrayCourse.size() > 0) {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("该主题馆下周课程已经排好，不能重复排课！");
				} else {
					TemplateCourse templateCourse = new TemplateCourse();
					templateCourse.setStoreNo(arrayCourse.getStoreNo());
					// 查询排课模板表
					List<TemplateCourse> listTemplateCourse = templateCourseService.getTemplateCourseList(new Query(templateCourse));
					if (listTemplateCourse != null && listTemplateCourse.size() > 0) {
						for (TemplateCourse template : listTemplateCourse) {
							ArrayCourse course = new ArrayCourse();
							course.setCourseNo(template.getCourseNo());
							course.setChineseName(template.getChineseName());
							course.setCourseWeek(template.getCourseWeek());
							// 处理日期
							if (template.getCourseStart() == null) {
								// 根据当前星期几获取当前日期
								Date thisWeekDate = this.getCourseWeekDate(ThemePavilionConstant.NEXT_WEEK,template.getCourseWeek());
								course.setCourseDate(thisWeekDate);
								// 设置课程的当前日期 开始时间、结束时间
								String startDate = ECDateUtils.toTimeString(template.getCourseStart());
								String endDate = ECDateUtils.toTimeString(template.getCourseEnd());
								String weekDate = ECDateUtils.toString(thisWeekDate);
								String courseStartDate = weekDate + " " + startDate;
								String courseEndDate = weekDate + " " + endDate;
								course.setCourseStart(ECDateUtils.parseDate(courseStartDate));
								course.setCourseEnd(ECDateUtils.parseDate(courseEndDate));

							} else {
								course.setCourseDate(template.getCourseDate());
								course.setCourseStart(template.getCourseStart());
								course.setCourseEnd(template.getCourseEnd());
							}

							course.setCoachNo(template.getCoachNo());
							course.setCoachName(template.getCoachName());
							course.setPeopleNumber(template.getPeopleNumber());
							course.setReservation(0);
							course.setLineUp(0);
							course.setFtlRow(template.getFtlRow());
							course.setCourseType(arrayCourse.getCourseType());
							course.setStoreNo(arrayCourse.getStoreNo());
							if (store != null) {
								course.setStoreName(store.getStoreName());
								course.setCityId(store.getCityId());
								course.setStoreAreaCode(store.getAddrRegion2Id());
							}
							course.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_NO);
							resultInfo = arrayCourseService.addArrayCourse(course, getOperator());
							resultInfo.setCode(Constant.SECCUESS);
						}
					}
				}
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("很抱歉，该主题馆暂无排课模板数据，请先配置好模板再进行排课！");
		}
		return resultInfo;
	}

	/**
	 * 去修改该排课课程的教练
	 */
	@RequestMapping("modifiyCoach")
	@ResponseBody
	public ResultInfo<ArrayCourse> modifiyCoach(String arrayCourseId) {
		ResultInfo<ArrayCourse> resultInfo = new ResultInfo<ArrayCourse>();
		ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseId);
		if (arrayCourse != null) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(arrayCourse);
		}
		return resultInfo;
	}

	/**
	 * 修改主题馆课程已选择的教练信息
	 */
	@RequestMapping("updateArrayCourseCoach")
	@ResponseBody
	public ResultInfo<ArrayCourse> updateArrayCourseCoach(@ModelAttribute("arrayCourse") ArrayCourse arrayCourse) {
		ResultInfo<ArrayCourse> resultInfo = new ResultInfo<ArrayCourse>();
		// 查询重新选择的教练信息
		Coach coach = coachService.getCoach(arrayCourse.getCoachNo());
		if (coach != null) {
			ArrayCourse course = new ArrayCourse();
			course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
			course.setCoachNo(coach.getCoachNo());
			course.setCoachName(coach.getCoachName());
			// 修改
			resultInfo = arrayCourseService.updateArrayCourse(course, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
		}
		return resultInfo;
	}

	/**
	 * 主题馆本周课程发布
	 */
	@RequestMapping("postedArrayCourse")
	@ResponseBody
	public ResultInfo<ArrayCourse> postedArrayCourse(String selectStoreNo) {
		ResultInfo<ArrayCourse> resultInfo = new ResultInfo<ArrayCourse>();
		ArrayCourse arrayCourse = new ArrayCourse();
		arrayCourse.setStoreNo(selectStoreNo);
		arrayCourse.setCourseType(ThemePavilionConstant.THIS_WEEK);
		List<ArrayCourse> list = arrayCourseService.getArrayCourseList(new Query(arrayCourse));
		for (ArrayCourse array : list) {
			// 查询该主题馆本周课程后，依次循环发布，发布后APP端可浏览
			array.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);
			resultInfo = arrayCourseService.updateArrayCourse(array, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
		}
		return resultInfo;
	}

	/**
	 * 发布之前判断该主题馆课程是否已经发布
	 */
	@RequestMapping("uniqueArrayCourse")
	@ResponseBody
	public ResultInfo<ArrayCourse> uniqueArrayCourse(String storeNo) {
		ResultInfo<ArrayCourse> resultInfo = new ResultInfo<ArrayCourse>();
		ArrayCourse arrayCourse = new ArrayCourse();
		arrayCourse.setStoreNo(storeNo);
		arrayCourse.setCourseType(ThemePavilionConstant.THIS_WEEK);
		List<ArrayCourse> list = arrayCourseService.getArrayCourseList(new Query(arrayCourse));
		for (ArrayCourse array : list) {
			// 查询该主题馆本周课程后，依次循环发布，发布后APP端可浏览
			if (array.getPostedStatus().equals(ThemePavilionConstant.POSTED_STATUS_YES)) {
				resultInfo.setCode(Constant.SECCUESS);
			}
		}
		return resultInfo;
	}

}