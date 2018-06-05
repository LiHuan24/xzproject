package cn.com.shopec.common.tjx;

import java.io.IOException;
import java.nio.charset.Charset;

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
import net.sf.json.JSONObject;

public class TjxPostUtils {

	public static ResultInfo<TjxData> post(JSONObject jsonParam, String url, String token)
			throws ParseException, IOException {
		ResultInfo<TjxData> resultInfo = new ResultInfo<TjxData>();

		try {
			// 新建一个客户对象
			CloseableHttpClient client = HttpClients.createDefault();
			// 新建一个HttpPost请求的对象 --并将uri传给这个对象
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json");
			if (token != null && !token.equals("")) {
				post.setHeader("token", token);
			}

			post.setEntity(new StringEntity(jsonParam.toString(), Charset.forName("UTF-8")));
			// 新建一个响应对象来接收客户端执行post的结果
			CloseableHttpResponse response = client.execute(post);
			// 从响应对象中提取需要的结果-->实际结果,与预期结果进行对比
			// System.out.println(EntityUtils.toString(response.getEntity(),
			// "utf-8"));
			// System.out.println(EntityUtils.toString(response.getEntity()));
			Gson gson = new Gson();
			TjxData d = gson.fromJson(EntityUtils.toString(response.getEntity(), "utf-8"), TjxData.class);
			if (d == null || d.getCode() == null || d.getCode().equals("")) {
				resultInfo.setCode(Constant.FAIL);
			} else {
				if (d.getCode().equals("200")) {
					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setMsg(Constant.YES_RECORD);
					resultInfo.setData(d);
				} else {
					resultInfo.setCode(d.getCode());
					resultInfo.setMsg(d.getMessage());
					resultInfo.setData(d);
				}

			}
		} catch (JsonSyntaxException e) {
			resultInfo.setCode(Constant.FAIL);
			e.printStackTrace();
		}

		return resultInfo;
	}

}
