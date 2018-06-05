define([],function() {
	var auxiliaryEquipment = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#auxiliaryEquipmentSearch").click(function() {
				auxiliaryEquipment.pageList();
			});
			// 列表页面分页方法调用
			auxiliaryEquipment.pageList();
		},
		
		operateColumEvent : function() {
			$(".auxiliaryEquipment-operate-edit").each(function() {
				$(this).on("click", function() {
					addTab("辅助设备编辑",auxiliaryEquipment.appPath+ "/auxiliaryEquipment/toAuxiliaryEquipmentEdit.do?auxiliaryEquipmentNo="+$(this).data("id"));
				});
			});
			
			$(".auxiliaryEquipment-operate-view").each(function() {
				$(this).on("click", function() {
					addTab("辅助设备查看",auxiliaryEquipment.appPath+ "/auxiliaryEquipment/toAuxiliaryEquipmentView.do?auxiliaryEquipmentNo="+$(this).data("id"));
				});
			});
		},
		pageList : function() {
			var form = $("form[name=auxiliaryEquipmentSerachForm]");
			var auxiliaryEquipmentNo = form.find("input[name='auxiliaryEquipmentNo']").val();
			var sortNo = form.find("select[name='sortNo']").val();
			
			var auxiliaryEquipmentBtnTpl = $("#auxiliaryEquipmentBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(auxiliaryEquipmentBtnTpl);
			var table = $('#auxiliaryEquipmentList')
				.dataTable(
				{
					searching : false,
					destroy : true,
					"ajax" : {
						"type" : "POST",
						"url" : auxiliaryEquipment.appPath
								+ "/auxiliaryEquipment/auxiliaryEquipmentPageList.do",
						"data" : function(d) {
							return $.extend({},d,
									{"pageNo" : (d.start / d.length) + 1,
									 "pageSize" : d.length,
									 "auxiliaryEquipmentNo" : auxiliaryEquipmentNo,
									 "sortNo" : sortNo
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
						"data" : "auxiliaryEquipmentNo"
					}, {
						"title" : "城市",
						"data" : "cityName"
					}, {
						"title" : "所在门店",
						"data" : "storeName"
					} ,
					{
						"title" : "设备类型",
						"data" : "sortName"
					} ,
					{
						"title" : "品牌",
						"data" : "brandName"
					} ,
					{
						"title" : "型号",
						"data" : "modelName"
					}  ,{
						"title" : "操作",
						"orderable" : false
					} ],
					"dom": "<'row'<'#auxiliaryEquipmentTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					   initComplete: function () {
						   $("#auxiliaryEquipmentTools").css("float", "right");
						   $("#auxiliaryEquipmentTools").removeClass("col-xs-6");
						   $("#auxiliaryEquipmentTools").append('<button type="button" class="btn btn-default btn-sm auxiliaryEquipment-operate-add" data-toggle="modal" data-target="#equipmentBrandToolsModal" id="equipmentBrandModel" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">新增辅助设备</button>');
						   $(".auxiliaryEquipment-operate-add").on("click",function(){
		        				addTab("辅助设备添加", auxiliaryEquipment.appPath+ "/auxiliaryEquipment/toAuxiliaryEquipmentAdd.do");
			       			});
					   },
					"drawCallback" : function(settings) {
						auxiliaryEquipment.operateColumEvent();
					},
					"order" : [ [ 0, "desc" ] ],
					"columnDefs" : [
						{
							targets : 6,
							render : function(a, b, c,
									d) {
								var edit = '<span class="glyphicon auxiliaryEquipment-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
										+ c.auxiliaryEquipmentNo
										+ '" >编辑</span>';
								var view = '<span class="glyphicon auxiliaryEquipment-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'
									+ c.auxiliaryEquipmentNo
									+ '" >查看</span>';
								return edit+view;
						}
					}]
				});
		},
	};
	return auxiliaryEquipment;
});