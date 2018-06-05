package cn.com.shopec.core.finace.service.impl;

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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.dao.DepositOrderDao;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.service.DepositOrderService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;

/**
 * 押金支付订单表 服务实现类
 */
@Service
public class DepositOrderServiceImpl implements DepositOrderService {

	private static final Log log = LogFactory.getLog(DepositOrderServiceImpl.class);

	@Resource
	private DepositOrderDao depositOrderDao;
	@Resource
	private PrimarykeyService primarykeyService;

	/**
	 * 根据查询条件，查询并返回DepositOrder的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DepositOrder> getDepositOrderList(Query q) {
		List<DepositOrder> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = depositOrderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DepositOrder>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回DepositOrder的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<DepositOrder> getDepositOrderPagedList(Query q) {
		PageFinder<DepositOrder> page = new PageFinder<DepositOrder>();

		List<DepositOrder> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = depositOrderDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = depositOrderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DepositOrder>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个DepositOrder对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public DepositOrder getDepositOrder(String id) {
		DepositOrder obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = depositOrderDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组DepositOrder对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<DepositOrder> getDepositOrderByIds(String[] ids) {
		List<DepositOrder> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = depositOrderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<DepositOrder>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的DepositOrder记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DepositOrder> delDepositOrder(String id, Operator operator) {
		ResultInfo<DepositOrder> resultInfo = new ResultInfo<DepositOrder>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = depositOrderDao.delete(id);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_DATA_NOT_EXISTS);
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
	 * 新增一条DepositOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param depositOrder
	 *            新增的DepositOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DepositOrder> addDepositOrder(DepositOrder depositOrder, Operator operator) {
		ResultInfo<DepositOrder> resultInfo = new ResultInfo<DepositOrder>();

		if (depositOrder == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " depositOrder = " + depositOrder);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (depositOrder.getDepositOrderNo() == null) {
					depositOrder.setDepositOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					depositOrder.setOperatorType(operator.getOperatorType());
					depositOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				depositOrder.setCreateTime(now);
				depositOrder.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(depositOrder);

				// 调用Dao执行插入操作
				depositOrderDao.add(depositOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(depositOrder);
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
	 * 根据主键，更新一条DepositOrder记录
	 * 
	 * @param depositOrder
	 *            更新的DepositOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<DepositOrder> updateDepositOrder(DepositOrder depositOrder, Operator operator) {
		ResultInfo<DepositOrder> resultInfo = new ResultInfo<DepositOrder>();

		if (depositOrder == null || depositOrder.getDepositOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " depositOrder = " + depositOrder);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					depositOrder.setOperatorType(operator.getOperatorType());
					depositOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				depositOrder.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = depositOrderDao.update(depositOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(depositOrder);
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
		return "Y" + primarykeyService.getValueByBizType(PrimarykeyConstant.themeOrder);
	}

	/**
	 * 为DepositOrder对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(DepositOrder obj) {
		if (obj != null) {
			if (obj.getPayStatus() == null) {
				obj.setPayStatus(0);
			}
			if (obj.getIsAvailable() == null) {
				obj.setIsAvailable(0);
			}
		}
	}

}
