package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 优惠券表 数据实体类
 */
public class Coupon extends Entity<String> {

	private static final long serialVersionUID = -7684823387692067730L;

	// 优惠卷编号
	private String couponNo;
	// 方案编号
	private String planNo;
	// 会员编号
	private String memberNo;
	// 业务订单类型
	private String bizObjType;
	// 订单编号
	private String bizObjNo;
	// 标题
	private String title;
	// 优惠卷类型(1.优惠券)
	private Integer couponType;
	// 优惠方式(1.打折，2.直减，3.健身体验券，4.课程体验券)
	private Integer couponMethod;
	// 免费健身时长---社区馆
	private String freeFitnessTime;
	// 免费体验课程优惠券节数
	private String freeCourseNumber;
	// 折扣金额
	private Double discount;
	// 直减金额
	private Double discountAmount;
	// 实际抵扣金额
	private Double deductibleAmount;
	// 有效起始日期
	private Date availableTime1;
	// 有效起始日期 时间范围起（查询用）
	private Date availableTime1Start;
	// 有效起始日期 时间范围止（查询用）
	private Date availableTime1End;
	// 有效结束日期
	private Date availableTime2;
	// 有效结束日期 时间范围起（查询用）
	private Date availableTime2Start;
	// 有效结束日期 时间范围止（查询用）
	private Date availableTime2End;
	// 有效天数
	private Integer availableDays;
	// 发放状态(1.系统注册 2活动奖励 3手动发放 4充值赠送)
	private Integer issueMethod;
	// 发放人
	private String issuerId;
	// 发放渠道
	private String issueChannel;
	// 发放时间
	private Date issueTime;
	// 发放时间 时间范围起（查询用）
	private Date issueTimeStart;
	// 发放时间 时间范围止（查询用）
	private Date issueTimeEnd;
	// 启用状态(0.停用 1.启用)
	private Integer isAvailable;
	// 启用时间
	private Date availableUpdateTime;
	// 使用状态(0.未使用 1.已使用)
	private Integer usedStatus;
	// 使用时间
	private Date usedTime;
	// 使用时间 时间范围起（查询用）
	private Date usedTimeStart;
	// 使用时间 时间范围止（查询用）
	private Date usedTimeEnd;
	// 会员姓名（查询用）
	private String memberName;
	// 会员手机号（查询用）
	private String mobilePhone;
	/** 该变量为批量增加时前台传值用，各个会员编号间采用英文逗号间隔 **/
	private String memberNos;

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getBizObjType() {
		return bizObjType;
	}

	public void setBizObjType(String bizObjType) {
		this.bizObjType = bizObjType;
	}

	public String getBizObjNo() {
		return bizObjNo;
	}

	public void setBizObjNo(String bizObjNo) {
		this.bizObjNo = bizObjNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}

	public Integer getCouponMethod() {
		return couponMethod;
	}

