<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>易湃共享健身房管理系统</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
     <!-- Bootstrap 3.3.5 -->
     <link rel="stylesheet" type="text/css" href="res/dep/bootstrap-3.3.5/css/bootstrap.min.css">
     <!-- Font Awesome -->
     <link rel="stylesheet" type="text/css" href="res/dep/font-awesome-4.5.0/css/font-awesome.min.css">
     <!-- Ionicons -->
     <link rel="stylesheet" type="text/css" href="res/dep/ionicons/css/ionicons.min.css">
     <!-- Theme style -->
     <link rel="stylesheet" type="text/css" href="res/dep/AdminLTE-2.3.0/css/AdminLTE.css">
     <!-- daterangepicker -->
     <link rel="stylesheet" type="text/css" href="res/dep/daterangepicker/daterangepicker-bs3.css">
     <!-- DataTables -->
     <link rel="stylesheet" type="text/css" href="res/dep/DataTables/css/dataTables.bootstrap.css">
     <!-- skin -->
     <link rel="stylesheet" type="text/css" href="res/dep/AdminLTE-2.3.0/css/skins/skin-green-light.css">
     <!-- easyui css -->
     <link rel="stylesheet" type="text/css" href="res/dep/jquery-easyui-1.4.4/themes/icon.css">
     <!-- 自定义css -->
     <link rel="stylesheet" type="text/css" href="res/css/common.css">
     
     <link href="res/css/login/black_login.css" rel="stylesheet" type="text/css" />
     <link href="res/css/font/iconfont.css" rel="stylesheet" /> 
     <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
     <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
     <!--[if lt IE 9]>
     <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
     <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
     <![endif]-->
     <!--[if lt IE 9]>
     <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
     <![endif]-->
  </head>
 <body>
<style>
		.loginCont{
 			width: 100%; 		
 			/*background: url("res/img/gray_login/loginBg.png") no-repeat center center;
 			background-size: 100% 100%;*/
 			background-color: #3e9ffe;
 		}
 		.bg{
 			display: block;
 			width: 100%;
 			height: 500px;
 			margin: 0 auto;
 			z-index: -100;
 		}
</style>
 	<div class="logincont">
 	<div class="loginTitle">
	 		
	 		
	 	</div>
	 	<div class="loginCont">
	 		<img src="res/img/login.png" class="bg">
	 		 <form name="loginForm" action="login.do" method="post" class="loginPanel" style="z-index: 30000">
	 		 	<span class="userLogin pdl36 ft36">用户登录</span>
                            <div class="form-group input-group" style="width: 79%; margin-left: 10%;">
                                <span class="input-group-addon">
                                	<img src="res/img/gray_login/iconUser.png" style="width: 16px; height: 16px;"/>
                                	<!--<i class="hziconfont icons-icon-onlock"></i>-->
                                </span>
                                <input class="form-control ui-wizard-content" placeholder="请输入用户名称" name="loginName" required="" aria-required="true" type="text">
                            </div>
                            <div class="form-group input-group" style="width: 79%; margin-left: 10%;">
                                <span class="input-group-addon">
                                	<img src="res/img/gray_login/iconPas.png" style="width: 16px; height: 16px;"/>
                                	<!--<i class="hziconfont icons-user-password"></i>-->
                                </span>
                                <input class="form-control ui-wizard-content" placeholder="请输入密码" name="loginPassword" required="" aria-required="true" type="password">
                            </div>
                       		<!--<div style="margin:-10px 0 10px;">
                       			<span class="input-group-addon" style="color:#f85252;" id="errorInfo">${errorInfo}</span>
                       		</div>-->
                       		<style>
                       			.loginBtn{
                       				width: 79% !important;
                       			}
                       			.forgetPas{
                       				width: 79% !important;
                       			}
                       		</style>
                            <div class="form-group">
                               <button type="submit" id="loginBtn" class="sigin-btn loginBtn" style="width:100%;background: #2B94FD; outline: none;">登录</button>
                             </div>
                             <a href="#" class="forgetPas">忘记密码?</a>
                        </form>
	 	</div>
	 	<div class="downLoad">
	 		
	 	</div>
	 	<div class="zui">
	 		<h5>深圳市行知网络科技有限公司   Copyright © 2013-2017</span>
	 	</div>
	 	</div>
	 <!--<div class="container">-->
	 	
        <!--<div id="preloader">
            <div id="status">&nbsp;</div>
        </div>  
        <div class="row" style="position: fixed;width: 100%;left:0;top:-30%;">-->
            <!--<div class="preloaderbg">
                <div class="bgtitle">行知网络分时租赁管理系统</div>
            </div> -->
            <!--<div class="col-md-6 col-md-offset-3">
                <div class="account-box">
                    <div class="userbgbox"><img src="res/img/gray_login/userbg.png" /></div>
                       <form name="loginForm" action="login.do" method="post" style=" width:400px; margin: 0 auto;">
                            <div class="form-group input-group">
                                <span class="input-group-addon"><i class="hziconfont icons-icon-onlock"></i></span>
                                <input class="form-control ui-wizard-content" placeholder="请输入用户名称" name="loginName" required="" aria-required="true" type="text">
                            </div>
                            <div class="form-group input-group">
                                <span class="input-group-addon"><i class="hziconfont icons-user-password"></i></span>
                                <input class="form-control ui-wizard-content" placeholder="请输入密码" name="loginPassword" required="" aria-required="true" type="password">
                            </div>
                       		<div style="margin:-10px 0 10px;">
                       			<span class="input-group-addon" style="color:#f85252;" id="errorInfo">${errorInfo}</span>
                       		</div>
                            <div class="form-group">
                               <button type="submit" id="loginBtn" class="sigin-btn" style="width:100%;">登录</button>
                             </div>
                        </form>
                    </div>
            </div>-->
            <!--<div class="col-xs-12" style="text-align:center;margin:50px auto 0;">
                <h6 style="color:#fff;">深圳市行知网络科技有限公司   Copyright © 2013-2017</h6>
            </div>-->
        <!--</div>

    </div>-->
</body>
</html>
<script type="text/javascript" src="res/js/lib/jquery-1.11.3.js"></script>
<!-- jquery form -->
<script type="text/javascript" src="res/dep/jquery-form/jquery.form.min.js"></script>
<script type="text/javascript" src="res/js/common/md5.js"></script>
<script type="text/javascript">
$(function(){	
    
});	  
</script>
