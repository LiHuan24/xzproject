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
							<label class="col-sm-12 control-label"><h4>门店详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="stroeEditForm">
					<input class="form-control" type="hidden" name="storeNo" value="${store.storeNo}"/>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;门店名称：</label>
							<div class="col-sm-6">
								${store.storeName}
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;所属城市：</label>
							<div class="col-sm-6">
							    ${store.cityName}
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;门店开门时间：</label>
							<div class="col-sm-6">
								${store.storeOpenDate?string('HH:mm')}
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;门店关门时间：</label>
							<div class="col-sm-6">
							    ${store.storeColseDate?string('HH:mm')}
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;门店类型：</label>
							<div class="col-sm-6">
								<#if store.storeType == 0> 社区馆</#if>
							    <#if store.storeType == 1> 主题馆 </#if>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;门店状态：</label>
							<div class="col-sm-6">
								<#if store.storeStatus == 0> 启用</#if>
							    <#if store.storeStatus == 1> 停用 </#if>
							</div>
						</div>
						<!-- 高德地图 地图选点 -->
						<div class="form-group col-md-6">
                                <label class="col-sm-6 control-label key">&nbsp;&nbsp;门店坐标：</label>
                                <div class="col-sm-6">
								 <input   style="width:50px;height:34px;"  name="longitude" value="${store.longitude}" readonly="readonly"/>N
                                 <input   style="width:50px;height:34px;"  name="latitude"  value="${store.latitude}"/ readonly="readonly">E
								<!-- <input type="button" class="btn btn-info addorderMonthCar" id="mapLocationGdAdd" value="地图选址"> -->
						</div>
						</div>
						
                       <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;门店地址：</label>
							<div class="col-sm-6">
								${store.addrStreet}
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;&nbsp;门店介绍：</label>
							<div class="col-sm-6">
								 ${store.synopsis}
							</div>
						</div>
						
			            <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;&nbsp;备注：</label>
							<div class="col-sm-6">
								 ${store.memo}
							</div>
						</div>
						
						<#list picUrls  as urls>
                		<div class="form-group col-md-12">
                		<hr>
                			<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;图片：</label>
                			<div class="col-sm-8">
                			<label class="control-label val">
                				<img src="${imagePath!''}/${urls}" width="320" height="200"/>
                				</label>
                			</div>
                		</div>
				        </#list>
				        
				        <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;门禁编码：</label>
							<div class="col-sm-6">
								${store.entranceCode}
							</div>
						</div>
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeViewStore" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: -330px !important">
                             	 关闭
                        </button>
                    </div>
                    </div>
                </div>
</div>


<script type="text/javascript">
	$(function(){
		$("#closeViewStore").click(function(){
			closeTab("门店查看");
		});
		
		
		var config=new Object();
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
		});	
		
		$('.datetimepicker').datetimepicker({
		    format: 'yyyy-mm-dd hh:ii:ss',
		    autoclose: true,
		    minView: 0,
		    minuteStep:1
		});
	});
</script>
</body>
