<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Книги</title>
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
        <link href="<c:url value='/static/css/lib/tree-control-attribute.css' />" rel="stylesheet"></link>
        <link href="<c:url value='/static/css/lib/tree-control.css' />" rel="stylesheet"></link>   
        <link href="<c:url value='/static/css/lib/smoke.min.css' />" rel="stylesheet"></link>   
        <script src="<c:url value='/static/js/lib/jquery.js' />"></script>
        <script src="<c:url value='/static/js/lib/jquery-ui.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap.js' />"></script>
        <script src="<c:url value='/static/js/lib/smoke.min.js' />"></script>

    </head>
    <body class="ng-cloak " ng-app="myApp" ng-controller="BookController  as ctrl">
        <%@include file="/static/header.html"  %>

        <div class="generic-container" >            
            <div role="tabpanel" class="tab-pane active" id="reviewBook">
                <div class=" row"  >
                    <div class="col-md-2" >
                        <div class="navigationTreeContainer">
                            <div ng-repeat="c in ctrl.categories">
                                <button class="btn tag  btn-xs" ng-class="[{'btn-primary':!ctrl.checkSelectCategory(c)}, {'btn-danger':ctrl.checkSelectCategory(c)}]" categoryId={{c.id}} ng-click="ctrl.selectTag(c)">
                                    <span>{{c.name}}</span>
                                    <span class="glyphicon glyphicon-tag"></span>
                                </button>   
                            </div>
                        </div>
                    </div>
                    <div class="col-md-10">
                        <div class="row">
                            <div class=" form-group has-feedback  floatRight">
                                <input ng-model="bookName.$" type="text" class="form-control input-sm input-small" placeholder="Поиск  книги">                                           
                                <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
                            </div> 
                        </div>
                        <div class="tablecontainer">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>Наименование</th>
                                        <th>Инвентарный номер</th>
                                        <th width="15%"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr ng-repeat="t in ctrl.books| filter:ctrl.filterByCategory|filter:bookName">
                                        <td>
                                            <span ng-bind="t.name"></span>
                                            <div>
                                                <span  ng-repeat="cat in t.category" ng-click="ctrl.selectTag(cat)"   ng-class="['tag','label',{'label-danger':ctrl.checkSelectCategory(cat)}, {'label-info':!ctrl.checkSelectCategory(cat)}]" categoryId={{cat.id}} ng-bind="cat.name"></span>
                                            </div>
                                        </td>
                                        <td><span ng-bind="t.inventorynumber"></span></td>        
                                        <td>
                                            <button type="button" ng-click="ctrl.openBookDetails(t)" 
                                                    class="btn btn-primary custom-width " >Просмотреть</button>                                                   
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <!-- file details modal -->
        <div class="modal fade " id="bookDetails" tabindex="-1"  role="dialog"  >
            <div class="my-modal-dialog modal-content" role="document">
                <div>
                    <div class="modal-header">
                        <h4 class="modal-title" id="myModalLabel">Просмотр книг</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-horizontal">
                            <div class="row ">
                                <div class="col-md-5">
                                    <div class="form-horizontal">
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">Имя</label>
                                            <div class="col-sm-10">
                                                <textarea ng-model="ctrl.selectBook.name" cols="36" rows="4" readonly=true class="form-control " >
                                                </textarea>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label ">Инвентарный номер</label>
                                            <div class="col-sm-4">
                                                <input type="text" readonly=true ng-model="ctrl.selectBook.inventorynumber" class="form-control " required="true">
                                            </div>                                             
                                            <label class="col-sm-2 control-label ">Инвентарный номер оригинала</label>
                                            <div class="col-sm-4">
                                                <input type="text" readonly=true ng-model="ctrl.selectBook.originalinventorynumber" class="form-control " required="true">
                                            </div>   
                                        </div>                                       
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label ">Децимальный номер</label>
                                            <div class="col-sm-10">
                                                <input type="text" readonly=true ng-model="ctrl.selectBook.decimalnumber" class="form-control " required="true">
                                            </div>                                      
                                        </div>     
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label ">Организация</label>   
                                            <div class="col-sm-10">
                                                <input type="text" readonly=true ng-model="ctrl.selectBook.organization.name" class="form-control"  required="true" >
                                            </div>
                                        </div>   
                                        <div class="form-group">
                                            <label class="col-sm-2 control-label ">№ книги</label>
                                            <div class="col-sm-4">
                                                <input type="text" readonly=true ng-model="ctrl.selectBook.booknumber" class="form-control " required="true" >
                                            </div>
                                            <label class="col-sm-3 control-label ">Часть книги</label>
                                            <div class="col-sm-3">
                                                <input type="text" readonly=true ng-model="ctrl.selectBook.bookpart" class="form-control " required="true" >
                                            </div>                                           
                                        </div>   
                                        <div class="form-group">
                                            <label class="col-md-2 control-label ">№ экземпляра</label>
                                            <div class="col-md-4">
                                                <input type="text" readonly=true ng-model="ctrl.selectBook.copynumber"  class="form-control " required="true" >
                                            </div>
                                            <label class="col-md-3 control-label ">Количество страниц</label>
                                            <div class="col-md-3">
                                                <input type="text" readonly=true ng-model="ctrl.lastDocument.listcount" class="form-control " required="true" >
                                            </div>   
                                        </div>
                                        <hr>                                        
                                    </div>
                                </div>                            
                                <div class="col-md-7">
                                    <div class="form-horizontal">
                                        <div class="row">
                                            <label class="col-md-2 control-label ">Категории</label>
                                            <div class="col-md-4 input-group input-group-sm">                                                            
                                                <input  type="text" ng-model="ctrl.newTeg"  class="form-control"   list="categoryList">
                                                <datalist id="categoryList">
                                                    <option  ng-repeat="c in ctrl.categories" value="{{c.name}}">
                                                </datalist>
                                                <span class="input-group-btn">
                                                    <button class="btn btn-primary btn-sm"  ng-click="ctrl.updateAfterCall(ctrl.selectBook, ctrl.addCategory(ctrl.newTeg, ctrl.selectBook))">
                                                        <span class="glyphicon glyphicon-plus"></span>
                                                    </button>
                                                </span>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-8 col-md-offset-1"> 
                                                <div class="form-group ">                                                    
                                                </div>
                                                <div class="row grouping-block">
                                                    <div class="btn-group " ng-repeat="c in ctrl.selectBook.category"> 
                                                        <button type="button" class="btn btn-primary btn-xs" ><span ng-bind="c.name"></span></button> 
                                                        <button type="button" class="btn btn-primary dropdown-toggle btn-xs"  aria-haspopup="true" aria-expanded="false" ng-click="ctrl.updateAfterCall(ctrl.selectBook, ctrl.removeLabelFromBook(c, ctrl.selectBook.category))">
                                                            <span class="glyphicon glyphicon-remove"></span>
                                                        </button>
                                                    </div>
                                                </div>                                                                                           
                                            </div>
                                        </div> 
                                        <hr>
                                        <div class="row">
                                            <div class="col-md-offset-1">                                               
                                                <div>
                                                    <a  ng-href ="{{'/LibraryManager/delivery_page/?bookId=' + ctrl.selectBook.id}}"  target="_blank">
                                                        <button  type="button" class="btn btn-sm btn-success dropdown-toggle col-md-2 " style="margin: 5px;"  aria-expanded="false">
                                                            <span>История выдачи</span>
                                                        </button>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <h4 style="margin-left: 5px;">Изменения</h4>
                                <div class="small-block block" id="documentTable">
                                    <table class="col-md-12 table table-bordered">
                                        <thead>
                                            <tr>                                               
                                                <th>№ извещения:</th>
                                                <th>Дата извещения</th>
                                                <th>№ входящего</th>                       
                                                <th>Дата входящего</th>    
                                                <th>№ изменения</th>
                                                <th width="5%"></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr ng-repeat="d in ctrl.selectBook.documents|orderBy:'-modification'">                                                
                                                <td><span ng-bind="d.notice"></span></td>
                                                <td><span ng-bind="d.noticedate"></span></td>
                                                <td><span ng-bind="d.incomenumber"></span></td>
                                                <td><span ng-bind="d.incomedate"></span></td>       
                                                <td><span ng-bind="d.modification"></span></td>
                                                <td>
                                                    <button type="button" class="btn btn-xs btn-primary dropdown-toggle"
                                                            data-toggle="tooltip" 
                                                            data-placement="bottom" 
                                                            title="Показать содержимое книги для изменения"  
                                                            aria-expanded="false" ng-click="ctrl.formidFile(ctrl.selectBook, d)">
                                                        <span class="glyphicon glyphicon-eye-open"></span>
                                                    </button>
                                                    <a  ng-attr-href ="{{'/LibraryManager/document_page/?documentId=' + d.id + '&bookId=' + ctrl.selectBook.id}}" >
                                                        <button  type="button" 
                                                                 class="btn btn-xs btn-success dropdown-toggle"   
                                                                 data-toggle="tooltip" 
                                                                 data-placement="bottom" 
                                                                 title="Просмотр/редактирование изменения"  
                                                                 aria-expanded="false">
                                                            <span class="glyphicon glyphicon-edit"></span>
                                                        </button>
                                                    </a>                                                    
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>                                    
                                </div>
                                <div class="bookmark floatRight " id="bookmark">
                                    <span class="glyphicon glyphicon-bookmark btn-bookmark" aria-hidden="true" ></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button"   data-dismiss="modal" class="btn btn-primary" >Закрыть</button>
                    </div>
                </div>
            </div>
        </div>
        <script src="<c:url value='/static/js/lib/bootstrap.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap.min.js' />"></script>
        <script src="<c:url value='/static/js/lib/angular.min.1.4.9.js' />"></script>
        <script src="<c:url value='/static/js/lib/bootstrap-datetimepicker.min.js' />"></script>
        <script src="<c:url value='/static/js/app.js' />"></script>
        <script src="<c:url value='/static/js/book/controller.js' />"></script>
        <script src="<c:url value='/static/js/book/service.js' />"></script>
        <script src="<c:url value='/static/js/lib/directive.js' />"></script>
        <script src="<c:url value='/static/js/properties.js' />"></script>
        <script src="<c:url value='/static/js/book/script.js' />"></script>
    </body>
</html>
