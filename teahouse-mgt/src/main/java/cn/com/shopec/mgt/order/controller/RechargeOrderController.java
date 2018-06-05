package cn.com.shopec.mgt.order.controller;

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
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECStringUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.model.RechargeOrder;
import cn.com.shopec.core.finace.service.RechargeOrderService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.dao.SysUserDao;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 充值订单
 *
 */
@Controller
@RequestMapping("/rechargeOrder")
public class RechargeOrderController extends BaseController {
	
	@Resource
	private RechargeOrderService rechargeOrderService;
	@Resource
	private SysUserDao sysUserDao;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private MemberService memberService;
	/**
	 * 进入列表页面
	 * 
	 * @return
	 */
	
	@RequestMapping("toRechargeOrderList")
	public String toRechargeOrderList(@ModelAttribute("rechargeOrder") RechargeOrder rechargeOrder,ModelMap model) {
		model.addAttribute("rechargeOrder", rechargeOrder);
		return "/order/rechargeOrder_list";
	}
	
	/**
	 * 查询列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListRechargeOrder")
	@ResponseBody
	public PageFinder<RechargeOrder> pageListRechargeOrder(@ModelAttribute("rechargeOrder") RechargeOrder rechargeOrder,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, rechargeOrder);
		PageFinder<RechargeOrder> rechargeOrderPage =  rechargeOrderService.getRechargeOrderPagedList(q);
		return rechargeOrderPage;
	}
	
	/**
	 * 详情
	 * 
	 * @param rechargeOrderNo
	 * @return
	 */
	@RequestMapping("toRechargeOrderView")
	public String toRechargeOrderView(@ModelAttribute("rechargeOrderNo") String rechargeOrderNo, Model model) {
		RechargeOrder rechargeOrder = rechargeOrderService.getRechargeOrder(rechargeOrderNo);
		if(rechargeOrder != null){
			Member member = memberService.getMember(rechargeOrder.getMemberNo());
			if(member != null){
				rechargeOrder.setMemberName(member.getMemberName());
			}
		}
		model.addAttribute("rechargeOrder", rechargeOrder);
		return "/order/rechargeOrder_view";
	}

	/**
	 * 导出excel文档
	 * @param rechargeOrder
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("toRechargeOrderExport")
	public void toRechargeOrderExport(@ModelAttribute("rechargeOrder") RechargeOrder rechargeOrder, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			@SuppressWarnings("deprecation")
			String path = request.getRealPath("/") + "res" + File.separator + "rechargeOrder.xls";// 声明工作薄
			InputStream is = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);// 生成表格
			
			List<RechargeOrder> list = rechargeOrderService.getRechargeOrderListForExport(new Query(rechargeOrder));
			int rowIndex = 1;
			for (RechargeOrder rechargeOrderData : list) {
				sheet.createRow(rowIndex);
				
				// 支付状态（0、未支付，1、已支付，2、部分支付-预留）
				String payStatus = rechargeOrderData.getPayStatus() == null ? "" : rechargeOrderData.getPayStatus().toString();
				if (payStatus.equals("0")) {
					payStatus = "未支付";
				} else if (payStatus.equals("1")){
					payStatus = "已支付";
				} else if (payStatus.equals("2")){
					payStatus = "部分支付";
				} else {
					payStatus = "";
				}
				// 是否可用（0，不可用，1、可用；默认1）
				String isAvailable = rechargeOrderData.getIsAvailable() == null ? "" : rechargeOrderData.getIsAvailable().toString();
				if (isAvailable.equals("0")) {
					isAvailable = "不可用";
				} else if (isAvailable.equals("1")){
					isAvailable = "可用";
				} else {
					isAvailable = "";
				}
				
				int columnIndex = 0;
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(rechargeOrderData.getRechargeOrderNo()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(rechargeOrderData.getMemberName()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(rechargeOrderData.getMobilePhone()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(rechargeOrderData.getRechargeName()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(rechargeOrderData.getRechargeAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(rechargeOrderData.getPayableAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(payStatus));
				setColumnValue(sheet, rowIndex, columnIndex++, ECDateUtils.formatTime(rechargeOrderData.getCreateTime()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(isAvailable));
				rowIndex++;
			}
			response.reset();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=rechargeOrder.xls");
			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
