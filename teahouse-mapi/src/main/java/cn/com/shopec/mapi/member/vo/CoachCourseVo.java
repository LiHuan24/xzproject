package cn.com.shopec.mapi.member.vo;

import java.io.Serializable;

/**
 * 教练的课程数据
 * @author LiHuan
 * Date 2017年12月20日 上午11:43:53
 */
public class CoachCourseVo implements Serializable {

	private static final long serialVersionUID = -4867863665810166774L;
	
	private String coachNo;//教练编号
	private String arrayCourseNo;//排课编号
	private String courseNo;//课程编号
	private String courseName;//课程名称
	private String classNumber;//上课人数
	private String signNumber;//签到人数
	private String storeNo;//门店编号
	private String storeName;//门店名称
	private String classTime;//上课时间
	private Integer courseStatus;//0、未上课 1、进行中 2、已结束
	
	public String getArrayCourseNo() {
		return arrayCourseNo;
	}
	public void setArrayCourseNo(String arrayCourseNo) {
		this.arrayCourseNo = arrayCourseNo;
	}
	public String getStoreNo() {
		return storeNo;
	}
	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}
	public String getCoachNo() {
		return coachNo;
	}
	public void setCoachNo(String coachNo) {
		this.coachNo = coachNo;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getClassNumber() {
		return classNumber;
	}
	public void setClassNumber(String classNumber) {
		this.classNumber = classNumber;
	}
	public String getSignNumber() {
		return signNumber;
	}
	public void setSignNumber(String signNumber) {
		this.signNumber = signNumber;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getClassTime() {
		return classTime;
	}
	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}
	public Integer getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(Integer courseStatus) {
		this.courseStatus = courseStatus;
	}
	public String getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}
	
}