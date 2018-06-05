package cn.com.shopec.core.finace.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.RechargeOrder;

/**
 * 充值订单表 数据库处理类
 */
public interface RechargeOrderDao extends BaseDao<RechargeOrder, String> {

	/**
	 * 管理端导出查询
	 * @param q
	 * @return
	 */
	List<RechargeOrder> queryAllForExport(Query q);

}
