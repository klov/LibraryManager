<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Список категорий</title>
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
    <body class="ng-cloak " ng-app="myApp" ng-controller="CategoryController as ctrl">
        <%@include file="/static/header.html"  %>
        <div class="generic-container " >
            <div class="panel panel-default">
                <div class="panel-heading"><span class="lead">Форма добавления категория</span></div>
                <div class="formcontainer">
                    <form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
                        <input type="hidden" ng-model="ctrl.bookType.id" />
                        <div class="row">
                            <div class="form-group col-md-12">
                                <label class="col-md-2 control-lable" for="name">Наименование</label>
                                <div class="col-md-7">
                                    <input type="text" ng-model="ctrl.category.name" 
                                           id="name" 
                                           class="btname form-control input-sm" 
                                           placeholder="Введите название категории" 
                                           required />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-actions floatRight">
                                <input type="submit"  
                                       value="{{!ctrl.category.id ? 'Добавить' : 'Изменить'}}" 
                                       class="btn btn-primary btn-sm" 
                                       ng-disabled="myForm.$invalid">
                                <button type="button" 
                                        ng-click="ctrl.reset()" 
                                        class="btn btn-warning btn-sm" 
                                        ng-disabled="myForm.$pristine">Сбросить</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div
            <!-- Default panel contents -->
            <div class=" form-group has-feedback  floatRight">
                <input ng-model="categoryName.$" type="text" class="form-control input-sm input-small" placeholder="Поиск  категории">                                           
                <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
            </div> 
            <div class="panel-heading"><span class="lead">Список категорий</span></div>
            <div class="tablecontainer" style="max-height:  60vh;">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Наименование</th>
                            <th width="15%"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="t in ctrl.categories|filter:categoryName">
                            <td><span ng-bind="t.name"></span></td>
                            <td>
                                <button type="button" ng-click="ctrl.edit(t)" 
                                        class="btn btn-success custom-width"
                                        style=" width: 90px !important;">Изменить</button>  
                                <button type="button" 
                                        ng-click="ctrl.deleteCategory(t)" 
                                        class="btn btn-danger custom-width">Удалить</button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <script src="<c:url value='/static/js/lib/angular.min.1.4.9.js' />"></script>
        <script src="<c:url value='/static/js/app.js' />"></script>
        <script src="<c:url value='/static/js/category/service.js' />"></script>
        <script src="<c:url value='/static/js/category/controller.js' />"></script>
    </body>
</html>
