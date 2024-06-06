<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>${initParam['title']}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="d-flex align-items-center justify-content-center vh-100 text-center p-3">

<form class="input-group fixed-top m-3" action="reset" method="get">
    <span class="input-group-text text-primary">${initParam['sessionInfoCaption']}</span>
    <span class="input-group-text">${initParam['sessionInfoUserUsernameCaption']} : ${sessionScope.user.name}</span>
    <span class="input-group-text">${initParam['sessionInfoGamesWonCaption']} : ${sessionScope.user.gamesWon}</span>
    <span class="input-group-text">${initParam['sessionInfoGamesPlayedCaption']} : ${sessionScope.user.gamesPlayed}</span>
    <button type="submit" class="btn btn-outline-primary">${initParam['resetButtonCaption']}</button>
</form>


<form class="container-sm" action="quest" method="POST">
    <div class="row">
        <div class="col">
            <p class="h1 ">${requestScope.question}</p>
        </div>
    </div>

    <div class="row">
        <c:forEach var="entry" items="${requestScope.nextKeysAndActions}">
            <div class="col">
                <button class="btn btn-warning" type="submit" name="edgeKey"
                        value="${entry.key}">${entry.value}</button>
            </div>
        </c:forEach>
        <c:if test="${sessionScope.user.state.name == 'win'}">
            <div class="col">
                <button class="btn btn-success" type="submit"
                        name="edgeKey">${initParam['restartButtonCaption']}</button>
            </div>
        </c:if>
        <c:if test="${sessionScope.user.state.name == 'defeat'}">
            <div class="col">
                <button class="btn btn-danger" type="submit"
                        name="edgeKey">${initParam['restartButtonCaption']}</button>
            </div>
        </c:if>
    </div>
</form>


</body>
</html>
