package cn.com.shopec.core.themepavilion.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 课程标签表 数据实体类
 */
public class CourseLabel extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//课程标签编号
	private String courseLabelNo;
	//标签名称
	private String labelName;
	//删除状态（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
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
		return courseLabelNo;
	}
	
	public String getCourseLabelNo(){
		return courseLabelNo;
	}
	
	public void setCourseLabelNo(String courseLabelNo){
		this.courseLabelNo = courseLabelNo;
	}
	
	public String getLabelName(){
		return labelName;
	}
	
	public void setLabelName(String labelName){
		this.labelName = labelName;
	}
	
	public Integer getIsDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
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
		return "CourseLabel ["
		 + "courseLabelNo = " + courseLabelNo + ", labelName = " + labelName + ", isDeleted = " + isDeleted + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		 + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		+"]";
	}
}
