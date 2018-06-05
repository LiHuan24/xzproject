define(
[],
function() {
var equipmentCategoryEdit = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveEditEquipmentCategory").click(function(){
			equipmentCategoryEdit.editEquipmentCategory();
		});
		//返回
		$("#closeEditEquipmentCategory").click(function(){
			closeTab("设备分类编辑");
			$("#equipmentCategoryList").DataTable().ajax.reload(function(){});
		});
	},
	editEquipmentCategory:function() {
		$("span[name='sortTypeEdit']").empty();
		$("span[name='sortNameEdit']").empty();
		var form = $("form[name=equipmentCategoryEditForm]");
	
		if (form.find("select[name='sortType']").val()=="") {
			$("#sortTypeEdit").append("<font color='red' class='formtips onError emaill'>请选择设备分类类型！</font>");
			return false;
		}
		if (form.find("input[name='sortName']").val()=="") {
			$("#sortNameEdit").append("<font color='red' class='formtips onError emaill'>请输入设备分类名称！</font>");
			return false;
		}
		var sortNo = form.find("input[name='sortNo']").val();
		var sortType = form.find("select[name='sortType']").val();
		var sortName = form.find("input[name='sortName']").val();
		
		$.post("equipmentCategory/checkSortNameUp.do",{sortNo:sortNo,sortName:sortName,sortType:sortType},function(res){
			if(res.code==1){
				$("span[name='sortNameEdit']").append("<font color='red'>设备分类名称重复，请重新输入！</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : equipmentCategoryEdit.appPath + "/equipmentCategory/saveOrUpdateEquipmentCategory.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
								closeTab("设备分类编辑");
								$("#equipmentCategoryList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
						}
					},
					/*beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						var form = $("form[name=equipmentCategoryEditForm]");
						$("span[name='sortTypeEdit']").empty();
						$("span[name='sortNameEdit']").empty();
						if (form.find("select[name='sortType']").val()=="") {
							$("#sortTypeEdit").append("<font color='red' class='formtips onError emaill'>请选择设备分类类型！</font>");
							return false;
						}
						if (form.find("input[name='sortName']").val()=="") {
							$("#sortNameEdit").append("<font color='red' class='formtips onError emaill'>请输入设备分类名称！</font>");
							return false;
						}
					}*/
				});
			}
			});
		
		
	 }
	}
	return equipmentCategoryEdit;
})
