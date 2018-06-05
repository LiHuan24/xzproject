define([],function() {
var equipmentRepairAdd = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	init : function() {
		//添加提交
		$("#saveEquipmentRepair").click(function(){
			equipmentRepairAdd.addEquipmentRepair();
		});
		//返回
		$("#closeAddEquipmentRepair").click(function(){
			closeTab("新增维修记录");
			$("#equipmentRepairList").DataTable().ajax.reload(function(){});
		});
		//上传图片弹出层
		$("#addRepairPhotoUs").click(function(){
			$("#repairAddModalUs").modal("show");
		});
		//根据城市选择门店
		var form = $("form[name=equipmentRepairAddForm]");
		form.find("select[name='cityId']").change(function(){
			var cityId = form.find("select[name='cityId']").find("option:selected").val();
			$.ajax({
				 type: "post",
	             url: equipmentRepairAdd.appPath+"/equipmentRepair/getStoreByCityId.do",
	             data: {cityId:cityId},
	             success: function(res){
	            	 var dataItems = res.data;
	            	 if(res.code=="1"){
	            		$("#storeModelNs").html("");
	 					var select="<select name='storeNo'  class='form-control val' style='height:34px;border: 1px solid #ccc;margin-right:5px;' onchange='getEquipmentByStoreNo(this.value)'>";
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
		//新增图片回填
		$("#addRepairPhotoBtnUs").click(function(){
			var form=$("form[name=repairPhotoFormUs]");
			var img=form.find("input[name=repairPictureUrl1]").val();
			if(img!=""){
				var form1=$("form[name=equipmentRepairAddForm]");
				form1.find("input[name=repairPictureUrl1]").val(img);
				form1.find("img[name=repairPicUrlImg]").attr("src",equipmentModelAdd.imgPath+"/"+img);
				$("#repairAddModalUs").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
	},
	addEquipmentRepair:function() {
		var form = $("form[name=equipmentRepairAddForm]");
		form.ajaxSubmit({
			url : equipmentRepairAdd.appPath + "/equipmentRepair/saveOrUpdateEquipmentRepair.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增维修记录");
						$("#equipmentRepairList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='cityAdd']").empty();
				$("span[name='storeAdd']").empty();
				$("span[name='fitnessEquipmentAdd']").empty();
				$("span[name='repairStatusAdd']").empty();
				$("span[name='repairIdAdd']").empty();
				$("span[name='repairNameAdd']").empty();
				$("span[name='memoAdd']").empty();

				if (form.find("select[name='cityId']").val() == "") {
					$("span[name='cityAdd']").append("<font color='red'>请选择城市！</font>");
					return false;
				}
				if (form.find("select[name='storeNo']").val() == "") {
					$("span[name='storeAdd']").append("<font color='red'>请选择门店！</font>");
					return false;
				}
				if (form.find("select[name='fitnessEquipmentNo']").val() == "") {
					$("span[name='fitnessEquipmentAdd']").append("<font color='red'>请选择设备编号！</font>");
					return false;
				}
				if (form.find("select[name='repairStatus']").val() == "") {
					$("span[name='repairStatusAdd']").append("<font color='red'>请选择维修状态！</font>");
					return false;
				}
				if (form.find("select[name='repairId']").val() == "") {
					$("span[name='repairIdAdd']").append("<font color='red'>请选择维修状态！</font>");
					return false;
				}
				if (form.find("input[name='repairName']").val() == "") {
					$("span[name='repairNameAdd']").append("<font color='red'>请输入维修人名称！</font>");
					return false;
				}
				var memo = form.find("textarea[name='memo']").val();
				if(memo.length > 200){
					$("span[name='memoAdd']").append("<font color='red'>输入备注字数不能大于200个字符！</font>");
					return false;
				}
			}
		});
	 }
	}
	return equipmentRepairAdd;
})
//根据门店选择设备
function getEquipmentByStoreNo(storeNo){
	$.post('equipmentRepair/getEquipmentByStoreNo.do', {storeNo:storeNo}, 
		function(result) {
			var dataItems = result.data;
			if(result.code=="1"){
				$("#fitnessEquipmentModel").html("");
				var select="<select name='fitnessEquipmentNo'  class='form-control val' style='height:34px;border: 1px solid #ccc;margin-right:5px;' onchange='getEquipmentSortByFitnessNo(this.value)'>";
				select += "<option value=''>请选择设备</option>";
				for(var i=0;i<dataItems.length;i++){
					select+="<option  value='"+dataItems[i].fitnessEquipmentNo+"'> "+dataItems[i].fitnessEquipmentNo+" </option>";
				}
				
				$("#fitnessEquipmentModel").append(select);
	        }else{
	        	var select="<select name='fitnessEquipmentNo'  class='form-control val' style='height:34px;border: 1px solid #ccc;margin-right:5px;'>";
				select += "<option value=''>请选择设备</option>";
				select+="</select>";
	        	$("#fitnessEquipmentModel").html(select);
	        }
	},"json");
}
//根据选择的设备获取该设备的类型
function getEquipmentSortByFitnessNo(fitnessEquipmentNo){
	$.post('equipmentRepair/getEquipmentByStoreNo.do', {fitnessEquipmentNo:fitnessEquipmentNo}, 
		function(result) {
			var dataItems = result.data;
			if(result.code=="1"){
				$("#sortModel").html("");
				var select="<select name='sortNo'  class='form-control val' style='height:34px;border: 1px solid #ccc;margin-right:5px;'>";
				for(var i=0;i<dataItems.length;i++){
					select+="<option  value='"+dataItems[i].sortNo+"'> "+dataItems[i].sortName+" </option>";
				}
				select+="</select>";
				$("#sortModel").append(select);
	        }else{
				var select="<select name='sortNo'  class='form-control val' style='height:34px;border: 1px solid #ccc;margin-right:5px;'>";
				select += "<option value=''>请选择类型</option>";
				select+="</select>";
	        	$("#sortModel").html(select);
	        }
		},"json");
}