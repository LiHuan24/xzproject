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
           <form class="form-horizontal" name="rechargeOrderSearchForm">
        <div class="with-border">

            <div class="row pull-down-menu col-md-11" style="margin-left: 1px; background: #f1f5f8;">
                <div class="col-md-3">
                    <div class="frombg">
                        <span>订单编号</span><input type="text" class="form-control" name="rechargeOrderNo" placeholder="">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="frombg">
                        <span>会员名称</span><input type="text" class="form-control" name="memberName" placeholder="">
                    </div>
                </div>
                 <div class="col-md-3">
                    <div class="frombg">
                        <span>会员手机</span><input type="text" class="form-control" name="mobilePhone" placeholder="">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="frombg">
                        <span>充值包名称</span><input type="text" class="form-control" name="rechargeName" placeholder="">
                    </div>
                </div>
            </div>
            </div>
	        <!-- /.box-header -->
	        <div class="box-body">
	           <div class="row pull-down-menu col-md-11 other searching">
	           <div class="col-md-3">
	                <div class="frombg">
	                    <span>支付状态</span>
	                    <select class="form-control" name="payStatus">
		                     <option value="">全部</option>
		                     <option value="0">未支付</option>
		                     <option value="1">已支付</option>
	                    </select>
	                </div>
	          </div>
	          <div class="col-md-3">
	                <div class="frombg">
	                    <span>支付方式</span>
	                    <select class="form-control" name="paymentMethod">
	                    	<option value="">全部</option>
	                        <option  value="1" >支付宝</option>
	                        <option  value="2" >微信</option>
	                   </select>
	                </div>
	           </div>
	           <div class="col-md-3">
		        	<div class="frombg">
		            	<span>支付日期</span><input class="datepicker form-control" name="createTimeStart" placeholder=""/>
		            </div>
		        </div>
		        <div class="col-md-3">
		        	<div class="frombg">
		            	<span>至</span> <input class="datepicker form-control" name="createTimeEnd" placeholder=""/>
		       		</div>
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
                  <div class="col-md-12" style='float:right'>
                         <div class="box-footer">
							<button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="rechargeOrderSearchafter" style="background:#2b94fd;margin-right:-2px !important">
								确定
  							</button>
							<button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault" style="background:#fa6e30;float:right;margin-right:20px !important">
								清空
							</button>
                         </div>
                  </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
       <div class="row" style="width: 100%; height: 2px; background: #f1f5f8"></div>
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="rechargeOrderTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="rechargeOrderList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"rechargeOrder":"res/js/order/rechargeOrder_list"}});
		require(["rechargeOrder"], function (rechargeOrder){
			rechargeOrder.init();
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
