package cn.com.shopec.core.member.vo;

/**
 * 会员信息表 数据实体类
 */
public class MrVo {

	/* Auto generated properties start */

	// 会员编号
	private String memberNo;
	// 头像照片url
	private String memberPhotoUrl;
	// 姓名
	private String memberName;
	// 昵称
	private String memberNick;
	// 级别名称
	private String levelName;
	// 手机号
	private String mobilePhone;
	// 性别（0、女，1、男）
	private String sex;
	// 生日
	private String birthday;
	// 身高
	private String height;
	// 体重
	private String weight;
	// 运动习惯
	private String habit;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHabit() {
		return habit;
	}

	public void setHabit(String habit) {
		this.habit = habit;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
