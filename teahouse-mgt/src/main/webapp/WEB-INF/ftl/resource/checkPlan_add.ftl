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
							<label class="col-sm-12 control-label"><h4>巡检计划新增</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="checkPlanAddForm">
						<div class="form-group col-md-12">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-5">
								<select name="cityId" class="form-control val">
								 <#list cities as citie>
									 <option  value="${citie.dataDictItemId}" >
				                            ${citie.itemValue}
				                     </option>
				                 </#list>  
								</select>
							</div>
						</div>
						<div class="form-group col-md-12">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;计划日期：</label>
							<div class="col-sm-5">
								<input class="datepicker form-control val" name="planDate" readonly style="background:#ffffff;"/>
							</div>
							<div class="col-sm-5"><span name="planDate"></span></div>
						</div>
						<div class="form-group col-md-12">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;计划巡检项：</label>
							<div class="col-sm-5 val">
							<#list items as item>
							<label class="checkbox-inline">
		                    <input type="checkbox"     class="butcheck" value="${item.dataDictItemId}"><span>${item.itemValue}</span>
							</label>	
				            </#list> <span name="checkItemId"></span>
							</div>
							 <input type="hidden" name="checkItemId" id="checkItemId">
							 <input type="hidden" name="checkItem" id="checkItem">
							 
						</div>	
						<div class="form-group col-md-12">
							 <label class="col-sm-6 control-label key">&nbsp;&nbsp;</label>
							<div class="col-sm-5"> &nbsp;&nbsp;
							</div> 
						</div>	
						<div class="form-group col-md-12">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;计划巡检场站：</label>
							<div class="col-sm-4">
								<input class="form-control val" name="parkName" id="parkNames" readonly style="background:#ffffff;"/>
							</div>
							<input type="hidden" name="parkNo" id="parkNos"/> 
							<button type="button" class="btn btn-default" id="checkAddParkBtn">场站列表</button>
							<div class="col-sm-5"><span name="parkName"></span></div>
						</div>
						<div class="form-group col-md-12">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;计划巡检人员：</label>
							<div class="col-sm-4">
							<input class="form-control val" name="workerName" id="workerName" readonly style="background:#ffffff;"/>
							</div>
							<button type="button" class="btn btn-default" id="checkAddUserBtn">人员列表</button>
							<input type="hidden" name="workerId" id="workerId">
							<div style="margin-top:7px;"><span name="workerName"></span></div>
						</div>
					</form>

					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                     <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" id="closeAddCheckPlan" class="btn btn-default pull-right btncolorb">
                        关闭
                      </button>
                      <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" id="saveCheckPlan" class="btn btn-default pull-right btncolora">
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
	  require.config({paths: {"checkPlanAdd":"res/js/resource/checkPlan_add"}});
		require(["checkPlanAdd"], function (checkPlanAdd){
			checkPlanAdd.init();
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
    });
</script>
