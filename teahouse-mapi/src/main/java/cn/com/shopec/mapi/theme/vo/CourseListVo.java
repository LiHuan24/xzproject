package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 主题店课程封装
 * 
 * @author LiHuan Date 2017年11月11日 下午5:47:09
 */
public class CourseListVo implements Serializable {

	private static final long serialVersionUID = -4867863665810166774L;

	private String storeName;// 主题馆名称
	private String areaName;// 区名称
	private List<StoreCourseVo> listStoreCourseVo;// 数据封装

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public List<StoreCourseVo> getListStoreCourseVo() {
		return listStoreCourseVo;
	}

	public void setListStoreCourseVo(List<StoreCourseVo> listStoreCourseVo) {
		this.listStoreCourseVo = listStoreCourseVo;
	}
}
