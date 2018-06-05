define([],function() {
	var coachList = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#coachSearch").click(function() {
				coachList.pageList();
			});
			// 列表页面分页方法调用
			coachList.pageList();
			
		},
		operateColumEvent : function() {
			$(".coach-operate-edit").each(function() {
				$(this).on("click", function() {
					addTab("教练编辑",coachList.appPath+ "/coach/toCoachEdit.do?coachNo="+$(this).data("id"));
				});
			});
			$(".coach-operate-viewCourse").each(function() {
				$(this).on("click", function() {
					addTab("查看教练课程",coachList.appPath+ "/coach/toCoachCourse.do?coachNo="+$(this).data("id"));
				});
			});
			$(".coach-operate-audit").each(function() {
				$(this).on("click", function() {
					addTab("教练审核",coachList.appPath+ "/coach/toCoachAudit.do?coachNo="+$(this).data("id"));
				});
			});
			
			//下载二维码
			$(".coach-operate-qrcode").each(function(id,obj){
				$(this).on("click",function(){
					var coachNo=$(this).data("id");
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要下载二维码吗？", function(result) {
						if(result){
							window.location.href = coachList.appPath+ "/coach/downloadCoachQrcode.do?coachNo="+coachNo;
						}						
       				});
				});
			});
			//教练启用
			$(".coach-operate-enable").each(function() {
				$(this).on("click", function() {
					var coachNo = $(this).data("id");
					var status = "1";//启用状态
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要启用该教练吗？", function(result) {
						if(result){
							$.post("coach/operationCoachStatus.do",{coachNo:coachNo,status:status},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "启用成功！",function(){
										$(".bootbox").modal("hide");
										$("#coachList").DataTable().ajax.reload(function(){
				    					}); 
									  });
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "启用失败！");
								}
							});
						}						
       				});
					
				});
			});
			//教练停用
			$(".coach-operate-disable").each(function() {
				$(this).on("click", function() {
					var coachNo = $(this).data("id");
					var status = "0";//停用状态
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要停用该教练吗？", function(result) {
						if(result){
							$.post("coach/operationCoachStatus.do",{coachNo:coachNo,status:status},function(res){	
								if(res.code==1){
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "停用成功！",function(){
										$(".bootbox").modal("hide");
										$("#coachList").DataTable().ajax.reload(function(){
				    					}); 
									  });
								}else{
									bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "停用失败！");
								}
							});
						}						
       				});
					
				});
			});
		},
	
		pageList : function() {
			var form = $("form[name=coachSerachForm]");
			var mobilePhone = $.trim(form.find("input[name='mobilePhone']").val());
			var coachName = $.trim(form.find("input[name='coachName']").val());
			var coachBtnTpl = $("#coachBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(coachBtnTpl);
			var table = $('#coachList')
			.dataTable(
					{
						searching : false,
						destroy : true,
						"ajax" : {
							"type" : "POST",
							"url" : coachList.appPath+ "/coach/coachPageList.do",
							"data" : function(d) {
								return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "mobilePhone" :mobilePhone,
										 "coachName":coachName
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
						},{
							"title" : "是否启用",
							"data" : "coachStatus"
						},{
							"title" : "操作",
							"orderable" : false
						} ],
						"dom": "<'row'<'#coachTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
						   initComplete: function () {
						   },
						"drawCallback" : function(settings) {
							coachList.operateColumEvent();
						},
						"order" : [ [ 1, "desc" ] ],
						"columnDefs" : [
								{
									"targets" : [ 2],
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
									"targets" : [ 3 ],
									"orderable" : false,
									"render" : function(a, b, c, d) {
										if(a!=null){
											var now = moment(a);
											return now.format('YYYY-MM-DD HH:mm:ss');
										}else{
											return "--";
										}
									}
								},
								{
									"targets" : [ 4 ],
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
									"targets" : [ 5 ],
									"render" : function(a, b, c, d) {
										var enableName;
										//（0、未启用，1、已启用，默认0）
										if(a==0){
											enableName="未启用";
										}else if(a==1){
											enableName="已启用";
										}else{
											return "--";
										}
										return enableName;
									}
								},
								{
									targets : 6,
									render : function(a, b, c, d) {
										var edit = '<span class="glyphicon coach-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
												+ c.coachNo + '" >编辑</span>';
										var viewCourse = '<span class="glyphicon coach-operate-viewCourse"" style="text-decoration: underline; cursor: pointer;" data-id="'
												+ c.coachNo + '" >查看课程</span>';
										var audit = "";
										if(c.censorStatus == 0 || c.censorStatus == 2 || c.censorStatus == 3){
											audit = '<span class="glyphicon coach-operate-audit"" style="text-decoration: underline; cursor: pointer;" data-id="'
												+ c.coachNo + '" >审核</span>';
										}
										var enable = "";
										var disable = "";
										if(c.censorStatus == 1){
											if(c.coachStatus == 0){
												enable = '<span class="glyphicon coach-operate-enable"" style="text-decoration: underline; cursor: pointer;" data-id="'
													+ c.coachNo + '" >启用</span>';
											}else{
												disable = '<span class="glyphicon coach-operate-disable"" style="text-decoration: underline; cursor: pointer;" data-id="'
													+ c.coachNo + '" >停用</span>';
											}
										}
										//var qrcode='<span class="glyphicon coach-operate-qrcode" data-id="'+c.coachNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">下载二维码</span>';
										return edit+audit+enable+disable+viewCourse;
									}
								}]
					});
		},
	};
	return coachList;
});