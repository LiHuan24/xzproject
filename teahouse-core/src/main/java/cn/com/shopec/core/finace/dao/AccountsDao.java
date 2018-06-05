package cn.com.shopec.core.finace.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.finace.model.Accounts;

/**
 * 记账表 数据库处理类
 */
public interface AccountsDao extends BaseDao<Accounts, String> {

	Accounts getAccountsByBizType(String bizNo, Integer bizeType);

}
