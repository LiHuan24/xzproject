package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 主题馆详情数据
 * @author LiHuan Date 
 * 2017年11月10日 下午4:35:20
 */
public class ThemeStoreDetail implements Serializable {
	private static final long serialVersionUID = -4867863665810166774L;

	private String themeId;// 主题馆ID
	private String storesName;// 主题馆名称
	private String themeAdress;// 主题馆地址
	private String longitude;// 经度
	private String latitude;// 纬度
	private String openTime;// 开发时段
	private String synopsis;// 主题馆简介
	private List<String> storePic;

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public String getStoresName() {
		return storesName;
	}

	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}

	public String getThemeAdress() {
		return themeAdress;
	}

	public void setThemeAdress(String themeAdress) {
		this.themeAdress = themeAdress;
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

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public List<String> getStorePic() {
		return storePic;
	}

	public void setStorePic(List<String> storePic) {
		this.storePic = storePic;
	}
}