<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div>
    <div id="questionText">
        ${requestScope.question}
    </div>
    <div id="controls">
        <form action="quest" method="POST">
            <c:forEach var="entry" items="${requestScope.nextKeysAndActions}">
                <button type="submit" name="edgeKey" value="${entry.key}">${entry.value}</button>
            </c:forEach>
            <c:if test="${sessionScope.currentEdgeKey == null}">
                <button type="submit" name="edgeKey">Начать заново</button>
            </c:if>
        </form>
    </div>
    <div id="stats">
        User name : ${sessionScope.user.name}<br>
        games Won : ${sessionScope.user.gamesWon}<br>
        games Played : ${sessionScope.user.gamesPlayed}<br>
        <form action="reset" method="get">
            <button type="submit" class="btn btn-primary" id="startButton">Сброс</button>
        </form>

    </div>

</div>

</body>
</html>
