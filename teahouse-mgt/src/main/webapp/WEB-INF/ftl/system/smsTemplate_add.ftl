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
							<label class="col-sm-12 control-label"><h4>短信模板新增</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-12 form-horizontal">
					<form name="smsTemplateAddForm" class="form-horizontal">
						<div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label key" style="text-align:right;">短信模板类型：</label>
                            <div class="col-sm-4">
                                <select class="form-control val" id="CustomeremplateType" name="templateType">
                                  <option value="1">注册</option>
                                  <option value="2">修改密码</option>
                                  <option value="3">预约成功</option>
                                  <option value="4">会员审核</option>
                                  <option value="6">退款通知</option>
                                  <option value="7">排队成功</option>
                                  <option value="8">本次排队未能预约成功</option>
								</select>  
                            </div>
                            <div><span name="templateTypeAdd"></span></div>
                      	  </div>
                      	   <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label key" style="text-align:right;">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp短信内容：</label>
                            <div class="col-sm-4">
                                <textarea class="form-control val" rows="6" id="CustomeremplateContent" name="templateContent"></textarea>
                            </div>
                            <div><span name="templateContentAdd"></span></div>
                        	</div>  
						</div>
						</form>	
						<div class="form-group">
							<div class="col-sm-6" style="margin-bottom:20px;">
					       <button type="button" class="btn btn-default pull-right btnDefault"  id="closeSmsTemplateAdd" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">关闭</button>
                           <button type="button" class="btn btn-default pull-right btnColorA"  id="addSmsTemplate" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">保存</button>
							</div>	
						</div>
					</div>	
        		</div><!-- /.row -->
    
</div>
<script type="text/javascript">
$(function(){
	  require.config({paths: {"smsTemplateAdd":"res/js/system/smsTemplate_add"}});
		require(["smsTemplateAdd"], function (smsTemplateAdd){
			smsTemplateAdd.init();
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
