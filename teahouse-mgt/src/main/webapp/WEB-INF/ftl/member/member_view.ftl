<meta charset="utf-8">
<body>
<style>
    .key {
        font-size: 1.5rem;
        font-weight: 500;
        color: #414550;
        line-height: 15px;
    }

    .val {
        font-size: 1.5rem;
        font-weight: 500;
        color: #a0a7af;
        line-height: 15px;
    }
    .con{
    margin-top:20px;
    }
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-5 control-label"><h4>会员信息详情</h4></label>
						</div>
					</div>
				</div>
				<div class="row hzlist">
						<div class="form-group col-md-12">
							<label class="col-sm-2 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;头像：</label>
							<div class="col-sm-10 val">
							    <img src="${imagePath!''}/${member.memberPhotoUrl}" width="100" height="80"/>
							</div>
						</div>
					<div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-5 control-label"><h4>基本详情信息</h4></label>
						</div>
					</div>
				</div>
						<div class="col-md-12" style="border:1px solid lightgrey;margin:20px 0;">
						<div class="col-md-12 con"></div>
						<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;会员昵称：</label>
							<div class="col-sm-7">
								<label class="control-label val">${member.memberNick}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;会员姓名：</label>
							<#if member.memberName !=null>
               					<label class="control-label val">${member.memberName}</label>
							<#else>
								<label class="control-label val">&nbsp;&nbsp;暂无</label>
							</#if>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;手机号：</label>
							<div class="col-sm-7">
								<label class="control-label val">${member.mobilePhone}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;邀请码：</label>
							<div class="col-sm-7">
								<label class="control-label val">${member.invitationCode}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
	                			<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;注册邀请人：</label>
	                			<div class="col-sm-7">
	                				<#if memberReferee !=null>
										<label class="control-label val"> ${memberReferee}</label>
									<#else>
										<label class="control-label val">暂无</label>
									</#if>
	                			</div>
                		</div>
    					<div class="form-group col-md-6">
                			<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;邀请人数：</label>
                			<div class="col-sm-6">
                				<label class="control-label val">
                					<input type="hidden" id="memberNo" value="${member.memberNo}"/>
                					<input type="button" id="mbs"  value="${mbs}"/>
            					</label>
                			</div>
                		</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;性别：</label>
							<div class="col-sm-7">
							<label class="control-label val">
								<#if member.sex==0>
								女
								<#elseif member.sex==1>
								男
								<#else>
								未知
								</#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;身份证号：</label>
							<div class="col-sm-7">
								<#if member.idCard !=null>
								<label class="control-label val">${member.idCard}</label>
								<#else>
									<label class="control-label val">暂无</label>
								</#if>
							</div>
						</div>
						<div class="form-group col-md-6">
                			<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;会员余额：</label>
                			<div class="col-sm-7">
                				<label class="control-label val">${balance}元</label>
                			</div>
                		</div>
                		<div class="form-group col-md-6">
                			<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;押金：</label>
                			<div class="col-sm-7">
                				<label class="control-label val">${deposit}元</label>
                			</div>
                		</div>
						<div class="form-group col-md-6">
                			<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;会员等级：</label>
                			<div class="col-sm-7">
                			<label class="control-label val">
                				${levelName}
                			</label>
                			</div>
                		</div>
                		<div class="form-group col-md-6">
                			<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;会员积分：</label>
                			<div class="col-sm-7">
                				<#if member.memberPointsValue !=null>
									<label class="control-label val">${member.memberPointsValue}分</label>
								<#else>
									<label class="control-label val">0分</label>
								</#if>
                			</div>
                		</div>
                		<div class="form-group col-md-6">
                			<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;会员芝麻分：</label>
                			<div class="col-sm-7">
								<label class="control-label val">${zhimaScore}分</label>
                			</div>
                		</div>
                		<div class="form-group col-md-6">
                			<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;信用等级：</label>
                			<div class="col-sm-7">
                				<#if member.memberCreditLevel !=null>
									<label class="control-label val"> ${member.memberCreditLevel}</label>
								<#else>
									<label class="control-label val">暂无</label>
								</#if>
                			</div>
                		</div>
                		<div class="form-group col-md-6">
                			<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;状态：</label>
                			<div class="col-sm-7">
                			<label class="control-label val">
                				<#if member.isBlacklist==0>
								正常
								<#elseif member.isBlacklist==1>
                				黑名单
                				</#if>
                			</label>
                			</div>
                		</div>
                		<div class="form-group col-md-6">
                			<label class="col-sm-5 control-label key"><#-- <span>*</span> -->&nbsp;&nbsp;注册时间：</label>
                			<div class="col-sm-7">
                			<label class="control-label val">
                				<#if member.registerTime?exists>
                					${member.registerTime?string('yyyy-MM-dd HH:mm:ss')}
                				</#if>
                			</label>
                			</div>
                		</div>
					</div>
        		</div><!-- /.row -->
    <div class="form-group">
        <div class="col-sm-7">
            <button id="closeMember" class="btn btn-default pull-right navbar-btn btn-flat member-operate-save btnDefault" style="width: 95px; height: 32px; line-height: 25px; padding-left: 15px; background: #2b94fd">
                    关闭
            </button>
        </div>
    </div>
 					
</div>
<script type="text/javascript">
$(function(){
	  require.config({paths: {"memberEdit":"res/js/member/member_edit"}});
		require(["memberEdit"], function (memberEdit){
			memberEdit.init();
		});  
	});
</script>
</body>
