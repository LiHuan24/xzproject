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
							<label class="col-sm-12 control-label"><h4>健身设备新增</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="fitnessEquipmentAddForm">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;所属城市：</label>
							<div class="col-sm-6">
							    <select name="cityId" class="form-control val" id="cityId">
                                     <option value="" >请选择</option>
                                     <#list cities as citie>
                                         <option value="${citie.dataDictItemId}" >
                                               ${citie.itemValue}
                                         </option>
                                     </#list>
                                    </select>
							</div>
							<div class="col-sm-6"><span name="cityAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;所属门店：</label>
							<div class="col-sm-6" id="storeModelNs5">
							    <select name="storeNo" class="form-control val" >
							    	<option value="">请选择</option>
							    	
								</select> 
							</div>
							<div class="col-sm-6"><span name="storeAdd"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;设备品牌：</label>
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
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备型号：</label>
							<div class="col-sm-6" id="ecquimentModelDiv">
							    <select name="modelNo" class="form-control val" >
							    	<option value="">请选择</option>
							    	
								</select> 
							</div>
							<div class="col-sm-6"><span name="modelAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;设备类型：</label>
							<div class="col-sm-6" id="sortModel">
							    <select name="sortNo" class="form-control val" >
							    	<option value="">请选择</option>
							    	
								</select> 
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;&nbsp;备注：</label>
							<div class="col-sm-6">
								 <textarea class="form-control val" name="memo" style="width: 250px;height: 100px;"></textarea>
							</div>
							<div class="col-sm-6"><span name="memoAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备二维码编号：</label>
							<div class="col-sm-6">
								 <input class="form-control val" name="devId" />
							</div>
							<div class="col-sm-6"><span name="devIdAdd"></span></div>
						</div>
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeAddFitnessEquipment" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
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
	  require.config({paths: {"fitnessEquipmentAdd":"res/js/equipment/fitness_equipment_add"}});
		require(["fitnessEquipmentAdd"], function (fitnessEquipmentAdd){
			fitnessEquipmentAdd.init();
		});
	});
</script>
</body>
