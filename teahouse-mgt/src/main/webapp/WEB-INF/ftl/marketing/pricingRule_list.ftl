<meta charset="utf-8">
<style>
.frombg .form-control{
	width:50% !important;
}
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%;
}
.frombg .form-control{
	width:45% !important;
	margin-right:20%;
}
.pull-down-menu span {
    width: 20%;
}
}
@media only screen and (max-width:768px) {
.row .sorting_disabled{
font-size:1.1rem !important;
}
}
@media only screen and (min-width:768px) and (max-width:1024px) {
.row .sorting_disabled{
font-size:1.2rem !important;
}
}
@media only screen and (min-width:1024px) and (max-width:1366px) {
.row .sorting_disabled{
font-size:1.3rem !important;
}
}
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12 pd10">
          <h4 class="box-title">查询</h4>
                   <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool more">
              更多<img src="res/img/arrow-down.png" width="15" style="margin-left: 2px;"/>
          </button>
         </div>
         <hr>
         <!-- /.box-tools -->
       	<div class="box box-default">
			<form class="form-horizontal" name="pricingRuleSerachForm">
	        <div class="with-border">
	
	            <div class="row pull-down-menu col-md-11">
	                <div class="col-md-3">
	                    <div class="frombg">
	                  		<span>规则编号</span><input  class="form-control" name="ruleNo" placeholder="">
	                    </div>
	                </div>
	                <div class="col-md-3">
	                    <div class="frombg">
	                 		<span>规则名称</span><input class="form-control" name="ruleName" placeholder="">
	                    </div>
	                </div>
	                 <div class="col-md-3">
	                    <div class="frombg">
	                 		<span>城市</span><input class="form-control" name="cityName" placeholder="">
	                    </div>
	                </div>
	                <div class="col-md-3">
	                    <div class="frombg">
			                <span>审核状态</span>
			                <select class="form-control" name="cencorStatus">
			                    <option value="">全部</option>
			                    <option value="0">未审核</option>
			                    <option value="1">已审核</option>
			                    <option value="2">审核不通过</option>
			                </select>
	                    </div>
	                </div>
	            </div>
			</div>
	        <div class="box-body">
	            <div class="row pull-down-menu col-md-11 other searching">
					<div class="col-md-3">
						<div class="frombg">
							<span>启用状态</span>
							<select class="form-control" name="isEnable">
								<option value="">全部</option>
								<option value="0">不可用</option>
								<option value="1">可用</option>
							</select>
						</div>
					</div>
				</div>
	         	<div class="row"></div><!-- /.row -->
			</div><!-- /.box-body -->
			<div class="col-md-12" style='float:right'>
		      	<div class="box-footer">
		      		<button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="pricingRuleSearch"  style="background:#2b94fd;margin-right:-2px !important">
						确定
					</button>
					<button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault"  style="background:#fa6e30;float:right;margin-right:20px !important">
						清空
					</button>
		       	</div>
			</div>
			</form>
		</div>
  </div>
</div>
<!--修改-->
<script>
   $(".other").hide();
   $(document).ready(function(){
       var state = 0; //hide
       $(".more").click(function(){
           if (state == 0){
               $(".other").show();

               $(this).html('收起<img src="res/img/arrow-up.png" width="15" style="margin-left: 2px;"/>');
               state = 1;
           } else {
               $(".other").hide();
               $(this).html('更多<img src="res/img/arrow-down.png" width="15" style="margin-left: 2px;"/>');
               state = 0;
           }
       })
   })
</script>
<div class="row" style="width: 100%; height: 2px; background: #f1f5f8"></div>
	<div class="row">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="pricingRuleTpl" type="text/x-handlebars-template">
        		{{#each func}}
        		<button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        		{{/each}}
			</script>
	       	<div class="box">
				<div class="box-body">
		         	<table id="pricingRuleList" class="table table-hover" width="100%" border="1">
					</table>
				</div><!-- /.box-body -->
			</div><!-- /.box -->
      </div>
     </div>
</div><!-- /.container-fluid -->
<div class="modal fade" id="pricingRuleCencorModel">
	<div class="modal-dialog">
	    <div class="modal-content">
	        <div class="modal-header">
	            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	            <h4 class="modal-title">审核</h4>
	        </div>
	        <div class="modal-body">
				<form class="form-horizontal" name="pricingRuleCencorForm"> 
		  			<input type="hidden"  name="ruleNo" id="pricingRuleCencorNo">
		    		<label for="inputEmail3" class=" control-label wen" id="pricingRuleCencorMemo"></label>
		      		<div>
		        		<label for="inputEmail3" class=" control-label reason">审核状态:</label>
		      		</div>
					<div class="form-group">
					 	<div class="col-sm-1"></div>
							<div class="col-sm-4">
		                         <input type="radio"  name="cencorStatus" value="1" checked="checked">通过&nbsp;&nbsp;
								<input type="radio"  name="cencorStatus" value="2">不通过
							</div>
						<div class="col-sm-3"></div>
					</div>
					<div>
		        		<label for="inputEmail3" class=" control-label reason">审核备注:</label>
		      		</div>
					<div class="form-group">
		            	<div class="col-sm-8">
		                	<textarea class="form-control textareas" name="cencorMemo"></textarea>
		            	</div>
		            </div>
			       	<div class="modal-footer">
	                    <input type="button" class="btn btn-default pull-right sure" id="pricingRuleCencorBtn" value="确定">
	                    <button type="button" class="btn btn-default pull-right cancels" id="pricingRuleCencorCancel">取消</button>
	                </div>              
				</form> 
	         </div>
	     </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
    
<script type="text/javascript">
	$(function(){
		require.config({paths: {"pricingRule":"res/js/marketing/pricingRule_list"}});
		require(["pricingRule"], function (pricingRule){
			pricingRule.init();
		});
		$(".datepicker").datepicker({
          language: "zh-CN",
          autoclose: true,//选中之后自动隐藏日期选择框
          clearBtn: true,//清除按钮
          todayBtn: 'linked',//今日按钮
          format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
      	});
  	}); 
</script>
