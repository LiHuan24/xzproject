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
							<label class="col-sm-6 control-label"><h4>会员积分规则编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form name="memberPointsRuleEditForm">
					<input type="hidden" name="ruleId" value="${memberPointsRule.ruleId}"/>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;积分业务类型：</label>
							<div class="col-sm-6">
							   <select name="businessType" class="form-control val" disabled>
									<option value="1" <#if memberPointsRule.businessType==1>selected="selected"</#if>>社区订单支付</option>
									<option value="2" <#if memberPointsRule.businessType==2>selected="selected"</#if>>主题订单支付</option>
								</select>
							</div>
							<div><span name="businessTypeEdit"></span></div>
						</div>
						
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;规则所对应的积分值：</label>
							<div class="col-sm-6">
								<input class="form-control val" name="pointsValue" value="${memberPointsRule.pointsValue}"/>
							</div>
							<div><span name="pointsValueEdit"></span></div>
						</div>
						<!--		
						<div class="form-group">
							<label class="col-sm-6 control-label">*&nbsp;&nbsp;是否是默认规则：</label>
							<div class="col-sm-6">
							 <select name="isDefault" class="form-control">
									<option value="0" <#if memberPointsRule.isDefault==0>selected="selected"</#if>>非默认</option>
									<option value="1" <#if memberPointsRule.isDefault==1>selected="selected"</#if>>默认</option>
								</select>
							</div>
							<div style="margin-top:7px;"><span name="isDefaultEdit"></span></div>
						</div>
																									
						 <div class="form-group">
							<label class="col-sm-6 control-label">*&nbsp;&nbsp;是否可用：</label>
							<div class="col-sm-6">
								<select name="isAvailable" class="form-control">
									<option value="0" <#if memberPointsRule.isAvailable==0>selected="selected"</#if>>不可用</option>
									<option value="1" <#if memberPointsRule.isAvailable==1>selected="selected"</#if>>可用</option>
								</select>
							</div>
							<div style="margin-top:7px;"><span name="isAvailableEdit"></span></div>
						</div>-->
						</form>

					</div>	
        		</div><!-- /.row -->
        		<div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                      <button id="closeMemberPointsRuleEdit" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btncolorb"style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                            关闭
                      </button>
                      <button id="editMemberPointsRule" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btncolora"style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                            保存
                      </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberPointsRuleEdit":"res/js/member/member_points_rule_edit"}});
		require(["memberPointsRuleEdit"], function (memberPointsRuleEdit){
			memberPointsRuleEdit.init();
		}); 
	});    
</script>
