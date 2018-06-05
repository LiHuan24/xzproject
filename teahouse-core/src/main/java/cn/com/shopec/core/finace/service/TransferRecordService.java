package cn.com.shopec.core.finace.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.TransferRecord;

/**
 * 转账记录表 服务接口类
 */
public interface TransferRecordService extends BaseService {

	/**
	 * 根据查询条件，查询并返回TransferRecord的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<TransferRecord> getTransferRecordList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回TransferRecord的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<TransferRecord> getTransferRecordPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个TransferRecord对象
	 * @param id 主键id
	 * @return
	 */
	public TransferRecord getTransferRecord(String id);

	/**
	 * 根据主键数组，查询并返回一组TransferRecord对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<TransferRecord> getTransferRecordByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的TransferRecord记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<TransferRecord> delTransferRecord(String id, Operator operator);
	
	/**
	 * 新增一条TransferRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param transferRecord 新增的TransferRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<TransferRecord> addTransferRecord(TransferRecord transferRecord, Operator operator);
	
	/**
	 * 根据主键，更新一条TransferRecord记录
	 * @param transferRecord 更新的TransferRecord数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<TransferRecord> updateTransferRecord(TransferRecord transferRecord, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为TransferRecord对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(TransferRecord obj);
	
	/**
	 * 取消余额提现
	 * @param memmberNo
	 */
	public ResultInfo<TransferRecord> cancelBalanceTransfer(TransferRecord transferRecord, boolean isUserOperate);

	/**
	 * 转账
	 * @param transferNo
	 * @param operator
	 * @return
	 */
	public ResultInfo<TransferRecord> transfer(String transferNo, Operator operator);

	/**
	 * 支付宝转账
	 * @param transferRecord 该参数不能为null
	 * @param operator
	 * @return
	 */
	ResultInfo<TransferRecord> alipayTransfer(TransferRecord transferRecord,Operator operator);

	/**
	 * 新增转账记录，并且减去会员余额
	 * @param transferRecord
	 * @param operator
	 * @return
	 */
	public ResultInfo<TransferRecord> addTransferRecordForApplyBalance(TransferRecord transferRecord,
			Operator operator);

	/**
	 * 审核提现
	 * @param transferRecord
	 * @param operator
	 * @return
	 */
	public ResultInfo<TransferRecord> cencorTransferRecord(TransferRecord transferRecord, Operator operator);

}
