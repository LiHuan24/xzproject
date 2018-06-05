package cn.com.shopec.mgt.order.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.CommunityOrder;
import cn.com.shopec.core.order.service.CommunityOrderService;
import cn.com.shopec.core.order.vo.CommunityOrderStatisticVo;
import cn.com.shopec.core.system.dao.SysUserDao;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 社区订单
 *
 */
@Controller
@RequestMapping("/communityOrder")
public class CommunityOrderController extends BaseController {
	
	@Resource
	private CommunityOrderService communityOrderService;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private MemberService memberService;
	@Resource
	private SysParamService sysParamService;
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	
	@RequestMapping("toCommunityOrderList")
	public String toCommunityOrderList(@ModelAttribute("communityOrder") CommunityOrder communityOrder,ModelMap model) {
		model.addAttribute("communityOrder", communityOrder);
		return "/order/communityOrder_list";
	}
	
	/**
	 * 查询列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListCommunityOrder")
	@ResponseBody
	public PageFinder<CommunityOrder> pageListCommunityOrder(@ModelAttribute("communityOrder") CommunityOrder communityOrder,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, communityOrder);
		PageFinder<CommunityOrder> communityOrderPage =  communityOrderService.getCommunityOrderPagedList(q);
		return communityOrderPage;
	}
	
	/**
	 * 详情
	 * 
	 * @param communityOrderNo
	 * @return
	 */
	@RequestMapping("toCommunityOrderView")
	public String toCommunityOrderView(@ModelAttribute("communityOrderNo") String communityOrderNo, Model model) {
		CommunityOrder communityOrder = communityOrderService.getCommunityOrder(communityOrderNo);
		if(communityOrder != null){
			Member member = memberService.getMember(communityOrder.getMemberNo());
			if(member != null){
				communityOrder.setMemberName(member.getMemberName());
			}
		}
		model.addAttribute("communityOrder", communityOrder);
		return "/order/communityOrder_view";
	}
	
	/**
	 * 导出excel文档
	 * @param communityOrder
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("toCommunityOrderExport")
	public void toCommunityOrderExport(@ModelAttribute("communityOrder") CommunityOrder communityOrder, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			@SuppressWarnings("deprecation")
			String path = request.getRealPath("/") + "res" + File.separator + "communityOrder.xls";// 声明工作薄
			InputStream is = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);// 生成表格
			
			List<CommunityOrder> list = communityOrderService.getCommunityOrderListForManagePage(new Query(communityOrder));
			int rowIndex = 1;
			for (CommunityOrder communityOrderData : list) {
				sheet.createRow(rowIndex);
				
				// 订单状态：0、进行中，1、已结束，2、已取消
				String orderStatus = communityOrderData.getOrderStatus() == null ? "" : communityOrderData.getOrderStatus().toString();
				if (orderStatus.equals("0")) {
					orderStatus = "进行中";
				} else if (orderStatus.equals("1")){
					orderStatus = "已结束";
				} else if (orderStatus.equals("2")){
					orderStatus = "已取消";
				} else {
					orderStatus = "";
				}
				// 支付状态（0、未支付，1、已支付，默认0）
				String payStatus = communityOrderData.getPayStatus() == null ? "" : communityOrderData.getPayStatus().toString();
				if (payStatus.equals("0")) {
					payStatus = "未支付";
				} else if (payStatus.equals("1")){
					payStatus = "已支付";
				} else {
					payStatus = "";
				}
				
				int columnIndex = 0;
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(communityOrderData.getCommunityOrderNo()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(communityOrderData.getMemberName()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(communityOrderData.getMobilePhone()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(communityOrderData.getCityName()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(communityOrderData.getStoreName()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderStatus));
				setColumnValue(sheet, rowIndex, columnIndex++, ECDateUtils.formatTime(communityOrderData.getStartTime()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECDateUtils.formatTime(communityOrderData.getEndTime()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(communityOrderData.getWhenLong()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(communityOrderData.getOrderAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(communityOrderData.getDiscountAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(communityOrderData.getBalanceDiscountAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(communityOrderData.getPayableAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(payStatus));
				rowIndex++;
			}
			
			response.reset();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=communityOrder.xls");
			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载社区馆订单数据报表统计页面
	 */
	@RequestMapping("toCommunityOrderStatEchart")
	public String toCommunityOrderStatEchart(ModelMap model) {
		return "/order/communityOrder_echart_list";
	}
	
