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
							<label class="col-sm-12 control-label"><h4>会员等级详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;等级名称：</label>
							<div class="col-sm-6">
							<label class="control-label val">${memberLevel.levelName}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;等级折扣率：</label>
							<div class="col-sm-6">
							<label class="control-label val">${memberLevel.levelDiscount}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;晋升所需积分：</label>
							<div class="col-sm-6">
							<label class="control-label val">${memberLevel.upgradePoint}</label>
							</div>
						</div>		
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label reason key"><span></span>&nbsp;&nbsp;备注：</label>
							<div class="col-sm-6">
							<label class="control-label val">${memberLevel.memo}</label>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;是否可用：</label>
							<div class="col-sm-6">
							<label class="control-label val">
								<#if memberLevel.isAvailable==1>可用
									<#elseif memberLevel.isAvailable==0>不可用
								</#if>
							</label>
							</div>
						</div>

					</div>	
        		</div><!-- /.row -->
        		<div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;overflow: hidden;">
                      <button id="closeMemberLevelView" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btncolora" style="width: 95px; height: 32px; line-height: 25px; padding-left: 15px; background: #2b94fd">
                            关闭
                      </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberLevel":"res/js/member/member_level_list"}});
		require(["memberLevel"], function (memberLevel){
			memberLevel.init();
		});  
	});    
</script>
