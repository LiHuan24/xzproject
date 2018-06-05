define([],function() {
	var courseList = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#courseSearch").click(function() {
				courseList.pageList();
			});
			// 列表页面分页方法调用
			courseList.pageList();
			
			//启用状态提交
			$("#courseOnFormBtn").click(function(){
				courseList.onFormSub();
            });
			//启用取消
			$("#courseOnCancelBtn").click(function(){
				$("#onCourseModal").modal("hide");
            });
			//停用状态提交
			$("#courseOffBtn").click(function(){
				courseList.offFormSub();
            });
			//停用取消
			$("#courseOffCancel").click(function(){
				$("#offCourseModal").modal("hide");
            });
			
		},
		operateColumEvent : function() {
			$(".course-operate-edit").each(function() {
				$(this).on("click", function() {
					addTab("课程编辑",courseList.appPath+ "/course/toCourseEdit.do?courseNo="+$(this).data("id"));
				});
			});
			$(".course-operate-view").each(function() {
				$(this).on("click", function() {
					addTab("课程查看",courseList.appPath+ "/course/toCourseView.do?courseNo="+$(this).data("id"));
				});
			});
			
			//启用弹出层
			$(".course-operate-enable").each(function() {
				$(this).on("click", function() {
					var courseNo=$(this).data("id");
					$.ajax({
						 type: "post",
			             url: courseList.appPath+"/course/tCourse.do",
			             data: {courseNo:courseNo},
			             dataType: "json",
			             success: function(data){
			            	 if(data.code="1"){
			            	    $("#onCourseModal").modal("show");
			            	    $("body")[0].style.paddingRight=0;
								$("#courseNo1").val(data.courseNo);
								$("#onCourseMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;课程启用");
							 }
			             }
					});
				});
			});
			
			//停用弹出层
			$(".course-operate-disable").each(function() {
				$(this).on("click", function() {
					var courseNo=$(this).data("id");
					$.ajax({
						 type: "post",
			             url: courseList.appPath+"/course/tCourse.do",
			             data: {courseNo:courseNo},
			             dataType: "json",
			             success: function(data){
			            	 if(data.code="1"){
			            	    $("#offCourseModal").modal("show");
			            	    $("body")[0].style.paddingRight=0;
								$("#courseNo2").val(data.courseNo);
								$("#offCourseMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;课程停用");
							 }
			             }
					});
				});
			});
		},
		
        //启用操作
        onFormSub: function () {
        	var form = $("form[name=onCourseForm]");
			form.ajaxSubmit({
    			url:courseList.appPath+"/course/changeCourseEnable.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
							$("#onCourseModal").modal('hide')
						});
						$("#courseList").DataTable().ajax.reload(function(){
						}); 
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					if (form.find("textarea[name='memo']").val()=="") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入启用备注！");
						return false;
					}
				}
			});
		},
		//停用操作
		offFormSub: function () {
        	var form = $("form[name=offCourseForm]");
			form.ajaxSubmit({
				url:courseList.appPath+"/course/changeCourseEnable.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
					  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
						$("#offCourseModal").modal('hide')
					  });
					  $("#courseList").DataTable().ajax.reload(function(){
					  }); 
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					if (form.find("textarea[name='memo']").val()=="") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入停用备注！");
						return false;
					}
				}
			});
		},
		
		pageList : function() {
			var form = $("form[name=courseSerachForm]");
			var courseSortNo = form.find("select[name='courseSortNo']").val();
			var isEnable = form.find("select[name='isEnable']").val();
			var courseBtnTpl = $("#courseBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(courseBtnTpl);
			var table = $('#courseList')
			.dataTable(
					{
						searching : false,
						destroy : true,
						"ajax" : {
							"type" : "POST",
							"url" : courseList.appPath+ "/course/coursePageList.do",
							"data" : function(d) {
								return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "courseSortNo" :courseSortNo,
										 "isEnable":isEnable
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
							"title" : "课程中文名称",
							"data" : "chineseName"
						},{
							"title" : "课程英文名称",
							"data" : "englishName"
						},{
							"title" : "标签",
							"data" : "labelName"
						},{
							"title" : "分类",
							"data" : "sortName"
						},{
							"title" : "价格",
							"data" : "price"
						},{
							"title" : "启用状态",
							"data" : "isEnable"
						}, {
							"title" : "操作",
							"orderable" : false
						} ],
						"dom": "<'row'<'#courseTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
						   initComplete: function () {
							   $("#courseTools").css("float", "right");
							   $("#courseTools").removeClass("col-xs-6");
							   $("#courseTools").append('<button type="button" class="btn btn-default btn-sm course-operate-add" data-toggle="modal" data-target="#fitnesseEquipmentModal" id="fitnesseEquipmentModel" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">新增课程</button>');
							   $(".course-operate-add").on("click",function(){
			        				addTab("新增课程", courseList.appPath+ "/course/toCourseAdd.do");
				       			});
						   },
						"drawCallback" : function(settings) {
							courseList.operateColumEvent();
						},
						"order" : [ [ 1, "desc" ] ],
						"columnDefs" : [
								{
									"targets" : [ 5 ],
									"render" : function(a, b, c, d) {
										var enableName;
										//（0、未删除，1、已删除）
										if(a==0){
											enableName="停用";
										}else if(a==1){
											enableName="启用";
										}else{
											return "--";
										}
										return enableName;
									}
								},
								{
									targets : 6,
									render : function(a, b, c, d) {
										var edit = "";
										var view = '<span class="glyphicon course-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
												+ c.courseNo + '" >查看</span>';
										var enable = "";//启用
										var disable = "";//停用
										if(c.isEnable == 0){
											enable = '<span class="glyphicon course-operate-enable"" style="text-decoration: underline; cursor: pointer;" data-id="'
												+ c.courseNo + '" >启用</span>';
											edit = '<span class="glyphicon course-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
												+ c.courseNo + '" >编辑</span>';
										}else{
											disable = '<span class="glyphicon course-operate-disable"" style="text-decoration: underline; cursor: pointer;" data-id="'
												+ c.courseNo + '" >停用</span>';
										}
										return edit+view+enable+disable;
									}
								}]
					});
		},
	};
	return courseList;
});