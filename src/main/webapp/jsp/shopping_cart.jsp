<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Art1985
  Date: 24.11.2019
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart menu</title>
</head>
<body>
    <h1 align="center">Shopping cart menu</h1>
    <table align="center">
        <tr>
            <form action="${pageContext.request.contextPath}/save_cart" method="post">
                <td>Enter cart name to create new</td>
                <td><input type="text" name="name"/></td>
                <td><input type="submit" value="Create shopping cart"/></td>
            </form>
        </tr>
        <tr>
            <form action="${pageContext.request.contextPath}/shopping_cart/find_byName" method="get">
                <td>Enter cart name you want to find</td>
                <td><input type="text" name="name"/></td>
                <td><input type="submit" value="Find by name"/></td>
            </form>
        </tr>
        <tr>
            <form action="${pageContext.request.contextPath}/shopping_cart/find_byID" method="get">
                <td>Enter cart if you wish to find</td>
                <td><input type="number" name="id"/></td>
                <td><input type="submit" value="Find by id"></td>
            </form>
        </tr>
    </table>
    <div align="center">
        <a href="/shopping_carts">Show all shopping carts</a>
    </div>
</body>
</html>
