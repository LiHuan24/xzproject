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
							<label class="col-sm-2 control-label"><h4>新增文章</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="advertAddFrom">
					<div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;文章类型：</label>
							<div class="col-sm-7">
							 <select name="advertType" class="form-control val">
									 <option  value="1" >健身文章</option>
									 <option  value="2" >资讯文章</option>
									 <option  value="3" >广告文章</option>
								</select>
							</div>
							<div class="col-sm-7"><span name="advertTypeAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;文章名称：</label>
							<div class="col-sm-7">
							 <input class="form-control val" name="advertName" />
							</div>
							<div class="col-sm-7"><span name="advertNameAdd"></span></div>
						</div>
						<div class="form-group col-md-6 synopsisNs">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;文章简介：</label>
							<div class="col-sm-7">
							<textarea class="form-control val" name="synopsis" style="width: 330px;height: 100px;" placeholder="最多40个文字描述"></textarea>
							</div>
							<div class="col-sm-7"><span name="synopsisAdd"></span></div>
						</div>
						
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span></span>&nbsp;&nbsp;外部链接：</label>
							<div class="col-sm-7">
							 <input class="form-control val" name="linkUrl" />
							</div>
							<div class="col-sm-7"><span name="linkUrlAdd"></span></div>
						</div> -->
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;文章排名：</label>
							<div class="col-sm-7">
							<input class="form-control val" name="ranking" maxlength="11" onkeyup="this.value=this.value.replace(/\D/g,'')"/>
							</div>
							<div class="col-sm-7"><span name="rankingAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
                            <label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;启动app展示：</label>
                            <div class="col-sm-3 val">
                                <label>
                                	<input name="isStartAdvert" type="radio" value="1" />是 
                                	 &nbsp;&nbsp;
                                	<input name="isStartAdvert" type="radio" value="0" checked/>否 
                                </label>
                                <div class="col-sm-7"><span name="isStartAdvertAdd"></span></div>
                            </div>
                    
                        </div> -->
						<div class="form-group col-md-6">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;文章图片：</label>
                				<div class="col-sm-9">
                					<img width="100" height="100" name="advertPicUrlImg" />&nbsp;&nbsp;
                					<button type="button" id="addAdvertPicUrlButton" class="btn btn-default">上传图片</button> 
                				<span name="advertPicUrlAdd"></span>
                				</div>
                			<input name="advertPicUrl" type="hidden"/>
						</div>
						<div class="form-group col-md-12">
							<label class="col-sm-3 control-label key"><span>*</span>&nbsp;&nbsp;内容：</label>
						</div>
						<div class="form-group col-md-12">
							<div class="col-sm-12">
    							<textarea name="text1" class="form-control val" id="addText1" title="Contents"></textarea>
							</div>
							<div style="margin-top:7px;"><span name="text1Add"></span></div>
						</div>
					   </form>	

					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-7" style="margin-bottom:20px;">
                    <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="closeAddAdvert" class="btn btn-default pull-right sure btncolorb" >
                            关闭
                    </button>
                    <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="addAdvert" class="btn btn-default pull-right sure btncolora" >
                            保存
                    </button>
                    </div>
                </div>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="advertPicUrlAddModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">文章图片上传</h4>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="advertPicUrlAddForm">
					<div class="form-group col-md-6">
                            <label class="col-md-3 control-label val">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control val" placeholder="" name="advertPicUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group col-md-6">
                           <label class="col-md-3 control-label val">文章图片：</label>
                           <div class="col-md-8">
                                <div id="advertPicUploader"><span name="advertPicErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="addAdvertPicBtn" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"advertAdd":"res/js/advert/advert_add"}});
		require(["advertAdd"], function (advertAdd){
			advertAdd.init();
		});  
		var config=new Object();
		config.uploadId="advertPicUploader";
		//storePath为业务路径，member_icon  （会员头像）member_doc （会员证件）park_photo （场站照片）car_photo （车辆照片）car_doc  （车辆证件）,advert_photo(文章图片)
		config.storePath = "advert_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=500 * 1024;
		config.minSizeLimit=10* 1024;
		config.formName= "advertPicUrlAddForm";
		//文件回显inputName
		config.inputName = "advertPicUrl1";
		//错误提示span标签name
		config.spanName = "advertPicErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});
		
		$('#addText1').summernote({
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
