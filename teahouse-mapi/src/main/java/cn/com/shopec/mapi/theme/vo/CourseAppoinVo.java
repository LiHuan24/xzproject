package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;

/**
 * 主题馆排课课程预约封装数据
 * @author LiHuan
 * Date 2017年11月15日 下午4:55:47
 */
public class CourseAppoinVo implements Serializable{
	private static final long serialVersionUID = -4867863665810166774L;
	
	private String arrayCourseNo;//预约课程编号
	private String courseName;//预约课程名称
	private String storeName;//门店名称
	private String storeAdress;//门店地址
	private String courseTime;//课程上课时间
	private String coursePrice;//课程价格---页面根据人数计算得出课程总价
	private String peopleNumber;//课程上课总人数
	private String surplusNumber;//剩余人数
	private String lineUpNumber;//排队人数
	private String surplusLineNum;//排队剩余人数
	private String courseStatus;//课程状态 已结束/排队已满 ：0 正常预约，排队未满可预约：1
	private String courseDescribe;//课程描述
	public String getArrayCourseNo() {
		return arrayCourseNo;
	}
	public void setArrayCourseNo(String arrayCourseNo) {
		this.arrayCourseNo = arrayCourseNo;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreAdress() {
		return storeAdress;
	}
	public void setStoreAdress(String storeAdress) {
		this.storeAdress = storeAdress;
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
	public String getPeopleNumber() {
		return peopleNumber;
	}
	public void setPeopleNumber(String peopleNumber) {
		this.peopleNumber = peopleNumber;
	}
	public String getSurplusNumber() {
		return surplusNumber;
	}
	public void setSurplusNumber(String surplusNumber) {
		this.surplusNumber = surplusNumber;
	}
	public String getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}
	public String getCourseDescribe() {
		return courseDescribe;
	}
	public void setCourseDescribe(String courseDescribe) {
		this.courseDescribe = courseDescribe;
	}
	public String getLineUpNumber() {
		return lineUpNumber;
	}
	public void setLineUpNumber(String lineUpNumber) {
		this.lineUpNumber = lineUpNumber;
	}
	public String getSurplusLineNum() {
		return surplusLineNum;
	}
	public void setSurplusLineNum(String surplusLineNum) {
		this.surplusLineNum = surplusLineNum;
	}
	
	
}