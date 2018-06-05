package cn.com.shopec.core.marketing.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/** 
 * 兑换码记录表 数据实体类
 */
public class RedeemRecord extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//id
	private String id;
	//兑换码
	private String redeemCode;
	//会员编号（即兑换人编号）
	private String memberNo;
	//创建时间
	private Date createTime;
	//创建时间 时间范围起（查询用）
	private Date createTimeStart;
	//创建时间 时间范围止（查询用）
	private Date createTimeEnd;	
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return id;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getRedeemCode(){
		return redeemCode;
	}
	
	public void setRedeemCode(String redeemCode){
		this.redeemCode = redeemCode;
	}
	
	public String getMemberNo(){
		return memberNo;
	}
	
	public void setMemberNo(String memberNo){
		this.memberNo = memberNo;
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
	
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	
	@Override
	public String toString() {
		return "RedeemRecord ["
		 + "id = " + id + ", redeemCode = " + redeemCode + ", memberNo = " + memberNo + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = " + createTimeEnd
		+"]";
	}
}
