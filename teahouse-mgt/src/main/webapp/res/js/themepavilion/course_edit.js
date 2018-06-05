define([],function() {
var courseEdit = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	resImgPath:getPath("resImg"),
	beforePath:getPath("before"),
	init : function() {
		//添加提交
		$("#updateCourse").click(function(){
			courseEdit.editCourse();
		});
		//返回
		$("#closeEditCourse").click(function(){
			closeTab("课程编辑");
			$("#courseList").DataTable().ajax.reload(function(){});
		});
		//上传图片弹出层
		$("#editCoursePhotoUs").click(function(){
			$("#courseEditModalUs").modal("show");
		});
		//新增图片回填
		$("#editCoursePhotoBtnUs").click(function(){
			
			var ls= courseEdit.getUploadFilePath();
			if(ls.length>0){
				var form1=$("form[name=courseEditForm]");
				form1.find("input[name=coursePictureUrl1]").val(ls.toString());
			}
			$("#courseEditModalUs").modal("hide");
			
			
		});
		
		
		var form = $("form[name=courseEditForm]");
		var labelNos = form.find("input[name=labelNo]").val();
		labelNos = labelNos.split(",");
		$.each(labelNos,function(i,item){
			$("input[name='courseLabelNo'][value="+item+"]").attr("checked","checked");
		});
		
		courseEdit.uploadFile();
		var courseNo = form.find("input[name=courseNo]").val();
		courseEdit.getItemPics(courseNo);
	},
	//编辑保存
	editCourse:function(){
		var form = $("form[name=courseEditForm]");
		$("span[name='courseSortNoEdit']").empty();
		$("span[name='chineseNameEdit']").empty();
		$("span[name='englishNameEdit']").empty();
		$("span[name='courseLabelNoEdit']").empty();
		$("span[name='priceEdit']").empty();

		var ywReg = /^[A-Za-z]+$/;
		var zwReg = /[^\u4e00-\u9fa5]/;
		var priceReg = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
		
		if (form.find("select[name='courseSortNo']").val() == "") {
			$("span[name='courseSortNoEdit']").append("<font color='red'>请选择分类！</font>");
			return false;
		}
		if (form.find("input[name='chineseName']").val() == "") {
			$("span[name='chineseNameEdit']").append("<font color='red'>请输入中文课程名称！</font>");
			return false;
		}
		if(zwReg.test(form.find("input[name='chineseName']").val())){
			$("span[name='chineseNameEdit']").append("<font color='red'>输入中文课程名称格式不正确！</font>");
			return false;
		}
		if (form.find("input[name='englishName']").val() == "") {
			$("span[name='englishNameEdit']").append("<font color='red'>请输入英文课程名称！</font>");
			return false;
		}
		if(!ywReg.test(form.find("input[name='englishName']").val())){
			$("span[name='englishNameEdit']").append("<font color='red'>输入英文课程名称格式不正确！</font>");
			return false;
		}
		if (form.find("select[name='courseLabelNo']").val() == "") {
			$("span[name='courseLabelNoEdit']").append("<font color='red'>请选择标签！</font>");
			return false;
		}
		if (form.find("input[name='price']").val() == "") {
			$("span[name='priceEdit']").append("<font color='red'>请输入价格！</font>");
			return false;
		}
		if(!priceReg.test(form.find("input[name='price']").val())){
			$("span[name='priceEdit']").append("<font color='red'>输入价格格式不对（格式应为正整数或小数且小数保留两位小数点）！</font>");
			return false;
		}
		var synopsis = form.find("textarea[name='synopsis']").val();
		var effect = form.find("textarea[name='effect']").val();
		var suit = form.find("textarea[name='suit']").val();
		var beCareful = form.find("textarea[name='beCareful']").val();
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
		
		var courseNo = form.find("input[name='courseNo']").val();
		
		$.post("course/updateCheckCourseUnique.do",{courseNo:courseNo,courseName:form.find("input[name='chineseName']").val()},function(res){
			if(res.code==1){
				$("span[name='chineseNameEdit']").append("<font color='red'>课程名称重复，请重新输入！</font>");
				return false;
			}else{
				form.ajaxSubmit({
					url : courseEdit.appPath + "/course/saveOrUpdateCourse.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"编辑成功！", function() {
								closeTab("课程编辑");
								$("#courseList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败");
						}
					},
				});
			}
		});
	},
	
	 //执行图片上传
	  uploadFile:function(){
			var relativePath="course_photo";
			var resImgPath=courseEdit.resImgPath;
			var imgPath=courseEdit.imgPath;
			var storePath=resImgPath+"/" + relativePath;
			$("#appUpload").load("res/tpl/uploadFile.html",function(){
		        var manualUploader = new qq.FineUploader({
		            element: document.getElementById("courseEditFineUploaderUs"),
		            template: "qq-template-manual-trigger-m",
		            request: {
		                endpoint: courseEdit.appPath+"/upload/uploadFileNew.do",
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
		                	var li=$("#courseEditFineUploaderUs .qq-uploader .qq-upload-list").find("li");
                     	if(li != null && li.length < 4){
     						li=$("#courseEditFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
     						storeEdit.itemPicsBindEvent(li);
	                        li.find(".qq-upload-remove").hide();
     					}else{
	                        $("#courseEditFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]")[0].remove();
	                       // bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "上传图片不能超过3张！");
	                        $("span[name='courseEditWs']").empty();
	                        $("span[name='courseEditWs']").append("<font color='red' class='formtips onError emaill'>上传图片不能超过3张，多出图片已被自动删除。</font>");
     					}
		                },
		                onComplete: function(id, fileName, responseJSON) {
                     	var form = $("form[name='coursePhotoFormUs']");
                     	var form1=$("form[name=courseEditForm]");
		                	if(form.find("input[name='coursePictureUrl1']").val() != ""){
		                		var path = form.find("input[name='coursePictureUrl1']").val() + "," + responseJSON.data[0]
		                    	form.find("input[name='coursePictureUrl1']").val(path);                        	
		                		form1.find("input[name=coursePictureUrl1]").val(path);
		                	}else{
		                		form.find("input[name='coursePictureUrl1']").val(responseJSON.data[0]);
		                		form1.find("input[name=coursePictureUrl1]").val(responseJSON.data[0]);

		                	}
		                	var li=$("#courseEditFineUploaderUs .qq-uploader .qq-upload-list").find("li[qq-file-id="+id+"]");
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
		        $("#courseEditFineUploaderUs .trigger-upload").on("click", function() {
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
				resPath=courseEdit.resImgPath
			}
			$.ajax({
				url: courseEdit.appPath+"/upload/deleteFile.do?filePaths="+filePaths,
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
                 			var form1=$("form[name=courseEditForm]");
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
			$("#courseEditFineUploaderUs .qq-upload-success").each(function(){
				filePath.push($(this).data("filepath"));                		
			});
			return filePath;
		},
		getItemPics:function(courseNo){
			var uploadTpl = $("#upload-item-template").html();
			$.ajax({
				url: courseEdit.appPath+"/course/getCoursePitureUrl.do?courseNo="+courseNo, 
				success: function(data){
					if(data){
						for(x in data){
							var to=$("#courseEditFineUploaderUs .qq-uploader .qq-upload-list");
							var li=$(uploadTpl).appendTo(to);
							var regexp = /res\/img/g;
							var imgsrc="";
							if(data[x].match(regexp)){
								imgsrc=courseEdit.appPath+"/"+data[x];
							}else{
								imgsrc=courseEdit.imgPath+"/"+data[x]
							}
							li.attr("data-filepath",data[x]).find("img").attr("src",imgsrc);
							var length=data[x].split("/").length;
							li.find(".qq-upload-file").text(data[x].split("/")[length-1]);
							courseEdit.itemPicsBindEvent(li);
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
				courseEdit.deleteFile(filePath,function(){
					obj.remove();
				});
			});
		}	
	}
	return courseEdit;
})