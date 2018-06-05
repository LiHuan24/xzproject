package cn.com.shopec.quartz.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.themepavilion.common.ThemePavilionConstant;
import cn.com.shopec.core.themepavilion.model.ArrayCourse;
import cn.com.shopec.core.themepavilion.service.ArrayCourseService;

/**
 * 主题馆课程定时发布 
 * 发布所有主题馆下的下周课程，每周五晚上22点执行
 * @author LiHuan 
 * Date 2017年11月16日 下午4:04:10
 */
public class StoreCoursePostedQuartz {

	private Log log = LogFactory.getLog(StoreCoursePostedQuartz.class);

	@Resource
	private SysParamService sysParamService;
	@Resource
	private ArrayCourseService arrayCourseService;
	@Resource
	private StoreService storeService;
	
	public void quartzStart() throws Exception {
		//获取主题馆课程发布时间参数
		SysParam param = sysParamService.getByParamKey("StoreCoursePostedQuartzParam");
		if(param != null && param.getParamValue() != null){
			//获取所有的主题馆
			Store store = new Store();
			store.setStoreStatus(Constant.STORE_START_STATUS);
			store.setStoreType(ThemePavilionConstant.THEME_STORE);
			//查询全部主题馆门店数据
			List<Store> listStore = storeService.getStoreList(new Query(store));
			if(listStore != null && listStore.size() >0){
				for (Store storeBean : listStore) {
					ArrayCourse arrayCourse = new ArrayCourse();
					arrayCourse.setStoreNo(storeBean.getStoreNo());
					//查询所有主题馆下周的课程数据
					arrayCourse.setCourseType(ThemePavilionConstant.NEXT_WEEK);
					List<ArrayCourse> listArrayCourse = arrayCourseService.getArrayCourseList(new Query(arrayCourse));
					if(listArrayCourse != null && listArrayCourse.size() >0){
						for (ArrayCourse course : listArrayCourse) {
							ArrayCourse courseBean = new ArrayCourse();
							courseBean.setArrayCourseNo(course.getArrayCourseNo());
							courseBean.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);//设置成发布状态
							courseBean.setUpdateTime(new Date());//设置更新时间
							arrayCourseService.updateArrayCourse(courseBean, null);
						}
					}else{
						//若该主题馆没有下周的排课数据，则继续沿用上一周的排课，进行发布并且设置课程周类型为下周
						ArrayCourse thisCourse = new ArrayCourse();
						thisCourse.setStoreNo(storeBean.getStoreNo());
						//查询所有主题馆下周的课程数据
						thisCourse.setCourseType(ThemePavilionConstant.THIS_WEEK);
						List<ArrayCourse> listThisArrayCourse = arrayCourseService.getArrayCourseList(new Query(arrayCourse));
						if(listThisArrayCourse != null && listThisArrayCourse.size() >0){
							for (ArrayCourse course : listThisArrayCourse) {
								ArrayCourse courseBean = new ArrayCourse();
								courseBean.setArrayCourseNo(course.getArrayCourseNo());
								courseBean.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_YES);//设置成发布状态
								courseBean.setUpdateTime(new Date());//设置更新时间
								courseBean.setCourseType(ThemePavilionConstant.NEXT_WEEK);
								arrayCourseService.updateArrayCourse(courseBean, null);
							}
						}
					}
				}
			}
		}
	}
	
	protected void execute(String arg0) throws Exception {
		try {
			log.info("--------主题馆执行课程发布，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			log.info("--------主题馆执行课程发布定时任务完成...");
		} catch (Exception e) {
			log.error("--------主题馆执行课程发布，错误信息：" + e.getMessage(), e);
		}
	}
}