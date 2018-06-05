package cn.com.shopec.core.storemanagement.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 门店表 数据实体类
 */
public class Store extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 门店编号
	private String storeNo;
	// 门店名称
	private String storeName;
	// 门店状态：0、启用，1、停用
	private Integer storeStatus;
	// 门店类型：0、社区馆，1、主题馆
	private Integer storeType;
	// 门店经度
	private String longitude;
	// 门店纬度
	private String latitude;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 一级行政区（省）id
	private String addrRegion1Id;
	// 一级行政区（省）名称
	private String addrRegion1Name;
	// 二级行政区（市/直辖市区县）id
	private String addrRegion2Id;
	// 二级行政区（市/直辖市区县）名称
	private String addrRegion2Name;
	// 三级行政区（区县）id
	private String addrRegion3Id;
	// 三级行政区（区县）名称
	private String addrRegion3Name;
	// 街道
	private String addrStreet;
	// 门店图片1
	private String storePictureUrl1;
	// 门店图片2
	private String storePictureUrl2;
	// 门店图片3
	private String storePictureUrl3;
	// 门禁二维码
	private String storeQrCode;
	// 门店简介
	private String synopsis;
	// 备注
	private String memo;
	// 门店启用、停用理由
	private String startBlockReason;
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
	/** ====新加字段======= **/
	// 门店开门时间
	private Date storeOpenDate;
	// 门店关门时间
	private Date storeColseDate;
	// 门禁编码
	private String entranceCode;
	// 门店状态 0 下线 1 上线 默认 0
	private Integer entranceStatus;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return storeNo;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getStoreStatus() {
		return storeStatus;
	}

	public void setStoreStatus(Integer storeStatus) {
		this.storeStatus = storeStatus;
	}

	public Integer getStoreType() {
		return storeType;
	}

	public void setStoreType(Integer storeType) {
		this.storeType = storeType;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAddrRegion1Id() {
		return addrRegion1Id;
	}

	public void setAddrRegion1Id(String addrRegion1Id) {
		this.addrRegion1Id = addrRegion1Id;
	}

	public String getAddrRegion1Name() {
		return addrRegion1Name;
	}

	public void setAddrRegion1Name(String addrRegion1Name) {
		this.addrRegion1Name = addrRegion1Name;
	}

	public String getAddrRegion2Id() {
		return addrRegion2Id;
	}

	public void setAddrRegion2Id(String addrRegion2Id) {
		this.addrRegion2Id = addrRegion2Id;
	}

	public String getAddrRegion2Name() {
		return addrRegion2Name;
	}

	public void setAddrRegion2Name(String addrRegion2Name) {
		this.addrRegion2Name = addrRegion2Name;
	}

	public String getAddrRegion3Id() {
		return addrRegion3Id;
	}

	public void setAddrRegion3Id(String addrRegion3Id) {
		this.addrRegion3Id = addrRegion3Id;
	}

	public String getAddrRegion3Name() {
		return addrRegion3Name;
	}

	public void setAddrRegion3Name(String addrRegion3Name) {
		this.addrRegion3Name = addrRegion3Name;
	}

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
	}

	public String getStorePictureUrl1() {
		return storePictureUrl1;
	}

	public void setStorePictureUrl1(String storePictureUrl1) {
		this.storePictureUrl1 = storePictureUrl1;
	}

	public String getStorePictureUrl2() {
		return storePictureUrl2;
	}

	public void setStorePictureUrl2(String storePictureUrl2) {
		this.storePictureUrl2 = storePictureUrl2;
	}

	public String getStorePictureUrl3() {
		return storePictureUrl3;
	}

	public void setStorePictureUrl3(String storePictureUrl3) {
		this.storePictureUrl3 = storePictureUrl3;
	}

	public String getStoreQrCode() {
		return storeQrCode;
	}

	public void setStoreQrCode(String storeQrCode) {
		this.storeQrCode = storeQrCode;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getStartBlockReason() {
		return startBlockReason;
	}

	public void setStartBlockReason(String startBlockReason) {
		this.startBlockReason = startBlockReason;
	}

	public Date getStoreOpenDate() {
		return storeOpenDate;
	}

	public void setStoreOpenDate(Date storeOpenDate) {
		this.storeOpenDate = storeOpenDate;
	}

	public Date getStoreColseDate() {
		return storeColseDate;
	}

	public void setStoreColseDate(Date storeColseDate) {
		this.storeColseDate = storeColseDate;
	}

	public String getEntranceCode() {
		return entranceCode;
	}

	public void setEntranceCode(String entranceCode) {
		this.entranceCode = entranceCode;
	}

	public Integer getEntranceStatus() {
		return entranceStatus;
	}

	public void setEntranceStatus(Integer entranceStatus) {
		this.entranceStatus = entranceStatus;
	}

	@Override
	public String toString() {
		return "Store [" + "storeNo = " + storeNo + ", storeName = " + storeName + ", storeStatus = " + storeStatus
				+ ", storeType = " + storeType + ", longitude = " + longitude + ", latitude = " + latitude
				+ ", addrRegion1Id = " + addrRegion1Id + ", addrRegion1Name = " + addrRegion1Name + ", addrRegion2Id = "
				+ addrRegion2Id + ", addrRegion2Name = " + addrRegion2Name + ", addrRegion3Id = " + addrRegion3Id
				+ ", addrRegion3Name = " + addrRegion3Name + ", addrStreet = " + addrStreet + ", storePictureUrl1 = "
				+ storePictureUrl1 + ", storePictureUrl2 = " + storePictureUrl2 + ", storePictureUrl3 = "
				+ storePictureUrl3 + ", storeQrCode = " + storeQrCode + ", synopsis = " + synopsis + ", isDeleted = "
				+ isDeleted + ", createTime = " + createTime + ", createTimeStart = " + createTimeStart
				+ ", createTimeEnd = " + createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = "
				+ updateTimeStart + ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType
				+ ", operatorId = " + operatorId + ", cityId = " + cityId + ", cityName = " + cityName + ", memo = "
				+ memo + "" + ", startBlockReason = " + startBlockReason + ",storeOpenDate = " + storeOpenDate
				+ ",storeColseDate = " + storeColseDate + ",entranceCode = " + entranceCode + ",entranceStatus = "
				+ entranceStatus + "]";
	}
}