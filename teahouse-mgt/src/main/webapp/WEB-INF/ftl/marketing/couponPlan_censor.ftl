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
							<label class="col-sm-12 control-label"><h4>优惠券方案审核</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
	 				<form name="couponPlanCencorForm" class="form-horizontal">
	 					<input type="hidden" name="planNo" value="${couponPlan.planNo}"/>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;标题：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${couponPlan.title}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;子标题：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${couponPlan.subtitle}</label>
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;优惠方式：</label>
							<div class="col-sm-6">
							   <label class="control-label val">
							   <#if couponPlan.couponMethod==1>
							 	  折扣
							   </#if>
							   <#if couponPlan.couponMethod==2>
							 	  直减
							   </#if>
							   <#if couponPlan.couponMethod==3>
							 	社区馆一天体验券
								</#if>
								<#if couponPlan.couponMethod==4>
							 	主题课程一节体验券
								</#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;
							<#if couponPlan.couponMethod==1>
							 	折扣率：
							</#if>
							<#if couponPlan.couponMethod==2>
							 	直减金额：
							</#if>
							<#if couponPlan.couponMethod==3>
							 	社区馆一天体验券：
							</#if>
							<#if couponPlan.couponMethod==4>
								 主题课程一节体验券：
							</#if>
							</label>
							<div class="col-sm-6">
							   <label class="control-label val">
							   <#if couponPlan.couponMethod==1>
							   		${couponPlan.discount}
								</#if>
								<#if couponPlan.couponMethod==2>
								 	 ${couponPlan.discountAmount}
								</#if>
								<#if couponPlan.couponMethod==3>
								 	 ${couponPlan.freeFitnessTime} 次
								</#if>
								<#if couponPlan.couponMethod==4>
								 	 ${couponPlan.freeCourseNumber} 节
								</#if>
							   </label>
							</div>
						</div>
						<!-- <#if couponPlan.discountMaxAmount ??>
							<div class="form-group col-md-6">
								<label class="col-sm-6 control-label key">&nbsp;&nbsp;封顶金额</label>
								<div class="col-sm-6">
								   <label class="control-label val">${couponPlan.discountMaxAmount}</label>
								</div>
							</div>
						</#if> -->
						<#if couponPlan.consumeAmount ??>
							<div class="form-group col-md-6">
								<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;满减金额</label>
								<div class="col-sm-6">
								   <label class="control-label val">${couponPlan.consumeAmount}</label>
								</div>
							</div>
						</#if>
											
						<#if couponPlan.vailableTime1 ??>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;有效日期：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${couponPlan.vailableTime1?string('yyyy-MM-dd ')}  至  ${couponPlan.vailableTime2?string('yyyy-MM-dd ')} </label>
							</div>
						</div>
						</#if>
						<#if couponPlan.availableDays!=null&&couponPlan.availableDays!=''>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;有效天数：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${couponPlan.availableDays} 天</label>
							</div>
						</div>
						</#if>
						
						<#if couponPlan.photoUrl!=null&&couponPlan.photoUrl!=''>
						<div class="form-group col-md-12">
							<label class="col-sm-2 control-label val">&nbsp;&nbsp;图片：</label>
							<div class="col-sm-5">
                			<label class="control-label val">
                				<img src="${imagePath!''}/${couponPlan.photoUrl}" width="320" height="200"/>
                				</label>
                			</div>
						</div>
						</#if>
						<div class="form-group col-md-6">
                         	<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;发放数量：</label>
                            <div class="col-sm-6">
                               	<label class="control-label val">${couponPlan.maxQuantity}</label>
                            </div>
                        </div>

						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;审核状态：</label>
							<div class="col-sm-6 val">
							    <label class="radio-inline">
	                               <input type="radio" name="censorStatus"value="1" <#if (couponPlan.censorStatus == 1)>checked="checked"</#if>/> 审核通过
	                            </label>
	                            <label class="radio-inline">
	                               <input type="radio" name="censorStatus" value="2" <#if (couponPlan.censorStatus == 0)>checked="checked"</#if>/>不通过
                                </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key reason">&nbsp;&nbsp;审核备注：</label>
							<div class="col-sm-6">
								<textarea class="form-control val"   name="censorMemo"></textarea>
							</div>
						</div>
  					</form>					
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    	<button type="button" id="closeCouponPlanRule" class="btn btn-default pull-right sure " style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">关闭</button>
						&nbsp;&nbsp;
						<button type="button" id="EditCouponPlanRule" class="btn btn-default pull-right sure " style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">提交</button>
                    </div>
                </div>	
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"couponPlanCencor":"res/js/marketing/couponPlan_cencor"}});
		require(["couponPlanCencor"], function (couponPlanCencor){
			couponPlanCencor.init();
		});  
	}); 
</script>
</body>
