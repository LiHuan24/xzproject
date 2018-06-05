package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;
import java.util.List;
/**
 * 主题馆首页数据展示类
 * @author LiHuan
 * Date 2017年11月9日 下午2:34:29
 */

public class ThemeHomeVo implements Serializable{
	private static final long serialVersionUID = -4867863665810166774L;

	private List<ThemeVo> listThemeVo;
	
	private List<CourseOrderVo> listCourseOrderVo;

	public List<ThemeVo> getListThemeVo() {
		return listThemeVo;
	}

	public void setListThemeVo(List<ThemeVo> listThemeVo) {
		this.listThemeVo = listThemeVo;
	}

	public List<CourseOrderVo> getListCourseOrderVo() {
		return listCourseOrderVo;
	}

	public void setListCourseOrderVo(List<CourseOrderVo> listCourseOrderVo) {
		this.listCourseOrderVo = listCourseOrderVo;
	}
	
}