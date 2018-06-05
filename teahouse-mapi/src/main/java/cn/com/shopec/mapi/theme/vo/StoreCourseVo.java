package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;

/**
 * 门店-主题馆课程
 * @author LiHuan
 * Date 2017年11月11日 下午3:37:12
 */
public class StoreCourseVo implements Serializable{
	private static final long serialVersionUID = -4867863665810166774L;
	
	private String arrayCourseNo;//主题馆排课编号
	private String storeNo;//门店编号
	private String courseNo;//课程编号
	private String courseName;//课程名称
	private String courseLabel;//课程标签
	private String courseType;//课程分类;
	private String courseTime;//上课时间段
	private String coursePrice;//课程单价
	private String courseStatus;//课程状态 0 结束 1 预定 2满员
	private String coursePictureUrl1;//课程图片
	
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
	
	public String getCourseNo() {
		return courseNo;
	}
	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseLabel() {
		return courseLabel;
	}
	public void setCourseLabel(String courseLabel) {
		this.courseLabel = courseLabel;
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
	public String getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}
	public String getCoursePictureUrl1() {
		return coursePictureUrl1;
	}
	public void setCoursePictureUrl1(String coursePictureUrl1) {
		this.coursePictureUrl1 = coursePictureUrl1;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
}