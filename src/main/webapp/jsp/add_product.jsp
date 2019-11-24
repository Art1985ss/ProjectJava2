<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Art1985
  Date: 24.11.2019
  Time: 3:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
    <h1>Add product</h1>
    <form:form method="post" action="save">
        <table>
            <tr>
                <td></td>
                <td><form:hidden path="id"/> </td>
            </tr>
            <tr>
                <td>Name</td>
                <td><form:input path="name"/></td>
            </tr>
            <tr>
                <td>Category</td>
                <td><form:input path="category"/></td>
            </tr>
            <tr>
                <td>Price</td>
                <td><form:input path="price"/></td>
            </tr>
            <tr>
                <td>Discount</td>
                <td><form:input path="discount"/></td>
            </tr>
            <tr>
                <td>Description</td>
                <td><form:input path="description"/></td>
            </tr>
            <tr>
                <td> </td>
                <td><input type="submit" value="Save" /></td>
            </tr>
        </table>
    </form:form>
</body>
</html>
