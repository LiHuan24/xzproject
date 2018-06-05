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
import cn.com.shopec.core.finace.model.GymCardOrder;
import cn.com.shopec.core.finace.service.GymCardOrderService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.dao.SysUserDao;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 健身卡订单
 *
 */
@Controller
@RequestMapping("/gymCardOrder")
public class GymCardOrderController extends BaseController {
	
	@Resource
	private GymCardOrderService gymCardOrderService;
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
	
	@RequestMapping("toGymCardOrderList")
	public String toGymCardOrderList(@ModelAttribute("gymCardOrder") GymCardOrder gymCardOrder,ModelMap model) {
		model.addAttribute("gymCardOrder", gymCardOrder);
		return "/order/gymCardOrder_list";
	}
	
	/**
	 * 查询列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListGymCardOrder")
	@ResponseBody
	public PageFinder<GymCardOrder> pageListGymCardOrder(@ModelAttribute("gymCardOrder") GymCardOrder gymCardOrder,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, gymCardOrder);
		PageFinder<GymCardOrder> gymCardOrderPage =  gymCardOrderService.getGymCardOrderPagedList(q);
		return gymCardOrderPage;
	}
	
	/**
	 * 详情
	 * 
	 * @param gymCardOrderNo
	 * @return
	 */
	@RequestMapping("toGymCardOrderView")
	public String toGymCardOrderView(@ModelAttribute("gymCardOrderNo") String gymCardOrderNo, Model model) {
		GymCardOrder gymCardOrder = gymCardOrderService.getGymCardOrder(gymCardOrderNo);
		if(gymCardOrder != null){
			Member member = memberService.getMember(gymCardOrder.getMemberNo());
			if(member != null){
				gymCardOrder.setMemberName(member.getMemberName());
			}
		}
		model.addAttribute("gymCardOrder", gymCardOrder);
		return "/order/gymCardOrder_view";
	}
	
	/**
	 * 导出excel文档
	 * @param gymCardOrder
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("toGymCardOrderExport")
	public void toGymCardOrderExport(@ModelAttribute("gymCardOrder") GymCardOrder gymCardOrder, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			@SuppressWarnings("deprecation")
			String path = request.getRealPath("/") + "res" + File.separator + "gymCardOrder.xls";// 声明工作薄
			InputStream is = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);// 生成表格
			
			List<GymCardOrder> list = gymCardOrderService.getGymCardOrderListForExport(new Query(gymCardOrder));
			int rowIndex = 1;
			for (GymCardOrder gymCardOrderData : list) {
				sheet.createRow(rowIndex);
				
				// 是否可用（0，不可用，1、可用；默认1）
				String isAvailable = gymCardOrderData.getIsAvailable() == null ? "" : gymCardOrderData.getIsAvailable().toString();
				if (isAvailable.equals("0")) {
					isAvailable = "不可用";
				} else if (isAvailable.equals("1")){
					isAvailable = "可用";
				} else {
					isAvailable = "";
				}
				// 支付状态（0、未支付，1、已支付，默认0）
				String payStatus = gymCardOrderData.getPayStatus() == null ? "" : gymCardOrderData.getPayStatus().toString();
				if (payStatus.equals("0")) {
					payStatus = "未支付";
				} else if (payStatus.equals("1")){
					payStatus = "已支付";
				} else {
					payStatus = "";
				}
				
				int columnIndex = 0;
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(gymCardOrderData.getGymCardOrderNo()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(gymCardOrderData.getMemberName()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(gymCardOrderData.getMobilePhone()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(gymCardOrderData.getGymCardName()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(gymCardOrderData.getRechargeAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(payStatus));
				setColumnValue(sheet, rowIndex, columnIndex++, ECDateUtils.formatTime(gymCardOrderData.getCreateTime()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(isAvailable));
				rowIndex++;
			}
			response.reset();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=gymCardOrder.xls");
			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
