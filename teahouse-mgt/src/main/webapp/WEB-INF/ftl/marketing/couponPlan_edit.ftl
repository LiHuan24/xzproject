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
							<label class="col-sm-2 control-label"><h4>优惠券编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-10 form-horizontal">
					<form  class="form-horizontal" name="couponPlanEditFrom">
					<input type="hidden" name="planNo" value="${couponPlan.planNo}"/>
					<input type="hidden" name="censorStatus" value="${couponPlan.censorStatus}"/>
					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;标题：</label>
						<div class="col-sm-7">
						<input class="form-control val" name="title" id="titleId" value="${couponPlan.title}"/>
						</div>
						<div class="col-sm-7"><span name="titleEdit"></span></div>
					</div>
					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label key">&nbsp;&nbsp;子标题：</label>
						<div class="col-sm-7">
						<input class="form-control val" name="subtitle" value="${couponPlan.subtitle}" />
						</div>
						<div class="col-sm-7"><span name="subtitleEdit"></span></div>
					</div>	
					
					<div class="form-group col-md-6">
						<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;优惠类型：</label>
						<div class="col-sm-7">
						 <select name="couponMethod" class="form-control val">
								 <option  value="1" <#if couponPlan.couponMethod==1>selected="selected"</#if> >折扣</option>
								 <option  value="2" <#if couponPlan.couponMethod==2>selected="selected"</#if> >直减</option>
								 <option  value="3" <#if couponPlan.couponMethod==3>selected="selected"</#if> >社区馆一天体验券</option>
								 <option  value="4" <#if couponPlan.couponMethod==4>selected="selected"</#if> >主题课程一节体验券</option>
							</select>
						</div>
						<div class="col-sm-7"><span name="couponMethodEdit"></span></div>
					</div>
							
			<#if couponPlan.couponMethod==1>
				<div class="form-group col-md-6 couponMethod-1" >
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;折扣率：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="discount"  value="${couponPlan.discount}"/>
					</div>
					<div class="col-sm-7"><span name="discountEdit"></span></div>
				</div>
				<!-- <div class="form-group col-md-6 couponMethod-1" >
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;封顶金额：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="discountMaxAmount" value="${couponPlan.discountMaxAmount}"/>
					</div>
					<div class="col-sm-7"><span name="discountMaxAmountEdit"></span></div>
				</div> -->
				<div class="form-group col-md-6 couponMethod-2" hidden="hidden">
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;直减金额：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="discountAmount"  value="${couponPlan.discountAmount}"/>
					</div>
					<div class="col-sm-7"><span name="discountAmountEdit"></span></div>
				</div>
				<div class="form-group col-md-6 couponMethod-2" hidden="hidden">
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;满减金额：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="consumeAmount" value="${couponPlan.consumeAmount}"/>
					</div>
					<div class="col-sm-7"><span name="consumeAmount"></span></div>
				</div>
				
				
			</#if>
			<#if couponPlan.couponMethod==2>
				<div class="form-group col-md-6 couponMethod-1" hidden="hidden">
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;折扣率：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="discount"  value="${couponPlan.discount}"/>
					</div>
					<div class="col-sm-7"><span name="discountEdit"></span></div>
				</div>
				
				<div class="form-group col-md-6 couponMethod-2" >
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;直减金额：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="discountAmount"  value="${couponPlan.discountAmount}"/>
					</div>
					<div class="col-sm-7"><span name="discountAmountEdit"></span></div>
				</div>
				<div class="form-group col-md-6 couponMethod-2" >
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;满减金额：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="consumeAmount" value="${couponPlan.consumeAmount}"/>
					</div>
					<div class="col-sm-7"><span name="consumeAmount"></span></div>
				</div>
				
			</#if>
			<#if couponPlan.couponMethod==3>
				<div class="form-group col-md-6 couponMethod-1" hidden="hidden">
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;折扣率：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="discount"  value="${couponPlan.discount}"/>
					</div>
					<div class="col-sm-7"><span name="discountEdit"></span></div>
				</div>
				
				<div class="form-group col-md-6 couponMethod-2" hidden="hidden">
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;直减金额：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="discountAmount"  value="${couponPlan.discountAmount}"/>
					</div>
					<div class="col-sm-7"><span name="discountAmountEdit"></span></div>
				</div>
				<div class="form-group col-md-6 couponMethod-2" hidden="hidden">
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;满减金额：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="consumeAmount" value="${couponPlan.consumeAmount}"/>
					</div>
					<div class="col-sm-7"><span name="consumeAmount"></span></div>
				</div>
				
			</#if>
			<#if couponPlan.couponMethod==4>
				<div class="form-group col-md-6 couponMethod-1" hidden="hidden">
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;折扣率：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="discount"  value="${couponPlan.discount}"/>
					</div>
					<div class="col-sm-7"><span name="discountEdit"></span></div>
				</div>
				
				<div class="form-group col-md-6 couponMethod-2" hidden="hidden">
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;直减金额：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="discountAmount"  value="${couponPlan.discountAmount}"/>
					</div>
					<div class="col-sm-7"><span name="discountAmountEdit"></span></div>
				</div>
				<div class="form-group col-md-6 couponMethod-2" hidden="hidden">
					<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;满减金额：</label>
					<div class="col-sm-7">
					<input class="form-control val" name="consumeAmount" value="${couponPlan.consumeAmount}"/>
					</div>
					<div class="col-sm-7"><span name="consumeAmount"></span></div>
				</div>
				
			</#if>
			<div class="form-group col-md-10"></div>
			
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;发放数量：</label>
				<div class="col-sm-2">
					<input type="radio" name = "isLimit" value="1" <#if couponPlan.maxQuantity==null||couponPlan.maxQuantity==''>checked="checked"</#if>>不限
				</div>
				<div class="col-sm-2">
					<input type="radio" name = "isLimit" value="2" <#if couponPlan.maxQuantity!=null&&couponPlan.maxQuantity!=''>checked="checked"</#if>>限制
				</div>
				<div class="col-sm-3">
					<input class="form-control val maxQuantity" name="maxQuantity" <#if couponPlan.maxQuantity==null||couponPlan.maxQuantity==''>style="display:none"</#if> value="${couponPlan.maxQuantity}"/>
				</div>
				<div class="col-sm-7"><span name="maxQuantityEdit"></span></div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;有效时间类型：</label>
				<div class="col-sm-2">
					<input type="radio" name = "availableTime" value="1" <#if couponPlan.availableDays??>checked="checked"</#if>>天数&nbsp;&nbsp;
				</div>
				<div class="col-sm-4">
					<input type="radio" name = "availableTime" value="2" <#if !couponPlan.availableDays??>checked="checked"</#if>>日期				
				</div>
			</div>
			<div class="form-group col-md-6 availableTime-1" <#if !couponPlan.availableDays??>hidden="hidden"</#if>>
				<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;有效天数：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="availableDays" value="${couponPlan.availableDays}"/>
				</div>
				<div class="col-sm-7"><span name="availableDaysEdit"></span></div>
			</div>
			<div class="form-group col-md-6 availableTime-2" <#if couponPlan.availableDays??>hidden="hidden"</#if>>
				<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;有效日期：</label>
				<div class="col-sm-7">
				<input class="form-control val" id="availableTime1EdMs" name="availableTime1" value="${couponPlan.availableTime1?string('yyyy-MM-dd')}"/>
				 至<input class=" form-control val" id="availableTime2EdMs" name="availableTime2" value="${couponPlan.availableTime2?string('yyyy-MM-dd')}"/>
				</div>
				<div><span name="vailableTime1Edit"></span></div>
				<div><span name="vailableTime2Edit"></span></div>
			</div>
			
			
					
			
			
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label key reason">&nbsp;&nbsp;备注：</label>
				<div class="col-sm-7">
				<textarea class="form-control val" rows="6"  name="remark">${couponPlan.remark}</textarea>
				</div>
				<div style="margin-top:7px;"><span name="remarkEdit"></span></div>
			</div>	
			</form>	
			</div>	
      		</div><!-- /.row -->
              <div class="form-group">
                  <div class="col-sm-5" style="margin-bottom:20px;">
                  <button type="button" id="closeEditCouponPlan" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                          	关闭
                  </button> 
                  <button type="button" id="editCouponPlan" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                          	保存
                  </button> 																				
                  </div>	
              </div>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="couponPlanPicUrlEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">图片上传</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="couponPlanPicUrlEditForm">
					<div class="form-group col-md-6">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="couponPlanPicUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group col-md-6">
                           <label class="col-md-3 control-label val">图片：</label>
                           <div class="col-md-8">
                                <div id="couponPlanPicUploaderEdit"><span name="couponPlanPicEditErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editCouponPlanPicBtn" value="确定" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    
  
