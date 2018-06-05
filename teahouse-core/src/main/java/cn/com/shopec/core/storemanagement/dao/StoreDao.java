package cn.com.shopec.core.storemanagement.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.storemanagement.model.Store;

/**
 * 门店表 数据库处理类
 */
public interface StoreDao extends BaseDao<Store, String> {
	// 根据门禁编号查找门店
	public Store getStoreById(String entranceCode);

}
