正式发布时,可将此文件夹删去




========== 改  ========== 
995	车辆管理-车辆列表-查看车辆详情     lifeng   20170601
	改：	型号（此项紧跟着品牌之后显示）、上线时间/下线时间（改为上/下线时间）、
		上下线操作人（此项紧跟着上/下线时间之后显示）、电量（电量的值后面，加%，如87%）、
		里程（改为行驶里程，值的后面加Km，如7688Km）、
		投保日期（格式为yyyy-MM-dd）、保险 有效期（格式为yyyy-MM-dd）
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/car/car_view.ftl
	
996	车辆管理-车辆列表-编辑车辆信息：     
	改：所属场站（改为“所在场站”，非必填项）、电量（电量的值后面，加%，如87%）、里程（改为行驶里程，值的后面加Km，如7688Km）
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/car/car_edit.ftl

997	 终端设备管理-终端设备列表-列表查询    导出模板”按钮（改为“导入模板下载”）
	*/fs-mgt/src/main/webapp/res/js/device/device_list.js
	
998	终端设备管理-新增设备    SIM卡号、城市（改为非必填）
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/device/device_add.ftl
	
999	 终端设备管理-编辑设备    SIM卡号、城市（改为非必填）
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/device/device_edit.ftl
	
========== 删  ==========
1000   会员管理-会员列表-导出会员的excel中，删除是否加盟字段  lifeng   20170602
	*/fs-mgt/src/main/webapp/res/member.xls
	*/fs-mgt/src/main/java/cn/com/shopec/mgt/member/controller/MemberController.java
	
1001 会员管理-会员列表-查看会员详情  删：“员工类型”字段、“审核结果”字段（已经有认证情况字段了）、“创建时间”、“更新时间” 
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/member/member_view.ftl
	
1002 会员管理-会员列表-编辑会员      删： “创建时间”、“更新时间”    
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/member/member_edit.ftl

	 会员管理-会员列表-列表查询     删：“身份证”列 
	*/fs-mgt/src/main/webapp/res/js/member/member_list.js
	
1003  终端设备管理-终端设备列表-列表查询    删查询条件：  绑定时间起 、 绑定时间止 、 SIM卡号  、 终端状态	 
	      删查询条件：  绑定时间起 、 绑定时间止 、 SIM卡号  、 终端状态  
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/device/device_list.ftl
	
1004   终端设备管理-终端设备列表-列表查询	删“SIM卡号”列 	  
	*/fs-mgt/src/main/webapp/res/js/device/device_list.js
	
1005  终端设备管理-编辑设备   删： “创建时间”、“更新时间”   
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/device/device_edit.ftl
	
1006  车辆管理-车辆列表-列表查询    删：“车主”列
	 */fs-mgt/src/main/webapp/res/js/car/car_list.js
	 
1007 车辆管理-车辆列表-上下线操作日志   删：车辆编号
	 */fs-mgt/src/main/webapp/res/js/car/car_online_list.js
	
1008 车辆状态列表-列表查询   删：“车主”列、“终端状态”列、 “里程”-查询条件
	*/fs-mgt/src/main/webapp/res/js/carStatus/car_status_list.js
	

========== 增  ========== 
1009 监控管理-车辆监控   增：查询条件-状态（加“空闲”项）；
 	*/fs-mgt/src/main/webapp/WEB-INF/ftl/monitor/car_monitor_fake.ftl
 	
1010 车辆管理-车辆列表-查看车辆详情     续航里程（值的单位为Km，此项出现在电量之后
	 */fs-mgt/src/main/webapp/WEB-INF/ftl/car/car_view.ftl
	 
1011 车辆管理-车辆列表-编辑车辆详情     续航里程（值的单位为Km，此项出现在电量之后
	 */fs-mgt/src/main/webapp/WEB-INF/ftl/car/car_edit.ftl
	 
1012   车辆管理-车辆列表-上下线操作日志  加：操作日期范围（2个日期，到天即可）
	*/fs-core/src/main/java/cn/com/shopec/core/car/mapper/CarOnlineLogMapper.xml
	*/fs-mgt/src/main/webapp/res/js/car/car_online_list.js
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/car/car_online_list.ftl
	 
1013  车辆管理-车辆列表-上下线操作日志   加查询条件： 操作人（文本框输入，查询按系统用户名查询）
	 */fs-mgt/src/main/webapp/res/js/car/car_online_list.js
	 /fs-mgt/src/main/webapp/WEB-INF/ftl/car/car_online_list.ftl
	 */fs-core/src/main/java/cn/com/shopec/core/car/mapper/CarOnlineLogMapper.xml
	 
1014	  车辆状态列表-列表查询   加：“续航”列 单位Km 		  lifeng  20170605
	*/fs-mgt/src/main/webapp/res/js/carStatus/car_status_list.js		
	
1015   车辆状态列表-列表查询  加：“车辆状态”-查询条件（下拉，全部、已点火、已熄火   lifeng  20170606
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/car/car_status_list.ftl
	*/fs-core/src/main/java/cn/com/shopec/core/car/mapper/CarStatusMapper.xml
	*/fs-mgt/src/main/webapp/res/js/carStatus/car_status_list.js
	
	
1016	 会员管理-会员列表-导出   加： 加“会员等级”字段、“认证情况”字段、“状态”字段（正常、黑名单）  lifeng  20170606
	*/fs-mgt/src/main/java/cn/com/shopec/mgt/member/controller/MemberController.java
	*/fs-mgt/src/main/webapp/res/member.xls

1017   会员管理-会员列表-查看会员详情		  lifeng  20170606
	/fs-mgt/src/main/webapp/WEB-INF/ftl/member/member_view.ftl



1018 	会员页面编辑  加：手持证件照		  lifeng  20170606
	*/fs-mgt/src/main/webapp/res/js/member/member_edit.js
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/member/member_edit.ftl
	
1019		 会员管理-会员列表-编辑会员   加：会员积分字段   lifeng  20170606
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/member/member_edit.ftl
	
	
1020	终端设备管理-编辑设备  改：MAC地址 （改为不可修改）	lifeng  20170606
	*/fs-mgt/src/main/webapp/WEB-INF/ftl/device/device_edit.ftl
