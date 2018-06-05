package cn.com.shopec.common.tjx;

public class UserInfo {
	// 会员ID
	private String userId;
	// 会员姓名
	private String userName;
	// 会员头像
	private String headPic;
	// 会员性别
	private String sex;
	// 会员生日
	private String birthDate;
	// 会员身高
	private String currentHeight;
	// 会员体重
	private String currentWeight;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getCurrentHeight() {
		return currentHeight;
	}

	public void setCurrentHeight(String currentHeight) {
		this.currentHeight = currentHeight;
	}

	public String getCurrentWeight() {
		return currentWeight;
	}

	public void setCurrentWeight(String currentWeight) {
		this.currentWeight = currentWeight;
	}

}
