package cn.com.shopec.mgt.themepavilion.controller;

import java.text.ParseException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.themepavilion.common.ThemePavilionConstant;
import cn.com.shopec.core.themepavilion.common.ThemePavilionEnum;
import cn.com.shopec.core.themepavilion.dao.TemplateCourseDao;
import cn.com.shopec.core.themepavilion.model.Coach;
import cn.com.shopec.core.themepavilion.model.Course;
import cn.com.shopec.core.themepavilion.model.CourseBean;
import cn.com.shopec.core.themepavilion.model.TemplateCourse;
import cn.com.shopec.core.themepavilion.service.CoachService;
import cn.com.shopec.core.themepavilion.service.CourseService;
import cn.com.shopec.core.themepavilion.service.TemplateCourseService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 共享健身房：主题馆-排课模板业务控制
 * 
 * @author LiHuan Date 2017年11月2日 下午2:00:06
 */
@Controller
@RequestMapping("templateCourse")
public class TemplateCourseController extends BaseController {

	private static final Log log = LogFactory.getLog(TemplateCourseController.class);

	@Resource
	private TemplateCourseService templateCourseService;
	@Resource
	private CourseService courseService;
	@Resource
	private CoachService coachService;
	@Resource
	private TemplateCourseDao templateCourseDao;
	@Resource
	private StoreService storeService;

	/**
	 * 进入课程模板列表页
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("toTemplateCourseList")
	public String toTemplateCourseList(ModelMap modelMap, String storeNo) throws Exception {
		log.info("加载课程模板列表页");
		Store store = new Store();
		store.setStoreStatus(Constant.STORE_START_STATUS);
		store.setStoreType(ThemePavilionConstant.THEME_STORE);
		// 查询全部主题馆门店数据
		List<Store> listStore = storeService.getStoreList(new Query(store));
		if (listStore != null && listStore.size() > 0) {
			modelMap.put("listStore", listStore);
		}
		TemplateCourse templateCourse = new TemplateCourse();
		List<CourseBean> ls = new ArrayList<CourseBean>();
		// 首先根据保存的行号分组查询一共多少行
		if (StringUtils.isNotBlank(storeNo)) {
			templateCourse.setStoreNo(storeNo);
			modelMap.put("storeNo", storeNo);
		} else {
			templateCourse.setStoreNo(listStore.get(0).getStoreNo());
			modelMap.put("storeNo", templateCourse.getStoreNo());
		}
		List<TemplateCourse> listTemplate = templateCourseService.templateCourseList(new Query(templateCourse));
		if (listTemplate != null && listTemplate.size() > 0) {
			for (int i = 0; i < listTemplate.size(); i++) {
				TemplateCourse course = new TemplateCourse();
				course.setFtlRow(listTemplate.get(i).getFtlRow());
				if (StringUtils.isNotBlank(storeNo)) {
					course.setStoreNo(storeNo);
				} else {
					course.setStoreNo(listStore.get(0).getStoreNo());
				}
				// 再根据行号循环获取一行的所有数据
				List<TemplateCourse> list = templateCourseService.getTemplateCourseList(new Query(course));
				CourseBean c = new CourseBean();
				if (list.size() > 0) {
					for (TemplateCourse t : list) {
						if (StringUtils.isNotBlank(t.getCourseWeek())) {
							if (t.getCourseWeek().equals("周一")) {
								c.setMonName(t.getChineseName());
								c.setMonId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周二")) {
								c.setTueName(t.getChineseName());
								c.setTueId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周三")) {
								c.setWedName(t.getChineseName());
								c.setWedId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周四")) {
								c.setThuName(t.getChineseName());
								c.setThuId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周五")) {
								c.setFriName(t.getChineseName());
								c.setFriId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周六")) {
								c.setSatName(t.getChineseName());
								c.setSatId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周日")) {
								c.setSunName(t.getChineseName());
								c.setSunId(t.getTemplateCourseNo());
							}
						}
						if (StringUtils.isBlank(t.getChineseName())) {
							String showDate = "";
							String startTime = ECDateUtils.farmatDateToHM(t.getCourseStart());
							String endTime = ECDateUtils.farmatDateToHM(t.getCourseEnd());
							showDate = startTime + "至" + endTime;
							c.setShowDate(showDate);
							c.setDateId(t.getTemplateCourseNo());
						}

					}
				}
				ls.add(c);
			}
		}
		modelMap.put("listTemplate", ls);
		return "/themepavilion/template_course_list";
	}

	/**
	 * 页面选择门店查询该主题馆的排课模板数据
	 * 
	 */
	@RequestMapping("queryTemplateCourseList")
	public String queryTemplateCourseList(ModelMap modelMap, String storeNo) throws Exception {
		log.info("根据门店编号查询课程模板列表");
		Store store = new Store();
		store.setStoreStatus(Constant.STORE_START_STATUS);
		store.setStoreType(ThemePavilionConstant.THEME_STORE);
		// 查询全部主题馆门店数据
		List<Store> listStore = storeService.getStoreList(new Query(store));
		if (listStore != null && listStore.size() > 0) {
			modelMap.put("listStore", listStore);
		}
		TemplateCourse templateCourse = new TemplateCourse();
		List<CourseBean> ls = new ArrayList<CourseBean>();
		// 首先根据保存的行号分组查询一共多少行
		if (StringUtils.isNotBlank(storeNo)) {
			templateCourse.setStoreNo(storeNo);
			modelMap.put("storeNo", storeNo);
		} else {
			templateCourse.setStoreNo(listStore.get(0).getStoreNo());
			modelMap.put("storeNo", templateCourse.getStoreNo());
		}
		List<TemplateCourse> listTemplate = templateCourseService.templateCourseList(new Query(templateCourse));
		if (listTemplate != null && listTemplate.size() > 0) {
			for (int i = 0; i < listTemplate.size(); i++) {
				TemplateCourse course = new TemplateCourse();
				course.setFtlRow(listTemplate.get(i).getFtlRow());
				if (StringUtils.isNotBlank(storeNo)) {
					course.setStoreNo(storeNo);
				} else {
					course.setStoreNo(listStore.get(0).getStoreNo());
				}
				// 再根据行号循环获取一行的所有数据
				List<TemplateCourse> list = templateCourseService.getTemplateCourseList(new Query(course));
				CourseBean c = new CourseBean();
				if (list.size() > 0) {
					for (TemplateCourse t : list) {
						if (StringUtils.isNotBlank(t.getCourseWeek())) {
							if (t.getCourseWeek().equals("周一")) {
								c.setMonName(t.getChineseName());
								c.setMonId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周二")) {
								c.setTueName(t.getChineseName());
								c.setTueId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周三")) {
								c.setWedName(t.getChineseName());
								c.setWedId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周四")) {
								c.setThuName(t.getChineseName());
								c.setThuId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周五")) {
								c.setFriName(t.getChineseName());
								c.setFriId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周六")) {
								c.setSatName(t.getChineseName());
								c.setSatId(t.getTemplateCourseNo());
							} else if (t.getCourseWeek().equals("周日")) {
								c.setSunName(t.getChineseName());
								c.setSunId(t.getTemplateCourseNo());
							}
						}
						if (StringUtils.isBlank(t.getChineseName())) {
							String showDate = "";
							String startTime = ECDateUtils.farmatDateToHM(t.getCourseStart());
							String endTime = ECDateUtils.farmatDateToHM(t.getCourseEnd());
							showDate = startTime + "至" + endTime;
							c.setShowDate(showDate);
							c.setDateId(t.getTemplateCourseNo());
						}
					}
				}
				ls.add(c);
			}
		}
		modelMap.put("listTemplate", ls);
		return "/themepavilion/template_course_query_list";
	}

