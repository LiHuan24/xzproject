package cn.com.shopec.core.order.vo;

public class CommunityOrderStatisticVo {
	private String communityOrderNo;//订单编号
	
	private Double orderAmount;//订单总金额
	
	private Double discountAmount;//优惠券总抵扣金额
	
	private Double balanceAmount;//余额总抵扣金额
	
	private Double payableAmount;//实际支付总金额
	
	private String whenLong;//订单时长
	
	private String days;//天
	
	private String months;//月
	
	private String years;//年

	public String getCommunityOrderNo() {
		return communityOrderNo;
	}

	public void setCommunityOrderNo(String communityOrderNo) {
		this.communityOrderNo = communityOrderNo;
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

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(Double payableAmount) {
		this.payableAmount = payableAmount;
	}

	public String getWhenLong() {
		return whenLong;
	}

	public void setWhenLong(String whenLong) {
		this.whenLong = whenLong;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getMonths() {
		return months;
	}

	public void setMonths(String months) {
		this.months = months;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}
	
	
}
