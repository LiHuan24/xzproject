package cn.com.shopec.core.themepavilion.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.themepavilion.model.SignAppraise;

/**
 * 课程签到评价表 服务接口类
 */
public interface SignAppraiseService extends BaseService {

	/**
	 * 根据查询条件，查询并返回SignAppraise的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<SignAppraise> getSignAppraiseList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回SignAppraise的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<SignAppraise> getSignAppraisePagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个SignAppraise对象
	 * @param id 主键id
	 * @return
	 */
	public SignAppraise getSignAppraise(String id);

	/**
	 * 根据主键数组，查询并返回一组SignAppraise对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<SignAppraise> getSignAppraiseByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的SignAppraise记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<SignAppraise> delSignAppraise(String id, Operator operator);
	
	/**
	 * 新增一条SignAppraise记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param signAppraise 新增的SignAppraise数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<SignAppraise> addSignAppraise(SignAppraise signAppraise, Operator operator);
	
	/**
	 * 根据主键，更新一条SignAppraise记录
	 * @param signAppraise 更新的SignAppraise数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<SignAppraise> updateSignAppraise(SignAppraise signAppraise, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为SignAppraise对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(SignAppraise obj);
	/**
	 * 根据教练编号和课程排课编号查询该教练是否已经签到
	 */
	public SignAppraise getSignAppraiseStatus(String coachNo,String arrayCourseNo);
		
}
