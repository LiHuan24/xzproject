package cn.com.shopec.core.marketing.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.model.RedeemCode;

/**
 * 兑换码表 服务接口类
 */
public interface RedeemCodeService extends BaseService {

	/**
	 * 根据查询条件，查询并返回RedeemCode的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<RedeemCode> getRedeemCodeList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回RedeemCode的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<RedeemCode> getRedeemCodePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个RedeemCode对象
	 * @param id 主键id
	 * @return
	 */
	public RedeemCode getRedeemCode(String id);

	/**
	 * 根据主键数组，查询并返回一组RedeemCode对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<RedeemCode> getRedeemCodeByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的RedeemCode记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<RedeemCode> delRedeemCode(String id, Operator operator);
	
	/**
	 * 新增一条RedeemCode记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param redeemCode 新增的RedeemCode数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<RedeemCode> addRedeemCode(RedeemCode redeemCode, Operator operator);
	
	/**
	 * 根据主键，更新一条RedeemCode记录
	 * @param redeemCode 更新的RedeemCode数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<RedeemCode> updateRedeemCode(RedeemCode redeemCode, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
	/**
	 * 为RedeemCode对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(RedeemCode obj);

	/**
	 * 兑换优惠券
	 * @param redCode
	 * @param memberNo
	 * @param operator
	 * @return
	 */
	ResultInfo<RedeemCode> redeemCoupon(String redCode, String memberNo, Operator operator);

	/**
	 * 根据兑换码获取优惠券方案
	 * @param redeemCode
	 * @return
	 */
	public List<CouponPlan> getPageForRedeemmCode(String redeemCode);
	
		
}
