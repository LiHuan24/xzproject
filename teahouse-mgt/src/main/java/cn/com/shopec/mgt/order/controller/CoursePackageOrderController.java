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
import cn.com.shopec.core.finace.model.CoursePackageOrder;
import cn.com.shopec.core.finace.service.CoursePackageOrderService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.dao.SysUserDao;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 课程包订单
 *
 */
@Controller
@RequestMapping("/coursePackageOrder")
public class CoursePackageOrderController extends BaseController {
	
	@Resource
	private CoursePackageOrderService coursePackageOrderService;
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
	
	@RequestMapping("toCoursePackageOrderList")
	public String toCoursePackageOrderList(@ModelAttribute("coursePackageOrder") CoursePackageOrder coursePackageOrder,ModelMap model) {
		model.addAttribute("coursePackageOrder", coursePackageOrder);
		return "/order/coursePackageOrder_list";
	}
	
	/**
	 * 查询列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListCoursePackageOrder")
	@ResponseBody
	public PageFinder<CoursePackageOrder> pageListCoursePackageOrder(@ModelAttribute("coursePackageOrder") CoursePackageOrder coursePackageOrder,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, coursePackageOrder);
		PageFinder<CoursePackageOrder> coursePackageOrderPage =  coursePackageOrderService.getCoursePackageOrderPagedList(q);
		return coursePackageOrderPage;
	}
	
	/**
	 * 详情
	 * 
	 * @param coursePackageOrderNo
	 * @return
	 */
	@RequestMapping("toCoursePackageOrderView")
	public String toCoursePackageOrderView(@ModelAttribute("rechargeOrderNo") String rechargeOrderNo, Model model) {
		CoursePackageOrder coursePackageOrder = coursePackageOrderService.getCoursePackageOrder(rechargeOrderNo);
		if(coursePackageOrder != null){
			Member member = memberService.getMember(coursePackageOrder.getMemberNo());
			if(member != null){
				coursePackageOrder.setMemberName(member.getMemberName());
			}
		}
		model.addAttribute("coursePackageOrder", coursePackageOrder);
		return "/order/coursePackageOrder_view";
	}
	
	/**
	 * 导出excel文档
	 * @param coursePackageOrder
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("toCoursePackageOrderExport")
	public void toCoursePackageOrderExport(@ModelAttribute("coursePackageOrder") CoursePackageOrder coursePackageOrder, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			@SuppressWarnings("deprecation")
			String path = request.getRealPath("/") + "res" + File.separator + "coursePackageOrder.xls";// 声明工作薄
			InputStream is = new FileInputStream(new File(path));
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);// 生成表格
			
			List<CoursePackageOrder> list = coursePackageOrderService.getCoursePackageOrderListForExport(new Query(coursePackageOrder));
			int rowIndex = 1;
			for (CoursePackageOrder coursePackageOrderData : list) {
				sheet.createRow(rowIndex);
				
				// 是否可用（0，不可用，1、可用；默认1）
				String isAvailable = coursePackageOrderData.getIsAvailable() == null ? "" : coursePackageOrderData.getIsAvailable().toString();
				if (isAvailable.equals("0")) {
					isAvailable = "不可用";
				} else if (isAvailable.equals("1")){
					isAvailable = "可用";
				} else {
					isAvailable = "";
				}
				// 支付状态（0、未支付，1、已支付，默认0）
				String payStatus = coursePackageOrderData.getPayStatus() == null ? "" : coursePackageOrderData.getPayStatus().toString();
				if (payStatus.equals("0")) {
					payStatus = "未支付";
				} else if (payStatus.equals("1")){
					payStatus = "已支付";
				} else {
					payStatus = "";
				}
				
				int columnIndex = 0;
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(coursePackageOrderData.getRechargeOrderNo()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(coursePackageOrderData.getMemberName()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(coursePackageOrderData.getMobilePhone()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(coursePackageOrderData.getCoursePackageName()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(coursePackageOrderData.getPayableAmount()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(payStatus));
				setColumnValue(sheet, rowIndex, columnIndex++, ECDateUtils.formatTime(coursePackageOrderData.getCreateTime()));
				setColumnValue(sheet, rowIndex, columnIndex++, ECStringUtils.toStringForNull(isAvailable));

				rowIndex++;
			}
			response.reset();
			response.setContentType("text/plain;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=coursePackageOrder.xls");
			ServletOutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close(); // 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
