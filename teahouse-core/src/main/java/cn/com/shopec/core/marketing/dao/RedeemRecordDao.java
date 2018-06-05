package cn.com.shopec.core.marketing.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.marketing.model.RedeemRecord;

/**
 * 兑换码记录表 数据库处理类
 */
public interface RedeemRecordDao extends BaseDao<RedeemRecord,String> {
	
	/**
	 * 得到会员某兑换码的记录
	 * @param redCode
	 * @param memberNo
	 * @return
	 */
	List<RedeemRecord> getForRedeem(String redCode, String memberNo);

}
