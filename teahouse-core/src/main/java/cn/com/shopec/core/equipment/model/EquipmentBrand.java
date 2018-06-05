package cn.com.shopec.core.equipment.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 设备品牌表 数据实体类
 * @author LiHuan
 */
public class EquipmentBrand extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 设备品牌编号
	private String brandNo;
	// 品牌名称
	private String brandName;
	// 品牌类型：0、健身设备类型，1、辅助设备类型
	private Integer brandType;
	// 类型编号
	private String sortNo;
	// 品牌网址
	private String brandWebsite;
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
		return brandNo;
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

	public Integer getBrandType() {
		return brandType;
	}

	public void setBrandType(Integer brandType) {
		this.brandType = brandType;
	}

	public String getBrandWebsite() {
		return brandWebsite;
	}

	public void setBrandWebsite(String brandWebsite) {
		this.brandWebsite = brandWebsite;
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

	public String getSortNo() {
		return sortNo;
	}

	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "EquipmentBrand [" + "brandNo = " + brandNo + ", brandName = " + brandName + ", brandType = " + brandType
				+ ", brandWebsite = " + brandWebsite + ", isDeleted = " + isDeleted + ", createTime = " + createTime
				+ ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = "
				+ updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
				+ ", operatorType = " + operatorType + ", operatorId = " + operatorId + ", sortNo = " + sortNo + "]";
	}
}
