package cn.com.shopec.core.themepavilion.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.themepavilion.model.Course;

/**
 * 课程表 数据库处理类
 */
public interface CourseDao extends BaseDao<Course,String> {
	/**
	 * 根据标签号和启用查询该课程数据
	 */
	List<Course> queryCourseList(Query q);
}
