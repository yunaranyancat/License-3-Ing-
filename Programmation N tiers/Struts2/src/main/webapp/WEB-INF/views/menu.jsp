<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yunara
  Date: 13/02/18
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="menu.pageTitle"></s:text></title>
</head>
<body>
<s:a action="choixQCM"><s:text name="menu.choixQCM"/></s:a><br>

<s:a action="historique"><s:text name="menu.histoQCM"/></s:a><br>

<s:a action="deconnect"><s:text name="menu.deconnect"/></s:a><br>
</body>
</html>
