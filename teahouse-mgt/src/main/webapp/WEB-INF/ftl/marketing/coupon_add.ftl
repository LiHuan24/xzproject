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
				<label class="col-sm-12 control-label"><h4>发放优惠券</h4></label>
			</div>
		</div>
	</div>	       
		<div class="row hzlist">
			<div class="col-md-12 form-horizontal">
			<form  class="form-horizontal" name="couponAddFrom">
			<input type="hidden" name="memberNos" value="${memberNos}" />
			<div class="form-group col-md-12">
				<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;方案标题：</label>
				<div class="col-sm-3">
					<input class="form-control val" name="title" id="title" readonly style="background:#ffffff;"/>
				</div>
				<input type="hidden" name="planNo" id="planNo"/>
				<button type="button" class="btn btn-default" id="checkAddPlanBtn">方案列表</button>
				<div class="col-sm-6"><span name="planNoAdd"></span></div>
			</div>
			<!-- <div class="form-group col-md-6">
				<label class="col-sm-6 control-label key">优惠券类型：</label>
				<div class="col-sm-6">
					<input class="form-control val" name="couponTypeName" readonly style="background:#ffffff;"/>
					<input type="hidden" name="couponType"/>
				</div>
			</div> -->
			<div class="form-group col-md-6">
				<label class="col-sm-6 control-label key">优惠类型：</label>
				<div class="col-sm-6">
					<input class="form-control val" name="couponMethodName" readonly style="background:#ffffff;"/>
					<input type="hidden" name="couponMethod"/>
				</div>
			</div>
			<div class="form-group col-md-6 couponMethod-1">
				<label class="col-sm-6 control-label key">折扣率：</label>
				<div class="col-sm-6">
				<input class="form-control val" name="discount" readonly style="background:#ffffff;"/>
				</div>
			</div>
			<div class="form-group col-md-6 couponMethod-2"   hidden="hidden">
				<label class="col-sm-6 control-label key">直减金额：</label>
				<div class="col-sm-6">
				<input class="form-control val" name="discountAmount" readonly style="background:#ffffff;"/>
				</div>
			</div>
			
			<div class="form-group col-md-6 couponMethod-3"   hidden="hidden">
				<label class="col-sm-6 control-label key">健身次数：</label>
				<div class="col-sm-6">
				<input class="form-control val" name="freeFitnessTime" readonly style="background:#ffffff;"/>
				</div>
			</div>
			
			<div class="form-group col-md-6 couponMethod-4"   hidden="hidden">
				<label class="col-sm-6 control-label key">课程节数：</label>
				<div class="col-sm-6">
				<input class="form-control val" name="freeCourseNumber" readonly style="background:#ffffff;"/>
				</div>
			</div>

			<div class="form-group col-md-6  availableTime-1">
				<label class="col-sm-6 control-label key">有效天数：</label>
				<div class="col-sm-6">
				<input class="form-control val" name="availableDays"  readonly style="background:#ffffff;"/>
				</div>
			</div>
			<div class="form-group col-md-6  availableTime-2"  hidden="hidden">
				<label class="col-sm-6 control-label key">有效日期：</label>
				<div class="col-sm-6">
				<input class="datetimepicker form-control val " name="availableTime1" data-format="dd-MM-yyyy hh:mm:ss"  disabled style="background:#ffffff;"/>
				 至<input class="datetimepicker form-control val " name="availableTime2" data-format="dd-MM-yyyy hh:mm:ss"  disabled style="background:#ffffff;"/>
				</div>
			</div>
	  		</form>
		</div>
	</div><!-- /.row -->

    <div class="form-group">
        <div class="col-sm-7" style="margin-bottom:20px;">
        <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="closeAddCoupon" class="btn btn-default pull-right sure btncolorb" >
                关闭
        </button>
        <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="addCoupon" class="btn btn-default pull-right sure btncolora" >
                发放
        </button>
        </div>
    </div>
</div>
<div class="modal fade" id="couponModal" tabindex="-1" role="dialog">
	<div class="modal-dialog">
	   	<div class="modal-content">
		       <!--定义操作列按钮模板-->
		       <script id="couponBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-body">
			         <table id="couponLists" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
    
    
    
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"couponAdd":"res/js/marketing/coupon_add"}});
		require(["couponAdd"], function (couponAdd){
			couponAdd.init();
		});  
		
		$(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            minView: "month",
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
		
    });
</script>
