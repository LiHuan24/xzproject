define([],function() {
var storeEdit = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	resImgPath:getPath("resImg"),
	beforePath:getPath("before"),
	init : function() {
		//添加提交
		$("#saveStoreEdit").click(function(){
			storeEdit.addStore();
		});
		//返回
		$("#closeStoreEdit").click(function(){
			closeTab("门店编辑");
			$("#storeList").DataTable().ajax.reload(function(){});
		});
		$("#editStorePhotoUs").click(function(){
			$("#storeEditModalUs").modal("show");
		});
		
		storeEdit.uploadFile();
		var form1=$("form[name=stroeEditForm]");
		var storeNo = form1.find("input[name=storeNo]").val();
		storeEdit.getItemPics(storeNo);
		
		
		//新增图片回填
		$("#editStorePhotoBtnUs").click(function(){
			
			var ls= storeEdit.getUploadFilePath();
			if(ls.length>0){
				var form1=$("form[name=stroeEditForm]");
				form1.find("input[name=storePictureUrl1]").val(ls.toString());
			}
			$("#storeEditModalUs").modal("hide");
		});
		
        //高德地图点击地图获取地址
        $("#mapLocationGdEdit").click(function(){
        	$("#lctNsEdit").empty();
        	$("input[name=addrStreet]").empty();
        	$("#mapLocationEdit").modal("show");
        	var form = $("form[name=stroeEditForm]");
    		var mlon = form.find("input[name='longitude']").val();
            var mlat = form.find("input[name='latitude']").val();
        	$("#mapLctNsEdit").css({
				"width":"800px",
				"height":"500px",
				"overflow":"hidden",
				"margin":"0px"
			});
        	setTimeout(function(){
        		var map = new AMap.Map("mapLctNsEdit", {
        	        resizeEnable: true,
        			zoom: 15
        	    });
        	    
        		AMap.service('AMap.Geocoder',function(){//回调函数
     		        //实例化Geocoder
     		        geocoder = new AMap.Geocoder({
     		            city: "010"//城市，默认：“全国”
     		        });
     		    })
     		    
     		    AMap.plugin(['AMap.ToolBar','AMap.Scale','AMap.OverView'],function(){
                     map.addControl(new AMap.ToolBar());
                     map.addControl(new AMap.Scale());
                     map.addControl(new AMap.OverView({isOpen:true}));
                 })
                
                var placeSearch;
        		var initmarker = "";//初始化标注
        		if(mlon == "" && mlat == ""){
		    		AMap.service(["AMap.PlaceSearch"], function() {
		             	placeSearch = new AMap.PlaceSearch({ //构造地点查询类
		                 	city: "010"//城市
		             });
		       		}); 
        		}else{
	            	 //根据终点位置定位当前区域
	            	 AMap.service('AMap.Geocoder',function(){//回调函数
	 					var geocoder = new AMap.Geocoder({
	 			            radius: 1000,
	 			            extensions: "all"
	 			        });
	 					var lnglat = [mlon,mlat];
	 			        geocoder.getAddress(lnglat, function(status, result) {
	 			            if (status === 'complete' && result.info === 'OK') {
	 			            	 var address = result.regeocode.formattedAddress; //返回地址描述
	                             $("#lctNsEdit").html(address);
	                             form.find("input[name='addrStreet']").val(address);
	 			            }
	 			        }); 
	 			       initmarker = new AMap.Marker({  //加点
	 			            map: map,
	 			            position: lnglat
	 			        });
	 			       map.setCenter(lnglat);
	        		})
	             }
                //高德地图点击事件
                map.on('click', function(e) {
                  	if(initmarker !=""){
                  		map.remove(initmarker);
                  	}
                 	var lnglatXY = [e.lnglat.getLng(),e.lnglat.getLat()];
                 	$("input[name=longitude]").val(e.lnglat.getLng());
                 	$("input[name=latitude]").val(e.lnglat.getLat());
	        		AMap.service('AMap.Geocoder',function(){//回调函数
	 					var geocoder = new AMap.Geocoder({
	 			            radius: 1000,
	 			            extensions: "all"
	 			        });
	 					geocoder.getAddress(lnglatXY, function(status, result) {
	                         if (status === 'complete' && result.info === 'OK') {
	                        	 var address = result.regeocode.formattedAddress; //返回地址描述
	                             $("#lctNsEdit").html(address);
	                 			 $("input[name=addrStreet]").val(address);
	                         }
	                     });          
	                    //执行定位
	                    var marker = new AMap.Marker({  //加点
	 			            map: map,
	 			            position: lnglatXY
	 			        });
	                    
	                  //为地图注册click事件获取鼠标点击出的经纬度坐标
	                    var clickEventListener = map.on('click', function(e) {
	                    	  lng = e.lnglat.getLng(); 
	                    	  lat = e.lnglat.getLat();
	                          lnglatXY = [e.lnglat.getLng(),e.lnglat.getLat()]; //已知点坐标   
	                          marker.setPosition(lnglatXY); 
	                    });
	                    
	                    AMap.plugin('AMap.Autocomplete',function(){   
	                        var auto = new AMap.Autocomplete({
	                            input: "keywords"
	                        });
	                        AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
	                        function select(e) {
	                            if (e.poi && e.poi.location) {
	                                map.setZoom(15);
	                                map.setCenter(e.poi.location);
	                            }
	                          }
	                        })    
	        			})
                   });
        	    
        	},200);
        });

	},
	addStore:function() {
		var form = $("form[name=stroeEditForm]");
		
		$("span[name='storeNameEdit']").empty();
		$("span[name='cityEdit']").empty();
		$("span[name='storeTypeEdit']").empty();
		$("span[name='latlongEdit']").empty();
		$("span[name='synopsisEdit']").empty();
		$("span[name='memoEdit']").empty();
		
		if (form.find("input[name='storeName']").val()=="") {
			$("span[name='storeNameEdit']").append("<font color='red' class='formtips onError emaill'>请输入门店名称！</font>");
			return false;
		}
		if (form.find("select[name='cityId']").val()=="") {
			$("span[name='cityEdit']").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
			return false;
		}
		if (form.find("select[name='storeType']").val()=="") {
			$("span[name='storeTypeEdit']").append("<font color='red' class='formtips onError emaill'>请选择门店类型！</font>");
			return false;
		}
		if (form.find("input[name='longitude']").val()=="" && form.find("input[name='latitude']").val()=="") {
			$("span[name='latlongEdit']").append("<font color='red' class='formtips onError emaill'>请先选好门店地图定位！</font>");
			return false;
		}
		var synopsis = form.find("textarea[name='synopsis']").val();
		var memo = form.find("textarea[name='memo']").val();
		if(synopsis.length > 200){
			$("span[name='synopsisEdit']").append("<font color='red' class='formtips onError emaill'>门店介绍输入字符不能大于200个字符！</font>");
			return false;
		}
		if(memo.length > 200){
			$("span[name='memoEdit']").append("<font color='red' class='formtips onError emaill'>门店备注输入字符不能大于200个字符！</font>");
			return false;
		}
		
		var beginDate=form.find("input[name=storeOpenDate]").val();  
		var endDate=form.find("input[name=storeColseDate]").val();  
		var d1 = new Date(beginDate.replace(/\-/g, "\/"));  
		var d2 = new Date(endDate.replace(/\-/g, "\/"));  
		if(d1 >=d2)  
		{  
			 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于等于结束时间！");  
			 return false;  
		}
		var storeId = form.find("input[name='storeNo']").val();
		var entranceCode = $.trim(form.find("input[name='entranceCode']").val());
		if(entranceCode == ""){
			$("span[name='entranceCodeEdit']").append("<font color='red' class='formtips onError emaill'>请输入门禁编码！</font>");
			return false;  
		}
		
		 $.post("store/checkEditEntranceCode.do",{storeId:storeId,entranceCode:entranceCode},function(res){
			 if(res.code == '1'){
				 $("span[name='entranceCodeEdit']").append("<font color='red'>门禁编码重复，请重新输入！</font>");
				 return false;
			 }else{
				form.ajaxSubmit({
					url : storeEdit.appPath + "/store/saveOrUpdateStore.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
								closeTab("门店编辑");
								$("#storeList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
						}
					},
				});
			 }
		 });
	  },
	  
	  //执行图片上传
	  uploadFile:function(){
			var relativePath="store_photo";
			var resImgPath=storeEdit.resImgPath;
			var imgPath=storeEdit.imgPath;
			var storePath=resImgPath+"/" + relativePath;
			$("#appUpload").load("res/tpl/uploadFile.html",function(){
		        var manualUploader = new qq.FineUploader({
		            element: document.getElementById("storeEditFineUploaderUs"),
		            template: "qq-template-manual-trigger-m",
		            request: {
		                endpoint: storeEdit.appPath+"/upload/uploadFileNew.do",
		                method: "post",
		                params:{
		                	storePath:storePath,
		                	resPath:relativePath
		                }
		            },
		            validation: {
		                allowedExtensions: ["jpeg", "jpg", "gif", "png"]
		            },
		            thumbnails: {
		                placeholders: {
		                    waitingPath: "res/dep/fine-uploader/placeholders/waiting-generic.png",
		                    notAvailablePath: "res/dep/fine-uploader/placeholders/not_available-generic.png"
		                }
		            },
		            autoUpload: false,
		            callbacks: {
		                onUpload: function (id, fileName) {
		                },
		                onSubmitted: function (id, fileName) {
		                	var li=$("#storeEditFineUploaderUs .qq-uploader .qq-upload-list").find("li");
                      	if(li != null && li.length < 4){
      						li=$("#storeEditFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
      						storeEdit.itemPicsBindEvent(li);
	                        li.find(".qq-upload-remove").hide();
      					}else{
	                        $("#storeEditFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]")[0].remove();
	                        //bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "上传图片不能超过3张！");
	                        $("span[name='storeEditWs']").empty();
	                        $("span[name='storeEditWs']").append("<font color='red' class='formtips onError emaill'>上传图片不能超过3张，多出图片已被自动删除。</font>");
      					}
		                },
		                onComplete: function(id, fileName, responseJSON) {
                      	var form = $("form[name='storePhotoFormUs']");
                      	var form1=$("form[name=stroeEditForm]");
		                	if(form.find("input[name='storePictureUrl1']").val() != ""){
		                		var path = form.find("input[name='storePictureUrl1']").val() + "," + responseJSON.data[0]
		                    	form.find("input[name='storePictureUrl1']").val(path);                        	
		                		form1.find("input[name=storePictureUrl1]").val(path);
		                	}else{
		                		form.find("input[name='storePictureUrl1']").val(responseJSON.data[0]);
		                		form1.find("input[name=storePictureUrl1]").val(responseJSON.data[0]);

		                	}
		                	var li=$("#storeEditFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
		                	li.attr("data-filepath",responseJSON.data[0]);
		                	if(responseJSON.success){
		                		li.find(".qq-upload-remove").show();                  		
		                	}
		                },
		                onProgress: function(id,  fileName,  loaded,  total) {
		                },
		                onCancel: function(id,  fileName) {                       	
		                }, 
		            }
		        });
		        $("#storeEditFineUploaderUs .trigger-upload").on("click", function() {
		            manualUploader.uploadStoredFiles();
		        }); 
		    });	        	
		},
		deleteFile:function(filePaths,fn){
			debugger
			var resPath="";
			var regexp = /res\/img/g;	    		
			if(filePaths.match(regexp)){
				resPath="";
			}else{
				resPath=storeEdit.resImgPath
			}
			$.ajax({
				url: storeEdit.appPath+"/upload/deleteFile.do?filePaths="+filePaths,
				data:{resPath:resPath},
				success: function(data){
					if(data.code=1){
						var form = $("form[name='storePhotoFormUs']");
						var input = form.find("input[name='storePictureUrl1']");
      				if(input != ""){
      					var aa = input.val();
                  		var urls = input.val().split(",");
                  		var newUrls = [];
                  		if(urls != null){
                  			for(var i = 0 ; i < urls.length; i++){
                      			if(urls[i] != filePaths){
                      				newUrls[newUrls.length] = urls[i];
                      			}
                      		}
                  			input.val(newUrls);  
                  			var form1=$("form[name=stroeEditForm]");
          					form1.find("input[name=storePictureUrl1]").val(newUrls);
                  		}
                  	}
						if(fn){
							fn();
						}
					}
				}
			});        	
		},
		getUploadFilePath:function(){
			debugger
			var filePath=[];	        	
			$("#storeEditFineUploaderUs .qq-upload-success").each(function(){
				filePath.push($(this).data("filepath"));                		
			});
			return filePath;
		},
		getItemPics:function(storeNo){
			debugger
			var uploadTpl = $("#upload-item-template").html();
			$.ajax({
				url: storeEdit.appPath+"/store/getStorePitureUrl.do?storeNo="+storeNo, 
				success: function(data){
					if(data){
						for(x in data){
							var to=$("#storeEditFineUploaderUs .qq-uploader .qq-upload-list");
							var li=$(uploadTpl).appendTo(to);
							var regexp = /res\/img/g;
							var imgsrc="";
							if(data[x].match(regexp)){
								imgsrc=storeEdit.appPath+"/"+data[x];
							}else{
								imgsrc=storeEdit.imgPath+"/"+data[x]
							}
							li.attr("data-filepath",data[x]).find("img").attr("src",imgsrc);
							var length=data[x].split("/").length;
							li.find(".qq-upload-file").text(data[x].split("/")[length-1]);
							storeEdit.itemPicsBindEvent(li);
						}
					}
				}
			});
		},
		itemPicsBindEvent:function(obj){
			obj.find(".qq-upload-up").on("click",function(){
				if(obj.prev().length){
					obj.insertBefore(obj.prev());
				}
			});
			obj.find(".qq-upload-down").on("click",function(){
				if(obj.next().length){
					obj.insertAfter(obj.next());
				}
			});	
			obj.find(".qq-upload-remove").on("click",function(){
				var filePath=obj.data("filepath");
				var temp=$(this);
				storeEdit.deleteFile(filePath,function(){
					obj.remove();
				});
			});
		}	
	}
	return storeEdit;
})