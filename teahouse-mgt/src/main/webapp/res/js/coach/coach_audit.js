define([],function() {
var coachAudit = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	init : function() {
		//添加提交
		$("#auditCoach").click(function(){
			coachAudit.auditCoach();
		});
		//返回
		$("#closeAuditCoach").click(function(){
			closeTab("教练审核");
			$("#coachList").DataTable().ajax.reload(function(){});
		});
		
	},
	auditCoach:function() {
		var form = $("form[name=coachAuditForm]");
		form.ajaxSubmit({
			url : coachAudit.appPath + "/coach/changeCoachAuditStatus.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"审核成功！", function() {
						closeTab("教练审核");
						$("#coachList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核失败！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='censorStatusAudit']").empty();
				$("span[name='cencorMemoAudit']").empty();


				if (form.find("input[name='censorStatus']").val() == "") {
					$("span[name='censorStatusAudit']").append("<font color='red'>请选择认证状态！</font>");
					return false;
				}
				if (form.find("textarea[name='cencorMemo']").val() == "") {
					$("span[name='cencorMemoAudit']").append("<font color='red'>请输入审核备注！</font>");
					return false;
				}
			}
		});
	 }
	}
	return coachAudit;
})