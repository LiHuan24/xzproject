package cn.com.shopec.mgt.themepavilion.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.qrcode.DownloadQrcode;
import cn.com.shopec.common.utils.qrcode.QRcodeImageCreater;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.themepavilion.common.ThemePavilionConstant;
import cn.com.shopec.core.themepavilion.model.ArrayCourse;
import cn.com.shopec.core.themepavilion.model.Coach;
import cn.com.shopec.core.themepavilion.service.ArrayCourseService;
import cn.com.shopec.core.themepavilion.service.CoachService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 共享健身房：教练管理
 * 
 * @author LiHuan Date 
 * 2017年11月2日 下午2:33:36
 */
@Controller
@RequestMapping("coach")
public class CoachController extends BaseController {

	private static final Log log = LogFactory.getLog(CoachController.class);

	@Resource
	private CoachService coachService;
	@Resource
	private MemberService MemberService;
	@Resource
	private ArrayCourseService arrayCourseService;
	
	@Value("${share_path}")
	private String sharePath;
	@Value("${res_img_path}")
	private String resImgPath;
	@Value("${image_path}")
	private String imgPath;

	/**
	 * 进入教练列表页
	 */
	@RequestMapping("toCoachList")
	public String toCoachList() {
		log.info("加载教练列表");
		return "/coach/coach_list";
	}

	/**
	 * 获取教练分页列表数据
	 * 
	 * @param coach
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("coachPageList")
	@ResponseBody
	public PageFinder<Coach> coachPageList(@ModelAttribute("coach") Coach coach, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		log.info("获取教练分页列表数据");
		Query q = new Query(pageNo, pageSize, coach);
		return coachService.getCoachPagedList(q);
	}

	/**
	 * 进入教练新增页
	 */
	@RequestMapping("toCoachAdd")
	public String toCoachAdd(ModelMap modelMap,String mobilePhone) {
		log.info("进入教练新增页");
		modelMap.put("mobilePhone", mobilePhone);
		return "/coach/coach_add";
	}
	
	/**
	 * 进入教练编辑页
	 */
	@RequestMapping("toCoachEdit")
	public String toCoachEdit(ModelMap modelMap,String coachNo) {
		log.info("进入教练编辑页");
		Coach coach = coachService.getCoach(coachNo);
		modelMap.put("coach", coach);
		return "/coach/coach_edit";
	}
	
	/**
	 * 进入教练查看页
	 */
	@RequestMapping("toCoachView")
	public String toCoachView(ModelMap modelMap,String mobilePhone) {
		log.info("进入教练查看页");
		Coach coachBean = coachService.getCoachByMemberPhone(mobilePhone);
		if(coachBean != null){
			Coach coach = coachService.getCoach(coachBean.getCoachNo());
			//隐藏身份证中间几位 为用户保密
			String idCard = coach.getIdCard().replaceAll("(\\d{4})\\d{10}(\\d{4})","$1****$2");
			coach.setIdCard(idCard);
			modelMap.put("coach", coach);
		}
		return "/coach/coach_view";
	}
	
	/**
	 * 进入教练审核页audit
	 */
	@RequestMapping("toCoachAudit")
	public String toCoachAudit(ModelMap modelMap,String coachNo) {
		log.info("进入教练审核页");
		Coach coach = coachService.getCoach(coachNo);
		modelMap.put("coach", coach);
		return "/coach/coach_audit";
	}
	
