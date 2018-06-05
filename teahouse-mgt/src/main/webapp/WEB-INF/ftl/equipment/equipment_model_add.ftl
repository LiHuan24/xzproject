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
							<label class="col-sm-12 control-label"><h4>设备型号新增</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="equipmentModelAddForm">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备品牌：</label>
							<div class="col-sm-6">
							    <select name="brandNo" class="form-control val" >
							    	<option value="">请选择</option>
							    	<#list listBrand as brand>
                           				<option  value="${brand.brandNo}" >
                               				${brand.brandName}
                           				</option>
                       				</#list>
								</select> 
							</div>
							<div class="col-sm-6"><span name="brandNoAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备类型：</label>
							<div class="col-sm-6">
							    <select name="sortNo" class="form-control val" >
							    	<option value="">请选择</option>
							    	<#list listCategory as category>
                           				<option  value="${category.sortNo}" >
                               				${category.sortName}
                           				</option>
                       				</#list>
								</select> 
							</div>
							<div class="col-sm-6"><span name="sortNoAdd"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备型号（名称）：</label>
							<div class="col-sm-6">
							    <input class="form-control val" name="modelName"/>
							</div>
							<div class="col-sm-6"><span name="modelNameAdd"></span></div>
						</div>
						<!-- <div class="form-group col-md-10 modelPictureUrl1">
			                <label class="col-sm-3 col-xs-3 control-label key">&nbsp;&nbsp;设备图片：</label>
			                <input name="modelPictureUrl1" type="hidden" value=""/>
			                <div class="col-sm-8">
			                    <img src="" width="320" height="200" name="modelPicUrlImg" />
			                    <button type="button" id="addModelPhotoUs" class="btn btn-info btn-default">上传图片</button>
			                <div style="margin-top:7px;"><span name="modelPictureUrl1"></span></div>
			                </div>
			            </div> -->
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeAddEquipmentModel" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                        <button type="button" id="saveEquipmentModel" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                              	保存
                        </button>
                    </div>
                </div>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="modelAddModalUs">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="modelPhotoFormUs">
					<div class="form-group">
                            <label class="col-md-3 control-label">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="modelPictureUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label">车辆图片：</label>
                           <div class="col-md-8">
                                <div id="modelFineUploaderUs"><span name="parkErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="addModelPhotoBtnUs" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"equipmentModelAdd":"res/js/equipment/equipment_model_add"}});
		require(["equipmentModelAdd"], function (equipmentModelAdd){
			equipmentModelAdd.init();
		});
		
		var config=new Object();
		config.uploadId="modelFineUploaderUs";
		//storePath为业务路径，equipmentModel_photo 设备型号图片
		config.storePath = "equipmentModel_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "modelPhotoFormUs";
		//文件回显inputName
		config.inputName = "modelPictureUrl1";
		//错误提示span标签name
		config.spanName = "parkErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});	
	});
</script>
</body>
