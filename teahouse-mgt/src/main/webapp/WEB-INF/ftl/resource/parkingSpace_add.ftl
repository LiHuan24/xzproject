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
							<label class="col-sm-12 control-label key"><h4>车位新增</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-10">
					<form name="parkingAddForm">
						<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-7">
								<select name="cityId" id="cityId" class="form-control val">
								 <#list cities as citie>
									 <option  value="${citie.dataDictItemId}"  <#if cityId == citie.dataDictItemId> selected="selected"</#if>>
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
							</div>
						</div>
						<div class="form-group clearfix col-md-6">
							<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;场站名称：</label>
							<div class="col-sm-7">
				            	<select name="parkNo" id="parkNode" class="form-control val">
								 <#list parks as park>
									 <option  value="${park.parkNo}" <#if parkNo == park.parkNo> selected="selected"</#if>>
				                            ${park.parkName}
				                     </option>
				                 </#list>  
								</select>
					       </div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;带电桩：</label>
							<div class="col-sm-7">
								<select name="hasCharger" class="form-control val">
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</div>
						</div>
						<div id="chargerNoXS">	
						<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;电桩编号：</label>
							<div class="col-sm-7">
								<input class="form-control val" name="chargerNo" value="${parkingSpace.chargerNo}"/>
							</div>
							<div style="margin-top:7px;"><span name="chargerNo"></span></div>
						</div>																								
                		</div>
						</form>	

					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="addcloseParkingSpace" class="btn btn-default pull-right btncolorb" >
                            关闭
                    </button>
                    <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="addParking" class="btn btn-default pull-right btncolora" >
                            保存
                    </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
$(function(){
       require.config({paths: {"parkingSpaceAdd":"res/js/resource/parkingSpace_add"}});
		require(["parkingSpaceAdd"], function (parkingSpaceAdd){
			parkingSpaceAdd.init();
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
