package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;

/**
 * Accounts 数据 vo对象 交易明细
 */
public class AccountsVo implements Serializable {

	private static final long serialVersionUID = -4867863665810166774L;

	// 记账单号
	private String accountNo;
	// 会员编号
	private String memberNo;
	// 业务单号
	private String businessNo;
	// 业务单类型（1、社区馆订单，2、主题馆订单，3、交押金，4、退押金，5、余额充值，6、健身卡，7、课程包 8、提现）
	private Integer businessType;
	// 记账类型（1、出账 2、入账）
	private Integer accountType;
	// 记账金额
	private Double accountMoney;
	// 记账时间
	private String accountTime;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Double getAccountMoney() {
		return accountMoney;
	}

	public void setAccountMoney(Double accountMoney) {
		this.accountMoney = accountMoney;
	}

	public String getAccountTime() {
		return accountTime;
	}

	public void setAccountTime(String accountTime) {
		this.accountTime = accountTime;
	}
}