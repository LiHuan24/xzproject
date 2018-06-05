package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 会员押金状态表数据实体类
 */
public class DepositStatus extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 会员编号
	private String memberNo;
	// 押金状态：0、未缴押金，1、已缴押金，2、押金退款中
	private Integer depositStatus;
	// 支付类型：1、现金，2、芝麻信用
	private Integer paymentType;
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
		return memberNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getDepositStatus() {
		return depositStatus;
	}

	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
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
		return "DepositStatus [" + "memberNo = " + memberNo + ", depositStatus = " + depositStatus + ", paymentType = "
				+ paymentType + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart
				+ ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = "
				+ updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
				+ ", operatorId = " + operatorId + "]";
	}
}
