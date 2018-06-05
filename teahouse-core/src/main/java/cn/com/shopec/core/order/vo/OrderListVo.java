package cn.com.shopec.core.order.vo;

public class OrderListVo {
	// 订单编号
	private String communityOrderNo;
	// 订单门店名称
	private String storeName;
	// 订单开始时间
	private String createTime;
	// 订单状态：0、进行中，1、已完成
	private Integer orderStatus;
	// 支付状态0、未支付，1、已支付
	private Integer payStatus;

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

}
