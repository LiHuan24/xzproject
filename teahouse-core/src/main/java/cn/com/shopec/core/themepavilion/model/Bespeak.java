package cn.com.shopec.core.themepavilion.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 课程预约表 数据实体类
 */
public class Bespeak extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//预约编号
	private String bespeakNo;
	//排课编号
	private String arrayCourseNo;
	//预约日期
	private Date bespeakDate;
	//预约日期 时间范围起（查询用）
	private Date bespeakDateStart;
	//预约日期 时间范围止（查询用）
	private Date bespeakDateEnd;	
	//课程人数
	private Integer peopleNumber;
	//已预约人数
	private Integer reservation;
	//排队人数
	private Integer lineUp;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//更新时间
	private Date updateTime;
	//更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	//更新时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人类型
	private Integer operatorType;
	//操作人id
	private String operatorId;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return bespeakNo;
	}
	
	public String getBespeakNo(){
		return bespeakNo;
	}
	
	public void setBespeakNo(String bespeakNo){
		this.bespeakNo = bespeakNo;
	}
	
	public String getArrayCourseNo(){
		return arrayCourseNo;
	}
	
	public void setArrayCourseNo(String arrayCourseNo){
		this.arrayCourseNo = arrayCourseNo;
	}
	
	public Date getBespeakDate(){
		return bespeakDate;
	}
	
	public void setBespeakDate(Date bespeakDate){
		this.bespeakDate = bespeakDate;
	}
	
	public Date getBespeakDateStart(){
		return bespeakDateStart;
	}
	
	public void setBespeakDateStart(Date bespeakDateStart){
		this.bespeakDateStart = bespeakDateStart;
	}
	
	public Date getBespeakDateEnd(){
		return bespeakDateEnd;
	}
	
	public void setBespeakDateEnd(Date bespeakDateEnd){
		this.bespeakDateEnd = bespeakDateEnd;
	}	
	
	public Integer getPeopleNumber(){
		return peopleNumber;
	}
	
	public void setPeopleNumber(Integer peopleNumber){
		this.peopleNumber = peopleNumber;
	}
	
	public Integer getReservation(){
		return reservation;
	}
	
	public void setReservation(Integer reservation){
		this.reservation = reservation;
	}
	
	public Integer getLineUp(){
		return lineUp;
	}
	
	public void setLineUp(Integer lineUp){
		this.lineUp = lineUp;
	}
	
	public Date getCreateTime(){
		return createTime;
	}
	
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	
	public void setCreateTimeStart(Date createTimeStart){
		this.createTimeStart = createTimeStart;
	}
	
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	
	public void setCreateTimeEnd(Date createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}	
	
	public Date getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
	
	public Date getUpdateTimeStart(){
		return updateTimeStart;
	}
	
	public void setUpdateTimeStart(Date updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}
	
	public Date getUpdateTimeEnd(){
		return updateTimeEnd;
	}
	
	public void setUpdateTimeEnd(Date updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}	
	
	public Integer getOperatorType(){
		return operatorType;
	}
	
	public void setOperatorType(Integer operatorType){
		this.operatorType = operatorType;
	}
	
	public String getOperatorId(){
		return operatorId;
	}
	
	public void setOperatorId(String operatorId){
		this.operatorId = operatorId;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "Bespeak ["
		 + "bespeakNo = " + bespeakNo + ", arrayCourseNo = " + arrayCourseNo + ", bespeakDate = " + bespeakDate + ", bespeakDateStart = " + bespeakDateStart + ", bespeakDateEnd = " + bespeakDateEnd + ", peopleNumber = " + peopleNumber
		 + ", reservation = " + reservation + ", lineUp = " + lineUp + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd
		 + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
