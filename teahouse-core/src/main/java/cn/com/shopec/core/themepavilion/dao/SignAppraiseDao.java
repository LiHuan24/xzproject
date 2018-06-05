package cn.com.shopec.core.themepavilion.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.themepavilion.model.SignAppraise;

/**
 * 课程签到评价表 数据库处理类
 */
public interface SignAppraiseDao extends BaseDao<SignAppraise,String> {
	public SignAppraise getSignAppraiseStatus(String coachNo,String arrayCourseNo);
}
