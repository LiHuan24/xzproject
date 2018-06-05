package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 转账记录表 数据实体类
 */
public class TransferRecord extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//转账编号
	private String transferNo;
	//会员编号
	private String memberNo;
	//会员名称
	private String memberName;
	//会员手机号
	private String mobilePhone;
	//申请时间
	private Date applyTime;
	//申请时间 时间范围起（查询用）
	private Date applyTimeStart;
	//申请时间 时间范围止（查询用）
	private Date applyTimeEnd;	
	//转账金额
	private Double transferAmount;
	//转入账户信息(会员的第三方账户)
	private String  transferAccount;
	//转账方法（1、支付宝，2、微信）
	private Integer transferMethod;
	//转账时间
	private Date transferTime;
	//转账时间 时间范围起（查询用）
	private Date transferTimeStart;
	//转账时间 时间范围止（查询用）
	private Date transferTimeEnd;	
	//转账状态（0、未转账(申请中),1、已转账,2、转账失败,3、用户取消,4、后台取消,默认0）
	private Integer transferStatus;
	//取消申请原因
	private String cancelReason;
	//第三方订单号or流水号
	private String flowNo;
	//转账错误码
	private String errorCode;
	//转账错误信息
	private String errorMsg;
	//审核人id
	private String cencorId;
	//审核状态（0、未审核，1、已审核，2、审核不通过，默认0）
	private Integer cencorStatus;
	//审核备注
	private String cencorMemo;
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
		return transferNo;
	}
	
	public String getTransferNo(){
		return transferNo;
	}
	
	public void setTransferNo(String transferNo){
		this.transferNo = transferNo;
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
	
	public String getMobilePhone(){
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	
	public Date getApplyTime(){
		return applyTime;
	}
	
	public void setApplyTime(Date applyTime){
		this.applyTime = applyTime;
	}
	
	public Date getApplyTimeStart(){
		return applyTimeStart;
	}
	
	public void setApplyTimeStart(Date applyTimeStart){
		this.applyTimeStart = applyTimeStart;
	}
	
	public Date getApplyTimeEnd(){
		return applyTimeEnd;
	}
	
	public void setApplyTimeEnd(Date applyTimeEnd){
		this.applyTimeEnd = applyTimeEnd;
	}	
	
	public Double getTransferAmount(){
		return transferAmount;
	}
	
	public void setTransferAmount(Double transferAmount){
		this.transferAmount = transferAmount;
	}
	
	public Integer getTransferMethod(){
		return transferMethod;
	}
	
	public void setTransferMethod(Integer transferMethod){
		this.transferMethod = transferMethod;
	}
	
	public Date getTransferTime(){
		return transferTime;
	}
	
	public void setTransferTime(Date transferTime){
		this.transferTime = transferTime;
	}
	
	public Date getTransferTimeStart(){
		return transferTimeStart;
	}
	
	public void setTransferTimeStart(Date transferTimeStart){
		this.transferTimeStart = transferTimeStart;
	}
	
	public Date getTransferTimeEnd(){
		return transferTimeEnd;
	}
	
	public void setTransferTimeEnd(Date transferTimeEnd){
		this.transferTimeEnd = transferTimeEnd;
	}	
	
	public Integer getTransferStatus(){
		return transferStatus;
	}
	
	public void setTransferStatus(Integer transferStatus){
		this.transferStatus = transferStatus;
	}
	
	public String getFlowNo(){
		return flowNo;
	}
	
	public void setFlowNo(String flowNo){
		this.flowNo = flowNo;
	}
	
	public String getErrorCode(){
		return errorCode;
	}
	
	public void setErrorCode(String errorCode){
		this.errorCode = errorCode;
	}
	
	public String getErrorMsg(){
		return errorMsg;
	}
	
	public void setErrorMsg(String errorMsg){
		this.errorMsg = errorMsg;
	}
	
	public String getCencorId(){
		return cencorId;
	}
	
	public void setCencorId(String cencorId){
		this.cencorId = cencorId;
	}
	
	public Integer getCencorStatus(){
		return cencorStatus;
	}
	
	public void setCencorStatus(Integer cencorStatus){
		this.cencorStatus = cencorStatus;
	}
	
	public String getCencorMemo(){
		return cencorMemo;
	}
	
	public void setCencorMemo(String cencorMemo){
		this.cencorMemo = cencorMemo;
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
	
	public String getTransferAccount() {
		return transferAccount;
	}

	public void setTransferAccount(String transferAccount) {
		this.transferAccount = transferAccount;
	}
	/*Auto generated methods end*/

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	/*Customized methods start*/
	
	/*Customized methods end*/

	@Override
	public String toString() {
		return "TransferRecord [transferNo=" + transferNo + ", memberNo=" + memberNo + ", memberName=" + memberName
				+ ", mobilePhone=" + mobilePhone + ", applyTime=" + applyTime + ", applyTimeStart=" + applyTimeStart
				+ ", applyTimeEnd=" + applyTimeEnd + ", transferAmount=" + transferAmount + ", transferAccount="
				+ transferAccount + ", transferMethod=" + transferMethod + ", transferTime=" + transferTime
				+ ", transferTimeStart=" + transferTimeStart + ", transferTimeEnd=" + transferTimeEnd
				+ ", transferStatus=" + transferStatus + ", cancelReason=" + cancelReason + ", flowNo=" + flowNo
				+ ", errorCode=" + errorCode + ", errorMsg=" + errorMsg + ", cencorId=" + cencorId + ", cencorStatus="
				+ cencorStatus + ", cencorMemo=" + cencorMemo + ", createTime=" + createTime + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd + ", updateTime=" + updateTime
				+ ", updateTimeStart=" + updateTimeStart + ", updateTimeEnd=" + updateTimeEnd + ", operatorType="
				+ operatorType + ", operatorId=" + operatorId + "]";
	}
	
}
