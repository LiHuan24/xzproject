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
							<label class="col-sm-2 control-label"><h4>数据字典项新增</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-8 form-horizontal">
					<form name="dataDictItemAddForm">
						<div class="form-group ">
							<label class="col-sm-3 control-label key">数据字典分类代码：</label>
							<div class="col-sm-4">
							   <select name="dataDictCatCode" class="form-control val">
							    	<option value="">请选择</option>
							    	<#if dataDictCatList?exists>
							    		<#list dataDictCatList as dataDictCat>
							    			<option value="${dataDictCat.dataDictCatCode}">${dataDictCat.dataDictCatName}</option>
							    		</#list>
							    	</#if>
							    </select>
							</div>
							<div style="margin-top:7px;"><span id="dataDictItemCodeAdd"></span></div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">数据字典项父级项：</label>
							<div class="col-sm-4">
							    <select name="parentCatCode" class="form-control val" id="parentCatCode">
							    	<option value="">请选择</option>
							    	<#if dataDictItemList?exists>
							    		<#list dataDictItemList as dataDictItem>
							    			<option value="${dataDictItem.dataDictItemId}">${dataDictItem.dataDictCatCode}</option>
							    		</#list>
							    	</#if>
							    </select>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">数据字典项父级项值：</label>
							<div class="col-sm-4">
							    <select name="parentItemId" class="form-control val" id="parentItemId">
							    	<option value="">请选择</option>
							    	<option  ></option>
							    </select>
							</div>
						</div>		
						<div class="form-group">
							<label class="col-sm-3 control-label key">数据字典项值：</label>
							<div class="col-sm-4">
								<input class="form-control val" name="itemValue"/>
							</div>
							<div style="margin-top:7px;"><span id="itemValueAdd"></span></div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">扩展信息：</label>
							<div class="col-sm-4">
								<input class="form-control val" name="info1"/>
							</div>
							<div style="margin-top:7px;"><span id="info1Add"></span></div>
						</div>	
						<!-- <div class="form-group">
							<label class="col-sm-3 control-label key">扩展信息2：</label>
							<div class="col-sm-4">
								<input class="form-control val" name="info2"/>
							</div>
							<div style="margin-top:7px;"><button type="button" id="addinfo2" class="btn btn-info">上传文件</button></div>
						</div>	 -->
						<div class="form-group">
							<label class="col-sm-3 control-label key">备注：</label>
							<div class="col-sm-4">
								<textarea class="form-control val" name="memo"/>
							</div>
						</div>												
                	</form>	
                		<div class="form-group">
							<div class="col-sm-7">
                            <button id="closeAddDataDictItem" class="btn btn-default pull-right navbar-btn btn-flat btnDefault" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">关闭</button>
                            <button id="saveAddDataDictItem" class="btn btn-default pull-right navbar-btn btn-flat btnColorA" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">保存</button>
							</div>	
						</div>	
					</div>	
        		</div><!-- /.row -->
    
</div>
 <div class="modal fade" id="info2AddModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传文件</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="infoForm">
					<div class="form-group">
                            <label class="col-md-3 control-label val">地址URL：</label>
                            <div class="col-md-8">
                             <input type="text" class="form-control val" placeholder="" name="info2" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val">文件选择：</label>
                           <div class="col-md-8">
                                <div id="info1zhFineUploader"><span name="parkErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right"
							id="info2AddW" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"dataDictItemAdd":"res/js/system/dataDictItemAdd"}});
		require(["dataDictItemAdd"], function (dataDictItemAdd){
			dataDictItemAdd.init();
		}); 
		
		
		var config=new Object();
		config.uploadId="info1zhFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "car_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png","txt"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "infoForm";
		//文件回显inputName
		config.inputName = "info2";
		//错误提示span标签name
		config.spanName = "parkErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		}); 
	});
</script>
</body>
