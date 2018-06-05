<meta charset="utf-8">
<body>
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
    .text1{
    	border: 1px #EEEE solid;
    }
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-12 control-label"><h4>文章详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
	 				<form name="advertViewForm" class="form-horizontal">
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;文章类型：</label>
							<div class="col-sm-6">
							   <label class="control-label val">
							    <#if advert.advertType==1>
							 	健身文章
							   </#if>
							   <#if advert.advertType==2>
							 	资讯文章
							   </#if>
							   <#if advert.advertType==3>
							 	  文章文章
							   </#if>
							</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;文章名称：</label>
							<div class="col-sm-6">
							   <label class="control-label val">${advert.advertName}</label>
							</div>
						</div>
						<#if advert.advertType==1>
						<div class="form-group col-md-6 synopsisNso">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;文章简介：</label>
							<div class="col-sm-7">
							<div class="col-sm-10 text1">
											${advert.synopsis}
								</div>
							</div>
						</div>
						</#if>
						<#if advert.censorMemo!=null&&advert.censorMemo!=''>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label reason key"><span></span>&nbsp;&nbsp;审核备注：</label>
							<div class="col-sm-6">
								<label class="control-label val">
									${advert.censorMemo}
								</label>
							</div>
						</div>
						</#if>
                        <div class="form-group col-md-6">
                            <label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;审核状态：</label>
                            <div class="col-sm-6">
							     <label class="control-label val">
	                                <#if advert.censorStatus==0>未审核
	                                <#elseif advert.censorStatus==1>已审核
	                                <#elseif advert.censorStatus==2>待审核
	                                <#elseif advert.censorStatus==3>未通过
	                                </#if>
                                </label>
                            </div>
                        </div>
                        
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${advert.createTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span></span>&nbsp;&nbsp;更新时间：</label>
							<div class="col-sm-6">
							    <label class="control-label val">${advert.updateTime?string('yyyy-MM-dd HH:mm:ss')}</label>
							</div>
						</div>
						<#if advert.advertPicUrl!=null&&advert.advertPicUrl!=''>
						<div class="form-group col-md-12">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;文章图片：</label>
							<div class="col-sm-6">
							         <label class="control-label val">
                				<img src="${imagePath!''}/${advert.advertPicUrl}" width="320" height="190"/>
                				</label>
                			</div>
						</div>
						</#if>
						<#if advert.text1!=null&&advert.text1!=''>
						<div class="form-group col-md-12">
							<label class="col-sm-1 control-label key"><span></span>&nbsp;&nbsp;内容：</label>
							<div class="col-sm-10 text1">
										${advert.text1}
							</div>
							<label class="col-sm-2 control-label key"></label>
						</div>
						</#if>
  					</form>
					</div>
        		</div><!-- /.row -->
                <div class="form-group col-md-12">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button id="closeAdvertView" class="btn btn-default pull-right navbar-btn btn-flat advert-operate-close btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
                                	关闭
                        </button>
                    </div>
                </div>	
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"advert":"res/js/advert/advert_list"}});
		require(["advert"], function (advert){
			advert.init();
		});  
	});
</script>
</body>
