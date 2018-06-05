package cn.com.shopec.core.member.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 会员信息表 数据实体类
 */
public class Member extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 会员编号
	private String memberNo;
	// 头像照片url
	private String memberPhotoUrl;
	// 姓名
	private String memberName;
	// 昵称
	private String memberNick;
	// 手机号
	private String mobilePhone;
	// 密码
	private String password;
	// 性别（0、女，1、男）
	private Integer sex;
	// 身高
	private Integer height;
	// 体重
	private Integer weight;
	// 生日
	private Date birthday;
	// 运动习惯
	private String habit;
	// 身份证
	private String idCard;
	// 信誉等级
	private Integer memberCreditLevel;
	// 会员级别id
	private String memberLevelId;
	// 会员当前积分值
	private Integer memberPointsValue;
	// 注册时间
	private Date registerTime;
	// 注册时间 时间范围起（查询用）
	private Date registerTimeStart;
	// 注册时间 时间范围止（查询用）
	private Date registerTimeEnd;
	// 是否黑名单（0、非黑名单，1、黑名单，默认0）
	private Integer isBlacklist;
	// 移入黑名单备注
	private String blacklistMemo;
	// 邀请码
	private String invitationCode;
	// 推荐人Id
	private String refereeId;
	// TOKEN
	private String token;
	// token生成时间
	private Date tokenGenerateTime;
	// token生成时间 时间范围起（查询用）
	private Date tokenGenerateTimeStart;
	// token生成时间 时间范围止（查询用）
	private Date tokenGenerateTimeEnd;
	// 应缴押金
	private Double payableDeposit;
	// 剩余押金
	private Double surplusDeposit;
	// 硬件平台会员ID
	private String mid;
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
	// app推送客户端标识
	private String clientId;
	// 易健星设备Id
	private String serialNumber;
	// 易健星token
	private String yjxToken;

	// 生日
	private String by;
	// 注册用户类型 0：会员 1：教练
	private Integer usersType;

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

	public String getMemberPhotoUrl() {
		return memberPhotoUrl;
	}

	public void setMemberPhotoUrl(String memberPhotoUrl) {
		this.memberPhotoUrl = memberPhotoUrl;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberNick() {
		return memberNick;
	}

	public void setMemberNick(String memberNick) {
		this.memberNick = memberNick;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Integer getMemberCreditLevel() {
		return memberCreditLevel;
	}

	public void setMemberCreditLevel(Integer memberCreditLevel) {
		this.memberCreditLevel = memberCreditLevel;
	}

	public String getMemberLevelId() {
		return memberLevelId;
	}

	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}

	public Integer getMemberPointsValue() {
		return memberPointsValue;
	}

	public void setMemberPointsValue(Integer memberPointsValue) {
		this.memberPointsValue = memberPointsValue;
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

	public Integer getIsBlacklist() {
		return isBlacklist;
	}

	public void setIsBlacklist(Integer isBlacklist) {
		this.isBlacklist = isBlacklist;
	}

	public String getBlacklistMemo() {
		return blacklistMemo;
	}

	public void setBlacklistMemo(String blacklistMemo) {
		this.blacklistMemo = blacklistMemo;
	}

	public String getInvitationCode() {
		return invitationCode;
	}

	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}

	public String getRefereeId() {
		return refereeId;
	}

	public void setRefereeId(String refereeId) {
		this.refereeId = refereeId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenGenerateTime() {
		return tokenGenerateTime;
	}

	public void setTokenGenerateTime(Date tokenGenerateTime) {
		this.tokenGenerateTime = tokenGenerateTime;
	}

	public Date getTokenGenerateTimeStart() {
		return tokenGenerateTimeStart;
	}

	public void setTokenGenerateTimeStart(Date tokenGenerateTimeStart) {
		this.tokenGenerateTimeStart = tokenGenerateTimeStart;
	}

	public Date getTokenGenerateTimeEnd() {
		return tokenGenerateTimeEnd;
	}

	public void setTokenGenerateTimeEnd(Date tokenGenerateTimeEnd) {
		this.tokenGenerateTimeEnd = tokenGenerateTimeEnd;
	}

	public Double getPayableDeposit() {
		return payableDeposit;
	}

	public void setPayableDeposit(Double payableDeposit) {
		this.payableDeposit = payableDeposit;
	}

	public Double getSurplusDeposit() {
		return surplusDeposit;
	}

	public void setSurplusDeposit(Double surplusDeposit) {
		this.surplusDeposit = surplusDeposit;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
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

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "Member [" + "memberNo = " + memberNo + ", memberPhotoUrl = " + memberPhotoUrl + ", memberName = "
				+ memberName + ", memberNick = " + memberNick + ", mobilePhone = " + mobilePhone + ", password = "
				+ password + ", sex = " + sex + ", idCard = " + idCard + ", memberCreditLevel = " + memberCreditLevel
				+ ", memberLevelId = " + memberLevelId + ", memberPointsValue = " + memberPointsValue
				+ ", registerTime = " + registerTime + ", registerTimeStart = " + registerTimeStart
				+ ", registerTimeEnd = " + registerTimeEnd + ", isBlacklist = " + isBlacklist + ", blacklistMemo = "
				+ blacklistMemo + ", invitationCode = " + invitationCode + ", refereeId = " + refereeId + ", token = "
				+ token + ", tokenGenerateTime = " + tokenGenerateTime + ", tokenGenerateTimeStart = "
				+ tokenGenerateTimeStart + ", tokenGenerateTimeEnd = " + tokenGenerateTimeEnd + ", payableDeposit = "
				+ payableDeposit + ", surplusDeposit = " + surplusDeposit + ", mid = " + mid + ", createTime = "
				+ createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
				+ ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = "
				+ updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId + ", clientId = "
				+ clientId + "]";
	}

	public String getHabit() {
		return habit;
	}

	public void setHabit(String habit) {
		this.habit = habit;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public Integer getUsersType() {
		return usersType;
	}

	public void setUsersType(Integer usersType) {
		this.usersType = usersType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getYjxToken() {
		return yjxToken;
	}

	public void setYjxToken(String yjxToken) {
		this.yjxToken = yjxToken;
	}

}
