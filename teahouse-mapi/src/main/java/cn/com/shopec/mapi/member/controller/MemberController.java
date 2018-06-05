package cn.com.shopec.mapi.member.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.antgroup.zmxy.openplatform.api.response.ZhimaAuthInfoAuthqueryResponse;
import com.fasterxml.jackson.databind.util.LRUMap;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.cache.CommonCacheUtil;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.sendmsg.MsgConstant;
import cn.com.shopec.common.sendmsg.SendMsgCommonInterfaceService;
import cn.com.shopec.common.utils.AESCipher;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECMd5Utils;
import cn.com.shopec.common.utils.ECRandomUtils;
import cn.com.shopec.common.utils.GenerateInvitationCodeUtil;
import cn.com.shopec.common.zmxy.ZhimaUtil;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.equipment.model.EquipmentFeedback;
import cn.com.shopec.core.equipment.model.EquipmentRecord;
import cn.com.shopec.core.equipment.model.FitnessEquipment;
import cn.com.shopec.core.equipment.service.EquipmentFeedbackService;
import cn.com.shopec.core.equipment.service.EquipmentRecordService;
import cn.com.shopec.core.equipment.service.FitnessEquipmentService;
import cn.com.shopec.core.finace.model.Accounts;
import cn.com.shopec.core.finace.model.DepositStatus;
import cn.com.shopec.core.finace.service.AccountsService;
import cn.com.shopec.core.finace.service.DepositStatusService;
import cn.com.shopec.core.marketing.model.Coupon;
import cn.com.shopec.core.marketing.model.CouponPlan;
import cn.com.shopec.core.marketing.model.RedeemCode;
import cn.com.shopec.core.marketing.service.CouponPlanService;
import cn.com.shopec.core.marketing.service.CouponService;
import cn.com.shopec.core.marketing.service.RedeemCodeService;
import cn.com.shopec.core.member.common.MemberConstant;
import cn.com.shopec.core.member.model.Member;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.member.model.MemberGymCard;
import cn.com.shopec.core.member.model.MemberLevel;
import cn.com.shopec.core.member.model.MemberZhimaScore;
import cn.com.shopec.core.member.service.MemberBalanceService;
import cn.com.shopec.core.member.service.MemberGymCardService;
import cn.com.shopec.core.member.service.MemberLevelService;
import cn.com.shopec.core.member.service.MemberService;
import cn.com.shopec.core.member.service.MemberZhimaScoreService;
import cn.com.shopec.core.member.vo.MrVo;
import cn.com.shopec.core.member.vo.WalletVo;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.model.ThemeOrder;
import cn.com.shopec.core.order.service.ThemeOrderService;
import cn.com.shopec.core.storemanagement.model.Store;
import cn.com.shopec.core.storemanagement.service.StoreService;
import cn.com.shopec.core.system.model.SysParam;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.themepavilion.model.ArrayCourse;
import cn.com.shopec.core.themepavilion.model.Coach;
import cn.com.shopec.core.themepavilion.model.Course;
import cn.com.shopec.core.themepavilion.model.SignAppraise;
import cn.com.shopec.core.themepavilion.service.ArrayCourseService;
import cn.com.shopec.core.themepavilion.service.CoachService;
import cn.com.shopec.core.themepavilion.service.CourseService;
import cn.com.shopec.core.themepavilion.service.SignAppraiseService;
import cn.com.shopec.mapi.common.TokenGeneratorUtil;
import cn.com.shopec.mapi.common.controller.BaseController;
import cn.com.shopec.mapi.common.filter.RandomValidateCode;
import cn.com.shopec.mapi.member.vo.AccountsVo;
import cn.com.shopec.mapi.member.vo.CoachCourseDetailVo;
import cn.com.shopec.mapi.member.vo.CoachCourseVo;
import cn.com.shopec.mapi.member.vo.CouponsVo;
import cn.com.shopec.mapi.member.vo.FitnessRecordLsVo;
import cn.com.shopec.mapi.member.vo.FitnessRecordVo;
import cn.com.shopec.mapi.member.vo.MemberInfoVo;
import cn.com.shopec.mapi.member.vo.MemberThemeOrderVo;

@Controller
@RequestMapping("/app/member")
public class MemberController extends BaseController {
	// 创建日志文件
	private static Logger log = Logger.getLogger(MemberController.class);
	@Resource
	private MemberService memberService;
	@Resource
	private MemberLevelService memberLevelService;
	@Resource
	private SysParamService sysParamService;
	@Resource
	private SendMsgCommonInterfaceService sendMsgCommonInterfaceService;
	@Resource
	private MemberBalanceService memberBalanceService;
	@Resource
	private MemberGymCardService memberGymCardService;
	@Resource
	private CouponService couponService;
	@Resource
	private MemberZhimaScoreService memberZhimaScoreService;
	@Resource
	private DepositStatusService depositStatusService;
	@Resource
	private AccountsService accountsService;
	@Resource
	private ThemeOrderService themeOrderService;
	@Resource
	private CourseService courseService;
	@Resource
	private ArrayCourseService arrayCourseService;
	@Resource
	private EquipmentRecordService equipmentRecordService;
	@Resource
	private RedeemCodeService redeemCodeService;
	@Resource
	private FitnessEquipmentService fitnessEquipmentService;
	@Resource
	private EquipmentFeedbackService equipmentFeedbackService;
	@Resource
	private SignAppraiseService signAppraiseService;
	@Resource
	private CoachService coachService;
	@Resource
	private StoreService storeService;
	@Resource
	private CouponPlanService couponPlanService;
	@Resource
	private CommonCacheUtil cacheUtil;
	@Value("${image_path}")
	private String imgPath;
	@Value("${res_img_path}")
	private String resImgPath;

