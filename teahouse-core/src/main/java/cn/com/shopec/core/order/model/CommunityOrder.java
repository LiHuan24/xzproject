package cn.com.shopec.core.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 社区订单表 数据实体类
 */
public class CommunityOrder implements Serializable {

	private String communityOrderNo;// 社区订单编号
	private String memberNo;// 会员编号
	private String mobilePhone;// '会员手机
	private String memberGymCardNo;// 会员健身卡编号
	private String cityId;// 城市ID
	private String cityName;// 城市名称
	private String storeNo;// 门店编号
	private String storeName;// 门店名称
	private Date startTime;// 订单开始时间
	private Date endTime;// 订单结束时间
	private String whenLong;// 订单时长
	private Integer consumeType;// 消费类型：0、按小时，1、健身卡
	private Integer orderStatus;// 订单状态：0、进行中，1、已结束，2、已取消
	private Integer payStatus;// 支付状态（0、未支付，1、已支付，默认0）
	private Integer paymentMethod;// 支付方式（1、支付宝、2、微信,3、其它）
	private Date paymentTime;// 支付时间
	private String paymentFlowNo;// 支付流水号
	private String partTradeFlowNo;// 内部支付流水号
	private Double orderAmount;// 订单总金额
	private String couponNo;// 优惠券编号
	private Double discountAmount;// 优惠金额'
	private Double balanceDiscountAmount;// 余额抵扣金额
	private Double payableAmount;// 应付金额
	private Integer isNeedInvoice;// 是否需开票（0，不开票，1、需开票，默认0）
	private Integer isInvoiceIssued;// 是否已开发票（0，未开票，1，已开票，默认0）
	private Date invoiceTime;// 开票时间
	private String invioceId;// 发票id
	private String invioceNo;// 发票号
	private String memo;// 备注

	private Integer operatorType;// 操作人类型
	private String operatorId;// 操作人id
	private Date createTime;// 创建时间
	private Date updateTime;// 更新时间
	private Integer isDeleted;// 删除状态（0、未删除，1、已删除，默认0）

	private Date paymentTimeStart; // 支付时间 时间范围起（查询用）
	private Date paymentTimeEnd; // 支付时间 时间范围止（查询用）
	private Date invoiceTimeStart; // 开票时间 时间范围起（查询用）
	private Date invoiceTimeEnd; // 开票时间 时间范围止（查询用）

	private Date startTimeStart;// 订单开始时间 时间范围起（查询用）
	private Date startTimeEnd; // 订单开始时间 时间范围止（查询用）
	private Date endTimeStart;// 订单结束时间 时间范围起（查询用）
	private Date endTimeEnd;// 订单结束时间 时间范围止（查询用）

	private Date createTimeStart;// 创建时间 时间范围起（查询用）
	private Date createTimeEnd; // 创建时间 时间范围止（查询用）
	private Date updateTimeStart;// 更新时间 时间范围起（查询用）
	private Date updateTimeEnd;// 更新时间 时间范围止（查询用）

	private String memberName;// 会员名称（查询用）

	private static final long serialVersionUID = 1L;

	public String getCommunityOrderNo() {
		return communityOrderNo;
	}

