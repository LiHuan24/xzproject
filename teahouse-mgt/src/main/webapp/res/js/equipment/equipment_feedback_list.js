define([],function() {
	var equipmentFeedback = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#equipmentFeedbackSearch").click(function() {
				equipmentFeedback.pageList();
			});
			// 列表页面分页方法调用
			equipmentFeedback.pageList();
		},
		
		operateColumEvent : function() {
			//设备报修转成设备维修数据
			$(".equipmentFeedback-operate-turn").each(function() {
				$(this).on("click", function() {
					var equipmentFeedbackNo = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要转成设备维修数据吗？", function(result) {
						if(result){
							$.post("equipmentFeedback/feedbackDataTurnEquipmentRepair.do",{equipmentFeedbackNo:equipmentFeedbackNo},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
										  $(".bootbox").modal("hide");
										  $("#equipmentFeedbackList").DataTable().ajax.reload(function(){
				    						}); 
									  });
									
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败！");
								}
							});
						}						
       				});
					
				});
			});
		},
		pageList : function() {
			var form = $("form[name=equipmentFeedbackSerachForm]");
			var memberName = form.find("input[name='memberName']").val();
			var feedbackId = form.find("select[name='feedbackId']").val();
			
			var equipmentFeedbackBtnTpl = $("#equipmentFeedbackBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(equipmentFeedbackBtnTpl);
			var table = $('#equipmentFeedbackList')
				.dataTable(
				{
					searching : false,
					destroy : true,
					"ajax" : {
						"type" : "POST",
						"url" : equipmentFeedback.appPath
								+ "/equipmentFeedback/equipmentFeedbackPageList.do",
						"data" : function(d) {
							return $.extend({},d,
									{"pageNo" : (d.start / d.length) + 1,
									 "pageSize" : d.length,
									 "memberName" : memberName,
									 "feedbackId" : feedbackId
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
						"title" : "报修编号",
						"data" : "equipmentFeedbackNo"
					},{
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
						"title" : "报修类型",
						"data" : "feedbackName"
					}, {
						"title" : "会员编号",
						"data" : "memberNo"
					}, {
						"title" : "会员名称",
						"data" : "memberName"
					}, {
						"title" : "会员电话",
						"data" : "mobilePhone"
					},{
						"title" : "问题描述",
						"data" : "memo"
					},{
						"title" : "转维修状态",
						"data" : "turnStatus"
					},{
						"title" : "操作",
						"orderable" : false
					} ],
					"dom": "<'row'<'#equipmentFeedbackTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					   initComplete: function () {
						   
					   },
					"drawCallback" : function(settings) {
						equipmentFeedback.operateColumEvent();
					},
					"order" : [ [ 0, "desc" ] ],
					"columnDefs" : [
						{
							"targets" : [ 10 ],
							"render" : function(a, b, c, d) {
								var turnStatusName;
								//0、未转 1已转
								if(a==0){
									turnStatusName="未转";
								}else if(a==1){
									turnStatusName="已转";
								}else{
									return "--";
								}
								return turnStatusName;
							}
						},
						{
							targets : 11,
							render : function(a, b, c,
									d) {
								var turn = "";
								if(c.turnStatus == 0){
									turn = '<span class="glyphicon equipmentFeedback-operate-turn" style="text-decoration: underline; cursor: pointer;" data-id="'
										+ c.equipmentFeedbackNo
										+ '" >转维修</span>';
								}
								return turn;
						}
					}]
				});
		},
	};
	return equipmentFeedback;
});