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
       <h4 class="box-title">查询</h4>
       <div class=" with-border">

         <div class="box-tools pull-right">
         <!-- <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>-->
         </div>
         <!-- /.box-tools -->
        </div>
        <hr>
       <div class="box box-default">
        
        <!-- /.box-header -->
         <form class="form-horizontal" name="returnAreaSearchForm">
        <div class="box-body">
         <div class="row pull-down-menu col-md-11">
          <div class="col-md-6">
                <div class="frombg">
                    <span>名称</span><input type="text" class="form-control" id="returnAreaName" name="returnAreaName" placeholder="">
                </div>
          </div>
          <div class="col-md-6">
               <div class="frombg">
                   <span>状态</span><select class="form-control" name="isAvailable">
                                         <option value="">全部</option>
                                         <option value="0">停用</option>
                                         <option value="1">启用</option>
                                        </select>
               </div>
          </div>
          

            </div><!-- /.row -->
         </div><!-- /.box-body -->
         <!--修改-->
               <div class="col-md-12" style='float:right'>
                       <div class="box-footer">
                          <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->
                          <button type="button" class="btn btn-default pull-right btn-flat flatten flatten btnColorA" id="returnAreaSearchafter" style="background:#2b94fd;margin-right:-2px !important">确定</button>
                          <button type="reset" class="btn btn-default pull-right btn-flat flatten flatten btnDefault"  style="background:#fa6e30;float:right;margin-right:20px !important">清空</button>
                        </div>
                 </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
       <div class="row" style="width: 100%; height: 2px; background: #f1f5f8"></div>
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="returnAreaTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="returnAreaList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
     
     
     

    </div><!-- /.container-fluid -->
    
    
      <div class="modal fade"  id="onreturnAreaModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定启用</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="onreturnAreaForm"> 
			       <input type="hidden"  name="returnAreaId" id="returnAreaId1">
			      <input type="hidden"  name="isAvailable" value="1">
			       <label for="inputEmail3" class=" control-label wen" id="onreturnAreaMemo"></label>
			         <div>
			            <label for="inputEmail3" class=" control-label reason">理由:</label>
			         </div> 
			          <div class="form-group">
                            <div class="col-sm-8">
                                 <textarea class="form-control textareas"   name="memo"></textarea>
                            </div>
                      </div>
			     </div>
			    
			     </form> 
			      <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="returnAreaOnFormBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"id="returnAreaOnCancelBtn">取消</button>
                </div> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     
    <div class="modal fade" id="offreturnAreaModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定停用</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="offreturnAreaForm"> 
				      <input type="hidden"  name="returnAreaId" id="returnAreaId2">
				      <input type="hidden"  name="isAvailable" value="0">
				        <label for="inputEmail3" class=" control-label wen" id="offreturnAreaMemo"></label>
				         <div>
				            <label for="inputEmail3" class=" control-label reason">理由:</label>
				         </div> 
				          <div class="form-group">
	                            
	                            <div class="col-sm-8">
	                                 <textarea class="form-control textareas"   name="memo"></textarea>
	                            </div>
	                      </div>
	                          
				   </form> 
				       <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="returnAreaOffBtn" value="确定" >
                    <button type="button" class="btn btn-default pull-right cancels"  id="returnAreaOffCancel">取消</button>
                </div>      
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    
    
    <script type="text/javascript">
     $(function(){
      require.config({paths: {"returnArea":"res/js/resource/returnArea_list"}});
		require(["returnArea"], function (returnArea){
			returnArea.init();
		});
		});
    </script>
