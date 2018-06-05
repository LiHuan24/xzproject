package cn.com.shopec.core.finace.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.CoursePackageOrder;

/**
 * 课程包订单表 数据库处理类
 */
public interface CoursePackageOrderDao extends BaseDao<CoursePackageOrder, String> {

	/**
	 * 管理端导出查询
	 * @param q
	 * @return
	 */
	List<CoursePackageOrder> queryAllForExport(Query q);

}
