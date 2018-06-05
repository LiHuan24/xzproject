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
        font-size: 1.4rem;
        font-weight: 500;
        color: #414550;
        /*line-height: 15px;*/
    }

    .val {
        font-size: 1.4rem;
        font-weight: 500;
        color: #a0a7af;
        /*line-height: 15px;*/
    }
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-4 control-label"><h4>新增场站</h4></label>
						</div>
					</div>
				</div>
				<div class="row hzlist">
                    <div class="col-md-12">
                            <form name="parkAddForm">
                            <input name="isPloygon" type="hidden" value='${isPloygon}'/>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;名称：</label>
                                <div class="col-sm-5">
                                    <input class="form-control val" name="parkName" maxlength="20"/>
                                </div>
                                <div><span id="parkNameAdd"></span></div>
                            </div>
                           
                           <div class="form-group col-md-6"> <label class="col-sm-4 col-xs-4 control-label key"><span></span>&nbsp;&nbsp;加盟商：</label>
				                <div class="col-sm-5">
									<input type="hidden"  id="franchiseeId" name="franchiseeId" value = ""/>
									<input type="text" class="form-control val"  name="franchiseeName" readonly="readonly"/>
									<input type="button" class="btn btn-info"  id="relateFranchiseeAdd" value="选择">
								</div>
            				</div>
                           
                            <div class="form-group clearfix col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;所在地区：</label>
                                <div class="col-sm-5">
                        <select class="col-sm-5" id="addrRegion1Id"  name="addrRegion1Id" value="${park.addrRegion1Id}" onchange="getResultCity(this.value)" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
                        <#list plist as province>
                        <option <#if park.addrRegion1Id==province.regionId>selected</#if> value="${province.regionId}" >
                        ${province.regionName}
                        </option>
                        </#list>
                        </select>


                        <span id="itemcity">
                        <#if park.addrRegion2Id!=0&&park.addrRegion2Id!=null>
                         <select class="col-sm-5" name="addrRegion2Id" style="height:34px;border: 1px solid #ccc;margin-right:5px;" onchange="getResultCountry(this.value)">
                        <#list plists2 as pr>
                        <option <#if park.addrRegion2Id==pr.regionId>selected</#if> value="${pr.regionId}" >
                        ${pr.regionName}
                        </option>
                        </#list>
                        </select>
                        </#if>
                        </span>

                       <span id="countrycity">
                        <#if park.addrRegion3Id!=0&&park.addrRegion3Id!=null>
                         <select class="col-sm-5" name="addrRegion3Id" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
                        <#list plists3 as pr>
                        <option <#if park.addrRegion3Id==pr.regionId>selected</#if> value="${pr.regionId}" >
                        ${pr.regionName}
                        </option>
                        </#list>
                        </select>
                        </#if>
                        </span>


                        <span id="itemarea">

                        </span>
                        <div class="col-sm-5"><span id="addrRegion1IdAdd"></span></div>
                        </div>

                            </div>
                            
                           <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
                                <div class="col-sm-5">
                                    <select name="cityId" class="form-control val">
                                     <option value="" >
                                        	      请选择
                                         </option>
                                     <#list cities as citie>
                                         <option value="${citie.dataDictItemId}" >
                                                ${citie.itemValue}
                                         </option>
                                     </#list>
                                    </select>
                                </div>
                            </div>  
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;片区：</label>
                                <div class="col-sm-5">
                                    <select name="regionId" class="form-control val">
                                     <option value="">请选择</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;详细街道</label>
                                    <div class="col-sm-5">
                                        <input class="form-control val" name="addrStreet" />
                                    </div>
                                    <div><span id="addrStreetAdd"></span>

                                    </div>

                            </div>

                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;坐标：</label>
                                <div class="col-sm-5">
                                    <input type="hidden" name="ploygonPoints"/>
                                    <input   style="width:50px;height:34px;"  name="longitude" />N
                                    <input   style="width:50px;height:34px;"  name="latitude"  />E
                                    <button type="button" id="searchGC" class="btn btn-default pull-right " >地图选点</button>
                                </div>
                                <div><span id="longitudeAdd"></span><span id="latitudeAdd"></span></div>

                            </div>
                            <#if isPloygon??>
                                <#if isPloygon=="0">
                                  <div class="form-group col-md-6">
                                     <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;电子围栏半径：</label>
                                        <div class="col-sm-5" style="position:relative;">
                                         	<input class="form-control val" name="electronicFenceRadius"  maxlength="4"/><label style="position:absolute;right:25px;top:0;">米</label>
                                         </div>
                                       <div class="col-sm-5"><span id="electronicFenceRadiusAdd"></span></div>
                                    </div>
                                   </#if>
                             </#if>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;类别：</label>
                                <div class="col-sm-5">
                                    <select name="parkType" class="form-control val">
                                        <option value="2" >使用类</option>
                                        <option value="1" >管理类</option>
                                    </select>
                                </div>
                                <div><span id="parkTypeAdd"></span></div>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;是否开放：</label>
                                <div class="col-sm-5">
                                    <select name="isPublic" class="form-control val">
                                        <option value="1" >开放</option>
                                        <option value="0" >不开放</option>
                                    </select>
                                </div>
                                <div><span id="isPublicAdd"></span></div>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key">&nbsp;&nbsp;样式：</label>
                                <div class="col-sm-5">
                                    <select name="styleId" class="form-control val">
                                         <option value="">请选择</option>
                                         <#list styles as stylep>
                                         <option value="${stylep.dataDictItemId}" >
                                                ${stylep.itemValue}
                                         </option>
                                         </#list>
                                    </select>
                                </div>
                                <div><span id="styleIdAdd"></span></div>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key">&nbsp;&nbsp;车位数：</label>
                                <div class="col-sm-5">
                                 <input class="form-control val"  name="parkingSpaceNumber"  maxlength="4">
                                </div>
                                <div><span id="parkingSpaceNumberAdd"></span></div>
                            </div>
                            <!--
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;车辆数：</label>
                                <div class="col-sm-5">
                                 <input class="form-control val" name="carNumber"  maxlength="4">
                                </div>
                                <div class="col-sm-5"><span id="carNumberAdd"></span></div>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;电桩数：</label>
                                <div class="col-sm-5">
                                 <input class="form-control val" name="chargerNumber" >
                                </div>
                                <div class="col-sm-5"><span id="chargerNumberAdd"></span></div>
                            </div>
                            -->
                            <div class="form-group col-md-6" id="supportedServicesPark">
                                <label class="col-sm-4 control-label key">&nbsp;&nbsp;服务：</label>
                                <div class="col-sm-6 val">
                                <label class="checkbox-inline">
                                <input type="checkbox"     class="butcheck" value="快充"> 快充
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" class="butcheck" value="慢充"> 慢充
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" class="butcheck" value="清洗"> 清洗
                                </label>
                                <label class="checkbox-inline">
                                    <input type="checkbox" class="butcheck" value="维修"> 维修
                                </label>
                                </div>
                                 <input type="hidden" name="supportedServices" id="supportedServices">
                                 <div><span name="supportedServices"></span></div>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key">&nbsp;&nbsp;所属：</label>
                                <div class="col-sm-6">
                                    <select name="ownerType" class="form-control val">
                                        <option value="">请选择</option>
                                        <option value="1"  >自有</option>
                                        <option value="2"  >租用</option>
                                    </select>
                                </div>
                                <div><span id="ownerTypeAdd"></span></div>
                            </div>
                            <div class="form-group col-md-12"  id="parkCompanyRelIds">
                                <label class="col-sm-2 control-label key"><span>*</span>&nbsp;&nbsp;所属集团：</label>
                                <div class="col-sm-9 val">
                                    <#list companyList as company>
                                        <label class="checkbox-inline">
                                            <input type="checkbox"  class="butcheck" value="${company.companyId}"> ${company.companyName}
                                        </label>
                                    </#list>
                                </div>
                                 <input type="hidden" name="companyIds" id="parkCompanyIds" value="">
                            </div>

                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;图片：</label>
                                <input name="parkPicUrl1" type="hidden"/>
                                <div class="col-sm-5">
