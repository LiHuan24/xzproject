package cn.com.shopec.core.order.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;

import cn.com.shopec.common.Operator;
import cn.com.shopec.common.ResultInfo;
import cn.com.shopec.common.constants.Constant;
import cn.com.shopec.common.pay.aliPay.AlipayConfig;
import cn.com.shopec.common.pay.wxPay.GetWxOrderno;
import cn.com.shopec.common.pay.wxPay.PayCommonUtil;
import cn.com.shopec.common.pay.wxPay.TenpayUtil;
import cn.com.shopec.common.pay.wxPay.WxpayConfig;
import cn.com.shopec.common.pay.wxPay.XMLUtil;
import cn.com.shopec.common.sendmsg.MsgConstant;
import cn.com.shopec.common.sendmsg.SendMsgCommonInterfaceService;
import cn.com.shopec.common.utils.ECCalculateUtils;
import cn.com.shopec.common.utils.ECDateUtils;
import cn.com.shopec.common.utils.ECRandomUtils;
import cn.com.shopec.core.common.PageFinder;
import cn.com.shopec.core.common.Query;
import cn.com.shopec.core.member.dao.MemberBalanceDao;
import cn.com.shopec.core.member.model.MemberBalance;
import cn.com.shopec.core.order.common.OrderConstant;
import cn.com.shopec.core.order.dao.ThemeOrderDao;
import cn.com.shopec.core.order.model.ThemeOrder;
import cn.com.shopec.core.order.service.ThemeOrderService;
import cn.com.shopec.core.order.vo.ThemeOrderStatisticVo;
import cn.com.shopec.core.system.common.PrimarykeyConstant;
import cn.com.shopec.core.system.service.PrimarykeyService;
import cn.com.shopec.core.system.service.SysParamService;
import cn.com.shopec.core.themepavilion.dao.ArrayCourseDao;
import cn.com.shopec.core.themepavilion.model.ArrayCourse;
import cn.com.shopec.core.themepavilion.model.Course;
import cn.com.shopec.core.themepavilion.service.CourseService;

/**
 * 主题订单表 服务实现类
 */
@Service
public class ThemeOrderServiceImpl implements ThemeOrderService {

	private static final Log log = LogFactory.getLog(ThemeOrderServiceImpl.class);

	@Resource
	private ThemeOrderDao themeOrderDao;

	@Resource
	private PrimarykeyService primarykeyService;
	
	@Resource
	private SysParamService sysParamService;
	
	@Resource
	private SendMsgCommonInterfaceService sendMsgCommonInterfaceService;
	
	@Resource
	private ArrayCourseDao arrayCourseDao;
	
	@Resource
	private MemberBalanceDao memberBalanceDao;
	
	@Resource
	private CourseService courseService;

