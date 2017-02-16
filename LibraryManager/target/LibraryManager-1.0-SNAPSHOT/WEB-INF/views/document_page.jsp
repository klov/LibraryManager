<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Документы</title>
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
        <link href="<c:url value='/static/css/lib/jquery.treemenu.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/lib/bootstrap-datetimepicker.min.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/lib/smoke.min.css' />" rel="stylesheet"></link> 
        <script src="<c:url value='/static/js/lib/jquery-1.12.0.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/jquery-ui.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap.js' />"></script>
        <script src="<c:url value='/static/js/lib/jquery.treemenu.js' />"></script>
        <script src="<c:url value='/static/js/lib/smoke.min.js' />"></script>
    </head>
    <body class="ng-cloak " ng-app="myApp" ng-controller="DocumentController as ctrl">
        <%@include file="/static/header.html"  %>
        <div class="row">
            <div class="generic-container">
                <div class="modal-header">
                    <h4 class="modal-title" id="myModalLabel">Изменение</h4>
                </div>
                <div>
                    <div role="tabpanel" class="tab-pane active" id="paginalLoading" >
                        <div class="modal-body">
                            <div class="form-horizontal">
                                <div class="row ">
                                    <div class="col-md-3">
                                        <div class="row">                                                
                                            <div class="col-md-5" >
                                                <label class="btn btn-default btn-file" id="addedButton">Добавить страницы
                                                    <input type="file"                                                              
                                                           onchange= "angular.element(this).scope().loadFile(this)" 
                                                           id="pdfDocument" >
                                                </label>                                                
                                            </div>     
                                            <div class="col-md-6">
                                                <label>Сортировать по номеру страницы
                                                    <input type="checkbox" autocomplete="off" ng-model="sortFlag"> 
                                                </label>
                                            </div>
                                        </div>
                                        <div class="row" >
                                            <div class="col-md-5" >
                                                <input type="button"  value="Аннулировать страницу"  ng-disabled="!ctrl.newPage.name" ng-click="ctrl.deletePageFromBook(pageList)" class=" btn btn-danger" >                                         
                                            </div>
                                            <div class="col-md-5">                                                
                                                <input  type="text" placeholder="Номер страницы"  class="form-control " ng-model="ctrl.newPage.name" id="pageName" required="true">
                                            </div>                                                
                                        </div>
                                        <div class="row">
                                            <div  class="col-md-10 block large-block" style="margin-bottom: 5px;">
                                                <table class="table table-bordered">
                                                    <tr ng-repeat=" p in pageList|orderBy:orderName():false:localeComparator" class="rows">
                                                        <td ng-click="ctrl.viewPage(p)"><spen   ng-model="p.name" contenteditable="true" ></spen></td>
                                                    <td ng-click="ctrl.viewPage(p)"><spen  ng-bind="p.fileName" ></spen></td>
                                                    <td width="5em">
                                                        <button type="button" 
                                                                class="btn btn-xs btn-primary dropdown-toggle"
                                                                ng-click="ctrl.deletePageFromList(p, pageList)" 
                                                                aria-expanded="false"
                                                                >
                                                            <span class="glyphicon glyphicon-remove"></span>
                                                        </button>
                                                    </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <button class="btn btn-primary btn-xs center-block" ng-click="ctrl.autonumberingPages()">Автонумерация страниц</button>
                                        </div>
                                    </div>
                                    <div class="col-md-8">
                                        <object  data="{{selectFile.fileURL}}" type="application/pdf" class="pdf-frame" >
                                            alt : <a href="{{selectFile.fileURL}}">test.pdf</a>
                                        </object>
                                        <button class="btn btn-success center-block" ng-click="ctrl.formidBook()" ng-disabled="ctrl.fileIsAdded"><span>Показать книгу</span></button>
                                    </div>

                                </div>
                                <div  class="row">

                                </div>
                            </div>
                        </div>
                        <div class="containerform">
                            <form class="form-horizontal" name="newDocumentForm">
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <label class="control-lable ">Входящий №</label>
                                                    <input type="text"   class=" input-small form-control" ng-model="ctrl.document.incomenumber" required="true" >
                                                </div>
                                                <div class=" col-md-3 input-group">
                                                    <label class="control-lable ">Дата входящего</label>
                                                    <div class=" input-group date form_date">
                                                        <input type='text'  class="form-control"ng-model="ctrl.document.incomedate" required="true" />
                                                        <span class="input-group-addon" >
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-md-5">
                                                    <label class="control-lable">Исходящий №</label>
                                                    <input type="text"                                                           
                                                           class="input-small form-control" 
                                                           ng-model="ctrl.document.outcomenumber" 
                                                           required="true" >
                                                </div>
                                                <div class=" col-md-3 input-group ">
                                                    <label class="control-lable ">Дата исходящего</label>
                                                    <div class=" input-group date form_date" >
                                                        <input type='text' class="form-control"  ng-model="ctrl.document.outcomedate" required="true"  />
                                                        <span class="input-group-addon" >
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>
                                                </div>                                         
                                            </div>
                                            <div class="row">                                                    
                                                <div class=" col-md-5 ">
                                                    <label class="control-lable">№ извещения:</label>
                                                    <input type="text" class="input-small form-control"  ng-model="ctrl.document.notice" required="true" >
                                                </div>
                                                <div class=" col-md-3 input-group ">
                                                    <label class="control-lable ">Дата извещения</label>
                                                    <div class=" input-group date form_date" >
                                                        <input type='text' class="form-control"   ng-model="ctrl.document.noticedate" required="true"  />
                                                        <span class="input-group-addon" >
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>
                                                </div>  
                                            </div>  
                                            <div class="row">
                                                <div class="col-md-4">
                                                    <label class="control-lable" >№ изменения:</label>
                                                    <input type="number" min="0" class="input-small form-control"  ng-model="ctrl.document.modification" required="true" >
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class=" col-md-2  ">
                                                    <label class="control-lable">Количество листов:</label>
                                                    <input type="number" min="0"  ng-model="ctrl.document.listcount"  class="form-control " required="true" >
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-6">
                                            <div class="row">
                                                <div class="col-md-11">
                                                    <label>Примечание</label>
                                                    <textarea class="form-control" rows="3" ng-model="ctrl.newComment.content">                                                        
                                                    </textarea>
                                                    <button class="btn btn-success btn-sm form-element right" ng-click="ctrl.addCommentToTheDocument(ctrl.newComment, ctrl.document)">Добавить</button>
                                                </div>
                                            </div>
                                            <div class="row ">
                                                <div class=" col-md-11">
                                                    <div class="grouping-block"">
                                                        <div>
                                                            <div ng-repeat="c in ctrl.document.comments">
                                                                <div class="row col-md-offset-9 col-md-3">
                                                                    <span >{{c.date}}</span>
                                                                    <button class=" btn btn-xs right" ng-click="ctrl.removeCommentFromDocument(c, ctrl.document.comments)">
                                                                        <span class="glyphicon glyphicon-remove"></span>
                                                                    </button>
                                                                </div>
                                                                <div>
                                                                    <span>{{c.content}}</span>
                                                                </div>
                                                                <hr>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>                       
                    <div class="row ">
                        <hr>
                        <button type="button"   
                                ng-if="ctrl.document.modification != 0"
                                class="btn btn-danger "   
                                ng-click="ctrl.deleteDocument(ctrl.document)" >                     
                            <span>Удалить изменение</span>
                        </button>
                        <button type="button" id="saveButton" 
                                class="btn btn-success floatRight"   
                                ng-click="ctrl.processingFunction('saveButton', ctrl.savingChanges)" 
                                ng-disabled="ctrl.saveIsActive()"
                                >                     
                            Сохранить
                        </button>
                    </div>                    
                </div>                
            </div>
        </div>
        <script src="<c:url value='/static/js/lib/bootstrap.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/angular.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap-datetimepicker.min.js' />"></script>
        <script src="<c:url value='/static/js/app.js' />"></script>
        <script src="<c:url value='/static/js/document/script.js' />"></script>
        <script src="<c:url value='/static/js/document/service.js' />"></script>
        <script src="<c:url value='/static/js/lib/directive.js' />"></script>
        <script src="<c:url value='/static/js/document/controller.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap-datetimepicker.js' />"></script>
    </body>
</html>
