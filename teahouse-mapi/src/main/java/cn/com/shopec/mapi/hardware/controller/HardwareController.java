package cn.com.shopec.mapi.hardware.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.shopec.common.hardware.HardwareUtils;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app/hardware")
public class HardwareController {
	@RequestMapping("test")
	public String test() throws org.apache.http.ParseException, IOException {

		JSONObject jsonParam = new JSONObject();
		// jsonParam.put("mid", "d011e53778ad4f0c8fb2c51dbfd5cf3b");
		jsonParam.put("devId", "893746089864335360");
		HardwareUtils.post(jsonParam, "/Api/services/sport/devStatus");
		return null;
	}

}
