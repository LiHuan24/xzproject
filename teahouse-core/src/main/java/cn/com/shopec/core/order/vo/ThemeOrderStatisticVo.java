package cn.com.shopec.core.order.vo;
/**
 * 主题订单统计
 * @author LiHuan
 * Date 2018年1月4日 上午10:22:40
 */
public class ThemeOrderStatisticVo {

	private String themeOrderNo;//订单编号
	
	private Double orderAmount;//订单总金额
	
	private Double discountAmount;//优惠券总抵扣金额
	
	private Double balanceAmount;//余额总抵扣金额
	
	private Double payableAmount;//实际支付总金额
	
	private Integer courseBagNum;//抵用课时包总数量
	
	private String days;//天
	
	private String months;//月
	
	private String years;//年
	
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

	public String getThemeOrderNo() {
		return themeOrderNo;
	}

	public void setThemeOrderNo(String themeOrderNo) {
		this.themeOrderNo = themeOrderNo;
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

	public Integer getCourseBagNum() {
		return courseBagNum;
	}

	public void setCourseBagNum(Integer courseBagNum) {
		this.courseBagNum = courseBagNum;
	}
}