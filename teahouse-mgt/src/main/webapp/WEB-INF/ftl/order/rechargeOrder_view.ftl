<meta charset="utf-8">
<style>
    .key{
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
							<label class="col-sm-12 control-label key"><h4>订单详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					  	<!-- tabs -->
						<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
					  		<!-- Nav tabs -->
					  		<ul id="rechargeOrderViewTabs" class="nav nav-tabs">
						    	<li class="active"><a href="#baseTab" aria-controls="baseTab" role="tab" data-toggle="tab">基础信息</a></li>
						    	<li><a href="#costTab" aria-controls="costTab" role="tab" data-toggle="tab">充值包信息</a></li>
						    	<li><a href="#payTab" aria-controls="payTab" role="tab" data-toggle="tab">支付信息</a></li>
						    	<!-- <li><a href="#billingTab" aria-controls="billingTab" role="tab" data-toggle="tab">开票信息</a></li>
						    	<li><a href="#commentsTab" aria-controls="commentsTab" role="tab" data-toggle="tab">评价信息</a></li>
 -->					 	</ul>    
						    <!-- Tab panes begin -->
							<div class="tab-content">
						     	<div class="tab-pane active" id="baseTab">
									<div class="col-md-12 form-horizontal">
										<div class="form-group col-md-12">
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;订单号：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											   ${rechargeOrder.rechargeOrderNo}
			       								<input type="hidden"  name="rechargeOrderNo" value="${rechargeOrder.rechargeOrderNo}">
											   </label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;客户：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											   ${rechargeOrder.memberName}
											 </label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;手机：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											      ${rechargeOrder.mobilePhone}
											       </label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;充值包名称：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											${rechargeOrder.rechargeName}
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;充值包金额：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											${rechargeOrder.rechargeAmount}
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付金额：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											${rechargeOrder.payableAmount}
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付状态：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if rechargeOrder.payStatus==0>
													未支付
												<#elseif rechargeOrder.payStatus==1>
					                				已支付
					                			<#elseif rechargeOrder.payStatus==2>
					                				部分支付
					                			<#else>
					                			</#if>
											</label>
											</div>
										</div>	
									</div>
					      	 	</div>
						     	<div class="tab-pane" id="costTab">
									<div class="col-md-12 form-horizontal">
										<div class="form-group col-md-12">
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;充值包编号：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												${rechargeOrder.rechargeNo}
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;充值包名称：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												${rechargeOrder.rechargeName}
											</label>
											</div>
										</div>	
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;充值包金额：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if rechargeOrder.rechargeAmount??>
													${rechargeOrder.rechargeAmount?string(',###.##')}元
												</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;应支付：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if rechargeOrder.payableAmount??>
													${rechargeOrder.payableAmount?string(',###.##')}元
												</#if>
											</label>
											</div>
										</div>	
						      	 	</div>
						      	 </div>
								 <div class="tab-pane" id="payTab">
									<div class="col-md-12 form-horizontal">
										<div class="form-group col-md-12">
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付状态：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if rechargeOrder.payStatus==0>
													未支付
												<#elseif rechargeOrder.payStatus==1>
					                				已支付
					                			<#elseif rechargeOrder.payStatus==2>
					                				部分支付
					                			<#else>
					                			</#if>
											</label>
											</div>
										</div>	
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;应支付：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if rechargeOrder.payableAmount??>
													${rechargeOrder.payableAmount?string(',###.##')}元
												</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付时间：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if rechargeOrder.createTime??>
											${rechargeOrder.createTime?string('yyyy-MM-dd HH:mm:ss')}
											</#if>
											</label>	
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付方式：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											    <#if rechargeOrder.paymentMethod==1>
													支付宝
												<#elseif rechargeOrder.paymentMethod==2>
					                				微信
					                			<#elseif rechargeOrder.paymentMethod==3>
					                				银行卡转账
					                			<#else>
					                				其他
					                			</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;流水号：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											${rechargeOrder.partTradeFlowNo}
											</label>
											</div>
										</div>
									</div>
								</div>
						    	
						    	<div class="tab-pane" id="billingTab">
									<div class="col-md-12 form-horizontal">
										<div class="form-group col-md-12">
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;申请时间：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if invoiceTime??>
											${invoiceTime?string('yyyy-MM-dd HH:mm:ss')}
											<#else>
											无
											</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;开票类型：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											   <#if invoiceType==1>
													增值税普通发票纸质版
												<#elseif invoiceType==2>
					                				增值税专用发票
					                			<#elseif invoiceType==3>
					                				增值税普通发票电子版
					                			<#else>
													无
					                			</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;开票时间：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if rechargeOrder.invoiceTime??>
											${rechargeOrder.invoiceTime?string('yyyy-MM-dd HH:mm:ss')}
											<#else>
											无
											</#if>
											</label>
											</div>
										</div>	
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;发票号：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if rechargeOrder.invioceNo!="">
												${rechargeOrder.invioceNo}
											<#else>
											无
											</#if>
											</label>
											</div>
										</div>
				      	 			</div>
					    		</div> 
					    		<div class="tab-pane" id="commentsTab">
									<div class="col-md-12 form-horizontal">
										<div class="form-group col-md-12">
										</div>
										<#list commentsList as comments>
											<div class="form-group col-md-6">
												<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;评价星级：</label>
												<div class="col-sm-6">
												<label class="control-label val">
													<#if comments.score!="">
													${comments.score}
													<#else>
													无
													</#if>
												</label>
												</div>
											</div>
											<div class="form-group col-md-6">
												<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;评价内容：</label>
												<div class="col-sm-6">
												<label class="control-label val">
													<#if comments.content!="">
													${comments.content}
													<#else>
													无
													</#if>
												</label>
												</div>
											</div>
										</#list>
				      	 			</div>
					    		</div>
				      		</div><!-- Tab panes end -->
	    				</div><!-- /.tabs -->   
    				</div><!-- /.col -->   
        		</div><!-- /.row -->
	<div class="form-group">
        <div class="col-sm-7">
            <button id="rechargeOrderViewClose" class="btn btn-default pull-right navbar-btn btn-flat member-operate-save btnDefault" style="width: 95px; height: 32px; line-height: 25px; padding-left: 15px; background: #2b94fd">
                    	关闭
            </button>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
     require.config({paths: {"rechargeOrderView":"res/js/order/rechargeOrder_view"}});
		require(["rechargeOrderView"], function (rechargeOrderView){
			rechargeOrderView.init();
		});  
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd hh:mm:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
