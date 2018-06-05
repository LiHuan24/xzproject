define([], function() {
	var earningsTrendChart = {
		appPath : getPath("app"),
		init : function() {
			//页面查询
			//按天
			$("#memberSearchDays").click(function() {
				var form = $("form[name=earningsSerachForm]");
				var startTimeDay =  form.find("input[name=startTimeDay]").val();
				var endTimeDay = form.find("input[name=endTimeDay]").val();
				if(startTimeDay != "" && endTimeDay != ""){
					if(new Date(startTimeDay) > new Date(endTimeDay)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
					}else{
						earningsTrendChart.showEarningsTrendDaysChart();
					}
				}else{
					earningsTrendChart.showEarningsTrendDaysChart();
				}
			});
			//按月
			$("#memberSearchMonth").click(function() {
				var form = $("form[name=memberSerachMonthForm]");
				var startTimeMonth =  form.find("input[name=startTimeMonth]").val();
				var endTimeMonth = form.find("input[name=endTimeMonth]").val();
				if(startTimeMonth != "" && endTimeMonth != ""){
					if(new Date(startTimeMonth) > new Date(endTimeMonth)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
					}else{
						earningsTrendChart.showEarningsTrendMonthChart();
					}
				}else{
					earningsTrendChart.showEarningsTrendMonthChart();
				}
			});
			//按年
			$("#memberSearchYear").click(function() {
				var form = $("form[name=memberSerachYearForm]");
				var startTimeYear =  form.find("input[name=startTimeYear]").val();
				var endTimeYear = form.find("input[name=endTimeYear]").val();
				if(startTimeYear != "" && endTimeYear != ""){
					if(new Date(startTimeYear) > new Date(endTimeYear)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
					}else{
						earningsTrendChart.showEarningsTrendYearChart();
					}
				}else{
					earningsTrendChart.showEarningsTrendYearChart();
				}
			});
			earningsTrendChart.showEarningsTrendDaysChart();   //易湃会员注册数据统计图表 按天
			earningsTrendChart.showEarningsTrendMonthChart();  //易湃会员注册数据统计图表 按月
			earningsTrendChart.showEarningsTrendYearChart();  //易湃会员注册数据统计图表 按年
		},
		
		//年统计
		showEarningsTrendYearChart : function() {
			var form = $("form[name=memberSerachYearForm]");
			var startTimeYear =  form.find("input[name=startTimeYear]").val();
			var endTimeYear = form.find("input[name=endTimeYear]").val();
			$.ajax({
				url : earningsTrendChart.appPath + "/member/statisticMemberYearRegister.do",
				type : "post",
				data: {"startTimeYear":startTimeYear,"endTimeYear":endTimeYear},  
				success : function(res) {
					if (res.code == "1") {
						var year = res.data.years;
						var memberCount = res.data.memberCount;
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('memberCountYearEcharts')); 
		                option = {
		                	    title : {
		                	        text: '会员注册量统计(按年)'
		                	    },
		                	    tooltip : {
		                	        trigger: 'axis',
		                	        axisPointer: {
	                	                type: 'cross',
	                	                label: {
	                	                    backgroundColor: '#6a7985'
	                	                }
	                	            }
		                	    },
		                	    legend: {
		                	    	x:200,
		                	        data:['注册人数']
		                	    },
		                	    toolbox: {
		                	    },
		                	    grid: {
		                	        left: '3%',
		                	        right: '4%',
		                	        bottom: '3%',
		                	        containLabel: true
		                	    },
		                	    calculable : true,
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            boundaryGap : false,
		                	            data : year
		                	        }
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}人'
		                	            }
		                	        }
		                	    ],
		                	    series : [
		                     	    {
		                   	            name:'注册人数',
		                   	            type:'line',
		                   	            data:memberCount
		                   	        }
		                	    ]
		                	};
		        
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
					}
				}
			});
		},
		
		//月统计
		showEarningsTrendMonthChart : function() {
			var form = $("form[name=memberSerachMonthForm]");
			var startTimeMonth =  form.find("input[name=startTimeMonth]").val();
			var endTimeMonth = form.find("input[name=endTimeMonth]").val();
			$.ajax({
				url : earningsTrendChart.appPath + "/member/statisticMemberMonthRegister.do",
				type : "post",
				data: {"startTimeMonth":startTimeMonth,"endTimeMonth":endTimeMonth},  
				success : function(res) {
					if (res.code == "1") {
						var month = res.data.month;
						var memberCount = res.data.memberCount;
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('memberCountMonthEcharts')); 
		                option = {
		                	    title : {
		                	        text: '会员注册量统计(按月)'
		                	    },
		                	    tooltip : {
		                	        trigger: 'axis',
		                	        axisPointer: {
	                	                type: 'cross',
	                	                label: {
	                	                    backgroundColor: '#6a7985'
	                	                }
	                	            }
		                	    },
		                	    legend: {
		                	    	x:200,
		                	        data:['注册人数']
		                	    },
		                	    toolbox: {
		                	    },
		                	    grid: {
		                	        left: '3%',
		                	        right: '4%',
		                	        bottom: '3%',
		                	        containLabel: true
		                	    },
		                	    calculable : true,
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            boundaryGap : false,
		                	            data : month
		                	        }
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}人'
		                	            }
		                	        }
		                	    ],
		                	    series : [
		                     	    {
		                   	            name:'注册人数',
		                   	            type:'line',
		                   	            data:memberCount
		                   	        }
		                	    ]
		                	};
		        
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
					}
				}
			});
		},	
		
		//天统计
		showEarningsTrendDaysChart : function() {
			var form = $("form[name=earningsSerachForm]");
			var startTimeDay =  form.find("input[name=startTimeDay]").val();
			var endTimeDay = form.find("input[name=endTimeDay]").val();
			$.ajax({
				url : earningsTrendChart.appPath + "/member/statisticMemberDaysRegister.do",
				type : "post",
				data: {"startTimeDay":startTimeDay,"endTimeDay":endTimeDay},  
				success : function(res) {
					if (res.code == "1") {
						var days = res.data.days;
						var memberCount = res.data.memberCount;
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('earningsTrendDays')); 
		                option = {
		                	    title : {
		                	        text: '会员注册量统计(按天)'
		                	    },
		                	    tooltip : {
		                	        trigger: 'axis',
		                	        axisPointer: {
	                	                type: 'cross',
	                	                label: {
	                	                    backgroundColor: '#6a7985'
	                	                }
	                	            }
		                	    },
		                	    legend: {
		                	    	x:200,
		                	        data:['注册人数']
		                	    },
		                	    toolbox: {
		                	    },
		                	    grid: {
		                	        left: '3%',
		                	        right: '4%',
		                	        bottom: '3%',
		                	        containLabel: true
		                	    },
		                	    calculable : true,
		                	    xAxis : [
		                	        {
		                	            type : 'category',
		                	            boundaryGap : false,
		                	            data : days
		                	        }
		                	    ],
		                	    yAxis : [
		                	        {
		                	            type : 'value',
		                	            axisLabel : {
		                	                formatter: '{value}人'
		                	            }
		                	        }
		                	    ],
		                	    series : [
		                     	    {
		                   	            name:'注册人数',
		                   	            type:'line',
		                   	            data:memberCount
		                   	        }
		                	    ]
		                	};
		        
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
					}
				}
			});
		},	
	}
	return earningsTrendChart;
});