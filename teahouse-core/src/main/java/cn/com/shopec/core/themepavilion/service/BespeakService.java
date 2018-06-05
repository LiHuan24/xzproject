package cn.com.shopec.core.themepavilion.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.themepavilion.model.Bespeak;

/**
 * 课程预约表 服务接口类
 */
public interface BespeakService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Bespeak的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<Bespeak> getBespeakList(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回Bespeak的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<Bespeak> getBespeakPagedList(Query q);
	
	/**
	 * 根据主键，查询并返回一个Bespeak对象
	 * @param id 主键id
	 * @return
	 */
	public Bespeak getBespeak(String id);

	/**
	 * 根据主键数组，查询并返回一组Bespeak对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Bespeak> getBespeakByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的Bespeak记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Bespeak> delBespeak(String id, Operator operator);
	
	/**
	 * 新增一条Bespeak记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param bespeak 新增的Bespeak数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Bespeak> addBespeak(Bespeak bespeak, Operator operator);
	
	/**
	 * 根据主键，更新一条Bespeak记录
	 * @param bespeak 更新的Bespeak数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<Bespeak> updateBespeak(Bespeak bespeak, Operator operator);

	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK();
	
		/**
	 * 为Bespeak对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(Bespeak obj);
		
}
