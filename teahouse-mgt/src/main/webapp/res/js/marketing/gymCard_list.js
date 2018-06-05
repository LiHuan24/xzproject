define([],function() {
	var gymCard = {
		appPath : getPath("app"),
		init : function() {
			$("#closeGymCardView").click(function(){
				closeTab("健身卡详情");
			});
			// 列表页面搜索调用
			$("#gymCardSearch").click(function() {
				gymCard.pageList();
			});
			//审核提交
			$("#gymCardCencorBtn").click(function(){
				gymCard.gymCardCencorFormSub();
            });
			//审核取消
			$("#gymCardCencorCancel").click(function(){
				$("#gymCardCencorModel").modal("hide");
            });
			$("#gymCardCencorModel").on("hidden.bs.modal", function() {  
            	var form = $("form[name=gymCardCencorForm]");
            	form.resetForm();
            	$("#cencorMemo").text("");
            });
			// 列表页面分页方法调用
			gymCard.pageList();

		},
		//审核操作
		gymCardCencorFormSub: function () {
        	var form = $("form[name=gymCardCencorForm]");
			form.ajaxSubmit({
    			url:gymCard.appPath+"/gymCard/updateGymCardNs.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
						$("#gymCardCencorModel").modal("hide");
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
							$("#gymCardList").DataTable().ajax.reload(function(){});
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
			$(".gymCard-operate-view").each(function() {
				$(this).on("click", function() {
					addTab("健身卡详情",gymCard.appPath+ "/gymCard/toGymCardView.do?gymCardNo="+$(this).data("id"));
				});
			});
			$(".gymCard-operate-edit").each(function(){
				$(this).on("click",function(){
					addTab("健身卡修改",gymCard.appPath+ "/gymCard/toEditGymCard.do?gymCardNo="+$(this).data("id"));
				});
			});
			//审核弹出层
			$(".gymCard-operate-cencor").each(function(id){
				$(this).on("click",function(){
					var gymCardNo=$(this).data("id");
					var gymCardName = $(this).data("name");
            	    $("#gymCardCencorModel").modal("show");
					$("#gymCardCencorNo").val(gymCardNo);
					$("#gymCardCencorMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;["+gymCardName+"]&nbsp;&nbsp;健身卡审核界面");
				});
			});
			
			//启停用
			$(".gymCard-operate-isEnable").each(function(id,obj){
				$(this).on("click",function(){
					var id=$(this).data("id");
					var isEnable = $(this).data("isenable");
					var name = $(this).data("name");
					var hint = isEnable == 1 ? "启用" : "停用";
					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+hint+"["+name+"] 健身卡吗？", function(result) {
						if(result){
							 $.ajax({
				    			url:gymCard.appPath+"/gymCard/updateGymCard.do",
				    			type:"post",
				    			data:{
				    				gymCardNo:id,
				    				isEnable:isEnable
				    			},
				    			dataType:"json",
				    			success:function(res){
				    				var code=res.code;
				    				var msg=res.msg;
			    					if(code == "1"){ 
			    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
			    							$("#gymCardList").DataTable().ajax.reload(function(){});
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
			var form = $("form[name=gymCardSerachForm]");
			var gymCardTpl = $("#gymCardTpl").html();
			// 预编译模板
			var template = Handlebars.compile(gymCardTpl);
			var table = $('#gymCardList').dataTable({
				searching : false,
				destroy : true,
				"ajax" : {
					"type" : "POST",
					"url" : gymCard.appPath
							+ "/gymCard/pageListGymCard.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "gymCardName" :form.find("input[name='gymCardName']").val(),
										 "gymCardNo" : form.find("input[name='gymCardNo']").val(),
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
					{ "title":"健身卡编号","data": "gymCardNo"},
					{"title" : "健身卡名称","data" : "gymCardName"}, 
					{"title" : "城市","data" : "cityName"}, 
					{"title" : "销售价","data" : "price"}, 
					/*{"title" : "健身卡金额","data" : "rechargeAmount"}, */
					{"title" : "审核状态","data" : "cencorStatus"},
					{"title" : "启用状态","data" : "isEnable"},
					{"title" : "操作","orderable" : false} 
				],
				"dom": "<'row'<'#gymCardTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				"initComplete" : function() {
					$("#gymCardTools").html("");
				   	$("#gymCardTools").css("float", "right");
				   	$("#gymCardTools").removeClass("col-xs-6");
				   	$("#gymCardTools").append('<button type="button" class="btn btn-default btn-sm gymCardTools-operate-add btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd" >新增健身卡</button>');
					$(".gymCardTools-operate-add").on("click",function(){
	       				addTab("新增健身卡", gymCard.appPath+ "/gymCard/toAddGymCard.do");
	       			});	
				},
				"drawCallback" : function(settings) {
					gymCard.operateColumEvent();
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
						targets : [ 6 ],
						render : function(a, b, c,d) {
							var view = '<span class="glyphicon gymCard-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
									+ c.gymCardNo
									+ '" >查看</span>';
							var edit='<span class="glyphicon gymCard-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.gymCardNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
							var censorStatus = "";
							var isEnable = "";
							if(c.cencorStatus==0){
								censorStatus='<span class="glyphicon gymCard-operate-cencor" data-name="'+c.gymCardName+'" style="text-decoration: underline; cursor: pointer;" data-id="'+ c.gymCardNo	+ '" >审核</span>';
							}else if(c.cencorStatus==1){
								if (c.isEnable==0) {
									isEnable='<span class="glyphicon gymCard-operate-isEnable" style="text-decoration: underline; cursor: pointer;"data-name="'+c.gymCardName+'" data-id="'+c.gymCardNo+'"  data-isEnable=1 data-toggle="tooltip" data-placement="top">启用</span>';
								}else if(c.isEnable==1) {
									edit = "";
									isEnable='<span class="glyphicon gymCard-operate-isEnable" style="text-decoration: underline; cursor: pointer;"data-name="'+c.gymCardName+'" data-id="'+c.gymCardNo+'" data-isEnable=0 data-toggle="tooltip" data-placement="top">停用</span>';
								}
							}
							return edit + censorStatus + isEnable +  view ;
						}
					}]
			});
		}
	};
	return gymCard;
});
