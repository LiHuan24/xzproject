package cn.com.shopec.core.storemanagement.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.storemanagement.model.Store;

/**
 * 门店表 服务接口类
 */
public interface StoreService extends BaseService {

	/**
	 * 根据查询条件，查询并返回Store的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public List<Store> getStoreList(Query q);

	/**
	 * 根据查询条件，分页查询并返回Store的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	public PageFinder<Store> getStorePagedList(Query q);

	/**
	 * 根据主键，查询并返回一个Store对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	public Store getStore(String id);

	/**
	 * 根据主键数组，查询并返回一组Store对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<Store> getStoreByIds(String[] ids);

	/**
	 * 根据主键，删除特定的Store记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Store> delStore(String id, Operator operator);

	/**
	 * 新增一条Store记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param store
	 *            新增的Store数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Store> addStore(Store store, Operator operator);

	/**
	 * 根据主键，更新一条Store记录
	 * 
	 * @param store
	 *            更新的Store数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	public ResultInfo<Store> updateStore(Store store, Operator operator);

	/**
	 * 生成主键
	 * 
	 * @return
	 */
	public String generatePK();

	/**
	 * 为Store对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(Store obj);

	// 根据门禁编号查找门店
	public Store getStoreById(String entranceCode);

}
