package cn.com.shopec.core.member.vo;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 会员信息表 数据实体类
 */
public class MemberBalanceVo extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 会员编号
	private String memberNo;
	// 姓名
	private String memberName;
	// 昵称
	private String memberNick;
	// 手机号
	private String mobilePhone;
	// 性别（0、女，1、男）
	private Integer sex;
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
	// 账户余额
	private Double memberBalance;
	// 剩余 押金
	private Double memberDeposit;
	// 用户类型 会员  0 教练 1
	private Integer usersType;
	
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
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
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
	
	public Integer getUsersType() {
		return usersType;
	}
	public void setUsersType(Integer usersType) {
		this.usersType = usersType;
	}
	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberName=" + memberName + ", memberNick=" + memberNick
				+ ", mobilePhone=" + mobilePhone + ", sex=" + sex + ", memberCreditLevel=" + memberCreditLevel
				+ ", memberLevelId=" + memberLevelId + ", memberPointsValue=" + memberPointsValue + ", registerTime="
				+ registerTime + ", registerTimeStart=" + registerTimeStart + ", registerTimeEnd=" + registerTimeEnd
				+ ", isBlacklist=" + isBlacklist + ", memberBalance=" + memberBalance + ", memberDeposit="
				+ memberDeposit + "]";
	}
}
