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
						<div class="form-group">
							<label class="col-sm-2 control-label"><h4>退款审核</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row">
					<div class="col-md-8">
	 				<form name="depositRefundCencorForm" class="form-horizontal">
	 				<input type="hidden" name="refundNo" value="${depositRefund.refundNo}"/>
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;客户名称：</label>
							<div class="col-sm-4">
							    <label class="control-label val">${depositRefund.memberName}
							    </label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;客户手机号：</label>
							<div class="col-sm-4">
							   <label class="control-label val">${depositRefund.mobilePhone}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;申请时间：</label>
							<div class="col-sm-4">
							<label class="control-label val">${depositRefund.applyTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;退款金额：</label>
							<div class="col-sm-4">
							    <label class="control-label val">${depositRefund.refundAmount?string(',###.##')}</label>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;审核结果：</label>
							<div class="col-sm-4 val">
							   <input type="radio" name="cencorStatus"  value="1" checked="checked">通过</input>
                                <input type="radio" name="cencorStatus"  value="3" >不通过</input>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;审核通过时请确认：</label>
							<div class="col-sm-4 val">
								<label class="checkbox-inline">
		                    	<input type="checkbox"  class="butcheck" value="确认无欠费"> 确认无欠费
								</label>
								<br/>
								
								<label class="checkbox-inline">
								<input type="checkbox" class="butcheck" value="确认设备无损坏">确认设备无损坏
								</label>
								<br/>
							</div>
							 <input type="hidden" name="cencorConfirmItem" id="cencorConfirmItem" value="${depositRefund.cencorConfirmItem}">
						</div>
						<div class="form-group">
							<label class="col-sm-3 control-label key">&nbsp;&nbsp;审核备注：</label>
							<div class="col-sm-4 val">
							    <textarea class="form-control val" rows="6"  name="cencorMemo" >${depositRefund.cencorMemo}</textarea>
							</div>
						</div>
  					</form>					
                		<div class="form-group">
                			<div class="col-sm-9">
                				<button type="button" id="closeDepositRefundCencor" class="btn btn-default pull-right sure " style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">关闭</button>
							<button type="button" id="cencorDepositRefund" class="btn btn-default pull-right sure " style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd; margin-right: 10px;">保存</button>
                			</div>
                		</div>	
					</div>	
        		</div><!-- /.row -->
    
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"depositRefundCencor":"res/js/finace/depositRefund_cencor"}});
		require(["depositRefundCencor"], function (depositRefundCencor){
			depositRefundCencor.init();
		});  
	});
</script>
</body>
