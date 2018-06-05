package cn.com.shopec.core.order.dao;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.CommunityOrder;
import cn.com.shopec.core.order.vo.CommunityOrderStatisticVo;
import cn.com.shopec.core.order.vo.OrderDetailsVo;
import cn.com.shopec.core.order.vo.OrderListVo;

/**
 * 社区订单表 数据库处理类
 */
public interface CommunityOrderDao extends BaseDao<CommunityOrder, Serializable> {

	/**
	 * 管理端根据条件查询数据(不带分页)
	 * 
	 * @param q
	 * @return
	 */
	public List<CommunityOrder> queryAllForManagePage(Query q);

	/**
	 * 管理端根据条件查询数据
	 * 
	 * @param q
	 * @return
	 */
	public List<CommunityOrder> pageListForManagePage(Query q);

	/**
	 * 管理端根据条件查询数据条件
	 * 
	 * @param q
	 * @return
	 */
	public Long countForManagePage(Query q);

	/**
	 * app订单列表
	 */
	public List<OrderListVo> getOrderList(Query q);

	/**
	 * app订单详情
	 */
	public OrderDetailsVo getOrderDetails(String orderNo);
	
	/**
	 * 按天统计社区馆订单数据
	 */
	public List<CommunityOrderStatisticVo> statDaysCommunityOrderList(String beginTime, String endTime);
	
	/**
	 * 按月统计社区馆订单数据
	 */
	public List<CommunityOrderStatisticVo> statMonthsCommunityOrderList(String beginTime, String endTime);
	
	/**
	 * 按年统计社区馆订单数据
	 */
	public List<CommunityOrderStatisticVo> statYearsCommunityOrderList(String beginTime, String endTime);
}