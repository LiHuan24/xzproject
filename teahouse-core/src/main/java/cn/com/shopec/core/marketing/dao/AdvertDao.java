package cn.com.shopec.core.marketing.dao;

import java.util.List;

import cn.com.shopec.core.common.BaseDao;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.marketing.model.Advert;
import cn.com.shopec.core.marketing.vo.AdvertVo;

/**
 * 广告表 数据库处理类
 */
public interface AdvertDao extends BaseDao<Advert, String> {

	/**
	 * 得到最新的一个广告
	 */
	List<Advert> getLatestAdverts();

	/**
	 * 首页查询文章
	 */
	public List<AdvertVo> getAdvertVo(Query q);

	public Long countNs(Query q);

	/**
	 * 首页查询文章
	 */
	public List<AdvertVo> getAtVo();

	/**
	 * 首页查询咨询文章
	 */
	public List<AdvertVo> getAtzVo();

}
