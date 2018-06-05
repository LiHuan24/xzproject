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
							<label class="col-sm-3 control-label"><h4>数据字典分类编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-9 form-horizontal">
					<form name="dataDictCatEditForm">
						<div class="form-group ">
							<label class="col-sm-3 control-label key">数据字典分类代码：</label>
							<div class="col-sm-4">
							    <input class="form-control val" name="dataDictCatCode" value="${dataDictCat.dataDictCatCode}" readonly/>
							</div>
							<div style="margin-top:7px;"><span id="dataDictCatCodeEdit"></span></div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">数据字典分类名称：</label>
							<div class="col-sm-4">
								<input class="form-control val" name="dataDictCatName" value="${dataDictCat.dataDictCatName}"/>
							</div>
							<div style="margin-top:7px;"><span id="dataDictCatNameEdit"></span></div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp父分类代码：</label>
							<div class="col-sm-4">
							    <select name="parentCatCode" class="form-control val">
							    	<option value="">请选择</option>
							    	<#if dataDictCatList?exists>
							    		<#list dataDictCatList as dictCat>
							    			<option value="${dictCat.dataDictCatCode}" <#if dataDictCat.dataDictCatCode==dictCat.dataDictCatCode>selected=selected</#if>>${dictCat.dataDictCatName}</option>
							    		</#list>
							    	</#if>
							    </select>
							</div>
							<div style="margin-top:7px;"><span id="parentCatCodeEdit"></span></div>
						</div>	
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp是否可用：</label>
							<div class="col-sm-4 val">
								<input type="radio" name="isAvailable" value="1" <#if dataDictCat.isAvailable==1>checked=checked</#if>/>可用
								<input type="radio" name="isAvailable" value="0" <#if dataDictCat.isAvailable==0>checked=checked</#if>/>不可用
							</div>
						</div>																						
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp备注：</label>
							<div class="col-sm-4">
								<textarea class="form-control val" name="memo"/>
							</div>
							<div style="margin-top:7px;"><span id="memoEdit"></span></div>
						</div>												
                	</form>	
                		<div class="form-group">
							<div class="col-sm-8">
							  <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" id="closeEditDataDictCat" class="btn btn-default pull-right navbar-btn btn-flat device-operate-closeCarIllegalAdd btnDefault">
							        关闭
							  </button>
					          <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;margin-right: 50px !important" id="saveEditDataDictCat" class="btn btn-default pull-right navbar-btn btn-flat device-operate-saveCarIllegalAdd btnColorA">
					                保存
					          </button>
							</div>	
						</div>	
					</div>	
        		</div><!-- /.row -->
    
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"dataDictCatEdit":"res/js/system/dataDictCatEdit"}});
		require(["dataDictCatEdit"], function (dataDictCatEdit){
			dataDictCatEdit.init();
		});  
	});
</script>
</body>
