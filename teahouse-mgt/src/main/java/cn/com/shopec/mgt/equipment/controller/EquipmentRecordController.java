package cn.com.shopec.mgt.equipment.controller;

import java.text.ParseException;
import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.EquipmentRecord;
import cn.com.shopec.core.equipment.service.EquipmentRecordService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.system.dao.SysUserDao;
import cn.com.shopec.core.system.service.SysUserService;
import cn.com.shopec.mgt.common.BaseController;

/**
 * 健身设备使用记录
 *
 */
@Controller
@RequestMapping("/equipmentRecord")
public class EquipmentRecordController extends BaseController {
	
	@Resource
	private EquipmentRecordService equipmentRecordService;
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
	@RequestMapping("toEquipmentRecordList")
	public String toEquipmentRecordList(@ModelAttribute("equipmentRecord") EquipmentRecord equipmentRecord,ModelMap model) {
		model.addAttribute("equipmentRecord", equipmentRecord);
		return "/equipment/equipmentRecord_list";
	}
	
	/**
	 * 查询列表
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListEquipmentRecord")
	@ResponseBody
	public PageFinder<EquipmentRecord> pageListEquipmentRecord(@ModelAttribute("equipmentRecord") EquipmentRecord equipmentRecord,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		Query q = new Query(pageNo, pageSize, equipmentRecord);
		PageFinder<EquipmentRecord> equipmentRecordPage =  equipmentRecordService.getEquipmentRecordPagedList(q);
		return equipmentRecordPage;
	}
	
	/**
	 * 详情
	 * 
	 * @param equipmentRecordNo
	 * @return
	 */
	@RequestMapping("toEquipmentRecordView")
	public String toEquipmentRecordView(@ModelAttribute("equipmentRecord") EquipmentRecord equipmentRecord, Model model) {
		model.addAttribute("equipmentRecord", equipmentRecordService.getEquipmentRecord(equipmentRecord.getRecordId()));
		return "/equipment/equipmentRecord_view";
	}
	
	/**
	 * 查询列表（不带分页限制）
	 * 
	 * @param query
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("pageListEquipmentRecordByOrderNo")
	@ResponseBody
	public PageFinder<EquipmentRecord> pageListEquipmentRecordByOrderNo(String orderNo,
			@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize) throws ParseException {
		PageFinder<EquipmentRecord> equipmentRecordPage =  null;
		if(orderNo != null && !orderNo.equals("")){
			EquipmentRecord equipmentRecord = new EquipmentRecord();
			equipmentRecord.setOrderNo(orderNo);
			equipmentRecordPage =  equipmentRecordService.pageListEquipmentRecordByOrderNo(new Query(pageNo, pageSize, equipmentRecord));
		}else{
			equipmentRecordPage =  new PageFinder<EquipmentRecord>();
			equipmentRecordPage.setRowCount(0);
			equipmentRecordPage.setData(new ArrayList<EquipmentRecord>());
		}
		return equipmentRecordPage;
	}
	
	
	/*public PageFinder<EquipmentRecord> pageListAllEquipmentRecord(@ModelAttribute("equipmentRecord") EquipmentRecord equipmentRecord) throws ParseException {
		List<EquipmentRecord> equipmentRecordList =  equipmentRecordService.getEquipmentRecordList(new Query(equipmentRecord));
		PageFinder<EquipmentRecord> equipmentRecordPage =  new PageFinder<EquipmentRecord>();
		if(equipmentRecordList != null && !equipmentRecordList.isEmpty()){
			equipmentRecordPage.setData(equipmentRecordList);
			equipmentRecordPage.setPageCount(equipmentRecordList.size());
			equipmentRecordPage.setRowCount(equipmentRecordList.size());
		}else{
			equipmentRecordPage.setPageCount(0);
			equipmentRecordPage.setRowCount(0);
			equipmentRecordPage.setData(new ArrayList<EquipmentRecord>());
		}
		return equipmentRecordPage;
	}*/
}
