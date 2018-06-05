define([],function() {
	var courseSort = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#courseSortSearch").click(function() {
				courseSort.pageList();
			});
			// 列表页面分页方法调用
			courseSort.pageList();
			
		},
		operateColumEvent : function() {
			$(".courseSort-operate-edit").each(function() {
				$(this).on("click", function() {
					addTab("课程分类编辑",courseSort.appPath+ "/courseSort/toCourseSortEdit.do?courseSortNo="+$(this).data("id"));
				});
			});
			$(".courseSort-operate-del").each(function() {
				$(this).on("click", function() {
					var courseSortNo = $(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
						if(result){
							$.post("courseSort/delCourseSort.do",{courseSortNo:courseSortNo},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
										$(".bootbox").modal("hide");
										$("#courseSortList").DataTable().ajax.reload(function(){
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
			var form = $("form[name=courseSortSerachForm]");
			var sortName = form.find("input[name='sortName']").val();
		
			var courseSortBtnTpl = $("#courseSortBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(courseSortBtnTpl);
			var table = $('#courseSortList')
			.dataTable(
					{
						searching : false,
						destroy : true,
						"ajax" : {
							"type" : "POST",
							"url" : courseSort.appPath
									+ "/courseSort/courseSortPageList.do",
							"data" : function(d) {
								return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "sortName" :sortName
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
							"title" : "课程分类名称",
							"data" : "sortName"
						}, {
							"title" : "删除状态",
							"data" : "isDeleted"
						}, {
							"title" : "操作",
							"orderable" : false
						} ],
						"dom": "<'row'<'#courseSortTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
						   initComplete: function () {
							   $("#courseSortTools").css("float", "right");
							   $("#courseSortTools").removeClass("col-xs-6");
							   $("#courseSortTools").append('<button type="button" class="btn btn-default btn-sm courseSort-operate-add" data-toggle="modal" data-target="#fitnesseEquipmentModal" id="fitnesseEquipmentModel" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">新增课程分类</button>');
							   $(".courseSort-operate-add").on("click",function(){
			        				addTab("新增课程分类", courseSort.appPath+ "/courseSort/toCourseSortAdd.do");
				       			});
						   },
						"drawCallback" : function(settings) {
							courseSort.operateColumEvent();
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

										var edit = '<span class="glyphicon courseSort-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
												+ c.courseSortNo
												+ '" >编辑</span>';
										var del = '<span class="glyphicon courseSort-operate-del"" style="text-decoration: underline; cursor: pointer;" data-id="'
											+ c.courseSortNo
											+ '" >删除</span>';
										return edit+del;
									}
								}]
					});
		},
	};
	return courseSort;
});