<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>博客信息可视化分析</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/echarts/css/index.css" />
  </head>

  <body>
    <header>
      <h1>博客信息可视化分析</h1>
      <div class="showTime">当前时间：2020年3月17-0时54分14秒</div>
      <script type="text/javascript">
        var t = null;
        t = setTimeout(time, 1000); //開始运行
        function time() {
          clearTimeout(t); //清除定时器
          dt = new Date();
          var y = dt.getFullYear();
          var mt = dt.getMonth() + 1;
          var day = dt.getDate();
          var h = dt.getHours(); //获取时
          var m = dt.getMinutes(); //获取分
          var s = dt.getSeconds(); //获取秒
          document.querySelector(".showTime").innerHTML =
            "当前时间：" +
            y +
            "年" +
            mt +
            "月" +
            day +
            "-" +
            h +
            "时" +
            m +
            "分" +
            s +
            "秒";
          t = setTimeout(time, 1000); //设定定时器，循环运行
        }
      </script>
    </header>
    <section class="mainbox">

      <div class="column">
        <div class="panel bar">
          <h2>
            按博客月发布数量可视化分析
          </h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>

        <div class="panel line">
          <h2>折线图-人员变化</h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>

        <div class="panel pie">
          <h2>饼形图-年龄分布</h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>

      </div>

      <div class="column">
        <div class="no">
          <div class="no-hd">
            <ul>
              <li>125811</li>
              <li>204563</li>
            </ul>
          </div>
          <div class="no-bd">
            <ul>
              <li>博客访问人数</li>
              <li>博客总点击数</li>
            </ul>
          </div>
        </div>

        <div class="map">
          <div class="chart"></div>
          <div class="map1"></div>
          <div class="map2"></div>
          <div class="map3"></div>
        </div>

      </div>

      <div class="column">
        <div class="panel bar1">
          <h2>柱状图-技能掌握</h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>
        <div class="panel line1">
          <h2>折线图-播放量</h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>
        <div class="panel pie1">
          <h2>饼形图-地区分布</h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>
      </div>
    </section>
    <script src="${pageContext.request.contextPath}/static/echarts/js/flexible.js"></script>
    <!-- 引入 echarts.js -->
    <script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/jquery/jquery-3.5.1.js"></script>
    <script src="${pageContext.request.contextPath}/static/echarts/js/index.js"></script>
    <script src="${pageContext.request.contextPath}/static/echarts/js/china.js"></script>
    <script src="${pageContext.request.contextPath}/static/echarts/js/myMap.js"></script>
  </body>
</html>
