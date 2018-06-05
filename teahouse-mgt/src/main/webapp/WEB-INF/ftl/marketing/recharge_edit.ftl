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
				<label class="col-sm-2 control-label"><h4>充值包编辑</h4></label>
			</div>
		</div>
	</div>	       
	<div class="row hzlist">
		<div class="col-md-12 form-horizontal">
			<form  class="form-horizontal" name="rechargeEditFrom">
			<input type="hidden" name="cencorStatus" value="${recharge.cencorStatus}" />
			    <div class="form-group col-md-6">
					<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;充值包编号：</label>
					<div class="col-sm-7">
					 	<input class="form-control val" name="rechargeNo" value="${recharge.rechargeNo}" readonly style="background-color: white"/>
					</div>
				</div>
				<div class="form-group col-md-6">
					<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;充值包名称：</label>
					<div class="col-sm-7">
					 	<input class="form-control val" name="rechargeName" value="${recharge.rechargeName}" style="background-color: white"/>
					</div>
					<div style="margin-top:7px;"><span name="rechargeNameEdit"></span></div>
				</div>
				<div class="form-group col-md-6">
					<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
					<div class="col-sm-7">
			             <select name="cityId" class="form-control val">
						 	<#list cities as citie>
								 <option  <#if recharge.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                           ${citie.itemValue}
				                 </option>
			                </#list>  
						</select>
					</div>
				</div>
				<div class="form-group col-md-6">
					<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;销售价：</label>
					<div class="col-sm-7">
					 <input class="form-control val" name="price" value="${recharge.price}" style="background-color: white"/>
					</div>
					<div style="margin-top:7px;">元<span name="priceEdit"></span></div>
				</div>
				
			</form>
		</div>	
	</div><!-- /.row -->
    <div class="form-group">
        <div class="col-sm-5" style="margin-bottom:20px;">
        <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="closeEditRecharge" class="btn btn-default pull-right sure btncolorb" >
			关闭
        </button> 
        <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="editRecharge" class="btn btn-default pull-right sure btncolora" >
			保存
        </button> 																				
		</div>	
	</div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"rechargeEdit":"res/js/marketing/recharge_edit"}});
		require(["rechargeEdit"], function (rechargeEdit){
			rechargeEdit.init();
		});  
	});    
</script>
