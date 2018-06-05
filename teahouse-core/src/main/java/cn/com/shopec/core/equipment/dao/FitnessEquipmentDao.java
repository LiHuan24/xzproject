package cn.com.shopec.core.equipment.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.equipment.model.FitnessEquipment;

/**
 * FitnessEquipment 数据库处理类
 */
public interface FitnessEquipmentDao extends BaseDao<FitnessEquipment, String> {
	/**
	 * 根据门店编号查询健身设备类别
	 */
	public List<String> getFeBysortNo(String storeNo);

	/**
	 * 根据平台设备唯一ID查询设备
	 */
	public FitnessEquipment getFeByDevId(String devId);

}
