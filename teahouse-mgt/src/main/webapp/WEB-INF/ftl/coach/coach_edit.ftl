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
							<label class="col-sm-12 control-label"><h4>教练编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="coachEditForm">
					<input type="hidden" id="coachNo" value="${coach.coachNo}" name="coachNo">
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;教练姓名：</label>
							<div class="col-sm-7">
							    <input class="form-control val" name="coachName" value="${coach.coachName}"/>
							</div>
							<div class="col-sm-6"><span name="coachNameEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;昵称：</label>
							<div class="col-sm-7" >
							     <input class="form-control val" name="coachNick" value="${coach.coachNick}"/>
							</div>
							<div class="col-sm-6"><span name="coachNickEdit"></span></div>
						</div>
					</div>
					
					<div class="col-md-12">
						<div class="form-group col-md-6"> 
               				<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;手机号：</label>
			                 <div class="col-sm-7">
			                     <input class="form-control val" name="mobilePhone" value="${coach.mobilePhone}" readonly="readonly"/>
			                 </div>
		                 	<div><span name="mobilePhoneAdd"></span></div>
		             	</div>
		             	<div class="form-group col-md-6"> 
               				<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;性别：</label>
			                 <div class="col-sm-7">
			                     <input name="sex" type="radio" value="0" <#if (coach.sex == 0)>checked="checked"</#if>/>女
			                     <input name="sex" type="radio" value="1" <#if (coach.sex == 1)>checked="checked"</#if>/>男
			                 </div>
		             	</div>
					</div>
				
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;身份证：</label>
							<div class="col-sm-7">
							    	<input name="idCard" class="form-control val" value="${coach.idCard}"/>
							</div>
							<div class="col-sm-6">
							<span name="idCardEdit"></span>
							</div>
						</div>
					</div>
					
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;教练简介：</label>
							<div class="col-sm-7">
								 <textarea class="form-control val" name="synopsis" style="width: 300px;height: 150px;">
								 ${coach.synopsis}
								 </textarea>
							</div>
						</div>
					</div>	
					<div class="col-md-12">
						<div class="form-group col-md-10 memberPhotoUrl">
			                <label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;教练头像：</label>
			                <input name="memberPhotoUrl" type="hidden" value=""/>
			                <div class="col-sm-7">
			                    <img src="${imagePath!''}/${coach.memberPhotoUrl}" width="320" height="200" name="coachPicUrlImg" />
			                    <button type="button" id="editCoachPhotoUs" class="btn btn-info btn-default">上传图片</button>
			                <div style="margin-top:7px;"><span name="memberPhotoUrl"></span></div>
			                </div>
			            </div>
					</div>
                		
					</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeEditCoach" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                        <button type="button" id="updateCoach" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                              	保存
                        </button>
                    </div>
                </div>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="coachEditModalUs">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="coachPhotoFormUs">
					<div class="form-group">
                            <label class="col-md-3 control-label">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="memberPhotoUrl" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label">教练头像：</label>
                           <div class="col-md-8">
                                <div id="coachFineUploaderUs"><span name="coachErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editCoachPhotoBtnUs" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"coachEdit":"res/js/coach/coach_edit"}});
		require(["coachEdit"], function (coachEdit){
			coachEdit.init();
		});
		
		
		var config=new Object();
		config.uploadId="coachFineUploaderUs";
		//storePath为业务路径，coach_photo 教练头像图片
		config.storePath = "coach_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "coachPhotoFormUs";
		//文件回显inputName
		config.inputName = "memberPhotoUrl";
		//错误提示span标签name
		config.spanName = "coachErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});	
		
	});
</script>
</body>
