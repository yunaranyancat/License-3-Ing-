<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yunara
  Date: 27/02/18
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><s:text name="jeux.pageTitle"/></title>
</head>
<body>
A vous de jouer!
<s:property value="%{Plateau}"/>
</body>
</html>
