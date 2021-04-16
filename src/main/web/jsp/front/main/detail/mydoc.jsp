<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%></>
<!DOCTYPE HTML>
<html>
<head>
    <title>mydoc</title>
    <%--    <meta http-equiv="refresh" content="0; url=">&lt;%&ndash;经过一段时间转到另外某个页面&ndash;%&gt;--%>
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
    <style>
        #son, #son2 {
            width: 0px;
            height: 30px;
            background: greenyellow;
        }
    </style>

    <script type="text/javascript">
        function shareFriend2(id) {
            console.log("进来了");
            $.ajax("${path}/friend/shareDir?uid=" + id);
            onSub();
        }

        function shareDir(id) {
            console.log("进来了");
            $.ajax("${path}/friend/shareDir?ShareDirId=" + id);
            onSub();
        }

        //分享好友
        function shareFriend(uid) {
            console.log("进来了");
            $.ajax("${path}/friend/sharefile?uid=" + uid);
            onSub();
        }

        /*分享文件*/
        function share(id) {
            console.log("进来了");
            $.ajax("${path}/friend/sharefile?sharefileId=" + id);
            onSub();
        }

        /*刷新*/
        function onSub() {
            setTimeout(function () {
                window.location.reload();
            }, 1000);
        }

        /*----------------------------------------------------------------------------*/
        /*文件夹重命名*/
        function changDirName() {
            console.log("进来了");
            var bb = $('#changeDirName').val();
            console.log(bb);
            $.ajax("${path}/mydoc/changeDirName?dirName=" + bb);
            onSub();
        }

        function changeName12(id) {
            console.log("进来了");
            $.ajax("${path}/mydoc/changeDirName?dirId=" + id);
            onSub();
        }

        /*文件重命名*/
        function changeName1(id) {
            console.log("进来了");
            $.ajax("${path}/mydoc/changFileName?fileId=" + id);
            onSub();
        }

        function changFileName() {
            console.log("进来了");
            var bb = $('#changFileName').val();
            console.log(bb);
            $.ajax("${path}/mydoc/changFileName?fileName=" + bb);
            onSub();
        }

        /*搜索框*/
        function inSearchName() {
            console.log("进来了");
            var bb = $('#inSearch').val();
            console.log(bb);
            $.ajax("${path}/mydoc/inSearch?inSearchName=" + bb);
            onSub();
        }

        /* /!*文件下载*!/
         function down1(id) {
             console.log("进来了");
             console.log(id);
             $.ajax("\${path}/mydoc/down?id=" + id, function () {
                 onSub();
             });
         }*/

        /*文件 删除————————————————————————————————————————————————*/
        function cancle(s) {
            console.log("进来了");
            console.log(s);
            $.ajax("${path}/mydoc/del?fileId=" + s);
            onSub();
        }

        /*文件夹删除*/
        function cancles(s) {
            console.log("进来了");
            console.log(s);
            $.ajax("${path}/mydoc/delDir?pid=" + s);
            onSub();
        }

        /*文件 移入隐藏文件夹————————————————————————————————————————————————*/
        function yrcancle(s) {
            console.log("进来了");
            console.log(s);
            $.ajax("${path}/hide/yrdel?fileId=" + s);
            onSub();
        }

        /*文件夹移入隐藏文件夹*/
        function yrcancles(s) {
            console.log("进来了");
            console.log(s);
            $.ajax("${path}/hide/yrdelDir?pid=" + s);
            onSub();
        }

        /*文件夹上传————————————————————————————————————————————————*/
        $(function () {
            uploadFile2();
        });

        function uploadFile2() {
            $(".file2").change(function () {
                var formData2 = $(this).parent(); //得到表单
                $.ajax({
                    url: '${path}/mydoc/uploaddir?fn=upload&r=' + Math.random(),
                    type: 'POST',
                    Accept: 'text/html;charset=UTF-8',
                    cache: false,
                    contentType: "multipart/form-data",
                    data: new FormData(formData2[0]),
                    processData: false,
                    contentType: false,
                    xhr: function () {
                        myXhr = $.ajaxSettings.xhr();
                        if (myXhr.upload) { // check if upload property exists
                            myXhr.upload.addEventListener('progress', function (e) {
                                var loaded = e.loaded;//已经上传大小情况
                                var tot = e.total;//附件总大小
                                var per = Math.floor(100 * loaded / tot);  //已经上传的百分比
                                $("#son2").html(per + "%");
                                $("#son2").css("width", per + "%");
                                console.log('附件总大小 = ' + loaded);
                                console.log('已经上传大小 = ' + tot);
                            }, false); // for handling the progress of the upload
                        }
                        return myXhr;
                    },
                    success: function (data) {
                        console.log(data);
                        console.log("上传成功!!!!");
                        onSub();
                    },
                    error: function () {
                        console.log("上传失败！");
                        onSub();
                    }
                });
            });
        }

        /*文件上传*/
        $(function () {
            uploadFile();
        });

        function uploadFile() {
            $(".file").change(function () {
                var formData2 = $(this).parent(); //得到表单
                $.ajax({
                    url: '${path}/mydoc/uploadfile?fn=upload&r=' + Math.random(),
                    type: 'POST',
                    Accept: 'text/html;charset=UTF-8',
                    cache: false,
                    contentType: "multipart/form-data",
                    data: new FormData(formData2[0]),
                    processData: false,
                    contentType: false,
                    xhr: function () {
                        myXhr = $.ajaxSettings.xhr();
                        if (myXhr.upload) { // check if upload property exists
                            myXhr.upload.addEventListener('progress', function (e) {
                                var loaded = e.loaded;//已经上传大小情况
                                var tot = e.total;//附件总大小
                                var per = Math.floor(100 * loaded / tot);  //已经上传的百分比
                                $("#son").html(per + "%");
                                $("#son").css("width", per + "%");
                                console.log('附件总大小 = ' + loaded);
                                console.log('已经上传大小 = ' + tot);
                            }, false); // for handling the progress of the upload
                        }
                        return myXhr;
                    },
                    success: function (data) {
                        console.log(data);
                        console.log("上传成功!!!!");
                        onSub();
                    },
                    error: function () {
                        console.log("上传失败！");
                        onSub();
                    }
                });
            });
        }

        /* function myrefresh()
         {
             window.location.reload();
         }
         setTimeout('myrefresh()',9999999999);
         先执行后刷新
         */
        /*查询*/
        function sous(pp) {
            console.log("进来了");
            console.log(pp);
            $.ajax("${path}/mydoc/queryOne?did=" + pp);
            onSub();
        }

        $(function () {
            /* $.ajax("\${path}/mydoc/queryOne");*/
            $("#createDir").click(function () {
                $.post("${path}/mydoc/addDir?fileName=" + $("#createDirName").val())
            });
            /*错误信息提示*/
            $.post("${path}/mydoc/getMessage", function (message) {
                console.log(message);
                if (message == null || message === "") {

                } else {
                    window.alert(message);
                    $.post("${path}/mydoc/cleMessage");
                    /*var b = window.confirm(message);*/
                }
            })
        });

    </script>
