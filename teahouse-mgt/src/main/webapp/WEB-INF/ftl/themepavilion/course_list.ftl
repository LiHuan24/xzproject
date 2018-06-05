<meta charset="utf-8">
<link rel="stylesheet" href="../res/dep/jstree/themes/default/style.min.css" />
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
			<h4 class="box-title">查询</h4>

			<hr>
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

				<form class="form-horizontal" name="courseSerachForm">
					<div class="box-body">
						<div class="row pull-down-menu col-md-11">
							<div class="col-md-4">
								<div class="frombg">
									<span>课程分类</span>
									 <select class="form-control" name="courseSortNo">
				                      <option value="">全部</option>
				                      <#list listCourseSort as courseSort>
				                      <option value="${courseSort.courseSortNo}">${courseSort.sortName}</option>
				                      </#list>
				                 </select>
								</div>
							</div>
							<div class="col-md-4">
				            <div class="frombg">
				                <span>启用状态</span>
				                <select class="form-control" name="isEnable">
				                      <option value="">全部</option>
				                      <option value="1">启用</option>
				                      <option value="0">停用</option>
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
								id="courseSearch"
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
			<script id="courseBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
			<div class="box">
				<div class="box-body">
					<table id="courseList" class="table table-hover"
						width="100%" border="1">
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
<!-- 课程启用 -->
<div class="modal fade"  id="onCourseModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="onCourseForm"> 
			      <input type="hidden"  name="courseNo" id="courseNo1">
			      <input type="hidden"  name="isEnable" value="1">
			       <label for="inputEmail3" class=" control-label wen" id="onCourseMemo"></label>
			         <div>
			            <label for="inputEmail3" class=" control-label reason">备注:</label>
			         </div> 
			          <div class="form-group">
                            <div class="col-sm-8">
                                 <textarea class="form-control textareas"   name="memo"></textarea>
                            </div>
                      </div>
			     </div>
			     </form> 
			      <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="courseOnFormBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="courseOnCancelBtn">取消</button>
                </div> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     <!-- 课程停用 -->
    <div class="modal fade" id="offCourseModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="offCourseForm"> 
				      <input type="hidden"  name="courseNo" id="courseNo2">
				      <input type="hidden"  name="isEnable" value="0">
				        <label for="inputEmail3" class=" control-label wen" id="offCourseMemo"></label>
				         <div>
				            <label for="inputEmail3" class=" control-label reason">备注:</label>
				         </div> 
				          <div class="form-group">
	                            <div class="col-sm-8">
	                                 <textarea class="form-control textareas"   name="memo"></textarea>
	                            </div>
	                      </div>
				   </form> 
				       <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="courseOffBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="courseOffCancel">取消</button>
                </div>      
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
<script type="text/javascript">
    $(function(){
	  require.config({paths: {"courseList":"res/js/themepavilion/course_list"}});
		require(["courseList"], function (courseList){
			courseList.init();
		});  
	});
</script>