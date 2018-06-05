package cn.com.shopec.core.equipment.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.AuxiliaryEquipment;

/**
 * 辅助设备表 服务接口类
 */
public interface AuxiliaryEquipmentService extends BaseService {

	/**
	 * 根据查询条件，查询并返回AuxiliaryEquipment的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<AuxiliaryEquipment> getAuxiliaryEquipmentList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回AuxiliaryEquipment的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<AuxiliaryEquipment> getAuxiliaryEquipmentPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个AuxiliaryEquipment对象
	 * @param id 主键id
	 * @return
	 */
	public AuxiliaryEquipment getAuxiliaryEquipment(String id);

	/**
	 * 根据主键数组，查询并返回一组AuxiliaryEquipment对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<AuxiliaryEquipment> getAuxiliaryEquipmentByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的AuxiliaryEquipment记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<AuxiliaryEquipment> delAuxiliaryEquipment(String id, Operator operator);
	
	/**
	 * 新增一条AuxiliaryEquipment记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param auxiliaryEquipment 新增的AuxiliaryEquipment数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<AuxiliaryEquipment> addAuxiliaryEquipment(AuxiliaryEquipment auxiliaryEquipment, Operator operator);
	
	/**
	 * 根据主键，更新一条AuxiliaryEquipment记录
	 * @param auxiliaryEquipment 更新的AuxiliaryEquipment数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<AuxiliaryEquipment> updateAuxiliaryEquipment(AuxiliaryEquipment auxiliaryEquipment, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为AuxiliaryEquipment对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(AuxiliaryEquipment obj);
		
}
