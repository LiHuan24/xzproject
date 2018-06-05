package cn.com.shopec.common.hardware;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import cn.com.shopec.common.zmxy.ZhimaConfig;

public class HardwareConfig {
	public static final String appID = getString("appID");
	public static final String appSecret = getString("appSecret");
	public static final String appUrl = getString("appUrl");

	public static String getString(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream((getAppPath() + "hardware.properties")));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取应用路径
	 * 
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String getAppPath() throws UnsupportedEncodingException {
		String configPath = ZhimaConfig.class.getClassLoader().getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}
}
