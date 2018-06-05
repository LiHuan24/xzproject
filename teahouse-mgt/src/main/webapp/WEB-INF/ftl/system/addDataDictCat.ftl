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
							<label class="col-sm-3 control-label"><h4>数据字典分类新增</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-9 form-horizontal">
					<form name="dataDictCatAddForm">
						<div class="form-group ">
							<label class="col-sm-3 control-label key">数据字典分类代码：</label>
							<div class="col-sm-4">
							    <input class="form-control val" name="dataDictCatCode"/>
							</div>
							<div style="margin-top:7px;"><span id="dataDictCatCodeAdd"></span></div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">数据字典分类名称：</label>
							<div class="col-sm-4">
								<input class="form-control val" name="dataDictCatName"/>
							</div>
							<div style="margin-top:7px;"><span id="dataDictCatNameAdd"></span></div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp父分类代码：</label>
							<div class="col-sm-4">
							    <select name="parentCatCode" class="form-control val">
							    	<option value="">请选择</option>
							    	<#if dataDictCatList?exists>
							    		<#list dataDictCatList as dataDictCat>
							    			<option value="${dataDictCat.dataDictCatCode}">${dataDictCat.dataDictCatName}</option>
							    		</#list>
							    	</#if>
							    </select>
							</div>
							<div style="margin-top:7px;"><span id="parentCatCodeAdd"></span></div>
						</div>																							
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp备注：</label>
							<div class="col-sm-4">
								<textarea class="form-control val" name="memo"/>
							</div>
							<div style="margin-top:7px;"><span id="memoAdd"></span></div>
						</div>												
                	</form>	
                		<div class="form-group">
							<div class="col-sm-7">
							  <button id="closeAddDataDictCat" class="btn btn-default pull-right navbar-btn btn-flat device-operate-closeCarIllegalAdd btnDefault" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
							    关闭
							  </button>
					          <button id="saveAddDataDictCat" class="btn btn-default pull-right navbar-btn btn-flat device-operate-saveCarIllegalAdd btnColorA" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
					            保存
					          </button>
							</div>	
						</div>	
					</div>	
        		</div><!-- /.row -->
    
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"dataDictCatAdd":"res/js/system/dataDictCatAdd"}});
		require(["dataDictCatAdd"], function (dataDictCatAdd){
			dataDictCatAdd.init();
		});  
	});
</script>
</body>
