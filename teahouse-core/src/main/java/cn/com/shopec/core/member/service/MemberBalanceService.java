package cn.com.shopec.core.member.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.MemberBalance;

/**
 * 会员余额表 服务接口类
 */
public interface MemberBalanceService extends BaseService {

	/**
	 * 根据查询条件，查询并返回MemberBalance的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<MemberBalance> getMemberBalanceList(Query q);

	/**
	 * 根据查询条件，分页查询并返回MemberBalance的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<MemberBalance> getMemberBalancePagedList(Query q);

	/**
	 * 根据主键，查询并返回一个MemberBalance对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public MemberBalance getMemberBalance(String id);

	/**
	 * 根据主键数组，查询并返回一组MemberBalance对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<MemberBalance> getMemberBalanceByIds(String[] ids);

	/**
	 * 根据主键，删除特定的MemberBalance记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<MemberBalance> delMemberBalance(String id, Operator operator);

	/**
	 * 新增一条MemberBalance记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param memberBalance
	 *            新增的MemberBalance数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<MemberBalance> addMemberBalance(MemberBalance memberBalance, Operator operator);

	/**
	 * 根据主键，更新一条MemberBalance记录
	 * 
	 * @param memberBalance
	 *            更新的MemberBalance数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<MemberBalance> updateMemberBalance(MemberBalance memberBalance, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为MemberBalance对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(MemberBalance obj);

}
