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
    .plan_model {
    	style:"width:900px"
    }
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>兑换码编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
						<form  class="form-horizontal" name="redeemCodeEditFrom">
						<input type="hidden" name="redeemCode" value="${redeemCode.redeemCode}"/>
						<div class="form-group col-md-8">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;名称：</label>
							<div class="col-sm-7">
							 <input class="form-control val" name="redeemName" value="${redeemCode.redeemName}"/>
							</div>
							<div class="col-sm-7"><span name="redeemNameEdit"></span></div>
						</div>
						<div class="form-group col-md-8 availableTime-2">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;有效日期：</label>
							<div class="col-sm-7">
							<input class="form-control val" id="availableTime1WxEd" name="availableTime1" value="${redeemCode.availableTime1?string('yyyy-MM-dd')}"/>
							 至<input class="form-control val" id="availableTime2WxEd" name="availableTime2" value="${redeemCode.availableTime2?string('yyyy-MM-dd')}"/>
							</div>
							<div><span name="availableTime1Edit"></span></div>
							<div><span name="availableTime2Edit"></span></div>
						</div>
						<div class="form-group col-md-8">
							<label class="col-sm-3 control-label reason key">&nbsp;&nbsp;备注：</label>
							<div class="col-sm-7">
							<textarea class="form-control val" rows="6"  name="memo">${redeemCode.memo}</textarea>
							</div>
							<div style="margin-top:7px;"><span name="memoEdit"></span></div>
						</div>	
					   <div class="form-group col-md-8">
							<label class="col-sm-3 control-label reason key"><span>*</span>&nbsp;&nbsp;优惠方案：</label>
							<div class="col-sm-3">
								<button type="button" class="btn btn-default" id="couponPlanOfRedeemCodeEdit">方案列表</button>
								<input type="hidden" name="dataSubmit" value=''/>
								<input type="hidden" name="dataTemp" value = ''/>
								<input type="hidden" name="planString" value = '${redeemCouponPlans}'/>
							</div>
							<div style="margin-top:7px;"><span name="planNoEdit"></span></div>
						</div>
						<div class = "planNosOfRedeemCodeEditModal"></div>
						</form>		
					</div>	
        		</div><!-- /.row -->
                <div class="form-group col-md-8">
                    <div class="col-sm-8" style="margin-bottom:20px;">
                    <button type="button" id="closeEditRedeemCode" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                            	关闭
                    </button> 
                    <button type="button" id="editRedeemCode" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                            	保存
                    </button> 																				
                    </div>	
                </div>
			</div>

<div class="modal fade" id="couponPlanOfRedeemCodeEditModal" tabindex="-1" role="dialog">
	<div class="modal-dialog plan_model">
	   	<div class="modal-content" >
		       <!--定义操作列按钮模板-->
		       <script id="couponPlanOfRedeemCodeEditTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-body">
			         <table id="couponPlanOfRedeemCodeEditTable" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<script type="text/javascript">
	$(function(){
	  require.config({paths: {"redeemCodeEdit":"res/js/marketing/redeemCode_edit"}});
		require(["redeemCodeEdit"], function (redeemCodeEdit){
			redeemCodeEdit.init();
		});  
    });
	
	$("#availableTime1WxEd").datetimepicker({
    	language: "zh-CN",
   	 minView: "month",
        autoclose: true,//选中之后自动隐藏日期选择框
        clearBtn: true,//清除按钮
        todayBtn: 'linked',//今日按钮
        format: "yyyy-mm-dd"
        //startDate: "2017-01-01" //日期开始时间 也可以是new Date()只能选择以后的时间
    }).on("changeDate",function(){
        var start = $("#availableTime1WxEd").val();
        $("#availableTime2WxEd").datetimepicker("setStartDate",start);
    }); 
    $("#availableTime2WxEd").datetimepicker({
    	language: "zh-CN",
   	 minView: "month",
        autoclose: true,//选中之后自动隐藏日期选择框
        clearBtn: true,//清除按钮
        todayBtn: 'linked',//今日按钮
        format: "yyyy-mm-dd",
        startDate: $("#availableTime1WxEd").val()  //开始时间  ENdDate 结束时间
    }).on("changeDate",function(){
    	debugger
        var end = $("#availableTime2WxEd").val();
        $("#availableTime1WxEd").datetimepicker("setEndDate",end);
    });   
</script>
