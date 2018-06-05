define([],function() {
var redeemCodeEdit = {
	appPath : getPath("app"),
	imgPath : getPath("img"),
	init : function() {
		//编辑提交
		$("#editRedeemCode").click(function(){
			redeemCodeEdit.editRedeemCode();
		});
		//编辑页面的关闭
		$("#closeEditRedeemCode").click(function(){
			closeTab("兑换码编辑");
		});
		//方案列表
		$("#couponPlanOfRedeemCodeEdit").click(function(){
			redeemCodeEdit.pageListPlan();
			$("#couponPlanOfRedeemCodeEditModal").modal({
				 show:true,
				 backdrop:'static'
			});
		});
		
		redeemCodeEdit.initCouponPlanModal();
	},
	editRedeemCode:function() {
		var form = $("form[name=redeemCodeEditFrom]");
		var planNosModals = $(".planNoModal");
		if(planNosModals != null && planNosModals.length > 0){
			var data = [];
			for(var i = 0; i < planNosModals.length;i ++){
				var	temp = new Object;
				temp.planNo =  planNosModals.eq(i).find(".plan-no").html();
				temp.planTitle = planNosModals.eq(i).find(".plan-name").html();;
				temp.redeemQutity = planNosModals.eq(i).find(".val").val();
				data[data.length] = temp
			}
			form.find('input[name=planString]').val(JSON.stringify(data))
		}
		
		form.ajaxSubmit({
			url : redeemCodeEdit.appPath + "/redeemCode/updateRedeemCode.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "兑换码编辑成功", function() {
						closeTab("兑换码编辑");
						$("#redeemCodeList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "兑换码添加失败！"+msg);
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='redNameEdit']").empty();
				$("span[name='availableTime1Edit']").empty();
				$("span[name='availableTime2Edit']").empty();
				
				if (form.find("input[name='redName']").val()=="") {
					$("span[name='redNameEdit']").append("<font color='red'>请输入兑换码名称</font>");
					return false;
				}
			
				if (form.find("input[name='availableTime1']").val()=="" || form.find("input[name='availableTime1']").val()==null) {
					$("span[name='availableTime1Edit']").append("<font color='red'>有效开始日期不为空！</font>");
					return false;
				}
				if (form.find("input[name='availableTime2']").val()=="" || form.find("input[name='availableTime2']").val()==null) {
					$("span[name='availableTime2Edit']").append("<font color='red'>有效结束日期不为空！</font>");
					return false;
				}
				if(new Date(form.find("input[name='availableTime1']").val())>new Date(form.find("input[name='availableTime2']").val())){
					$("span[name='availableTime1Edit']").append("<font color='red'>有效期开始日期不能大于结束日期！</font>");
					return false;
				}
				
				var planNosModals = $(".planNoModal");
				if(planNosModals == null || planNosModals.length <= 0){
					$("span[name='planNoEdit']").append("<font color='red'>请选择一个优惠券方案！</font>");
					return false;
				}
				for(var i = 0; i < planNosModals.length;i ++){
					var redeemQutity =  planNosModals.eq(i).find(".val").val();
					var name = planNosModals.eq(i).find(".plan-name").html();
					if(redeemQutity == null ||redeemQutity == ""){
						$("span[name='planNoEdit']").append("<font color='red'>请设置["+name+"]方案的 兑换数量！</font>");
						return false;
					}
					if(!/^[0-9]*[1-9][0-9]*$/.test(redeemQutity)){
						$("span[name='planNoEdit']").append("<font color='red'>请设置["+name+"]方案的 兑换数量为正整数！</font>");
						return false;
					}
				}
			}
		});
	},
	initCouponPlanModal:function(){
		var form = $("form[name=redeemCodeEditFrom]");
		var jsonString = form.find("input[name=planString]").val();
		if(jsonString != null){
			var data =  JSON.parse(jsonString);
			var resultData = [];
			if(data != null && data.length > 0){
				for(var i = 0; i < data.length;i ++){
					var	temp = new Object;
					temp.id = data[i].planNo
					temp.name = data[i].planTitle
					temp.redeemQutity = data[i].redeemQutity;
					resultData[resultData.length] = temp
				}
			}
			form.find('input[name=dataTemp]').val(JSON.stringify(resultData))
		}
		redeemCodeEdit.showSelectData();
	},
	pageListPlan:function () {
		var tpl = $("#couponPlanOfRedeemCodeEditTpl").html();
		var template = Handlebars.compile(tpl);// 预编译模板
		
		$('#couponPlanOfRedeemCodeEditTable').dataTable( {
			searching:false,
			destroy: true,
			"ajax": {
				"type": "POST",
				"url": redeemCodeEdit.appPath+'/couponPlan/pageListCouponPlanNs.do?censorStatus=1&isAvailable=1',
				"data": function ( d ) {
					return $.extend( {}, d, {
						"pageNo": (d.start/d.length)+1,
						"pageSize":d.length
					} );
				},
				"dataSrc": function ( json ) {
					json.recordsTotal=json.rowCount;
					json.recordsFiltered=json.rowCount;
					json.data=json.data;
					return json.data;
				},
				error: function (xhr, error, thrown) {  
	            }
			},
			"columns": [
			               { "title":"","data": "planNo","width":"10%"},
			   			   { "title":"方案标题","data": "title"},
			   			   { "title":"优惠方式","data": "couponMethod" },
			   			   { "title":"折扣率/直减金额","data": "couponMethod" },
			   			   { "title":"有效起始日期","data": "vailableTime1" },
			   			   { "title":"有效结束日期","data": "vailableTime2" },
			   			   { "title":"有效天数（天）","data": "availableDays" },
			   			   { "title":"剩余可发放数量","data": "maxQuantity" }
			],
		   "dom": "<'row'<'#couponPlanToolss.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
		    initComplete: function () {
			   	$(this).find("thead tr th:first-child").prepend('');
				$("#couponPlanToolss").append('<button type="button"  class="btn btn-default btn-sm coupanPlan-batch-add">确定</button>');
				$("#couponPlanToolss").append('<button type="button"  class="btn btn-default btn-sm coupanPlan-batch-close">关闭</button>');
				
				$(".coupanPlan-batch-add").on("click",function(){
					redeemCodeEdit.showSelectData();
					$("#couponPlanOfRedeemCodeEditModal").modal("hide");
					$('#couponPlanOfRedeemCodeEditTable tbody input[type="checkbox"]:checked').prop("checked",false);
				});
				$(".coupanPlan-batch-close").on("click",function(){
					redeemCodeEdit.setJsonStrToInput("dataTemp",redeemCodeEdit.getSelectedData());
					$("#couponPlanOfRedeemCodeEditModal").modal("hide");
				});
			},
			"drawCallback": function( settings ) {
				redeemCodeEdit.modalCallback()
		    },
			"columnDefs": [
				  {
					"targets" : [0],
					"orderable":false,
					"render" : function(data, type, full, meta) {
						var text = '<input type="checkbox"  name="dataDictItemId" value="' + full.planNo + '" nameValue="'+full.title+'" ';
						var data = redeemCodeEdit.getSelectedData();
						for(var i = 0; i < data.length;i ++){
							if(data[i].id == full.planNo){
								text += ' checked="checked" '
								break;
							}
						}
						return text +' >';
					}
				},{
				    "targets": [2],
				    "render": function(a,b, c, d) {
				    		if(a==1){
				    			return "折扣";
				    		}else if(a==2){
				    			return "直减";
				    		}else{
				    			return "--";
				    		}
				    }
				},{
				    "targets": [3],
				    "render": function(a,b, c, d) {
				    		if(a==1){
				    			var discount = c.discount;
				    			if(discount == 0 || discount == 1){
				    				return c.discount+".00"
				    			}
				    			return ""+c.discount;
				    		}else if(a==2){
				    			return ""+c.discountAmount;
				    		}else{
				    			return "--";
				    		}
				    }
				},{
				    "targets": [4,5],
				    "render": function(data, type, row, meta) {
				    	if(data){
				    		var now = moment(data); 
        	            	return now.format('YYYY-MM-DD'); 
					    	}else{
					    		return "--";
					    	}
				    }
				},{
				    "targets": [7],
				    "render": function(data, type, row, meta) {
				    	if(row.existingQuantity != null){
				    		return "" + (row.maxQuantity - row.existingQuantity);
				    	}
				    	return "" + row.maxQuantity;
				    }
				}
			]
		});
	},
	getSelectedData:function () {
		var form = $("form[name=redeemCodeEditFrom]");
		var jsonString = form.find("input[name='dataSubmit']").val();
		if(jsonString != null && jsonString != ""){
			return JSON.parse(jsonString);
		}
		return [];
	},
	getDataTemp:function () {//得到缓存区的数据
		var form = $("form[name=redeemCodeEditFrom]");
		var jsonString = form.find("input[name='dataTemp']").val();
		if(jsonString != null  && jsonString != ""){
			return JSON.parse(jsonString);
		}
		return [];
	},
	setJsonStrToInput:function (inputName,data) {//设置缓存区的数据
		var form = $("form[name=redeemCodeEditFrom]");
		var input = form.find("input[name='"+inputName+"']");
		if(data != null && data.length > 0){
			input.val(JSON.stringify(data));
		}else{
			input.val("");//传入的data为空时清除input的数据
		}
	},
	showSelectData:function () {
		var dataTemp = redeemCodeEdit.getDataTemp();//获取缓存区的数据
		var resultHtml = "";
		if(dataTemp != null && dataTemp.length > 0){
			dataTemp = redeemCodeEdit.setRedeemQutityForExistData(dataTemp);
			for (var i = 0; i < dataTemp.length; i++) {
				var redeemQutity = dataTemp[i].redeemQutity == null ? "" : dataTemp[i].redeemQutity;
				var text = '<div class="form-group col-md-8"><label class="col-sm-3 control-label reason key"></label>'
					+ '<div class="col-sm-7 planNoModal">'
					+ '<span class = "plan-no" hidden="true">'+dataTemp[i].id+'</span>'
					+ '<span class = "plan-name">'+dataTemp[i].name+'</span>' 
					+ ' 兑换数量：<input class="form-control val" value = "'+redeemQutity+'"/>'
					+ '</div><div style="margin-top:7px;"></div></div>';
				resultHtml += text;
			}
		}
		redeemCodeEdit.setJsonStrToInput("dataSubmit",dataTemp);//将缓冲区的数据放入提交区中
		$('.planNosOfRedeemCodeEditModal').html(resultHtml);
	},
	setRedeemQutityForExistData:function(data){
		var planNosModals = $(".planNoModal");
		if(planNosModals != null && planNosModals.length > 0){
			for(var i = 0; i < planNosModals.length; i++){
				var redeemQutity =  planNosModals.eq(i).find(".val").val();
				var planNo = planNosModals.eq(i).find(".plan-no").html();
				for(var j = 0; j < data.length; j++){
					if(planNo == data[j].id){
						data[j].redeemQutity = redeemQutity;
						break;
					}
				}
			}
		}
		return data;
	},
	modalCallback:function () {
		$("input[name=dataDictItemId]").click(function(){
			var dataArray = redeemCodeEdit.getDataTemp();//获取缓存区的数据
			if(dataArray == null){
				dataArray = [];
			}
			var newDataArray = [];
			if($(this).is(':checked')){
				var thisData = new Object;
				thisData.id = $(this).val();
				thisData.name = $(this).attr('nameValue') == null ? "" : $(this).attr('nameValue');
				thisData.redeemQutity = "";
				
				var isNewData = true;
				if(dataArray.length > 0){
					for(var i = 0; i < dataArray.length;i ++){
						if(dataArray[i].id == $(this).val()){
							isNewData = false;
						}
					}
				}
				newDataArray = dataArray;
				if(isNewData == true){
					newDataArray.push(thisData)
				}
				
			}else{
				if(dataArray.length > 0){
					for(var i = 0; i < dataArray.length;i ++){
						if(dataArray[i].id != $(this).val()){
							newDataArray.push(dataArray[i])
						}
					}
				}
			}
			redeemCodeEdit.setJsonStrToInput("dataTemp",newDataArray);
		});
	}
}
return redeemCodeEdit;
})
