package cn.com.shopec.mgt.themepavilion.controller;

import java.util.List;

import javax.annotation.Resource;

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
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.themepavilion.common.ThemePavilionConstant;
import cn.com.shopec.core.themepavilion.model.Course;
import cn.com.shopec.core.themepavilion.model.CourseLabel;
import cn.com.shopec.core.themepavilion.model.CourseSort;
import cn.com.shopec.core.themepavilion.service.CourseLabelService;
import cn.com.shopec.core.themepavilion.service.CourseService;
import cn.com.shopec.core.themepavilion.service.CourseSortService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 共享健身房：主题馆-课程表业务控制
 * 
 * @author LiHuan Date 2017年11月1日 下午2:10:40
 */
@Controller
@RequestMapping("course")
public class CourseController extends BaseController {

	private static final Log log = LogFactory.getLog(CourseController.class);

	@Resource
	private CourseService courseService;
	@Resource
	private CourseLabelService courseLabelService;
	@Resource
	private CourseSortService courseSortService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Value("${res_img_path}")
	private String resImgPath;

	/**
	 * 进入课程列表页
	 */
	@RequestMapping("toCourseList")
	public String toCourseList(ModelMap modelMap) {
		log.info("加载课程列表");
		// 获取课程分类
		CourseSort courseSort = new CourseSort();
		courseSort.setIsDeleted(ThemePavilionConstant.DEL_FLAG_NO);
		List<CourseSort> listCourseSort = courseSortService.getCourseSortList(new Query(courseSort));
		if (listCourseSort != null && listCourseSort.size() > 0) {
			modelMap.put("listCourseSort", listCourseSort);
		}
		return "/themepavilion/course_list";
	}

