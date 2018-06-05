package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 课程包表 数据实体类
 */
public class CoursePackage extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 课程包编号
	private String coursePackageNo;
	// 课程包包名称
	private String coursePackageName;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 销售价
	private Double price;
	// 课程节数
	private Integer courseNumber;
	// 启用状态：0、不可用，1、可用（默认0）
	private Integer isEnable;
	// 审核状态（0、未审核，1、已审核，2、审核不通过，默认0）
	private Integer cencorStatus;
	// 审核备注
	private String cencorMemo;
	// 审核时间
	private Date cencorTime;
	// 审核时间 时间范围起（查询用）
	private Date cencorTimeStart;
	// 审核时间 时间范围止（查询用）
	private Date cencorTimeEnd;
	// 审核人id
	private String cencorId;
	// 删除状态（0、未删除，1、已删除，默认0）
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

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return coursePackageNo;
	}

	public String getCoursePackageNo() {
		return coursePackageNo;
	}

	public void setCoursePackageNo(String coursePackageNo) {
		this.coursePackageNo = coursePackageNo;
	}

	public String getCoursePackageName() {
		return coursePackageName;
	}

	public void setCoursePackageName(String coursePackageName) {
		this.coursePackageName = coursePackageName;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(Integer courseNumber) {
		this.courseNumber = courseNumber;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public Integer getCencorStatus() {
		return cencorStatus;
	}

	public void setCencorStatus(Integer cencorStatus) {
		this.cencorStatus = cencorStatus;
	}

	public String getCencorMemo() {
		return cencorMemo;
	}

	public void setCencorMemo(String cencorMemo) {
		this.cencorMemo = cencorMemo;
	}

	public Date getCencorTime() {
		return cencorTime;
	}

	public void setCencorTime(Date cencorTime) {
		this.cencorTime = cencorTime;
	}

	public Date getCencorTimeStart() {
		return cencorTimeStart;
	}

	public void setCencorTimeStart(Date cencorTimeStart) {
		this.cencorTimeStart = cencorTimeStart;
	}

	public Date getCencorTimeEnd() {
		return cencorTimeEnd;
	}

	public void setCencorTimeEnd(Date cencorTimeEnd) {
		this.cencorTimeEnd = cencorTimeEnd;
	}

	public String getCencorId() {
		return cencorId;
	}

	public void setCencorId(String cencorId) {
		this.cencorId = cencorId;
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

	@Override
	public String toString() {
		return "CoursePackage [" + "coursePackageNo = " + coursePackageNo + ", coursePackageName = " + coursePackageName
				+ ", cityId = " + cityId + ", cityName = " + cityName + ", price = " + price + ", courseNumber = "
				+ courseNumber + ", isEnable = " + isEnable + ", cencorStatus = " + cencorStatus + ", cencorMemo = "
				+ cencorMemo + ", cencorTime = " + cencorTime + ", cencorTimeStart = " + cencorTimeStart
				+ ", cencorTimeEnd = " + cencorTimeEnd + ", cencorId = " + cencorId + ", isDeleted = " + isDeleted
				+ ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = "
				+ createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart
				+ ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = "
				+ operatorId + "]";
	}
}
