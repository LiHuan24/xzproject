package cn.com.shopec.core.themepavilion.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.themepavilion.model.ArrayCourse;

/**
 * 排课表 数据库处理类
 */
public interface ArrayCourseDao extends BaseDao<ArrayCourse,String> {
	//根据已经排完的课程查询数据页面行数关系
	List<ArrayCourse> queryArrayCourseRow(Query q);
	//查询属于该课程的排课时间段
	ArrayCourse getCourseTimeByFtl(int ftl,int type);
	//查询属于该主题馆的课程排课时间段
	ArrayCourse getStoreCourseTimeByFtl(String storeNo,int ftl,int type);
	//按城市编码分组查询当前哪些主题馆已排课程
	List<ArrayCourse> listStoreNameGroupBy(String cityCoding);
	//根据城市区编码查询当前区的主题馆排课数据
	List<ArrayCourse> listStoreByAreaCode(String areaCode);
	//查询全部主题馆排课课程
	List<ArrayCourse> listThemeAll(Query q);
	
	long countThemeAll(Query q);
	//
	ArrayCourse getArrayForCourse(String areaCode);
}
