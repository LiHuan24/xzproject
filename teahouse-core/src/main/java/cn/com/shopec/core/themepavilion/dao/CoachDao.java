package cn.com.shopec.core.themepavilion.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.themepavilion.model.Coach;

/**
 * 教练表 数据库处理类
 */
public interface CoachDao extends BaseDao<Coach,String> {
	 /**
	  * 根据会员手机号查询教练信息
	  */
	public Coach getCoachByMemberPhone(String memberPhone);
}