<div class="modal fade" id="couponPlanModal_edit" tabindex="-1" role="dialog">
	<div class="modal-dialog">
	   	<div class="modal-content">
		       <!--定义操作列按钮模板-->
		       <input type="hidden" name="dataTemp" />
		       <script id="couponPlanTpl_edit" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-body">
			         <table id="couponPlanList_edit" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"couponPlanEdit":"res/js/marketing/couponPlan_edit"}});
		require(["couponPlanEdit"], function (couponPlanEdit){
			couponPlanEdit.init();
		});  
		
		$("#availableTime1EdMs").datetimepicker({
        	language: "zh-CN",
       	 minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd",
            startDate: new Date()
        }).on("changeDate",function(){
            var start = $("#availableTime1EdMs").val();
            $("#availableTime2EdMs").datetimepicker("setStartDate",start);
        }); 
        $("#availableTime2EdMs").datetimepicker({
        	language: "zh-CN",
       	 minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd",
            startDate: new Date() //开始时间  ENdDate 结束时间
        }).on("changeDate",function(){
        	debugger
            var end = $("#availableTime2EdMs").val();
            $("#availableTime1EdMs").datetimepicker("setEndDate",end);
        });   
        
		var config=new Object();
		config.uploadId="couponPlanPicUploaderEdit";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,couponPlan_photo(活动图片)
		config.storePath = "couponPlan_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "couponPlanPicUrlEditForm";
		//文件回显inputName
		config.inputName = "couponPlanPicUrl1";
		//错误提示span标签name
		config.spanName = "couponPlanPicEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
    });
</script>
