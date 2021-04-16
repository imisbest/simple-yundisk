<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>login</title>
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
        $(function () {
            /*错误信息提示*/
            $.post("${path}/user/getMessage", function (message) {
                console.log(message);
                if (message == null || message === "") {

                } else {
                    window.alert(message);
                    $.post("${path}/user/cleMessageLogin");
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

        function login() {
            $.ajax({
                url: "${path}/user/login",
                type: "POST",
                datatype: "JSON",
                data: $("#loginForm").serialize(),
                success: function (data) {
                    console.log("data]" + data);
                    if (data != null && data !== "") {
                        console.log("报错");
                        $("#msg").val(data)
                    } else {
                        console.log("成功进入");
                        location.href = "${path}/jsp/front/main/main.jsp";
                    }
                }
            });
            onSub();
        }

        $(function () {
            /*//切换样式*/
            $(".list-group").on("click", ".list-group-item", function () {
                //去掉所有
                $(".list-group-item").removeClass().addClass("list-group-item");
                //点击激活
                $(this).addClass("active");
            });
        })
    </script>
</head>
<body>


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h1 class="modal-title text-center" id="myModalLabel">欢迎来到云盘登录</h1>
        </div>
        <form id="loginForm" method="post" <%--action="${path}/user/login"--%>>
            <div class="modal-body" id="model-body">
                <div class="form-group">
                    <label>
                        <input type="tel" value="17337815026" class="form-control input-lg" placeholder="手机号"
                               autocomplete="off"
                               name="phone">
                    </label>
                </div>
                <div class="form-group">
                    <label>
                        <input type="password" value="1" class="form-control input-lg" placeholder="密码" autocomplete="off"
                               name="pwd">
                    </label>
                </div>
                <div class="form-group">
                    <img src="${path}/captcha/captcha" id="captchaImg" alt="" class="bg-primary"/>
                    <div class="label-text"><h4>验证码：</h4><%--${sessionScope.securityCode}--%></div>
                    <label>
                        <input type="text" name="captchaCode" value="${captchaCode}" class="input-lg">
                    </label><br/>
                    <a href="javascript:void(0)"
                       onclick="changeCaptcha()"><h4>看不清，换一张!~</h4></a>&nbsp;<br>
                    <script type="text/javascript">
                        function changeCaptcha() {
                            // 根据ID获取到验证码图片对象
                            var captchaImg = document.getElementById('captchaImg');
                            captchaImg.src = '${path}/captcha/captcha?' + Math.random();
                        }
                    </script>
                </div>
            </div>

            <button type="button" class="btn btn-primary form-control btn-lg" id="log" onclick="login()">点击登录</button>

    </div>
    <div class="modal-footer">
        <div class="form-group list-group col-sm-4">
            <a href="${path}/jsp/front/regist.jsp" class="list-group-item ">
                <button type="button" class="btn  form-control btn-lg">注册</button>
            </a>
        </div>
        <div class="form-group list-group col-sm-4">
            <a href="${path}/jsp/front/findpwd.jsp" class="list-group-item ">
                <button type="button" class="btn  form-control btn-lg">找回密码</button>
            </a>
        </div>
        <div class="form-group list-group col-sm-4">
            <a href="${path}/jsp/front/unlock.jsp" class="list-group-item ">
                <button type="button" class="btn  form-control btn-lg">申请解冻</button>
            </a>
        </div>
    </div>
</div>
</body>
</html>
