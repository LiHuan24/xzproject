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
							<label class="col-sm-12 control-label"><h4>教练详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="coachAddForm">
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;教练姓名：</label>
							<div class="col-sm-7">
							    ${coach.coachName}
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;昵称：</label>
							<div class="col-sm-7" >
							     ${coach.coachNick}
							</div>
						</div>
					</div>
					
					<div class="col-md-12">
						<div class="form-group col-md-6"> 
               				<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;手机号：</label>
			                 <div class="col-sm-7">
			                     ${coach.mobilePhone}
			                 </div>
		             	</div>
		             	<div class="form-group col-md-6"> 
               				<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;性别：</label>
			                 <div class="col-sm-7">
			                 	<#if coach.sex = 0>女</#if>
			                 	<#if coach.sex = 1>男</#if>
			                 </div>
		             	</div>
					</div>
				
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;身份证：</label>
							<div class="col-sm-7">
							    ${coach.idCard}
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
			                <input name="repairPictureUrl1" type="hidden" value=""/>
			                <div class="col-sm-7">
			                    <img src="${imagePath!''}/${coach.memberPhotoUrl}" width="320" height="200" name="coachPicUrlImg" />
			                <div style="margin-top:7px;"><span name="memberPhotoUrl"></span></div>
			                </div>
			            </div>
					</div>
                		
					</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeViewCoach" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
		$("#closeViewCoach").click(function(){
			closeTab("教练查看");
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
