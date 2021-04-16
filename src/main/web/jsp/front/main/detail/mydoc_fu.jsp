<%
    request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>mydoc_fu</title>
</head>
<body onload="window.parent.location.href='${path}/jsp/front/login.jsp'">

</body>
</html>