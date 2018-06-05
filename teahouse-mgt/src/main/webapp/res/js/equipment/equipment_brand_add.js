define(
[],
function() {
var equipmentBrandAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveEquipmentBrand").click(function(){
			equipmentBrandAdd.addEquipmentBrand();
		});
		//返回
		$("#closeAddEquipmentBrand").click(function(){
			closeTab("设备品牌添加");
			$("#equipmentBrandList").DataTable().ajax.reload(function(){});
		});
	},
	addEquipmentBrand:function() {
		var form = $("form[name=equipmentBrandAddForm]");
		var sortF = form.find("input[name='sortF']").val();
		var sortJ = form.find("input[name='sortJ']").val();
		var brandName = form.find("input[name='brandName']").val();
		//验证设备品牌名称是否唯一
		$.ajax({
		    url:equipmentBrandAdd.appPath+"/equipmentBrand/uniqueBrandName.do",
			type:"post",
			data:{brandName:brandName},
			success:function(res){ 
				var code = res.code;
				if(code == '1'){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败!已输入设备名称存在");
					return false;
				}else{
					form.ajaxSubmit({
						url : equipmentBrandAdd.appPath + "/equipmentBrand/saveOrUpdateEquipmentBrand.do",
						type : "post",
						data : {"sortF":sortF,"sortJ":sortJ},
						success : function(res) {
							var msg = res.msg;
							var code = res.code;
							if (code == "1") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
									closeTab("设备品牌添加");
									$("#equipmentBrandList").DataTable().ajax.reload(function(){});
								});
							} else {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							var form = $("form[name=equipmentBrandAddForm]");
							$("span[name='sortTypeAdd']").empty();
							$("span[name='brandNameAdd']").empty();
							$("span[name='brandWebsiteAdd']").empty();
							$("span[name='memoAdd']").empty();
							
							var ck = /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/;
							if (form.find("select[name='brandType']").val()=="") {
								$("span[name='sortTypeAdd']").append("<font color='red' class='formtips onError emaill'>请选择品牌类型！</font>");
								return false;
							}
							if (form.find("input[name='brandName']").val()=="") {
								$("span[name='brandNameAdd']").append("<font color='red' class='formtips onError emaill'>请输入品牌名称！</font>");
								return false;
							}
							if (form.find("input[name='brandWebsite']").val()=="") {
								$("span[name='brandWebsiteAdd']").append("<font color='red' class='formtips onError emaill'>请输入品牌网址！</font>");
								return false;
							}else if(!ck.test(form.find("input[name='brandWebsite']").val())){
								$("span[name='brandWebsiteAdd']").append("<font color='red' class='formtips onError emaill'>品牌网址格式不正确！</font>");
								return false;
							}
							var memo = form.find("textarea[name='memo']").val();
							if (memo.length > 200) {
								$("span[name='memoAdd']").append("<font color='red' class='formtips onError emaill'>输入备注字数不能大于200个！</font>");
								return false;
							}
							
							}
						});
					}
				}
	  	});
 	}
}
return equipmentBrandAdd;
})