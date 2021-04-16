<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>regist</title>
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
                    $.post("${path}/user/cleMessageRegist");
                    /*var b = window.confirm(message);*/
                }
            })
        });
        $(function () {
            /*//切换样式*/
            $(".list-group").on("click", ".list-group-item", function () {
                //去掉所有
                $(".list-group-item").removeClass().addClass("list-group-item");
                //点击激活
                $(this).addClass("active");
            });

            $("#phoneClick").click(function () {
                console.log($("#phonenumber").val());
                $.post("${path}/user/sendm?phone=" + $("#phonenumber").val(), function (data) {
                    console.log("返回");
                    console.log(data);
                    $.each(data, function (k, v) {
                        if (k === "message") {
                            console.log("进来");
                            console.log(v);
                            $("#msgaa").html(v);
                            console.log($("#msgaa").val());
                        }
                    });
                })
                onSub();
            })
        })

        /*刷新*/
        function onSub() {
            setTimeout(function () {
                window.location.reload();
            }, 1000);
        }
    </script>
</head>
<body>


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h1 class="modal-title text-center" id="myModalLabel">欢迎注册</h1>
        </div>
        <form action="${path}/user/regist" method="post">
            <div class="modal-body" id="model-body">
                <div class="form-group">
                    <input type="text" class="form-control input-lg" placeholder="用户名" autocomplete="off" name="name">
                </div>
                <div class="form-group">
                    <input type="text" class="form-control input-lg" placeholder="邮箱" autocomplete="off" name="email">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control input-lg" placeholder="密码" autocomplete="off" name="pwd">
                </div>
                <div class="form-group">
                    <input type="password" class="form-control input-lg" placeholder="重复输一次密码" autocomplete="off"
                           name="repwd">
                </div>
                <div class="form-group">
                    <input type="tel" id="phonenumber" class="form-control input-lg" placeholder="手机号"
                           autocomplete="off"
                           name="phone" value="">
                </div>
                <input type="button" id="phoneClick" value="发送验证码" class="input-lg">


                <div class="form-group">
                    <input type="text" class="form-control input-lg" placeholder="请输入你收到的手机验证码" autocomplete="off"
                           name="code">
                </div>
                <div class="form-group">

                    <img src="${path}/captcha/captcha" id="captchaImg" alt="" class="bg-primary"/>
                    <div class="label-text"><h4>验证码：</h4><%--${sessionScope.securityCode}--%></div>
                    <input type="text" name="captchaCode" class="input-lg"><br/>
                    <a href="javascript:void(0)"
                       onclick="changeCaptcha()"><h4>看不清，换一张!~</h4></a>&nbsp;
                    <span style="color:red">${errorMsg }</span><br/>
                    <script type="text/javascript">
                        function changeCaptcha() {
                            // 根据ID获取到验证码图片对象
                            var captchaImg = document.getElementById('captchaImg');
                            captchaImg.src = '${path}/captcha/captcha?' + Math.random();
                        }
                    </script>

                </div>
                <span style="color: red">${sendMsg}</span>
            </div>
            <button type="submit" class="btn btn-primary form-control btn-lg">提交注册</button>
        </form>


        <div class="modal-footer">
            <div class="form-group list-group">
                <a href="${path}/jsp/front/login.jsp" class="list-group-item ">
                    <button type="button" class="btn  form-control btn-lg" id="log">去登录</button>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
