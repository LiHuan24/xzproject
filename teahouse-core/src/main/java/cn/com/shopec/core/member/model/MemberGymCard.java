package cn.com.shopec.core.member.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 会员健身卡表 数据实体类
 */
public class MemberGymCard extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 会员健身卡编号
	private String memberGymCardNo;
	// 会员编号
	private String memberNo;
	// 会员姓名
	private String memberName;
	// 健身卡编号
	private String gymCardNo;
	// 健身卡名称
	private String gymCardName;
	// 健身卡类型：0、包天，1、包月，2、包年
	private Integer gymCardType;
	// 健身卡状态：1、使用中，2、已过期
	private Integer gymcardStatus;
	// 有效起始时间
	private Date startTime;
	// 有效起始时间 时间范围起（查询用）
	private Date startTimeStart;
	// 有效起始时间 时间范围止（查询用）
	private Date startTimeEnd;
	// 有效结束时间
	private Date endTime;
	// 有效结束时间 时间范围起（查询用）
	private Date endTimeStart;
	// 有效结束时间 时间范围止（查询用）
	private Date endTimeEnd;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;
	// 查询用
	private Date endTimeNs;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return memberGymCardNo;
	}

	public String getMemberGymCardNo() {
		return memberGymCardNo;
	}

	public void setMemberGymCardNo(String memberGymCardNo) {
		this.memberGymCardNo = memberGymCardNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getGymCardNo() {
		return gymCardNo;
	}

	public void setGymCardNo(String gymCardNo) {
		this.gymCardNo = gymCardNo;
	}

	public String getGymCardName() {
		return gymCardName;
	}

	public void setGymCardName(String gymCardName) {
		this.gymCardName = gymCardName;
	}

	public Integer getGymCardType() {
		return gymCardType;
	}

	public void setGymCardType(Integer gymCardType) {
		this.gymCardType = gymCardType;
	}

	public Integer getGymcardStatus() {
		return gymcardStatus;
	}

	public void setGymcardStatus(Integer gymcardStatus) {
		this.gymcardStatus = gymcardStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getStartTimeStart() {
		return startTimeStart;
	}

	public void setStartTimeStart(Date startTimeStart) {
		this.startTimeStart = startTimeStart;
	}

	public Date getStartTimeEnd() {
		return startTimeEnd;
	}

	public void setStartTimeEnd(Date startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getEndTimeStart() {
		return endTimeStart;
	}

	public void setEndTimeStart(Date endTimeStart) {
		this.endTimeStart = endTimeStart;
	}

	public Date getEndTimeEnd() {
		return endTimeEnd;
	}

	public void setEndTimeEnd(Date endTimeEnd) {
		this.endTimeEnd = endTimeEnd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "MemberGymCard [" + "memberGymCardNo = " + memberGymCardNo + ", memberNo = " + memberNo
				+ ", memberName = " + memberName + ", gymCardNo = " + gymCardNo + ", gymCardName = " + gymCardName
				+ ", gymCardType = " + gymCardType + ", gymcardStatus = " + gymcardStatus + ", startTime = " + startTime
				+ ", startTimeStart = " + startTimeStart + ", startTimeEnd = " + startTimeEnd + ", endTime = " + endTime
				+ ", endTimeStart = " + endTimeStart + ", endTimeEnd = " + endTimeEnd + ", createTime = " + createTime
				+ ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = "
				+ updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
				+ ", operatorType = " + operatorType + ", operatorId = " + operatorId + "]";
	}

	public Date getEndTimeNs() {
		return endTimeNs;
	}

	public void setEndTimeNs(Date endTimeNs) {
		this.endTimeNs = endTimeNs;
	}
}
