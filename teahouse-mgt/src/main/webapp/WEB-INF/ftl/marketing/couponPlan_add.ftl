<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1, maximum-scale=3, minimum-scale=1, user-scalable=no">
<meta name="" http-equiv="X-UA-compatible" content="IE=8,chorm=1"/ie="edge">
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
				<label class="col-sm-12 control-label"><h4>新增优惠券方案</h4></label>
			</div>
		</div>
	</div>	       
		<div class="row hzlist">
			<div class="col-md-12 form-horizontal">
			<form  class="form-horizontal" name="couponPlanAddFrom">
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;标题：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="title" id="titleId"/>
				</div>
				<div class="col-sm-7"><span name="titleAdd"></span></div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key">&nbsp;&nbsp;子标题：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="subtitle" />
				</div>
				<div class="col-sm-7"><span name="subtitleAdd"></span></div>
			</div>
			<!-- <div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;优惠类型：</label>
				<div class="col-sm-7">
				 <select name="couponType" class="form-control val">
						 <option  value="1" >优惠券</option>
					</select>
				</div>
				<div class="col-sm-7"><span name="couponTypeAdd"></span></div>
			</div> -->
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;优惠类型：</label>
				<div class="col-sm-7">
				 <select name="couponMethod" class="form-control val">
						 <option  value="1" >折扣</option>
						 <option  value="2" >直减</option>
						 <option  value="3" >社区馆一天体验券</option>
						 <option  value="4" >主题课程一节体验券</option>
					</select>
				</div>
				<div class="col-sm-7"><span name="couponMethodAdd"></span></div>
			</div>
			<div class="form-group col-md-6 couponMethod-1">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;折扣率：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="discount"/>
				</div>
				<div class="col-sm-7"><span name="discountAdd"></span></div>
			</div>
			<!-- <div class="form-group col-md-6 couponMethod-1">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;封顶金额：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="discountMaxAmount" />
				</div>
				<div class="col-sm-7"><span name="discountMaxAmountAdd"></span></div>
			</div> -->
			<div class="form-group col-md-6 couponMethod-2"   hidden="hidden">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;直减金额：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="discountAmount"/>
				</div>
				<div class="col-sm-7"><span name="discountAmountAdd"></span></div>
			</div>
			<div class="form-group col-md-6 couponMethod-2"  hidden="hidden">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;满减金额：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="consumeAmount" />
				</div>
				<div class="col-sm-7"><span name="consumeAmountAdd"></span></div>
			</div>
			
			<div class="form-group col-md-6 couponMethod-3"   hidden="hidden">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;健身体验次数：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="freeFitnessTime"/>
				</div>
				<div class="col-sm-7"><span name="freeFitnessTimeAdd"></span></div>
			</div>
			
			<div class="form-group col-md-6 couponMethod-4"   hidden="hidden">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;课程体验节数：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="freeCourseNumber"/>
				</div>
				<div class="col-sm-7"><span name="freeCourseNumberAdd"></span></div>
			</div>
			<div class="form-group col-md-10"></div>
			<!-- <div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
				<div class="col-sm-5">
				<input class="form-control val" name="cityName" id="cityName" readonly style="background:#ffffff;"/>
				</div>
				<button type="button" class="btn btn-default" id="couponPlanModal_add_Btn">城市列表</button>
				<input type="hidden" name="cityId" id="cityId">
				<div style="margin-top:7px;"><span name="cityNameAdd"></span></div>
			</div> -->
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;发放数量：</label>
				<div class="col-sm-2">
					<input type="radio" name = "isLimit" value="1" checked="checked">不限
				</div>
				<div class="col-sm-2">
					<input type="radio" name = "isLimit" value="2">限制
				</div>
				<div class="col-sm-3">
					<input class="form-control val maxQuantity" name="maxQuantity" style="display:none"/>
				</div>
				<div class="col-sm-7"><span name="maxQuantityAdd"></span></div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;有效时间类型：</label>
				<div class="col-sm-3">
					<input type="radio" name = "availableTime" value="1" checked="checked">天数&nbsp;&nbsp;
				</div>
				<div class="col-sm-4">
					<input type="radio" name = "availableTime" value="2" >日期				
				</div>
			</div>
			<div class="form-group col-md-6 availableTime-1">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;有效天数：</label>
				<div class="col-sm-7">
				<input class="form-control val" name="availableDays" />
				</div>
				<div class="col-sm-7"><span name="availableDaysAdd"></span></div>
			</div>
			<div class="form-group col-md-6 availableTime-2" hidden="hidden">
				<label class="col-sm-5 control-label key"><span>*</span>&nbsp;&nbsp;有效日期：</label>
				<div class="col-sm-7">
				<input class="form-control val" id="availableTime1Ms" name="availableTime1" />
				 至<input class="form-control val" id="availableTime2Ms" name="availableTime2" />
				</div>
				<div><span name="availableTime1Add"></span></div>
				<div><span name="availableTime2Add"></span></div>
			</div>
			<!-- <div class="form-group col-md-6">
				<label class="col-sm-5 control-label key">&nbsp;&nbsp;图片：</label>
    				<div class="col-sm-7">
    					<img width="180" height="180" name="couponPlanPicUrlImg" />
    					<button type="button" id="addCouponPlanPicUrlButton" class="btn btn-default">上传图片</button>
    					<span name="couponPlanPicUrlAdd"></span>
    				</div>
        		<input name="photoUrl" type="hidden"/>
			</div> -->
			<div class="form-group col-md-6">
				<label class="col-sm-5 control-label reason key">&nbsp;&nbsp;备注：</label>
				<div class="col-sm-7">
				<textarea class="form-control val" rows="6"  name="remark"></textarea>
				</div>
				<div style="margin-top:7px;"><span name="remarkAdd"></span></div>
			</div>	
	  		</form>	
		</div>	
	</div><!-- /.row -->
	
    <div class="form-group">
        <div class="col-sm-7" style="margin-bottom:20px;">
        <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="closeAddCouponPlan" class="btn btn-default pull-right sure btncolorb" >
                	关闭
        </button>
        <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="addCouponPlan" class="btn btn-default pull-right sure btncolora" >
                	保存
        </button>
        </div>
    </div>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="couponPlanPicUrlAddModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">图片上传</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="couponPlanPicUrlAddForm">
					<div class="form-group col-md-6">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="couponPlanPicUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group col-md-6">
                           <label class="col-md-3 control-label val">图片：</label>
                           <div class="col-md-8">
                                <div id="couponPlanPicUploader"><span name="couponPlanPicErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="addCouponPlanPicBtn" value="确定">
					</div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
