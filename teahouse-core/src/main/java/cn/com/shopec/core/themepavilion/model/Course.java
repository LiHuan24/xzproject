package cn.com.shopec.core.themepavilion.model;

import java.util.Date;

import cn.com.shopec.core.common.Entity;

/**
 * 课程表 数据实体类
 */
public class Course extends Entity<String> {

	private static final long serialVersionUID = 1l;

	/* Auto generated properties start */

	// 课程编号
	private String courseNo;
	// 城市ID
	private String cityId;
	// 城市名称
	private String cityName;
	// 启用状态：0、不可用，1、可用（默认0）
	private Integer isEnable;
	// 课程分类编号
	private String courseSortNo;
	// 课程分类名称
	private String sortName;
	// 课程标签编号
	private String courseLabelNo;
	// 课程标签名称
	private String labelName;
	// 课程中文名称
	private String chineseName;
	// 课程英文名称
	private String englishName;
	// 课程价格
	private Double price;
	// 课程难度：1、1星，2、2星，3、3星，4、4星，5、5星
	private Integer chineseDifficulty;
	// 课程简介
	private String synopsis;
	// 训练效果
	private String effect;
	// 适合人群
	private String suit;
	// 注意事项
	private String beCareful;
	// 启用、停用备注
	private String memo;
	// 课程图片1
	private String coursePictureUrl1;
	// 课程图片2
	private String coursePictureUrl2;
	// 课程图片3
	private String coursePictureUrl3;
	// 删除状态（0、未删除，1、已删除，默认0）
	private Integer isDeleted;
	// 创建时间
	private Date createTime;
	// 创建时间 时间范围起（查询用）
	private Date createTimeStart;
	// 创建时间 时间范围止（查询用）
	private Date createTimeEnd;
	// 更新时间
	private Date updateTime;
	// 更新时间 时间范围起（查询用）
	private Date updateTimeStart;
	// 更新时间 时间范围止（查询用）
	private Date updateTimeEnd;
	// 操作人类型
	private Integer operatorType;
	// 操作人id
	private String operatorId;

	/* Auto generated properties end */

	/* Customized properties start */

	/* Customized properties end */

	/* Auto generated methods start */

	@Override
	public String getPK() {
		return courseNo;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getCourseSortNo() {
		return courseSortNo;
	}

	public void setCourseSortNo(String courseSortNo) {
		this.courseSortNo = courseSortNo;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getCourseLabelNo() {
		return courseLabelNo;
	}

	public void setCourseLabelNo(String courseLabelNo) {
		this.courseLabelNo = courseLabelNo;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getChineseDifficulty() {
		return chineseDifficulty;
	}

	public void setChineseDifficulty(Integer chineseDifficulty) {
		this.chineseDifficulty = chineseDifficulty;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public String getBeCareful() {
		return beCareful;
	}

	public void setBeCareful(String beCareful) {
		this.beCareful = beCareful;
	}

	public String getCoursePictureUrl1() {
		return coursePictureUrl1;
	}

	public void setCoursePictureUrl1(String coursePictureUrl1) {
		this.coursePictureUrl1 = coursePictureUrl1;
	}

	public String getCoursePictureUrl2() {
		return coursePictureUrl2;
	}

	public void setCoursePictureUrl2(String coursePictureUrl2) {
		this.coursePictureUrl2 = coursePictureUrl2;
	}

	public String getCoursePictureUrl3() {
		return coursePictureUrl3;
	}

	public void setCoursePictureUrl3(String coursePictureUrl3) {
		this.coursePictureUrl3 = coursePictureUrl3;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTimeStart() {
		return updateTimeStart;
	}

	public void setUpdateTimeStart(Date updateTimeStart) {
		this.updateTimeStart = updateTimeStart;
	}

	public Date getUpdateTimeEnd() {
		return updateTimeEnd;
	}

	public void setUpdateTimeEnd(Date updateTimeEnd) {
		this.updateTimeEnd = updateTimeEnd;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/* Auto generated methods end */

	/* Customized methods start */

	/* Customized methods end */

	@Override
	public String toString() {
		return "Course [" + "courseNo = " + courseNo + ", cityId = " + cityId + ", cityName = " + cityName
				+ ", isEnable = " + isEnable + ", courseSortNo = " + courseSortNo + ", sortName = " + sortName
				+ ", courseLabelNo = " + courseLabelNo + ", labelName = " + labelName + ", chineseName = " + chineseName
				+ ", englishName = " + englishName + ", price = " + price + ", chineseDifficulty = " + chineseDifficulty
				+ ", synopsis = " + synopsis + ", effect = " + effect + ", suit = " + suit + ", beCareful = "
				+ beCareful + ", coursePictureUrl1 = " + coursePictureUrl1 + ", coursePictureUrl2 = "
				+ coursePictureUrl2 + ", coursePictureUrl3 = " + coursePictureUrl3 + ", isDeleted = " + isDeleted
				+ ", createTime = " + createTime + ", createTimeStart = " + createTimeStart + ", createTimeEnd = "
				+ createTimeEnd + ", updateTime = " + updateTime + ", updateTimeStart = " + updateTimeStart
				+ ", updateTimeEnd = " + updateTimeEnd + ", operatorType = " + operatorType + ", operatorId = "
				+ operatorId + ", memo = "+ memo + "]";
	}
}
