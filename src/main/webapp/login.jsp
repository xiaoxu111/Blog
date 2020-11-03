<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>博客系统后台登录页面-Powered by aliwo.cn</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/login.css">
    <script src="${pageContext.request.contextPath}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/jquery/jquery-3.5.1.js"></script>
    <script src="https://ssl.captcha.qq.com/TCaptcha.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/login.js"></script>

</head>
<body>
<DIV class="top_div">
</DIV>
<form id="loginForm" action="${pageContext.request.contextPath}/blogger/login.do" method="post">
    <DIV style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">
        <DIV style="width: 165px; height: 96px; position: absolute;">
            <DIV class="tou">
            </DIV>
            <DIV class="initial_left_hand" id="left_hand">
            </DIV>
            <DIV class="initial_right_hand" id="right_hand">
            </DIV>
        </DIV>
        <P style="padding: 30px 0px 10px; position: relative;">
            <SPAN class="u_logo"></SPAN>
            <INPUT id="userName" name="userName" class="ipt" type="text" placeholder="请输入用户名"
                   value="${blogger.userName }">
        </P>
        <P style="position: relative;">
            <SPAN class="p_logo"></SPAN>
            <INPUT id="password" name="password" class="ipt" type="password" placeholder="请输入密码"
                   value="${blogger.password }">
        </P>
        <DIV style="height: 50px; line-height: 50px; margin-top: 30px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">
            <P style="margin: 0px 35px 20px 45px;">
                <SPAN style="float: left;"><a href="${pageContext.request.contextPath}/index.html">小徐博客系统</a></SPAN>
                <span><font color="red" id="error">${errorInfo }</font></span>
                <SPAN style="float: right;">
                    <input onclick="vali()" type="button"
                           style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;"
                           value="登录">
	         </SPAN>
            </P>
        </DIV>
    </DIV>
</form>
<div style="text-align:center;padding-top: 30px">
    Copyright © 2019-2020 IT小工小徐 版权所有
</div>
</body>
</html>