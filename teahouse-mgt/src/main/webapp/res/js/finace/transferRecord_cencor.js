define([],function() {	
var transferRecordCencor = {
		appPath : getPath("app"),
		init : function() {
			//审核余额提现信息提交
			$("#cencorTransferRecord").click(function(){
				transferRecordCencor.cencorTransferRecord();
			});
			//余额提现审核页面关闭
			$("#closeTransferRecordCencor").click(function(){
				closeTab("提现审核");
			});
			var form = $("form[name=transferRecordCencorForm]");
			form.find("input[name='cencorStatus']").click(function(){
				if(form.find("input[name='cencorStatus']:checked").val() == 1){
					 form.find(".cancel_memo_div").hide(); 
					 form.find("input[name=cancelReason]").html("")
				}else{
					form.find(".cancel_memo_div").show(); 
					form.find("input[name=cancelReason]").html("")
				}
		    });
		},
		cencorTransferRecord:function() {
			var form = $("form[name=transferRecordCencorForm]");
			form.ajaxSubmit({
				url : transferRecordCencor.appPath + "/transferRecord/cencorTransferRecord.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核操作成功", function() {
							closeTab("提现审核");
							$("#transferRecordList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核操作失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					var form = $("form[name=transferRecordCencorForm]");
					if(form.find("textarea[name=cencorMemo]").val()==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入审核备注！");
						return false;
					}
					
					if(form.find("input[name='cencorStatus']:checked").val() == 2){
						if(form.find("textarea[name=cancelReason]").val()==""){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核不通过时，需输入取消原因！");
							return false;
						}
					}
				}
			});
		}
}
return transferRecordCencor
})
