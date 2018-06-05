package cn.com.shopec.mapi.common.vo;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.mapi.theme.vo.CourseLabelVo;

/**
 * APP缓存公用配置类
 * @author LiHuan
 * Date 2017年12月5日 下午2:05:08
 */
public class AppCacheConfigVo implements Serializable {

	private static final long serialVersionUID = -4867863665810166774L;
	
	private List<CourseLabelVo> listCourseLabelVo;//课程标签配置

	public List<CourseLabelVo> getListCourseLabelVo() {
		return listCourseLabelVo;
	}

	public void setListCourseLabelVo(List<CourseLabelVo> listCourseLabelVo) {
		this.listCourseLabelVo = listCourseLabelVo;
	}
}
