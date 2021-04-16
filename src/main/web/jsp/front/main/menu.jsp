<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>menu</title>
    <style>
        .aa {
            height: 55px;
            line-height: 55px;
            margin: 0 auto;
            width: 150px;
            margin-bottom: 12px;
            border: 1px solid #b3d8ff;
            background: #ecf5ff;
            border-radius: 6px;
            font-size: 25px;
        }

        .aa a {
            color: #409eff;
        }
    </style>
    <script type="text/javascript" src="${path}/statics/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".aa").click(function () {
                $(".aa").css({"background": "#ecf5ff", "border-color": "#b3d8ff"});
                $(".aa a").css("color", "#409eff");
                $(this).css({"background": "#409eff", "border-color": "#409eff"});
                $(this).children("a").css("color", "#fff");
            });
        });
    </script>

</head>

<body style="text-align: center;background-color: #d4e7f0">  <!-- #f0f9fd -->

<h4>系统主菜单</h4>
<hr/>
<br/>
<div style="text-align: center" class="div1">
    <div class="aa">
        <a style="text-decoration: none;" href="${path}/mydoc/queryOne?did=shouye"
           target="Homepage">我的文件</a><br/><br/>
    </div>
    <div class="aa">
        <a style="text-decoration: none;" href="${path}/friend/friQue"
           target="Homepage">好友管理</a><br/><br/>
    </div>
    <div class="aa">
        <a style="text-decoration: none;" href="${path}/jsp/front/main/detail/center.jsp"
           target="Homepage">个人中心</a><br/><br/>
    </div>
    <div class="aa">
        <a style="text-decoration: none;" href="${path}/public/queryAll"
           target="Homepage">文件共享区</a><br/><br/>
    </div>

    <div class="aa">
        <a style="text-decoration: none;" href="${path}/recover/queryAll"
           target="Homepage">回收站</a><br/><br/>
    </div>

    <div class="aa">
        <a style="text-decoration: none;" href="${path}/latest/queryAll"
           target="Homepage">最近浏览</a><br/><br/>
    </div>
    <div class="aa">
        <a style="text-decoration: none;" href="${path}/jsp/front/loginHide.jsp"
           target="Homepage">隐藏文件夹</a><br/><br/>
    </div>


</div>
</body>
</html>
