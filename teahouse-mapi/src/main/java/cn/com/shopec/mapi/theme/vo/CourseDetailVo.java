package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 课程详情
 * 
 * @author LiHuan 
 * Date 2017年11月14日 下午5:00:50
 */
public class CourseDetailVo implements Serializable {

	private static final long serialVersionUID = -4867863665810166774L;

	private String coachName;// 教练名称
	private String coachSynopsis;// 教练简介
	private String memberPhotoUrl;// 教练头像
	private String storeName;// 门店名称
	private String longitude;// 门店经度
	private String latitude;// 门店纬度
	private String addrStreet;// 门店地址
	private List<String> coursePhotoUrl;// 课程图片
	private String courseTime;// 课程开课时间
	private String coursePrice;// 课程单价
	private String courseSynopsis;// 课程简介
	private String arrayCourseNo;//主题馆 排课编号
	private String courseStatus;//课程状态
	private String effect;//训练效果
	private String suit;//适合人群
	private String beCareful;//注意事项

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
	}

	public String getCoachSynopsis() {
		return coachSynopsis;
	}

	public void setCoachSynopsis(String coachSynopsis) {
		this.coachSynopsis = coachSynopsis;
	}

	public String getMemberPhotoUrl() {
		return memberPhotoUrl;
	}

	public void setMemberPhotoUrl(String memberPhotoUrl) {
		this.memberPhotoUrl = memberPhotoUrl;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}

	public List<String> getCoursePhotoUrl() {
		return coursePhotoUrl;
	}

	public void setCoursePhotoUrl(List<String> coursePhotoUrl) {
		this.coursePhotoUrl = coursePhotoUrl;
	}

	public String getCourseTime() {
		return courseTime;
	}

	public void setCourseTime(String courseTime) {
		this.courseTime = courseTime;
	}

	public String getCoursePrice() {
		return coursePrice;
	}

	public void setCoursePrice(String coursePrice) {
		this.coursePrice = coursePrice;
	}

	public String getCourseSynopsis() {
		return courseSynopsis;
	}

	public void setCourseSynopsis(String courseSynopsis) {
		this.courseSynopsis = courseSynopsis;
	}

	public String getArrayCourseNo() {
		return arrayCourseNo;
	}

	public void setArrayCourseNo(String arrayCourseNo) {
		this.arrayCourseNo = arrayCourseNo;
	}

	public String getCourseStatus() {
		return courseStatus;
	}

	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public String getBeCareful() {
		return beCareful;
	}

	public void setBeCareful(String beCareful) {
		this.beCareful = beCareful;
	}
	
}