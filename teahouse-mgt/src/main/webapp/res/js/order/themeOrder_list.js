define([],function() {	
	var themeOrder={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				themeOrder.pageList();
				//查询
				$("#themeOrderSearchafter").click(function(){
					var form = $("form[name=themeOrderSearchForm]");
					var vailableTime1 =  form.find("input[name=startTimeStart]").val();
					var vailableTime2 = form.find("input[name=endTimeEnd]").val();
					if(vailableTime1!=""&&vailableTime2!=""){
						if(new Date(vailableTime1)>new Date(vailableTime2)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始日期不能大于结束日期！");
						}else{
							themeOrder.pageList();
						}
					}else{
						themeOrder.pageList();
					}
	            });
				$("#closeThemeOrderView").click(function(){
					closeTab("主题馆订单详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".themeOrder-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("主题馆订单详情",themeOrder.appPath+ "/themeOrder/toThemeOrderView.do?themeOrderNo="+$(this).data("id"));
					});
				});
	        },
	        excel:function(){
				var form = $("form[name=themeOrderSearchForm]");
				window.location.href = themeOrder.appPath+"/themeOrder/toThemeOrderExport.do"
						+ "?themeOrderNo=" + $.trim(form.find("input[name=themeOrderNo]").val())
						+ "&memberName=" + $.trim(form.find("input[name=memberName]").val())
						+ "&mobilePhone=" + $.trim(form.find("input[name=mobilePhone]").val())
						+ "&storeName=" + $.trim(form.find("input[name=storeName]").val())
						+ "&cityName=" + $.trim(form.find("input[name=cityName]").val())
						+ "&orderStatus=" + form.find("select[name=orderStatus]").val()
						+ "&payStatus=" + form.find("select[name=payStatus]").val()
						+ "&paymentMethod=" + form.find("select[name=paymentMethod]").val()
						+ "&startTimeStart=" + form.find("input[name=startTimeStart]").val()
						+ "&endTimeEnd=" + form.find("input[name=endTimeEnd]").val();
			},
			pageList:function () {	
				var themeOrderTpl = $("#themeOrderTpl").html();
				// 预编译模板
				var template = Handlebars.compile(themeOrderTpl);
				$('#themeOrderList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": themeOrder.appPath+'/themeOrder/pageListThemeOrder.do',
						"data": function ( d ) {
							var form = $("form[name=themeOrderSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"themeOrderNo":$.trim(form.find("input[name=themeOrderNo]").val()),
								"storeName":$.trim(form.find("input[name=storeName]").val()),
								"cityName":$.trim(form.find("input[name=cityName]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"orderStatus":form.find("select[name=orderStatus]").val(),
								"payStatus":form.find("select[name=payStatus]").val(),
								"paymentMethod":form.find("select[name=paymentMethod]").val(),
								"startTimeStart":form.find("input[name=startTimeStart]").val(),
								"endTimeEnd":form.find("input[name=endTimeEnd]").val(),
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
			            { "title":"订单编号","data": "themeOrderNo" },
						{ "title":"会员名称","data": "memberName","defaultContent":"--" },
						{ "title":"手机号码","data": "mobilePhone" },
						{ "title":"城市名称","data": "cityName" },
						{ "title":"门店名称","data": "storeName" },
						{ "title":"订单状态","data": "orderStatus" },
						{ "title":"支付状态","data": "payStatus" },
						{ "title":"支付方式","data": "paymentMethod" },
						{ "title":"订单总金额","data": "orderAmount" },
						{ "title":"优惠金额","data": "discountAmount","defaultContent":"-" },
						{ "title":"余额抵扣金额","data": "balanceDiscountAmount","defaultContent":"-" },
						{ "title":"应付金额","data": "payableAmount" },
						{ "title":"抵用课程包","data": "courseBagNum","defaultContent":"--"},
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#themeOrderTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#themeOrderTools").html("");
					   $("#themeOrderTools").css("float", "right");
					   $("#themeOrderTools").removeClass("col-xs-6");
					   $("#themeOrderTools").append('<button type="button" class="btn btn-default btn-sm themeOrderTools-operate-export btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">导出</button>');
					   //导出
					   $(".themeOrderTools-operate-export").on("click",function(){		   					
	       					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
	       						if(result){
	       							themeOrder.excel();
	       						}
	       					});	
					   }); 
					},
					"drawCallback": function( settings ) {
						themeOrder.operateColumEvent();
	        	    },
					"columnDefs": [
					  {
						    "targets": [5],
						    "render": function(a,b, c, d) {
					    		if(a==0){
					    			return "已预约";
					    		}else if(a==1){
					    			return "进行中";
					    		}else if(a==2){
					    			return "已结束";
					    		}else if(a==3){
					    			return "已取消";
					    		}else if(a==4){
					    			return "排队中";
					    		}else{
					    			return "待支付";
					    		}
					    		return "未知";
						    }
						},
						{
						    "targets": [6],
						    "render": function(a, b, c, d) {
						    	if(a==0){
					    			return "未支付";
					    		}else if(a==1){
					    			return "已支付";
					    		}
					    		return "未知";
						    }
						},{
						    "targets": [7],
						    "render": function(a,b, c, d) {
						    	if(a==1){
					    			return "支付宝";
					    		}else if(a==2){
					    			return "微信";
					    		}else if(a==3){
					    			return "其他方式";
					    		}
					    		return "未知";
						    }
						},{
						    "targets": [9],
						    "render": function(a,b, c, d) {
						    	if(a==0){
					    			return "-";
					    		}
					    		return a;
						    }
						},{
						    "targets": [10],
						    "render": function(a,b, c, d) {
						    	if(a==0){
					    			return "-";
					    		}
					    		return a;
						    }
						},{
						    "targets": [12],
						    "render": function(a,b, c, d) {
						    	if(a==0){
					    			return "-";
					    		}
					    		return a;
						    }
						},{
							"targets": [13],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon themeOrder-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.themeOrderNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
	        					return find;
							}
						}
					]
				});
			}
	    };
	return themeOrder;
});


