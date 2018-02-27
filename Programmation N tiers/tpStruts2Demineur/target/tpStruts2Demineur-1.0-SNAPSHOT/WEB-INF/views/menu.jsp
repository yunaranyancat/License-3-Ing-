<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yunara
  Date: 27/02/18
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="menu.pageTitle"></s:text></title>
</head>
<body>
<s:a action="commencer"><s:text name="menu.commencer"/></s:a><br>

<s:a action="deconnect"><s:text name="menu.deconnect"/></s:a>

</body>
</html>
