define([],function() {
var rechargeEdit = {
		appPath : getPath("app"),
		init : function() {
			//编辑提交
			$("#editRecharge").click(function(){
				rechargeEdit.saveRecharge();
			});
			//编辑页面的关闭
			$("#closeEditRecharge").click(function(){
				closeTab("充值包编辑");
			});
		},
		saveRecharge:function() {
			var form = $("form[name=rechargeEditFrom]");
			$("span[name='rechargeNameEdit']").empty();
			$("span[name='priceEdit']").empty();
			$("span[name='rechargeAmountEdit']").empty();
			if (form.find("input[name='rechargeName']").val()=="") {
				$("span[name='rechargeNameEdit']").append("<font color='red'>请输入充值包名称！</font>");
				return false;
			}
			if (form.find("input[name='price']").val()=="") {
				$("span[name='priceEdit']").append("<font color='red'>请输入销售价！</font>");
				return false;
			}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='price']").val())){
            	$("span[name='priceEdit']").append("<font color='red'>格式不正确，不能有空格，销售价只能输入整数或小数！</font>");
				return false;
            }
			
			
			var rechargeNo = form.find("input[name='rechargeNo']").val();
			var cityId = form.find("select[name='cityId']").val();
			var price = form.find("input[name='price']").val();
			$.post("recharge/checkRechargeUp.do",{rechargeNo:rechargeNo,cityId:cityId,price:price},function(res){
				if(res.code==1){
					$("span[name='priceEdit']").append("<font color='red'>当前城市已存在相同售价的充值包！</font>");
					return false;
				}else{
					form.ajaxSubmit({
						url : rechargeEdit.appPath + "/recharge/updateRecharge.do",
						type : "post",
						success : function(res) {
							var msg = res.msg;
							var code = res.code;
							if (code == "1") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改成功", function() {
									closeTab("充值包编辑");
									$("#rechargeList").DataTable().ajax.reload(function(){});
								});
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改失败,"+msg+"！");
							}
						},
						
					});
					
				}
				});
			
   		 	
		}
}
return rechargeEdit;
})
