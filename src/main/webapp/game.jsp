<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Game</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<form class="container-sm text-center" action="quest" method="POST">
        <div class="row">
            <div class="col">
                <p class="h1 text-center">${requestScope.question}</p>
            </div>
        </div>

        <div class="row">
            <c:forEach var="entry" items="${requestScope.nextKeysAndActions}">
                <div class="col">
                    <button class="btn btn-warning" type="submit" name="edgeKey"
                            value="${entry.key}">${entry.value}</button>
                </div>
            </c:forEach>
            <c:if test="${sessionScope.currentEdgeKey == null}">
                <div class="col">
                    <button class="btn btn-danger" type="submit" name="edgeKey">Начать заново</button>
                </div>
            </c:if>

        </div>
</form>

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
