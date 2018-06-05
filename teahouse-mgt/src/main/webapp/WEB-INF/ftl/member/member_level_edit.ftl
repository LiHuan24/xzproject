<meta charset="utf-8">
<style>
    .key {
        font-size: 1.5rem;
        font-weight: 500;
        color: #414550;
        line-height: 15px;
        white-space:nowrap;
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
							<label class="col-sm-12 control-label"><h4>会员等级编辑</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-10">
					<form name="memberLevelEditForm">
					<input type="hidden" name="memberLevelId" value="${memberLevel.memberLevelId}"/>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;等级名称：</label>
							<div class="col-sm-7">
							    <input class="form-control val" name="levelName" value="${memberLevel.levelName}"/>
							</div>
							<div style="margin-top:7px;"><span name="levelNameEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;等级折扣率：</label>
							<div class="col-sm-7">
								<input class="form-control val" name="levelDiscount" value="${memberLevel.levelDiscount}"/>
							</div>
							<div style="margin-top:7px;"><span name="levelDiscountEdit"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;晋升所需积分：</label>
							<div class="col-sm-7">
								<input class="form-control val" name="upgradePoint" value="${memberLevel.upgradePoint}"/>
							</div>
							<div style="margin-top:7px;"><span name="upgradePointEdit"></span></div>
						</div>		
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label reason key"><span>*</span>&nbsp;&nbsp;备注：</label>
							<div class="col-sm-7 val">
							 <textarea name="memo">${memberLevel.memo}</textarea>
							</div>
							<div style="margin-top:7px;"><span name="memoEdit"></span></div>
						</div>
																									
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;是否可用：</label>
							<div class="col-sm-7">
								<select name="isAvailable" class="form-control val">
									<option value="0" <#if memberLevel.isAvailable==0>selected="selected"</#if>>不可用</option>
									<option value="1" <#if memberLevel.isAvailable==1>selected="selected"</#if>>可用</option>
								</select>
							</div>
							<div style="margin-top:7px;"><span name="isAvailableEdit"></span></div>
						</div>
						</form>

					</div>	
        		</div><!-- /.row -->
        		<div class="form-group" style="margin-bottom:20px;overflow: hidden;">
                    <div class="col-sm-7">
                      <button id="closeMemberLevelEdit" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btnDefault" style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important">
                            关闭
                      </button>
                      <button id="editMemberLevel" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btnColorA" style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;">
                            保存
                      </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberLevelEdit":"res/js/member/member_level_edit"}});
		require(["memberLevelEdit"], function (memberLevelEdit){
			memberLevelEdit.init();
		}); 
	});    
</script>
