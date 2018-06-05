package cn.com.shopec.core.equipment.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.equipment.model.EquipmentBrand;

/**
 * 设备品牌表 数据库处理类
 */
public interface EquipmentBrandDao extends BaseDao<EquipmentBrand,String> {
	/**
	 * 编辑验证设备品牌名称不唯一
	 * 查询除过当前不属于该设备编号的数据
	 */
	List<EquipmentBrand> queryUniqueBrandList(String brandNo);
}
