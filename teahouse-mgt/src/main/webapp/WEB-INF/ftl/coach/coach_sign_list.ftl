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

				<form class="form-horizontal" name="coachSignSerachForm">
					<div class="box-body">
						<div class="row pull-down-menu col-md-11">
							<div class="col-md-4">
				            <div class="frombg">
				                <span>教练名称</span>
				                <input type="text" class="form-control" name="coachName" placeholder="">
				            </div>
				         </div>
						</div>
						<!-- /.box-body -->
					</div>
					<!--修改-->
					<div class="col-md-12" style='float: right !important;'>
						<div class="box-footer">
							<button type="button"
								class="btn btn-default pull-right btn-flat flatten flatten btnColorA"
								id="coachSignSearch"
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
			<script id="coachSignBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
			<div class="box">
				<div class="box-body">
					<table id="coachSignList" class="table table-hover"
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
<div class="modal fade"  id="coachClassModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">教练上课完成审核</h4>
            </div>
            <div class="modal-body">
              <form class="form-horizontal" name="coachClassForm"> 
			     <input type="hidden"  name="siapId" id="siapId1">
			     <input type="hidden"  name="signStatus" value="1">
			      <label for="inputEmail3" class=" control-label wen" id="classAuditMemo"></label>
			        <div>
			           <label for="inputEmail3" class=" control-label reason">审核通过原因:</label>
			        </div> 
			         <div class="form-group">
			            <div class="col-sm-8">
			                 <textarea class="form-control textareas" name="coachAppraise"></textarea>
			            </div>
			         </div>
			    </div>
			    </form> 
			    <div class="modal-footer">
	                <input type="button" class="btn btn-default pull-right sure" id="submitCoachClass" value="确定" >
	                 <button type="button" class="btn btn-default pull-right cancels"  id="coachClassCancelBtn">取消</button>
		        </div> 
		        </div>
			    </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
<script type="text/javascript">
    $(function(){
	  require.config({paths: {"coachSignList":"res/js/coach/coach_sign_list"}});
		require(["coachSignList"], function (coachSignList){
			coachSignList.init();
		});  
	});
</script>