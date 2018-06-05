package cn.com.shopec.core.marketing.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.marketing.model.RedeemCouponPlan;

/**
 * 优惠卷方案,兑换码关系表 数据库处理类
 */
public interface RedeemCouponPlanDao extends BaseDao<RedeemCouponPlan,String> {
	
	/**
	 * 根据兑换码删除数据
	 * @param redeemCode
	 * @return
	 */
	public int deleteByRedeemCode (String redeemCode);

}
