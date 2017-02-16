'use strict';
var App = angular.module('myApp', []);
App.controller('BookController', ['$scope', 'BookService',
    function ($scope, BookService) {
        var self = this;
        $scope.selectDocumentPages = {name: '', pageData: '', fileURL: '', fileName: ''};
        $scope.pageList = [];
        self.bookName = "fgh";
        $scope.listDocumentPages = [];
        $scope.pageForNewDocument = [];
        $scope.fileForNewDocument = [];
        self.newTeg = "";
        $scope.selectCategory = new Set();
        self.categories = [];
        self.organizations = [];
        self.books = [];
        self.pageList = [];
        self.users = [];
        self.delivery = {};
        self.editDocumentFlag = false;
        self.newComment = {id: null, content: "", date: null};
        self.newComment.reset = function () {
            this.id = null;
            this.content = "";
            this.date = null;
        };
        self.loadingFile = {file: '', url: ''};
        self.selectBook = {id: null, name: '', inventorynumber: '', decimalnumber: '', booknumber: null, originalinventorynumber: '',
            bookpart: '', copynumber: '', category: '', organization: '', booktype: '', listcount: '', documents: [], delivery: null};
        self.newPage = {name: '', pageData: '', fileURL: '', fileName: '', pageState: null};
        self.book = {id: null, name: '', inventorynumber: '', decimalnumber: '', booknumber: null, originalinventorynumber: '',
            bookpart: '', copynumber: '', category: [], organization: '', booktype: '', documents: []};
        self.addidDocument = {id: null, modification: null, listcount: '', incomenumber: '', notice: '',
            outcomenumber: null, incomedate: null, outcomedate: null, noticedate: null, book: null, comments: []};
        self.document = {id: null, modification: 0, listcount: 0, incomenumber: '', noticedate: null, notice: '',
            outcomenumber: null, incomedate: null, outcomedate: null, book: null, comments: []};
        self.resetSelectFile = function () {
            $scope.selectDocumentPages = {name: '', pageData: '', fileURL: '', fileName: ''};
        };
        self.lastDocument;
        self.addCategory = function (category, book) {
            for (var i = 0; i < self.categories.length; i++) {
                if (self.categories[i].name == category && !book.category.some(function (value) {
                    return (value.name == category) ? true : false;
                })) {
                    book.category.push(self.categories[i]);
                    break;
                }
            }
            self.newTeg = "";
        };
        self.removeLabelFromBook = function (category, collection) {
            collection.splice(collection.indexOf(category), 1);
        };
        self.bookFilter = function (book) {
            if (book.organization.name != self.serchRow.organization.name &&
                    book.category.name != self.serchRow.category.name &&
                    book.booktype.name != self.serchRow.booktype.name &&
                    book.organization.name != '' &&
                    book.category.name != '' &&
                    book.booktype.name != '') {
                return false;
            }
            return true;
        };

        self.fetchAllOrganizations = function () {
            BookService.getOrganizations().then(function (response) {
                self.organizations = response;
            }, function (err) {
                console.error(err);
            });
        };
        /* Добавляет файл в список загружаемых документов */
        $scope.loadFileDocument = function (element, coollection) {
            for (var file = 0; file < element.files.length; file++) {
                var newDocumentPages = {};
                newDocumentPages.fileURL = URL.createObjectURL(element.files[file]);
                newDocumentPages.fileName = element.files[file].name;
                newDocumentPages.pageData = element.files[file];
                coollection.push(newDocumentPages);
            }
            self.resetPage();
            $scope.$apply();
        };
        /*Удаляет страницу из списка страниц для добавления и ануллирования */
        self.deletePageFromList = function (page, collection) {
            var index = collection.indexOf(page);
            collection.splice(index, 1);
            if (index > 0) {
                $scope.selectDocumentPages = collection[index - 1].fileURL;
            } else {
                self.resetSelectFile();
            }
            $scope.$apply();
        };

        self.viewDocument = function (document) {
            $scope.selectDocumentPages = document;
        };
        /*Функция для фильтра кнопки создания новой книги. Возвращает истину если все обязательные поля для книги и первого изменения заполнены */
        self.checkFormFilling = function () {
            return $scope.bookForm.$valid && $scope.documentForm.$valid;
        };

        self.resetPage = function () {
            self.newPage = {name: '', pageData: '', fileURL: '', pageState: null};
        };
        self.reset = function () {
            self.book = {id: null, name: '', inventorynumber: '', decimalnumber: '', booknumber: null, originalinventorynumber: '',
                bookpart: '', copynumber: '', category: [], organization: '', booktype: '', documents: []}
            self.document = {id: null, modification: 0, listcount: '', incomenumber: '', notice: '', noticdate: null,
                outcomenumber: null, incomedate: null, outcomedate: null, book: null, comment: ""};
            self.resetPage();
            $scope.listDocumentPages = [];
            $scope.selectDocumentPages = {name: '', pageData: '', fileURL: '', fileName: ''};
        };

        /*Функция для создания нового дакумента*/
        self.createDocument = function (document) {
            BookService.createDocument(document)
                    .then(
                            function (responce) {
                                return responce;
                            },
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while creating Document.');
                            }
                    );
        };
        /*Функция создания книги.
         * Функция на вход получает объект новой книги, первое изменение, список файлов для добавлении в с содержанием страниц.
         * Сперва функция, создает с помощью BookService книгу на сервере, в случае успеха вызывается функции добавления  страниц создаваемых из содержимого  списка файлов.
         * После этого, обновляет список книг и очищает форму создания новой книги. */
        self.createBook = function (book, document, fileDocument) {
            book.documents.push(document);
            BookService.createBook(book).then(function (response)
            {
                var document = response.documents[0];
                self.savePagesFromDocumentFile(fileDocument, document);
                self.fetchAllBook();
                self.reset();
            }
            ,
                    function (err) {
                    });
        };
        self.addNewDocumentForBook = function (document, pages, fileDocument) {
            BookService.createDocument(document).then(
                    function (respoce) {
                        self.savePagesInTheDocument(pages, respoce);
                        self.savePagesFromDocumentFile(fileDocument, respoce);
                        self.fetchAllBook();
                    }, function (errResponse) {
                showAlert(errResponse);
                console.error('Error while creating Document.');
            });
        };
        self.addNewDocument = function (document, fileDocument, pages) {
            self.addNewDocumentsForBook(document, pages);
            self.addNewDocumentForBook(document, fileDocument);
        };
        self.deleteBook = function (bookId) {
            var book = {};
            book.id = bookId.id;
            BookService.deleteCategory(book)
                    .then(
                            function (response) {
                                self.fetchAllBook();
                            },
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while deleting ');
                            }
                    );
        };
        self.fetchAllBookCategories = function () {
            BookService.getBookCategories().then(
                    function (response) {
                        self.categories = response;
                    }, function (err) {
                showAlert(err);
                console.error(err);
            }
            );
        };
        self.fetchAllBook = function () {
            BookService.fetchAllBook()
                    .then(
                            function (d) {
                                self.books = d;
                            },
                            function (errResponse) {
    
                                console.error('Error while fetching Book');
                            }
                    );
        };
        self.fetchAllUser = function () {
            BookService.fetchAllUser().then(function (responce) {
                self.users = responce;
            }, function (errResponce) {
                showAlert("Ошибка загрузки списка сотрудников");
                console.error('Error of sample of users ');
            });
        };
        /*
         * Вызывает функцию создания новой страницы для документа.
         * В качества параметров передается объект содержащий информацию о новой странице и документ
         * для которого страница создается.
         */
        self.addPageToTheDocument = function (newPage, document) {
            var page = {name: newPage.name, fileData: newPage.pageData, pageState: newPage.pageState};
            BookService.addPageToTheDocument(page, document).then(function (d) {
            }, function (errResponse) {
                showAlert(errResponse);
                console.error('Error while add page to the document');
            });
        };
        /*
         Сохраняет на сервере список страниц документа.Функция вызывает цикл для добавления новых 
         страниц в документе. Получает на вход список с информацией о страницах и документ в 
         котором сохраняются страницы.
         */
        self.savePagesInTheDocument = function (pages, document) {
            for (var page in pages) {
                self.addPageToTheDocument(pages[page], document);
            }
        };
        self.addPagesToTheDocument = function (file, document) {
            var fileDocument = {fileData: file.pageData, documentId: document.id};
            BookService.addPagesToTheDocument(fileDocument).then(
                    function () {

                    }, function (errResponce) {
                showAlert(errResponse);
                console.error('Error while change state document');
            })
        };
        self.savePagesFromDocumentFile = function (documentFile, document) {
            for (var file in documentFile) {
                self.addPagesToTheDocument(documentFile[file], document);
            }
        };
        self.resetAddidDocument = function () {
            self.addidDocument = {id: null, modification: null, listcount: '', incomenumber: '', notice: '',
                outcomenumber: null, incomedate: null, outcomedate: null, noticedate: null, book: null, comments: []};
        };
        self.openModalForLodingPage = function (flag) {
            $('#myModal').modal();
            self.editDocumentFlag = flag;
        };
        /*
         * Открывает модальное окно для добавления нового документа
         */
        self.openModalDialogForAddDocument = function (book) {
            $('#addidDocument').modal();
            self.resetSelectFile();
            self.resetAddidDocument();
            self.addidDocument.book = book.id;
            $scope.pageForNewDocument = [];
        };

        /*
         * Открывает модальное окно для просмотра книги.
         */
        self.openBookDetails = function (book) {
            BookService.getLastDelivery(book).then(function (response) {
                self.selectBook.delivery = response;
                self.delivery = response;
                $('#bookDetails').modal();
            }, function (errResponse) {
                showAlert(errResponse);
                console.error('Error while get delivery');
            });
            self.selectBook = book;
            book.documents.sort(function (a, b) {
                return a.modification - b.modification;
            });
            self.lastDocument = book.documents[book.documents.length - 1];
        };

        /*
         * Устанавливает URL для элемента отображения файла pdf. В  URL хронится адрес контролера 
         * который создает нужную версию файла. Параметр t, случайное число нужное для 
         * гарантированного запроса к серверу(новый адрес совпадает с прежним).
         */
        self.formidFile = function (book, document) {
            self.loadingFile.url = '/LibraryManager/books/fileFormation?bookId='
                    + book.id
                    + '&modification='
                    + document.modification
                    + '&t=' + Math.random() * 666;
            window.open(self.loadingFile.url,book.name);
        };
        self.fio = function (employee) {
            return employee.lastname + " " + employee.firstname + " " + employee.secondname;
        };

        /*
         * Создает выдачу книги. На вход программа получает объект с информацией о выдаче
         *  и книгу которую выдают.
         */
        self.createDelivery = function (delivery, book) {
            var newDelivery = {};
            newDelivery.employeeid = findEmployeeId(delivery.eployeeName);
            newDelivery.book = book;
            newDelivery.deliverydate = delivery.deliverydate;
            newDelivery.surrenderdate = delivery.surrenderdate;
            newDelivery.id = null;
            if (newDelivery.employeeid == "") {
                showAlert("Неверно введено ФИО сотрудника");
            } else {
                BookService.createDelivery(newDelivery).then(function (response) {
                    $("#deliveryForm").hide();
                    self.selectBook.delivery = response;
                    self.delivery = response;
                }, function (errResponse) {
                    showAlert("Ошибка создания выдачи");
                    console.error('Error while create delivery');
                });
            }
        };
        /*
         * Обращается к серверу и закрывает выдачу.
         */
        self.deliveryClosing = function (delivery) {
            BookService.deliveryClosing(delivery).then(function (response) {
                self.selectBook.delivery = "";
                self.delivery = "";
                $("#closingDeliveryForm").hide();
            }, function (errResponse) {
                showAlert("Ошибка закрытие выдачи");
                console.error('Error while delivery comes back');
            });
        };

        var findEmployeeId = function (employeeName) {
            for (var i = 0; i < self.users.length; i++) {
                if (self.fio(self.users[i]) === employeeName) {
                    return self.users[i].id;
                }
            }
            return"";
        };

        self.findEmployeeName = function (employeeId) {
            for (var i = 0; i < self.users.length; i++) {
                if (self.users[i].id == employeeId) {
                    return self.fio(self.users[i]);
                }
            }
            return "";
        };

        self.selectTag = function (label) {
            if ($scope.selectCategory.has(label.name)) {
                $scope.selectCategory.delete(label.name);
            } else {
                $scope.selectCategory.add(label.name);
            }
        };
        self.checkSelectCategory = function(category){
            return $scope.selectCategory.has(category.name);
        }
        /*
         * Функция фильтрации для отображения на интерфейсе списка книг.
         */
        self.filterByCategory = function (actual) {
            if ($scope.selectCategory.size == 0) {
                return true;
            }
            var set = new Set();
            for (var i = 0; i < actual.category.length; i++) {
                set.add(actual.category[i].name)
            }
            for (var c of $scope.selectCategory){
                if (!set.has(c)) {
                    return false;
                }
            }
            return true;
        };

        self.updateAfterCall = function (book, f) {
            self.updateBook(book);
        };
        self.updateBook = function (book) {
            BookService.updateBook(book).then(function (response) {
                self.fetchAllBook();
                self.fetchAllUser();
                self.fetchAllOrganizations();
                self.fetchAllBookCategories();
            }, function (errResponse) {
                showAlert("Ошибка обнавления книги");
                console.error('Error while update book');
            });
        };
        self.addAnElementToCollection = function (enty, collection) {
            collection.push(enty);
        };
        self.removeElementFromCollection = function (enty, collection) {
            if (collection.splice) {
                collection.splice(collection.indexOf(enty), 1);
            } else {
                delete collection[collection.indexOf(enty)];
            }
        };
        /*
         * Добавляет коментарий к документу.
         */
        self.addCommentToTheDocument = function (comment, document) {
            var date = new Date();
            var c = {};
            c.id = "";
            c.content = comment.content;
            c.date = date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear();
            comment.reset();
            self.addAnElementToCollection(c, document.comments);
        };

        /*Возвращает true если выдача существует и книга еще не возвращена*/
        self.deliveryIsOpened = function (delivery) {
            var ans = (delivery != undefined) && (delivery.deliverydate != undefined) && (delivery.surrenderdate == undefined);
            return  ans;
        };
        self.fetchAllBook();
        self.fetchAllUser();
        self.fetchAllOrganizations();
        self.fetchAllBookCategories();
    }]);


