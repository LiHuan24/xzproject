package cn.com.shopec.quartz.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.shopec.common.sendmsg.MsgConstant;
import cn.com.shopec.common.sendmsg.SendMsgCommonInterfaceService;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.ThemeOrder;
import cn.com.shopec.core.order.service.ThemeOrderService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.themepavilion.model.Course;
import cn.com.shopec.core.themepavilion.service.CourseService;

/**
 * 会员预约课程成功后，在上课时间两个小时之前需要发送短信提示会员去上课。
 * 每天2小时执行一次
 * @author LiHuan
 * Date 2017年12月12日 下午4:41:10
 */
public class ThemeOrderRemindQuartz {

	private Log log = LogFactory.getLog(ThemeOrderRemindQuartz.class);
	
	@Resource
	private MemberDao memberDao;
	@Resource
	private ThemeOrderService themeOrderService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private SendMsgCommonInterfaceService sendMsgCommonInterfaceService;
	@Resource
	private CourseService courseService;
	
	//定时任务业务处理
	public void quartzStart(){
		log.info("进入定时任务处理方法================查询已经预约的订单短信提示会员上课！");
		try {
			SysParam param = sysParamService.getByParamKey("ORDER_APPION_TIME");
			String orderAppionTime = param.getParamValue();
			if(param.getIsConfigurable() == 1 && StringUtils.isNotBlank(orderAppionTime)){
				//查询当前已注册的会员
				Member member = new Member();
				member.setIsBlacklist(0);//非黑名单
				List<Member> listMember = memberDao.queryMemberList(new Query(member));
				if(listMember != null && listMember.size() > 0){
					for (Member memberBean : listMember) {
						//根据会员编号、订单状态依次查询当前会员的订单
						ThemeOrder themeOrder = new ThemeOrder();
						themeOrder.setMemberNo(memberBean.getMemberNo());
						themeOrder.setOrderStatus(Integer.parseInt(OrderConstant.yuyueOrder_code));//已预约
						themeOrder.setIsSendMsg(0);//未发送短信的订单
						List<ThemeOrder> listThemeOrder = themeOrderService.getThemeOrderList(new Query(themeOrder));
						if(listThemeOrder != null && listThemeOrder.size() >0){
							log.info("扫码该会员已经预约的订单数量为："+listThemeOrder.size()+"条");
							for (ThemeOrder theme : listThemeOrder) {
								Date courseTime = this.getCourseDate(theme.getClassTime());
								Date newTime = new Date();
								int courseMinutes = ECDateUtils.differMinutes(newTime, courseTime).intValue();
								int paramMinutes = Integer.parseInt(orderAppionTime);
								if(courseMinutes == paramMinutes){
									if(new Date().getTime() > courseTime.getTime()){
										log.info("当前课程上课已结束");
									}else{
										//执行短信发送提示
										//您好，您于{2017年12月20号预约成功的健美操课程}上课不足两小时，请您合理安排时间，准时上课。
										String classDate = ECDateUtils.formatDate(theme.getPaymentTime(), "yyyy年MM月dd日 HH:mm:ss");
										String courseName = "";
										Course course = courseService.getCourse(theme.getCourseNo());
										if(course != null){
											courseName = course.getChineseName();
										}
										boolean status = false;
										String content = classDate+"预约成功的"+courseName+"课程";
										String msgType = MsgConstant.THEMEORDERTYPE;
										status = sendMsgCommonInterfaceService.sendMsgPost(memberBean.getMobilePhone(), content, msgType);
										log.info("订单预约成功提示会员按时上课发送结果!========================"+status);
										if(status == true){
											//修改主题订单表订单短信状态
											ThemeOrder order = new ThemeOrder();
											order.setThemeOrderNo(theme.getThemeOrderNo());
											order.setIsSendMsg(1);//已发送短信
											themeOrderService.updateThemeOrder(order, null);
										}
									}
								}
							}
						}
					}
				}else{
					log.info("此会员暂无已预约的课程订单！");
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	
	//处理上课时间
	private Date getCourseDate(String classTime){
		String time = classTime.substring(0,classTime.length() - 6);
		String courseTime = time+":00";
		Date courseDate = null;
		try {
			courseDate = ECDateUtils.parseTime(courseTime);
			log.info(courseDate);
		} catch (ParseException e) {
			log.info(e.getMessage());
		}
		return courseDate;
	}
	
	protected void execute(String arg0) throws Exception {
		try {
			log.info("--------课程预约订单上课短信提示，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			log.info("--------课程预约订单上课短信提示定时任务完成...");
		} catch (Exception e) {
			log.error("--------课程预约订单上课短信提示，错误信息：" + e.getMessage(), e);
		}
	}
}