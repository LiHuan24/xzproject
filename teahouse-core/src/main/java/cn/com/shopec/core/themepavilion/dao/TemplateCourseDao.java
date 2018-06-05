package cn.com.shopec.core.themepavilion.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.themepavilion.model.TemplateCourse;

/**
 * 排课模版表 数据库处理类
 */
public interface TemplateCourseDao extends BaseDao<TemplateCourse,String> {
	
	//分组查询排课模板数据
	List<TemplateCourse> queryTemplateCourseList(Query q);
	//查询数据库已保存的主题馆排课模板页最大行数 param store_no
	Long getTemplateCourseMaxRow(String storeNo);
	//根据删除行号，查询所有大于该行号的排课数据
	List<TemplateCourse> getTemplateCourseRowList(String storeNo, int rowNo);
}
