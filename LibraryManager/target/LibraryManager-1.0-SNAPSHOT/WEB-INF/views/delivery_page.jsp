<%@ page session="false" isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Список выдачей</title>
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
        <link href="<c:url value='/static/css/lib/smoke.min.css' />" rel="stylesheet"></link>   
        <link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
        <script src="<c:url value='/static/js/lib/jquery-1.12.0.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/jquery-ui.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/smoke.min.js' />"></script>
    </head>
    <body class="ng-cloak " ng-app="myApp" ng-controller="DeliveryController as ctrl">
        <!-- editing form  -->
        <div class="modal fade " id="myModal" role="dialog" aria-labelledby="myModal">
            <div class="my-small-dialog"  role="document">
                <div class="modal-content ">
                    <div class="modal-header">
                        <h4 class="modal-title" id="myModalLabel">Редактирование выдачи</h4>
                    </div>
                    <div>       
                        <div role="tabpanel" class="tab-pane active" id="documentLoading" > 
                            <div class="modal-body">
                                <div class="form-horizontal">
                                    <div class="row form-element">
                                        <label class="col-md-offset-2 col-sm-2 control-label ">Пользователь</label>
                                        <div class="col-md-5">
                                            <input  ng-model="ctrl.selectedDel.eployeeName" type="text" class="form-control"   list="employeeList" required="true" >
                                            <datalist id="employeeList">
                                                <option  ng-repeat="u in ctrl.users" value="{{ctrl.fio(u)}}">
                                            </datalist>
                                        </div>
                                    </div>
                                    <div class="row form-element">                                                                           
                                        <label class="col-md-offset-2 col-md-2 control-label">Дата выдачи</label>
                                        <span>{{ctrl.selectDelivery.deliverydate}}</span>
                                        <div class="col-md-offset-2 col-md-3 ">
                                            <div class="   input-group">                                           
                                                <div class=" input-group date form_date">
                                                    <input type='text' class="form-control" ng-model="ctrl.selectedDel.deliverydate"  />
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row form-element">   
                                        <label class="col-md-offset-2 col-md-2 control-label">Дата возврата</label>
                                        <div class="col-md-offset-2 col-md-3 ">
                                            <div class=" input-group">                                      
                                                <div class=" input-group date form_date">
                                                    <input type='text' class="form-control" ng-model="ctrl.selectedDel.surrenderdate"/>
                                                    <span class="input-group-addon">
                                                        <span class="glyphicon glyphicon-calendar"></span>
                                                    </span>
                                                </div>
                                            </div> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button"  data-dismiss="modal"  class="btn btn-primary" ng-click="ctrl.updatDelivery(ctrl.selectedDel)">Сохранить</button>
                        <button type="button"   data-dismiss="modal" class="btn btn-warning" ng-click="ctrl.reset()" >Сбросить</button>
                    </div>                        
                </div>
            </div>
        </div>
        <div class="generic-container " style="background-color: #ffffff" >
            <div class="form-group">
                <div class="row">
                    <div class="row" ng-if="{{ctrl.selectedDel.id != null}}" >                                                                           
                        <span class="col-md-3 ">Дата выдачи</span>

                        <div class="col-md-3">
                            <div class="   input-group">                                           
                                <div class=" input-group date form_date">
                                    <input type='text' class="form-control" ng-model="ctrl.selectedDel.deliverydate"  />
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <span class="col-md-3">Дата возврата</span>
                        <div class="col-md-3">
                            <div class=" input-group">                                      
                                <div class=" input-group date form_date">
                                    <input type='text' class="form-control" ng-model="ctrl.selectedDel.surrenderdate"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-calendar"></span>
                                    </span>
                                </div>
                            </div> 
                        </div>
                    </div>
                    <label class="col-md-1">Имя книг:</label>
                    <div class="col-md-3" >
                        <textarea ng-model="ctrl.book.name" cols="20" class="form-control " rows="8" readonly=true  >
                        </textarea>
                    </div>
                </div>
                <div class="row" style="margin-top:  5px;">
                    <div class="col-md-1">
                        <label >Инвентарный номер:</label>
                    </div>
                    <div class="col-md-3">
                        <label ng-bind="ctrl.book.inventorynumber"></label>                 
                    </div>
                </div>
            </div>           
            <div class="panel-heading"><span class="lead">Список выдачи:</span></div>
            <div class="tablecontainer">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>ФИО</th>
                            <th>Дата выдачи</th>
                            <th>Дата возврата</th>
                            <th width="10%"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="t in ctrl.deliveries">
                            <td><span ng-bind="ctrl.findEmployeeName(t.employeeid)"></span></td>
                            <td><span ng-bind="t.deliverydate"></span></td>
                            <td><span ng-bind="t.surrenderdate"></span></td>
                            <td>
                                <button type="button" ng-click="ctrl.editDelivery(t)" 
                                        class="edit-button btn btn-success custom-width " >Редактировать</button>  
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <script src="<c:url value='/static/js/lib/angular.min.1.4.9.js' />"></script>
        <script src="<c:url value='/static/js/app.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap-datetimepicker.min.js' />"></script>
        <script src="<c:url value='/static/js/properties.js' />"></script>
        <script src="<c:url value='/static/js/delivery/service.js' />"></script>
        <script src="<c:url value='/static/js/delivery/controller.js' />"></script>
        <script src="<c:url value='/static/js/delivery/script.js' />"></script>
    </body>
</html>
