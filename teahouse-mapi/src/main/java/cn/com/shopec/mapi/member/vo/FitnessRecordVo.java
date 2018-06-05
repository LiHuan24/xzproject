package cn.com.shopec.mapi.member.vo;

import java.util.List;

public class FitnessRecordVo {
	// 开始时间
	private String startTimeStart;
	// 结束时间
	private String endTimeEnd;
	// 总里程
	private String rDistTotal;
	// 总消耗
	private String rCalTotal;
	// 健身详细记录
	private List<FitnessRecordLsVo> ls;

	public String getStartTimeStart() {
		return startTimeStart;
	}

	public void setStartTimeStart(String startTimeStart) {
		this.startTimeStart = startTimeStart;
	}

	public String getEndTimeEnd() {
		return endTimeEnd;
	}

	public void setEndTimeEnd(String endTimeEnd) {
		this.endTimeEnd = endTimeEnd;
	}

	public String getrDistTotal() {
		return rDistTotal;
	}

	public void setrDistTotal(String rDistTotal) {
		this.rDistTotal = rDistTotal;
	}

	public String getrCalTotal() {
		return rCalTotal;
	}

	public void setrCalTotal(String rCalTotal) {
		this.rCalTotal = rCalTotal;
	}

	public List<FitnessRecordLsVo> getLs() {
		return ls;
	}

	public void setLs(List<FitnessRecordLsVo> ls) {
		this.ls = ls;
	}

}
