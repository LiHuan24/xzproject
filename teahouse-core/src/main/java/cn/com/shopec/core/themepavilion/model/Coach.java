package cn.com.shopec.core.themepavilion.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 教练表 数据实体类
 */
public class Coach extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 教练编号
	private String coachNo;
	// 教练名称
	private String coachName;
	// 昵称
	private String coachNick;
	// 教练简介
	private String synopsis;
	// 头像照片url
	private String memberPhotoUrl;
	// 手机号
	private String mobilePhone;
	// 性别（0、女，1、男）
	private Integer sex;
	// 身份证
	private String idCard;
	// 教练二维码
	private String coachQrCode;
	// 注册时间
	private Date registerTime;
	// 注册时间 时间范围起（查询用）
	private Date registerTimeStart;
	// 注册时间 时间范围止（查询用）
	private Date registerTimeEnd;
	// 审核状态（认证状态）（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
	private Integer censorStatus;
	// 教练状态 启用 1 停用 0
	private Integer coachStatus;
	// 审核时间
	private Date cencorTime;
	// 审核时间 时间范围起（查询用）
	private Date cencorTimeStart;
	// 审核时间 时间范围止（查询用）
	private Date cencorTimeEnd;
	// 审核人id
	private String cencorId;
	// 审核备注
	private String cencorMemo;
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
		return coachNo;
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

	public String getCoachNick() {
		return coachNick;
	}

	public void setCoachNick(String coachNick) {
		this.coachNick = coachNick;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getMemberPhotoUrl() {
		return memberPhotoUrl;
	}

	public void setMemberPhotoUrl(String memberPhotoUrl) {
		this.memberPhotoUrl = memberPhotoUrl;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getRegisterTimeStart() {
		return registerTimeStart;
	}

	public void setRegisterTimeStart(Date registerTimeStart) {
		this.registerTimeStart = registerTimeStart;
	}

	public Date getRegisterTimeEnd() {
		return registerTimeEnd;
	}

	public void setRegisterTimeEnd(Date registerTimeEnd) {
		this.registerTimeEnd = registerTimeEnd;
	}

	public Integer getCensorStatus() {
		return censorStatus;
	}

	public void setCensorStatus(Integer censorStatus) {
		this.censorStatus = censorStatus;
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

	public String getCencorMemo() {
		return cencorMemo;
	}

	public void setCencorMemo(String cencorMemo) {
		this.cencorMemo = cencorMemo;
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

	public String getCoachQrCode() {
		return coachQrCode;
	}

	public void setCoachQrCode(String coachQrCode) {
		this.coachQrCode = coachQrCode;
	}

	public Integer getCoachStatus() {
		return coachStatus;
	}

	public void setCoachStatus(Integer coachStatus) {
		this.coachStatus = coachStatus;
	}
	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "Coach [" + "coachNo = " + coachNo + ", coachName = " + coachName + ", coachNick = " + coachNick
				+ ", synopsis = " + synopsis + ", memberPhotoUrl = " + memberPhotoUrl + ", mobilePhone = " + mobilePhone
				+ ", sex = " + sex + ", idCard = " + idCard + ", registerTime = " + registerTime
				+ ", registerTimeStart = " + registerTimeStart + ", registerTimeEnd = " + registerTimeEnd
				+ ", censorStatus = " + censorStatus + ", cencorTime = " + cencorTime + ", cencorTimeStart = "
				+ cencorTimeStart + ", cencorTimeEnd = " + cencorTimeEnd + ", cencorId = " + cencorId
				+ ", cencorMemo = " + cencorMemo + ", createTime = " + createTime + ", createTimeStart = "
				+ createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime
				+ ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = "
				+ operatorType + ", operatorId = " + operatorId + ", coachQrCode = " + coachQrCode + "]";
	}
}
