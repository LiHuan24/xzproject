package cn.com.shopec.mgt.themepavilion.controller;

import java.util.List;

import javax.annotation.Resource;

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
import cn.com.shopec.core.themepavilion.common.ThemePavilionConstant;
import cn.com.shopec.core.themepavilion.model.CourseSort;
import cn.com.shopec.core.themepavilion.service.CourseSortService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 共享健身房：主题馆-课程分类业务控制
 * 
 * @author LiHuan Date 
 * 2017年11月1日 上午10:56:47
 */
@Controller
@RequestMapping("courseSort")
public class CourseSortController extends BaseController {

	private static final Log log = LogFactory.getLog(CourseSortController.class);

	@Resource
	private CourseSortService courseSortService;

	/**
	 * 加载课程分类列表
	 */
	@RequestMapping("toCourseSortList")
	public String toCourseSortList() {
		log.info("加载课程分类列表");
		return "/themepavilion/course_sort_list";
	}

	/**
	 * 课程分类分页查询
	 * 
	 * @param courseLabel
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("courseSortPageList")
	@ResponseBody
	public PageFinder<CourseSort> courseSortPageList(@ModelAttribute("courseSort") CourseSort courseSort,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) {
		log.info("获取课程分类列表分页数据");
		Query q = new Query(pageNo, pageSize, courseSort);
		return courseSortService.getCourseSortPagedList(q);
	}

	/**
	 * 进入课程分类新增
	 */
	@RequestMapping("toCourseSortAdd")
	public String toCourseSortAdd() {
		log.info("课程分类新增页");
		return "/themepavilion/course_sort_add";
	}

	/**
	 * 进入课程分类编辑
	 */
	@RequestMapping("toCourseSortEdit")
	public String toCourseSortEdit(ModelMap model, String courseSortNo) {
		log.info("课程分类编辑页");
		CourseSort courseSort = courseSortService.getCourseSort(courseSortNo);
		model.put("courseSort", courseSort);
		return "/themepavilion/course_sort_edit";
	}

	/**
	 * 课程分类新增编辑操作
	 */
	@RequestMapping("/saveOrUpdateCourseSort")
	@ResponseBody
	public ResultInfo<CourseSort> saveOrUpdateCourseSort(@ModelAttribute("courseSort") CourseSort courseSort) {
		ResultInfo<CourseSort> resultInfo = new ResultInfo<CourseSort>();
		if (null == courseSort.getCourseSortNo()) {
			// 新增
			resultInfo = courseSortService.addCourseSort(courseSort, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg("添加成功！");
			return resultInfo;
		} else {
			// 编辑
			// 判断若是已删除状态，修改之前恢复课程分类为未删除
			if (courseSort.getIsDeleted() == ThemePavilionConstant.DEL_FLAG_YES) {
				courseSort.setIsDeleted(ThemePavilionConstant.DEL_FLAG_NO);
			}
			resultInfo = courseSortService.updateCourseSort(courseSort, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg("编辑成功！");
			return resultInfo;
		}
	}

	/**
	 * 课程分类删除操作
	 */
	@RequestMapping("delCourseSort")
	@ResponseBody
	public ResultInfo<CourseSort> delCourseSort(String courseSortNo) {
		return courseSortService.delCourseSort(courseSortNo, getOperator());
	}
	
	/**
	 * 课程分类新增时判断标签名称是否唯一
	 */
	@RequestMapping("addCourseSortUnique")
	@ResponseBody
	public ResultInfo<String> addCourseSortUnique(String sortName){
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		CourseSort courseSort = new CourseSort();
		courseSort.setSortName(sortName);
		courseSort.setIsDeleted(0);//未删除
		List<CourseSort> listCourseSort = courseSortService.getCourseSortList(new Query(courseSort));
		if(listCourseSort.size() > 0){
			resultInfo.setCode(Constant.SECCUESS);
		}else{
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}
	
	/**
	 * 课程分类编辑时判断标签名称是否唯一
	 */
	@RequestMapping("updateCourseSortUnique")
	@ResponseBody
	public ResultInfo<String> updateCourseSortUnique(String courseSrotNo,String sortName){
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		CourseSort cs = courseSortService.getCourseSort(courseSrotNo);
		if(cs.getSortName().equals(sortName)){
			resultInfo.setCode(Constant.FAIL);
		}else{
			CourseSort courseSort = new CourseSort();
			courseSort.setSortName(sortName);
			courseSort.setIsDeleted(0);//未删除
			List<CourseSort> listCourseSort = courseSortService.getCourseSortList(new Query(courseSort));
			if(listCourseSort.size() > 0){
				resultInfo.setCode(Constant.SECCUESS);
			}else{
				resultInfo.setCode(Constant.FAIL);
			}
		}
		return resultInfo;
	}
}