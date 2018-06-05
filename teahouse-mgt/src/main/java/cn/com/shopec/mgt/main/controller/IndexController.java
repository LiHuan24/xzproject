package cn.com.shopec.mgt.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.mgt.common.BaseController;

@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {
	
		
	

	
	@RequestMapping("test")
	public String test() {
		return "main/test";
	}

	/**
	 * 首页头部数据
	 * 
	 * @return
	 */
	@RequestMapping("headerData")
	@ResponseBody
	public ResultInfo<Map<String, Object>> headerData() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = new HashMap<>();
//		map.put("orderNum", orderService.countOrder(new Query())); // 订单总数
//		map.put("memberNum", memberService.count(new Query())); // 会员总数
//		map.put("censoredMemberNum", memberService.countMemberByCensorStatus(1)); // 认证会员总数
		/*map.put("carNum", carService.count(new Query())); // 车辆总数
		map.put("parkNum", parkService.count(new Query())); // 场站总数
*/		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 待办数据
	 * 
	 * @return
	 */
	@RequestMapping("toDoData")
	@ResponseBody
	public ResultInfo<Map<String, Object>> toDoData() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = new HashMap<>();
//		map.put("waitCensorMemberNum", memberService.countMemberByCensorStatus(2)); // 待审核会员总数
//		map.put("waitRefundNum", depositRefundService.getTodoIndexValue()); // .押金退款待审核总数
//		map.put("onOrderNum", carStatusService.countNoOrderUseCar()); // 非订单用车
//		map.put("lowPowerNum", carStatusService.countCarMinLowPower()); // 小电瓶低电
//		map.put("newParkOpeningNum", parkOpeningService.countParkOpening()); // 今天新增网点反馈数量
//		map.put("customerFeedbackNum", customerFeedbackService.countCustomerFeedbackNum()); // 客服反馈列表未回复数量
//		map.put("orderCommentsNum", orderCommentsService.countOrderComments()); // 当天新增用车评价数量
		
//		Invoice Invoice = new Invoice();
		/*Invoice.setInvoiceStatus(0);
		map.put("invoiceNum", invoiceService.count(new Query())); */// 未开发票总数
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 交易数据
	 * 
	 * @return
	 */
	@RequestMapping("transactionData")
	@ResponseBody
	public ResultInfo<Map<String, Object>> transactionData() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		/*Map<String, Object> map = orderService.getTransactionCountMap();
		map.put("rechargeNum", pricingPackOrderService.countRecharge());*/ //今日充值笔数
		Map<String, Object> map = new HashMap<>();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 车务数据
	 * 
	 * @return
	 */
	@RequestMapping("carServiceData")
	@ResponseBody
	public ResultInfo<Map<String, Object>> carServiceData() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
//		Map<String, Object> map = carService.carServiceMap();
		resultInfo.setCode(Constant.SECCUESS);
//		resultInfo.setData(map);
		return resultInfo;
	}

	/**
	 * 监控告警数据
	 * 
	 * @return
	 */
	@RequestMapping("warningData")
	@ResponseBody
	public ResultInfo<Map<String, Object>> warningData() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		Map<String, Object> map = new HashMap<>();		
//		map.put("carIdleNum", carService.countCarIdle()); //闲置车辆
//		map.put("onOrderNum", carStatusService.countNoOrderUseCar()); // 非订单用车
//		map.put("lowPowerNum", carStatusService.countCarMinLowPower()); // 小电瓶低电
//		map.put("excessOrderNum", orderService.countExcessOrder()); //超额订单
//		map.put("carBrokenLineNum", carStatusService.countCarBrokenLine()); //断电车辆
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}	
	
	/**
	 * 获取近10天的订单数
	 * 
	 * @return
	 */
	@RequestMapping("getOrderDay10Count")
	@ResponseBody
	public ResultInfo<Map<String, Object>> getOrderDay10Count() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
//		Map<String, Object> map = orderService.getOrderDay10CountMap();
		Map<String, Object> map = new HashMap<>();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
	
	/**
	 * 获取近10天的会员数
	 * 
	 * @return
	 */
	@RequestMapping("getMember10Count")
	@ResponseBody
	public ResultInfo<Map<String, Object>> getMember10Count() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
//		Map<String, Object> map = memberService.getMemberDay10CountMap();
		Map<String, Object> map = new HashMap<>();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
	
	/**
	 * 获取近10天的车辆上下线数
	 * 
	 * @return
	 */
	@RequestMapping("getCarLine10Count")
	@ResponseBody
	public ResultInfo<Map<String, Object>> getCarLine10Count() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
		/*Map<String, Object> map = carOnlineCountService.getCarLine10CountMap();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);*/
		return resultInfo;
	}
	
	/**
	 * 获取近10天的告警线数
	 * 
	 * @return
	 */
	@RequestMapping("getWarning10Count")
	@ResponseBody
	public ResultInfo<Map<String, Object>> getWarning10Count() {
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<>();
//		Map<String, Object> map = warningService.getWarningDay10CountMap();
		Map<String, Object> map = new HashMap<>();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
	
}
