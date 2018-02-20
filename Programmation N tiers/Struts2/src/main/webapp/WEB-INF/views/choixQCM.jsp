<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yunara
  Date: 20/02/18
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="choixQCM.pageTitle"/></title>
</head>
<body>

<form action="repondreQCM">
    <s:select list="%{qcms}" listKey="iqQuestionnaire" listValue="libelleQuestionnaire" key="choixQCM.envoyer"/>
    <s:submit/>
</form>

</body>
</html>
