package cn.com.shopec.common.hardware;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import net.sf.json.JSONObject;

public class HardwareUtils {

	public static ResultInfo<HwData> post(JSONObject jsonParam, String url) throws ParseException, IOException {
		ResultInfo<HwData> resultInfo = new ResultInfo<HwData>();

		try {
			// 数据签名
			String sg = createSign(HardwareConfig.appSecret, HardwareConfig.appID);
			// 新建一个客户对象
			CloseableHttpClient client = HttpClients.createDefault();
			// 新建一个HttpPost请求的对象 --并将uri传给这个对象
			HttpPost post = new HttpPost(HardwareConfig.appUrl + url);
			post.setHeader("X-JK-Id", HardwareConfig.appID);
			post.setHeader("X-JK-Span", ECDateUtils.date2UnixTimestamp(new Date()) + "");
			post.setHeader("X-JK-Key", sg);
			post.setHeader("Content-Type", "application/json");

			post.setEntity(new StringEntity(jsonParam.toString(), Charset.forName("UTF-8")));
			// 新建一个响应对象来接收客户端执行post的结果
			CloseableHttpResponse response = client.execute(post);
			// 从响应对象中提取需要的结果-->实际结果,与预期结果进行对比
			// System.out.println(EntityUtils.toString(response.getEntity(),
			// "utf-8"));
			// System.out.println(EntityUtils.toString(response.getEntity()));
			Gson gson = new Gson();
			HwData h = gson.fromJson(EntityUtils.toString(response.getEntity(), "utf-8"), HwData.class);
			if (h == null || h.getCode() == null || h.getCode().equals("")) {
				resultInfo.setCode(Constant.FAIL);
			} else {
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg(Constant.YES_RECORD);
				resultInfo.setData(h);
			}
		} catch (JsonSyntaxException e) {
			resultInfo.setCode(Constant.FAIL);
			e.printStackTrace();
		}

		return resultInfo;
	}

	/***
	 * 生成签名*
	 * 
	 * @param appSecret
	 * @param appId
	 * @return 返回签名的base64编码
	 */
	public static String createSign(String appSecret, String appId) {
		/////////////
		try {
			List<String> strings = new ArrayList<>();
			strings.add(appSecret);
			strings.add(appId);
			long timespan = System.currentTimeMillis() / 1000;
			strings.add(String.valueOf(timespan));
			String resutl = strings.stream().sorted().collect(Collectors.joining(""));
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] resut = md.digest(resutl.getBytes("utf-8"));
			return Base64.getEncoder().encodeToString(resut);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
