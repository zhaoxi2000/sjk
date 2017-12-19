<%@ page language="java" pageEncoding="UTF-8"%>
<%
final String path = request.getContextPath();
final String basePath = new StringBuilder(200).append(request.getScheme())
.append("://").append(request.getServerName()).append(":").append(request.getServerPort())
.append(path).append("/").toString();
// 放入EL
pageContext.setAttribute("path",path);
pageContext.setAttribute("basePath",basePath);
%>