package cn.com.shopec.mapi.theme.vo;

import java.io.Serializable;
/**
 * 课程标签实体封装
 * @author LiHuan
 * Date 2017年12月5日 上午9:35:02
 */
public class CourseLabelVo implements Serializable {

	private static final long serialVersionUID = -4867863665810166774L;
	
	private String labelNo;//标签编号
	private String labelName;//标签名称
	public String getLabelNo() {
		return labelNo;
	}
	public void setLabelNo(String labelNo) {
		this.labelNo = labelNo;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
