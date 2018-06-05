define([],function() {
	var courseLabel = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#courseLabelSearch").click(function() {
				courseLabel.pageList();
			});
			// 列表页面分页方法调用
			courseLabel.pageList();
			
		},
		operateColumEvent : function() {
			$(".courseLabel-operate-edit").each(function() {
				$(this).on("click", function() {
					addTab("课程标签编辑",courseLabel.appPath+ "/courseLabel/toCourseLabelEdit.do?courseLabelNo="+$(this).data("id"));
				});
			});
			$(".courseLabel-operate-del").each(function() {
				$(this).on("click", function() {
					var courseLabelNo = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
							$.post("courseLabel/delCourseLabel.do",{courseLabelNo:courseLabelNo},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
										$(".bootbox").modal("hide");
										$("#courseLabelList").DataTable().ajax.reload(function(){
				    					}); 
									  });
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！");
								}
							});
						}						
       				});
					
				});
			});
		},
		
		pageList : function() {
			var form = $("form[name=courseLabelSerachForm]");
			var labelName = form.find("input[name='labelName']").val();
		
			var courseLabelBtnTpl = $("#courseLabelBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(courseLabelBtnTpl);
			var table = $('#courseLabelList')
			.dataTable(
					{
						searching : false,
						destroy : true,
						"ajax" : {
							"type" : "POST",
							"url" : courseLabel.appPath
									+ "/courseLabel/courseLabelPageList.do",
							"data" : function(d) {
								return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "labelName" :labelName
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
							"title" : "课程标签名称",
							"data" : "labelName"
						}, {
							"title" : "删除状态",
							"data" : "isDeleted"
						}, {
							"title" : "操作",
							"orderable" : false
						} ],
						"dom": "<'row'<'#courseLabelTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
						   initComplete: function () {
							   $("#courseLabelTools").css("float", "right");
							   $("#courseLabelTools").removeClass("col-xs-6");
							   $("#courseLabelTools").append('<button type="button" class="btn btn-default btn-sm courseLabel-operate-add" data-toggle="modal" data-target="#fitnesseEquipmentModal" id="fitnesseEquipmentModel" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">新增课程标签</button>');
							   $(".courseLabel-operate-add").on("click",function(){
			        				addTab("新增课程标签", courseLabel.appPath+ "/courseLabel/toCourseLabelAdd.do");
				       			});
						   },
						"drawCallback" : function(settings) {
							courseLabel.operateColumEvent();
						},
						"order" : [ [ 1, "desc" ] ],
						"columnDefs" : [
								{
									"targets" : [ 1 ],
									"render" : function(a, b, c, d) {
										var deletedName;
										//（0、未删除，1、已删除）
										if(a==0){
											deletedName="未删除"
										}else if(a==1){
											deletedName="已删除"
										}else{
											return "--";
										}
										return deletedName;
									}
								},
								{
									targets : 2,
									render : function(a, b, c,
											d) {

										var edit = '<span class="glyphicon courseLabel-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
												+ c.courseLabelNo
												+ '" >编辑</span>';
										var del = '<span class="glyphicon courseLabel-operate-del"" style="text-decoration: underline; cursor: pointer;" data-id="'
											+ c.courseLabelNo
											+ '" >删除</span>';
										return edit+del;
									}
								}]
					});
		},
	};
	return courseLabel;
});