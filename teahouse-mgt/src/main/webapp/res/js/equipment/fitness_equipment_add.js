define([],function() {
var fitnessEquipmentAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveFitnessEquipment").click(function(){
			fitnessEquipmentAdd.addFitnessEquipment();
		});
		//返回
		$("#closeAddFitnessEquipment").click(function(){
			closeTab("健身设备添加");
			$("#fitnessEquipmentList").DataTable().ajax.reload(function(){});
		});
		//根据城市选择门店
		var form = $("form[name=fitnessEquipmentAddForm]");
		form.find("select[name='cityId']").change(function(){
			var cityId = form.find("select[name='cityId']").find("option:selected").val();
			$.ajax({
				 type: "post",
	             url: fitnessEquipmentAdd.appPath+"/fitnesseEquipment/getStoreByCityId.do",
	             data: {cityId:cityId},
	             success: function(res){
	            	 var dataItems = res.data;
	            	 if(res.code=="1"){
	            		$("#storeModelNs5").html("");
	 					var select="<select name='storeNo'  class='form-control val' style='height:34px;border: 1px solid #ccc;margin-right:5px;'>";
	 					select += "<option value=''>请选择门店</option>";
	 					for(var i=0;i<dataItems.length;i++){
	 						select+="<option  value='"+dataItems[i].storeNo+"'> "+dataItems[i].storeName+" </option>";
	 					}
	 					select+="</select>";
	 					$("#storeModelNs5").append(select);
	            		 
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
		
		//辅助设备---根据设备品牌获取改品牌的型号
		form.find("select[name='brandNo']").change(function(){
			var brandNo = form.find("select[name='brandNo']").find("option:selected").val();
			$.ajax({
				 type: "post",
	             url: fitnessEquipmentAdd.appPath+"/auxiliaryEquipment/getModelByBrandId.do",
	             data: {brandId:brandNo},
	             success: function(res){
	            	 var dataItems = res.data;
	            	 if(res.code=="1"){
	            		$("#ecquimentModelDiv").html("");
	 					var select="<select name='modelNo'  class='form-control val' style='height:34px;border: 1px solid #ccc;margin-right:5px;' onchange='getEquipmentCategoryByModelId(this.value)'>";
	 					select += "<option value=''>请选择型号</option>";
	 					for(var i=0;i<dataItems.length;i++){
	 						select+="<option  value='"+dataItems[i].modelNo+"'> "+dataItems[i].modelName+" </option>";
	 					}
	 					select+="</select>";
	 					$("#ecquimentModelDiv").append(select);
	                 }else{
	                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
	                	 var option1 = "";
	                	 var selectModelName = "请选择";
	                	 option1+="<option  value=''> "+selectModelName+" </option>";
	                	 form.find("select[name='modelNo']").html(option1);
	                 }
	             }
			});
		});

	},
	addFitnessEquipment:function() {
		$("span[name='cityAdd']").empty();
		$("span[name='storeAdd']").empty();
		$("span[name='brandNoAdd']").empty();
		$("span[name='modelAdd']").empty();
		$("span[name='memoAdd']").empty();
		$("span[name='devIdAdd']").empty();
		var form = $("form[name=fitnessEquipmentAddForm]");
		var devId = $.trim(form.find("input[name='devId']").val());
		
		
		
		
		var memo = form.find("textarea[name='memo']").val();
		
		if (form.find("select[name='cityId']").val() == "") {
			$("span[name='cityAdd']").append("<font color='red'>请选择城市！</font>");
			return false;
		}
		if (form.find("select[name='storeNo']").val() == "") {
			$("span[name='storeAdd']").append("<font color='red'>请选择门店！</font>");
			return false;
		}
		if (form.find("select[name='brandNo']").val() == "") {
			$("span[name='brandNoAdd']").append("<font color='red'>请选择设备品牌！</font>");
			return false;
		}
		if (form.find("select[name='modelNo']").val() == "") {
			$("span[name='modelAdd']").append("<font color='red'>请选择设备型号！</font>");
			return false;
		}
		if(memo.length > 200){
			$("span[name='memoAdd']").append("<font color='red'> 输入备注字数不能大于200个！</font>");
			return false;
		}
		
		if (form.find("input[name='devId']").val() == "") {
			$("span[name='devIdAdd']").append("<font color='red'>请输入二维码编号！</font>");
			return false;
		}
		
			
		
		
		$.post("fitnesseEquipment/checkDevId.do",{devId:devId},function(res){
			if(res.code==1){
				$("span[name='devIdAdd']").append("<font color='red'>二维码编号重复，请重新输入！</font>");
				return false;
			}else{
				
				
				form.ajaxSubmit({
					url : fitnessEquipmentAdd.appPath + "/fitnesseEquipment/saveOrUpdateFitnessEquipment.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
								closeTab("健身设备添加");
								$("#fitnessEquipmentList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
						}
					},
					/*beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						$("span[name='cityAdd']").empty();
						$("span[name='storeAdd']").empty();
						$("span[name='brandNoAdd']").empty();
						$("span[name='modelAdd']").empty();
						$("span[name='memoAdd']").empty();
						$("span[name='devId']").empty();
						
						var memo = form.find("textarea[name='memo']").val();
						
						if (form.find("select[name='cityId']").val() == "") {
							$("span[name='cityAdd']").append("<font color='red'>请选择城市！</font>");
							return false;
						}
						if (form.find("select[name='storeNo']").val() == "") {
							$("span[name='storeAdd']").append("<font color='red'>请选择门店！</font>");
							return false;
						}
						if (form.find("select[name='brandNo']").val() == "") {
							$("span[name='brandNoAdd']").append("<font color='red'>请选择设备品牌！</font>");
							return false;
						}
						if (form.find("select[name='modelNo']").val() == "") {
							$("span[name='modelAdd']").append("<font color='red'>请选择设备型号！</font>");
							return false;
						}
						if(memo.length > 200){
							$("span[name='memoAdd']").append("<font color='red'> 输入备注字数不能大于200个！</font>");
							return false;
						}
						
						if (form.find("input[name='devId']").val() == "") {
							$("span[name='devIdAdd']").append("<font color='red'>请输入二维码编号！</font>");
							return false;
						}
						
							
						
					}*/
				});
				
			}	
				
				
			});
		
		
	 }
	}
	return fitnessEquipmentAdd;
})
//辅助设备--根据所选的设备型号查询该型号的分类
function getEquipmentCategoryByModelId(modelNo){
	$.post('auxiliaryEquipment/getEquipmentCategoryByModelId.do', {modelId:modelNo}, 
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
	        	$("#sortModel").html("");
	        }
	},"json");
}