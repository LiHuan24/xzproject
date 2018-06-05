define([],function() {
	var equipmentRepair = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#equipmentRepairSearch").click(function() {
				equipmentRepair.pageList();
			});
			// 列表页面分页方法调用
			equipmentRepair.pageList();
		},
		
		operateColumEvent : function() {
			$(".equipmentRepair-operate-edit").each(function() {
				$(this).on("click", function() {
					addTab("设备维修记录编辑",equipmentRepair.appPath+ "/equipmentRepair/toEquipmentRepairEdit.do?equipmentRepairNo="+$(this).data("id"));
				});
			});
			
			$(".equipmentRepair-operate-view").each(function() {
				$(this).on("click", function() {
					addTab("设备维修记录查看",equipmentRepair.appPath+ "/equipmentRepair/toEquipmentRepairView.do?equipmentRepairNo="+$(this).data("id"));
				});
			});
		},
		pageList : function() {
			var form = $("form[name=equipmentRepairSerachForm]");
			var fitnessEquipmentNo = form.find("input[name='fitnessEquipmentNo']").val();
			var repairId = form.find("select[name='repairId']").val();
			var repairStatus = form.find("select[name='repairStatus']").val();
			
			var equipmentRepairBtnTpl = $("#equipmentRepairBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(equipmentRepairBtnTpl);
			var table = $('#equipmentRepairList')
				.dataTable(
				{
					searching : false,
					destroy : true,
					"ajax" : {
						"type" : "POST",
						"url" : equipmentRepair.appPath
								+ "/equipmentRepair/equipmentRepairPageList.do",
						"data" : function(d) {
							return $.extend({},d,
									{"pageNo" : (d.start / d.length) + 1,
									 "pageSize" : d.length,
									 "fitnessEquipmentNo" : fitnessEquipmentNo,
									 "repairId" : repairId,
									 "repairStatus":repairStatus
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
						"data" : "fitnessEquipmentNo"
					}, {
						"title" : "城市",
						"data" : "cityName"
					}, {
						"title" : "所在门店",
						"data" : "storeName"
					}, {
						"title" : "设备类型",
						"data" : "sortName"
					}, {
						"title" : "维护类型",
						"data" : "repairTypeName"
					}, {
						"title" : "维护状态",
						"data" : "repairStatus"
					}, {
						"title" : "记录人",
						"data" : "recordName"
					}, {
						"title" : "维护人",
						"data" : "repairName"
					},  {
						"title" : "操作",
						"orderable" : false
					} ],
					"dom": "<'row'<'#equipmentRepairTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					   initComplete: function () {
						   $("#equipmentRepairTools").css("float", "right");
						   $("#equipmentRepairTools").removeClass("col-xs-6");
						   $("#equipmentRepairTools").append('<button type="button" class="btn btn-default btn-sm equipmentRepair-operate-add" data-toggle="modal" data-target="#equipmentRepairToolsModal" id="equipmentRepairModel" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">新增维修记录</button>');
						   $(".equipmentRepair-operate-add").on("click",function(){
		        				addTab("新增维修记录", equipmentRepair.appPath+ "/equipmentRepair/toEquipmentRepairAdd.do");
			       			});
					   },
					"drawCallback" : function(settings) {
						equipmentRepair.operateColumEvent();
					},
					"order" : [ [ 0, "desc" ] ],
					"columnDefs" : [
						{
							"targets" : [ 5 ],
							"render" : function(a, b, c, d) {
								var repairStatusName;
								//0、未开始，1、进行中，2、已完成
								if(a==0){
									repairStatusName="未开始";
								}else if(a==1){
									repairStatusName="进行中";
								}else if(a==2){
									repairStatusName="已完成";
								}else{
									return "--";
								}
								return repairStatusName;
							}
						},
						{
							targets : 8,
							render : function(a, b, c,
									d) {
								var edit = '<span class="glyphicon equipmentRepair-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
										+ c.equipmentRepairNo
										+ '" >编辑</span>';
								var view = '<span class="glyphicon equipmentRepair-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'
									+ c.equipmentRepairNo
									+ '" >查看</span>';
								return edit+view;
						}
					}]
				});
		},
	};
	return equipmentRepair;
});