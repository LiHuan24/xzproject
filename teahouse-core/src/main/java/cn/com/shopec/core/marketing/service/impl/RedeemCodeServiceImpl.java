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
import cn.com.shopec.common.utils.ECNumberUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.dao.CouponDao;
import cn.com.shopec.core.marketing.dao.CouponPlanDao;
import cn.com.shopec.core.marketing.dao.RedeemCodeDao;
import cn.com.shopec.core.marketing.dao.RedeemCouponPlanDao;
import cn.com.shopec.core.marketing.dao.RedeemRecordDao;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.model.RedeemCode;
import cn.com.shopec.core.marketing.model.RedeemCouponPlan;
import cn.com.shopec.core.marketing.model.RedeemRecord;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.marketing.service.RedeemCodeService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;

/**
 * 兑换码表 服务实现类
 */
@Service
public class RedeemCodeServiceImpl implements RedeemCodeService {

	private static final Log log = LogFactory.getLog(RedeemCodeServiceImpl.class);

	@Resource
	private RedeemCodeDao redeemCodeDao;
	@Resource
	private MemberDao memberDao;
	@Resource
	private CouponService couponService;
	@Resource
	private CouponPlanDao couponPlanDao;
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private RedeemCouponPlanDao redeemCouponPlanDao;
	@Resource
	private RedeemRecordDao redeemRecordDao;
	@Resource
	private CouponDao couponDao;

