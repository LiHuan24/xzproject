package cn.com.shopec.core.equipment.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.EquipmentBrand;

/**
 * 设备品牌表 服务接口类
 */
public interface EquipmentBrandService extends BaseService {

	/**
	 * 根据查询条件，查询并返回EquipmentBrand的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<EquipmentBrand> getEquipmentBrandList(Query q);
	/**
	 * 编辑验证设备品牌名称不唯一
	 * 查询除过当前不属于该设备编号的数据
	 */
	public List<EquipmentBrand> queryUniqueBrandList(String brandNo);
	
	/**
	 * 根据查询条件，分页查询并返回EquipmentBrand的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<EquipmentBrand> getEquipmentBrandPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个EquipmentBrand对象
	 * @param id 主键id
	 * @return
	 */
	public EquipmentBrand getEquipmentBrand(String id);

	/**
	 * 根据主键数组，查询并返回一组EquipmentBrand对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<EquipmentBrand> getEquipmentBrandByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的EquipmentBrand记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<EquipmentBrand> delEquipmentBrand(String id, Operator operator);
	
	/**
	 * 新增一条EquipmentBrand记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param equipmentBrand 新增的EquipmentBrand数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<EquipmentBrand> addEquipmentBrand(EquipmentBrand equipmentBrand, Operator operator);
	
	/**
	 * 根据主键，更新一条EquipmentBrand记录
	 * @param equipmentBrand 更新的EquipmentBrand数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<EquipmentBrand> updateEquipmentBrand(EquipmentBrand equipmentBrand, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为EquipmentBrand对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(EquipmentBrand obj);
		
}
