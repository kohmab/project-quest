<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Start page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-sm">
    <p class="h1 text-center">Simple quest</p>

    <br/>

    <form id="nameForm" method="post" action="newUser">
        <div class="input-group mb-3">
            <input type="text" class="form-control" name="userName" placeholder="Your name"
                   aria-describedby="button-addon2">
            <button class="btn btn-outline-secondary" type="submit" id="button-addon2">Start</button>
        </div>
    </form>

</div>
</body>
</html>