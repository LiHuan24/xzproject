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
				<label class="col-sm-12 control-label"><h4>计费规则新增</h4></label>
			</div>
		</div>
	</div>	       
<div class="row hzlist">
	<div class="col-md-12 form-horizontal">
	<form  class="form-horizontal" name="pricingRuleAddFrom">
		<div class="form-group col-md-6">
			<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;计费规则名称：</label>
			<div class="col-sm-5">
			 <input class="form-control val" name="ruleName" />
			</div>
			<div><span name="ruleNameAdd"></span></div>
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
			<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;计费金额：</label>
			<div class="col-sm-5">
			 <input class="form-control val" name="hourPrice"/>
			</div>
			<div>元<span name="hourPriceAdd"></span></div>
		</div>
		<!-- <div class="form-group col-md-6" id="moneyDiv">
			<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;每日封顶：</label>
			<div class="col-sm-5">
			 <input class="form-control val" name="dayCap" maxlength="6"/>
			</div>
			<div>元<span name="dayCapAdd"></span></div>
		</div>
		<div class="form-group col-md-6" id="moneyDiv">
			<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;累计消费额度（转包月）：</label>
			<div class="col-sm-5">
			 <input class="form-control val" name="consumptionQuota" maxlength="6"/>
			</div>
			<div>小时<span name="consumptionQuotaAdd"></span></div>
		</div> -->
	</form>

</div>	
   		</div><!-- /.row -->
      <div class="form-group">
          <div class="col-sm-5" style="margin-bottom:20px;">
          <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="closeAddPricingRule" class="btn btn-default pull-right sure btncolorb" >
			关闭
          </button>
          <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="addPricingRule" class="btn btn-default pull-right sure btncolora" >
			保存
          </button>
          </div>
      </div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingRuleAdd":"res/js/marketing/pricingRule_add"}});
		require(["pricingRuleAdd"], function (pricingRuleAdd){
			pricingRuleAdd.init();
		});  
	});    
</script>
