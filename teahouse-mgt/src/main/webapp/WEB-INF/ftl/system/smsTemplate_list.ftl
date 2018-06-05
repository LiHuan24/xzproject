<meta charset="utf-8">
<link rel="stylesheet" href="../res/dep/jstree/themes/default/style.min.css" />
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 100%  !important;
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

       <hr>
     <div class="box box-default">
      <div class="with-border">
              <#--<div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
       </div>
       <!-- /.box-tools &ndash;&gt;-->
       </div><!-- /.box-header -->
       
        <form class="form-horizontal" name="smsTemplateSearchForm"> 
       <div class="box-body">
        <div class="row pull-down-menu col-md-11">
         <div class="col-md-4">
                <div class="frombg">
                    <span>短信模板类型</span><select class="form-control" name="templateType">
                                                     <option value="">全部</option>
                                                     <option value="1">注册</option>
                                                     <option value="2">修改密码</option>
                                                     <option value="3">预约成功</option>
                                                     <option value="4">会员审核</option>
                                                     <option value="6">退款通知</option>
                                                     <option value="7">排队成功</option>
                                                     <option value="8">本次排队未能预约成功</option>
                                                </select>
                </div>
         </div>

        </div><!-- /.box-body -->
        </div>
        <!--修改-->
           <div class="col-md-12" style='float:right !important;'>
                  <div class="box-footer">
                   <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->

                   <button type="button" class="btn btn-default pull-right btn-flat flatten flatten btnColorA" id="smsTemplateSearch"  style="background:#2b94fd;margin-right:-2px !important">确定</button>
                   <button type="reset" class="btn btn-default pull-right btn-flat flatten flatten btnDefault" style="float:right;margin-right:20px !important">清空</button>
                  </div><!-- /.box-footer -->
            </div>
        </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
      <div class="row" style="width: 100%; height: 2px; background: #f1f5f8"></div>
      <div class="row">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="smsTemplateBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="smsTemplate" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
   <script type="text/javascript">
    $(function(){
	  require.config({paths: {"smsTemplate":"res/js/system/smsTemplate_list"}});
		require(["smsTemplate"], function (smsTemplate){
			smsTemplate.init();
		});  
	});
    $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
