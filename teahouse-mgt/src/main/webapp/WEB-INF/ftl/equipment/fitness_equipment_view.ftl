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
							<label class="col-sm-12 control-label"><h4>健身设备详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="auxiliaryEquipmentViewForm">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;所属城市：</label>
							<div class="col-sm-6">
							    ${fitnessEquipment.cityName}
							</div>
							<div class="col-sm-6"><span name="cityAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;所属门店：</label>
							<div class="col-sm-6">
							    ${fitnessEquipment.storeName}
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;设备品牌：</label>
							<div class="col-sm-6">
							    ${fitnessEquipment.brandName}
							</div>
							<div class="col-sm-6"><span name="brandNoAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;设备型号：</label>
							<div class="col-sm-6">
							    ${fitnessEquipment.modelNo}
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;设备类型：</label>
							<div class="col-sm-6">
							    ${fitnessEquipment.sortName}
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;&nbsp;备注：</label>
							<div class="col-sm-6">
								 <textarea class="form-control val" name="memo" style="width: 250px;height: 100px;">${fitnessEquipment.memo}</textarea>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;设备二维码：</label>
							<div class="col-sm-6">
							    ${fitnessEquipment.devId}
							</div>
						</div>
						</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeFitnessEquipmentView" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                    </div>
                </div>
</div>

<script type="text/javascript">
	$(function(){
		$("#closeFitnessEquipmentView").click(function(){
			closeTab("健身设备详情");
		});
	});
</script>
</body>
