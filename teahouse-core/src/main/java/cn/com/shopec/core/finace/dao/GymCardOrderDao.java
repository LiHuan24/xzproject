package cn.com.shopec.core.finace.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.GymCardOrder;

/**
 * 健身卡订单表 数据库处理类
 */
public interface GymCardOrderDao extends BaseDao<GymCardOrder, String> {

	/**
	 * 管理端导出查询
	 * @param q
	 * @return
	 */
	List<GymCardOrder> queryAllForExport(Query q);
}