	/**
	 * 根据查询条件，查询并返回RedeemCode的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RedeemCode> getRedeemCodeList(Query q) {
		List<RedeemCode> list = null;
		try {
			// 已删除的不查出
			RedeemCode redeemCode = (RedeemCode) q.getQ();
			if (redeemCode != null) {
				redeemCode.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = redeemCodeDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<RedeemCode>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回RedeemCode的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<RedeemCode> getRedeemCodePagedList(Query q) {
		PageFinder<RedeemCode> page = new PageFinder<RedeemCode>();

		List<RedeemCode> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			RedeemCode redeemCode = (RedeemCode) q.getQ();
			if (redeemCode != null) {
				redeemCode.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = redeemCodeDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = redeemCodeDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<RedeemCode>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个RedeemCode对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public RedeemCode getRedeemCode(String id) {
		RedeemCode obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = redeemCodeDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组RedeemCode对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RedeemCode> getRedeemCodeByIds(String[] ids) {
		List<RedeemCode> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = redeemCodeDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<RedeemCode>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的RedeemCode记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<RedeemCode> delRedeemCode(String id, Operator operator) {
		ResultInfo<RedeemCode> resultInfo = new ResultInfo<RedeemCode>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			RedeemCode redeemCode = new RedeemCode();
			redeemCode.setRedeemCode(id);
			redeemCode.setIsDeleted(Constant.DEL_STATE_YES);
			redeemCode.setUpdateTime(new Date());
			if (operator != null) { // 最近操作人
				redeemCode.setOperatorType(operator.getOperatorType());
				redeemCode.setOperatorId(operator.getOperatorId());
			}

			// 调用Dao执行更新操作，并判断更新语句执行结果
			int count = redeemCodeDao.update(redeemCode);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(redeemCode);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条RedeemCode记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param redeemCode
	 *            新增的RedeemCode数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<RedeemCode> addRedeemCode(RedeemCode redeemCode, Operator operator) {
		ResultInfo<RedeemCode> resultInfo = new ResultInfo<RedeemCode>();

		if (redeemCode == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " redeemCode = " + redeemCode);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (redeemCode.getRedeemCode() == null) {
					redeemCode.setRedeemCode(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					redeemCode.setOperatorType(operator.getOperatorType());
					redeemCode.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				redeemCode.setCreateTime(now);
				redeemCode.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(redeemCode);

				// 调用Dao执行插入操作
				redeemCodeDao.add(redeemCode);

				// 维护兑换券与计划的关系
				List<RedeemCouponPlan> redeemCouponPlans = redeemCode.getRedeemCouponPlans();
				if (redeemCouponPlans != null) {
					for (RedeemCouponPlan redeemCouponPlan : redeemCouponPlans) {
						redeemCouponPlan.setRedeemCode(redeemCode.getRedeemCode());
						redeemCouponPlanDao.add(redeemCouponPlan);
					}
				}

				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(redeemCode);
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
	 * 根据主键，更新一条RedeemCode记录
	 * 
	 * @param redeemCode
	 *            更新的RedeemCode数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<RedeemCode> updateRedeemCode(RedeemCode redeemCode, Operator operator) {
		ResultInfo<RedeemCode> resultInfo = new ResultInfo<RedeemCode>();

		if (redeemCode == null || redeemCode.getRedeemCode() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " redeemCode = " + redeemCode);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					redeemCode.setOperatorType(operator.getOperatorType());
					redeemCode.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				redeemCode.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = redeemCodeDao.update(redeemCode);
				if (count > 0) {
					if (redeemCodeDao.update(redeemCode) > 0) {
						// 维护兑换券与计划的关系
						List<RedeemCouponPlan> redeemCouponPlans = redeemCode.getRedeemCouponPlans();
						if (redeemCouponPlans != null) {
							redeemCouponPlanDao.deleteByRedeemCode(redeemCode.getRedeemCode());
							for (RedeemCouponPlan redeemCouponPlan : redeemCouponPlans) {
								redeemCouponPlan.setRedeemCode(redeemCode.getRedeemCode());
								redeemCouponPlanDao.add(redeemCouponPlan);
							}
						}
					}
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(redeemCode);
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
		Random random = new Random();
		String val = ECNumberUtils.to32Hex(new Date().getTime());// 当前时间
		// 参数length，表示生成几位随机数
		for (int i = 0; i < 2; i++) {
			boolean randomFlag = random.nextInt(2) % 2 == 0;
			String rn = "";
			if (randomFlag) {
				char r = (char) (random.nextInt(26) + 65);// 大写字母
				if ('O' == r) {
					r = '0';
				} else if ('Z' == r) {
					r = '2';
				} else if ('I' == r || 'L' == r) {
					r = '1';
				}
				rn = String.valueOf(r);
			} else {
				rn = String.valueOf(random.nextInt(10));
			}
			if (i == 0) {
				val = rn + val;
			} else if (i == 1) {
				val = val + rn;
			}
		}
		return val;
	}

	/**
	 * 为RedeemCode对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(RedeemCode obj) {
		if (obj != null) {
		}
		if (obj != null) {
			if (obj.getIsAvailable() == null) {
				obj.setIsAvailable(0);
			}
			if (obj.getCensorStatus() == null) {
				obj.setCensorStatus(0);
			}
			if (obj.getIsDeleted() == null) {
				obj.setIsDeleted(Constant.DEL_STATE_NO);
			}
			if (obj.getCensorStatus() == null) {
				obj.setCensorStatus(0);
			}
		}
		if (obj.getAvailableTime2() != null) {
			String dateString = ECDateUtils.formatDate(obj.getAvailableTime2(), ECDateUtils.Format_Date);
			try {
				obj.setAvailableTime2(ECDateUtils.parseTime(dateString + " 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 兌換优惠券
	 * 
	 * @param RedeemCode
	 * @param operator
	 * @return
	 */
	@Transactional
	@Override
	public ResultInfo<RedeemCode> redeemCoupon(String redeemCode, String memberNo, Operator operator) {
		ResultInfo<RedeemCode> resultInfo = new ResultInfo<RedeemCode>();

		if (redeemCode == null || memberNo == null || "".equals(redeemCode) || "".equals(memberNo)) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " redCode = " + redeemCode + " memberNo = " + memberNo);
		} else {
			try {

				Member member = memberDao.get(memberNo);
				if (member != null) {
					List<RedeemRecord> rrs = redeemRecordDao.getForRedeem(redeemCode, memberNo);
					if (rrs != null && rrs.size() > 0) {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("您已经兑换 请重新填写兑换码");
						return resultInfo;
					}
					RedeemCode rcd = redeemCodeDao.get(redeemCode);
					if (rcd != null) {
						if (new Date().getTime() < rcd.getAvailableTime1().getTime()) {
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("兑换码未到有效开始时间，暂不能兑换");
							return resultInfo;
						}

						if (new Date().getTime() > rcd.getAvailableTime2().getTime()) {
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("兑换码已经过期 请重新填写兑换码");
							return resultInfo;
						}

					}
					RedeemCode rc = new RedeemCode();
					rc.setRedeemCode(redeemCode);
					rc.setIsAvailable(1);
					rc.setAvailableTime2Start(new Date());
					Query qs = new Query(rc);
					List<RedeemCode> redeemCodes = redeemCodeDao.queryAll(qs);
					if (redeemCodes != null && redeemCodes.size() > 0) {
						// 求出该兑换码拥有哪些方案
						RedeemCouponPlan redeemCouponPlan = new RedeemCouponPlan();
						redeemCouponPlan.setRedeemCode(redeemCode);
						Query q = new Query(redeemCouponPlan);
						List<RedeemCouponPlan> redeemCouponPlans = redeemCouponPlanDao.queryAll(q);
						if (redeemCouponPlans != null && redeemCouponPlans.size() > 0) {
							// 构建优惠券
							for (RedeemCouponPlan temp : redeemCouponPlans) {
								CouponPlan couponPlan = couponPlanDao.get(temp.getPlanNo());

								// 校验优惠券方案是否启用,如果方案未启用则忽略，继续下一个方案
								if (couponPlan.getIsAvailable().intValue() == 0) {
									resultInfo.setCode(Constant.FAIL);
									resultInfo.setMsg("优惠券不可用。");
									continue;
								}

								if (couponPlan.getAvailableTime2() != null) {
									if (new Date().getTime() > couponPlan.getAvailableTime2().getTime()) {
										resultInfo.setCode(Constant.FAIL);
										resultInfo.setMsg("优惠券已过期。");
										continue;
									}
								}

								// 开始校验，计算该方案实际能够兑换的优惠券数量
								int addCounter = temp.getRedeemQutity();
								int existingQuantity = couponPlan.getExistingQuantity() == null ? 0
										: couponPlan.getExistingQuantity() + addCounter;
								// 判断方案是否有设置发放数量的最大值,如果有并且此次发放后数量超过限制数量，则开始计算实际能够方法数量
								if (couponPlan.getMaxQuantity() != null) {
									int maxQuantity = couponPlan.getMaxQuantity().intValue();
									if (existingQuantity > maxQuantity) {
										addCounter = couponPlan.getMaxQuantity() == null ? 0
												: couponPlan.getMaxQuantity() - existingQuantity;
										if (addCounter <= 0) {// 数量不够忽略，继续下一个方案
											resultInfo.setCode(Constant.FAIL);
											resultInfo.setMsg("优惠券已经发放完 下次来早点");
											continue;
										}
									}
								}

								// 更新方案表信息
								CouponPlan couponPlanTemp = new CouponPlan();
								couponPlanTemp.setExistingQuantity(existingQuantity);
								couponPlanService.fillUpdateValues(couponPlanTemp, operator);
								couponPlanTemp.setPlanNo(temp.getPlanNo());
								Date date = new Date();

								if (couponPlan.getAvailableDays() != null && couponPlan.getAvailableTime2() == null) {
									couponPlanTemp.setAvailableTime2(
											ECDateUtils.getDateAfter(date, couponPlan.getAvailableDays()));
									couponPlanTemp.setAvailableTime1(date);
									couponPlanTemp.setAvailableDays(couponPlan.getAvailableDays());
								} else {
									couponPlanTemp.setAvailableTime2(couponPlan.getAvailableTime2());
									couponPlanTemp.setAvailableTime1(couponPlan.getAvailableTime1());
								}

								couponPlanTemp.setUpdateTime(new Date());
								couponPlanDao.update(couponPlanTemp);

								// 新增优惠券表信息
								List<Coupon> coupons = new ArrayList<Coupon>();
								for (int i = 0; i < addCounter; i++) {
									Coupon couponTemp = new Coupon();
									couponTemp.setMemberNo(memberNo);
									couponTemp.setCouponNo(couponService.generatePK());
									couponTemp.setIssueTime(new Date());
									couponTemp.setIssueMethod(2);// 活动发放
									// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
									couponTemp.setPlanNo(couponPlan.getPlanNo());
									couponTemp.setTitle(couponPlan.getTitle());
									couponTemp.setCouponType(couponPlan.getCouponType());
									couponTemp.setCouponMethod(couponPlan.getCouponMethod());
									couponTemp.setDiscount(couponPlan.getDiscount());
									couponTemp.setDiscountAmount(couponPlan.getDiscountAmount());
									Date date1 = new Date();
									if (couponPlan.getAvailableDays() != null
											&& couponPlan.getAvailableTime2() == null) {
										couponTemp.setAvailableTime2(
												ECDateUtils.getDateAfter(date1, couponPlan.getAvailableDays()));
										couponTemp.setAvailableTime1(date1);
										couponTemp.setAvailableDays(couponPlan.getAvailableDays());
									} else {
										couponTemp.setAvailableTime2(couponPlan.getAvailableTime2());
										couponTemp.setAvailableTime1(couponPlan.getAvailableTime1());
									}

									couponService.fillDefaultValues(couponTemp);// 填充其他默认值
									coupons.add(couponTemp);
								}
								// 调用Dao执行插入操作
								couponDao.addBatch(coupons);

								// 添加记录
								RedeemRecord record = new RedeemRecord();
								record.setId(this.generatePK());
								record.setRedeemCode(redeemCode);
								record.setMemberNo(memberNo);
								record.setCreateTime(new Date());
								redeemRecordDao.add(record);
								resultInfo.setCode(Constant.SECCUESS);
							}
						}
					} else {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("兑换码不存在");
					}
				} else {
					resultInfo.setCode(Constant.FAIL);// 会员不存在
					resultInfo.setMsg(MemberConstant.MEMBER_NO_EXISTENCE);
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
	public List<CouponPlan> getPageForRedeemmCode(String redeemCode) {
		List<CouponPlan> list = null;
		if (redeemCode == null || redeemCode.length() == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = couponPlanDao.getPageForRedeemmCode(redeemCode);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CouponPlan>(0) : list;
		return list;
	}
}
