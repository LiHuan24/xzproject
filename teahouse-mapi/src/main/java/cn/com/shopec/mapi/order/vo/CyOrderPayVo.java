package cn.com.shopec.mapi.order.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 课程预约支付数据封装
 * 
 * @author LiHuan Date 2017年11月17日 上午9:29:36
 */
public class CyOrderPayVo implements Serializable {
	private static final long serialVersionUID = -4867863665810166774L;

	private String orderNo;
	private String type;// 健身类型：0、计时，1、健身卡
	private String orderAmount;// 订单总价
	private Double balance;// 余额
	private List<CyCouponVo> listCouponVo; // 优惠券数据

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public List<CyCouponVo> getListCouponVo() {
		return listCouponVo;
	}

	public void setListCouponVo(List<CyCouponVo> listCouponVo) {
		this.listCouponVo = listCouponVo;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

}