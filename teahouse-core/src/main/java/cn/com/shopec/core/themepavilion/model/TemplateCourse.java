package cn.com.shopec.core.themepavilion.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 排课模版表 数据实体类
 */
public class TemplateCourse extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 排课模版编号
	private String templateCourseNo;
	// 门店编号
	private String storeNo;
	// 门店名称
	private String storeName;
	// 课程编号
	private String courseNo;
	// 课程中文名称
	private String chineseName;
	// 课程起始时间
	private Date courseStart;
	// 课程起始时间 时间范围起（查询用）
	private Date courseStartStart;
	// 课程起始时间 时间范围止（查询用）
	private Date courseStartEnd;
	// 课程结束时间
	private Date courseEnd;
	// 课程结束时间 时间范围起（查询用）
	private Date courseEndStart;
	// 课程结束时间 时间范围止（查询用）
	private Date courseEndEnd;
	// 排课日期
	private Date courseDate;
	// 排课日期 时间范围起（查询用）
	private Date courseDateStart;
	// 排课日期 时间范围止（查询用）
	private Date courseDateEnd;
	// 排课星期（如周一）
	private String courseWeek;
	// 教练编号
	private String coachNo;
	// 教练名称
	private String coachName;
	// 课程人数
	private Integer peopleNumber;
	// 已预约人数
	private Integer reservation;
	// 排队人数
	private Integer lineUp;
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
	// 页面展示时间点
	private String showDate;
	//页面行数
	private Integer ftlRow;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return templateCourseNo;
	}

	public String getTemplateCourseNo() {
		return templateCourseNo;
	}

	public void setTemplateCourseNo(String templateCourseNo) {
		this.templateCourseNo = templateCourseNo;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getFtlRow() {
		return ftlRow;
	}

	public void setFtlRow(Integer ftlRow) {
		this.ftlRow = ftlRow;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public Date getCourseStart() {
		return courseStart;
	}

	public void setCourseStart(Date courseStart) {
		this.courseStart = courseStart;
	}

	public Date getCourseStartStart() {
		return courseStartStart;
	}

	public void setCourseStartStart(Date courseStartStart) {
		this.courseStartStart = courseStartStart;
	}

	public Date getCourseStartEnd() {
		return courseStartEnd;
	}

	public void setCourseStartEnd(Date courseStartEnd) {
		this.courseStartEnd = courseStartEnd;
	}

	public Date getCourseEnd() {
		return courseEnd;
	}

	public void setCourseEnd(Date courseEnd) {
		this.courseEnd = courseEnd;
	}

	public Date getCourseEndStart() {
		return courseEndStart;
	}

	public void setCourseEndStart(Date courseEndStart) {
		this.courseEndStart = courseEndStart;
	}

	public Date getCourseEndEnd() {
		return courseEndEnd;
	}

	public void setCourseEndEnd(Date courseEndEnd) {
		this.courseEndEnd = courseEndEnd;
	}

	public Date getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	public Date getCourseDateStart() {
		return courseDateStart;
	}

	public void setCourseDateStart(Date courseDateStart) {
		this.courseDateStart = courseDateStart;
	}

	public Date getCourseDateEnd() {
		return courseDateEnd;
	}

	public void setCourseDateEnd(Date courseDateEnd) {
		this.courseDateEnd = courseDateEnd;
	}

	public String getCourseWeek() {
		return courseWeek;
	}

	public void setCourseWeek(String courseWeek) {
		this.courseWeek = courseWeek;
	}

	public String getCoachNo() {
		return coachNo;
	}

	public void setCoachNo(String coachNo) {
		this.coachNo = coachNo;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public Integer getReservation() {
		return reservation;
	}

	public void setReservation(Integer reservation) {
		this.reservation = reservation;
	}

	public Integer getLineUp() {
		return lineUp;
	}

	public void setLineUp(Integer lineUp) {
		this.lineUp = lineUp;
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

	public String getShowDate() {
		return showDate;
	}

	public void setShowDate(String showDate) {
		this.showDate = showDate;
	}
	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "TemplateCourse [" + "templateCourseNo = " + templateCourseNo + ", storeNo = " + storeNo
				+ ", storeName = " + storeName + ", courseNo = " + courseNo + ", chineseName = " + chineseName
				+ ", courseStart = " + courseStart + ", courseStartStart = " + courseStartStart + ", courseStartEnd = "
				+ courseStartEnd + ", courseEnd = " + courseEnd + ", courseEndStart = " + courseEndStart
				+ ", courseEndEnd = " + courseEndEnd + ", courseDate = " + courseDate + ", courseDateStart = "
				+ courseDateStart + ", courseDateEnd = " + courseDateEnd + ", courseWeek = " + courseWeek
				+ ", coachNo = " + coachNo + ", coachName = " + coachName + ", peopleNumber = " + peopleNumber
				+ ", reservation = " + reservation + ", lineUp = " + lineUp + ", createTime = " + createTime
				+ ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = "
				+ updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
				+ ", operatorType = " + operatorType + ", operatorId = " + operatorId + "]";
	}
}
