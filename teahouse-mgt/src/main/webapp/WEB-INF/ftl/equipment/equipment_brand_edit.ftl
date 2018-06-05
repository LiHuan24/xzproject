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
							<label class="col-sm-12 control-label"><h4>设备品牌编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="equipmentBrandEditForm">
					<input type="hidden" name="brandNo" id="brandNo" value="${equipmentBrand.brandNo}">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;品牌类型：</label>
							<div class="col-sm-6">
							    <!-- <#list listEquipmentCategory as equipmentCategory>
								<input type="radio"  name="brandType" value="${equipmentCategory.sortType}" <#if (equipmentBrand.brandType == equipmentCategory.sortType)>checked="checked"</#if>>
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
							    	<option value="0" <#if equipmentBrand.brandType == 0>selected</#if> >健身设备</option>
							    	<option value="1" <#if equipmentBrand.brandType == 1>selected</#if> >辅助设备</option>
								</select> 
							</div>
							<div class="col-sm-6"><span name="sortTypeEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备品牌：</label>
							<div class="col-sm-6">
							    <input class="form-control val" name="brandName" value="${equipmentBrand.brandName}"/>
							</div>
							<div class="col-sm-6"><span name="brandNameEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;品牌网址：</label>
							<div class="col-sm-6">
							    <input class="form-control val" name="brandWebsite" value="${equipmentBrand.brandWebsite}"/>
							</div>
							<div class="col-sm-6"><span name="brandWebsiteEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;备注：</label>
							<div class="col-sm-6">
								 <textarea class="form-control val" name="memo" style="width: 250px;height: 100px;">${equipmentBrand.memo}</textarea>
							</div>
							<div class="col-sm-6"><span name="memoEdit"></span></div>
						</div>
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeEditEquipmentBrand" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                        <button type="button" id="saveEditEquipmentBrand" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                              	保存
                        </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"equipmentBrandEdit":"res/js/equipment/equipment_brand_edit"}});
		require(["equipmentBrandEdit"], function (equipmentBrandEdit){
			equipmentBrandEdit.init();
		});  
	});
</script>
</body>
