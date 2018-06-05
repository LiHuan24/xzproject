<meta charset="utf-8">
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-12 control-label"><h4>巡检结果编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-10 form-horizontal">
					<form name="checkResultEditForm" class="form-horizontal">
					    <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检计划号：</label>
							<input type="hidden" name="checkResultId" value="${checkResult.checkResultId}"/>
							<div class="col-sm-7">
								<input class=" form-control" name="checkPlanNo" value="${checkPlan.checkPlanNo}" readonly/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;计划日期：</label>
							<div class="col-sm-7">
								<input class="datepicker form-control" name="planDate" value="${checkPlan.planDate?string('yyyy-MM-dd')}" readonly/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检开始时间：</label>
							<div class="col-sm-7">
								<input class="datetimepicker form-control" name="actualStartTime" value="${checkPlan.actualStartTime?string('yyyy-MM-dd HH:mm:ss')}" readonly/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检完成时间：</label>
							<div class="col-sm-7">
								<input class="datetimepicker form-control" name="actualEndTime" value="${checkPlan.actualEndTime?string('yyyy-MM-dd HH:mm:ss')}" readonly/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检场地：</label>
							<div class="col-sm-7">
								<input class=" form-control" name="parkName" value="${checkPlan.parkName}" readonly/>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;巡检项目：</label>
							<div class="col-sm-4">
								<input class=" form-control" name="checkItem" value="${checkPlan.checkItem}" readonly/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;设备编号：</label>
							<div class="col-sm-7">
								<input class=" form-control" name="deviceNo" value="${checkResult.deviceNo}"/>
							</div>
							<div style="margin-top:7px;"><span name="deviceNo"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;结果：</label>
							<div class="col-sm-7">
								<select name="checkResult" class="form-control">
									 <option <#if checkResult.checkResult==0>selected</#if> value="0" >异常</option>
									 <option <#if checkResult.checkResult==1>selected</#if> value="1" >正常</option>
								</select>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;异常简述：</label>
							<div class="col-sm-7">
								<input class=" form-control" name="abnormalBrief" value="${checkResult.abnormalBrief}"/>
							</div>
							<div style="margin-top:7px;"><span name="abnormalBrief"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span>*</span>&nbsp;&nbsp;异常详述：</label>
							<div class="col-sm-7">
								 <textarea class="form-control" name="abnormalDetail">${checkResult.abnormalDetail}</textarea>
							</div>
							<div style="margin-top:7px;"><span name="abnormalDetail"></span></div>
						</div>
						</form>	
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
                    <button type="button" id="closeEditcheckResult" class="btn btn-default pull-right btnDefault" >
                            <i class="glyphicon glyphicon-remove"></i>关闭
                    </button>
                    <button type="button" id="editcheckResult" class="btn btn-default pull-right btnColorA" >
                            <i class="glyphicon glyphicon-check"></i>保存
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
