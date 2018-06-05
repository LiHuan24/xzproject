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
							<label class="col-sm-12 control-label"><h4>巡检结果新增</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-11">
					<form name="checkResultAddForm">
					    <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;巡检计划号：</label>
							<div class="col-sm-6">
								<input class=" form-control val" name="checkPlanNo" id="checkPlanNos"/>
							</div>
							<div class="col-sm-6"><span name="checkPlanNo"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;计划日期：</label>
							<div class="col-sm-6">
								<input class="datepicker form-control val"  name="planDate" readonly style="background-color:#fff;"/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;巡检开始时间：</label>
							<div class="col-sm-6">
								<input class="datetimepicker form-control val"  name="actualStartTime" readonly style="background-color:#fff;"/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;巡检完成时间：</label>
							<div class="col-sm-6">
								<input class="datetimepicker form-control val" name="actualEndTime" readonly style="background-color:#fff;"/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;巡检场地：</label>
							<div class="col-sm-6">
							    <input type="hidden" name="parkNo"/>
								<input class=" form-control val" name="parkName" readonly style="background-color:#fff;"/>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;巡检项目：</label>
							<div class="col-sm-6">
							<input type="hidden" name="checkItemId"/>
							<input class=" form-control val" name="checkItem" readonly style="background-color:#fff;"/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;设备编号：</label>
							<div class="col-sm-6">
								<input class=" form-control val" name="deviceNo"/>
							</div>
							<div class="col-sm-6"><span name="deviceNo"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;结果：</label>
							<div class="col-sm-6">
								<select name="checkResult" class="form-control val">
									 <option  value="0" >异常</option>
									 <option  value="1" >正常</option>
								</select>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;异常简述：</label>
							<div class="col-sm-6">
								<input class=" form-control val" name="abnormalBrief"/>
							</div>
							<div class="col-sm-6"><span name="abnormalBrief"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;异常详述：</label>
							<div class="col-sm-6">
								 <textarea class="form-control val" name="abnormalDetail"></textarea>
							</div>
							<div class="col-sm-6"><span name="abnormalDetail"></span></div>
						</div>
						</form>	

					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-4" style="margin-bottom:20px;">
                    <button type="button" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" id="closeAddcheckResult" class="btn btn-default pull-right btncolorb" >
                            关闭
                    </button>
                    <button type="button" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" id="savecheckResult" class="btn btn-default pull-right btncolora" >
                             保存
                    </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"checkResultAdd":"res/js/resource/checkResult_add"}});
		require(["checkResultAdd"], function (checkResultAdd){
			checkResultAdd.init();
		});  
	});    
      $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: "linked",//今日按钮
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
