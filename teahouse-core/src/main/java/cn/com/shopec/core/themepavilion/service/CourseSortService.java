package cn.com.shopec.core.themepavilion.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.themepavilion.model.CourseSort;

/**
 * 课程分类表 服务接口类
 */
public interface CourseSortService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CourseSort的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<CourseSort> getCourseSortList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回CourseSort的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<CourseSort> getCourseSortPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个CourseSort对象
	 * @param id 主键id
	 * @return
	 */
	public CourseSort getCourseSort(String id);

	/**
	 * 根据主键数组，查询并返回一组CourseSort对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CourseSort> getCourseSortByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的CourseSort记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CourseSort> delCourseSort(String id, Operator operator);
	
	/**
	 * 新增一条CourseSort记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param courseSort 新增的CourseSort数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CourseSort> addCourseSort(CourseSort courseSort, Operator operator);
	
	/**
	 * 根据主键，更新一条CourseSort记录
	 * @param courseSort 更新的CourseSort数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<CourseSort> updateCourseSort(CourseSort courseSort, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为CourseSort对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(CourseSort obj);
		
}
