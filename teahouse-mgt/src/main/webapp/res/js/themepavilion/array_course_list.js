define([],function() {
	var arrayCourse = {
		appPath : getPath("app"),
		init : function() {
			//排课弹出层
			$("#addArrayCourse").click(function(){
				var form = $("form[name=arrayCourseSerachForm]");
				var storeNo = form.find("select[name='storeNo']").val();
				if(storeNo == ""){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择主题馆！");
					return false;
				}else{
					$("#addArrayCourseModal").modal("show");
					var addForm = $("form[name=arrayCourseForm]");
					addForm.find("input[name='storeNo']").val(storeNo);
				}
			});
			
			//选择周次后保存提交
			$("#addArrayCourseSub").click(function(){
				arrayCourse.saveArrayCourse();
			});
			
			//取消弹出层
			$("#cancelEditBtn").click(function(){
				$("#addArrayCourseModal").modal("hide");
	        });
			
			//查询
			$("#arrayCourseSearch").click(function(){
				var form = $("form[name=arrayCourseSerachForm]");
				var storeNo = form.find("select[name='storeNo']").val();
				closeTab("排课");
				addTab("排课",arrayCourse.appPath+ "/arrayCourse/toArrayCourseList.do?storeNo="+storeNo);
			});
			
			//加载教练列表
			$("#selectedCoach").click(function() {
				$("#coachModifiyistModal").modal("show");
				arrayCourse.pageCoachList();
			});
			
			//返回
			$("#cancelBtn").click(function(){
				$("#modifiyCoachDivModal").modal("hide");
	        });
			
			//保存已修改的教练信息
			$("#modifiyCourseBtn").click(function(){
				arrayCourse.modifiyCoach();
			});
			
			//发布
			$("#postedArrayCourse").click(function(){
				bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要发布本周课程吗？", function(result) {
					if(result){
						arrayCourse.checkArrayCourse();
					}						
				});
			});
			
		},
		
		//发布主题馆本周课程
		postedArrayCourse:function(){
			var form = $("form[name=arrayCourseSerachForm]");
			form.ajaxSubmit({
				url : arrayCourse.appPath + "/arrayCourse/postedArrayCourse.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					var courseData = res.data;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"发布成功", function() {
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"发布失败");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					
				}
			});
		},
		
		//发布之前判断该主题馆课程是否已经发布 
		checkArrayCourse:function() {
		  var form = $("form[name=arrayCourseSerachForm]");
		  var storeNo=form.find("input[name=selectStoreNo]").val();
		  $.ajax({
			    url:arrayCourse.appPath+"/arrayCourse/uniqueArrayCourse.do",
				type:"post",
				data:{storeNo:storeNo},
				success:function(res){ 
					if(res.code == "1"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "该主题馆本周课程已经发布！");
					}else{
						form.ajaxSubmit({
							url : arrayCourse.appPath + "/arrayCourse/postedArrayCourse.do",
							type : "post",
							success : function(res) {
								var msg = res.msg;
								var code = res.code;
								var courseData = res.data;
								if (code == "1") {
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"发布成功", function() {
									});
								} else {
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"发布失败");
								}
							},
							beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
								
							}
						});
					}
				}
		  });
		},
		
		//修改教练
		modifiyCoach:function(){
			var form = $("form[name=modifiyCoachForm]");
			form.ajaxSubmit({
				url : arrayCourse.appPath + "/arrayCourse/updateArrayCourseCoach.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					var courseData = res.data;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"修改成功", function() {
							$("#modifiyCoachDivModal").modal("hide");
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"修改失败");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					var arrayCourseNo=form.find("input[name=arrayCourseNo]").val();
					if(arrayCourseNo == ""){
						 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择教练！");
						 return false;
					}
				}
			});
		},
		
		//已经审核认证的教练列表
		pageCoachList:function(){
			var coachBtnTplModifiy = $("#coachBtnTplModifiy").html();
			// 预编译模板
			var template = Handlebars.compile(coachBtnTplModifiy);
			var table = $('#coachListModifiy').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : arrayCourse.appPath+"/templateCourse/pageListAuditCoach.do",
					"data" : function(d) {
						return $.extend({},d,
							{"pageNo" : (d.start / d.length) + 1,
							 "pageSize" : d.length
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
				{ "title":"","data": "coachNo","width":"5px"},
				{
					"title" : "手机号",
					"data" : "mobilePhone"
				},{
					"title" : "姓名",
					"data" : "coachName"
				},{
					"title" : "性别",
					"data" : "sex"
				},{
					"title" : "注册时间",
					"data" : "registerTime"
				},{
					"title" : "审核状态",
					"data" : "censorStatus"
				}],
				"dom" : "<'row'<'#coachToolssssEdit.col-xs-6'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#coachToolssssEdit").append('<button type="button"  class="btn btn-default btn-sm sysworkerMgCar-batch-adddel">选择</button>');
						$("#coachToolssssEdit").append('<button type="button"  class="btn btn-default btn-sm sysworkerMgCar-batch-addclose">关闭</button>');
						$(".sysworkerMgCar-batch-adddel").on("click",function(){
							var ids=[];
							var coachName="";
							var len=$('#coachListModifiy tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择教练！")
							}else{
								$("#coachListModifiy tbody").find("input:checked").each(function(){
		        					ids.push($(this).val());
		        					coachName = $(this).next('input').val();
		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "教练只能选择一个！")
	        					}else{
	        						var form = $("form[name=modifiyCoachForm]");
	        	     				form.find("input[name='coachNo']").val(ids);
	        	     				form.find("input[name='coachName']").val(coachName);
	        	     				$("#coachModifiyistModal").modal("hide");
	        	     				$(".modal-backdrop").hide();
	        					}
							}
							
							$('#coachListModifiy thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#coachListModifiy tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#coachListModifiy tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysworkerMgCar-batch-addclose").on("click",function(){
							$("#coachModifiyistModal").modal("hide");
							$(".modal-backdrop").hide();
							$('#coachListModifiy tbody input[type="checkbox"]:checked').prop("checked",false);
						});
				  },
				"columnDefs" : [
						{
							"targets" : [ 3],
							"render" : function(a, b, c, d) {
								var sexName;
								//性别（0、女，1、男）
								if(a==0){
									sexName="女";
								}else if(a==1){
									sexName="男";
								}else{
									return "--";
								}
								return sexName;
							}
						},{
							"targets" : [ 5 ],
							"render" : function(a, b, c, d) {
								var censorName;
								//（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
								if(a==0){
									censorName="未审核/未认证";
								}else if(a==1){
									censorName="已审核/已认证";
								}else if(a==2){
									censorName="待审核/待认证";
								}else if(a==3){
									censorName="未通过";
								}else{
									return "--";
								}
								return censorName;
							}
						},{
						"targets" : [0],
						 "orderable":false,
						"render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="coachNo" value="' + full.coachNo + '"><input type="hidden" value="' + full.coachName + '">';
						}
					   }
					   ]
			});
		},
		
		saveArrayCourse:function(){
			debugger
			var form = $("form[name=arrayCourseForm]");
			form.ajaxSubmit({
				url : arrayCourse.appPath + "/arrayCourse/saveArrayCourse.do",
				type : "post",
				async: true,
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					var courseData = res.data;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"排课成功！", function() {
							$("#addArrayCourseModal").modal("hide");
							$(".modal-backdrop").remove();//手动去掉遮罩层
							closeTab("排课");
							addTab("排课",arrayCourse.appPath+ "/arrayCourse/toArrayCourseList.do?storeNo="+courseData.storeNo);
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					var courseType=form.find("select[name=courseType]").val();
					if(courseType == ""){
						 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择周次！");
						 return false;
					}
				}
			});
		},
		
	};
	return arrayCourse;
})
//修改教练
function modifiyCoach(arrayCourseId){
	if(arrayCourseId != ""){
		$.post('arrayCourse/modifiyCoach.do', {arrayCourseId:arrayCourseId}, 
				function(result) {
					var dataInfo = result.data;
					if(result.code == '1'){
						$("#modifiyCoachDivModal").modal("show");
						var form = $("form[name=modifiyCoachForm]");
						form.find("input[name=arrayCourseNo]").val(dataInfo.arrayCourseNo);
						form.find("input[name=chineseName]").val(dataInfo.chineseName);
						form.find("input[name=peopleNumber]").val(dataInfo.peopleNumber);
						form.find("input[name=coachName]").val(dataInfo.coachName);
					}
			},"json");
	}
}