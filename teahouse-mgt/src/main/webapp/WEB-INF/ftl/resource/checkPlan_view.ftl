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
							<label class="col-sm-12 control-label"><h4>巡检计划详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;编号：</label>
							<div class="col-sm-6">
							  <label class=" control-label val">
							   ${checkPlan.checkPlanNo}
							   </label>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-6">
							 <label class=" control-label val">
				               ${checkPlan.cityName}
				               </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;计划日期：</label>
							<div class="col-sm-6">
							 <label class=" control-label val">
								${checkPlan.planDate?string('yyyy-MM-dd')}
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;计划巡检场站：</label>
							<div class="col-sm-6">
							 <label class=" control-label val">
							${checkPlan.parkName}
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;计划巡检项：</label>
							<div class="col-sm-6">
							 <label class=" control-label val">
							${checkPlan.checkItem}
							</label>
							</div>
						</div>	
						 <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;计划巡检人员：</label>
							<div class="col-sm-6">
							 <label class=" control-label val">
							${checkPlan.workerName}
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;实际开始时间：</label>
							<div class="col-sm-6">
							 <label class=" control-label val">
							  ${checkPlan.actualStartTime?string('yyyy-MM-dd HH:mm:ss')}
							  </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;实际完成时间：</label>
							<div class="col-sm-6">
							 <label class=" control-label val">
							    ${checkPlan.actualEndTime?string('yyyy-MM-dd HH:mm:ss')}
							  </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;状态：</label>
							<div class="col-sm-6">
							 <label class=" control-label val">
									 <#if checkPlan.planStatus==1>待处理</#if>
									 <#if checkPlan.planStatus==2>处理中</#if> 
									 <#if checkPlan.planStatus==3>已完成</#if> 
									 <#if checkPlan.planStatus==4>已取消</#if>  
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;巡检结果：</label>
							<div class="col-sm-6">
							 <label class=" control-label val">
							        <a class="checkPlan-result-detail" data-url="/checkResult/toCheckResult.do?checkPlanNo=${checkPlan.checkPlanNo}">巡检结果</a>
							  </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
							<div class="col-sm-6">
							 <label class=" control-label val">
							${checkPlan.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;更新时间：</label>
							<div class="col-sm-6">
							  <label class=" control-label val">
							${checkPlan.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>

					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closecheckPlan" class="btn btn-default pull-right btncolora" style="width: 95px; height: 32px; line-height: 25px; padding-left: 15px; background: #2b94fd">
                            关闭
                    </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"checkPlan":"res/js/resource/checkPlan_list"}});
		require(["checkPlan"], function (checkPlan){
			checkPlan.init();
		});  
	});    
      $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
         $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
