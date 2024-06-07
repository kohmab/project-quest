<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>${initParam['title']}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body class="d-flex align-items-center justify-content-center vh-100 text-center p-3">

<div class="container-sm">
    <div class="row">
        <div class="col">
            <p class="h1 ">${initParam['title']}</p>
        </div>
    </div>
    <div class="row justify-content-md-center">
        <div class="col col-3 m-3">
            <form class="input-group " method="post" action="newUser">
                <input type="text" class="form-control" name="userName"
                       placeholder="${initParam['userNamePlaceholder']}"
                       aria-describedby="button-addon2">
                <button class="btn btn-primary" type="submit"
                        id="button-addon2">${initParam['startButtonCaption']}</button>
            </form>
        </div>
    </div>


</div>
</body>
</html>