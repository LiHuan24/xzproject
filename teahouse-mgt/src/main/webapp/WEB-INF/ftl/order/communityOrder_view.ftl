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
						<form  class="form-horizontal" name="communityOrderViewFrom">
			       			<input type="hidden"  name="communityOrderNo" value="${communityOrder.communityOrderNo}">
						</form>
					  	<!-- tabs -->
						<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
					  		<!-- Nav tabs -->
					  		<ul id="communityOrderViewTabs" class="nav nav-tabs">
						    	<li class="active"><a href="#baseTab" aria-controls="baseTab" role="tab" data-toggle="tab">基础信息</a></li>
						    	<li><a href="#costTab" aria-controls="costTab" role="tab" data-toggle="tab">费用信息</a></li>
						    	<li><a href="#payTab" aria-controls="payTab" role="tab" data-toggle="tab">支付信息</a></li>
						    	<li><a href="#equipmentRecordTab" aria-controls="equipmentRecordTab" role="tab" data-toggle="tab">设备使用记录</a></li>
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
											   ${communityOrder.communityOrderNo}
											   </label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;客户：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											   ${communityOrder.memberName}
											 </label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;手机：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											      ${communityOrder.mobilePhone}
											       </label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;订单状态：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if communityOrder.orderStatus==0>
												进行中
											</#if>
											<#if communityOrder.orderStatus==1>
												已结束
											</#if>
											<#if communityOrder.orderStatus==2>
												已取消
											</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付状态：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if communityOrder.payStatus==0>
												未支付
											<#else>
												已支付
											</#if>
											</label>
											</div>
										</div>																					
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;城市：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											${communityOrder.cityName}
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;门店：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											${communityOrder.storeName}
											</label>
											</div>
										</div>
										
										<#if communityOrder.orderStatus!=2>
											<div class="form-group col-md-6">
												<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;开始时间：</label>
												<div class="col-sm-6">
												<label class="control-label val">
												<#if communityOrder.startTime??>
													${communityOrder.startTime?string('yyyy-MM-dd HH:mm:ss')}
												<#else>
													无
												</#if>
												</label>
												</div>
											</div>
											<div class="form-group col-md-6">
												<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;结束时间：</label>
												<div class="col-sm-6">
												<label class="control-label val">
												<#if communityOrder.endTime??>
													${communityOrder.endTime?string('yyyy-MM-dd HH:mm:ss')}
												<#else>
													无
												</#if>
												</label>
												</div>
											</div>
											<div class="form-group col-md-6">
												<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;订单时长（分钟）：</label>
												<div class="col-sm-6">
												<label class="control-label val">
													${communityOrder.whenLong}
													</label>
												</div>
											</div>
										<#else>
											<div class="form-group col-md-6">
												<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;取消时间：</label>
												<div class="col-sm-6">
												<label class="control-label val">
												<#if communityOrder.cancelTime??>
													${communityOrder.cancelTime?string('yyyy-MM-dd HH:mm:ss')}
												<#else>
													无
												</#if>
												</label>
												</div>
											</div>
										</#if>	
										
									</div>
					      	 	</div>
						     	<div class="tab-pane" id="costTab">
									<div class="col-md-12 form-horizontal">
										<div class="form-group col-md-12">
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;总金额：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if communityOrder.orderAmount??>
												${communityOrder.orderAmount?string(',###.##')}元
											</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;优惠：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if communityOrder.discountAmount??>
												${communityOrder.discountAmount?string(',###.##')}元
											</#if>
											</label>
											</div>
										</div>	
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;余额抵扣：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if communityOrder.balanceDiscountAmount??>
													${communityOrder.balanceDiscountAmount?string(',###.##')}元
												</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;应支付：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if communityOrder.payableAmount??>
													${communityOrder.payableAmount?string(',###.##')}元
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
											<#if communityOrder.payStatus==0>
												未支付
											<#else>
												已支付
											</#if>
											</label>
											</div>
										</div>	
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;应支付：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if communityOrder.payableAmount??>
													${communityOrder.payableAmount?string(',###.##')}元
												</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付时间：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if communityOrder.paymentTime??>
											${communityOrder.paymentTime?string('yyyy-MM-dd HH:mm:ss')}
											</#if>
											</label>	
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付方式：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											   <#if communityOrder.paymentMethod==1>
													支付宝
												<#elseif communityOrder.paymentMethod==2>
					                				微信
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
											${communityOrder.paymentFlowNo}
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
											<#if communityOrder.invoiceTime??>
											${communityOrder.invoiceTime?string('yyyy-MM-dd HH:mm:ss')}
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
											<#if communityOrder.invioceNo!="">
												${communityOrder.invioceNo}
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
					    		<div class="tab-pane" id="equipmentRecordTab">
									<div class="col-md-12 form-horizontal">
										<div class="form-group col-md-12">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;</label>
											<div class="col-sm-12">
												<table id="equipmentRecordListView" class="table table-hover" width="100%" style=""></table>
											</div>
										</div>	
									</div>
								</div>
				      		</div><!-- Tab panes end -->
	    				</div><!-- /.tabs -->   
    				</div><!-- /.col -->   
        		</div><!-- /.row -->
    <div class="form-group">
        <div class="col-sm-6">
            <button id="communityOrderViewClose" class="btn btn-default pull-right navbar-btn btn-flat member-operate-save btnDefault" style="width: 95px; height: 32px; line-height: 25px; padding-left: 15px; background: #2b94fd">
                    	关闭
            </button>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
     require.config({paths: {"communityOrderView":"res/js/order/communityOrder_view"}});
		require(["communityOrderView"], function (communityOrderView){
			communityOrderView.init();
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
