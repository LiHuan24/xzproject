package cn.com.shopec.core.marketing.vo;

public class AdvertVo {
	// 文章类型文章类型（1、健身文章，2、资讯文章，3、广告文章）
	private String advertType;
	// 文章名称
	private String advertName;
	// 文章简介
	private String synopsis;
	// 文章图片url
	private String advertPicUrl;
	// 跳转链接url
	private String linkUrl;
	// 更新时间
	private String updateTime;

	public String getAdvertName() {
		return advertName;
	}

	public void setAdvertName(String advertName) {
		this.advertName = advertName;
	}

	public String getAdvertPicUrl() {
		return advertPicUrl;
	}

	public void setAdvertPicUrl(String advertPicUrl) {
		this.advertPicUrl = advertPicUrl;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getAdvertType() {
		return advertType;
	}

	public void setAdvertType(String advertType) {
		this.advertType = advertType;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

}
