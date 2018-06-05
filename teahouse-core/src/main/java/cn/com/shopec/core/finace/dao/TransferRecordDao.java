package cn.com.shopec.core.finace.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.finace.model.TransferRecord;

/**
 * 转账记录表 数据库处理类
 */
public interface TransferRecordDao extends BaseDao<TransferRecord,String> {

	TransferRecord getMemberLastTransferRecord(String memberNo);
	
}
