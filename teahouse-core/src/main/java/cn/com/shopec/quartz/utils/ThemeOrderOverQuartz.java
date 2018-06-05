package cn.com.shopec.quartz.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.ThemeOrder;
import cn.com.shopec.core.order.service.ThemeOrderService;
import cn.com.shopec.core.pay.PayService;

/**
 * 主题订单修改为已结束
 * 当已预约的课程没有去上课、进行中的订单上课完成执行订单状态的修改
 * 每天2小时执行一次
 * @author LiHuan
 * Date 2017年12月12日 下午4:00:29
 */
public class ThemeOrderOverQuartz {
	
	private Log log = LogFactory.getLog(ThemeOrderOverQuartz.class);
	
	@Resource
	private MemberDao memberDao;
	@Resource
	private ThemeOrderService themeOrderService;
	@Resource
	private PayService payService;
	
	//定时任务业务处理
	public void quartzStart(){
		log.info("进入修改订单状态为已结束的方法");
		//查询所有已注册且不为黑名单的会员账户
		Member member = new Member();
		member.setIsBlacklist(0);//非黑名单
		List<Member> listMember = memberDao.queryMemberList(new Query(member));
		if(listMember != null && listMember.size() > 0){
			for (Member members : listMember) {
				List<ThemeOrder> listOrder = themeOrderService.queryOrderDataByMemberNo(members.getMemberNo());
				if(listOrder != null && listOrder.size() >0){
					for (ThemeOrder themeOrder : listOrder) {
						Date nowDate = new Date();
						Date classEndDate = this.handlingClassTime(themeOrder.getClassTime());
						if(nowDate.getTime() > classEndDate.getTime()){
							//如果当前时间大于该课程订单的上课结束时间，则可认为此订单已结束
							ThemeOrder updateOrder = new ThemeOrder();
							updateOrder.setThemeOrderNo(themeOrder.getThemeOrderNo());
							updateOrder.setOrderStatus(Integer.parseInt(OrderConstant.yjsOrder_code));
							themeOrderService.updateThemeOrder(updateOrder, null);
							
							payService.payOverAddRecordzOrder(themeOrder);//订单结束后给新加积分
						}
					}
				}else{
					log.info("提示：此会员暂无已预约或者进行中的主题订单数据...");
				}
			}
		}else{
			log.info("暂无注册的会员数据...");
		}
	}

	//处理课程结束时间
	private Date handlingClassTime(String classTime){
		String times = classTime.substring(0, 10);
		String hour = classTime.substring(classTime.length() - 5);
		String courseTime = times+" "+hour+":00";
		Date courseDate = null;
		try {
			courseDate = ECDateUtils.parseTime(courseTime);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return courseDate;
	}
	
	protected void execute(String arg0) throws Exception {
		try {
			log.info("--------课程订单结束，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			log.info("--------课程订单修改状态为已结束定时任务完成...");
		} catch (Exception e) {
			log.error("--------课程订单修改状态为已结束，错误信息：" + e.getMessage(), e);
		}
	}
}