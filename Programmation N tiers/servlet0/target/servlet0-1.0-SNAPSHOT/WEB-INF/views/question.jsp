<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yunara
  Date: 30/01/18
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Question</title>
    <jsp:useBean id="laQuestion" type="modele.QuestionReponse" scope="request"/>
</head>
<body>

<h1>${laQuestion.question}</h1>
<form action="/run?action=repondreQuestion" method="post">
<c:forEach items="${laQuestion.reponsesPossibles}" var="rep">
    <input type="hidden" name="idQuest" value="${laQuestion.idQuestion}">
    <input type="radio" name="reponse" id="${rep}">${rep}<br>
</c:forEach>
    <input type="submit" value="Repondre">
<c:if test="${!empty erreur}">
    <p>${erreur}</p>
</c:if>
</form>
</body>
</html>
