package cn.com.shopec.mapi.member.vo;

public class CouponsVo {
	// 优惠券名称
	private String title;
	// 优惠方式(1.打折，2.直减，3.健身体验券，4.课程体验券)
	// private String couponMethod;
	// 面额
	private String Denomination;
	// 使用状态(0.未使用 1.已使用2.已过期)
	private String usedStatus;
	// 有效结束日期
	private String availableTime2;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsedStatus() {
		return usedStatus;
	}

	public void setUsedStatus(String usedStatus) {
		this.usedStatus = usedStatus;
	}

	public String getAvailableTime2() {
		return availableTime2;
	}

	public void setAvailableTime2(String availableTime2) {
		this.availableTime2 = availableTime2;
	}

	public String getDenomination() {
		return Denomination;
	}

	public void setDenomination(String denomination) {
		Denomination = denomination;
	}

}
