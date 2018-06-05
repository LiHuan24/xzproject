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
            
    .btn-new {
	    display: inline-block;
	    padding: 6px 12px;
	    margin-bottom: 0;
	    font-size: 14px;
	    font-weight: normal;
	    line-height: 1.42857143;
	    text-align: center;
	    white-space: nowrap;
	    vertical-align: middle;
	    -ms-touch-action: manipulation;
	    touch-action: manipulation;
	    cursor: pointer;
	    -webkit-user-select: none;
	    -moz-user-select: none;
	    -ms-user-select: none;
	    user-select: none;
	    background-image: none;
	    border: 1px solid transparent;
	    border-radius: 4px;
	}
    
    .btn-sm-new {
	    height: 30px;
		padding: 5px 10px;
	    font-size: 12px;
	    line-height: 1.5;
	    border-radius: 3px;
	}
	
	.btn-default-new {
	    color: #333;
	    background-color: #fff;
	    border-color: #ccc;
	}
	
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>文章编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="advertEditFrom">
					<input type="hidden" name="advertNo" value="${advert.advertNo}"/>
					<input type="hidden" name="censorStatus" value="${advert.censorStatus}"/>
					<div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;文章类型：</label>
							<div class="col-sm-7">
							 	<select name="advertType" class="form-control val" disabled="disabled">
									 <option  value="1" <#if advert.advertType==1>selected="selected"</#if> >健身文章</option>
									 <option  value="2" <#if advert.advertType==2>selected="selected"</#if> >资讯文章</option>
									<option  value="3" <#if advert.advertType==3>selected="selected"</#if> >广告文章</option>
								</select>
							</div>
							<div class="col-sm-7"><span name="advertTypeEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;文章名称：</label>
							<div class="col-sm-7">
							 <input class="form-control val" name="advertName" value="${advert.advertName}"/>
							</div>
							<div class="col-sm-7"><span name="advertNameEdit"></span></div>
						</div>
						<#if advert.advertType==1>
						<div class="form-group col-md-6 synopsisNso">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;文章简介：</label>
							<div class="col-sm-7">
							<textarea class="form-control val" name="synopsis" style="width: 330px;height: 100px;" placeholder="最多40个文字描述">${advert.synopsis}</textarea>
							</div>
							<div class="col-sm-7"><span name="synopsisEdit"></span></div>
						</div>
						
						</#if> 
						
						
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;文章图片：</label>
                				<div class="col-sm-9">
                					<img width="130" height="130" name="advertPicUrlImgEdit" src="${imagePath!''}/${advert.advertPicUrl}"/>
                					<button type="button" id="editAdvertPicUrlButton" class="btn btn-default">上传图片</button> 
                					<span name="advertPicUrlEdit"></span>
                				</div>
                			<input name="advertPicUrl" type="hidden" value="${advert.advertPicUrl}"/>
						</div>
						<div class="form-group col-md-12">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;内容：</label>
						</div>
						<div class="form-group col-md-12">
							<div class="col-sm-12">
    						<textarea name="text1" class="form-control val" id="editText1" title="Contents">${advert.text1}</textarea>
							</div>
							<div class="col-sm-7"><span name="text1Edit"></span></div>
						</div>
					   </form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-7" style="margin-bottom:20px;">
                    <button type="button" id="closeEditAdvert" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                           	关闭
                    </button> 
                    <button type="button" id="editAdvert" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
							保存
                    </button> 																				
                    </div>	
                </div>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="advertPicUrlEditModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">文章图片上传</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="advertPicUrlEditForm">
					<div class="form-group col-md-6">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="advertPicUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group col-md-6">
                           <label class="col-md-3 control-label val">文章图片：</label>
                           <div class="col-md-8">
                                <div id="advertPicUploaderEdit"><span name="advertPicEditErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="editAdvertPicBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"advertEdit":"res/js/advert/advert_edit"}});
		require(["advertEdit"], function (advertEdit){
			advertEdit.init();
		});  
		var config=new Object();
		config.uploadId="advertPicUploaderEdit";
		config.storePath = "advert_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "advertPicUrlEditForm";
		//文件回显inputName
		config.inputName = "advertPicUrl1";
		//错误提示span标签name
		config.spanName = "advertPicEditErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
		
		$('#editText1').summernote({
        	height: 300
      	});
      	
	    $('.note-editor button').removeClass("btn");
	    $('.note-editor button').addClass("btn-new");
	    $('.note-editor button').removeClass("btn-default");
	    $('.note-editor button').addClass("btn-default-new");
	    $('.note-editor button').removeClass("btn-sm");
	    $('.note-editor button').addClass("btn-sm-new");
    });
</script>
