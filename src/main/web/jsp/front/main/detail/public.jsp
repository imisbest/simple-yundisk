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
        /*文件夹保存*/
        function onsaveDirs(id) {
            console.log("进来了");
            $.ajax("${path}/public/onsaveDirs?fid=" + id);
            onSub();
        }

        /*文件保存*/
        function onsaves(id) {
            console.log("进来了");
            $.ajax("${path}/public/onsaves?fid=" + id);
            onSub();
        }


        $(function () {
            /*错误信息提示*/
            $.post("${path}/public/getMessage", function (message) {
                console.log(message);
                if (message == null || message === "") {

                } else {
                    window.alert(message);
                    $.post("${path}/public/cleMessage");
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

        /*搜索框*/
        function inSearchName() {
            console.log("进来了");
            var bb = $('#inSearch').val();
            console.log(bb);
            $.ajax("${path}/public/inSearch?inSearchName=" + bb);
            onSub();
        }
    </script>
</head>
<body>


<div class="container">
    <div class="col-sm-6">
        <input type="text" id="inSearch" placeholder="请输入搜索内容">
        <input type="button" value="提交" onclick="inSearchName()">
        <%----%>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>文件id</th>
                <th>文件名字</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${shareListDirs}" var="p">
                <tr>
                    <td>${p.dir.id}</td>
                    <td>${p.dir.name}</td>
                    <td><input type="button" value="保存" onclick="onsaveDirs('${p.id}')"></td>
                </tr>
            </c:forEach>
            <c:forEach items="${shareListfiles}" var="p">
                <tr>
                    <td>${p.file.id}</td>
                    <td>${p.file.name}</td>
                    <td><input type="button" value="保存" onclick="onsaves('${p.id}')"></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
