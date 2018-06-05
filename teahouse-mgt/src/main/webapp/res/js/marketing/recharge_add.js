define([],function() {
var rechargeAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addRecharge").click(function(){
				rechargeAdd.saveRecharge();
			});
			//新增页面的关闭
			$("#closeAddRecharge").click(function(){
				closeTab("充值包新增");
			});
		},
		saveRecharge:function() {
			$("span[name='rechargeNameAdd']").empty();
			$("span[name='priceAdd']").empty();
			$("span[name='rechargeAmountAdd']").empty();
			var form = $("form[name=rechargeAddFrom]");
			if (form.find("input[name='rechargeName']").val()=="") {
				$("span[name='rechargeNameAdd']").append("<font color='red'>请输入充值包名称！</font>");
				return false;
			}
			if (form.find("input[name='price']").val()=="") {
				$("span[name='priceAdd']").append("<font color='red'>请输入销售价！</font>");
				return false;
			}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='price']").val())){
            	$("span[name='priceAdd']").append("<font color='red'>格式不正确，不能有空格，销售价只能输入整数或小数！</font>");
				return false;
            }
			
			var cityId = form.find("select[name='cityId']").val();
			var price = form.find("input[name='price']").val();
			$.post("recharge/checkRecharge.do",{cityId:cityId,price:price},function(res){
				if(res.code==1){
					$("span[name='priceAdd']").append("<font color='red'>当前城市已存在相同售价的充值包！</font>");
					return false;
				}else{
					form.ajaxSubmit({
						url : rechargeAdd.appPath + "/recharge/addRecharge.do",
						type : "post",
						success : function(res) {
							var msg = res.msg;
							var code = res.code;
							if (code == "1") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
									closeTab("充值包新增");
									$("#rechargeList").DataTable().ajax.reload(function(){});
								});
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败,"+msg+"！");
							}
						},
						
					});
					
				}
				});
			
   		 	
		}
}
return rechargeAdd;
})
