define([], function() {
	var communityOrderEChart = {
		appPath : getPath("app"),
		init : function() {
			//页面查询
			//按天
			$("#communityOrderSearchDays").click(function() {
				var form = $("form[name=communityOrderDaysSerachForm]");
				var startTimeDay =  form.find("input[name=startTimeDay]").val();
				var endTimeDay = form.find("input[name=endTimeDay]").val();
				if(startTimeDay != "" && endTimeDay != ""){
					if(new Date(startTimeDay) > new Date(endTimeDay)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
					}else{
						communityOrderEChart.showCommunityOrderDaysChart();
					}
				}else{
					communityOrderEChart.showCommunityOrderDaysChart();
				}
			});
			//按月
			$("#communityOrderSearchMonths").click(function() {
				var form = $("form[name=communityOrderSerachMonthsForm]");
				var startTimeMonth =  form.find("input[name=startTimeMonth]").val();
				var endTimeMonth = form.find("input[name=endTimeMonth]").val();
				if(startTimeMonth != "" && endTimeMonth != ""){
					if(new Date(startTimeMonth) > new Date(endTimeMonth)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
					}else{
						communityOrderEChart.showCommunityOrderMonthsChart();
					}
				}else{
					communityOrderEChart.showCommunityOrderMonthsChart();
				}
			});
			//按年
			$("#communityOrderSearchYears").click(function() {
				var form = $("form[name=communityOrderSerachYearsForm]");
				var startTimeYear =  form.find("input[name=startTimeYear]").val();
				var endTimeYear = form.find("input[name=endTimeYear]").val();
				if(startTimeYear != "" && endTimeYear != ""){
					if(new Date(startTimeYear) > new Date(endTimeYear)){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "开始时间不能大于结束时间！");
					}else{
						communityOrderEChart.showCommunityOrderYearsChart();
					}
				}else{
					communityOrderEChart.showCommunityOrderYearsChart();
				}
			});
			communityOrderEChart.showCommunityOrderDaysChart();   //社区馆订单数据统计图表 按天
			communityOrderEChart.showCommunityOrderMonthsChart(); //社区馆订单数据统计图表 按月
			communityOrderEChart.showCommunityOrderYearsChart();  //社区馆订单数据统计图表 按年
		},
		
		//年统计
		showCommunityOrderYearsChart : function() {
			var form = $("form[name=communityOrderSerachYearsForm]");
			var startTimeYear =  form.find("input[name=startTimeYear]").val();
			var endTimeYear = form.find("input[name=endTimeYear]").val();
			$.ajax({
				url : communityOrderEChart.appPath + "/communityOrder/statisticCommunityOrderYears.do",
				type : "post",
				data: {"startTimeYear":startTimeYear,"endTimeYear":endTimeYear},  
				success : function(res) {
					if (res.code == "1") {
						var year = res.data.years;
						var orderCount = res.data.orderCount;//订单数量数组
						var orderAmount = res.data.orderAmount;//订单总金额数组
						var discountAmount = res.data.discountAmount;//优惠总金额数组
						var balanceAmount = res.data.balanceAmount;//余额抵扣总金额数组
						var payableAmount = res.data.payableAmount;//订单总支付金额数组
						var whenLong = res.data.whenLong;//抵扣课程包总数量数组
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('communityOrderYearEcharts')); 
		                option = {
		                	    title : {
		                	        text: '社区馆订单统计(按年)'
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
		                	        data:['订单数量','订单总金额','优惠总金额','余额抵扣总金额','订单实际支付总金额','订单时长']
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
		                	                formatter: '{value}'
		                	            }
		                	        }
		                	    ],
		                	    series : [
		                     	    {
		                   	            name:'订单数量',
		                   	            type:'line',
		                   	            data:orderCount
		                   	        }, {
		                   	            name:'订单总金额',
		                   	            type:'line',
		                   	            data:orderAmount
		                   	        }, {
		                   	            name:'优惠总金额',
		                   	            type:'line',
		                   	            data:discountAmount
		                   	        }, {
		                   	            name:'余额抵扣总金额',
		                   	            type:'line',
		                   	            data:balanceAmount
		                   	        }, {
		                   	            name:'订单实际支付总金额',
		                   	            type:'line',
		                   	            data:payableAmount
		                   	        }, {
		                   	            name:'订单时长',
		                   	            type:'line',
		                   	            data:whenLong
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
		showCommunityOrderMonthsChart : function() {
			var form = $("form[name=communityOrderSerachMonthsForm]");
			var startTimeMonth =  form.find("input[name=startTimeMonth]").val();
			var endTimeMonth = form.find("input[name=endTimeMonth]").val();
			$.ajax({
				url : communityOrderEChart.appPath + "/communityOrder/statisticCommunityOrderMonths.do",
				type : "post",
				data: {"startTimeMonth":startTimeMonth,"endTimeMonth":endTimeMonth},  
				success : function(res) {
					if (res.code == "1") {
						var month = res.data.months;
						var orderCount = res.data.orderCount;//订单数量数组
						var orderAmount = res.data.orderAmount;//订单总金额数组
						var discountAmount = res.data.discountAmount;//优惠总金额数组
						var balanceAmount = res.data.balanceAmount;//余额抵扣总金额数组
						var payableAmount = res.data.payableAmount;//订单总支付金额数组
						var whenLong = res.data.whenLong;//订单时长
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('communityOrderMonthEcharts')); 
		                option = {
		                	    title : {
		                	        text: '社区馆订单统计(按月)'
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
		                	    	 data:['订单数量','订单总金额','优惠总金额','余额抵扣总金额','订单实际支付总金额','订单时长']
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
		                	                formatter: '{value}'
		                	            }
		                	        }
		                	    ],
		                	    series : [
		                     	     {
		                   	            name:'订单数量',
		                   	            type:'line',
		                   	            data:orderCount
		                   	        }, {
		                   	            name:'订单总金额',
		                   	            type:'line',
		                   	            data:orderAmount
		                   	        }, {
		                   	            name:'优惠总金额',
		                   	            type:'line',
		                   	            data:discountAmount
		                   	        }, {
		                   	            name:'余额抵扣总金额',
		                   	            type:'line',
		                   	            data:balanceAmount
		                   	        }, {
		                   	            name:'订单实际支付总金额',
		                   	            type:'line',
		                   	            data:payableAmount
		                   	        }, {
		                   	            name:'订单时长',
		                   	            type:'line',
		                   	            data:whenLong
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
		showCommunityOrderDaysChart : function() {
			var form = $("form[name=communityOrderDaysSerachForm]");
			var startTimeDay =  form.find("input[name=startTimeDay]").val();
			var endTimeDay = form.find("input[name=endTimeDay]").val();
			$.ajax({
				url : communityOrderEChart.appPath + "/communityOrder/statisticCommunityOrderDays.do",
				type : "post",
				data: {"startTimeDay":startTimeDay,"endTimeDay":endTimeDay},  
				success : function(res) {
					if (res.code == "1") {
						debugger
						var days = res.data.days;
						var orderCount = res.data.orderCount; //订单数量数组
						var orderAmount = res.data.orderAmount; //订单总金额数组
						var discountAmount = res.data.discountAmount; //优惠总金额数组
						var balanceAmount = res.data.balanceAmount; //余额抵扣总金额数组
						var payableAmount = res.data.payableAmount; //订单总支付金额数组
						var whenLong = res.data.whenLong; //订单时长
		                // 基于准备好的dom，初始化echarts图表
		                var myChart = echarts.init(document.getElementById('communityOrderDayEcharts')); 
		                option = {
		                	    title : {
		                	        text: '社区馆订单统计(按天)'
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
		                	    	 data:['订单数量','订单总金额','优惠总金额','余额抵扣总金额','订单实际支付总金额','订单时长']
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
		                	                formatter: '{value}'
		                	            }
		                	        }
		                	    ],
		                	    series : [
		                     	     {
		                   	            name:'订单数量',
		                   	            type:'line',
		                   	            data:orderCount
		                   	        },
		                   	        {
		                   	            name:'订单总金额',
		                   	            type:'line',
		                   	            data:orderAmount
		                   	        },
		                   	        {
		                   	            name:'优惠总金额',
		                   	            type:'line',
		                   	            data:discountAmount
		                   	        }, {
		                   	            name:'余额抵扣总金额',
		                   	            type:'line',
		                   	            data:balanceAmount
		                   	        }, 
		                   	        {
		                   	            name:'订单实际支付总金额',
		                   	            type:'line',
		                   	            data:payableAmount
		                   	        },
		                   	        {
		                   	            name:'订单时长',
		                   	            type:'line',
		                   	            data:whenLong
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
	return communityOrderEChart;
});