<meta charset="utf-8">
<link rel="stylesheet"
	href="../res/dep/jstree/themes/default/style.min.css" />
<style>
@media only screen and (max-width:992px) {
	.pull-down-menu input, .pull-down-menu select {
		width: 100% !important;
	}
	.frombg .form-control {
		width: 45% !important;
		margin-right: 20%;
	}
	.pull-down-menu span {
		width: 20%;
	}
}

@media only screen and (max-width:768px) {
	.row .sorting_disabled {
		font-size: 1.1rem !important;
	}
}

@media only screen and (min-width:768px) and (max-width:1024px) {
	.row .sorting_disabled {
		font-size: 1.2rem !important;
	}
}

@media only screen and (min-width:1024px) and (max-width:1366px) {
	.row .sorting_disabled {
		font-size: 1.3rem !important;
	}
}

table {
	border: 1px solid rgba(127, 127, 127, 0.05);
}
</style>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12 pd10">
			<div class="box box-default">
				<div class="with-border">
					<#--
					<div class="box-tools pull-right">
						<button type="button" class="btn btn-box-tool"
							data-widget="collapse">
							<i class="fa fa-minus"></i>
						</button>
					</div>
					<!-- /.box-tools &ndash;&gt;-->
				</div>
				<!-- /.box-header -->

				<form class="form-horizontal" name="templateCourseSerachForm">
					<input type="hidden" id="storeNo" value="${storeNo}" name="selectStoreNo">
					<div class="box-body">
						<div class="row pull-down-menu col-md-11">
							<div class="col-md-4">
								<div class="frombg">
									<span>主题馆</span> 
									<select class="form-control" name="storeNo">
										<option value="">请选择</option> 
										<#list listStore as store>
										<option value="${store.storeNo}"<#if store.storeNo = storeNo>selected</#if> > ${store.storeName}</option> 
										</#list>
									</select>
								</div>
							</div>

						</div>
						<!-- /.box-body -->
					</div>
					<div class="col-md-12" style='float: right !important;'>
						<div class="box-footer">
							<!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->
							<button type="button"
								class="btn btn-default pull-right btn-flat flatten flatten btnColorA"
								id="queryTemPlateCourse"
								style="background: #2b94fd; margin-right: -2px !important">查询模板</button>
						</div>
						<!-- /.box-footer -->
					</div>
				</form>
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	<div class="row" style="width: 100%; height: 2px; background: #f1f5f8"></div>
	<div class="row">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="templateCourseBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
			<div class="box">
				<div class="box-body">
					<table id="templateCourseList" class="table table-hover"
						width="100%" border="1">
						<thead>
							<tr>
								<th>时间</th>
								<th>周一</th>
								<th>周二</th>
								<th>周三</th>
								<th>周四</th>
								<th>周五</th>
								<th>周六</th>
								<th>周日</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody id="testMs" style="text-align: center;">
						<#if listTemplate??>
							<#list listTemplate as course>
								<tr>
									<td onclick="queryDateDiv('${course.dateId}');" class="dataDiv">
										<input type="text" value="${course.showDate}" style="border:0;outline:none;padding-left:20%;"/>
									</td>
									<td onclick="queryCourseAndCoach('1','${course.monId}');">
										<input type="text" value="${course.monName!'----'}" style="border:0;outline:none;padding-left:20%;"/>
									</td>
									<td onclick="queryCourseAndCoach('2','${course.tueId}');">
										<input type="text" value="${course.tueName!'----'}" style="border:0;outline:none;padding-left:20%;"/>
									</td>
									<td onclick="queryCourseAndCoach('3','${course.wedId}');">
										<input type="text" value="${course.wedName!'----'}" style="border:0;outline:none;padding-left:20%;"/>
									</td>
									<td onclick="queryCourseAndCoach('4','${course.thuId}');">
										<input type="text" value="${course.thuName!'----'}" style="border:0;outline:none;padding-left:20%;"/>
									</td>
									<td onclick="queryCourseAndCoach('5','${course.friId}');">
										<input type="text" value="${course.friName!'----'}" style="border:0;outline:none;padding-left:20%;"/>
									</td>
									<td onclick="queryCourseAndCoach('6','${course.satId}');">
										<input type="text" value="${course.satName!'----'}" style="border:0;outline:none;padding-left:20%;"/>
									</td>
									<td onclick="queryCourseAndCoach('7','${course.sunId}');">
										<input type="text" value="${course.sunName!'----'}" style="border:0;outline:none;padding-left:20%;"/>
									</td>
									<td  id="delTemplate" onclick="delTemplate(this);">
										删除
									</td>
								</tr>
							</#list>
						</#if>
						</tbody>
					</table>
					<button type="button" id="addQueryTemplateTr"
						class="btn btn-default pull-left sure btncolora"
						style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd;">
						新增行</button>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.container-fluid -->
