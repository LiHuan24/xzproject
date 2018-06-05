define([],function() {
var courseSortEdit = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#updateCourseSort").click(function(){
			courseSortEdit.editCourseSort();
		});
		//返回
		$("#closeEditCourseSort").click(function(){
			closeTab("课程分类编辑");
			$("#courseSortList").DataTable().ajax.reload(function(){});
		});

	},
	editCourseSort:function() {
		var form = $("form[name=courseSortEditForm]");
		var courseSrotNo = form.find("input[name='courseSortNo']").val();
		var sortName = $.trim(form.find("input[name='sortName']").val());
		
		$("span[name='sortNameEdit']").empty();

		if (sortName == "") {
			$("span[name='sortNameEdit']").append("<font color='red'>请输入课程分类名称！</font>");
			return false;
		}
		
		$.post("courseSort/updateCourseSortUnique.do",{courseSrotNo:courseSrotNo,sortName:sortName},function(res){
			if(res.code == 1){
				$("span[name='sortNameEdit']").append("<font color='red'>课程分类名称不能重复！</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : courseSortEdit.appPath + "/courseSort/saveOrUpdateCourseSort.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+msg, function() {
								closeTab("课程分类编辑");
								$("#courseSortList").DataTable().ajax.reload(function(){});
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
	return courseSortEdit;
})