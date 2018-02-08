<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: yunara
  Date: 30/01/18
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Choix du QCM</title>
    <jsp:useBean id="listeQcm" type="java.util.Collection<modele.Questionnaire>" scope="request"/>
</head>
<body>
    Choisir un questionnaire:
    <c:forEach var="qcm" items="${listeQcm}" >
        <option>
        <a href="/run?action=lancerQcm&idQuestionnaire=${qcm.idQuestionnaire}">
           ${qcm.libelleQuestionnaire}
        </a>
    </c:forEach>
    <br>
</body>
</html>

