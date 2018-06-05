<meta charset="utf-8">
<link rel="stylesheet" href="../res/dep/jstree/themes/default/style.min.css" />
<style>
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

.other{
margin:0 !important;
padding:0 !important;
}
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
<div class="container-fluid">
<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
	<ul id="orderCountTabs" class="nav nav-tabs" role="tablist">
	    <li role="presentation" class="active"><a href="#memberCountDay" aria-controls="memberCountDay" role="tab" data-toggle="tab">按日统计</a></li>
	    <li role="presentation"><a href="#memberCountMonth" aria-controls="memberCountMonth" role="tab" data-toggle="tab">按月统计</a></li>
	    <li role="presentation"><a href="#memberCountYear" aria-controls="memberCountYear" role="tab" data-toggle="tab">按年统计</a></li>
	  </ul>
	<div class="tab-content">
	    <div role="tabpanel" class="tab-pane active" id="memberCountDay">
           <form class="form-horizontal" name="earningsSerachForm">
          <div class="row pull-down-menu">
	    	<div class="col-md-2">
			        <div class="frombg">
                     <span style="width:40%">时间开始</span><input style="width:56%;background:none;" class="datetimepicker form-control" name="startTimeDay" style="background:#FFFFFF" readonly/>
                 </div>
			</div>
            <div class="col-md-2">
                  <div class="frombg">
                       <span style="width:40%">时间结束</span> <input style="width:56%;background:none;" class="datetimepicker form-control" name="endTimeDay" style="background:#FFFFFF" readonly/>
                   </div> 
            </div>
            <!--修改-->
            <div class="col-md-3">
                    <div class="box-footer">
                         <button type="button" class="btn btn-default pull-right btn-flat flatten btncolora" id="memberSearchDays" style="background:#2b94fd">确定</button>
                         <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb" style="background:#fa6e30">清空</button>
                     </div>
            </div> 
           </div>
         </form>
         
          <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <table style="width: 1400px;height:320px;">
                        	<tr>
                        		<td> <div id="earningsTrendDays" style="width:1100px;height:400px;"></div>  </td>
                        	</tr>
                        </table>
         			     
                    </div><!-- / box-->
               </div><!-- /.col -->
            </div><!-- /.row --> 
         <!-- /.box-footer -->
       </div>
	    <div role="tabpanel" class="tab-pane" id="memberCountMonth">
          <form class="form-horizontal" name="memberSerachMonthForm">
           <div class="row pull-down-menu">
	    	<div class="col-md-2">
			        <div class="frombg">
                     <span style="width:40%">时间开始</span><input style="width:56%;background:none;" class="monthTime form-control" name="startTimeMonth" style="background:#FFFFFF" readonly/>
                 </div>
			</div>
            <div class="col-md-2">
                  <div class="frombg">
                       <span style="width:40%">时间结束</span> <input style="width:56%;background:none;" class="monthTime form-control" name="endTimeMonth" style="background:#FFFFFF" readonly/>
                   </div> 
            </div>
            <div class="col-md-3">
                    <div class="box-footer">
                         <button type="button" class="btn btn-default pull-right btn-flat flatten btncolora" id="memberSearchMonth" style="background:#2b94fd">确定</button>
                          <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"  style="background:#fa6e30">清空</button>
                     </div>
            </div> 
           </div> 
         </form>
	        <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <table style="width: 1400px;height:320px;">
                        	<tr>
                        		<td><div id="memberCountMonthEcharts" style="width: 1100px;height:400px;"></div> </td>
                        	</tr>
                        </table>
                    </div>
               </div>
            </div>
            </br>    
	    </div>
	    
	    
	        	<div role="tabpanel" class="tab-pane" id="memberCountYear">
      	<form class="form-horizontal" name="memberSerachYearForm">
           <div class="row pull-down-menu">
	    	<div class="col-md-2">
			        <div class="frombg">
                     <span style="width:40%">时间开始</span><input style="width:56%;background:none;" class="yearTime form-control" name="startTimeYear" style="background:#FFFFFF" readonly/>
                 </div>
			</div>
            <div class="col-md-2">
                  <div class="frombg">
                       <span style="width:40%">时间结束</span> <input style="width:56%;background:none;" class="yearTime form-control" name="endTimeYear" style="background:#FFFFFF" readonly/>
                   </div> 
            </div>
            <div class="col-md-3">
                    <div class="box-footer">
                         <button type="button" class="btn btn-default pull-right btn-flat flatten btncolora" id="memberSearchYear" style="background:#2b94fd">确定</button>
                          <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"  style="background:#fa6e30">清空</button>
                     </div>
            </div> 
           </div> 
         </form>
         <div class="row">
              <div class="col-md-12">
                   <div class="box box-default">
                        <table style="width: 1400px;height:320px;">
                        	<tr>
                        		<td><div id="memberCountYearEcharts" style="width: 1100px;height:400px;"></div> </td>
                        	</tr>
                        </table>
                    </div>
               </div>
            </div>
            </br>  
        </div>
	  	
     </div>
     </div>
     </div>
    
  
    
    
    <script src="res/js/echarts/echarts-all.js"></script>
    <script type="text/javascript">
	$(function(){
	  require.config({paths: {"earningsTrendChart":"res/js/member/member_echart_list"}});
		require(["earningsTrendChart"], function (earningsTrendChart){
			earningsTrendChart.init();
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
	
	$('.monthTime').datetimepicker({  
	     format: 'yyyy-mm',  
	     weekStart: 1,  
	     autoclose: true,  
	     startView: 3,  
	     minView: 3,  
	     forceParse: false,  
	     language: 'zh-CN'  
	}); 
	
	$('.yearTime').datetimepicker({  
	     format: 'yyyy',  
	     weekStart: 1,  
	     autoclose: true,  
	     startView: 4,  
	     minView: 4,  
	     forceParse: false,  
	     language: 'zh-CN'  
	}); 
</script>
