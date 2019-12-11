<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Art1985
  Date: 24.11.2019
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Find product</title>
</head>
<body>
    <h1 align="center">Find product</h1>
    <table align="center">
        <tr>
            <form action="${pageContext.request.contextPath}/find_by_id" method="get" name="id">
                <td>Enter id</td>
                <td><input type="number" name="id"/></td>
                <td><input type="submit" value="Search by id"></td>
            </form>
        </tr>
        <tr>
            <form action="${pageContext.request.contextPath}/find_by_name" method="get" name="name">
                <td>Enter name</td>
                <td><input type="text" name="name"/></td>
                <td><input type="submit" value="Search by name"/></td>
            </form>
        </tr>
    </table>
</body>
</html>
