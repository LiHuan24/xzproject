define(
[],
function() {
var equipmentBrandEdit = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveEditEquipmentBrand").click(function(){
			equipmentBrandEdit.editEquipmentBrand();
		});
		//返回
		$("#closeEditEquipmentBrand").click(function(){
			closeTab("设备品牌编辑");
			$("#equipmentBrandList").DataTable().ajax.reload(function(){});
		});
	},
	editEquipmentBrand:function() {
		var form = $("form[name=equipmentBrandEditForm]");
		var sortNoF = form.find("input[name='sortNoF']").val();
		var sortNoJ = form.find("input[name='sortNoJ']").val();
		var brandNo = form.find("input[name='brandNo']").val();
		var brandName = form.find("input[name='brandName']").val();
		$.ajax({
		    url:equipmentBrandEdit.appPath+"/equipmentBrand/uniqueBrandName.do",
			type:"post",
			data:{brandNo:brandNo,brandName:brandName},
			success:function(res){ 
				var code = res.code;
				if(code == '1'){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败!已输入设备名称存在");
					return false;
				}else{
					form.ajaxSubmit({
						url : equipmentBrandEdit.appPath + "/equipmentBrand/saveOrUpdateEquipmentBrand.do",
						type : "post",
						data : {"sortF":sortNoF,"sortJ":sortNoJ},
						success : function(res) {
							var msg = res.msg;
							var code = res.code;
							if (code == "1") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
									closeTab("设备品牌编辑");
									$("#equipmentBrandList").DataTable().ajax.reload(function(){});
								});
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							var form = $("form[name=equipmentBrandEditForm]");
							$("span[name='sortTypeEdit']").empty();
							$("span[name='brandNameEdit']").empty();
							$("span[name='brandWebsiteEdit']").empty();
							$("span[name='memoEdit']").empty();
							var ck = /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/;
							if (form.find("select[name='sortType']").val()=="") {
								$("span[name='sortTypeEdit']").append("<font color='red' class='formtips onError emaill'>请选择品牌类型！</font>");
								return false;
							}
							if (form.find("input[name='brandName']").val()=="") {
								$("span[name='brandNameEdit']").append("<font color='red' class='formtips onError emaill'>请输入品牌名称！</font>");
								return false;
							}
							if (form.find("input[name='brandWebsite']").val()=="") {
								$("span[name='brandWebsiteEdit']").append("<font color='red' class='formtips onError emaill'>请输入品牌网址！</font>");
								return false;
							}else if(!ck.test(form.find("input[name='brandWebsite']").val())){
								$("span[name='brandWebsiteEdit']").append("<font color='red' class='formtips onError emaill'>品牌网址格式不正确！</font>");
								return false;
							}
							var memo = form.find("textarea[name='memo']").val();
							if (memo.length > 200) {
								$("span[name='memoEdit']").append("<font color='red' class='formtips onError emaill'>输入备注字数不能大于200个！</font>");
								return false;
							}
							
						}
					});
				}
			}
		});
	 }
}
return equipmentBrandEdit;
})