package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;

/**
 * Member 数据实体类
 */
public class MemberInfoVo implements Serializable {

	/**
	 *
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -4867863665810166774L;
	// 会员编号
	private String memberNo;
	// 手机号
	private String mobilePhone;
	// token
	private String token;
	// 硬件平台会员ID
	private String mid;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

}
