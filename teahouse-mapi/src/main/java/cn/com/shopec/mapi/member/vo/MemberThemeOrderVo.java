package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;

/**
 * 会员主题馆课程订单
 * 
 * @author LiHuan 
 * Date 2017年11月29日 上午9:22:53
 */
public class MemberThemeOrderVo implements Serializable {
	private static final long serialVersionUID = -4867863665810166774L;
	
	private String storeName;//主题馆门店名称
	private String courseName;//课程名称
	private String orderStatus;//订单状态 0、已预约，1、进行中，2、已结束，3、已取消 4、排队中 5、待支付
	private String classTime;//上课时间
	private Double payableAmount;//订单实际支付金额
	private String themeOrderNo;//订单编号
	private String arrayCourseNo;//排课编号
	private int appoinNumber;//预约人数
	private String coursePrice;//预约价钱 人数*价钱 从预约订单界面计算而来
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
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
	
	public String getClassTime() {
		return classTime;
	}
	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}
	public Double getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}
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
	public int getAppoinNumber() {
		return appoinNumber;
	}
	public void setAppoinNumber(int appoinNumber) {
		this.appoinNumber = appoinNumber;
	}
	public String getCoursePrice() {
		return coursePrice;
	}
	public void setCoursePrice(String coursePrice) {
		this.coursePrice = coursePrice;
	}
}