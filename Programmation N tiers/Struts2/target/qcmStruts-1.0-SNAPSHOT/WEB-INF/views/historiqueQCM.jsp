<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yunara
  Date: 20/02/18
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="historique.PageTitle"/></title>
</head>
<body>

<ul>
<s:iterator value="%{qcms}" var="q">
    <li>
    <s:url action="detailCorrection" var="url1">
        <s:param name="idQuestionnaire" value="#q.idQuestionnaire"/>
    </s:url>
        <s:a href="%{url1}"><s:property value="#q.libelleQuestionnaire"/></s:a>
    </li>
</s:iterator>
</ul>
</body>
</html>
