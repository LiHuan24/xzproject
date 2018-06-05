define([],function() {
var equipmentModelEdit = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	init : function() {
		//添加提交
		$("#saveEquipmentModel").click(function(){
			equipmentModelEdit.editEquipmentModel();
		});
		//返回
		$("#closeEditEquipmentModel").click(function(){
			closeTab("设备品牌编辑");
			$("#equipmentModelList").DataTable().ajax.reload(function(){});
		});
		$("#editModelPhotoUs").click(function(){
			$("#modelEditModalUs").modal("show");
		});
		//新增图片回填
		$("#editModelPhotoBtnUs").click(function(){
			var form=$("form[name=modelPhotoFormUs]");
			var img=form.find("input[name=modelPictureUrl1]").val();
			var form1=$("form[name=equipmentModelAddForm]");
			if(img!=""){
				form1.find("input[name=modelPictureUrl1]").val(img);
				form1.find("img[name=modelPicUrlImg]").attr("src",equipmentModelEdit.imgPath+"/"+img);
				$("#modelEditModalUs").modal("hide");
			}else{
				form1.find("input[name=modelPictureUrl1]").val("");
				$("#modelEditModalUs").modal("hide");
				/*bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");*/
			}
		});
		//根据设备品牌选择设备设备类型
		var form = $("form[name=equipmentModelEditForm]");
		form.find("select[name='brandNo']").change(function(){
			var brandId = form.find("select[name='brandNo']").find("option:selected").val();
			$.ajax({
				 type: "post",
	             url: equipmentModelEdit.appPath+"/equipmentModel/getEquipmentCategoryByBrandNo.do",
	             data: {brandId:brandId},
	             success: function(res){
	            	 var dataItems = res.data;
	            	 if(res.code=="1"){
	            		 form.find("select[name='sortNo']").html("");
	            		 var option = "";
	            		 for(var i=0;i<dataItems.length;i++){
	            			 option+="<option  value='"+dataItems[i].sortNo+"'> "+dataItems[i].sortName+" </option>";
	              		 }
	            		 form.find("select[name='sortNo']").html(option);
	                 }else{
	                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
	                 }
	             }
			});
		});
	},
	editEquipmentModel:function() {
		$("span[name='brandNoEdit']").empty();
		$("span[name='sortNoEdit']").empty();
		$("span[name='modelNameEdit']").empty();
		var form = $("form[name=equipmentModelEditForm]");
		
		
		if (form.find("select[name='brandNo']").val()=="") {
			$("span[name='brandNoEdit']").append("<font color='red' class='formtips onError emaill'>请选择设备品牌！</font>");
			return false;
		}
		if (form.find("input[select='sortNo']").val()=="") {
			$("span[name='sortTypeEdit']").append("<font color='red' class='formtips onError emaill'>请选择设备类型！</font>");
			return false;
		}
		if (form.find("input[name='modelName']").val()=="") {
			$("span[name='modelNameEdit']").append("<font color='red' class='formtips onError emaill'>请输入设备型号名称！</font>");
			return false;
		}
		
		var modelNo = form.find("input[name='modelNo']").val();
		var brandNo = form.find("select[name='brandNo']").val();
		var sortNo = form.find("select[name='sortNo']").val();
		var modelName = form.find("input[name='modelName']").val();
		$.post("equipmentModel/checkEquipmentModelUp.do",{modelNo:modelNo,brandNo:brandNo,sortNo:sortNo,modelName:modelName},function(res){
			if(res.code==1){
				$("span[name='modelNameEdit']").append("<font color='red'>设备型号名称重复，请重新输入！</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : equipmentModelEdit.appPath + "/equipmentModel/saveOrUpdateEquipmentModel.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
								closeTab("设备型号编辑");
								$("#equipmentModelList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
						}
					},
					/*beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						var form = $("form[name=equipmentModelEditForm]");
						$("span[name='brandNoEdit']").empty();
						$("span[name='sortNoEdit']").empty();
						$("span[name='modelNameEdit']").empty();
						if (form.find("select[name='brandNo']").val()=="") {
							$("span[name='brandNoEdit']").append("<font color='red' class='formtips onError emaill'>请选择设备品牌！</font>");
							return false;
						}
						if (form.find("input[select='sortNo']").val()=="") {
							$("span[name='sortTypeEdit']").append("<font color='red' class='formtips onError emaill'>请选择设备类型！</font>");
							return false;
						}
						if (form.find("input[name='modelName']").val()=="") {
							$("span[name='modelNameEdit']").append("<font color='red' class='formtips onError emaill'>请输入设备型号名称！</font>");
							return false;
						}
					}*/
				});
			}
			});
		
		
	 }
	}
	return equipmentModelEdit;
})