<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../base.jsp"%>
<!DOCTYPE>
<html>
<head>
    <!--IE doesnot support relative path.-->
    <base href="<%=basePath%>" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>搜索词维护</title>
</head>
<body>
    <form action="admin/keyword/save.d" method="post" enctype="multipart/form-data" accept-charset="UTF-8" id="formData">
        <div>
            <label>原始搜索词</label><input id="name" name="name" type="text" />
            <label>排序方式</label>
            <select name="rankType">
                <option value="DOCUMENT" selected="selected">文档相关度</option>
                <option value="DOWNLOAD">下载量排序</option>
            </select>
            <label>目标搜索词</label><input id="targetKeyword" name="targetKeyword" type="text" />
            <button type="submit">保存</button>
        </div>
        <input name="totalHits" value="0" />
    </form>
</body>
</html>