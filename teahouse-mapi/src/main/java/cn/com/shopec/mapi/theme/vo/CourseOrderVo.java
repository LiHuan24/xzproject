package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;

public class CourseOrderVo implements Serializable{
	private static final long serialVersionUID = -4867863665810166774L;
	
	/** 课程订单 **/
	private String courseId;//预约课程ID
	private String courseName;//预约课程名称
	private String storesName;//课程附属门店名称
	private String classTime;//上课时间
	private Integer courseStatus;//课程状态 0、已预约，1、进行中，2、已结束，3、已取消
	private String memberId;//会员ID
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getStoresName() {
		return storesName;
	}
	public void setStoresName(String storesName) {
		this.storesName = storesName;
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
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
	
}
