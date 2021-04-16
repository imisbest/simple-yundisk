<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE HTML>
<html>
<head>
    <style>
        .btn-logout {
            display: inline-block;
            width: 120px;
            height: 36px;
            text-align: center;
            line-height: 36px;
            border-radius: 10px;
            border: 1px solid #fbc4c4;
            color: #f56c6c;
            background: #fef0f0;
            cursor: pointer;
            font-size: 22Px;
        }

        .btn-logout:hover {
            background: #f56c6c;
            border-color: #f56c6c;
            color: #fff;
        }

        html, body {
            margin: 0;
            padding: 0;
        }
    </style>
</head>

<body style="text-align:center;font-family:'KaiTi';">
<div style="opacity:0.6;background-size:100% auto;background-size: cover;height:149.6px;position:absolute;width:100%;z-index:-1"></div>
<div style="padding-top: 8px;">

    <div style="position:absolute;right:24px;">
        <p style="font-size: 18px">
            <%@ page import="java.text.SimpleDateFormat" %>
            <%@ page import="java.util.Calendar" %>
            <% String datetime = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()); /*//获取系统时间*/%><%=datetime%>
            <br/> <br/>
            <br/></p>
    </div>
    <div onclick="window.parent.location.href='${path}/user/logOut'"
         style="position:absolute;right:400px;border: 1px solid;"
         class="btn-logout">安全退出
    </div>
    <div style="font-size:22px;line-height:36px;position:absolute;right:130px;color:#d74b4b;border: 1px solid">
        当前用户:${user.name }
    </div>
    <h1>个人云盘管理系统</h1>
</div>
</body>
</html>