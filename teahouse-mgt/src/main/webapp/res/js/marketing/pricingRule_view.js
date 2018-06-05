define([],function() {	
	var pricingRuleView = {
		appPath : getPath("app"),
		init : function() {
			//详情关闭
			$("#closePricingRuleView").click(function(){
				closeTab("计费规则详情");
			});
			
			//初始化显示自定义表
			pricingRuleView.pageListCustomPricingRule();
		},
		pageListCustomPricingRule:function () {	
			$('#pricingRuleCustomListView').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": pricingRuleView.appPath+"/customPricingRule/pageListCustomPricingRule.do",
					"data": function ( d ) {
						var form = $("form[name=pricingRuleViewFrom]");
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"ruleNo":$.trim(form.find("input[name=ruleNo]").val()),
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
				],
			   initComplete: function () {
				    $("#parkTools").html("");
				},
				"drawCallback": function( settings ) {
	    	    },
	    	    "order": [[ 1, "desc" ]],
				"columnDefs": [
	            	{
					    "targets": [0],
					    "render": function(a,b, c, d) {
					        if(a!=null){
								return moment(a).format('YYYY-MM-DD');
					        }
				        	return "--";
					    }
					}
				]
			});
		}	
	}
	return	pricingRuleView;
});


