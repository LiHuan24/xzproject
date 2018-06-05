package cn.com.shopec.common.pay.handler.alipay;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*import com.alibaba.fastjson.JSONObject;*/
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayFundTransOrderQueryModel;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransOrderQueryResponse;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;

/**
 * 支付宝转账处理类（企业付款）
 * @author admin
 * https://docs.open.alipay.com/api_28/alipay.fund.trans.toaccount.transfer/
 */
public class AlipayTransferUtil{

	private static final Log log = LogFactory.getLog(AlipayTransferUtil.class);
	public static final String ALIPAY_USERID = "ALIPAY_USERID";//支付宝账号对应的支付宝唯一用户号。以2088开头的16位纯数字组成。 
	public static final String ALIPAY_LOGONID = "ALIPAY_LOGONID";//支付宝登录号，支持邮箱和手机号格式。
	
    /**
	 * 转账(返回AlipayFundTransToaccountTransferResponse对象)
	 * @param remark
	 * @param refundAmount
	 * @param accountNo
	 * @param outBizNo
	 * @param payeeType
	 * @return
	 * @throws AlipayApiException
	 */
	public static AlipayFundTransToaccountTransferResponse transferToResponse(String outBizNo,String payeeAccount, String payeeType, String amount,String payerName,String remark) throws AlipayApiException {
		
		AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
		model.setOutBizNo(outBizNo);//商户转账唯一订单号
		model.setPayeeType(payeeType);//收款方账户类型。可取值：1、ALIPAY_USERID：支付宝账号对应的支付宝唯一用户号;2、ALIPAY_LOGONID：支付宝登录号，支持邮箱和手机号格式。
		model.setPayeeAccount(payeeAccount);//收款方账户
		model.setAmount(amount);//转账金额，单位：元。 
		model.setRemark(remark);//转账备注（支持200个英文/100个汉字）。
		model.setPayerShowName(payerName);//支付人名称
		
		return transferToResponse(model);
	}
    
    /**
     * 转账(返回AlipayFundTransToaccountTransferResponse对象)
     * @param model
     * 
	 * 1、如果商户重复请求转账，支付宝会幂等返回成功结果，商户必须对重复转账的业务做好幂等处理；如果不判断，存在潜在的风险，商户自行承担因此而产生的所有损失。
	 * 2、如果调用alipay.fund.trans.toaccount.transfer掉单时，或返回结果code=20000时，或返回结果code=40004，sub_code= SYSTEM_ERROR时，请调用alipay.fund.trans.order.query发起查询，如果未查询到结果，请保持原请求不变再次请求alipay.fund.trans.toaccount.transfer接口。
	 * 3、商户处理转账结果时，对于错误码的处理，只能使用sub_code作为后续处理的判断依据，不可使用sub_msg作为后续处理的判断依据
     * @return
     * @throws AlipayApiException
     */
    public static AlipayFundTransToaccountTransferResponse transferToResponse(AlipayFundTransToaccountTransferModel model) throws AlipayApiException{
       
    	log.info(model.toString());
    	AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizModel(model);
        AlipayFundTransToaccountTransferResponse response = AlipayBaseUtil.getAlipayClient().execute(request);
        log.info(response.getBody());
        return response;
    }
    
    
    /**
	 * 转账查询接口（返回boolean）
	 * @param outBizNo
	 * @return
	 * @throws AlipayApiException
	 */
	public static boolean transQuery(String outBizNo) throws AlipayApiException {
		
		AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();
		model.setOutBizNo(outBizNo);
		AlipayFundTransOrderQueryResponse response = transferQueryToResponse(model);
        if(response.isSuccess()){
            return true;
        }
        return false;
	}	
    
    
    /**
     * 转账查询接口(返回AlipayFundTransOrderQueryResponse对象)
     * @param model
     * @return
     * @throws AlipayApiException
     */
    public static AlipayFundTransOrderQueryResponse transferQueryToResponse(String outBizNo) throws AlipayApiException{
		
    	AlipayFundTransOrderQueryModel model = new AlipayFundTransOrderQueryModel();
		model.setOutBizNo(outBizNo);//商户转账唯一订单号
		return transferQueryToResponse(model);
    }
    
    /**
     * 转账查询接口(返回AlipayFundTransOrderQueryResponse对象)
     * @param model
     * @return
     * @throws AlipayApiException
     */
    private static AlipayFundTransOrderQueryResponse transferQueryToResponse(AlipayFundTransOrderQueryModel model) throws AlipayApiException{
        
    	AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
        request.setBizModel(model);
        return AlipayBaseUtil.getAlipayClient().execute(request);
    }

}
