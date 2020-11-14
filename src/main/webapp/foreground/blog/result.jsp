<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
</head>
<body class="easyui-layout">
<div class="data_list">
    <div class="data_list_title">
        <img src="${pageContext.request.contextPath}/static/images/search_icon.png"/>
        搜索&nbsp;<font color="red">${q }</font>&nbsp;的结果 &nbsp;(总共搜索到&nbsp;${resultTotal}&nbsp;条记录)
    </div>
    <div class="datas search">
        <ul>
            <c:choose>
                <c:when test="${blogList.size()==0 }">
                    <div align="center" style="padding-top: 20px">未查询到结果，请换个关键字试试看！</div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="blog" items="${blogList }">
                        <li style="margin-bottom: 20px">
                            <span class="title">
                                <div class="easyui-accordion" data-options="fit:true,border:false">
                                <a href="${pageContext.request.contextPath}/blog/articles/${blog.id}.html" target="_blank"
                                   class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-refresh'">${blog.title }</a>
                                </div>
                            </span>
                            <span class="summary">摘要: ${blog.content }...</span>
                            <span class="link">
                                <a href="${pageContext.request.contextPath}/blog/articles/${blog.id}.html">
                                    http://www.aliwo.cn/blog/articles/${blog.id}.html
                                </a>
                                &nbsp;&nbsp;&nbsp;&nbsp;发布日期：${blog.releaseDateStr }
                            </span>
                        </li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    ${pageCode }
</div>
</body>
</html>