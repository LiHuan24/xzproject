package cn.com.shopec.common.hardware;

/**
 * 返回结果全局数据
 * 
 */
public class HwData {
	private String mid;
	private String desc;
	private String code;
	private InfoData info;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public InfoData getInfo() {
		return info;
	}

	public void setInfo(InfoData info) {
		this.info = info;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
}
