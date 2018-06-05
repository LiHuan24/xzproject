define([],function() {
var pricingRuleAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addPricingRule").click(function(){
				pricingRuleAdd.savePricingRule();
			});
			//新增页面的关闭
			$("#closeAddPricingRule").click(function(){
				closeTab("计费规则新增");
			});
		},
		savePricingRule:function() {
			var form = $("form[name=pricingRuleAddFrom]");
   		 	form.ajaxSubmit({
				url : pricingRuleAdd.appPath + "/pricingRule/addPricingRule.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("计费规则新增");
							$("#pricingRuleList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败,"+msg+"！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='ruleNameAdd']").empty();
					$("span[name='hourPriceAdd']").empty();
					$("span[name='dayCapAdd']").empty();
					$("span[name='consumptionQuotaAdd']").empty();

					if (form.find("input[name='ruleName']").val()=="") {
						$("span[name='ruleNameAdd']").append("<font color='red'>请输入计费规则名称！</font>");
						return false;
					}
					if (form.find("input[name='hourPrice']").val()=="") {
						$("span[name='hourPriceAdd']").append("<font color='red'>请输入计费金额！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='hourPrice']").val())){
		            	$("span[name='hourPriceAdd']").append("<font color='red'>格式不正确，计费金额只能输入整数或小数！</font>");
						return false;
		            }
					
					/*if (form.find("input[name='dayCap']").val()=="") {
						$("span[name='dayCapAdd']").append("<font color='red'>请输入每日封顶！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='dayCap']").val())){
		            	$("span[name='dayCapAdd']").append("<font color='red'>格式不正确，每日封顶只能输入整数或小数！</font>");
						return false;
		            }*/
					/*if (form.find("input[name='consumptionQuota']").val()=="") {
						$("span[name='consumptionQuotaAdd']").append("<font color='red'>请输入消费额度！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='consumptionQuota']").val())){
		            	$("span[name='consumptionQuotaAdd']").append("<font color='red'>格式不正确，累计消费额度只能输入整数或小数！</font>");
						return false;
		            }*/
				}
			});
		}
}
return pricingRuleAdd;
})
