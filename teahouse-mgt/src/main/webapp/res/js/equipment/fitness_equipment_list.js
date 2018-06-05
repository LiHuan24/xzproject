define(
		[],
		function() {
			var fitnesseEquipment = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#fitnesseEquipmentSearch").click(function() {
						fitnesseEquipment.pageList();
					});
					// 列表页面分页方法调用
					fitnesseEquipment.pageList();

					//上线状态提交
					$("#onFormBtn").click(function(){
						fitnesseEquipment.onFormSub();
		            });
					//上线取消
					$("#onCancelBtn").click(function(){
						$("#upOnLineModel").modal("hide");
		            });
					//下线状态提交
					$("#offBtn").click(function(){
						fitnesseEquipment.offFormSub();
		            });
					//下线取消
					$("#offCancel").click(function(){
						$("#downOnLineModel").modal("hide");
		            });
					
				},
				operateColumEvent : function() {
					$(".fitnesseEquipment-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("健身设备详情",fitnesseEquipment.appPath+ "/fitnesseEquipment/toFitnessEquipmentView.do?fitnessEquipmentNo="+$(this).data("id"));
						});
					});
					$(".fitnesseEquipment-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("健身设备编辑",fitnesseEquipment.appPath+ "/fitnesseEquipment/toFitnessEquipmentEdit.do?fitnessEquipmentNo="+$(this).data("id"));
						});
					});
					
					//上线弹出层
					$(".fitnesseEquipment-operate-upOnLine").each(function(){
						$(this).on("click",function(){
							var fitnessEquipmentNo=$(this).data("id");
							$.ajax({
								 url : fitnesseEquipment.appPath+ "/fitnesseEquipment/getFitnessEquipmentById.do",
								 type:"post",
								 data: {fitnessEquipmentNo:fitnessEquipmentNo},
					             dataType: "json",
					             success: function(data){
				            	    $("#upOnLineModel").modal("show");
									$("#upFitnessEquipmentNo1").val(fitnessEquipmentNo);
									$("#upFitnessEquipmentMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将设备上线吗？");
					             }
							});
						});
					});
					//下线弹出层
					$(".fitnesseEquipment-operate-downOnLine").each(function(){
						$(this).on("click",function(){
							var fitnessEquipmentNo=$(this).data("id");
							$.ajax({
								 type: "post",
								 url : fitnesseEquipment.appPath+ "/fitnesseEquipment/getFitnessEquipmentById.do",
								 data: {fitnessEquipmentNo:fitnessEquipmentNo},
					             dataType: "json",
								 success: function(data){
				            	    $("#downOnLineModel").modal("show");
									$("#downFitnessEquipmentNo2").val(fitnessEquipmentNo);
									$("#downFitnessEquipmentMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将设备下线吗？");
					             }
							});
						});
					});
					$(".fitnesseEquipment-operate-onLineRecord").each(function(id,obj){
						$(this).on("click",function(){
							addTab("健身设备上下线日志",fitnesseEquipment.appPath+ "/fitnesseEquipment/toEquipmentOnlineLogList.do?fitnessEquipmentNo="+$(this).data("id"));			
						});
					});	
					
				},
		        //健身设备上线操作
		        onFormSub: function () {
		        	var form = $("form[name=onEquipmentForm]");
					form.ajaxSubmit({
		    			url:fitnesseEquipment.appPath+"/fitnesseEquipment/changeEquipmentOnLineStatus.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
									$("#upOnLineModel").modal('hide')
								});
								$("#fitnessEquipmentList").DataTable().ajax.reload(function(){
								}); 
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							if (form.find("textarea[name='onOffLineReason']").val()=="") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入上线理由！");
								return false;
							}
						}
					});
				},
				//设备下线操作
				offFormSub: function () {
		        	var form = $("form[name=offEquipmentForm]");
					form.ajaxSubmit({
						url:fitnesseEquipment.appPath+"/fitnesseEquipment/changeEquipmentOnLineStatus.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
							  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
									$("#downOnLineModel").modal('hide')
							  });
							  $("#fitnessEquipmentList").DataTable().ajax.reload(function(){
							  }); 
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							if (form.find("textarea[name='onOffLineReason']").val()=="") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入下线理由！");
								return false;
							}
						}
					});
				},
				pageList : function() {
					var form = $("form[name=fitnessEquipmentSerachForm]");
					var fitnessEquipmentNo = form.find("input[name='fitnessEquipmentNo']").val();
					var onLineStatus = form.find("select[name='onLineStatus']").val();
					var sortNo = $.trim(form.find("select[name='sortNo']").val());
					var fitnessEquipmentBtnTpl = $("#fitnessEquipmentBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(fitnessEquipmentBtnTpl);
					var table = $('#fitnessEquipmentList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : fitnesseEquipment.appPath
													+ "/fitnesseEquipment/fitnessEquipmentPageList.do",
											"data" : function(d) {
												return $.extend({},d,
														{"pageNo" : (d.start / d.length) + 1,
														 "pageSize" : d.length,
														 "fitnessEquipmentNo" :fitnessEquipmentNo,
														 "onlineStatus" : onLineStatus,
														 "sortNo" :sortNo
														});
											},
											"dataSrc" : function(json) {
												json.recordsTotal = json.rowCount;
												json.recordsFiltered = json.rowCount;
												json.data = json.data;
												return json.data;
											},
											error : function(xhr, error, thrown) {
											}
										},
										"columns" : [ 
										{
											"title" : "设备编号",
											"data" : "fitnessEquipmentNo","defaultContent":"-"
										}, {
											"title" : "城市",
											"data" : "cityName","defaultContent":"-"
										}, {
											"title" : "所在门店",
											"data" : "storeName","defaultContent":"-"
										}, {
											"title" : "设备类型",
											"data" : "sortName","defaultContent":"-"
										}, {
											"title" : "型号",
											"data" : "modelNo","defaultContent":"-"
										}, 
										{
											"title" : "使用状态",
											"data" : "useStatus","defaultContent":"-"
										},
										{
											"title" : "上线状态",
											"data" : "onlineStatus","defaultContent":"-"
										},
										{
											"title" : "设备状态",
											"data" : "isOnLine","defaultContent":"-"
										},
										{
											"title" : "操作",
											"orderable" : false
										} ],
										"dom": "<'row'<'#fitnesseEquipmentTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										   initComplete: function () {
											   $("#fitnesseEquipmentTools").css("float", "right");
											   $("#fitnesseEquipmentTools").removeClass("col-xs-6");
											   $("#fitnesseEquipmentTools").append('<button type="button" class="btn btn-default btn-sm fitnesseEquipment-operate-add" data-toggle="modal" data-target="#fitnesseEquipmentModal" id="fitnesseEquipmentModel" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">新增设备</button>');
											   $(".fitnesseEquipment-operate-add").on("click",function(){
							        				addTab("健身设备添加", fitnesseEquipment.appPath+ "/fitnesseEquipment/toFitnessEquipmentAdd.do");
								       			});
										   },
										"drawCallback" : function(settings) {
											fitnesseEquipment.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
											{
												"targets" : [ 5 ],
												"render" : function(a, b, c, d) {
													var onLineStatusName;
													if(a==0){
														onLineStatusName="空闲"
													}else if(a==1){
														onLineStatusName="使用中"
													}else if(a==2){
														onLineStatusName="维修中"
													}
													return onLineStatusName;
												}
											},
												{
													"targets" : [ 6 ],
													"render" : function(a, b, c, d) {
														var onLineStatusName;
														if(a==0){
															onLineStatusName="下线"
														}else if(a==1){
															onLineStatusName="上线"
														}else{
															return "--";
														}
														return onLineStatusName;
													}
												},
												{
													"targets" : [ 7 ],
													"render" : function(a, b, c, d) {
														var onLineStatusName;
														if(a==0){
															onLineStatusName="设备在线"
														}else if(a==1){
															onLineStatusName="设备不在线"
														}
														return onLineStatusName;
													}
												},
												{
													targets : 8,
													render : function(a, b, c,
															d) {
														var online = "";
														var view = '<span class="glyphicon fitnesseEquipment-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.fitnessEquipmentNo
																+ '" >查看</span>';
														var edit = '<span class="glyphicon fitnesseEquipment-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.fitnessEquipmentNo
																+ '" >编辑</span>';
														if(c.onlineStatus == 0){
															online = '<span class="glyphicon fitnesseEquipment-operate-upOnLine" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.fitnessEquipmentNo
																+ '" >上线</span>';
														}else{
															edit ="";
															online = '<span class="glyphicon fitnesseEquipment-operate-downOnLine" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.fitnessEquipmentNo
																+ '" >下线</span>';
														}
														var onlineRecord = '<span class="glyphicon fitnesseEquipment-operate-onLineRecord" style="text-decoration: underline; cursor: pointer;" data-id="'
															+ c.fitnessEquipmentNo
															+ '" >上下线日志</span>';
														
														return view+edit+online+onlineRecord;
													}
												}]
									});
				},
			};
			return fitnesseEquipment;
		});
