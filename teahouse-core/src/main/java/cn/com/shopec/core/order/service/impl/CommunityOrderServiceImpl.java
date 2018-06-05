package cn.com.shopec.core.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.hardware.HardwareUtils;
import cn.com.shopec.common.hardware.HwData;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.dao.EquipmentRecordDao;
import cn.com.shopec.core.equipment.model.EquipmentRecord;
import cn.com.shopec.core.equipment.model.FitnessEquipment;
import cn.com.shopec.core.order.dao.CommunityOrderDao;
import cn.com.shopec.core.order.model.CommunityOrder;
import cn.com.shopec.core.order.service.CommunityOrderService;
import cn.com.shopec.core.order.vo.CommunityOrderStatisticVo;
import cn.com.shopec.core.order.vo.OrderDetailsVo;
import cn.com.shopec.core.order.vo.OrderListVo;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;
import net.sf.json.JSONObject;

/**
 * 社区订单表 服务实现类
 */
@Service
public class CommunityOrderServiceImpl implements CommunityOrderService {

	private static final Log log = LogFactory.getLog(CommunityOrderServiceImpl.class);

	@Resource
	private CommunityOrderDao communityOrderDao;

	@Resource
	private PrimarykeyService primarykeyService;
	@Resource
	private EquipmentRecordDao equipmentRecordDao;

