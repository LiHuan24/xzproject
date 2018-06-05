<meta charset="utf-8">
<body>
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
							<label class="col-sm-12 control-label"><h4>消息推送详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
	 				<form name="messagePushViewForm" class="form-horizontal">
	 					<div class="form-group col-md-7">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;推送方式：</label>
							<div class="col-sm-6">
							   <label class="control-label val">
							   	<#if messagePush.pushPattern==1>多个用户
                                <#elseif messagePush.pushPattern==2>全部用户
                                </#if>
							</label>
							</div>
						</div>
						<#if messagePush.pushPattern==1>
						<div class="form-group col-md-7">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;推送会员：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${messagePush.memberName}</label>
							</div>
						</div>
						</#if>
						<div class="form-group col-md-7">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;标题：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${messagePush.title}</label>
							</div>
						</div>
						
						<div class="form-group col-md-8">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;内容：</label>
							<div class="col-sm-6">
    						<textarea name="content" class="form-control val" style="height: 250px;line-height: 25px"  disabled="disabled">${messagePush.content}</textarea>
							</div>
						</div>
                        <div class="form-group col-md-7">
                            <label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;推送状态：</label>
                            <div class="col-sm-6">
							    <label class="control-label val">
	                                <#if messagePush.pushStatus==0>未推送
	                                <#elseif messagePush.pushStatus==1>已推送
	                                </#if>
                                </label>
                            </div>
                        </div>
                        <#if messagePush.pushStatus==1>
                        <div class="form-group col-md-7">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;推送时间：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${messagePush.pushTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						</#if>
						<div class="form-group col-md-7">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${messagePush.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
  					</form>
					</div>
        		</div><!-- /.row -->
                <div class="form-group col-md-7">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button id="closeMessagePushView" class="btn btn-default pull-right navbar-btn btn-flat messagePush-operate-close btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
                                关闭
                        </button>
                    </div>
                </div>	
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"messagePush":"res/js/marketing/messagePush_list"}});
		require(["messagePush"], function (messagePush){
			messagePush.init();
		});  
	});
</script>
</body>
