package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 优惠券方案表 数据实体类
 */
public class CouponPlan extends Entity<String> {

	private static final long serialVersionUID = -7684823387692067730L;

	// 方案编号
	private String planNo;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 标题
	private String title;
	// 子标题
	private String subtitle;
	// 图片
	private String photoUrl;
	// 优惠卷类型(1.优惠券)
	private Integer couponType;
	// 优惠方式(1.打折，2.直减，3.健身体验券，4.课程体验券)
	private Integer couponMethod;
	// 免费健身时长---社区馆
	private String freeFitnessTime;
	// 免费体验课程优惠券节数
	private String freeCourseNumber;
	// 免费体验课程价钱
	private Double freeCoursePrice;
	// 打折优惠额
	private Double discount;
	// 直减优惠额
	private Double discountAmount;
	// 封顶金额
	private Double discountMaxAmount;
	// 满减金额
	private Double consumeAmount;
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
	// 备注
	private String remark;
	// 审核状态(0.未审核 1.通过 2.拒绝)
	private Integer censorStatus;
	// 审核人
	private String censorId;
	// 审核时间
	private Date censorTime;
	// 审核时间 时间范围起（查询用）
	private Date censorTimeStart;
	// 审核时间 时间范围止（查询用）
	private Date censorTimeEnd;
	// 审核备注
	private String censorMemo;
	// 启用状态(0.停用 1.启用)
	private Integer isAvailable;
	// 启用时间
	private Date availableUpdateTime;
	// 启用时间 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	// 启用时间 时间范围止（查询用）
	private Date availableUpdateTimeEnd;
	// 方案现生成优惠卷数
	private Integer existingQuantity;
	// 方案最大生成优惠卷数
	private Integer maxQuantity;
	// 删除状态
	private Integer deletedState;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 编辑时间
	private Date updateTime;
	// 编辑时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 编辑时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人ID
	private String operatorId;

	// 以下字段做查询用，并无存入数据库
	// 发放人（查看详情用）
	private String censorName;
	// 兑换数量（兑换码模块用）
	private Integer redeemQuantity;

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		this.planNo = planNo;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
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

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getDiscountMaxAmount() {
		return discountMaxAmount;
	}

	public void setDiscountMaxAmount(Double discountMaxAmount) {
		this.discountMaxAmount = discountMaxAmount;
	}

	public Double getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(Double consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public Date getAvailableTime1() {
		return availableTime1;
	}

	public void setAvailableTime1(Date availableTime1) {
		this.availableTime1 = availableTime1;
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

	public Date getAvailableTime2() {
		return availableTime2;
	}

	public void setAvailableTime2(Date availableTime2) {
		this.availableTime2 = availableTime2;
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

	public Integer getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(Integer availableDays) {
		this.availableDays = availableDays;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCensorStatus() {
		return censorStatus;
	}

	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
	}

	public String getCensorId() {
		return censorId;
	}

	public void setCensorId(String censorId) {
		this.censorId = censorId;
	}

	public Date getCensorTime() {
		return censorTime;
	}

	public void setCensorTime(Date censorTime) {
		this.censorTime = censorTime;
	}

	public Date getCensorTimeStart() {
		return censorTimeStart;
	}

	public void setCensorTimeStart(Date censorTimeStart) {
		this.censorTimeStart = censorTimeStart;
	}

	public Date getCensorTimeEnd() {
		return censorTimeEnd;
	}

	public void setCensorTimeEnd(Date censorTimeEnd) {
		this.censorTimeEnd = censorTimeEnd;
	}

	public String getCensorMemo() {
		return censorMemo;
	}

	public void setCensorMemo(String censorMemo) {
		this.censorMemo = censorMemo;
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

	public Date getAvailableUpdateTimeStart() {
		return availableUpdateTimeStart;
	}

	public void setAvailableUpdateTimeStart(Date availableUpdateTimeStart) {
		this.availableUpdateTimeStart = availableUpdateTimeStart;
	}

	public Date getAvailableUpdateTimeEnd() {
		return availableUpdateTimeEnd;
	}

	public void setAvailableUpdateTimeEnd(Date availableUpdateTimeEnd) {
		this.availableUpdateTimeEnd = availableUpdateTimeEnd;
	}

	public Integer getExistingQuantity() {
		return existingQuantity;
	}

	public void setExistingQuantity(Integer existingQuantity) {
		this.existingQuantity = existingQuantity;
	}

	public Integer getMaxQuantity() {
		return maxQuantity;
	}

	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}

	public Integer getDeletedState() {
		return deletedState;
	}

	public void setDeletedState(Integer deletedState) {
		this.deletedState = deletedState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getCensorName() {
		return censorName;
	}

	public void setCensorName(String censorName) {
		this.censorName = censorName;
	}

	public Integer getRedeemQuantity() {
		return redeemQuantity;
	}

	public void setRedeemQuantity(Integer redeemQuantity) {
		this.redeemQuantity = redeemQuantity;
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

	public Double getFreeCoursePrice() {
		return freeCoursePrice;
	}

	public void setFreeCoursePrice(Double freeCoursePrice) {
		this.freeCoursePrice = freeCoursePrice;
	}

	@Override
	public String toString() {
		return "CouponPlan [planNo=" + planNo + ", cityId=" + cityId + ", cityName=" + cityName + ", title=" + title
				+ ", subtitle=" + subtitle + ", photoUrl=" + photoUrl + ", couponType=" + couponType + ", couponMethod="
				+ couponMethod + ", freeFitnessTime=" + freeFitnessTime + ", freeCourseNumber=" + freeCourseNumber
				+ ", freeCoursePrice=" + freeCoursePrice + ", discount=" + discount + ", discountAmount="
				+ discountAmount + ", discountMaxAmount=" + discountMaxAmount + ", consumeAmount=" + consumeAmount
				+ ", availableTime1=" + availableTime1 + ", availableTime1Start=" + availableTime1Start
				+ ", availableTime1End=" + availableTime1End + ", availableTime2=" + availableTime2
				+ ", availableTime2Start=" + availableTime2Start + ", availableTime2End=" + availableTime2End
				+ ", availableDays=" + availableDays + ", remark=" + remark + ", censorStatus=" + censorStatus
				+ ", censorId=" + censorId + ", censorTime=" + censorTime + ", censorTimeStart=" + censorTimeStart
				+ ", censorTimeEnd=" + censorTimeEnd + ", censorMemo=" + censorMemo + ", isAvailable=" + isAvailable
				+ ", availableUpdateTime=" + availableUpdateTime + ", availableUpdateTimeStart="
				+ availableUpdateTimeStart + ", availableUpdateTimeEnd=" + availableUpdateTimeEnd
				+ ", existingQuantity=" + existingQuantity + ", maxQuantity=" + maxQuantity + ", deletedState="
				+ deletedState + ", createTime=" + createTime + ", createTimeStart=" + createTimeStart
				+ ", createTimeEnd=" + createTimeEnd + ", updateTime=" + updateTime + ", updateTimeStart="
				+ updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + ", operatorType=" + operatorType
				+ ", operatorId=" + operatorId + ", censorName=" + censorName + ", redeemQuantity=" + redeemQuantity
				+ "]";
	}
}