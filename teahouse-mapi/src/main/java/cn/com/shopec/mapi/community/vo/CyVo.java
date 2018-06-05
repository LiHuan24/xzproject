package cn.com.shopec.mapi.community.vo;

import java.util.List;

public class CyVo {
	// 门店名称
	private String storeName;
	// 门店地址
	private String addrStreet;
	// 门店经度
	private String longitude;
	// 门店纬度
	private String latitude;
	// 门店营业时间
	private String openTime;
	// 门店图片
	private List<String> storePic;
	// 门店设备信息
	private List<FeVo> feList;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAddrStreet() {
		return addrStreet;
	}

	public void setAddrStreet(String addrStreet) {
		this.addrStreet = addrStreet;
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

	public List<FeVo> getFeList() {
		return feList;
	}

	public void setFeList(List<FeVo> feList) {
		this.feList = feList;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public List<String> getStorePic() {
		return storePic;
	}

	public void setStorePic(List<String> storePic) {
		this.storePic = storePic;
	}

}
