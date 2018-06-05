package cn.com.shopec.mapi.finace.vo;

public class MemberDepositRefundVo {
	// 申请时间
	private String applyTime;
	// 退款金额
	private String refundAmount;
	// 退款状态（0、未审核，1、已审核，2、已退款，3、审核不通过，默认0）
	private String cencorStatus;

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getCencorStatus() {
		return cencorStatus;
	}

	public void setCencorStatus(String cencorStatus) {
		this.cencorStatus = cencorStatus;
	}

}
