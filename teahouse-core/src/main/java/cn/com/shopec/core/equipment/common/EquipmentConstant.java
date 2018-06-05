package cn.com.shopec.core.equipment.common;
/**
 * 设备管理常量配置类
 * @author LiHuan
 * Date 2017年10月23日 上午11:49:07
 */
public class EquipmentConstant {
	/**
	 * 设备类型
	 */
	/** 健身设备 **/
	public static final int FITNESS_EQUIPMENT = 0;
	/** 辅助设备 **/
	public static final int ASSIST_EQUIPMENT = 1;
	
	/**
	 * 健身设备上下线状态
	 */
	/** 下线 **/
	public static final int EQUIPMENT_OFF_STATUS = 0;
	/** 上线 **/
	public static final int EQUIPMENT_ON_STATUS = 1;
	
	/**
	 * 设备报修数据是否已转成设备维修记录状态
	 */
	/** 未转 **/
	public static final int TURN_STATUS_NO = 0;
	/** 已转  **/
	public static final int TURN_STATUS_YES = 1;
}
