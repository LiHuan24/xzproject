package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.RedeemCouponPlan;

/**
 * 优惠卷方案,兑换码关系表 服务接口类
 */
public interface RedeemCouponPlanService extends BaseService {

	/**
	 * 根据查询条件，查询并返回RedeemCouponPlan的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<RedeemCouponPlan> getRedeemCouponPlanList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回RedeemCouponPlan的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<RedeemCouponPlan> getRedeemCouponPlanPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个RedeemCouponPlan对象
	 * @param id 主键id
	 * @return
	 */
	public RedeemCouponPlan getRedeemCouponPlan(String id);

	/**
	 * 根据主键数组，查询并返回一组RedeemCouponPlan对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<RedeemCouponPlan> getRedeemCouponPlanByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的RedeemCouponPlan记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<RedeemCouponPlan> delRedeemCouponPlan(String id, Operator operator);
	
	/**
	 * 新增一条RedeemCouponPlan记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param redeemCouponPlan 新增的RedeemCouponPlan数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<RedeemCouponPlan> addRedeemCouponPlan(RedeemCouponPlan redeemCouponPlan, Operator operator);
	
	/**
	 * 根据主键，更新一条RedeemCouponPlan记录
	 * @param redeemCouponPlan 更新的RedeemCouponPlan数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<RedeemCouponPlan> updateRedeemCouponPlan(RedeemCouponPlan redeemCouponPlan, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为RedeemCouponPlan对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(RedeemCouponPlan obj);
		
}
