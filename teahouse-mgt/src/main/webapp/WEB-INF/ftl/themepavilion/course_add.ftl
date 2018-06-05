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
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-12 control-label"><h4>课程新增</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="courseAddForm">
					<div class="col-md-12">
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;所属城市：</label>
							<div class="col-sm-7">
							    <select name="cityId" class="form-control val" id="cityId">
                                     <option value="" >请选择城市</option>
                                     <#list cities as citie>
                                         <option value="${citie.dataDictItemId}" >
                                               ${citie.itemValue}
                                         </option>
                                     </#list>
                                    </select>
							</div>
							<div class="col-sm-6"><span name="cityAdd"></span></div>
						</div> -->
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;分类：</label>
							<div class="col-sm-7" >
							    <select name="courseSortNo" class="form-control val" >
							    	<option value="">请选择分类</option>
							    	<#list listCourseSort as courseSort>
                                         <option value="${courseSort.courseSortNo}" >
                                               ${courseSort.sortName}
                                         </option>
                                     </#list>
								</select> 
							</div>
							<div class="col-sm-6"><span name="courseSortNoAdd"></span></div>
						</div>
					</div>
					
					<div class="col-md-12">
						<div class="form-group col-md-6"> 
               				<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;课程中文名称：</label>
			                 <div class="col-sm-7">
			                     <input class="form-control val" name="chineseName" />
			                 </div>
		                 	<div><span name="chineseNameAdd"></span></div>
		             	</div>
		             	<div class="form-group col-md-6"> 
               				<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;课程英文名称：</label>
			                 <div class="col-sm-7">
			                     <input class="form-control val" name="englishName" />
			                 </div>
		                 	<div><span name="englishNameAdd"></span></div>
		             	</div>
					</div>
				
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;标签：</label>
							<div class="col-sm-7">
								<#list listCourseLabel as courseLabel>
							    	<input name="courseLabelNo" type="checkbox" value="${courseLabel.courseLabelNo}"/>
							    	${courseLabel.labelName}
							 	</#list>
							</div>
							<div class="col-sm-6"><span name="courseLabelNoAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;价格：</label>
							<div class="col-sm-7">
							    <input class="form-control val" name="price" />
							</div>
							<div class="col-sm-6"><span name="priceAdd"></span></div>
						</div>
						</div>
							
					<div class="col-md-12">
						<div class="form-group col-md-6"> 
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;课程简介：</label>
			                    <div class="col-sm-7">
			                         <textarea class="form-control val" name="synopsis" style="width: 280px;height: 100px;">
			                         </textarea>
			                    </div>
	                		</div>
	                		<div class="form-group col-md-6"> 
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;训练效果：</label>
			                    <div class="col-sm-7">
			                         <textarea class="form-control val" name="effect" style="width: 280px;height: 100px;">
			                         </textarea>
			                    </div>
	                		</div>
					</div>
					<div class="col-md-12">
						<div class="form-group col-md-6"> 
                			<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;适合人群：</label>
			                 <div class="col-sm-7">
			                      <textarea class="form-control val" name="suit" style="width: 280px;height: 100px;">
			                         </textarea>
			                 </div>
		             	</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;注意事项：</label>
							<div class="col-sm-7">
								 <textarea class="form-control val" name="beCareful" style="width: 280px;height: 100px;">
								 </textarea>
							</div>
						</div>
					</div>	
					<div class="col-md-12">
						<div class="form-group col-md-10 coursePictureUrl1">
			                <label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;课程图片：</label>
			                <input name="coursePictureUrl1" type="hidden" value=""/>
			                <div class="col-sm-7">
			                    <!-- <img src="" width="320" height="200" name="coursePicUrlImg" /> -->
			                    <button type="button" id="addCoursePhotoUs" class="btn btn-info btn-default">上传图片</button>
			                <div style="margin-top:7px;"><span name="coursePictureUrl1"></span></div>
			                </div>
			            </div>
					</div>
                		
					</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeAddCourse" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                        <button type="button" id="saveCourse" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                              	保存
                        </button>
                    </div>
                </div>
</div>
<!-- 弹出框-->
    <div class="modal fade" id="courseAddModalUs">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">上传图片</h4><span name="courseAddWs"></span>
                </div>
                <div class="modal-body">
                     <form class="form-horizontal"  name="coursePhotoFormUs">
					<div class="form-group">
                            <label class="col-md-3 control-label">图片URL：</label>
                            <div class="col-md-8">
                               <input type="text" class="form-control" placeholder="" name="coursePictureUrl1" readonly>
                            </div>
                     </div>
                     <div class="form-group">
                           <label class="col-md-3 control-label">课程图片：</label>
                           <div class="col-md-8">
                                <div id="courseFineUploaderUs"><span name="courseErrorInfo"></span></div>
                           </div>
                    </div>
				</form>
					<div class="modal-footer">
						<input type="button" class="btn btn-default pull-right sure"
							id="addCoursePhotoBtnUs" value="确定">
					</div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"courseAdd":"res/js/themepavilion/course_add"}});
		require(["courseAdd"], function (courseAdd){
			courseAdd.init();
		});
		
		
		/* var config=new Object();
		config.uploadId="courseFineUploaderUs";
		//storePath为业务路径，equipmentRepair_photo 设备维修记录图片
		config.storePath = "course_photo";
		config.itemLimit=1;
		config.allowedExtensions=["jpeg", "jpg", "gif", "png"];
		config.sizeLimit=1000 * 1024*1024;
		config.minSizeLimit=5* 5;
		config.formName= "coursePhotoFormUs";
		//文件回显inputName
		config.inputName = "coursePictureUrl1";
		//错误提示span标签name
		config.spanName = "courseErrorInfo";
		require.config({paths: {"upload":"res/js/resource/uploadFile"}});
		require(["upload"], function (upload){
			upload.init(config);
		});	 */
		
	});
</script>
</body>
