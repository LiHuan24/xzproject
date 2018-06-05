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
							<label class="col-sm-4 control-label"><h4>还车区域详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;名称：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							    ${returnArea.areaName}
							    </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-7">
							<label class="control-label val">
								${returnArea.cityName}
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;选点类型：</label>
							<div class="col-sm-7">
							<label class="control-label val">
								<#if isPloygon==1>
									多边形
								<#else>
									圆形
								</#if>
								</label>
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;坐标：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${returnArea.longitude}&nbsp;N&nbsp;&nbsp;&nbsp;&nbsp;
							${returnArea.latitude}&nbsp;E
							</label>
							</div>
						</div>
					
						<#if returnArea.isAvailable==1>	
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;状态：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							 启用
							</label>
							</div>
						</div>
						
						<#else>
							<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;状态：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							 停用
							</label>
							</div>
						</div>
						
						</#if>
						
                		<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;创建日期：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${returnArea.createTime?string('yyyy-MM-dd')}
							</label>
							</div>
						</div>	
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;更新日期：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${returnArea.updateTime?string('yyyy-MM-dd')}
							</label>
							</div>
						</div>
						

					
        		</div><!-- /.row -->
                
                    <div class="col-sm-6" style="margin-bottom:20px;">

                    <button type="button" id="closereturnArea" class="btn btn-default pull-right btncolora" style="width: 95px; height: 32px; line-height: 25px; padding-left: 15px; background: #2b94fd">
                            关闭
                    </button>

                    </div>
                
</div>
<script type="text/javascript">
    $(function () {
      require.config({paths: {"returnArea":"res/js/resource/returnArea_list"}});
		require(["returnArea"], function (returnArea){
			returnArea.init();
		});
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
