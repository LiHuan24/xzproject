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
							<label class="col-sm-12 control-label"><h4>课程详情</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12">
					<form name="courseEditForm">
					<input type="hidden" id="courseNo" name="courseNo" value="${course.courseNo}">
					<div class="col-md-12">
						<!-- <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;所属城市：</label>
							<div class="col-sm-7">
							    ${course.cityName}
							</div>
						</div> -->
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;分类：</label>
							<div class="col-sm-7" >
                                 ${course.sortName}
							</div>
						</div>
					</div>
					<div class="col-md-12">
						<div class="form-group col-md-6"> 
               				<label class="col-sm-4 control-label key">&nbsp;&nbsp;课程中文名称：</label>
			                 <div class="col-sm-7">
			                     ${course.chineseName}
			                 </div>
		             	</div>
		             	<div class="form-group col-md-6"> 
               				<label class="col-sm-4 control-label key">&nbsp;&nbsp;课程英文名称：</label>
			                 <div class="col-sm-7">
			                     ${course.englishName}
			                 </div>
		             	</div>
					</div>
				
					<div class="col-md-12">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;标签：</label>
							<div class="col-sm-7">
							    ${course.labelName}
							</div>
							<div class="col-sm-6"><span name="courseLabelNoAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;价格：</label>
							<div class="col-sm-7">
							    ${course.price}
							</div>
						</div>
						</div>
							
					<div class="col-md-12">
						<div class="form-group col-md-6"> 
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;课程简介：</label>
			                    <div class="col-sm-7">
			                         <textarea class="form-control val" name="synopsis" style="width: 280px;height: 100px;">
			                         ${course.synopsis}
			                         </textarea>
			                    </div>
	                		</div>
	                		<div class="form-group col-md-6"> 
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;训练效果：</label>
			                    <div class="col-sm-7">
			                         <textarea class="form-control val" name="effect" style="width: 280px;height: 100px;">
			                         ${course.effect}
			                         </textarea>
			                    </div>
	                		</div>
					</div>
					<div class="col-md-12">
						<div class="form-group col-md-6"> 
                			<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;适合人群：</label>
			                 <div class="col-sm-7">
			                      <textarea class="form-control val" name="suit" style="width: 280px;height: 100px;">
			                         ${course.suit}
			                         </textarea>
			                 </div>
		             	</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key">&nbsp;&nbsp;&nbsp;注意事项：</label>
							<div class="col-sm-7">
								 <textarea class="form-control val" name="beCareful" style="width: 280px;height: 100px;">
								 ${course.beCareful}
								 </textarea>
							</div>
						</div>
					</div>	
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
                		
					</form>	
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                        <button type="button" id="closeViewCourse" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                             	 关闭
                        </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
		$("#closeViewCourse").click(function(){
			closeTab("课程查看");
		});
	});
</script>
</body>