<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Le titre</title>
</head>
<body>
<p>EL activée ? ${3 + 4}</p>
<a href="/hello">Lancer la servlet à la main</a>
<c:redirect url="/run?action=login"></c:redirect>
</body>
</html>