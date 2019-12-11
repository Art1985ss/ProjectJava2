<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Art1985
  Date: 25.11.2019
  Time: 0:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart management</title>
</head>
<body>
    <h1>Manage your ${cart.name} products</h1>
    <table border="2" width="50%" style="float: left">
        <tr>
            <th>Name</th>
            <th>Category</th>
            <th>Price</th>
            <th>Discount</th>
            <th>Description</th>
            <th>Remove</th>
        </tr>
        <c:forEach var="p" items="${cart.productDTOList}">
            <tr>
                <td>${p.name}</td>
                <td>${p.category}</td>
                <td>${p.price}</td>
                <td>${p.discount}</td>
                <td>${p.description}</td>
                <td><a href="/remove_product/${cart.id}/${p.id}">Remove</a> </td>
            </tr>
        </c:forEach>
    </table>
    <table border="2" width="50%" style="float: left">
        <tr>
            <th>Name</th>
            <th>Category</th>
            <th>Price</th>
            <th>Discount</th>
            <th>Description</th>
            <th>Add to cart</th>
        </tr>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.name}</td>
                <td>${p.category}</td>
                <td>${p.price}</td>
                <td>${p.discount}</td>
                <td>${p.description}</td>
                <td><a href="/add_product/${cart.id}/${p.id}">Add to cart</a> </td>
            </tr>
        </c:forEach>
    </table>
    Total product price = ${total_price}
</body>
</html>
