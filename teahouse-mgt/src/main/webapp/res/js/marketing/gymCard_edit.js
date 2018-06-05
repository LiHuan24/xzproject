define([],function() {
var gymCardEdit = {
		appPath : getPath("app"),
		init : function() {
			//编辑提交
			$("#editGymCard").click(function(){
				gymCardEdit.saveGymCard();
			});
			//编辑页面的关闭
			$("#closeEditGymCard").click(function(){
				closeTab("健身卡编辑");
			});
		},
		saveGymCard:function() {
			var form = $("form[name=gymCardEditFrom]");
			
			$("span[name='gymCardNameEdit']").empty();
			$("span[name='priceEdit']").empty();
			$("span[name='rechargeAmountEdit']").empty();
			$("span[name='cityIdEdit']").empty();
			if (form.find("input[name='gymCardName']").val()=="") {
				$("span[name='gymCardNameEdit']").append("<font color='red'>请输入健身卡名称！</font>");
				return false;
			}
			if (form.find("input[name='price']").val()=="") {
				$("span[name='priceEdit']").append("<font color='red'>请输入销售价！</font>");
				return false;
			}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='price']").val())){
            	$("span[name='priceEdit']").append("<font color='red'>格式不正确，不能有空格，销售价只能输入整数或小数！</font>");
				return false;
            }
			
			/*if (form.find("input[name='rechargeAmount']").val()=="") {
				$("span[name='rechargeAmountEdit']").append("<font color='red'>请输入健身卡金额！</font>");
				return false;
			}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='rechargeAmount']").val())){
				$("span[name='rechargeAmountEdit']").append("<font color='red'>健身卡金额只能输入正整数！</font>");
				return false;
			}*/
			
			var cityId = form.find("select[name='cityId']").val();
			var gymCardNo = form.find("input[name='gymCardNo']").val();
			$.post("gymCard/checkGymCardUp.do",{gymCardNo:gymCardNo,cityId:cityId},function(res){
				if(res.code==1){
					$("span[name='cityIdEdit']").append("<font color='red'>当前城市已存在健身卡！</font>");
					return false;
				}else{
					
					form.ajaxSubmit({
						url : gymCardEdit.appPath + "/gymCard/updateGymCard.do",
						type : "post",
						success : function(res) {
							var msg = res.msg;
							var code = res.code;
							if (code == "1") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改成功", function() {
									closeTab("健身卡编辑");
									$("#gymCardList").DataTable().ajax.reload(function(){});
								});
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改失败,"+msg+"！");
							}
						},
						/*beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							$("span[name='gymCardNameEdit']").empty();
							$("span[name='priceEdit']").empty();
							$("span[name='rechargeAmountEdit']").empty();
							if (form.find("input[name='gymCardName']").val()=="") {
								$("span[name='gymCardNameEdit']").append("<font color='red'>请输入健身卡名称！</font>");
								return false;
							}
							if (form.find("input[name='price']").val()=="") {
								$("span[name='priceEdit']").append("<font color='red'>请输入销售价！</font>");
								return false;
							}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='price']").val())){
				            	$("span[name='priceEdit']").append("<font color='red'>格式不正确，不能有空格，销售价只能输入整数或小数！</font>");
								return false;
				            }
							
							if (form.find("input[name='rechargeAmount']").val()=="") {
								$("span[name='rechargeAmountEdit']").append("<font color='red'>请输入健身卡金额！</font>");
								return false;
							}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='rechargeAmount']").val())){
								$("span[name='rechargeAmountEdit']").append("<font color='red'>健身卡金额只能输入正整数！</font>");
								return false;
							}
						}*/
					});	
					
				}
				});
			
   		 	
		}
}
return gymCardEdit;
})
