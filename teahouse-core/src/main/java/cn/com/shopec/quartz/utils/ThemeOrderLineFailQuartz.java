package cn.com.shopec.quartz.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

/**
 * 排队中的订单，判断当前时间和上课时间相差1小时，则需要进行退款操作，修改订单状态为 2分钟执行一次
 * 
 * @author LiHuan Date 2017年12月13日 上午11:00:37
 */
public class ThemeOrderLineFailQuartz {

	private Log log = LogFactory.getLog(ThemeOrderLineFailQuartz.class);

	@Resource
	private MemberDao memberDao;
	@Resource
	private ThemeOrderService themeOrderService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private SendMsgCommonInterfaceService sendMsgCommonInterfaceService;

	// 定时任务业务处理
	public void quartzStart() {
		log.info("排队订单定时任务开始执行!");
		SysParam param = sysParamService.getByParamKey("LINEORDER_APPION_FAIL");
		String orderLineTime = param.getParamValue();
		if(param.getIsConfigurable() == 1 && StringUtils.isNotBlank(orderLineTime)){
			//查询当前已注册的会员
			Member member = new Member();
			member.setIsBlacklist(0);//非黑名单
			List<Member> listMember = memberDao.queryMemberList(new Query(member));
			if(listMember != null && listMember.size() > 0){
				for (Member memberBean : listMember) {
					//根据会员编号、订单状态依次查询当前会员的订单
					ThemeOrder themeOrder = new ThemeOrder();
					themeOrder.setIsSendMsg(0);//未发送短信
					themeOrder.setMemberNo(memberBean.getMemberNo());
					themeOrder.setOrderStatus(Integer.parseInt(OrderConstant.pdzOrder_code));//排队中
					List<ThemeOrder> listThemeOrder = themeOrderService.getThemeOrderList(new Query(themeOrder));
					if(listThemeOrder != null && listThemeOrder.size() >0){
						for (ThemeOrder theme : listThemeOrder) {
							Date courseTime = this.getCourseDate(theme.getClassTime());
							Date newTime = new Date();
							//当前时间和上课时间相差60分钟
							int courseMinutes = ECDateUtils.differMinutes(newTime, courseTime).intValue();
							int paramMinutes = Integer.parseInt(orderLineTime);
							if(courseMinutes <= paramMinutes){
								//执行退款操作、退款操作成功后执行短信发送
								if(new Date().getTime()>courseTime.getTime()){
									log.info("退款失败！");
								}else{
									themeOrderService.lineOrderRefundUpdate(theme);
								}
							}
						}
					}else{
						log.info("此会员暂无排队中的课程订单！");
					}
				}
			}
		}
	}

	// 处理上课时间
	private Date getCourseDate(String classTime) {
		String time = classTime.substring(0, classTime.length() - 6);
		String courseTime = time + ":00";
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
			log.info("--------排队订单预约未成功，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			log.info("--------排队订单预约未成功定时任务完成...");
		} catch (Exception e) {
			log.error("--------排队订单预约未成功，错误信息：" + e.getMessage(), e);
		}
	}
}