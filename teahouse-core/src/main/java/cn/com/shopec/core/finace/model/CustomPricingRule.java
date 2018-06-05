package cn.com.shopec.core.finace.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 自定义计费规则表 数据实体类
 */
public class CustomPricingRule extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 自定义计费规则编号
	private String customNo;
	// 计费规则编号
	private String ruleNo;
	// 计费规则名称
	private String ruleName;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 计费金额（元/小时）
	private Double hourPrice;
	// 自定义日期
	private Date customDate;
	// 自定义日期 时间范围起（查询用）
	private Date customDateStart;
	// 自定义日期 时间范围止（查询用）
	private Date customDateEnd;
	// 删除状态（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return customNo;
	}

	public String getCustomNo() {
		return customNo;
	}

	public void setCustomNo(String customNo) {
		this.customNo = customNo;
	}

	public String getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(String ruleNo) {
		this.ruleNo = ruleNo;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Double getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(Double hourPrice) {
		this.hourPrice = hourPrice;
	}

	public Date getCustomDate() {
		return customDate;
	}

	public void setCustomDate(Date customDate) {
		this.customDate = customDate;
	}

	public Date getCustomDateStart() {
		return customDateStart;
	}

	public void setCustomDateStart(Date customDateStart) {
		this.customDateStart = customDateStart;
	}

	public Date getCustomDateEnd() {
		return customDateEnd;
	}

	public void setCustomDateEnd(Date customDateEnd) {
		this.customDateEnd = customDateEnd;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "CustomPricingRule [" + "customNo = " + customNo + ", ruleNo = " + ruleNo + ", ruleName = " + ruleName
				+ ", cityId = " + cityId + ", cityName = " + cityName + ", hourPrice = " + hourPrice + ", customDate = "
				+ customDate + ", customDateStart = " + customDateStart + ", customDateEnd = " + customDateEnd
				+ ", isDeleted = " + isDeleted + ", createTime = " + createTime + ", createTimeStart = "
				+ createTimeStart + ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime
				+ ", updateTimeStart = " + updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = "
				+ operatorType + ", operatorId = " + operatorId + "]";
	}
}