	public void setCommunityOrderNo(String communityOrderNo) {
		this.communityOrderNo = communityOrderNo;
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

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMemberGymCardNo() {
		return memberGymCardNo;
	}

	public void setMemberGymCardNo(String memberGymCardNo) {
		this.memberGymCardNo = memberGymCardNo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getWhenLong() {
		return whenLong;
	}

	public void setWhenLong(String whenLong) {
		this.whenLong = whenLong;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
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

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getPaymentFlowNo() {
		return paymentFlowNo;
	}

	public void setPaymentFlowNo(String paymentFlowNo) {
		this.paymentFlowNo = paymentFlowNo;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getBalanceDiscountAmount() {
		return balanceDiscountAmount;
	}

	public void setBalanceDiscountAmount(Double balanceDiscountAmount) {
		this.balanceDiscountAmount = balanceDiscountAmount;
	}

	public Double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public Integer getIsNeedInvoice() {
		return isNeedInvoice;
	}

	public void setIsNeedInvoice(Integer isNeedInvoice) {
		this.isNeedInvoice = isNeedInvoice;
	}

	public Integer getIsInvoiceIssued() {
		return isInvoiceIssued;
	}

	public void setIsInvoiceIssued(Integer isInvoiceIssued) {
		this.isInvoiceIssued = isInvoiceIssued;
	}

	public Date getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(Date invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public String getInvioceId() {
		return invioceId;
	}

	public void setInvioceId(String invioceId) {
		this.invioceId = invioceId;
	}

	public String getInvioceNo() {
		return invioceNo;
	}

	public void setInvioceNo(String invioceNo) {
		this.invioceNo = invioceNo;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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

	public Date getPaymentTimeStart() {
		return paymentTimeStart;
	}

	public void setPaymentTimeStart(Date paymentTimeStart) {
		this.paymentTimeStart = paymentTimeStart;
	}

	public Date getPaymentTimeEnd() {
		return paymentTimeEnd;
	}

	public void setPaymentTimeEnd(Date paymentTimeEnd) {
		this.paymentTimeEnd = paymentTimeEnd;
	}

	public Date getInvoiceTimeStart() {
		return invoiceTimeStart;
	}

	public void setInvoiceTimeStart(Date invoiceTimeStart) {
		this.invoiceTimeStart = invoiceTimeStart;
	}

	public Date getInvoiceTimeEnd() {
		return invoiceTimeEnd;
	}

	public void setInvoiceTimeEnd(Date invoiceTimeEnd) {
		this.invoiceTimeEnd = invoiceTimeEnd;
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

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", communityOrderNo=").append(communityOrderNo);
		sb.append(", cityId=").append(cityId);
		sb.append(", cityName=").append(cityName);
		sb.append(", storeNo=").append(storeNo);
		sb.append(", storeName=").append(storeName);
		sb.append(", memberNo=").append(memberNo);
		sb.append(", mobilePhone=").append(mobilePhone);
		sb.append(", memberGymCardNo=").append(memberGymCardNo);
		sb.append(", startTime=").append(startTime);
		sb.append(", endTime=").append(endTime);
		sb.append(", whenLong=").append(whenLong);
		sb.append(", orderStatus=").append(orderStatus);
		sb.append(", payStatus=").append(payStatus);
		sb.append(", paymentMethod=").append(paymentMethod);
		sb.append(", paymentTime=").append(paymentTime);
		sb.append(", paymentFlowNo=").append(paymentFlowNo);
		sb.append(", orderAmount=").append(orderAmount);
		sb.append(", discountAmount=").append(discountAmount);
		sb.append(", balanceDiscountAmount=").append(balanceDiscountAmount);
		sb.append(", payableAmount=").append(payableAmount);
		sb.append(", isNeedInvoice=").append(isNeedInvoice);
		sb.append(", isInvoiceIssued=").append(isInvoiceIssued);
		sb.append(", invoiceTime=").append(invoiceTime);
		sb.append(", invioceId=").append(invioceId);
		sb.append(", invioceNo=").append(invioceNo);
		sb.append(", memo=").append(memo);
		sb.append(", isDeleted=").append(isDeleted);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateTime=").append(updateTime);
		sb.append(", operatorType=").append(operatorType);
		sb.append(", operatorId=").append(operatorId);
		sb.append("]");
		return sb.toString();
	}

	public String getPartTradeFlowNo() {
		return partTradeFlowNo;
	}

	public void setPartTradeFlowNo(String partTradeFlowNo) {
		this.partTradeFlowNo = partTradeFlowNo;
	}

	public Integer getConsumeType() {
		return consumeType;
	}

	public void setConsumeType(Integer consumeType) {
		this.consumeType = consumeType;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
}