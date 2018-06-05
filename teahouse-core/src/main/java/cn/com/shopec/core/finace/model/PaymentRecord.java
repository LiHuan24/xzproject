package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 支付记录表 数据实体类
 */
public class PaymentRecord extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 支付记录id
	private String paymemtId;
	// 支付类型：1、社区订单，2、主题订单，3、押金，4、余额充值，5、健身卡，6、课程包
	private Integer bizObjType;
	// 对象主键编号
	private String bizObjNo;
	// 应支付金额
	private Double payableAmount;
	// 已支付金额
	private Double paidAmount;
	// 支付状态(1、已支付，2、支付失败)
	private Integer payStatus;
	// 支付方式（1、支付宝，2、微信）
	private Integer payType;
	// 支付流水号
	private String payFlowNo;
	// 支付完成时间
	private Date paidTime;
	// 支付完成时间 时间范围起（查询用）
	private Date paidTimeStart;
	// 支付完成时间 时间范围止（查询用）
	private Date paidTimeEnd;
	// 买家支付账户号 支付宝或微信 唯一标识
	private String buyerId;
	// 买家账号 支付宝或微信登录账号，为邮箱或者手机号
	private String buyerAccount;
	// 内部支付流水号
	private String partTradeFlowNo;
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
		return paymemtId;
	}

	public String getPaymemtId() {
		return paymemtId;
	}

	public void setPaymemtId(String paymemtId) {
		this.paymemtId = paymemtId;
	}

	public Integer getBizObjType() {
		return bizObjType;
	}

	public void setBizObjType(Integer bizObjType) {
		this.bizObjType = bizObjType;
	}

	public String getBizObjNo() {
		return bizObjNo;
	}

	public void setBizObjNo(String bizObjNo) {
		this.bizObjNo = bizObjNo;
	}

	public Double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getPayFlowNo() {
		return payFlowNo;
	}

	public void setPayFlowNo(String payFlowNo) {
		this.payFlowNo = payFlowNo;
	}

	public Date getPaidTime() {
		return paidTime;
	}

	public void setPaidTime(Date paidTime) {
		this.paidTime = paidTime;
	}

	public Date getPaidTimeStart() {
		return paidTimeStart;
	}

	public void setPaidTimeStart(Date paidTimeStart) {
		this.paidTimeStart = paidTimeStart;
	}

	public Date getPaidTimeEnd() {
		return paidTimeEnd;
	}

	public void setPaidTimeEnd(Date paidTimeEnd) {
		this.paidTimeEnd = paidTimeEnd;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerAccount() {
		return buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	public String getPartTradeFlowNo() {
		return partTradeFlowNo;
	}

	public void setPartTradeFlowNo(String partTradeFlowNo) {
		this.partTradeFlowNo = partTradeFlowNo;
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
		return "PaymentRecord [" + "paymemtId = " + paymemtId + ", bizObjType = " + bizObjType + ", bizObjNo = "
				+ bizObjNo + ", payableAmount = " + payableAmount + ", paidAmount = " + paidAmount + ", payStatus = "
				+ payStatus + ", payType = " + payType + ", payFlowNo = " + payFlowNo + ", paidTime = " + paidTime
				+ ", paidTimeStart = " + paidTimeStart + ", paidTimeEnd = " + paidTimeEnd + ", buyerId = " + buyerId
				+ ", buyerAccount = " + buyerAccount + ", partTradeFlowNo = " + partTradeFlowNo + ", isDeleted = "
				+ isDeleted + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart
				+ ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = "
				+ updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
				+ ", operatorId = " + operatorId + "]";
	}
}
