package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;

public class ThemeVo implements Serializable {
	private static final long serialVersionUID = -4867863665810166774L;

	/** 门店-主题馆or社区馆 **/
	private String themeId;// 门店ID
	private String storesName;// 门店名称
	private String themeAdress;// 门店地址
	private String themePhotourl;// 门店图片
	private Double distance;// 距离

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public String getThemeAdress() {
		return themeAdress;
	}

	public void setThemeAdress(String themeAdress) {
		this.themeAdress = themeAdress;
	}

	public String getThemePhotourl() {
		return themePhotourl;
	}

	public void setThemePhotourl(String themePhotourl) {
		this.themePhotourl = themePhotourl;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getStoresName() {
		return storesName;
	}

	public void setStoresName(String storesName) {
		this.storesName = storesName;
	}

}
