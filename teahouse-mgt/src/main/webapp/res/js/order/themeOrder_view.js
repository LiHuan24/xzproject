define([],function() {	
	var themeOrderView = {
		appPath : getPath("app"),
		init : function() {
			//详情关闭
			$("#themeOrderViewClose").click(function(){
				closeTab("社区馆订单详情");
            });
		}
	}
 return	themeOrderView;
});


