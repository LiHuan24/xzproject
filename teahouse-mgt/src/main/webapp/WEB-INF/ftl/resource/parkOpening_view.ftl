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
						<div class="form-group">
							<label class="col-sm-12 control-label"><h4>客服反馈信息详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-8">
	 				<form name="customerFeedbackViewForm" class="form-horizontal">
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;客户：</label>
							<div class="col-sm-4">
							   <label class="control-label val">${pa.memberName}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;手机号：</label>
							<div class="col-sm-4">
							   <label class="control-label val">${pa.mobilePhone}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;内容：</label>
							<div class="col-sm-4">
							<textarea class="form-control val" rows="6"  name="content" disabled>${pa.memo}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;提交时间：</label>
							<div class="col-sm-4">
							    <label class="control-label val">${pa.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<!--<div class="form-group">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;回复状态：</label>
							<div class="col-sm-4">
							<label class="control-label">
								 <#if customerFeedback.replyStatus==0>未回复
								 <#elseif customerFeedback.feedbackType==1>已回复
                                 </#if> 
							</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;客服回复时间：</label>
							<div class="col-sm-4">
							    <label class="control-label">${customerFeedback.replyTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">&nbsp;&nbsp;客服回复内容：</label>
							<div class="col-sm-4">
							<textarea class="form-control" rows="6"  name="replyContent" disabled>${customerFeedback.replyContent}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;回复人：</label>
							<div class="col-sm-4">
							    <label class="control-label">${customerFeedback.operatorName}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;相关图片1：</label>
							<div class="col-sm-9">
                			<label class="control-label">
                				<img src="${imagePath!''}/${customerFeedback.photoUrl1}" width="320" height="200"/>
                				</label>
                			</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;相关图片2：</label>
							<div class="col-sm-9">
                			<label class="control-label">
                				<img src="${imagePath!''}/${customerFeedback.photoUrl2}" width="320" height="200"/>
                				</label>
                			</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label">*&nbsp;&nbsp;相关图片3：</label>
							<div class="col-sm-9">
                			<label class="control-label">
                				<img src="${imagePath!''}/${customerFeedback.photoUrl3}" width="320" height="200"/>
                				</label>
                			</div>
						</div>-->
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;更新时间：</label>
							<div class="col-sm-4">
							    <label class="control-label val">${pa.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
  					</form>					
                		<div class="form-group">
                			<div class="col-sm-9">
                				<button id="closeparkOpeningView" class="btn btn-default pull-right navbar-btn btn-flat carRecord-operate-save" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">关闭</button>
                			</div>
                		</div>	
					</div>	
        		</div><!-- /.row -->
    
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"customerFeedback":"res/js/resource/parkOpening_list"}});
		require(["parkOpening"], function (parkOpening){
			parkOpening.init();
		});  
	});
</script>
</body>
