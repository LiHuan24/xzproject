<meta charset="utf-8">
<style>
    .key {
        font-size: 1.5rem;
        font-weight: 500;
        color: #414550;
        line-height: 15px;
    }

    .val {
        font-size: 1.5rem;
        font-weight: 500;
        color: #a0a7af;
        line-height: 15px;
    }
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>短信模板编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-8 form-horizontal">
					<form name="smsTemplateEditForm" class="form-horizontal">
					<input type="hidden" name="templateId" value="${smsTemplate.templateId}"/>
						<div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label key">短信模板类型：</label>
                            <div class="col-sm-8">
                                <select class="form-control val" id="CustomeremplateType" name="templateType">
                                  <option value="1" <#if smsTemplate.templateType==1>selected</#if> >注册</option>
                                  <option value="2" <#if smsTemplate.templateType==2>selected</#if> >修改密码</option>
                                   <option value="3"<#if smsTemplate.templateType==3>selected</#if> >预约成功</option>
									<option value="4" <#if smsTemplate.templateType==4>selected</#if> >会员审核</option>
									<option value="6" <#if smsTemplate.templateType==6>selected</#if> >退款通知</option>
									<option value="7" <#if smsTemplate.templateType==7>selected</#if> >排队成功</option>
									<option value="8" <#if smsTemplate.templateType==8>selected</#if> >本次排队未能预约成功</option>
								</select>  
                            </div>
                            <div><span name="templateTypeEdit"></span></div>
                      	  </div>
                      	   <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label key">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp短信内容：</label>
                            <div class="col-sm-8">
                                <textarea class="form-control val" rows="6" id="CustomeremplateContent" name="templateContent">${smsTemplate.templateContent}</textarea>
                            </div>
                            <div><span name="templateContentEdit"></span></div>
                        	</div>  
						</div>
						</form>	
						<div class="form-group">
							<div class="col-sm-7" style="margin-bottom:20px;">
							<button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="closeSmsTemplateEdit" class="btn btn-default pull-right btnDefault" >
							    关闭
							</button>
					        <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;margin-right: 50px !important" type="button" id="editSmsTemplate" class="btn btn-default pull-right btnColorA" >
					            保存
					        </button>
							</div>	
						</div>
					</div>	
        		</div><!-- /.row -->
    
</div>
<script type="text/javascript">
$(function(){
	  require.config({paths: {"smsTemplateEdit":"res/js/system/smsTemplate_edit"}});
		require(["smsTemplateEdit"], function (smsTemplateEdit){
			smsTemplateEdit.init();
		});  
        $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
});
</script>
