package cn.com.shopec.core.themepavilion.service.impl;

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
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.themepavilion.dao.CourseSortDao;
import cn.com.shopec.core.themepavilion.model.CourseSort;
import cn.com.shopec.core.themepavilion.service.CourseSortService;

/**
 * 课程分类表 服务实现类
 */
@Service
public class CourseSortServiceImpl implements CourseSortService {

	private static final Log log = LogFactory.getLog(CourseSortServiceImpl.class);

	@Resource
	private CourseSortDao courseSortDao;

	/**
	 * 根据查询条件，查询并返回CourseSort的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CourseSort> getCourseSortList(Query q) {
		List<CourseSort> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = courseSortDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CourseSort>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回CourseSort的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<CourseSort> getCourseSortPagedList(Query q) {
		PageFinder<CourseSort> page = new PageFinder<CourseSort>();

		List<CourseSort> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			CourseSort courseSort = (CourseSort) q.getQ();
			if (courseSort != null) {
				courseSort.setIsDeleted(Constant.DEL_STATE_NO);
			}
			// 调用dao查询满足条件的分页数据
			list = courseSortDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = courseSortDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CourseSort>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}

	/**
	 * 根据主键，查询并返回一个CourseSort对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public CourseSort getCourseSort(String id) {
		CourseSort obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = courseSortDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组CourseSort对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<CourseSort> getCourseSortByIds(String[] ids) {
		List<CourseSort> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = courseSortDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<CourseSort>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的CourseSort记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CourseSort> delCourseSort(String id, Operator operator) {
		ResultInfo<CourseSort> resultInfo = new ResultInfo<CourseSort>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			CourseSort courseSort = new CourseSort();
			courseSort.setCourseSortNo(id);
			courseSort.setIsDeleted(Constant.DEL_STATE_YES);
			courseSort.setUpdateTime(new Date());
			if (operator != null) { // 最近操作人
				courseSort.setOperatorType(operator.getOperatorType());
				courseSort.setOperatorId(operator.getOperatorId());
			}

			// 调用Dao执行更新操作，并判断更新语句执行结果
			int count = courseSortDao.update(courseSort);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(courseSort);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条CourseSort记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param courseSort
	 *            新增的CourseSort数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CourseSort> addCourseSort(CourseSort courseSort, Operator operator) {
		ResultInfo<CourseSort> resultInfo = new ResultInfo<CourseSort>();

		if (courseSort == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " courseSort = " + courseSort);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (courseSort.getCourseSortNo() == null) {
					courseSort.setCourseSortNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					courseSort.setOperatorType(operator.getOperatorType());
					courseSort.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				courseSort.setCreateTime(now);
				courseSort.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(courseSort);

				// 调用Dao执行插入操作
				courseSortDao.add(courseSort);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(courseSort);
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
	 * 根据主键，更新一条CourseSort记录
	 * 
	 * @param courseSort
	 *            更新的CourseSort数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<CourseSort> updateCourseSort(CourseSort courseSort, Operator operator) {
		ResultInfo<CourseSort> resultInfo = new ResultInfo<CourseSort>();

		if (courseSort == null || courseSort.getCourseSortNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " courseSort = " + courseSort);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					courseSort.setOperatorType(operator.getOperatorType());
					courseSort.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				courseSort.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = courseSortDao.update(courseSort);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(courseSort);
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
		return String.valueOf(new Date().getTime() * 10000 + new Random().nextInt(10000));
	}

	/**
	 * 为CourseSort对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(CourseSort obj) {
		if (obj != null) {
			if (obj.getIsDeleted() == null) {
				obj.setIsDeleted(0);
			}
		}
	}

}
