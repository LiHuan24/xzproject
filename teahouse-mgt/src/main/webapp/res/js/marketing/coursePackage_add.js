define([],function() {
var coursePackageAdd = {
		appPath : getPath("app"),
		init : function() {
			//新增提交
			$("#addCoursePackage").click(function(){
				coursePackageAdd.saveCoursePackage();
			});
			//新增页面的关闭
			$("#closeAddCoursePackage").click(function(){
				closeTab("课程包新增");
			});
		},
		saveCoursePackage:function() {
			var form = $("form[name=coursePackageAddFrom]");
   		 	form.ajaxSubmit({
				url : coursePackageAdd.appPath + "/coursePackage/addCoursePackage.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加成功", function() {
							closeTab("课程包新增");
							$("#coursePackageList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "增加失败,"+msg+"！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='coursePackageNameAdd']").empty();
					$("span[name='priceAdd']").empty();
					$("span[name='coursePackageAdd']").empty();
					if (form.find("input[name='coursePackageName']").val()=="") {
						$("span[name='coursePackageNameAdd']").append("<font color='red'>请输入课程包名称！</font>");
						return false;
					}
					if (form.find("input[name='price']").val()=="") {
						$("span[name='priceAdd']").append("<font color='red'>请输入销售价！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='price']").val())){
		            	$("span[name='priceAdd']").append("<font color='red'>格式不正确，不能有空格，销售价只能输入整数或小数！</font>");
						return false;
		            }
					
					if (form.find("input[name='courseNumber']").val()=="") {
						$("span[name='coursePackageAdd']").append("<font color='red'>请输入课程节数！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='courseNumber']").val())){
						$("span[name='courseNumberAdd']").append("<font color='red'>课程节数只能输入正整数！</font>");
						return false;
					}
				}
			});
		}
}
return coursePackageAdd;
})
