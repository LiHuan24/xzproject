package cn.com.shopec.core.equipment.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 健身设备使用记录表 数据实体类
 */
public class EquipmentRecord extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 记录id
	private String recordId;
	// 订单编号
	private String orderNo;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 门店编号
	private String storeNo;
	// 门店名称
	private String storeName;
	// 会员编号
	private String memberNo;
	// 会员手机
	private String mobilePhone;
	// 健身设备编号
	private String fitnessEquipmentNo;
	// 健身分类名称
	private String sortName;
	// 使用开始时间
	private Date startTime;
	// 使用开始时间 时间范围起（查询用）
	private Date startTimeStart;
	// 使用开始时间 时间范围止（查询用）
	private Date startTimeEnd;
	// 使用结束时间
	private Date endTime;
	// 使用结束时间 时间范围起（查询用）
	private Date endTimeStart;
	// 使用结束时间 时间范围止（查询用）
	private Date endTimeEnd;
	// 使用状态:0、使用中，1、已停止
	private Integer useStatus;
	// 订单类型：0、计时，1、健身卡
	private Integer useType;
	// 删除状态（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
	// 本次跑步机启动后累计距离 单位：m
	private String rDist;
	// 本次跑步机启动后累计能耗 单位：cal（卡路里）
	private String rCal;
	// 使用时长
	private String whenLong;
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

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return recordId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getFitnessEquipmentNo() {
		return fitnessEquipmentNo;
	}

	public void setFitnessEquipmentNo(String fitnessEquipmentNo) {
		this.fitnessEquipmentNo = fitnessEquipmentNo;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTimeStart() {
		return startTimeStart;
	}

	public void setStartTimeStart(Date startTimeStart) {
		this.startTimeStart = startTimeStart;
	}

	public Date getStartTimeEnd() {
		return startTimeEnd;
	}

	public void setStartTimeEnd(Date startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTimeStart() {
		return endTimeStart;
	}

	public void setEndTimeStart(Date endTimeStart) {
		this.endTimeStart = endTimeStart;
	}

	public Date getEndTimeEnd() {
		return endTimeEnd;
	}

	public void setEndTimeEnd(Date endTimeEnd) {
		this.endTimeEnd = endTimeEnd;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
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

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "EquipmentRecord [" + "recordId = " + recordId + ", orderNo = " + orderNo + ", cityId = " + cityId
				+ ", cityName = " + cityName + ", storeNo = " + storeNo + ", storeName = " + storeName + ", memberNo = "
				+ memberNo + ", mobilePhone = " + mobilePhone + ", fitnessEquipmentNo = " + fitnessEquipmentNo
				+ ", sortName = " + sortName + ", startTime = " + startTime + ", startTimeStart = " + startTimeStart
				+ ", startTimeEnd = " + startTimeEnd + ", endTime = " + endTime + ", endTimeStart = " + endTimeStart
				+ ", endTimeEnd = " + endTimeEnd + ", useType = " + useType + ", isDeleted = " + isDeleted
				+ ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = "
				+ createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart
				+ ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = "
				+ operatorId + "]";
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	public String getrDist() {
		return rDist;
	}

	public void setrDist(String rDist) {
		this.rDist = rDist;
	}

	public String getrCal() {
		return rCal;
	}

	public void setrCal(String rCal) {
		this.rCal = rCal;
	}

	public String getWhenLong() {
		return whenLong;
	}

	public void setWhenLong(String whenLong) {
		this.whenLong = whenLong;
	}
}
