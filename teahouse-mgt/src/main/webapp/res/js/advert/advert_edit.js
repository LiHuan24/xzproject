define([],function() {
	var advertEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//编辑提交
			$("#editAdvert").click(function(){
				advertEdit.editAdvert();
			});
			//编辑页面的关闭
			$("#closeEditAdvert").click(function(){
				closeTab("文章编辑");
			});
			//上传图片
			$("#editAdvertPicUrlButton").click(function(){
				$("#advertPicUrlEditModal").modal("show");
			});
			var form = $("form[name=advertEditFrom]");
			form.find("select[name='advertType']").change(function(){
				if(form.find("select[name='advertType']").val() == 1){
					 form.find(".synopsisNso").show(); 
				}else {
					 form.find(".synopsisNso").hide(); 
					
					 form.find("textarea[name=synopsis]").val("");
				}
		    });
			//编辑图片回填
			$("#editAdvertPicBtn").click(function(){
				var form=$("form[name=advertPicUrlEditForm]");
				var img=form.find("input[name=advertPicUrl1]").val();
				if(img!=""){
					var form1=$("form[name=advertEditFrom]");
					form1.find("input[name=advertPicUrl]").val(img);
					form1.find("img[name=advertPicUrlImg]").attr("src",advertEdit.imgPath+"/"+img);
					$("#advertPicUrlEditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
		},
		editAdvert:function() {
			var form = $("form[name=advertEditFrom]");
			
			form.ajaxSubmit({
				url : advertEdit.appPath + "/advert/updateAdvert.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "文章编辑成功", function() {
							closeTab("文章编辑");
							$("#advertList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "文章编辑失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='advertTypeEdit']").empty();
					$("span[name='advertNameEdit']").empty();
					$("span[name='rankingEdit']").empty();
					$("span[name='advertPicUrlEdit']").empty();
					$("span[name='linkUrl']").empty();
					$("span[name='text1Edit']").empty();
					$("span[name='text2Edit']").empty();
					$("span[name='text3Edit']").empty();
					$("span[name='isAvailableEdit']").empty();
					$("span[name='synopsisEdit']").empty();
					if (form.find("select[name='advertType']").val()=="") {
						$("span[name='advertTypeEdit']").append("<font color='red'>请选择文章类型！</font>");
						return false;
					}
					if (form.find("input[name='advertName']").val()=="") {
						$("span[name='advertNameEdit']").append("<font color='red'>请输入文章名称！</font>");
						return false;
					}
					if(form.find("select[name='advertType']").val()==1){
						var sy = form.find("textarea[name='synopsis']").val();
						if (sy=="") {
							$("span[name='synopsisEdit']").append("<font color='red'>请输入文章简介！</font>");
							return false;
						}else if(sy.length>40){
							$("span[name='synopsisEdit']").append("<font color='red'>文章简介不能大于40个文字！</font>");
							return false;
						}	
					}
//					if (form.find("input[name='ranking']").val()=="") {
//						$("span[name='rankingEdit']").append("<font color='red'>请输入排序！</font>");
//						return false;
//					}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='ranking']").val())){
//		            	$("span[name='rankingEdit']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
//						return false;
//		            }
					if (form.find("input[name='advertPicUrl']").val()=="") {
						$("span[name='advertPicUrlEdit']").append("<font color='red'>请上传文章图片！</font>");
						return false;
					}
		//			if (form.find("input[name='linkUrl']").val()=="") {
		//				$("span[name='linkUrlEdit']").append("<font color='red'>请输入外部链接！</font>");
		//				return false;
		//			}
					var text1= form.find("textarea[name='text1']").val();
					var text=text1.replace(/<[^>]+>/g,"");
					if (text == "") {
						$("span[name='text1Edit']").append("<font color='red'>请输入文章内容！</font>");
						return false;
					}
//					if (form.find("textarea[name='text2']").val()=="") {
//						$("span[name='text2Edit']").append("<font color='red'>请输入文章内容2！</font>");
//						return false;
//					}
//					if (form.find("textarea[name='text3']").val()=="") {
//						$("span[name='text3Edit']").append("<font color='red'>请输入文章内容3！</font>");
//						return false;
//					}
//					if (form.find("select[name='isAvailable']").val()=="") {
//						$("span[name='isAvailableEdit']").append("<font color='red'>请选择可用状态！</font>");
//						return false;
//					}
				}
			});
		}
	}
	return advertEdit;
})
