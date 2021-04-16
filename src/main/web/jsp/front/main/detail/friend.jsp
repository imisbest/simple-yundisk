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
        function onsaveDir(id) {
            console.log("进来了");
            $.ajax("${path}/friend/onsaveDir?fid=" + id);
            onSub();
        }

        /*文件保存*/
        function onsave(id) {
            console.log("进来了");
            $.ajax("${path}/friend/onsave?fid=" + id);
            onSub();
        }


        $(function () {
            /*错误信息提示*/
            $.post("${path}/friend/getMessage", function (message) {
                console.log(message);
                if (message == null || message === "") {

                } else {
                    window.alert(message);
                    $.post("${path}/friend/cleMessage");
                    /*var b = window.confirm(message);*/
                }
            })
        });

        /*删除好友*/
        function cleFriend(id) {
            console.log("进来了");
            $.ajax("${path}/friend/celFriend?fid=" + id);
            onSub();
        }

        /*刷新*/
        function onSub() {
            setTimeout(function () {
                window.location.reload();
            }, 1000);
        }

        /*好友搜索*/
        function serFri() {
            console.log("进来了");
            var bb = $('#serFri').val();
            console.log(bb);
            $.ajax("${path}/friend/serFri?inSearchName=" + bb);
            onSub();
        }
    </script>
</head>
<body>
<%--搜索————————————————————————————————————————————————————————————————————————--%>
<% String serFri = (String) session.getAttribute("serFri");
    if (serFri != null) {%>
<a href="${path}/friend/serFri?aa=1" style="color: red"><input type="button" value="确认添加" class="btn bg-primary"> </a>
<a href="${path}/friend/serFri?aa=0" style="color: red"><input type="button" value="取消添加" class="btn bg-primary"> </a>
<%}%>

<div class="container">
    <div class="col-sm-6">
        <input type="text" id="serFri" placeholder="请输入好友id">
        <input type="button" value="添加好友" onclick="serFri()">
        <%----%>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>好友id</th>
                <th>好友名字</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${friendList}" var="p">
                <tr>
                    <td>
                        <input type="button" value=" ${p.fuser.id}" onclick="sous('${p.id}')">
                    </td>
                    <td>${p.fuser.name}</td>
                    <td>
                        <input type="button" value="删除" onclick="cleFriend('${p.id}')">
                    </td>
                </tr>
            </c:forEach>


            </tbody>
        </table>

    </div>
    <div class="col-sm-6">

        <table class="table table-hover">
            <thead>
            <tr>
                <th>文件id</th>
                <th>文件名字</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${shareListDir}" var="p">
                <tr>
                    <td>
                            ${p.dir.id}
                    </td>
                    <td>${p.dir.name}</td>
                    <td>
                        <input type="button" value="保存" onclick="onsaveDir('${p.id}')">
                    </td>
                </tr>
            </c:forEach>
            <c:forEach items="${shareListfile}" var="p">
                <tr>
                    <td>
                            ${p.file.id}
                    </td>
                    <td>${p.file.name}</td>
                    <td>
                        <input type="button" value="保存" onclick="onsave('${p.id}')">
                    </td>
                </tr>
            </c:forEach>


            </tbody>
        </table>
    </div>
</div>

</body>
</html>
