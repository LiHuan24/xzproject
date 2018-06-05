package cn.com.shopec.core.equipment.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 辅助设备表 数据实体类
 * 
 * @author LiHuan
 */
public class AuxiliaryEquipment extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 辅助设备编号
	private String auxiliaryEquipmentNo;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 门店编号
	private String storeNo;
	// 门店名称
	private String storeName;
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
	// 删除状态（0、未删除，1、已删除，默认0）
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

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return auxiliaryEquipmentNo;
	}

	public String getAuxiliaryEquipmentNo() {
		return auxiliaryEquipmentNo;
	}

	public void setAuxiliaryEquipmentNo(String auxiliaryEquipmentNo) {
		this.auxiliaryEquipmentNo = auxiliaryEquipmentNo;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "AuxiliaryEquipment [" + "auxiliaryEquipmentNo = " + auxiliaryEquipmentNo + ", cityId = " + cityId
				+ ", cityName = " + cityName + ", storeNo = " + storeNo + ", storeName = " + storeName + ", brandNo = "
				+ brandNo + ", brandName = " + brandName + ", modelNo = " + modelNo + ", modelName = " + modelName
				+ ", sortNo = " + sortNo + ", sortName = " + sortName + ", isDeleted = " + isDeleted + ", createTime = "
				+ createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
				+ ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = "
				+ updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId + ", memo = " + memo + "]";
	}
}