<!-- 选择时间层 -->
<div class="modal fade"  id="queryDateDivModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择时间</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="dateForm"> 
                  <input type="hidden" id="storeNo" name="storeNo" value=""/>
                  <input type="hidden" id="ftlRow" name="ftlRow" value=""/>
			         <div>
			            <label for="inputEmail3" class=" control-label reason">起始时间:</label>
			         </div> 
			          <div class="form-group">
                          <div class="col-sm-6">
                               <input class="datetimepicker form-control" name="courseStart" style="background:#FFFFFF" readonly/>
                          </div>
                      </div>
                      <div>
			            <label for="inputEmail3" class=" control-label reason">结束时间:</label>
			         </div> 
			          <div class="form-group">
                         <div class="col-sm-6">
                              <input class="datetimepicker form-control" name="courseEnd" style="background:#FFFFFF" readonly/>
                         </div>
                      </div>
			     </div>
			     </form> 
			      <div class="modal-footer">
                     <button type="button" class="btn btn-default pull-right sure" id="selectQueryDateSub" >确定</button>
                     <button type="button" class="btn btn-default pull-right cancels"  id="onQueryCancelBtn">取消</button>
                </div> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- 编辑排课时间弹出层 -->
    <div class="modal fade"  id="editQueryDateDivModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">选择时间</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="editDateForm"> 
                  <input type="hidden" id="ftlRow" name="ftlRow" value=""/>
                  <input type="hidden" id="templateCourseNo" name="templateCourseNo" value=""/>
			         <div>
			            <label for="inputEmail3" class=" control-label reason">起始时间:</label>
			         </div> 
			          <div class="form-group">
                          <div class="col-sm-6">
                               <input class="datetimepicker1 form-control" name="courseStart" value=""  style="background:#FFFFFF" readonly/>
                          </div>
                      </div>
                      <div>
			            <label for="inputEmail3" class=" control-label reason">结束时间:</label>
			         </div> 
			          <div class="form-group">
                         <div class="col-sm-6">
                              <input class="datetimepicker1 form-control" name="courseEnd" value=""  style="background:#FFFFFF" readonly/>
                         </div>
                      </div>
			     </div>
			     </form> 
			      <div class="modal-footer">
                     <button type="button" class="btn btn-default pull-right sure" id="editQueryDateSub" >确定</button>
                     <button type="button" class="btn btn-default pull-right cancels"  id="cancelQueryEditBtn">取消</button>
                </div> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- 编辑课程和教练、人数谈弹出层 -->
        <div class="modal fade"  id="editQueryCourseAndCoachModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">添加课程、教练和人数</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="editCourseAndCoachForm"> 
                  <input type="hidden" id="ftlRow" name="ftlRow" value=""/>
                  <input type="hidden" id="courseWeek" name="courseWeek" value="">
                  <input type="hidden" id="templateCourseNo" name="templateCourseNo" value="">
                  
			         <div>
			            <label for="inputEmail3" class="">课程:</label>
			         </div> 
			         <div class="form-group">
                       <div class="col-sm-6">
                      	<input id="courseNo" name="courseNo" type="hidden" value=""/>    
                          <input class="form-control" name="chineseName" value="" style="background:#FFFFFF" readonly/>
                             <button type="button" id="selectQueryEditCourse" class="btn btn-default pull-left sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd;">
						选择课程
						</button>
                          </div>
                      </div>
                      <div>
			            <label for="inputEmail3" class="">教练:</label>
			         </div> 
			          <div class="form-group">
                         <div class="col-sm-6">
                         <input id="coachNo" name="coachNo" type="hidden" value=""/>  
                              <input class="form-control" name="coachName" value="" style="background:#FFFFFF" readonly/>
                              <button type="button" id="selectQueryEditCoach" class="btn btn-default pull-left sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd;">
								选择教练
								</button>
                         </div>
                      </div>
                      <div>
			            <label for="inputEmail3" class="">人数:</label>
			         </div> 
			          <div class="form-group">
                         <div class="col-sm-6">
                              <input class="form-control" name="peopleNumber" value=""/>
                         </div>
                      </div>
			     </div>
			     </form> 
			      <div class="modal-footer">
                    	<button type="button" class="btn btn-default pull-right sure" id="editQueryCourseBtn">提交</button>
                     	<button type="button" class="btn btn-default pull-right cancels"  id="cancelQueryEditCourseBtn">取消</button>
                </div> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- 排课选择课程和教练层 -->
    <div class="modal fade"  id="querycourseAndCoachModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">添加课程、教练和人数</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="courseAndCoachForm"> 
                  <input type="hidden" id="ftlRow" name="ftlRow" value=""/>
                  <input type="hidden" id="courseWeek" name="courseWeek" value="">
                  <input type="hidden" id="storeNo" name="storeNo" value="">
			         <div>
			            <label for="inputEmail3" class="">课程:</label>
			         </div> 
			         <div class="form-group">
                       <div class="col-sm-6">
                      	<input id="courseNo" name="courseNo" type="hidden" value=""/>    
                          <input class="form-control" name="chineseName" style="background:#FFFFFF" readonly/>
                             <button type="button" id="selectQueryCourse" class="btn btn-default pull-left sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd;">
						选择课程
						</button>
                          </div>
                      </div>
                      <div>
			            <label for="inputEmail3" class="">教练:</label>
			         </div> 
			          <div class="form-group">
                         <div class="col-sm-6">
                         <input id="coachNo" name="coachNo" type="hidden" value=""/>  
                              <input class="form-control" name="coachName" style="background:#FFFFFF" readonly/>
                              <button type="button" id="selectQueryCoach" class="btn btn-default pull-left sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd;">
								选择教练
								</button>
                         </div>
                      </div>
                      <div>
			            <label for="inputEmail3" class="">人数:</label>
			         </div> 
			          <div class="form-group">
                         <div class="col-sm-6">
                              <input class="form-control" name="peopleNumber" />
                         </div>
                      </div>
			     </div>
			     </form> 
			      <div class="modal-footer">
                    	<button type="button" class="btn btn-default pull-right sure" id="onQueryFormBtn">提交</button>
                     	<button type="button" class="btn btn-default pull-right cancels"  id="cancelQueryCourseBtn">取消</button>
                </div> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
  <!-- 课程列表层 -->
  <div class="modal" id="queryCourselistModal">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   	<form name="courseAndCoachForm">
		      <div class="with-border">
		       <div class="box-tools pull-right">
		       </div><!-- /.box-tools -->
		          <div class="row pull-down-menu-car col-md-11" style="margin-left: 1px; background: #f1f5f8;width: 748px;">
		          </div>
		       </div><!-- /.box-header -->
         		<!--修改-->
        	</form>
	       <div class="col-xs-14">
		       <!--定义操作列按钮模板-->
		       <script id="courseBtnTplAdd" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body">
			         <table id="courseQueryListAdd" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>
