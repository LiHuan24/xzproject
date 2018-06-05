<meta charset="utf-8">
<style>
.frombg .form-control{
	width:50% !important;
}
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
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12 pd10">
               <h3 class="box-title">查询</h3>
               <hr>
       <div class="box box-default">
        <div class="box-header with-border">

         <div class="box-tools pull-right">
       
               <div class="box-tools pull-right">
          <!--<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>-->
             
         </div>
         </div>
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
         <form class="form-horizontal" name="memberSerachForm">
         <input type="hidden" name="refereeId" value="${member.refereeId}"/>
        <div class="box-body">
         <div class="row pull-down-menu col-md-11">
          <div class="col-md-3">
              <div class="frombg">
                  <span>会员姓名</span><input  class="form-control" name="memberName" placeholder="" value="${member.memberName}">
              </div>
          </div>
          <div class="col-md-3">
              <div class="frombg">
                  <span>手机</span><input class="form-control" name="mobilePhone" placeholder="">
              </div>
          </div>
          <div class="col-md-3">
                 <div class="frombg">                        
                 	<span>身份证</span><input  class="form-control" name="idCard" placeholder="">
                 </div>
           </div>
           <div class="col-md-3">
                <div class="frombg">
                    <span>会员等级</span><select class="form-control" name="memberLevelId">
                    <option value="">全部</option>
                <#list memberLevelList as memberLevel>
                    <option value="${memberLevel.memberLevelId}">${memberLevel.levelName}</option>
                </#list>
                </select>
                </div>
            </div>
          </div><!-- /.box-body -->
         </div>
          <!--修改-->
          <div class="col-md-12" style='float:right !important;'>
               <div class="box-footer">
					<button type="button" class="btn btn-default pull-right  flatten btnColorA" id="memberSearch">确定</button>
					<button type="reset" class="btn btn-default pull-right  flatten btnDefault"  >清空</button>
				</div>
           </div>
       </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="memberBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="memberList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    
     <div class="modal fade" id="inBlacklistModel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">移入黑名单</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="inBlacklistForm"> 
				      <input type="hidden"  name="memberNo" id="inMemberNo">
				      <input type="hidden"  name="isBlacklist" value="1">
				        <label for="inputEmail3" class=" control-label wen" id="inBlacklistMemo"></label>
				         <div>
				            <label for="inputEmail3" class=" control-label reason"><span style="color:red;">*</span>理由:</label>
				         </div> 
				          <div class="form-group">
	                            <div class="col-sm-8">
	                                 <textarea class="form-control textareas" name="blacklistMemo"></textarea>
	                            </div>
	                      </div>
	                      <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="inBlacklistBtn" value="确定" >
                    <button type="button" class="btn btn-default pull-right cancels"  id="inBlacklistCancel">取消</button>
                </div>              
				   </form> 
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    <div class="modal fade" id="outBlacklistModel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">移出黑名单</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="outBlacklistForm"> 
				      <input type="hidden"  name="memberNo" id="outMemberNo">
				      <input type="hidden"  name="isBlacklist" value="0">
				        <label for="inputEmail3" class=" control-label wen" id="outBlacklistMemo"></label>
				         <div>
				            <label for="inputEmail3" class=" control-label reason"><span style="color:red;">*</span>理由:</label>
				         </div> 
				          <div class="form-group">
	                            <div class="col-sm-8">
	                                 <textarea class="form-control textareas" name="blacklistMemo"></textarea>
	                            </div>
	                      </div>
	                      <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="outBlacklistBtn" value="确定" >
                    <button type="button" class="btn btn-default pull-right cancels"  id="outBlacklistCancel">取消</button>
                </div>              
				   </form> 
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript">
	$(function(){
	  require.config({paths: {"member":"res/js/member/member_list"}});
		require(["member"], function (member){
			member.init();
		});  
	});    
   $(function () {
        $(".datetimepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: 'linked',//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>

