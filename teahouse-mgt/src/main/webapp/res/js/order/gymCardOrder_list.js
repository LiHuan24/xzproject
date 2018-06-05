define([],function() {	
	var gymCardOrder={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				gymCardOrder.pageList();
				//查询
				$("#gymCardOrderSearchafter").click(function(){
					var form = $("form[name=gymCardOrderSearchForm]");
					var vailableTime1 =  form.find("input[name=startTimeStart]").val();
					var vailableTime2 = form.find("input[name=endTimeEnd]").val();
					if(vailableTime1!=""&&vailableTime2!=""){
						if(new Date(vailableTime1)>new Date(vailableTime2)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始日期不能大于结束日期！");
						}else{
							gymCardOrder.pageList();
						}
					}else{
						gymCardOrder.pageList();
					}
	            });
				$("#closeGymCardOrderView").click(function(){
					closeTab("健身卡订单详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".gymCardOrder-operate-find").each(function(){
					$(this).on("click",function(){
						addTab("健身卡订单详情",gymCardOrder.appPath+ "/gymCardOrder/toGymCardOrderView.do?gymCardOrderNo="+$(this).data("id"));
					});
				});
	        },
	        excel:function(){
				var form = $("form[name=gymCardOrderSearchForm]");
				window.location.href = gymCardOrder.appPath+"/gymCardOrder/toGymCardOrderExport.do"
						+ "?gymCardOrderNo=" + $.trim(form.find("input[name=gymCardOrderNo]").val())
						+ "&memberName=" + $.trim(form.find("input[name=memberName]").val())
						+ "&mobilePhone=" + $.trim(form.find("input[name=mobilePhone]").val())
						+ "&gymCardName=" + $.trim(form.find("input[name=gymCardName]").val())
						+ "&payStatus=" + form.find("select[name=payStatus]").val()
						+ "&paymentMethod=" + form.find("select[name=paymentMethod]").val()
						+ "&createTimeStart=" + $.trim(form.find("input[name='createTimeStart']").val()) + " 00:00:00"
						+ "&createTimeEnd=" + $.trim(form.find("input[name='createTimeEnd']").val()) + " 23:59:59"

	        },
			pageList:function () {	
				var gymCardOrderTpl = $("#gymCardOrderTpl").html();
				// 预编译模板
				var template = Handlebars.compile(gymCardOrderTpl);
				$('#gymCardOrderList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": gymCardOrder.appPath+'/gymCardOrder/pageListGymCardOrder.do',
						"data": function ( d ) {
							var form = $("form[name=gymCardOrderSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"gymCardOrderNo":$.trim(form.find("input[name=gymCardOrderNo]").val()),
								"memberNo":$.trim(form.find("input[name=memberNo]").val()),
								"memberName":$.trim(form.find("input[name=memberName]").val()),
								"mobilePhone":$.trim(form.find("input[name=mobilePhone]").val()),
								"gymCardName":$.trim(form.find("input[name=gymCardName]").val()),
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
			            { "title":"订单编号","data": "gymCardOrderNo" },
						{ "title":"会员名称","data": "memberName" },
						{ "title":"手机号码","data": "mobilePhone" },
						{ "title":"健身卡名称","data": "gymCardName" },
						{ "title":"健身卡金额","data": "rechargeAmount" },
						{ "title":"应付金额","data": "payableAmount" },
						{ "title":"支付状态","data": "payStatus" },
						{ "title":"支付方式","data": "paymentMethod" },
						{ "title":"支付时间","data": "createTime" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#gymCardOrderTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#gymCardOrderTools").html("");
					   $("#gymCardOrderTools").css("float", "right");
					   $("#gymCardOrderTools").removeClass("col-xs-6");
					   $("#gymCardOrderTools").append('<button type="button" class="btn btn-default btn-sm gymCardOrderTools-operate-export btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">导出</button>');

					   //导出
						$(".gymCardOrderTools-operate-export").on("click",function(){		   					
	       					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
	       						if(result){
	       							gymCardOrder.excel();
	       						}
	       					});	
		       			});
					   
					},
					"drawCallback": function( settings ) {
						gymCardOrder.operateColumEvent();
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
								var find='<span class="glyphicon gymCardOrder-operate-find" style="text-decoration: underline; cursor: pointer;"data-id="'+c.gymCardOrderNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
	        					return find;
							}
						}
					]
				});
			}
	    };
	return gymCardOrder;
});


