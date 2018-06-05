<meta charset="utf-8">
<style>
.daterangepicker .calendar-date {
  border: 1px solid #ddd;
  padding: 4px;
  border-radius: 4px;
  background: #fff;
  display: none;
}
</style>
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
							<label class="col-sm-4 control-label key"><h4>新增还车区域</h4></label>
						</div>
					</div>
				</div>
				<div class="row hzlist">
                    <div class="col-md-12">
                            <form name="returnAreaAddForm">
                             <input name="isPloygon" type="hidden" value='${isPloygon}'/>
                             <input  type="hidden" name="addrStreet"  value=""/>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;名称：</label>
                                <div class="col-sm-5">
                                    <input class="form-control val" name="areaName" maxlength="20"/>
                                </div>
                                <div><span id="returnAreaNameAdd"></span></div>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
                                <div class="col-sm-5">
                                    <select name="cityId" class="form-control val">
                                     <#list cities as citie>
                                         <option value="${citie.dataDictItemId}" >
                                                ${citie.itemValue}
                                         </option>
                                     </#list>
                                    </select>
                                </div>
                            </div>
                      
                           

                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;坐标：</label>
                                <div class="col-sm-5">
                                    <input type="hidden" name="ploygonPoints"/>
                                    <input   style="width:50px;height:34px;"  name="longitude" />N
                                    <input   style="width:50px;height:34px;"  name="latitude"  />E
                                    <button type="button" id="searchAreaGC" class="btn btn-default pull-right " >地图选点</button>
                                </div>
                                <div><span id="longitudeAdd"></span><span id="latitudeAdd"></span></div>

                            </div>
                            
                           
                            
                            
                    </form>

                    </div>
        		</div><!-- /.row -->
        		<div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="addclosereturnArea" class="btn btn-default pull-right btncolorb" >
                              关闭
                        </button>
                        <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="addreturnArea" class="btn btn-default pull-right btncolora" >
                              保存
                        </button>
                    </div>
                </div>
</div>

 <!-- 弹出框 获取坐标地址 操作-->
    <div class="modal fade" id="returnAreaAddSearchGCModal" style="height:800px;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">获取坐标</h4>
                </div>
                <div class="modal-body" style="max-height:800px;">
                       <form class="form-horizontal" name="returnAreaAddSearchGCForm"> 
                     <div id="searchGCMapArea">
                            
                        </div>
                        <div class="form-group" style="margin-top:15px;">
                            <label class="col-sm-3 control-label key">&nbsp;&nbsp;要查询的地址：</label>
							<div class="col-sm-6">
							 <input class="form-control val" type="text" name="gcAddress" >
							</div>
                        </div>
                        <div class="form-group" style="margin-top:15px;">
                            <label class="col-sm-3 control-label key">&nbsp;&nbsp;坐标：</label>
							<div class="col-sm-6">
							 <input class="form-control val" name="gcPoint" readonly>
							</div>
							<button type="button" class="btn btn-default " id="searchPoint">查询</button>
                        </div>
                       
                        <div class="modal-footer">
                       <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
                       <input style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" class="btn btn-default pull-right" id="returnAreaAddSearchGCBtn" value="保存">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
 <!-- 弹出框-->
 <!-- 弹出框 画多边形 操作-->
    <div class="modal fade" id="returnAreaAddPloygonPointsModal" style="height:600px;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">获取坐标</h4>
                </div>
               
                <div class="modal-body" style="max-height:800px;">
                     	<div id="AreaploygonPointsMap">
                            
                        </div>
                        <form class="form-horizontal"  name="AreaploygonPointsSearchForm">
		                    <div class="form-group" style="margin-top:15px;">
			                    <label class="col-sm-3 control-label key" style="margin-left:-20px;">要查询的地址：</label>
								<div class="col-sm-5">
								 <input class="form-control val"  style="margin-left:-15px;" name="gcAddress" >
								</div>
								<div style="float:left;margin-left:-10px;" class="col-sm-3"><label class="control-label key" style="margin-left:-10px;">如：深圳市南山区</label></div>
								<button type="button" class="btn btn-default " id="ploygonPointsSearchAddress">查询</button>
			                </div>
		                </form>
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
                       <input type="button" class="btn btn-default pull-right" id="returnAreaAddPloygonPointsBtn" value="保存">
                   	<p style="float:left">结束画图时，双击鼠标左键</p>
	                <input type="button"  class="btn btn-default pull-right" value="删除多边形"  id="deletePloygonPointsBtn"/>
                  </div>              
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

<script type="text/javascript">
  $(function () {
      require.config({paths: {"returnAreaAdd":"res/js/resource/reaturnArea_add"}});
		require(["returnAreaAdd"], function (returnAreaAdd){
			returnAreaAdd.init();
		});
		
       $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            clearBtn: true,//清除按钮
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
        $('.business_hours').daterangepicker({
				timePicker:true,
				format:'HH:mm',
				
			});
    });
</script>
<!--加载鼠标绘制工具-->
<script type="text/javascript" src="res/dep/baidu-DrawingManager/DrawingManager_min.js"></script>
<link rel="stylesheet" href="res/dep/baidu-DrawingManager/DrawingManager_min.css" />
