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
							<label class="col-sm-12 control-label"><h4>设备维修记录详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="auxiliaryEquipmentViewForm">
					
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;设备编号：</label>
							<div class="col-sm-7">
							    ${equipmentRepair.fitnessEquipmentNo}
							</div>
							<div class="col-sm-6"><span name="cityAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;维修类型：</label>
							<div class="col-sm-7">
							    ${equipmentRepair.repairTypeName}
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;开始时间：</label>
							<div class="col-sm-7">
							    ${equipmentRepair.startTime?string('yyyy-MM-dd')}
							</div>
							<div class="col-sm-6"><span name="brandNoAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;结束时间：</label>
							<div class="col-sm-7">
							    ${equipmentRepair.endTime?string('yyyy-MM-dd')}
							</div>
						</div>
					</div>	
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;维护状态：</label>
							<div class="col-sm-7">
							<#if equipmentRepair.repairStatus == 0>未开始</#if>
							<#if equipmentRepair.repairStatus == 1>进行中</#if>
							<#if equipmentRepair.repairStatus == 2>已完成</#if>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;维护人：</label>
							<div class="col-sm-7">
							    ${equipmentRepair.repairName}
							</div>
						</div>
					</div>
					<div class="col-md-12">
					<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;城市：</label>
							<div class="col-sm-7">
							    ${equipmentRepair.cityName}
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;所属门店：</label>
							<div class="col-sm-7">
							    ${equipmentRepair.storeName}
							</div>
						</div>
					</div>
					
						
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeEquipmentRepairView" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                    </div>
                </div>
</div>

<script type="text/javascript">
	$(function(){
		$("#closeEquipmentRepairView").click(function(){
			closeTab("设备维修记录查看");
		});
	});
</script>
</body>
