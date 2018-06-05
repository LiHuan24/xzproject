package cn.com.shopec.core.order.common;

public class OrderConstant {

	public static final String fail_code = "0";
	public static final String success_code = "1";

	/** 系统错误 */
	public static final String fail_msg = "系统错误";

	/** 订单删除 */
	public static final String order_del_code = "-1";
	public static final String order_del_msg = "该订单不能删除";

	/** 参数 */
	public static final String fail_parameters = "参数错误";

	/** 预约后自动取消订单的时间 */
	public static final String cancel_order_key = "CANCELORDERTIME";

	/*** 已有记录 */
	public static final String hasRecord = "已有记录";
	/** 当前没有订单 **/
	public static final String noNowOrder = "当前没有订单";

	/** 订单不存在 **/
	public static final String noOrderExsit = "订单不存在 ";
	

	/** 订单未支付 **/
	public static final String notPayOrder_code = "0";
	public static final String notPayOrder_msg = "订单未支付 ";
	/** 订单已支付 **/
	public static final String alreday_pay = "1";
	public static final String alreday_pay_msg = "该订单已支付！ ";
	/** 订单已计费 **/
	public static final String jifeiOrder_code = "3";
	public static final String jifeiOrder_msg = "有已计费的订单 ";
	/** 订单评价 **/
	public static final String pingjiaOrder_msg = "评价成功，祝您生活愉快，欢迎下次预定！ ";
	/** 测试支付宝和微信时，金额是否为0.01 */
	public static final String IS_Correct_OrderAmount = "IS_Correct_OrderAmount";


	/** 订单已预约 **/
	public static final String yuyueOrder_code = "0";
	public static final String yuyueOrder_msg = "有已预约的订单 ";
	/** 订单进行中 **/
	public static final String jinxzOrder_code = "1";
	public static final String jinxzOrder_msg = "进行中的订单 ";
	/** 订单已结束 **/
	public static final String yjsOrder_code = "2";
	public static final String overOrder = "订单已结束 ";
	/** 订单已取消 **/
	public static final String yqxOrder_code = "3";
	public static final String yqxOrder_msg = "已取消的订单 ";
	/**
	 * 订单排队中
	 */
	public static final String pdzOrder_code = "4";
	public static final String pdzOrder_msg = "排队中 ";
	
	/**
	 * 订单支付方式
	 */
	/** 支付宝 **/
	public static final int alipay_method = 1;
	/** 微信 **/
	public static final int weChat_method = 2;
	
	/**
	 * 优惠券优惠方式
	 */
	public static final int coupon_dz_method=1;//打折
	public static final int coupon_zj_method=2;//直减
	public static final int coupon_ty_method=3;//健身体验
	public static final int coupon_kc_method=4;//课程体验
	
	/**
	 * 短信常量内容
	 */
	public static final String msgOrderContent = "课程预约成功";
	
	/**
	 * 订单签到
	 */
	public static final int ORDER_SIGN_YES = 1;
	public static final int ORDER_SIGN_NO = 0;

}
