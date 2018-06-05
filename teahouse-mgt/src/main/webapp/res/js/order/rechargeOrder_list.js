define([],function() {	
	var rechargeOrder={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				rechargeOrder.pageList();
				//查询
				$("#rechargeOrderSearchafter").click(function(){
					var form = $("form[name=rechargeOrderSearchForm]");
					var vailableTime1 =  form.find("input[name=startTimeStart]").val();
					var vailableTime2 = form.find("input[name=endTimeEnd]").val();
					if(vailableTime1!=""&&vailableTime2!=""){
						if(new Date(vailableTime1)>new Date(vailableTime2)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始日期不能大于结束日期！");
						}else{
							rechargeOrder.pageList();
						}
					}else{
						rechargeOrder.pageList();
					}
	            });
				$("#closeRechargeOrderView").click(function(){
					closeTab("充值订单详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".rechargeOrder-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("充值订单详情",rechargeOrder.appPath+ "/rechargeOrder/toRechargeOrderView.do?rechargeOrderNo="+$(this).data("id"));
					});
				});
	        },
	        excel:function(){
				var form = $("form[name=rechargeOrderSearchForm]");
				window.location.href = rechargeOrder.appPath+"/rechargeOrder/toRechargeOrderExport.do"
						+ "?rechargeOrderNo=" + $.trim(form.find("input[name=rechargeOrderNo]").val())
						+ "&memberName=" + $.trim(form.find("input[name=memberName]").val())
						+ "&mobilePhone=" + $.trim(form.find("input[name=mobilePhone]").val())
						+ "&rechargeName=" + $.trim(form.find("input[name=rechargeName]").val())
						+ "&payStatus=" + form.find("select[name=payStatus]").val()
						+ "&paymentMethod=" + form.find("select[name=paymentMethod]").val()
						+ "&createTimeStart=" + $.trim(form.find("input[name='createTimeStart']").val()) + " 00:00:00"
						+ "&createTimeEnd=" + $.trim(form.find("input[name='createTimeEnd']").val()) + " 23:59:59"
	        },
			pageList:function () {	
				var rechargeOrderTpl = $("#rechargeOrderTpl").html();
				// 预编译模板
				var template = Handlebars.compile(rechargeOrderTpl);
				$('#rechargeOrderList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": rechargeOrder.appPath+'/rechargeOrder/pageListRechargeOrder.do',
						"data": function ( d ) {
							var form = $("form[name=rechargeOrderSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"rechargeOrderNo":$.trim(form.find("input[name=rechargeOrderNo]").val()),
								"memberNo":$.trim(form.find("input[name=memberNo]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"rechargeName":$.trim(form.find("input[name=rechargeName]").val()),
								"payStatus":form.find("select[name=payStatus]").val(),
								"paymentMethod":form.find("select[name=paymentMethod]").val(),
								"createTimeStart" : $.trim(form.find("input[name='createTimeStart']").val()) + " 00:00:00",
								"createTimeEnd" : $.trim(form.find("input[name='createTimeEnd']").val()) + " 23:59:59"

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
			            { "title":"订单编号","data": "rechargeOrderNo" },
						{ "title":"会员名称","data": "memberName" },
						{ "title":"手机号码","data": "mobilePhone" },
						{ "title":"充值包名称","data": "rechargeName" },
						{ "title":"充值包金额","data": "rechargeAmount" },
						{ "title":"应付金额","data": "payableAmount" },
						{ "title":"支付状态","data": "payStatus" },
						{ "title":"支付方式","data": "paymentMethod" },
						{ "title":"支付时间","data": "createTime" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#rechargeOrderTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#rechargeOrderTools").html("");
					   $("#rechargeOrderTools").css("float", "right");
					   $("#rechargeOrderTools").removeClass("col-xs-6");
					   $("#rechargeOrderTools").append('<button type="button" class="btn btn-default btn-sm rechargeOrderTools-operate-export btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">导出</button>');

					   //导出
						$(".rechargeOrderTools-operate-export").on("click",function(){		   					
	       					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
	       						if(result){
	       							rechargeOrder.excel();
	       						}
	       					});	
		       			}); 
					},
					"drawCallback": function( settings ) {
						rechargeOrder.operateColumEvent();
	        	    },
					"columnDefs": [
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
					    		}
					    		return "未知";
						    }
						},
						{
		            		"targets": [8],
						    "render": function(a,b, c, d) {
						        if(a!=null){
						    		return moment(a).format('YYYY-MM-DD HH:mm:ss'); 
						        }
					        	return "--";
						    }
						},
						{
							"targets": [9],
							"render": function (a, b, c, d) {
								var find='<span class="glyphicon rechargeOrder-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.rechargeOrderNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
	        					return find;
							}
						}
					]
				});
			}
	    };
	return rechargeOrder;
});


