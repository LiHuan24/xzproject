<meta charset="utf-8">
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
							<label class="col-sm-12 control-label"><h4>车位详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-10">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;编号：</label>
							<div class="col-sm-6">
							<label class="control-label val">
							    ${parkingSpace.parkingSpaceNo}
							    </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-6">
							<label class="control-label val">
								   ${parkingSpace.cityName}
								   </label>
							</div>
						</div>
						<div class="form-group clearfix col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;场站名称：</label>
							<div class="col-sm-6">
							<label class="control-label val">
				            	${park.parkName}
				            	</label>
					        </div>
						</div>
						<div class="form-group clearfix col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;所属：</label>
							<div class="col-sm-6">
							<label class="control-label val">
				            	<#if park.ownerType=1>
							      自有
							 <#elseif park.ownerType=2>
							     租用
							</#if>
				            	</label>
					        </div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;状态：</label>
							<div class="col-sm-6">
							<label class="control-label val">
							<#if parkingSpace.isAvailable=1>
							       启用
							 <#else>
							     停用 
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;带电桩：</label>
							<div class="col-sm-6">
							<label class="control-label val">
							<#if parkingSpace.hasCharger=1>
							     是
							 <#else>
							     否
							</#if>
							</label>
							</div>
						</div>	
						<div class="form-group col-md-6">
						<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;启用时间</label>
							<div class="col-sm-6">
							<label class="control-label val">
								${parkingSpace.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
							</div>
						</div>	
						<div class="form-group col-md-6">
						<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;停用时间</label>
							<div class="col-sm-6">
							<label class="control-label val">
								${parkingSpace.disabledUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
							</div>
						</div>	
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;电桩编号：</label>
							<div class="col-sm-6">
							<label class="control-label val">
							${parkingSpace.chargerNo}
							</label>
							</div>
						</div>																								
                		<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;创建日期：</label>
							<div class="col-sm-6">
							<label class="control-label val">
							${parkingSpace.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>	
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;更新日期：</label>
							<div class="col-sm-6">
							<label class="control-label val">
							${parkingSpace.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>	

					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeParking" class="btn btn-default pull-right btncolora" style="width: 95px; height: 32px; line-height: 25px; padding-left: 15px; background: #2b94fd">
                            关闭
                    </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
 $(function(){
       require.config({paths: {"parkingSpace":"res/js/resource/parkingSpace_list"}});
		require(["parkingSpace"], function (parkingSpace){
			parkingSpace.init();
		});  
		});
</script>
<script type="text/javascript">
    $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
