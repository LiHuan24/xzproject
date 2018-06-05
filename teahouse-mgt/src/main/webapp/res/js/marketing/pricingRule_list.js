define([],function() {
	var pricingRule = {
		appPath : getPath("app"),
		init : function() {
			$("#closePricingRuleView").click(function(){
				closeTab("计费规则详情");
			});
			// 列表页面搜索调用
			$("#pricingRuleSearch").click(function() {
				pricingRule.pageList();
			});
			//审核提交
			$("#pricingRuleCencorBtn").click(function(){
				pricingRule.pricingRuleCencorFormSub();
            });
			//审核取消
			$("#pricingRuleCencorCancel").click(function(){
				$("#pricingRuleCencorModel").modal("hide");
            });
			$("#pricingRuleCencorModel").on("hidden.bs.modal", function() {  
            	var form = $("form[name=pricingRuleCencorForm]");
            	form.resetForm();
            	$("#cencorMemo").text("");
            });
			// 列表页面分页方法调用
			pricingRule.pageList();

		},
		//审核操作
		pricingRuleCencorFormSub: function () {
        	var form = $("form[name=pricingRuleCencorForm]");
			form.ajaxSubmit({
    			url:pricingRule.appPath+"/pricingRule/cencorPricingRule.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
						$("#pricingRuleCencorModel").modal("hide");
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
							$("#pricingRuleList").DataTable().ajax.reload(function(){});
						});
					}else{
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					if (form.find("textarea[name='cencorMemo']").val()=="") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核备注不能为空！");
						return false;
					}
				}
			});
		},
		operateColumEvent : function() {
			$(".pricingRule-operate-view").each(function() {
				$(this).on("click", function() {
					addTab("计费规则详情",pricingRule.appPath+ "/pricingRule/toPricingRuleView.do?ruleNo="+$(this).data("id"));
				});
			});
			$(".pricingRule-operate-edit").each(function(){
				$(this).on("click",function(){
					addTab("计费规则修改",pricingRule.appPath+ "/pricingRule/toEditPricingRule.do?ruleNo="+$(this).data("id"));
				});
			});
			//审核弹出层
			$(".pricingRule-operate-cencor").each(function(id){
				$(this).on("click",function(){
					var ruleNo=$(this).data("id");
					var ruleName = $(this).data("name");
            	    $("#pricingRuleCencorModel").modal("show");
					$("#pricingRuleCencorNo").val(ruleNo);
					$("#pricingRuleCencorMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;["+ruleName+"]&nbsp;&nbsp;计费规则审核界面");
				});
			});
			
			//启停用
			$(".pricingRule-operate-isEnable").each(function(id,obj){
				$(this).on("click",function(){
					var id=$(this).data("id");
					var isEnable = $(this).data("isenable");
					var name = $(this).data("name");
					var hint = isEnable == 1 ? "启用" : "停用";
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+hint+"["+name+"] 计费规则吗？", function(result) {
						if(result){
							 $.ajax({
				    			url:pricingRule.appPath+"/pricingRule/updatePricingRule.do",
				    			type:"post",
				    			data:{
				    				ruleNo:id,
				    				isEnable:isEnable
				    			},
				    			dataType:"json",
				    			success:function(res){
				    				var code=res.code;
				    				var msg=res.msg;
			    					if(code == "1"){ 
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
			    							$("#pricingRuleList").DataTable().ajax.reload(function(){});
			    						});
			    					}else if(msg != null){
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败," + msg);
			    					}else{
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
			    					}
			    				}
			    			});
						}						
       				});
				});
			});
		},
		pageList : function() {
			var form = $("form[name=pricingRuleSerachForm]");
			var pricingRuleTpl = $("#pricingRuleTpl").html();
			// 预编译模板
			var template = Handlebars.compile(pricingRuleTpl);
			var table = $('#pricingRuleList').dataTable({
				searching : false,
				destroy : true,
				"ajax" : {
					"type" : "POST",
					"url" : pricingRule.appPath
							+ "/pricingRule/pageListPricingRule.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "ruleName" :form.find("input[name='ruleName']").val(),
										 "ruleNo" : form.find("input[name='ruleNo']").val(),
										 "cityName" :form.find("input[name='cityName']").val(),
										 "cencorStatus":form.find("select[name='cencorStatus']").val(),
										 "isEnable":form.find("select[name='isEnable']").val()
										});
					},
					"dataSrc" : function(json) {
						json.recordsTotal = json.rowCount;
						json.recordsFiltered = json.rowCount;
						json.data = json.data;
						return json.data;
					},
					error : function(xhr, error, thrown) {
					}
				},
				"columns" : [ 
					{ "title":"计费规则编号","data": "ruleNo"},
					{"title" : "计费规则名称","data" : "ruleName"}, 
					{"title" : "城市","data" : "cityName"}, 
					{"title" : "计费金额（元/小时）","data" : "hourPrice"}, 
					//{"title" : "每日封顶（小时）","data" : "dayCap"}, 
					//{"title" : "累计消费额度（转包月）","data" : "consumptionQuota"}, 
					{"title" : "审核状态","data" : "cencorStatus"},
					{"title" : "启用状态","data" : "isEnable"},
					{"title" : "操作","orderable" : false} 
				],
				"dom": "<'row'<'#pricingRuleTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				"initComplete" : function() {
					$("#pricingRuleTools").html("");
				   	$("#pricingRuleTools").css("float", "right");
				   	$("#pricingRuleTools").removeClass("col-xs-6");
				   	$("#pricingRuleTools").append('<button type="button" class="btn btn-default btn-sm pricingRuleTools-operate-add btnDefault" style="width: 125px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd" >新增计费规则</button>');
					$(".pricingRuleTools-operate-add").on("click",function(){
	       				addTab("新增计费规则", pricingRule.appPath+ "/pricingRule/toAddPricingRule.do");
	       			});	
				},
				"drawCallback" : function(settings) {
					pricingRule.operateColumEvent();
				},
				"columnDefs" : [
					{
						"targets" : [ 4 ],
						"render" : function(a,
								b, c, d) {
							var text ="";
							if(a==0){
								text="未审核";
							}else if(a==1){
								text="已审核";
							}else if(a==2){
								text="未通过";
							}
							return text;
						}
					},
					{
						"targets" : [ 5 ],
						"render" : function(a,
								b, c, d) {
							var text ="";
							if(a==0){
								text="不可用";
							}else if(a==1){
								text="可用";
							}
							return text;
						}
					},
					{
						targets : [6 ],
						render : function(a, b, c,d) {
							var view = '<span class="glyphicon pricingRule-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
									+ c.ruleNo
									+ '" >查看</span>';
							var edit='<span class="glyphicon pricingRule-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.ruleNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
							var censorStatus = "";
							var isEnable = "";
							if(c.cencorStatus==0){
								censorStatus='<span class="glyphicon pricingRule-operate-cencor" data-name="'+c.ruleName+'" style="text-decoration: underline; cursor: pointer;" data-id="'+ c.ruleNo	+ '" >审核</span>';
							}else if(c.cencorStatus==1){
								if (c.isEnable==0) {
									isEnable='<span class="glyphicon pricingRule-operate-isEnable" style="text-decoration: underline; cursor: pointer;"data-name="'+c.ruleName+'" data-id="'+c.ruleNo+'"  data-isEnable=1 data-toggle="tooltip" data-placement="top">启用</span>';
								}else if(c.isEnable==1) {
									
									isEnable='<span class="glyphicon pricingRule-operate-isEnable" style="text-decoration: underline; cursor: pointer;"data-name="'+c.ruleName+'" data-id="'+c.ruleNo+'" data-isEnable=0 data-toggle="tooltip" data-placement="top">停用</span>';
								}
							}
							return edit + censorStatus + isEnable +  view ;
						}
					}]
			});
		}
	};
	return pricingRule;
});
