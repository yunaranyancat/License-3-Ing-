<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yunara
  Date: 20/02/18
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="repondre.PageTitle"/></title>
</head>
<body>

Question : <s:property value="%{questionCourante.question}"/> =

<ul>
    <s:iterator value="%{questionCourante.reponsesPossibles}" var="rp">
        <li>
            <s:url action="validerQuestion" var="valQuest">
                <s:param value="%{questionCourante.idQuestion}" name="idQuestion"/>
                <s:param value="#rp" name="reponse"/>
            </s:url>
            <s:a href="%{valQuest}"><s:property value="#rp"/></s:a>
        </li>
    </s:iterator>
</ul>

</body>
</html>
