package cn.com.shopec.mapi.finace.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.common.DepositConstant;
import cn.com.shopec.core.finace.model.CoursePackage;
import cn.com.shopec.core.finace.model.GymCard;
import cn.com.shopec.core.finace.model.Recharge;
import cn.com.shopec.core.finace.service.CoursePackageService;
import cn.com.shopec.core.finace.service.GymCardService;
import cn.com.shopec.core.finace.service.RechargeService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.system.model.DataDictItem;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.DataDictItemService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.finace.vo.BusinessVo;

@Controller
@RequestMapping("/app/finace")
public class FinaceController extends BaseController {
	// 创建日志文件
	private static Logger log = Logger.getLogger(FinaceController.class);
	@Resource
	private SysParamService sysParamService;
	@Resource
	private RechargeService rechargeService;
	@Resource
	private DataDictItemService dataDictItemService;
	@Resource
	private GymCardService gymCardService;
	@Resource
	private CoursePackageService coursePackageService;
	@Value("${city_coding}")
	private String city_coding;

	/**
	 * 进入交押金页面
	 *
	 */
	@RequestMapping("toDeposit")
	@ResponseBody
	public ResultInfo<BusinessVo> toDeposit() {
		ResultInfo<BusinessVo> resultInfo = new ResultInfo<BusinessVo>();
		BusinessVo v = new BusinessVo();
		SysParam sysParam = sysParamService.getByParamKey(DepositConstant.deposit_amount_key);
		if (sysParam == null || sysParam.getParamValue() == null || sysParam.getParamValue().equals("")) {
			resultInfo.setCode(Constant.NO_RESULT);
			resultInfo.setMsg(Constant.NO_RECORD);
		} else {
			SysParam param = sysParamService.getByParamKey(MemberConstant.ZMXY_FRACTION);
			v.setPrice(sysParam.getParamValue());
			v.setBusinessNumber(param.getParamValue());
			resultInfo.setCode(Constant.SECCUESS);
			resultInfo.setData(v);
		}

		return resultInfo;
	}

	/**
	 * 进入课程包购买页面
	 *
	 */
	@RequestMapping("toCoursePackage")
	@ResponseBody
	public ResultInfo<List<BusinessVo>> toCoursePackage(String cityCoding) {
		// if (cityCoding == null || cityCoding.equals("")) {
		cityCoding = "233";
		// }
		ResultInfo<List<BusinessVo>> resultInfo = new ResultInfo<List<BusinessVo>>();

		List<BusinessVo> lr = new ArrayList<BusinessVo>();

		// 查询当前城市编码
		DataDictItem m = new DataDictItem();
		m.setDataDictCatCode("CITY");
		m.setInfo1(cityCoding);
		List<DataDictItem> ms = dataDictItemService.getDataDictItemList(new Query(m));
		if (ms.size() == 1) {
			CoursePackage r = new CoursePackage();
			r.setCityId(ms.get(0).getDataDictItemId());
			r.setIsEnable(1);
			r.setCencorStatus(1);
			List<CoursePackage> ls = coursePackageService.getCoursePackageList(new Query(r));
			if (ls.size() > 0) {
				for (CoursePackage c : ls) {
					BusinessVo v = new BusinessVo();
					v.setBusinessNo(c.getCoursePackageNo());
					v.setPrice(c.getPrice() + "");
					v.setBusinessNumber(c.getCourseNumber() + "");
					lr.add(v);
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg(Constant.YES_RECORD);
				resultInfo.setData(lr);

			} else {
				resultInfo.setCode(Constant.NO_RESULT);
				resultInfo.setMsg(Constant.NO_RECORD);
			}

		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("获取城市信息异常");
		}

		return resultInfo;
	}

	/**
	 * 进入健身卡购买页面
	 *
	 */
	@RequestMapping("toGymCard")
	@ResponseBody
	public ResultInfo<List<BusinessVo>> toGymCard(String cityCoding) {
		// if (cityCoding == null || cityCoding.equals("")) {
		cityCoding = "233";
		// }
		ResultInfo<List<BusinessVo>> resultInfo = new ResultInfo<List<BusinessVo>>();

		List<BusinessVo> lr = new ArrayList<BusinessVo>();

		// 查询当前城市编码
		DataDictItem m = new DataDictItem();
		m.setDataDictCatCode("CITY");
		m.setInfo1(cityCoding);
		List<DataDictItem> ms = dataDictItemService.getDataDictItemList(new Query(m));
		if (ms.size() == 1) {
			GymCard r = new GymCard();
			r.setCityId(ms.get(0).getDataDictItemId());
			r.setIsEnable(1);
			r.setCencorStatus(1);
			r.setGymCardType(1);
			List<GymCard> ls = gymCardService.getGymCardList(new Query(r));
			if (ls.size() > 0) {
				for (GymCard g : ls) {
					BusinessVo v = new BusinessVo();
					v.setBusinessNo(g.getGymCardNo());
					v.setPrice(g.getPrice() + "");
					lr.add(v);
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg(Constant.YES_RECORD);
				resultInfo.setData(lr);

			} else {
				resultInfo.setCode(Constant.NO_RESULT);
				resultInfo.setMsg(Constant.NO_RECORD);
			}

		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("获取城市信息异常");
		}

		return resultInfo;
	}

	/**
	 * 进入充值页面
	 *
	 */
	@RequestMapping("toRecharge")
	@ResponseBody
	public ResultInfo<List<BusinessVo>> toRecharge(String cityCoding) {
		// if (cityCoding == null || cityCoding.equals("")) {
		cityCoding = "233";
		// }
		ResultInfo<List<BusinessVo>> resultInfo = new ResultInfo<List<BusinessVo>>();

		List<BusinessVo> lr = new ArrayList<BusinessVo>();

		// 查询当前城市编码
		DataDictItem m = new DataDictItem();
		m.setDataDictCatCode("CITY");
		m.setInfo1(cityCoding);
		List<DataDictItem> ms = dataDictItemService.getDataDictItemList(new Query(m));
		if (ms.size() == 1) {
			Recharge r = new Recharge();
			r.setCityId(ms.get(0).getDataDictItemId());
			r.setIsEnable(1);
			r.setCencorStatus(1);
			List<Recharge> ls = rechargeService.getRechargeList(new Query(r));
			if (ls.size() > 0) {
				for (Recharge recharge : ls) {
					BusinessVo v = new BusinessVo();
					v.setBusinessNo(recharge.getRechargeNo());
					v.setPrice(recharge.getPrice() + "");
					lr.add(v);
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg(Constant.YES_RECORD);
				resultInfo.setData(lr);

			} else {
				resultInfo.setCode(Constant.NO_RESULT);
				resultInfo.setMsg(Constant.NO_RECORD);
			}

		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("获取城市信息异常");
		}

		return resultInfo;
	}

}
