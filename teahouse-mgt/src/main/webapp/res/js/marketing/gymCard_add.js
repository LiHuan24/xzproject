define([],function() {
var gymCardAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addGymCard").click(function(){
				gymCardAdd.saveGymCard();
			});
			//新增页面的关闭
			$("#closeAddGymCard").click(function(){
				closeTab("健身卡新增");
			});
		},
		saveGymCard:function() {
			$("span[name='gymCardNameAdd']").empty();
			$("span[name='priceAdd']").empty();
			$("span[name='rechargeAmountAdd']").empty();
			$("span[name='cityIdAdd']").empty();
			var form = $("form[name=gymCardAddFrom]");
			
			
			if (form.find("input[name='gymCardName']").val()=="") {
				$("span[name='gymCardNameAdd']").append("<font color='red'>请输入健身卡名称！</font>");
				return false;
			}
			if (form.find("input[name='price']").val()=="") {
				$("span[name='priceAdd']").append("<font color='red'>请输入销售价！</font>");
				return false;
			}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='price']").val())){
            	$("span[name='priceAdd']").append("<font color='red'>格式不正确，不能有空格，销售价只能输入整数或小数！</font>");
				return false;
            }
			
			/*if (form.find("input[name='rechargeAmount']").val()=="") {
				$("span[name='rechargeAmountAdd']").append("<font color='red'>请输入健身卡金额！</font>");
				return false;
			}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='rechargeAmount']").val())){
				$("span[name='rechargeAmountAdd']").append("<font color='red'>健身卡金额只能输入正整数！</font>");
				return false;
			}*/
			var cityId = form.find("select[name='cityId']").val();
			
			$.post("gymCard/checkGymCard.do",{cityId:cityId},function(res){
				if(res.code==1){
					$("span[name='cityIdAdd']").append("<font color='red'>当前城市已存在健身卡！</font>");
					return false;
				}else{
					form.ajaxSubmit({
						url : gymCardAdd.appPath + "/gymCard/addGymCard.do",
						type : "post",
						success : function(res) {
							var msg = res.msg;
							var code = res.code;
							if (code == "1") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
									closeTab("健身卡新增");
									$("#gymCardList").DataTable().ajax.reload(function(){});
								});
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败,"+msg+"！");
							}
						},
						/*beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							$("span[name='gymCardNameAdd']").empty();
							$("span[name='priceAdd']").empty();
							$("span[name='rechargeAmountAdd']").empty();
							if (form.find("input[name='gymCardName']").val()=="") {
								$("span[name='gymCardNameAdd']").append("<font color='red'>请输入健身卡名称！</font>");
								return false;
							}
							if (form.find("input[name='price']").val()=="") {
								$("span[name='priceAdd']").append("<font color='red'>请输入销售价！</font>");
								return false;
							}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='price']").val())){
				            	$("span[name='priceAdd']").append("<font color='red'>格式不正确，不能有空格，销售价只能输入整数或小数！</font>");
								return false;
				            }
							
							if (form.find("input[name='rechargeAmount']").val()=="") {
								$("span[name='rechargeAmountAdd']").append("<font color='red'>请输入健身卡金额！</font>");
								return false;
							}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='rechargeAmount']").val())){
								$("span[name='rechargeAmountAdd']").append("<font color='red'>健身卡金额只能输入正整数！</font>");
								return false;
							}
						}*/
					});
					
				}
				
			});
			
   		 	
		}
}
return gymCardAdd;
})
