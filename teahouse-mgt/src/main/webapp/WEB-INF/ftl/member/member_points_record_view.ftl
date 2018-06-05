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
							<label class="col-sm-6 control-label"><h4>会员积分记录详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-10 form-horizontal">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;积分业务类型：</label>
							<div class="col-sm-6">
								<label class="control-label val">
									<#if memberPointsRecord.businessType==1>订单支付
									<#elseif memberPointsRecord.businessType==2>套餐支付
									</#if>
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;积分类型：</label>
							<div class="col-sm-6">
								<label class="control-label val">
									<#if memberPointsRecord.pointsType==0>会员经验积分
									<#elseif memberPointsRecord.pointsType==1>可用于消费的积分</#if>
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;操作类型：</label>
							<div class="col-sm-6">
								<label class="control-label val">
									<#if memberPointsRecord.opType==0>扣除/使用积分
									<#elseif memberPointsRecord.opType==1>增加/获得积分</#if>
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;积分值：</label>
							<div class="col-sm-6">
							<label class="control-label val">${memberPointsRecord.pointsValue}</label>
							</div>
						</div>	
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;业务数据：</label>
							<div class="col-sm-6">
							<label class="control-label val">${memberPointsRecord.businessData}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;会员姓名：</label>
							<div class="col-sm-6">
							<label class="control-label val">${memberPointsRecord.memberName}</label>
							</div>
						</div>		
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label reason key"><span></span>&nbsp;&nbsp;备注：</label>
							<div class="col-sm-6">
								<label class="control-label val">
									${memberPointsRecord.recordMemo}
								</label>
							</div>
						</div>
					</div>	
        		</div><!-- /.row -->
        		<div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                      <button id="closeMemberPointsRecordView" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
                            关闭
                      </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberPointsRecord":"res/js/member/member_points_record_list"}});
		require(["memberPointsRecord"], function (memberPointsRecord){
			memberPointsRecord.init();
		});  
	});    
</script>
