define([],function() {	
	var communityOrder={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				communityOrder.pageList();
				//查询
				$("#communityOrderSearchafter").click(function(){
					var form = $("form[name=communityOrderSearchForm]");
					var vailableTime1 =  form.find("input[name=startTimeStart]").val();
					var vailableTime2 = form.find("input[name=endTimeEnd]").val();
					if(vailableTime1!=""&&vailableTime2!=""){
						if(new Date(vailableTime1)>new Date(vailableTime2)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始日期不能大于结束日期！");
						}else{
							communityOrder.pageList();
						}
					}else{
						communityOrder.pageList();
					}
	            });
				$("#closeCommunityOrderView").click(function(){
					closeTab("社区订单详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".communityOrder-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("社区订单详情",communityOrder.appPath+ "/communityOrder/toCommunityOrderView.do?communityOrderNo="+$(this).data("id"));
					});
				});
	        },
	        excel:function(){
				var form = $("form[name=communityOrderSearchForm]");
				window.location.href = communityOrder.appPath+"/communityOrder/toCommunityOrderExport.do"
						+ "?communityOrderNo=" + $.trim(form.find("input[name=communityOrderNo]").val())
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
				var communityOrderTpl = $("#communityOrderTpl").html();
				// 预编译模板
				var template = Handlebars.compile(communityOrderTpl);
				$('#communityOrderList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": communityOrder.appPath+'/communityOrder/pageListCommunityOrder.do',
						"data": function ( d ) {
							var form = $("form[name=communityOrderSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"communityOrderNo":$.trim(form.find("input[name=communityOrderNo]").val()),
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
			            { "title":"订单编号","data": "communityOrderNo","defaultContent":"-" },
						{ "title":"会员名称","data": "memberName","defaultContent":"-"  },
						{ "title":"手机号码","data": "mobilePhone","defaultContent":"-"  },
						{ "title":"城市名称","data": "cityName","defaultContent":"-"  },
						{ "title":"门店名称","data": "storeName","defaultContent":"-"  },
						{ "title":"订单开始时间","data": "startTime","defaultContent":"-"  },
						{ "title":"订单结束时间","data": "endTime","defaultContent":"-"  },
						{ "title":"订单时长（分钟）","data": "whenLong","defaultContent":"-"  },
						{ "title":"订单状态","data": "orderStatus","defaultContent":"-"  },
						{ "title":"支付状态","data": "payStatus","defaultContent":"-"  },
						{ "title":"支付方式","data": "paymentMethod","defaultContent":"-"  },
						{ "title":"订单总金额","data": "orderAmount","defaultContent":"-"  },
						{ "title":"优惠金额","data": "discountAmount","defaultContent":"-"  },
						{ "title":"余额抵扣金额","data": "balanceDiscountAmount","defaultContent":"-"  },
						{ "title":"应付金额","data": "payableAmount","defaultContent":"-"  },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#communityOrderTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#communityOrderTools").html("");
					   $("#communityOrderTools").css("float", "right");
					   $("#communityOrderTools").removeClass("col-xs-6");
					   $("#communityOrderTools").append('<button type="button" class="btn btn-default btn-sm communityOrderTools-operate-export btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">导出</button>');

					   //导出
						$(".communityOrderTools-operate-export").on("click",function(){		   					
	       					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
	       						if(result){
	       							communityOrder.excel();
	       						}
	       					});	
		       			}); 
					},
					"drawCallback": function( settings ) {
						communityOrder.operateColumEvent();
	        	    },
					"columnDefs": [
					  {
						    "targets": [5,6],
						    "render": function(data, type, row, meta) {
						    	if(data){
						    		var now = moment(data); 
						    		return now.format('YYYY-MM-DD'); 
						    	}
						    }
						},{
						    "targets": [8],
						    "render": function(a,b, c, d) {
					    		if(a==0){
					    			return "进行中";
					    		}else if(a==1){
					    			return "已结束";
					    		}else if(a==2){
					    			return "已取消";
					    		}
					    		
						    }
						},
						{
						    "targets": [9],
						    "render": function(a, b, c, d) {
						    	if(a==0){
					    			return "未支付";
					    		}else if(a==1){
					    			return "已支付";
					    		}
					    		
						    }
						},{
						    "targets": [10],
						    "render": function(a,b, c, d) {
						    	if(a==1){
					    			return "支付宝";
					    		}else if(a==2){
					    			return "微信";
					    		}
					    		
						    }
						},
						{
							"targets": [15],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon communityOrder-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.communityOrderNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
	        					return find;
							}
						}
					]
				});
			}
	    };
	return communityOrder;
});


