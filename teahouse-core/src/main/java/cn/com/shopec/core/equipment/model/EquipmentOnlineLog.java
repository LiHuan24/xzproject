package cn.com.shopec.core.equipment.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 健身设备上下线日志表 数据实体类
 */
public class EquipmentOnlineLog extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 设备上下线日志编号
	private String logNo;
	// 分类名称
	private String fitnessEquipmentNo;
	// 操作类型（0 下线 1 上线）
	private Integer opType;
	// 上下线理由
	private String updownWhy;
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
	// 操作人名称
	public String userName;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return logNo;
	}

	public String getLogNo() {
		return logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	public String getFitnessEquipmentNo() {
		return fitnessEquipmentNo;
	}

	public void setFitnessEquipmentNo(String fitnessEquipmentNo) {
		this.fitnessEquipmentNo = fitnessEquipmentNo;
	}

	public Integer getOpType() {
		return opType;
	}

	public void setOpType(Integer opType) {
		this.opType = opType;
	}

	public String getUpdownWhy() {
		return updownWhy;
	}

	public void setUpdownWhy(String updownWhy) {
		this.updownWhy = updownWhy;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "EquipmentOnlineLog [" + "logNo = " + logNo + ", fitnessEquipmentNo = " + fitnessEquipmentNo
				+ ", opType = " + opType + ", updownWhy = " + updownWhy + ", memo = " + memo + ", isDeleted = "
				+ isDeleted + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart
				+ ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = "
				+ updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
				+ ", operatorId = " + operatorId + "]";
	}
}