	/**
	 * 教练数据新增或者编辑操作
	 */
	@RequestMapping("saveOrUpdateCoach")
	@ResponseBody
	public ResultInfo<Coach> saveOrUpdateCoach(@ModelAttribute("coach") Coach coach){
		ResultInfo<Coach> resultInfo = new ResultInfo<Coach>();
		if(null == coach.getCoachNo()){
			//新增
			String coachNo = String.valueOf(new Date().getTime() * 10000 + new Random().nextInt(10000));
			coach.setCoachNo(coachNo);
			// 生成门店二维码
			if (StringUtils.isBlank(coach.getCoachQrCode())) {
				try {
					String resFilePathName = QRcodeImageCreater.generateQRCode(coach.getCoachNo(), 10, "jpg", imgPath+"/coach_photo" + "/", "",coach.getCoachNo());
					coach.setCoachQrCode(resFilePathName);
				} catch (IOException e) {
					log.error(e.toString());
				}
			}
			//设置教练 未审核状态
			coach.setCensorStatus(ThemePavilionConstant.AUDIT_NO);
			coach.setCoachStatus(0);//默认停用
			resultInfo = coachService.addCoach(coach, getOperator());
			//修改会员用户类型为教练
			Member member = MemberService.getMemberByPhone(coach.getMobilePhone());
			if(member != null){
				Member memberBean = new Member();
				memberBean.setMemberNo(member.getMemberNo());
				memberBean.setUsersType(Constant.COACH_TYPE);//转为教练类型
				MemberService.updateMember(memberBean, getOperator());
			}
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		}else{
			/*try {
				String resFilePathName = QRcodeImageCreater.generateQRCode(coach.getCoachNo(), 10, "jpg", "http://182.92.194.70/image-server/coach_photo" + "/", "",coach.getCoachNo());
				coach.setCoachQrCode(resFilePathName);
			} catch (IOException e) {
				log.error(e.toString());
			}*/
			resultInfo = coachService.updateCoach(coach, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
			return resultInfo;
		}
	}
	
	/**
	 * 执行下载二维码
	 * @throws Exception 
	 */
	@RequestMapping("downloadCoachQrcode")
	public void downloadCoachQrcode(HttpServletRequest request, HttpServletResponse response,String coachNo) throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		//获取该教练对象
		Coach coach = coachService.getCoach(coachNo);
		if(coach != null){
			String qrCodeUrl = coach.getCoachQrCode();
			map.put("url", qrCodeUrl);
			map.put("resImgPath", imgPath+"/coach_photo/qrcode");
			//执行下载
			log.info("通过文件流执行二维码下载");
			DownloadQrcode.downloadQrcode(request, response, map);
		}
	}
	
	/**
	 * 教练数据审核操作
	 * 
	 */
	@RequestMapping("changeCoachAuditStatus")
	@ResponseBody
	public ResultInfo<Coach> changeCoachAuditStatus(@ModelAttribute("coach") Coach coach){
		ResultInfo<Coach> resultInfo = new ResultInfo<Coach>();
		if(StringUtils.isNotBlank(coach.getCoachNo())){
			coach.setCencorTime(new Date());
			Operator operator = getOperator();
			coach.setCencorId(operator.getOperatorId());
			resultInfo = coachService.updateCoach(coach, getOperator());
			resultInfo.setCode(Constant.SECCUESS);
		}
		return resultInfo;
	}
	
	/**
	 * 启用或者停用教练
	 */
	@RequestMapping("operationCoachStatus")
	@ResponseBody
	public ResultInfo<Coach> operationCoachStatus(String coachNo,String status) {
		ResultInfo<Coach> resultInfo = new ResultInfo<Coach>();
		try {
			Coach coach = coachService.getCoach(coachNo);
			if(coach != null){
				Coach updateCoach = new Coach();
				updateCoach.setCoachNo(coach.getCoachNo());
				updateCoach.setCoachStatus(Integer.parseInt(status));
				coachService.updateCoach(updateCoach, getOperator());
			}
			resultInfo.setCode(Constant.SECCUESS);
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			log.error(e.getMessage());
		}
		return resultInfo;
	}
	
	/**
	 * 根据教练编号查询教练的课程数据
	 */
	@RequestMapping("toCoachCourse")
	public String toCoachCourse(ModelMap modelMap, String coachNo){
		modelMap.put("coachNo", coachNo);
		return "/coach/coach_course_list";
	}
	
	/**
	 * 教练课程列表
	 */
	@RequestMapping("coachCoursenPageList")
	@ResponseBody
	public PageFinder<ArrayCourse> pageCoachCourseList(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize,String coachNo) throws ParseException{
		ArrayCourse arrayCourse = new ArrayCourse();
		arrayCourse.setCoachNo(coachNo);
		arrayCourse.setPostedStatus(1);//已发布
		Query q = new Query(pageNo, pageSize, arrayCourse);
		PageFinder<ArrayCourse> pageList = arrayCourseService.getArrayCoursePagedList(q);
		if(pageList != null && pageList.getData() != null){
			for (ArrayCourse array : pageList.getData()) {
				ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(array.getStoreNo(), array.getFtlRow(),array.getCourseType());
				Date corseDate = ECDateUtils.formatDateToDateNew(array.getCourseDate());
				String classTime = ECDateUtils.getCurrentDateAsString(corseDate);
				String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
				String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
				array.setShowDate(classTime + " " + startTime + "-" + endTime);
			}
		}
		return pageList;
	}
}