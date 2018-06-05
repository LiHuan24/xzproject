define([],function() {
		var fitnessEquipmentLog = {
			appPath : getPath("app"),
			init : function() {
				// 列表页面搜索调用
				$("#fitnessEquipmentLogSearch").click(function() {
					fitnessEquipmentLog.pageList();
				});
				// 列表页面分页方法调用
				fitnessEquipmentLog.pageList();
		},

		pageList : function() {
			var form = $("form[name=fitnessEquipmentLogSerachForm]");
			var fitnessEquipmentNo = form.find("input[name='fitnessEquipmentNo']").val();
			var opType = form.find("select[name='opType']").val();
			var fitnessEquipmentLogBtnTpl = $("#fitnessEquipmentLogBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(fitnessEquipmentLogBtnTpl);
			var table = $('#fitnessEquipmentLogList')
					.dataTable(
							{
								searching : false,
								destroy : true,
								"ajax" : {
									"type" : "POST",
									"url" : fitnessEquipmentLog.appPath
											+ "/fitnesseEquipment/pageEquipmentOnlineLog.do",
									"data" : function(d) {
										return $.extend({},d,
												{"pageNo" : (d.start / d.length) + 1,
												 "pageSize" : d.length,
												 "fitnessEquipmentNo" :fitnessEquipmentNo,
												 "opType" : opType
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
									"title" : "操作类型",
									"data" : "opType"
								}, {
									"title" : "操作时间",
									"data" : "createTime"
								}, {
									"title" : "备注",
									"data" : "memo"
								}, {
									"title" : "上下线理由",
									"data" : "updownWhy"
								}, {
									"title" : "操作人",
									"data" : "userName"
								}],
								"dom": "<'row'<'#fitnesseEquipmentLogTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
								   initComplete: function () {
									  
								   },
								"drawCallback" : function(settings) {
								},
								"order" : [ [ 1, "desc" ] ],
								"columnDefs" : [
										{
											"targets" : [ 1 ],
											"render" : function(a, b, c, d) {
												var onLineStatusName;
												//（0、下线，1、上线，默认0）
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
					        	            "targets": [2],
					        	            "render": function(data, type, row, meta) {
					        	            	if(data){
					        	            		var now = moment(data); 
						        	            	return now.format('YYYY-MM-DD HH:mm:ss');
					        	            	}else{
					        	            		return "--";
					        	            	}
					        	            	 
					        	            }
					        	        },
										]
							});
		},
	};
	return fitnessEquipmentLog;
});