package cn.com.shopec.core.equipment.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * FitnessEquipment 数据实体类 健身设备表
 */
public class FitnessEquipment extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 健身设备编号
	private String fitnessEquipmentNo;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 门店编号
	private String storeNo;
	// 门店名称
	private String storeName;
	// 终端编号
	private String deviceNo;
	// 品牌编号
	private String brandNo;
	// 品牌名称
	private String brandName;
	// 型号编号
	private String modelNo;
	// 型号名称
	private String modelName;
	// 分类编号
	private String sortNo;
	// 分类名称
	private String sortName;
	// 使用状态:0，空闲、1，使用中、2，维修中
	private Integer useStatus;
	// 设备在线:0、设备在线，1、设备不在线
	private Integer isOnLine;
	// 上线状态（0、下线，1、上线，默认0）
	private Integer onlineStatus;
	// 上下线理由
	private String onOffLineReason;
	// 上线状态更新时间
	private Date onlineStatusUpdateTime;
	// 上线状态更新时间 时间范围起（查询用）
	private Date onlineStatusUpdateTimeStart;
	// 上线状态更新时间 时间范围止（查询用）
	private Date onlineStatusUpdateTimeEnd;
	// 是否删除（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
	// 备注
	private String memo;
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
	// 设备二维码ID
	private String devId;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return fitnessEquipmentNo;
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

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
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

	public Integer getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public String getOnOffLineReason() {
		return onOffLineReason;
	}

	public void setOnOffLineReason(String onOffLineReason) {
		this.onOffLineReason = onOffLineReason;
	}

	public Date getOnlineStatusUpdateTime() {
		return onlineStatusUpdateTime;
	}

	public void setOnlineStatusUpdateTime(Date onlineStatusUpdateTime) {
		this.onlineStatusUpdateTime = onlineStatusUpdateTime;
	}

	public Date getOnlineStatusUpdateTimeStart() {
		return onlineStatusUpdateTimeStart;
	}

	public void setOnlineStatusUpdateTimeStart(Date onlineStatusUpdateTimeStart) {
		this.onlineStatusUpdateTimeStart = onlineStatusUpdateTimeStart;
	}

	public Date getOnlineStatusUpdateTimeEnd() {
		return onlineStatusUpdateTimeEnd;
	}

	public void setOnlineStatusUpdateTimeEnd(Date onlineStatusUpdateTimeEnd) {
		this.onlineStatusUpdateTimeEnd = onlineStatusUpdateTimeEnd;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}
	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "FitnessEquipment [" + "fitnessEquipmentNo = " + fitnessEquipmentNo + ", cityId = " + cityId
				+ ", cityName = " + cityName + ", storeNo = " + storeNo + ", storeName = " + storeName + ", deviceNo = "
				+ deviceNo + ", brandNo = " + brandNo + ", brandName = " + brandName + ", modelNo = " + modelNo
				+ ", modelName = " + modelName + ", sortNo = " + sortNo + ", sortName = " + sortName
				+ ", onlineStatus = " + onlineStatus + ", onOffLineReason = " + onOffLineReason
				+ ", onlineStatusUpdateTime = " + onlineStatusUpdateTime + ", onlineStatusUpdateTimeStart = "
				+ onlineStatusUpdateTimeStart + ", onlineStatusUpdateTimeEnd = " + onlineStatusUpdateTimeEnd
				+ ", isDeleted = " + isDeleted + ", memo = " + memo + ", createTime = " + createTime
				+ ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = "
				+ updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
				+ ", operatorType = " + operatorType + ", operatorId = " + operatorId + ", devId = " + devId + "]";
	}

	public Integer getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	public Integer getIsOnLine() {
		return isOnLine;
	}

	public void setIsOnLine(Integer isOnLine) {
		this.isOnLine = isOnLine;
	}
}
