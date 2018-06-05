define([],function() {
	var coursePackage = {
		appPath : getPath("app"),
		init : function() {
			$("#closeCoursePackageView").click(function(){
				closeTab("课程包详情");
			});
			// 列表页面搜索调用
			$("#coursePackageSearch").click(function() {
				coursePackage.pageList();
			});
			//审核提交
			$("#coursePackageCencorBtn").click(function(){
				coursePackage.coursePackageCencorFormSub();
            });
			//审核取消
			$("#coursePackageCencorCancel").click(function(){
				$("#coursePackageCencorModel").modal("hide");
            });
			$("#coursePackageCencorModel").on("hidden.bs.modal", function() {  
            	var form = $("form[name=coursePackageCencorForm]");
            	form.resetForm();
            	$("#cencorMemo").text("");
            });
			// 列表页面分页方法调用
			coursePackage.pageList();

		},
		//审核操作
		coursePackageCencorFormSub: function () {
        	var form = $("form[name=coursePackageCencorForm]");
			form.ajaxSubmit({
    			url:coursePackage.appPath+"/coursePackage/updateCoursePackageNs.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
						$("#coursePackageCencorModel").modal("hide");
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
							$("#coursePackageList").DataTable().ajax.reload(function(){});
						});
					}else{
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					if (form.find("textarea[name='cencorMemo']").val()=="") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核备注不能为空！");
						return false;
					}
				}
			});
		},
		operateColumEvent : function() {
			$(".coursePackage-operate-view").each(function() {
				$(this).on("click", function() {
					addTab("课程包详情",coursePackage.appPath+ "/coursePackage/toCoursePackageView.do?coursePackageNo="+$(this).data("id"));
				});
			});
			$(".coursePackage-operate-edit").each(function(){
				$(this).on("click",function(){
					addTab("课程包修改",coursePackage.appPath+ "/coursePackage/toEditCoursePackage.do?coursePackageNo="+$(this).data("id"));
				});
			});
			//审核弹出层
			$(".coursePackage-operate-cencor").each(function(id){
				$(this).on("click",function(){
					var coursePackageNo=$(this).data("id");
					var coursePackageName = $(this).data("name");
            	    $("#coursePackageCencorModel").modal("show");
					$("#coursePackageCencorNo").val(coursePackageNo);
					$("#coursePackageCencorMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;["+coursePackageName+"]&nbsp;&nbsp;课程包审核界面");
				});
			});
			
			//启停用
			$(".coursePackage-operate-isEnable").each(function(id,obj){
				$(this).on("click",function(){
					var id=$(this).data("id");
					var isEnable = $(this).data("isenable");
					var name = $(this).data("name");
					var hint = isEnable == 1 ? "启用" : "停用";
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+hint+"["+name+"] 课程包吗？", function(result) {
						if(result){
							 $.ajax({
				    			url:coursePackage.appPath+"/coursePackage/updateCoursePackage.do",
				    			type:"post",
				    			data:{
				    				coursePackageNo:id,
				    				isEnable:isEnable
				    			},
				    			dataType:"json",
				    			success:function(res){
				    				var code=res.code;
				    				var msg=res.msg;
			    					if(code == "1"){ 
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
			    							$("#coursePackageList").DataTable().ajax.reload(function(){});
			    						});
			    					}else if(msg != null){
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败," + msg);
			    					}else{
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
			    					}
			    				}
			    			});
						}						
       				});
				});
			});
		},
		pageList : function() {
			var form = $("form[name=coursePackageSerachForm]");
			var coursePackageTpl = $("#coursePackageTpl").html();
			// 预编译模板
			var template = Handlebars.compile(coursePackageTpl);
			var table = $('#coursePackageList').dataTable({
				searching : false,
				destroy : true,
				"ajax" : {
					"type" : "POST",
					"url" : coursePackage.appPath
							+ "/coursePackage/pageListCoursePackage.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "coursePackageName" :form.find("input[name='coursePackageName']").val(),
										 "coursePackageNo" : form.find("input[name='coursePackageNo']").val(),
										 "cityName" :form.find("input[name='cityName']").val(),
										 "cencorStatus":form.find("select[name='cencorStatus']").val(),
										 "isEnable":form.find("select[name='isEnable']").val()
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
					{ "title":"课程包编号","data": "coursePackageNo"},
					{"title" : "课程包名称","data" : "coursePackageName"}, 
					{"title" : "城市","data" : "cityName"}, 
					{"title" : "销售价","data" : "price"}, 
					{"title" : "课程节数","data" : "courseNumber"}, 
					{"title" : "审核状态","data" : "cencorStatus"},
					{"title" : "启用状态","data" : "isEnable"},
					{"title" : "操作","orderable" : false} 
				],
				"dom": "<'row'<'#coursePackageTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				"initComplete" : function() {
					$("#coursePackageTools").html("");
				   	$("#coursePackageTools").css("float", "right");
				   	$("#coursePackageTools").removeClass("col-xs-6");
				   	$("#coursePackageTools").append('<button type="button" class="btn btn-default btn-sm coursePackageTools-operate-add btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd" >新增课程包</button>');
					$(".coursePackageTools-operate-add").on("click",function(){
	       				addTab("新增课程包", coursePackage.appPath+ "/coursePackage/toAddCoursePackage.do");
	       			});	
				},
				"drawCallback" : function(settings) {
					coursePackage.operateColumEvent();
				},
				"columnDefs" : [
					{
						"targets" : [ 5 ],
						"render" : function(a,
								b, c, d) {
							var text ="";
							if(a==0){
								text="未审核";
							}else if(a==1){
								text="已审核";
							}else if(a==2){
								text="未通过";
							}
							return text;
						}
					},
					{
						"targets" : [ 6 ],
						"render" : function(a,
								b, c, d) {
							var text ="";
							if(a==0){
								text="不可用";
							}else if(a==1){
								text="可用";
							}
							return text;
						}
					},
					{
						targets : [ 7 ],
						render : function(a, b, c,d) {
							var view = '<span class="glyphicon coursePackage-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="' + c.coursePackageNo + '" >查看</span>';
							var edit='<span class="glyphicon coursePackage-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.coursePackageNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
							var censorStatus = "";
							var isEnable = "";
							if(c.cencorStatus==0){
								censorStatus='<span class="glyphicon coursePackage-operate-cencor" data-name="'+c.coursePackageName+'" style="text-decoration: underline; cursor: pointer;" data-id="'+ c.coursePackageNo	+ '" >审核</span>';
							}else if(c.cencorStatus==1){
								if (c.isEnable==0) {
									isEnable='<span class="glyphicon coursePackage-operate-isEnable" style="text-decoration: underline; cursor: pointer;"data-name="'+c.coursePackageName+'" data-id="'+c.coursePackageNo+'"  data-isEnable=1 data-toggle="tooltip" data-placement="top">启用</span>';
								}else if(c.isEnable==1) {
									edit = "";
									isEnable='<span class="glyphicon coursePackage-operate-isEnable" style="text-decoration: underline; cursor: pointer;"data-name="'+c.coursePackageName+'" data-id="'+c.coursePackageNo+'" data-isEnable=0 data-toggle="tooltip" data-placement="top">停用</span>';
								}
							}
							return edit + censorStatus + isEnable +  view ;
						}
					}]
			});
		}
	};
	return coursePackage;
});
