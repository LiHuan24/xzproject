package cn.com.shopec.common.apppush;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gexin.rp.sdk.base.IBatch;
import com.gexin.rp.sdk.base.IIGtPush;
import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.AbstractTemplate;

public class AppPushUtil {
	
	/**
	 * 对单个用户推送消息
	 * @param cid
	 * @param title
	 * @param contant
	 * @return
	 */
	public static String pushSingleMessage(String cid,String title,String contant){
		IGtPush push = IGtPushUtil.getPush();
		AbstractTemplate template = TemplateUtil.getNotificationTemplate(title,contant,contant);
        SingleMessage message =  MessageUtil.getSingleMessage(template);
        Target target = new Target();
        target.setAppId(IGtPushUtil.getAppId());        
        target.setClientId(cid);
        IPushResult ret = null;
        try {
            ret = push.pushMessageToSingle(message, target);
        } catch (RequestException e) {
            e.printStackTrace();
            ret = push.pushMessageToSingle(message, target, e.getRequestId());
        }
        return ret.getResponse().toString();
	}
	
	/**
	 * 对指定应用群推消息(应用通知模板)
	 * @param appIdList
	 * @param title
	 * @param contant
	 * @param taskName
	 */
	public static String pushAppMessage(String title,String contant){

        IGtPush push = IGtPushUtil.getPush();

		AbstractTemplate template = TemplateUtil.getNotificationTemplate(title,contant,contant);
        AppMessage appMessage = MessageUtil.getAppMessage(template);
       
        //推送给App的目标用户需要满足的条件
        List<String> appIdList = new ArrayList<String>();
        appIdList.add(IGtPushUtil.getAppId());
        appMessage.setAppIdList(appIdList);

        
        /* 
        //设置特点条件
        AppConditions cdt = new AppConditions();
       	List<String> phoneTypeList = new ArrayList<String>();  //手机类型 
        cdt.addCondition(AppConditions.PHONE_TYPE, phoneTypeList);
        List<String> provinceList = new ArrayList<String>(); //省份
        cdt.addCondition(AppConditions.REGION, provinceList);
        List<String> tagList = new ArrayList<String>(); //自定义tag
        cdt.addCondition(AppConditions.TAG,tagList);
        appMessage.setConditions(cdt);
        */

        IPushResult ret = push.pushMessageToApp(appMessage);
        return ret.getResponse().toString();
    }
	
	
	/**
	 * 批量单推
	 * @param cid
	 * @param title
	 * @param contant
	 * @return
	 */
	public static String pushBatchMessage(List<String> cids,String title,String contant){

	    IIGtPush push = IGtPushUtil.getPush();
	    IBatch batch = push.getBatch();
	    IPushResult ret = null;
		try {
			for (String cid : cids) {
				AbstractTemplate template = TemplateUtil.getNotificationTemplate(title,contant,contant);
				SingleMessage message =  MessageUtil.getSingleMessage(template);
		        Target target = new Target();
		        target.setAppId(IGtPushUtil.getAppId());
		        target.setClientId(cid);
		        batch.add(message, target);
			}
			ret = batch.submit();
		} catch (IOException e) {
			 e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.getResponse().toString();
	}
	
	/**
	 * 对指定列表用户推送消息
	 * @param cids
	 * @param title
	 * @param contant
	 * @param transmissionContent
	 * @return
	 */
	public static String pushListMessage(List<String> cids,String title,String contant,String transmissionContent){

        IGtPush push = IGtPushUtil.getPush();
		AbstractTemplate template = TemplateUtil.getNotificationTemplate(title,contant,contant);
        ListMessage message = MessageUtil.getListMessage(template);
        
        // 配置推送目标
        List<Target> targets = new ArrayList<Target>();
        for (int i = 0; i < cids.size(); i++) {
        	Target target = new Target();
            target.setAppId(IGtPushUtil.getAppId());  
            target.setClientId(cids.get(i));
            targets.add(target);
		}
      
        // taskId用于在推送时去查找对应的message
        String taskId = push.getContentId(message);
        IPushResult ret = push.pushMessageToList(taskId, targets);
        return ret.getResponse().toString();
	}
}

