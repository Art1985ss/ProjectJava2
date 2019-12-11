<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Art1985
  Date: 24.11.2019
  Time: 1:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
    <h1>Product options</h1>
    <a href="${pageContext.request.contextPath}/add_product">Add product</a><br>
    <a href="find_product">Find products</a><br>
    <a href="${pageContext.request.contextPath}/products">Show all products</a><br>
</body>
</html>
