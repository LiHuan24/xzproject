package cn.com.shopec.core.member.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 会员余额表 数据实体类
 */
public class MemberBalance extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 会员编号
	private String memberNo;
	// 账户余额
	private Double memberBalance;
	// 押金余额
	private Double memberDeposit;
	// 课程包剩余节数
	private Integer courseNumber;
	// 课程支付之前已抵扣的课程包节数 先冻结 支付后归0
	private Integer freeCourseBag;
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
		return memberNo;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public Double getMemberBalance() {
		return memberBalance;
	}

	public void setMemberBalance(Double memberBalance) {
		this.memberBalance = memberBalance;
	}

	public Double getMemberDeposit() {
		return memberDeposit;
	}

	public void setMemberDeposit(Double memberDeposit) {
		this.memberDeposit = memberDeposit;
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
		return "MemberBalance [" + "memberNo = " + memberNo + ", memberBalance = " + memberBalance
				+ ", memberDeposit = " + memberDeposit + ", createTime = " + createTime + ", createTimeStart = "
				+ createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime
				+ ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = "
				+ operatorType + ", operatorId = " + operatorId + "]";
	}

	public Integer getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(Integer courseNumber) {
		this.courseNumber = courseNumber;
	}

	public Integer getFreeCourseBag() {
		return freeCourseBag;
	}

	public void setFreeCourseBag(Integer freeCourseBag) {
		this.freeCourseBag = freeCourseBag;
	}
	
}
