package cn.com.shopec.mgt.member.controller;

import java.text.ParseException;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberPointsRecord;
import cn.com.shopec.core.member.service.MemberPointsRecordService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 会员积分记录管理
 * 
 * @author zln
 *
 */
@Controller
@RequestMapping("/memberPointsRecord")
public class MemberPointsRecordController extends BaseController {

	@Resource
	private MemberPointsRecordService memberPointsRecordService;
	
	@Resource
	private MemberService memberService;
	
	/**
	 * 进入会员积分记录列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toMemberPointsRecord")
	public String toMemberPointsRecord() {
		return "/member/member_points_record_list";
	}

	/**
	 * 查询会员积分记录列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListMemberPointsRecord")
	@ResponseBody
	public PageFinder<MemberPointsRecord> pageListMemberPointsRecord(@ModelAttribute("memberPointsRecord") MemberPointsRecord memberPointsRecord,@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, memberPointsRecord);
		return memberPointsRecordService.getMemberPointsRecordPagedList(q);
	}

	/**
	 * 会员积分规则详情
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("toMemberPointsRecordView")
	public String toMemberPointsRecordView(String recordId, ModelMap model) {
		MemberPointsRecord memberPointsRecord = memberPointsRecordService.getMemberPointsRecord(recordId);
		if(memberPointsRecord.getMemberNo()!=null&&!memberPointsRecord.getMemberNo().equals("")){
			Member member = memberService.getMember(memberPointsRecord.getMemberNo());
			if(member!=null){
				memberPointsRecord.setMemberName(member.getMemberName());
			}
		}
		model.addAttribute("memberPointsRecord", memberPointsRecord);
		return "/member/member_points_record_view";
	}
}
