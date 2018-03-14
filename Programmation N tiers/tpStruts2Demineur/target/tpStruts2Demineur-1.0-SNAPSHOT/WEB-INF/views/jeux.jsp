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
<%--A vous de jouer!--%>

<table>
    <s:iterator value="%{plateau}" var="myPlateau" status="x">
        <tr>
            <s:iterator var="maCase" value="#myPlateau" status="y">
                <td>
                    <s:if test="#maCase.cachee">
                        <s:url action="jouer" var="url"></s:url>
                        <s:a href="%{url}">?</s:a>
                    </s:if>
                    <s:else>
                        <s:property value="#maCase.valeur"/>
                    </s:else>
                        <%--            <s:property value="#x.index"/>,
                                       <s:property value="#y.index"/>

                                       pour les images :
                                       <img src="img/box${maCase.valeur}">
                       --%>

                </td>
            </s:iterator>
        </tr>
    </s:iterator>
</table>
</body>
</html>
