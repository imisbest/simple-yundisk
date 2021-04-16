<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%></>
<!DOCTYPE HTML>
<html>
<head>
    <title>friend</title>
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
        function openBefore(id) {
            console.log("进来了");
            $.ajax("${path}/latest/openBefore?fileId=" + id);
        }

    </script>
</head>
<body>


<div class="container">
    <div class="col-sm-12">

        <%----%>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>名字</th>
                <th>类型</th>
                <th>大小</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${fileEntities}" var="p">
                <tr>
                    <td>${p.name}</td>
                    <td>${p.icon}</td>
                    <td>${p.sizes}</td>
                    <td>
                        <a target="Homepage" href="${path}/mydoc/openBefore?fileId=${p.id}">
                            打开文件所在位置
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
