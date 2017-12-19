<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../../base.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <base href="<%=basePath%>" />
        <title>操作结果,自动关闭</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    </head>
    <body>
        <c:out value="操作" />
        <c:choose>
            <c:when test="${rstCode == 0}">
                <c:out value="操作成功!1秒后自动关闭..." />
                <script type="text/javascript">
                    (function() {
                        window.close()
                    })();
                </script>
            </c:when>
            <c:otherwise>
                <c:out value="失败!" />
                失败原因：${rstMsg}
            </c:otherwise>
        </c:choose>
    </body>
</html>