<!-- 教练列表层 -->
  <div class="modal" id="coachQuerylistModal">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   	<form name="courseAndCoachForm">
		      <div class="with-border">
		       <div class="box-tools pull-right">
		       </div><!-- /.box-tools -->
		          <div class="row pull-down-menu-car col-md-11" style="margin-left: 1px; background: #f1f5f8;width: 748px;">
		          </div>
		       </div><!-- /.box-header -->
         		<!--修改-->
        	</form>
	       <div class="col-xs-14">
		       <!--定义操作列按钮模板-->
		       <script id="coachBtnTplAdd" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body">
			         <table id="coachQueryListAdd" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>



<!-- 编辑 课程列表层-->
<div class="modal" id="courseQueryEditListModal">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   	<form name="editCourseForm">
		      <div class="with-border">
		       <div class="box-tools pull-right">
		       </div><!-- /.box-tools -->
		          <div class="row pull-down-menu-car col-md-11" style="margin-left: 1px; background: #f1f5f8;width: 748px;">
		          </div>
		       </div><!-- /.box-header -->
         		<!--修改-->
        	</form>
	       <div class="col-xs-14">
		       <!--定义操作列按钮模板-->
		       <script id="courseBtnTplEditQuery" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body">
			         <table id="courseListEditQuery" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>

<!-- 教练列表层 -->
  <div class="modal" id="querycoachEditListModal">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   	<form name="editCoachForm">
		      <div class="with-border">
		       <div class="box-tools pull-right">
		       </div><!-- /.box-tools -->
		          <div class="row pull-down-menu-car col-md-11" style="margin-left: 1px; background: #f1f5f8;width: 748px;">
		          </div>
		       </div><!-- /.box-header -->
         		<!--修改-->
        	</form>
	       <div class="col-xs-14">
		       <!--定义操作列按钮模板-->
		       <script id="coachBtnTplEdit" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body">
			         <table id="coachQueryListEdit" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>
<script type="text/javascript">
$(function(){
	 require.config({paths: {"templateCourseQuery":"res/js/themepavilion/template_course_query_list"}});
		require(["templateCourseQuery"], function (templateCourseQuery){
			templateCourseQuery.init();
		});
		//console.info($("#mainConsoleTabs").tabs("getSelected"));
});
$(function () {
	$('.datetimepicker').datetimepicker({
		format: "hh:ii",
		language:  'fr',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 1,
        minView: 0,
        maxView: 1,
        forceParse: 0
	});
});

$(function () {
	$('.datetimepicker1').datetimepicker({
		format: "hh:ii",
		language:  'fr',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 1,
        minView: 0,
        maxView: 1,
        forceParse: 0
	});
});
</script>