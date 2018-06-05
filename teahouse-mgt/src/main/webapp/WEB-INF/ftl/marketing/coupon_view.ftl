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
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-4 control-label"><h4>优惠券详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
	 				<form name="couponViewForm" class="form-horizontal">
	 					<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;优惠券编号：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${coupon.couponNo}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;方案标题：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${coupon.title}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;会员编号：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${coupon.memberNo}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;会员名称：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${coupon.memberName}</label>
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;优惠类型：</label>
							<div class="col-sm-6">
							   <label class="control-label val">
							   <#if coupon.couponType==1>
							 	  优惠券
							   </#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;优惠方式：</label>
							<div class="col-sm-6">
							   <label class="control-label val">
							   <#if coupon.couponMethod==1>
							 	  折扣
							   </#if>
							   <#if coupon.couponMethod==2>
							 	  直减
							   </#if>
							   <#if coupon.couponMethod=3>
							 	  健身体验券
							   </#if>
							   <#if coupon.couponMethod=4>
							 	 课程体验券
							   </#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;
							<#if coupon.couponMethod==1>
							 	折扣率
							</#if>
							<#if coupon.couponMethod==2>
							 	直减金额
							</#if>
							<#if coupon.couponMethod==3>
							 	健身体验次数
							</#if>
							<#if coupon.couponMethod==4>
							 	课程体验节数
							</#if>
							</label>
							<div class="col-sm-6">
							   <label class="control-label val">
							   <#if coupon.couponMethod==1>
							   		${coupon.discount}
								</#if>
								<#if coupon.couponMethod==2>
								 	 ${coupon.discountAmount}
								</#if>
								<#if coupon.couponMethod==3>
								 	 ${coupon.freeFitnessTime} 次
								</#if>
								<#if coupon.couponMethod==4>
								 	 ${coupon.freeCourseNumber} 节
								</#if>
							   </label>
							</div>
						</div>
						<#if coupon.vailableTime1??>
                        <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;有效日期：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${coupon.vailableTime1?string('yyyy-MM-dd ')}  至   ${coupon.vailableTime2?string('yyyy-MM-dd ')}</label>
							</div>
						</div>
						</#if>
						<#if coupon.availableDays??>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;有效天数：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${coupon.availableDays}</label>
							</div>
						</div>
						</#if>
						<div class="form-group col-md-6">
                            <label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;发放时间：</label>
                            <div class="col-sm-6">
                                <label class="control-label val">
									  <#if coupon.issueMethod==1>
									 	  系统注册
									   </#if>
									   <#if coupon.issueMethod==2>
									 	  活动奖励
									   </#if>
									    <#if coupon.issueMethod==3>
									 	 手动发放
									   </#if>
								</label>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                         	<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;发放渠道：</label>
                            <div class="col-sm-6">
                               	<label class="control-label val">
									 <#if coupon.issueChannel==1>
									 	  官网
									</#if>
                               	</label>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                         	<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;发放方式：</label>
                            <div class="col-sm-6">
                            	<label class="control-label val">${coupon.issueTime?string('yyyy-MM-dd ')}</label>
                            </div>
                        </div>
						<div class="form-group col-md-6">
                            <label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;启用状态：</label>
                            <div class="col-sm-6">
                                <label class="control-label val"><#if coupon.isAvailable==1>启用<#elseif coupon.isAvailable==0>停用</#if></label>
                            </div>
                        </div>
                        <div class="form-group col-md-6">
                            <label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;<#if coupon.isAvailable==1>启用日期<#elseif coupon.isAvailable==0>停用时间</#if>
							</label>
                            <div class="col-sm-6">
                               <label class="control-label val">${coupon.availableUpdateTime?string('yyyy-MM-dd ')}</label>
                            </div>
                        </div>
						<div class="form-group col-md-6">
                            <label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;使用状态：</label>
                            <div class="col-sm-6">
                                <label class="control-label val">
									  <#if coupon.usedStatus==0>
									 	  未使用
									   </#if>
									   <#if coupon.usedStatus==1>
									 	  已使用
									   </#if>
								</label>
                            </div>
                        </div>
                       <#if coupon.usedStatus==1>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;使用时间：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${coupon.usedTime?string('yyyy-MM-dd')}</label>
							</div>
							
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;订单类型：</label>
							<div class="col-sm-6">
							   <label class="control-label val">
							   <#if coupon.bizObjType==2>
							 	  分时订单
							   </#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;订单编号：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${coupon.bizObjNo}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;实际抵扣金额</label>
							<div class="col-sm-6">
							   <label class="control-label val">${coupon.deductibleAmount}</label>
							</div>
						</div>
						</#if>
  					</form>					
                		
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button id="closeCouponView" class="btn btn-default pull-right navbar-btn btn-flat coupon-operate-close btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
                                关闭
                        </button>
                    </div>
                </div>	
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"coupon":"res/js/marketing/coupon_list"}});
		require(["coupon"], function (coupon){
			coupon.init();
		});  
	});
</script>
</body>