	/**
	 * 按天统计社区馆订单报表
	 * Echarts 折线形式展示
	 */
	@RequestMapping("statisticCommunityOrderDays")
	@ResponseBody
	public ResultInfo<Map<String,Object>> statisticCommunityOrderDays(Date startTimeDay, Date endTimeDay) throws Exception{
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<Map<String, Object>>();
		String beginDate = "";
		String endDate = "";
		if(startTimeDay == null && endTimeDay == null){
			beginDate = ECDateUtils.getFirstDayOfMonth();
			endDate = ECDateUtils.getLastDayOfMonth();
		}else{
			beginDate = ECDateUtils.formatDate(startTimeDay, "yyyy-MM-dd");
			endDate = ECDateUtils.formatDate(endTimeDay, "yyyy-MM-dd");
		}
		List<String> listDate = ECDateUtils.getDatesForBeginAndEndTime(beginDate, endDate);
		int size = listDate.size();
		String[] dates = (String[])listDate.toArray(new String[size]);//使用了第二种接口，返回值和参数均为结果 
		//数组初始化
		int[] orderCount = new int[dates.length];//订单数量数组
		Double[] orderAmount = new Double[dates.length];//订单总金额数组
		Arrays.fill(orderAmount,0d);
		Double[] discountAmount = new Double[dates.length];//优惠总金额数组
		Arrays.fill(discountAmount,0d);
		Double[] balanceAmount = new Double[dates.length];//余额抵扣总金额数组
		Arrays.fill(balanceAmount,0d);
		Double[] payableAmount = new Double[dates.length];//订单总支付金额数组
		Arrays.fill(payableAmount,0d);
		Double[] whenLong = new Double[dates.length];//订单时长
		Arrays.fill(whenLong,0d);
		for (int i = 0; i < dates.length; i++) {
			//统计每一天的订单数据
			List<CommunityOrderStatisticVo> listCommunityOrder = communityOrderService.statDaysCommunityOrderList(beginDate, endDate);
			if(listCommunityOrder != null && listCommunityOrder.size() > 0){
				for (CommunityOrderStatisticVo communityVo : listCommunityOrder) {
					if(dates[i].equals(communityVo.getDays())){
						orderCount[i] = Integer.parseInt(communityVo.getCommunityOrderNo());
						orderAmount[i] = communityVo.getOrderAmount();
						discountAmount[i] = communityVo.getDiscountAmount();
						balanceAmount[i] =  communityVo.getBalanceAmount();
						payableAmount[i] = communityVo.getPayableAmount();
						whenLong[i] = Double.parseDouble(communityVo.getWhenLong());
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("days", dates);
		map.put("orderCount", orderCount);
		map.put("orderAmount", orderAmount);
		map.put("discountAmount", discountAmount);
		map.put("balanceAmount", balanceAmount);
		map.put("payableAmount", payableAmount);
		map.put("whenLong", whenLong);
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
	
	/**
	 * 按月统计社区馆订单数据 
	 */
	@RequestMapping("statisticCommunityOrderMonths")
	@ResponseBody
	public ResultInfo<Map<String,Object>> statisticCommunityOrderMonths(String startTimeMonth, String endTimeMonth) throws Exception{
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<Map<String, Object>>();
		String beginDate = "";
		String endDate = "";
		if(StringUtils.isBlank(startTimeMonth) && StringUtils.isBlank(endTimeMonth)){
			beginDate = ECDateUtils.getCurrentTime("yyyy")+"-01";
			endDate = ECDateUtils.getCurrentTime("yyyy")+"-12";
		}else{
			beginDate = startTimeMonth;
			endDate = endTimeMonth;
		}
		List<String> listDate = ECDateUtils.getMonthBetween(beginDate, endDate);
		int size = listDate.size();
		String[] dates = (String[])listDate.toArray(new String[size]);//使用了第二种接口，返回值和参数均为结果 
		int[] orderCount = new int[dates.length];//订单数量数组
		Double[] orderAmount = new Double[dates.length];//订单总金额数组
		Arrays.fill(orderAmount,0d);
		Double[] discountAmount = new Double[dates.length];//优惠总金额数组
		Arrays.fill(discountAmount,0d);
		Double[] balanceAmount = new Double[dates.length];//余额抵扣总金额数组
		Arrays.fill(balanceAmount,0d);
		Double[] payableAmount = new Double[dates.length];//订单总支付金额数组
		Arrays.fill(payableAmount,0d);
		Double[] whenLong = new Double[dates.length];//订单时长
		Arrays.fill(whenLong,0d);
		for (int i = 0; i < dates.length; i++) {
			//查询统计月主题订单数据
			List<CommunityOrderStatisticVo> listCommunityOrder = communityOrderService.statMonthsCommunityOrderList(beginDate, endDate);
			if(listCommunityOrder != null && listCommunityOrder.size() > 0){
				for (CommunityOrderStatisticVo communityVo : listCommunityOrder) {
					if(dates[i].equals(communityVo.getMonths())){
						orderCount[i] = Integer.parseInt(communityVo.getCommunityOrderNo());
						orderAmount[i] = communityVo.getOrderAmount();
						discountAmount[i] = communityVo.getDiscountAmount();
						balanceAmount[i] =  communityVo.getBalanceAmount();
						payableAmount[i] = communityVo.getPayableAmount();
						whenLong[i] = Double.parseDouble(communityVo.getWhenLong());
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("months", dates);
		map.put("orderCount", orderCount);
		map.put("orderAmount", orderAmount);
		map.put("discountAmount", discountAmount);
		map.put("balanceAmount", balanceAmount);
		map.put("payableAmount", payableAmount);
		map.put("whenLong", whenLong);
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
	
	/**
	 * 按年统计社区馆订单数据
	 */
	@RequestMapping("statisticCommunityOrderYears")
	@ResponseBody
	public ResultInfo<Map<String,Object>> statisticCommunityOrderYears(String startTimeYear, String endTimeYear) throws Exception{
		ResultInfo<Map<String, Object>> resultInfo = new ResultInfo<Map<String, Object>>();
		String beginDate = "";
		String endDate = "";
		if(StringUtils.isBlank(startTimeYear) && StringUtils.isBlank(endTimeYear)){
			beginDate = sysParamService.getByParamKey("STATE_YEAR").getParamValue();
			endDate = ECDateUtils.getCurrentTime("yyyy");
		}else{
			beginDate = startTimeYear;
			endDate = endTimeYear;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); //格式化日期
		Date startTime =  sdf.parse(beginDate);
		Date endTime = sdf.parse(endDate);
		List<Date> listDate = ECDateUtils.getDatesBetweenTwoDate(startTime, endTime);  
		List<String> listTime = new ArrayList<String>();
		for(int i=0;i<listDate.size();i++){  
	        listTime.add(sdf.format(listDate.get(i)));
	    }  
		int size = listTime.size();
		String[] dates = (String[])listTime.toArray(new String[size]);//使用了第二种接口，返回值和参数均为结果 
		int[] orderCount = new int[dates.length];//订单数量数组
		Double[] orderAmount = new Double[dates.length];//订单总金额数组
		Arrays.fill(orderAmount,0d);
		Double[] discountAmount = new Double[dates.length];//优惠总金额数组
		Arrays.fill(discountAmount,0d);
		Double[] balanceAmount = new Double[dates.length];//余额抵扣总金额数组
		Arrays.fill(balanceAmount,0d);
		Double[] payableAmount = new Double[dates.length];//订单总支付金额数组
		Arrays.fill(payableAmount,0d);
		Double[] whenLong = new Double[dates.length];//订单时长
		Arrays.fill(whenLong,0d);
		for (int i = 0; i < dates.length; i++) {
			List<CommunityOrderStatisticVo> listCommunityOrder = communityOrderService.statYearsCommunityOrderList(beginDate, endDate);
			if(listCommunityOrder != null && listCommunityOrder.size() > 0){	
				for (CommunityOrderStatisticVo cmmunityVo : listCommunityOrder) {
					if(dates[i].equals(cmmunityVo.getYears())){
						orderCount[i] = Integer.parseInt(cmmunityVo.getCommunityOrderNo());
						orderAmount[i] = cmmunityVo.getOrderAmount();
						discountAmount[i] = cmmunityVo.getDiscountAmount();
						balanceAmount[i] =  cmmunityVo.getBalanceAmount();
						payableAmount[i] = cmmunityVo.getPayableAmount();
						whenLong[i] = Double.parseDouble(cmmunityVo.getWhenLong());
					}
				}
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("years", dates);
		map.put("orderCount", orderCount);
		map.put("orderAmount", orderAmount);
		map.put("discountAmount", discountAmount);
		map.put("balanceAmount", balanceAmount);
		map.put("payableAmount", payableAmount);
		map.put("whenLong", whenLong);
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setData(map);
		return resultInfo;
	}
}