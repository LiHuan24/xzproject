<meta charset="utf-8">
<style>
    .key {
        font-size: 1.5rem;
        font-weight: 500;
        color: #414550;
        line-height: 15px;
        white-space: nowrap;
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
							<label class="col-sm-12 control-label"><h4>会员等级添加</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-10 form-horizontal">
					<form name="memberLevelAddForm">
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;等级名称：</label>
							<div class="col-sm-7">
							    <input class="form-control val" name="levelName"/>
							</div>
							<div class="col-sm-6"><span name="levelNameAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;等级折扣率：</label>
							<div class="col-sm-7">
								<input class="form-control val" name="levelDiscount"/>
							</div>
							<div class="col-sm-6"><span name="levelDiscountAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;晋升所需积分：</label>
							<div class="col-sm-7">
								<input class="form-control val" name="upgradePoint"/>
							</div>
							<div class="col-sm-6"><span name="upgradePointAdd"></span></div>
						</div>		
						
						<div class="form-group col-md-6">
							<label class="col-sm-4 control-label reason key"><span>*</span>&nbsp;&nbsp;备注：</label>
							<div class="col-sm-7">
							 <textarea name="memo" rows="1" cols="40"></textarea><span name="memoAdd"></span>
							</div>
						</div>
																									
						 <div class="form-group col-md-6">
							<label class="col-sm-4 control-label key"><span>*</span>&nbsp;&nbsp;是否可用：</label>
							<div class="col-sm-7">
								<select name="isAvailable" class="form-control val">
									<option value="">请选择</option>
									<option value="0">不可用</option>
									<option value="1">可用</option>
								</select>
							</div>
							<div class="col-sm-6"><span name="isAvailableAdd"></span></div>
						</div>
						</form>

					</div>	
        		</div><!-- /.row -->
        		<div class="form-group">
                    <div class="col-sm-7" style="margin-bottom:20px;">
                      <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" id="closeMemberLevel" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btnDefault">
                            关闭
                      </button>
                      <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" id="saveMemberLevel" class="btn btn-default pull-right navbar-btn btn-flat memberLevel-operate-save btnColorA">
                            保存
                      </button>
                    </div>
                </div>
</div>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"memberLevelAdd":"res/js/member/member_level_add"}});
		require(["memberLevelAdd"], function (memberLevelAdd){
			memberLevelAdd.init();
		}); 
	});    
</script>
