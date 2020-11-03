<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <meta charset="utf-8">
    <title>博客信息可视化分析</title>
    <!-- 引入 echarts.js -->
    <script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/jquery/jquery-3.5.1.js"></script>
</head>
<body bgcolor="#ffe4c4">
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 显示标题，图例和空的坐标轴
        myChart.setOption({
            title: {
                text: '按博客发布时间可视化分析',
                subtext: '博客发布量'
            },
            tooltip: {
                show: true
            },
            legend: {
                data: ['按博客热度可视化分析']
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            xAxis: {
                data: []
            },
            yAxis: {},
            series: [{
                name: '博客发布量',
                type: 'bar',
                itemStyle: {
                    normal: {
                        label: {
                            show: true,
                            position: 'top',
                            textStyle: {color: '#615a5a'},
                            formatter: function (params) {
                                if (params.value == 0) {
                                    return '';
                                } else {
                                    return params.value;
                                }
                            }
                        }
                    }
                },
                data: [],
                markPoint: {
                    data: [
                        {type: 'max', name: '最大发布数量'},
                        {type: 'min', name: '最小发布数量'}
                    ]
                },
                markLine: {
                    data: [
                        {type: 'average', name: '平均发布博客量'}
                    ]
                }
            }]
        });

        myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画

        var releaseDates = [];    //博客发布日期(年月分类)（实际用来盛放X轴坐标值）
        var publishBlogs = [];    //博客发布数量（实际用来盛放Y坐标值）

        $.ajax({
            type: "post",
            async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "${pageContext.request.contextPath}/blog/blogYearAndMonth.do",    //请求发送到TestServlet处
            data: {},
            dataType: "json",        //返回数据形式为json
            success: function (hotBlogList) {
                //请求成功时执行该函数内容，result即为服务器返回的json对象
                if (hotBlogList) {
                    for (var i = 0; i < hotBlogList.length; i++) {
                        releaseDates.push(hotBlogList[i].releaseDateStr);    //挨个取出类别并填入类别数组
                    }
                    for (var i = 0; i < hotBlogList.length; i++) {
                        publishBlogs.push(hotBlogList[i].blogCount);    //挨个取出销量并填入销量数组
                    }
                    myChart.hideLoading();    //隐藏加载动画
                    myChart.setOption({        //加载数据图表
                        xAxis: {
                            data: releaseDates
                        },
                        series: [{
                            // 根据名字对应到相应的系列
                            name: '博客发布量',
                            data: publishBlogs
                        }]
                    });
                }
            },
            error: function (errorMsg) {
                //请求失败时执行该函数
                alert("图表请求数据失败!");
                myChart.hideLoading();
            }
        })
    });
</script>
</body>
</html>