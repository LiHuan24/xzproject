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
							<label class="col-sm-12 control-label"><h4>设备品牌新增</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="equipmentBrandAddForm">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;品牌类型：</label>
							<div class="col-sm-6">
							<!-- <#list listEquipmentCategory as equipmentCategory>
								<input type="radio"  name="brandType" value="${equipmentCategory.sortType}" checked="checked">
									<#if equipmentCategory.sortType == 0>
									<input type="hidden" id="sortNo" name="sortNoJ" value="${equipmentCategory.sortNo}" />
									健身器材
									</#if>
									<#if equipmentCategory.sortType == 1>
									<input type="hidden" id="sortNo" name="sortNoF" value="${equipmentCategory.sortNo}" />
									辅助设备
									</#if>
								</input>
							</#list> -->
							     <select name="brandType" class="form-control val"  id="brandType">
							    	<option value="">请选择</option>
							    	<option value="0">健身设备</option>
							    	<option value="1">辅助设备</option>
								</select> 
							</div>
							<div class="col-sm-6"><span name="sortTypeAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备品牌：</label>
							<div class="col-sm-6">
							    <input class="form-control val" name="brandName"/>
							</div>
							<div class="col-sm-6"><span name="brandNameAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;品牌网址：</label>
							<div class="col-sm-6">
							    <input class="form-control val" name="brandWebsite"/>
							</div>
							<div class="col-sm-6"><span name="brandWebsiteAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;备注：</label>
							<div class="col-sm-6">
								 <textarea class="form-control val" name="memo" style="width: 250px;height: 100px;"></textarea>
							</div>
							<div class="col-sm-6"><span name="memoAdd"></span></div>
						</div>
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeAddEquipmentBrand" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                        <button type="button" id="saveEquipmentBrand" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                              	保存
                        </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"equipmentBrandAdd":"res/js/equipment/equipment_brand_add"}});
		require(["equipmentBrandAdd"], function (equipmentBrandAdd){
			equipmentBrandAdd.init();
		});  
	});
</script>
</body>
