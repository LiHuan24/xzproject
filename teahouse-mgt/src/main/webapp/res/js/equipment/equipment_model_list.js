define(
		[],
		function() {
			var equipmentModel = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#equipmentModelSearch").click(function() {
						equipmentModel.pageList();
					});
					// 列表页面分页方法调用
					equipmentModel.pageList();

				},
				operateColumEvent : function() {
					$(".equipmentModel-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("设备型号编辑",equipmentModel.appPath+ "/equipmentModel/toEquipmentModelEdit.do?modelNo="+$(this).data("id"));
						});
					});
				},
				pageList : function() {
					var form = $("form[name=equipmentModelSerachForm]");
					var modelNo = form.find("input[name='modelNo']").val();
					var brandName = $.trim(form.find("input[name='brandName']").val());
					var equipmentModelBtnTpl = $("#equipmentModelBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(equipmentModelBtnTpl);
					var table = $('#equipmentModelList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : equipmentModel.appPath
													+ "/equipmentModel/equipmentModelPageList.do",
											"data" : function(d) {
												return $.extend({},d,
														{"pageNo" : (d.start / d.length) + 1,
														 "pageSize" : d.length,
														 "modelNo" :modelNo,
														 "brandName" : brandName
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
											"title" : "设备型号",
											"data" : "modelNo"
										}, {
											"title" : "设备名称",
											"data" : "modelName"
										}, {
											"title" : "品牌编号",
											"data" : "brandNo"
										}, {
											"title" : "品牌名称",
											"data" : "brandName"
										}, {
											"title" : "操作",
											"orderable" : false
										} ],
										"dom": "<'row'<'#equipmentModelTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										   initComplete: function () {
											   $("#equipmentModelTools").css("float", "right");
											   $("#equipmentModelTools").removeClass("col-xs-6");
											   $("#equipmentModelTools").append('<button type="button" class="btn btn-default btn-sm equipmentModel-operate-add" data-toggle="modal" data-target="#equipmentModelToolsModal" id="equipmentModel" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">添加</button>');
											   $(".equipmentModel-operate-add").on("click",function(){
							        				addTab("设备型号添加", equipmentModel.appPath+ "/equipmentModel/toEquipmentModelAdd.do");
								       			});
										},
										"drawCallback" : function(settings) {
											equipmentModel.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
												{
													targets : 4,
													render : function(a, b, c,
															d) {
														var edit = '<span class="glyphicon equipmentModel-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.modelNo
																+ '" >编辑</span>';
														return edit;
													}
												}]
									});
				},
			};
			return equipmentModel;
		});