<div class="modal fade" id="couponPlanModal_add" tabindex="-1" role="dialog">
	<div class="modal-dialog">
	   	<div class="modal-content">
		       <!--定义操作列按钮模板-->
		       <input type="hidden" name="dataTemp" />
		       <script id="couponPlanTpl_add" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
			        <div class="box-body">
			         <table id="couponPlanList_add" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
    
    
    
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"couponPlanAdd":"res/js/marketing/couponPlan_add"}});
		require(["couponPlanAdd"], function (couponPlanAdd){
			couponPlanAdd.init();
		});  
		
		
		var config=new Object();
		config.uploadId="couponPlanPicUploader";
		config.storePath = "couponPlan_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "couponPlanPicUrlAddForm";
		//文件回显inputName
		config.inputName = "couponPlanPicUrl1";
		//错误提示span标签name
		config.spanName = "couponPlanPicErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
    });
	
	$(function(){
        $("#availableTime1Ms").datetimepicker({
        	language: "zh-CN",
       	 minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd",
            startDate:new Date()
        }).on("changeDate",function(){
            var start = $("#availableTime1Ms").val();
            $("#availableTime2Ms").datetimepicker("setStartDate",start);
        }); 
        $("#availableTime2Ms").datetimepicker({
        	language: "zh-CN",
       	 minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"
           // startDate: "2017-01-01"  //开始时间  ENdDate 结束时间
        }).on("changeDate",function(){
            var end = $("#availableTime2Ms").val();
            $("#availableTime1Ms").datetimepicker("setEndDate",end);
        });  
	});
	
</script>
