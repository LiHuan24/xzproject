package cn.com.shopec.mapi.order.vo;

public class CyOrderVo {
	// 是否正在使用设备，0：使用中，1：未使用
	private String isUse;
	// 会员健身开始时间
	private String startTime;
	// 消费类型：0、按小时，1、健身卡
	private String gymCardType;
	// 设备id
	private String devId;
	// 设备类别
	private String sortName;
	// 订单编号
	private String communityOrderNo;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getGymCardType() {
		return gymCardType;
	}

	public void setGymCardType(String gymCardType) {
		this.gymCardType = gymCardType;
	}

	public String getDevId() {
		return devId;
	}

	public void setDevId(String devId) {
		this.devId = devId;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getCommunityOrderNo() {
		return communityOrderNo;
	}

	public void setCommunityOrderNo(String communityOrderNo) {
		this.communityOrderNo = communityOrderNo;
	}

	public String getIsUse() {
		return isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

}
