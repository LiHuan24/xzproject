package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;

/**
 * 教练课程详情数据
 * @author LiHuan
 * Date 2017年12月20日 上午11:43:53
 */
public class CoachCourseDetailVo implements Serializable {

	private static final long serialVersionUID = -4867863665810166774L;
	
	private String coachNo;//教练编号
	private String arrayCourseNo;//排课编号
	private String coursePhotoUrl;//课程图片
	private String storeName;//门店名称
	private String storeAddress;//门店地址
	private String courseSynopsis;//课程介绍
	private String coachQrCodeUrl;//教练二维码地址
	private Integer courseStatus;//上课状态

	
	public String getCoachNo() {
		return coachNo;
	}
	public void setCoachNo(String coachNo) {
		this.coachNo = coachNo;
	}
	public String getArrayCourseNo() {
		return arrayCourseNo;
	}
	public void setArrayCourseNo(String arrayCourseNo) {
		this.arrayCourseNo = arrayCourseNo;
	}
	public String getCoursePhotoUrl() {
		return coursePhotoUrl;
	}
	public void setCoursePhotoUrl(String coursePhotoUrl) {
		this.coursePhotoUrl = coursePhotoUrl;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public String getCourseSynopsis() {
		return courseSynopsis;
	}
	public void setCourseSynopsis(String courseSynopsis) {
		this.courseSynopsis = courseSynopsis;
	}
	public Integer getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(Integer courseStatus) {
		this.courseStatus = courseStatus;
	}
	public String getCoachQrCodeUrl() {
		return coachQrCodeUrl;
	}
	public void setCoachQrCodeUrl(String coachQrCodeUrl) {
		this.coachQrCodeUrl = coachQrCodeUrl;
	}

}