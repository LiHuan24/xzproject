package cn.com.shopec.core.member.vo;

public class WalletVo {
	// 是否有正在使用的健身卡，1、有，2、没有
	private String isGymCard;
	// 健身卡有效天数
	private String gymCardDay;
	// 账户余额
	private String memberBalance;
	// 剩余 押金
	private String memberDeposit;
	// 押金状态：0、未缴押金，1、已缴押金，2、押金退款中
	private String depositStatus;
	// 支付类型：1、现金，2、芝麻信用
	private String paymentType;
	// 课程包剩余节数
	private String courseNumber;
	// 剩余优惠券
	private String memberCoupon;
	// 会员状态 0：非黑名单，1、黑名单
	private Integer memberStatus;

	public String getIsGymCard() {
		return isGymCard;
	}

	public void setIsGymCard(String isGymCard) {
		this.isGymCard = isGymCard;
	}

	public String getGymCardDay() {
		return gymCardDay;
	}

	public void setGymCardDay(String gymCardDay) {
		this.gymCardDay = gymCardDay;
	}

	public String getMemberDeposit() {
		return memberDeposit;
	}

	public void setMemberDeposit(String memberDeposit) {
		this.memberDeposit = memberDeposit;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getMemberCoupon() {
		return memberCoupon;
	}

	public void setMemberCoupon(String memberCoupon) {
		this.memberCoupon = memberCoupon;
	}

	public String getMemberBalance() {
		return memberBalance;
	}

	public void setMemberBalance(String memberBalance) {
		this.memberBalance = memberBalance;
	}

	public String getDepositStatus() {
		return depositStatus;
	}

	public void setDepositStatus(String depositStatus) {
		this.depositStatus = depositStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Integer getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(Integer memberStatus) {
		this.memberStatus = memberStatus;
	}
}