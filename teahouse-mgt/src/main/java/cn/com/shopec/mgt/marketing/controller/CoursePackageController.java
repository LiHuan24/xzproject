package cn.com.shopec.mgt.marketing.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.CoursePackage;
import cn.com.shopec.core.finace.service.CoursePackageService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysUser;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 课程包
 */
@Controller
@RequestMapping("coursePackage")
public class CoursePackageController extends BaseController {
	@Resource
	private CoursePackageService coursePackageService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 跳转列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCoursePackageList")
	public String toCoursePackageList() {
		return "marketing/coursePackage_list";
	}

	/**
	 * 列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/pageListCoursePackage")
	@ResponseBody
	public PageFinder<CoursePackage> pageListCoursePackage(@ModelAttribute("coursePackage") CoursePackage coursePackage,
			Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), coursePackage);
		return coursePackageService.getCoursePackagePagedList(q);
	}

	/**
	 * 跳转详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCoursePackageView")
	public String toCoursePackageView(String coursePackageNo, ModelMap modelMap) {
		CoursePackage coursePackage = coursePackageService.getCoursePackage(coursePackageNo);
		modelMap.put("coursePackage", coursePackage);
		if (coursePackage.getCencorId() != null) {
			SysUser sysUser = sysUserService.detail(coursePackage.getCencorId());
			if (sysUser != null) {
				modelMap.put("cencor", sysUser.getRealName());
			}
		}
		return "marketing/coursePackage_view";
	}

	/**
	 * 跳转新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddCoursePackage")
	public String toAddCoursePackage(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "marketing/coursePackage_add";
	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping("/addCoursePackage")
	@ResponseBody
	public ResultInfo<CoursePackage> addCoursePackage(@ModelAttribute("coursePackage") CoursePackage coursePackage) {
		if (coursePackage.getCityId() != null && coursePackage.getCityId().length() != 0) {
			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(coursePackage.getCityId());
			if (dataDictItem != null) {
				coursePackage.setCityName(dataDictItem.getItemValue());
			}
		}
		return coursePackageService.addCoursePackage(coursePackage, getOperator());
	}

	/**
	 * 跳转修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEditCoursePackage")
	public String toEditCoursePackage(String coursePackageNo, ModelMap modelMap) {
		CoursePackage coursePackage = coursePackageService.getCoursePackage(coursePackageNo);
		modelMap.put("coursePackage", coursePackage);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "marketing/coursePackage_edit";
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping("/updateCoursePackage")
	@ResponseBody
	public ResultInfo<CoursePackage> updateCoursePackage(@ModelAttribute("coursePackage") CoursePackage coursePackage) {
		if (coursePackage.getCityId() != null && coursePackage.getCityId().length() != 0) {
			DataDictItem dataDictItem = dataDictItemService.getDataDictItem(coursePackage.getCityId());
			if (dataDictItem != null) {
				coursePackage.setCityName(dataDictItem.getItemValue());
			}
		}
		Operator operator = this.getOperator();
		if (coursePackage.getCencorStatus() != null && coursePackage.getCencorStatus() == 2) {
			coursePackage.setCencorStatus(0);
		}
		return coursePackageService.updateCoursePackage(coursePackage, operator);
	}

	/**
	 * 审核
	 * 
	 * @return
	 */
	@RequestMapping("/updateCoursePackageNs")
	@ResponseBody
	public ResultInfo<CoursePackage> updateCoursePackageNs(
			@ModelAttribute("coursePackage") CoursePackage coursePackage) {

		Operator operator = this.getOperator();
		if (operator != null && coursePackage.getCencorStatus() != null) {
			coursePackage.setCencorTime(new Date());
			coursePackage.setCencorId(operator.getOperatorId());
		}
		return coursePackageService.updateCoursePackage(coursePackage, operator);
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/deleteCoursePackage")
	@ResponseBody
	public ResultInfo<CoursePackage> deleteCoursePackage(@ModelAttribute("coursePackage") CoursePackage coursePackage) {
		return coursePackageService.delCoursePackage(coursePackage.getCoursePackageNo(), getOperator());
	}

}
