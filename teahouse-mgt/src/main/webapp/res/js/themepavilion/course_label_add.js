define([],function() {
var courseLabelAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveCourseLabel").click(function(){
			courseLabelAdd.addCourseLabel();
		});
		//返回
		$("#closeAddCourseLabel").click(function(){
			closeTab("新增课程标签");
			$("#courseLabelList").DataTable().ajax.reload(function(){});
		});
	},
	addCourseLabel:function() {
		var form = $("form[name=courseLabelAddForm]");
		
		$("span[name='labelNameAdd']").empty();
		var labelName = $.trim(form.find("input[name='labelName']").val());

		if (labelName == "") {
			$("span[name='labelNameAdd']").append("<font color='red'>请输入课程标签名称！</font>");
			return false;
		}
		
		$.post("courseLabel/addCheckLabelUnique.do",{labelName:labelName},function(res){
			if(res.code==1){
				$("span[name='labelNameAdd']").append("<font color='red'>课程标签名称重复，请重新输入！</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : courseLabelAdd.appPath + "/courseLabel/saveOrUpdateCourseLabel.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+msg, function() {
								closeTab("新增课程标签");
								$("#courseLabelList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败");
						}
					},
				});
			}
		});
	 }
	}
	return courseLabelAdd;
})