package cn.com.shopec.core.order.service;

import java.util.List;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.BaseService;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.ThemeOrder;
import cn.com.shopec.core.order.vo.ThemeOrderStatisticVo;

/**
 * 主题订单 服务接口类
 */
public interface ThemeOrderService extends BaseService {

	/**
	 * 根据查询条件，查询并返回ThemeOrder的列表
	 * @param q 查询条件
	 * @return
	 */
	public List<ThemeOrder> getThemeOrderList(Query q);
	
	/**
	 * app端查询会员预约的主题馆课程size，默认查询4条
	 */
	public List<ThemeOrder> getAppThemeOrderList(Query q);
	
	/**
	 * 根据查询条件，查询并返回ThemeOrder的列表(管理端用)
	 * @param q 查询条件
	 * @return
	 */
	public List<ThemeOrder> getThemeOrderListForManagePage(Query q);
	
	/**
	 * 根据查询条件，分页查询并返回ThemeOrder的分页结果
	 * @param q 查询条件
	 * @return
	 */
	public PageFinder<ThemeOrder> getThemeOrderPagedList(Query q);
	
	public PageFinder<ThemeOrder> themeOrderPagedList(Query q);
	/**
	 * 根据排课编号、订单状态按倒序查询正在排队的订单
	 */
	public PageFinder<ThemeOrder> queryLineThemeOrder(Query q);
	/**
	 * 根据主键，查询并返回一个ThemeOrder对象
	 * @param id 主键id
	 * @return
	 */
	public ThemeOrder getThemeOrder(String id);

	/**
	 * 根据主键数组，查询并返回一组ThemeOrder对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	public List<ThemeOrder> getThemeOrderByIds(String[] ids);
	
	/**
	 * 根据主键，删除特定的ThemeOrder记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ThemeOrder> delThemeOrder(String id, Operator operator);
	
	/**
	 * 新增一条ThemeOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param themeOrder 新增的ThemeOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ThemeOrder> addThemeOrder(ThemeOrder themeOrder, Operator operator);
	
	/**
	 * 根据主键，更新一条ThemeOrder记录
	 * @param themeOrder 更新的ThemeOrder数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	public ResultInfo<ThemeOrder> updateThemeOrder(ThemeOrder themeOrder, Operator operator);
	/**
	 * 统计课程预约人数
	 */
	public long statCourseAppoinNumber(String arrayCourseNo);
	/**
	 * 生成主键
	 * @return
	 */
	String generatePK();
	/**
	 * 主题馆订单取消后，支付宝退款请求
	 */
	public ResultInfo<ThemeOrder> cancelOrderRquestAlipay(ThemeOrder themeOrder,String remark, double refundAmount);
	/**
	 * 主题馆订单取消后，微信退款请求
	 */
	public ResultInfo<ThemeOrder> cancelOrderRquestWx(ThemeOrder themeOrder,String remark, double refundAmount);
	/**
	 * 取消订单退款成功后 回调修改相关表数据
	 */
	public void callBackOrderData(ThemeOrder themeOrder,double refundAmount,int refundMethod, int slectNumber);
	/**
	 * 订单取消后执行订单相关表数据的修改
	 */
	public void updateOrderGlData(ThemeOrder themeOrder,int selectNumber, int type);
	/**
	 * 排队中的订单排队预约失败执行退款更新订单表
	 * @param ThemeOrder themeOrder
	 */
	public void lineOrderRefundUpdate(ThemeOrder themeOrder);
	/**
	 * 排队订单预约失败退款操作
	 */
	public void callBackOrderLineRefundData(ThemeOrder themeOrder, double refundAmount, int refundMethod, int selectNumber,int refundType);
	/**
	 * 根据会员编号查询订单状态已预约或者进行中的数据
	 */
	public List<ThemeOrder> queryOrderDataByMemberNo(String memberNo);
	/**
	 * 按天统计主题馆订单数据
	 */
	public List<ThemeOrderStatisticVo> statDaysThemeOrderList(String beginTime, String endTime);
	/**
	 * 按月统计主题馆订单数据
	 */
	public List<ThemeOrderStatisticVo> statMonthsThemeOrderList(String beginTime, String endTime);
	/**
	 * 按年统计主题馆订单数据
	 */
	public List<ThemeOrderStatisticVo> statYearsThemeOrderList(String beginTime, String endTime);
}