	/**
	 * 会员上课签到
	 * 
	 * @param memberNo会员编号
	 * @param themeOrderNo订单编号
	 * @param coachNo教练编号
	 * @param arrayCourseNo排课编号
	 */
	@RequestMapping("memberSignOperation")
	@ResponseBody
	public ResultInfo<String> memberSignOperation(String memberNo, String coachNo, String themeOrderNo,
			String arrayCourseNo) {
		ResultInfo<String> result = new ResultInfo<String>();
		try {
			if (StringUtils.isNotBlank(memberNo) && StringUtils.isNotBlank(coachNo)) {
				// 签到之前查询教练编号是否匹配
				ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
				if (arrayCourse.getCoachNo().equals(coachNo)) {
					// 会员签到之前查询教练是否已经上课签到
					SignAppraise signAppraise = signAppraiseService.getSignAppraiseStatus(coachNo, arrayCourseNo);
					if (signAppraise.getIsSign() == 1) {
						if (signAppraise.getSignStatus() == 1) {// 已审核结束的课程不能再进行签到
							result.setCode(Constant.FAIL);
							result.setMsg("很抱歉！该课程上课已结束，您不能进行上课签到操作。");
						} else {
							// 教练已上课签到
							// 根据会员编号、订单编号、排课编号和所属教练编号查询当前订单信息
							ThemeOrder themeOrder = new ThemeOrder();
							themeOrder.setMemberNo(memberNo);
							themeOrder.setThemeOrderNo(themeOrderNo);
							themeOrder.setArrangeNo(arrayCourseNo);
							themeOrder.setCoachNo(coachNo);
							themeOrder.setOrderStatus(Integer.parseInt(OrderConstant.yuyueOrder_code));// 已预约
							List<ThemeOrder> listOrder = themeOrderService.getThemeOrderList(new Query(themeOrder));
							if (listOrder != null && listOrder.size() > 0) {
								for (ThemeOrder theme : listOrder) {
									// 修改订单状态为进行中、已签到
									ThemeOrder order = new ThemeOrder();
									order.setThemeOrderNo(theme.getThemeOrderNo());
									order.setIsSign(OrderConstant.ORDER_SIGN_YES);
									order.setOrderStatus(Integer.parseInt(OrderConstant.jinxzOrder_code));
									themeOrderService.updateThemeOrder(order, getOperator());

									// 修改完订单后 新增会员签到记录数据
									SignAppraise signMember = new SignAppraise();
									signMember.setSiapId(signAppraiseService.generatePK());
									signMember.setIsSign(1);// 会员已签到
									signMember.setSignType(0);// 会员签到
									signMember.setSignDate(new Date());
									signMember.setMemberNo(memberNo);
									signMember.setCoachNo(coachNo);
									signMember.setCourseNo(theme.getCourseNo());
									signMember.setThemeOrderNo(themeOrderNo);
									signMember.setArrayCourseNo(arrayCourseNo);
									signMember.setCourseDate(order.getClassTime());
									signMember.setCityId(order.getCityId());
									signMember.setCityName(order.getCityName());
									signMember.setStoreNo(order.getStoreNo());
									signMember.setStoreName(order.getStoreName());
									Member member = memberService.getMember(memberNo);
									if (member != null) {
										signMember.setMemberName(member.getMemberName());
									}
									if (arrayCourse != null) {
										signMember.setChineseName(arrayCourse.getChineseName());
										signMember.setCoachname(arrayCourse.getCoachName());
									}
									signAppraiseService.addSignAppraise(signMember, getOperator());
								}
								result.setCode(Constant.SECCUESS);
								result.setMsg(Constant.YES_RECORD);
							} else {
								result.setCode(Constant.NO_RESULT);
								result.setMsg("很抱歉！该会员暂无已预约的课程数据。");
							}
						}
					} else {
						result.setCode(Constant.FAIL);
						result.setMsg("很抱歉！该课程教练还未签到，您不能进行上课签到操作。");
					}
				} else {
					result.setCode(Constant.FAIL);
					result.setMsg("很抱歉！扫描教练二维码不匹配，请核对教练二维码！。");
				}
			} else {
				result.setCode(Constant.NO_RESULT);
				result.setMsg(Constant.NO_RECORD);
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 * 教练上课签到&教练上课完成
	 * 
	 * @param coachNo教练编号
	 * @param arrayCourseNo课程排课编号
	 */
	@RequestMapping("coachSignOperation")
	@ResponseBody
	public ResultInfo<String> coachSignOperation(String coachNo, String arrayCourseNo) {
		ResultInfo<String> result = new ResultInfo<String>();
		try {
			if (StringUtils.isNotBlank(coachNo) && StringUtils.isNotBlank(arrayCourseNo)) {
				// 教练点击APP端签到按钮之后生成教练课程签到数据
				SignAppraise signAppraise = signAppraiseService.getSignAppraiseStatus(coachNo, arrayCourseNo);
				if (signAppraise != null) {
					// 如果已完成签到，将在上课结束后点击完成上课按钮操作
					if (signAppraise.getIsSign() == 1) {
						SignAppraise singBean = new SignAppraise();
						singBean.setSiapId(signAppraise.getSiapId());
						singBean.setIsFinish(1);// 已完成
						signAppraiseService.updateSignAppraise(singBean, getOperator());

						result.setCode(Constant.SECCUESS);
						result.setMsg(Constant.YES_RECORD);
					}
				} else {
					// 教练未签到，则需要在上课30分钟之内完成签到
					// 查询课时上课时间
					ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
					if (arrayCourse != null) {
						ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(arrayCourse.getStoreNo(),
								arrayCourse.getFtlRow(), arrayCourse.getCourseType());
						Date corseDate = ECDateUtils.formatDateToDateNew(arrayCourse.getCourseDate());// 上课日期
						String courseTime = ECDateUtils.getCurrentDateAsString(corseDate);// 上课日期格式化
						String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());// 上课开始时间
						String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());// 上课结束时间
						String nowTime = courseTime + " " + startTime + ":00";// 上课开始日期
						String paramTime = sysParamService.getByParamKey("COACH_SING_TIME").getParamValue();// 系统参数：上课时间30分钟之内才可进行签到（教练）
						Date classDate = ECDateUtils.parseTime(nowTime);
						int paramMinutes = ECDateUtils.differMinutes(new Date(), classDate).intValue();
						if (paramMinutes <= Integer.parseInt(paramTime)) {
							SignAppraise signAppraiseBean = new SignAppraise();
							signAppraiseBean.setSiapId(signAppraiseService.generatePK());
							signAppraiseBean.setArrayCourseNo(arrayCourseNo);
							signAppraiseBean.setCoachNo(coachNo);
							signAppraiseBean.setChineseName(arrayCourse.getChineseName());
							signAppraiseBean.setCityId(arrayCourse.getCityId());
							signAppraiseBean.setCourseNo(arrayCourse.getCourseNo());
							signAppraiseBean.setCourseDate(courseTime + " " + startTime + "-" + endTime);
							signAppraiseBean.setSignDate(new Date());
							signAppraiseBean.setStoreNo(arrayCourse.getStoreNo());
							signAppraiseBean.setStoreName(arrayCourse.getStoreName());
							signAppraiseBean.setCoachname(arrayCourse.getCoachName());
							signAppraiseBean.setIsSign(1);// 教练已签到
							signAppraiseBean.setSignType(1);// 教练签到
							signAppraiseBean.setIsFinish(0);// 未完成上课
							signAppraiseBean.setSignStatus(0);// 未审核
							signAppraiseService.addSignAppraise(signAppraiseBean, getOperator());

							result.setCode(Constant.SECCUESS);
							result.setMsg(Constant.YES_RECORD);
						} else if (new Date().getTime() > classDate.getTime()) {
							// 已过上课时间，这个点不能去进行签到操作
							result.setCode(Constant.FAIL);
							result.setMsg("对不起、已过该课程上课时间，不能签到上课！");
						} else {
							result.setCode(Constant.FAIL);
							result.setMsg("对不起、教练签到只能在上课开始时间" + paramTime + "分钟之内！");
						}
					}
				}
			} else {
				result.setCode(Constant.NO_RESULT);
				result.setMsg(Constant.NO_RECORD);
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.getMessage());
		}
		return result;
	}

	// 处理时间
	private Date handlingClassTime(String classTime) {
		String times = classTime.substring(0, 10);
		String hour = classTime.substring(classTime.length() - 5);
		String courseTime = times + " " + hour + ":00";
		Date courseDate = null;
		try {
			courseDate = ECDateUtils.parseTime(courseTime);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return courseDate;
	}

	/**
	 * 查询教练的所属上课数据
	 * 
	 * @param pageNo页码
	 * @param memberNo教练编号
	 */
	@RequestMapping("toCoachCourseList")
	@ResponseBody
	public ResultInfo<List<CoachCourseVo>> toCoachCourseList(Integer pageNo, String memberNo) {
		ResultInfo<List<CoachCourseVo>> result = new ResultInfo<List<CoachCourseVo>>();
		try {
			if (StringUtils.isNotBlank(memberNo)) {
				Member member = memberService.getMember(memberNo);
				if (member != null) {
					// 上课时间
					Date courseDate = null;
					Coach coach = coachService.getCoachByMemberPhone(member.getMobilePhone());
					if (coach != null) {
						if (coach.getCoachStatus() == 1) {// 教练已启用
							// 查询教练的课程
							ArrayCourse arrayCourse = new ArrayCourse();
							arrayCourse.setCoachNo(coach.getCoachNo());
							arrayCourse.setPostedStatus(1);// 已发布
							Query q = new Query(pageNo, 10, arrayCourse);
							PageFinder<ArrayCourse> pageList = arrayCourseService.getArrayCoursePagedList(q);
							if (pageList != null && pageList.getData() != null) {
								List<CoachCourseVo> listCoachCourse = new ArrayList<CoachCourseVo>();
								for (ArrayCourse course : pageList.getData()) {
									CoachCourseVo coachCourse = new CoachCourseVo();
									coachCourse.setCoachNo(course.getCoachNo());
									coachCourse.setArrayCourseNo(course.getArrayCourseNo());
									coachCourse.setCourseNo(course.getCourseNo());
									coachCourse.setCourseName(course.getChineseName());
									coachCourse.setClassNumber(String.valueOf(course.getPeopleNumber()));
									coachCourse.setStoreNo(course.getStoreNo());
									coachCourse.setStoreName(course.getStoreName());
									ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(
											course.getStoreNo(), course.getFtlRow(), course.getCourseType());
									if (courseBeans != null) {
										Date corseDate = ECDateUtils.formatDateToDateNew(course.getCourseDate());
										String classTime = ECDateUtils.getCurrentDateAsString(corseDate);
										String startTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseStart());
										String endTime = ECDateUtils.farmatDateToHM(courseBeans.getCourseEnd());
										String courseTime = classTime + " " + startTime + "-" + endTime;
										courseDate = this.handlingClassTime(courseTime);
										coachCourse.setClassTime(classTime + " " + startTime + "-" + endTime);
									} else {
										coachCourse.setClassTime("");
									}
									// 查询签到人数
									int signNumber = 0;
									int reslSignNum = 0;
									ThemeOrder themeOrder = new ThemeOrder();
									themeOrder.setCoachNo(course.getCoachNo());
									themeOrder.setArrangeNo(course.getArrayCourseNo());
									List<ThemeOrder> listOrder = themeOrderService
											.getThemeOrderList(new Query(themeOrder));
									if (listOrder != null && listOrder.size() > 0) {
										for (ThemeOrder order : listOrder) {
											if (order.getIsSign() == 1) {
												if (order.getCancelNumber() == null) {
													reslSignNum = order.getPeopleNumber();
												} else {
													reslSignNum = order.getPeopleNumber() - order.getCancelNumber();
												}
												signNumber += reslSignNum;
											}
											coachCourse.setSignNumber(String.valueOf(signNumber));
										}
									} else {
										coachCourse.setSignNumber(String.valueOf(signNumber));
									}
									// 查询上课状态
									SignAppraise signAppraise = signAppraiseService
											.getSignAppraiseStatus(coach.getCoachNo(), course.getArrayCourseNo());
									if (signAppraise != null) {
										if (signAppraise.getIsSign() != null && signAppraise.getSignStatus() != null) {
											if (signAppraise.getIsSign() == 0) {// 未签到
												coachCourse.setCourseStatus(0);// 未上课
											} else {
												if (signAppraise.getSignStatus() == 1) {// 已审核
													coachCourse.setCourseStatus(2);// 已结束
												} else {
													if (new Date().getTime() > courseDate.getTime()) {
														coachCourse.setCourseStatus(3);// 待审核
													} else {
														coachCourse.setCourseStatus(1);// 进行中
													}
												}
											}
										} else {
											coachCourse.setCourseStatus(0);// 未上课
										}
									} else if (new Date().getTime() > courseDate.getTime()) {
										coachCourse.setCourseStatus(2);// 已结束
									} else {
										coachCourse.setCourseStatus(0);// 未上课
									}
									listCoachCourse.add(coachCourse);
								}
								result.setCode(Constant.SECCUESS);
								result.setMsg(Constant.YES_RECORD);
								result.setData(listCoachCourse);
							} else {
								result.setCode(Constant.NO_RESULT);
								result.setMsg(Constant.NO_RECORD);
							}
						}
					} else {
						result.setCode(Constant.NO_RESULT);
						result.setMsg(Constant.NO_RECORD);
					}
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 * 去教练课程详情页
	 * 
	 * @param coachNo教练编号
	 * @param arrayCourseNo排课编号
	 * @param courseStatus上课状态
	 */
	@RequestMapping("toCoachCourseDetail")
	@ResponseBody
	public ResultInfo<CoachCourseDetailVo> toCoachCourseDetail(String coachNo, String arrayCourseNo,
			String courseStatus) {
		ResultInfo<CoachCourseDetailVo> result = new ResultInfo<CoachCourseDetailVo>();
		try {
			ArrayCourse arrayCourse = arrayCourseService.getArrayCourse(arrayCourseNo);
			if (arrayCourse != null) {
				// 查询门店数据
				Store store = storeService.getStore(arrayCourse.getStoreNo());
				// 查询课程
				Course course = courseService.getCourse(arrayCourse.getCourseNo());
				CoachCourseDetailVo courseDetailVo = new CoachCourseDetailVo();
				courseDetailVo.setCoachNo(coachNo);
				courseDetailVo.setArrayCourseNo(arrayCourseNo);
				if (course != null) {
					String courseSynopsis = course.getSynopsis().replaceAll("\r|\n|\t", "").replace(" ", "");
					courseDetailVo.setCourseSynopsis(courseSynopsis);
					if (StringUtils.isNotBlank(course.getCoursePictureUrl1())) {
						String[] coursePic = course.getCoursePictureUrl1().split(",");
						courseDetailVo.setCoursePhotoUrl(imgPath + "/" + coursePic[0]);
					} else {
						courseDetailVo.setCoursePhotoUrl("");
					}
				}
				if (store != null) {
					courseDetailVo.setStoreName(store.getStoreName());
					courseDetailVo.setStoreAddress(store.getAddrStreet());
				} else {
					courseDetailVo.setStoreName("");
					courseDetailVo.setStoreAddress("");
				}
				// 上课状态
				SignAppraise signAppraise = signAppraiseService.getSignAppraiseStatus(coachNo, arrayCourseNo);
				if (signAppraise == null) {
					courseDetailVo.setCourseStatus(Integer.parseInt(courseStatus));
				} else {
					if (signAppraise.getIsSign() == 1) {
						if (signAppraise.getIsFinish() == 1) {
							courseDetailVo.setCourseStatus(2);// 已结束
						} else {
							courseDetailVo.setCourseStatus(1);// 已签到、进行中
						}
					}
				}

				// 教练二维码图片地址
				Coach coach = coachService.getCoach(coachNo);
				if (coach != null) {
					String url = imgPath + "/" + Constant.COACH_PHOTO + "/qrcode" + coach.getCoachQrCode();
					courseDetailVo.setCoachQrCodeUrl(url);
				} else {
					courseDetailVo.setCoachQrCodeUrl("");
				}

				result.setCode(Constant.SECCUESS);
				result.setMsg(Constant.YES_RECORD);
				result.setData(courseDetailVo);
			} else {
				result.setCode(Constant.NO_RESULT);
				result.setMsg(Constant.NO_RECORD);
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 * 会员主题馆课程订单数据
	 * 
	 * @param pageNo页码
	 * @param memberNo会员编号
	 * @param orderStatus会员状态
	 */
	@RequestMapping("toMemberThemeOrderList")
	@ResponseBody
	public ResultInfo<List<MemberThemeOrderVo>> toMemberThemeOrderList(Integer pageNo, String memberNo,
			String orderStatus) {
		ResultInfo<List<MemberThemeOrderVo>> result = new ResultInfo<List<MemberThemeOrderVo>>();
		if (StringUtils.isNotBlank(memberNo) && memberNo.trim().length() > 0) {
			ThemeOrder themeOrder = new ThemeOrder();
			if (StringUtils.isNotBlank(orderStatus)) {
				themeOrder.setOrderStatus(Integer.parseInt(orderStatus));
			}
			themeOrder.setMemberNo(memberNo);
			int pageSize = 10;
			try {
				PageFinder<ThemeOrder> pageList = themeOrderService
						.themeOrderPagedList(new Query(pageNo, pageSize, themeOrder));
				if (pageList != null && pageList.getData().size() > 0) {
					List<MemberThemeOrderVo> listOrder = new ArrayList<MemberThemeOrderVo>();
					for (ThemeOrder theme : pageList.getData()) {
						MemberThemeOrderVo orderVo = new MemberThemeOrderVo();
						orderVo.setThemeOrderNo(theme.getThemeOrderNo());
						orderVo.setStoreName(theme.getStoreName());
						// 查询课程名称
						Course course = courseService.getCourse(theme.getCourseNo());
						double appoinNum = 0d;
						if (theme.getPeopleNumber() != null) {
							appoinNum = Double.parseDouble(String.valueOf(theme.getPeopleNumber()));
							orderVo.setAppoinNumber(theme.getPeopleNumber());
						} else {
							appoinNum = 0d;
						}

						if (course != null) {
							orderVo.setCourseName(course.getChineseName());
							double coursePrice = ECCalculateUtils.mul(appoinNum, course.getPrice());
							orderVo.setCoursePrice(String.valueOf(coursePrice));
						}
						if (theme.getPayStatus() == 0) {
							orderVo.setOrderStatus("5");// 待支付
						} else {
							orderVo.setOrderStatus(String.valueOf(theme.getOrderStatus()));
						}
						orderVo.setClassTime(theme.getClassTime());
						orderVo.setPayableAmount(theme.getPayableAmount());
						orderVo.setArrayCourseNo(theme.getArrangeNo());

						listOrder.add(orderVo);
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg(Constant.YES_RECORD);
					result.setData(listOrder);
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} catch (Exception e) {
				result.setCode(Constant.FAIL);
				result.setMsg(e.toString());
			}
		}
		return result;
	}

	/**
	 * 排队中的订单-会员取消订单 无条件限制：随时都可以退
	 * 
	 * @param memberNo会员编号
	 * @param themeOrderNo订单编号
	 * @param selectNumber选择要取消的人数
	 */
	@RequestMapping("cancelLineOrder")
	@ResponseBody
	public ResultInfo<String> cancelLineOrder(String memberNo, String themeOrderNo, String selectNumber) {
		ResultInfo<String> result = new ResultInfo<String>();
		try {
			// 查询该取消课程订单的对象
			ThemeOrder themeOrder = themeOrderService.getThemeOrder(themeOrderNo);
			if (themeOrder != null) {
				String couponNo = themeOrder.getCouponNo();// 优惠券编号
				double balanceAmount = themeOrder.getBalanceDiscountAmount();// 余额抵扣
				int courseBagNum = themeOrder.getCourseBagNum();// 抵扣的优惠券
				double refundAmount = 0d;// 退款金额
				int orderStatus = Integer.parseInt(OrderConstant.yqxOrder_code);// 订单状态
				// 使用优惠券抵扣 课时包无抵扣 余额无抵扣
				if (StringUtils.isNotBlank(couponNo) && balanceAmount == 0d && courseBagNum == 0) {
					if (themeOrder.getPayableAmount() == 0d) {// 优惠券抵扣实付款为0.0时
						ThemeOrder order = new ThemeOrder();
						order.setThemeOrderNo(themeOrderNo);
						order.setCancelNumber(themeOrder.getPeopleNumber());
						order.setRefundAmount(refundAmount);
						order.setOrderStatus(orderStatus);// 已取消
						themeOrderService.updateThemeOrder(order, getOperator());
						// 修改排课表
						themeOrderService.updateOrderGlData(themeOrder, themeOrder.getPeopleNumber(), 2);
					} else {
						refundAmount = themeOrder.getPayableAmount();
						// 调用第三方接口退款
						if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
							// 支付宝
							themeOrderService.callBackOrderLineRefundData(themeOrder, refundAmount,
									OrderConstant.alipay_method, themeOrder.getPeopleNumber(), 2);
						} else {
							// 微信
							themeOrderService.callBackOrderLineRefundData(themeOrder, refundAmount,
									OrderConstant.weChat_method, themeOrder.getPeopleNumber(), 2);
						}
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg("取消订单退款成功");
				} else if (StringUtils.isBlank(couponNo) && balanceAmount == 0d && courseBagNum > 0) {
					// 使用课程包抵扣、没有使用优惠券和余额
					if (themeOrder.getPayableAmount() == 0d) {// 实付款为0.0时
						ThemeOrder order = new ThemeOrder();
						order.setThemeOrderNo(themeOrderNo);
						order.setCancelNumber(themeOrder.getPeopleNumber());
						order.setRefundAmount(refundAmount);
						order.setOrderStatus(orderStatus);// 已取消
						themeOrderService.updateThemeOrder(order, getOperator());
						// 修改排课表
						themeOrderService.updateOrderGlData(themeOrder, themeOrder.getPeopleNumber(), 2);
						// 修改会员余额表课程包数量
						MemberBalance memberBalance = memberBalanceService.getMemberBalance(themeOrder.getMemberNo());
						if (memberBalance != null) {
							MemberBalance mb = new MemberBalance();
							mb.setMemberNo(memberBalance.getMemberNo());
							mb.setCourseNumber(memberBalance.getCourseNumber() + themeOrder.getCourseBagNum());
							memberBalanceService.updateMemberBalance(mb, getOperator());
						}
					} else {
						refundAmount = themeOrder.getPayableAmount();
						// 调用第三方接口退款
						if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
							// 支付宝
							themeOrderService.callBackOrderLineRefundData(themeOrder, refundAmount,
									OrderConstant.alipay_method, themeOrder.getPeopleNumber(), 2);
						} else {
							// 微信
							themeOrderService.callBackOrderLineRefundData(themeOrder, refundAmount,
									OrderConstant.weChat_method, themeOrder.getPeopleNumber(), 2);
						}
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg("取消订单退款成功");
				} else if (StringUtils.isBlank(couponNo) && balanceAmount > 0d && courseBagNum == 0) {
					// 使用余额支付，没有使用优惠券和课程包
					// 实付款已用余额抵扣完，直接更新主题订单表和会员余额表
					ThemeOrder order = new ThemeOrder();
					order.setThemeOrderNo(themeOrderNo);
					order.setCancelNumber(themeOrder.getPeopleNumber());
					order.setRefundAmount(themeOrder.getBalanceDiscountAmount());
					refundAmount = themeOrder.getBalanceDiscountAmount();
					order.setOrderStatus(orderStatus);// 已取消
					themeOrderService.updateThemeOrder(order, getOperator());

					themeOrderService.updateOrderGlData(themeOrder, themeOrder.getPeopleNumber(), 2);
					// 修改会员余额表会员余额
					MemberBalance memberBalance = memberBalanceService.getMemberBalance(themeOrder.getMemberNo());
					if (memberBalance != null) {
						MemberBalance mb = new MemberBalance();
						mb.setMemberNo(memberBalance.getMemberNo());
						mb.setMemberBalance(ECCalculateUtils.add(refundAmount, memberBalance.getMemberBalance()));
						memberBalanceService.updateMemberBalance(mb, getOperator());
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg("取消订单退款成功");
				} else if (StringUtils.isNotBlank(couponNo) && balanceAmount > 0d && courseBagNum == 0) {
					// 选择优惠券和余额抵扣，不使用课程
					ThemeOrder order = new ThemeOrder();
					order.setThemeOrderNo(themeOrderNo);
					order.setCancelNumber(themeOrder.getPeopleNumber());
					order.setRefundAmount(themeOrder.getBalanceDiscountAmount());
					refundAmount = themeOrder.getBalanceDiscountAmount();
					order.setOrderStatus(orderStatus);// 已取消
					themeOrderService.updateThemeOrder(order, getOperator());
					// 修改排课表
					themeOrderService.updateOrderGlData(themeOrder, themeOrder.getPeopleNumber(), 2);
					// 修改会员余额表会员余额
					MemberBalance memberBalance = memberBalanceService.getMemberBalance(themeOrder.getMemberNo());
					if (memberBalance != null) {
						MemberBalance mb = new MemberBalance();
						mb.setMemberNo(memberBalance.getMemberNo());
						mb.setMemberBalance(ECCalculateUtils.add(refundAmount, memberBalance.getMemberBalance()));
						memberBalanceService.updateMemberBalance(mb, getOperator());
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg("取消订单退款成功");
				} else if (StringUtils.isBlank(themeOrder.getCouponNo()) && themeOrder.getBalanceDiscountAmount() > 0d
						&& themeOrder.getCourseBagNum() > 0) {
					// 使用课时包和余额抵扣
					ThemeOrder order = new ThemeOrder();
					order.setThemeOrderNo(themeOrderNo);
					order.setCancelNumber(themeOrder.getPeopleNumber());
					order.setRefundAmount(themeOrder.getBalanceDiscountAmount());
					refundAmount = themeOrder.getBalanceDiscountAmount();
					order.setOrderStatus(orderStatus);// 已取消
					themeOrderService.updateThemeOrder(order, getOperator());
					// 修改排课表
					themeOrderService.updateOrderGlData(themeOrder, themeOrder.getPeopleNumber(), 2);
					// 修改会员余额表会员余额和课程包
					MemberBalance memberBalance = memberBalanceService.getMemberBalance(themeOrder.getMemberNo());
					if (memberBalance != null) {
						MemberBalance mb = new MemberBalance();
						mb.setMemberNo(memberBalance.getMemberNo());
						mb.setMemberBalance(ECCalculateUtils.add(refundAmount, memberBalance.getMemberBalance()));
						mb.setCourseNumber(memberBalance.getCourseNumber() + themeOrder.getCourseBagNum());
						memberBalanceService.updateMemberBalance(mb, getOperator());
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg("取消订单退款成功");
				} else if (StringUtils.isBlank(couponNo) && balanceAmount == 0d && courseBagNum == 0) {
					// 支付时没有选择优惠券、课时包或者余额抵扣，直接调用第三方接口支付
					refundAmount = themeOrder.getPayableAmount();
					if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
						themeOrderService.callBackOrderLineRefundData(themeOrder, refundAmount,
								OrderConstant.alipay_method, themeOrder.getPeopleNumber(), 2);
					} else {
						themeOrderService.callBackOrderLineRefundData(themeOrder, refundAmount,
								OrderConstant.weChat_method, themeOrder.getPeopleNumber(), 2);
					}
					result.setCode(Constant.SECCUESS);
					result.setMsg("取消订单退款成功");
				}
			} else {
				result.setCode(Constant.FAIL);
				result.setMsg("取消失败、订单不存在！");
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.getMessage());
		}
		return result;
	}

	/**
	 * 已经预约的订单-会员取消订单 条件、在上课前两小时内是不能操作取消订单的。
	 * 
	 * @param memberNo会员编号
	 * @param themeOrderNo订单编号
	 * @param selectNumber选择要取消的人数
	 */
	@RequestMapping("cancelThemeOrder")
	@ResponseBody
	public ResultInfo<String> cancelThemeOrder(String memberNo, String themeOrderNo, String selectNumber) {
		ResultInfo<String> result = new ResultInfo<String>();
		try {
			// 查询该取消课程订单的对象
			ThemeOrder themeOrder = themeOrderService.getThemeOrder(themeOrderNo);
			if (themeOrder != null) {
				// 查询该课程的排课时间
				ArrayCourse courseDateBean = arrayCourseService.getArrayCourse(themeOrder.getArrangeNo());
				if (courseDateBean != null) {
					// 查询该课程的时间段：起始时间
					ArrayCourse courseBeans = arrayCourseService.getStoreCourseTimeByFtl(courseDateBean.getStoreNo(),
							courseDateBean.getFtlRow(), courseDateBean.getCourseType());
					if (courseBeans != null) {
						// 开始时间段
						String startTime = ECDateUtils.farmatDateToHMS(courseBeans.getCourseStart());
						// 排课日期
						String cTime = ECDateUtils.formatDate(courseDateBean.getCourseDate());
						// 排课时间 yyyy-MM-dd HH:mm:ss
						String classTime = cTime.substring(0, 10) + " 00:00:00";
						String nowTime = classTime.replaceAll("00:00:00", startTime + ":00");
						String confineMinutes = sysParamService.getByParamKey("CONFINE_OPPION_HOUR").getParamValue();
						Date arrayDate = ECDateUtils.parseTime(nowTime);
						int diffMinutes = ECDateUtils.differMinutes(new Date(), arrayDate).intValue();
						// 用户取消订单时间和课程上课时间相差2小时====大于2小时=120分钟，可操作取消预约
						if (diffMinutes > Integer.parseInt(confineMinutes)) {
							double refundAmount = 0d;// 退款金额
							double appionNum = 0d;// 预约人数
							double cancleNum = 0d;// 取消人数
							// 判断订单的支付操作，取消后更新订单状态
							// 1.判断是否有优惠券抵扣
							if (StringUtils.isNotBlank(themeOrder.getCouponNo())
									&& themeOrder.getBalanceDiscountAmount() == 0d
									&& themeOrder.getCourseBagNum() == 0) {
								// 如果选择优惠券已经抵扣完实际支付金额
								if (themeOrder.getPayableAmount() == 0d) {
									// 直接更新订单表
									ThemeOrder order = new ThemeOrder();
									order.setThemeOrderNo(themeOrder.getThemeOrderNo());
									if (Integer.parseInt(selectNumber) == themeOrder.getPeopleNumber()) {
										order.setRefundAmount(0d);// 退款金额为0.0
										order.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));// 已取消
										if (themeOrder.getCancelNumber() == null) {
											order.setCancelNumber(Integer.parseInt(selectNumber));
										} else {
											order.setCancelNumber(
													Integer.parseInt(selectNumber) + themeOrder.getCancelNumber());
										}
									} else if (Integer.parseInt(selectNumber) < themeOrder.getPeopleNumber()) {
										if (themeOrder.getCancelNumber() == null) {
											order.setCancelNumber(Integer.parseInt(selectNumber));
											order.setRefundAmount(0d);// 退款金额为0.0
										} else {
											int surplusNum = themeOrder.getPeopleNumber()
													- themeOrder.getCancelNumber();// 剩余可取消人数
											if (Integer.parseInt(selectNumber) == surplusNum) {
												order.setRefundAmount(0d);// 退款金额为0.0
												order.setCancelNumber(
														Integer.parseInt(selectNumber) + themeOrder.getCancelNumber());
												order.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));// 已取消
											} else if (Integer.parseInt(selectNumber) < surplusNum) {
												order.setRefundAmount(0d);// 退款金额为0.0
												order.setCancelNumber(
														Integer.parseInt(selectNumber) + themeOrder.getCancelNumber());
											}
										}
									}
									themeOrderService.updateThemeOrder(order, getOperator());
									// 修改其他表数据
									themeOrderService.updateOrderGlData(themeOrder, Integer.parseInt(selectNumber), 1);
									result.setCode(Constant.SECCUESS);
									result.setMsg("取消订单退款成功");
								} else {
									// 使用优惠券后有实际支付金额
									// 查询优惠券优惠方式
									Coupon coupon = couponService.getCoupon(themeOrder.getCouponNo());
									if (coupon != null) {
										if (coupon.getCouponMethod() == OrderConstant.coupon_dz_method) {
											// 打折
											Course course = courseService.getCourse(themeOrder.getCourseNo());
											// 打折金额
											double discountAmount = ECCalculateUtils.mul(course.getPrice(),
													coupon.getDiscount());
											cancleNum = Double.parseDouble(selectNumber);// 选择取消的人数
											// 计算应退的金额
											refundAmount = ECCalculateUtils.mul(cancleNum, discountAmount);
											// 调用第三方接口退款
											if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
												// 支付宝
												themeOrderService.callBackOrderData(themeOrder, refundAmount,
														OrderConstant.alipay_method, Integer.parseInt(selectNumber));
											} else {
												// 微信
												themeOrderService.callBackOrderData(themeOrder, refundAmount,
														OrderConstant.weChat_method, Integer.parseInt(selectNumber));
											}
										} else if (coupon.getCouponMethod() == OrderConstant.coupon_zj_method) {
											// 直减
											appionNum = Double
													.parseDouble(String.valueOf(themeOrder.getPeopleNumber()));
											cancleNum = Double.parseDouble(selectNumber);// 选择取消的人数
											// 调用第三方接口退款
											if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
												// 计算直减平均支付的金额
												// 剩余取消人数
												int serNum = 0;
												if (themeOrder.getCancelNumber() == null) {
													serNum = themeOrder.getPeopleNumber() - 0;
												} else {
													serNum = themeOrder.getPeopleNumber()
															- themeOrder.getCancelNumber();
												}
												if (cancleNum == serNum) {
													refundAmount = themeOrder.getPayableAmount();
												} else {
													double price = ECCalculateUtils.div(themeOrder.getPayableAmount(),
															appionNum);
													double dzPrice = ECCalculateUtils.remainFloor2(price);
													// 计算应退的金额
													refundAmount = ECCalculateUtils.mul(cancleNum, dzPrice);
												}
												// 支付宝
												themeOrderService.callBackOrderData(themeOrder, refundAmount,
														OrderConstant.alipay_method, Integer.parseInt(selectNumber));
											} else {
												// 微信
												// 计算直减平均支付的金额
												int serNum = 0;
												if (themeOrder.getCancelNumber() == null) {
													serNum = themeOrder.getPeopleNumber() - 0;
												} else {
													serNum = themeOrder.getPeopleNumber()
															- themeOrder.getCancelNumber();
												}
												if (cancleNum == serNum) {
													refundAmount = ECCalculateUtils.sub(themeOrder.getPayableAmount(),
															themeOrder.getRefundAmount());
												} else {
													double price = ECCalculateUtils.div(themeOrder.getPayableAmount(),
															appionNum);
													double dzPrice = ECCalculateUtils.remainFloor2(price);
													// 计算应退的金额
													refundAmount = ECCalculateUtils.mul(cancleNum, dzPrice);
												}
												themeOrderService.callBackOrderData(themeOrder, refundAmount,
														OrderConstant.weChat_method, Integer.parseInt(selectNumber));
											}
										} else if (coupon.getCouponMethod() == OrderConstant.coupon_kc_method) {
											// 课程体验券
											Course course = courseService.getCourse(themeOrder.getCourseNo());
											double coursePrice = course.getPrice();
											int selectNum = Integer.parseInt(selectNumber);// 选择人数
											int expeNum = Integer.parseInt(coupon.getFreeCourseNumber());// 体验节数
											if (selectNum == expeNum) {
												// 如果选择取消的人数等于免费体验节数
												if (themeOrder.getCancelNumber() != null) {
													// 当取消人数已经存在时，选择退款的人数应该按选择人数*单价计算退款金额
													refundAmount = ECCalculateUtils.mul(
															Double.parseDouble(String.valueOf(selectNum)), coursePrice);
													if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
														// 支付宝
														themeOrderService.callBackOrderData(themeOrder, refundAmount,
																OrderConstant.alipay_method,
																Integer.parseInt(selectNumber));
													} else {
														// 微信
														themeOrderService.callBackOrderData(themeOrder, refundAmount,
																OrderConstant.weChat_method,
																Integer.parseInt(selectNumber));
													}
												} else {
													// 直接修改订单表
													ThemeOrder order = new ThemeOrder();
													order.setThemeOrderNo(themeOrder.getThemeOrderNo());
													if (selectNum == themeOrder.getPeopleNumber()) {
														order.setOrderStatus(
																Integer.parseInt(OrderConstant.yqxOrder_code));// 已取消
														order.setCancelNumber(selectNum);
													} else {
														order.setCancelNumber(selectNum + themeOrder.getCancelNumber());
													}

													themeOrderService.updateThemeOrder(order, getOperator());

													themeOrderService.updateOrderGlData(themeOrder,
															Integer.parseInt(selectNumber), 1);
												}
											} else if (selectNum > expeNum) {
												if (themeOrder.getCancelNumber() != null) {
													refundAmount = ECCalculateUtils.mul(
															Double.parseDouble(String.valueOf(selectNum)), coursePrice);
													if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
														// 支付宝
														themeOrderService.callBackOrderData(themeOrder, refundAmount,
																OrderConstant.alipay_method,
																Integer.parseInt(selectNumber));
													} else {
														// 微信
														themeOrderService.callBackOrderData(themeOrder, refundAmount,
																OrderConstant.weChat_method,
																Integer.parseInt(selectNumber));
													}
												} else {
													// 实际需要退款的人数
													int realNum = selectNum - expeNum;
													refundAmount = ECCalculateUtils.mul(
															Double.parseDouble(String.valueOf(realNum)), coursePrice);
													if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
														// 支付宝
														themeOrderService.callBackOrderData(themeOrder, refundAmount,
																OrderConstant.alipay_method,
																Integer.parseInt(selectNumber));
													} else {
														// 微信
														themeOrderService.callBackOrderData(themeOrder, refundAmount,
																OrderConstant.weChat_method,
																Integer.parseInt(selectNumber));
													}
												}
											}
										}
									}
									result.setCode(Constant.SECCUESS);
									result.setMsg("取消订单退款成功");
								}
							} else if (StringUtils.isBlank(themeOrder.getCouponNo())
									&& themeOrder.getBalanceDiscountAmount() == 0d
									&& themeOrder.getCourseBagNum() > 0) {
								// 如果选择课时包抵扣
								// 抵扣后实际支付金额为0时
								int returnBag = 0;// 返还的课时包
								if (themeOrder.getPayableAmount() == 0d) {
									ThemeOrder order = new ThemeOrder();
									order.setThemeOrderNo(themeOrder.getThemeOrderNo());
									if (themeOrder.getPeopleNumber() == Integer.parseInt(selectNumber)) {
										order.setCancelNumber(Integer.parseInt(selectNumber));
										returnBag = Integer.parseInt(selectNumber);
										order.setRefundAmount(0d);
										order.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));// 已取消
									} else if (themeOrder.getPeopleNumber() > Integer.parseInt(selectNumber)) {
										if (themeOrder.getCancelNumber() == null) {
											order.setRefundAmount(0d);
											order.setCancelNumber(Integer.parseInt(selectNumber));
											returnBag = Integer.parseInt(selectNumber);
										} else {
											int cal = themeOrder.getPeopleNumber() - themeOrder.getCancelNumber();// 剩余可取消人数
											if (cal == Integer.parseInt(selectNumber)) {
												order.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));
												order.setCancelNumber(
														themeOrder.getCancelNumber() + Integer.parseInt(selectNumber));
												returnBag = Integer.parseInt(selectNumber);
											} else if (cal > Integer.parseInt(selectNumber)) {
												order.setCancelNumber(
														themeOrder.getCancelNumber() + Integer.parseInt(selectNumber));
												returnBag = Integer.parseInt(selectNumber);
											}
										}
									}
									themeOrderService.updateThemeOrder(order, getOperator());
									// 返还课时包节数
									MemberBalance mbBalance = memberBalanceService
											.getMemberBalance(themeOrder.getMemberNo());
									if (mbBalance != null) {
										MemberBalance mb = new MemberBalance();
										mb.setMemberNo(mbBalance.getMemberNo());
										mb.setCourseNumber(returnBag + mbBalance.getCourseNumber());
										memberBalanceService.updateMemberBalance(mb, getOperator());
									}
									// 修改其他表数据
									themeOrderService.updateOrderGlData(themeOrder, Integer.parseInt(selectNumber), 1);
									result.setCode(Constant.SECCUESS);
									result.setMsg("取消订单退款成功");
								} else {
									// 课程包抵扣后实际支付金额不为0
									if (Integer.parseInt(selectNumber) == themeOrder.getPeopleNumber()) {
										// 如果选择的人数等于该课程预约人数
										// 退实际支付的金额，返回会员课程包数量
										refundAmount = themeOrder.getPayableAmount();
										if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
											// 支付宝
											themeOrderService.callBackOrderData(themeOrder, refundAmount,
													OrderConstant.alipay_method, Integer.parseInt(selectNumber));
										} else {
											// 微信
											themeOrderService.callBackOrderData(themeOrder, refundAmount,
													OrderConstant.weChat_method, Integer.parseInt(selectNumber));
										}
										// 返还课程包
										MemberBalance mbBalance = memberBalanceService
												.getMemberBalance(themeOrder.getMemberNo());
										if (mbBalance != null) {
											MemberBalance mb = new MemberBalance();
											mb.setMemberNo(mbBalance.getMemberNo());
											mb.setCourseNumber(
													themeOrder.getCourseBagNum() + mbBalance.getCourseNumber());
											memberBalanceService.updateMemberBalance(mb, getOperator());
										}
									} else if (Integer.parseInt(selectNumber) < themeOrder.getPeopleNumber()) {
										// 选择人数小于该课程预约人数
										int courseBag = themeOrder.getCourseBagNum();
										if (Integer.parseInt(selectNumber) == courseBag) {// 选择取消人数等于课程包
											ThemeOrder order = new ThemeOrder();
											order.setThemeOrderNo(themeOrder.getThemeOrderNo());
											order.setRefundAmount(0d);
											if (themeOrder.getCancelNumber() == null) {
												int calNum = 0;
												order.setCancelNumber(calNum + Integer.parseInt(selectNumber));
											} else {
												order.setCancelNumber(
														themeOrder.getCancelNumber() + Integer.parseInt(selectNumber));
											}
											order.setCourseBagNum(0);

											themeOrderService.updateThemeOrder(order, getOperator());
											// 返还课时包节数
											MemberBalance mbBalance = memberBalanceService
													.getMemberBalance(themeOrder.getMemberNo());
											if (mbBalance != null) {
												MemberBalance mb = new MemberBalance();
												mb.setMemberNo(mbBalance.getMemberNo());
												mb.setCourseNumber(courseBag + mbBalance.getCourseNumber());
												memberBalanceService.updateMemberBalance(mb, getOperator());
											}

											// 修改其他表数据
											themeOrderService.updateOrderGlData(themeOrder,
													Integer.parseInt(selectNumber), 1);
										} else if (Integer.parseInt(selectNumber) < courseBag) {
											int serplusBag = courseBag - Integer.parseInt(selectNumber);// 剩余抵扣课时包
											ThemeOrder order = new ThemeOrder();
											order.setThemeOrderNo(themeOrder.getThemeOrderNo());
											order.setRefundAmount(0d);
											if (themeOrder.getCancelNumber() == null) {
												int calNum = 0;
												order.setCancelNumber(calNum + Integer.parseInt(selectNumber));
											} else {
												order.setCancelNumber(
														themeOrder.getCancelNumber() + Integer.parseInt(selectNumber));
											}
											order.setCourseBagNum(serplusBag);

											themeOrderService.updateThemeOrder(order, getOperator());
											// 返还课时包节数
											MemberBalance mbBalance = memberBalanceService
													.getMemberBalance(themeOrder.getMemberNo());
											if (mbBalance != null) {
												MemberBalance mb = new MemberBalance();
												mb.setMemberNo(mbBalance.getMemberNo());
												mb.setCourseNumber(serplusBag + mbBalance.getCourseNumber());
												memberBalanceService.updateMemberBalance(mb, getOperator());
											}

											// 修改其他表数据
											themeOrderService.updateOrderGlData(themeOrder,
													Integer.parseInt(selectNumber), 1);
										} else if (themeOrder.getCourseBagNum() == 0) {
											int serplusCalNum = themeOrder.getPeopleNumber()
													- themeOrder.getCancelNumber();// 剩余取消人数
											if (Integer.parseInt(selectNumber) == serplusCalNum) {
												refundAmount = themeOrder.getPayableAmount();
												if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
													// 支付宝
													themeOrderService.callBackOrderData(themeOrder, refundAmount,
															OrderConstant.alipay_method,
															Integer.parseInt(selectNumber));
												} else {
													// 微信
													themeOrderService.callBackOrderData(themeOrder, refundAmount,
															OrderConstant.weChat_method,
															Integer.parseInt(selectNumber));
												}
											} else if (Integer.parseInt(selectNumber) < serplusCalNum) {
												Course course = courseService.getCourse(themeOrder.getCourseNo());
												refundAmount = ECCalculateUtils.mul(course.getPrice(),
														Double.parseDouble(selectNumber));
												if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
													// 支付宝
													themeOrderService.callBackOrderData(themeOrder, refundAmount,
															OrderConstant.alipay_method,
															Integer.parseInt(selectNumber));
												} else {
													// 微信
													themeOrderService.callBackOrderData(themeOrder, refundAmount,
															OrderConstant.weChat_method,
															Integer.parseInt(selectNumber));
												}
											}
										} else {
											// 选择人数大于抵用的课时包
											int courseBags = themeOrder.getCourseBagNum();// 抵扣的课时包
											// 需要支付的人数
											int payNum = Integer.parseInt(selectNumber) - courseBags;
											Course course = courseService.getCourse(themeOrder.getCourseNo());
											refundAmount = ECCalculateUtils.mul(course.getPrice(),
													Double.parseDouble(String.valueOf(payNum)));
											themeOrder.setCourseBagNum(themeOrder.getCourseBagNum() - courseBags);
											if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
												// 支付宝
												themeOrderService.callBackOrderData(themeOrder, refundAmount,
														OrderConstant.alipay_method, Integer.parseInt(selectNumber));
											} else {
												// 微信
												themeOrderService.callBackOrderData(themeOrder, refundAmount,
														OrderConstant.weChat_method, Integer.parseInt(selectNumber));
											}
											// 返还课时包节数
											MemberBalance mbBalance = memberBalanceService
													.getMemberBalance(themeOrder.getMemberNo());
											if (mbBalance != null) {
												MemberBalance mb = new MemberBalance();
												mb.setMemberNo(mbBalance.getMemberNo());
												mb.setCourseNumber(courseBags + mbBalance.getCourseNumber());
												memberBalanceService.updateMemberBalance(mb, getOperator());
											}
										}
									}
									result.setCode(Constant.SECCUESS);
									result.setMsg("取消订单退款成功");
								}
							} else if (StringUtils.isBlank(themeOrder.getCouponNo())
									&& themeOrder.getBalanceDiscountAmount() > 0d
									&& themeOrder.getCourseBagNum() == 0) {
								// 如果该订单使用余额支付，支付后实际支付金额为0时
								if (themeOrder.getPayableAmount() == 0d) {
									// 余额抵扣
									double balance = themeOrder.getBalanceDiscountAmount();
									double appionNums = Double
											.parseDouble(String.valueOf(themeOrder.getPeopleNumber()));
									// 一节课的余额抵扣的价钱
									double avgPrice = ECCalculateUtils.div(balance, appionNums);
									double selectNum = Double.parseDouble(selectNumber);// 选择取消的人数
									ThemeOrder order = new ThemeOrder();
									order.setThemeOrderNo(themeOrder.getThemeOrderNo());
									if (Integer.parseInt(selectNumber) < themeOrder.getPeopleNumber()) {
										int canNum = 0;
										if (themeOrder.getCancelNumber() == null) {
											canNum = 0;// 剩余取消人数
										} else {
											canNum = themeOrder.getPeopleNumber() - themeOrder.getCancelNumber();// 剩余取消人数
										}

										if (Integer.parseInt(selectNumber) == canNum) {
											order.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));// 已取消
											refundAmount = ECCalculateUtils.mul(selectNum, avgPrice);
											if (themeOrder.getRefundAmount() == null) {
												order.setRefundAmount(refundAmount);
											} else {
												order.setRefundAmount(refundAmount + themeOrder.getRefundAmount());
											}
										} else {
											refundAmount = ECCalculateUtils.mul(selectNum, avgPrice);
											if (themeOrder.getRefundAmount() == null) {
												order.setRefundAmount(refundAmount);
											} else {
												order.setRefundAmount(refundAmount + themeOrder.getRefundAmount());
											}
										}
									} else if (Integer.parseInt(selectNumber) == themeOrder.getPeopleNumber()) {
										order.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));// 已取消
										refundAmount = themeOrder.getBalanceDiscountAmount();
										if (themeOrder.getRefundAmount() == null) {
											order.setRefundAmount(refundAmount);
										} else {
											order.setRefundAmount(refundAmount + themeOrder.getRefundAmount());
										}
									}
									if (themeOrder.getCancelNumber() == null) {
										int cancelNum = 0;
										order.setCancelNumber(cancelNum + Integer.parseInt(selectNumber));
									} else {
										order.setCancelNumber(
												themeOrder.getCancelNumber() + Integer.parseInt(selectNumber));
									}

									themeOrderService.updateThemeOrder(order, getOperator());
									// 修改其他表数据
									themeOrderService.updateOrderGlData(themeOrder, Integer.parseInt(selectNumber), 1);
									MemberBalance mbBalance = memberBalanceService
											.getMemberBalance(themeOrder.getMemberNo());
									if (mbBalance != null) {
										MemberBalance mb = new MemberBalance();
										mb.setMemberNo(mbBalance.getMemberNo());
										mb.setMemberBalance(
												ECCalculateUtils.add(refundAmount, mbBalance.getMemberBalance()));
										memberBalanceService.updateMemberBalance(mb, getOperator());
									}
									result.setCode(Constant.SECCUESS);
									result.setMsg("取消订单退款成功");
								}
							} else if (StringUtils.isNotBlank(themeOrder.getCouponNo())
									&& themeOrder.getBalanceDiscountAmount() > 0d
									&& themeOrder.getCourseBagNum() == 0) {
								// 使用优惠券和余额抵扣
								if (themeOrder.getPayableAmount() == 0d) {
									ThemeOrder order = new ThemeOrder();
									order.setThemeOrderNo(themeOrder.getThemeOrderNo());
									double price = 0d;
									double selectNum = 0d;
									int serPlusNum = 0;
									Coupon coupon = couponService.getCoupon(themeOrder.getCouponNo());
									if (coupon.getCouponMethod() == OrderConstant.coupon_dz_method
											|| coupon.getCouponMethod() == OrderConstant.coupon_zj_method) {
										if (Integer.parseInt(selectNumber) == themeOrder.getPeopleNumber()) {
											order.setRefundAmount(themeOrder.getBalanceDiscountAmount());
											order.setOrderStatus(3);
											order.setCancelNumber(Integer.parseInt(selectNumber));
											refundAmount = themeOrder.getBalanceDiscountAmount();
										} else {
											int selectNums = Integer.parseInt(selectNumber);
											double appionNumber = Double
													.parseDouble(String.valueOf(themeOrder.getPeopleNumber()));
											if (selectNums < themeOrder.getPeopleNumber()) {
												double aveAmount = ECCalculateUtils
														.div(themeOrder.getBalanceDiscountAmount(), appionNumber, 2);// 余额抵扣返还均价
												selectNum = Double.parseDouble(selectNumber);// 选择取消的人数
												if (themeOrder.getCancelNumber() != null) {
													serPlusNum = themeOrder.getPeopleNumber()
															- themeOrder.getCancelNumber();
													if (serPlusNum == 1) {
														if (themeOrder.getRefundAmount() != null) {
															double dikouAmount = ECCalculateUtils.sub(
																	themeOrder.getBalanceDiscountAmount(),
																	themeOrder.getRefundAmount());
															refundAmount = dikouAmount;
															order.setOrderStatus(3);
															order.setCancelNumber(
																	selectNums + themeOrder.getCancelNumber());
															order.setRefundAmount(ECCalculateUtils.add(refundAmount,
																	themeOrder.getRefundAmount()));
														}
													} else if (serPlusNum == selectNums) {
														double dikouAmount = ECCalculateUtils.sub(
																themeOrder.getBalanceDiscountAmount(),
																themeOrder.getRefundAmount());
														refundAmount = dikouAmount;
														order.setOrderStatus(3);
														order.setCancelNumber(
																selectNums + themeOrder.getCancelNumber());
														order.setRefundAmount(ECCalculateUtils.add(refundAmount,
																themeOrder.getRefundAmount()));
													} else {
														refundAmount = ECCalculateUtils.mul(selectNum, aveAmount);
														order.setCancelNumber(
																selectNums + themeOrder.getCancelNumber());
														order.setRefundAmount(ECCalculateUtils.add(refundAmount,
																themeOrder.getRefundAmount()));
													}
												} else {
													refundAmount = ECCalculateUtils.mul(selectNum, aveAmount);
													order.setCancelNumber(selectNums);
													order.setRefundAmount(refundAmount);
												}
											}
										}
									} else if (coupon.getCouponMethod() == OrderConstant.coupon_kc_method) {
										// 课程体验券
										selectNum = Double.parseDouble(selectNumber);// 选择取消的人数用于计算金额
										int expeNum = Integer.parseInt(coupon.getFreeCourseNumber());// 体验节数
										int selectNums = Integer.parseInt(selectNumber);// 选择取消的人数
										int orderStatus = Integer.parseInt(OrderConstant.yqxOrder_code);// 订单状态
										int balanceNum = themeOrder.getPeopleNumber() - expeNum;// 会员抵扣的人数
										if (selectNums == themeOrder.getPeopleNumber()) {
											// 如果选择人数==已预约人数
											refundAmount = themeOrder.getBalanceDiscountAmount();
											order.setRefundAmount(refundAmount);
											order.setCancelNumber(selectNums);
											order.setOrderStatus(orderStatus);// 已取消
										} else if (selectNums < themeOrder.getPeopleNumber()) {
											// 如果选择人数小于已预约人数
											if (selectNums > expeNum) {
												// 如果选择人数大于课程体验节数
												int realNum = selectNums - expeNum;// 实际计算人数
												price = ECCalculateUtils.div(themeOrder.getBalanceDiscountAmount(),
														Double.parseDouble(String.valueOf(balanceNum)));
												refundAmount = ECCalculateUtils
														.mul(Double.parseDouble(String.valueOf(realNum)), price);
												if (themeOrder.getRefundAmount() == null) {
													order.setRefundAmount(refundAmount);
												} else {
													order.setRefundAmount(ECCalculateUtils
															.add(themeOrder.getRefundAmount(), refundAmount));
												}

												if (themeOrder.getCancelNumber() == null) {
													order.setCancelNumber(selectNums);
												} else {
													order.setCancelNumber(selectNums + themeOrder.getCancelNumber());
												}
											} else if (selectNums == expeNum) {
												// 如果选择人数等于体验节数
												if (themeOrder.getCancelNumber() != null) {
													if (themeOrder.getCancelNumber() >= expeNum) {
														// 如果取消人数大于等于体验节数，证明体验人数已经取消
														int serNum = themeOrder.getPeopleNumber()
																- themeOrder.getCancelNumber();
														if (selectNums == serNum) {
															// 若选择的人数等于剩余取消的人数
															// 直接取消订单
															price = ECCalculateUtils.div(
																	themeOrder.getBalanceDiscountAmount(),
																	Double.parseDouble(String.valueOf(balanceNum)));
															refundAmount = ECCalculateUtils.mul(
																	Double.parseDouble(String.valueOf(selectNums)),
																	price);
															if (themeOrder.getRefundAmount() == null) {
																order.setRefundAmount(refundAmount);
															} else {
																order.setRefundAmount(ECCalculateUtils.add(
																		themeOrder.getRefundAmount(), refundAmount));
															}
															if (themeOrder.getCancelNumber() == null) {
																order.setCancelNumber(selectNums);
															} else {
																order.setCancelNumber(
																		selectNums + themeOrder.getCancelNumber());
															}
															order.setOrderStatus(orderStatus);// 已取消
														} else {
															price = ECCalculateUtils.div(
																	themeOrder.getBalanceDiscountAmount(),
																	Double.parseDouble(String.valueOf(balanceNum)));
															refundAmount = ECCalculateUtils.mul(
																	Double.parseDouble(String.valueOf(selectNums)),
																	price);
															if (themeOrder.getRefundAmount() == null) {
																order.setRefundAmount(refundAmount);
															} else {
																order.setRefundAmount(ECCalculateUtils.add(
																		themeOrder.getRefundAmount(), refundAmount));
															}
															if (themeOrder.getCancelNumber() == null) {
																order.setCancelNumber(selectNums);
															} else {
																order.setCancelNumber(
																		selectNums + themeOrder.getCancelNumber());
															}
														}
													} else {
														order.setRefundAmount(0d);
														if (themeOrder.getCancelNumber() == null) {
															order.setCancelNumber(selectNums);
														} else {
															order.setCancelNumber(
																	selectNums + themeOrder.getCancelNumber());
														}
													}
												} else {
													order.setRefundAmount(0d);
													if (themeOrder.getCancelNumber() == null) {
														order.setCancelNumber(selectNums);
													} else {
														order.setCancelNumber(
																selectNums + themeOrder.getCancelNumber());
													}
												}
											}
										}
									}

									themeOrderService.updateThemeOrder(order, getOperator());
									// 修改其他表数据
									themeOrderService.updateOrderGlData(themeOrder, Integer.parseInt(selectNumber), 1);
									// 修改会员账户课时包节数
									MemberBalance mbBalance = memberBalanceService
											.getMemberBalance(themeOrder.getMemberNo());
									if (mbBalance != null) {
										MemberBalance mb = new MemberBalance();
										mb.setMemberNo(mbBalance.getMemberNo());
										mb.setMemberBalance(
												ECCalculateUtils.add(refundAmount, mbBalance.getMemberBalance()));
										memberBalanceService.updateMemberBalance(mb, getOperator());
									}
									result.setCode(Constant.SECCUESS);
									result.setMsg("取消订单退款成功");
								}
							} else if (StringUtils.isBlank(themeOrder.getCouponNo())
									&& themeOrder.getBalanceDiscountAmount() > 0d && themeOrder.getCourseBagNum() > 0) {
								// 使用课时包和余额抵扣
								int balanceNum = themeOrder.getPeopleNumber() - themeOrder.getCourseBagNum();// 会员抵扣的人数
								int orderStatus = Integer.parseInt(OrderConstant.yqxOrder_code);// 订单状态
								int courseBag = themeOrder.getCourseBagNum();// 抵扣的课时包
								int selectNums = Integer.parseInt(selectNumber);
								int returnBag = 0;
								if (themeOrder.getPayableAmount() == 0d) {
									ThemeOrder order = new ThemeOrder();
									order.setThemeOrderNo(themeOrder.getThemeOrderNo());
									if (selectNums == themeOrder.getPeopleNumber()) {
										// 如果选择人数等于课程预约人数
										refundAmount = themeOrder.getBalanceDiscountAmount();
										returnBag = selectNums;
										order.setOrderStatus(orderStatus);// 已取消
										order.setCancelNumber(selectNums);
										order.setRefundAmount(refundAmount);
									} else if (selectNums < themeOrder.getPeopleNumber()) {
										// 如果选择人数小于课程预约人数
										if (selectNums > courseBag) {// 如果选择人数大于课程抵用的课程包节数
											int realNum = selectNums - courseBag;// 实际计算人数
											double price = ECCalculateUtils.div(themeOrder.getBalanceDiscountAmount(),
													Double.parseDouble(String.valueOf(balanceNum)));
											refundAmount = ECCalculateUtils
													.mul(Double.parseDouble(String.valueOf(realNum)), price);
											if (themeOrder.getRefundAmount() == null) {
												order.setRefundAmount(refundAmount);
											} else {
												order.setRefundAmount(ECCalculateUtils.add(themeOrder.getRefundAmount(),
														refundAmount));
											}

											if (themeOrder.getCancelNumber() == null) {
												order.setCancelNumber(selectNums);
											} else {
												order.setCancelNumber(selectNums + themeOrder.getCancelNumber());
											}
											returnBag = courseBag;
										} else if (selectNums == courseBag) {
											if (themeOrder.getCancelNumber() != null) {
												// 如果订单取消人数大于等于课程包数，则证明课程包已抵扣取消人数
												if (themeOrder.getCancelNumber() >= courseBag) {
													int serNum = themeOrder.getPeopleNumber()
															- themeOrder.getCancelNumber();// 剩余可取消人数
													if (selectNums == serNum) {
														// 如果选择人数等于剩余取消人数，直接取消订单
														double price = ECCalculateUtils.div(
																themeOrder.getBalanceDiscountAmount(),
																Double.parseDouble(String.valueOf(balanceNum)));
														refundAmount = ECCalculateUtils.mul(
																Double.parseDouble(String.valueOf(selectNums)), price);
														if (themeOrder.getRefundAmount() == null) {
															order.setRefundAmount(refundAmount);
														} else {
															order.setRefundAmount(ECCalculateUtils
																	.add(themeOrder.getRefundAmount(), refundAmount));
														}
														if (themeOrder.getCancelNumber() == null) {
															order.setCancelNumber(selectNums);
														} else {
															order.setCancelNumber(
																	selectNums + themeOrder.getCancelNumber());
														}
														order.setOrderStatus(orderStatus);// 已取消
													} else {
														double price = ECCalculateUtils.div(
																themeOrder.getBalanceDiscountAmount(),
																Double.parseDouble(String.valueOf(balanceNum)));
														refundAmount = ECCalculateUtils.mul(
																Double.parseDouble(String.valueOf(selectNums)), price);
														if (themeOrder.getRefundAmount() == null) {
															order.setRefundAmount(refundAmount);
														} else {
															order.setRefundAmount(ECCalculateUtils
																	.add(themeOrder.getRefundAmount(), refundAmount));
														}
														if (themeOrder.getCancelNumber() == null) {
															order.setCancelNumber(selectNums);
														} else {
															order.setCancelNumber(
																	selectNums + themeOrder.getCancelNumber());
														}
													}
												} else {
													if (themeOrder.getRefundAmount() == null) {
														order.setRefundAmount(0d);
													} else {
														order.setRefundAmount(ECCalculateUtils
																.add(themeOrder.getRefundAmount(), refundAmount));
													}
													if (themeOrder.getCancelNumber() == null) {
														order.setCancelNumber(selectNums);
													} else {
														order.setCancelNumber(
																selectNums + themeOrder.getCancelNumber());
													}
													returnBag = selectNums;
												}
											} else {
												if (themeOrder.getRefundAmount() == null) {
													order.setRefundAmount(0d);
												} else {
													order.setRefundAmount(ECCalculateUtils
															.add(themeOrder.getRefundAmount(), refundAmount));
												}
												if (themeOrder.getCancelNumber() == null) {
													order.setCancelNumber(selectNums);
												} else {
													order.setCancelNumber(selectNums + themeOrder.getCancelNumber());
												}
												returnBag = selectNums;
											}
										} else if (selectNums < courseBag) {
											// 如果选择人数小于课程包
											if (themeOrder.getCancelNumber() != null) {
												if (themeOrder.getCancelNumber() >= courseBag) {
													// 课时包已经等于抵用的取消人数
													int serNum = themeOrder.getPeopleNumber()
															- themeOrder.getCancelNumber();// 剩余可取消人数
													if (selectNums == serNum) {
														// 如果选择人数等于剩余取消人数，直接取消订单
														double price = ECCalculateUtils.div(
																themeOrder.getBalanceDiscountAmount(),
																Double.parseDouble(String.valueOf(balanceNum)));
														refundAmount = ECCalculateUtils.mul(
																Double.parseDouble(String.valueOf(selectNums)), price);
														if (themeOrder.getRefundAmount() == null) {
															order.setRefundAmount(refundAmount);
														} else {
															order.setRefundAmount(ECCalculateUtils
																	.add(themeOrder.getRefundAmount(), refundAmount));
														}
														if (themeOrder.getCancelNumber() == null) {
															order.setCancelNumber(selectNums);
														} else {
															order.setCancelNumber(
																	selectNums + themeOrder.getCancelNumber());
														}
														order.setOrderStatus(orderStatus);// 已取消
													} else {
														double price = ECCalculateUtils.div(
																themeOrder.getBalanceDiscountAmount(),
																Double.parseDouble(String.valueOf(balanceNum)));
														refundAmount = ECCalculateUtils.mul(
																Double.parseDouble(String.valueOf(selectNums)), price);
														if (themeOrder.getRefundAmount() == null) {
															order.setRefundAmount(refundAmount);
														} else {
															order.setRefundAmount(ECCalculateUtils
																	.add(themeOrder.getRefundAmount(), refundAmount));
														}
														if (themeOrder.getCancelNumber() == null) {
															order.setCancelNumber(selectNums);
														} else {
															order.setCancelNumber(
																	selectNums + themeOrder.getCancelNumber());
														}
													}
												} else {
													if (themeOrder.getCancelNumber() == null) {
														order.setCancelNumber(selectNums);
													} else {
														order.setCancelNumber(
																selectNums + themeOrder.getCancelNumber());
													}
													if (themeOrder.getCancelNumber() == null) {
														order.setCancelNumber(selectNums);
													} else {
														order.setCancelNumber(
																selectNums + themeOrder.getCancelNumber());
													}
													returnBag = selectNums;
													refundAmount = 0d;
												}
											} else {
												if (themeOrder.getCancelNumber() == null) {
													order.setCancelNumber(selectNums);
												} else {
													order.setCancelNumber(selectNums + themeOrder.getCancelNumber());
												}
												if (themeOrder.getCancelNumber() == null) {
													order.setCancelNumber(selectNums);
												} else {
													order.setCancelNumber(selectNums + themeOrder.getCancelNumber());
												}
												returnBag = selectNums;
												refundAmount = 0d;
											}

										} else {
											int serNum = themeOrder.getPeopleNumber() - themeOrder.getCancelNumber();// 剩余可取消人数
											if (selectNums == serNum) {
												// 如果选择人数等于剩余取消人数，直接取消订单
												double price = ECCalculateUtils.div(
														themeOrder.getBalanceDiscountAmount(),
														Double.parseDouble(String.valueOf(balanceNum)));
												refundAmount = ECCalculateUtils
														.mul(Double.parseDouble(String.valueOf(selectNums)), price);
												if (themeOrder.getRefundAmount() == null) {
													order.setRefundAmount(refundAmount);
												} else {
													order.setRefundAmount(ECCalculateUtils
															.add(themeOrder.getRefundAmount(), refundAmount));
												}
												if (themeOrder.getCancelNumber() == null) {
													order.setCancelNumber(selectNums);
												} else {
													order.setCancelNumber(selectNums + themeOrder.getCancelNumber());
												}
												order.setOrderStatus(orderStatus);// 已取消
											}
										}
									}

									themeOrderService.updateThemeOrder(order, getOperator());
									// 修改会员余额表
									MemberBalance mbBalance = memberBalanceService
											.getMemberBalance(themeOrder.getMemberNo());
									if (mbBalance != null) {
										MemberBalance mb = new MemberBalance();
										mb.setMemberNo(mbBalance.getMemberNo());
										mb.setCourseNumber(returnBag + mbBalance.getCourseNumber());
										mb.setMemberBalance(
												ECCalculateUtils.add(refundAmount, mbBalance.getMemberBalance()));
										memberBalanceService.updateMemberBalance(mb, getOperator());
									}
									// 修改其他表数据
									themeOrderService.updateOrderGlData(themeOrder, Integer.parseInt(selectNumber), 1);
									result.setCode(Constant.SECCUESS);
									result.setMsg("取消订单退款成功");
								}
							} else if (StringUtils.isBlank(themeOrder.getCouponNo())
									&& themeOrder.getBalanceDiscountAmount() == 0d
									&& themeOrder.getCourseBagNum() == 0) {
								// 支付时没有选择优惠券、课时包或者余额抵扣，直接调用第三方接口支付
								double selectNum = Double.parseDouble(selectNumber);
								Course course = courseService.getCourse(themeOrder.getCourseNo());
								refundAmount = ECCalculateUtils.mul(selectNum, course.getPrice());

								if (themeOrder.getPaymentMethod() == OrderConstant.alipay_method) {
									themeOrderService.callBackOrderData(themeOrder, refundAmount,
											OrderConstant.alipay_method, Integer.parseInt(selectNumber));
									result.setCode(Constant.SECCUESS);
									result.setMsg("取消订单退款成功");
								} else {
									themeOrderService.callBackOrderData(themeOrder, refundAmount,
											OrderConstant.weChat_method, Integer.parseInt(selectNumber));
									result.setCode(Constant.SECCUESS);
									result.setMsg("取消订单退款成功");
								}
							}
						} else if (new Date().getTime() > arrayDate.getTime()) {
							result.setCode(Constant.FAIL);
							result.setMsg("对不起，该课程在进行中或者已结束，不能取消订单！");
						} else {
							result.setCode(Constant.FAIL);
							result.setMsg("对不起，离上课时间已经不到" + confineMinutes + "分钟，不能取消订单！");
						}
					}
				} else {
					result.setCode(Constant.FAIL);
					result.setMsg("该课程已不存在、取消订单失败！");
				}
			} else {
				result.setCode(Constant.FAIL);
				result.setMsg("取消失败、订单不存在！");
			}
		} catch (Exception e) {
			result.setCode(Constant.FAIL);
			result.setMsg(e.toString());
		}
		return result;
	}

	/**
	 * 会员交易记录明细
	 * 
	 * @param memberNo会员编号
	 * @param pageNo
	 *            页码
	 */
	@RequestMapping("toAccountsRecordList")
	@ResponseBody
	public ResultInfo<List<AccountsVo>> toAccountsRecordList(String memberNo, Integer pageNo) {
		ResultInfo<List<AccountsVo>> result = new ResultInfo<List<AccountsVo>>();
		if (StringUtils.isNotBlank(memberNo) && memberNo.trim().length() > 0) {
			Accounts account = new Accounts();
			account.setMemberNo(memberNo);
			int pageSize = 10;
			try {
				PageFinder<Accounts> pageData = accountsService
						.getAccountsPagedList(new Query(pageNo, pageSize, account));
				if (pageData != null && pageData.getData().size() > 0) {
					List<AccountsVo> listAccountsVo = new ArrayList<AccountsVo>();
					for (Accounts accounts : pageData.getData()) {
						AccountsVo current = new AccountsVo();
						current.setAccountMoney(accounts.getAccountMoney());
						current.setAccountNo(accounts.getAccountNo());
						current.setAccountType(accounts.getAccountType());
						current.setBusinessNo(accounts.getBusinessNo());
						current.setBusinessType(accounts.getBusinessType());
						current.setAccountTime(ECDateUtils.formatDate(accounts.getAccountTime()));
						current.setMemberNo(accounts.getMemberNo());
						listAccountsVo.add(current);
					}

					result.setCode(Constant.SECCUESS);
					result.setMsg("请求成功");
					result.setData(listAccountsVo);
				} else {
					result.setCode(Constant.NO_RESULT);
					result.setMsg(Constant.NO_RECORD);
				}
			} catch (Exception e) {
				result.setCode(Constant.FAIL);
				result.setMsg(e.toString());
			}
		}
		return result;
	}

	/**
	 * 芝麻信用回调地址
	 */
	@RequestMapping("callbackForZhima")
	public String callbackForZhima(String params, String sign, ModelMap model) {
		Map<String, Object> returnParam = new HashMap<String, Object>();
		try {
			Map<String, String> map = ZhimaUtil.parseFromReturn(params, sign);

			if (map != null && map.get("open_id") != null && map.get("state") != null) {

				MemberZhimaScore memberZhimaScore = new MemberZhimaScore();
				memberZhimaScore.setMemberNo(map.get("state").toString());
				memberZhimaScore.setOpenId(map.get("open_id").toString());

				try {
					ResultInfo<MemberZhimaScore> getScoreResult = memberZhimaScoreService
							.getScoreForZhiMa(memberZhimaScore);
					if (getScoreResult.getCode().equals(Constant.SECCUESS)) {
						memberZhimaScore = getScoreResult.getData();
						if (memberZhimaScore.getScore() != null) {
							returnParam.put("code", Constant.SECCUESS);
							returnParam.put("data", memberZhimaScore.getScore());
							returnParam.put("msg", "授权成功");
						}
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 错误信息统一返回"系统错误"
		if (returnParam.get("code") == null || returnParam.get("code").equals(Constant.FAIL)) {
			returnParam.put("code", Constant.FAIL);// 预设默认值
			returnParam.put("data", 0);
			returnParam.put("msg", "系统错误");
		}
		String reuslt = JSONObject.toJSONString(returnParam);
		// reuslt = "jsCallNative("+reuslt+")";
		model.addAttribute("result", reuslt);
		return "/member/zhima_callback_page";
	}

	/**
	 * 跳转会员芝麻授权页面
	 * 
	 * @param memberNo
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("getZhimaAuthorizePageUrl")
	@ResponseBody
	public ResultInfo<String> getZhimaAuthorizePageUrl(String memberNo) throws ParseException {
		ResultInfo<String> result = new ResultInfo<String>();
		Member member = memberService.getMember(memberNo);

		if (member != null) {

			if (member.getMemberName() == null || member.getMemberName().equals("")) {
				result.setCode("5");
				result.setMsg("会员名称为空！");
				return result;
			}

			if (member.getIdCard() == null || member.getIdCard().equals("")) {
				result.setCode("5");
				result.setMsg("请先填写身份信息！");
				return result;
			}
			try {
				ZhimaAuthInfoAuthqueryResponse response = ZhimaUtil.getOpenId(member.getIdCard(),
						member.getMemberName());
				if (response.getErrorCode() != null) {
					if (response.getErrorCode().equals("ZMCSP.zm_account_not_existed")) {
						result.setCode("5");
						result.setMsg("支付宝帐号不存在，请填写正确信息。");
						return result;
					}
					// 个人信息暂时无法使用芝麻信用服务
					result.setCode(Constant.FAIL);
					result.setMsg("现个人信息暂时无法使用芝麻信用服务");
					return result;
				} else {
					if (response.getAuthorized() != null && response.getAuthorized().booleanValue() == false) {
						// 需要授权，返回授权页面
						String url = ZhimaUtil.getAuthorizePageUrl(member.getMemberNo(), member.getIdCard(),
								member.getMemberName());
						if (url != null && !url.equals("")) {
							result.setCode("1");
							result.setData(url);
							return result;
						} else {
							result.setCode(Constant.FAIL);
							result.setMsg("调用芝麻授权失败！");
						}

					} else {
						// 直接查询信用分
						MemberZhimaScore memberZhimaScore = new MemberZhimaScore();
						memberZhimaScore.setMemberNo(memberNo);
						memberZhimaScore.setOpenId(response.getOpenId());
						ResultInfo<MemberZhimaScore> getScoreResult = memberZhimaScoreService
								.getScoreForZhiMa(memberZhimaScore);
						if (getScoreResult.getCode().equals(Constant.SECCUESS)) {
							memberZhimaScore = getScoreResult.getData();
							if (memberZhimaScore.getScore() != null) {

								SysParam sysParam = sysParamService.getByParamKey(MemberConstant.ZMXY_FRACTION);
								if (sysParam == null || sysParam.getParamValue().equals("")) {
									result.setCode(Constant.FAIL);
									result.setMsg("未设置芝麻信用评分。");
									return result;
								} else {

									if (memberZhimaScore.getScore() >= Integer.parseInt(sysParam.getParamValue())) {
										// 用户芝麻评分可以减免押金
										// 修改押金状态信息
										DepositStatus s = depositStatusService.getDepositStatus(memberNo);
										s.setDepositStatus(1);
										s.setPaymentType(2);
										depositStatusService.updateDepositStatus(s, null);
										result.setCode("2");
										result.setMsg("会员已用芝麻信用评分减免押金");
										return result;

									} else {
										result.setCode(Constant.FAIL);
										result.setMsg("会员已用芝麻信用评分未达到要求！");
										return result;
									}

								}

							}
						} else {
							result.setCode(Constant.FAIL);
							result.setMsg("获取芝麻信用分失败！");
						}
					}
				}
			} catch (Exception e) {
				result.setCode(Constant.FAIL);
				result.setMsg("系统错误！");
			}
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("会员不存在！");
		}
		return result;
	}

	/**
	 * 注册
	 * 
	 * @param member
	 * @param veCode
	 *            短信验证码
	 * @param invitationCode
	 *            推荐人邀请码
	 * @return
	 */
	@RequestMapping("/register")
	@ResponseBody
	public ResultInfo<String> register(Member member, String veCode, String invitationCode) {

		ResultInfo<String> rs = new ResultInfo<String>();
		// 验证验证码
		if (veCode != null && !veCode.equals("")) {
			// 从缓存中取出验证码
			String regCode = cacheUtil.get("regCode" + member.getMobilePhone());
			if (veCode.equals(regCode)) {
				// 验证手机号是否已经存在
				ResultInfo<String> resultInfo = uniquePhone(member.getMobilePhone());

				if (resultInfo.getCode().equals(MemberConstant.phone_code)) {
					log.info("resultInfo----------------:code" + MemberConstant.phone_code);

					// if ("1".equals(isDecrypt)) {
					try {
						// log.info("取密码:-----------------:" +
						// member.getPassword());
						String password = AESCipher.aesDecryptString(member.getPassword());
						member.setPassword(password);
						log.info("password----------------:" + password);
					} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
							| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
							| UnsupportedEncodingException e) {
						e.printStackTrace();
						// 系统错误
						rs.setCode(MemberConstant.fail_code);
						rs.setMsg(MemberConstant.fail_msg);
						log.info("前端注册1-----------" + MemberConstant.fail_msg);
						return rs;
					}

					member.setMemberPointsValue(0);
					MemberLevel ml = new MemberLevel();
					ml.setIsAvailable(1);
					ml.setIsDeleted(0);
					List<MemberLevel> mList = memberLevelService.getMemberLevelList(new Query(ml));
					if (mList != null && mList.size() > 0) {
						member.setMemberLevelId(mList.get(0).getMemberLevelId());
					}
					// 根据推荐码查询会员编号作为推荐人id存入会员表
					if (invitationCode != null && !invitationCode.equals("")) {
						Member members = memberService.getInvitationCode(invitationCode);
						if (members != null) {
							member.setRefereeId(members.getMemberNo());
						} else {
							// 邀请码不存在
							rs.setCode("5");
							return rs;
						}
					}
					// 生成会员自己的推荐码
					String memberNo = memberService.generatePK();
					member.setMemberNo(memberNo);
					String memberInvitationCode = GenerateInvitationCodeUtil.toRandomCode(Long.valueOf(memberNo));
					member.setInvitationCode(memberInvitationCode);
					member.setUsersType(Constant.MEMBER_TYPE);// 注册类型 会员类型

					ResultInfo<Member> result = memberService.addMemberInfo(member, getOperator());

					rs.setCode(result.getCode());
					rs.setMsg(result.getMsg());
					if (result.getCode().equals(MemberConstant.success_code)) {
						rs.setData(member.getMemberNo());
					}

				} else if (resultInfo.getCode().equals(MemberConstant.success_code)) {
					// 手机号已存在
					rs.setCode(MemberConstant.phone_code);
					rs.setMsg(MemberConstant.phone_exsit_msg);
				} else if (resultInfo.getCode().equals(MemberConstant.fail_code)) {
					// 系统错误
					rs.setCode(MemberConstant.fail_code);
					rs.setMsg(MemberConstant.fail_msg);
					log.info("前端注册2-----------" + MemberConstant.fail_msg);
				}
			} else {
				// 验证码错误
				rs.setCode(MemberConstant.fail_code_code);
				rs.setMsg(MemberConstant.fail_code_msg);
			}
		} else {
			// 验证码不能为空
			rs.setCode(MemberConstant.fail_code);
			rs.setMsg(MemberConstant.null_code_msg);
		}
		return rs;
	}

	/**
	 * 判断手机号是否唯一
	 */
	@RequestMapping("/uniquePhone")
	@ResponseBody
	public ResultInfo<String> uniquePhone(String mobilePhone) {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		if (mobilePhone != null && !mobilePhone.equals("")) {
			if (memberService.getMemberByPhone(mobilePhone) != null) {
				resultInfo.setCode(MemberConstant.success_code);
				resultInfo.setMsg(MemberConstant.phone_exsit1_msg);
			} else {
				resultInfo.setCode(MemberConstant.phone_code);
				resultInfo.setMsg(MemberConstant.phone_exsit2_msg);
			}
		} else {
			resultInfo.setCode(MemberConstant.fail_code);
			resultInfo.setMsg(MemberConstant.fail_msg);
		}

		return resultInfo;
	}

	/**
	 * 获取会员信息
	 */
	@RequestMapping("/memberCenter")
	@ResponseBody
	public ResultInfo<MrVo> memberCenter(String memberNo) {

		return memberIofo(memberNo);
	}

	/**
	 * 我的钱包
	 * 
	 * @throws Exception
	 * @throws ParseException
	 */
	@RequestMapping("/memberWallet")
	@ResponseBody
	public ResultInfo<WalletVo> memberWallet(String memberNo) throws ParseException, Exception {
		ResultInfo<WalletVo> resultInfo = new ResultInfo<WalletVo>();
		WalletVo v = new WalletVo();

		try {
			// 查找会员健身卡信息
			MemberGymCard mg = new MemberGymCard();
			mg.setMemberNo(memberNo);
			mg.setGymCardType(1);
			mg.setGymcardStatus(1);
			List<MemberGymCard> ms = memberGymCardService.getMemberGymCardList(new Query(mg));
			if (ms.size() > 0) {
				MemberGymCard g = ms.get(0);
				// 判断健身卡有没有过期
				long t1 = ECDateUtils.formatDateToDateNew(new Date()).getTime();
				long t2 = g.getEndTime().getTime();
				if (t1 >= t2) {
					// 健身卡已过期，修改健身卡状态
					g.setGymcardStatus(2);
					memberGymCardService.updateMemberGymCard(g, null);
					v.setIsGymCard(2 + "");
					v.setGymCardDay(0 + "");
				} else {
					// 根据健身课有效结束时间和当前时间，计算健身卡剩余有效天数
					Long n = ECDateUtils.getDistanceDays(ECDateUtils.formatDate(new Date(), "yyyy-MM-dd"),
							ECDateUtils.formatDate(g.getEndTime(), "yyyy-MM-dd"));
					v.setIsGymCard(1 + "");
					v.setGymCardDay((n) + "");

				}

			} else {
				v.setIsGymCard(2 + "");
				v.setGymCardDay(0 + "");
			}

			// 查找会员可用优惠券可用数量
			Coupon c = new Coupon();
			c.setMemberNo(memberNo);
			c.setIsAvailable(1);
			c.setUsedStatus(0);
			c.setAvailableTime2Start(new Date());
			List<Coupon> ys = couponService.getCouponList(new Query(c));
			v.setMemberCoupon(ys.size() + "");
			// 查询会员余额信息
			MemberBalance mc = memberBalanceService.getMemberBalance(memberNo);
			// 账户余额
			if (mc.getMemberBalance() != null) {
				v.setMemberBalance(mc.getMemberBalance() + "");
			}
			// 剩余 押金
			if (mc.getMemberDeposit() != null) {
				v.setMemberDeposit(mc.getMemberDeposit() + "");
			}
			// 剩余课程节数
			if (mc.getCourseNumber() != null) {
				v.setCourseNumber(mc.getCourseNumber() + "");
			}
			// 获取会员押金状态信息
			DepositStatus ds = depositStatusService.getDepositStatus(memberNo);
			v.setDepositStatus(ds.getDepositStatus() + "");
			if (ds.getDepositStatus() == 1) {
				v.setPaymentType(ds.getPaymentType() + "");
			}
			// 获取会员状态
			Member member = memberService.getMember(memberNo);
			if (member.getIsBlacklist() == 0) {
				v.setMemberStatus(0);// 非黑名单
			} else {
				v.setMemberStatus(1);// 黑名单用户
			}
			resultInfo.setData(v);
			resultInfo.setCode(MemberConstant.success_code);
			resultInfo.setMsg(MemberConstant.success_msg);

		} catch (Exception e) {
			resultInfo.setCode(MemberConstant.fail_code);
			resultInfo.setMsg(MemberConstant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;
	}

	/**
	 * 编辑会员信息
	 */
	@RequestMapping("/updateMember")
	@ResponseBody
	public ResultInfo<Member> updateMember(Member member, String type) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();
		if (member.getBy() != null && !member.getBy().equals("")) {
			member.setBirthday(ECDateUtils.parseDate(member.getBy(), "yyyy-MM-dd"));
		}

		if (member.getMemberPhotoUrl() != null && !member.getMemberPhotoUrl().equals("")) {
			String pathUrl11 = member.getMemberPhotoUrl().substring(imgPath.length() + 1);
			member.setMemberPhotoUrl(pathUrl11);
		}
		resultInfo = memberService.updateMember(member, null);
		if (resultInfo.getCode().equals(Constant.SECCUESS)) {
			// 判断是否下发优惠券
			if (type != null && !type.equals("") && type.equals("1")) {
				// 下发优惠券
				CouponPlan c = new CouponPlan();
				c.setCouponType(3);
				List<CouponPlan> ls = couponPlanService.getCouponPlanList(new Query(c));
				if (ls.size() > 0) {
					c = ls.get(0);
					if (c.getExistingQuantity() == null) {
						c.setExistingQuantity(0);
					}
					c.setExistingQuantity(c.getExistingQuantity() + 1);
					couponPlanService.update(c, null);

					Coupon couponTemp = new Coupon();
					couponTemp.setMemberNo(member.getMemberNo());

					// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
					// couponTemp.setCouponNo(this.generatePK());
					couponTemp.setPlanNo(c.getPlanNo());
					couponTemp.setTitle(c.getTitle());
					couponTemp.setCouponType(c.getCouponType());
					couponTemp.setCouponMethod(c.getCouponMethod());
					couponTemp.setDiscount(c.getDiscount());
					couponTemp.setDiscountAmount(c.getDiscountAmount());
					if (c.getAvailableDays() != null) {
						Calendar calendar = new GregorianCalendar();
						Date date = new Date();
						calendar.setTime(date);
						couponTemp.setAvailableTime1(date);
						couponTemp.setAvailableTime2(ECDateUtils.getDateAfter(date, c.getAvailableDays()));
					} else {
						couponTemp.setAvailableTime1(c.getAvailableTime1());
						couponTemp.setAvailableTime2(c.getAvailableTime2());
					}
					couponTemp.setAvailableDays(c.getAvailableDays());

					// 设置发放时间为当前时间
					Date now = new Date();
					couponTemp.setIssueTime(now);

					// 设置手动发放优惠券时的发放人id

					couponTemp.setIssueMethod(1);
					// couponTemp.setIssueChannel(c.getIssueChannel());
					// 如果是系统发放則需要补充发放人

					// 保存健身时长 和 课程体验券节数
					couponTemp.setFreeFitnessTime(c.getFreeFitnessTime());
					couponTemp.setFreeCourseNumber(c.getFreeCourseNumber());
					couponService.addCoupon(couponTemp, null);
				}

			}

		}

		return resultInfo;

	}

	public ResultInfo<MrVo> memberIofo(String memberNo) {
		ResultInfo<MrVo> resultInfo = new ResultInfo<MrVo>();

		MrVo v = new MrVo();
		try {
			Member m = memberService.getMember(memberNo);
			v.setMemberNo(m.getMemberNo());
			if (m.getMemberPhotoUrl() != null && !m.getMemberPhotoUrl().equals("")) {
				v.setMemberPhotoUrl(imgPath + "/" + m.getMemberPhotoUrl());
			}

			v.setMemberNick(m.getMemberNick());
			v.setMobilePhone(m.getMobilePhone());

			if (m.getMemberLevelId() != null && !m.getMemberLevelId().equals("")) {
				MemberLevel e = memberLevelService.getMemberLevel(m.getMemberLevelId());
				if (e != null) {
					v.setLevelName(e.getLevelName());
				} else {
					v.setLevelName("");
				}
			}

			if (m.getSex() != null) {
				if (m.getSex() == 1) {
					v.setSex("男");
				} else {
					v.setSex("女");
				}

			}
			if (m.getHeight() != null) {
				v.setHeight(m.getHeight() + "");
			}
			if (m.getWeight() != null) {
				v.setWeight(m.getWeight() + "");
			}
			if (m.getBirthday() != null) {
				v.setBirthday(ECDateUtils.formatDate(m.getBirthday(), "yyyy-MM-dd"));
			}
			v.setMemberName(m.getMemberName());
			v.setHabit(m.getHabit());
			resultInfo.setData(v);
			resultInfo.setCode(MemberConstant.success_code);
		} catch (Exception e) {
			e.printStackTrace();
			resultInfo.setCode(MemberConstant.fail_code);
			resultInfo.setMsg(MemberConstant.fail_msg);
		}

		return resultInfo;
	}

	@RequestMapping("/getToken")
	@ResponseBody
	public ResultInfo<String> getToken() {
		ResultInfo<String> resultInfo = new ResultInfo<String>();
		String token = UUID.randomUUID().toString();
		resultInfo.setCode(Constant.SECCUESS);
		resultInfo.setMsg("获取Token成功。");
		resultInfo.setData(token);
		return resultInfo;
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobilePhone手机号
	 * @param time
	 *            时间戳
	 * @param sign签名（规则为：手机号+时间戳+key+type
	 *            进行加密， 安卓与IOS的key值不同）
	 * @param typeIOS
	 *            1 安卓 2
	 * @param tag注册页
	 *            1 忘记密码页 2 修改手机号3
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sendVerificationCode")
	@ResponseBody
	public ResultInfo<String> sendVerificationCode(String mobilePhone, String time, String sign, String type,
			String tag, String picCode, String token, HttpServletRequest request) throws Exception {

		// 定义返回结果集
		ResultInfo<String> resultInfo = new ResultInfo<>();
		// 验证时间戳
		if (null != time && time.trim().length() > 0 && !"".equals(time)) {
			String failTime = sysParamService.getByParamKey(Constant.SYS_FAILURE_TIME).getParamValue();
			Integer sysFailureTime = Integer.parseInt(failTime);
			Long reqTime = new Long(time);
			Integer diffMin = ECDateUtils.differMinutes(new Date(), new Date(reqTime)).intValue();
			Integer resu = Math.abs(diffMin);
			// 时间戳合法
			if (resu < sysFailureTime) {
				// 验证签名
				String result = "";
				// 对key进行验证 数据重新加密 若两次不匹配 说明数据被篡改 直接返回
				if ("1".equals(type)) {
					result = ECMd5Utils.hash(mobilePhone + time + type + Constant.IOS_KEY);
				} else if ("2".equals(type)) {
					result = ECMd5Utils.hash(mobilePhone + time + type + Constant.ANDROID_KEY);
				} else {
					// 返回错误 前台也做校验
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("请求参数异常，为空");
					resultInfo.setData("");
					return resultInfo;
				}

				if (null != sign && sign.equals(result)) {
					// 发送短信验证码方法
					try {
						if (StringUtils.isEmpty(mobilePhone) || StringUtils.isEmpty(tag)) {
							// 返回错误 前台也做校验
							resultInfo.setCode(Constant.FAIL);
							resultInfo.setMsg("请求参数异常，为空");
							resultInfo.setData("");
						} else {
							// String remoteAddr = request != null ?
							// request.getRemoteAddr() : "";
							String remoteAddr = request != null ? request.getRemoteAddr() : "";
							int isForbidden = IpForbidener.isForbidden(remoteAddr);
							if (isForbidden == 0 || isForbidden == 2) {
								log.info("####### sendVerificationCode, ip:" + remoteAddr + "is Allow");
							} else if (StringUtils.isBlank(picCode)) {
								resultInfo.setCode("16");
								resultInfo.setMsg("请输入图形验证码。");
								return resultInfo;
							}
							Member currentMb = memberService.getMemberByPhone(mobilePhone);
							// 判断哪个页面传递的信息
							if ("1".equals(tag)) { // 注册页,判断当手机号是否已经注册
								if (null != currentMb) {
									resultInfo.setCode(MemberConstant.fail_code);
									resultInfo.setMsg(MemberConstant.phone_exsit_msg);
								} else {

									HttpSession httpSession = request.getSession();

									if (StringUtils.isNotBlank(picCode)) {

										Object randomValidateCodeKey = null;
										log.info("token=================================" + token);
										if (StringUtils.isNotBlank(token)) {
											randomValidateCodeKey = RandomValidateCode.codeMap.get(token);
										} else {
											randomValidateCodeKey = request.getSession()
													.getAttribute("RANDOMVALIDATECODEKEY");
										}
										log.info("randomValidateCodeKey=" + randomValidateCodeKey + ",picCode="
												+ picCode);
										if (randomValidateCodeKey == null) {
											resultInfo.setCode(MemberConstant.fail_code);
											resultInfo.setMsg("此图形验证码已失效，请重新获取。");
											return resultInfo;
										}
										String randImage = randomValidateCodeKey.toString();
										if (picCode.equalsIgnoreCase(randImage)) {
											httpSession.removeAttribute("RANDOMVALIDATECODEKEY");
											RandomValidateCode.codeMap.remove(token);
											log.info("===================================验证码通过:picCode=" + picCode
													+ ",randImage=" + randImage);
										} else {
											log.info("===================================验证码不通过:picCode=" + picCode
													+ ",randImage=" + randImage);
											resultInfo.setCode(MemberConstant.fail_code);
											resultInfo.setMsg("图形验证码不正确。");
											return resultInfo;
										}
									}

									// 产生随机数字的验证码
									String validCode = ECRandomUtils.getRandomNumberStr(4);
									System.out.println("####### sendVerificationCode, ip:" + remoteAddr + "is Allow");
									boolean sate = false;
									try {
										// 聚合
										// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
										// validCode, tplId);
										// 聚通达,浪驰
										sate = sendMsgCommonInterfaceService.sendMsgPost(mobilePhone, validCode,
												MsgConstant.REGISTTYPE);
										// 叮咚云
										// sate =
										// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
										// validCode, "","1"); //1代表 验证码类短信
										// 阿里云
										// sate =
										// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
										// validCode,
										// tplId);
										System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
									} catch (Exception e) {
										e.printStackTrace();
									}

									// 验证码保存在缓存里面,注册的验证码是120秒有效
									cacheUtil.set("regCode" + mobilePhone.trim(), validCode,
											MemberConstant.regCodeExpire);
									resultInfo.setCode(Constant.SECCUESS);
									resultInfo.setMsg("验证码已发送至您的手机，请注意查收！");
									// resultInfo.setData(validCode);
								}
							} else if ("2".equals(tag)) { // 忘记密码页
															// 判断手机号是否存在用户
								if (currentMb != null) {
									// 产生随机数字的验证码
									String validCode = ECRandomUtils.getRandomNumberStr(4);
									System.out.println("####### sendVerificationCode, ip:" + remoteAddr + "is Allow");
									boolean sate = false;
									try {
										// 聚合
										// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
										// validCode, tplId);
										// 聚通达,浪驰
										sate = sendMsgCommonInterfaceService.sendMsgPost(mobilePhone, validCode,
												MsgConstant.FORGETPWDTYPE);
												// 叮咚云
												// sate =
												// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
												// validCode, "","1"); //1代表
												// 验证码类短信

										// 阿里云
										// sate =
										// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
										// validCode,
										// tplId);
										System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
									} catch (Exception e) {
										e.printStackTrace();
									}

									// 验证码保存在缓存里面,注册的验证码是120秒有效
									cacheUtil.set("regCode" + mobilePhone.trim(), validCode,
											MemberConstant.regCodeExpire);
									resultInfo.setCode(Constant.SECCUESS);
									resultInfo.setMsg("验证码已发送至您的手机，请注意查收！");
									// resultInfo.setData(validCode);

								}

								else {
									resultInfo.setCode(MemberConstant.fail_code);
									resultInfo.setMsg(MemberConstant.phone_exsit3_msg);
								}
							} else if ("3".equals(tag)) {

								// 产生随机数字的验证码
								String validCode = ECRandomUtils.getRandomNumberStr(4);
								System.out.println("####### sendVerificationCode, ip:" + remoteAddr + "is Allow");
								boolean sate = false;
								try {
									// 聚合
									// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
									// validCode, tplId);
									// 聚通达,浪驰
									sate = sendMsgCommonInterfaceService.sendMsgPost(mobilePhone, validCode,
											MsgConstant.FORGETPWDTYPE);
											// 叮咚云
											// sate =
											// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
											// validCode, "","1"); //1代表
											// 验证码类短信

									// 阿里云
									// sate =
									// sendMsgCommonInterfaceService.sendMsgPost(mobilePhone,
									// validCode,
									// tplId);
									System.err.println("发送结果！！！！！！！！！！！！！！" + sate);
								} catch (Exception e) {
									e.printStackTrace();
								}

								// 验证码保存在缓存里面,注册的验证码是120秒有效
								cacheUtil.set("regCode" + mobilePhone.trim(), validCode, MemberConstant.regCodeExpire);
								resultInfo.setCode(Constant.SECCUESS);
								resultInfo.setMsg("验证码已发送至您的手机，请注意查收！");
								// resultInfo.setData(validCode);

							} else {
								resultInfo.setCode(Constant.FAIL);
								resultInfo.setMsg("无法识别哪个业务流程请求验证码！");
								resultInfo.setData("");
							}
							/*
							 * } else { System.out.println(
							 * "####### sendVerificationCode, ip:" + remoteAddr
							 * + "is Forbidden");
							 * resultInfo.setCode(Constant.FAIL);
							 * resultInfo.setMsg("请勿频繁点击发送验证码，请稍后再试！");
							 * resultInfo.setData(""); }
							 */
						}

					} catch (Exception e) {
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setMsg("程序出现未知异常");
						e.printStackTrace();
					}
				} else {
					// 签名校验失败
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setMsg("签名校验失败，请稍后再试");
					resultInfo.setData("");
				}
			} else {
				// 时间戳校验失败
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setMsg("请求超时，请重新发起请求");
				resultInfo.setData("");
			}
		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("请求参数异常，时间为空");
			resultInfo.setData("");
		}
		return resultInfo;
	}

	/**
	 * 设备报修
	 */
	@RequestMapping("/repair")
	@ResponseBody
	public ResultInfo<EquipmentFeedback> repair(String memberNo, String devId, String memo) {
		ResultInfo<EquipmentFeedback> resultInfo = new ResultInfo<EquipmentFeedback>();

		FitnessEquipment f = fitnessEquipmentService.getFeByDevId(devId);
		if (f != null) {
			Member m = memberService.getMember(memberNo);
			EquipmentFeedback k = new EquipmentFeedback();
			k.setFitnessEquipmentNo(f.getFitnessEquipmentNo());
			k.setCityId(f.getCityId());
			k.setSortName(f.getSortName());
			k.setCityName(f.getCityName());
			k.setStoreNo(f.getStoreNo());
			k.setStoreName(f.getStoreName());
			k.setFeedbackId("28149624999922");
			k.setFeedbackName("设备故障");
			k.setFeedbackStatus(0);
			k.setMemberNo(m.getMemberNo());
			k.setMemberName(m.getMemberName());
			k.setMobilePhone(m.getMobilePhone());
			k.setMemo(memo);
			k.setTurnStatus(0);
			resultInfo = equipmentFeedbackService.addEquipmentFeedback(k, null);

		} else {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("没有找到对应设备，请检查设备编码是否正确。");
		}

		return resultInfo;

	}

	/**
	 * 查看会员健身记录
	 */
	@RequestMapping("/fitnessRecord")
	@ResponseBody
	public ResultInfo<FitnessRecordVo> fitnessRecord(String memberNo, String startTimeStart, String endTimeEnd,
			Integer page) {

		ResultInfo<FitnessRecordVo> resultInfo = new ResultInfo<FitnessRecordVo>();

		EquipmentRecord r = new EquipmentRecord();
		FitnessRecordVo v = new FitnessRecordVo();
		List<FitnessRecordLsVo> vs = new ArrayList<FitnessRecordLsVo>();
		try {
			if ((startTimeStart == null || startTimeStart.equals(""))
					&& (endTimeEnd == null || endTimeEnd.equals(""))) {
				// 如果没有起始时间，查当月健身记录
				r.setStartTimeStart(ECDateUtils.getFirstDayMonth());
				r.setEndTimeEnd(new Date());

			} else {
				r.setStartTimeStart(ECDateUtils.parseDate(startTimeStart + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
				r.setEndTimeEnd(ECDateUtils.parseDate(endTimeEnd + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}

			r.setMemberNo(memberNo);
			r.setUseStatus(1);
			Query q = new Query(page, 10, r);
			PageFinder<EquipmentRecord> rs = equipmentRecordService.getEquipmentRecordPagedList(q);
			List<EquipmentRecord> ls = rs.getData();
			v.setStartTimeStart(ECDateUtils.formatDate(r.getStartTimeStart(), "yyyy-MM-dd"));
			v.setEndTimeEnd(ECDateUtils.formatDate(r.getEndTimeEnd(), "yyyy-MM-dd"));
			if (ls.size() > 0) {
				Double rDistTotal = 0D;
				Double rCalTotal = 0D;
				for (EquipmentRecord e : ls) {
					FitnessRecordLsVo vo = new FitnessRecordLsVo();
					vo.setrCal(e.getrCal() == null ? "0" : e.getrCal().toString());
					vo.setrDist(e.getrDist() == null ? "0" : e.getrDist().toString());
					vo.setSortName(e.getSortName());
					vo.setWhenLong(e.getWhenLong() == null ? "0" : e.getWhenLong());
					vo.setStartTime(ECDateUtils.formatDate(e.getStartTime(), "yyyy-MM-dd HH:mm"));

					rDistTotal = ECCalculateUtils.add(Double.parseDouble(vo.getrDist()), rDistTotal);
					rCalTotal = ECCalculateUtils.add(Double.parseDouble(vo.getrCal()), rCalTotal);
					vs.add(vo);
				}
				v.setrCalTotal(rCalTotal + "");
				v.setrDistTotal(rDistTotal + "");
				v.setLs(vs);
				resultInfo.setData(v);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg(Constant.YES_RECORD);
			} else {
				resultInfo.setData(v);
				resultInfo.setCode(Constant.NO_RESULT);
				resultInfo.setMsg(Constant.NO_RECORD);
			}
		} catch (NumberFormatException e) {
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.fail_msg);
			e.printStackTrace();
		}

		return resultInfo;

	}

	public static void main(String[] args) {
		/*
		 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 * Calendar c = Calendar.getInstance(); c.add(Calendar.MONTH, 0);
		 * c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天 String first =
		 * format.format(c.getTime());
		 * 
		 * System.out.println(c.getTime());
		 */
	}

	/**
	 * 登录 app传过来的密码已经进过aes和md5加密
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ResultInfo<MemberInfoVo> login(Member member) {
		ResultInfo<MemberInfoVo> resultInfo = new ResultInfo<MemberInfoVo>();

		String passwordN = "";
		// 加密
		try {
			String password = AESCipher.aesDecryptString(member.getPassword());
			member.setPassword(password);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException e) {
			resultInfo.setCode(MemberConstant.fail_code);
			resultInfo.setMsg("用户名或密码错误");
			return resultInfo;
		}
		passwordN = member.getPassword();

		Member mm = memberService.getMemberByPhone(member.getMobilePhone());
		if (mm != null) {
			if (mm.getPassword().equals(passwordN)) {
				if (mm.getIsBlacklist() != null && mm.getIsBlacklist() == 1) {
					resultInfo.setCode(MemberConstant.fail_code);
					resultInfo.setMsg("会员已被移入黑名单，暂不能登录。");
					return resultInfo;
				}
				String token = TokenGeneratorUtil.tokenGenerator();
				mm.setToken(token);
				mm.setTokenGenerateTime(new Date());
				memberService.updateMember(mm, null);
				resultInfo.setCode(Constant.SECCUESS);
				MemberInfoVo vo = new MemberInfoVo();
				vo.setToken(token);
				vo.setMemberNo(mm.getMemberNo());
				vo.setMobilePhone(mm.getMobilePhone());
				vo.setMid(mm.getMid());

				resultInfo.setData(vo);
				resultInfo.setMsg(MemberConstant.login_success_msg);
			} else {
				resultInfo.setCode(MemberConstant.fail_code);
				resultInfo.setMsg(MemberConstant.login_fail_msg);
			}
		} else {
			resultInfo.setCode(MemberConstant.fail_code);
			resultInfo.setMsg(MemberConstant.login_fail_msg);
		}
		return resultInfo;
	}

	// 兑换优惠卷
	@RequestMapping("/getMyChangeCoupon")
	@ResponseBody
	public ResultInfo<RedeemCode> getMyDetailInvoice(String memberNo, String redCode) {
		ResultInfo<RedeemCode> result = new ResultInfo<RedeemCode>();
		if (redCode != null) {
			Operator operator = new Operator();
			result = redeemCodeService.redeemCoupon(redCode, memberNo, operator);
		} else {
			result.setCode(Constant.FAIL);
			result.setMsg("兑换失败");
		}

		return result;

	}

	/**
	 * 会员优惠券
	 */
	@RequestMapping("/myCoupon")
	@ResponseBody
	public ResultInfo<List<CouponsVo>> myCoupon(String memberNo, Integer page) {

		ResultInfo<List<CouponsVo>> resultInfo = new ResultInfo<List<CouponsVo>>();

		List<CouponsVo> vs = new ArrayList<CouponsVo>();

		try {
			Coupon c = new Coupon();
			c.setMemberNo(memberNo);
			c.setIsAvailable(1);
			c.setUsedStatus(0);
			c.setAvailableTime2Start(new Date());
			PageFinder<Coupon> pList = couponService.getCouponPagedList(new Query(page, 10, c));
			List<Coupon> ls = pList.getData();
			if (ls.size() > 0) {
				for (Coupon n : ls) {
					CouponsVo v = new CouponsVo();
					v.setTitle(n.getTitle());
					// v.setCouponMethod(n.getCouponMethod()+"");
					if (n.getCouponMethod() == 1) {
						v.setDenomination(ECCalculateUtils.mul(n.getDiscount(), 10D) + "折");
					} else if (n.getCouponMethod() == 2) {
						v.setDenomination("￥" + n.getDiscountAmount());
					} else if (n.getCouponMethod() == 3) {
						v.setDenomination(n.getFreeFitnessTime() + "天");
					} else if (n.getCouponMethod() == 4) {
						v.setDenomination(n.getFreeCourseNumber() + "节");
					}

					// if (n.getUsedStatus() == 1) {
					// v.setUsedStatus("已使用");
					// } else {
					// Long t1 = n.getAvailableTime2().getTime();
					// Long t2 = new Date().getTime();
					// if (t2 > t1) {
					// v.setUsedStatus("已过期");
					// } else {
					// v.setUsedStatus("未使用");
					// }
					// }
					v.setUsedStatus("");
					v.setAvailableTime2(ECDateUtils.formatDate(n.getAvailableTime2(), "yyyy-MM-dd HH:mm"));
					vs.add(v);

				}

				resultInfo.setData(vs);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setMsg(Constant.YES_RECORD);

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
	 * 忘记密码，修改密码
	 */
	@RequestMapping("/forgetPassword")
	@ResponseBody
	public ResultInfo<Member> forgetPassword(Member member, String veCode) {
		ResultInfo<Member> resultInfo = new ResultInfo<Member>();
		if (member.getMobilePhone() != null && !"".equals(member.getMobilePhone())) {
			Member memberPhone = memberService.getMemberByPhone(member.getMobilePhone());
			if (memberPhone != null) {
				// 从缓存中取出验证码
				String regCode = cacheUtil.get("regCode" + member.getMobilePhone());
				if (veCode != null && !veCode.equals("")) {
					if (veCode.equals(regCode)) {
						if (memberService.getMemberByPhone(member.getMobilePhone()) != null) {
							Member m = memberService.getMemberByPhone(member.getMobilePhone());
							try {
								String password = AESCipher.aesDecryptString(member.getPassword());
								member.setPassword(password);
							} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
									| InvalidAlgorithmParameterException | IllegalBlockSizeException
									| BadPaddingException | UnsupportedEncodingException e) {
								resultInfo.setCode(MemberConstant.fail_code);
								resultInfo.setMsg("用户名或登录密码错误");
								return resultInfo;
							}
							m.setPassword(member.getPassword());

							memberService.updateMember(m, getOperator());
							resultInfo.setCode(MemberConstant.success_code);
							resultInfo.setData(m);
							resultInfo.setMsg(MemberConstant.success_update_psd_msg);
						} else {
							resultInfo.setCode(MemberConstant.fail_code);
							resultInfo.setMsg(MemberConstant.fail_msg);
						}
					} else {
						resultInfo.setCode(MemberConstant.phone_code);
						resultInfo.setMsg(MemberConstant.ver_code_msg);
					}
				}
			} else {
				resultInfo.setCode(MemberConstant.fail_code);
				resultInfo.setMsg(MemberConstant.fail_msg_phone);
			}
		}

		return resultInfo;
	}

}

class IpForbidener {
	private static LRUMap<String, IpEntry> lruMap = new LRUMap<String, IpEntry>(100, 5000);

	private static int forbiddenTime = 3 * 60 * 60 * 1000;

	private static int minInterval = 3 * 60 * 60 * 1000;

	private static int maxAccessTimes = 2;

	private static int minAllowAccessIntever = 45 * 1000;

	/** 获取配置文件中数值 */

	public static String getString(String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream((getAppPath() + "ipforbidden.properties")));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取应用路径
	 * 
	 * @return String
	 * @throws UnsupportedEncodingException
	 */
	public static String getAppPath() throws UnsupportedEncodingException {
		String configPath = IpForbidener.class.getClassLoader().getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}

	public static int isForbidden(String ip) {
		if (ip == null || ip.equals("")) {
			return 1;
		}

		IpEntry ipEntry = lruMap.get(ip);
		Date now = new Date();

		if (ipEntry == null) {
			ipEntry = new IpEntry();
			ipEntry.setLastAccessTime(now);
			ipEntry.setAccessTimes(1);
			ipEntry.setIp(ip);
			lruMap.put(ip, ipEntry);
			return 2;
		}

		if (ipEntry.isForbidden()) {
			if (now.getTime() - ipEntry.getLastAccessTime().getTime() > forbiddenTime) { // 超过解封时间
				// 解封
				ipEntry.setForbidden(false);
				ipEntry.setAccessTimes(1);
				ipEntry.setLastAccessTime(now);
				return 0;
			} else {
				return 1;
			}
		}

		if (now.getTime() - ipEntry.getLastAccessTime().getTime() < minAllowAccessIntever) {
			// 访问过于频繁，封禁
			ipEntry.setLastAccessTime(now);
			ipEntry.setAccessTimes(ipEntry.getAccessTimes() + 1);
			if (ipEntry.getAccessTimes() > maxAccessTimes) {
				// 封禁
				ipEntry.setForbidden(true);
			}
			// ipEntry.setForbidden(true);
			return 1;
		}

		if (now.getTime() - ipEntry.getLastAccessTime().getTime() > minInterval) {
			// 太久没访问，重新计时
			ipEntry.setLastAccessTime(now);
			ipEntry.setAccessTimes(1);
			ipEntry.setForbidden(false);
			return 0;
		} else {
			ipEntry.setLastAccessTime(now);
			ipEntry.setAccessTimes(ipEntry.getAccessTimes() + 1);
			if (ipEntry.getAccessTimes() > maxAccessTimes) {
				// 封禁
				ipEntry.setForbidden(true);
				return 1;
			} else {
				return 0;
			}
		}

	}
}

class IpEntry {
	private String ip;

	private Date lastAccessTime;

	private int accessTimes;

	private boolean isForbidden = false;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public int getAccessTimes() {
		return accessTimes;
	}

	public void setAccessTimes(int accessTimes) {
		this.accessTimes = accessTimes;
	}

	public boolean isForbidden() {
		return isForbidden;
	}

	public void setForbidden(boolean isForbidden) {
		this.isForbidden = isForbidden;
	}

	@Override
	public String toString() {
		return "IpEntry [ip=" + ip + ", lastAccessTime=" + lastAccessTime + ", accessTimes=" + accessTimes
				+ ", isForbidden=" + isForbidden + "]";
	}
}