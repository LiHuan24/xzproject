package cn.com.shopec.core.equipment.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 设备型号表 数据实体类
 */
public class EquipmentModel extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 设备型号
	private String modelNo;
	// 型号名称
	private String modelName;
	// 品牌编号
	private String brandNo;
	// 品牌名称
	private String brandName;
	// 设备类型编号
	private String sortNo;
	// 设备型号图片1
	private String modelPictureUrl1;
	// 设备型号图片2
	private String modelPictureUrl2;
	// 设备型号图片3
	private String modelPictureUrl3;
	// 设备型号二维码
	private String modelQrCode;
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

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return modelNo;
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

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}

	public String getModelPictureUrl1() {
		return modelPictureUrl1;
	}

	public void setModelPictureUrl1(String modelPictureUrl1) {
		this.modelPictureUrl1 = modelPictureUrl1;
	}

	public String getModelPictureUrl2() {
		return modelPictureUrl2;
	}

	public void setModelPictureUrl2(String modelPictureUrl2) {
		this.modelPictureUrl2 = modelPictureUrl2;
	}

	public String getModelPictureUrl3() {
		return modelPictureUrl3;
	}

	public void setModelPictureUrl3(String modelPictureUrl3) {
		this.modelPictureUrl3 = modelPictureUrl3;
	}

	public String getModelQrCode() {
		return modelQrCode;
	}

	public void setModelQrCode(String modelQrCode) {
		this.modelQrCode = modelQrCode;
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
		return "EquipmentModel [" + "modelNo = " + modelNo + ", modelName = " + modelName + ", brandNo = " + brandNo
				+ ", brandName = " + brandName + ", modelPictureUrl1 = " + modelPictureUrl1 + ", modelPictureUrl2 = "
				+ modelPictureUrl2 + ", modelPictureUrl3 = " + modelPictureUrl3 + ", modelQrCode = " + modelQrCode
				+ ", isDeleted = " + isDeleted + ", createTime = " + createTime + ", createTimeStart = "
				+ createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime
				+ ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = "
				+ operatorType + ", operatorId = " + operatorId + ", sortNo = " + sortNo + "]";
	}
}
