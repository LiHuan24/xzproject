package cn.com.shopec.core.marketing.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Coupon;

/**
 * 优惠券表 数据库处理类
 */
public interface CouponDao extends BaseDao<Coupon,String> {

	/**
	 * 批量增加优惠券
	 * @param coupons
	 */
	void addBatch(List<Coupon> coupons);

	/**
	 * 按条件得到优惠卷数量（管理端用）
	 * @param q
	 * @return
	 */
	long getMemberCouponCount(Query q);
	
	/**
	 * 按条件得到优惠卷列表（管理端用）
	 * @param q
	 * @return
	 */
	List<Coupon> getMemberCouponPageList(Query q);
	
	/**
	 * 获取主题馆优惠券 会员关联
	 */
	List<Coupon> getThemeCouponList(Query q);
	/**
	 * 获取社区馆优惠券 会员关联
	 */
	List<Coupon> getCommunityCouponList(Query q);
	
}
