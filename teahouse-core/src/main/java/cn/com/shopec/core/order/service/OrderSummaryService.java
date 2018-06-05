package cn.com.shopec.core.order.service;

import java.util.List;

import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.OrderSummary;

/**
 * 社区订单 服务接口类
 */
public interface OrderSummaryService extends BaseService {

	/**
	 * 查询主题订单汇总信息
	 * @param orderSummary
	 * @return
	 */
	List<OrderSummary> queryThemeOrderList(Query q);
	
	/**
	 * 查询社区订单汇总信息
	 * @param orderSummary
	 * @return
	 */

	List<OrderSummary> queryCommunityOrderList(Query q);
	
}
