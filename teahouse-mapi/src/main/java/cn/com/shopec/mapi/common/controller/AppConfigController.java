package cn.com.shopec.mapi.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.themepavilion.model.CourseLabel;
import cn.com.shopec.core.themepavilion.service.CourseLabelService;
import cn.com.shopec.mapi.common.vo.AppCacheConfigVo;
import cn.com.shopec.mapi.theme.vo.CourseLabelVo;

/**
 * APP公用配置类
 * 
 * @author LiHuan 
 * Date 2017年12月5日 上午11:18:39
 */
@Controller
@RequestMapping("app/appConfig")
public class AppConfigController extends BaseController {

	private static final Log log = LogFactory.getLog(AppConfigController.class);
	@Resource
	private CourseLabelService courseLabelService;

	/**
	 * APP初始化访问时获取课程标签信息
	 */
	@RequestMapping("initAppConfig")
	@ResponseBody
	public ResultInfo<AppCacheConfigVo> initAppConfig() {
		log.info("初始化加载课程标签数据...");
		ResultInfo<AppCacheConfigVo> resultInfo = new ResultInfo<AppCacheConfigVo>();
		try {
			List<CourseLabelVo> listLabel = new ArrayList<CourseLabelVo>();
			//查询课程标签配置
			CourseLabel courseLabel = new CourseLabel();
			courseLabel.setIsDeleted(0);//查询未删除的标签数据
			List<CourseLabel> listLabels = courseLabelService.getCourseLabelList(new Query(courseLabel));
			if (listLabels != null && listLabels.size() > 0) {
				AppCacheConfigVo appVo = new AppCacheConfigVo();
				for (CourseLabel label : listLabels) {
					CourseLabelVo courseLabelVo = new CourseLabelVo();
					courseLabelVo.setLabelNo(label.getCourseLabelNo());
					courseLabelVo.setLabelName(label.getLabelName());
					listLabel.add(courseLabelVo);
				}
				appVo.setListCourseLabelVo(listLabel);
				
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg(Constant.YES_RECORD);
				resultInfo.setData(appVo);
			} else {
				resultInfo.setCode(Constant.NO_RESULT);
				resultInfo.setMsg(Constant.NO_RECORD);
			}
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(e.toString());
		}
		return resultInfo;
	}

}