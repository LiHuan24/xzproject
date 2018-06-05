package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 课程预约支付数据封装
 * @author LiHuan
 * Date 2017年11月17日 上午9:29:36
 */
public class CourseAppoinPayVo implements Serializable{
	private static final long serialVersionUID = -4867863665810166774L;
	
	private String memberNo;//会员编号
	private String arrayCourseNo;//排课课程编号
	private String payAmount;//支付金额
	private String appoinNumber;//本次预约人数
	private String surplusCourseBag;//剩余课程包
	private Double balance;//余额
	private String realPayAmount;//实付金额
	private List<CouponVo> listCouponVo; //优惠券数据
	private Double oneCoursePrice;//课程单价
	private String themeOrderNo;//订单编号
	
	
	public String getThemeOrderNo() {
		return themeOrderNo;
	}
	public void setThemeOrderNo(String themeOrderNo) {
		this.themeOrderNo = themeOrderNo;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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
	public String getAppoinNumber() {
		return appoinNumber;
	}
	public void setAppoinNumber(String appoinNumber) {
		this.appoinNumber = appoinNumber;
	}
	public String getSurplusCourseBag() {
		return surplusCourseBag;
	}
	public void setSurplusCourseBag(String surplusCourseBag) {
		this.surplusCourseBag = surplusCourseBag;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getRealPayAmount() {
		return realPayAmount;
	}
	public void setRealPayAmount(String realPayAmount) {
		this.realPayAmount = realPayAmount;
	}
	public Double getOneCoursePrice() {
		return oneCoursePrice;
	}
	public void setOneCoursePrice(Double oneCoursePrice) {
		this.oneCoursePrice = oneCoursePrice;
	}
	public List<CouponVo> getListCouponVo() {
		return listCouponVo;
	}
	public void setListCouponVo(List<CouponVo> listCouponVo) {
		this.listCouponVo = listCouponVo;
	}
	
}