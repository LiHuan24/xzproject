package cn.com.shopec.mgt.statement.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.order.model.OrderSummary;
import cn.com.shopec.core.order.service.OrderSummaryService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 订单报表
 * */

@Controller
@RequestMapping("/orderSummary")
public class OrderSummaryController extends BaseController{
	@Resource
	private OrderSummaryService orderSummaryService;
	/**
	 * 进入主题订单汇总信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toThemeOrderSummary")
	public String toThemeOrderSummary(ModelMap model) {
		String days = ECDateUtils.getFirstAndLastDayOfMonth();
		if(days!=null&&!days.equals("")){
			String[] temp=days.split(",");
			model.addAttribute("startTime",temp[0]);
			model.addAttribute("endTime",temp[1]);	
		}
		return "statement/themeOrderSummary_list";
	}
	/**
	 * 主题订单汇总信息
	 * @param summary
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/pageListThemeOrderSummary")
	@ResponseBody
	public PageFinder<OrderSummary> queryThemeOrderList(OrderSummary summary) throws ParseException {
		
		PageFinder<OrderSummary> page = new  PageFinder<OrderSummary>();
		List<OrderSummary> orderSummary =  orderSummaryService.queryThemeOrderList(new Query(summary));
		if(orderSummary != null ){
			page.setData(orderSummary);
			page.setRowCount(orderSummary.size());
		}else{
			page.setRowCount(0);
		}
		return page;
	}
	
	
	/**社区订单汇总信息页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/toCommunityOrderSummary")
	public String toCommunitySummary(ModelMap model) {
		String days = ECDateUtils.getFirstAndLastDayOfMonth();
		if(days!=null&&!days.equals("")){
			String[] temp=days.split(",");
			model.addAttribute("startTime",temp[0]);
			model.addAttribute("endTime",temp[1]);	
		}
		return "statement/communityOrderSummary_list";
	}
	/**
	 * 社区订单汇总信息
	 * @param summary
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/pageListCommunityOrderSummary")
	@ResponseBody
	public PageFinder<OrderSummary> queryCommunityOrderList(OrderSummary summary) throws ParseException {
		
		PageFinder<OrderSummary> page = new  PageFinder<OrderSummary>();
		List<OrderSummary> orderSummary =  orderSummaryService.queryCommunityOrderList(new Query(summary));
		if(orderSummary != null ){
			page.setData(orderSummary);
			page.setRowCount(orderSummary.size());
		}else{
			page.setRowCount(0);
		}
		return page;
	}
	/**
	 * 导出社区订单汇总excel文档
	 * @param themeOrder
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("toThemeOrderSummaryExport")
	public void toThemeOrderExport(@ModelAttribute("summary") OrderSummary summary, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			@SuppressWarnings("deprecation")
			String path = request.getRealPath("/") + "res" + File.separator + "themeOrderSummary.xls";// 声明工作薄
			InputStream is = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);// 生成表格
			List<OrderSummary> list = orderSummaryService.queryThemeOrderList(new Query(summary));
			int rowIndex = 1;
			for (OrderSummary orderSummary : list) {
				sheet.createRow(rowIndex);
				int columnIndex = 0;
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getOrderCount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getWhenLong()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getOrderAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getAlreadyAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getDiscountAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getDiscountOrderCount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getBalanceDiscountAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getBalanceOrderCount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getAlipayCount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getAlipayAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getAlipayRealAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getAlipayCharge()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getWxCount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getWxAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getWxRealAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getWxCharge()));
				rowIndex++;
			}
			
			response.reset();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=themeOrderSummary.xls");
			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出社区订单汇总excel文档
	 * @param themeOrder
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("toCommunityOrderSummaryExport")
	public void toCommunityOrderExport(@ModelAttribute("summary") OrderSummary summary, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			@SuppressWarnings("deprecation")
			String path = request.getRealPath("/") + "res" + File.separator + "communityOrderSummary.xls";// 声明工作薄
			InputStream is = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);// 生成表格
			List<OrderSummary> list = orderSummaryService.queryCommunityOrderList(new Query(summary));
			int rowIndex = 1;
			for (OrderSummary orderSummary : list) {
				sheet.createRow(rowIndex);
				int columnIndex = 0;
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getOrderCount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getWhenLong()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getOrderAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getAlreadyAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getDiscountAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getDiscountOrderCount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getBalanceDiscountAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getBalanceOrderCount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getAlipayCount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getAlipayAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getAlipayRealAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getAlipayCharge()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getWxCount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getWxAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getWxRealAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(orderSummary.getWxCharge()));
				rowIndex++;
			}
			
			response.reset();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=communityOrderSummary.xls");
			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
