<meta charset="utf-8">
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%  !important;
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
               <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool more">
              更多<img src="res/img/arrow-down.png" width="15" style="margin-left: 2px;"/>
          </button>
         </div>
         <hr>
       <div class="box box-default">
           <form class="form-horizontal" name="parkSearchForm">
        <div class="with-border">
    
         <!-- /.box-tools -->
            <div class="row pull-down-menu col-md-11">
                <div class="col-md-3">
                    <div class="frombg">
                        <span>编号</span><input type="text" class="form-control" id="parkNo" name="parkNo" placeholder="" value="${parkNo}">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="frombg">
                        <span>名称</span><input type="text" class="form-control" id="parkName" name="parkName" placeholder="">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="frombg">
                        <span>状态</span><select class="form-control" name="isAvailable">
                        <option value="">全部</option>
                        <option value="0">停用</option>
                        <option value="1">启用</option>
                    </select>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="frombg">
                        <span>服务</span><select class="form-control" name="supportedServices">
                        <option value="">全部</option>
                        <option value="快充">快充</option>
                        <option value="慢充">慢充</option>
                        <option value="清洗">清洗</option>
                        <option value="维修">维修</option>
                    </select>
                    </div>
                </div>
            </div>

            </div>
        <!-- /.box-header -->
        <div class="box-body">
            <div class="row pull-down-menu col-md-11 other searching">
          <div class="col-md-3">
                <div class="frombg">
                      <span>类别</span><select class="form-control" name="parkType">
                                        <option value="">全部</option>
                                        <option value="1">管理类</option>
                                        <option value="2">使用类</option>
                                       </select>
                </div>
          </div>
         <div class="col-md-3">
                <div class="frombg">
                    <span>是否车位不足</span><select class="form-control" name="isLotParkingSpace">
                                     		           <option value="">全部</option>
                                     		           <option value="1" <#if isLotParkingSpace!=null && isLotParkingSpace==1>selected</#if> >是</option>
                                     		           <option value="0">否</option>
                                     	          </select>
                </div>
          </div>

            </div><!-- /.row -->
         </div><!-- /.box-body -->
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
          <!--修改-->
                   <div class="col-md-12" style='float:left'>
                           <div class="box-footer">
                              <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->
                              <button type="button" class="btn btn-default pull-right btn-flat flatten flatten btnColorA" id="parkSearchafter"  style="background:#2b94fd;margin-right:-2px !important">确定</button>
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
       <script id="parkTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="parkList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
     
     
     

    </div><!-- /.container-fluid -->
    
    
      <div class="modal fade"  id="onParkModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定启用</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="onParkForm"> 
			      <input type="hidden"  name="parkNo" id="parkNo1">
			      <input type="hidden"  name="isAvailable" value="1">
			       <label for="inputEmail3" class=" control-label wen" id="onParkMemo"></label>
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
                    <input type="button" class="btn btn-default pull-right sure" id="parkOnFormBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="parkOnCancelBtn">取消</button>
                </div> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     
    <div class="modal fade" id="offParkModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定停用</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="offParkForm"> 
				      <input type="hidden"  name="parkNo" id="parkNo2">
				      <input type="hidden"  name="isAvailable" value="0">
				        <label for="inputEmail3" class=" control-label wen" id="offParkMemo"></label>
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
                    <input type="button" class="btn btn-default pull-right sure" id="parkOffBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="parkOffCancel">取消</button>
                </div>      
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    
    <div class="modal fade"  id="onViewModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定可见</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="onViewForm"> 
			      <input type="hidden"  name="parkNo" id="parkOnView">
			      <input type="hidden"  name="isView" value="1">
			       <label for="inputEmail3" class=" control-label wen" id="viewMemo"></label>
			     </div>
			    
			     </form> 
			      <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="onViewBtn" value="确定" >
                     <button type="button" class="btn btn-default pull-right cancels"  id="onViewCancelBtn">取消</button>
                </div> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <div class="modal fade"  id="noViewModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定隐藏</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="noViewForm"> 
			      <input type="hidden"  name="parkNo" id="parkNoView">
			      <input type="hidden"  name="isView" value="0">
			       <label for="inputEmail3" class=" control-label wen" id="noViewMemo"></label>
			     </div>
			    
			     </form> 
			      <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="noViewBtn" value="确定" >
                    <button type="button" class="btn btn-default pull-right cancels"  id="noViewCancelBtn">取消</button>
                </div> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <script type="text/javascript">
     $(function(){
      require.config({paths: {"park":"res/js/resource/park_list"}});
		require(["park"], function (park){
			park.init();
		});
		});
    </script>
