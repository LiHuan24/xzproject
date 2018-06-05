package cn.com.shopec.mgt.equipment.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.common.EquipmentConstant;
import cn.com.shopec.core.equipment.model.EquipmentFeedback;
import cn.com.shopec.core.equipment.model.EquipmentRepair;
import cn.com.shopec.core.equipment.service.EquipmentFeedbackService;
import cn.com.shopec.core.equipment.service.EquipmentRepairService;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.mgt.common.BaseController;
/**
 * 共享健身房：设备报修业务控制
 * @author LiHuan
 * Date 2017年10月31日 下午3:27:45
 */
@Controller
@RequestMapping("equipmentFeedback")
public class EquipmentFeedbackController extends BaseController {
	
	private static final Log log = LogFactory.getLog(EquipmentFeedbackController.class);
	
	@Resource
	private EquipmentFeedbackService equipmentFeedbackService;
	@Resource
	private EquipmentRepairService equipmentRepairService;
	@Resource
	private DataDictItemService dataDictItemService;
	
	/**
	 * 加载设备报修列表页
	 */
	@RequestMapping("toEquipmentFeedbackList")
	public String toEquipmentFeedbackList(ModelMap model) {
		log.info("加载设备报修列表");
		// 数据字典项----维修类型
		List<DataDictItem> maintenanceTypes = dataDictItemService.getDataDictItemListByCatCode("MAINTENANCE_TYPES");
		model.put("maintenanceTypes", maintenanceTypes);
		return "/equipment/equipment_feedback_list";
	}
	
	/**
	 * 设备报修分页查询列表
	 * 
	 * @param equipmentFeedback
	 * @param pageSize
	 * @param pageNo
	 */
	@RequestMapping("equipmentFeedbackPageList")
	@ResponseBody
	public PageFinder<EquipmentFeedback> equipmentFeedbackPageList(
			@ModelAttribute("equipmentFeedback") EquipmentFeedback equipmentFeedback, @RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		log.info("获取设备报修列表分页数据");
		Query q = new Query(pageNo, pageSize, equipmentFeedback);
		return equipmentFeedbackService.getEquipmentFeedbackPagedList(q);
	}
	
	/**
	 * 设备报修数据转成设备维修记录数据
	 */
	@RequestMapping("/feedbackDataTurnEquipmentRepair")
	@ResponseBody
	public ResultInfo<EquipmentRepair> feedbackDataTurnEquipmentRepair(String equipmentFeedbackNo){
		ResultInfo<EquipmentRepair> resultInfo = new ResultInfo<EquipmentRepair>();
		//获取设备报修数据
		EquipmentFeedback equipmentFeedback = equipmentFeedbackService.getEquipmentFeedback(equipmentFeedbackNo);
		if(equipmentFeedback != null){
			EquipmentRepair equipmentRepair = new EquipmentRepair();
			equipmentRepair.setFitnessEquipmentNo(equipmentFeedback.getFitnessEquipmentNo());
			equipmentRepair.setCityId(equipmentFeedback.getCityId());
			equipmentRepair.setCityName(equipmentFeedback.getCityName());
			equipmentRepair.setStoreNo(equipmentFeedback.getStoreNo());
			equipmentRepair.setStoreName(equipmentFeedback.getStoreName());
			equipmentRepair.setRepairId(equipmentFeedback.getFeedbackId());
			equipmentRepair.setRecordName(equipmentFeedback.getFeedbackName());
			equipmentRepair.setRepairStatus(equipmentFeedback.getFeedbackStatus());
			equipmentRepair.setRepairPictureUrl1(equipmentFeedback.getFeedbackPictureUrl1());
			equipmentRepair.setMemo(equipmentFeedback.getMemo());
			resultInfo = equipmentRepairService.addEquipmentRepair(equipmentRepair, getOperator());
			if(resultInfo.getData() != null){
				equipmentFeedback.setTurnStatus(EquipmentConstant.TURN_STATUS_YES);
				equipmentFeedbackService.updateEquipmentFeedback(equipmentFeedback, getOperator());
				resultInfo.setCode(Constant.SECCUESS);
			}else{
				resultInfo.setCode(Constant.FAIL);
			}
		}
		return resultInfo;
	}
}