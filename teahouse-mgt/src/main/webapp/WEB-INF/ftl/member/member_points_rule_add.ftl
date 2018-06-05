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
							<label class="col-sm-12 control-label"><h4>会员积分规则添加</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form name="memberPointsRuleAddForm">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;积分业务类型：</label>
							<div class="col-sm-6">
							   <select name="businessType" class="form-control val">
									<option value="">请选择</option>
									<option value="1">社区订单支付</option>
									<option value="2">主题订单支付</option>
								</select>
							</div>
							<div><span name="businessTypeAdd"></span></div>
						</div>
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;积分类型：</label>
							<div class="col-sm-6">
								<select name="pointsType" class="form-control val">
									<option value="">请选择</option>
									<option value="0">会员经验积分</option>
									<option value="1">可用于消费的积分</option>
									<option value="2">会员分享积分</option>
								</select>
							</div>
							<div><span name="pointsTypeAdd"></span></div>
						</div> -->
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;规则所对应的积分值：</label>
							<div class="col-sm-6">
								<input class="form-control val" name="pointsValue"/>
							</div>
							<div><span name="pointsValueAdd"></span></div>
						</div>		
						<!--<div class="form-group col-md-6">
							<label class="col-sm-6 control-label"><span>*</span>&nbsp;&nbsp;是否是默认规则：</label>
							<div class="col-sm-6">
							 <select name="isDefault" class="form-control">
									<option value="">请选择</option>
									<option value="0">非默认</option>
									<option value="1">默认</option>
								</select>
							</div>
							<div><span name="isDefaultAdd"></span></div>
						</div>
																									
						 <div class="form-group col-md-6">
							<label class="col-sm-6 control-label"><span>*</span>&nbsp;&nbsp;是否可用：</label>
							<div class="col-sm-6">
								<select name="isAvailable" class="form-control">
									<option value="">请选择</option>
									<option value="0">不可用</option>
									<option value="1">可用</option>
								</select>
							</div>
							<div style="margin-top:7px;"><span name="isAvailableAdd"></span></div>
						</div>-->
						</form>
					</div>	
        		</div><!-- /.row -->
        		<div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                      <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" id="closeMemberPointsRule" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btncolorb">
                            关闭
                      </button>
                      <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" id="saveMemberPointsRule" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btncolora">
                            保存
                      </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberPointsRuleAdd":"res/js/member/member_points_rule_add"}});
		require(["memberPointsRuleAdd"], function (memberPointsRuleAdd){
			memberPointsRuleAdd.init();
		}); 
	});    
</script>
