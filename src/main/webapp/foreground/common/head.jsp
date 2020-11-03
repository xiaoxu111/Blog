<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="row">
    <div class="col-md-4">
        <img alt="IT小工小徐博客系统" src="${pageContext.request.contextPath}/static/images/logo.png">
    </div>
    <div class="col-md-8">
        <%--免费天气预报--%>
        <%--<iframe style="float: right;" width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>--%>
        <%--免费天气预报--%>
        <iframe name="weather_inc" src="http://i.tianqi.com/index.php?c=code&id=7"
                style="border:solid 1px #7ec8ea;float: right" width="288" height="90" frameborder="0" marginwidth="0"
                marginheight="0" scrolling="no"></iframe>
    </div>
</div>