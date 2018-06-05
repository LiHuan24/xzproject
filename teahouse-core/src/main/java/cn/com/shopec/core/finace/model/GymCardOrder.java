package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 健身卡订单表 数据实体类
 */
public class GymCardOrder extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 健身卡订单编号
	private String gymCardOrderNo;
	// 会员编号
	private String memberNo;
	// 会员姓名
	private String memberName;
	// 手机号
	private String mobilePhone;
	// 健身卡编号
	private String gymCardNo;
	// 健身卡名称
	private String gymCardName;
	// 健身卡金额
	private Double rechargeAmount;
	// 应付金额
	private Double payableAmount;
	// 支付状态（0、未支付，1、已支付，2、部分支付-预留）
	private Integer payStatus;
	// 支付方式(1、支付宝2微信支付、3、银行卡转账4、其他)
	private Integer paymentMethod;
	private String partTradeFlowNo;
	// 支付流水号
	private String paymentFlowNo;
	// 是否可用（0，不可用，1、可用；默认1）
	private Integer isAvailable;
	// 发票id
	private String invoiceId;
	// 发票编号
	private String invoiceNo;
	// 开票状态
	private Integer invoiceStatus;
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
	// 操作人id
	private String operatorId;
	// 操作类型
	private Integer operatorType;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return gymCardOrderNo;
	}

	public String getGymCardOrderNo() {
		return gymCardOrderNo;
	}

	public void setGymCardOrderNo(String gymCardOrderNo) {
		this.gymCardOrderNo = gymCardOrderNo;
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

	public String getGymCardNo() {
		return gymCardNo;
	}

	public void setGymCardNo(String gymCardNo) {
		this.gymCardNo = gymCardNo;
	}

	public String getGymCardName() {
		return gymCardName;
	}

	public void setGymCardName(String gymCardName) {
		this.gymCardName = gymCardName;
	}

	public Double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPartTradeFlowNo() {
		return partTradeFlowNo;
	}

	public void setPartTradeFlowNo(String partTradeFlowNo) {
		this.partTradeFlowNo = partTradeFlowNo;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
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

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getPaymentFlowNo() {
		return paymentFlowNo;
	}

	public void setPaymentFlowNo(String paymentFlowNo) {
		this.paymentFlowNo = paymentFlowNo;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

}
