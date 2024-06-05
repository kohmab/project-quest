<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<div class="form-container">
    <form id="nameForm" method="post" action="user">
        <input type="text" class="form-control" id="userName" name="userName" placeholder="Ваше имя" minlength="2" maxlength="10">
        <button type="submit" class="btn btn-primary" id="startButton">Приступить</button>
    </form>
</div>
</body>
</html>