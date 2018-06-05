package cn.com.shopec.common.utils.qrcode;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 下载二维码
 * @author LiHuan
 * Date 2017年10月25日 下午4:33:59
 */
public class DownloadQrcode {

	/**
	 * 执行下载二维码
	 * @param HttpServletRequest request req请求
	 * @param HttpServletResponse response res请求
	 * @param map 封装参数
	 * @throws Exception 
	 */
	public static void downloadQrcode(HttpServletRequest request,HttpServletResponse response,Map<String,String> map) throws Exception{
		String url = map.get("url");
		String resImgPath = map.get("resImgPath");
		url = resImgPath + "/" + url;
		String downloadFilename = url.substring(url.lastIndexOf("/")+1);
		downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");// 转换中文否则可能会产生乱码
		response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
		response.setCharacterEncoding("UTF-8");
		// 设置Content-Disposition
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1){//火狐
			response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + downloadFilename);  // 设置在下载框默认显示的文件名
		} else {              
			response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename); // 设置在下载框默认显示的文件名
		}  
		File ff = new File(url);
		
		InputStream in = new FileInputStream(ff);
		
		OutputStream out = response.getOutputStream();
		//写文件
		int b;
		while ((b = in.read()) != -1) {
			out.write(b);
		}
		in.close();
		out.close();
	}
}
