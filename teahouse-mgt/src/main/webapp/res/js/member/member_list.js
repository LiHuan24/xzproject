define(
		[],
		function() {
			var member = {
				appPath : getPath("app"),
				init : function() {
					// 列表页面搜索调用
					$("#memberSearch").click(function() {
						var form = $("form[name=memberSerachForm]");
						var registerTimeStart =  form.find("input[name=registerTimeStart]").val();
						var registerTimeEnd = form.find("input[name=registerTimeEnd]").val();
						if(registerTimeStart!=""&&registerTimeEnd!=""){
							if(new Date(registerTimeStart)>new Date(registerTimeEnd)){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
							}else{
								member.pageList();
							}
						}else{
							member.pageList();
						}
					});
					//移入黑名单提交
					$("#inBlacklistBtn").click(function(){
						member.inBlacklistFormSub();
		            });
					//移入黑名单取消
					$("#inBlacklistCancel").click(function(){
						$("#inBlacklistModel").modal("hide");
		            });
					//移出黑名单提交
					$("#outBlacklistBtn").click(function(){
						member.outBlacklistFormSub();
		            });
					//移出黑名单取消
					$("#outBlacklistCancel").click(function(){
						$("#outBlacklistModel").modal("hide");
		            });
					$("#inBlacklistModel").on("hidden.bs.modal", function() {  
		            	var form = $("form[name=inBlacklistForm]");
		            	form.resetForm();
		            	$("#inBlacklistMemo").text("");
		            });
					$("#outBlacklistModel").on("hidden.bs.modal", function() {  
		            	var form = $("form[name=outBlacklistForm]");
		            	form.resetForm();
		            	$("#outBlacklistMemo").text("");
		            });
					// 列表页面分页方法调用
					member.pageList();

				},
				//移入黑名单操作
				inBlacklistFormSub: function () {
		        	var form = $("form[name=inBlacklistForm]");
					form.ajaxSubmit({
		    			url:member.appPath+"/member/updateMember.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								$("#inBlacklistModel").modal("hide");
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
									$("#memberList").DataTable().ajax.reload(function(){});
								});
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							if (form.find("textarea[name='blacklistMemo']").val()=="") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "理由不能为空！");
								return false;
							}
						}
					});
				},
				//移出黑名单操作
				outBlacklistFormSub: function () {
		        	var form = $("form[name=outBlacklistForm]");
					form.ajaxSubmit({
		    			url:member.appPath+"/member/updateMember.do",
						type : "post",
						dataType:"json", //数据类型  
						success:function(res){
							var msg = res.msg;
							var code = res.code;
							if(code == "1"){ 
								$("#outBlacklistModel").modal("hide");
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作成功", function() {
									$("#memberList").DataTable().ajax.reload(function(){});
								}); 
							}else{
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "操作失败");
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							if (form.find("textarea[name='blacklistMemo']").val()=="") {
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "理由不能为空！");
								return false;
							}
						}
					});
				},
				operateColumEvent : function() {
					$(".memberAccountAmount-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("套餐订单列表",member.appPath+ "/pricingPackOrder/toPricingPackOrderList.do?memberNo="+$(this).data("id"));
						});
					});
					$(".member-operate-view").each(function() {
						$(this).on("click", function() {
							addTab("会员详情",member.appPath+ "/member/toMemberView.do?memberNo="+$(this).data("id"));
						});
					});
					$(".member-operate-edit").each(function() {
						$(this).on("click", function() {
							addTab("会员编辑",member.appPath+ "/member/toUpdateMember.do?memberNo="+$(this).data("id"));
						});
					});
					$(".member-operate-cencor").each(function() {
						$(this).on("click", function() {
							addTab("会员审核",member.appPath+ "/member/toCencorMember.do?memberNo="+$(this).data("id"));
						});
					});
					//移入黑民单弹出层
					$(".member-operate-inBlacklist").each(function(id){
						$(this).on("click",function(){
							var memberNo=$(this).data("id");
							var name = $(this).data("name");
							if(name == null || name == ""){//会员有可能出现空
								name = ""
							}else{
								name ="“"+name+"”"
							}
		            	    $("#inBlacklistModel").modal("show");
							$("#inMemberNo").val(memberNo);
							$("#inBlacklistMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将会员"+name+"移入黑名单吗？黑名单中的会员将不能进行租车操作。");
						});
						
						$(".member-operate-toCoach").each(function() {
							$(this).on("click", function() {
								addTab("会员转为教练",member.appPath+ "/coach/toCoachAdd.do?mobilePhone="+$(this).data("id"));
							});
						}); 
						
						$(".member-operate-viewCoach").each(function() {
							$(this).on("click", function() {
								addTab("查看教练",member.appPath+ "/coach/toCoachView.do?mobilePhone="+$(this).data("id"));
							});
						});
						
						
					});
					//移出黑名单弹出层
					$(".member-operate-outBlacklist").each(function(id){
						$(this).on("click",function(){
							var memberNo=$(this).data("id");
							var memberName = $(this).data("membername");
		            	    $("#outBlacklistModel").modal("show");
							$("#outMemberNo").val(memberNo);
							$("#outBlacklistMemo").html("<img src='res/img/wen.png' style='width: 29px;height: 29px;display:inline-block;margin-top: -4px'/>&nbsp;&nbsp;您确定将会员“"+memberName+"”移出黑名单吗？");
						});
					});
				},
				excel:function(){
					var form = $("form[name=memberSerachForm]");
					window.location.href = member.appPath+"/member/toMemberExport.do"
							+ "?memberName=" + $.trim(form.find("input[name='memberName']").val())
							+ "&mobilePhone=" + $.trim(form.find("input[name='mobilePhone']").val())
							+ "&idCard=" + $.trim(form.find("input[name='idCard']").val())
							+ "&memberLevelId=" + form.find("select[name='memberLevelId']").val();
							/*+ "&isBlacklist=" + form.find("select[name='isBlacklist']").val()
							+ "&refereeId=" + form.find("input[name='refereeId']").val()
							+ "&invitationCode=" + invitationCode
							+ "&registerTimeStart=" + form.find("input[name='registerTimeStart']").val()
							+ "&registerTimeEnd=" + form.find("input[name='registerTimeEnd']").val();*/
				},
				pageList : function() {
					var form = $("form[name=memberSerachForm]");
					var refereeId = form.find("input[name='refereeId']").val();
					var memberName = $.trim(form.find("input[name='memberName']").val());
					var mobilePhone = $.trim(form.find("input[name='mobilePhone']").val());
					var idCard = $.trim(form.find("input[name='idCard']").val());
					var memberType = form.find("select[name='memberType']").val();
					var isBlacklist = form.find("select[name='isBlacklist']").val();
					var registerTimeStart = form.find("input[name='registerTimeStart']").val();
					var registerTimeEnd = form.find("input[name='registerTimeEnd']").val();
					var memberLevelId = form.find("select[name='memberLevelId']").val();
					var invitationCode = form.find("input[name='invitationCode']").val();
					var memberBtnTpl = $("#memberBtnTpl").html();
					
					// 预编译模板
					var template = Handlebars.compile(memberBtnTpl);
					var table = $('#memberList')
							.dataTable(
									{
										searching : false,
										destroy : true,
										"ajax" : {
											"type" : "POST",
											"url" : member.appPath
													+ "/member/pageListMember.do",
											"data" : function(d) {
												return $.extend({},d,
																{"pageNo" : (d.start / d.length) + 1,
																 "pageSize" : d.length,
																 "memberName" :memberName,
																 "mobilePhone" : mobilePhone,
																 "idCard" :idCard,
																 "memberType":memberType,
																 "isBlacklist":isBlacklist,
																 "registerTimeStart":registerTimeStart+" 00:00:00",
																 "registerTimeEnd":registerTimeEnd+" 23:59:59",
																 "memberLevelId":memberLevelId,
																 "invitationCode":invitationCode,
																 "refereeId":refereeId
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
											{ "title":"","data": "memberNo","width":"5px"},
											{"title" : "手机号","data" : "mobilePhone"}, 
											{"title" : "姓名","data" : "memberName","defaultContent":"--"}, 
											{"title" : "性别","data" : "sex"}, 
											{"title" : "注册时间","data" : "registerTime"}, 
											{"title" : "状态","data" : "isBlacklist"},
											{"title" : "押金","data" : "memberDeposit"},
											{"title" : "余额","data" : "memberBalance"},
											{"title" : "积分","data" : "memberPointsValue"},
											{"title" : "会员类型","data" : "usersType"},
											{"title" : "操作","orderable" : false} 
										],
										"dom" : "<'row'<'#membertool.col-xs-6'><'col-xs-6'f>r>"
												+ "t"
												+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
										initComplete : function() {
											$("#membertool").html("");
											$("#membertool").css("float", "right");
											$("#membertool").removeClass("col-xs-6");
											if($("#isAdmin").val() != null && $("#isAdmin").val() == 1){
												$("#membertool").append('<button type="button" class="btn btn-default btn-sm membertool-operate-recharge btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">手工充值</button>');
											}
								       		$("#membertool").append('<button type="button" class="btn btn-default btn-sm membertool-operate-sendCoupon btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">发放优惠券</button>');
								       		$("#membertool").append('<button type="button" class="btn btn-default btn-sm membertool-operate-export btnDefault" style="width: 95px; height: 32px; line-height: 25px; -webkit-border-radius: 3px;-moz-border-radius: 3px;border-radius: 3px; background-color: #2b94fd">导出</button>');
								       		$(".membertool-operate-recharge").on("click",function(){
								       			var ids=[];
												var len=$('#memberList tbody input[type="checkbox"]:checked');
												if(len.length==0){
													bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择会员！")
												}else{
													$("#memberList tbody").find("input:checked").each(function(){
							        					ids.push($(this).val());
							        				});
								       				addTab("手工充值", member.appPath+ "/memberAccountAmount/toMemberRecharge.do?memberNos="+ids);
												}
												
												$('#memberList thead input[type="checkbox"]').on("click",function(e){
							        				if(this.checked){
							        			         $('#memberList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
							        			      } else {
							        			         $('#memberList tbody input[type="checkbox"]:checked').prop("checked",false);
							        			      }
							        			});
												
							       			});
								       		
								       		
								       		$(".membertool-operate-sendCoupon").on("click",function(){
								       			var ids=[];
												var len=$('#memberList tbody input[type="checkbox"]:checked');
												if(len.length==0){
													bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择会员！")
												}else{
													$("#memberList tbody").find("input:checked").each(function(){
														
							        					ids.push($(this).val());
							        					
							        				});
								       				addTab("发放优惠券", member.appPath+ "/coupon/toAddCoupon.do?memberNos="+ids);
												}
												
												$('#memberList thead input[type="checkbox"]').on("click",function(e){
							        				if(this.checked){
							        			         $('#memberList tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
							        			      } else {
							        			         $('#memberList tbody input[type="checkbox"]:checked').prop("checked",false);
							        			      }
							        			});
												
							       			});
												
								       		//导出
											$(".membertool-operate-export").on("click",function(){		   					
						       					bootbox.confirm("<img src='res/img/wen.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "确定要导出吗？", function(result) {
						       						if(result){
						       							member.excel();
						       						}
						       					});	
							       			}); 
										},
										"drawCallback" : function(settings) {
											member.operateColumEvent();
										},
										"order" : [ [ 1, "desc" ] ],
										"columnDefs" : [
											{
												"targets" : [0],
												 "orderable":false,
												"render" : function(data, type, full, meta) {
													  return '<input type="checkbox" name="memberNo" value="' + data + '">';
												}
											   },
												{
													"targets" : [ 3 ],
													"render" : function(a,
															b, c, d) {
														var sex1;
														if(c.sex==1){
															sex1="男";
														}else if(c.sex==0){
															sex1="女";
														}else{
															sex1="未知";
														}
														return sex1;
													}
												},
												/*{
													"targets" : [ 6 ],
													"render" : function(a,
															b, c, d) {
														if(c.memberDeposit != null){
															if(c.memberDeposit > 0){
																return "有";
															}
														}
														return  "无";
													}
												},*/
												{
													"targets" : [ 5 ],
													"render" : function(a,
															b, c, d) {
														var authenticate = "";
														if(a==0){
															authenticate="正常"
														}else if(a==1){
															authenticate="黑名单"
														}
														return authenticate;
													}
												},
												{
													"targets" : [ 4 ],
													"orderable" : false,
													"render" : function(a, b, c, d) {
														if(a!=null){
															var now = moment(a);
															return now.format('YYYY-MM-DD HH:mm:ss');
														}else{
															return "--";
														}
													}
												},
												{
													"targets" : [ 9 ],
													"render" : function(a,
															b, c, d) {
														var typeName;
														if(c.usersType==1){
															typeName="教练";
														}else if(c.usersType==0){
															typeName="会员";
														}else{
															typeName="未知";
														}
														return typeName;
													}
												},
												{
													targets : [ 10 ],
													render : function(a, b, c,d) {
														var authenticate = "";
														var view = '<span class="glyphicon member-operate-view"" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.memberNo
																+ '" >查看</span>';
														if(c.isBlacklist==0){
															authenticate = '<span class="glyphicon member-operate-inBlacklist" data-name="'+c.memberName+'" style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.memberNo
																+ '" >移入黑名单</span>';
														}else if(c.isBlacklist==1){
															authenticate = '<span class="glyphicon member-operate-outBlacklist" data-name="'+c.memberName+'"  style="text-decoration: underline; cursor: pointer;" data-id="'
																+ c.memberNo
																+ '" >移出黑名单</span>';
														}
														var toCoach = "";
														var viewCoach = "";
														if(c.usersType == 0){
															toCoach = '<span class="glyphicon member-operate-toCoach" data-name="'+c.memberName+'"  style="text-decoration: underline; cursor: pointer;" data-id="'
															+ c.mobilePhone
															+ '" >转为教练</span>';
														}else{
															viewCoach = '<span class="glyphicon member-operate-viewCoach" data-name="'+c.memberName+'"  style="text-decoration: underline; cursor: pointer;" data-id="'
															+ c.mobilePhone
															+ '" >查看教练</span>';
														}
														
														return view + authenticate + toCoach + viewCoach;
													}
												}]
									});
				},
			};
			return member;
		});
