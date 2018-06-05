<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-12 control-label"><h4>巡检结果详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-10 form-horizontal">
					    <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检计划号：</label>
							<div class="col-sm-7">
							   <label class="control-label">
								${checkPlan.checkPlanNo}
								</label>
							</div>
							
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;计划日期：</label>
							<div class="col-sm-7">
							 <label class="control-label">
							${checkPlan.planDate?string('yyyy-MM-dd')}
							 </label>
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检开始时间：</label>
							<div class="col-sm-7">
							  <label class="control-label">
								${checkPlan.actualStartTime?string('yyyy-MM-dd HH:mm:ss')}
							  </label>
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检完成时间：</label>
							<div class="col-sm-7">
							   <label class="control-label">
								${checkPlan.actualEndTime?string('yyyy-MM-dd HH:mm:ss')}
							    </label>
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检场地：</label>
							<div class="col-sm-7">
							   <label class="control-label">
								${checkPlan.parkName}
								</label>
							</div>
						</div>
						
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检项目：</label>
							<div class="col-sm-7">
							  <label class="control-label">
								${checkPlan.checkItem}
								</label>
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;设备编号：</label>
							<div class="col-sm-7">
							  <label class="control-label">
								${checkResult.deviceNo}
								</label>
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;结果：</label>
							<div class="col-sm-7">
							 <label class="control-label">
							<#if checkResult.checkResult==0>
							异常
							<#else>
							正常
							</#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;异常简述：</label>
							<div class="col-sm-7">
							  <label class="control-label">
								${checkResult.abnormalBrief}
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;异常详述：</label>
							<div class="col-sm-7">
							   <label class="control-label">
								${checkResult.abnormalDetail}
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;创建日期：</label>
							<div class="col-sm-7">
							  <label class="control-label">
								${checkResult.createTime?string('yyyy-MM-dd HH:mm:ss')}
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;更新日期：</label>
							<div class="col-sm-7">
							 <label class="control-label">
								${checkResult.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							 </label>
							</div>
						</div>

					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <button type="button" id="closeViewcheckResult" class="btn btn-default pull-right btncolora" >
                            <i class="glyphicon glyphicon-remove"></i>关闭
                    </button>

                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"checkResultEdit":"res/js/resource/checkResult_edit"}});
		require(["checkResultEdit"], function (checkResultEdit){
			checkResultEdit.init();
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
