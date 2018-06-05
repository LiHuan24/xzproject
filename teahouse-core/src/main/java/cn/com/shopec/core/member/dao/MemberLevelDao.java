package cn.com.shopec.core.member.dao;

import java.io.Serializable;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.member.model.MemberLevel;

/**
 * 会员等级表 数据库处理类
 */
public interface MemberLevelDao extends BaseDao<MemberLevel, Serializable>{
	
	/**
	 * 获取会员当前等级的积分值
	 * @param memberPoint
	 * @return
	 */
	Integer getNowLevelPoints(int memberPoint);

	/**
	 * 获取会员下一等级的积分值
	 * @param memberPoint
	 * @return
	 */
	Integer getNextLevelPoints(int memberPoint);

	/**
	 * 根据等级积分查找名称
	 * @param nextLevelPoint
	 * @return
	 */
	MemberLevel getMemberLevelNex(int nextLevelPoint);
}