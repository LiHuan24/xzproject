define(
		[],
		function() {
			var equipmentCategory = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#equipmentCategorySearch").click(function() {
						equipmentCategory.pageList();
					});
					// 列表页面分页方法调用
					equipmentCategory.pageList();

				},
				
		        del: function (sortNo) {
		        	$.ajax({
		    			url:equipmentCategory.appPath+"/equipmentCategory/deleteEquipmentCategory.do",
		    			type:"post",
		    			data:{"sortNo":sortNo},
		    			dataType:"json",
		    			success:function(res){ 
		    					var code = res.code;
		    					var data = res.data;
		    					if(code == "1"){ 
		    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！", function() {
										//$("#smsTemplateModal").modal("hide");
			    						$("#equipmentCategoryList").DataTable().ajax.reload(function(){

			    						}); 
		    						});
		    					}else{
		    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！"); 
		    					} 
		    			}
		    		});
		    		return false;
		    		 
		        },
				
				operateColumEvent : function() {
					$(".equipmentCategory-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("设备分类详情",equipmentCategory.appPath+ "/equipmentCategory/toEquipmentCategoryView.do?sortNo="+$(this).data("id"));
						});
					});
					$(".equipmentCategory-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("设备分类编辑",equipmentCategory.appPath+ "/equipmentCategory/toEquipmentCategoryEdit.do?sortNo="+$(this).data("id"));
						});
					});
					
					$(".equipmentCategory-operate-del").each(function(id,obj){
						$(obj).on("click",function(){
							bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
								if(result){
									equipmentCategory.del($(obj).data("id"));
								}						
							}); 					
						});
					});	  

				},
				pageList : function() {
					var form = $("form[name=equipmentCategorySerachForm]");
					var sortNo = form.find("input[name='sortNo']").val();
					var sortType = $.trim(form.find("select[name='sortType']").val());
					
					var equipmentCategoryBtnTpl = $("#equipmentCategoryBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(equipmentCategoryBtnTpl);
					var table = $('#equipmentCategoryList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : equipmentCategory.appPath
													+ "/equipmentCategory/equipmentCategoryPageList.do",
											"data" : function(d) {
												return $.extend({},d,
														{"pageNo" : (d.start / d.length) + 1,
														 "pageSize" : d.length,
														 "sortNo" : sortNo,
														 "sortType" :sortType
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
											"title" : "设备分类编号",
											"data" : "sortNo"
										}, {
											"title" : "设备分类类型",
											"data" : "sortType"
										}, {
											"title" : "设备分类名称",
											"data" : "sortName"
										} ,{
											"title" : "操作",
											"orderable" : false
										} ],
										"dom": "<'row'<'#equipmentCategoryTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										   initComplete: function () {
											   $("#equipmentCategoryTools").css("float", "right");
											   $("#equipmentCategoryTools").removeClass("col-xs-6");
											   $("#equipmentCategoryTools").append('<button type="button" class="btn btn-default btn-sm equipmentCategory-operate-add" data-toggle="modal" data-target="#equipmentCategoryToolsModal" id="equipmentCategoryModel" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">添加</button>');
											   $(".equipmentCategory-operate-add").on("click",function(){
							        				addTab("设备分类添加", equipmentCategory.appPath+ "/equipmentCategory/toEquipmentCategoryAdd.do");
								       			});
										   },
										"drawCallback" : function(settings) {
											equipmentCategory.operateColumEvent();
										},
										"order" : [ [ 0, "desc" ] ],
										"columnDefs" : [
												{
													"targets" : [ 1 ],
													"render" : function(a, b, c, d) {
														var sortTypeName;
														//（0、下线，1、上线，默认0）
														if(a==0){
															sortTypeName="健身设备类型"
														}else if(a==1){
															sortTypeName="辅助设备类型"
														}else{
															return "--";
														}
														return sortTypeName;
													}
												},
												{
													targets : 3,
													render : function(a, b, c,
															d) {
														var online = "";
														var view = '<span class="glyphicon equipmentCategory-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.sortNo
																+ '" >查看</span>';
														var edit = '<span class="glyphicon equipmentCategory-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.sortNo
																+ '" >编辑</span>';
														var del='<span class="glyphicon equipmentCategory-operate-del" data-id="'+c.sortNo+'" style="text-decoration: underline; cursor: pointer;">删除</span>';
														return view+edit+del;
													}
												}]
									});
				},
			};
			return equipmentCategory;
		});
