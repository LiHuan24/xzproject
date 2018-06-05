package cn.com.shopec.core.equipment.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.EquipmentRecord;

/**
 * 健身设备使用记录表 服务接口类
 */
public interface EquipmentRecordService extends BaseService {

	/**
	 * 根据查询条件，查询并返回EquipmentRecord的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<EquipmentRecord> getEquipmentRecordList(Query q);

	/**
	 * 根据查询条件，分页查询并返回EquipmentRecord的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<EquipmentRecord> getEquipmentRecordPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个EquipmentRecord对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public EquipmentRecord getEquipmentRecord(String id);

	/**
	 * 根据主键数组，查询并返回一组EquipmentRecord对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<EquipmentRecord> getEquipmentRecordByIds(String[] ids);

	/**
	 * 根据主键，删除特定的EquipmentRecord记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<EquipmentRecord> delEquipmentRecord(String id, Operator operator);

	/**
	 * 新增一条EquipmentRecord记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param equipmentRecord
	 *            新增的EquipmentRecord数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<EquipmentRecord> addEquipmentRecord(EquipmentRecord equipmentRecord, Operator operator);

	/**
	 * 根据主键，更新一条EquipmentRecord记录
	 * 
	 * @param equipmentRecord
	 *            更新的EquipmentRecord数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<EquipmentRecord> updateEquipmentRecord(EquipmentRecord equipmentRecord, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为EquipmentRecord对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(EquipmentRecord obj);

	/**
	 * 查询分页查询社区订单下的设备使用记录
	 * 
	 * @param query
	 * @return
	 */
	public PageFinder<EquipmentRecord> pageListEquipmentRecordByOrderNo(Query query);

	/**
	 * 查看会员最近一次的设备记录
	 */
	public EquipmentRecord getErNs(String memberNo, String fitnessEquipmentNo);

	/**
	 * 根据订单编号和使用状态查找健身记录
	 */
	public EquipmentRecord getErMs(String orderNo, Integer useStatus);

}
