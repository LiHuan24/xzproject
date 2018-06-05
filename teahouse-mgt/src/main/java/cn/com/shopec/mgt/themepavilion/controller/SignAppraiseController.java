package cn.com.shopec.mgt.themepavilion.controller;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.themepavilion.model.SignAppraise;
import cn.com.shopec.core.themepavilion.service.SignAppraiseService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 教练&会员签到记录控制层
 * 目前此业务只查询教练签到记录
 * @author LiHuan
 * Date 2017年12月22日 上午11:29:36
 */
@Controller
@RequestMapping("coachSign")
public class SignAppraiseController extends BaseController {

	private static final Log log = LogFactory.getLog(SignAppraiseController.class);
	
	@Resource
	private SignAppraiseService signAppraiseService;
	
	/**
	 * 进入教练签到列表
	 */
	@RequestMapping("toCoachSignList")
	public String toCoachSignList() {
		log.info("加载教练上课签到列表");
		return "/coach/coach_sign_list";
	}
	
	/**
	 * 获取教练上课签到记录分页数据
	 */
	@RequestMapping("coachSignPageList")
	@ResponseBody
	public PageFinder<SignAppraise> coachSignPageList(@ModelAttribute("signAppraise") SignAppraise signAppraise, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		log.info("获取教练上课签到分页列表数据");
		signAppraise.setSignType(1);//教练签到类型
		Query q = new Query(pageNo, pageSize, signAppraise);
		return signAppraiseService.getSignAppraisePagedList(q);
	}
	
	/**
	 * 获取要审核的教练上课记录对象
	 */
	@RequestMapping("/toCoachClassAudit")
	@ResponseBody
	public SignAppraise toCoachClassAudit(String siapId) {
		SignAppraise signAppraise = null;
		if (siapId != null && siapId.length() != 0) {
			signAppraise = signAppraiseService.getSignAppraise(siapId);
		}
		return signAppraise;
	}
	
	/**
	 * 教练上课记录审核操作
	 */
	@RequestMapping("coachClassAudit")
	@ResponseBody
	public ResultInfo<SignAppraise> coachClassAudit(@ModelAttribute("signAppraise") SignAppraise signAppraise) {
		ResultInfo<SignAppraise> resultInfo = new ResultInfo<SignAppraise>();
		if (signAppraise.getSiapId() != null && signAppraise.getSiapId().length() != 0) {
			resultInfo = signAppraiseService.updateSignAppraise(signAppraise, getOperator());
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("上课记录编号不能为空");
		}
		return resultInfo;
	}	
}