<meta charset="utf-8">

<style>
.frombg .form-control{
	width:50% !important;
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
         <#-- <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>-->
         </div>
         <hr>
       <div class="box box-default">
        <div class="with-border">
  
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
        <form class="form-horizontal" name="sysRegionSearchForm">
        <div class="box-body">
         <div class="row pull-down-menu col-md-11">
          <div class="col-md-4">
                <div class="frombg">
                    <span>地区名称</span><input type="text" class="form-control" name="regionName" placeholder="">
                </div>
          </div>
          <div class="col-md-4">
                <div class="frombg">
                    <span>邮政编码</span><input type="text" class="form-control" name="postCode" placeholder="">
                </div>
          </div>
            <div class="col-md-4">
                  <div class="frombg">
                      <span>是否可用</span><select class="form-control" name="isAvailable">
                                              <option value="">全部</option>
                                              <option value="1">可用</option>
                                              <option value="0">不可用</option>
                                             </select>
                  </div>
            </div>

        </div> </div>
        <!-- /.box-body -->
<!--修改-->
            <div class="col-md-12" style='float:right !important;'>
                  <div class="box-footer">
                       <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->

                       <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="sysRegionSearchafter" style="background:#2b94fd;margin-right:-2px !important">确定</button>
                         <button type="reset" class="btn btn-default pull-right btn-flat flatten btnDefault"  style="background:#fa6e30;float:right;margin-right:20px !important">清空</button>
                  </div>

         </form>
        <!-- /.box-footer -->
        <!-- /.box-body -->
       </div>
       <!-- /.box -->
       <!-- </div> -->
      </div>
     </div>
      <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="sysRegionTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
          <div class="row" style="width: 100%; height: 2px; background: #f1f5f8"></div>
       <div class="box">
        <div class="box-body">
         <table id="sysRegionList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->

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
        <div class="modal fade" id="mySysRegionModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">添加/编辑地区管理</h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" name="sysRegionForm">
                            <div class="form-group">
                                <label for="inputEmail3" class="col-sm-3 control-label key">地区名称：</label>
                                <div class="col-sm-8">
                                    <input type="hidden" name="regionId" id="formRegionId">
                                    <input type="text" maxlength="40" class="form-control val" id="formRegionName" name="regionName" placeholder="">
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="inputEmail3" class="col-sm-3 control-label key">邮政编码：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control val" id="formPostCode" name="postCode" placeholder="">
                                </div>
                            </div>  
                            <div class="form-group">
                                <label for="inputEmail3" class="col-sm-3 control-label key">级别：</label>
                                <div class="col-sm-8">
                                    <input type="number" class="form-control val" id="formLevel" name="level" placeholder="">
                                </div>
                            </div>                           
                             <div class="form-group">
                                <label for="formParentName" class="col-sm-3 control-label key">父级地区：</label>
                                 <div class="col-sm-8" id="sysRegionTreeSel" style="">
                                   
                                </div>
                                <input type="hidden" id="formRegionParentId" name="parentId">
                            
                            </div>
                            <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label key">是否可用：</label>
                            <div class="col-sm-8 val">
                                <label class="radio-inline">
                                    <input type="radio" class="isAvailable" name="isAvailable" id="sysRegionformIsAvailable1" value="1"> 是
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" class="isAvailable" name="isAvailable" id="sysRegionformIsAvailable2" value="0"> 否
                                </label>                                    
                            </div>
                        </div> 
                    <div class="modal-footer">
                        <button type="button" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" class="btn btn-default pull-right btncolorb"  id="sysRegionResetBtn">重置</button>
                        <button type="button" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" class="btn btn-default pull-right btncolora"  id="sysRegionEditBtn">保存</button>
                    </div>              
                        </form>
                    </div>
                   
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
       
      </div>
     </div>
    </div>

    <script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>
