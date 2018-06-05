define([],function() {
	var coachCourseList = {
		appPath : getPath("app"),
		init : function() {
			// 列表页面分页方法调用
			coachCourseList.pageList();
			
		},
	
		pageList : function() {
			var form = $("form[name=coachCourseSerachForm]");
			var coachNo = form.find("input[name='coachNo']").val();
			var coachCourseBtnTpl = $("#coachCourseBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(coachCourseBtnTpl);
			var table = $('#coachCourseList')
			.dataTable(
					{
						searching : false,
						destroy : true,
						"ajax" : {
							"type" : "POST",
							"url" : coachCourseList.appPath+ "/coach/coachCoursenPageList.do",
							"data" : function(d) {
								return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "coachNo":coachNo
										});
							},
							"dataSrc" : function(json) {
								json.recordsTotal = json.rowCount;
								json.recordsFiltered = json.rowCount;
								json.data = json.data;
								return json.data;
							},
							error : function(xhr, error, thrown) {
							}
						},
						"columns" : [ 
						{
							"title" : "门店名称",
							"data" : "storeName"
						},{
							"title" : "课程名称",
							"data" : "chineseName"
						},{
							"title" : "教练名称",
							"data" : "coachName"
						},{
							"title" : "上课时间",
							"data" : "showDate"
						},{
							"title" : "星期名称",
							"data" : "courseWeek"
						},{
							"title" : "预约人数",
							"data" : "reservation"
						},{
							"title" : "本周/下周",
							"data" : "courseType"
						}],
						"dom": "<'row'<'#coachTools.col-xs-6'><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
						   initComplete: function () {
							   
						   },
						"drawCallback" : function(settings) {
						},
						"order" : [ [ 1, "desc" ] ],
						"columnDefs" : [
							{
								"targets" : [ 6 ],
								"render" : function(a,
										b, c, d) {
									var courseWeek;
									if(c.courseType == 0){
										courseWeek="本周";
									}else if(c.courseType==1){
										courseWeek="下周";
									}else{
										courseWeek="未知";
									}
									return courseWeek;
								}
							},
						 ]
					});
		},
	};
	return coachCourseList;
});