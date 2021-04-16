<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%></>
<!DOCTYPE HTML>
<html>
<head>
    <title>center</title>
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
        function sendMsg() {
            console.log($("#phonenumber").val());
            $.post("${path}/center/changeSendm?phone=" + $("#phonenumber").val(), function (data) {
                console.log("返回");
                console.log(data);
                $.each(data, function (k, v) {
                    if (k === "message") {
                        console.log("进来");
                        $("#msgaa").val(v);
                        console.log($("#msgaa").val());
                    }
                });
            })
        }

        $(function () {
            /*错误信息提示*/
            $.post("${path}/center/getMessage", function (message) {
                console.log(message);
                if (message == null || message === "") {

                } else {
                    window.alert(message);
                    $.post("${path}/center/cleMessage");
                    /*var b = window.confirm(message);*/
                }
            })
        });

    </script>
</head>
<body>
<form role="form" action="${path}/center/changeUser" method="post">
    <div class="form-group col-sm-6">
        <label class="">名字</label>
        <input type="text" class="form-control" value="${user.name}" name="name">
        <label>邮箱</label>
        <input type="text" class="form-control" value="${user.email}" name="email">
        <label>密码</label>
        <input type="text" class="form-control" placeholder="请输入新密码" name="pwd">
        <label>隐藏密码</label>
        <input type="text" class="form-control" placeholder="请输入隐藏区新密码" name="hidePwd">
        <label>手机号</label>
        <input type="text" class="form-control" value="${user.phone}" id="phonenumber" name="phone">
        <input type="button" id="phoneClick" value="发送验证码" onclick="sendMsg()">
        <input type="text" class="form-control" placeholder="请输入你收到的手机验证码" autocomplete="off" name="code">
        <input type="submit" value="提交" class="btn bg-primary">
    </div>
</form>


</body>
</html>
