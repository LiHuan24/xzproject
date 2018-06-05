define([],function() {
	var recharge = {
		appPath : getPath("app"),
		init : function() {
			$("#closeRechargeView").click(function(){
				closeTab("充值包详情");
			});
			// 列表页面搜索调用
			$("#rechargeSearch").click(function() {
				recharge.pageList();
			});
			//审核提交
			$("#rechargeCencorBtn").click(function(){
				recharge.rechargeCencorFormSub();
            });
			//审核取消
			$("#rechargeCencorCancel").click(function(){
				$("#rechargeCencorModel").modal("hide");
            });
			$("#rechargeCencorModel").on("hidden.bs.modal", function() {  
            	var form = $("form[name=rechargeCencorForm]");
            	form.resetForm();
            	$("#cencorMemo").text("");
            });
			// 列表页面分页方法调用
			recharge.pageList();

		},
		//审核操作
		rechargeCencorFormSub: function () {
        	var form = $("form[name=rechargeCencorForm]");
			form.ajaxSubmit({
    			url:recharge.appPath+"/recharge/updateRechargeNs.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
						$("#rechargeCencorModel").modal("hide");
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
							$("#rechargeList").DataTable().ajax.reload(function(){});
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
			$(".recharge-operate-view").each(function() {
				$(this).on("click", function() {
					addTab("充值包详情",recharge.appPath+ "/recharge/toRechargeView.do?rechargeNo="+$(this).data("id"));
				});
			});
			$(".recharge-operate-edit").each(function(){
				$(this).on("click",function(){
					addTab("充值包修改",recharge.appPath+ "/recharge/toEditRecharge.do?rechargeNo="+$(this).data("id"));
				});
			});
			//审核弹出层
			$(".recharge-operate-cencor").each(function(id){
				$(this).on("click",function(){
					var rechargeNo=$(this).data("id");
					var rechargeName = $(this).data("rechargename");
            	    $("#rechargeCencorModel").modal("show");
					$("#rechargeCencorNo").val(rechargeNo);
					$("#rechargeCencorMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;["+rechargeName+"]&nbsp;&nbsp;充值包审核界面");
				});
			});
			
			//启停用
			$(".recharge-operate-isEnable").each(function(id,obj){
				$(this).on("click",function(){
					var id=$(this).data("id");
					var isEnable = $(this).data("isenable");
					var name = $(this).data("name");
					var hint = isEnable == 1 ? "启用" : "停用";
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+hint+"["+name+"] 充值包吗？", function(result) {
						if(result){
							 $.ajax({
				    			url:recharge.appPath+"/recharge/updateRecharge.do",
				    			type:"post",
				    			data:{
				    				rechargeNo:id,
				    				isEnable:isEnable
				    			},
				    			dataType:"json",
				    			success:function(res){
				    				var code=res.code;
				    				var msg=res.msg;
			    					if(code == "1"){ 
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
			    							$("#rechargeList").DataTable().ajax.reload(function(){});
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
			var form = $("form[name=rechargeSerachForm]");
			var rechargeTpl = $("#rechargeTpl").html();
			// 预编译模板
			var template = Handlebars.compile(rechargeTpl);
			var table = $('#rechargeList').dataTable({
				searching : false,
				destroy : true,
				"ajax" : {
					"type" : "POST",
					"url" : recharge.appPath
							+ "/recharge/pageListRecharge.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "rechargeName" :form.find("input[name='rechargeName']").val(),
										 "rechargeNo" : form.find("input[name='rechargeNo']").val(),
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
					{ "title":"充值包编号","data": "rechargeNo"},
					{"title" : "充值包名称","data" : "rechargeName"}, 
					{"title" : "城市","data" : "cityName"}, 
					{"title" : "销售价","data" : "price"}, 
					/*{"title" : "充值包金额","data" : "rechargeAmount"}, */
					{"title" : "审核状态","data" : "cencorStatus"},
					{"title" : "启用状态","data" : "isEnable"},
					{"title" : "操作","orderable" : false} 
				],
				"dom": "<'row'<'#rechargeTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				"initComplete" : function() {
					$("#rechargeTools").html("");
				   	$("#rechargeTools").css("float", "right");
				   	$("#rechargeTools").removeClass("col-xs-6");
				   	$("#rechargeTools").append('<button type="button" class="btn btn-default btn-sm rechargeTools-operate-add btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd" >新增充值包</button>');
					$(".rechargeTools-operate-add").on("click",function(){
	       				addTab("新增充值包", recharge.appPath+ "/recharge/toAddRecharge.do");
	       			});	
				},
				"drawCallback" : function(settings) {
					recharge.operateColumEvent();
				},
				"columnDefs" : [
					{
						"targets" : [ 4],
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
						targets : [ 6 ],
						render : function(a, b, c,d) {
							var view = '<span class="glyphicon recharge-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
									+ c.rechargeNo
									+ '" >查看</span>';
							var edit='<span class="glyphicon recharge-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.rechargeNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
							var censorStatus = "";
							var isEnable = "";
							if(c.cencorStatus==0){
								censorStatus='<span class="glyphicon recharge-operate-cencor" data-rechargeName="'+c.rechargeName+'" style="text-decoration: underline; cursor: pointer;" data-id="'+ c.rechargeNo	+ '" >审核</span>';
							}else if(c.cencorStatus==1){
								if (c.isEnable==0) {
									isEnable='<span class="glyphicon recharge-operate-isEnable" style="text-decoration: underline; cursor: pointer;"data-name="'+c.rechargeName+'" data-id="'+c.rechargeNo+'"  data-isEnable=1 data-toggle="tooltip" data-placement="top">启用</span>';
								}else if(c.isEnable==1) {
									edit = "";
									isEnable='<span class="glyphicon recharge-operate-isEnable" style="text-decoration: underline; cursor: pointer;"data-name="'+c.rechargeName+'" data-id="'+c.rechargeNo+'" data-isEnable=0 data-toggle="tooltip" data-placement="top">停用</span>';
								}
							}
							return edit + censorStatus + isEnable +  view ;
						}
					}]
			});
		}
	};
	return recharge;
});
