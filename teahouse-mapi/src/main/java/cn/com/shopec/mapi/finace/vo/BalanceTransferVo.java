package cn.com.shopec.mapi.finace.vo;


/**
 * 余额提现vo
 * @author admin
 *
 */
public class BalanceTransferVo {
	
	//会员编号
	private String memberNo;
	//转账编号
	private String transferNo;
	//申请时间
	private String applyTime;
	//转账金额
	private Double transferAmount;
	//转账方法（1、支付宝，2、微信）
	private Integer transferMethod;
	//转账时间
	private String transferTime;
	//审核状态（0、未审核,1、已审核,2、审核不通过,默认0）
	private Integer cencorStatus;
	//转账状态（0、未转账(申请中),1、已转账,2、转账失败,3、用户取消,4、后台取消,默认0）//其中2为保留项，在给予app数据时，无需告知失败，失败改为未转账
	private Integer transferStatus;
	//转入的账户
	private String transferAccount;
	//取消申请的原因
	private String cancelReason;
	
	public String getTransferNo() {
		return transferNo;
	}
	public void setTransferNo(String transferNo) {
		this.transferNo = transferNo;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public Double getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}
	public Integer getTransferMethod() {
		return transferMethod;
	}
	public void setTransferMethod(Integer transferMethod) {
		this.transferMethod = transferMethod;
	}
	public String getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(String transferTime) {
		this.transferTime = transferTime;
	}
	public Integer getCencorStatus() {
		return cencorStatus;
	}
	public void setCencorStatus(Integer cencorStatus) {
		this.cencorStatus = cencorStatus;
	}
	public Integer getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(Integer transferStatus) {
		this.transferStatus = transferStatus;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getTransferAccount() {
		return transferAccount;
	}
	public void setTransferAccount(String transferAccount) {
		this.transferAccount = transferAccount;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	@Override
	public String toString() {
		return "BalanceTransferVo [memberNo=" + memberNo + ", transferNo=" + transferNo + ", applyTime=" + applyTime
				+ ", transferAmount=" + transferAmount + ", transferMethod=" + transferMethod + ", transferTime="
				+ transferTime + ", cencorStatus=" + cencorStatus + ", transferStatus=" + transferStatus
				+ ", transferAccount=" + transferAccount + ", cancelReason=" + cancelReason + "]";
	}
}
