package cn.com.shopec.mapi.tjx.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.http.ParseException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.tjx.EtInfo;
import cn.com.shopec.common.tjx.TjxData;
import cn.com.shopec.common.tjx.TjxPostUtils;
import cn.com.shopec.common.tjx.TjxUtils;
import cn.com.shopec.common.tjx.UserIf;
import cn.com.shopec.common.tjx.UserInfo;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * 第三方泰肌星
 */
@Controller
@RequestMapping("/app/tjx")
public class TjxController extends BaseController {
	@Resource
	private MemberService memberService;
	@Resource
	private SysParamService sysParamService;

	@Value("${image_path}")
	private String imgPath;

	private static Logger log = Logger.getLogger(TjxController.class);

	/**
	 * 进入手环页面
	 */
	@RequestMapping("/toBracelet")
	@ResponseBody
	public ResultInfo<String> toBracelet(String memberNo) {

		ResultInfo<String> resultInfo = new ResultInfo<String>();

		try {
			Member m = memberService.getMember(memberNo);

			if (m.getSerialNumber() == null) {
				resultInfo.setCode("10");
				resultInfo.setMsg(Constant.YES_RECORD);
			} else {
				resultInfo.setCode("11");
				resultInfo.setMsg(Constant.YES_RECORD);
			}
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;

	}

	/**
	 * 会员上课
	 */
	@RequestMapping("/startClass")
	@ResponseBody
	public ResultInfo<TjxData> startClass(String memberNo, String serialNumber) {
		ResultInfo<TjxData> resultInfo = new ResultInfo<TjxData>();
		// 查找会员信息
		Member m = memberService.getMember(memberNo);
		try {
			if (m == null) {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("会员不存在。");
				return resultInfo;
			}

			UserInfo f = new UserInfo();
			f.setUserId(m.getMemberNo());
			f.setUserName(m.getMemberName());
			if (m.getMemberPhotoUrl() != null && !m.getMemberPhotoUrl().equals("")) {
				f.setHeadPic(m.getMemberPhotoUrl());
			}

			if (m.getSex() != null) {
				if (m.getSex() == 0) {
					f.setSex("F");
				} else if (m.getSex() == 1) {
					f.setSex("M");
				}

			}

			if (m.getBirthday() != null) {
				f.setBirthDate(ECDateUtils.formatDate(m.getBirthday(), "yyyy-MM-dd"));
			}
			if (m.getHeight() != null) {
				f.setCurrentHeight(m.getHeight() + "");
			}
			if (m.getWeight() != null) {
				f.setCurrentWeight(m.getWeight() + "");
			}

			EtInfo e = new EtInfo();
			e.setUserInfo(f);
			e.setSerialNumber(serialNumber);
			SysParam sysParam = sysParamService.getByParamKey("TJX_TOKEN");
			JSONObject j = (JSONObject) JSONSerializer.toJSON(e);
			resultInfo = TjxPostUtils.post(j, TjxUtils.startClass, sysParam.getParamValue());
			if (resultInfo.getCode() != null && resultInfo.getCode().equals(Constant.SECCUESS)) {
				m.setSerialNumber(serialNumber);
				memberService.updateMember(m, null);
			}

		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;

	}

	/**
	 * 结束上课
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping("/closeClass")
	@ResponseBody
	public ResultInfo<TjxData> closeClass(String memberNo) throws ParseException, IOException {

		ResultInfo<TjxData> resultInfo = new ResultInfo<TjxData>();

		try {
			SysParam sysParam = sysParamService.getByParamKey("TJX_TOKEN");

			Member m = memberService.getMember(memberNo);
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("serialNumber", m.getSerialNumber());

			resultInfo = TjxPostUtils.post(jsonParam, TjxUtils.closeClass, sysParam.getParamValue());

			if (resultInfo.getCode() != null && resultInfo.getCode().equals(Constant.SECCUESS)) {
				m.setSerialNumber(null);
				memberService.updateMember(m, null);
			}
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;

	}

	/**
	 * 会员登录
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * 
	 */
	@RequestMapping("/studentLogin")
	@ResponseBody
	public ResultInfo<TjxData> studentLogin(String memberNo) throws ParseException, IOException {
		ResultInfo<TjxData> resultInfo = new ResultInfo<TjxData>();
		// 查找会员信息
		Member m = memberService.getMember(memberNo);

		if (m == null) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("会员不存在。");
			return resultInfo;
		}

		UserInfo f = new UserInfo();
		f.setUserId(m.getMemberNo());
		f.setUserName(m.getMemberName());
		if (m.getMemberPhotoUrl() != null && !m.getMemberPhotoUrl().equals("")) {
			f.setHeadPic(imgPath + "/" + m.getMemberPhotoUrl());
		}

		if (m.getSex() != null) {
			if (m.getSex() == 0) {
				f.setSex("F");
			} else if (m.getSex() == 1) {
				f.setSex("M");
			}

		}

		if (m.getBirthday() != null) {
			f.setBirthDate(ECDateUtils.formatDate(m.getBirthday(), "yyyy-MM-dd"));
		}
		if (m.getHeight() != null) {
			f.setCurrentHeight(m.getHeight() + "");
		}
		if (m.getWeight() != null) {
			f.setCurrentWeight(m.getWeight() + "");
		}

		UserIf u = new UserIf();
		u.setUserInfo(f);

		SysParam sysParam = sysParamService.getByParamKey("TJX_TOKEN");

		JSONObject j = (JSONObject) JSONSerializer.toJSON(u);

		resultInfo = TjxPostUtils.post(j, TjxUtils.studentLogin, sysParam.getParamValue());
		if (resultInfo.getCode().equals(Constant.SECCUESS)) {
			if (resultInfo.getData().getContent().getToken() != null
					&& !resultInfo.getData().getContent().getToken().equals("")) {
				m.setYjxToken(resultInfo.getData().getContent().getToken());
				memberService.updateMember(m, null);
			}

		}

		return resultInfo;

	}

	/*
	 * 查看报告
	 * 
	 */
	@RequestMapping("/toReport")
	@ResponseBody
	public ResultInfo<String> toReport(String memberNo) throws ParseException, IOException {
		ResultInfo<String> resultInfo = new ResultInfo<String>();

		// Member m = memberService.getMember(memberNo);
		// if (m.getYjxToken() != null && !m.getYjxToken().equals("")) {
		// resultInfo.setCode(Constant.SECCUESS);
		// resultInfo.setMsg(Constant.YES_RECORD);
		// resultInfo.setData(TjxUtils.translist + m.getYjxToken());
		//
		// } else {
		ResultInfo<TjxData> info = studentLogin(memberNo);

		if (info.getCode().equals(Constant.SECCUESS)) {
			if (info.getData().getContent().getToken() != null && !info.getData().getContent().getToken().equals("")) {

				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg(Constant.YES_RECORD);
				resultInfo.setData(TjxUtils.translist + info.getData().getContent().getToken());

			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.fail_msg);
			}

		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
		}

		// }

		return resultInfo;

	}

}