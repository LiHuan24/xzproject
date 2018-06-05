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
        /*line-height: 15px;*/
    }
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-12 control-label"><h4>巡检计划编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="checkPlanEditForm">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;编号：</label>
							<div class="col-sm-8">
							    <input class="form-control val" name="checkPlanNo" value="${checkPlan.checkPlanNo}"  readonly/>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-8">
								<select name="cityId" class="form-control val">
								 <#list cities as citie>
									 <option  <#if checkPlan.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>
								</select>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;计划日期：</label>
							<div class="col-sm-8">
								<input class="datepicker form-control val" name="planDate" value="${checkPlan.planDate?string('yyyy-MM-dd')}"  readonly/>
							</div>
							<div style="margin-top:7px;"><span name="planDate"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;计划巡检场站：</label>
							<div class="col-sm-5">
								<input class="form-control val" name="parkName" id="parkNames" value="${checkPlan.parkName}" readonly/>
							</div>
							<input type="hidden" name="parkNo" id="parkNos" value="${checkPlan.parkNo}"/>
							<button type="button" class="btn btn-default" id="checkAddParkBtn">场站列表</button>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;计划巡检项：</label>
							<div class="col-sm-8 val">
							<#list items as item>
							<label class="checkbox-inline">
		                    <input type="checkbox"     class="butcheck" value="${item.dataDictItemId}"><span>${item.itemValue}</span>
							</label>
				            </#list>
							</div>
							 <input type="hidden" name="checkItemId" id="checkItemId" value="${checkPlan.checkItemId}">
							 <input type="hidden" name="checkItem" id="checkItem" value="${checkPlan.checkItem}">
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;计划巡检人员：</label>
							<div class="col-sm-5">
							<input class="form-control val" name="workerName" id="workerName"  value="${checkPlan.workerName}" readonly/>
							</div>
							<button type="button" class="btn btn-default" id="checkAddUserBtn">人员列表</button>
							<input type="hidden" name="workerId" id="workerIds"  value="${checkPlan.workerId}" >
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;实际开始时间：</label>
							<div class="col-sm-8 val">
							    <input class="datetimepicker form-control val" name="actualStartTime" value="${checkPlan.actualStartTime?string('yyyy-MM-dd HH:mm:ss')}"  readonly/>
							</div>
							<div style="margin-top:7px;"><span name="actualStartTime"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;实际完成时间：</label>
							<div class="col-sm-8">
							    <input class="datetimepicker form-control val" name="actualEndTime" value="${checkPlan.actualEndTime?string('yyyy-MM-dd HH:mm:ss')}" id="actualEndTime"  readonly/>
							</div>
							<div style="margin-top:7px;"><span name="actualEndTime"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;状态：</label>
							<div class="col-sm-8">
							   <select name="planStatus" class="form-control val" id="planStatus" readonly>
									 <option  <#if checkPlan.planStatus==0>selected</#if> value="1" >待处理</option>
									 <option  <#if checkPlan.planStatus==1>selected</#if> value="2" >处理中</option>
									 <option  <#if checkPlan.planStatus==2>selected</#if> value="3" >已完成</option>
									 <option  <#if checkPlan.planStatus==3>selected</#if> value="4" >已取消</option>
								</select>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;巡检结果：</label>
							<div class="col-sm-8">
							<label class=" control-label val">
							   <a class="checkPlan-result-detail" data-url="/checkResult/toCheckResult.do?checkPlanNo=${checkPlan.checkPlanNo}">巡检结果</a>
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;创建时间：</label>
							<div class="col-sm-8">
							<label class="control-label val">
							${checkPlan.createTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;更新时间：</label>
							<div class="col-sm-8">
							<label class="control-label val">
							${checkPlan.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>
					</form>

					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-7" style="margin-bottom:20px;">
                    <button type="button" id="closeEditCheckPlan" class="btn btn-default pull-right btnDefault" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                            关闭
                    </button>
                    <button type="button" id="editCheckPlan" class="btn btn-default pull-right btnColorA" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                            保存
                    </button>

                    </div>
                </div>
</div>
<div class="modal fade" id="checkParkModal" tabindex="-1" role="dialog">
	<div class="modal-dialog">
	   	<div class="modal-content">
		       <!--定义操作列按钮模板-->
		       <script id="checkParkBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body">
			         <table id="parkLists" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade" id="checkUserModal">
	<div class="modal-dialog">
	   	<div class="modal-content">
		       <!--定义操作列按钮模板-->
		       <script id="checkWorkerBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body">
			         <table id="checkWorkerLists" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"checkPlanEdit":"res/js/resource/checkPlan_edit"}});
		require(["checkPlanEdit"], function (checkPlanEdit){
			checkPlanEdit.init();
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