	public void setCouponMethod(Integer couponMethod) {
		this.couponMethod = couponMethod;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getDeductibleAmount() {
		return deductibleAmount;
	}

	public void setDeductibleAmount(Double deductibleAmount) {
		this.deductibleAmount = deductibleAmount;
	}

	public Date getAvailableTime1() {
		return availableTime1;
	}

	public void setAvailableTime1(Date availableTime1) {
		this.availableTime1 = availableTime1;
	}

	public Date getAvailableTime2() {
		return availableTime2;
	}

	public void setAvailableTime2(Date availableTime2) {
		this.availableTime2 = availableTime2;
	}

	public Integer getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(Integer availableDays) {
		this.availableDays = availableDays;
	}

	public Integer getIssueMethod() {
		return issueMethod;
	}

	public void setIssueMethod(Integer issueMethod) {
		this.issueMethod = issueMethod;
	}

	public String getIssueChannel() {
		return issueChannel;
	}

	public void setIssueChannel(String issueChannel) {
		this.issueChannel = issueChannel;
	}

	public Date getIssueTime() {
		return issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Date getAvailableUpdateTime() {
		return availableUpdateTime;
	}

	public void setAvailableUpdateTime(Date availableUpdateTime) {
		this.availableUpdateTime = availableUpdateTime;
	}

	public Integer getUsedStatus() {
		return usedStatus;
	}

	public void setUsedStatus(Integer usedStatus) {
		this.usedStatus = usedStatus;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public String getMemberNos() {
		return memberNos;
	}

	public void setMemberNos(String memberNos) {
		this.memberNos = memberNos;
	}

	public Date getIssueTimeStart() {
		return issueTimeStart;
	}

	public void setIssueTimeStart(Date issueTimeStart) {
		this.issueTimeStart = issueTimeStart;
	}

	public Date getIssueTimeEnd() {
		return issueTimeEnd;
	}

	public void setIssueTimeEnd(Date issueTimeEnd) {
		this.issueTimeEnd = issueTimeEnd;
	}

	public Date getAvailableTime1Start() {
		return availableTime1Start;
	}

	public void setAvailableTime1Start(Date availableTime1Start) {
		this.availableTime1Start = availableTime1Start;
	}

	public Date getAvailableTime1End() {
		return availableTime1End;
	}

	public void setAvailableTime1End(Date availableTime1End) {
		this.availableTime1End = availableTime1End;
	}

	public Date getAvailableTime2Start() {
		return availableTime2Start;
	}

	public void setAvailableTime2Start(Date availableTime2Start) {
		this.availableTime2Start = availableTime2Start;
	}

	public Date getAvailableTime2End() {
		return availableTime2End;
	}

	public void setAvailableTime2End(Date availableTime2End) {
		this.availableTime2End = availableTime2End;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Date getUsedTimeStart() {
		return usedTimeStart;
	}

	public void setUsedTimeStart(Date usedTimeStart) {
		this.usedTimeStart = usedTimeStart;
	}

	public Date getUsedTimeEnd() {
		return usedTimeEnd;
	}

	public void setUsedTimeEnd(Date usedTimeEnd) {
		this.usedTimeEnd = usedTimeEnd;
	}

	public String getFreeFitnessTime() {
		return freeFitnessTime;
	}

	public void setFreeFitnessTime(String freeFitnessTime) {
		this.freeFitnessTime = freeFitnessTime;
	}

	public String getFreeCourseNumber() {
		return freeCourseNumber;
	}

	public void setFreeCourseNumber(String freeCourseNumber) {
		this.freeCourseNumber = freeCourseNumber;
	}

	@Override
	public String toString() {
		return "Coupon [couponNo=" + couponNo + ", planNo=" + planNo + ", memberNo=" + memberNo + ", bizObjType="
				+ bizObjType + ", bizObjNo=" + bizObjNo + ", title=" + title + ", couponType=" + couponType
				+ ", couponMethod=" + couponMethod + ", freeFitnessTime=" + freeFitnessTime + ", freeCourseNumber="
				+ freeCourseNumber + ", discount=" + discount + ", discountAmount=" + discountAmount
				+ ", deductibleAmount=" + deductibleAmount + ", availableTime1=" + availableTime1
				+ ", availableTime1Start=" + availableTime1Start + ", availableTime1End=" + availableTime1End
				+ ", availableTime2=" + availableTime2 + ", availableTime2Start=" + availableTime2Start
				+ ", availableTime2End=" + availableTime2End + ", availableDays=" + availableDays + ", issueMethod="
				+ issueMethod + ", issuerId=" + issuerId + ", issueChannel=" + issueChannel + ", issueTime=" + issueTime
				+ ", issueTimeStart=" + issueTimeStart + ", issueTimeEnd=" + issueTimeEnd + ", isAvailable="
				+ isAvailable + ", availableUpdateTime=" + availableUpdateTime + ", usedStatus=" + usedStatus
				+ ", usedTime=" + usedTime + ", usedTimeStart=" + usedTimeStart + ", usedTimeEnd=" + usedTimeEnd
				+ ", memberName=" + memberName + ", mobilePhone=" + mobilePhone + ", memberNos=" + memberNos + "]";
	}
}