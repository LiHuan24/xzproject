define([],function() {	
	var transferRecord={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				transferRecord.pageList();
				//查询
				$("#transferRecordSearch").click(function(){
					transferRecord.pageList();
	            });
				$("#closeTransferRecordView").click(function(){
					closeTab("提现详情");
					transferRecord.pageList();
	            });
				$("#memoTransferRecord").click(function(){
					transferRecord.memoTransferRecord();
				});
				//取消提现申请提交按钮
				$("#transferRecordCancel_btn").click(function(){
					transferRecord.transferRecordCancelFormSub();
	            });
				//取消提现申请取消按钮
				$("#transferRecordCancel_cancelBtn").click(function(){
					$("#transferRecordCancelModel").modal("hide");
	            });
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".transferRecord-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("提现详情",transferRecord.appPath+ "/transferRecord/toTransferRecordView.do?transferNo="+$(this).data("id"));
					});
				});
	        	//审核
	        	$(".transferRecord-operate-cencor").each(function(){
	        		$(this).on("click",function(){
						addTab("提现审核",transferRecord.appPath+ "/transferRecord/toTransferRecordCencor.do?transferNo="+$(this).data("id"));
					});
				});
	        	
	        	//转账
				$(".transferRecord-operate-transfer").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						var memberName=$(this).data("membername");
						var amount=$(this).data("amount");
						memberName = memberName == null || memberName == "" ? "" : "[" + memberName + "]";
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要让该会员" + memberName + "提取余额["+ amount +"]元吗？", function(result) {
							if(result){
								 $.ajax({
					    			url: transferRecord.appPath+"/transferRecord/transfer.do",
					    			type : "post",
					    			data : {
					    				transferNo:id
					    			},
					    			dataType:"json",
					    			success:function(res){
					    				var code = res.code;
					    				var msg = res.msg;
				    					if(code == "1"){ 
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
				    							$("#transferRecordList").DataTable().ajax.reload(function(){});
				    						});
				    					}else if(msg != null){
				    						
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败," + msg, function() {
				    							$("#transferRecordList").DataTable().ajax.reload(function(){});
				    						});
				    						
				    					}else{
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败,", function() {
				    							$("#transferRecordList").DataTable().ajax.reload(function(){});
				    						});
				    					}
				    				}
				    			});
							}						
	       				});
					});
				});
				
				//取消
				//审核弹出层
				$(".transferRecord-operate-cancel").each(function(id){
					$(this).on("click",function(){
						var id=$(this).data("id");
						var memberName = $(this).data("membername");
						var amount=$(this).data("amount");
	            	    $("#transferRecordCancelModel").modal("show");
	            	    var form = $("form[name=transferRecordCancelForm]");
	            	    form.find("input[name='transferNo']").val(id)
	            	    form.find("textarea[name='cancelReason']").val("")
						$("#transferRecordMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;["+memberName+"]&nbsp;&nbsp;余额申请取消界面");
					});
				});
				
				
				/*$(".transferRecord-operate-cancel").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						var memberName=$(this).data("membername");
						var amount=$(this).data("amount");
						memberName = memberName == null || memberName == "" ? "" : "[" + memberName + "]";
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定取消该会员" + memberName + "提取余额["+ amount +"]元余额提现申请吗？", function(result) {
							if(result){
								 $.ajax({
					    			url: transferRecord.appPath+"/transferRecord/cancel.do",
					    			type : "post",
					    			data : {
					    				transferNo:id
					    			},
					    			dataType:"json",
					    			success:function(res){
					    				var code = res.code;
					    				var msg = res.msg;
				    					if(code == "1"){ 
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
				    							$("#transferRecordList").DataTable().ajax.reload(function(){});
				    						});
				    					}else if(msg != null){
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败," + msg);
				    					}else{
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
				    					}
				    				}
				    			});
							}						
	       				});
					});
				});*/
	        },
	      //取消操作
			transferRecordCancelFormSub: function () {
	        	var form = $("form[name=transferRecordCancelForm]");
				form.ajaxSubmit({
	    			url:transferRecord.appPath+"/transferRecord/cancel.do",
					type : "post",
					dataType:"json", //数据类型  
					success:function(res){
						var msg = res.msg;
						var code = res.code;
						if(code == "1"){ 
							$("#transferRecordCancelModel").modal("hide");
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
								$("#transferRecordList").DataTable().ajax.reload(function(){});
							});
						}else{
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						if (form.find("textarea[name='cancelReason']").val()=="") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "取消原因不能为空！");
							return false;
						}
					}
				});
			},
			pageList:function () {
				var transferRecordBtnTpl = $("#transferRecordBtnTpl").html();
				// 预编译模板
				var template = Handlebars.compile(transferRecordBtnTpl);
				$('#transferRecordList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": transferRecord.appPath+'/transferRecord/pageListTransferRecord.do',
						"data": function ( d ) {
							var form = $("form[name=transferRecordSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"mobilePhone":form.find("input[name=mobilePhone]").val(),
								"applyTimeStart":form.find("input[name=applyTimeStart]").val()+" 00:00:00",
								"applyTimeEnd":form.find("input[name=applyTimeEnd]").val()+" 23:59:59",
								"cencorStatus":form.find("select[name=cencorStatus]").val(),
								"transferStatus":form.find("select[name=transferStatus]").val(),
								"transferTimeStart":form.find("input[name=transferTimeStart]").val()+" 00:00:00",
								"transferTimeEnd":form.find("input[name=transferTimeEnd]").val()+" 23:59:59",
								"flowNo":form.find("input[name=flowNo]").val(),
								"transferMethod":form.find("select[name=transferMethod]").val(),
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
			            { "title":"客户名称","data": "memberName" },
						{ "title":"手机号","data": "mobilePhone" },
						{ "title":"提现金额(元)","data": "transferAmount"},
						{ "title":"申请时间","data": "applyTime"},
						{ "title":"审核状态","data": "cencorStatus"},
						{ "title":"提现状态","data": "transferStatus"},
						{ "title":"提现方式","data": "transferMethod"},
						{ "title":"提现流水号","data": "flowNo","width":"20%"},
						{ "title":"提现时间","data": "transferTime"},	
						{ "title":"操作","orderable":false}
					],
				   "dom": "<'row'<'#transferRecordTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#transferRecordTools").html("");
					   $("#transferRecordTools").css("float", "right");
					   $("#transferRecordTools").removeClass("col-xs-6");
					},
					"drawCallback": function( settings ) {
						transferRecord.operateColumEvent();
	        	    },
					"columnDefs": [
					    {
						    "targets": [3,8],
						    "render": function(a,b,c,d) {
						    	 if(a!=null){
							        	var now = moment(a);
										return now.format('YYYY-MM-DD HH:mm:ss');
							        }else{
							        	return "--";
							        }
						    }
						},{
						    "targets": [4],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==0){
						        		return "未审核";
						        	}else if(a==1){
						        		return "已审核";
						        	}else if(a==2){
						        		return "不通过";
						        	}
						        }
						        return "--";
						    }
						},{
						    "targets": [5],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==0){
						        		return "未提现";
						        	}else if(a==1){
						        		return "已提现";
						        	}else if(a==2){
						        		return "提现失败";
						        	}else if(a==3){
						        		return "用户取消";
						        	}else if(a==4){
						        		return "后台取消";
						        	}
						        }
					        	return "--";
						    }
						},{
						    "targets": [6],
						    "render": function(a,b,c,d) {
						    	if(a!=null){
						        	if(a==1){
						        		return "支付宝";
						        	}else if(a==2){
						        		return "微信";
						        	}
						        }
						        return "--";
						    }
						},
						{
							"targets": [9],
							"render": function (a, b, c, d) {
								var view = '<span class="glyphicon transferRecord-operate-view" style="text-decoration: underline; cursor: pointer;"data-id="'+c.transferNo+'" data-toggle="tooltip" data-placement="top">查看</span>';
								var cencor = '';
								var transfer = '';
								var cancel = '';
								if(c.cencorStatus==0){
									cencor='<span class="glyphicon transferRecord-operate-cencor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.transferNo+'" data-toggle="tooltip" data-placement="top">审核</span>';
								}else if(c.cencorStatus==1){
									if(c.transferStatus == 0 || c.transferStatus == 2){
										transfer = '<span class="glyphicon transferRecord-operate-transfer" style="text-decoration: underline; cursor: pointer;"data-id="'+c.transferNo+'"data-memberName="'+c.memberName+'"data-amount="'+c.transferAmount+'" data-toggle="tooltip" data-placement="top">转账</span>';
										cancel = '<span class="glyphicon transferRecord-operate-cancel" style="text-decoration: underline; cursor: pointer;"data-id="'+c.transferNo+'"data-memberName="'+c.memberName+'"data-amount="'+c.transferAmount+'" data-toggle="tooltip" data-placement="top">取消</span>';
									}
								}
								return view + cencor + transfer + cancel;
							}
						}
					]
				});
			}
	    };
	return transferRecord;
});