	/**
	 * 获取课程分页列表数据
	 * 
	 * @param course
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("coursePageList")
	@ResponseBody
	public PageFinder<Course> coursePageList(@ModelAttribute("course") Course course,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		log.info("获取课程列表分页数据");
		Query q = new Query(pageNo, pageSize, course);
		return courseService.getCoursePagedList(q);
	}

	/**
	 * 进入课程新增页
	 */
	@RequestMapping("toCourseAdd")
	public String toCourseAdd(ModelMap modelMap) {
		log.info("进入课程新增页");
		// 获取课程分类
		CourseSort courseSort = new CourseSort();
		courseSort.setIsDeleted(ThemePavilionConstant.DEL_FLAG_NO);
		List<CourseSort> listCourseSort = courseSortService.getCourseSortList(new Query(courseSort));
		if (listCourseSort != null && listCourseSort.size() > 0) {
			modelMap.put("listCourseSort", listCourseSort);
		}
		// 获取课程标签
		CourseLabel courseLabel = new CourseLabel();
		courseLabel.setIsDeleted(ThemePavilionConstant.DEL_FLAG_NO);
		List<CourseLabel> listCourseLabel = courseLabelService.getCourseLabelList(new Query(courseLabel));
		if (listCourseLabel != null && listCourseLabel.size() > 0) {
			modelMap.put("listCourseLabel", listCourseLabel);
		}
		// 获取城市
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			modelMap.put("cities", cities);
		}
		return "/themepavilion/course_add";
	}

	/**
	 * 进入课程编辑页
	 */
	@RequestMapping("toCourseEdit")
	public String toCourseEdit(ModelMap modelMap, String courseNo) {
		log.info("进入课程编辑页");
		Course course = courseService.getCourse(courseNo);
		modelMap.put("course", course);
		// 获取课程分类
		CourseSort courseSort = new CourseSort();
		courseSort.setIsDeleted(ThemePavilionConstant.DEL_FLAG_NO);
		List<CourseSort> listCourseSort = courseSortService.getCourseSortList(new Query(courseSort));
		if (listCourseSort != null && listCourseSort.size() > 0) {
			modelMap.put("listCourseSort", listCourseSort);
		}
		// 获取课程标签
		CourseLabel courseLabel = new CourseLabel();
		courseLabel.setIsDeleted(ThemePavilionConstant.DEL_FLAG_NO);
		List<CourseLabel> listCourseLabel = courseLabelService.getCourseLabelList(new Query(courseLabel));
		if (listCourseLabel != null && listCourseLabel.size() > 0) {
			modelMap.put("listCourseLabel", listCourseLabel);
		}
		// 获取城市
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");
		if (cities != null && cities.size() > 0) {
			modelMap.put("cities", cities);
		}
		return "/themepavilion/course_edit";
	}

	/**
	 * 进入课程详情页
	 */
	@RequestMapping("toCourseView")
	public String toCourseView(ModelMap modelMap, String courseNo) {
		log.info("进入课程详情页");
		Course course = courseService.getCourse(courseNo);
		modelMap.put("course", course);
		if (course.getCoursePictureUrl1() != null) {
			modelMap.put("picUrls", course.getCoursePictureUrl1().split(","));
		}
		return "/themepavilion/course_view";
	}

	/**
	 * 课程新增或者编辑
	 */
	@RequestMapping("saveOrUpdateCourse")
	@ResponseBody
	public ResultInfo<Course> saveOrUpdateCourse(@ModelAttribute("course") Course course) {
		log.info("课程新增或者编辑");
		ResultInfo<Course> resultInfo = new ResultInfo<Course>();

		String synopsis = course.getSynopsis().replaceAll("\r|\n|\t", "").replace(" ", "");
		String effect = course.getEffect().replaceAll("\r|\n|\t", "").replace(" ", "");
		String suit = course.getSuit().replaceAll("\r|\n|\t", "").replace(" ", "");
		String beCareful = course.getBeCareful().replaceAll("\r|\n|\t", "").replace(" ", "");
		course.setSynopsis(synopsis);
		course.setEffect(effect);
		course.setSuit(suit);
		course.setBeCareful(beCareful);

		if (course.getCoursePictureUrl1() != null && course.getCoursePictureUrl1().equals("")) {
			course.setCoursePictureUrl1(null);
		}

		// set城市名称、课程分类名称和课程标签名称
		DataDictItem dataDictItem = dataDictItemService.getDataDictItem(course.getCityId());
		if (dataDictItem != null) {
			course.setCityName(dataDictItem.getItemValue());
		}
		CourseSort courseSort = courseSortService.getCourseSort(course.getCourseSortNo());
		if (courseSort != null) {
			course.setSortName(courseSort.getSortName());
		}
		// 处理标签
		String[] labelNo = course.getCourseLabelNo().split(",");
		String labelName = "";
		for (int i = 0; i < labelNo.length; i++) {
			CourseLabel courseLabel = courseLabelService.getCourseLabel(labelNo[i]);
			if (courseLabel != null) {
				labelName += courseLabel.getLabelName() + ",";
			}
		}
		labelName = labelName.substring(0, labelName.length() - 1).replace(" ", "");
		course.setLabelName(labelName);

		if (null == course.getCourseNo()) {
			// 新增
			resultInfo = courseService.addCourse(course, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		} else {
			// 编辑
			resultInfo = courseService.updateCourse(course, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		}
	}
	
	/**
	 * 课程新增时判断输入课程名称是否唯一
	 */
	@RequestMapping("addCheckCourseUnique")
	@ResponseBody
	public ResultInfo<String> addCheckCourseUnique(String courseName){
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		Course course = new Course();
		course.setChineseName(courseName);
		course.setIsDeleted(0);//未删除
		List<Course> listCourse = courseService.getCourseList(new Query(course));
		if(listCourse.size() > 0){
			resultInfo.setCode(Constant.SECCUESS);
		}else{
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}
	
	/**
	 * 课程编辑保存时判断输入中文名称是否唯一
	 */
	@RequestMapping("updateCheckCourseUnique")
	@ResponseBody
	public ResultInfo<String> updateCheckCourseUnique(String courseNo,String courseName){
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		Course course = courseService.getCourse(courseNo);
		if(course.getChineseName().equals(courseName)){
			resultInfo.setCode(Constant.FAIL);
		}else{
			Course courseBean = new Course();
			courseBean.setChineseName(courseName);;
			courseBean.setIsDeleted(0);//未删除
			List<Course> listCourse = courseService.getCourseList(new Query(courseBean));
			if(listCourse.size() > 0){
				resultInfo.setCode(Constant.SECCUESS);
			}else{
				resultInfo.setCode(Constant.FAIL);
			}
		}
		return resultInfo;
	}
	
	
	/**
	 * 启用、停用查询课程对象
	 */
	@RequestMapping("/tCourse")
	@ResponseBody
	public Course toCar(String courseNo) {
		Course course = null;
		if (courseNo != null && courseNo.length() != 0) {
			course = courseService.getCourse(courseNo);
		}
		return course;
	}

	/**
	 * 课程启用、停用操作
	 */
	@RequestMapping("changeCourseEnable")
	@ResponseBody
	public ResultInfo<Course> changeCourseEnable(@ModelAttribute("course") Course course) {
		ResultInfo<Course> resultInfo = new ResultInfo<Course>();
		if (course.getCourseNo() != null && course.getCourseNo().length() != 0) {

			Course c = courseService.getCourse(course.getCourseNo());
			course.setCoursePictureUrl1(c.getCoursePictureUrl1());
			resultInfo = courseService.updateCourse(course, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("课程编号不能为空");
		}
		return resultInfo;
	}

	/**
	 * 获取课程图片
	 */
	@RequestMapping("/getCoursePitureUrl")
	@ResponseBody
	public String[] getCoursePitureUrl(String courseNo) {
		if (courseNo != null && courseNo.length() != 0) {
			Course course = courseService.getCourse(courseNo);
			if (course.getCoursePictureUrl1() != null && !course.getCoursePictureUrl1().equals("")) {
				return course.getCoursePictureUrl1().split(",");
			}
		}
		return null;
	}
}