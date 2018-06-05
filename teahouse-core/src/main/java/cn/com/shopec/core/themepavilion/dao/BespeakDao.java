package cn.com.shopec.core.themepavilion.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.themepavilion.model.Bespeak;

/**
 * 课程预约表 数据库处理类
 */
public interface BespeakDao extends BaseDao<Bespeak,String> {
	//根据排课编号查询属于该排课课程的预约记录
	Bespeak getBespeakByArrayCourseNo(String arrayCourseNo);
}
