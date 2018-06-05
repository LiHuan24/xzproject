package cn.com.shopec.core.order.model;

import java.io.Serializable;

/**
 * 主题馆或社区馆支付订单入参
 * @author LiHuan
 * Date 2017年11月29日 下午5:21:59
 */
public class PayThemeOrderVo implements Serializable{
	private static final long serialVersionUID = -4867863665810166774L;
	
	private String arrayCourseNo;//(排课编号) 
	private String payAmount;//应付金额
	private String realPayAmount;//实付金额 
	private String appoinNumber;//预约人数
	private String discountAmount;//(优惠金额) 
	private String balanceDiscountAmount;//(余额抵扣金额) 
	private String couponNo;//(优惠券编号)
	private String courseBag;//课程包数
	private String payStatus;//支付状态
	private String themeOrderNo;//订单编号
	
	public String getThemeOrderNo() {
		return themeOrderNo;
	}
	public void setThemeOrderNo(String themeOrderNo) {
		this.themeOrderNo = themeOrderNo;
	}
	public String getArrayCourseNo() {
		return arrayCourseNo;
	}
	public void setArrayCourseNo(String arrayCourseNo) {
		this.arrayCourseNo = arrayCourseNo;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getRealPayAmount() {
		return realPayAmount;
	}
	public void setRealPayAmount(String realPayAmount) {
		this.realPayAmount = realPayAmount;
	}
	public String getAppoinNumber() {
		return appoinNumber;
	}
	public void setAppoinNumber(String appoinNumber) {
		this.appoinNumber = appoinNumber;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public String getBalanceDiscountAmount() {
		return balanceDiscountAmount;
	}
	public void setBalanceDiscountAmount(String balanceDiscountAmount) {
		this.balanceDiscountAmount = balanceDiscountAmount;
	}
	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public String getCourseBag() {
		return courseBag;
	}
	public void setCourseBag(String courseBag) {
		this.courseBag = courseBag;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	
}