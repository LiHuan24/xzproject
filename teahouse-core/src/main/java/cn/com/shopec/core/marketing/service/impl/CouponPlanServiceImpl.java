package cn.com.shopec.core.marketing.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.common.MarketingContant;
import cn.com.shopec.core.marketing.dao.CouponPlanDao;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.service.CouponPlanService;

/**
 * 优惠券表 服务实现类
 */
@Service
public class CouponPlanServiceImpl implements CouponPlanService {

	private static final Log log = LogFactory.getLog(CouponPlanServiceImpl.class);

	@Resource
	private CouponPlanDao couponPlanDao;

	/**
	 * 根据查询条件，查询并返回CouponPlan的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<CouponPlan> getCouponPlanList(Query q) {
		List<CouponPlan> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = couponPlanDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CouponPlan>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CouponPlan的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public PageFinder<CouponPlan> getCouponPlanPagedList(Query q) {
		PageFinder<CouponPlan> page = new PageFinder<CouponPlan>();
		List<CouponPlan> list = null;
		long rowCount = 0L;
		try {
			// 调用dao查询满足条件的分页数据
			list = couponPlanDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = couponPlanDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CouponPlan>(0) : list;
		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		return page;
	}

	/**
	 * 根据主键，查询并返回一个CouponPlan对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public CouponPlan getCouponPlan(String id) {
		CouponPlan obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = couponPlanDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CouponPlan对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public List<CouponPlan> getCouponPlanByIds(String[] ids) {
		List<CouponPlan> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = couponPlanDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CouponPlan>(0) : list;
		return list;
	}

	/**
	 * 新增一条CouponPlan记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param CouponPlan
	 *            新增的CouponPlan数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	@Override
	public ResultInfo<CouponPlan> addCouponPlan(CouponPlan couponPlan, Operator operator) {
		ResultInfo<CouponPlan> resultInfo = new ResultInfo<CouponPlan>();

		if (couponPlan == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " couponPlan = " + couponPlan);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (couponPlan.getPlanNo() == null) {
					couponPlan.setPlanNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					couponPlan.setOperatorType(operator.getOperatorType());
					couponPlan.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				couponPlan.setCreateTime(now);
				couponPlan.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(couponPlan);

				// 调用Dao执行插入操作
				couponPlanDao.add(couponPlan);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(couponPlan);
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
	 * 根据主键，更新一条CouponPlan（普通）
	 * 
	 * @param CouponPlan
	 *            更新CouponPlan数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	@Override
	public ResultInfo<CouponPlan> update(CouponPlan couponPlan, Operator operator) {
		ResultInfo<CouponPlan> resultInfo = new ResultInfo<CouponPlan>();

		if (couponPlan == null || couponPlan.getPlanNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " couponPlan = " + couponPlan);
		} else {
			try {
				//设置更新时必须变更的值
				fillUpdateValues(couponPlan, operator);
				// 调用Dao执行更新操作，并判断更新语句执行结果
				if (couponPlanDao.update(couponPlan) > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(couponPlan);
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
	 * 根据主键，更新一条CouponPlan（业务级别）
	 * 
	 * @param CouponPlan
	 *            更新CouponPlan数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	@Override
	public ResultInfo<CouponPlan> updateForBusiness(CouponPlan couponPlan, Operator operator) {
		ResultInfo<CouponPlan> resultInfo = new ResultInfo<CouponPlan>();

		if (couponPlan == null || couponPlan.getPlanNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " couponPlan = " + couponPlan);
		} else {
			try {
				CouponPlan couponPlanTemp = getCouponPlan(couponPlan.getPlanNo());
				if (couponPlanTemp != null) {
					//设置更新时必须变更的值
					fillUpdateValues(couponPlan, operator);
					
					// 调用Dao执行更新操作，并判断更新语句执行结果
					int count= couponPlanDao.updateForBusiness(couponPlan);//
					if (count > 0) {
						resultInfo.setCode(Constant.SECCUESS);
					} else {
						resultInfo.setCode(Constant.FAIL);
					}
				} else {
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg(MarketingContant.COUPON_NO_EXISTENCE);// CouponPlan不存在
				}
				resultInfo.setData(couponPlan);
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
	 * 为CouponPlan对象设置更新时必要变更的值
	 * 
	 * @param obj
	 */
	@Override
	public void fillUpdateValues(CouponPlan couponPlan, Operator operator) {
		if (couponPlan != null) {
			// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
			if (operator != null) {
				couponPlan.setOperatorType(operator.getOperatorType());
				couponPlan.setOperatorId(operator.getOperatorId());
			}
			if (couponPlan.getAvailableTime2() != null) {
				String dateString = ECDateUtils.formatDate(couponPlan.getAvailableTime2(),ECDateUtils.Format_Date);
				try {
					couponPlan.setAvailableTime2( ECDateUtils.parseTime(dateString +" 23:59:59"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			// 设置更新时间为当前时间
			couponPlan.setUpdateTime(new Date());
		}
	}
	
	/**
	 * 根据主键，删除特定的CouponPlan记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CouponPlan> delCouponPlan(String id, Operator operator) {
		ResultInfo<CouponPlan> resultInfo = new ResultInfo<CouponPlan>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行更新操作，并判断语句执行结果
			int count = couponPlanDao.delete(id);
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
	 * 为CouponPlan对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CouponPlan obj) {
		if (obj != null) {
			if (obj.getIsAvailable() == null) {
				obj.setIsAvailable(0);
			}
			if (obj.getCensorStatus() == null) {
				obj.setCensorStatus(0);
			}
			if (obj.getExistingQuantity() == null) {
				obj.setExistingQuantity(0);
			}
			if (obj.getCensorStatus() == null) {
				obj.setCensorStatus(0);
			}
		}
	}

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK() {
		return String.valueOf(new Date().getTime() * 1000000 + new Random().nextInt(1000000));
	}

}
