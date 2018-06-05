<meta charset="utf-8">
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
							<label class="col-sm-6 control-label"><h4>会员积分规则详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;积分业务类型：</label>
							<div class="col-sm-6">
								<label class="control-label val">
									<#if memberPointsRule.businessType==1>社区订单支付
									<#elseif memberPointsRule.businessType==2>主题订单支付</#if>
								</label>
							</div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;规则所对应的积分值：</label>
							<div class="col-sm-6">
							<label class="control-label val">${memberPointsRule.pointsValue}</label>
							</div>
						</div>		
						
						 

					</div>	
        		</div><!-- /.row -->
        		<div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                      <button id="closeMemberPointsRuleView" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
                            关闭
                      </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberPointsRule":"res/js/member/member_points_rule_list"}});
		require(["memberPointsRule"], function (memberPointsRule){
			memberPointsRule.init();
		});  
	});    
</script>
