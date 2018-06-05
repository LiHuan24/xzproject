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
							<label class="col-sm-12 control-label"><h4>兑换码审核</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
	 				<form name="redeemCodeCensorForm" class="form-horizontal">
	 					<input type="hidden" name="redeemCode" value="${redeemCode.redeemCode}"/>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;名称：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${redeemCode.redeemName}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;兑换码：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${redeemCode.redeemCode}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;有效日期：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${redeemCode.availableTime1?string('yyyy-MM-dd ')}  至  ${redeemCode.availableTime2?string('yyyy-MM-dd ')} </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${redeemCode.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key">&nbsp;&nbsp;审核状态：</label>
							<div class="col-sm-6">
							  <label class="control-label val radio-inline">
	                               <input type="radio" name="censorStatus"value="1" checked="checked"> 已审核
                              </label>
                               <label class="control-label val radio-inline">
                               		<input type="radio" name="censorStatus" value="2">不通过
                              </label>
                            </div>
						</div>
						<div class="form-group col-md-6"></div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key reason">&nbsp;&nbsp;审核备注：</label>
							<div class="col-sm-6">
								<label class="control-label val">
									<textarea class="form-control val" name="censorMemo" style="height: 150px;"></textarea>
								</label>
							</div>
						</div>
					  <div class="form-group col-md-12">
							<label class="col-sm-2 control-label reason key"><span>*</span>&nbsp;&nbsp;优惠方案：</label>
							<div class="col-sm-9">
								<table  class="table table-bordered table-striped table-hover couPonPlanLists_censor" width="80%"></table>
							</div>
							<div style="margin-top:7px;"><span name="planNoAdd"></span></div>
						</div>	
						
  					</form>					
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    	<button type="button" id="closeRedeemCodeCensor" class="btn btn-default pull-right sure " style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">关闭</button>
						&nbsp;&nbsp;
						<button type="button" id="editRedeemCodeCensor" class="btn btn-default pull-right sure " style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">保存</button>
                    </div>
                </div>	
</div>


<script type="text/javascript">
	$(function(){
	  require.config({paths: {"redeemCodeCensor":"res/js/marketing/redeemCode_censor"}});
		require(["redeemCodeCensor"], function (redeemCodeCensor){
			redeemCodeCensor.init();
		});  
	}); 
</script>
</body>
