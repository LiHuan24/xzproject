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
				<label class="col-sm-12 control-label"><h4>计费规则详情</h4></label>
			</div>
		</div>
	</div>	       
	<div class="row hzlist">
		<div class="col-md-10 form-horizontal">
			<div class="form-group col-md-6">
				<form  class="form-horizontal" name="pricingRuleViewFrom">
					<input class="form-control val" name="ruleNo" value="${pricingRule.ruleNo}" type="hidden"/>
				</form>
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;计费规则编号：</label>
					<div class="col-sm-6">
						<label class="control-label val">${pricingRule.ruleNo}</lable>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;计费规则名称：</label>
				<div class="col-sm-6">
					<label class="control-label val">${pricingRule.ruleName}</lable>
				</div>
			</div>
		 	<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;城市：</label>
				<div class="col-sm-6">
					<label class="control-label val">${pricingRule.cityName}</lable>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;计费金额：</label>
				<div class="col-sm-6">
					 <label class="control-label val">${pricingRule.hourPrice}元</label>
				</div>
			</div>
			<!-- <div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;每日封顶：</label>
				<div class="col-sm-6">
				 <label class="control-label val">小时</label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;累计消费额度（转包月）：</label>
				<div class="col-sm-6">
				 <label class="control-label val">元</label>
				</div>
			</div> -->
			<#if pricingRule.cencorStatus != 0>
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核状态：</label>
				<div class="col-sm-6">
					<label class="control-label val">
					<#if pricingRule.cencorStatus==0>
						未审核
					</#if>
					<#if pricingRule.cencorStatus==1>
						已审核
					</#if>
					<#if pricingRule.cencorStatus==2>
						未通过
					</#if>
					</label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;上下架状态：</label>
				<div class="col-sm-6">
					<label class="control-label val">
					<#if pricingRule.isAvailable==0>
						不可用
					<#else>
						可用
					</#if>
					</label>
				</div>
			</div>	
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核时间：</label>
				<div class="col-sm-6">
					<label class="control-label val">
						 ${pricingRule.cencorTime?string('yyyy-MM-dd HH:mm:ss')}
					</label>
				</div>
			</div>	
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核人：</label>
				<div class="col-sm-6">
					<label class="control-label val">
					 	${cencor}
					</label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核备注：</label>
				<div class="col-sm-6">
					<label class="control-label val">
					 	${pricingRule.cencorMemo}
					</label>
				</div>
			</div>	
			</#if>
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
				<div class="col-sm-6">
				<label class="control-label val">
					 ${pricingRule.createTime?string('yyyy-MM-dd HH:mm:ss')}
				</label>
				</div>
			</div>	
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;更新时间：</label>
				<div class="col-sm-6">
					<label class="control-label val">
					 	${pricingRule.updateTime?string('yyyy-MM-dd HH:mm:ss')}
					</label>
				</div>
			</div>	
			<div class="form-group col-md-12">
				<label class="col-sm-2 control-label key"><span></span>&nbsp;&nbsp;自定义计费规则:</label>
			</div>
			<div class="form-group col-md-12">
				<label class="col-sm-2 control-label key"><span></span>&nbsp;&nbsp;</label>
				<div class="col-sm-10">
					<table id="pricingRuleCustomListView" class="table table-hover" width="100%" style=""></table>
				</div>
			</div>
		</div>	
	</div><!-- /.row -->
	
	<div class="form-group">
		<div class="col-sm-6" style="margin-bottom:20px;">
			<button type="button" id="closePricingRuleView" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
	                  关闭
			</button>
		</div>	
	</div>
</div>

<script type="text/javascript">
	$(function(){
		require.config({paths: {"pricingRuleView":"res/js/marketing/pricingRule_view"}});
		require(["pricingRuleView"], function (pricingRuleView){
			pricingRuleView.init();
		});
		$(".datepicker").datepicker({
	      language: "zh-CN",
	      autoclose: true,//选中之后自动隐藏日期选择框
	      clearBtn: true,//清除按钮
	      todayBtn: 'linked',//今日按钮
	      format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	  	});
	}); 
</script>
