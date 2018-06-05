package cn.com.shopec.mgt.advert.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Advert;
import cn.com.shopec.core.marketing.service.AdvertService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 广告
 */
@Controller
@RequestMapping("advert")
public class AdvertController extends BaseController {
	@Resource
	private AdvertService advertService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private SysUserService sysUserService;

	@Value("${res_path}")
	private String resPath;

	/**
	 * 跳转列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAdvertList")
	public String toAdvertList() {
		return "advert/advert_list";
	}

	/**
	 * 列表分页查询
	 * 
	 * @return
	 */
	@RequestMapping("/pageListAdvert")
	@ResponseBody
	public PageFinder<Advert> pageListAdvert(@ModelAttribute("advert") Advert advert, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), advert);
		return advertService.getAdvertPagedList(q);
	}

	/**
	 * 跳转详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAdvertView")
	public String toAdvertView(String advertNo, ModelMap modelMap) {
		Advert advert = advertService.getAdvert(advertNo);
		modelMap.put("advert", advert);
		return "advert/advert_view";
	}

	/**
	 * 跳转新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/toAddAdvert")
	public String toAddAdvert(ModelMap modelMap) {
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "advert/advert_add";
	}

	/**
	 * 新增
	 * 
	 * @return
	 */
	@RequestMapping("/addAdvert")
	@ResponseBody
	public ResultInfo<Advert> addAdvert(@ModelAttribute("advert") Advert advert) {
		ResultInfo<Advert> resultInfo = advertService.addAdvert(advert, getOperator());
		if (Constant.SECCUESS.equals(resultInfo.getCode())) {
			this.generateHtml(advert.getAdvertNo());
		}
		return resultInfo;
	}

	/**
	 * 跳转修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/toEditAdvert")
	public String toEditAdvert(String advertNo, ModelMap modelMap) {
		Advert advert = advertService.getAdvert(advertNo);
		modelMap.put("advert", advert);
		List<DataDictItem> cities = dataDictItemService.getDataDictItemListByCatCode("CITY");// 城市
		modelMap.put("cities", cities);
		return "advert/advert_edit";
	}

	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping("/updateAdvert")
	@ResponseBody
	public ResultInfo<Advert> updateAdvert(@ModelAttribute("advert") Advert advert) {
		if (advert.getCensorStatus() == 2) {
			advert.setCensorStatus(0);
		}

		ResultInfo<Advert> resultInfo = advertService.updateAdvert(advert, this.getOperator());
		if (Constant.SECCUESS.equals(resultInfo.getCode())) {
			this.generateHtml(advert.getAdvertNo());
		}
		return resultInfo;
	}

	/**
	 * 跳转审核页面
	 * 
	 * @return
	 */
	@RequestMapping("/toCensorAdvert")
	public String toAdvertCensorView(String advertNo, ModelMap model) {
		model.addAttribute("advert", advertService.getAdvert(advertNo));
		return "advert/advert_censor";
	}

	/**
	 * 审核or启用提交
	 * 
	 * @return
	 */
	@RequestMapping("/updateAdvertStatus")
	@ResponseBody
	public ResultInfo<Advert> censorAdvert(@ModelAttribute("advert") Advert advert) {
		Operator operator = this.getOperator();
		if (operator != null) {
			if (advert.getCensorStatus() != null) {
				advert.setCensorTime(new Date());
				advert.setCensorId(operator.getOperatorId());
			}
			if (advert.getIsAvailable() != null) {
				advert.setAvailableUpdateTime(new Date());
			}
		}
		return advertService.updateAdvert(advert, operator);
	}

	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/deleteAdvert")
	@ResponseBody
	public ResultInfo<Advert> deleteAdvert(@ModelAttribute("advert") Advert advert) {
		return advertService.delAdvert(advert.getAdvertNo(), getOperator());
	}

	/**
	 * 构建html
	 * 
	 * @param advertNo
	 */
	private void generateHtml(String advertNo) {
		try {
			if (advertNo == null) {
				return;
			}
			Advert advert = advertService.getAdvert(advertNo);
			File fileDir = new File(resPath + "/advert/" + advertNo.substring(0, 2));
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			String url = "advert/" + advertNo.substring(0, 2) + "/" + advertNo.substring(2, advertNo.length())
					+ ".html";
			PrintStream printStream = new PrintStream(new FileOutputStream(resPath + "/" + url));
			StringBuffer buf = new StringBuffer();
			buf.append("<html>");
			buf.append("<head>");
			buf.append("<meta http-equiv=\"Content-Type\" content=\"text/html;charset=utf-8\"/>");
			buf.append("</head>");
			buf.append("<body>");
			buf.append("<div style=\"text-align: center;font-size: 24px;\">");
			buf.append(advert.getAdvertName());
			buf.append("</div>");
			buf.append("<div style=\"text-align: center;font-size: 16px;\">");
			buf.append(ECDateUtils.formatDate(advert.getCreateTime()));
			buf.append("</div>");
			buf.append("<div style=\"width:100%;clear:both;\">");
			buf.append(advert.getText1());
			buf.append("</div>");
			buf.append("</body>");
			buf.append("</html>");
			printStream.write(buf.toString().getBytes("utf-8"));
			printStream.close();
			advert.setLinkUrl(url);
			advertService.updateAdvert(advert, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
