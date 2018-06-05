package cn.com.shopec.mgt.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.member.model.MemberStatisticVo;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.model.MemberZhimaScore;
import cn.com.shopec.core.member.service.MemberBalanceService;
import cn.com.shopec.core.member.service.MemberLevelService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.member.service.MemberZhimaScoreService;
import cn.com.shopec.core.member.vo.MemberBalanceVo;
import cn.com.shopec.core.system.dao.SysUserDao;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 会员管理
 * 
 * @author machao
 *
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController {
	
/*	private static final Log log = LogFactory.getLog(MemberZhimaScoreServiceImpl.class);
*/
	@Resource
	private MemberService memberService;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private MemberLevelService memberLevelService;
	@Resource
	private MemberZhimaScoreService memberZhimaScoreService;
	@Resource
	private MemberBalanceService memberBalanceService;
	@Resource
	private SysParamService sysParamService;
	/*@Value("${base_path}")
	private String basePath;*/

	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	@RequestMapping("toMemberList")
	public String toMemberList(@ModelAttribute("member") Member member, ModelMap model) {
		Query q = new Query();
		MemberLevel memberLevel = new MemberLevel();
		memberLevel.setIsAvailable(1);
		memberLevel.setIsDeleted(0);
		q.setQ(memberLevel);
		List<MemberLevel> memberLevelList = memberLevelService.getMemberLevelList(q);
		model.addAttribute("memberLevelList", memberLevelList);
		model.addAttribute("member", member);
		model.addAttribute("roleAdminTag",getLoginSysUserRoleAdmin()); // 权限
		return "/member/member_list";
	}
	
	/**
	 * 查询列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListMember")
	@ResponseBody
	public PageFinder<MemberBalanceVo> pageListMember(@ModelAttribute("member") MemberBalanceVo member,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, member);
		PageFinder<MemberBalanceVo> memberPage =  memberService.getMemberBalancePageList(q);
		return memberPage;
	}
	
	/**
	 * 详情
	 * 
	 * @param memberNo
	 * @return
	 */
	@RequestMapping("toMemberView")
	public String toMemberView(@ModelAttribute("memberNo") String memberNo, Model model) {
		Member member = memberService.getMember(memberNo);
		
		//会员等级名称
		if (member.getMemberLevelId() != null && !member.getMemberLevelId().equals("")) {
			MemberLevel memberLevel = memberLevelService.getMemberLevel(member.getMemberLevelId());
			if (memberLevel != null) {
				model.addAttribute("levelName", memberLevel.getLevelName());
			}
		}
		
		//芝麻分
		MemberZhimaScore memberZhimaScore =  memberZhimaScoreService.getMemberZhimaScore(memberNo);
		int zhimaScore = 0;
		if(memberZhimaScore != null){
			if(memberZhimaScore.getScore() != null){
				zhimaScore = memberZhimaScore.getScore();
			}
		}
		model.addAttribute("zhimaScore", zhimaScore);

		//余额
		MemberBalance memberBalance = memberBalanceService.getMemberBalance(memberNo);
		double balance = 0d;
		double deposit = 0d;
		if(memberBalance != null){
			if(memberBalance.getMemberBalance() != null){
				balance = memberBalance.getMemberBalance();
				deposit = memberBalance.getMemberDeposit();
			}
		}
		model.addAttribute("balance", balance);
		model.addAttribute("deposit", deposit);
		
		// 昵称memberNick为空的时候，显示为姓名
		if ("".equals(member.getMemberNick()) || member.getMemberNick() == null) {
			member.setMemberNick(member.getMemberName());
		}
		
		// 邀请人
		if(member.getRefereeId() != null && !member.getRefereeId().equals("")){
			Member memberQuery = new Member();
			memberQuery.setMemberNo(member.getRefereeId());
			List<Member> memberTemp = memberService.getMemberList(new Query(memberQuery));
			if(memberTemp != null && !memberTemp.isEmpty()){
				model.addAttribute("memberReferee", memberTemp.get(0).getMobilePhone());
			}
		}
		
		//邀请会员数量
		Member memberQuery = new Member();
		memberQuery.setRefereeId(member.getMemberNo());
		List<Member> memberTemp = memberService.getMemberList(new Query(memberQuery));
		if (memberTemp != null && memberTemp.size() > 0) {
			model.addAttribute("mbs", memberTemp.size());
		} else {
			model.addAttribute("mbs", 0);
		}
		
		model.addAttribute("member", member);
		return "/member/member_view";
	}
	
	/**
	 * 修改
	 * 
	 * @param member
	 * @return
	 */
	@RequestMapping("updateMember")
	@ResponseBody
	public ResultInfo<Member> updateMember(@ModelAttribute("member") Member member) {
		return memberService.updateMember(member, getOperator());
	}
	
	/**
	 * 导出excel文档
	 * @param member
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("toMemberExport")
	public void toMemberExport(@ModelAttribute("member") Member member, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			@SuppressWarnings("deprecation")
			String path = request.getRealPath("/") + "res" + File.separator + "member.xls";// 声明工作薄
			InputStream is = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);// 生成表格
			
			List<Member> list = memberService.getMemberList(new Query(member));
			int rowIndex = 1;
			for (Member memberData : list) {
				sheet.createRow(rowIndex);
				
				String sex = memberData.getSex() == null ? "" : memberData.getSex().toString();
				if (sex.equals("0")) {
					sex = "女";
				} else if (sex.equals("1")){
					sex = "男";
				} else {
					sex = "未知";
				}
				
				String isBlacklist = memberData.getIsBlacklist() == null ? "" : memberData.getIsBlacklist().toString();
				if (isBlacklist.equals("0")) {
					isBlacklist = "正常";
				} else if (isBlacklist.equals("1")){
					isBlacklist = "黑名单";
				} else {
					isBlacklist = "";
				}
				
				int columnIndex = 0;
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(memberData.getMemberNo()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(memberData.getMemberName()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(memberData.getMobilePhone()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(sex));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(memberData.getIdCard()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(memberData.getInvitationCode()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(isBlacklist));
				setColumnValue(sheet, rowIndex, columnIndex++, ECDateUtils.formatTime(memberData.getRegisterTime()));
				
				rowIndex++;
			}
			response.reset();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=member.xls");
			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载会员统计页
	 */
	@RequestMapping("memberSummaryEchart")
	public String memberSummaryEchart(ModelMap model) {
		return "/member/member_echart_list";
	}
	
	/**
	 * 会员统计 统计会员注册APP数量
	 * 报表以图表折线图展示 按天统计
	 */
	@RequestMapping("statisticMemberDaysRegister")
	@ResponseBody
	public ResultInfo<Map<String,Object>> statisticMemberDaysRegister(Date startTimeDay, Date endTimeDay) throws Exception{
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<Map<String, Object>>();
		String beginDate = "";
		String endDate = "";
		if(startTimeDay == null && endTimeDay == null){
			beginDate = ECDateUtils.getFirstDayOfMonth();
			endDate = ECDateUtils.getLastDayOfMonth();
		}else{
			beginDate = ECDateUtils.formatDate(startTimeDay, "yyyy-MM-dd");
			endDate = ECDateUtils.formatDate(endTimeDay, "yyyy-MM-dd");
		}
		List<String> listDate = ECDateUtils.getDatesForBeginAndEndTime(beginDate, endDate);
		int size = listDate.size();
		String[] dates = (String[])listDate.toArray(new String[size]);//使用了第二种接口，返回值和参数均为结果 
		int[] memberCount = new int[dates.length];
		for (int i = 0; i < dates.length; i++) {
			//根据所选开始日期和结束日期统计每一天的会员注册数量
			List<MemberStatisticVo> listMember = memberService.statDaysMemberRegisterList(beginDate, endDate);
			if(listMember != null && listMember.size() > 0){	
				for (MemberStatisticVo memberDaysVo : listMember) {
					if(dates[i].equals(memberDaysVo.getDays())){
						System.out.println(Integer.parseInt(memberDaysVo.getMemberNo()));
						memberCount[i] = Integer.parseInt(memberDaysVo.getMemberNo());
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("days", dates);
		if(memberCount.length > 0){
			map.put("memberCount", memberCount);
		}else{
			map.put("memberCount", 0);
		}
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
	
	/**
	 * 会员统计 统计会员注册APP数量
	 * 报表以图表折线图展示 按月统计
	 */
	@RequestMapping("statisticMemberMonthRegister")
	@ResponseBody
	public ResultInfo<Map<String,Object>> statisticMemberMonthRegister(String startTimeMonth, String endTimeMonth) throws Exception{
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<Map<String, Object>>();
		String beginDate = "";
		String endDate = "";
		if(StringUtils.isBlank(startTimeMonth) && StringUtils.isBlank(endTimeMonth)){
			beginDate = ECDateUtils.getCurrentTime("yyyy")+"-01";
			endDate = ECDateUtils.getCurrentTime("yyyy")+"-12";
		}else{
			beginDate = startTimeMonth;
			endDate = endTimeMonth;
		}
		List<String> listDate = ECDateUtils.getMonthBetween(beginDate, endDate);
		int size = listDate.size();
		String[] dates = (String[])listDate.toArray(new String[size]);//使用了第二种接口，返回值和参数均为结果 
		int[] memberCount = new int[dates.length];
		for (int i = 0; i < dates.length; i++) {
			//根据所选开始日期和结束日期统计每一月的会员注册数量
			List<MemberStatisticVo> listMember = memberService.statMonthMemberRegisterList(beginDate, endDate);
			if(listMember != null && listMember.size() > 0){	
				for (MemberStatisticVo memberDaysVo : listMember) {
					if(dates[i].equals(memberDaysVo.getMonths())){
						memberCount[i] = Integer.parseInt(memberDaysVo.getMemberNo());
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("month", dates);
		if(memberCount.length > 0){
			map.put("memberCount", memberCount);
		}else{
			map.put("memberCount", 0);
		}
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
	
	/**
	 * 会员统计 统计会员注册APP数量
	 * 报表以图表折线图展示 按年统计
	 */
	@RequestMapping("statisticMemberYearRegister")
	@ResponseBody
	public ResultInfo<Map<String,Object>> statisticMemberYearRegister(String startTimeYear, String endTimeYear) throws Exception{
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<Map<String, Object>>();
		String beginDate = "";
		String endDate = "";
		if(StringUtils.isBlank(startTimeYear) && StringUtils.isBlank(endTimeYear)){
			beginDate = sysParamService.getByParamKey("STATE_YEAR").getParamValue();
			endDate = ECDateUtils.getCurrentTime("yyyy");
		}else{
			beginDate = startTimeYear;
			endDate = endTimeYear;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); //格式化日期
		Date startTime =  sdf.parse(beginDate);
		Date endTime = sdf.parse(endDate);
		List<Date> listDate = ECDateUtils.getDatesBetweenTwoDate(startTime, endTime);  
		List<String> listTime = new ArrayList<String>();
		for(int i=0;i<listDate.size();i++){  
	        System.out.println(sdf.format(listDate.get(i)));
	        listTime.add(sdf.format(listDate.get(i)));
	    }  
		int size = listTime.size();
		String[] dates = (String[])listTime.toArray(new String[size]);//使用了第二种接口，返回值和参数均为结果 
		int[] memberCount = new int[dates.length];
		for (int i = 0; i < dates.length; i++) {
			//根据所选开始日期和结束日期统计每一年的会员注册数量
			List<MemberStatisticVo> listMember = memberService.statYearMemberRegisterList(beginDate, endDate);
			if(listMember != null && listMember.size() > 0){	
				for (MemberStatisticVo memberDaysVo : listMember) {
					if(dates[i].equals(memberDaysVo.getYears())){
						memberCount[i] = Integer.parseInt(memberDaysVo.getMemberNo());
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("years", dates);
		if(memberCount.length > 0){
			map.put("memberCount", memberCount);
		}else{
			map.put("memberCount", 0);
		}
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
}