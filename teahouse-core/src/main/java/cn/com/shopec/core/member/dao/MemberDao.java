package cn.com.shopec.core.member.dao;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberStatisticVo;
import cn.com.shopec.core.member.vo.MemberBalanceVo;

/**
 * 会员表 数据库处理类
 */
public interface MemberDao extends BaseDao<Member, Serializable> {
	/**
	 * 判断注册手机号的唯一性
	 */
	public Member getByPhone(String phone);

	/**
	 * 根据 邀请码 查出 推荐人
	 */
	public Member getInvitationCode(String invitationCode);

	/**
	 * 根据mid查询会员信息
	 */
	public Member getMemberByMid(String mid);

	/**
	 * 查出会员以及其余额账户信息（分页查询）
	 */
	public List<MemberBalanceVo> getMemberBalancePageList(Query q);

	/**
	 * 查询会员信息 非黑名单的会员数据
	 */
	public List<Member> queryMemberList(Query q);

	/**
	 * 查出会员以及其余额账户信息（分页查询）
	 */
	public Long countNs(Query q);

	/**
	 * 按天分组统计所选日期时间段的会员注册数量
	 */
	public List<MemberStatisticVo> statDaysMemberRegisterList(String beginDate, String endDate);

	/**
	 * 按月分组统计所选日期时间段的会员注册数量
	 */
	public List<MemberStatisticVo> statMonthMemberRegisterList(String beginDate, String endDate);

	/**
	 * 按年分组统计所选日期时间段的会员注册数量
	 */
	public List<MemberStatisticVo> statYearMemberRegisterList(String beginDate, String endDate);

	/**
	 * 根据token获取会员
	 * 
	 * @param token
	 * @return
	 */
	public Member getMemberByToken(String token);
}