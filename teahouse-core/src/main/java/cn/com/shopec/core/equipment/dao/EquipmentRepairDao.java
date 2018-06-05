package cn.com.shopec.core.equipment.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.EquipmentRepair;

/**
 * 设备维修记录表 数据库处理类
 */
public interface EquipmentRepairDao extends BaseDao<EquipmentRepair,String> {
	
	/**
	 * 设备维修记录分页查询
	 * 联合查询sys_user表，记录记录人信息
	 */
	List<EquipmentRepair> pageListAll(Query q);
	
	long countAll(Query q);
	
	
}
