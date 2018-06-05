package cn.com.shopec.core.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 社区订单表 数据实体类
 */
public class OrderSummary implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer orderCount;//订单数量
	private Double orderAmount;//总订单金额
	private Integer whenLong;//总时长
	private Double alreadyAmount;//实际支付金额
	private Double discountAmount;//优惠金额
	private Integer discountOrderCount;//优惠订单数
	private Double balanceDiscountAmount;//余额抵扣金额
	private Integer balanceOrderCount;//余额抵扣订单数
	private Integer alipayCount;//支付宝订单数
	private Double alipayAmount;//支付宝订单金额
	private Double alipayRealAmount;//支付宝支付金额
	private Double alipayCharge;//支付宝手续费
	private Integer wxCount;//微信订单数
	private Double wxAmount;//微信订单金额
	private Double wxRealAmount;//微信支付金额
	private Double wxCharge;//微信手续费
	private Date startTime;//对账周期 开始时间 时间范围起（查询用）
	private Date endTime;//对账周期 结束时间 时间范围起（查询用）
	
	public Integer getOrderCount() {
		return orderCount;
	}
	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Integer getWhenLong() {
		return whenLong;
	}
	public void setWhenLong(Integer whenLong) {
		this.whenLong = whenLong;
	}
	public Double getAlreadyAmount() {
		return alreadyAmount;
	}
	public void setAlreadyAmount(Double alreadyAmount) {
		this.alreadyAmount = alreadyAmount;
	}
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	public Integer getDiscountOrderCount() {
		return discountOrderCount;
	}
	public void setDiscountOrderCount(Integer discountOrderCount) {
		this.discountOrderCount = discountOrderCount;
	}
	public Double getBalanceDiscountAmount() {
		return balanceDiscountAmount;
	}
	public void setBalanceDiscountAmount(Double balanceDiscountAmount) {
		this.balanceDiscountAmount = balanceDiscountAmount;
	}
	public Integer getBalanceOrderCount() {
		return balanceOrderCount;
	}
	public void setBalanceOrderCount(Integer balanceOrderCount) {
		this.balanceOrderCount = balanceOrderCount;
	}
	public Integer getAlipayCount() {
		return alipayCount;
	}
	public void setAlipayCount(Integer alipayCount) {
		this.alipayCount = alipayCount;
	}
	public Double getAlipayAmount() {
		return alipayAmount;
	}
	public void setAlipayAmount(Double alipayAmount) {
		this.alipayAmount = alipayAmount;
	}
	public Double getAlipayRealAmount() {
		return alipayRealAmount;
	}
	public void setAlipayRealAmount(Double alipayRealAmount) {
		this.alipayRealAmount = alipayRealAmount;
	}
	public Double getAlipayCharge() {
		return alipayCharge;
	}
	public void setAlipayCharge(Double alipayCharge) {
		this.alipayCharge = alipayCharge;
	}
	public Integer getWxCount() {
		return wxCount;
	}
	public void setWxCount(Integer wxCount) {
		this.wxCount = wxCount;
	}
	public Double getWxAmount() {
		return wxAmount;
	}
	public void setWxAmount(Double wxAmount) {
		this.wxAmount = wxAmount;
	}
	public Double getWxRealAmount() {
		return wxRealAmount;
	}
	public void setWxRealAmount(Double wxRealAmount) {
		this.wxRealAmount = wxRealAmount;
	}
	public Double getWxCharge() {
		return wxCharge;
	}
	public void setWxCharge(Double wxCharge) {
		this.wxCharge = wxCharge;
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
	@Override
	public String toString() {
		return "OrderSummary [orderCount=" + orderCount + ", orderAmount=" + orderAmount + ", whenLong=" + whenLong
				+ ", alreadyAmount=" + alreadyAmount + ", discountAmount=" + discountAmount + ", discountOrderCount="
				+ discountOrderCount + ", balanceDiscountAmount=" + balanceDiscountAmount + ", balanceOrderCount="
				+ balanceOrderCount + ", alipayCount=" + alipayCount + ", alipayAmount=" + alipayAmount
				+ ", alipayRealAmount=" + alipayRealAmount + ", alipayCharge=" + alipayCharge + ", wxCount=" + wxCount
				+ ", wxAmount=" + wxAmount + ", wxRealAmount=" + wxRealAmount + ", wxCharge=" + wxCharge
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
	

}