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
							<label class="col-sm-12 control-label"><h4>状态车位编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-10">
					<form name="parkingEditForm">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;编号：</label>
							<div class="col-sm-6">
							    <input class="form-control val" name="parkingSpaceNo" value="${parkingSpace.parkingSpaceNo}" readonly="true"/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-6">
								<select name="cityId" class="form-control val">
								 <#list cities as citie>
									 <option <#if parkingSpace.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
							</div>
						</div>
						<div class="form-group clearfix col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;场站名称：</label>
							<div class="col-sm-6">
				            	<select name="parkNo" class="form-control val">
								 <#list parks as park>
									 <option <#if parkingSpace.parkNo==park.parkNo>selected</#if> value="${park.parkNo}" >
				                            ${park.parkName}
				                     </option>
				                 </#list>  
								</select>
					       </div>
						</div>
                        <div class="form-group col-md-6">
                            <label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;状态：</label>
                            <div class="col-sm-6">
                                <select name="isAvailable" class="form-control val">
                                    <option value="1" <#if parkingSpace.isAvailable=1>selected="selected"</#if> >启用</option>
                                    <option value="0" <#if parkingSpace.isAvailable=0>selected="selected"</#if> >停用</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;带电桩：</label>
                            <div class="col-sm-6">
                                <select name="hasCharger" class="form-control val">
                                    <option value="1" <#if parkingSpace.hasCharger=1>selected="selected"</#if> >是</option>
                                    <option value="0" <#if parkingSpace.hasCharger=0>selected="selected"</#if> >否</option>
                                </select>
                            </div>
                        </div>
                        <div id="chargerNoXSEdit" <#if parkingSpace.hasCharger=1>style="display:block"</#if>
							 <#if parkingSpace.hasCharger=0>style="display:none"</#if>
                                >
                            <div class="form-group col-md-6">
                                <label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;电桩编号：</label>
                                <div class="col-sm-6">
                                    <input class="form-control val" name="chargerNo" value="${parkingSpace.chargerNo}"/>
                                </div>
                                <div style="margin-top:7px;"><span name="chargerNo"></span></div>
                            </div>
                        </div>
						<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;所属：</label>
							<div class="col-sm-6">
							<label class="control-label val">
								<#if parkingSpace.ownerType=1>
								自用
								<#else>
								租用
								</#if>
								</label>
							</div>
						</div>	
						<div class="form-group col-md-6">
						<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;启用时间</label>
							<div class="col-sm-6">
							<label class="control-label val">
								${parkingSpace.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
							</div>
						</div>	
						<div class="form-group col-md-6">
						<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;停用时间</label>
							<div class="col-sm-6">
							<label class="control-label val">
								${parkingSpace.disabledUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
							</div>
						</div>	

                		<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;创建日期：</label>
							<div class="col-sm-6">
							<label class="control-label val">
							${parkingSpace.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>	
						<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;更新日期：</label>
							<div class="col-sm-6">
							<label class="control-label val">
							${parkingSpace.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>	
						</form>	

					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-7" style="margin-bottom:20px;">
                    <button type="button" id="closeParkingSpace" class="btn btn-default pull-right btnDefault" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                            关闭
                    </button>
                    <button type="button" id="saveParking" class="btn btn-default pull-right btnColorA" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                            保存
                    </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
 $(function(){
       require.config({paths: {"parkingSpaceEdit":"res/js/resource/parkingSpace_edit"}});
		require(["parkingSpaceEdit"], function (parkingSpaceEdit){
			parkingSpaceEdit.init();
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
