define(
		[],
		function() {
			var equipmentBrand = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#equipmentBrandSearch").click(function() {
						equipmentBrand.pageList();
					});
					// 列表页面分页方法调用
					equipmentBrand.pageList();

				},
				
				operateColumEvent : function() {
					$(".equipmentBrand-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("设备品牌编辑",equipmentBrand.appPath+ "/equipmentBrand/toEquipmentBrandEdit.do?brandNo="+$(this).data("id"));
						});
					});
				},
				pageList : function() {
					var form = $("form[name=equipmentBrandSerachForm]");
					var brandName = form.find("input[name='brandName']").val();
					
					var equipmentBrandBtnTpl = $("#equipmentBrandBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(equipmentBrandBtnTpl);
					var table = $('#equipmentBrandList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : equipmentBrand.appPath
													+ "/equipmentBrand/equipmentBrandPageList.do",
											"data" : function(d) {
												return $.extend({},d,
														{"pageNo" : (d.start / d.length) + 1,
														 "pageSize" : d.length,
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
											"title" : "品牌名称",
											"data" : "brandName"
										}, {
											"title" : "网页",
											"data" : "brandWebsite"
										},{
											"title" : "操作",
											"orderable" : false
										} ],
										"dom": "<'row'<'#equipmentBrandTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										   initComplete: function () {
											   $("#equipmentBrandTools").css("float", "right");
											   $("#equipmentBrandTools").removeClass("col-xs-6");
											   $("#equipmentBrandTools").append('<button type="button" class="btn btn-default btn-sm equipmentBrand-operate-add" data-toggle="modal" data-target="#equipmentBrandToolsModal" id="equipmentBrandModel" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">添加</button>');
											   $(".equipmentBrand-operate-add").on("click",function(){
							        				addTab("设备品牌添加", equipmentBrand.appPath+ "/equipmentBrand/toEquipmentBrandAdd.do");
								       			});
										   },
										"drawCallback" : function(settings) {
											equipmentBrand.operateColumEvent();
										},
										"order" : [ [ 0, "desc" ] ],
										"columnDefs" : [
												{
													targets : 2,
													render : function(a, b, c,
															d) {
														var edit = '<span class="glyphicon equipmentBrand-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.brandNo
																+ '" >编辑</span>';
														return edit;
													}
												}]
									});
				},
			};
			return equipmentBrand;
		});
