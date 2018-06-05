package cn.com.shopec.mapi.finace.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.finace.dao.DepositOrderDao;
import cn.com.shopec.core.finace.model.DepositOrder;
import cn.com.shopec.core.finace.model.DepositRefund;
import cn.com.shopec.core.finace.model.DepositStatus;
import cn.com.shopec.core.finace.service.DepositRefundService;
import cn.com.shopec.core.finace.service.DepositStatusService;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.order.model.CommunityOrder;
import cn.com.shopec.core.order.service.CommunityOrderService;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.finace.vo.DepositRefundVo;
import cn.com.shopec.mapi.finace.vo.MemberDepositRefundVo;
import cn.com.shopec.mapi.theme.controller.ThemeController;

/**
 * 押金退款申请
 */
@Controller
@RequestMapping("/app/depositRefund")
public class DepositRefundController extends BaseController {
	private static Logger log = Logger.getLogger(ThemeController.class);

	@Resource
	private DepositRefundService depositRefundService;
	@Resource
	private CommunityOrderService communityOrderService;
	@Resource
	private MemberService memberService;
	@Resource
	private DepositOrderDao depositOrderDao;
	@Resource
	private DepositStatusService depositStatusService;

	/**
	 * 提交押金退款申请、添加押金退款申请记录
	 */
	@RequestMapping("/depositRefundApply")
	@ResponseBody
	public ResultInfo<String> depositRefundApply(String memberNo) {
		log.info("进入押金退款申请接口... ...");
		ResultInfo<String> result = new ResultInfo<String>();
		try {
			// 押金退款申请前先判断该用户在社区馆有没有进行中的订单
			CommunityOrder communityOrder = new CommunityOrder();
			communityOrder.setMemberNo(memberNo);
			communityOrder.setOrderStatus(0);// 进行中
			List<CommunityOrder> listCommunityOrder = communityOrderService.getCommunityOrderList(new Query(communityOrder));
			if (listCommunityOrder.size() > 0) {
				result.setCode(Constant.FAIL);
				result.setMsg("对不起，您有一个进行中的社区订单，暂时不能进行押金退款申请操作！");
			} else {
				DepositStatus ds = depositStatusService.getDepositStatus(memberNo);
				if (ds.getDepositStatus() == 2) {
					result.setCode(Constant.FAIL);
					result.setMsg("请求重复！");
					return null;
				}

				// 查询目前可用的押金支付订单数据
				DepositOrder depositOrder = depositOrderDao.getAvailabilityDepositOrder(memberNo);
				DepositRefund depositRefund = new DepositRefund();
				depositRefund.setRefundNo(String.valueOf(System.nanoTime()));
				if (depositOrder != null) {
					depositRefund.setDepositOrderNo(depositOrder.getDepositOrderNo());
					depositRefund.setRefundAmount(depositOrder.getDepositAmount());
					depositRefund.setRefundStatus(0);// 未退款
					depositRefund.setRefundMethod(depositOrder.getPaymentMethod());
					depositRefund.setApplyTime(new Date());
					if (StringUtils.isNotBlank(memberNo)) {
						depositRefund.setMemberNo(memberNo);
						Member member = memberService.getMember(memberNo);
						if (member != null) {
							depositRefund.setMobilePhone(member.getMobilePhone());
							depositRefund.setMemberName(member.getMemberName());
						}
					}
				}
				depositRefundService.addDepositRefund(depositRefund, getOperator());
				// 修改客户押金状态

				if (ds != null) {
					ds.setDepositStatus(2);
					depositStatusService.updateDepositStatus(ds, null);

				}

				result.setCode(Constant.SECCUESS);
				result.setMsg("请求成功！");
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.toString());
		}
		return result;
	}

	/**
	 * 会员退款记录
	 */
	@RequestMapping("/memberDepositRefund")
	@ResponseBody
	public ResultInfo<List<MemberDepositRefundVo>> memberDepositRefund(String memberNo) {
		ResultInfo<List<MemberDepositRefundVo>> resultInfo = new ResultInfo<List<MemberDepositRefundVo>>();

		List<MemberDepositRefundVo> vs = new ArrayList<MemberDepositRefundVo>();

		try {
			DepositRefund r = new DepositRefund();
			r.setMemberNo(memberNo);
			List<DepositRefund> ls = depositRefundService.getDepositRefundList(new Query(r));

			if (ls.size() > 0) {
				for (DepositRefund d : ls) {
					MemberDepositRefundVo v = new MemberDepositRefundVo();
					v.setApplyTime(ECDateUtils.formatDate(d.getApplyTime(), "yyyy-MM-dd HH:mm"));
					v.setRefundAmount(d.getRefundAmount() + "");
					if (d.getRefundStatus() == 1) {
						v.setCencorStatus("已退款");
					} else if (d.getCencorStatus() == 0) {
						v.setCencorStatus("未审核");
					} else if (d.getCencorStatus() == 1) {
						v.setCencorStatus("已审核");
					} else if (d.getCencorStatus() == 3) {
						v.setCencorStatus("未通过");
					}
					vs.add(v);
				}
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg(Constant.YES_RECORD);
				resultInfo.setData(vs);
			} else {
				resultInfo.setCode(Constant.NO_RESULT);
				resultInfo.setMsg(Constant.NO_RECORD);
			}
		} catch (Exception e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}
		return resultInfo;

	}

	/**
	 * 查询该退款状态的数据
	 */
	@RequestMapping("/viewDepositRefund")
	@ResponseBody
	public ResultInfo<DepositRefundVo> viewDepositRefund(String refundNo) {
		ResultInfo<DepositRefundVo> result = new ResultInfo<DepositRefundVo>();
		try {
			DepositRefund depositRefund = depositRefundService.getDepositRefund(refundNo);
			if (depositRefund != null) {
				DepositRefundVo depositRefundVo = new DepositRefundVo();
				depositRefundVo.setApplyTime(depositRefund.getApplyTime());
				depositRefundVo.setDepositOrderNo(depositRefund.getDepositOrderNo());
				depositRefundVo.setMemberName(depositRefund.getMemberName());
				depositRefundVo.setMemberNo(depositRefund.getMemberNo());
				depositRefundVo.setMobilePhone(depositRefund.getMobilePhone());
				depositRefundVo.setRefundAmount(depositRefund.getRefundAmount());
				depositRefundVo.setRefundNo(depositRefund.getRefundNo());
				depositRefundVo.setRefundStatus(depositRefund.getRefundStatus());

				result.setData(depositRefundVo);
				result.setCode(Constant.SECCUESS);
				result.setMsg("请求成功！");
			} else {
				result.setCode(Constant.FAIL);
				result.setMsg("暂无记录！");
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.toString());
		}
		return result;
	}
}