</head>
<body>
<div style="float: left">
    <%--上传文件夹=================================================================--%>
    <form id="uploadForm2" enctype="multipart/form-data" class="form-group">
        <h4>上传文件夹：</h4><input id="files" type="file" name="files" multiple webkitdirectory class="file2 input-lg"/>
    </form>
    <div id="son2"></div>
</div>
<div style="float: left">
    <%--上传文件-------------------------------------------------%>
    <form id="uploadForm" enctype="multipart/form-data">
        <h4>上传文件：</h4><input id="file" type="file" name="bb" class="file input-lg"/>
    </form>
    <div id="son"></div>
</div>
<div>
    <%--创建文件夹---------------------------------------------------%>
    <input type="text" id="createDirName">
    <button id="createDir" class="btn btn-primary btn-lg" data-loading-text="Loading..."
            type="button" onclick="onSub()"><h4>创建文件夹</h4>
    </button>
    <script type="text/javascript">

        function onSub() {
            setTimeout(function () {
                window.location.reload();
            }, 1000);
        }
    </script>
</div>
<%--搜索框-------------------------------------------------------------%>
<input type="text" id="inSearch" placeholder="请输入搜索内容" class="input-lg">
<input type="button" value="提交" onclick="inSearchName()" class="input-lg">

<br>
<hr color="red">
<%--确定保存分享的文件夹--------------------------------------------------------------%>
<% String onsaveDir = (String) session.getAttribute("onsaveDir");
    if (onsaveDir != null) {%>
<a href="${path}/friend/onsaveDir?aa=1"><input type="button" value="确定保存分享文件" class="input-lg"></a>
<a href="${path}/friend/onsaveDir?aa=0"><input type="button" value="取消保存分享文件" class="input-lg"></a>
<%}%>
<%--确定保存共享区的分享的文件夹--------------------------------------------------------------%>
<% String onsaveDirs = (String) session.getAttribute("onsaveDirs");
    if (onsaveDirs != null) {%>
<a href="${path}/public/onsaveDirs?aa=1"><input type="button" value="确定保存分享文件" class="input-lg"></a>
<a href="${path}/public/onsaveDirs?aa=0"><input type="button" value="取消保存分享文件" class="input-lg"></a>
<%}%>
<%--分享文件夹——----------------------------------------------------------%>
<% String shareDir = (String) session.getAttribute("shareDir");
    if (shareDir != null) {%>
<table class="table table-hover">
    <thead>
    <tr>
        <th><h4>好友id</h4></th>
        <th><h4>好友名字</h4></th>
        <th><h4>操作</h4></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${friendList}" var="p">
        <tr>
            <td>
                <input type="button" value=" ${p.fuser.id}" onclick="sous('${p.id}')" class="input-lg">
            </td>
            <td><h4>${p.fuser.name}</h4></td>
            <td>
                <input type="button" value="分享" onclick="shareFriend2('${p.fuser.id}')" class="input-lg">
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="${path}/friend/shareDir?aa=0">
    <input type="button" value="取消分享" class="bg-primary input-lg">
</a>
<%}%>
<%--确定保存分享文件--------------------------------------------------------------%>
<% String onsave = (String) session.getAttribute("onsave");
    if (onsave != null) {%>
<a href="${path}/friend/onsave?aa=1"><input type="button" value="确定保存分享文件" class="input-lg"></a>
<a href="${path}/friend/onsave?aa=0"><input type="button" value="取消保存分享文件" class="input-lg"></a>
<%}%>
<%--确定保存共享区的分享文件--------------------------------------------------------------%>
<% String onsaves = (String) session.getAttribute("onsaves");
    if (onsaves != null) {%>
<a href="${path}/public/onsaves?aa=1"><input type="button" value="确定保存分享文件" class="input-lg"></a>
<a href="${path}/public/onsaves?aa=0"><input type="button" value="取消保存分享文件" class="input-lg"></a>
<%}%>
<%--分享文件——------------------------------------------------------------------%>
<% String sharefile = (String) session.getAttribute("sharefile");
    if (sharefile != null) {%>
<table class="table table-hover">
    <thead>
    <tr>
        <th><h4>好友id</h4></th>
        <th><h4>好友名字</h4></th>
        <th><h4>操作</h4></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${friendList}" var="p">
        <tr>
            <td>
                <input type="button" value=" ${p.fuser.id}" onclick="sous('${p.id}')" class="input-lg">
            </td>
            <td><h4>${p.fuser.name}</h4></td>
            <td>
                <input type="button" value="分享" onclick="shareFriend('${p.fuser.id}')" class="input-lg">
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="${path}/friend/sharefile?aa=0">
    <input type="button" value="取消分享" class="bg-primary input-lg">
</a>

<%}%>
<%---文件移动------------------------------------------------------------------------------------------------------%>
<% String fileYdid = (String) session.getAttribute("fileYdid");
    if (fileYdid != null) {%>
<a href="${path}/mydoc/fileYd?aa=1" style="color: red"><input type="button" value="确认移动"
                                                              class="btn bg-primary input-lg"> </a>
<a href="${path}/mydoc/fileYd?aa=0" style="color: red"><input type="button" value="取消移动"
                                                              class="btn bg-primary input-lg"> </a>
<%}%>
<%--文件夹移动------------------------------------------------------------------%>
<% String DirYd = (String) session.getAttribute("DirYd");
    if (DirYd != null) {%>
<a href="${path}/mydoc/DirYd?aa=1" style="color: red"><input type="button" value="移动确认" class="btn bg-primary input-lg">
</a>
<a href="${path}/mydoc/DirYd?aa=0" style="color: red"><input type="button" value="取消移动" class="btn bg-primary input-lg">
</a>
<%}%>
<%-----文件重命名----------------------------------------------------------------------------------------------------%>
<% String changFileName = (String) session.getAttribute("changFileName");
    if (changFileName != null) {%>
<input type="text" id="changFileName" value="${text_name}" class="input-lg">
<input type="button" value="重命名确认" class="btn bg-primary input-lg" onclick="changFileName()">
<a href="${path}/mydoc/changFileName" style="color: red"><input type="button" value="取消重命名"
                                                                class="btn bg-primary input-lg"> </a>
<%}%>
<%--文件夹重命名-------------------------------------------------------------%>
<% String changeDirName = (String) session.getAttribute("changeDirName");
    if (changeDirName != null) {%>
<input type="text" id="changeDirName" value="${text_name2}" class="input-lg">
<input type="button" value="重命名确认" class="btn bg-primary input-lg" onclick="changDirName()">
<a href="${path}/mydoc/changeDirName" style="color: red"><input type="button" value="取消重命名"
                                                                class="btn bg-primary input-lg"> </a>
<%}%>
<%---跳转设计------------------------------------------------------------------------------------------------------%>
<br>
<div style="clear: left"></div>
<c:forEach items="${tzList}" var="p">
    <div style="float: left">
        <p title="${p.id}" onclick="sous('${p.id}')" style="color: red"><h4>${p.name}></h4></p>
    </div>
