package cn.com.shopec.core.order.dao;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.ThemeOrder;
import cn.com.shopec.core.order.vo.ThemeOrderStatisticVo;

/**
 * 主题订单表 数据库处理类
 */
public interface ThemeOrderDao  extends BaseDao<ThemeOrder, Serializable>{
	
	/**
	 * 管理端根据条件查询数据(不带分页)
	 * @param q
	 * @return
	 */
	public List<ThemeOrder> queryAllForManagePage(Query q);
	
	/**
	 * 管理端根据条件查询数据
	 * @param q
	 * @return
	 */
	public List<ThemeOrder> pageListForManagePage(Query q);
	
	/**
	 * 管理端根据条件查询数据条件
	 * @param q
	 * @return
	 */
	public Long countForManagePage(Query q);
	
	/**
	 * 根据门店编号和排课课程编号统计该课程预约订单数量
	 */
	public List<ThemeOrder> statThemeOrderSize(Query q);
	/**
	 * app端查询会员预约的主题馆课程size，默认查询4条
	 */
	public List<ThemeOrder> queryAppThemeOrder(Query q);
	/**
	 * 统计属于该课程已预约、已支付的预约人数
	 */
	public long statCourseAppoinNumber(String arrayCourseNo);
	/**
	 * 统计属于该课程已预约排队、已支付的预约人数
	 */
	public long statCourseLineNumber(String arrayCourseNo);
	/**
	 * 根据排课编号、订单状态按倒序查询正在排队的订单
	 */
	public List<ThemeOrder> queryLineThemeOrder(Query q);
	
	public  Long countLinePage(Query q);
	/**
	 * 根据会员编号查询订单状态为已预约or进行中的数据
	 */
	public List<ThemeOrder> queryOrderDataByMemberNo(String memberNo);
	/**
	 * 按天统计主题馆订单数据
	 */
	public List<ThemeOrderStatisticVo> statDaysThemeOrderList(String beginTime, String endTime);
	/**
	 * 按月统计主题馆订单数据
	 */
	public List<ThemeOrderStatisticVo> statMonthsThemeOrderList(String beginTime, String endTime);
	/**
	 * 按年统计主题馆订单数据
	 */
	public List<ThemeOrderStatisticVo> statYearsThemeOrderList(String beginTime, String endTime);
}