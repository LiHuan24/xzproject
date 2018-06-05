define([],function() {
var courseSortAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveCourseSort").click(function(){
			courseSortAdd.addCourseSort();
		});
		//返回
		$("#closeAddCourseSort").click(function(){
			closeTab("新增课程分类");
			$("#courseSortList").DataTable().ajax.reload(function(){});
		});
	},
	addCourseSort:function() {
		var form = $("form[name=courseSortAddForm]");
		var sortName = $.trim(form.find("input[name='sortName']").val());
		
		$("span[name='sortNameAdd']").empty();

		if (sortName == "") {
			$("span[name='sortNameAdd']").append("<font color='red'>请输入课程分类名称！</font>");
			return false;
		}	
		
		$.post("courseSort/addCourseSortUnique.do",{sortName:sortName},function(res){
			if(res.code == 1){
				$("span[name='sortNameAdd']").append("<font color='red'>课程分类名称不能重复！</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : courseSortAdd.appPath + "/courseSort/saveOrUpdateCourseSort.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+msg, function() {
								closeTab("新增课程分类");
								$("#courseSortList").DataTable().ajax.reload(function(){});
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
	return courseSortAdd;
})