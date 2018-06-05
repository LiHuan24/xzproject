package cn.com.shopec.mapi.theme.vo;

import java.util.List;

public class ThemeAndOrderVo {
	// 订单编号
	private String communityOrderNo;
	// 门店信息
	private List<ThemeVo> vs;

	public String getCommunityOrderNo() {
		return communityOrderNo;
	}

	public void setCommunityOrderNo(String communityOrderNo) {
		this.communityOrderNo = communityOrderNo;
	}

	public List<ThemeVo> getVs() {
		return vs;
	}

	public void setVs(List<ThemeVo> vs) {
		this.vs = vs;
	}

}
