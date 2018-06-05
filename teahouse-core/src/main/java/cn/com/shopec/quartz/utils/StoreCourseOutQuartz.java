package cn.com.shopec.quartz.utils;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.themepavilion.common.ThemePavilionConstant;
import cn.com.shopec.core.themepavilion.model.ArrayCourse;
import cn.com.shopec.core.themepavilion.service.ArrayCourseService;

/**
 * 主题馆本周课程修改为已过期数据
 * 每周日晚上23点执行一次
 * @author LiHuan
 * Date 2017年11月16日 下午5:30:20
 */
public class StoreCourseOutQuartz {

	private Log log = LogFactory.getLog(StoreCourseOutQuartz.class);
	
	@Resource
	private SysParamService sysParamService;
	@Resource
	private ArrayCourseService arrayCourseService;
	
	public void quartzStart() throws Exception {
		//获取主题馆本周课程过期时间参数
		SysParam param = sysParamService.getByParamKey("StoreCourseOutQuartzParam");
		ResultInfo<ArrayCourse> resultInfo = new ResultInfo<ArrayCourse>();
		if(param != null && param.getParamValue() != null){
			ArrayCourse courseOut = new ArrayCourse();
			courseOut.setCourseType(ThemePavilionConstant.THIS_WEEK);
			List<ArrayCourse> listArrayCourseOut = arrayCourseService.getArrayCourseList(new Query(courseOut));
			if(listArrayCourseOut != null && listArrayCourseOut.size() > 0){
				for (ArrayCourse arrayCourse : listArrayCourseOut) {
					ArrayCourse cBean = new ArrayCourse();
					cBean.setArrayCourseNo(arrayCourse.getArrayCourseNo());
					cBean.setPostedStatus(ThemePavilionConstant.POSTED_STATUS_NO);//修改成未发布状态
					cBean.setUpdateTime(new Date());//修改更新时间
					cBean.setCourseType(ThemePavilionConstant.EXPIRED);//主题馆本周课程已过期
					resultInfo = arrayCourseService.updateArrayCourse(cBean, null);
				}
			}
			if(resultInfo.getData() != null){
				ArrayCourse arrayCourse = new ArrayCourse();
				//查询所有主题馆下周的课程数据
				arrayCourse.setCourseType(ThemePavilionConstant.NEXT_WEEK);
				List<ArrayCourse> listArrayCourse = arrayCourseService.getArrayCourseList(new Query(arrayCourse));
				if(listArrayCourse != null && listArrayCourse.size() >0){
					for (ArrayCourse course : listArrayCourse) {
						ArrayCourse courseBean = new ArrayCourse();
						courseBean.setArrayCourseNo(course.getArrayCourseNo());
						courseBean.setUpdateTime(new Date());//设置更新时间
						courseBean.setCourseType(ThemePavilionConstant.THIS_WEEK);//主题馆下周课程转换成本周最新发布课程
						arrayCourseService.updateArrayCourse(courseBean, null);
					}
				}
			}
		}
	}
	
	protected void execute(String arg0) throws Exception {
		try {
			log.info("--------主题馆本周课程设置成已过期数据，开始时间：" + new Date());
			// 执行业务方法
			quartzStart();

			log.info("--------主题馆本周课程设置成已过期数据定时任务完成...");
		} catch (Exception e) {
			log.error("--------主题馆本周课程设置成已过期数据，错误信息：" + e.getMessage(), e);
		}
	}
}
