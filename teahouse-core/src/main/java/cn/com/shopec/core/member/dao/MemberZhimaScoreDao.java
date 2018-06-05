package cn.com.shopec.core.member.dao;

import java.io.Serializable;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.member.model.MemberZhimaScore;

/**
 * 会员芝麻信用分表 数据库处理类
 */
public interface MemberZhimaScoreDao  extends BaseDao<MemberZhimaScore, Serializable>{
 
	/**
	 * 修改对象
	 * @param obj
	 */
	public int updateForBusiness(MemberZhimaScore obj);
}