package cn.com.shopec.core.member.service.impl;

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
import cn.com.shopec.common.hardware.HwUrl;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.dao.DepositStatusDao;
import cn.com.shopec.core.finace.model.DepositStatus;
import cn.com.shopec.core.member.dao.MemberBalanceDao;
import cn.com.shopec.core.member.dao.MemberDao;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.member.model.MemberStatisticVo;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.member.vo.MemberBalanceVo;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;
import net.sf.json.JSONObject;

/**
 * 会员信息表 服务实现类
 */
@Service
public class MemberServiceImpl implements MemberService {

	private static final Log log = LogFactory.getLog(MemberServiceImpl.class);

	@Resource
	private MemberDao memberDao;
	@Resource
	private PrimarykeyService primarykeyService;
	@Resource
	private MemberBalanceDao memberBalanceDao;
	@Resource
	private DepositStatusDao depositStatusDao;

	/**
	 * 根据查询条件，查询并返回Member的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Member> getMemberList(Query q) {
		List<Member> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = memberDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Member>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回MemberBalanceVo的分页结果（管理端会员页面）
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<MemberBalanceVo> getMemberBalancePageList(Query q) {
		PageFinder<MemberBalanceVo> page = new PageFinder<MemberBalanceVo>();

		List<MemberBalanceVo> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = memberDao.getMemberBalancePageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = memberDao.countNs(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberBalanceVo>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据查询条件，分页查询并返回Member的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<Member> getMemberPagedList(Query q) {
		PageFinder<Member> page = new PageFinder<Member>();

		List<Member> list = null;
		long rowCount = 0L;

		try {
			// 调用dao查询满足条件的分页数据
			list = memberDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = memberDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Member>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个Member对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Member getMember(String id) {
		Member obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = memberDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组Member对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Member> getMemberByIds(String[] ids) {
		List<Member> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = memberDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<Member>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的Member记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Member> delMember(String id, Operator operator) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行删除，并判断删除语句执行结果
			int count = memberDao.delete(id);
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
	 * 新增一条Member记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param member
	 *            新增的Member数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Member> addMember(Member member, Operator operator) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();

		if (member == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " member = " + member);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (member.getMemberNo() == null) {
					member.setMemberNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					member.setOperatorType(operator.getOperatorType());
					member.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				member.setCreateTime(now);
				member.setUpdateTime(now);
				if (member.getRegisterTime() == null) {
					member.setRegisterTime(now);
				}
				// 填充默认值
				this.fillDefaultValues(member);

				// 调用Dao执行插入操作
				memberDao.add(member);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(member);
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
	 * 根据主键，更新一条Member记录
	 * 
	 * @param member
	 *            更新的Member数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<Member> updateMember(Member member, Operator operator) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();

		if (member == null || member.getMemberNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " member = " + member);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					member.setOperatorType(operator.getOperatorType());
					member.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				member.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = memberDao.update(member);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
					// 调用硬件平台
					JSONObject jsonParam = new JSONObject();
					// 用户ID
					jsonParam.put("id", member.getMid());
					// 用户性别
					if (member.getSex() == null) {
						jsonParam.put("sex", 1);
					} else {
						jsonParam.put("sex", member.getSex());
					}
					// 用户身高
					if (member.getHeight() == null) {
						jsonParam.put("height", 168);
					} else {
						jsonParam.put("height", member.getHeight());
					}
					// 用户体重
					if (member.getWeight() == null) {
						jsonParam.put("weight", 50);
					} else {
						jsonParam.put("weight", member.getWeight());
					}
					// 用户生日
					if (member.getBirthday() == null) {
						jsonParam.put("birthday", "1990-11-11");
					} else {
						jsonParam.put("birthday", ECDateUtils.formatDate(member.getBirthday(), "yyyy-MM-dd"));
					}

					HardwareUtils.post(jsonParam, HwUrl.upUser);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(member);
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
		return primarykeyService.getValueByBizType(PrimarykeyConstant.memberType) + "";
	}

	/**
	 * 为Member对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Member obj) {
		if (obj != null) {
			if (obj.getIsBlacklist() == null) {
				obj.setIsBlacklist(0);
			}
		}
	}

	/**
	 * 验证注册手机号的唯一性
	 */
	@Override
	public Member getMemberByPhone(String phone) {
		// TODO Auto-generated method stub
		Member obj = null;
		if (phone == null || phone.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " phone = " + phone);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = memberDao.getByPhone(phone);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return obj;
	}

