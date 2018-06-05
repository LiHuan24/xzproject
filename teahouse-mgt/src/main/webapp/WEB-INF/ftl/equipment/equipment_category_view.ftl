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
							<label class="col-sm-12 control-label"><h4>设备分类详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="equipmentCategoryAddForm">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备分类类别：</label>
							<div class="col-sm-6">
							     <#if equipmentCategory.sortType == 0>健身设备</#if>
								 <#if equipmentCategory.sortType == 1>辅助设备</#if>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备分类名称：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${equipmentCategory.sortName}</label>
							</div>
						</div>
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeViewEquipmentCategory" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
$(function(){
	$("#closeViewEquipmentCategory").click(function(){
		closeTab("设备分类详情");
	});
	
});
</script>
</body>