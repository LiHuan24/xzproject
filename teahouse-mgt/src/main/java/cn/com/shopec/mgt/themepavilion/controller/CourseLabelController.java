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
import cn.com.shopec.core.themepavilion.model.CourseLabel;
import cn.com.shopec.core.themepavilion.service.CourseLabelService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 共享健身房：主题馆-课程标签业务控制
 * @author LiHuan
 * Date 2017年10月31日 下午5:44:17
 */
@Controller 
@RequestMapping("courseLabel")
public class CourseLabelController extends BaseController {

	private static final Log log = LogFactory.getLog(CourseLabelController.class);
	
	@Resource
	private CourseLabelService courseLabelService;
	
	/**
	 * 加载课程标签列表
	 */
	@RequestMapping("toCourseLabelList")
	public String toCourseLabelList() {
		log.info("加载课程标签列表");
		return "/themepavilion/course_label_list";
	}
	
	/**
	 * 课程标签分页查询
	 * 
	 * @param courseLabel
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("courseLabelPageList")
	@ResponseBody
	public PageFinder<CourseLabel> courseLabelPageList(@ModelAttribute("courseLabel") CourseLabel courseLabel, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		log.info("获取课程标签列表分页数据");
		Query q = new Query(pageNo, pageSize, courseLabel);
		return courseLabelService.getCourseLabelPagedList(q);
	}
	
	/**
	 * 进入课程标签新增
	 */
	@RequestMapping("toCourseLabelAdd")
	public String toCourseLabelAdd() {
		log.info("课程标签新增页");
		return "/themepavilion/course_label_add";
	}
	
	/**
	 * 进入课程标签编辑
	 */
	@RequestMapping("toCourseLabelEdit")
	public String toCourseLabelEdit(ModelMap model,String courseLabelNo) {
		log.info("课程标签编辑页");
		CourseLabel courseLabel = courseLabelService.getCourseLabel(courseLabelNo);
		model.put("courseLabel", courseLabel);
		return "/themepavilion/course_label_edit";
	}
	
	/**
	 * 课程标签新增编辑操作
	 */
	@RequestMapping("/saveOrUpdateCourseLabel")
	@ResponseBody
	public ResultInfo<CourseLabel> saveOrUpdateCourseLabel(@ModelAttribute("courseLabel") CourseLabel courseLabel){
		ResultInfo<CourseLabel> resultInfo = new ResultInfo<CourseLabel>();
		if (null == courseLabel.getCourseLabelNo()) {
			//新增
			resultInfo = courseLabelService.addCourseLabel(courseLabel, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg("添加成功！");
			return resultInfo;
		} else {
			//编辑
			//判断若是已删除状态，修改之前恢复课程标签为未删除
			if(courseLabel.getIsDeleted() == ThemePavilionConstant.DEL_FLAG_YES){
				courseLabel.setIsDeleted(ThemePavilionConstant.DEL_FLAG_NO);
			}
			resultInfo = courseLabelService.updateCourseLabel(courseLabel, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg("编辑成功！");
			return resultInfo;
		}
	}
	
	/**
	 * 课程标签删除操作
	 */
	@RequestMapping("delCourseLabel")
	@ResponseBody
	public ResultInfo<CourseLabel> delCourseLabel(String courseLabelNo) {
		return courseLabelService.delCourseLabel(courseLabelNo, getOperator());
	}
	
	/**
	 * 课程标签新增时判断标签名称是否唯一
	 */
	@RequestMapping("addCheckLabelUnique")
	@ResponseBody
	public ResultInfo<String> addCheckLabelUnique(String labelName){
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		CourseLabel courseLabel = new CourseLabel();
		courseLabel.setLabelName(labelName);
		courseLabel.setIsDeleted(0);//未删除
		List<CourseLabel> listCourseLabel = courseLabelService.getCourseLabelList(new Query(courseLabel));
		if(listCourseLabel.size() > 0){
			resultInfo.setCode(Constant.SECCUESS);
		}else{
			resultInfo.setCode(Constant.FAIL);
		}
		return resultInfo;
	}
	
	/**
	 * 课程标签编辑时判断标签名称是否唯一
	 */
	@RequestMapping("updateCheckLabelUnique")
	@ResponseBody
	public ResultInfo<String> updateCheckLabelUnique(String courseLabelNo,String labelName){
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		CourseLabel cl = courseLabelService.getCourseLabel(courseLabelNo);
		if(cl.getLabelName().equals(labelName)){
			resultInfo.setCode(Constant.FAIL);
		}else{
			CourseLabel courseLabel = new CourseLabel();
			courseLabel.setLabelName(labelName);
			courseLabel.setIsDeleted(0);//未删除
			List<CourseLabel> listCourseLabel = courseLabelService.getCourseLabelList(new Query(courseLabel));
			if(listCourseLabel.size() > 0){
				resultInfo.setCode(Constant.SECCUESS);
			}else{
				resultInfo.setCode(Constant.FAIL);
			}
		}
		return resultInfo;
	}
}