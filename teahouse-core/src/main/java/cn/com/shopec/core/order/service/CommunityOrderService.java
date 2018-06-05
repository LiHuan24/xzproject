package cn.com.shopec.core.order.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.FitnessEquipment;
import cn.com.shopec.core.order.model.CommunityOrder;
import cn.com.shopec.core.order.vo.CommunityOrderStatisticVo;
import cn.com.shopec.core.order.vo.OrderDetailsVo;
import cn.com.shopec.core.order.vo.OrderListVo;
import net.sf.json.JSONObject;

/**
 * 社区订单 服务接口类
 */
public interface CommunityOrderService extends BaseService {

	/**
	 * 根据查询条件，查询并返回CommunityOrder的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CommunityOrder> getCommunityOrderList(Query q);

	/**
	 * 根据查询条件，查询并返回CommunityOrder的列表(管理端用)
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<CommunityOrder> getCommunityOrderListForManagePage(Query q);

	/**
	 * 根据查询条件，分页查询并返回CommunityOrder的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<CommunityOrder> getCommunityOrderPagedList(Query q);

	/**
	 * 根据主键，查询并返回一个CommunityOrder对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public CommunityOrder getCommunityOrder(String id);

	/**
	 * 根据主键数组，查询并返回一组CommunityOrder对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<CommunityOrder> getCommunityOrderByIds(String[] ids);

	/**
	 * 根据主键，删除特定的CommunityOrder记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CommunityOrder> delCommunityOrder(String id, Operator operator);

	/**
	 * 新增一条CommunityOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param communityOrder
	 *            新增的CommunityOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CommunityOrder> addCommunityOrder(CommunityOrder communityOrder, Operator operator);

	/**
	 * 根据主键，更新一条CommunityOrder记录
	 * 
	 * @param communityOrder
	 *            更新的CommunityOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<CommunityOrder> updateCommunityOrder(CommunityOrder communityOrder, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	String generatePK();

	/**
	 * 生成订单及启动设备
	 */
	public ResultInfo<CommunityOrder> addOrder(CommunityOrder order, JSONObject json, String url, FitnessEquipment fe);

	/**
	 * app订单列表
	 */
	public List<OrderListVo> getOrderList(Query q);

	/**
	 * app订单详情
	 */
	public OrderDetailsVo getOrderDetails(String orderNo);
	
	/**
	 * 按天统计社区馆订单数据
	 */
	public List<CommunityOrderStatisticVo> statDaysCommunityOrderList(String beginTime, String endTime);
	
	/**
	 * 按月统计社区馆订单数据
	 */
	public List<CommunityOrderStatisticVo> statMonthsCommunityOrderList(String beginTime, String endTime);
	
	/**
	 * 按年统计社区馆订单数据
	 */
	public List<CommunityOrderStatisticVo> statYearsCommunityOrderList(String beginTime, String endTime);

}
