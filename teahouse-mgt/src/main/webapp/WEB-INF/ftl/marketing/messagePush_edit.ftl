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
							<label class="col-sm-2 control-label"><h4>消息推送编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="messagePushEditFrom">
					<input type="hidden" name="id" value="${messagePush.id}"/>
						<div class="form-group col-md-7">
							<label class="col-sm-2 control-label key"><span>*</span>&nbsp;&nbsp;推送方式：</label>
							<div class="col-sm-6">
								<select name="pushPattern" class="form-control val">
									  <option  value="1" <#if messagePush.pushPattern==1>selected="selected"</#if> >多个用户</option>
									  <option  value="2" <#if messagePush.pushPattern==2>selected="selected"</#if> >全部用户</option>
								</select>
							</div>
							<div class="col-sm-3"><span name="messagePushTypeEdit"></span></div>
						</div>
						<div class="form-group col-md-7 memberName-div"  <#if messagePush.pushPattern!=1>hidden="hidden"</#if>>
							<label class="col-sm-2 control-label key"><span>*</span>&nbsp;&nbsp;推送会员：</label>
							<div class="col-sm-5">
								<input type="hidden" class="form-control val" name="memberNo" value="${messagePush.memberNo}"/>
								<input class="form-control val" name="memberName" readonly="readonly" value="${messagePush.memberName}" />
							</div>
							<div class="col-sm-2">
								<button type="button" class="btn btn-default-new" id="memberPushEditModalBtn">会员列表</button>
							</div>
							<div class="col-sm-3"><span name="memberNameEdit"></span></div>
						</div>
						<div class="form-group col-md-7">
							<label class="col-sm-2 control-label key"><span>*</span>&nbsp;&nbsp;标题：</label>
							<div class="col-sm-6">
							 <input class="form-control val" name="title" value="${messagePush.title}" maxlength="25"/>
							</div>
							<div class="col-sm-3"><span name="titleEdit"></span></div>
						</div>
						<div class="form-group col-md-7">
							<label class="col-sm-2 control-label key"><span>*</span>&nbsp;&nbsp;内容：</label>
							<div class="col-sm-6">
    						<textarea name="content" class="form-control val" style="height: 250px;line-height: 25px"  maxlength="255">${messagePush.content}</textarea>
							</div>
							<div class="col-sm-3"><span name="contentEdit"></span></div>
						</div>
					   </form>	
						
					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-5" style="margin-bottom:20px;">
                    <button type="button" id="closeEditMessagePush" class="btn btn-default pull-right sure btncolorb" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                            关闭
                    </button> 
                    <button type="button" id="editMessagePush" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                            保存
                    </button> 																				
                    </div>	
                </div>
</div>
<div class="modal fade" id="memberPushEditModal" tabindex="-1" role="dialog">
	<div class="modal-dialog">
	   	<div class="modal-content">
	       <!--定义操作列按钮模板-->
	       <script id="memberPushEditBtnTpl" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
	       <div class="box">
		        <div class="box-body">
		        	<input type="hidden" name="dataTemp" />
		         	<table id="memberPushEditLists" class="table table-bordered table-striped table-hover" width="100%">
			        </table>
			   	</div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"messagePushEdit":"res/js/marketing/messagePush_edit"}});
		require(["messagePushEdit"], function (messagePushEdit){
			messagePushEdit.init();
		});  
    });
</script>
