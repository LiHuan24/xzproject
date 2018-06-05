package cn.com.shopec.core.marketing.model;

import java.util.Date;
import java.util.List;

import cn.com.shopec.core.common.Entity;

/** 
 * 兑换码表 数据实体类
 */
public class RedeemCode extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//兑换码
	private String redeemCode;
	//兑换码名称
	private String redeemName;
	//可重复使用次数
	private Integer availableTimes;
	//有效起始日期
	private Date availableTime1;
	//有效起始日期 时间范围起（查询用）
	private Date availableTime1Start;
	//有效起始日期 时间范围止（查询用）
	private Date availableTime1End;	
	//有效结束日期
	private Date availableTime2;
	//有效结束日期 时间范围起（查询用）
	private Date availableTime2Start;
	//有效结束日期 时间范围止（查询用）
	private Date availableTime2End;	
	//备注
	private String memo;
	//审核状态(0.未审核 1.通过 2.拒绝)
	private Integer censorStatus;
	//审核人
	private String censorId;
	//审核时间
	private Date censorTime;
	//审核时间 时间范围起（查询用）
	private Date censorTimeStart;
	//审核时间 时间范围止（查询用）
	private Date censorTimeEnd;	
	//审核备注
	private String censorMemo;
	//启用状态(0.停用 1.启用)
	private Integer isAvailable;
	//启用时间
	private Date availableUpdateTime;
	//启用时间 时间范围起（查询用）
	private Date availableUpdateTimeStart;
	//启用时间 时间范围止（查询用）
	private Date availableUpdateTimeEnd;	
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	//修改时间
	private Date updateTime;
	//修改时间 时间范围起（查询用）
	private Date updateTimeStart;
	//修改时间 时间范围止（查询用）
	private Date updateTimeEnd;	
	//操作人id
	private Integer operatorType;
	//操作类型
	private String operatorId;
	//删除状态(0.未删 1.已删)
	private Integer isDeleted;
	//兑换码与优惠券关系列表
	private List<RedeemCouponPlan> redeemCouponPlans;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return redeemCode;
	}
	
	public String getRedeemCode(){
		return redeemCode;
	}
	
	public void setRedeemCode(String redeemCode){
		this.redeemCode = redeemCode;
	}
	
	public String getRedeemName(){
		return redeemName;
	}
	
	public void setRedeemName(String redeemName){
		this.redeemName = redeemName;
	}
	
	public Integer getAvailableTimes(){
		return availableTimes;
	}
	
	public void setAvailableTimes(Integer availableTimes){
		this.availableTimes = availableTimes;
	}
	
	public Date getAvailableTime1(){
		return availableTime1;
	}
	
	public void setAvailableTime1(Date availableTime1){
		this.availableTime1 = availableTime1;
	}
	
	public Date getAvailableTime1Start(){
		return availableTime1Start;
	}
	
	public void setAvailableTime1Start(Date availableTime1Start){
		this.availableTime1Start = availableTime1Start;
	}
	
	public Date getAvailableTime1End(){
		return availableTime1End;
	}
	
	public void setAvailableTime1End(Date availableTime1End){
		this.availableTime1End = availableTime1End;
	}	
	
	public Date getAvailableTime2(){
		return availableTime2;
	}
	
	public void setAvailableTime2(Date availableTime2){
		this.availableTime2 = availableTime2;
	}
	
	public Date getAvailableTime2Start(){
		return availableTime2Start;
	}
	
	public void setAvailableTime2Start(Date availableTime2Start){
		this.availableTime2Start = availableTime2Start;
	}
	
	public Date getAvailableTime2End(){
		return availableTime2End;
	}
	
	public void setAvailableTime2End(Date availableTime2End){
		this.availableTime2End = availableTime2End;
	}	
	
	public String getMemo(){
		return memo;
	}
	
	public void setMemo(String memo){
		this.memo = memo;
	}
	
	public Integer getCensorStatus(){
		return censorStatus;
	}
	
	public void setCensorStatus(Integer censorStatus){
		this.censorStatus = censorStatus;
	}
	
	public String getCensorId(){
		return censorId;
	}
	
	public void setCensorId(String censorId){
		this.censorId = censorId;
	}
	
	public Date getCensorTime(){
		return censorTime;
	}
	
	public void setCensorTime(Date censorTime){
		this.censorTime = censorTime;
	}
	
	public Date getCensorTimeStart(){
		return censorTimeStart;
	}
	
	public void setCensorTimeStart(Date censorTimeStart){
		this.censorTimeStart = censorTimeStart;
	}
	
	public Date getCensorTimeEnd(){
		return censorTimeEnd;
	}
	
	public void setCensorTimeEnd(Date censorTimeEnd){
		this.censorTimeEnd = censorTimeEnd;
	}	
	
	public String getCensorMemo(){
		return censorMemo;
	}
	
	public void setCensorMemo(String censorMemo){
		this.censorMemo = censorMemo;
	}
	
	public Integer getIsAvailable(){
		return isAvailable;
	}
	
	public void setIsAvailable(Integer isAvailable){
		this.isAvailable = isAvailable;
	}
	
	public Date getAvailableUpdateTime(){
		return availableUpdateTime;
	}
	
	public void setAvailableUpdateTime(Date availableUpdateTime){
		this.availableUpdateTime = availableUpdateTime;
	}
	
	public Date getAvailableUpdateTimeStart(){
		return availableUpdateTimeStart;
	}
	
	public void setAvailableUpdateTimeStart(Date availableUpdateTimeStart){
		this.availableUpdateTimeStart = availableUpdateTimeStart;
	}
	
	public Date getAvailableUpdateTimeEnd(){
		return availableUpdateTimeEnd;
	}
	
	public void setAvailableUpdateTimeEnd(Date availableUpdateTimeEnd){
		this.availableUpdateTimeEnd = availableUpdateTimeEnd;
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
	
	public Integer getIsDeleted(){
		return isDeleted;
	}
	
	public void setIsDeleted(Integer isDeleted){
		this.isDeleted = isDeleted;
	}
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	public List<RedeemCouponPlan> getRedeemCouponPlans() {
		return redeemCouponPlans;
	}

	public void setRedeemCouponPlans(List<RedeemCouponPlan> redeemCouponPlans) {
		this.redeemCouponPlans = redeemCouponPlans;
	}

	@Override
	public String toString() {
		return "RedeemCode ["
		 + "redeemCode = " + redeemCode + ", redeemName = " + redeemName + ", availableTimes = " + availableTimes + ", availableTime1 = " + availableTime1 + ", availableTime1Start = " + availableTime1Start + ", availableTime1End = " + availableTime1End
		 + ", availableTime2 = " + availableTime2 + ", availableTime2Start = " + availableTime2Start + ", availableTime2End = " + availableTime2End + ", memo = " + memo + ", censorStatus = " + censorStatus + ", censorId = " + censorId
		 + ", censorTime = " + censorTime + ", censorTimeStart = " + censorTimeStart + ", censorTimeEnd = " + censorTimeEnd + ", censorMemo = " + censorMemo + ", isAvailable = " + isAvailable + ", availableUpdateTime = " + availableUpdateTime + ", availableUpdateTimeStart = " + availableUpdateTimeStart + ", availableUpdateTimeEnd = " + availableUpdateTimeEnd
		 + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = " + operatorId
		 + ", isDeleted = " + isDeleted
		+"]";
	}
}
