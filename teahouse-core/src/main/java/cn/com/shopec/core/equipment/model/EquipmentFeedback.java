package cn.com.shopec.core.equipment.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 设备报修表 数据实体类
 */
public class EquipmentFeedback extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 设备报修编号
	private String equipmentFeedbackNo;
	// 设备编号
	private String fitnessEquipmentNo;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 分类名称
	private String sortName;
	// 门店编号
	private String storeNo;
	// 门店名称
	private String storeName;
	// 报修类型：数据字典项维护
	private String feedbackId;
	// 报修状态：0、未开始，1、进行中，2、已完成
	private Integer feedbackStatus;
	// 会员编号
	private String memberNo;
	// 会员名称
	private String memberName;
	// 会员手机
	private String mobilePhone;
	// 维修图片1
	private String feedbackPictureUrl1;
	// 维修图片2
	private String feedbackPictureUrl2;
	// 维修图片3
	private String feedbackPictureUrl3;
	// 备注
	private String memo;
	// 删除状态（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;
	// 设备报修名称
	private String feedbackName;
	// 是否已转成设备维修记录 0、未转 1、已转 TURN_STATUS
	private Integer turnStatus;
	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return equipmentFeedbackNo;
	}

	public String getEquipmentFeedbackNo() {
		return equipmentFeedbackNo;
	}

	public void setEquipmentFeedbackNo(String equipmentFeedbackNo) {
		this.equipmentFeedbackNo = equipmentFeedbackNo;
	}

	public String getFitnessEquipmentNo() {
		return fitnessEquipmentNo;
	}

	public void setFitnessEquipmentNo(String fitnessEquipmentNo) {
		this.fitnessEquipmentNo = fitnessEquipmentNo;
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

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(String feedbackId) {
		this.feedbackId = feedbackId;
	}

	public Integer getFeedbackStatus() {
		return feedbackStatus;
	}

	public void setFeedbackStatus(Integer feedbackStatus) {
		this.feedbackStatus = feedbackStatus;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
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

	public String getFeedbackPictureUrl1() {
		return feedbackPictureUrl1;
	}

	public void setFeedbackPictureUrl1(String feedbackPictureUrl1) {
		this.feedbackPictureUrl1 = feedbackPictureUrl1;
	}

	public String getFeedbackPictureUrl2() {
		return feedbackPictureUrl2;
	}

	public void setFeedbackPictureUrl2(String feedbackPictureUrl2) {
		this.feedbackPictureUrl2 = feedbackPictureUrl2;
	}

	public String getFeedbackPictureUrl3() {
		return feedbackPictureUrl3;
	}

	public void setFeedbackPictureUrl3(String feedbackPictureUrl3) {
		this.feedbackPictureUrl3 = feedbackPictureUrl3;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

	public String getFeedbackName() {
		return feedbackName;
	}

	public void setFeedbackName(String feedbackName) {
		this.feedbackName = feedbackName;
	}

	public Integer getTurnStatus() {
		return turnStatus;
	}

	public void setTurnStatus(Integer turnStatus) {
		this.turnStatus = turnStatus;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "EquipmentFeedback [" + "equipmentFeedbackNo = " + equipmentFeedbackNo + ", fitnessEquipmentNo = "
				+ fitnessEquipmentNo + ", cityId = " + cityId + ", cityName = " + cityName + ", storeNo = " + storeNo
				+ ", storeName = " + storeName + ", feedbackId = " + feedbackId + ", feedbackStatus = " + feedbackStatus
				+ ", memberNo = " + memberNo + ", memberName = " + memberName + ", mobilePhone = " + mobilePhone
				+ ", feedbackPictureUrl1 = " + feedbackPictureUrl1 + ", feedbackPictureUrl2 = " + feedbackPictureUrl2
				+ ", feedbackPictureUrl3 = " + feedbackPictureUrl3 + ", memo = " + memo + ", isDeleted = " + isDeleted
				+ ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = "
				+ createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart
				+ ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = "
				+ operatorId + ", feedbackName = " + feedbackName + ", turnStatus = " + turnStatus + "]";
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
}
