package cn.com.shopec.core.finace.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.DepositRefund;

/**
 * 押金退款表 数据库处理类
 */
public interface DepositRefundDao extends BaseDao<DepositRefund, String> {

	/**
	 * 根据模糊查询条件，查询并返回DepositRefund的列表（管理端用）
	 * @param query
	 * @return
	 */
	List<DepositRefund> getDepositRefundListForManagePage(Query query);

}
