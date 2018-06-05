define([],function() {	
	var gymCardOrderView = {
		appPath : getPath("app"),
		init : function() {
			//详情关闭
			$("#gymCardOrderViewClose").click(function(){
				closeTab("健身卡订单详情");
            });
		}
	}
 return	gymCardOrderView;
});


