package cn.com.shopec.core.member.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberStatisticVo;
import cn.com.shopec.core.member.vo.MemberBalanceVo;

/**
 * 会员信息表 服务接口类
 */
public interface MemberService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Member的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<Member> getMemberList(Query q);

	/**
	 * 根据查询条件，分页查询并返回MemberBalanceVo的分页结果（管理端会员页面）
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<MemberBalanceVo> getMemberBalancePageList(Query q);

	/**
	 * 根据查询条件，分页查询并返回Member的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<Member> getMemberPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个Member对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public Member getMember(String id);

	/**
	 * 根据主键数组，查询并返回一组Member对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Member> getMemberByIds(String[] ids);

	/**
	 * 根据主键，删除特定的Member记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Member> delMember(String id, Operator operator);

	/**
	 * 新增一条Member记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param member
	 *            新增的Member数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Member> addMember(Member member, Operator operator);

	/**
	 * 新增会员关联信息及调用硬件平台接口信息
	 */

	public ResultInfo<Member> addMemberInfo(Member member, Operator operator);

	/**
	 * 根据主键，更新一条Member记录
	 * 
	 * @param member
	 *            更新的Member数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Member> updateMember(Member member, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为Member对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Member obj);

	/**
	 * 验证注册手机号的唯一性
	 */
	public Member getMemberByPhone(String phone);

	// 根据 邀请码 查出 推荐人Id
	public Member getInvitationCode(String invitationCode);

	/**
	 * 根据mid查询会员信息
	 */
	public Member getMemberByMid(String mid);

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
