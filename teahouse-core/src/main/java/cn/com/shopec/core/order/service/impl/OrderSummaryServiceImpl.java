package cn.com.shopec.core.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.dao.OrderSummaryDao;
import cn.com.shopec.core.order.model.OrderSummary;
import cn.com.shopec.core.order.service.OrderSummaryService;

/**
 * 订单汇总表 服务实现类
 */
@Service
public class OrderSummaryServiceImpl implements OrderSummaryService {

	private static final Log log = LogFactory.getLog(OrderSummaryServiceImpl.class);

	@Resource
	private OrderSummaryDao orderSummaryOrderDao;

	/**
	 * 查询社区订单汇总信息
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<OrderSummary> queryThemeOrderList(Query q) {
		List<OrderSummary> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = orderSummaryOrderDao.queryThemeOrderList(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderSummary>(0) : list;
		return list;
	}

	/**
	 * 查询主题订单
	 */
	@Override
	public List<OrderSummary> queryCommunityOrderList(Query q) {
		List<OrderSummary> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = orderSummaryOrderDao.queryCommunityOrderList(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderSummary>(0) : list;
		return list;
	}

	

}
