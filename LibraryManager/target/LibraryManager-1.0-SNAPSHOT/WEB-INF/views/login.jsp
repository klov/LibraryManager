<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <link href="<c:url value='/static/css/lib/bootstrap.min.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/lib/bootstrap-theme.min.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
    </head>
    <body>
        <div class="container">           
            <form class="form-signin" action="/LibraryManager/login" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div>
                    <h2 class="form-signin-heading">Авторизация</h2>
                    <input type="text" id="username" name="username"  class="form-control" placeholder="Имя пользователя">
                </div>
                <div>
                <input type="password" id="password" name="password" class="form-control" placeholder="Пароль">
                </div>
                <button class="btn btn-primary btn-block" type="submit">Вход</button>
            </form>
        </div> 
    </body>
    <script src="<c:url value='/static/js/lib/jquery-1.12.0.min.js' />"></script>
    <script src="<c:url value='/static/js/lib/jquery-ui.min.js' />"></script>
    <script src="<c:url value='/static/js/lib/bootstrap.min.js' />"></script>
</html>
