package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;

/**
 * 会员绑定的优惠券信息
 * @author LiHuan
 * Date 2017年11月27日 下午3:28:59
 */
public class CouponVo implements Serializable{
	private static final long serialVersionUID = -4867863665810166774L;
	
	private String couponNo;//优惠券编号
	private String couponName;//优惠券名称
	private String memberNo;//会员编号
	private String conponType;//1、折扣 2、直减   3、健身体验券 4、课程体验券
	private String lapseAmount;//直减金额
	private Double discountRate;//折扣率
	private String freeCourseNumber;//课程体验节数
	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getConponType() {
		return conponType;
	}
	public void setConponType(String conponType) {
		this.conponType = conponType;
	}
	public String getLapseAmount() {
		return lapseAmount;
	}
	public void setLapseAmount(String lapseAmount) {
		this.lapseAmount = lapseAmount;
	}
	public Double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	public String getFreeCourseNumber() {
		return freeCourseNumber;
	}
	public void setFreeCourseNumber(String freeCourseNumber) {
		this.freeCourseNumber = freeCourseNumber;
	}
}