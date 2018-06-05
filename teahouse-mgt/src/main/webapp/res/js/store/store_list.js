define([],function() {
	var store = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面搜索调用
			$("#storeSearch").click(function() {
				store.pageList();
			});
			// 列表页面分页方法调用
			store.pageList();
			
			//启用状态提交
			$("#storeOnFormBtn").click(function(){
				store.onFormSub();
            });
			//启用取消
			$("#storeOnCancelBtn").click(function(){
				$("#onStoreModal").modal("hide");
            });
			//停用状态提交
			$("#storeOffBtn").click(function(){
				store.offFormSub();
            });
			//停用取消
			$("#storeOffCancel").click(function(){
				$("#offStoreModal").modal("hide");
            });
		},
		
		operateColumEvent : function() {
			$(".store-operate-edit").each(function() {
				$(this).on("click", function() {
					addTab("门店编辑",store.appPath+ "/store/toStoreEdit.do?storeNo="+$(this).data("id"));
				});
			});
			
			$(".store-operate-view").each(function() {
				$(this).on("click", function() {
					addTab("门店查看",store.appPath+ "/store/toStoreView.do?storeNo="+$(this).data("id"));
				});
			});
			
			//门店：主题馆进行排课
			$(".store-operate-class").each(function() {
				$(this).on("click", function() {
					addTab("排课",store.appPath+ "/arrayCourse/toArrayCourseList.do?storeNo="+$(this).data("id"));
				});
			});
			
			//启用弹出层
			$(".start-operate-store").each(function() {
				$(this).on("click", function() {
					var storeNo=$(this).data("id");
					$.ajax({
						 type: "post",
			             url: store.appPath+"/store/toStore.do",
			             data: {storeNo:storeNo},
			             dataType: "json",
			             success: function(data){
			            	 if(data.code="1"){
			            	    $("#onStoreModal").modal("show");
			            	    $("textarea[name=startBlockReason]").val("");
			            	    $("body")[0].style.paddingRight=0;
								$("#storeNo1").val(data.storeNo);
								$("#cityName1").val(data.cityName);
								$("#onStoreMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将该门店"+"“"+data.storeNo+"”"+" 启用吗？");
							 }
			             }
					});
				});
			});
			
			//停用弹出层
			$(".block-operate-store").each(function() {
				$(this).on("click", function() {
					var storeNo=$(this).data("id");
					$.ajax({
						 type: "post",
			             url: store.appPath+"/store/toStore.do",
			             data: {storeNo:storeNo},
			             dataType: "json",
			             success: function(data){
			            	 if(data.code="1"){
			            	    $("#offStoreModal").modal("show");
			            	    $("textarea[name=startBlockReason]").val("");
			            	    $("body")[0].style.paddingRight=0;
								$("#storeNo2").val(data.storeNo);
								$("#cityName2").val(data.cityName);
								$("#offStoreMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将该门店"+"“"+data.storeNo+"”"+" 停用吗？");
							 }
			             }
					});
				});
			});
		},
		
        //启用操作
        onFormSub: function () {
        	var form = $("form[name=onStoreForm]");
			form.ajaxSubmit({
    			url:store.appPath+"/store/changeStoreState.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
							$("#onStoreModal").modal('hide')
						});
						$("#storeList").DataTable().ajax.reload(function(){
						}); 
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					if (form.find("textarea[name='startBlockReason']").val()=="") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入理由！");
						return false;
					}
				}
			});
		},
		//停用操作
		offFormSub: function () {
        	var form = $("form[name=offStoreForm]");
			form.ajaxSubmit({
				url:store.appPath+"/store/changeStoreState.do",
				type : "post",
				dataType:"json", //数据类型  
				success:function(res){
					var msg = res.msg;
					var code = res.code;
					if(code == "1"){ 
					  bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功！", function() {
							$("#offStoreModal").modal('hide')
					  });
					  $("#storeList").DataTable().ajax.reload(function(){
					  }); 
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					if (form.find("textarea[name='startBlockReason']").val()=="") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入理由！");
						return false;
					}
				}
			});
		},
		//分页列表
		pageList : function() {
			var form = $("form[name=storeSerachForm]");
			var storeNo = form.find("input[name='storeNo']").val();
			var storeType = form.find("select[name='storeType']").val();
			var storeStatus = form.find("select[name='storeStatus']").val();
			
			var storeBtnTpl = $("#storeBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(storeBtnTpl);
			var table = $('#storeList')
				.dataTable(
				{
					searching : false,
					destroy : true,
					"ajax" : {
						"type" : "POST",
						"url" : store.appPath
								+ "/store/storePageList.do",
						"data" : function(d) {
							return $.extend({},d,
									{"pageNo" : (d.start / d.length) + 1,
									 "pageSize" : d.length,
									 "storeNo" : storeNo,
									 "storeType" : storeType,
									 "storeStatus":storeStatus
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
					{
						"title" : "门店编号",
						"data" : "storeNo"
					}, {
						"title" : "城市",
						"data" : "cityName"
					}, {
						"title" : "门店名称",
						"data" : "storeName"
					} ,
					{
						"title" : "门店类型",
						"data" : "storeType"
					} ,
					{
						"title" : "门店状态",
						"data" : "storeStatus"
					} ,
					{
						"title" : "地址",
						"data" : "addrStreet"
					} ,
					{
						"title" : "门禁状态",
						"data" : "entranceStatus"
					} ,
					{
						"title" : "操作",
						"orderable" : false
					} ],
					"dom": "<'row'<'#storeTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
					   initComplete: function () {
						   $("#storeTools").css("float", "right");
						   $("#storeTools").removeClass("col-xs-6");
						   $("#storeTools").append('<button type="button" class="btn btn-default btn-sm store-operate-add" data-toggle="modal" data-target="#storeToolsModal" id="storeModel" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">新增门店</button>');
						   $(".store-operate-add").on("click",function(){
		        				addTab("新增门店", store.appPath+ "/store/toStoreAdd.do");
			       			});
					   },
					"drawCallback" : function(settings) {
						store.operateColumEvent();
					},
					"order" : [ [ 0, "desc" ] ],
					"columnDefs" : [
						{
							"targets" : [ 3 ],
							"render" : function(a, b, c, d) {
								var storeType;
								if(a==0){
									storeType="社区馆"
								}else if(a==1){
									storeType="主题馆"
								}else{
									return "--";
								}
								return storeType;
							}
						},
						{
							"targets" : [ 4 ],
							"render" : function(a, b, c, d) {
								var storeStatus;
								if(a==0){
									storeStatus="启用";
								}else if(a==1){
									storeStatus="停用";
								}else{
									return "--";
								}
								return storeStatus;
							}
						},
						{
							"targets" : [ 6 ],
							"render" : function(a, b, c, d) {
								var entranceStatus;
								if(a==0){
									entranceStatus="下线";
								}else if(a==1){
									entranceStatus="上线";
								}else{
									return "--";
								}
								return entranceStatus;
							}
						},
						{
							targets : 7,
							render : function(a, b, c,
									d) {
								var makeClass = "";
								var edit = "";
								var view = '<span class="glyphicon store-operate-view" style="text-decoration: underline; cursor: pointer;" data-id="'
									+ c.storeNo
									+ '" >查看</span>';
								
								if(c.storeType == 1){
									if(c.storeStatus == 0){
										makeClass = '<span class="glyphicon store-operate-class" style="text-decoration: underline; cursor: pointer;" data-id="'
											+ c.storeNo
											+ '" >排课</span>';
									}
								}else{
									makeClass = "";
								}
								
								var storeStart = "";//启用
								var storeBlock = "";//停用
								if(c.storeStatus == 0){
									storeBlock = '<span class="glyphicon block-operate-store" style="text-decoration: underline; cursor: pointer;" data-id="'
										+ c.storeNo
										+ '" >停用</span>';
								}else{
									edit = '<span class="glyphicon store-operate-edit" style="text-decoration: underline; cursor: pointer;" data-id="'
										+ c.storeNo
										+ '" >编辑</span>';
									storeStart = '<span class="glyphicon start-operate-store" style="text-decoration: underline; cursor: pointer;" data-id="'
										+ c.storeNo
										+ '" >启用</span>';
								}
								
								//下载二维码
								//var qrcode='<span class="glyphicon store-operate-qrcode" data-id="'+c.storeNo+'" data-toggle="tooltip" data-placement="top" style="text-decoration: underline; cursor: pointer;">下载二维码</span>';
								return edit+view+makeClass+storeStart+storeBlock;
						}
					}]
				});
		},
	};
	return store;
});