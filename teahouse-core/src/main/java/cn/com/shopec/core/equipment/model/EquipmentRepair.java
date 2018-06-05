package cn.com.shopec.core.equipment.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 设备维修记录表 数据实体类
 */
public class EquipmentRepair extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 设备维修记录编号
	private String equipmentRepairNo;
	// 设备编号
	private String fitnessEquipmentNo;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 门店编号
	private String storeNo;
	// 门店名称
	private String storeName;
	// 维修类型：数据字典维护
	private String repairId;
	// 维修类型名称
	private String repairTypeName;
	// 设备分类ID
	private String sortNo;
	// 设备分类名称
	private String sortName;
	// 维护状态：0、未开始，1、进行中，2、已完成
	private Integer repairStatus;
	// 开始时间
	private Date startTime;
	// 开始时间 时间范围起（查询用）
	private Date startTimeStart;
	// 开始时间 时间范围止（查询用）
	private Date startTimeEnd;
	// 结束时间
	private Date endTime;
	// 结束时间 时间范围起（查询用）
	private Date endTimeStart;
	// 结束时间 时间范围止（查询用）
	private Date endTimeEnd;
	// 维修人姓名
	private String repairName;
	// 维修图片1
	private String repairPictureUrl1;
	// 维修图片2
	private String repairPictureUrl2;
	// 维修图片3
	private String repairPictureUrl3;
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
	// 记录人姓名---不入库
	private String recordName;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return equipmentRepairNo;
	}

	public String getEquipmentRepairNo() {
		return equipmentRepairNo;
	}

	public void setEquipmentRepairNo(String equipmentRepairNo) {
		this.equipmentRepairNo = equipmentRepairNo;
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

	public String getRepairId() {
		return repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}

	public String getRepairTypeName() {
		return repairTypeName;
	}

	public void setRepairTypeName(String repairTypeName) {
		this.repairTypeName = repairTypeName;
	}

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public Integer getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(Integer repairStatus) {
		this.repairStatus = repairStatus;
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

	public String getRepairName() {
		return repairName;
	}

	public void setRepairName(String repairName) {
		this.repairName = repairName;
	}

	public String getRepairPictureUrl1() {
		return repairPictureUrl1;
	}

	public void setRepairPictureUrl1(String repairPictureUrl1) {
		this.repairPictureUrl1 = repairPictureUrl1;
	}

	public String getRepairPictureUrl2() {
		return repairPictureUrl2;
	}

	public void setRepairPictureUrl2(String repairPictureUrl2) {
		this.repairPictureUrl2 = repairPictureUrl2;
	}

	public String getRepairPictureUrl3() {
		return repairPictureUrl3;
	}

	public void setRepairPictureUrl3(String repairPictureUrl3) {
		this.repairPictureUrl3 = repairPictureUrl3;
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

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "EquipmentRepair [" + "equipmentRepairNo = " + equipmentRepairNo + ", fitnessEquipmentNo = "
				+ fitnessEquipmentNo + ", cityId = " + cityId + ", cityName = " + cityName + ", storeNo = " + storeNo
				+ ", storeName = " + storeName + ", repairId = " + repairId + ", repairTypeName = " + repairTypeName
				+ ", sortNo = " + sortNo + ", sortName = " + sortName + ", repairStatus = " + repairStatus
				+ ", startTime = " + startTime + ", startTimeStart = " + startTimeStart + ", startTimeEnd = "
				+ startTimeEnd + ", endTime = " + endTime + ", endTimeStart = " + endTimeStart + ", endTimeEnd = "
				+ endTimeEnd + ", repairName = " + repairName + ", repairPictureUrl1 = " + repairPictureUrl1
				+ ", repairPictureUrl2 = " + repairPictureUrl2 + ", repairPictureUrl3 = " + repairPictureUrl3
				+ ", memo = " + memo + ", isDeleted = " + isDeleted + ", createTime = " + createTime
				+ ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = "
				+ updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
				+ ", operatorType = " + operatorType + ", operatorId = " + operatorId + "]";
	}
}
