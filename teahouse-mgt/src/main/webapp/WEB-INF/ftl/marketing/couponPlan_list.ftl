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
           <form class="form-horizontal" name="couponPlanSearchForm">
        <div class="with-border">

            <div class="row pull-down-menu col-md-11">
                <div class="col-md-3">
                    <div class="frombg">
                        <span>标题</span><input type="text" class="form-control" name="title" placeholder="">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="frombg">
                        <span>优惠类型</span>
                        <select class="form-control" name="couponType">
                            <option value="">全部</option>
                            <option value="1">优惠券</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="frombg">
                        <span>优惠方式</span>
                        <select class="form-control" name="couponMethod">
                            <option value="">全部</option>
                            <option value="1">折扣</option>
                            <option value="2">直减</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="frombg">
                        <span>审核状态</span>
                        <select class="form-control" name="censorStatus">
                            <option value="">全部</option>
                            <option value="0" >未审核</option>
                            <option value="1" >已审核</option>
                            <option value="2" >未通过</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
            <div class="row pull-down-menu col-md-11 other searching">
            <!--  <div class="col-md-3">
                <div class="frombg">
                    <span>方案编号</span><input type="text" class="form-control" name="planNo" placeholder="">
                </div>
          </div> -->

          <div class="col-md-3">
                <div class="frombg">
                    <span>启用状态</span>
                    <select class="form-control" name="isAvailable">
	                     <option value="">全部</option>
	                     <option value="0">停用</option>
	                     <option value="1">启用</option>
                    </select>
                </div>
          </div>

          <div class="col-md-3">
                  <div class="frombg">
                      <span>有效日期</span><input class="datepicker form-control" name="vailableTime1" placeholder=""/>
                  </div>
            </div>
            <div class="col-md-3">
                 <div class="frombg">
                     <span>至</span> <input class="datepicker form-control" name="vailableTime2" placeholder=""/>
                 </div>
           </div>


            <div class="col-md-3">
                           <div class="frombg">
                               <span>有效天数</span><input type="text" class="form-control" name="availableDays" placeholder="">
                           </div>
                     </div>
         </div>
         <div class="row">

         </div><!-- /.row -->
        </div><!-- /.box-body -->
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

                               <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="couponPlanSearchafter"  style="background:#2b94fd;margin-right:-2px !important">
                                                          确定
                                </button>
                                 <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault"  style="background:#fa6e30;float:right;margin-right:20px !important">
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
       <script id="couponPlanTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="couponPlanList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"couponPlan":"res/js/marketing/couponPlan_list"}});
		require(["couponPlan"], function (couponPlan){
			couponPlan.init();
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