	// 根据邀请码 获取推荐人id
	public Member getInvitationCode(String invitationCode) {
		return memberDao.getInvitationCode(invitationCode);
	}

	@Transactional
	public ResultInfo<Member> addMemberInfo(Member member, Operator operator) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();

		if (member == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " member = " + member);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (member.getMemberNo() == null) {
					member.setMemberNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					member.setOperatorType(operator.getOperatorType());
					member.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				member.setCreateTime(now);
				member.setUpdateTime(now);
				if (member.getRegisterTime() == null) {
					member.setRegisterTime(now);
				}
				// 填充默认值
				this.fillDefaultValues(member);

				// 调用Dao执行插入操作
				memberDao.add(member);
				// 新增会员余额信息
				MemberBalance mc = new MemberBalance();
				mc.setMemberNo(member.getMemberNo());
				mc.setMemberBalance(0D);
				mc.setMemberDeposit(0D);
				mc.setCourseNumber(0);
				memberBalanceDao.add(mc);

				// 新增会员押金状态表
				DepositStatus ds = new DepositStatus();
				ds.setMemberNo(member.getMemberNo());
				// 未缴纳押金
				ds.setDepositStatus(0);
				depositStatusDao.add(ds);

				// 调用硬件平台
				JSONObject jsonParam = new JSONObject();
				// 用户ID
				jsonParam.put("id", member.getMemberNo());
				// 用户性别
				if (member.getSex() == null) {
					jsonParam.put("sex", 1);
				} else {
					jsonParam.put("sex", member.getSex());
				}
				// 用户身高
				if (member.getHeight() == null) {
					jsonParam.put("height", 168);
				} else {
					jsonParam.put("height", member.getHeight());
				}
				// 用户体重
				if (member.getWeight() == null) {
					jsonParam.put("weight", 50);
				} else {
					jsonParam.put("weight", member.getWeight());
				}
				// 用户生日
				if (member.getBirthday() == null) {
					jsonParam.put("birthday", "1990-11-11");
				} else {
					jsonParam.put("birthday", ECDateUtils.formatDate(member.getBirthday(), "yyyy-MM-dd"));
				}

				ResultInfo<HwData> re = HardwareUtils.post(jsonParam, HwUrl.register);
				String mid = re.getData().getMid();
				if (mid != null && !mid.equals("")) {
					member.setMid(mid);
					memberDao.update(member);

				}

				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(member);
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
	public Member getMemberByMid(String mid) {
		return memberDao.getMemberByMid(mid);
	}

	@Override
	public List<MemberStatisticVo> statDaysMemberRegisterList(String beginDate, String endDate) {
		List<MemberStatisticVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = memberDao.statDaysMemberRegisterList(beginDate, endDate);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberStatisticVo>(0) : list;
		return list;
	}

	@Override
	public List<MemberStatisticVo> statMonthMemberRegisterList(String beginDate, String endDate) {
		List<MemberStatisticVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = memberDao.statMonthMemberRegisterList(beginDate, endDate);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberStatisticVo>(0) : list;
		return list;
	}

	@Override
	public List<MemberStatisticVo> statYearMemberRegisterList(String beginDate, String endDate) {
		List<MemberStatisticVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = memberDao.statYearMemberRegisterList(beginDate, endDate);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<MemberStatisticVo>(0) : list;
		return list;
	}

	@Override
	public Member getMemberByToken(String token) {
		return memberDao.getMemberByToken(token);
	}
}