	/**
	 * 加载已经启用的课程数据分页列表
	 */
	@RequestMapping("pageListAuditCourse")
	@ResponseBody
	public PageFinder<Course> pageListAuditCourse(@ModelAttribute("course") Course course,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		log.info("获取课程列表分页数据");
		course.setIsEnable(ThemePavilionConstant.UP_COURSE);
		Query q = new Query(pageNo, pageSize, course);
		return courseService.getCoursePagedList(q);
	}

	/**
	 * 加载已经审核的教练数据分页列表
	 */
	@RequestMapping("pageListAuditCoach")
	@ResponseBody
	public PageFinder<Coach> pageListAuditCoach(@ModelAttribute("coach") Coach coach,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		log.info("获取教练列表分页数据");
		coach.setCensorStatus(ThemePavilionConstant.AUDIT_YES);
		Query q = new Query(pageNo, pageSize, coach);
		return coachService.getCoachPagedList(q);
	}

	/**
	 * 保存更新排课模板
	 * 
	 * @throws Exception
	 */
	@RequestMapping("saveOrUpDateTemplateCourse")
	@ResponseBody
	public ResultInfo<TemplateCourse> saveOrUpDateTemplateCourse(
			@ModelAttribute("templateCourse") TemplateCourse templateCourse, String courseStart, String courseEnd)
					throws Exception {
		ResultInfo<TemplateCourse> resultInfo = new ResultInfo<TemplateCourse>();
		if (StringUtils.isNotBlank(templateCourse.getCourseWeek())) {
			// 根据页面传入星期编号，set周一至周日不同的名称
			templateCourse.setCourseWeek(ThemePavilionEnum.getName(Integer.parseInt(templateCourse.getCourseWeek())));
		}

		if (null == templateCourse.getTemplateCourseNo()) {
			// 新增
			// 更新排课日期
			if (StringUtils.isNotBlank(courseStart) && StringUtils.isNotBlank(courseEnd)) {
				templateCourse.setCourseDate(new Date());
				templateCourse.setCourseStart(ECDateUtils.parseDate(courseStart + ":00", "HH:mm:ss"));
				templateCourse.setCourseEnd(ECDateUtils.parseDate(courseEnd + ":00", "HH:mm:ss"));
				String showDate = courseStart + "至" + courseEnd;
				templateCourse.setShowDate(showDate);
			} else {
				templateCourse.setCourseDate(new Date());
				templateCourse.setCourseStart(templateCourse.getCourseStart());
				templateCourse.setCourseEnd(templateCourse.getCourseEnd());
				String showDate = "";
				String startTime = ECDateUtils.farmatDateToHM(templateCourse.getCourseStart());
				String endTime = ECDateUtils.farmatDateToHM(templateCourse.getCourseEnd());
				showDate = startTime + "至" + endTime;
				templateCourse.setShowDate(showDate);
			}

			templateCourse.setStoreNo(templateCourse.getStoreNo());

			return templateCourseService.addTemplateCourse(templateCourse, getOperator());
		} else {
			// 编辑
			if (StringUtils.isNotBlank(courseStart) && StringUtils.isNotBlank(courseEnd)) {
				templateCourse.setCourseDate(new Date());
				templateCourse.setCourseStart(ECDateUtils.parseDate(courseStart + ":00", "HH:mm:ss"));
				templateCourse.setCourseEnd(ECDateUtils.parseDate(courseEnd + ":00", "HH:mm:ss"));
				String showDate = courseStart + "至" + courseEnd;
				templateCourse.setShowDate(showDate);
			} else {
				templateCourse.setCourseDate(new Date());
				templateCourse.setCourseStart(templateCourse.getCourseStart());
				templateCourse.setCourseEnd(templateCourse.getCourseEnd());
				String showDate = "";
				String startTime = ECDateUtils.farmatDateToHM(templateCourse.getCourseStart());
				String endTime = ECDateUtils.farmatDateToHM(templateCourse.getCourseEnd());
				showDate = startTime + "至" + endTime;
				templateCourse.setShowDate(showDate);
			}

			templateCourse.setStoreNo(templateCourse.getStoreNo());

			return templateCourseService.updateTemplateCourse(templateCourse, getOperator());
		}
	}