	/**
	 * 根据查询条件，查询并返回CommunityOrder的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CommunityOrder> getCommunityOrderList(Query q) {
		List<CommunityOrder> list = null;
		try {
			// 已删除的不查出
			CommunityOrder communityOrder = (CommunityOrder) q.getQ();
			if (communityOrder != null) {
				communityOrder.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = communityOrderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CommunityOrder>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，查询并返回CommunityOrder的列表(管理端用)
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CommunityOrder> getCommunityOrderListForManagePage(Query q) {
		List<CommunityOrder> list = null;
		try {
			// 已删除的不查出
			CommunityOrder communityOrder = (CommunityOrder) q.getQ();
			if (communityOrder != null) {
				communityOrder.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = communityOrderDao.queryAllForManagePage(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CommunityOrder>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CommunityOrder的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CommunityOrder> getCommunityOrderPagedList(Query q) {
		PageFinder<CommunityOrder> page = new PageFinder<CommunityOrder>();

		List<CommunityOrder> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			CommunityOrder communityOrder = (CommunityOrder) q.getQ();
			if (communityOrder != null) {
				communityOrder.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = communityOrderDao.pageListForManagePage(q);
			// 调用dao统计满足条件的记录总数
			rowCount = communityOrderDao.countForManagePage(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CommunityOrder>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个CommunityOrder对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CommunityOrder getCommunityOrder(String id) {
		CommunityOrder obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = communityOrderDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CommunityOrder对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CommunityOrder> getCommunityOrderByIds(String[] ids) {
		List<CommunityOrder> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = communityOrderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CommunityOrder>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的CommunityOrder记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CommunityOrder> delCommunityOrder(String id, Operator operator) {
		ResultInfo<CommunityOrder> resultInfo = new ResultInfo<CommunityOrder>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行更新操作，并判断语句执行结果
			int count = communityOrderDao.delete(id);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条CommunityOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param communityOrder
	 *            新增的CommunityOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CommunityOrder> addCommunityOrder(CommunityOrder communityOrder, Operator operator) {
		ResultInfo<CommunityOrder> resultInfo = new ResultInfo<CommunityOrder>();

		if (communityOrder == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " communityOrder = " + communityOrder);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (communityOrder.getCommunityOrderNo() == null) {
					communityOrder.setCommunityOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					communityOrder.setOperatorType(operator.getOperatorType());
					communityOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				communityOrder.setCreateTime(now);
				communityOrder.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(communityOrder);

				// 调用Dao执行插入操作
				communityOrderDao.add(communityOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(communityOrder);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	/**
	 * 根据主键，更新一条CommunityOrder记录
	 * 
	 * @param communityOrder
	 *            更新的CommunityOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CommunityOrder> updateCommunityOrder(CommunityOrder communityOrder, Operator operator) {
		ResultInfo<CommunityOrder> resultInfo = new ResultInfo<CommunityOrder>();

		if (communityOrder == null || communityOrder.getCommunityOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " communityOrder = " + communityOrder);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					communityOrder.setOperatorType(operator.getOperatorType());
					communityOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				communityOrder.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = communityOrderDao.update(communityOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(communityOrder);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK() {
		return "S" + primarykeyService.getValueByBizType(PrimarykeyConstant.communityOrder);
	}

	/**
	 * 为CommunityOrder对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CommunityOrder obj) {
		if (obj != null) {
			if (obj.getOrderStatus() == null) {
				obj.setOrderStatus(0);
			}
			if (obj.getPayStatus() == null) {
				obj.setPayStatus(0);
			}
			if (obj.getPayStatus() == null) {
				obj.setPaymentMethod(0);
			}
		}
	}

	@Transactional
	public ResultInfo<CommunityOrder> addOrder(CommunityOrder order, JSONObject json, String url, FitnessEquipment fe) {
		ResultInfo<CommunityOrder> resultInfo = new ResultInfo<CommunityOrder>();

		if (order == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " communityOrder = " + order);
		} else {
			try {

				ResultInfo<HwData> info = HardwareUtils.post(json, url);
				if (info.getData().getCode().equals("0")) {
					// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
					if (order.getCommunityOrderNo() == null) {
						order.setCommunityOrderNo(this.generatePK());
					}

					// 设置创建时间和更新时间为当前时间
					Date now = new Date();
					order.setCreateTime(now);
					order.setUpdateTime(now);

					// 填充默认值
					this.fillDefaultValues(order);

					// 调用Dao执行插入操作
					communityOrderDao.add(order);
					// 增加设备使用记录
					EquipmentRecord r = new EquipmentRecord();
					r.setRecordId(String.valueOf(System.nanoTime()));
					r.setOrderNo(order.getCommunityOrderNo());
					r.setCityId(order.getCityId());
					r.setCityName(order.getCityName());
					r.setStoreNo(order.getStoreNo());
					r.setStoreName(order.getStoreName());
					r.setMemberNo(order.getMemberNo());
					r.setMobilePhone(order.getMobilePhone());
					r.setFitnessEquipmentNo(fe.getFitnessEquipmentNo());
					r.setSortName(fe.getSortName());
					r.setStartTime(new Date());
					r.setUseStatus(0);
					r.setUseType(order.getConsumeType());
					r.setIsDeleted(0);
					equipmentRecordDao.add(r);

					resultInfo.setCode(Constant.SECCUESS);
					resultInfo.setData(order);

				} else {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(info.getData().getDesc());
				}

			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}

		return resultInfo;
	}

	@Override
	public List<OrderListVo> getOrderList(Query q) {
		List<OrderListVo> list = null;
		try {

			// 直接调用Dao方法进行查询
			list = communityOrderDao.getOrderList(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<OrderListVo>(0) : list;
		return list;
	}

	@Override
	public OrderDetailsVo getOrderDetails(String orderNo) {
		return communityOrderDao.getOrderDetails(orderNo);
	}

	@Override
	public List<CommunityOrderStatisticVo> statDaysCommunityOrderList(String beginTime, String endTime) {
		List<CommunityOrderStatisticVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = communityOrderDao.statDaysCommunityOrderList(beginTime,endTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CommunityOrderStatisticVo>(0) : list;
		return list;
	}

	@Override
	public List<CommunityOrderStatisticVo> statMonthsCommunityOrderList(String beginTime, String endTime) {
		List<CommunityOrderStatisticVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = communityOrderDao.statMonthsCommunityOrderList(beginTime,endTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CommunityOrderStatisticVo>(0) : list;
		return list;
	}

	@Override
	public List<CommunityOrderStatisticVo> statYearsCommunityOrderList(String beginTime, String endTime) {
		List<CommunityOrderStatisticVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = communityOrderDao.statYearsCommunityOrderList(beginTime,endTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CommunityOrderStatisticVo>(0) : list;
		return list;
	}
}