<!--                                     <img width="320" height="200" name="parkPicUrlImg"/>
 -->                                    <button type="button" id="addParkPhotoButton" class="btn btn-default">上传图片</button>
                                    <div><span id="parkPicUrl1Add"></span></div>
                                </div>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key">&nbsp;&nbsp;场站电费：</label>
                                <div class="col-sm-5">
                                 <input class="form-control val" name="electricPrice"   maxlength="4">
                                </div>
                                <div>元/度<span id="electricPriceAdd"></span></div>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key">&nbsp;&nbsp;场站租金：</label>
                                <div class="col-sm-5">
                                 <input class="form-control val" name="parkRent">
                                </div><span>元/月</span>
                                <div><span id="parkRentAdd" ></span></div>
                            </div>
                            <!-- <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key">&nbsp;&nbsp;交租日期：</label>
                                <div class="col-sm-5">
                                 <input class="form-control val" name="payRentDayOfMonth"  >注：填写交租日期，如是10月5日，填写5即可。
                                </div>
                                <div><span id="payRentDayOfMonthAdd"></span></div>
                            </div> -->
                             

                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;节假日营业：</label>
                                <div class="col-sm-5 val">
                                    <label><input name="isBusinessFestival" type="radio" value="1" checked/>是 </label>
                                    <label><input name="isBusinessFestival" type="radio" value="0" />否 </label>
                                </div>
                            </div>


                             <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;取车附加费用：</label>
                                <div class="col-sm-5">
                                    <input class="form-control val" name="serviceFeeGet" maxlength="13"  placeholder="0"/>
                                </div><span>元</span>
                                <div><span id="serviceFeeGetAdd"></span></div>
                            </div>
                             <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;还车附加费用：</label>
                                <div class="col-sm-5">
                                    <input class="form-control val" name="serviceFeeBack" maxlength="13" placeholder="0"/>
                                </div><span>元</span>
                                <div><span id="serviceFeeBackAdd"></span></div>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;营业时间：</label>
                                <div class="col-sm-5">
                                	<input class="business_hours  form-control val" name="businessHours"  readonly="readonly" value="00:00 - 23:59"/>
                                </div>
                                <div class="col-sm-2">
                                	<button type="button"  class="business_hours  form-control val" name="businessHours" style="width:50px;height:35px;">修改</button>
								</div>
                                <div><span id="businessHoursAdd"></span></div>

                            </div>
                    </form>

                    </div>
        		</div><!-- /.row -->
        		<div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="addclosePark" class="btn btn-default pull-right btnDefault" >
                              关闭
                        </button>
                        <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="addPark" class="btn btn-default pull-right btnColorA" >
                              保存
                        </button>
                    </div>
                </div>
