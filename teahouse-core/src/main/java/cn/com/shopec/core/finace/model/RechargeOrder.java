package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 充值订单表 数据实体类
 */
public class RechargeOrder extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 充值订单编号
	private String rechargeOrderNo;
	// 会员编号
	private String memberNo;
	// 会员姓名
	private String memberName;
	// 手机号
	private String mobilePhone;
	// 充值包编号
	private String rechargeNo;
	// 充值包名称
	private String rechargeName;
	// 充值包金额
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
	private String invoiceNo;
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
	private Integer operatorType;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return rechargeOrderNo;
	}

	public String getRechargeOrderNo() {
		return rechargeOrderNo;
	}

	public void setRechargeOrderNo(String rechargeOrderNo) {
		this.rechargeOrderNo = rechargeOrderNo;
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

	public String getRechargeNo() {
		return rechargeNo;
	}

	public void setRechargeNo(String rechargeNo) {
		this.rechargeNo = rechargeNo;
	}

	public String getRechargeName() {
		return rechargeName;
	}

	public void setRechargeName(String rechargeName) {
		this.rechargeName = rechargeName;
	}

	public Double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
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

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "RechargeOrder [" + "rechargeOrderNo = " + rechargeOrderNo + ", memberNo = " + memberNo
				+ ", memberName = " + memberName + ", mobilePhone = " + mobilePhone + ", rechargeNo = " + rechargeNo
				+ ", rechargeName = " + rechargeName + ", rechargeAmount = " + rechargeAmount + ", payableAmount = "
				+ payableAmount + ", payStatus = " + payStatus + ", paymentMethod = " + paymentMethod
				+ ", partTradeFlowNo = " + partTradeFlowNo + ", isAvailable = " + isAvailable + ", invoiceId = "
				+ invoiceId + ", invoiceNo = " + invoiceNo + ", invoiceStatus = " + invoiceStatus + ", createTime = "
				+ createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
				+ ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = "
				+ updateTimeEnd + ", operatorId = " + operatorId + ", operatorType = " + operatorType + "]";
	}

	public String getPaymentFlowNo() {
		return paymentFlowNo;
	}

	public void setPaymentFlowNo(String paymentFlowNo) {
		this.paymentFlowNo = paymentFlowNo;
	}
}
