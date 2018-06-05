package cn.com.shopec.core.finace.common;

public class BalanceTransferConstant {

	/**
	 * 系统参数:会员每日申请提现的最大次数
	 */
	public static final String SYS_TRANSFER_APPLICATION_MAXIMUM = "TRANSFER_APPLICATION_MAXIMUM";

	/**
	 * 系统参数：会员单次申请提现的金额最大值
	 */
	public static final String SYS_TRANSFER_AMOUNT_MAXIMUM = "TRANSFER_AMOUNT_MAXIMUM";

	// 支付宝转账备注
	public static final String ALIPAY_REMARK = "余额退款";
	// 支付宝转账账户,商家名称
	public static String ALIPAY_BUSINESS_NAME = "易湃健身";

	// 客户端操作响应---
	public static final String MEMBER_IS_NOT_EXISTENCE = "会员信息不存在";
	public static final String ACCOUNT_IS_NOT_EXISTENCE = "请输入转入的账户信息";
	public static final String MEMBER_IS_ILLEGAL = "您的账号暂时无法进行提现，请联系客服";
	public static final String MEMBER_NO_TRANSFER = "提现状态未在申请中";
	public static final String AMOUNT_NO_EXISTENCE = "退款金额不能为空";
	public static final String AMOUNT_IS_ILLEGAL = "退款金额必须大于0";
	public static final String AMOUNT_IS_NOT_SUFFICIENT = "当前余额不足,无法申请提现";
	public static final String AMOUNT_IS_NOT_FREE = "余额已被冻结，无法申请提现";
	public static final String MEMBER_HAVE_TRANSFER_APPLING = "您已经提交了提现申请，请勿重复提交";
	public static final String APPLICATION_TIMES_GREATER_MAXIMUM = "今日的申请提现次数已达最大限制";
	public static final String AMOUNT_GREATER_MAXIMUM = "单次提现金额不能大于%s元";

	// 管理端操作响应---
	public static final String MEMBER_IS_BLACKLIST = "会员处于黑名单中";
	public static final String MEMBER_AMOUNT_IS_NOT_SUFFICIENT = "会员余额不足";
	public static final String RECORD_IS_NOT_EXISTENCE = "记录不存在";
	public static final String TRANSFER_METHOD_ERROR = "转账方式错误";
	public static final String INVOKE_THIRD_PARTY_ERROR = "第三方接口调用错误";
	public static final String THIRD_PARTY_RESPONSE_ERROR = "第三方接口返回错误";
}
