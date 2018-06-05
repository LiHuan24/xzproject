package cn.com.shopec.core.marketing.common;

public class MarketingContant {

	public static final String fail_code = "0";
	public static final String success_code = "1";
	
	/**系统错误*/
	public static final String fail_msg = "系统错误";
	
	/**暂无活动*/
	public static final String fail_behavior_msg = "暂无活动";
	
	/**信息分页每页的大小*/
	public static final int msg_pageSize = 10;
	
	/**活动分页每页的大小*/
	public static final int adv_pageSize = 10;
	//套餐产品待审核统计
	public static final String pricingPakage_CENCOR_STATUS = "pricingPakage_CENCOR_STATUS";
	
	/**优惠券不存在*/
	public static final String  COUPON_NO_EXISTENCE = "该优惠券不存在";
	
	/**优惠券不存在*/
	public static final String  COUPON_QUANTITY_EXCEEDED = "该优惠券发放数量超越过方案设置数量";
	
	/**优惠券方案未通过审核*/
	public static final String  COUPON_CENSOR_PASS = "该优惠券方案未通过审核";
	/**优惠券方案不存在*/
	public static final String  COUPONPLAN_NO_DISABLE = "该优惠券方案未启用";
	/**优惠券方案不存在*/
	public static final String  COUPONPLAN_NO_EXISTENCE = "该优惠券方案不存在";
	
}
