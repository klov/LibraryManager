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
    <body class="ng-cloak " ng-app="myApp" ng-controller="BookController
                        as
                        ctrl">
        <%@include file="/static/header.html"  %>
        <div class="generic-container">
            <div>
                <div>
                    <div class=" form-group has-feedback  floatRight">
                        <input ng-model="bookName.$" type="text" class="form-control input-sm input-small" placeholder="Поиск  книги">                                           
                        <span class="glyphicon glyphicon-search form-control-feedback" aria-hidden="true"></span>
                    </div> 
                    <ul class="nav nav-tabs" id="tabs">
                        <li role="presentation" ><a href="#addBook">Добавление книги</a></li>
                        <li role="presentation" class="active"><a href="#reviewBook">Просмотр книг</a></li>
                    </ul>
                    <div class="tab-content">
                        <!-- Tab 1 -->
                        <div role="tabpanel" class="tab-pane " id="addBook" >                            
                            <div class="panel panel-default">
                                <div class="panel-heading"><span class="lead">Форма добавления книг</span></div>
                                <div class="formcontainer">
                                    <form class="form-horizontal" name="bookForm" >
                                        <div class="row">
                                            <div class="col-md-7">
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">Имя документа</label>
                                                    <div class="col-sm-8">
                                                        <input type="text" ng-model="ctrl.book.name" class="form-control " required="true"> 

                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label ">Инвентарный номер</label>
                                                    <div class="col-sm-8">
                                                        <input type="text" ng-model="ctrl.book.inventorynumber" class="form-control " required="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label ">Инвентарный номер оригинала</label>
                                                    <div class="col-sm-8">
                                                        <input type="text" ng-model="ctrl.book.originalinventorynumber" class="form-control " required="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label ">Децимальный номер</label>
                                                    <div class="col-sm-8">
                                                        <input type="text" ng-model="ctrl.book.decimalnumber" class="form-control " required="true">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label ">Номер книги</label>
                                                    <div class="col-sm-3">
                                                        <input type="text" ng-model="ctrl.book.booknumber" class="form-control "  >
                                                    </div>
                                                    <label class="col-sm-2 control-label ">Часть книги</label>
                                                    <div class="col-sm-3">
                                                        <input type="text" ng-model="ctrl.book.bookpart" class="form-control " >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label ">Номер экземпляра</label>
                                                    <div class="col-sm-4">
                                                        <input type="text" ng-model="ctrl.book.copynumber"  class="form-control " >
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label ">Организация</label>   
                                                    <div class="col-sm-4">
                                                        <select ng-model="ctrl.book.organization" class="form-control" ng-options="x.name for x in ctrl.organizations" required="true" >
                                                        </select>  
                                                    </div>                                                    
                                                </div>
                                            </div>
                                            <div class="col-md-4 col-md-offset-1">
                                                <div>                                                   
                                                    <div class="form-group row">
                                                        <label class="col-md-2 control-label ">Категории</label>
                                                        <div class="col-md-5 input-group input-group-sm">                                                            
                                                            <input  type="text" ng-model="ctrl.newTeg"  class="form-control"   list="categoryList">
                                                            <datalist id="categoryList">
                                                                <option  ng-repeat="c in ctrl.categories" value="{{c.name}}">
                                                            </datalist>
                                                            <span class="input-group-btn">
                                                                <button class="btn btn-primary btn-sm"  ng-click="ctrl.addCategory(ctrl.newTeg, ctrl.book)">
                                                                    <span class="glyphicon glyphicon-plus"></span>
                                                                </button>
                                                            </span>
                                                        </div>
                                                    </div>
                                                    <div class="row ">
                                                        <div class="col-md-11">
                                                            <div class="grouping-block">
                                                                <div class="btn-group " ng-repeat="c in ctrl.book.category"> 
                                                                    <button type="button" class="btn btn-primary btn-xs" ><span ng-bind="c.name"></span></button> 
                                                                    <button type="button" class="btn btn-primary dropdown-toggle btn-xs"  aria-haspopup="true" aria-expanded="false" ng-click="ctrl.removeElementFromCollection(c, ctrl.book.category)">
                                                                        <span class="glyphicon glyphicon-remove"></span>
                                                                    </button>
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
                            <div class="panel panel-default">
                                <div class="panel-heading"><span class="lead">Извещение</span></div>
                                <div class="formcontainer">
                                    <form class="form-horizontal" name="documentForm">
                                        <div class="row">
                                            <div class="col-md-8">
                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <label >Входящий №</label>
                                                            <input type="text" class=" input-small form-control" ng-model="ctrl.document.incomenumber" required="true" >

                                                        </div>
                                                        <div class=" col-md-2  input-group">
                                                            <label class="control-lable ">Дата входящего</label>
                                                            <div class=" input-group date form_date">
                                                                <input type='text' class="form-control" ng-model="ctrl.document.incomedate" required="true" />
                                                                <span class="input-group-addon">
                                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                                </span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-5">
                                                            <label class="control-lable">Исходящий №</label>
                                                            <input type="text" class="input-small form-control" ng-model="ctrl.document.outcomenumber" required="true" >
                                                        </div>
                                                        <div class=" col-md-2 input-group">
                                                            <label class="control-lable ">Дата исходящего</label>
                                                            <div class=" input-group date">
                                                                <input type='text' class="form-control"  ng-model="ctrl.document.outcomedate" required="true"  />
                                                                <span class="input-group-addon">
                                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                                </span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class=" col-md-5 ">
                                                            <label class="control-lable">№ извещения</label>
                                                            <input type="text" class="input-small form-control"  ng-model="ctrl.document.notice" required="true" >
                                                        </div>
                                                        <div class=" col-md-2  input-group">
                                                            <label class="control-lable ">Дата извещения</label>
                                                            <div class=" input-group date form_date">
                                                                <input type='text' class="form-control" ng-model="ctrl.document.noticedate" required="true" />
                                                                <span class="input-group-addon">
                                                                    <span class="glyphicon glyphicon-calendar"></span>
                                                                </span>
                                                            </div>
                                                        </div>                                                                                                                
                                                    </div>                                       
                                                    <div  class="row">
                                                        <div class=" col-md-2 ">
                                                            <label class="control-lable">Количество листов в книге</label>
                                                            <input type="number" min="0"  ng-model="ctrl.document.listcount" class="form-control input-small" required="true" >
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-md-3">
                                                            <button type="button" id="loadDocument" class="btn btn-success btn-sm btn-form" ng-click="ctrl.openModalForLodingPage(false)" >Загрузить содержимое</button>
                                                        </div>
                                                    </div>   
                                                </div>
                                            </div>
                                            <div class="col-md-4">
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
                                                                        <button class=" btn btn-xs right" ng-click="ctrl.removeElementFromCollection(c, ctrl.document.comments)">
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
                                    </form>                                    
                                </div>                             
                            </div>
                            <!--                             <b>*-поле обязательно должно быть заполнено</b>-->
                            <div class="row">
                                <div class="form-actions floatRight">
                                    <input type="submit"   ng-click="ctrl.createBook(ctrl.book, ctrl.document, listDocumentPages)"
                                           value="Добавить" 
                                           class="btn btn-primary btn-sm" 
                                           ng-disabled="!ctrl.checkFormFilling()">
                                    <button type="button" 
                                            ng-click="ctrl.reset()" 
                                            class="btn btn-warning btn-sm" 
                                            ng-disabled="">Сбросить</button>              
                                </div>
                            </div>
                        </div>
                        <!-- Tab 2 -->
                        <div role="tabpanel" class="tab-pane active" id="reviewBook">
                            <div class=" row">
                                <div class=" col-md-2  container"> 
                                    <div class="navigationTreeContainer">
                                        <div ng-repeat="c in ctrl.categories|orderBy:'name'">
                                            <button class="btn tag  btn-xs" ng-class="[{'btn-primary':!ctrl.checkSelectCategory(c)}, {'btn-danger':ctrl.checkSelectCategory(c)}]" categoryId={{c.id}} ng-click="ctrl.selectTag(c)">
                                                <span>{{c.name}}</span>
                                                <span class="glyphicon glyphicon-tag"></span>
                                            </button>   
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-10">  
                                    <div class="tablecontainer">
                                        <table class=" table table-hover">
                                            <thead>
                                                <tr>
                                                    <th>Наименование</th>
                                                    <th>Инвентарный номер</th>
                                                    <th width="25%"></th>
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
                                                        <button type="button"  
                                                                ng-click="ctrl.openModalDialogForAddDocument(t)" 
                                                                class="btn btn-default custom-width">Добавить изменение</button>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
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
                                                <span class="col-md-3"  ng-hide ="!ctrl.deliveryIsOpened(ctrl.selectBook.delivery)" style="display: "><b>Взял:   </b>{{ctrl.findEmployeeName(ctrl.selectBook.delivery.employeeid)}}</span>
                                                <button class="btn btn-primary btn-sm col-md-2 col-md-offset-1" style="margin: 5px;" id="deliveryFormButton" ng-hide ="ctrl.deliveryIsOpened(ctrl.selectBook.delivery)">Выдача
                                                </button>                                                                             
                                                <button ng-hide ="!ctrl.deliveryIsOpened(ctrl.selectBook.delivery)" style="margin: 5px;" class="btn btn-primary btn-sm col-md-2 col-md-offset-1" id="closeDeliveryFormButton"  >Возврат
                                                </button>
                                                <div>
                                                    <a  ng-href ="{{'/LibraryManager/delivery_page/?bookId=' + ctrl.selectBook.id}}"  target="_blank">
                                                        <button  type="button" class="btn btn-sm btn-success dropdown-toggle col-md-2 " style="margin: 5px;"  aria-expanded="false">
                                                            <span>История выдачи</span>
                                                        </button>
                                                    </a>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="generic-container" id="deliveryForm">
                                            <form name="formDelivery">
                                                <div class="form-group">                                                
                                                    <label class="col-sm-2 control-label ">Пользователь</label> 
                                                    <div class="col-md-3">
                                                        <input  ng-model="ctrl.delivery.eployeeName" type="text"  class="form-control"   list="employeeList" required="true" >
                                                        <datalist id="employeeList">
                                                            <option  ng-repeat="u in ctrl.users" value="{{ctrl.fio(u)}}">
                                                        </datalist>
                                                    </div>
                                                    <label class="col-sm-2 control-label ">Дата</label> 
                                                    <div class="col-md-3 input-group date ">
                                                        <input type='text' class="form-control"  ng-model="ctrl.delivery.deliverydate"  required="true"  />
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>                                              
                                                </div>  
                                            </form>
                                            <button  class="btn btn-primary btn-sm" ng-disabled="formDelivery.$invalid" id="closeingDeliveryFormButton"  ng-click="ctrl.createDelivery(ctrl.delivery, ctrl.selectBook)" >
                                                Выдать
                                            </button>                                        
                                        </div>
                                        <div class="generic-container" id="closingDeliveryForm">
                                            <form name="closingFormDelivery">
                                                <div class="form-group row"> 
                                                    <label class="col-md-2 control-label ">Дата выдачи:</label> 
                                                    <div class="col-md-3">
                                                        <input type="text" readonly=true ng-model="ctrl.delivery.deliverydate" class="form-control " >
                                                    </div>
                                                    <label class="col-md-2 control-label ">Дата возврата</label> 
                                                    <div class="col-md-3 input-group date ">
                                                        <input type='text' class="form-control" ng-model="ctrl.delivery.surrenderdate" required="true"  />
                                                        <span class="input-group-addon">
                                                            <span class="glyphicon glyphicon-calendar"></span>
                                                        </span>
                                                    </div>                                                
                                                </div>  
                                            </form>
                                            <button  class="btn btn-primary btn-sm"  ng-disabled="closingFormDelivery.$invalid"  ng-click="ctrl.deliveryClosing(ctrl.delivery)" >
                                                Вернуть
                                            </button>
                                        </div>  
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <hr>
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
                                                <th width="10%"></th>
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
        <!-- File uploadin Modal  -->
        <div class="modal fade " id="myModal" tabindex="-1"  role="dialog" aria-labelledby="myModalLabel">
            <div class="my-modal-dialog" role="document">
                <div class="modal-content ">
                    <div class="modal-header">
                        <h4 class="modal-title" id="myModalLabel">Добавление документа</h4>
                    </div>
                    <div>                                                 
                        <div role="tabpanel" class="tab-pane active" id="documentLoading" > 
                            <div class="modal-body">
                                <div class="form-horizontal"> 
                                    <div class="row">                                            
                                        <div class="row col-md-8">                                                                                               
                                            <object  data="{{selectDocumentPages.fileURL}}" type="application/pdf" class="pdf-frame" ></object>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="col-md-4">
                                                <label class=" btn btn-default btn-file"  >Загрузить файл
                                                    <input type="file" multiple="true" onchange="angular.element(this).scope().loadFileDocument(this, angular.element(this).scope().listDocumentPages)">
                                                </label>
                                            </div>                                            
                                            <div  class="col-md-10 block large-block">
                                                <table class="table table-bordered">
                                                    <tr ng-repeat=" p in listDocumentPages" >
                                                        <td width="80%" ng-click="ctrl.viewDocument(p)"><spen  ng-bind="p.fileName" ></spen></td>
                                                    <td width="5em">
                                                        <button type="button" class="btn btn-xs btn-primary dropdown-toggle" ng-click="ctrl.deletePageFromList(p, listDocumentPages)" aria-expanded="false">
                                                            <span class="glyphicon glyphicon-remove"></span>
                                                        </button>
                                                    </td>
                                                    </tr>
                                                </table>
                                            </div> 
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button"  data-dismiss="modal"  ng-if="ctrl.editDocumentFlag" class="btn btn-warning">Сохранить</button>
                            <button type="button"   data-dismiss="modal" class="btn btn-primary" >Закрыть</button>
                        </div>                        
                    </div>
                </div>
            </div>
        </div>
        <!-- The modal document of addition --> 
        <div class="modal fade " id="addidDocument" tabindex="-1"  role="dialog" aria-labelledby="myModalLabel">
            <div class="my-modal-dialog" role="document">
                <div class="modal-content ">
                    <div class="modal-header">
                        <h4 class="modal-title" id="myModalLabel">Добавление изменения</h4>
                    </div>
                    <div>
                        <div class="tab-content">
                            <div class="containerform">
                                <form class="form-horizontal" name="newDocumentForm">
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <label class="control-lable ">Входящий №</label>
                                                        <input type="text" class=" input-small form-control" ng-model="ctrl.addidDocument.incomenumber" required="true" >
                                                    </div>
                                                    <div class=" col-md-2 col-md-offset-1 input-group">
                                                        <label class="control-lable ">Дата входящего</label>
                                                        <div class=" input-group date form_date">
                                                            <input type='text' class="form-control" ng-model="ctrl.addidDocument.incomedate" required="true" />
                                                            <span class="input-group-addon">
                                                                <span class="glyphicon glyphicon-calendar"></span>
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <label class="control-lable">Исходящий №</label>
                                                        <input type="text" class="input-small form-control" ng-model="ctrl.addidDocument.outcomenumber" required="true" >
                                                    </div>
                                                    <div class=" col-md-2 col-md-offset-1 input-group">
                                                        <label class="control-lable ">Дата исходящего</label>
                                                        <div class=" input-group date">
                                                            <input type='text' class="form-control"  ng-model="ctrl.addidDocument.outcomedate" required="true"  />
                                                            <span class="input-group-addon">
                                                                <span class="glyphicon glyphicon-calendar"></span>
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">                                                    
                                                    <div class=" col-md-6 ">
                                                        <label class="control-lable">№ извещения:</label>
                                                        <input type="text" class="input-small form-control"  ng-model="ctrl.addidDocument.notice" required="true" >
                                                    </div>
                                                    <div class=" col-md-2 col-md-offset-1 input-group">
                                                        <label class="control-lable ">Дата извещения</label>
                                                        <div class=" input-group date">
                                                            <input type='text' class="form-control"  ng-model="ctrl.addidDocument.noticedate" required="true"  />
                                                            <span class="input-group-addon">
                                                                <span class="glyphicon glyphicon-calendar"></span>
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="row">
                                                    <div class="col-md-6">
                                                        <label class="control-lable">№ именения:</label>
                                                        <input type="number" min="0" class="input-small form-control"  ng-model="ctrl.addidDocument.modification" required="true" >
                                                    </div>
                                                </div>
                                                <div class="row">                                                
                                                    <div class=" col-md-3 ">
                                                        <label class="control-lable">Количество листов в книге:</label>
                                                        <input type="number" min="0"  ng-model="ctrl.addidDocument.listcount" class="form-control input-small" required="true" >
                                                    </div> 
                                                </div> 
                                            </div>
                                            <div class="col-md-6">
                                                <div class="row">
                                                    <div class="col-md-8">                         
                                                        <div class="row">
                                                            <div class="col-md-11">
                                                                <label>Примечание</label>
                                                                <textarea class="form-control" rows="3" ng-model="ctrl.newComment.content">                                                        
                                                                </textarea>
                                                                <button class="btn btn-success btn-sm form-element right" ng-click="ctrl.addCommentToTheDocument(ctrl.newComment, ctrl.addidDocument)">Добавить</button>
                                                            </div>
                                                        </div>
                                                        <div class="row ">
                                                            <div class=" col-md-11">
                                                                <div class="grouping-block"">
                                                                    <div>
                                                                        <div ng-repeat="c in ctrl.addidDocument.comments">
                                                                            <div class="row col-md-offset-9 col-md-3">
                                                                                <span >{{c.date}}</span>
                                                                                <button class=" btn btn-xs right" ng-click="ctrl.removeElementFromCollection(c, ctrl.document.comments)">
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
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button"   data-dismiss="modal" class="btn btn-success"   ng-click="ctrl.addNewDocumentForBook(ctrl.addidDocument, pageForNewDocument, fileForNewDocument)"   ng-disabled="newDocumentForm.$invalid">Добавить</button>
                                <button type="button"   data-dismiss="modal" class="btn btn-primary" >Закрыть</button>
                            </div>
                        </div>
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
