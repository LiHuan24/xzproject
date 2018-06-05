<meta charset="utf-8">

<style>
.frombg span{
white-space: nowrap;
}
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
white-space: nowrap;
}
}
@media only screen and (min-width:768px) and (max-width:1024px) {
.row .sorting_disabled{
font-size:1.2rem !important;
white-space: nowrap;
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
          <#--<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>-->
         </div>
         <hr>
       <div class="box box-default">
        <div class="with-border">
     
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
        <form class="form-horizontal" name="dataDictItemSearchForm">
        <div class="box-body">
         <div class="row pull-down-menu col-md-11">
            <div class="col-md-6">
                <div class="frombg">
                    <span>数据字典分类名称</span><select class="form-control" name="dataDictCatCode">
                                                  	<option value="">请选择</option>
                                                  	<#if dataDictCatList?exists>
                                         	    		<#list dataDictCatList as dataDictCat>
                                         	    			<option value="${dataDictCat.dataDictCatCode}">${dataDictCat.dataDictCatName}</option>
                                         	    		</#list>
                                         	    	</#if>
                                                  </select>
                </div>
         </div>
            <div class="col-md-6">
                <div class="frombg">
                    <span>是否可用</span> <select class="form-control" name="isAvailable">
                                                <option value="">全部</option>
                                                <option value="1">可用</option>
                                                <option value="0">不可用</option>
                                               </select>
                </div>
          </div>

         </div><!-- /.row -->
         </div><!-- /.box-body -->
          <!--修改-->
                     <div class="col-md-12" style='float:right !important;'>
                           <div class="box-footer">

                           	         <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="dataDictItemSearchButton" style="background:#2b94fd;margin-right:-2px !important">确定</button>
                                     <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault"  style="float:right;margin-right:20px !important">清空</button>
                                    </div>
                     </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="dataDictItemTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
          <div class="row" style="width: 100%; height: 2px; background: #f1f5f8"></div>
       <div class="box">
        <div class="box-body">
         <table id="dataDictItemList" class="table  table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"dataDictItem":"res/js/system/dataDictItem"}});
		require(["dataDictItem"], function (dataDictItem){
			dataDictItem.init();
		});  
	});
</script>
