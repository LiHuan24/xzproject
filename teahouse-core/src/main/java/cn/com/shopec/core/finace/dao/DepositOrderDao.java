package cn.com.shopec.core.finace.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.finace.model.DepositOrder;

/**
 * 押金支付订单表 数据库处理类
 */
public interface DepositOrderDao extends BaseDao<DepositOrder, String> {

	//根据会员编号查询当前可用的押金支付数据
	DepositOrder getAvailabilityDepositOrder(String memberNo);
}
