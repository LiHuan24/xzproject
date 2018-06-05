<meta charset="utf-8">
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%;
}
.frombg .form-control{
	width:45% !important;
	margin-right:20%;
}
.pull-down-menu span {
    width: 20%;
}
}
@media only screen and (max-width:768px) {
.row .sorting_disabled{
font-size:1.1rem !important;
}
}
@media only screen and (min-width:768px) and (max-width:1024px) {
.row .sorting_disabled{
font-size:1.2rem !important;
}
}
@media only screen and (min-width:1024px) and (max-width:1366px) {
.row .sorting_disabled{
font-size:1.3rem !important;
}
}
.other{
margin:0 !important;
padding:0 !important;
}
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
  <div class="container-fluid">
   <div class="row">
    <div class="col-md-12 pd10">
        <h4 class="box-title">查询</h4>
               <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool more">
              更多<img src="res/img/arrow-down.png" width="15" style="margin-left: 2px;"/>
          </button>
       </div><!-- /.box-tools -->
     <div class="box box-default">
         <form name="depositRefundSearchForm">
      <div class="with-border">

          <div class="row pull-down-menu col-md-11" style="margin-left: 1px; background: #f1f5f8;">
              <div class="col-md-3">
                  <div class="frombg">
                      <span>客户手机</span><input class="form-control" name="mobilePhone" placeholder="">
                  </div>
              </div>
              <div class="col-md-3">
                  <div class="frombg">
                      <span>申请时间</span><input class="datepicker form-control" id="applyTime1Ns" name="applyTime1"  placeholder=""/></div>
              </div>

              <div class="col-md-3">
                  <div class="frombg">
                      <span>至</span><input class="datepicker form-control" id="applyTime2Ns" name="applyTime2"  placeholder=""/>
                  </div>
              </div>
              <div class="col-md-3">
                  <div class="frombg">
                      <span>审核状态</span><select class="form-control" name="cencorStatus">
                      <option value="">全部</option>
                      <option value="1">已审核</option>
                      <option value="0"<#if cencorStatus!=null && cencorStatus==0>selected</#if> >未审核</option>
                      <option value="1">已通过</option>
                      <option value="2">待审核</option>
                      <option value="3">未通过</option>
                  </select>
                  </div>
              </div>
          </div>
      </div><!-- /.box-header -->

       <div class="box-body">
           <div class="row pull-down-menu col-md-11 other searching">
			<div class="col-md-3">
			    <div class="frombg">
                    <span>退款状态</span><select class="form-control" name="refundStatus">
                                              <option value="">全部</option>
                                              <option value="1">已退款</option>
                                              <option value="0">未退款</option>
                                              <option value="2">退款失败</option>
                                            </select>
                </div>
			 </div>
			 <div class="col-md-3">
                    <div class="frombg">
                        <span>退款流水号</span><input class="form-control" name="refundFlowNo" placeholder="">
                    </div>
              </div>
               <div class="col-md-3">
                   <div class="frombg">
                       <span>退款时间</span><input class="datepicker form-control" id="refundTime1Ns" name="refundTime1"  placeholder=""/>
                   </div>
               </div>
               <div class="col-md-3">
                   <div class="frombg">
                       <span>至</span><input class="datepicker form-control" id="refundTime2Ns" name="refundTime2"  placeholder=""/>
                   </div>
               </div>
               <div class="col-md-3">
                   <div class="frombg">
                       <span>退款方式</span>
                       <select class="form-control" name="refundMethod">
                           <option value="">全部</option>
                           <option value="1">支付宝</option>
                           <option value="2">微信</option>
                           <option value="4">线下退款</option>
                       </select>
                   </div>
               </div>
		</div>

        </div><!-- /.box-body -->
     <!--修改-->

             <script>
                 $(".other").hide();
                 $(document).ready(function(){
                     var state = 0; //hide
                     $(".more").click(function(){
                         if (state == 0){
                             $(".other").show();

                             $(this).html('收起<img src="res/img/arrow-up.png" width="15" style="margin-left: 2px;"/>');
                             state = 1;
                         } else {
                             $(".other").hide();
                             $(this).html('更多<img src="res/img/arrow-down.png" width="15" style="margin-left: 2px;"/>');
                             state = 0;
                         }
                     })
                 })
             </script>
         <div class="col-md-12" style='float:left'>
                <div class="box-footer">
                    <!-- <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"><i class="hziconfont icons-qingchu iconbtn"></i>清空</button>-->

                     <!--<button type="button" class="btn btn-default pull-right btn-flat btncolora" id="depositRefundSearch"><i class="hziconfont icons-yuanxingxuanzhong iconbtn"></i>确认</button>-->

                       <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="depositRefundSearch" style="background:#2b94fd;margin-right:-2px !important">确定</button>
                       <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault"  style="background:#fa6e30;float:right;margin-right:20px !important">清空</button>
                </div><!-- /.box-footer -->
          </div>
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
      <div class="row" style="width: 100%; height: 15px; background: #f1f5f8"></div>
      <div class="row">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="depositRefundBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="depositRefundList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    <!-- 弹出框   审核操作-->
    <div class="modal fade" id="depositRefundBrowseModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">纳税人认定通知书</h4>
                </div>
                <div class="modal-body" >
                       <form class="form-horizontal" name="memberCompanyInfoEditForm"> 
                             <img id="depositRefundBrowseImg" style="width: 500px;height: 600px;"/>
                              <div class="modal-footer">
                       <input type="button" class="btn btn-default pull-right sure" id="depositRefundBrowseModalClose"  value="关闭">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     <!-- 弹出框   审核操作-->
    <div class="modal fade" id="depositRefundMemoModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">线下退款</h4>
                </div>
                <div class="modal-body" >
                       <form class="form-horizontal" name="refundDepositByOfflineForm">
                       <input type="hidden" name="refundNo" /> 
                       <div class="form-group">
							<label class="col-sm-3 control-label reason">*&nbsp;&nbsp;备注：</label>
							<div class="col-sm-8">
							    <textarea class="form-control" rows="6"  name="refundMemo" ></textarea>
							</div>
						</div>
                              <div class="modal-footer">
                              <button type="button" id="closeDepositRefundMemo" class="btn btn-default pull-right sure ">关闭</button>
							<button type="button" id="memoDepositRefund" class="btn btn-default pull-right sure " style="margin-right:20px">提交</button>  		
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"depositRefund":"res/js/finace/depositRefund_list"}});
		require(["depositRefund"], function (depositRefund){
			depositRefund.init();
		});  
	});
    /* $(function () {
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: "linked",//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
    applyTime2Ns refundTime2Ns*/
  
    $(function(){
        $("#applyTime1Ns").datetimepicker({
        	language: "zh-CN",
       	 minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"
            //startDate: "2017-01-01" //日期开始时间 也可以是new Date()只能选择以后的时间
        }).on("changeDate",function(){
            var start = $("#applyTime1Ns").val();
            $("#applyTime2Ns").datetimepicker("setStartDate",start);
        }); 
        $("#applyTime2Ns").datetimepicker({
        	language: "zh-CN",
       	 minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"
           // startDate: "2017-01-01"  //开始时间  ENdDate 结束时间
        }).on("changeDate",function(){
            var end = $("#applyTime2Ns").val();
            $("#applyTime1Ns").datetimepicker("setEndDate",end);
        });   
        
        
        
        $("#refundTime1Ns").datetimepicker({
        	language: "zh-CN",
       	 minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"
            //startDate: "2017-01-01" //日期开始时间 也可以是new Date()只能选择以后的时间
        }).on("changeDate",function(){
            var start = $("#refundTime1Ns").val();
            $("#refundTime2Ns").datetimepicker("setStartDate",start);
        }); 
        $("#refundTime2Ns").datetimepicker({
        	language: "zh-CN",
       	 minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"
           // startDate: "2017-01-01"  //开始时间  ENdDate 结束时间
        }).on("changeDate",function(){
            var end = $("#refundTime2Ns").val();
            $("#refundTime1Ns").datetimepicker("setEndDate",end);
        }); 
        
    });
    
    
   
    
</script>
