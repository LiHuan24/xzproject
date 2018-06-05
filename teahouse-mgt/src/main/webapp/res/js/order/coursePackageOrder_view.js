define([],function() {	
	var coursePackageOrderView = {
		appPath : getPath("app"),
		init : function() {
			//详情关闭
			$("#coursePackageOrderViewClose").click(function(){
				closeTab("课程包订单详情");
            });
		}
	}
 return	coursePackageOrderView;
});


