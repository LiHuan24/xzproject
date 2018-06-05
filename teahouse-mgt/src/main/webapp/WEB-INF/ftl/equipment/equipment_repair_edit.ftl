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
							<label class="col-sm-12 control-label"><h4>设备维修记录编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="equipmentRepairEditForm">
					<input type="hidden" name="equipmentRepairNo" id="equipmentRepairNo" value="${equipmentRepair.equipmentRepairNo}">
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;所属城市：</label>
							<div class="col-sm-7">
							    <select name="cityId" class="form-control val" id="cityId">
                                     <option value="" >请选择城市</option>
                                     <#list cities as citie>
                                         <option value="${citie.dataDictItemId}" <#if equipmentRepair.cityId == citie.dataDictItemId>selected</#if>>
                                               ${citie.itemValue}
                                         </option>
                                     </#list>
                                    </select>
							</div>
							<div class="col-sm-6"><span name="cityEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;所属门店：</label>
							<div class="col-sm-7" id="storeModelNs3">
							    <select name="storeNo" class="form-control val" >
							    	<option value="">请选择门店</option>
							    	<#list listStore as store>
                                         <option value="${store.storeNo}" <#if equipmentRepair.storeNo == store.storeNo>selected</#if>>
                                               ${store.storeName}
                                         </option>
                                     </#list>
								</select> 
							</div>
							<div class="col-sm-6"><span name="storeEdit"></span></div>
						</div>
					</div>
					
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;设备编号：</label>
							<div class="col-sm-7" id="fitnessEquipmentModel">
							    <select name="fitnessEquipmentNo" class="form-control val" >
							    	<option value="">请选择设备</option>
							    	<#list listFitness as fitness>
                                         <option value="${fitness.fitnessEquipmentNo}" <#if equipmentRepair.fitnessEquipmentNo == fitness.fitnessEquipmentNo>selected</#if>>
                                               ${fitness.fitnessEquipmentNo}
                                         </option>
                                     </#list>
								</select> 
							</div>
							<div class="col-sm-6">
								<span name="fitnessEquipmentEdit"></span>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;设备类型：</label>
							<div class="col-sm-7" id="sortModel">
							    <select name="sortNo" class="form-control val" >
							    	<#list listSort as sort>
                                         <option value="${sort.sortNo}" <#if equipmentRepair.sortNo == sort.sortNo>selected</#if>>
                                               ${sort.sortName}
                                         </option>
                                     </#list>
								</select> 
							</div>
						</div>
					</div>
				
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;维护状态：</label>
							<div class="col-sm-7">
							    <select name="repairStatus" class="form-control val" >
							    	<option value="">请选择状态</option> 
										<option value="0" <#if equipmentRepair.repairStatus == 0>selected</#if>>未开始</option> 
										<option value="1" <#if equipmentRepair.repairStatus == 1>selected</#if>>进行中</option> 
										<option value="2" <#if equipmentRepair.repairStatus == 2>selected</#if>>已完成</option> 
								</select> 
							</div>
							<div class="col-sm-6">
								<span name="repairStatusEdit"></span>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;维护类型：</label>
							<div class="col-sm-7">
							    <select name="repairId" class="form-control val" >
							    	<option value="">请选择维护类型</option>
							    	<#list maintenanceTypes as mintypes>
										<option value="${mintypes.dataDictItemId}" <#if equipmentRepair.repairId == mintypes.dataDictItemId>selected</#if>>
										${mintypes.itemValue}
										</option>
									</#list>
								</select> 
							</div>
							<div class="col-sm-6">
								<span name="repairIdEdit"></span>
							</div>
						</div>
						</div>
							
					<div class="col-md-12">
						<div class="form-group col-md-6"> 
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;开始时间：</label>
			                    <div class="col-sm-7">
			                        <input class="datepicker form-control val" name="startTime" value=" ${equipmentRepair.startTime?string('yyyy-MM-dd')}"/>
			                    </div>
	                		</div>
	                		<div class="form-group col-md-6"> 
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;结束时间：</label>
			                    <div class="col-sm-7">
			                        <input class="datepicker form-control val" name="endTime" value=" ${equipmentRepair.endTime?string('yyyy-MM-dd')}"/>
			                    </div>
	                		</div>
					</div>
					<div class="col-md-12">
						<div class="form-group col-md-6"> 
                			<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;维护人：</label>
			                 <div class="col-sm-7">
			                     <input class="form-control val" name="repairName" value="${equipmentRepair.repairName}"/>
			                 </div>
			                 <br /><div><span name="repairNameEdit"></span></div>
		             	</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;备注：</label>
							<div class="col-sm-7">
								 <textarea class="form-control val" name="memo" style="width: 280px;height: 100px;">${equipmentRepair.memo}</textarea>
							</div>
							<div><span name="memoEdit"></span></div>
						</div>
					</div>	
                		
					</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeEditEquipmentRepair" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                        <button type="button" id="updateEquipmentRepair" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                              	保存
                        </button>
                    </div>
                </div>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="repairEditModalUs">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="repairPhotoFormUs">
					<div class="form-group">
                            <label class="col-md-3 control-label">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="repairPictureUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label">车辆图片：</label>
                           <div class="col-md-8">
                                <div id="repairFineUploaderUs"><span name="repairErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editRepairPhotoBtnUs" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"equipmentRepairEdit":"res/js/equipment/equipment_repair_edit"}});
		require(["equipmentRepairEdit"], function (equipmentRepairEdit){
			equipmentRepairEdit.init();
		});
		
		
		var config=new Object();
		config.uploadId="repairFineUploaderUs";
		//storePath为业务路径，equipmentRepair_photo 设备维修记录图片
		config.storePath = "equipmentRepair_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "repairPhotoFormUs";
		//文件回显inputName
		config.inputName = "repairPictureUrl1";
		//错误提示span标签name
		config.spanName = "repairErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});	
		
		 $(".datepicker").datetimepicker({
	            language: "zh-CN",
	            minView: "month",
	            autoclose: true,
	            clearBtn: true,//清除按钮
	            todayBtn: true,
	            minuteStep: 5,
	            startDate: moment(new Date()).format("YYYY-MM"),
	            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	        });
	});
</script>
</body>
