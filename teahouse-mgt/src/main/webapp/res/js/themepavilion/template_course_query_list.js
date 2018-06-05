define([],function() {
	var templateCourseQuery = {
		appPath : getPath("app"),
		init : function() {
			$("#addQueryTemplateTr").click(function(){
				templateCourseQuery.addtemplateCourse();
			});
			
			//************************************排课日期、课程和教练新增
			//加载课程列表
			$("#selectQueryCourse").click(function() {
				$("#queryCourselistModal").modal("show");
				templateCourseQuery.pageCourseList();
			});
			
			//加载教练列表
			$("#selectQueryCoach").click(function() {
				$("#coachQuerylistModal").modal("show");
				templateCourseQuery.pageCoachList();
			});
			//选择日期数据后保存提交
			$("#selectQueryDateSub").click(function(){
				templateCourseQuery.addTemplateCourseDate();
			});
			
			//取消选择日期弹出层
			$("#onQueryCancelBtn").click(function(){
				$("#queryDateDivModal").modal("hide");
	        });
			
			//选择完教练、课程及课程人数后保存提交
			$("#onQueryFormBtn").click(function(){
				templateCourseQuery.addTemplateCourse();
			});
			//返回
			$("#cancelQueryCourseBtn").click(function(){
//				$(".modal-backdrop").remove();
				$("#querycourseAndCoachModal").modal("hide");
//				$("#querycourseAndCoachModal").modal("hide");
	        });
			
			//***********************************排课日期、选择课程、教练编辑
			//选择日期数据后保存提交
			$("#editQueryDateSub").click(function(){
				templateCourseQuery.editTemplateCourseDate();
			});
			
			//取消选择日期弹出层
			$("#cancelQueryEditBtn").click(function(){
				$("#editQueryDateDivModal").modal("hide");
	        });
			
			//选择完教练、课程及课程人数后保存提交
			$("#editQueryCourseBtn").click(function(){
				templateCourseQuery.editTemplateCourse();
			});
			//返回
			$("#cancelQueryEditCourseBtn").click(function(){
				$("#editQueryCourseAndCoachModal").modal("hide");
	        });
			
			//加载课程列表
			$("#selectQueryEditCourse").click(function() {
				$("#courseQueryEditListModal").modal("show");
				templateCourseQuery.pageCourseEditList();
			});
			
			//加载教练列表
			$("#selectQueryEditCoach").click(function() {
				$("#querycoachEditListModal").modal("show");
				templateCourseQuery.pageCoachEditList();
			});
			
			//查询
			$("#queryTemPlateCourse").click(function(){
				var form = $("form[name=templateCourseSerachForm]");
				var storeNo = form.find("select[name='storeNo']").val();
				closeTab("排课模板");
				addTab("排课模板",templateCourseQuery.appPath+ "/templateCourse/queryTemplateCourseList.do?storeNo="+storeNo);
			});
		},
		//添加表格行
		addtemplateCourse:function(){
			//获取选中的门店
			var form = $("form[name=templateCourseSerachForm]");
			var selectStore = form.find("select[name=storeNo]").val();
			if(selectStore == ""){
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择主题馆！");
			}else{
				$("#testMs").append("<tr>" +
						"<td onclick='queryDateDiv(0);' class='dataDiv'><input type='text' value='' style='border:0;outline:none;padding-left:20%;'/></td>" +
						"<td onclick='queryCourseAndCoach(1,0);' ><input type='text' value='' style='border:0;outline:none;padding-left:20%;'/></td>" +
						"<td onclick='queryCourseAndCoach(2,0);' ><input type='text' value='' style='border:0;outline:none;padding-left:20%;'/></td>" +
						"<td onclick='queryCourseAndCoach(3,0);' ><input type='text' value='' style='border:0;outline:none;padding-left:20%;'/></td>" +
						"<td onclick='queryCourseAndCoach(4,0);' ><input type='text' value='' style='border:0;outline:none;padding-left:20%;'/></td>" +
						"<td onclick='queryCourseAndCoach(5,0);' ><input type='text' value='' style='border:0;outline:none;padding-left:20%;'/></td>" +
						"<td onclick='queryCourseAndCoach(6,0);' ><input type='text' value='' style='border:0;outline:none;padding-left:20%;'/></td>" +
						"<td onclick='queryCourseAndCoach(7,0);' ><input type='text' value='' style='border:0;outline:none;padding-left:20%;'/></td>" +
						"<td id='delTemplate' onclick='delTemplate(this);'>删除</td>" +
						"</tr>");
			}
		},
		
		//jquery格式化日期比较大小
		comptime:function(a,b){
			var dateA = new Date("1900/1/1 " + a);   
			var dateB = new Date("1900/1/1 " + b);   
			if(isNaN(dateA) || isNaN(dateB)) return null;   
			if(dateA > dateB) return 1;   
			if(dateA < dateB) return -1;   
			return 0; 
		},
		
		//保存排课模板表数据
		addTemplateCourseDate:function(){
			var form = $("form[name=dateForm]");
			form.ajaxSubmit({
				url : templateCourseQuery.appPath + "/templateCourse/saveOrUpDateTemplateCourse.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					var courseData = res.data;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"新增成功！", function() {
							$("#queryDateDivModal").modal("hide");
							var i = courseData.ftlRow-1;
							$("#testMs tr:eq("+i+") > td:eq(0)").find('input').val(courseData.showDate);
							
							$(".modal-backdrop").remove();//手动去掉遮罩层
							closeTab("排课模板");
							addTab("排课模板",templateCourseQuery.appPath+ "/templateCourse/toTemplateCourseList.do?storeNo="+courseData.storeNo);
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"新增失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					 var beginDate=form.find("input[name=courseStart]").val();  
					 var endDate=form.find("input[name=courseEnd]").val();  
					 
					 if(templateCourseQuery.comptime(beginDate,endDate) != -1){
						 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");  
						 return false;  
					 }
					 if(beginDate == ""){
						 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入开始日期！");
						 return false;
					 }
					 if(endDate == ""){
						 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入结束日期！");
						 return false;
					 }
				}
			});
		},
		
		//编辑排课时间
		editTemplateCourseDate:function(){
			var form = $("form[name=editDateForm]");
			form.ajaxSubmit({
				url : templateCourseQuery.appPath + "/templateCourse/saveOrUpDateTemplateCourse.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					var courseData = res.data;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"编辑成功！", function() {
							$("#editQueryDateDivModal").modal("hide");
							var i = courseData.ftlRow-1;
							$("#testMs tr:eq("+i+") > td:eq(0)").find('input').val(courseData.showDate);
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"编辑失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					 var beginDate=form.find("input[name=courseStart]").val();  
					 var endDate=form.find("input[name=courseEnd]").val();  
					 
					 if(templateCourseQuery.comptime(beginDate,endDate) != -1){
						 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");  
						 return false;  
					 }
					 if(beginDate == ""){
						 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入开始日期！");
						 return false;
					 }
					 if(endDate == ""){
						 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入结束日期！");
						 return false;
					 }
				}
			});
		},
		
		//保存排课模板表数据
		addTemplateCourse:function(){
			debugger
			var form = $("form[name=courseAndCoachForm]");
			form.ajaxSubmit({
				url : templateCourseQuery.appPath + "/templateCourse/saveOrUpDateTemplateCourse.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					var courseData = res.data;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"新增成功！", function() {
							$("#querycourseAndCoachModal").modal("hide");
							var i = courseData.ftlRow-1;
							var trList = $("#testMs").children("tr:eq("+i+")");
							
							for (var i=0;i<trList.length;i++) {
						        var tdArr = trList.eq(i).find("td");
						        //set课程名称
						        if(courseData.courseWeek == '周一'){
						        	tdArr.eq(1).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周二'){
						        	tdArr.eq(2).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周三'){
						        	tdArr.eq(3).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周四'){
						        	tdArr.eq(4).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周五'){
						        	tdArr.eq(5).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周六'){
						        	tdArr.eq(6).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周日'){
						        	tdArr.eq(7).find('input').val(courseData.chineseName);
						        }else{
						        	return ;
						        }
						    }
							
							$(".modal-backdrop").remove();//手动去掉遮罩层
							closeTab("排课模板");
							addTab("排课模板",templateCourseQuery.appPath+ "/templateCourse/queryTemplateCourseList.do?storeNo="+courseData.storeNo);
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"新增失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					var chineseName = form.find("input[name=chineseName]").val();
					var coachName = form.find("input[name=coachName]").val();
					var peopleNumber = form.find("input[name=peopleNumber]").val();
					
					var numberReg = /^[1-9]\d*$/;
					
					if(chineseName == ""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择课程！");
						return false;
					}
					if(coachName == ""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择教练！");
						return false;
					}
					if(peopleNumber == ""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入人数！");
						return false;
					}
					if(!numberReg.test(peopleNumber)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对不起、输入人数格式不对（正确格式为正整数）！");
						return false;
					}
				}
			});
		},
		
		//编辑排课课程、教练和人数
		editTemplateCourse:function(){
			debugger
			var form = $("form[name=editCourseAndCoachForm]");
			form.ajaxSubmit({
				url : templateCourseQuery.appPath + "/templateCourse/saveOrUpDateTemplateCourse.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					var courseData = res.data;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"编辑成功！", function() {
							$("#editQueryCourseAndCoachModal").modal("hide");
							var i = courseData.ftlRow-1;
							var trList = $("#testMs").children("tr:eq("+i+")");
							
							for (var i=0;i<=trList.length;i++) {
						        var tdArr = trList.eq(i).find("td");
						        //set课程名称
						        if(courseData.courseWeek == '周一'){
						        	tdArr.eq(1).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周二'){
						        	tdArr.eq(2).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周三'){
						        	tdArr.eq(3).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周四'){
						        	tdArr.eq(4).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周五'){
						        	tdArr.eq(5).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周六'){
						        	tdArr.eq(6).find('input').val(courseData.chineseName);
						        }else if(courseData.courseWeek == '周日'){
						        	tdArr.eq(7).find('input').val(courseData.chineseName);
						        }else{
						        	return ;
						        }
						    }
							
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"编辑失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					var chineseName = form.find("input[name=chineseName]").val();
					var coachName = form.find("input[name=coachName]").val();
					var peopleNumber = form.find("input[name=peopleNumber]").val();
					
					var numberReg =/^[1-9]\d*$/;
					
					if(chineseName == ""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择课程！");
						return false;
					}
					if(coachName == ""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择教练！");
						return false;
					}
					if(peopleNumber == ""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入人数！");
						return false;
					}
					if(!numberReg.test(peopleNumber)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "对不起、输入人数格式不对（正确格式为正整数）！");
						return false;
					}
				}
			});
		},
		
		//已经审核认证的教练列表
		pageCoachList:function(){
			var coachBtnTplAdd = $("#coachBtnTplAdd").html();
			// 预编译模板
			var template = Handlebars.compile(coachBtnTplAdd);
			var table = $('#coachQueryListAdd').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : templateCourseQuery.appPath+"/templateCourse/pageListAuditCoach.do",
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
				"dom" : "<'row'<'#coachToolssssAdd.col-xs-6'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#coachToolssssAdd").append('<button type="button"  class="btn btn-default btn-sm sysworkerMgCar-batch-adddel">选择</button>');
						$("#coachToolssssAdd").append('<button type="button"  class="btn btn-default btn-sm sysworkerMgCar-batch-addclose">关闭</button>');
						$(".sysworkerMgCar-batch-adddel").on("click",function(){
							var ids=[];
							var coachName="";
							var len=$('#coachQueryListAdd tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择教练！")
							}else{
								$("#coachQueryListAdd tbody").find("input:checked").each(function(){
		        					ids.push($(this).val());
		        					coachName = $(this).next('input').val();
		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "教练只能选择一个！")
	        					}else{
	        						var form = $("form[name=courseAndCoachForm]");
	        	     				form.find("input[name='coachNo']").val(ids);
	        	     				form.find("input[name='coachName']").val(coachName);
	        	     				$("#coachQuerylistModal").modal("hide");
	        	     				$(".modal-backdrop").hide();
	        					}
							}
							
							$('#coachQueryListAdd thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#coachQueryListAdd tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#coachQueryListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysworkerMgCar-batch-addclose").on("click",function(){
							$("#coachQuerylistModal").modal("hide");
							$(".modal-backdrop").hide();
							$('#coachQueryListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
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
						},
						{
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
						},
					   {
						"targets" : [0],
						 "orderable":false,
						"render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="coachNo" value="' + full.coachNo + '"><input type="hidden" value="' + full.coachName + '">';
						}
					   }
					   ]
			});
		},
		
		//已启用的课程列表
		pageCourseList:function(){
			var courseBtnTplAdd = $("#courseBtnTplAdd").html();
			// 预编译模板
			var template = Handlebars.compile(courseBtnTplAdd);
			var table = $('#courseQueryListAdd').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : templateCourseQuery.appPath+"/templateCourse/pageListAuditCourse.do",
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
				{ "title":"","data": "courseNo","width":"5px"},
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
				}],
				"dom" : "<'row'<'#courseToolssssAdd.col-xs-6'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#courseToolssssAdd").append('<button type="button"  class="btn btn-default btn-sm sysworkerMgCar-batch-adddel">选择</button>');
						$("#courseToolssssAdd").append('<button type="button"  class="btn btn-default btn-sm sysworkerMgCar-batch-addclose">关闭</button>');
						$(".sysworkerMgCar-batch-adddel").on("click",function(){
							var ids=[];
							var chineseName="";
							var len=$('#courseQueryListAdd tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择课程！")
							}else{
								$("#courseQueryListAdd tbody").find("input:checked").each(function(){
		        					ids.push($(this).val());
		        					chineseName = $(this).next('input').val();
		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "课程只能选择一个！")
	        					}else{
	        						var form = $("form[name=courseAndCoachForm]");
	        	     				form.find("input[name='courseNo']").val(ids);
	        	     				form.find("input[name='chineseName']").val(chineseName);
	        	     				$("#queryCourselistModal").modal("hide");
	        	     				$(".modal-backdrop").hide();
	        					}
							}
							
							$('#courseQueryListAdd thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#courseQueryListAdd tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#courseQueryListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysworkerMgCar-batch-addclose").on("click",function(){
							$("#queryCourselistModal").modal("hide");
							$(".modal-backdrop").hide();
							$('#courseQueryListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
						});
				  },
				"columnDefs" : [
						{
							"targets" : [ 7 ],
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
						"targets" : [0],
						 "orderable":false,
						"render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="courseNo" value="' + full.courseNo + '"><input type="hidden" value="' + full.chineseName + '">';
						}
					   }
					   ]
			});
		},
		
		
		
		//************编辑时选择
		//已经审核认证的教练列表
		pageCoachEditList:function(){
			var coachBtnTplEdit = $("#coachBtnTplEdit").html();
			// 预编译模板
			var template = Handlebars.compile(coachBtnTplEdit);
			var table = $('#coachQueryListEdit').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : templateCourseQuery.appPath+"/templateCourse/pageListAuditCoach.do",
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
							var len=$('#coachQueryListEdit tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择教练！")
							}else{
								$("#coachQueryListEdit tbody").find("input:checked").each(function(){
		        					ids.push($(this).val());
		        					coachName = $(this).next('input').val();
		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "教练只能选择一个！")
	        					}else{
	        						var form = $("form[name=editDateForm]");
	        	     				form.find("input[name='coachNo']").val(ids);
	        	     				form.find("input[name='coachName']").val(coachName);
	        	     				$("#querycoachEditListModal").modal("hide");
	        	     				$(".modal-backdrop").hide();
	        					}
							}
							
							$('#coachQueryListEdit thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#coachQueryListEdit tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#coachQueryListEdit tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysworkerMgCar-batch-addclose").on("click",function(){
							$("#querycoachEditListModal").modal("hide");
							$(".modal-backdrop").hide();
							$('#coachQueryListEdit tbody input[type="checkbox"]:checked').prop("checked",false);
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
		
		//已启用的课程列表
		pageCourseEditList:function(){
			var courseBtnTplEdit = $("#courseBtnTplEditQuery").html();
			// 预编译模板
			var template = Handlebars.compile(courseBtnTplEdit);
			var table = $('#courseListEditQuery').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : templateCourseQuery.appPath+"/templateCourse/pageListAuditCourse.do",
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
				{ "title":"","data": "courseNo","width":"5px"},
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
				}],
				"dom" : "<'row'<'#courseToolsEdit.col-xs-6'><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#courseToolsEdit").append('<button type="button"  class="btn btn-default btn-sm sysworkerMgCar-batch-adddel">选择</button>');
						$("#courseToolsEdit").append('<button type="button"  class="btn btn-default btn-sm sysworkerMgCar-batch-addclose">关闭</button>');
						$(".sysworkerMgCar-batch-adddel").on("click",function(){
							var ids=[];
							var chineseName="";
							var len=$('#courseListEditQuery tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择课程！")
							}else{
								$("#courseListEditQuery tbody").find("input:checked").each(function(){
		        					ids.push($(this).val());
		        					chineseName = $(this).next('input').val();
		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "课程只能选择一个！")
	        					}else{
	        						var form = $("form[name=editCourseAndCoachForm]");
	        	     				form.find("input[name='courseNo']").val(ids);
	        	     				form.find("input[name='chineseName']").val(chineseName);
	        	     				$("#courseQueryEditListModal").modal("hide");
	        	     				$(".modal-backdrop").hide();
	        					}
							}
							
							$('#courseListEditQuery thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#courseListEditQuery tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#courseListEditQuery tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysworkerMgCar-batch-addclose").on("click",function(){
							$("#courseQueryEditListModal").modal("hide");
							$(".modal-backdrop").hide();
							$('#courseListEditQuery tbody input[type="checkbox"]:checked').prop("checked",false);
						});
				  },
				"columnDefs" : [
						{
							"targets" : [ 7 ],
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
						},{
						"targets" : [0],
						 "orderable":false,
						"render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="courseNo" value="' + full.courseNo + '"><input type="hidden" value="' + full.chineseName + '">';
						}
					   }
					   ]
			});
		},
		
	};
	return templateCourseQuery;
})
function queryDateDiv(dateId){
	var form = $("form[name=templateCourseSerachForm]");
	var selectStore = form.find("select[name=storeNo]").val();
	if(dateId == '0'){
		$("#queryDateDivModal").modal("show");
		var form = $("form[name=dateForm]");
		form.find("input[name=courseDateStart]").val("");
		form.find("input[name=courseDateEnd]").val("");
		$("#querycourseAndCoachModal").modal("hide");
		//获取当前是第几行
		$("#testMs tr").off().click(function(){
			var row = $(this).index()+1;//这个前面得到的是tr的序号，从0开始，要得到第几行，+1即可
			form.find("input[name=ftlRow]").val(row);
			form.find("input[name=storeNo]").val(selectStore);
		})
	}
	else{
		$.post('templateCourse/getTemplateCourse.do', {id:dateId}, 
				function(result) {
					var dataInfo = result.data;
					if(result.code == '1'){
						$("#querycourseAndCoachModal").modal("hide");
						$("#editQueryDateDivModal").modal("show");
//						$(".modal-backdrop").remove();
						var form = $("form[name=editDateForm]");
						form.find("input[name=templateCourseNo]").val(dataInfo.templateCourseNo);
						var courseStart = moment(dataInfo.courseStart);
						form.find("input[name=courseStart]").val(courseStart.format('HH:mm'));
						var courseEnd = moment(dataInfo.courseEnd);
						form.find("input[name=courseEnd]").val(courseEnd.format('HH:mm'));
						//获取当前是第几行
						$("#testMs tr").off().click(function(){
							var row = $(this).index()+1;//这个前面得到的是tr的序号，从0开始，要得到第几行，+1即可
							form.find("input[name=ftlRow]").val(row);
						})
					}
			},"json");
	}
	
}
function queryCourseAndCoach(weekNo,courseId){
	var v = "";
	var form = $("form[name=templateCourseSerachForm]");
	var selectStore = form.find("select[name=storeNo]").val();
	if(courseId == "0" || courseId == ""){
		$("#testMs tr").off().click(function() {
		       v = $(this).children('td').eq(0).find('input').val();
		       if(v == ""){
		    	   alert("请先添加课程时间！");
		    	   return false;
		       }else{
		    	   $("#querycourseAndCoachModal").modal("show");
		    	   
		    	   var row = $(this).index()+1;
		   		   var form = $("form[name=courseAndCoachForm]");
		   		   form.find("input[name=ftlRow]").val(row);
		   		   form.find("input[name=courseWeek]").val(weekNo);
		   		   form.find("input[name=storeNo]").val(selectStore);
		       }
		});
	}else{
		$.post('templateCourse/getTemplateCourse.do', {id:courseId}, 
				function(result) {
					var dataInfo = result.data;
					if(result.code == '1'){
					   $("#querycourseAndCoachModal").modal("hide");
					   $(".modal-backdrop").remove();
					   $("#editQueryCourseAndCoachModal").modal("show");
					   var form = $("form[name=editCourseAndCoachForm]");
					   form.find("input[name=courseWeek]").val(weekNo);
			   		   form.find("input[name=templateCourseNo]").val(dataInfo.templateCourseNo);
			   		   form.find("input[name=chineseName]").val(dataInfo.chineseName);
			   		   form.find("input[name=coachName]").val(dataInfo.coachName);
			   		   form.find("input[name=peopleNumber]").val(dataInfo.peopleNumber);
						//获取当前是第几行
						$("#testMs tr").off().click(function() {
						       v = $(this).children('td').eq(0).find('input').val();
						       if(v == ""){
						    	   alert("请先添加课程时间！");
						    	   return false;
						       }else{
						    	   var row = $(this).index()+1;
						   		   form.find("input[name=ftlRow]").val(row);
						       }
						});
					}
			},"json");
	}
}
//删除
function delTemplate(sub){
	bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要删除吗？", function(result) {
		if(result){
			var row = $(sub).parent("tr").prevAll().size()+1;//行号
			var courseTime = $(sub).siblings(".dataDiv").children("input").val();
			//获取门店编号
			var form = $("form[name=templateCourseSerachForm]");
			var storeNo =  form.find("select[name=storeNo]").val();
			//若该行没有选择排课时间则直接删除行信息
			if(courseTime == ""){
				$(sub).parent().remove();
			}else{
				//否则进行逻辑删除，直接从数据库删除。
				$.post("templateCourse/delTemplateCourse.do",{rowNo:row,storeNo:storeNo},function(res){	
					if(res.code==1){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除成功！",function(){
							$(sub).parent().remove();
						 });
					}else{
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "删除失败！");
					}
				});
			}
		}						
	});
}