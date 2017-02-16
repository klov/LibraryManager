<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Список организаций</title>
        <style>
            .btname.ng-valid {
                background-color: lightgreen;
            }
            .btname.ng-dirty.ng-invalid-required {
                background-color: red;
            }
            .btname.ng-dirty.ng-invalid-minlength {
                background-color: yellow;
            }
        </style>
        <meta name="_csrf" content="${_csrf.token}"/>
        <meta name="_csrf_header" content="${_csrf.headerName}"/>
        <link href="<c:url value='/static/css/lib/bootstrap.min.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/lib/bootstrap-theme.min.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
        <script src="<c:url value='/static/js/lib/jquery-1.12.0.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/jquery-ui.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap.min.js' />"></script>        
        <script src="<c:url value='/static/js/lib/smoke.min.js' />"></script>
    </head>
    <body class="ng-cloak " ng-app="myApp">
        <%@include file="/static/header.html"  %>
        <div class="generic-container " ng-controller="OrganizationController as ctrl">
            <!-- Default panel contents -->
            <div class=" form-group has-feedback  floatRight">
                <input ng-model="organizationName.$" type="text" class="form-control input-sm input-small" placeholder="Поиск  организации">                                           
                <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
            </div> 
            <div class="panel-heading"><span class="lead">Список организаций</span></div>
            <div class="tablecontainer" style="max-height:  60vh;">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Наименование</th>
                            <th>Адрес</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="t in ctrl.organizations|filter:organizationName">
                            <td><span ng-bind="t.name"></span></td>
                            <td><span ng-bind="t.address"></span></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <script src="<c:url value='/static/js/lib/angular.min.1.4.9.js' />"></script>
        <script src="<c:url value='/static/js/app.js' />"></script>
        <script src="<c:url value='/static/js/organization/service.js' />"></script>
        <script src="<c:url value='/static/js/organization/controller.js' />"></script>
    </body>
</html>
