package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;
/**
 * 会员课程订单详情
 * @author LiHuan
 * Date 2017年12月4日 下午5:52:10
 */
public class ThemeOrderDetailVo implements Serializable {
	private static final long serialVersionUID = -4867863665810166774L;
	
	private String themeOrderNo;//订单编号
	private String storeName;//场馆名称
	private String classTime;//上课时间
	private String appionNumber;//预约人数
	private Double couponAmount;//优惠金额
	private Double balanceOffset;//余额抵扣
	private String courseName;//课程名称
	private String orderStatus;//订单状态 0、已预约，1、进行中，2、已结束，3、已取消，4、排队中
	private String payMethod;//支付方式 1、支付宝、2、微信 3、其他方式)
	private Double realPayAmount;//实付款
	private Double totalAmount;//订单总价
	private Integer cancelNumber;//取消人数
	private Double refundAmount;//退款金额
	private Integer courseBag;//使用课程包
	private String arrayCourseNo;//排课编号
	
	public String getThemeOrderNo() {
		return themeOrderNo;
	}
	public void setThemeOrderNo(String themeOrderNo) {
		this.themeOrderNo = themeOrderNo;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getClassTime() {
		return classTime;
	}
	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}
	public String getAppionNumber() {
		return appionNumber;
	}
	public void setAppionNumber(String appionNumber) {
		this.appionNumber = appionNumber;
	}
	public Double getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Double getBalanceOffset() {
		return balanceOffset;
	}
	public void setBalanceOffset(Double balanceOffset) {
		this.balanceOffset = balanceOffset;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public Double getRealPayAmount() {
		return realPayAmount;
	}
	public void setRealPayAmount(Double realPayAmount) {
		this.realPayAmount = realPayAmount;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getCancelNumber() {
		return cancelNumber;
	}
	public void setCancelNumber(Integer cancelNumber) {
		this.cancelNumber = cancelNumber;
	}
	public Double getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
	public Integer getCourseBag() {
		return courseBag;
	}
	public void setCourseBag(Integer courseBag) {
		this.courseBag = courseBag;
	}
	public String getArrayCourseNo() {
		return arrayCourseNo;
	}
	public void setArrayCourseNo(String arrayCourseNo) {
		this.arrayCourseNo = arrayCourseNo;
	}
	
}