<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: yunara
  Date: 13/02/18
  Time: 14:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <s:head/>
    <title>Page de connection</title>
</head>
<body>

<s:form action="connect">
    <s:textfield name="login" key="connect.login"/>
    <s:password name="password" key="connect.password"/>
    <s:submit key="connect.submit"/>
</s:form>

</body>
</html>
