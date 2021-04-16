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
        function delForverfile(id) {
            console.log("进来了");
            $.ajax("${path}/recover/del?fileId=" + id);
            onSub();
        }

        function recorverFile(id) {
            console.log("进来了");
            $.ajax("${path}/recover/recorverFile?fileId=" + id);
            onSub();
        }

        function recorverDir(id) {
            console.log("进来了");
            $.ajax("${path}/recover/recorverDir?pid=" + id);
            onSub();
        }

        function delForverDir(id) {
            console.log("进来了");
            $.ajax("${path}/recover/delDir?pid=" + id);
            onSub();
        }


        $(function () {
            /*错误信息提示*/
            $.post("${path}/recover/getMessage", function (message) {
                console.log(message);
                if (message == null || message === "") {

                } else {
                    window.alert(message);
                    $.post("${path}/recover/cleMessage");
                    /*var b = window.confirm(message);*/
                }
            })
        });


        /*刷新*/
        function onSub() {
            setTimeout(function () {
                window.location.reload();
            }, 1000);
        }


    </script>
</head>
<body>


<div class="container">
    <div class="col-sm-12">
        <input type="text" id="inSearch" placeholder="请输入搜索内容">
        <input type="button" value="提交" onclick="inSearchName()">
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
            <c:forEach items="${dirs}" var="p">
                <tr>
                    <td>${p.name}</td>
                    <td>${p.icon}</td>
                    <td></td>
                    <td>
                        <input type="button" value="还原文件夹" onclick="recorverDir('${p.id}')">
                        <input type="button" value="彻底删除" onclick="delForverDir('${p.id}')">
                    </td>
                </tr>
            </c:forEach>
            <c:forEach items="${fileEntities}" var="p">
                <tr>
                    <td>${p.name}</td>
                    <td>${p.icon}</td>
                    <td>${p.sizes}</td>
                    <td>
                        <input type="button" value="还原文件" onclick="recorverFile('${p.id}')">
                        <input type="button" value="彻底删除" onclick="delForverfile('${p.id}')">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