	/**
	 * 去编辑课程模板
	 */
	@RequestMapping("getTemplateCourse")
	@ResponseBody
	public ResultInfo<TemplateCourse> getTemplateCourse(String id) {
		ResultInfo<TemplateCourse> resultInfo = new ResultInfo<TemplateCourse>();
		TemplateCourse templateCourse = templateCourseService.getTemplateCourse(id);
		if (templateCourse != null) {
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(templateCourse);
		}
		return resultInfo;
	}

	/**
	 * 页面执行行数数据逻辑删除
	 * 
	 */
	@RequestMapping("delTemplateCourse")
	@ResponseBody
	public ResultInfo<TemplateCourse> delTemplateCourse(String rowNo, String storeNo) {
		ResultInfo<TemplateCourse> resultInfo = new ResultInfo<TemplateCourse>();
		// 获取已保存的排课信息的页最大行
		Long maxRow = templateCourseDao.getTemplateCourseMaxRow(storeNo);
		int maxParam = Integer.parseInt(rowNo);
		// 若选择的行数和数据库查询的最大行数相等，则直接删除属于该行数的全部数据
		if (maxRow == maxParam) {
			TemplateCourse templateCourse = new TemplateCourse();
			templateCourse.setFtlRow(maxParam);
			templateCourse.setStoreNo(storeNo);
			List<TemplateCourse> listTemplateCourse = templateCourseService
					.getTemplateCourseList(new Query(templateCourse));
			if (listTemplateCourse != null && listTemplateCourse.size() > 0) {
				for (TemplateCourse template : listTemplateCourse) {
					resultInfo = templateCourseService.delTemplateCourse(template.getTemplateCourseNo(), getOperator());
				}
				resultInfo.setCode(Constant.SECCUESS);
			}
		} else {
			// 否则，先删除选择行数的全部数据，删除成功后 ，对其他行的数据进行update
			TemplateCourse templateCourse = new TemplateCourse();
			templateCourse.setFtlRow(Integer.parseInt(rowNo));
			templateCourse.setStoreNo(storeNo);
			List<TemplateCourse> listTemplateCourse = templateCourseService
					.getTemplateCourseList(new Query(templateCourse));
			if (listTemplateCourse != null && listTemplateCourse.size() > 0) {
				for (TemplateCourse template : listTemplateCourse) {
					resultInfo = templateCourseService.delTemplateCourse(template.getTemplateCourseNo(), getOperator());
				}
				resultInfo.setCode(Constant.SECCUESS);
			}
			if (resultInfo.getCode().equals(Constant.SECCUESS)) {
				// 查询大于选择该行号的所有行数据进行更新操作
				List<TemplateCourse> listTemplate = templateCourseService.getTemplateCourseRowList(storeNo,
						Integer.parseInt(rowNo));
				if (listTemplate != null && listTemplate.size() > 0) {
					for (TemplateCourse course : listTemplate) {
						int rows = course.getFtlRow() - 1;
						course.setFtlRow(rows);
						templateCourseService.updateTemplateCourse(course, getOperator());
					}
				}
			}
		}
		return resultInfo;
	}
}