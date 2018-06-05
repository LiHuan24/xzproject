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
				<label class="col-sm-2 control-label"><h4>新增兑换码</h4></label>
			</div>
		</div>
	</div>	       
		<div class="row hzlist">
			<div class="col-md-10 form-horizontal">
			<form  class="form-horizontal" name="redeemCodeAddFrom">
			<div class="form-group col-md-8">
				<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;名称：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="redeemName" id="redeemName"/>
				</div>
				<div class="col-sm-7"><span name="redeemNameAdd"></span></div>
			</div>
			<div class="form-group col-md-8" hidden="hidden">
				<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;兑换次数：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="availableTimes" id="availableTimes"/>
				</div>
				<div class="col-sm-7"><span name="availableTimesAdd"></span></div>
			</div>
			
			<div class="form-group col-md-8 availableTime-2">
				<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;有效日期：</label>
				<div class="col-sm-7">
				<input class="form-control val" id="availableTime1Wx" name="availableTime1" />
				 至<input class="form-control val" id="availableTime2Wx" name="availableTime2" />
				</div>
				<div><span name="availableTime1Add"></span></div>
				<div><span name="availableTime2Add"></span></div>
			</div>
			
			<div class="form-group col-md-8">
				<label class="col-sm-3 control-label reason key">&nbsp;&nbsp;备注：</label>
				<div class="col-sm-7">
				<textarea class="form-control val" rows="6"  name="memo"></textarea>
				<input type="hidden" name="couponPlanString" value = ""/>
				</div>
				<div style="margin-top:7px;"><span name="memoAdd"></span></div>
			</div>	
	  		<div class="form-group col-md-8">
				<label class="col-sm-3 control-label reason key"><span>*</span>&nbsp;&nbsp;优惠方案：</label>
				<div class="col-sm-3">
					<button type="button" class="btn btn-default" id="couponPlanOfRedeemCodeAdd">方案列表</button>
					<input type="hidden" name="dataSubmit"/>
					<input type="hidden" name="dataTemp"/>
					<input type="hidden" name="planString" value = ""/>
				</div>
				<div style="margin-top:7px;"><span name="planNoAdd"></span></div>
			</div>
	  		<div class = "planNosOfRedeemCodeAddModal"></div>	
			</form>		
		</div>	
	</div><!-- /.row -->
	
    <div class="form-group">
        <div class="col-sm-5" style="margin-bottom:20px;">
        <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="closeAddRedeemCode" class="btn btn-default pull-right sure btncolorb" >
                关闭
        </button>
        <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="addRedeemCode" class="btn btn-default pull-right sure btncolora" >
                保存
        </button>
        </div>
    </div>
</div>


<div class="modal fade" id="couponPlanOfRedeemCodeAddModal" tabindex="-1" role="dialog">
	<div class="modal-dialog">
	   	<div class="modal-content">
		       <!--定义操作列按钮模板-->
		       <script id="couponPlanOfRedeemCodeAddTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-body">
			         <table id="couponPlanOfRedeemCodeAddTable" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->

    
    
    
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"redeemCodeAdd":"res/js/marketing/redeemCode_add"}});
		require(["redeemCodeAdd"], function (redeemCodeAdd){
			redeemCodeAdd.init();
		});  
		
		$("#availableTime1Wx").datetimepicker({
        	language: "zh-CN",
       	 minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd",
            startDate:new Date()
        }).on("changeDate",function(){
            var start = $("#availableTime1Wx").val();
            $("#availableTime2Wx").datetimepicker("setStartDate",start);
        }); 
        $("#availableTime2Wx").datetimepicker({
        	language: "zh-CN",
       	 minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"
           // startDate: "2017-01-01"  //开始时间  ENdDate 结束时间
        }).on("changeDate",function(){
            var end = $("#availableTime2Wx").val();
            $("#availableTime1Wx").datetimepicker("setEndDate",end);
        });  
		
    });
</script>
