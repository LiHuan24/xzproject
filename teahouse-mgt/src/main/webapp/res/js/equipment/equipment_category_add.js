define(
[],
function() {
var equipmentCategoryAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveEquipmentCategory").click(function(){
			equipmentCategoryAdd.addEquipmentCategory();
		});
		//返回
		$("#closeAddEquipmentCategory").click(function(){
			closeTab("设备分类添加");
			$("#equipmentCategoryList").DataTable().ajax.reload(function(){});
		});
	},
	addEquipmentCategory:function() {
		$("span[name='sortTypeAdd']").empty();
		$("span[name='sortNameAdd']").empty();
		var form = $("form[name=equipmentCategoryAddForm]");
		
		var sortName = $.trim(form.find("input[name='sortName']").val());
		var sortType = form.find("select[name='sortType']").val();
		
		if (form.find("select[name='sortType']").val()=="") {
			$("span[name='sortTypeAdd']").append("<font color='red' class='formtips onError emaill'>请选择设备分类类型！</font>");
			return false;
		}
		if (form.find("input[name='sortName']").val()=="") {
			$("span[name='sortNameAdd']").append("<font color='red' class='formtips onError emaill'>请输入设备分类名称！</font>");
			return false;
		}
		
		$.post("equipmentCategory/checkSortName.do",{sortName:sortName,sortType:sortType},function(res){
			if(res.code==1){
				$("span[name='sortNameAdd']").append("<font color='red'>设备分类名称重复，请重新输入！</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : equipmentCategoryAdd.appPath + "/equipmentCategory/saveOrUpdateEquipmentCategory.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
								closeTab("设备分类添加");
								$("#equipmentCategoryList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
						}
					},
					/*beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						var form = $("form[name=equipmentCategoryAddForm]");
						$("span[name='sortTypeAdd']").empty();
						$("span[name='sortNameAdd']").empty();
						if (form.find("select[name='sortType']").val()=="") {
							$("span[name='sortTypeAdd']").append("<font color='red' class='formtips onError emaill'>请选择设备分类类型！</font>");
							return false;
						}
						if (form.find("input[name='sortName']").val()=="") {
							$("span[name='sortNameAdd']").append("<font color='red' class='formtips onError emaill'>请输入设备分类名称！</font>");
							return false;
						}
					}*/
				});
			} 
			});
		
		
	 }
	}
	return equipmentCategoryAdd;
})
