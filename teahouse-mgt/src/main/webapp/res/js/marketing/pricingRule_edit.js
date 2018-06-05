define([],function() {
var pricingRuleEdit = {
		appPath : getPath("app"),
		init : function() {
			//修改提交
			$("#EditPricingRule").click(function(){
				pricingRuleEdit.savePricingRule();
			});
			//修改页面的关闭
			$("#closeEditPricingRule").click(function(){
				closeTab("计费规则修改");
			});
			
			//打开自定义计费 弹出框
			$("#customModelOpen").click(function() {
				var form = $("form[name=customForm]");
				form.find("input[name=customNo]").val("");
				form.find("input[name=hourPrice]").val("");
				form.find(".c_add").show();
				form.find(".c_edit").hide(); 
				form.find(".c_add").attr("name","customDateStr");
				form.find(".c_edit").attr("name","");
				form.find(".c_add").val("");
				form.find(".c_edit").val("");
				$("#customModal").modal("show");
            });
			//关闭自定义计费 弹出框
			$("#customCancelBtn").click(function() {  
				$("#customModal").modal("hide");
            });
			//提交自定义计费
			$("#customSubmitBtn").click(function(){
				pricingRuleEdit.saveCustom();
			});
			//切换自定义表
			$("input[name=isOverdue]").click(function(){
				 pricingRuleEdit.pageListCustomPricingRule();
			});
			
			//初始化显示自定义表
			pricingRuleEdit.pageListCustomPricingRule();
			
		},
		savePricingRule:function() {
			var form = $("form[name=pricingRuleEditFrom]");
   		 	form.ajaxSubmit({
				url : pricingRuleEdit.appPath + "/pricingRule/updatePricingRule.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改成功", function() {
							closeTab("计费规则修改");
							$("#pricingRuleList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改失败,"+msg+"！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='ruleNameEdit']").empty();
					$("span[name='hourPriceEdit']").empty();
					$("span[name='dayCapEdit']").empty();
					$("span[name='consumptionQuotaEdit']").empty();

					if (form.find("input[name='ruleName']").val()=="") {
						$("span[name='ruleNameEdit']").append("<font color='red'>请输入计费规则名称！</font>");
						return false;
					}
					if (form.find("input[name='hourPrice']").val()=="") {
						$("span[name='hourPriceEdit']").append("<font color='red'>请输入计费金额！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='hourPrice']").val())){
		            	$("span[name='hourPriceEdit']").append("<font color='red'>格式不正确，计费金额只能输入整数或小数！</font>");
						return false;
		            }
					
					/*if (form.find("input[name='dayCap']").val()=="") {
						$("span[name='dayCapEdit']").append("<font color='red'>请输入每日封顶！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='dayCap']").val())){
		            	$("span[name='dayCapEdit']").append("<font color='red'>格式不正确，每日封顶只能输入整数或小数！</font>");
						return false;
		            }*/
					/*if (form.find("input[name='consumptionQuota']").val()=="") {
						$("span[name='consumptionQuotaEdit']").append("<font color='red'>请输入消费额度！</font>");
						return false;
					}else if(!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='consumptionQuota']").val())){
		            	$("span[name='consumptionQuotaEdit']").append("<font color='red'>格式不正确，累计消费额度只能输入整数或小数！</font>");
						return false;
		            }*/
				}
			});
		},saveCustom:function(){
			var form = $("form[name=customForm]");
			form.ajaxSubmit({
				url:pricingRuleEdit.appPath+"/customPricingRule/addorUpdateCustomPricingRule.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
							$("#customModal").modal("hide");
							form.resetForm();
							$("#pricingRuleCustomListAdd").DataTable().ajax.reload(function(){
							}); 
						});
					}else{
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败！ "+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='hourPriceError']").empty();
					$("span[name='customDateError']").empty();
					if (form.find("input[name='hourPrice']").val()=="") {
						$("span[name='hourPriceError']").append("<font color='red'>请输入计费金额！</font>");
						return false;
					}
					if (form.find("input[name='hourPrice']").val()!=""&&!/^[0-9]+([.]{1}[0-9]+){0,1}$/.test(form.find("input[name='hourPrice']").val())) {
						$("span[name='hourPriceError']").append("<font color='red'>计费金额输入有误(正数或0)！</font>");
						return false;
					}
					if (form.find("input[name='customDate']").val()=="") {
						$("span[name='customDateError']").append("<font color='red'>请选择自定义日期！</font>");
						return false;
					}
				}
			});
		},	
		pageListCustomPricingRule:function () {	
			var parkTpl = $("#pricingRuleCustomTpl").html();
			var template = Handlebars.compile(parkTpl);// 预编译模板
			var customDateStart = null;
			var customDateEnd = null;
			var isOverdue=$('#oid input[name="isOverdue"]:checked ').val();
			if(isOverdue == 0){
				customDateStart = pricingRuleEdit.getNowFormatDate() + " 00:00:00";
			}else{
				customDateEnd =  pricingRuleEdit.getNowFormatDate() + " 00:00:00";
			}
			$('#pricingRuleCustomListAdd').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": pricingRuleEdit.appPath+"/customPricingRule/pageListCustomPricingRule.do",
					"data": function ( d ) {
						var form = $("form[name=pricingRuleEditFrom]");
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"ruleNo":$.trim(form.find("input[name=ruleNo]").val()),
							"customDateStart":customDateStart,
							"customDateEnd":customDateEnd
						} );
					},
					"dataSrc": function ( json ) {
						json.recordsTotal=json.rowCount;
						json.recordsFiltered=json.rowCount;
						json.data=json.data;
						if(json.data.length<=0){
							$(".pcds").hide();
						}else{
							$(".pcds").show();
						}
						return json.data;
					},
					error: function (xhr, error, thrown) {  
		            }
				},
				"columns": [
		            { "title":"自定义日期","data": "customDate" },
					{ "title":"计费金额（元/小时）","data": "hourPrice" },
					{ "title":"操作","orderable":false}
				],
			   initComplete: function () {
				    $("#parkTools").html("");
				},
				"drawCallback": function( settings ) {
					pricingRuleEdit.operateColumEvent();
        	    },
        	    "order": [[ 1, "desc" ]],
				"columnDefs": [
	            	{
					    "targets": [0],
					    "render": function(a,b, c, d) {
					        if(a!=null){
					        	var now = moment(a);
								return now.format('YYYY-MM-DD');
					        }else{
					        	return "--";
					        }
					    }
					},{
						"targets": [2],
						"render": function (a, b, c, d) {
							var del='<span class="glyphicon operate-del" data-id="'+c.customNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">删除</span>';
							var	edit='<span class="glyphicon operate-edit" data-id="'+c.customNo+'" data-price="'+c.hourPrice+'" data-date="'+c.customDate+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">编辑</span>';
        					return del+edit;
						}
					}
				]
			});
		},operateColumEvent: function(){
			 $(".operate-del").each(function(id,obj){
					$(this).on("click",function(){
						var customNo=$(this).data("id");
						$.ajax({
							 type: "post",
				             url: pricingRuleEdit.appPath+"/customPricingRule/deleteCustomPricingRule.do",
				             data: {customNo:customNo},
				             dataType: "json",
				             success: function(data){
				            	 if(data.code="1"){
				            		 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！",function(){
											$("#pricingRuleCustomListAdd").DataTable().ajax.reload(function(){
				    						}); 
				            		 });
				                 }
				             }
						});
					});
				});
			 
			 $(".operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						var customNo = $(this).data("id");
						var hourPrice = $(this).data("price");
						var customDate = moment($(this).data("date")).format('YYYY-MM-DD');
						$("#customModal").modal("show");
						var form = $("form[name=customForm]");
						form.find(".c_add").hide();
						form.find(".c_edit").show(); 
						form.find(".c_add").attr("name","");
						form.find(".c_edit").attr("name","customDateStr");
						form.find(".c_add").val("");
						form.find(".c_edit").val(customDate);
						form.find("input[name=customNo]").val(customNo);
						form.find("input[name=hourPrice]").val(hourPrice);
					});
				});

		 },getNowFormatDate : function(){
			    var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = null;
			    currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
			    	/*currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
			    	+ " " + date.getHours() + seperator2 + date.getMinutes()
			    	+ seperator2 + date.getSeconds();*/
			    return currentdate;
			}
	}
return pricingRuleEdit;
})
