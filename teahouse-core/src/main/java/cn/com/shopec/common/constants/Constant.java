package cn.com.shopec.common.constants;

/**
 * 开发框架基础架构的公共常量设置类（行知网络使用此基础框架的项目均可使用此常量类）
 * 
 * @author xieyong
 *
 */
public class Constant {

	/** 整个应用0表示失败 */
	public static final String FAIL = "0";
	/** 整个应用1表示成功 */
	public static final String SECCUESS = "1";
	/** 未登录 */
	public static final String NO_LOGIN = "-1";
	/** 未授权 */
	public static final String NO_AUTHORITY = "-2";
	/** 无结果 */
	public static final String NO_RESULT = "3";

	/** 整个应用0表示否 */
	public static final int NO = 0;
	/** 整个应用1表示是 */
	public static final int YES = 1;

	/** 待审核 */
	public static final int CENSORED_STATE_NO = 0;
	/** 审核通过 */
	public static final int CENSORED_STATE_PASS = 1;
	/** 审核不通过 */
	public static final int CENSORED_STATE_NOPASS = 2;

	/** 禁用 */
	public static final int DISABLED = 0;
	/** 启用 */
	public static final int ENABLED = 1;

	/** 不删除 */
	public static final int DEL_STATE_NO = 0;
	/** 已删除 */
	public static final int DEL_STATE_YES = 1;

	/** 默认的分页每页记录数 */
	public static final int DEFAULT_PAGE_SIZE = 10;

	/** 错误信息，参数无效或不合要求 */
	public static final String ERR_MSG_INVALID_ARG = "Invalid argument.";

	/** 错误信息，捕获到异常 */
	public static final String ERR_MSG_EXCEPTION_CATCHED = "Exception catched.";

	/** 错误信息，数据不存在 */
	public static final String ERR_MSG_DATA_NOT_EXISTS = "Data not exists.";

	/** 数据查询参数设置 */
	public static final int START_WITH_ZERO = 0;
	public static final int NUM_FOUR = 4;
	public static final int NUM_TEN = 10;

	/** 上传图片路径设置 */
	/*
	 * member_icon （会员头像） member_doc （会员证件） park_photo （场站照片） car_photo （车辆照片）
	 * car_doc （车辆证件） tax_photo(纳税证) feedback_photo（反馈图片） advert_photo(活动图片)
	 * equipmentModel_photo(设备型号图片) store_photo(门店图片) equipmentRepair_photo
	 * course_photo coach_photo
	 */
	public static final String COURSE_PHOTO = "course_photo";

	public static final String COACH_PHOTO = "coach_photo";

	public static final String MEMBER_ICON = "member_icon";

	public static final String MEMBER_DOC = "member_doc";

	public static final String PARK_PHOTO = "park_photo";

	public static final String CAR_PHOTO = "car_photo";

	public static final String EQUIPMENT_PHOTO = "equipmentModel_photo";

	public static final String EQUIPMENTREPAIR_PHOTO = "equipmentRepair_photo";

	public static final String STORE_PHOTO = "store_photo";

	public static final String CAR_DOC = "car_doc";

	public static final String TAX_PHOTO = "tax_photo";

	public static final String FEEDBACK_PHOTO = "feedback_photo";

	public static final String ADVERT_PHOTO = "advert_photo";

	public static final String COUPONPLAN_PHOTO = "couponPlan_photo";

	public static final String DEVICE_PHOTO = "device_photo";

	public static final String DEVICE_MP4 = "device_mp4";

	/** 暂无记录 */
	public static final String NO_RECORD = "暂无记录";
	/** 请求成功 */
	public static final String YES_RECORD = "请求成功";
	/** 系统错误 */
	public static final String fail_msg = "系统错误";

	/** 驾驶证有效期距离到期时间天数，发送消息给会员 */
	public static final String driver_license_over_date = "DRIVER_LICENSE_OVER_DATE";

	/** 支付宝转账签名内容 */
	public static final String TRANS_SIGN_CONTENT = "LCGX_TO_BE_ONE";

	/** 系统参数配置的失效时间 */
	public static final String SYS_FAILURE_TIME = "SYS_FAILURE_TIME";

	/** 客户端加密参数KEY */
	public static final String ANDROID_KEY = "TJFS_ANDROID_2017_001";
	public static final String IOS_KEY = "TJFS_IOS_2017_002";

	/** 门店启用、停用常量状态 **/
	public static final int STORE_START_STATUS = 0;// 启用
	public static final int STORE_BLOCK_STATUS = 1;// 停用
	
	/** 用户注册类型 **/
	public static final int MEMBER_TYPE = 0;//会员
	public static final int COACH_TYPE = 1;//教练

}
