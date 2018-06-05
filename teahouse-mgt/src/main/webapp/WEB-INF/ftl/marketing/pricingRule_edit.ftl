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
				<label class="col-sm-12 control-label"><h4>计费规则调整</h4></label>
			</div>
		</div>
	</div>	       
	<div class="row hzlist">
		<div class="col-md-12 form-horizontal">
			<form  class="form-horizontal" name="pricingRuleEditFrom">
			<input type="hidden" name="cencorStatus" value="${pricingRule.cencorStatus}">
			    <div class="form-group col-md-6">
					<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;计费规则编号：</label>
					<div class="col-sm-7">
					 	<input class="form-control val" name="ruleNo" value="${pricingRule.ruleNo}" readonly style="background-color: white"/>
					</div>
				</div>
				<div class="form-group col-md-6">
					<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;计费规则名称：</label>
					<div class="col-sm-7">
					 	<input class="form-control val" name="ruleName" value="${pricingRule.ruleName}" style="background-color: white"/>
					</div>
					<div style="margin-top:7px;"><span name="ruleNameEdit"></span></div>
				</div>
				<div class="form-group col-md-6">
					<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
					<div class="col-sm-7">
			             <select name="cityId" class="form-control val">
						 	<#list cities as citie>
								 <option  <#if pricingRule.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
				                           ${citie.itemValue}
				                 </option>
			                </#list>  
						</select>
					</div>
				</div>
				<div class="form-group col-md-6">
					<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;计费金额：</label>
					<div class="col-sm-7">
					 <input class="form-control val" name="hourPrice" value="${pricingRule.hourPrice}" style="background-color: white"/>
					</div>
					<div style="margin-top:7px;">元<span name="hourPriceEdit"></span></div>
				</div>
				<!-- <div class="form-group col-md-6">
					<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;每日封顶：</label>
					<div class="col-sm-7">
					 <input class="form-control val" name="dayCap" maxlength="6" />
					</div>
					<div>小时<span name="dayCapEdit"></span></div>
				</div>
				<div class="form-group col-md-6">
					<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;累计消费额度（转包月）：</label>
					<div class="col-sm-7">
					 <input class="form-control val" name="consumptionQuota" maxlength="6" />
					</div>
					<div>元<span name="consumptionQuotaEdit"></span></div>
				</div> -->
			</form>
		</div>	
	</div><!-- /.row -->
	<div>
		<button type="button" class="btn btn-info" style="position: relative; top: 15px; left: 20px; float: left;" id="customModelOpen">添加自定义计费规则</button>
		<div class="form-group col-md-6" id="oid" style="margin-top: 75px; margin-left: -130px; float: left;">
			<label class="radio-inline">
				<input type="radio" name="isOverdue" id="overdueId" value="0" checked>进行中
			</label>
			 <label class="radio-inline">
					<input type="radio" name="isOverdue" id="isOverdueId" value="1" >已过期
			</label>
		</div>
	</div>
    <div class="modal-dialog " style="width:377px;">
	   	<div class="modal-content">
	       <div class="">
		       <!--定义操作列按钮模板-->
		       <script id="pricingRuleCustomTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
			   	   <div class="box-body pcds" style="width: 1200px; margin-top: -115px; float: left; margin-left: -400px;">
					   <table id="pricingRuleCustomListAdd" class="table table-hover" width="100%" style=""></table>
				   </div><!-- /.box-body -->
		   </div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
        <div class="form-group">
	        <div class="col-sm-10" style="position: relative; margin-top: 20px; margin-bottom:20px; float: left;">
		        <button type="button" id="closeEditPricingRule" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
					关闭
				</button>
				<button type="button" id="EditPricingRule" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
		                        保存
				</button> 										
			</div>	
		</div>
	</div>
</div>
<div class="modal fade" id="customModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">自定义日期计费</h4>
            </div>
            <div class="modal-body hzlist">
				<form class="form-horizontal" name="customForm"> 
					<input  class="form-control val pdd" type="hidden" name="customNo">
			      	<input type="hidden"  name="ruleNo" value="${pricingRule.ruleNo}">
			      	<input type="hidden"  name="ruleName" value="${pricingRule.ruleName}">
			      	<input type="hidden"  name="cityId" value="${pricingRule.cityId}">
			      	<input type="hidden"  name="cityName" value="${pricingRule.cityName}">
					<div class="form-group col-md-12">
	         	 		<label class="col-sm-4 control-label key"><span>*</span>自定义日期：</label>
	                   	<div class="col-sm-4">
	                         <input class="form-control val pdd" name="hourPrice">
	                    </div>
	                    <div>元/小时</div>
	                    <div><span name="hourPriceError"></span></div>
					</div>
	                <div class="form-group col-md-12">
	      	 			<label class="col-sm-4 control-label key"><span>*</span>自定义日期：</label>
	                     <div class="col-sm-4">
	                     	 <input class="datepicker form-control pdd c_add" placeholder="" readOnly="readOnly"/>
	                         <input class="datepicker form-control pdd c_edit"  placeholder="" readOnly="readOnly"/>
	                     </div>
	                     <div><span name="customDateError"></span></div>
	                </div>
   				</form> 
   				<div class="modal-footer">
                	<input type="button" class="btn btn-default pull-right sure" id="customSubmitBtn" value="确定" >
                	<button type="button" class="btn btn-default pull-right cancels"  id="customCancelBtn">取消</button>
           	    </div>    
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"pricingRuleEdit":"res/js/marketing/pricingRule_edit"}});
		require(["pricingRuleEdit"], function (pricingRuleEdit){
			pricingRuleEdit.init();
		});  
	});    
    $(function () {
        $(".c_add").datepicker({
            language: "zh-CN",
            startDate: new Date(),
            autoclose: false,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            multidate:true,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
        $(".c_edit").datepicker({
            language: "zh-CN",
            startDate: new Date(),
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            multidate:false,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
