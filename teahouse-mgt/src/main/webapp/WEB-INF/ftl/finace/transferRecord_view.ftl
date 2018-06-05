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
				<label class="col-sm-12 control-label"><h4>余额提现详情</h4></label>
			</div>
		</div>
	</div>	       
	<div class="row hzlist">
		<div class="col-md-10 form-horizontal">
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;提现编号：</label>
					<div class="col-sm-6">
						<label class="control-label val">${transferRecord.transferNo}</lable>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;会员名称：</label>
				<div class="col-sm-6">
					<label class="control-label val">${transferRecord.memberName}</lable>
				</div>
			</div>
		 	<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;会员手机号：</label>
				<div class="col-sm-6">
					<label class="control-label val">${transferRecord.mobilePhone}</lable>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;申请时间：</label>
				<div class="col-sm-6">
				<label class="control-label val">${transferRecord.applyTime?string('yyyy-MM-dd HH:mm:ss')}</label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;提现状态：</label>
				<div class="col-sm-6">
				   <label class="control-label val">
				   <#if transferRecord.transferStatus==0>未提现
				   <#elseif transferRecord.transferStatus==1>已提现
				   <#elseif transferRecord.transferStatus==2>提现失败
				   <#elseif transferRecord.transferStatus==3>用户取消
				   <#elseif transferRecord.transferStatus==4>后台取消
				   </#if>
				   </label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;流水号：</label>
				<div class="col-sm-7">
				<label class="control-label val">${transferRecord.flowNo}</label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;提现方法：</label>
				<div class="col-sm-6">
				<label class="control-label val">
				<#if transferRecord.transferMethod==1>支付宝
				   <#elseif transferRecord.transferMethod==2>微信
				   </#if>
				</label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;提现时间：</label>
				<div class="col-sm-6">
				<label class="control-label val">${transferRecord.transferTime?string('yyyy-MM-dd HH:mm:ss')}</label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;提现金额：</label>
				<div class="col-sm-6">
				<label class="control-label val">${transferRecord.transferAmount?string(',###.##')}元</label>
				</div>
			</div>
			<#if transferRecord.transferStatus==2>
			   <div class="form-group col-md-6">
					<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;失败原因：</label>
					<div class="col-sm-6">
						<label class="control-label val">${transferRecord.errorMsg}</label>
					</div>
				</div>
			</#if>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;审核状态：</label>
				<div class="col-sm-6">
				   <label class="control-label val">
				   <#if transferRecord.cencorStatus==0>未审核
				   <#elseif transferRecord.cencorStatus==1>已审核
				   <#elseif transferRecord.cencorStatus==2>未通过
				   </#if>
				   </label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;审核人：</label>
				<div class="col-sm-6">
				<label class="control-label val">${cencorName}</label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;审核备注：</label>
				<div class="col-sm-6">
				<label class="control-label val">${transferRecord.cencorMemo}</label>
				</div>
			</div>
			<#if transferRecord.transferStatus==4>
				<div class="form-group col-md-6">
					<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;取消原因：</label>
					<div class="col-sm-6">
					<label class="control-label val">${transferRecord.cancelReason}</label>
					</div>
				</div>
			</#if>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;创建日期：</label>
				<div class="col-sm-6">
				<label class="control-label val">${transferRecord.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span></span>&nbsp;&nbsp;更新日期：</label>
				<div class="col-sm-6">
				<label class="control-label val">${transferRecord.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
				</div>
			</div>
		</div>	
	</div><!-- /.row -->
	<div class="form-group">
		<div class="col-sm-6" style="margin-bottom:20px;">
			<button type="button" id="closeTransferRecordView" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
	                  关闭
			</button>
		</div>	
	</div>
</div>

<script type="text/javascript">
	$(function(){
		require.config({paths: {"transferRecord":"res/js/marketing/transferRecord_list"}});
		require(["transferRecord"], function (transferRecord){
			transferRecord.init();
		});
		$(".datepicker").datepicker({
	      language: "zh-CN",
	      autoclose: true,//选中之后自动隐藏日期选择框
	      clearBtn: true,//清除按钮
	      todayBtn: 'linked',//今日按钮
	      format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	  	});
	}); 
</script>
