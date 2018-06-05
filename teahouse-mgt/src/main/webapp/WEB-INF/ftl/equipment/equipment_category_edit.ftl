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
							<label class="col-sm-12 control-label"><h4>设备分类编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="equipmentCategoryEditForm">
					<input type="hidden" name="sortNo" id="sortNo" value="${equipmentCategory.sortNo}">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备分类类别：</label>
							<div class="col-sm-6">
							     <select name="sortType" class="form-control val"  id="sortType">
							    	<option value="">请选择</option>
							    	<option value="0" <#if (equipmentCategory.sortType == '0')>selected="selected"</#if>>
					    			健身设备</option>
							    	<option value="1" <#if (equipmentCategory.sortType == '1')>selected="selected"</#if>>
					    			辅助设备</option>
								</select>
							</div>
							<div class="col-sm-6"><span name="sortTypeEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备分类名称：</label>
							<div class="col-sm-6">
							    <input class="form-control val" name="sortName" value="${equipmentCategory.sortName}"/>
							</div>
							<div class="col-sm-6"><span name="sortNameEdit"></span></div>
						</div>
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeEditEquipmentCategory" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; margin-top:50px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                        <button type="button" id="saveEditEquipmentCategory" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; margin-top:50px; background:#2b94fd;">
                              	保存
                        </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"equipmentCategoryEdit":"res/js/equipment/equipment_category_edit"}});
		require(["equipmentCategoryEdit"], function (equipmentCategoryEdit){
			equipmentCategoryEdit.init();
		}); 
	});
</script>
</body>