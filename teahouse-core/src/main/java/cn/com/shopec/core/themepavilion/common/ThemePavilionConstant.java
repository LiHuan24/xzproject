package cn.com.shopec.core.themepavilion.common;

/**
 * 主题馆常量设置
 * 
 * @author LiHuan Date 2017年10月31日 下午5:30:30
 */
public class ThemePavilionConstant {

	/**
	 * 删除状态
	 */
	/** 已删除 **/
	public static final int DEL_FLAG_YES = 1;
	/** 未删除 **/
	public static final int DEL_FLAG_NO = 0;

	/** 教练审核/认证状态 **/
	// （0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
	public static final int AUDIT_NO = 0;
	public static final int AUDIT_YES = 1;
	public static final int AUDIT_TREAT = 2;
	public static final int AUDIT_NO_PASS = 3;

	/*** 课程启用、课程停用 */
	public static final int UP_COURSE = 1;
	public static final int DOWN_COURSE = 0;

	/** 排课类型 */
	// 0、本周，1、下周，2、已过期
	public static final int THIS_WEEK = 0;
	public static final int NEXT_WEEK = 1;
	public static final int EXPIRED = 2;

	/** * 主题馆常量 */
	public static final int THEME_STORE = 1;
	/** 社区馆常量 **/
	public static final int COMMUNITY_STORE = 0;
	
	/** 门店-主题馆 0 启用、1停用 **/
	public static final int THEME_ENABLE = 0;
	public static final int THEME_DISUSE = 1;
	
	/**
	 * 主题馆课程发布状态、
	 * 0 未发布 1 已发布
	 */
	public static final int POSTED_STATUS_YES = 1;
	public static final int POSTED_STATUS_NO = 0;
	
	/**
	 * app端课程状态
	 */
	public static final String COURSE_STATS_OVER = "0";//已结束
	public static final String COURSE_STATS_MAKE = "1";//可预约
	public static final String COURSE_STATS_LINE = "2";//排队中
	public static final String COURSE_STATS_FULL = "3";//满员
	public static final String COURSE_STOP_APPION = "4";//停止预约
	
	/**
	 * 课程分类
	 */
	public static final String COURSE_ANAEROBIC = "0";//无氧
	public static final String COURSE_AEROBIC = "1";//有氧
	
	/**
	 * 课程状态标识
	 */
	public static final String APP_COURSE_MAKE = "1";//正常预约、排队预约
	public static final String APP_COURSE_FULL = "0";//已结束、排队满员

}
