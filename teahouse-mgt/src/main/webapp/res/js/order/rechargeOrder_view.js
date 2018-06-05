define([],function() {	
	var rechargeOrderView = {
		appPath : getPath("app"),
		init : function() {
			//详情关闭
			$("#rechargeOrderViewClose").click(function(){
				closeTab("充值订单详情");
            });
		}
	}
 return	rechargeOrderView;
});


