package cn.com.shopec.core.member.dao;

import java.io.Serializable;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.member.model.MemberPointsRecord;

/**
 * 会员积分记录表 数据库处理类
 */
public interface MemberPointsRecordDao  extends BaseDao<MemberPointsRecord, Serializable>{
	
	/**
	 * 根据会员编号查询会员当前的积分数
	 * @param memberNo
	 * @return
	 */
	int getPointsByMemberNo(String memberNo);
}