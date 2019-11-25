<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Art1985
  Date: 24.11.2019
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart list</title>
</head>
<body>
    <h1 align="canter">List of all shopping carts</h1>
    <table border="2" width="100%">
        <tr>
            <th>Name</th>
            <th>Products</th>
            <th>Delete</th>
            <th>Manage</th>
        </tr>
        <c:forEach var="cart" items="${carts}">
            <tr>
                <td>${cart.name}</td>
                <td>
                    <c:if test="${not empty cart.productDTOList}">
                    <table border="1" width="100%">
                        <tr>
                            <th>Name</th>
                            <th>Category</th>
                            <th>Price</th>
                            <th>Discount</th>
                            <th>Description</th>
                        </tr>
                        <c:forEach var="product" items="${cart.productDTOList}">
                            <tr>
                                <td>${product.name}</td>
                                <td>${product.category}</td>
                                <td>${product.price}</td>
                                <td>${product.discount}</td>
                                <td>${product.description}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    </c:if>
                </td>
                <td align="center">
                    <a href="/delete_cart/${cart.id}">Delete cart</a>
                </td>
                <td align="center">
                    <a href="/manage_cart/${cart.id}">Manage products</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
