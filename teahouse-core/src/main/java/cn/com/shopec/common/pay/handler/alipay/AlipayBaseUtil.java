package cn.com.shopec.common.pay.handler.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

import cn.com.shopec.common.pay.aliPay.AlipayConfig;

/**
 * 支付宝基础处理类
 * @author admin
 *
 */
public class AlipayBaseUtil {
	
	public static final String ALIPAY_WAY = "https://openapi.alipay.com/gateway.do";//支付宝网关（正式）;
	public static final String ALIPAY_WAY_DEV = "https://openapi.alipaydev.com/gateway.do";//支付宝网关（沙箱测试）;
	public static final String SUCCESS_CODE = "10000";//统一调用成功码

	public static AlipayClient alipayClient = null;

	static {
		String appId = AlipayConfig.appId;
		String privateKey = AlipayConfig.rsa_private;
		String publicKey = AlipayConfig.rsa_public;
		alipayClient = new DefaultAlipayClient(ALIPAY_WAY, appId, privateKey, "json", "UTF-8", publicKey, "RSA");
	}
	
	private AlipayBaseUtil(){
	}
	
	/**
	 * 得到AlipayClient对象
	 * @return
	 */
	public static AlipayClient getAlipayClient() {
		return alipayClient;
	}
	
}

