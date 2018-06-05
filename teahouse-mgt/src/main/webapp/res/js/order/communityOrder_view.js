define([],function() {	
	var communityOrderView = {
		appPath : getPath("app"),
		init : function() {
			//详情关闭
			$("#communityOrderViewClose").click(function(){
				closeTab("社区馆订单详情");
            });
			
			communityOrderView.pageListCustomPricingRule();
		},
		pageListCustomPricingRule:function () {	
			$('#equipmentRecordListView').dataTable( {
				searching:false,
				destroy: true,
				bLengthChange:false,
				"ajax": {
					"type": "POST",
					"url": communityOrderView.appPath+"/equipmentRecord/pageListEquipmentRecordByOrderNo.do",
					"data": function ( d ) {
						var form = $("form[name=communityOrderViewFrom]");
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"orderNo":$.trim(form.find("input[name=communityOrderNo]").val()),
						} );
					},
					"dataSrc": function ( json ) {
						json.recordsTotal=json.rowCount;
						json.recordsFiltered=json.rowCount;
						json.data=json.data;
						if(json.data.length<=0){
							$(".pcds").hide();
						}else{
							$(".pcds").show();
						}
						return json.data;
					},
					error: function (xhr, error, thrown) {  
		            }
				},
				"columns": [
		            { "title":"设备编号","data": "fitnessEquipmentNo" },
					{ "title":"分类名称","data": "sortName" },
					{ "title":"开始时间","data": "startTime" },
					{ "title":"结束时间","data": "endTime" },
					{ "title":"使用时长（分钟）","data": "whenLong" },
					{ "title":"健身里程（m）","data": "rDist" },
					{ "title":"消耗卡路里","data": "rCal" }
				],
			   initComplete: function () {
				    $("#parkTools").html("");
				},
				"drawCallback": function( settings ) {
	    	    },
	    	    "order": [[ 2, "desc" ]],
				"columnDefs": [
	            	{
	            		"targets": [2,3],
					    "render": function(a,b, c, d) {
					        if(a!=null){
					    		return moment(a).format('YYYY-MM-DD HH:mm:ss'); 
					        }
				        	return "--";
					    }
					}
				]
			});
		}	
	}
 return	communityOrderView;
});


