define([],function() {
	var coachSignList = {
		appPath : getPath("app"),
	init : function() {
	// 列表页面搜索调用
	$("#coachSignSearch").click(function() {
		coachSignList.pageList();
	});
	
	//审核通过状态提交
	$("#submitCoachClass").click(function(){
		coachSignList.auditThroughSub();
    });
	//取消
	$("#coachClassCancelBtn").click(function(){
		$("#coachClassModal").modal("hide");
    });
	// 列表页面分页方法调用
	coachSignList.pageList();
	
	},
	operateColumEvent : function() {
	//教练上课审核
		$(".sign-operate-audit").each(function() {
			$(this).on("click", function() {
				var siapId=$(this).data("id");
				$.ajax({
				 type: "post",
	             url: coachSignList.appPath+"/coachSign/toCoachClassAudit.do",
	             data: {siapId:siapId},
	             dataType: "json",
	             success: function(data){
	            	 	if(data.code="1"){
		            	    $("#coachClassModal").modal("show");
		            	    $("body")[0].style.paddingRight=0;
							$("#siapId1").val(data.siapId);
							$("#classAuditMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定要审核该教练上课记录吗？");
					 	}
	             	}
				});
			});
		});
	},

	auditThroughSub: function () {
		var form = $("form[name=coachClassForm]");
		form.ajaxSubmit({
			url:coachSignList.appPath+"/coachSign/coachClassAudit.do",
			type : "post",
			dataType:"json", //数据类型  
			success:function(res){
				var msg = res.msg;
				var code = res.code;
				if(code == "1"){ 
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
						$("#coachClassModal").modal('hide')
					});
				
					$("#coachSignList").DataTable().ajax.reload(function(){}); 
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				if (form.find("textarea[name='coachAppraise']").val() == "") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入审核通过理由！");
					return false;
				}
			}
		});
	},
	
	pageList : function() {
		var form = $("form[name=coachSignSerachForm]");
		var coachName = $.trim(form.find("input[name='coachName']").val());//教练名称
		var coachSignBtnTpl = $("#coachSignBtnTpl").html();
		// 预编译模板
		var template = Handlebars.compile(coachSignBtnTpl);
		var table = $('#coachSignList').dataTable(
			{
				searching : false,
				destroy : true,
				"ajax" : {
					"type" : "POST",
					"url" : coachSignList.appPath+ "/coachSign/coachSignPageList.do",
					"data" : function(d) {
						return $.extend({},d,
								{"pageNo" : (d.start / d.length) + 1,
								 "pageSize" : d.length,
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
					"title" : "门店名称",
					"data" : "storeName"
				},{
					"title" : "教练名称",
					"data" : "coachname"
				},{
					"title" : "课程名称",
					"data" : "chineseName"
				},{
					"title" : "签到时间",
					"data" : "signDate"
				},{
					"title" : "上课时间",
					"data" : "courseDate"
				},{
					"title" : "是否签到",
					"data" : "isSign"
				},{
					"title" : "是否完成上课",
					"data" : "isFinish"
				},{
					"title" : "上课审核状态",
					"data" : "signStatus"
				},{
					"title" : "更新时间",
					"data" : "updateTime"
				},{
					"title" : "操作",
					"orderable" : false
				} ],
				"dom": "<'row'<'#coachTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
				   },
				"drawCallback" : function(settings) {
					coachSignList.operateColumEvent();
				},
				"order" : [ [ 1, "desc" ] ],
				"columnDefs" : [
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
						"targets" : [5],
						"render" : function(a, b, c, d) {
							var signName;
							//性别（0、未签到，1、已签到）
							if(a==0){
								signName="未签到";
							}else if(a==1){
								signName="已签到";
							}else{
								return "--";
							}
							return signName;
						}
					},
					{
						"targets" : [ 6 ],
						"orderable" : false,
						"render" : function(a, b, c, d) {
							var finishName = "";
							if(a == 0){
								finishName = "未完成";
							}else if(a ==1){
								finishName = "已完成";
							}else{
								return "--";
							}
							return finishName;
						}
					},
					{
						"targets" : [ 7 ],
						"render" : function(a, b, c, d) {
							var censorName;
							//（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
							if(a==0){
								censorName="未审核";
							}else if(a==1){
								censorName="已审核";
							}else{
								return "--";
							}
							return censorName;
						}
					},{
						"targets" : [ 8 ],
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
						targets : 9,
						render : function(a, b, c, d) {
							var audit = "";
							var bh = "";
							if(c.isSign == 1 && c.isFinish == 1){
								if(c.signStatus == 0){
									audit = '<span class="glyphicon sign-operate-audit"" style="text-decoration: underline; cursor: pointer;" data-id="'
										+ c.siapId + '" >审核通过</span>';
								}else{
									bh = "审核通过";
								}
								
							}
							return audit+bh;
					}
				}]
			});
		},
	};
	return coachSignList;
});