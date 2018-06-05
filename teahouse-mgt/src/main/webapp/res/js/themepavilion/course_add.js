define([],function() {
var courseAdd = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	resImgPath:getPath("resImg"),
	beforePath:getPath("before"),
	init : function() {
		//添加提交
		$("#saveCourse").click(function(){
			courseAdd.addCourse();
		});
		//返回
		$("#closeAddCourse").click(function(){
			closeTab("新增课程");
			$("#courseList").DataTable().ajax.reload(function(){});
		});
		//上传图片弹出层
		$("#addCoursePhotoUs").click(function(){
			$("#courseAddModalUs").modal("show");
		});
		//新增图片回填
		$("#addCoursePhotoBtnUs").click(function(){
			
			var ls= courseAdd.getUploadFilePath();
			if(ls.length>0){
				var form1=$("form[name=courseAddForm]");
				form1.find("input[name=coursePictureUrl1]").val(ls.toString());
			}
			$("#courseAddModalUs").modal("hide");
			
			
		});
		
		courseAdd.uploadFile();
	},
	
	//新增
	addCourse:function(){
		var form = $("form[name=courseAddForm]");
		$("span[name='courseSortNoAdd']").empty();
		$("span[name='chineseNameAdd']").empty();
		$("span[name='englishNameAdd']").empty();
		$("span[name='courseLabelNoAdd']").empty();
		$("span[name='priceAdd']").empty();

		var ywReg = /^[A-Za-z]+$/;
		var zwReg = /^[\u4E00-\u9FA5]{1,}$/;  
		var priceReg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
		
		var synopsis = form.find("textarea[name='synopsis']").val();
		var effect = form.find("textarea[name='effect']").val();
		var suit = form.find("textarea[name='suit']").val();
		var beCareful = form.find("textarea[name='beCareful']").val();
		
		if (form.find("select[name='courseSortNo']").val() == "") {
			$("span[name='courseSortNoAdd']").append("<font color='red'>请选择分类！</font>");
			return false;
		}
		if (form.find("input[name='chineseName']").val() == "") {
			$("span[name='chineseNameAdd']").append("<font color='red'>请输入中文课程名称！</font>");
			return false;
		}
		if(!zwReg.test(form.find("input[name='chineseName']").val())){
			$("span[name='chineseNameAdd']").append("<font color='red'>输入中文课程名称格式不正确！</font>");
			return false;
		}
		if (form.find("input[name='englishName']").val() == "") {
			$("span[name='englishNameAdd']").append("<font color='red'>请输入英文课程名称！</font>");
			return false;
		}
		if(!ywReg.test(form.find("input[name='englishName']").val())){
			$("span[name='englishNameAdd']").append("<font color='red'>输入英文课程名称格式不正确！</font>");
			return false;
		}
		if (form.find("select[name='courseLabelNo']").val() == "") {
			$("span[name='courseLabelNoAdd']").append("<font color='red'>请选择标签！</font>");
			return false;
		}
		if (form.find("input[name='price']").val() == "") {
			$("span[name='priceAdd']").append("<font color='red'>请输入价格！</font>");
			return false;
		}
		if(!priceReg.test(form.find("input[name='price']").val())){
			$("span[name='priceAdd']").append("<font color='red'>输入价格格式不对（格式应为正整数或小数且小数保留两位小数点）！</font>");
			return false;
		}
		if(synopsis.length > 200){
			bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "输入课程简介字符不能大于200！");
			return false;
		}
		if(effect.length > 200){
			bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "输入训练效果字符不能大于200！");
			return false;
		}
		if(effect.length > 200){
			bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "输入适合人群字符不能大于200！");
			return false;
		}
		if(beCareful.length > 200){
			bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "输入注意事项字符不能大于200！");
			return false;
		}
		
		$.post("course/addCheckCourseUnique.do",{courseName:form.find("input[name='chineseName']").val()},function(res){
			if(res.code==1){
				$("span[name='chineseNameAdd']").append("<font color='red'>课程名称重复，请重新输入！</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : courseAdd.appPath + "/course/saveOrUpdateCourse.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"添加成功", function() {
								closeTab("新增课程");
								$("#courseList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败");
						}
					},
				});
			}
		});
	},
	 
	//执行上传，多张图片
	 uploadFile:function(){
		 debugger
			var relativePath="course_photo";
			var resImgPath=courseAdd.resImgPath;
			var imgPath=courseAdd.imgPath;
			var storePath=resImgPath+"/" + relativePath;
			$("#appUpload").load("res/tpl/uploadFile.html",function(){
		        var manualUploader = new qq.FineUploader({
		            element: document.getElementById("courseFineUploaderUs"),
		            template: "qq-template-manual-trigger-m",
		            request: {
		                endpoint: courseAdd.appPath +"/upload/uploadFileNew.do",
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
		                	var li=$("#courseFineUploaderUs .qq-uploader .qq-upload-list").find("li");
                     	if(li != null && li.length < 4){
     						li=$("#courseFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
     						courseAdd.itemPicsBindEvent(li);
	                        li.find(".qq-upload-remove").hide();
     					}else{
	                        $("#courseFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]")[0].remove();
     						//bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "上传图片不能超过3张！");
	                        $("span[name='courseAddWs']").empty();
	                        $("span[name='courseAddWs']").append("<font color='red' class='formtips onError emaill'>上传图片不能超过3张，多出图片已被自动删除。</font>");
     					}
		                },
		                onComplete: function(id, fileName, responseJSON) {
	                     	var form = $("form[name='coursePhotoFormUs']");
	                     	var form1=$("form[name=courseAddForm]");
		                	if(form.find("input[name='coursePictureUrl1']").val() != ""){
		                		var path = form.find("input[name='coursePictureUrl1']").val() + "," + responseJSON.data[0];
		                    	form.find("input[name='coursePictureUrl1']").val(path);                        	
		                		form1.find("input[name=coursePictureUrl1]").val(path);
		                	}else{
		                		form.find("input[name='coursePictureUrl1']").val(responseJSON.data[0]);
		                		form1.find("input[name=coursePictureUrl1]").val(responseJSON.data[0]);
		                	}
		                	var li=$("#courseFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
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
		        $("#courseFineUploaderUs .trigger-upload").on("click", function() {
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
				resPath=courseAdd.resImgPath;
			}
			$.ajax({
				url: courseAdd.appPath+"/upload/deleteFile.do?filePaths="+filePaths,
				data:{resPath:resPath},
				success: function(data){
					if(data.code=1){
					var form = $("form[name='coursePhotoFormUs']");
                 	var input = form.find("input[name='coursePictureUrl1']");
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
                 			var form1=$("form[name=courseAddForm]");
         					form1.find("input[name=coursePictureUrl1]").val(newUrls);
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
			$("#courseFineUploaderUs .qq-upload-success").each(function(){
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
				courseAdd.deleteFile(filePath,function(){
					obj.remove();
				});
			});
		}
	}
	return courseAdd;
})