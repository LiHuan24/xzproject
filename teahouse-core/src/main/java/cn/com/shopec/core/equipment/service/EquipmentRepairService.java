package cn.com.shopec.core.equipment.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.EquipmentRepair;

/**
 * 设备维修记录表 服务接口类
 */
public interface EquipmentRepairService extends BaseService {

	/**
	 * 根据查询条件，查询并返回EquipmentRepair的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<EquipmentRepair> getEquipmentRepairList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回EquipmentRepair的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<EquipmentRepair> getEquipmentRepairPagedList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回EquipmentRepair的分页结果
	 * 联合查询user表
	 * @param q 查询条件
	 */
	public PageFinder<EquipmentRepair> pageEquipmentRepairList(Query q);
	
	/**
	 * 根据主键，查询并返回一个EquipmentRepair对象
	 * @param id 主键id
	 * @return
	 */
	public EquipmentRepair getEquipmentRepair(String id);

	/**
	 * 根据主键数组，查询并返回一组EquipmentRepair对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<EquipmentRepair> getEquipmentRepairByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的EquipmentRepair记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<EquipmentRepair> delEquipmentRepair(String id, Operator operator);
	
	/**
	 * 新增一条EquipmentRepair记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param equipmentRepair 新增的EquipmentRepair数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<EquipmentRepair> addEquipmentRepair(EquipmentRepair equipmentRepair, Operator operator);
	
	/**
	 * 根据主键，更新一条EquipmentRepair记录
	 * @param equipmentRepair 更新的EquipmentRepair数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<EquipmentRepair> updateEquipmentRepair(EquipmentRepair equipmentRepair, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为EquipmentRepair对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(EquipmentRepair obj);
		
}
