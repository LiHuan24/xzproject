define([],function() {
var courseLabelEdit = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#updateCourseLabel").click(function(){
			courseLabelEdit.editCourseLabel();
		});
		//返回
		$("#closeEditCourseLabel").click(function(){
			closeTab("课程标签编辑");
			$("#courseLabelList").DataTable().ajax.reload(function(){});
		});

	},
	editCourseLabel:function() {
		var form = $("form[name=courseLabelEditForm]");
		$("span[name='labelNameEdit']").empty();
		var labelName = $.trim(form.find("input[name='labelName']").val());
		if (labelName == "") {
			$("span[name='labelNameEdit']").append("<font color='red'>请输入课程名称！</font>");
			return false;
		}
		var courseLabelNo = form.find("input[name='courseLabelNo']").val();
		$.post("courseLabel/updateCheckLabelUnique.do",{courseLabelNo:courseLabelNo,labelName:labelName},function(res){
			if(res.code==1){
				$("span[name='labelNameEdit']").append("<font color='red'>课程标签名称重复，请重新输入！</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : courseLabelEdit.appPath + "/courseLabel/saveOrUpdateCourseLabel.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+msg, function() {
								closeTab("课程标签编辑");
								$("#courseLabelList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败");
						}
					},
				});
			}
		});
	 }
	}
	return courseLabelEdit;
})