package cn.com.shopec.core.marketing.model;

import cn.com.shopec.core.common.Entity;

/** 
 * 优惠卷方案,兑换码关系表 数据实体类
 */
public class RedeemCouponPlan extends Entity<String> {
	
	private static final long serialVersionUID = 1l;
	
	/*Auto generated properties start*/
	
	//兑换码
	private String redeemCode;
	//优惠卷方案编号
	private String planNo;
	//优惠卷方案名称
	private String planTitle;
	//兑换的优惠卷数量
	private Integer redeemQutity;
	
	/*Auto generated properties end*/
	
	
	
	/*Customized properties start*/
	
	/*Customized properties end*/
	
	
	
	/*Auto generated methods start*/
	
	@Override
	public String getPK(){
		return planNo;
	}
	
	public String getPlanNo(){
		return planNo;
	}
	
	public void setPlanNo(String planNo){
		this.planNo = planNo;
	}
	
	public String getRedeemCode(){
		return redeemCode;
	}
	
	public void setRedeemCode(String redeemCode){
		this.redeemCode = redeemCode;
	}
	
	public Integer getRedeemQutity(){
		return redeemQutity;
	}
	
	public void setRedeemQutity(Integer redeemQutity){
		this.redeemQutity = redeemQutity;
	}

	public String getPlanTitle() {
		return planTitle;
	}

	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}
	
	/*Auto generated methods end*/
	
	
	
	/*Customized methods start*/
	
	/*Customized methods end*/
	
	

	@Override
	public String toString() {
		return "RedeemCouponPlan [redeemCode=" + redeemCode + ", planNo=" + planNo + ", planTitle=" + planTitle
				+ ", redeemQutity=" + redeemQutity + "]";
	}
}
