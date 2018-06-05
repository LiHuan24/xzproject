<meta charset="utf-8">
<body>
<style>
    .key {
        font-size: 1.5rem;
        font-weight: 500;
        color: #414550;
        line-height: 15px;
    }

    .val {
        font-size: 1.5rem;
        font-weight: 500;
        color: #a0a7af;
        line-height: 15px;
    }
</style>
<div class="container-fluid">
 <div class="row">
	<div class="col-md-12">
		<div class="form-group compiletitle">
			<label class="col-sm-12 control-label"><h4>课程分类新增</h4></label>
		</div>
	</div>
</div>	       
<div class="row hzlist">
	<div class="col-md-12">
	<form name="courseSortAddForm">
	<div class="col-md-12">
		<div class="form-group col-md-6"> 
            			<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;课程分类名称：</label>
                <div class="col-sm-7">
                    <input class="form-control val" name="sortName" />
                </div>
                <div><span name="sortNameAdd"></span></div>
           	</div>
		</div>	
	</form>	
		</div>	
   		</div><!-- /.row -->
           <div class="form-group">
               <div class="col-sm-6" style="margin-bottom:20px;">
                   <button type="button" id="closeAddCourseSort" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                        	 关闭
                   </button>
                   <button type="button" id="saveCourseSort" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                         	保存
                   </button>
               </div>
       </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"courseSortAdd":"res/js/themepavilion/course_sort_add"}});
		require(["courseSortAdd"], function (courseSortAdd){
			courseSortAdd.init();
		});
	});
</script>
</body>