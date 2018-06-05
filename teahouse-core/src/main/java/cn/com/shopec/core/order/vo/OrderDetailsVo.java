package cn.com.shopec.core.order.vo;

public class OrderDetailsVo {
	// 订单编号
	private String communityOrderNo;
	// 订单门店名称
	private String storeName;
	// 消费类型：0、按小时，1、健身卡
	private String consumeType;
	// 订单开始时间
	private String createTime;
	// 订单结束时间
	private String endTime;
	// 订单时长（分钟）
	private String whenLong;
	// 优惠金额
	private String discountAmount;
	// 余额抵扣金额
	private Double balanceDiscountAmount;
	// 订单状态：0、进行中，1、已完成
	private Integer orderStatus;
	// 支付状态（0、未支付，1、已支付，默认0）
	private Integer payStatus;
	// 订单总金额
	private Double orderAmount;
	// 应付金额
	private Double payableAmount;

	public String getCommunityOrderNo() {
		return communityOrderNo;
	}

	public void setCommunityOrderNo(String communityOrderNo) {
		this.communityOrderNo = communityOrderNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getConsumeType() {
		return consumeType;
	}

	public void setConsumeType(String consumeType) {
		this.consumeType = consumeType;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getWhenLong() {
		return whenLong;
	}

	public void setWhenLong(String whenLong) {
		this.whenLong = whenLong;
	}

	public String getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getBalanceDiscountAmount() {
		return balanceDiscountAmount;
	}

	public void setBalanceDiscountAmount(Double balanceDiscountAmount) {
		this.balanceDiscountAmount = balanceDiscountAmount;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

}
