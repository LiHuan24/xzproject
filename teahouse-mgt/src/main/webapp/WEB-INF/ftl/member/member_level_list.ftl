<meta charset="utf-8">
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%  !important;
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
          <!--<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>-->
         </div>
       <hr>
       <div class="box box-default">
        <div class=" with-border">

        
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
         <form class="form-horizontal" name="memberLevelSerachForm">
        <div class="box-body">
         <div class="row pull-down-menu col-md-11">
          <div class="col-md-3">
                <div class="frombg">
                    <span>等级名称</span><input  class="form-control" name="levelName" placeholder="">
                </div>
          </div>
         <div class="col-md-3">
                <div class="frombg">
                    <span>是否可用</span><select class="form-control" name="isAvailable">
                                     	                                  <option value="">全部</option>
                                     	                                  <option value="1">可用</option>
                                     	                                  <option value="0">不可用</option>
                                     									</select>
                </div>
			</div>
			<div class="col-md-3">
			    <div class="frombg">
                    <span>创建时间</span><input class="datetimepicker form-control" name="createTimeStart" style="background:#FFFFFF" readonly/>
                </div>
			</div>
            <div class="col-md-3">
                    <div class="frombg">
                        <span>至</span><input class="datetimepicker form-control" name="createTimeEnd" style="background:#FFFFFF" readonly/>
                    </div>
            </div>

          </div><!-- /.box-body -->

         </div>
         <!--修改-->
                     <div class="col-md-12">
                             <div class="box-footer">
                                 <!-- <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"><i class="hziconfont icons-qingchu iconbtn"></i>清空</button>-->
                                  <!--<button type="button" class="btn btn-default pull-right btn-flat btncolora" id="memberLevelSearch"><i class="hziconfont icons-yuanxingxuanzhong iconbtn"></i>确认</button>-->

                                    <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="memberLevelSearch"  style="background:#2b94fd;margin-right:-2px !important">确定</button>
                                  <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault"  style="background:#fa6e30;float:right;margin-right:20px !important">清空</button>

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
       <script id="memberLevelBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="memberLevelList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberLevel":"res/js/member/member_level_list"}});
		require(["memberLevel"], function (memberLevel){
			memberLevel.init();
		});  
	});    
   $(function () {
        $(".datetimepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
