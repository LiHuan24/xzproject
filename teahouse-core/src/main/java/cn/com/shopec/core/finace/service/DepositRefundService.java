package cn.com.shopec.core.finace.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.PaymentRecord;
import cn.com.shopec.core.finace.model.TransToAccount;

/**
 * 押金退款表 服务接口类
 */
public interface DepositRefundService extends BaseService {

	/**
	 * 根据查询条件，查询并返回DepositRefund的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<DepositRefund> getDepositRefundList(Query q);

	/**
	 * 根据查询条件，分页查询并返回DepositRefund的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<DepositRefund> getDepositRefundPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个DepositRefund对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public DepositRefund getDepositRefund(String id);

	/**
	 * 根据主键数组，查询并返回一组DepositRefund对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<DepositRefund> getDepositRefundByIds(String[] ids);

	/**
	 * 根据主键，删除特定的DepositRefund记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<DepositRefund> delDepositRefund(String id, Operator operator);

	/**
	 * 新增一条DepositRefund记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param depositRefund
	 *            新增的DepositRefund数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<DepositRefund> addDepositRefund(DepositRefund depositRefund, Operator operator);

	/**
	 * 根据主键，更新一条DepositRefund记录
	 * 
	 * @param depositRefund
	 *            更新的DepositRefund数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<DepositRefund> updateDepositRefund(DepositRefund depositRefund, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为DepositRefund对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(DepositRefund obj);

	/**
	 * 根据模糊查询条件，查询并返回DepositRefund的列表（管理端用）
	 * @param query
	 * @return
	 */
	public List<DepositRefund> getDepositRefundListForManagePage(Query query);

	/**
	 * 线下退款
	 * @param dRefund
	 * @return
	 */
	public ResultInfo<DepositRefund> refundDepositByOffline(DepositRefund dRefund);

	/**
	 * 微信退款
	 * @param dRefund
	 * @param dOrder
	 * @return
	 */
	public ResultInfo<DepositRefund> refundDepositByWX(DepositRefund dRefund, DepositOrder dOrder);

	/**
	 * 微信转账
	 * @param dRefund
	 * @param dOrder
	 * @param paymentRecord
	 * @param request
	 * @return
	 */
	public ResultInfo<DepositRefund> transAccountByWX(DepositRefund dRefund, DepositOrder dOrder,
			PaymentRecord paymentRecord, HttpServletRequest request);
	
	/**
	 * 根据押金单号查询当前订单是否支持退款（支付宝）
	 * @param depositRefundNo
	 * @param tradeInfo
	 * @return
	 */
	public ResultInfo<String> checkSignOrderByAlipay(String depositRefundNo, Map<String, String> tradeInfo);

	/**
	 * 支付宝退款
	 * @param depositRefundNo
	 * @param response
	 * @param remark
	 * @return
	 * @throws Exception
	 */
	public ResultInfo<String> refundDepositByAlipay(String depositRefundNo, HttpServletResponse response,
			String remark) throws Exception;
	
	/**
	 * 支付宝转账
	 * @param depositRefundNo
	 * @param remark
	 * @param string
	 * @param string2
	 * @param operator
	 * @return
	 */
	ResultInfo<TransToAccount> transAccountByAlipay(String depositRefundNo, String remark, String payeeId,
			String payeeAccount, Operator operator);
	/**
	 * 支付宝退款回调处理
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	void alipayRefundCallback(HttpServletRequest request, HttpServletResponse response) throws Exception;


}
