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
							<label class="col-sm-7 control-label"><h4>数据字典分类详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-8 form-horizontal">
					<form name="dataDictCatEditForm">
						<div class="form-group ">
							<label class="col-sm-7 control-label key">数据字典分类代码：</label>
							<div class="col-sm-4">
							    <label class="control-label val">${dataDictCat.dataDictCatCode}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-7 control-label key">数据字典分类名称：</label>
							<div class="col-sm-4">
								<label class="control-label val">${dataDictCat.dataDictCatName}</label>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-7 control-label key">父分类代码：</label>
							<div class="col-sm-4">
								<label class="control-label val">${dataDictCat.parentCatCode}</label>
							</div>
						</div>		
						<div class="form-group">
							<label class="col-sm-7 control-label key">是否可用：</label>
							<div class="col-sm-4">
							<label class="control-label val">
								<#if dataDictCat.isAvailable==1>
								可用
								<#else>
								不可用
								</#if>
							</label>
							</div>
						</div>		
						<div class="form-group">
							<label class="col-sm-7 control-label key">是否删除：</label>
							<div class="col-sm-4">
							<label class="control-label val">
								<#if dataDictCat.isDeleted==1>
								已删除
								<#else>
								未删除
								</#if>
								</div>
							</label>
						</div>
						<div class="form-group">
							<label class="col-sm-7 control-label key">创建时间：</label>
							<div class="col-sm-4">
								<label class="control-label val">${dataDictCat.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>	
						<div class="form-group">
							<label class="col-sm-7 control-label key">更新时间：</label>
							<div class="col-sm-4">
								<label class="control-label val">${dataDictCat.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>																								
						<div class="form-group">
							<label class="col-sm-7 control-label key">备注：</label>
							<div class="col-sm-4">
								<label class="control-label val">${dataDictCat.memo}</label>
							</div>
						</div>											
                	</form>	
                		<div class="form-group">
							<div class="col-sm-12">
							  <button id="closeViewDataDictCat" class="btn btn-default  navbar-btn btn-flat device-operate-closeCarIllegalAdd btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd;margin-left:55%">
							        关闭
							  </button>
							</div>	
						</div>	
					</div>	
        		</div><!-- /.row -->
    
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"dataDict":"res/js/system/dataDictCat"}});
		require(["dataDict"], function (dataDict){
			dataDict.init();
		});  
	});
</script>
</body>
