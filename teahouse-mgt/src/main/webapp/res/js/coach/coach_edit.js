define([],function() {
var coachEdit = {
	appPath : getPath("app"),
	imgPath:getPath("img"),
	init : function() {
		//添加提交
		$("#updateCoach").click(function(){
			coachEdit.addCoach();
		});
		//返回
		$("#closeEditCoach").click(function(){
			closeTab("教练编辑");
			$("#coachList").DataTable().ajax.reload(function(){});
		});
		//上传图片弹出层
		$("#editCoachPhotoUs").click(function(){
			$("#coachEditModalUs").modal("show");
		});
		//新增图片回填
		$("#editCoachPhotoBtnUs").click(function(){
			var form=$("form[name=coachPhotoFormUs]");
			var img=form.find("input[name=memberPhotoUrl]").val();
			if(img!=""){
				var form1=$("form[name=coachEditForm]");
				form1.find("input[name=memberPhotoUrl]").val(img);
				form1.find("img[name=coachPicUrlImg]").attr("src",coachEdit.imgPath+"/"+img);
				$("#coachEditModalUs").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
			}
		});
	},
	addCoach:function() {
		var form = $("form[name=coachEditForm]");
		form.ajaxSubmit({
			url : coachEdit.appPath + "/coach/saveOrUpdateCoach.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;"+"编辑成功!", function() {
						closeTab("教练编辑");
						$("#coachList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败!");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='coachNameEdit']").empty();
				$("span[name='coachNickEdit']").empty();
				$("span[name='mobilePhoneEdit']").empty();
				$("span[name='idCardEdit']").empty();
				
				var phoneReg = /^[1][3,4,5,7,8][0-9]{9}$/;
				var idCardReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
				
				if ($.trim(form.find("input[name='coachName']").val()) == "") {
					$("span[name='coachNameEdit']").append("<font color='red'>请输入教练名称！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='coachNick']").val()) == "") {
					$("span[name='coachNickEdit']").append("<font color='red'>请输入教练昵称！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='mobilePhone']").val()) == "") {
					$("span[name='mobilePhoneEdit']").append("<font color='red'>请输入手机号！</font>");
					return false;
				}
				if(!phoneReg.test($.trim(form.find("input[name='mobilePhone']").val()))){
					$("span[name='mobilePhoneEdit']").append("<font color='red'>输入手机号格式不对（正确格式为11位）！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='idCard']").val()) == "") {
					$("span[name='idCardEdit']").append("<font color='red'>请输入身份证！</font>");
					return false;
				}
				if (idCardReg.test($.trim(form.find("input[name='idCard']").val())) == false) {
					$("span[name='idCardEdit']").append("<font color='red'>输入身份证格式不对（正确格式应为15位或者18位）！</font>");
					return false;
				}
				var synopsis = $.trim(form.find("textarea[name='synopsis']").val());
				if(synopsis.length > 200){
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "输入教练简介字符不能大于200!");
					return false;
				}
			
			}
		});
	 }
	}
	return coachEdit;
})