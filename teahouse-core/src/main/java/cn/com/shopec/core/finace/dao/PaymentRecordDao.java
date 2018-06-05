package cn.com.shopec.core.finace.dao;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.finace.model.PaymentRecord;

/**
 * 支付记录表 数据库处理类
 */
public interface PaymentRecordDao extends BaseDao<PaymentRecord, String> {

	/**
	 * 根据押金退款申请单号获取支付记录
	 * 
	 * @param depositRefundNo
	 */
	PaymentRecord getBydepositRefundNo(String depositRefundNo);

	/**
	 * 根据支付流水号获取支付记录
	 * @param paymentFlowNo
	 * @return
	 */
	PaymentRecord getPaymentRecordByFlowNo(String paymentFlowNo);

}
