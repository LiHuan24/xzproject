package cn.com.shopec.mapi.home.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Advert;
import cn.com.shopec.core.marketing.service.AdvertService;
import cn.com.shopec.core.marketing.vo.AdvertVo;
import cn.com.shopec.core.marketing.vo.AtVo;

@Controller
@RequestMapping("/app/home")
public class HomeController {
	@Resource
	private AdvertService advertService;
	@Value("${image_path}")
	private String imgPath;
	@Value("${file_path}")
	private String filePath;

	/**
	 * 进入首页
	 * 
	 * @return
	 */
	@RequestMapping("toHome")
	@ResponseBody
	public ResultInfo<AtVo> toHome(@RequestParam("page") Integer page, Advert advert) {

		ResultInfo<AtVo> resultInfo = new ResultInfo<AtVo>();

		try {
			AtVo v = new AtVo();
			// 健身文章
			Query q = new Query(page, 10, advert);
			PageFinder<AdvertVo> as = advertService.getAdvertVo(q);
			List<AdvertVo> atList = as.getData();
			if (atList.size() > 0) {
				for (AdvertVo d : atList) {
					d.setAdvertPicUrl(imgPath + "/" + d.getAdvertPicUrl());
					d.setLinkUrl(filePath + "/" + d.getLinkUrl());

				}
				v.setDs(as.getData());
			}
			// 广告文章
			List<AdvertVo> ls = advertService.getAtVo();
			if (ls.size() > 0) {

				for (AdvertVo advertVo : ls) {
					advertVo.setAdvertPicUrl(imgPath + "/" + advertVo.getAdvertPicUrl());
					advertVo.setLinkUrl(filePath + "/" + advertVo.getLinkUrl());
				}

				v.setAs(ls);
			}
			// 咨询文章
			List<AdvertVo> zs = advertService.getAtzVo();
			if (zs.size() > 0) {

				for (AdvertVo advertVo : zs) {
					advertVo.setAdvertPicUrl(imgPath + "/" + advertVo.getAdvertPicUrl());
					advertVo.setLinkUrl(filePath + "/" + advertVo.getLinkUrl());
				}

				v.setZs(zs);
			}
			resultInfo.setData(v);
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg(Constant.YES_RECORD);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;

	}

	/**
	 * 首页查找文章
	 * 
	 * @return
	 */
	@RequestMapping("getAdvert")
	@ResponseBody
	public ResultInfo<List<AdvertVo>> getAdvert(@RequestParam("page") Integer page, Advert advert) {

		ResultInfo<List<AdvertVo>> resultInfo = new ResultInfo<List<AdvertVo>>();

		try {
			Query q = new Query(page, 10, advert);
			PageFinder<AdvertVo> as = advertService.getAdvertVo(q);
			List<AdvertVo> atList = as.getData();
			if (atList.size() > 0) {
				for (AdvertVo d : atList) {
					d.setAdvertPicUrl(imgPath + "/" + d.getAdvertPicUrl());
					d.setLinkUrl(filePath + "/" + d.getLinkUrl());

				}
				resultInfo.setData(atList);
			}

			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setMsg(Constant.YES_RECORD);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;

	}

}
