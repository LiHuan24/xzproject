define([],function() {
var storeAdd = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	resImgPath:getPath("resImg"),
	beforePath:getPath("before"),
	init : function() {
		//添加提交
		$("#saveStoreAdd").click(function(){
			storeAdd.addStore();
		});
		//返回
		$("#closeStoreAdd").click(function(){
			closeTab("新增门店");
			$("#storeList").DataTable().ajax.reload(function(){});
		});
		$("#addStorePhotoUs").click(function(){
			$("#storeAddModalUs").modal("show");
		});
		//新增图片回填
		$("#addStorePhotoBtnUs").click(function(){
			
			var ls= storeAdd.getUploadFilePath();
			if(ls.length>0){
				var form1=$("form[name=stroeAddForm]");
				form1.find("input[name=storePictureUrl1]").val(ls.toString());
			}
			$("#storeAddModalUs").modal("hide");
			
		});
		
		
        //高德地图点击地图获取地址
        $("#mapLocationGdAdd").click(function(){
        	$("#lctNsAdd").empty();
        	$("input[name=addrStreet]").empty();
        	$("#mapLocation").modal("show");
        	var form = $("form[name=stroeAddForm]");
    		var mlon = form.find("input[name='longitude']").val();
            var mlat = form.find("input[name='latitude']").val();
        	$("#mapLctNsAdd").css({
				"width":"800px",
				"height":"500px",
				"overflow":"hidden",
				"margin":"0px"
			});
        	setTimeout(function(){
        		var map = new AMap.Map("mapLctNsAdd", {
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
	                             $("#lctNsAdd").html(address);
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
	                             $("#lctNsAdd").html(address);
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
        
        storeAdd.uploadFile();
	},
	addStore:function() {
		var form = $("form[name=stroeAddForm]");
		
		$("span[name='storeNameAdd']").empty();
		$("span[name='cityAdd']").empty();
		$("span[name='storeTypeAdd']").empty();
		$("span[name='latlongAdd']").empty();
		$("span[name='synopsisAdd']").empty();
		$("span[name='memoAdd']").empty();
		
		if (form.find("input[name='storeName']").val()=="") {
			$("span[name='storeNameAdd']").append("<font color='red' class='formtips onError emaill'>请输入门店名称！</font>");
			return false;
		}
		if (form.find("select[name='cityId']").val()=="") {
			$("span[name='cityAdd']").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
			return false;
		}
		if (form.find("select[name='storeType']").val()=="") {
			$("span[name='storeTypeAdd']").append("<font color='red' class='formtips onError emaill'>请选择门店类型！</font>");
			return false;
		}
		if (form.find("input[name='longitude']").val()=="" && form.find("input[name='latitude']").val()=="") {
			$("span[name='latlongAdd']").append("<font color='red' class='formtips onError emaill'>请先选好门店地图定位！</font>");
			return false;
		}
		
		var synopsis = form.find("textarea[name='synopsis']").val();
		var memo = form.find("textarea[name='memo']").val();
		if(synopsis.length > 200){
			$("span[name='synopsisAdd']").append("<font color='red' class='formtips onError emaill'>门店介绍输入字符不能大于200个字符！</font>");
			return false;
		}
		if(memo.length > 200){
			$("span[name='memoAdd']").append("<font color='red' class='formtips onError emaill'>门店备注输入字符不能大于200个字符！</font>");
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
		 //门禁编码
		 var entranceCode = $.trim(form.find("input[name='entranceCode']").val());
		 if(entranceCode == ""){
			 $("span[name='entranceCodeAdd']").append("<font color='red' class='formtips onError emaill'>请输入门禁编码！</font>");
			 return false;  
		 }
		 
		 $.post("store/checkAddEntranceCode.do",{entranceCode:entranceCode},function(res){
			 if(res.code == '1'){
				 $("span[name='entranceCodeAdd']").append("<font color='red'>门禁编码重复，请重新输入！</font>");
				 return false;
			 }else{
				form.ajaxSubmit({
					url : storeAdd.appPath + "/store/saveOrUpdateStore.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
								closeTab("新增门店");
								$("#storeList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
						}
					},
				});
			 }
		 });
	 },
	 
	 //执行上传，多张图片
	 uploadFile:function(){
			var relativePath="store_photo";
			var resImgPath=storeAdd.resImgPath;
			console.info(resImgPath);
			var imgPath=storeAdd.imgPath;
			console.info(imgPath);
			var storePath=resImgPath+"/" + relativePath;
			console.info(storePath);
			$("#appUpload").load("res/tpl/uploadFile.html",function(){
		        var manualUploader = new qq.FineUploader({
		            element: document.getElementById("storeFineUploaderUs"),
		            template: "qq-template-manual-trigger-m",
		            request: {
		                endpoint: storeAdd.appPath +"/upload/uploadFileNew.do",
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
		                	var li=$("#storeFineUploaderUs .qq-uploader .qq-upload-list").find("li");
                     	if(li != null && li.length < 4){
     						li=$("#storeFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
     						storeAdd.itemPicsBindEvent(li);
	                        li.find(".qq-upload-remove").hide();
     					}else{
	                        $("#storeFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]")[0].remove();
     						//bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "上传图片不能超过3张！");
	                        $("span[name='storeAddWs']").empty();
	                        $("span[name='storeAddWs']").append("<font color='red' class='formtips onError emaill'>上传图片不能超过3张，多出图片已被自动删除。</font>");
     					}
		                },
		                onComplete: function(id, fileName, responseJSON) {
		                	console.info(responseJSON.data[0]);
                     	var form = $("form[name='storePhotoFormUs']");
                     	var form1=$("form[name=stroeAddForm]");
		                	if(form.find("input[name='storePictureUrl1']").val() != ""){
		                		var path = form.find("input[name='storePictureUrl1']").val() + "," + responseJSON.data[0];
		                    	form.find("input[name='storePictureUrl1']").val(path);                        	
		                		form1.find("input[name=storePictureUrl1]").val(path);
		                	}else{
		                		form.find("input[name='storePictureUrl1']").val(responseJSON.data[0]);
		                		form1.find("input[name=storePictureUrl1]").val(responseJSON.data[0]);
		                	}
		                	var li=$("#storeFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
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
		        $("#storeFineUploaderUs .trigger-upload").on("click", function() {
		            manualUploader.uploadStoredFiles();
		        }); 
		    });	        	
		},
		deleteFile:function(filePaths,fn){
			var resPath="";
			var regexp = /res\/img/g;	    		
			if(filePaths.match(regexp)){
				resPath="";
			}else{
				resPath=storeAdd.resImgPath;
			}
			$.ajax({
				url: storeAdd.appPath+"/upload/deleteFile.do?filePaths="+filePaths,
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
                 			var form1=$("form[name=storeAddForm]");
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
			var filePath=[];	        	
			$("#storeFineUploaderUs .qq-upload-success").each(function(){
				filePath.push($(this).data("filepath"));                		
			});
			return filePath;
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
				storeAdd.deleteFile(filePath,function(){
					obj.remove();
				});
			});
		}
	}
	return storeAdd;
})