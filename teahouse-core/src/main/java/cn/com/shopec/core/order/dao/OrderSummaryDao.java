package cn.com.shopec.core.order.dao;

import java.io.Serializable;
import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.OrderSummary;

/**
 * 订单汇总表 数据库处理类
 */
public interface OrderSummaryDao  extends BaseDao<OrderSummary, Serializable>{
	
	/**
	 * 查询主题订单
	 * @param orderSummary
	 * @return
	 */
	List<OrderSummary> queryThemeOrderList(Query query);
	
	/**
	 * 查询社区定的
	 * @param query
	 * @return
	 */
	List<OrderSummary> queryCommunityOrderList(Query query);
	
}