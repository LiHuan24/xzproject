package cn.com.shopec.quartz.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.sendmsg.MsgConstant;
import cn.com.shopec.common.sendmsg.SendMsgCommonInterfaceService;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.ThemeOrder;
import cn.com.shopec.core.order.service.ThemeOrderService;
import cn.com.shopec.core.themepavilion.model.ArrayCourse;
import cn.com.shopec.core.themepavilion.model.Course;
import cn.com.shopec.core.themepavilion.service.ArrayCourseService;
import cn.com.shopec.core.themepavilion.service.CourseService;

/**
 * 主题馆订单未支付定时任务处理 每30分钟执行一次
 * 
 * @author LiHuan Date 2018年1月8日 上午11:39:56
 */
public class ThemeOrderNoPayDealQuartz {
	private Log log = LogFactory.getLog(ThemeOrderNoPayDealQuartz.class);

	@Resource
	private MemberDao memberDao;
	@Resource
	private ArrayCourseService arrayCourseService;
	@Resource
	private ThemeOrderService themeOrderService;
	@Resource
	private CourseService courseService;
	@Resource
	private SendMsgCommonInterfaceService sendMsgCommonInterfaceService;

	public void quartzStart() throws Exception {
		Member member = new Member();
		member.setIsBlacklist(0);// 非黑名单
		List<Member> listMember = memberDao.queryMemberList(new Query(member));
		if (listMember != null && listMember.size() > 0) {
			for (Member memberBean : listMember) {
				ThemeOrder themeOrder = new ThemeOrder();
				themeOrder.setMemberNo(memberBean.getMemberNo());
				themeOrder.setPayStatus(0);//未支付
				List<ThemeOrder> listThemeOrder = themeOrderService.getThemeOrderList(new Query(themeOrder));
				if(listThemeOrder != null && listThemeOrder.size() >0){
					for (ThemeOrder theme : listThemeOrder) {
						if(theme.getOrderStatus() == null){
							ThemeOrder order = new ThemeOrder();
							order.setThemeOrderNo(theme.getThemeOrderNo());
							order.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));
							ResultInfo<ThemeOrder> result = themeOrderService.updateThemeOrder(order, null);
							if(result.getCode().equals(Constant.SECCUESS)){
								//更新订单后修改排课表
								ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(theme.getArrangeNo());
								if(arrayCourse != null){
									if(arrayCourse.getPeopleNumber() == arrayCourse.getReservation()){
										ArrayCourse course = new ArrayCourse();
										course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
										if(arrayCourse.getLineUp() >= theme.getPeopleNumber()){
											course.setLineUp(arrayCourse.getLineUp() - theme.getPeopleNumber());
										}
										arrayCourseService.updateArrayCourse(course, null);
									}else{
										ArrayCourse course = new ArrayCourse();
										course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
										if(arrayCourse.getReservation() >= theme.getPeopleNumber()){
											course.setReservation(arrayCourse.getReservation() - theme.getPeopleNumber());
										}
										
										ResultInfo<ArrayCourse> resultCourse = arrayCourseService.updateArrayCourse(course, null);
										//更新排课表成功之后，查询有没有排队中的订单，若有直接更新此订单为已预约
										if(resultCourse.getCode().equals(Constant.SECCUESS)){
											ThemeOrder themeBean = new ThemeOrder();
											themeBean.setArrangeNo(theme.getArrangeNo());
											themeBean.setOrderStatus(Integer.parseInt(OrderConstant.pdzOrder_code));
											int pageNo = 1;
											int pageSize = theme.getPeopleNumber();
											PageFinder<ThemeOrder> pageLineOrder = themeOrderService.queryLineThemeOrder(new Query(pageNo, pageSize, themeBean));
											if(pageLineOrder != null && pageLineOrder.getData() != null && pageLineOrder.getData().size()>0){
												for (ThemeOrder to : pageLineOrder.getData()) {
													//更新排队订单
													ThemeOrder lineOrder = new ThemeOrder();
													lineOrder.setThemeOrderNo(to.getThemeOrderNo());
													lineOrder.setOrderStatus(Integer.parseInt(OrderConstant.yuyueOrder_code));
													ResultInfo<ThemeOrder> themeResult = themeOrderService.updateThemeOrder(lineOrder, null);
													if(themeResult.getCode().equals(OrderConstant.success_code)){
														//执行短信发送
														String classDate = ECDateUtils.formatDate(to.getPaymentTime(), "yyyy年MM月dd日 HH:mm:ss");
														String courseName = "";
														Course courseBean = courseService.getCourse(to.getCourseNo());
														if(courseBean != null){
															courseName = courseBean.getChineseName();
														}
														String msgType = MsgConstant.LINEUPTYPE;
														String content = classDate+"预约成功的"+courseName+"课程";
														boolean flag = false;
														flag = sendMsgCommonInterfaceService.sendMsgPost(to.getMobilePhone(), content, msgType);
														log.info("发送内容为：==========================》"+flag);
													}else{
														log.info("更新排队中的订单数据失败！");
													}
												}
											}
										}else{
											log.info("更新排课表数据失败！");
										}
									}
								}
							}else{
								log.info("更新未支付的主题订单失败！");
							}
						}
					}
				}
			}
		}
	}

	protected void execute(String arg0) throws Exception {
		try {
			log.info("--------主题馆未支付订单修改，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			log.info("--------主题馆未支付订单修改定时任务完成...");
		} catch (Exception e) {
			log.error("--------主题馆未支付订单修改，错误信息：" + e.getMessage(), e);
		}
	}
}