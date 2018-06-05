define([],function() {
var redeemCodeAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//新增提交
			$("#addRedeemCode").click(function(){
				redeemCodeAdd.saveRedeemCode();
			});
			//新增页面的关闭
			$("#closeAddRedeemCode").click(function(){
				closeTab("新增兑换码");
			});
			
			//方案列表
			$("#couponPlanOfRedeemCodeAdd").click(function(){
				redeemCodeAdd.pageListPlan();
				$("#couponPlanOfRedeemCodeAddModal").modal({
					 show:true,
					 backdrop:'static'
				});
			});
		},
		saveRedeemCode:function() {
			var form = $("form[name=redeemCodeAddFrom]");
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
				url : redeemCodeAdd.appPath + "/redeemCode/addRedeemCode.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "兑换码添加成功", function() {
							closeTab("新增兑换码");
							$("#redeemCodeList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "兑换码添加失败！"+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='redeemNameAdd']").empty();
					$("span[name='availableTime1Add']").empty();
					$("span[name='availableTime2Add']").empty();
					$("span[name='planNoAdd']").empty();
					if (form.find("input[name='redeemName']").val()=="") {
						$("span[name='redeemNameAdd']").append("<font color='red'>请输入兑换码名称</font>");
						return false;
					}
					
					if (form.find("input[name='availableTime1']").val()=="" || form.find("input[name='availableTime1']").val()==null) {
						$("span[name='availableTime1Add']").append("<font color='red'>有效开始日期不为空！</font>");
						return false;
					}
					if (form.find("input[name='availableTime2']").val()=="" || form.find("input[name='availableTime2']").val()==null) {
						$("span[name='availableTime2Add']").append("<font color='red'>有效结束日期不为空！</font>");
						return false;
					}
					if(new Date(form.find("input[name='availableTime1']").val())>new Date(form.find("input[name='availableTime2']").val())){
						$("span[name='availableTime1Add']").append("<font color='red'>有效期开始日期不能大于结束日期！</font>");
						return false;
					}
					
					var planNosModals = $(".planNoModal");
					if(planNosModals == null || planNosModals.length <= 0){
						$("span[name='planNoAdd']").append("<font color='red'>请选择一个优惠券方案！</font>");
						return false;
					}
					for(var i = 0; i < planNosModals.length;i ++){
						var redeemQutity =  planNosModals.eq(i).find(".val").val();
						var name = planNosModals.eq(i).find(".plan-name").html();
						if(redeemQutity == null ||redeemQutity == ""){
							$("span[name='planNoAdd']").append("<font color='red'>请设置["+name+"]方案的 兑换数量！</font>");
							return false;
						}
						if(!/^[0-9]*[1-9][0-9]*$/.test(redeemQutity)){
							$("span[name='planNoAdd']").append("<font color='red'>请设置["+name+"]方案的 兑换数量为正整数！</font>");
							return false;
						}
					}
				}
			});
		},pageListPlan:function () {
			var tpl = $("#couponPlanOfRedeemCodeAddTpl").html();
			// 预编译模板
			var template = Handlebars.compile(tpl);
			$('#couponPlanOfRedeemCodeAddTable').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": redeemCodeAdd.appPath+'/couponPlan/pageListCouponPlanNs.do?censorStatus=1&isAvailable=1',
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
				   			   { "title":"健身次数/课程节数","data": "couponMethod" },
				   			   { "title":"有效起始日期","data": "availableTime1" },
				   			   { "title":"有效结束日期","data": "availableTime2" },
				   			   { "title":"有效天数（天）","data": "availableDays","defaultContent":"--" },
				   			   { "title":"剩余可发放数量","data": "maxQuantity" }
				 ],
			   "dom": "<'row'<'#couponPlanToolsss.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			   initComplete: function () {
				   	$(this).find("thead tr th:first-child").prepend('');
					$("#couponPlanToolsss").append('<button type="button"  class="btn btn-default btn-sm coupanPlan-batch-add">确定</button>');
					$("#couponPlanToolsss").append('<button type="button"  class="btn btn-default btn-sm coupanPlan-batch-close">关闭</button>');
					
					$(".coupanPlan-batch-add").on("click",function(){
						redeemCodeAdd.showSelectData();
						$("#couponPlanOfRedeemCodeAddModal").modal("hide");
						$('#couponPlanOfRedeemCodeAddTable tbody input[type="checkbox"]:checked').prop("checked",false);
					});
					$(".coupanPlan-batch-close").on("click",function(){
						redeemCodeAdd.setJsonStrToInput("dataTemp",redeemCodeAdd.getSelectedData());
						$("#couponPlanOfRedeemCodeAddModal").modal("hide");
					});
				},
				"drawCallback": function( settings ) {
					redeemCodeAdd.modalCallback()
			    },
				"columnDefs": [
					  {
						"targets" : [0],
						"orderable":false,
						"render" : function(data, type, full, meta) {
							var text = '<input type="checkbox"  name="dataDictItemId" value="' + full.planNo + '" nameValue="'+full.title+'" ';
							var data = redeemCodeAdd.getSelectedData();
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
					    		}else if(a==3){
					    			return "社区馆一天体验券";
					    		}else if(a==4){
					    			return "主题课程一节体验券";
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
					},
					{
					    "targets": [4],
					    "render": function(a,b, c, d) {
					    		if(a==3){
					    			return c.freeFitnessTime+"次";
					    		}else if(a==4){
					    			return c.freeCourseNumber+"节";
					    		}else{
					    			return "--";
					    		}
					    }
					},
					
					{
					    "targets": [5,6],
					    "render": function(data, type, row, meta) {
					    	if(data){
					    		var now = moment(data); 
	        	            	return now.format('YYYY-MM-DD'); 
						    	}else{
						    		return "--";
						    	}
					    }
					},{
					    "targets": [8],
					    "render": function(data, type, row, meta) {
					    	if(row.maxQuantity!=null&&row.existingQuantity != null){
					    		return "" + (row.maxQuantity - row.existingQuantity);
					    	}
					    	return "不限";
					    }
					}
				]
			});
		},
		getSelectedData:function () {//得到确认的提交的数据
			var form = $("form[name=redeemCodeAddFrom]");
			var jsonString = form.find("input[name='dataSubmit']").val();
			if(jsonString != null && jsonString != ""){
				return JSON.parse(jsonString);
			}
			return [];
		},
		getDataTemp:function () {//得到缓存区的数据
			var form = $("form[name=redeemCodeAddFrom]");
			var jsonString = form.find("input[name='dataTemp']").val();
			if(jsonString != null  && jsonString != ""){
				return JSON.parse(jsonString);
			}
			return [];
		},
		setJsonStrToInput:function (inputName,data) {//设置缓存区的数据
			var form = $("form[name=redeemCodeAddFrom]");
			var input = form.find("input[name='"+inputName+"']");
			if(data != null && data.length > 0){
				input.val(JSON.stringify(data));
			}else{
				input.val("");//传入的data为空时清除input的数据
			}
		},
		showSelectData:function () {
			var dataTemp = redeemCodeAdd.getDataTemp();//获取缓存区的数据
			var resultHtml = "";
			if(dataTemp != null && dataTemp.length > 0){
				dataTemp = redeemCodeAdd.setRedeemQutityForExistData(dataTemp);
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
			redeemCodeAdd.setJsonStrToInput("dataSubmit",dataTemp);//将缓冲区的数据放入提交区中
			$('.planNosOfRedeemCodeAddModal').html(resultHtml);
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
				var dataArray = redeemCodeAdd.getDataTemp();//获取缓存区的数据
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
				redeemCodeAdd.setJsonStrToInput("dataTemp",newDataArray);
			});
		}
	}
	return redeemCodeAdd;
})
