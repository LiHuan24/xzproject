package cn.com.shopec.core.equipment.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.dao.EquipmentModelDao;
import cn.com.shopec.core.equipment.model.EquipmentModel;
import cn.com.shopec.core.equipment.service.EquipmentModelService;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;


/**
 * 设备型号表 服务实现类
 */
@Service
public class EquipmentModelServiceImpl implements EquipmentModelService {

	private static final Log log = LogFactory.getLog(EquipmentModelServiceImpl.class);
	
	@Resource
	private EquipmentModelDao equipmentModelDao;
	@Resource
	private PrimarykeyService primarykeyService;
	

	/**
	 * 根据查询条件，查询并返回EquipmentModel的列表
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<EquipmentModel> getEquipmentModelList(Query q) {
		List<EquipmentModel> list = null;
		try {
			//已删除的不查出
			EquipmentModel equipmentModel = (EquipmentModel)q.getQ();
			if (equipmentModel != null) {
				equipmentModel.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//直接调用Dao方法进行查询
			list = equipmentModelDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<EquipmentModel>(0) : list;
		return list; 
	}
	
	/**
	 * 根据查询条件，分页查询并返回EquipmentModel的分页结果
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<EquipmentModel> getEquipmentModelPagedList(Query q) {
		PageFinder<EquipmentModel> page = new PageFinder<EquipmentModel>();
		
		List<EquipmentModel> list = null;
		long rowCount = 0L;
		
		try {
			//已删除的不查出
			EquipmentModel equipmentModel = (EquipmentModel)q.getQ();
			if (equipmentModel != null) {
				equipmentModel.setIsDeleted(Constant.DEL_STATE_NO);
			}
					
			//调用dao查询满足条件的分页数据
			list = equipmentModelDao.pageList(q);
			//调用dao统计满足条件的记录总数
			rowCount = equipmentModelDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<EquipmentModel>(0) : list;
	
		//将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);
		
		return page;
	}	
	
	/**
	 * 根据主键，查询并返回一个EquipmentModel对象
	 * @param id 主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public EquipmentModel getEquipmentModel(String id) {
		EquipmentModel obj = null;
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			//调用dao，根据主键查询
			obj = equipmentModelDao.get(id); 
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组EquipmentModel对象
	 * @param ids 主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<EquipmentModel> getEquipmentModelByIds(String[] ids) {
		List<EquipmentModel> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				//调用dao，根据主键数组查询
				list = equipmentModelDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		
		//如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<EquipmentModel>(0) : list;

		return list;
	}	
	
	/**
	 * 根据主键，删除特定的EquipmentModel记录
	 * @param id 主键id
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<EquipmentModel> delEquipmentModel(String id, Operator operator) {
		ResultInfo<EquipmentModel> resultInfo = new ResultInfo<EquipmentModel>();
		if (id == null || id.length() == 0) { //传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			//逻辑删除，实际上执行update语句，以下设置待更新记录的主键、删除标识、更新时间、操作人信息等
			EquipmentModel equipmentModel = new EquipmentModel();
			equipmentModel.setModelNo(id);
			equipmentModel.setIsDeleted(Constant.DEL_STATE_YES);
			equipmentModel.setUpdateTime(new Date());
			if (operator != null) { //最近操作人
				equipmentModel.setOperatorType(operator.getOperatorType());
				equipmentModel.setOperatorId(operator.getOperatorId());
			}
			
			//调用Dao执行更新操作，并判断更新语句执行结果
			int count = equipmentModelDao.update(equipmentModel);			
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
			resultInfo.setData(equipmentModel);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}
		
	/**
	 * 新增一条EquipmentModel记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * @param equipmentModel 新增的EquipmentModel数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<EquipmentModel> addEquipmentModel(EquipmentModel equipmentModel, Operator operator) {
		ResultInfo<EquipmentModel> resultInfo = new ResultInfo<EquipmentModel>();
		
		if (equipmentModel == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " equipmentModel = " + equipmentModel);
		} else {
			try {
				//如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (equipmentModel.getModelNo() == null) {
					equipmentModel.setModelNo(this.generatePK());
				}
				//如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					equipmentModel.setOperatorType(operator.getOperatorType());
					equipmentModel.setOperatorId(operator.getOperatorId());
				}
				
				//设置创建时间和更新时间为当前时间
				Date now = new Date();
				equipmentModel.setCreateTime(now);
				equipmentModel.setUpdateTime(now);
				
				//填充默认值
				this.fillDefaultValues(equipmentModel);
				
				//调用Dao执行插入操作
				equipmentModelDao.add(equipmentModel);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(equipmentModel);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}	
		}
		
		return resultInfo;
	}			
	
	/**
	 * 根据主键，更新一条EquipmentModel记录
	 * @param equipmentModel 更新的EquipmentModel数据，且其主键不能为空
	 * @param operator 操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<EquipmentModel> updateEquipmentModel(EquipmentModel equipmentModel, Operator operator) {
		ResultInfo<EquipmentModel> resultInfo = new ResultInfo<EquipmentModel>();
		
		if (equipmentModel == null || equipmentModel.getModelNo() == null) { //传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG );
			log.info(Constant.ERR_MSG_INVALID_ARG + " equipmentModel = " + equipmentModel);
		} else {
			try {
				//如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					equipmentModel.setOperatorType(operator.getOperatorType());
					equipmentModel.setOperatorId(operator.getOperatorId());
				}
				
				//设置更新时间为当前时间
				equipmentModel.setUpdateTime(new Date());
				
				//调用Dao执行更新操作，并判断更新语句执行结果
				int count = equipmentModelDao.update(equipmentModel);			
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(equipmentModel);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
			}
		}
		
		return resultInfo;
	}	
	
	/**
	 * 生成主键
	 * @return
	 */
	public String generatePK() {
		return "SBXH"+primarykeyService.getValueByBizType(PrimarykeyConstant.equipmentModel);
	}
	
	/**
	 * 为EquipmentModel对象设置默认值
	 * @param obj
	 */
	public void fillDefaultValues(EquipmentModel obj) {
		if (obj != null) {
		    if (obj.getIsDeleted() == null) {
		    	obj.setIsDeleted(0);
		    }
		}
	}

}
