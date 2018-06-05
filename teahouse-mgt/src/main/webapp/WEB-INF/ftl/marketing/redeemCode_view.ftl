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
			<form name="redeemCodeViewForm" class="form-horizontal">
				<input type="hidden" name="redeemCode" value="${redeemCode.redeemCode}"/>
            </form>    
               	<div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-4 control-label"><h4>兑换码详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
	 				<form name="redeemCodeViewForm" class="form-horizontal">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;名称：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${redeemCode.redeemName}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;兑换码：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${redeemCode.redeemCode}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;有效日期：</label>
							<div class="col-sm-6 val">
							    <label class="control-label">${redeemCode.availableTime1?string('yyyy-MM-dd ')}  至  ${redeemCode.availableTime2?string('yyyy-MM-dd ')} </label>
							</div>
						</div>
						<#if redeemCode.remark!=null&&redeemCode.remark!=''>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label reason key"><span></span>&nbsp;&nbsp;备注：</label>
							<div class="col-sm-6">
							<label class="control-label val">${redeemCode.remark}</label>
							</div>
						</div>
						</#if>
						<div class="form-group col-md-6">
                            <label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核状态：</label>
                            <div class="col-sm-6">
							                         <label class="control-label val">
                                <#if redeemCode.censorStatus==0>未审核
                                <#elseif redeemCode.censorStatus==1>已审核
                                <#elseif redeemCode.censorStatus==2>未通过
                                </#if></label>
                            </div>
                        </div>
                        <#if redeemCode.censorStatus!=0>
                        <div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核日期：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${redeemCode.censorTime?string('yyyy-MM-dd ')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label reason key"><span></span>&nbsp;&nbsp;审核备注：</label>
							<div class="col-sm-6">
								<label class="control-label val">${redeemCode.censorMemo}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
                            <label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;启用状态：</label>
                            <div class="col-sm-6">
							                         <label class="control-label val">
                                <#if redeemCode.isAvailable==1>
                                	启用
                                <#elseif redeemCode.isAvailable==0>
                                	停用
                                </#if>
                                </label>
                            </div>
                        </div>
                        </#if>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${redeemCode.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;更新时间：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${redeemCode.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
  					</form>					
               		<div class="form-group col-md-12">
						<label class="col-sm-2 control-label reason key">&nbsp;&nbsp;优惠方案：</label>
						<div class="col-sm-10">
						</div>
					</div>	
					<div class="form-group col-md-12">
						<label class="col-sm-2 control-label key"><span></span></label>
						<div class="col-sm-9">
							<table  class="table table-bordered table-striped table-hover couPonPlanLists_censor"></table>
						</div>
        			</div>
                <div class="form-group col-md-10">
                    <div class="col-sm-7" style="margin-bottom:20px;">
                        <button id="closeRedeemCodeView" class="btn btn-default pull-right navbar-btn btn-flat redeemCode-operate-close btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
                                关闭
                        </button>
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
