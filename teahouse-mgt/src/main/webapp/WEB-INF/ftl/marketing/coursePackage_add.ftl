<meta charset="utf-8">
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
				<label class="col-sm-12 control-label"><h4>课程包新增</h4></label>
			</div>
		</div>
	</div>	       
<div class="row hzlist">
	<div class="col-md-12 form-horizontal">
	<form  class="form-horizontal" name="coursePackageAddFrom">
		<div class="form-group col-md-6">
			<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;课程包名称：</label>
			<div class="col-sm-5">
			 <input class="form-control val" name="coursePackageName" />
			</div>
			<div><span name="coursePackageNameAdd"></span></div>
		</div>
		 <div class="form-group col-md-6">
			<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
			<div class="col-sm-5">
              <select name="cityId" class="form-control val">
				 <#list cities as citie>
					 <option  value="${citie.dataDictItemId}" >
                      ${citie.itemValue}
                    </option>
                </#list>  
			</select>
		</div>
		</div>
		<div class="form-group col-md-6">
			<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;销售价：</label>
			<div class="col-sm-5">
			 <input class="form-control val" name="price"/>
			</div>
			<div>元<span name="priceAdd"></span></div>
		</div>
		<div class="form-group col-md-6" id="moneyDiv">
			<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;课程节数：</label>
			<div class="col-sm-5">
			 <input class="form-control val" name="courseNumber" maxlength="6"/>
			</div>
			<div><span name="courseNumberAdd"></span></div>
		</div>
	</form>

</div>	
   		</div><!-- /.row -->
      <div class="form-group">
          <div class="col-sm-5" style="margin-bottom:20px;">
          <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="closeAddCoursePackage" class="btn btn-default pull-right sure btncolorb" >
			关闭
          </button>
          <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="addCoursePackage" class="btn btn-default pull-right sure btncolora" >
			保存
          </button>
          </div>
      </div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"coursePackageAdd":"res/js/marketing/coursePackage_add"}});
		require(["coursePackageAdd"], function (coursePackageAdd){
			coursePackageAdd.init();
		});  
	});    
</script>
