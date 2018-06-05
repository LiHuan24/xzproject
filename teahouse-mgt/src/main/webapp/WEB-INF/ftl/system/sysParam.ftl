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
@media only screen and (min-width:992px) and (max-width:1190px) {
.row .frombg{
	font-size: 0rem !important;
}
.frombg .form-control{
	width:120px !important;
}
}
#sysParamTools{
margin-right:8px;
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
          <#--<button type="button" class="btn btn-box-tool" data-widget="collapse">
              <i class="fa fa-minus"></i>

          </button>-->
         </div>
         <hr>
       <div class="box box-default">
        <#--<div class="box-header with-border">-->
        <div class="with-border">

  
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
        <form class="form-horizontal" name="sysParamSearchForm">
        <div class="box-body">
         <div class="row pull-down-menu col-md-11">
          <div class="col-md-4">
                <div class="frombg">
                    <span>参数名称</span><input type="text" class="form-control" name="paramName" placeholder="">
                </div>
          </div>
          <div class="col-md-4">
                <div class="frombg">
                    <span>参数键</span><input type="text" class="form-control" name="paramKey" placeholder="">
                </div>
          </div>
          <div class="col-md-4">
                <div class="frombg">
                    <span>是否可配置</span><select class="form-control" name="isConfigurable">
                                               <option value="">全部</option>
                                               <option value="1">是</option>
                                               <option value="0">否</option>
                                            </select>
                </div>
           </div>

          </div><!-- /.row -->
         </div><!-- /.box-body -->
            <!--修改-->
            <div class="col-md-12" style='float:right !important;'>
                <div class="box-footer" >
                    <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->

                    <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="sysParamSearchafter">确定</button>
                     <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault"  style="float:right;margin-right:20px !important">清空</button>
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
       <script id="sysParamTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="sysParamList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->

    <!-- 弹出框-->
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
    <div class="modal fade" id="mySysParamModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">添加/编辑系统参数</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" name="sysParamForm">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label key">参数名称：</label>
                            <div class="col-sm-8">
                                <input type="hidden" name="paramId" id="formParamId">
                                <input type="text" maxlength="30" class="form-control val" id="formParamName" name="paramName" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label key">参数键：</label>
                            <div class="col-sm-8">
                                <input type="text" maxlength="30" class="form-control val" id="formParamKey" name="paramKey" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label key">参数值：</label>
                            <div class="col-sm-8">
                                <input type="text" maxlength="500" class="form-control val" id="formParamValue" name="paramValue" placeholder="">
                            </div>
                        </div>
                         <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label key">参数描述：</label>
                            <div class="col-sm-8">
                                <input type="text" maxlength="100" class="form-control val" id="sysParamformMemo" name="memo" placeholder="">
                            </div>
                        </div>                            
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label key">是否可配置：</label>
                            <div class="col-sm-8">
                                <label class="radio-inline val">
                                    <input type="radio" name="isConfigurable" id="formIsConfigurable1" value="1" checked="checked"> 是
                                </label>
                                <label class="radio-inline val">
                                    <input type="radio" name="isConfigurable" id="formIsConfigurable2" value="0"> 否
                                </label>                                    
                            </div>
                        </div>    
                <div class="modal-footer">
                    <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" class="btn btn-default pull-right btncolorb" id="sysParamResetBtn">重置</button>
                    <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" class="btn btn-default pull-right btncolora" id="sysParamEditBtn">保存</button>
                </div>              
                    </form>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>
