
<%--
  Created by IntelliJ IDEA.
  User: Art1985
  Date: 24.11.2019
  Time: 1:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Product list</title>
</head>
<body>
    <h1 align="center">List of all products</h1>
    <table border="2" width="100%">
        <tr>
            <th>Name</th>
            <th>Category</th>
            <th>Price</th>
            <th>Discount</th>
            <th>Description</th>
            <th>Delete</th>
        </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.name}</td>
                <td>${product.category}</td>
                <td>${product.price}</td>
                <td>${product.discount}</td>
                <td>${product.description}</td>
                <td><a href="/delete_product/${product.id}">Delete ${product.name}</a> </td>
            </tr>
        </c:forEach>
    </table>

    <form action="${pageContext.request.contextPath}/product" method="get">
        <input type="submit" value="Return to product menu">
    </form>
</body>
</html>
