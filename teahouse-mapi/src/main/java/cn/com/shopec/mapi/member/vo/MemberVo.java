package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;

/**
 * Member 数据实体类
 */
public class MemberVo implements Serializable {

	/**
	 *
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -4867863665810166774L;
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
	// 会员级别id
	private String memberLevelId;
	// 押金状态
	private Integer depositStatus;
	// 会员级别名称
	private String levelName;
	// 下一等级所需积分
	private int nextLevelPoint;
	// 会员下个级别名称
	private String nexLevelName;
	// token
	private String token;
	// 剩余押金
	private Double depositAmount;
	// 会员当前积分
	private Integer memberPoint;
	// 身份证号
	private String idCard;
	// 我的余额
	private Double memberBalance;
	// 硬件平台会员ID
	private String mid;

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

	public String getMemberLevelId() {
		return memberLevelId;
	}

	public void setMemberLevelId(String memberLevelId) {
		this.memberLevelId = memberLevelId;
	}

	public Integer getDepositStatus() {
		return depositStatus;
	}

	public void setDepositStatus(Integer depositStatus) {
		this.depositStatus = depositStatus;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public int getNextLevelPoint() {
		return nextLevelPoint;
	}

	public void setNextLevelPoint(int nextLevelPoint) {
		this.nextLevelPoint = nextLevelPoint;
	}

	public String getNexLevelName() {
		return nexLevelName;
	}

	public void setNexLevelName(String nexLevelName) {
		this.nexLevelName = nexLevelName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public Integer getMemberPoint() {
		return memberPoint;
	}

	public void setMemberPoint(Integer memberPoint) {
		this.memberPoint = memberPoint;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public Double getMemberBalance() {
		return memberBalance;
	}

	public void setMemberBalance(Double memberBalance) {
		this.memberBalance = memberBalance;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

}
