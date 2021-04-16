<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>loginHide</title>
    <link rel="stylesheet" href="${path}/boot/css/bootstrap.min.css">
    <!--引入jqgrid的bootstrap css-->
    <link rel="stylesheet" href="${path}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <!--引入jquery核心js-->
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <!--引入jqgrid核心js-->
    <script src="${path}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <!--引入jqgrid国际化js-->
    <script src="${path}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <!--引入bootstrap组件js-->
    <script src="${path}/boot/js/bootstrap.min.js"></script>
    <script type="text/javascript">


    </script>
</head>
<body><%--
<form role="form">
    <div class="form-group">
        <input type="text" class="form-control" id="name"
               placeholder="请输入名称">
    </div>

    <button type="submit" class="btn btn-default">提交</button>
</form>--%>

<form action="${path}/hide/queryAll" method="post" role="form">
    <div class="form-group">
        <input type="text" placeholder="请输入隐藏区密码" name="hidePwd" class="form-control">
    </div>
    <button type="submit" class="btn btn-default">提交</button>
</form>
</body>
</html>