</c:forEach>
<%--文件文件夹显示================================================================--%>
<table class="table table-hover">
    <thead>
    <tr>
        <th><h3>名字</h3></th>
        <th><h3>大小</h3></th>
        <th><h3>类型</h3></th>
        <th><h3>操作</h3></th>
    </tr>
    </thead>
    <tbody>

    <%--文件夹遍历-------------------------------------------------------------%>
    <c:forEach items="${dirs}" var="p">
        <tr>
            <td>
                <input type="button" class="input-lg" value=" ${p.name}" onclick="sous('${p.id}')">
            </td>
            <td></td>
            <td><h4>${p.icon}</h4></td>
            <td>
                <input type="button" value="删除" class="btn bg-primary input-lg" onclick="cancles('${p.id}')">
                <input type="button" value="移入隐藏文件夹" class="btn bg-primary input-lg" onclick="yrcancles('${p.id}')">
                <a href="${path}/mydoc/downloadFiles?id=${p.id}" style="float: left"
                   target="" onclick="onSub()">
                    <input type="button" value="下载" id="btnFiles" class="input-lg"> </a>&nbsp;&nbsp;
                <a href="${path}/mydoc/DirYd?id=${p.id}&aa=1" style="float: left"
                   onclick="onSub()">
                    <input type="button" value="移动" id="btnYd2" class="input-lg"> </a>&nbsp;
                <input type="button" value="重命名" class="btn bg-primary input-lg" title="${p.name}"
                       id="changeName12" alt="${p.name}"
                       onclick="changeName12('${p.id}')">
                <input type="button" value="分享" class="btn bg-primary input-lg" title="${p.name}"
                       alt="${p.name}"
                       onclick="shareDir('${p.id}')">
            </td>
        </tr>
    </c:forEach>

    <%--文件遍历---------------------------------------------------------------%>
    <c:forEach items="${files}" var="p" varStatus="abc">
        <tr>
            <td class="fileEntities" title="${p.id}"><h4>${p.name}</h4></td>
            <td><h4>${p.sizes}</h4></td>
            <td>
                    <%-- <c:if test="${p.icon.equals('图片')}">
                         <img src="${stringList.get(abc.index)}" width="20px" height="20px">
                     </c:if>
                     <c:if test="${!p.icon.equals('图片')}">--%>
                <h4>${p.icon}</h4>
                    <%-- </c:if>--%>
            </td>
            <td>
                <input type="button" value="删除" class="btn bg-primary input-lg" onclick="cancle('${p.id}')">
                <input type="button" value="移入隐藏文件夹" class="btn bg-primary input-lg" onclick="yrcancle('${p.id}')">
                    <%--文件下载(请求方式是Get,并且只能用表单请求，否则返回的是流格式 不会自动下载)--%>
                <a href="${path}/mydoc/down?id=${p.id}" style="float: left"
                   target="" onclick="onSub()">
                    <input type="button" value="下载" id="button" class="input-lg"> </a>&nbsp;&nbsp;

                <a href="${path}/mydoc/fileYd?id=${p.id}&aa=1" style="float: left"
                   onclick="onSub()">
                    <input type="button" value="移动" id="btnYd" class="input-lg"> </a>&nbsp;

                <input type="button" value="重命名" class="btn bg-primary input-lg" title="${p.name}"
                       id="changeName1" alt="${p.name}"
                       onclick="changeName1('${p.id}')">
                <input type="button" value="分享" class="btn bg-primary input-lg" title="${p.name}"
                       alt="${p.name}"
                       onclick="share('${p.id}')">
                    <%--  <c:if test="${p.icon}=='图片'">--%>
                <a href="${path}/mydoc/down?id=${p.id}&openStyle=inline" style="float: left"
                   target="" onclick="onSub()">
                    <input type="button" value="在线打开" id="btn" class="input-lg"> </a>&nbsp;&nbsp;
                    <%--  </c:if>--%>

                <script type="text/javascript">
                    function onSub() {
                        setTimeout(function () {
                            window.location.reload();
                        }, 1000);
                    }
                </script>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