	/**
	 * 根据查询条件，查询并返回ThemeOrder的列表
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ThemeOrder> getThemeOrderList(Query q) {
		List<ThemeOrder> list = null;
		try {
			// 已删除的不查出
			ThemeOrder themeOrder = (ThemeOrder) q.getQ();
			if (themeOrder != null) {
				themeOrder.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = themeOrderDao.queryAll(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrder>(0) : list;
		return list;
	}
	
	/**
	 * 根据查询条件，查询并返回ThemeOrder的列表(管理端用)
	 * 
	 * @param q 查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ThemeOrder> getThemeOrderListForManagePage(Query q) {
		List<ThemeOrder> list = null;
		try {
			// 已删除的不查出
			ThemeOrder themeOrder = (ThemeOrder) q.getQ();
			if (themeOrder != null) {
				themeOrder.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = themeOrderDao.queryAllForManagePage(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrder>(0) : list;
		return list;
	}

	/**
	 * 根据查询条件，分页查询并返回ThemeOrder的分页结果
	 * 
	 * @param q
	 *            查询条件
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ThemeOrder> getThemeOrderPagedList(Query q) {
		PageFinder<ThemeOrder> page = new PageFinder<ThemeOrder>();

		List<ThemeOrder> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			ThemeOrder themeOrder = (ThemeOrder) q.getQ();
			if (themeOrder != null) {
				themeOrder.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = themeOrderDao.pageListForManagePage(q);
			// 调用dao统计满足条件的记录总数
			rowCount = themeOrderDao.countForManagePage(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrder>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ThemeOrder> themeOrderPagedList(Query q) {
		PageFinder<ThemeOrder> page = new PageFinder<ThemeOrder>();

		List<ThemeOrder> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			ThemeOrder themeOrder = (ThemeOrder) q.getQ();
			if (themeOrder != null) {
				themeOrder.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 调用dao查询满足条件的分页数据
			list = themeOrderDao.pageList(q);
			// 调用dao统计满足条件的记录总数
			rowCount = themeOrderDao.count(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrder>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}
	
	/**
	 * 
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PageFinder<ThemeOrder> queryLineThemeOrder(Query q) {
		PageFinder<ThemeOrder> page = new PageFinder<ThemeOrder>();

		List<ThemeOrder> list = null;
		long rowCount = 0L;

		try {
			// 已删除的不查出
			ThemeOrder themeOrder = (ThemeOrder) q.getQ();
			if (themeOrder != null) {
				themeOrder.setIsDeleted(Constant.DEL_STATE_NO);
			}

			list = themeOrderDao.queryLineThemeOrder(q);
			// 调用dao统计满足条件的记录总数
			rowCount = themeOrderDao.countLinePage(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrder>(0) : list;

		// 将分页数据和记录总数设置到分页结果对象中
		page.setData(list);
		page.setRowCount(rowCount);

		return page;
	}
	
	
	/**
	 * 根据主键，查询并返回一个ThemeOrder对象
	 * 
	 * @param id
	 *            主键id
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ThemeOrder getThemeOrder(String id) {
		ThemeOrder obj = null;
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回null
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return obj;
		}
		try {
			// 调用dao，根据主键查询
			obj = themeOrderDao.get(id);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return obj;
	}

	/**
	 * 根据主键数组，查询并返回一组ThemeOrder对象
	 * 
	 * @param ids
	 *            主键数组，数组中的主键值应当无重复值，如有重复值时，则返回的列表长度可能小于传入的数组长度
	 * @return
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ThemeOrder> getThemeOrderByIds(String[] ids) {
		List<ThemeOrder> list = null;
		if (ids == null || ids.length == 0) {
			log.info(Constant.ERR_MSG_INVALID_ARG + " ids is null or empty array.");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = themeOrderDao.getByIds(ids);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}

		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrder>(0) : list;

		return list;
	}

	/**
	 * 根据主键，删除特定的ThemeOrder记录
	 * 
	 * @param id
	 *            主键id
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ThemeOrder> delThemeOrder(String id, Operator operator) {
		ResultInfo<ThemeOrder> resultInfo = new ResultInfo<ThemeOrder>();
		if (id == null || id.length() == 0) { // 传入的主键无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " id = " + id);
			return resultInfo;
		}
		try {
			// 调用Dao执行更新操作，并判断语句执行结果
			int count = themeOrderDao.delete(id);
			if (count > 0) {
				resultInfo.setCode(Constant.SECCUESS);
			} else {
				resultInfo.setCode(Constant.FAIL);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_EXCEPTION_CATCHED);
		}
		return resultInfo;
	}

	/**
	 * 新增一条ThemeOrder记录，执行成功后传入对象及返回对象的主键属性值不为null
	 * 
	 * @param themeOrder
	 *            新增的ThemeOrder数据（如果无特殊需求，新增对象的主键值请保留为null）
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ThemeOrder> addThemeOrder(ThemeOrder themeOrder, Operator operator) {
		ResultInfo<ThemeOrder> resultInfo = new ResultInfo<ThemeOrder>();

		if (themeOrder == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " themeOrder = " + themeOrder);
		} else {
			try {
				// 如果传入参数的主键为null，则调用生成主键的方法获取新的主键
				if (themeOrder.getThemeOrderNo() == null) {
					themeOrder.setThemeOrderNo(this.generatePK());
				}
				// 如果传入的操作人不为null，则设置新增记录的操作人类型和操作人id
				if (operator != null) {
					themeOrder.setOperatorType(operator.getOperatorType());
					themeOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置创建时间和更新时间为当前时间
				Date now = new Date();
				themeOrder.setCreateTime(now);
				themeOrder.setUpdateTime(now);

				// 填充默认值
				this.fillDefaultValues(themeOrder);

				// 调用Dao执行插入操作
				themeOrderDao.add(themeOrder);
				resultInfo.setCode(Constant.SECCUESS);
				resultInfo.setData(themeOrder);
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
	 * 根据主键，更新一条ThemeOrder记录
	 * 
	 * @param themeOrder
	 *            更新的ThemeOrder数据，且其主键不能为空
	 * @param operator
	 *            操作人对象
	 * @return
	 */
	@Transactional
	public ResultInfo<ThemeOrder> updateThemeOrder(ThemeOrder themeOrder, Operator operator) {
		ResultInfo<ThemeOrder> resultInfo = new ResultInfo<ThemeOrder>();

		if (themeOrder == null || themeOrder.getThemeOrderNo() == null) { // 传入参数无效时直接返回失败结果
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg(Constant.ERR_MSG_INVALID_ARG);
			log.info(Constant.ERR_MSG_INVALID_ARG + " themeOrder = " + themeOrder);
		} else {
			try {
				// 如果传入的操作人不为null，则设置更新记录的操作人类型和操作人id
				if (operator != null) {
					themeOrder.setOperatorType(operator.getOperatorType());
					themeOrder.setOperatorId(operator.getOperatorId());
				}

				// 设置更新时间为当前时间
				themeOrder.setUpdateTime(new Date());

				// 调用Dao执行更新操作，并判断更新语句执行结果
				int count = themeOrderDao.update(themeOrder);
				if (count > 0) {
					resultInfo.setCode(Constant.SECCUESS);
				} else {
					resultInfo.setCode(Constant.FAIL);
				}
				resultInfo.setData(themeOrder);
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
	 * 
	 * @return
	 */
	public String generatePK() {
		return "Z" + primarykeyService.getValueByBizType(PrimarykeyConstant.themeOrder);
	}

	/**
	 * 为ThemeOrder对象设置默认值
	 * 
	 * @param obj
	 */
	public void fillDefaultValues(ThemeOrder obj) {
		if (obj != null) {
			/*if (obj.getOrderStatus() == null) {
				obj.setOrderStatus(0);
			}*/
			if (obj.getPayStatus() == null) {
				obj.setPayStatus(0);
			}
			if (obj.getPayStatus() == null) {
				obj.setPaymentMethod(0);
			}
			if (obj.getIsSign() == null) {
				obj.setIsSign(0);
			}
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<ThemeOrder> getAppThemeOrderList(Query q) {
		List<ThemeOrder> list = null;
		try {
			// 已删除的不查出
			ThemeOrder themeOrder = (ThemeOrder) q.getQ();
			if (themeOrder != null) {
				themeOrder.setIsDeleted(Constant.DEL_STATE_NO);
			}

			// 直接调用Dao方法进行查询
			list = themeOrderDao.queryAppThemeOrder(q);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrder>(0) : list;
		return list;
	}

	@Override
	public long statCourseAppoinNumber(String arrayCourseNo) {
		// TODO Auto-generated method stub
		long statNum = 0;
		long statNums = themeOrderDao.statCourseAppoinNumber(arrayCourseNo);
		if(statNums == 0){
			statNums = statNum;
		}
		return statNums;
	}
	
	public Map wxPayRefundRequest(String transaction_id, String out_trade_no, String out_refund_no, String total_fee,
			String refund_fee, String op_user_id,Integer trade_type) throws Exception {
		String appid = WxpayConfig.appID;
		String mch_id = WxpayConfig.mchID;// 邮件里的MCHID

		if (trade_type == 3) {
			appid = WxpayConfig.h5_appID;
			mch_id = WxpayConfig.h5_mchID;
		}

		String device_info = "";
		String currTime = TenpayUtil.getCurrTime();
		// 8位日期
		String strTime = currTime.substring(8, currTime.length());
		// 四位随机数
		String strRandom = TenpayUtil.buildRandom(4) + "";
		// 10位序列号,可以自行调整。
		String strReq = strTime + strRandom;
		String nonce_str = strReq;
		total_fee = total_fee.substring(0, total_fee.indexOf("."));
		refund_fee = refund_fee.substring(0, refund_fee.indexOf("."));
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("op_user_id", op_user_id);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("out_refund_no", out_refund_no);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		String sign = PayCommonUtil.createSign("UTF-8", packageParams);
		packageParams.put("sign", sign);
		String reuqestXml = getRequestXml(packageParams);
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		String pahtCert = getPath() + "cert/apiclient_cert.p12";
		if (trade_type == 3) {
			pahtCert = getPath() + "cert/h5/apiclient_cert.p12";
		}
		File file = new File(pahtCert);
		FileInputStream instream = new FileInputStream(file);//// 放退款证书的路径
		try {
			keyStore.load(instream, mch_id.toCharArray());
		} finally {
			instream.close();
		}

		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {

			HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");// 退款接口

			System.out.println("----------executing request--------" + httpPost.getRequestLine());
			StringEntity reqEntity = new StringEntity(reuqestXml);
			// 设置类型
			reqEntity.setContentType("application/x-www-form-urlencoded");
			httpPost.setEntity(reqEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			Map map;
			try {
				HttpEntity entity = response.getEntity();

				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				if (entity != null) {
					System.out.println("Response content length: " + entity.getContentLength());
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(entity.getContent(), "UTF-8"));
					String result = "";
					String text;
					while ((text = bufferedReader.readLine()) != null) {
						System.out.println(text);
						result = result + text;
					}
					map = XMLUtil.doXMLParse(result);
					return map;
				}
				EntityUtils.consume(entity);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			httpclient.close();
		}
		return null;

	}
	
	public static String getPath() throws UnsupportedEncodingException {
		String configPath = GetWxOrderno.class.getResource("/").getPath();
		return java.net.URLDecoder.decode(configPath, "utf-8");
	}
	
	public static String getRequestXml(SortedMap<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
	/**
	 * 微信退款请求
	 */
	@Override
	public ResultInfo<ThemeOrder> cancelOrderRquestWx(ThemeOrder themeOrder, String remark, double refundAmount) {
		ResultInfo<ThemeOrder> resultInfo = new ResultInfo<ThemeOrder>();
		String transaction_id = themeOrder.getPaymentFlowNo();
		String out_trade_no = themeOrder.getPartTradeFlowNo();
		Double totalFee = themeOrder.getPayableAmount() *100 ;
		Double refundFee = refundAmount * 100;
		String op_user_id = themeOrder.getMemberNo();
		String nowTime = ECDateUtils.formatStringTimeWX(new Date());
		String out_refund_no = nowTime + themeOrder.getThemeOrderNo();
		String total_fee = totalFee.toString();
		String refund_fee = refundFee.toString();
		String trade_state;
		Integer trade_type = themeOrder.getPaymentMethod();
		try {
			Map map = wxPayRefundRequest(transaction_id, out_trade_no, out_refund_no, total_fee, refund_fee,
					op_user_id,trade_type);
			if (map != null) {
				String return_code = (String) map.get("return_code");
				String result_code = (String) map.get("result_code");
				log.info("WXRefundRes,return_code=" + return_code + ",result_code=" + result_code);
				if (return_code.contains("SUCCESS")) {
					trade_state = (String) map.get("trade_state");
					String refundNo = (String) map.get("out_refund_no");//商户退款流水号
					String refund_id = (String) map.get("refund_id");//微信退款流水号
					if (result_code.contains("SUCCESS")) {
						/*
						 * SUCCESS—支付成功 REFUND—转入退款 NOTPAY—未支付 CLOSED—已关闭
						 * REVOKED—已撤销（刷卡支付） USERPAYING--用户支付中
						 * PAYERROR--支付失败(其他原因，如银行返回失败)
						 */
						themeOrder.setPartTradeFlowNo(out_trade_no);
						themeOrder.setPaymentFlowNo(transaction_id);
						themeOrder.setRefundFlowNo(refundNo);
						themeOrder.setOutRefundFlowNo(refund_id);
						//修改实际支付金额
						//themeOrder.setPayableAmount(ECCalculateUtils.sub(themeOrder.getPayableAmount(), refundAmount));
						resultInfo.setData(themeOrder);
						resultInfo.setCode(Constant.SECCUESS);
						log.info("returned SUCCESS");
					} else {
						String err_code_des = (String) map.get("err_code_des");
						if (themeOrder != null) {
							themeOrder.setRefundFailInfo(err_code_des);
						}
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setData(themeOrder);
						resultInfo.setMsg(err_code_des);
					}
				} else {
					if (themeOrder != null) {
						String err_code_des = (String) map.get("err_code_des");
						themeOrder.setRefundFailInfo(err_code_des);
					}
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setData(themeOrder);
					resultInfo.setMsg("微信退款失败！");
				}
			} else {
				if (themeOrder != null) {
					String err_code_des = (String) map.get("err_code_des");
					themeOrder.setRefundFailInfo(err_code_des);
				}
				resultInfo.setCode(Constant.FAIL);
				resultInfo.setData(themeOrder);
				resultInfo.setMsg("微信退款失败！");
			}
		} catch (Exception e) {
			if (themeOrder != null) {
				themeOrder.setRefundFailInfo("微信退款失败！");
			}
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setData(themeOrder);
			resultInfo.setMsg("微信退款失败！");
			log.info("returned FAIL");
		}
		return resultInfo;
	}
	/**
	 * 支付宝退款请求
	 */
	@Override
	public ResultInfo<ThemeOrder> cancelOrderRquestAlipay(ThemeOrder themeOrder, String remark, double refundAmount) {
		ResultInfo<ThemeOrder> resultInfo = new ResultInfo<ThemeOrder>();
		if(themeOrder != null){
			Double orderRefundAmount =  refundAmount;//实际退款金额
			String appId = AlipayConfig.appId;//账户ID
			String privateKey = AlipayConfig.rsa_private;//私钥
			String publicKey = AlipayConfig.rsa_public;//公钥
			String outBizNo = themeOrder.getPartTradeFlowNo();//订单支付时传入的商户订单号
			String paymentFlowNo = themeOrder.getPaymentFlowNo();//支付宝交易号
			
			String outRequestNo = ECRandomUtils.getRandomHexStr(16);//自定义内部退款流水号
			
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",appId,privateKey,"json","UTF-8",publicKey,"RSA");
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent("{" + "\"out_trade_no\":\"" + outBizNo + "\"," + "\"trade_no\":\"" + paymentFlowNo
			+ "\"," + "\"refund_amount\":\"" + orderRefundAmount + "\"," + "\"refund_reason\":\"" + remark + "\"," + "\"out_request_no\":\"" + outRequestNo + "\"}");
			
			try {
				AlipayTradeRefundResponse  alipayResponse = alipayClient.execute(request);
				log.info("response:"+alipayResponse);//获取退款结果
				if (alipayResponse.isSuccess()) { // 调用退款接口成功
					if ("10000".equals(alipayResponse.getCode())) {
						themeOrder.setPartTradeFlowNo(outBizNo);
						themeOrder.setPaymentFlowNo(paymentFlowNo);
						themeOrder.setRefundFlowNo("ALIPAY_REFUND_"+themeOrder.getThemeOrderNo());
						themeOrder.setOutRefundFlowNo(outRequestNo);
						themeOrder.setPayableAmount(ECCalculateUtils.sub(themeOrder.getPayableAmount(), orderRefundAmount));//修改实际支付金额
						resultInfo.setData(themeOrder);
						resultInfo.setCode(Constant.SECCUESS);
						resultInfo.setMsg("退款成功");
					}else{
						//支付宝退款查询失败原因
						try {
							AlipayTradeFastpayRefundQueryRequest requestInfo = new AlipayTradeFastpayRefundQueryRequest();
							requestInfo.setBizContent("{" + "\"out_trade_no\":\"" + outBizNo + "\"" + "," + "\"out_request_no\":\"" + outRequestNo + "\"}");
							AlipayTradeFastpayRefundQueryResponse getInfoResponse = alipayClient.execute(requestInfo);
							log.info("getInfoResponse:"+getInfoResponse);
							if (getInfoResponse.isSuccess()) {
								if (!"10000".equals(getInfoResponse.getCode())) {
									themeOrder.setRefundFailInfo(getInfoResponse.getRefundReason());
									log.error("query transAccount fail ,out_biz_no is" + outBizNo + "the info is"+ getInfoResponse.getRefundReason());
								}
							} else {
								themeOrder.setRefundFailInfo(getInfoResponse.getRefundReason());
								log.error("query transAccount fail ,out_biz_no is" + outBizNo + "the info is"+ getInfoResponse.getRefundReason());
							}
						} catch (Exception e) {
							log.info(e.getMessage());
						}
						resultInfo.setCode(Constant.FAIL);
						resultInfo.setData(themeOrder);
						resultInfo.setMsg("退款失败");
					}
				}else{
					themeOrder.setRefundFailInfo(alipayResponse.getMsg());
					resultInfo.setCode(Constant.FAIL);
					resultInfo.setData(themeOrder);
					resultInfo.setMsg("退款请求失败，失败原因为:" + alipayResponse.getMsg());
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}else{
			resultInfo.setCode(Constant.FAIL);
			resultInfo.setMsg("退款失败,该主题订单不存在！");
		}
		return resultInfo;
	}

	/**
	 * APP预约订单和取消
	 */
	@Override
	public void callBackOrderData(ThemeOrder themeOrder, double refundAmount, int refundMethod, int selectNumber) {
		String refundReason = sysParamService.getByParamKey("THEME_ORDER_MEMO").getParamValue();
		if(StringUtils.isNotBlank(refundReason)){
			if(refundMethod == OrderConstant.alipay_method){
				//进入支付宝退款请求
				ResultInfo<ThemeOrder> resultInfo = cancelOrderRquestAlipay(themeOrder, refundReason, refundAmount);
				if(Constant.SECCUESS.equals(resultInfo.getCode())){
					//退款成功后，修改订单相关数据
					ThemeOrder order = resultInfo.getData();
					ThemeOrder orderUpdate = new ThemeOrder();
					orderUpdate.setThemeOrderNo(themeOrder.getThemeOrderNo());
					orderUpdate.setUpdateTime(new Date());
					orderUpdate.setPeopleNumber(order.getPeopleNumber());
					//修改抵扣的课时包
					orderUpdate.setCourseBagNum(themeOrder.getCourseBagNum());
					
					if(order != null){
						if(themeOrder.getPeopleNumber() == selectNumber){
							orderUpdate.setCancelNumber(selectNumber);
							orderUpdate.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));
						}else if(themeOrder.getPeopleNumber()>selectNumber){
							if(order.getCancelNumber() ==  null){
								order.setCancelNumber(0);
								orderUpdate.setCancelNumber(order.getCancelNumber()+selectNumber);
							}else{
								int cal = order.getPeopleNumber()-order.getCancelNumber();//剩余可取消人数
								if(cal == selectNumber){
									orderUpdate.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));
									orderUpdate.setCancelNumber(order.getCancelNumber()+selectNumber);
								}else if(cal>selectNumber){
									orderUpdate.setCancelNumber(order.getCancelNumber()+selectNumber);
								}
							}
						}
						//计算取消退款的金额
						if(order.getRefundAmount() == null){
							order.setRefundAmount(0d);
							orderUpdate.setRefundAmount(ECCalculateUtils.add(refundAmount, order.getRefundAmount()));
						}else{
							orderUpdate.setRefundAmount(ECCalculateUtils.add(refundAmount, order.getRefundAmount()));
						}
						orderUpdate.setPayableAmount(order.getPayableAmount());
						orderUpdate.setPaymentFlowNo(order.getPaymentFlowNo());
						orderUpdate.setPartTradeFlowNo(order.getPartTradeFlowNo());
						orderUpdate.setOutRefundFlowNo(order.getOutRefundFlowNo());
						orderUpdate.setRefundFlowNo(order.getRefundFlowNo());
					}
					int row = themeOrderDao.update(orderUpdate);
					log.info("更新主题订单表");
					if(row > 0){
						//执行排队订单退款短信通知
						/*boolean refundFlag = false;
						String refundContent = "订单金额"+refundAmount;
						try {
							refundFlag = sendMsgCommonInterfaceService.sendMsgPost(themeOrder.getMobilePhone(), refundContent, MsgConstant.MEMBERDEPOSIT);
							log.info("发送内容为：==========================》"+refundFlag);
						} catch (Exception e) {
							e.printStackTrace();
						}*/
						
						this.updateOrderGlData(themeOrder, selectNumber, 1);
					}
				}
			}
			//进入微信退款
			if(refundMethod == OrderConstant.weChat_method){
				ResultInfo<ThemeOrder> resultInfo = cancelOrderRquestWx(themeOrder, refundReason, refundAmount);
				if(Constant.SECCUESS.equals(resultInfo.getCode())){
					//退款成功后，修改订单相关数据
					ThemeOrder order = resultInfo.getData();
					ThemeOrder orderUpdate = new ThemeOrder();
					orderUpdate.setThemeOrderNo(themeOrder.getThemeOrderNo());
					orderUpdate.setUpdateTime(new Date());
					//修改抵扣的课时包
					orderUpdate.setCourseBagNum(themeOrder.getCourseBagNum());
					if(order != null){
						if(themeOrder.getPeopleNumber() == selectNumber){
							orderUpdate.setCancelNumber(selectNumber);
							orderUpdate.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));
						}else if(themeOrder.getPeopleNumber()>selectNumber){
							if(order.getCancelNumber() ==  null){
								order.setCancelNumber(0);
								orderUpdate.setCancelNumber(order.getCancelNumber()+selectNumber);
							}else{
								int cal = order.getPeopleNumber()-order.getCancelNumber();//剩余可取消人数
								if(cal == selectNumber){
									orderUpdate.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));
									orderUpdate.setCancelNumber(order.getCancelNumber()+selectNumber);
								}else if(cal>selectNumber){
									orderUpdate.setCancelNumber(order.getCancelNumber()+selectNumber);
								}
							}
						}
						//计算取消退款的金额
						if(order.getRefundAmount() == null){
							order.setRefundAmount(0d);
							orderUpdate.setRefundAmount(ECCalculateUtils.add(refundAmount, order.getRefundAmount()));
						}else{
							orderUpdate.setRefundAmount(ECCalculateUtils.add(refundAmount, order.getRefundAmount()));
						}
						orderUpdate.setPayableAmount(order.getPayableAmount());
						orderUpdate.setPaymentFlowNo(order.getPaymentFlowNo());
						orderUpdate.setPartTradeFlowNo(order.getPartTradeFlowNo());
						orderUpdate.setOutRefundFlowNo(order.getOutRefundFlowNo());
						orderUpdate.setRefundFlowNo(order.getRefundFlowNo());
					}
					int row = themeOrderDao.update(orderUpdate);
					log.info("更新主题订单表");
					if(row > 0){
						//执行排队订单失败退款短信通知
						/*boolean refundFlag = false;
						String refundContent = "订单金额"+refundAmount;
						try {
							refundFlag = sendMsgCommonInterfaceService.sendMsgPost(themeOrder.getMobilePhone(), refundContent, MsgConstant.MEMBERDEPOSIT);
							log.info("发送内容为：==========================》"+refundFlag);
						} catch (Exception e) {
							e.printStackTrace();
						}*/
						this.updateOrderGlData(themeOrder, selectNumber,1);
					}
				}
			}
		}
	}
	
	/**
	 * 订单取消后执行订单相关表数据的修改
	 */
	@Override
	public void updateOrderGlData(ThemeOrder themeOrder,int selectNumber,int type){
		//主题订单表更新完后根据入参去修改相关联的表数据
		//根据会员选择取消的人数更新主题订单表中正在排队的订单
		//取消几个、按排队时间倒序统计目前正在排队的订单
		try {
			if(type == 1){
				ThemeOrder tOrder = new ThemeOrder();
				tOrder.setArrangeNo(themeOrder.getArrangeNo());
				tOrder.setOrderStatus(Integer.parseInt(OrderConstant.pdzOrder_code));
				int pageNo = 1;
				int pageSize = selectNumber;
				PageFinder<ThemeOrder> pageLineOrder = queryLineThemeOrder(new Query(pageNo, pageSize, tOrder));
				if(pageLineOrder != null && pageLineOrder.getData() != null && pageLineOrder.getData().size()>0){
					for (ThemeOrder to : pageLineOrder.getData()) {
						//更新排队订单
						ThemeOrder lineOrder = new ThemeOrder();
						lineOrder.setThemeOrderNo(to.getThemeOrderNo());
						lineOrder.setOrderStatus(Integer.parseInt(OrderConstant.yuyueOrder_code));
						themeOrderDao.update(lineOrder);
						
						//执行短信发送
						/*String classDate = ECDateUtils.formatDate(to.getPaymentTime(), "yyyy年MM月dd日 HH:mm:ss");
						String courseName = "";
						Course course = courseService.getCourse(to.getCourseNo());
						if(course != null){
							courseName = course.getChineseName();
						}
						String msgType = MsgConstant.LINEUPTYPE;
						String content = classDate+"预约成功的"+courseName+"课程";
						this.sendMsg(content, to.getMobilePhone(), courseName, msgType);*/
					}
					
					//更新排课表
					ArrayCourse arrayCourse = arrayCourseDao.get(themeOrder.getArrangeNo());
					if(arrayCourse != null){
						ArrayCourse course = new ArrayCourse();
						course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
						//预约 8人 排队3人  
						int serAppionNum = arrayCourse.getReservation() - selectNumber;
						if(selectNumber == arrayCourse.getLineUp()){
							course.setReservation(serAppionNum+arrayCourse.getLineUp());
							course.setLineUp(0);
						}else if(selectNumber < arrayCourse.getLineUp()){
							int line = arrayCourse.getLineUp() - selectNumber;
							course.setLineUp(line);
							int appionNum = selectNumber;
							course.setReservation(serAppionNum + appionNum);
						}else if(selectNumber > arrayCourse.getLineUp()){
							int line =  selectNumber - arrayCourse.getLineUp();
							course.setLineUp(0);
							int appionNum = selectNumber - line;
							course.setReservation(serAppionNum + appionNum);
						}
						arrayCourseDao.update(course);
					}
				}else{
					//若此订单没有排队订单生成，则直接修改排课表
					ArrayCourse arrayCourse = arrayCourseDao.get(themeOrder.getArrangeNo());
					if(arrayCourse.getReservation()>selectNumber){
						ArrayCourse course = new ArrayCourse();
						course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
						course.setReservation(arrayCourse.getReservation() - selectNumber);
						arrayCourseDao.update(course);
					}else if(arrayCourse.getReservation()==selectNumber){
						ArrayCourse course = new ArrayCourse();
						course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
						course.setReservation(0);
						arrayCourseDao.update(course);
					}
				}
			}else{
				//排队取消退款 修改排课表
				ArrayCourse arrayCourse = arrayCourseDao.get(themeOrder.getArrangeNo());
				if(arrayCourse.getLineUp() > selectNumber){
					ArrayCourse course = new ArrayCourse();
					course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
					course.setLineUp(arrayCourse.getLineUp() - selectNumber);
					arrayCourseDao.update(course);
				}else if(arrayCourse.getLineUp() == selectNumber){
					ArrayCourse course = new ArrayCourse();
					course.setArrayCourseNo(arrayCourse.getArrayCourseNo());
					course.setLineUp(0);
					arrayCourseDao.update(course);
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	
	/**
	 * 主题订单：发送短信
	 * @param content 内容模板
	 * @param memberPhone 用户手机号
	 * @param courseName 课程名称
	 * @param msgType 发送模板类型
	 * @throws Exception 
	 */
	private boolean sendMsg(String content,String memberPhone,String courseName,String msgType) throws Exception{
		boolean flag = false;
		flag = sendMsgCommonInterfaceService.sendMsgPost(memberPhone, content, msgType);
		log.info("发送内容为：==========================》"+flag);
		return flag;
	}
	
	
	/**
	 * 定时任务：排队中的订单排队预约失败执行退款更新订单表
	 * @param ThemeOrder themeOrder
	 */
	@Override
	public void lineOrderRefundUpdate(ThemeOrder themeOrder){
		log.info("进入排队订单退款操作...");
		try {
			String orderNo = themeOrder.getThemeOrderNo();//订单编号
			String couponNo = themeOrder.getCouponNo();//优惠券
			double balanceAmount = themeOrder.getBalanceDiscountAmount();//余额抵扣
			int courseBagNum = themeOrder.getCourseBagNum();
			String memberPhone = themeOrder.getMobilePhone();//会员手机号
			double refundAmount = 0d;//退款金额
			if(StringUtils.isNotBlank(couponNo) && balanceAmount == 0d && courseBagNum == 0){
				//使用优惠券抵扣，余额和课程包无使用
				if(themeOrder.getPayableAmount() == 0d){//优惠券抵扣实付款为0.0时
					ThemeOrder order  = new ThemeOrder();
					order.setThemeOrderNo(orderNo);
					order.setCancelNumber(themeOrder.getPeopleNumber());
					order.setRefundAmount(refundAmount);
					order.setOrderStatus(3);//已取消
					order.setIsSendMsg(1);//已发送短信通知
					int row = themeOrderDao.update(order);
					if(row > 0){
						//执行短信发送
						//您好，很抱歉，您于{2017年12月20号预约成功的健美操课程}订单排队未成功，已将支付课程订单费用全额退款。
						String classDate = ECDateUtils.formatDate(themeOrder.getPaymentTime(), "yyyy年MM月dd日 HH:mm:ss");
						String courseName = "";
						Course course = courseService.getCourse(themeOrder.getCourseNo());
						if(course != null){
							courseName = course.getChineseName();
						}
						String msgType = MsgConstant.LINEFAILTYPE;
						String content = classDate+"预约成功的"+courseName+"课程";
						this.sendMsg(content, memberPhone, courseName, msgType);
						//修改排课表
						updateOrderGlData(themeOrder, themeOrder.getPeopleNumber(), 2);
					}
				}else{
					refundAmount = themeOrder.getPayableAmount();
					//调用第三方接口退款
					if(themeOrder.getPaymentMethod() == OrderConstant.alipay_method){
						//支付宝
						callBackOrderLineRefundData(themeOrder, refundAmount, OrderConstant.alipay_method, themeOrder.getPeopleNumber(), 1);
					}else{
						//微信
						callBackOrderLineRefundData(themeOrder, refundAmount, OrderConstant.weChat_method, themeOrder.getPeopleNumber(), 1);
					}
				}
			}else if(StringUtils.isBlank(couponNo) && balanceAmount == 0d && courseBagNum > 0){
				//使用课程包抵扣
				if(themeOrder.getPayableAmount() == 0d){//实付款为0.0时
					ThemeOrder order  = new ThemeOrder();
					order.setThemeOrderNo(orderNo);
					order.setCancelNumber(themeOrder.getPeopleNumber());
					order.setRefundAmount(refundAmount);
					order.setOrderStatus(3);//已取消
					order.setIsSendMsg(1);//已发送短信通知
					int row = themeOrderDao.update(order);
					if(row > 0){
						//执行短信发送
						//您好，很抱歉，您于{2017年12月20号预约成功的健美操课程}订单排队未成功，已将支付课程订单费用全额退款。
						String classDate = ECDateUtils.formatDate(themeOrder.getPaymentTime(), "yyyy年MM月dd日 HH:mm:ss");
						String courseName = "";
						Course course = courseService.getCourse(themeOrder.getCourseNo());
						if(course != null){
							courseName = course.getChineseName();
						}
						String msgType = MsgConstant.LINEFAILTYPE;
						String content = classDate+"预约成功的"+courseName+"课程";
						this.sendMsg(content, memberPhone, courseName, msgType);
						//修改排课表
						updateOrderGlData(themeOrder, themeOrder.getPeopleNumber(), 2);
						//修改会员余额表课程包数量
						MemberBalance memberBalance = memberBalanceDao.get(themeOrder.getMemberNo());
						if(memberBalance != null){
							MemberBalance mb = new MemberBalance();
							mb.setMemberNo(memberBalance.getMemberNo());
							mb.setCourseNumber(memberBalance.getCourseNumber()+themeOrder.getCourseBagNum());
							memberBalanceDao.update(mb);
						}
					}
				}else{
					refundAmount = themeOrder.getPayableAmount();
					//调用第三方接口退款
					if(themeOrder.getPaymentMethod() == OrderConstant.alipay_method){
						//支付宝
						callBackOrderLineRefundData(themeOrder, refundAmount, OrderConstant.alipay_method, themeOrder.getPeopleNumber(), 1);
					}else{
						//微信
						callBackOrderLineRefundData(themeOrder, refundAmount, OrderConstant.weChat_method, themeOrder.getPeopleNumber(), 1);
					}
				}
			}else if(StringUtils.isBlank(couponNo) && balanceAmount > 0d && courseBagNum == 0){
				//使用余额支付
				//实付款已用余额抵扣完，直接更新主题订单表和会员余额表
				ThemeOrder order  = new ThemeOrder();
				order.setThemeOrderNo(orderNo);
				order.setCancelNumber(themeOrder.getPeopleNumber());
				order.setRefundAmount(themeOrder.getBalanceDiscountAmount());
				order.setOrderStatus(3);//已取消
				order.setIsSendMsg(1);//已发送短信通知
				refundAmount = themeOrder.getBalanceDiscountAmount();
				int row = themeOrderDao.update(order);
				if(row > 0){
					//执行短信发送
					String classDate = ECDateUtils.formatDate(themeOrder.getPaymentTime(), "yyyy年MM月dd日 HH:mm:ss");
					String courseName = "";
					Course course = courseService.getCourse(themeOrder.getCourseNo());
					if(course != null){
						courseName = course.getChineseName();
					}
					String msgType = MsgConstant.LINEFAILTYPE;
					String content = classDate+"预约成功的"+courseName+"课程";

					this.sendMsg(content, memberPhone, courseName, msgType);
					//修改排课表
					updateOrderGlData(themeOrder, themeOrder.getPeopleNumber(), 2);
					//修改会员余额表会员余额
					MemberBalance memberBalance = memberBalanceDao.get(themeOrder.getMemberNo());
					if(memberBalance != null){
						MemberBalance mb = new MemberBalance();
						mb.setMemberNo(memberBalance.getMemberNo());
						mb.setMemberBalance(ECCalculateUtils.add(refundAmount, memberBalance.getMemberBalance()));
						memberBalanceDao.update(mb);
					}
				}
			}else if(StringUtils.isNotBlank(couponNo) && balanceAmount > 0d && courseBagNum == 0){
				//选择优惠券和余额抵扣，不使用课程包 直接抵扣完
				ThemeOrder order  = new ThemeOrder();
				order.setThemeOrderNo(orderNo);
				order.setCancelNumber(themeOrder.getPeopleNumber());
				order.setRefundAmount(themeOrder.getBalanceDiscountAmount());
				refundAmount = themeOrder.getBalanceDiscountAmount();
				order.setOrderStatus(3);//已取消
				order.setIsSendMsg(1);//已发送短信通知
				int row = themeOrderDao.update(order);
				if(row > 0){
					//修改排课表
					updateOrderGlData(themeOrder, themeOrder.getPeopleNumber(), 2);
					//修改会员余额表会员余额
					MemberBalance memberBalance = memberBalanceDao.get(themeOrder.getMemberNo());
					if(memberBalance != null){
						MemberBalance mb = new MemberBalance();
						mb.setMemberNo(memberBalance.getMemberNo());
						mb.setMemberBalance(ECCalculateUtils.add(refundAmount, memberBalance.getMemberBalance()));
						memberBalanceDao.update(mb);
					}
					//执行短信发送
					String classDate = ECDateUtils.formatDate(themeOrder.getPaymentTime(), "yyyy年MM月dd日 HH:mm:ss");
					String courseName = "";
					Course course = courseService.getCourse(themeOrder.getCourseNo());
					if(course != null){
						courseName = course.getChineseName();
					}
					String msgType = MsgConstant.LINEFAILTYPE;
					String content = classDate+"预约成功的"+courseName+"课程";

					this.sendMsg(content, memberPhone, courseName, msgType);
				}
			}else if(StringUtils.isBlank(couponNo) && balanceAmount > 0d && courseBagNum >0){
				//使用余额和课程包抵扣完
				ThemeOrder order  = new ThemeOrder();
				order.setThemeOrderNo(orderNo);
				order.setCancelNumber(themeOrder.getPeopleNumber());
				order.setRefundAmount(themeOrder.getBalanceDiscountAmount());
				refundAmount = themeOrder.getBalanceDiscountAmount();
				order.setOrderStatus(3);//已取消
				order.setIsSendMsg(1);//已发送短信通知
				int row = themeOrderDao.update(order);
				if(row > 0){
					//修改排课表
					updateOrderGlData(themeOrder, themeOrder.getPeopleNumber(), 2);
					//修改会员余额表会员余额和课程包
					MemberBalance memberBalance = memberBalanceDao.get(themeOrder.getMemberNo());
					if(memberBalance != null){
						MemberBalance mb = new MemberBalance();
						mb.setMemberNo(memberBalance.getMemberNo());
						mb.setMemberBalance(ECCalculateUtils.add(refundAmount, memberBalance.getMemberBalance()));
						mb.setCourseNumber(memberBalance.getCourseNumber()+themeOrder.getCourseBagNum());
						memberBalanceDao.update(mb);
					}
					//执行短信发送
					String classDate = ECDateUtils.formatDate(themeOrder.getPaymentTime(), "yyyy年MM月dd日 HH:mm:ss");
					String courseName = "";
					Course course = courseService.getCourse(themeOrder.getCourseNo());
					if(course != null){
						courseName = course.getChineseName();
					}
					String msgType = MsgConstant.LINEFAILTYPE;
					String content = classDate+"预约成功的"+courseName+"课程";

					this.sendMsg(content, memberPhone, courseName, msgType);
				}
			}else if(StringUtils.isBlank(couponNo) && balanceAmount == 0d && courseBagNum== 0){
				//直接调用第三方接口用现金支付
				refundAmount = themeOrder.getPayableAmount();
				if(themeOrder.getPaymentMethod() == OrderConstant.alipay_method){
					callBackOrderLineRefundData(themeOrder, refundAmount, OrderConstant.alipay_method, themeOrder.getPeopleNumber(), 1);
				}else{
					callBackOrderLineRefundData(themeOrder, refundAmount, OrderConstant.weChat_method, themeOrder.getPeopleNumber(), 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}
	
	/**
	 * 排队订单预约失败退款操作
	 */
	@Override
	public void callBackOrderLineRefundData(ThemeOrder themeOrder, double refundAmount, int refundMethod, int selectNumber,int refundType) {
		String refundReason = sysParamService.getByParamKey("THEME_ORDER_MEMO").getParamValue();
		if(StringUtils.isNotBlank(refundReason)){
			if(refundMethod == OrderConstant.alipay_method){
				//进入支付宝退款请求
				ResultInfo<ThemeOrder> resultInfo = cancelOrderRquestAlipay(themeOrder, refundReason, refundAmount);
				if(Constant.SECCUESS.equals(resultInfo.getCode())){
					//退款成功后，修改订单相关数据
					ThemeOrder order = resultInfo.getData();
					ThemeOrder orderUpdate = new ThemeOrder();
					orderUpdate.setThemeOrderNo(themeOrder.getThemeOrderNo());
					orderUpdate.setUpdateTime(new Date());
					orderUpdate.setPeopleNumber(order.getPeopleNumber());
					//修改抵扣的课时包
					if(order != null){
						orderUpdate.setCancelNumber(selectNumber);
						orderUpdate.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));
						//计算取消退款的金额
						if(order.getRefundAmount() == null){
							order.setRefundAmount(0d);
							orderUpdate.setRefundAmount(ECCalculateUtils.add(refundAmount, order.getRefundAmount()));
						}else{
							orderUpdate.setRefundAmount(ECCalculateUtils.add(refundAmount, order.getRefundAmount()));
						}
						orderUpdate.setPayableAmount(order.getPayableAmount());
						orderUpdate.setPaymentFlowNo(order.getPaymentFlowNo());
						orderUpdate.setPartTradeFlowNo(order.getPartTradeFlowNo());
						orderUpdate.setOutRefundFlowNo(order.getOutRefundFlowNo());
						orderUpdate.setRefundFlowNo(order.getRefundFlowNo());
						orderUpdate.setIsSendMsg(1);//已发送
					}
					int row = themeOrderDao.update(orderUpdate);
					log.info("更新主题订单表");
					if(row > 0){
						this.updateOrderGlData(themeOrder, selectNumber, 2);
						if(themeOrder.getCourseBagNum() > 0){
							//修改会员余额表课程包
							MemberBalance memberBalance = memberBalanceDao.get(themeOrder.getMemberNo());
							if(memberBalance != null){
								MemberBalance mb = new MemberBalance();
								mb.setMemberNo(memberBalance.getMemberNo());
								mb.setCourseNumber(memberBalance.getCourseNumber()+themeOrder.getCourseBagNum());
								memberBalanceDao.update(mb);
							}
						}
						//定时任务执行排队订单取消退款进行短信通知，手动操作不进行短信通知
						if(refundType == 1){
							//执行排队失败短信发送通知
							//您好，很抱歉，您于{2017年12月20号预约成功的健美操课程}订单排队未成功，已将支付课程订单费用全额退款。
							String classDate = ECDateUtils.formatDate(themeOrder.getPaymentTime(), "yyyy年MM月dd日 HH:mm:ss");
							String courseName = "";
							Course course = courseService.getCourse(themeOrder.getCourseNo());
							if(course != null){
								courseName = course.getChineseName();
							}
							String msgType = MsgConstant.LINEFAILTYPE;
							String content = classDate+"预约成功的"+courseName+"课程";

							try {
								this.sendMsg(content, themeOrder.getMobilePhone(), courseName, msgType);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							//执行排队订单失败退款短信通知
							boolean refundFlag = false;
							String refundContent = "订单金额"+refundAmount;
							try {
								refundFlag = sendMsgCommonInterfaceService.sendMsgPost(themeOrder.getMobilePhone(), refundContent, MsgConstant.MEMBERDEPOSIT);
								log.info("发送内容为：==========================》"+refundFlag);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			//进入微信退款
			if(refundMethod == OrderConstant.weChat_method){
				ResultInfo<ThemeOrder> resultInfo = cancelOrderRquestWx(themeOrder, refundReason, refundAmount);
				if(Constant.SECCUESS.equals(resultInfo.getCode())){
					//退款成功后，修改订单相关数据
					ThemeOrder order = resultInfo.getData();
					ThemeOrder orderUpdate = new ThemeOrder();
					orderUpdate.setThemeOrderNo(themeOrder.getThemeOrderNo());
					orderUpdate.setUpdateTime(new Date());
					if(order != null){
						orderUpdate.setCancelNumber(selectNumber);
						orderUpdate.setOrderStatus(Integer.parseInt(OrderConstant.yqxOrder_code));
						//计算取消退款的金额
						if(order.getRefundAmount() == null){
							order.setRefundAmount(0d);
							orderUpdate.setRefundAmount(ECCalculateUtils.add(refundAmount, order.getRefundAmount()));
						}else{
							orderUpdate.setRefundAmount(ECCalculateUtils.add(refundAmount, order.getRefundAmount()));
						}
						orderUpdate.setPayableAmount(order.getPayableAmount());
						orderUpdate.setPaymentFlowNo(order.getPaymentFlowNo());
						orderUpdate.setPartTradeFlowNo(order.getPartTradeFlowNo());
						orderUpdate.setOutRefundFlowNo(order.getOutRefundFlowNo());
						orderUpdate.setRefundFlowNo(order.getRefundFlowNo());
						orderUpdate.setIsSendMsg(1);//已发送短信
					}
					int row = themeOrderDao.update(orderUpdate);
					log.info("更新主题订单表");
					if(row > 0){
						this.updateOrderGlData(themeOrder, selectNumber, 2);
						if(themeOrder.getCourseBagNum() > 0){
							//修改会员余额表课程包
							MemberBalance memberBalance = memberBalanceDao.get(themeOrder.getMemberNo());
							if(memberBalance != null){
								MemberBalance mb = new MemberBalance();
								mb.setMemberNo(memberBalance.getMemberNo());
								mb.setCourseNumber(memberBalance.getCourseNumber()+themeOrder.getPeopleNumber());
								memberBalanceDao.update(mb);
							}
						}
						//定时任务执行排队订单取消退款进行短信通知，手动操作不进行短信通知
						if(refundType == 1){
							//执行短信发送通知
							String classDate = ECDateUtils.formatDate(themeOrder.getPaymentTime(), "yyyy年MM月dd日 HH:mm:ss");
							String courseName = "";
							Course course = courseService.getCourse(themeOrder.getCourseNo());
							if(course != null){
								courseName = course.getChineseName();
							}
							String msgType = MsgConstant.LINEFAILTYPE;
							String content = classDate+"预约成功的"+courseName+"课程";
							try {
								this.sendMsg(content, themeOrder.getMobilePhone(), courseName, msgType);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							//执行排队订单失败退款短信通知
							boolean refundFlag = false;
							String refundContent = "订单金额"+refundAmount;
							try {
								refundFlag = sendMsgCommonInterfaceService.sendMsgPost(themeOrder.getMobilePhone(), refundContent, MsgConstant.MEMBERDEPOSIT);
								log.info("发送内容为：==========================》"+refundFlag);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 根据会员编号查询主题订单状态为已预约or进行中的数据
	 */
	@Override
	public List<ThemeOrder> queryOrderDataByMemberNo(String memberNo) {
		List<ThemeOrder> list = null;
		if (memberNo == null || memberNo == "") {
			log.info(Constant.ERR_MSG_INVALID_ARG + " memberNo is null or empty");
		} else {
			try {
				// 调用dao，根据主键数组查询
				list = themeOrderDao.queryOrderDataByMemberNo(memberNo);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrder>(0) : list;
		return list;
	}

	@Override
	public List<ThemeOrderStatisticVo> statDaysThemeOrderList(String beginTime, String endTime) {
		List<ThemeOrderStatisticVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = themeOrderDao.statDaysThemeOrderList(beginTime, endTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrderStatisticVo>(0) : list;
		return list;
	}

	@Override
	public List<ThemeOrderStatisticVo> statMonthsThemeOrderList(String beginTime, String endTime) {
		List<ThemeOrderStatisticVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = themeOrderDao.statMonthsThemeOrderList(beginTime, endTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrderStatisticVo>(0) : list;
		return list;
	}

	@Override
	public List<ThemeOrderStatisticVo> statYearsThemeOrderList(String beginTime, String endTime) {
		List<ThemeOrderStatisticVo> list = null;
		try {
			// 直接调用Dao方法进行查询
			list = themeOrderDao.statYearsThemeOrderList(beginTime, endTime);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// 如list为null时，则改为返回一个空列表
		list = list == null ? new ArrayList<ThemeOrderStatisticVo>(0) : list;
		return list;
	}
}