define([],function() {	
	var themeOrderSummary={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				themeOrderSummary.pageList();
				//查询
				$("#themeOrderSummarySearchafter").click(function(){
					var form = $("form[name=themeOrderSummarySearchForm]");
					var vailableTime1 =  form.find("input[name=startTime]").val();
					var vailableTime2 = form.find("input[name=endTime]").val();
					if(vailableTime1!=""&&vailableTime2!=""){
						if(new Date(vailableTime1)>new Date(vailableTime2)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始日期不能大于结束日期！");
						}else{
							themeOrderSummary.pageList();
						}
					}else{
						themeOrderSummary.pageList();
					}
	            });
			},
	        excel:function(){
				var form = $("form[name=themeOrderSummarySearchForm]");
				window.location.href = themeOrderSummary.appPath+"/orderSummary/toThemeOrderSummaryExport.do"
						+ "?startTime=" + form.find("input[name=startTime]").val()
						+ "&endTime=" + form.find("input[name=endTime]").val();
			},
			pageList:function () {	
				var themeOrderSummaryTpl = $("#themeOrderSummaryTpl").html();
				// 预编译模板
				var template = Handlebars.compile(themeOrderSummaryTpl);
				$('#themeOrderSummaryList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": themeOrderSummary.appPath+'/orderSummary/pageListThemeOrderSummary.do',
						"data": function ( d ) {
							var form = $("form[name=themeOrderSummarySearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"startTime":form.find("input[name=startTime]").val(),
								"endTime":form.find("input[name=endTime]").val(),
							} );
						},
						"dataSrc": function ( json ) {
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;
							return json.data;
						},
						error: function (xhr, error, thrown) {  
			            }
					},
					"columns": [
			            { "title":"订单数量","data": "orderCount" },
						{ "title":"总时长(分)","data": "whenLong" },
						{ "title":"总订单金额(元)","data": "orderAmount" },
						{ "title":"实收金额(元)","data": "alreadyAmount" },
						{ "title":"优惠金额(元)","data": "discountAmount" },
						{ "title":"优惠订单数","data": "discountOrderCount" },
						{ "title":"余额抵扣金额","data": "balanceDiscountAmount" },
						{ "title":"余额抵扣订单数","data": "balanceOrderCount" },
						{ "title":"支付宝订单数(元)","data": "alipayCount" },
						{ "title":"支付宝订单金额(元)","data": "alipayAmount" },
						{ "title":"支付宝实收金额(元)","data": "alipayRealAmount" },
						{ "title":"支付宝手续费(元)","data": "alipayCharge" },
						{ "title":"微信订单数","data": "wxCount" },
						{ "title":"微信订单金额(元)","data": "wxAmount" },
						{ "title":"微信实收金额(元)","data": "wxRealAmount" },
						{ "title":"微信手续费(元)","data": "wxCharge" }
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#themeOrderSummaryTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#themeOrderSummaryTools").html("");
					   $("#themeOrderSummaryTools").css("float", "right");
					   $("#themeOrderSummaryTools").removeClass("col-xs-6");
					   $("#themeOrderSummaryTools").append('<button type="button" class="btn btn-default btn-sm themeOrderSummaryTools-operate-export btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">导出</button>');
					   //导出
					   $(".themeOrderSummaryTools-operate-export").on("click",function(){		   					
	       					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
	       						if(result){
	       							themeOrderSummary.excel();
	       						}
	       					});	
					   }); 
					},
					"drawCallback": function( settings ) {
	        	    },
					"columnDefs": [
					  
					]
				});
			}
	    };
	return themeOrderSummary;
});


