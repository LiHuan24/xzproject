package cn.com.shopec.core.themepavilion.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 课程签到评价表 数据实体类
 */
public class SignAppraise extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 课程签到评价ID
	private String siapId;
	// 课程排课编号
	private String arrayCourseNo;
	// 课程编号
	private String courseNo;
	// 课程中文名称
	private String chineseName;
	// 课程日期
	private String courseDate;
	// 课程日期 时间范围起（查询用）
	private Date courseDateStart;
	// 课程日期 时间范围止（查询用）
	private Date courseDateEnd;
	// 排课起始时间段
	private String startTimePeriod;
	// 排课结束时间段
	private String endTimePeriod;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 会员编号
	private String memberNo;
	// 会员名称
	private String memberName;
	// 签到时间
	private Date signDate;
	// 签到时间 时间范围起（查询用）
	private Date signDateStart;
	// 签到时间 时间范围止（查询用）
	private Date signDateEnd;
	// 课程评价内容
	private String courseAppraise;
	// 课程评价时间
	private Date courseAppraiseDate;
	// 课程评价时间 时间范围起（查询用）
	private Date courseAppraiseDateStart;
	// 课程评价时间 时间范围止（查询用）
	private Date courseAppraiseDateEnd;
	// 教练评价内容
	private String coachAppraise;
	// 教练评价时间
	private Date coachAppraiseDate;
	// 教练评价时间 时间范围起（查询用）
	private Date coachAppraiseDateStart;
	// 教练评价时间 时间范围止（查询用）
	private Date coachAppraiseDateEnd;
	// 门店编号
	private String storeNo;
	// 门店名称
	private String storeName;
	// 教练编号
	private String coachNo;
	// 教练名称
	private String coachname;
	// 订单ID
	private String themeOrderNo;
	// 是否删除（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
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
	// 签到类型 0 会员上课签到 1教练上课签到
	private Integer signType;
	// 是否已签到
	private Integer isSign;// 是否签到(是否签到：0、未签到，1、已签到，默认0)
	// 教练上课签到完成审核状态
	private Integer signStatus;// 是否审核 0 未审核 1 已审核
	// 教练app端是否已完成上课
	private Integer isFinish;// 是否完成 0 未完成 1 已完成
	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return siapId;
	}

	public String getSiapId() {
		return siapId;
	}

	public void setSiapId(String siapId) {
		this.siapId = siapId;
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

	public String getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(String courseDate) {
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

	public String getStartTimePeriod() {
		return startTimePeriod;
	}

	public void setStartTimePeriod(String startTimePeriod) {
		this.startTimePeriod = startTimePeriod;
	}

	public String getEndTimePeriod() {
		return endTimePeriod;
	}

	public void setEndTimePeriod(String endTimePeriod) {
		this.endTimePeriod = endTimePeriod;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
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

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Date getSignDateStart() {
		return signDateStart;
	}

	public void setSignDateStart(Date signDateStart) {
		this.signDateStart = signDateStart;
	}

	public Date getSignDateEnd() {
		return signDateEnd;
	}

	public void setSignDateEnd(Date signDateEnd) {
		this.signDateEnd = signDateEnd;
	}

	public String getCourseAppraise() {
		return courseAppraise;
	}

	public void setCourseAppraise(String courseAppraise) {
		this.courseAppraise = courseAppraise;
	}

	public Date getCourseAppraiseDate() {
		return courseAppraiseDate;
	}

	public void setCourseAppraiseDate(Date courseAppraiseDate) {
		this.courseAppraiseDate = courseAppraiseDate;
	}

	public Date getCourseAppraiseDateStart() {
		return courseAppraiseDateStart;
	}

	public void setCourseAppraiseDateStart(Date courseAppraiseDateStart) {
		this.courseAppraiseDateStart = courseAppraiseDateStart;
	}

	public Date getCourseAppraiseDateEnd() {
		return courseAppraiseDateEnd;
	}

	public void setCourseAppraiseDateEnd(Date courseAppraiseDateEnd) {
		this.courseAppraiseDateEnd = courseAppraiseDateEnd;
	}

	public String getCoachAppraise() {
		return coachAppraise;
	}

	public void setCoachAppraise(String coachAppraise) {
		this.coachAppraise = coachAppraise;
	}

	public Date getCoachAppraiseDate() {
		return coachAppraiseDate;
	}

	public void setCoachAppraiseDate(Date coachAppraiseDate) {
		this.coachAppraiseDate = coachAppraiseDate;
	}

	public Date getCoachAppraiseDateStart() {
		return coachAppraiseDateStart;
	}

	public void setCoachAppraiseDateStart(Date coachAppraiseDateStart) {
		this.coachAppraiseDateStart = coachAppraiseDateStart;
	}

	public Date getCoachAppraiseDateEnd() {
		return coachAppraiseDateEnd;
	}

	public void setCoachAppraiseDateEnd(Date coachAppraiseDateEnd) {
		this.coachAppraiseDateEnd = coachAppraiseDateEnd;
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

	public String getCoachNo() {
		return coachNo;
	}

	public void setCoachNo(String coachNo) {
		this.coachNo = coachNo;
	}

	public String getCoachname() {
		return coachname;
	}

	public void setCoachname(String coachname) {
		this.coachname = coachname;
	}

	public String getThemeOrderNo() {
		return themeOrderNo;
	}

	public void setThemeOrderNo(String themeOrderNo) {
		this.themeOrderNo = themeOrderNo;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

	public Integer getSignType() {
		return signType;
	}

	public void setSignType(Integer signType) {
		this.signType = signType;
	}

	public Integer getIsSign() {
		return isSign;
	}

	public void setIsSign(Integer isSign) {
		this.isSign = isSign;
	}

	public Integer getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}

	public Integer getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(Integer isFinish) {
		this.isFinish = isFinish;
	}

	public String getArrayCourseNo() {
		return arrayCourseNo;
	}

	public void setArrayCourseNo(String arrayCourseNo) {
		this.arrayCourseNo = arrayCourseNo;
	}

	@Override
	public String toString() {
		return "SignAppraise [" + "siapId = " + siapId + ", courseNo = " + courseNo + ", chineseName = " + chineseName
				+ ", courseDate = " + courseDate + ", courseDateStart = " + courseDateStart + ", courseDateEnd = "
				+ courseDateEnd + ", startTimePeriod = " + startTimePeriod + ", endTimePeriod = " + endTimePeriod
				+ ", cityId = " + cityId + ", cityName = " + cityName + ", memberNo = " + memberNo + ", memberName = "
				+ memberName + ", signDate = " + signDate + ", signDateStart = " + signDateStart + ", signDateEnd = "
				+ signDateEnd + ", courseAppraise = " + courseAppraise + ", courseAppraiseDate = " + courseAppraiseDate
				+ ", courseAppraiseDateStart = " + courseAppraiseDateStart + ", courseAppraiseDateEnd = "
				+ courseAppraiseDateEnd + ", coachAppraise = " + coachAppraise + ", coachAppraiseDate = "
				+ coachAppraiseDate + ", coachAppraiseDateStart = " + coachAppraiseDateStart
				+ ", coachAppraiseDateEnd = " + coachAppraiseDateEnd + ", storeNo = " + storeNo + ", storeName = "
				+ storeName + ", coachNo = " + coachNo + ", coachname = " + coachname + ", themeOrderNo = "
				+ themeOrderNo + ", isDeleted = " + isDeleted + ", createTime = " + createTime + ", createTimeStart = "
				+ createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime
				+ ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = "
				+ operatorType + ", operatorId = " + operatorId + "]";
	}
}
