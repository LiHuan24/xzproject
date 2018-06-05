<meta charset="utf-8">
<style>
.frombg .form-control{
	width:50% !important;
}
@media only screen and (max-width:768px) {
.row .sorting_disabled{
font-size:1.1rem !important;
white-space: nowrap;
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
          <#--<button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>-->
          <#--</button>-->
         </div>
         <hr>
       <div class="box box-default">
        <div class="with-border">
     
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
         <form class="form-horizontal" name="sysPermissionSearchForm">
        <div class="box-body">
         <div class="row pull-down-menu col-md-11">
          <div class="col-md-4">
                <div class="frombg">
                    <span>权限名称</span><input type="text" class="form-control" id="permName" name="permName" placeholder="">
                </div>
          </div>
          <div class="col-md-4">
                <div class="frombg">
                    <span>模块名称</span> <input type="text" class="form-control" id="moduleName" name="moduleName" placeholder="">
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

         </div><!-- /.box-body -->
         </div>
         <!--修改-->
                 <div class="col-md-12" style='float:right !important;'>
                         <div class="box-footer">
                            <!-- <button type="submit" class="btn btn-default pull-right">Cancel</button> -->

                            <button type="button" class="btn btn-default pull-right btn-flat flatten btnColorA" id="sysPermissionSearchafter" style="background:#2b94fd;margin-right:-2px !important">确定</button>
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
       <script id="sysPermissionTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="sysPermissionList" class="table table-hover" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
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
    <div class="modal fade" id="mySysPermissionModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">添加/编辑权限管理</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" name="sysPermissionForm">
                        <div class="form-group">
                            <label for="formModuleName" class="col-sm-3 control-label key">模快名称：</label>
                            <div class="col-sm-8">
                                <input type="hidden" name="permId" id="permId">
                                <input type="text" class="form-control val" id="formModuleName" name="moduleName" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="formPermName" class="col-sm-3 control-label key">权限名称：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control val" id="formPermName" name="permName" placeholder="">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label key">权限类型：</label>
                            <div class="col-sm-8 val">
                                <label class="radio-inline">
                                    <input type="radio" name="permType" id="formPermType1" value="0"> 访问路径url
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="permType" id="formPermType2" value="1"> 页面标签
                                </label>                                    
                            </div>
                        </div>    
                        <div class="form-group">
                            <label for="formPermResource" class="col-sm-3 control-label key">权限资源：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control val" id="formPermResource" name="permResource" placeholder="">
                            </div>
                        </div>                             
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label key">是否是菜单：</label>
                            <div class="col-sm-8 val">
                                <label class="radio-inline">
                                    <input type="radio" name="isMenu" id="sysPermissionformIsMenu1" value="1"> 是
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="isMenu" id="sysPermissionformIsMenu2" value="0"> 否
                                </label>                                    
                            </div>
                        </div>
                        <!-- 是否是菜单-->
                        <div id="sysPermissionDivShow">
                            <div class="form-group">
                                <label for="inputPassword3" class="col-sm-3 control-label key">是否显示：</label>
                                <div class="col-sm-8">
                                    <label class="radio-inline">
                                        <input type="radio" name="isMenuShow" id="sysPermissionformIsMenuShow1" value="1"> 是
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="isMenuShow" id="sysPermissionformIsMenuShow2" value="0"> 否
                                    </label>                                    
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="formMenuName" class="col-sm-3 control-label key">菜单名称：</label>
                                <div class="col-sm-8">
                                    <input type="text" class="form-control val" id="formMenuName" name="menuName" placeholder="">
                                </div>
                            </div>
                         </div>
                         <div class="form-group">
                            <label for="formLevel" class="col-sm-3 control-label key">层级：</label>
                            <div class="col-sm-8">
                                <input type="number" class="form-control val" id="formLevel" name="level" placeholder="">
                            </div>
                        </div>
                         <div class="form-group">
                            <label for="formParentName" class="col-sm-3 control-label key">父节点：</label>
                             <div class="col-sm-8" id="sysPermissionTreeSel" style="">
                               
                            </div>
                            <input type="hidden" id="formParentId" name="parentId">
                            
                        </div>
                          <div class="form-group">
                            <label for="formPermResource" class="col-sm-3 control-label key">排序：</label>
                            <div class="col-sm-8">
                                <input type="number" class="form-control val" id="formRanking" name="ranking" placeholder="">
                            </div>
                        </div>

                         <div class="form-group">
                            <label for="formMemo" class="col-sm-3 control-label key">备注：</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control val" id="sysPermissionformMemo" name="memo" placeholder="">
                            </div>
                        </div>                            
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label key">是否可用：</label>
                            <div class="col-sm-8 val">
                                <label class="radio-inline">
                                    <input type="radio" name="isAvailable" id="sysPermissionformIsAvailable1" value="1"> 是
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="isAvailable" id="sysPermissionformIsAvailable2" value="0"> 否
                                </label>                                    
                            </div>
                        </div>    
                <div class="modal-footer">
                    <button type="button" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" class="btn btn-default pull-right btncolorb"  id="sysPermissionResetBtn">重置</button>
                    <button type="button" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" class="btn btn-default pull-right btncolora"  id="sysPermissionEditBtn">保存</button>
                </div>              
                    </form>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script type="text/javascript" src="${basePath!'' }/res/js/system/main.js"></script>
