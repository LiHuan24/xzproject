package cn.com.shopec.core.themepavilion.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 课程排队表 数据实体类
 */
public class LineUp extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//排队编号
	private String lineUpNo;
	//预约编号
	private String bespeakNo;
	//会员编号
	private String memberNo;
	//会员名称
	private String memberName;
	//排队状态：0、排队中，1，已预约
	private Integer lineUpStatus;
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
		return lineUpNo;
	}
	
	public String getLineUpNo(){
		return lineUpNo;
	}
	
	public void setLineUpNo(String lineUpNo){
		this.lineUpNo = lineUpNo;
	}
	
	public String getBespeakNo(){
		return bespeakNo;
	}
	
	public void setBespeakNo(String bespeakNo){
		this.bespeakNo = bespeakNo;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
	}
	
	public String getMemberName(){
		return memberName;
	}
	
	public void setMemberName(String memberName){
		this.memberName = memberName;
	}
	
	public Integer getLineUpStatus(){
		return lineUpStatus;
	}
	
	public void setLineUpStatus(Integer lineUpStatus){
		this.lineUpStatus = lineUpStatus;
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
		return "LineUp ["
		 + "lineUpNo = " + lineUpNo + ", bespeakNo = " + bespeakNo + ", memberNo = " + memberNo + ", memberName = " + memberName
		 + ", lineUpStatus = " + lineUpStatus + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
		 + ", operatorId = " + operatorId
		+"]";
	}
}
