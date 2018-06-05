<meta charset="utf-8">
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
							<label class="col-sm-6 control-label"><h4>场站详情</h4></label>
						</div>
					</div>
				</div>
				<div class="row hzlist">
					<div class="">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;编号：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							    ${park.parkNo}
							    </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;名称：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							    ${park.parkName}
							    </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;城市：</label>
							<div class="col-sm-7">
							<label class="control-label val">
								${park.cityName}
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;详细地址：</label>
							<div class="col-sm-7">
							<label class="control-label val">
								${park.addrStreet}
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;加盟商：</label>
							<div class="col-sm-7">
							<label class="control-label val">
								${franchisee.franchiseeName}
								</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;坐标：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${park.longitude}&nbsp;N&nbsp;&nbsp;&nbsp;&nbsp;
							${park.latitude}&nbsp;E
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;电子围栏半径：</label>
							<div class="col-sm-7">
							<label class="control-label val">
								${park.electronicFenceRadius}米
								</label>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp; 类别：</label>
							<div class="col-sm-7">
							<label class="control-label val">
									<#if park.parkType=1>
									管理类
									<#else>
									使用类
									</#if>
								</label>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;是否开放：</label>
							<div class="col-sm-7">
							<label class="control-label val">
								<#if park.isPublic=1>
									开放
									<#else>
									不开放
									</#if>
									</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;样式：</label>
							<div class="col-sm-7">
							<label class="control-label val">
								${park.style}
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;车位数：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							 ${park.parkingSpaceNumber}
							 </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;电桩数：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							 ${park.chargerNumber}
							 </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;车辆数：</label>
							<div class="col-sm-7">
							<label class="control-label val">
						     ${park.carNumber}
						     </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;服务：</label>
							<div class="col-sm-7">
							<label class="control-label val">
						     ${park.supportedServices}
						     </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;所属：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							<#if park.ownerType=1>
								自有
							<#else>
								租用
							</#if>
							</label>
							</div>
						</div>
						<#if (parkCompanyRelList?size>0)>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;所属集团：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							<#list parkCompanyRelList as company>
							${company.companyName}&nbsp;&nbsp;&nbsp;&nbsp;
							</#list>
							</label>
							</div>
						</div>
						</#if>
						<#if park.isAvailable==1>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;状态：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							 启用
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;启用时间：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${park.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>
						<#else>
							<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;状态：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							 停用
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;停用时间：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${park.availableUpdateTime?string('yyyy-MM-dd HH:mm:ss')}
							</label>
							</div>
						</div>
						</#if>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;场站电费：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							 ${park.electricPrice}元/度
							 </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;场站租金：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							 ${park.parkRent}元/月
							 </label>
							</div>
						</div>
						<#-- <div class="form-group col-md-6">
							<label class="col-sm-4 control-label"><span></span>&nbsp;&nbsp;交租日期：</label>
							<div class="col-sm-7">
							<label class="control-label">
							${park.payRentDayOfMonth}
							</label>
							</div>
						</div>	 -->
                		<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;创建日期：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${park.createTime?string('yyyy-MM-dd')}
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;更新日期：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${park.updateTime?string('yyyy-MM-dd')}
							</label>
							</div>
						</div>
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;取车附加费用：</label>
							<div class="col-sm-7">
							<label class="control-label val">
								${park.serviceFeeGet}元
								 </label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;还车附加费用</label>
							<div class="col-sm-7">
							<label class="control-label val">
							    ${park.serviceFeeBack}元
							    </label>
							</div>
						</div>

						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;营业时间：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							${park.businessHours}
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;节假日营业：</label>
							<div class="col-sm-7">
							<label class="control-label val">
							<#if park.isBusinessFestival==1>
							是
							</#if>
							<#if park.isBusinessFestival==0>
							否
							</#if>
							</label>
							</div>
						</div>

						<#-- <div class="form-group col-md-12">
						
                			<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;图片：</label>
                			<div class="col-sm-8">
                			<label class="control-label val">
                				<img src="${imagePath!''}/${park.parkPicUrl1}" width="320" height="200"/>
                				</label>
                			</div>
                		</div>-->

                		<#list picUrls  as urls>
                		<div class="form-group col-md-12">
                		<hr>
                			<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;图片：</label>
                			<div class="col-sm-8">
                			<label class="control-label val">
                				<img src="${imagePath!''}/${urls}" width="320" height="200"/>
                				</label>
                			</div>
                		</div>
				        </#list>
					</div>
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-7" style="margin-bottom:20px;">

                    <button type="button" id="closepark" class="btn btn-default pull-right btncolora" style="width: 95px; height: 32px; line-height: 25px; padding-left: 15px; background: #2b94fd">
                            关闭
                    </button>

                    </div>
                </div>
</div>
<script type="text/javascript">
    $(function () {
      require.config({paths: {"park":"res/js/resource/park_list"}});
		require(["park"], function (park){
			park.init();
		});
        $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>
