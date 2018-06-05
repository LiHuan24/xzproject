package cn.com.shopec.core.marketing.vo;

import java.util.List;

public class AtVo {
	// 广告
	private List<AdvertVo> as;
	// 健身
	private List<AdvertVo> ds;
	// 咨询
	private List<AdvertVo> zs;

	public List<AdvertVo> getAs() {
		return as;
	}

	public void setAs(List<AdvertVo> as) {
		this.as = as;
	}

	public List<AdvertVo> getDs() {
		return ds;
	}

	public void setDs(List<AdvertVo> ds) {
		this.ds = ds;
	}

	public List<AdvertVo> getZs() {
		return zs;
	}

	public void setZs(List<AdvertVo> zs) {
		this.zs = zs;
	}

}
