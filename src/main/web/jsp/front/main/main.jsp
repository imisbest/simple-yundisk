<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE HTML>
<html>
<head>
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <script type="text/javascript">
        $(function () {

            $.post("${path}/user/getUser", function (aa) {
                console.log(aa);
                if (aa == null || aa === "") {
                    console.log("空");
                    $.post("${path}/user/logOut2");
                }
            })
        })

    </script>

</head>
<frameset rows="70,*" noresize="true" border="2px" bordercolor="#9A9D9F">
    <frame src="${path}/jsp/front/main/head.jsp">
    <frameset cols="16%,*">
        <frame src="${path}/jsp/front/main/menu.jsp" name="menuPage"/>
        <frame name="Homepage" src="${path}/jsp/front/main/home.jsp">
    </frameset>
</frameset>
</html>