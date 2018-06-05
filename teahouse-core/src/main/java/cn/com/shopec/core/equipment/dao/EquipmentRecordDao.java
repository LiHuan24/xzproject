package cn.com.shopec.core.equipment.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.EquipmentRecord;

/**
 * 健身设备使用记录表 数据库处理类
 */
public interface EquipmentRecordDao extends BaseDao<EquipmentRecord, String> {

	/**
	 * 查询分页查询社区订单下的设备使用记录
	 * 
	 * @param q
	 * @return
	 */
	List<EquipmentRecord> pageListByOrderNo(Query q);

	/**
	 * 查询分页查询社区订单下的设备使用记录总数量
	 * 
	 * @param q
	 * @return
	 */
	long countByOrderNo(Query q);

	/**
	 * 查看会员最近一次的设备记录
	 */
	public EquipmentRecord getErNs(String memberNo, String fitnessEquipmentNo);

	/**
	 * 根据订单编号和使用状态查找健身记录
	 */
	public EquipmentRecord getErMs(String orderNo, Integer useStatus);

}
