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
							<label class="col-sm-2 control-label"><h4>退款详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-8">
	 				<form name="invoiceViewForm" class="form-horizontal">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;客户名称：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${depositRefund.memberName}
							    </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;客户手机号：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${depositRefund.mobilePhone}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;申请时间：</label>
							<div class="col-sm-6">
							<label class="control-label val">${depositRefund.applyTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核状态：</label>
							<div class="col-sm-6">
							   <label class="control-label val">
							   <#if depositRefund.cencorStatus==0>未审核
							   <#elseif depositRefund.cencorStatus==1>已审核
							   <#elseif depositRefund.cencorStatus==3>未通过
							   </#if>
							   </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;退款状态：</label>
							<div class="col-sm-6">
							   <label class="control-label val">
							   <#if depositRefund.refundStatus==0>未退款
							   <#elseif depositRefund.refundStatus==1>已退款
							   </#if>
							   </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;退款流水号：</label>
							<div class="col-sm-6">
							<label class="control-label val">${depositRefund.refundFlowNo}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;退款方法：</label>
							<div class="col-sm-6">
							<label class="control-label val">
							<#if depositRefund.refundMethod==1>支付宝
							   <#elseif depositRefund.refundMethod==2 || depositRefund.refundMethod==3>微信
							   <#elseif depositRefund.refundMethod==4>线下退款
							   </#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;退款时间：</label>
							<div class="col-sm-6">
							<label class="control-label val">${depositRefund.refundTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;退款金额：</label>
							<div class="col-sm-6">
							<label class="control-label val">${depositRefund.refundAmount?string(',###.##')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;创建日期：</label>
							<div class="col-sm-6">
							<label class="control-label val">${depositRefund.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;更新日期：</label>
							<div class="col-sm-6">
							<label class="control-label val">${depositRefund.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
  					</form>					

					</div>	
        		</div><!-- /.row -->
        		<div class="form-group>
                    <div class="col-sm-4" style="margin-bottom:20px;">
                        <button id="closeDepositRefundView" class="btn btn-default pull-right navbar-btn btn-flat carRecord-operate-save btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
                            关闭
                        </button>
                    </div>
                </div>
    
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"depositRefund":"res/js/finace/depositRefund_list"}});
		require(["depositRefund"], function (depositRefund){
			depositRefund.init();
		});  
	});
</script>
</body>
