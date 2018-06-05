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
					  		<ul id="themeOrderViewTabs" class="nav nav-tabs">
						    	<li class="active"><a href="#baseTab" aria-controls="baseTab" role="tab" data-toggle="tab">基础信息</a></li>
						    	<li><a href="#costTab" aria-controls="costTab" role="tab" data-toggle="tab">费用信息</a></li>
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
											   ${themeOrder.themeOrderNo}
			       								<input type="hidden"  name="themeOrderNo" value="${themeOrder.themeOrderNo}">
											   </label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;客户：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											   ${themeOrder.memberName}
											 </label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;手机：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											      ${themeOrder.mobilePhone}
											       </label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;订单状态：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if themeOrder.orderStatus==0>
												已预约
											</#if>
											<#if themeOrder.orderStatus==1>
												进行中
											</#if>
											<#if themeOrder.orderStatus==2>
												已结束
											</#if>
											<#if themeOrder.orderStatus==3>
												已取消
											</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付状态：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if themeOrder.payStatus==0>
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
											${themeOrder.cityName}
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;门店：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											${themeOrder.storeName}
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;课程编号：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												${themeOrder.courseNo}
												</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;排课编号：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												${themeOrder.arrangeNo}
												</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;教练名称：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												${themeOrder.coachName}
												</label>
											</div>
										</div>
										<div class="form-group 预约人数">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;预约人数：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												${themeOrder.arrangeNo}
												</label>
											</div>
										</div>
										
										
										
<!-- 										<#if themeOrder.orderStatus!=2>
											<div class="form-group col-md-6">
												<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;开始时间：</label>
												<div class="col-sm-6">
												<label class="control-label val">
												<#if themeOrder.startTime??>
													${themeOrder.startTime?string('yyyy-MM-dd HH:mm:ss')}
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
												<#if themeOrder.startTime??>
													${themeOrder.startTime?string('yyyy-MM-dd HH:mm:ss')}
												<#else>
													无
												</#if>
												</label>
												</div>
											</div>
											<div class="form-group col-md-6">
												<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;订单时长：</label>
												<div class="col-sm-6">
												<label class="control-label val">
													${themeOrder.whenLong}
													</label>
												</div>
											</div>
											<div class="form-group col-md-6">
												<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;是否签到：</label>
												<div class="col-sm-6">
												<label class="control-label val">
													<#if themeOrder.orderStatus==0>
														未签到
													</#if>
													<#if themeOrder.orderStatus==1>
														已签到
													</#if>
													</label>
												</div>
											</div>
										<#else>
											<div class="form-group col-md-6">
												<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;取消时间：</label>
												<div class="col-sm-6">
												<label class="control-label val">
												<#if themeOrder.cancelTime??>
													${themeOrder.cancelTime?string('yyyy-MM-dd HH:mm:ss')}
												<#else>
													无
												</#if>
												</label>
												</div>
											</div>
										</#if> -->	
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;订单备注：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if themeOrder.memo!="">
												${themeOrder.memo}
											<#else>
												无
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
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;总金额：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if themeOrder.orderAmount??>
												${themeOrder.orderAmount?string(',###.##')}元
											</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;优惠：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if themeOrder.discountAmount??>
												${themeOrder.discountAmount?string(',###.##')}元
											</#if>
											</label>
											</div>
										</div>	
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;余额抵扣：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if themeOrder.balanceDiscountAmount??>
													${themeOrder.balanceDiscountAmount?string(',###.##')}元
												</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;应支付：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if themeOrder.payableAmount??>
													${themeOrder.payableAmount?string(',###.##')}元
												</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;抵用课程包：</label>
											<div class="col-sm-6">
											<label class="control-label val">
												<#if themeOrder.courseBagNum??>
													${themeOrder.courseBagNum}节
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
											<#if themeOrder.payStatus==0>
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
												<#if themeOrder.payableAmount??>
													${themeOrder.payableAmount?string(',###.##')}元
												</#if>
											</label>
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付时间：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											<#if themeOrder.paymentTime??>
											${themeOrder.paymentTime?string('yyyy-MM-dd HH:mm:ss')}
											</#if>
											</label>	
											</div>
										</div>
										<div class="form-group col-md-6">
											<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;支付方式：</label>
											<div class="col-sm-6">
											<label class="control-label val">
											   <#if themeOrder.paymentMethod==1>
													支付宝
												<#elseif themeOrder.paymentMethod==2>
					                				微信
					                			<#elseif themeOrder.paymentMethod==3>
					                				其他
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
											${themeOrder.paymentFlowNo}
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
											<#if themeOrder.invoiceTime??>
											${themeOrder.invoiceTime?string('yyyy-MM-dd HH:mm:ss')}
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
											<#if themeOrder.invioceNo!="">
												${themeOrder.invioceNo}
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
            <button id="themeOrderViewClose" class="btn btn-default pull-right navbar-btn btn-flat member-operate-save btnDefault" style="width: 95px; height: 32px; line-height: 25px; padding-left: 15px; background: #2b94fd">
                    	关闭
            </button>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
     require.config({paths: {"themeOrderView":"res/js/order/themeOrder_view"}});
		require(["themeOrderView"], function (themeOrderView){
			themeOrderView.init();
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
