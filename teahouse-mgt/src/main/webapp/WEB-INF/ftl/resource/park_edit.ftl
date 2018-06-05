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
        /*line-height: 15px;*/
    }

    .val {
        font-size: 1.5rem;
        font-weight: 500;
        color: #a0a7af;
        /*line-height: 15px;*/
    }
    .control-label{
   white-space: nowrap;
    }
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-12 control-label key"><h4>场站编辑</h4></label>
						</div>
					</div>
				</div>
				<div class="row hzlist">
					<div class="col-md-12">
					    <form name="parkEditForm">

						<input name="isPloygon" type="hidden" value='${isPloygon}'/>
            	<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;编号：</label>
							<div class="col-sm-7">
							    <input class="form-control val" name="parkNo" value="${park.parkNo}" readonly style="background-color:#ffffff;"/>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;名称：</label>
							<div class="col-sm-7">
							    <input class="form-control val" name="parkName" value="${park.parkName}" maxlength="20"/>
							</div>
							<div style="margin-top:7px;"><span id="parkNameEdit"></span></div>
            </div>
						</div>

						<div class="form-group col-md-12">
              <div class="form-group col-md-6">
  							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;城市：</label>
  							<div class="col-sm-7">
  								<select name="cityId" class="form-control val">
  								 <#list cities as citie>
  									 <option <#if park.cityId==citie.dataDictItemId>selected</#if> value="${citie.dataDictItemId}" >
  				                            ${citie.itemValue}
  				                     </option>
  				                 </#list>
  								</select>
  							</div>
  						</div>
  						<div class="form-group col-md-6">
  							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;片区：</label>
  							<div class="col-sm-7">
  								<select name="regionId" class="form-control val">
  								 <#list dots as d>
  									 <option <#if park.regionId==d.dataDictItemId>selected</#if> value="${d.dataDictItemId}" >
  				                            ${d.itemValue}
  				                     </option>
  				                 </#list>
  								</select>
  							</div>
  						</div>
						</div>
				<div class="form-group clearfix col-md-12">
          <div class="form-group clearfix col-md-6">
            <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;详细地址：</label>
            <div class="col-sm-7 val">
                              <select class="col-sm-3" id="addrRegion1Id"  name="addrRegion1Id" value="${park.addrRegion1Id}" onchange="getResultCity(this.value)" style="height:34px;border: 1px solid #ccc;margin-right:5px;">
                              <#list plist as province>
                              <option <#if park.addrRegion1Id==province.regionId>selected</#if> value="${province.regionId}" >
                              ${province.regionName}
                              </option>
                              </#list>
                              </select>


                              <span id="itemcity">
                              <#if park.addrRegion2Id!=0&&park.addrRegion2Id!=null>
                               <select class="col-sm-3" name="addrRegion2Id" style="height:34px;border: 1px solid #ccc;margin-right:5px;" onchange="getResultCountry(this.value)">
                              <#list plists2 as pr>
                              <option <#if park.addrRegion2Id==pr.regionId>selected</#if> value="${pr.regionId}" >
                              ${pr.regionName}
                              </option>
                              </#list>
                              </select>
                              </#if>
                              </span>


                              <span id="itemarea">
                              <#if park.addrRegion3Id!=0&&park.addrRegion3Id!=null>
                              <select class="col-sm-3" name="addrRegion3Id" style="height:34px;border: 1px solid #ccc;">
                              <#list plists3 as p3>
                              <option
                              <#if park.addrRegion3Id==p3.regionId>selected</#if> value="${p3.regionId}" >
                              ${p3.regionName}
                              </option>
                             </#list>
                              </select>
                              </#if>
                              </span>

                              </div>
          </div>
          <div class="form-group col-md-6">
              <label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;详细街道：</label>
            <div class="col-sm-7">
              <input class="form-control val" name="addrStreet" value="${park.addrStreet}"/>
            </div>
            <div style="margin-top:7px;"><span id="addrStreetEdit"></span></div>
          </div>
           
				</div>
						<div class="form-group col-md-12">
						<div class="form-group col-md-6">
	                <label class="col-sm-4 control-label key">&nbsp;&nbsp;加盟商：</label>
	                <div class="col-sm-7">
	                    <input type="hidden"  id="franchiseeNo" name="franchiseeNo" value = ""/>
	                    <input class="form-control val" name="franchiseeName" value="${franchisee.franchiseeName}" readonly="readonly" />
	                </div>
	            </div>
              <div class="form-group col-md-6">
  							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;坐标：</label>
  							<div class="col-sm-7 val">
  								<input type="hidden" name="ploygonPoints" value="${park.ploygonPoints}"/>
  								<input   style="width:50px;height:34px;"  name="longitude" value="${park.longitude}"/>N
  								<input   style="width:50px;height:34px;"  name="latitude"  value="${park.latitude}"/>E
  								<button type="button" id="searchGCEdit" class="btn btn-default pull-right " >地图选点</button>
  							</div>
  							<div style="margin-top:7px;"><span id="longitudeEdit"></span><span id="latitudeEdit"></span></div>
  						</div>
  						 <#if isPloygon??>
                              <#if isPloygon=="0">
  							<div class="form-group col-md-6">
  								<label class="col-sm-4 control-label key"><span>*</span>电子围栏半径：</label>
  								<div class="col-sm-7">
  									<input class="form-control val" name="electronicFenceRadius" value="${park.electronicFenceRadius}"  maxlength="4"/>
  								</div>
  								<div><span>米</span></div>
  								<div><span id="electronicFenceRadiusEdit"></span></div>
  							</div>
                </#if>
                          </#if>
						</div>

						<!--<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp; 类别：</label>
							<div class="col-sm-7">
                                <#if isPloygon??>
                                    <#if isPloygon=="0">
                                    </#if>
                                </#if>
						    </div>
                        </div>--!>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp; 类别：</label>
							<div class="col-sm-7">
								<select name="parkType" class="form-control val">
									<option value="2" <#if park.parkType=2>selected="selected"</#if> >使用类</option>
									<option value="1" <#if park.parkType=1>selected="selected"</#if> >管理类</option>
								</select>
							</div>
							<div style="margin-top:7px;"><span id="parkTypeEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;是否开放：</label>
							<div class="col-sm-7 val">
							   <label class="radio-inline">
                                    <input type="radio" class="isAvailable"  name="isPublic" <#if park.isPublic=1>checked</#if>   value="1"> 是
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" class="isAvailable" name="isPublic"   <#if park.isPublic=0>checked</#if>  value="0"> 否
                                </label>
							</div>
							<div style="margin-top:7px;"><span id="isPublicEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;样式：</label>
							<div class="col-sm-7">
								<select name="styleId" class="form-control val">
									<option value="">请选择</option>
									 <#list styles as stylep>
									 <option <#if park.styleId==stylep.dataDictItemId>selected</#if> value="${stylep.dataDictItemId}" >
				                            ${stylep.itemValue}
				                     </option>
				                     </#list>
								</select>
							</div>
							<div style="margin-top:7px;"><span id="styleIdEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;车位数：</label>
							<div class="col-sm-5">
							<label class="control-label val">
							 <input class="form-control val" name="parkingSpaceNumber"  value="${park.parkingSpaceNumber}">
							</lable>
							</div>
							 <div style="margin-top:7px;">
							 <span id="ownerTypeEdit"></span>
							 <input type="button" class="btn btn-default" data-id="${park.parkNo}" id="carParkingSpaceOnFormBtn" value="车位维护"></div>

						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;电桩数：</label>
							<div class="col-sm-5">
							<label class="control-label val">
							<#if park.chargerNumber=="">
							0
							<#else>
							 ${park.chargerNumber}
							</#if>
							</lable>
							</div>
							<div><input type="button" class="btn btn-default" data-id="${park.parkNo}" id="carChargerOnFormBtn" value="电桩维护" ></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;车辆数：</label>
							<div class="col-sm-7">
						     <label class="control-label val">${park.carNumber}</lable>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;服务：</label>
							<div class="col-sm-7 val" id="supportedServicesParkEdit">
							<label class="checkbox-inline">
		                    <input type="checkbox" <#if park.supportedServices=="快充"||park.supportedServices=="快充,慢充"||park.supportedServices=="快充,慢充,清洗"||park.supportedServices=="快充,慢充,维修"||park.supportedServices=="快充,清洗"||park.supportedServices=="快充,维修"||park.supportedServices=="快充,清洗,维修"||park.supportedServices=="快充,慢充,清洗,维修">checked</#if> class="butcheck" value="快充"> 快充
							</label>
							<label class="checkbox-inline">
							<input type="checkbox" <#if park.supportedServices=="慢充"||park.supportedServices=="快充,慢充"||park.supportedServices=="慢充,清洗"||park.supportedServices=="慢充,维修"||park.supportedServices=="快充,慢充,清洗"||park.supportedServices=="慢充,清洗,维修"||park.supportedServices=="快充,慢充,清洗,维修">checked</#if>  class="butcheck" value="慢充"> 慢充
							</label>
							<label class="checkbox-inline">
							<input type="checkbox" <#if park.supportedServices=="清洗"||park.supportedServices=="快充,慢充,清洗"||park.supportedServices=="慢充,清洗,维修"||park.supportedServices=="快充,清洗"||park.supportedServices=="慢充,清洗"||park.supportedServices=="清洗,维修"||park.supportedServices=="快充,慢充,清洗,维修">checked</#if> class="butcheck" value="清洗"> 清洗
							</label>
							<label class="checkbox-inline">
							<input type="checkbox" <#if park.supportedServices=="维修"||park.supportedServices=="快充,维修"||park.supportedServices=="慢充,维修"||park.supportedServices=="快充,慢充,维修"||park.supportedServices=="清洗,维修"||park.supportedServices=="快充,慢充,清洗,维修">checked</#if> class="butcheck" value="维修"> 维修
							</label>
							</div>
							 <input type="hidden" name="supportedServices" id="supportedServicesEdit" value="${park.supportedServices}">
							 <div style="margin-top:7px;"><span name="supportedServices"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;所属：</label>
							<div class="col-sm-7">
								<select name="ownerType" class="form-control val">
									<option value="">请选择</option>
									<option value="1" <#if park.ownerType=1>selected="selected"</#if> >自有</option>
									<option value="2" <#if park.ownerType=2>selected="selected"</#if> >租用</option>
								</select>
							</div>
							<div style="margin-top:7px;"><span id="ownerTypeEdit"></span></div>
						</div>
						<div class="form-group col-md-12">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;所属集团：</label>
							<div class="col-sm-7 val" id="parkCompanyRelIdsEdit">
								<#list companyList as company>
									<label class="checkbox-inline">
		                    			<input type="checkbox"  class="butcheck" value="${company.companyId}"
		                    			<#list parkCompanyRelList as companyRel><#if companyRel.companyId==company.companyId>checked="checked"</#if></#list>
		                    			> ${company.companyName}
									</label>
								</#list>
							</div>
							 <input type="hidden" name="companyIds" id="parkCompanyIdsEdit" value="">
						</div>
						<#if park.isAvailable==1>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;状态：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							 启用
							</lable>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;启用时间：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${park.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</lable>
							</div>
						</div>
						<#else>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;状态：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							 停用
							</lable>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;停用时间：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${park.updateTime?string('yyyy-MM-dd HH:mm:ss')}
							</lable>
							</div>
						</div>
						</#if>


						<div class="form-group col-md-9">
                			<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;图片：</label>
                			<div class="col-sm-7">
<!--                 				<img src="${imagePath!''}/${park.parkPicUrl1}" width="320" height="200" name="imgEditUrl"/>
 -->                				<button type="button" class="btn btn-default" id="editParkPhotoBtn" class="btn btn-default">上传图片</button>
                				<input name="parkPicUrl1" type="hidden"/>
                			</div>
                		</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;场站电费：</label>
							<div class="col-sm-7">
							 <input class="form-control val" name="electricPrice"  value="${park.electricPrice}"  maxlength="4">
							</div>
							<div style="margin-top:7px;">元/度<span id="electricPriceEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;场站租金：</label>
							<div class="col-sm-7">
							 <input class="form-control val" name="parkRent"  value="${park.parkRent}" >
							</div>
							<div style="margin-top:7px;" class="val">元/月<span id="parkRentEdit"></span></div>
						</div>
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;交租日期：</label>
							<div class="col-sm-7">
							 <input class="form-control val" name="payRentDayOfMonth"  value="${park.payRentDayOfMonth}" >
							</div>
							<div style="margin-top:7px;"><span id="payRentDayOfMonthEdit"></span></div>
						</div> -->
                		<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;创建日期：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${park.createTime?string('yyyy-MM-dd')}
							</lable>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;更新日期：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${park.updateTime?string('yyyy-MM-dd')}
							</lable>
							</div>
						</div>
 						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;营业时间：</label>
							<div class="col-sm-4 val">
								<input class="business_hours_ed  form-control val" name="businessHours" value="${park.businessHours}"  readonly="readonly" />
							</div>
							<div class="col-sm-4">
								<button type="button"  class="business_hours_ed  form-control val" name="businessHours" style="width:50px;height:35px;">修改</button>
							</div>

							<div style="margin-top:7px;"><span id="businessHoursAdde"></span></div>

						</div>

						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;节假日营业：</label>
							<div class="col-sm-7 val">
							<#if park.isBusinessFestival==1>
							<label><input name="isBusinessFestival" type="radio" value="1"  checked />是 </label>
								<label><input name="isBusinessFestival" type="radio" value="0"/>否 </label>
							</#if>
							<#if park.isBusinessFestival==0>
							<label><input name="isBusinessFestival" type="radio" value="1" />是 </label>
								<label><input name="isBusinessFestival" type="radio" value="0" checked/>否 </label>
							</#if>

							</div>

						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;取车附加费用：</label>
							<div class="col-sm-4">
								<input class="form-control val" name="serviceFeeGet" value="${park.serviceFeeGet}"   maxlength="13"/>
							</div>
							<div style="margin-top:7px;">元<span id="serviceFeeGetAdde"></span></div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;还车附加费用：</label>
							<div class="col-sm-7">
								<input class="form-control val" name="serviceFeeBack" value="${park.serviceFeeBack}" maxlength="13"/>
							</div>
							<div style="margin-top:7px;">元<span id="serviceFeeBackAdde"></span></div>
						</div>
						</form>
					</div>
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-7" style="margin-bottom:20px;">
                    <button type="button" id="closePark" class="btn btn-default pull-right btnDefault" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                            关闭
                    </button>
                    <button type="button" id="savePark" class="btn btn-default pull-right btnColorA" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                            保存
                    </button>
                    </div>
                </div>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="editParkPhotoModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">场站上传图片</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="parkPhotoEditForm">
					<div class="form-group" style = "display: none">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="parkPicUrl1" value="${park.parkPicUrl1}" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label val">场站图片：</label>
                           <div class="col-md-8">
                                <div id="parkFineEditUploader"><span name="parkEditErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right"
							id="editParkPhotoButton" value="确定">
					</div>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
 <!-- 弹出框地图选址操作-->
    <div class="modal fade" id="parkEditSearchGCModal" style="height:800px;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">获取坐标</h4>
                </div>
                <div class="modal-body" style="max-height:800px;">
                       <form class="form-horizontal" name="parkEditSearchGCForm">
                     <div id="searchGCMapEdit">

                        </div>
                        <div class="form-group" style="margin-top:15px;">
                            <label class="col-sm-3 control-label val">&nbsp;&nbsp;要查询的地址：</label>
							<div class="col-sm-6">
							 <input class="form-control val" type="text" name="gcAddress" >
							</div>
                        </div>
                        <div class="form-group" style="margin-top:15px;">
                            <label class="col-sm-3 control-label val">&nbsp;&nbsp;坐标：</label>
							<div class="col-sm-6">
							 <input class="form-control val" name="gcPoint" readonly>
							</div>
							<button type="button" class="btn btn-default " id="searchPointEdit">查询</button>
                        </div>
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
                       <input type="button" class="btn btn-default pull-right" id="parkEditSearchGCBtn" value="保存">
                   </div>
                   </form>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
 <!-- 弹出框 画多边形 操作-->
    <div class="modal fade" id="parkEditPloygonPointsModal" style="">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">获取坐标</h4>
                </div>
                <div class="modal-body" style="max-height:800px;">
                     	<div id="ploygonPointsMapEdit">
                        </div>
                        <form class="form-horizontal"  name="ploygonPointsSearchEditForm">
		                    <div class="form-group" style="margin-top:15px;">
			                    <label class="col-sm-3 control-label val" style="margin-left:-20px;">要查询的地址：</label>
								<div class="col-sm-5">
								 <input class="form-control val"  style="margin-left:-15px;" name="gcAddress" >
								</div>
								<div style="float:left;margin-left:-10px;" class="col-sm-3"><label class="control-label val" style="margin-left:-10px;">如：深圳市南山区</label></div>
								<button type="button" class="btn btn-default " id="ploygonPointsSearchAddressEdit">查询</button>
			                </div>
		                </form>
                        <div class="modal-footer">
                       <button type="button" class="btn btn-default pull-right" data-dismiss="modal">取消</button>
                       <input type="button" class="btn btn-default pull-right" id="parkEditPloygonPointsBtn" value="保存">
                   	<p style="float:left">结束画图时，双击鼠标左键</p>
	                <input type="button"  class="btn btn-default pull-right" value="删除多边形"  id="deleteEditPloygonPointsBtn"/>
                  </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog-->
    </div><!-- /.modal -->
 <!-- 弹出框-->
<script type="text/javascript">
    $(function () {
      require.config({paths: {"parkEdit":"res/js/resource/park_edit"}});
		require(["parkEdit"], function (parkEdit){
			parkEdit.init();
		});
		/* var config=new Object();
		config.uploadId="parkFineUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）
		config.storePath = "park_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=50* 1024;
		config.formName= "parkPhotoEditForm";
		//文件回显inputName
		config.inputName = "parkPicEditUrl";
		//错误提示span标签name
		config.spanName = "parkEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		}); */
        $(".datetimepicker").datetimepicker({
            language: "zh-CN",
            autoclose: true,
            clearBtn: true,//清除按钮
            todayBtn: true,
            minuteStep: 5,
            format: "yyyy-mm-dd hh:ii:ss"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
          $('.business_hours_ed').daterangepicker({
				timePicker:true,
				format:'HH:mm',

			});
    });
</script>
<!--加载鼠标绘制工具-->
<script type="text/javascript" src="res/dep/baidu-DrawingManager/DrawingManager_min.js"></script>
<link rel="stylesheet" href="res/dep/baidu-DrawingManager/DrawingManager_min.css" />
