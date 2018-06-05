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

				<form class="form-horizontal" name="arrayCourseSerachForm">
				<input type="hidden" id="storeNo" value="${storeNo}" name="selectStoreNo">
					<div class="box-body">
						<div class="row pull-down-menu col-md-11">
							<div class="col-md-4">
								<div class="frombg">
									<span>主题馆</span> <select class="form-control" name="storeNo">
										<option value="">请选择</option> <#list listStore as store>
										<option value="${store.storeNo}"<#if store.storeNo =
											storeNo>selected</#if> > ${store.storeName}</option> </#list>
									</select>
								</div>
							</div>

						</div>
						<!-- /.box-body -->
					</div>
					<!--修改-->
					<div class="col-md-12" style='float: right !important;'>
						<div class="box-footer">
							<!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->
							<button type="button"
								class="btn btn-default pull-right btn-flat flatten flatten btnColorA"
								id="arrayCourseSearch"
								style="background: #2b94fd; margin-right: -2px !important">确定</button>
							<button type="reset"
								class="btn btn-default pull-right btn-flat flatten flatten btnDefault"
								style="float: right; margin-right: 20px !important">清空</button>
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
			<script id="arrayCourseBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
			<div class="box">
				<div class="box-body">
				<#if listArrayCourse??&&(listArrayCourse?size>0)>
				<button type="button" id="postedArrayCourse"
						class="btn btn-default pull-right sure btncolora"
						style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd;">
						发布课程</button>
						</#if>
					<button type="button" id="addArrayCourse"
						class="btn btn-default pull-right sure btncolora"
						style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd;">
						排课</button>
					<table id="arrayCourseList" class="table table-hover" width="100%"
						border="1">
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
								<th>周次</th>
							</tr>
						</thead>
						<tbody id="arrayCourseTab" style="text-align: center;">
							<#if listThisArrayCourse??> 
							<#list listThisArrayCourse as course>
							<tr>
								<td>${course.showDate}</td>
								<td onclick="modifiyCoach('${course.monId}')">
									${course.monName!'----'}</td>
								<td onclick="modifiyCoach('${course.tueId}')">
									${course.tueName!'----'}</td>
								<td onclick="modifiyCoach('${course.wedId}')">
									${course.wedName!'----'}</td>
								<td onclick="modifiyCoach('${course.thuId}')">
									${course.thuName!'----'}</td>
								<td onclick="modifiyCoach('${course.friId}')">
									${course.friName!'----'}</td>
								<td onclick="modifiyCoach('${course.satId}')">
									${course.satName!'----'}</td>
								<td onclick="modifiyCoach('${course.sunId}')">
									${course.sunName!'----'}</td>
								<td><#if course.week == 0> 本周 </#if> <#if course.week == 1>
									下周 </#if></td>
							</tr>
							</#list> </#if>
						</tbody>
						<tbody id="arrayCourseTab" style="text-align: center;">
							<#if listNextArrayCourse??> 
							<#list listNextArrayCourse as course>
							<tr>
								<td>${course.showDate}</td>
								<td onclick="modifiyCoach('${course.monId}')">
									${course.monName!'----'}</td>
								<td onclick="modifiyCoach('${course.tueId}')">
									${course.tueName!'----'}</td>
								<td onclick="modifiyCoach('${course.wedId}')">
									${course.wedName!'----'}</td>
								<td onclick="modifiyCoach('${course.thuId}')">
									${course.thuName!'----'}</td>
								<td onclick="modifiyCoach('${course.friId}')">
									${course.friName!'----'}</td>
								<td onclick="modifiyCoach('${course.satId}')">
									${course.satName!'----'}</td>
								<td onclick="modifiyCoach('${course.sunId}')">
									${course.sunName!'----'}</td>
								<td><#if course.week == 0> 本周 </#if> <#if course.week == 1>
									下周 </#if></td>
							</tr>
							</#list> 
							</#if>
						</tbody>
					</table>

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
<div class="modal fade" id="addArrayCourseModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">选择周次</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="arrayCourseForm">
					<input type="hidden" name="storeNo" id="storeNo" value="">
					<div>
						<label for="inputEmail3">周次:</label>
					</div>
					<div class="form-group">
						<div class="col-sm-6">
							<select class="form-control" name="courseType">
								<option value="">请选择</option>
								<option value="0">本周</option>
								<option value="1">下周</option>
							</select>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<button type="button" class="btn btn-default pull-right sure"
						id="addArrayCourseSub">确定</button>
					<button type="button" class="btn btn-default pull-right cancels"
						id="cancelEditBtn">取消</button>
				</div>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<div class="modal fade" id="modifiyCoachDivModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">修改教练</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" name="modifiyCoachForm">
					<input type="hidden" id="arrayCourseNo" name="arrayCourseNo" value="">
					<div>
						<label for="inputEmail3" class="">课程:</label>
					</div>
					<div class="form-group">
						<div class="col-sm-6">
							<input class="form-control" name="chineseName" value="" style="background: #FFFFFF" readonly />
						</div>
					</div>
					<div>
						<label for="inputEmail3" class="">人数:</label>
					</div>
					<div class="form-group">
						<div class="col-sm-6">
							<input class="form-control" name="peopleNumber" value="" readonly="readonly"/>
						</div>
					</div>
					<div>
						<label for="inputEmail3" class="">教练:</label>
					</div>
					<div class="form-group">
						<div class="col-sm-6">
							<input id="coachNo" name="coachNo" type="hidden" value="" /> 
							<input class="form-control" name="coachName" value="" style="background: #FFFFFF" readonly />
							<button type="button" id="selectedCoach" class="btn btn-default pull-left sure btncolora"
								style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd;">
								选择教练</button>
						</div>
					</div>
					
			</div>
			</form>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-right sure"
					id="modifiyCourseBtn">保存</button>
				<button type="button" class="btn btn-default pull-right cancels"
					id="cancelBtn">取消</button>
			</div>
		</div>

	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal-dialog -->
<!-- 教练列表层 -->
  <div class="modal" id="coachModifiyistModal">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   	<form name="modifiyCoachForm">
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
		       <script id="coachBtnTplModifiy" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body">
			         <table id="coachListModifiy" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>
<script type="text/javascript">
$(function(){
	 require.config({paths: {"arrayCourse":"res/js/themepavilion/array_course_list"}});
		require(["arrayCourse"], function (arrayCourse){
			arrayCourse.init();
		});
});
</script>