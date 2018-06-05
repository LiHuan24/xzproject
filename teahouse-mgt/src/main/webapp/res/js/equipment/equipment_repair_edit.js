define([],function() {
var equipmentRepairEdit = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	init : function() {
		//添加提交
		$("#updateEquipmentRepair").click(function(){
			equipmentRepairEdit.addEquipmentRepair();
		});
		//返回
		$("#closeEditEquipmentRepair").click(function(){
			closeTab("设备维修记录编辑");
			$("#equipmentRepairList").DataTable().ajax.reload(function(){});
		});
		//上传图片弹出层
		$("#editRepairPhotoUs").click(function(){
			$("#repairEditModalUs").modal("show");
		});
		//根据城市选择门店
		var form = $("form[name=equipmentRepairEditForm]");
		form.find("select[name='cityId']").change(function(){
			var cityId = form.find("select[name='cityId']").find("option:selected").val();
			$.ajax({
				 type: "post",
	             url: equipmentRepairEdit.appPath+"/auxiliaryEquipment/getStoreByCityId.do",
	             data: {cityId:cityId},
	             success: function(res){
	            	 var dataItems = res.data;
	            	 if(res.code=="1"){
	            		$("#storeModelNs3").html("");
	 					var select="<select name='storeNo'  class='form-control val' style='height:34px;border: 1px solid #ccc;margin-right:5px;' >";
	 					select += "<option value=''>请选择门店</option>";
	 					for(var i=0;i<dataItems.length;i++){
	 						select+="<option  value='"+dataItems[i].storeNo+"'> "+dataItems[i].storeName+" </option>";
	 					}
	 					select+="</select>";
	 					$("#storeModelNs").append(select);
	            		 
	                 }else{
	                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
	                	 var option = "";
	                	 var selectName = "请选择";
	                	 option+="<option  value=''> "+selectName+" </option>";
	                	 form.find("select[name='storeNo']").html(option);
	                 }
	             }
			});
		});
		//选择门店之后获取设备
		form.find("select[name='storeNo']").change(function(){
			var storeNo = form.find("select[name='storeNo']").find("option:selected").val();
			$.ajax({
				 type: "post",
	             url: equipmentRepairEdit.appPath+"/equipmentRepair/getEquipmentByStoreNo.do",
	             data: {storeNo:storeNo},
	             success: function(res){
	            	 var dataItems = res.data;
	            	 if(res.code=="1"){
	            		$("#fitnessEquipmentModel").html("");
	 					var select="<select name='fitnessEquipmentNo'  class='form-control val' style='height:34px;border: 1px solid #ccc;margin-right:5px;'>";
	 					select += "<option value=''>请选择设备</option>";
	 					for(var i=0;i<dataItems.length;i++){
	 						select+="<option  value='"+dataItems[i].fitnessEquipmentNo+"'> "+dataItems[i].fitnessEquipmentNo+" </option>";
	 					}
	 					select+="</select>";
	 					$("#fitnessEquipmentModel").append(select);
	            		 
	                 }else{
	                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
	                	 var option = "";
	                	 var selectName = "请选择设备";
	                	 option+="<option  value=''> "+selectName+" </option>";
	                	 form.find("select[name='fitnessEquipmentNo']").html(option);
	                 }
	             }
			});
		});
		//选择设备编号之后获取该设备对应的设备类型名称
		form.find("select[name='fitnessEquipmentNo']").change(function(){
			var fitnessEquipmentNo = form.find("select[name='fitnessEquipmentNo']").find("option:selected").val();
			$.ajax({
				 type: "post",
	             url: equipmentRepairEdit.appPath+"/equipmentRepair/getEquipmentByStoreNo.do",
	             data: {fitnessEquipmentNo:fitnessEquipmentNo},
	             success: function(res){
	            	 var dataItems = res.data;
	            	 if(res.code=="1"){
	            		$("#sortModel").html("");
	 					var select="<select name='sortNo'  class='form-control val' style='height:34px;border: 1px solid #ccc;margin-right:5px;'>";
	 					for(var i=0;i<dataItems.length;i++){
	 						select+="<option  value='"+dataItems[i].sortNo+"'> "+dataItems[i].sortName+" </option>";
	 					}
	 					select+="</select>";
	 					$("#sortModel").append(select);
	            		 
	                 }else{
	                	 //bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
	                	 var option = "";
	                	 var selectName = "请选择类型";
	                	 option+="<option  value=''> "+selectName+" </option>";
	                	 form.find("select[name='sortNo']").html(option);
	                 }
	             }
			});
		});
		//新增图片回填
		$("#editRepairPhotoBtnUs").click(function(){
			var form=$("form[name=repairPhotoFormUs]");
			var img=form.find("input[name=repairPictureUrl1]").val();
			if(img!=""){
				var form1=$("form[name=equipmentRepairEditForm]");
				form1.find("input[name=repairPictureUrl1]").val(img);
				form1.find("img[name=repairPicUrlImg]").attr("src",equipmentRepairEdit.imgPath+"/"+img);
				$("#repairEditModalUs").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
	},
	addEquipmentRepair:function() {
		var form = $("form[name=equipmentRepairEditForm]");
		form.ajaxSubmit({
			url : equipmentRepairEdit.appPath + "/equipmentRepair/saveOrUpdateEquipmentRepair.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "更新成功", function() {
						closeTab("设备维修记录编辑");
						$("#equipmentRepairList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "更新失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='cityEdit']").empty();
				$("span[name='storeEdit']").empty();
				$("span[name='fitnessEquipmentEdit']").empty();
				$("span[name='repairStatusEdit']").empty();
				$("span[name='repairIdEdit']").empty();
				$("span[name='repairNameEdit']").empty();
				$("span[name='memoEdit']").empty();
				if (form.find("select[name='cityId']").val() == "") {
					$("span[name='cityEdit']").append("<font color='red'>请选择城市！</font>");
					return false;
				}
				if (form.find("select[name='storeNo']").val() == "") {
					$("span[name='storeEdit']").append("<font color='red'>请选择门店！</font>");
					return false;
				}
				if (form.find("select[name='fitnessEquipmentNo']").val() == "") {
					$("span[name='fitnessEquipmentEdit']").append("<font color='red'>请选择设备编号！</font>");
					return false;
				}
				if (form.find("select[name='repairStatus']").val() == "") {
					$("span[name='repairStatusEdit']").append("<font color='red'>请选择维修状态！</font>");
					return false;
				}
				if (form.find("select[name='repairId']").val() == "") {
					$("span[name='repairIdEdit']").append("<font color='red'>请选择维修状态！</font>");
					return false;
				}
				if (form.find("input[name='repairName']").val() == "") {
					$("span[name='repairNameEdit']").append("<font color='red'>请输入维修人名称！</font>");
					return false;
				}
				var memo = form.find("textarea[name='memo']").val();
				if (memo.length > 200) {
					$("span[name='memoEdit']").append("<font color='red'>输入备注字数不能大于200个字符！</font>");
					return false;
				}
			}
		});
	 }
	}
	return equipmentRepairEdit;
})