define([],function() {	
	var advert={
			appPath:getPath("app"),	
			init: function () {	
	            //数据列表
				advert.pageList();
				//查询
				$("#advertSearchafter").click(function(){
					var form = $("form[name=advertSearchForm]");
					var createTimeStart =  form.find("input[name=createTimeStart]").val();
					var createTimeEnd = form.find("input[name=createTimeEnd]").val();
					if(createTimeStart!=""&&createTimeEnd!=""){
						if(new Date(createTimeStart)>new Date(createTimeEnd)){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "创建开始时间不能大于结束时间！");
						}else{
							advert.pageList();
						}
					}else{
						advert.pageList();
					}
	            });
				$("#closeAdvertView").click(function(){
					closeTab("文章详情");
				});
			},
			//方法加载
	        operateColumEvent: function(){
	        	$(".advert-operate-view").each(function(){
					$(this).on("click",function(){
						addTab("文章详情",advert.appPath+ "/advert/toAdvertView.do?advertNo="+$(this).data("id"));
					});
				});
				$(".advert-operate-edit").each(function(id,obj){
					$(this).on("click",function(){
						addTab("文章编辑",advert.appPath+ "/advert/toEditAdvert.do?advertNo="+$(this).data("id"));			
					});
				});
				$(".advert-operate-censor").each(function(id,obj){
					$(this).on("click",function(){
						addTab("文章审核",advert.appPath+ "/advert/toCensorAdvert.do?advertNo="+$(this).data("id"));			
					});
				});
				$(".advert-operate-available").each(function(id,obj){
					$(this).on("click",function(){
						var id=$(this).data("id");
						var isAvailable=$(this).data("available");
						var availableName = "启用";
						if(isAvailable == 1){
							isAvailable=0;
							availableName = "停用";
						}else{
							isAvailable=1;
						}
						bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要"+availableName+"文章吗？", function(result) {
							if(result){
								 $.ajax({
    				    			url:advert.appPath+"/advert/updateAdvertStatus.do",
    				    			type:"post",
    				    			data:{advertNo:id,isAvailable:isAvailable},
    				    			dataType:"json",
    				    			success:function(res){
					    				var code=res.code;
				    					if(code == "1"){ 
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "文章"+availableName+"操作成功", function() {
				    							$("#advertList").DataTable().ajax.reload(function(){});
				    						});
				    					}else{
				    						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "文章"+availableName+"操作失败");
				    					}
					    			}
    				    		});
							}						
	       				});
					});
				});
	        },
			pageList:function () {	
				var advertTpl = $("#advertTpl").html();
				// 预编译模板
				var template = Handlebars.compile(advertTpl);
				$('#advertList').dataTable( {
					searching:false,
					destroy: true,
					"ajax": {
						"type": "POST",
						"url": advert.appPath+'/advert/pageListAdvert.do',
						"data": function ( d ) {
							var form = $("form[name=advertSearchForm]");
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
								"pageSize":d.length,
								"advertName":form.find("input[name=advertName]").val(),
								"createTimeStart":form.find("input[name=createTimeStart]").val()+" 00:00:00",
								"createTimeEnd":form.find("input[name=createTimeEnd]").val()+" 23:59:59",
	        					"advertType":form.find("select[name=advertType]").val(),
	        					"censorStatus":form.find("select[name=censorStatus]").val(),
	        					"isAvailable":form.find("select[name=isAvailable]").val(),
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
						{ "title":"文章类型","data": "advertType" },
						{ "title":"文章名称","data": "advertName" },
//						{ "title":"排名","data": "ranking" },
						{ "title":"可用状态","data": "isAvailable" },
						{ "title":"审核状态","data": "censorStatus" },
						{ "title":"创建时间","data": "createTime" },
						{ "title":"操作","orderable":false}
					],
				   ///"dom": "<'row'<'col-xs-2'l><'#adverttool.col-xs-4'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "dom": "<'row'<'#advertTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				   initComplete: function () {
					   $("#advertTools").html("");
					   $("#advertTools").css("float", "right");
					   $("#advertTools").removeClass("col-xs-6");
		       			$("#advertTools").append('<button type="button" class="btn btn-default btn-sm advertTools-operate-add btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd" >新增</button>');
		       			$(".advertTools-operate-add").on("click",function(){
		       				addTab("新增文章", advert.appPath+ "/advert/toAddAdvert.do");
		       			});	 
					},
					"drawCallback": function( settings ) {
						advert.operateColumEvent();
	        	    },
					"columnDefs": [
					 	{
					 		"targets": [0],
						    "render": function(a,b, c, d) {
						    	if(a != null){
						    		if(a == 1){
						    			return "健身文章";
						    		}else if(a == 2){
						    			return "资讯文章";
						    		}else if(a == 3){
						    			return "广告文章";
						    		}
						    	}
						    	return "--";
						    }
						},{
						    "targets": [2],
						    "render": function(a,b, c, d) {
						    	if(a != null){
						    		if(a == 1){
						    			return "可用";
						    		}else if(a==0){
						    			return "不可用";
						    		}
						    	}
						    	return "--";
						    }
						},{
						    "targets": [3],
						    "render": function(a,b, c, d) {//0、未审核，1、已审核，2、待审核，3、未通过
						    	if(a != null){
						    		if(a == 0){
						    			return "未审核";
						    		}else if(a==1){
						    			return "已审核";
						    		}else if(a==2){
						    			return "未通过";
						    		}
						    	}
						    	return "--";
						    }
						},{
						    "targets": [4],
						    "render": function(data, type, row, meta) {
						    	if(data != null){
						    		return moment(data).format('YYYY-MM-DD HH:mm:ss'); 
						    	}
						    	return "--";
						    }
						},{
							"targets": [5],
							"render": function (a, b, c, d) {
								var view='<span class="glyphicon advert-operate-view" style="text-decoration: underline; cursor: pointer;"data-id="'+c.advertNo+'" data-toggle="tooltip" data-placement="top" >查看</span>';
								var edit='<span class="glyphicon advert-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.advertNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
	        					var censor="";
	        					var available="";
	        					debugger
								if(c.censorStatus==0){
									censor='<span class="glyphicon advert-operate-censor" style="text-decoration: underline; cursor: pointer;"data-id="'+c.advertNo+'" data-toggle="tooltip" data-placement="top">审核</span>';
									edit='<span class="glyphicon advert-operate-edit" style="text-decoration: underline; cursor: pointer;"data-id="'+c.advertNo+'" data-toggle="tooltip" data-placement="top" >修改</span>';
								}else if(c.censorStatus==1){
								if(c.isAvailable==1){
									available='<span class="glyphicon advert-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.advertNo+'" data-available="'+c.isAvailable+'" data-toggle="tooltip" data-placement="top">停用</span>';
									edit="";
								}else{
									available='<span class="glyphicon advert-operate-available" style="text-decoration: underline; cursor: pointer;" data-id="'+c.advertNo+'" data-available="'+c.isAvailable+'"  data-toggle="tooltip" data-placement="top">启用</span>';
									
								}
								}
	        					return view+edit + censor + available;
							}
						}
					]
				});
			}
	    };
	return advert;
});


