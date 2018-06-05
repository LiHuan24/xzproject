define([],function() {
var equipmentModelAdd = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	init : function() {
		//添加提交
		$("#saveEquipmentModel").click(function(){
			equipmentModelAdd.addEquipmentModel();
		});
		//返回
		$("#closeAddEquipmentModel").click(function(){
			closeTab("设备品牌添加");
			$("#equipmentModelList").DataTable().ajax.reload(function(){});
		});
		$("#addModelPhotoUs").click(function(){
			$("#modelAddModalUs").modal("show");
		});
		//新增图片回填
		$("#addModelPhotoBtnUs").click(function(){
			var form=$("form[name=modelPhotoFormUs]");
			var img=form.find("input[name=modelPictureUrl1]").val();
			var form1=$("form[name=equipmentModelAddForm]");
			if(img!=""){
				form1.find("input[name=modelPictureUrl1]").val(img);
				form1.find("img[name=modelPicUrlImg]").attr("src",equipmentModelAdd.imgPath+"/"+img);
				$("#modelAddModalUs").modal("hide");
			}else{
				form1.find("input[name=modelPictureUrl1]").val("");
				$("#modelAddModalUs").modal("hide");
				/*bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");*/
			}
		});
		//根据设备品牌选择设备设备类型
		var form = $("form[name=equipmentModelAddForm]");
		form.find("select[name='brandNo']").change(function(){
			var brandId = form.find("select[name='brandNo']").find("option:selected").val();
			$.ajax({
				 type: "post",
	             url: equipmentModelAdd.appPath+"/equipmentModel/getEquipmentCategoryByBrandNo.do",
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
	addEquipmentModel:function() {
		$("span[name='brandNoAdd']").empty();
		$("span[name='sortNoAdd']").empty();
		$("span[name='modelNameAdd']").empty();
		
		var form = $("form[name=equipmentModelAddForm]");
		
		if (form.find("select[name='brandNo']").val()=="") {
			$("span[name='brandNoAdd']").append("<font color='red' class='formtips onError emaill'>请选择设备品牌！</font>");
			return false;
		}
		if (form.find("select[name='sortNo']").val()=="") {
			$("span[name='sortNoAdd']").append("<font color='red' class='formtips onError emaill'>请选择设备类型！</font>");
			return false;
		}
		if (form.find("input[name='modelName']").val()=="") {
			$("span[name='modelNameAdd']").append("<font color='red' class='formtips onError emaill'>请输入设备型号名称！</font>");
			return false;
		}
		
		var brandNo = form.find("select[name='brandNo']").val();
		var sortNo = form.find("select[name='sortNo']").val();
		var modelName = form.find("input[name='modelName']").val();
		$.post("equipmentModel/checkEquipmentModel.do",{brandNo:brandNo,sortNo:sortNo,modelName:modelName},function(res){
			if(res.code==1){
				$("span[name='modelNameAdd']").append("<font color='red'>设备型号名称重复，请重新输入！</font>");
				return false;
			}else{
				
				form.ajaxSubmit({
					url : equipmentModelAdd.appPath + "/equipmentModel/saveOrUpdateEquipmentModel.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
								closeTab("设备型号添加");
								$("#equipmentModelList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
						}
					},
					/*beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						var form = $("form[name=equipmentModelAddForm]");
						$("span[name='brandNoAdd']").empty();
						$("span[name='sortNoAdd']").empty();
						$("span[name='modelNameAdd']").empty();
						if (form.find("select[name='brandNo']").val()=="") {
							$("span[name='brandNoAdd']").append("<font color='red' class='formtips onError emaill'>请选择设备品牌！</font>");
							return false;
						}
						if (form.find("select[name='sortNo']").val()=="") {
							$("span[name='sortNoAdd']").append("<font color='red' class='formtips onError emaill'>请选择设备类型！</font>");
							return false;
						}
						if (form.find("input[name='modelName']").val()=="") {
							$("span[name='modelNameAdd']").append("<font color='red' class='formtips onError emaill'>请输入设备型号名称！</font>");
							return false;
						}
						
					}*/
				});
				
			}
			});
		
		
	 }
	}
	return equipmentModelAdd;
})