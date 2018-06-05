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
							<label class="col-sm-12 control-label"><h4>门店编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="stroeEditForm">
					<input class="form-control" type="hidden" name="storeNo" value="${store.storeNo}"/>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;门店名称：</label>
							<div class="col-sm-6">
								<input class="form-control val" name="storeName" value="${store.storeName}"/>
							</div>
							<div class="col-sm-6"><span name="storeNameEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;所属城市：</label>
							<div class="col-sm-6">
							    <select name="cityId" class="form-control val" id="cityId">
                                     <option value="" >
                                      		请选择
                                     </option>
                                     <#list cities as citie>
                                         <option value="${citie.dataDictItemId}" <#if store.cityId == citie.dataDictItemId> selected </#if>>
                                                ${citie.itemValue}
                                         </option>
                                     </#list>
                                    </select>
							</div>
							<div class="col-sm-6"><span name="cityEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;门店开门时间：</label>
							<div class="col-sm-6">
								<input class="datetimepicker form-control" name="storeOpenDate" value="${store.storeOpenDate?string('HH:mm')}" />
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;门店关门时间：</label>
							<div class="col-sm-6">
							    <input class="datetimepicker form-control" name="storeColseDate" value="${store.storeColseDate?string('HH:mm')}" />
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;门店类型：</label>
							<div class="col-sm-6">
								 <select name="storeType" class="form-control val"  id="storeType">
							    	<option value="">请选择</option>
							    	<option value="0" <#if store.storeType == 0> selected </#if>>社区馆</option>
							    	<option value="1" <#if store.storeType == 1> selected </#if>>主题馆</option>
								</select>
							</div>
							<div class="col-sm-6"><span name="storeTypeEdit"></span></div>
						</div>
						<!-- 高德地图 地图选点 -->
						<div class="form-group col-md-6">
                                <label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;门店坐标：</label>
                                <div class="col-sm-6">
								 <input   style="width:50px;height:34px;"  name="longitude" value="${store.longitude}"/>N
                                 <input   style="width:50px;height:34px;"  name="latitude"  value="${store.latitude}"/>E
								<input type="button" class="btn btn-info addorderMonthCar" id="mapLocationGdEdit" value="地图选址">
						</div>
						<div class="col-sm-6"><span name="latlongEdit"></span></div>
						</div>
						
                       <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;门店地址：</label>
							<div class="col-sm-6">
								<input class="form-control val" name="addrStreet" id="addrStreet" value="${store.addrStreet}"/>
							</div>
						</div>
						
						<div class="form-group col-md-10 modelPictureUrl1">
			                <label class="col-sm-3 col-xs-3 control-label key">&nbsp;&nbsp;门店图片：</label>
			                <input name="storePictureUrl1" type="hidden" value="${store.storePictureUrl1}"/>
			                <div class="col-sm-8">
			                    <button type="button" id="editStorePhotoUs" class="btn btn-info btn-default">上传图片</button>
			                <div style="margin-top:7px;"><span name="storePictureUrl1"></span></div>
			                </div>
			            </div>
			            <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;&nbsp;备注：</label>
							<div class="col-sm-6">
								 <textarea class="form-control val" name="memo"  style="width: 250px;height: 100px;">${store.memo}</textarea>
							</div>
							<div class="col-sm-6"><span name="memoEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;&nbsp;门店介绍：</label>
							<div class="col-sm-6">
								 <textarea class="form-control val" name="synopsis"  style="width: 250px;height: 100px;">${store.synopsis}</textarea>
							</div>
							<div class="col-sm-6"><span name="synopsisEdit"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;门禁编码：</label>
							<div class="col-sm-6">
								 <input class="form-control val" name="entranceCode" value="${store.entranceCode}"/>
							</div>
							<div class="col-sm-6"><span name="entranceCodeEdit"></span></div>
						</div>
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeStoreEdit" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                        <button type="button" id="saveStoreEdit" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                              	保存
                        </button>
                    </div>
                </div>
</div>
<!-- 上传图片弹出框-->
    <div class="modal fade" id="storeEditModalUs">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4><span name="storeEditWs"></span>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="storePhotoFormUs">
					<div class="form-group">
                            <label class="col-md-3 control-label">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="storePictureUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label">门店图片：</label>
                           <div class="col-md-8">
                                <div id="storeEditFineUploaderUs"><span name="storeErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editStorePhotoBtnUs" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- 高德地图实例 -->
 	<!-- 弹出框 获取坐标地址 操作-->
	<div class="modal fade" id="mapLocationEdit" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="margin-top: 0px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">
						选择地址 <br /> <span id="lctNsEdit"></span>
					</h4>
				</div>
				<div class="modal-body" style="max-height: 500px;">
					<div id="mapLctNsEdit" tabindex="0"></div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">确定
				</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"storeEdit":"res/js/store/store_edit"}});
		require(["storeEdit"], function (storeEdit){
			storeEdit.init();
		});  
		
		
/* 		var config=new Object();
		config.uploadId="storeFineUploaderUs";
		//storePath为业务路径，equipmentModel_photo 设备型号图片
		config.storePath = "store_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "storePhotoFormUs";
		//文件回显inputName
		config.inputName = "storePictureUrl1";
		//错误提示span标签name
		config.spanName = "storeErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});	 */
		
 		$(".datetimepicker").datetimepicker({
 			format: "hh:ii",
			language:  'fr',
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 1,
	        minView: 0,
	        maxView: 1,
	        forceParse: 0
        });
		
	});
</script>
</body>
