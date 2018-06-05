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
							<label class="col-sm-12 control-label"><h4>健身设备编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="fitnessEquipmentEditForm">
					<input type="hidden" name="fitnessEquipmentNo" id="fitnessEquipmentNo" value="${fitnessEquipment.fitnessEquipmentNo}">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;所属城市：</label>
							<div class="col-sm-6">
							    <select name="cityId" class="form-control val" id="cityId">
                                     <option value="" >请选择</option>
                                     <#list cities as citie>
                                         <option value="${citie.dataDictItemId}" <#if fitnessEquipment.cityId == citie.dataDictItemId>selected</#if>>
                                               ${citie.itemValue}
                                         </option>
                                     </#list>
                                    </select>
							</div>
							<div class="col-sm-6"><span name="cityEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;所属门店：</label>
							<div class="col-sm-6" id="storeModelNs6">
							    <select name="storeNo" class="form-control val" >
							    	<option value="">请选择</option>
							    	<#list listStore as store>
                                         <option value="${store.storeNo}" <#if fitnessEquipment.storeNo == store.storeNo>selected</#if>>
                                               ${store.storeName}
                                         </option>
                                     </#list>
								</select> 
							</div>
							<div class="col-sm-6"><span name="storeEdit"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备品牌：</label>
							<div class="col-sm-6">
							    <select name="brandNo" class="form-control val" >
							    	<option value="">请选择</option>
							    	<#list listBrand as brand>
                           				<option  value="${brand.brandNo}" <#if fitnessEquipment.brandNo == brand.brandNo>selected</#if>>
                               				${brand.brandName}
                           				</option>
                       				</#list>
								</select> 
							</div>
							<div class="col-sm-6"><span name="brandNoEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备型号：</label>
							<div class="col-sm-6" id="ecquimentModelDiv">
							    <select name="modelNo" class="form-control val" >
							    	<option value="">请选择</option>
							    	<#list listEquipmentModel as model>
                           				<option  value="${model.modelNo}" <#if fitnessEquipment.modelNo == model.modelNo>selected</#if>>
                               				${model.modelName}
                           				</option>
                       				</#list>
								</select> 
							</div>
							<div class="col-sm-6"><span name="modelEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;设备类型：</label>
							<div class="col-sm-6" id="sortModel">
							    <select name="sortNo" class="form-control val" >
							    	<#list listEquipmentCategory as ec>
                           				<option  value="${ec.sortNo}" <#if fitnessEquipment.sortNo == ec.sortNo>selected</#if>>
                               				${ec.sortName}
                           				</option>
                       				</#list>
								</select> 
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;&nbsp;备注：</label>
							<div class="col-sm-6">
								 <textarea class="form-control val" name="memo" style="width: 250px;height: 100px;">${fitnessEquipment.memo}</textarea>
							</div>
							<div class="col-sm-6"><span name="memoEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备二维码编号：</label>
							<div class="col-sm-6">
								 <input class="form-control val" name="devId" value="${fitnessEquipment.devId}"/>
							</div>
							<div class="col-sm-6"><span name="devIdEdit"></span></div>
						</div>
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeEditFitnessEquipment" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                        <button type="button" id="saveFitnessEquipment" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                              	保存
                        </button>
                    </div>
                </div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"fitnessEquipmentEdit":"res/js/equipment/fitness_equipment_edit"}});
		require(["fitnessEquipmentEdit"], function (fitnessEquipmentEdit){
			fitnessEquipmentEdit.init();
		});
	});
</script>
</body>