</div>

 <!-- 弹出框 获取坐标地址 操作-->
    <div class="modal fade" id="parkAddSearchGCModal" style="height:800px;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">获取坐标</h4>
                </div>
                <div class="modal-body" style="max-height:800px;">
                       <form class="form-horizontal" name="parkAddSearchGCForm"> 
                     <div id="searchGCMap">
                            
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
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
                       <input type="button" class="btn btn-default pull-right" id="parkAddSearchGCBtn" value="保存">
                   </div>              
                   </form> 
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <!-- 加盟商 -->
<div class="modal" id="relateFranchiseeModel">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	   	<form name="relateFranchiseeSerachForm">
		      <div class="with-border">
		       <div class="box-tools pull-right">
		       </div><!-- /.box-tools -->
		          <div class="row pull-down-menu-car col-md-11" style="margin-left: 1px; background: #f1f5f8;width: 748px;">
		              <div class="col-md-3">
		                  <div class="frombg">
		                      <span style="width: 45%">加盟商编号</span>
		                      <input class="form-control" name="franchiseeNo" id="franchiseeNo" value="">
		                  </div>
		              </div>
		              
		          </div>
		       </div><!-- /.box-header -->

		       <div class="box-body">
		        </div><!-- /.box-body -->
         		<!--修改-->
              <div class="col-md-12">
                    <div class="box-footer">
                     <!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->
                     <button type="button" class="btn btn-default pull-right btn-flat flatten btncolora" id="relateFranchiseeSearch" style="background:#2b94fd;margin-right:330px !important;margin-top: -48px;">确定</button>
                      <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb" style="background:#fa6e30;float:right;margin-right:435px !important;margin-top: -48px;">清空</button>
                  </div><!-- /.box-footer -->
    		 </div>
        	</form>
	       <div class="col-xs-14">
		       <!--定义操作列按钮模板-->
		       <script id="relateFranchiseeBtnTplAdd" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body">
			         <table id="relateFranchiseeListAdd" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>
    
    
 <!-- 弹出框-->
 <!-- 弹出框 画多边形 操作-->
    <div class="modal fade" id="parkAddPloygonPointsModal" style="height:600px;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">获取坐标</h4>
                </div>
               
                <div class="modal-body" style="max-height:800px;">
                     	<div id="ploygonPointsMap">
                            
                        </div>
                        <form class="form-horizontal"  name="ploygonPointsSearchForm">
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
                       <input type="button" class="btn btn-default pull-right" id="parkAddPloygonPointsBtn" value="保存">
                   	<p style="float:left">结束画图时，双击鼠标左键</p>
	                <input type="button"  class="btn btn-default pull-right" value="删除多边形"  id="deletePloygonPointsBtn"/>
                  </div>              
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<!-- 弹出框-->
    <div class="modal fade" id="parkPhotoAddModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">场站上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="parkphotoForm" >
					<div class="form-group" style = "display: none">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="parkPicUrl1" readonly>
<!--                                <input type="text" class="form-control" placeholder="" name="parkPicUrl2" readonly>
                               <input type="text" class="form-control" placeholder="" name="parkPicUrl3" readonly>
                               <input type="text" class="form-control" placeholder="" name="parkPicUrl4" readonly>
                               <input type="text" class="form-control" placeholder="" name="parkPicUrl5" readonly> -->
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val">场站图片：</label>
                           <div class="col-md-8">
                                <div id="parkFineUploader"><span name="parkErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right"
							id="addParkPhotoBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
  $(function () {
      require.config({paths: {"parkAdd":"res/js/resource/park_add"}});
		require(["parkAdd"], function (parkAdd){
			parkAdd.init();
		});
		 var config=new Object();
		config.uploadId="parkFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "park_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "parkphotoForm";
		//文件回显inputName
		config.inputName = "parkPicUrl1";
		//错误提示span标签name
		config.spanName = "parkErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
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
