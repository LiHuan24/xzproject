package cn.com.shopec.mgt.member.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.member.service.MemberBalanceService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 会员余额管理
 */
@Controller
@RequestMapping("/member")
public class MemberBalanceController extends BaseController {

	@Resource
	private MemberService memberService;
	@Resource
	private MemberBalanceService memberBalanceService;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 会员余额列表页面
	 * 
	 * @return
	 */
	@RequestMapping("/toMemberBalanceList")
	public String toMemberBalanceList() {
		return "member/memberBalance_list";
	}

	/**
	 * 会员余额列表页面分页
	 * 
	 * @return
	 */
	@RequestMapping("/pageListMemberBalance")
	@ResponseBody
	public PageFinder<MemberBalance> pageListMemberBalance(@ModelAttribute("memberBalance") MemberBalance memberBalance, Query query) {
		Query q = new Query(query.getPageNo(), query.getPageSize(), memberBalance);
		return memberBalanceService.getMemberBalancePagedList(q);
	}

	/**
	 * 会员余额详情页面
	 * 
	 * @return
	 */
	@RequestMapping("/toMemberBalanceView")
	public String toMemberBalanceView(String memberNo, ModelMap modelMap) {
		MemberBalance memberBalance = memberBalanceService.getMemberBalance(memberNo);
		modelMap.put("memberBalance", memberBalance);
		return "member/memberBalance_view";
	}

	
	/**
	 * 会员余额解冻/冻结页面
	 * 
	 * @return
	 */
	@RequestMapping("/toMemberBalanceFreezeView")
	public String toMemberBalanceView(String memberNo,Integer isFreeze, ModelMap modelMap) {
		MemberBalance memberBalance = memberBalanceService.getMemberBalance(memberNo);
		modelMap.put("memberBalance", memberBalance);
		modelMap.put("isFreeze", isFreeze);
		return "member/memberBalance_freeze";
	}

	/**
	 * 会员余额冻结/解冻用提交
	 * 
	 * @return
	 */
	@RequestMapping("/updateMemberBalance")
	@ResponseBody
	public ResultInfo<MemberBalance> updateMemberBalance(@ModelAttribute("memberBalance")MemberBalance memberBalance) {
		return memberBalanceService.updateMemberBalance(memberBalance, getOperator());
	}

}
