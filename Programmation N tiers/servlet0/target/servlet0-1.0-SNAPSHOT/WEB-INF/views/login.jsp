<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yunara
  Date: 30/01/18
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Page de login</title>
</head>
<body>

<c:if test="${!empty message}">
    <p>${message}</p>

</c:if>

<form method="post" action="/run?action=connect">
    Saisir votre login :<br>
    <input type="text" name="login"><br>
    Saisir votre mot de passe:<br>
    <input type="password" name="password"><br>
    <input type="submit" value="Se connecter"><br>
</form>


</body>
</html>
