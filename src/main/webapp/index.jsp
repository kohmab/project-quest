<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Start page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1> Simple quest </h1>
    <br/>
    <div class="form-container">
        <form id="nameForm" method="post" action="newUser">
            <input type="text" class="form-control" id="userName" name="userName" placeholder="Ваше имя" minlength="2"
                   maxlength="10">
            <button type="submit" class="btn btn-primary" id="startButton">Приступить</button>
        </form>
    </div>
</div>
</body>
</html>