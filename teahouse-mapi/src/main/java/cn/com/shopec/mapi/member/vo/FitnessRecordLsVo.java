package cn.com.shopec.mapi.member.vo;

public class FitnessRecordLsVo {
	// 设备名称
	private String sortName;
	// 里程
	private String rDist;
	// 使用时长
	private String whenLong;
	// 消耗
	private String rCal;
	// 使用开始时间
	private String startTime;

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getrDist() {
		return rDist;
	}

	public void setrDist(String rDist) {
		this.rDist = rDist;
	}

	public String getWhenLong() {
		return whenLong;
	}

	public void setWhenLong(String whenLong) {
		this.whenLong = whenLong;
	}

	public String getrCal() {
		return rCal;
	}

	public void setrCal(String rCal) {
		this.rCal = rCal;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
}
