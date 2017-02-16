'use strict';
App.controller('DocumentController', ['$scope', 'DocumentService',
    function ($scope, DocumentService) {
        var self = this;
        var ADDED = "ADDED";
        var DELETE = "DELETE";
        $scope.selectDocumentPages = {name: '', pageData: '', fileURL: '', fileName: ''};
        $scope.pageList = [];
        $scope.selectFile = {name: '', pageData: '', fileURL: '', fileName: ''};
        self.document = {id: getUrlVars()["documentId"], modification: null, listcount: '', incomenumber: '', noticdate: null,
            outcomenumber: null, incomedate: null, outcomedate: null, book: null, fileData: null, page_state: null};
        self.newPage = {id: null, name: '', pageData: '', fileURL: '', fileName: ''};
        self.deletePageList = [];
        self.fileIsAdded = false;
        self.newComment = {id: null, content: "", date: null};
        self.newComment.reset = function () {
            this.id = null;
            this.content = "";
            this.date = null;
        };

        /*Возвращает поле по которому должна проводится сортировка страниц документа*/
        $scope.orderName = function () {
            if ($scope.sortFlag) {
                return "name";
            }
            return "id";
        };
        self.saveIsActive = function () {
            return $scope.newDocumentForm.$invalid || self.fileIsAdded;
        };
        self.getValueForComparator = function (obj) {
            if (obj.type = "number") {
                return obj.value;
            }
            return "id";
        };
        /*
         * Функция сравнения, для сортировки страниц. Если оба параметра числа, то сравниваются 
         * значения переменных. Если в параметрах есть символы, тогда сначало сравнивает числовые 
         * части параметров, а при равенстве чисел использует лексикографическое сравнение 
         * строковых частей.
         * @param {type} v1
         * @param {type} v2
         * @returns {Number}
         */
        $scope.localeComparator = function (v1, v2) {
            var val1 = self.getValueForComparator(v1);
            var val2 = self.getValueForComparator(v2);
            var o1IntPart = 0, o2IntPart = 0;
            var o1ChartPart = "", o2ChartPart = "";
            if (String(val1) == "null" || String(val1) == "") {
                return 1;
            }
            if (String(val2) == "null" || String(val2) == "") {
                return -1;
            }
            if (String(val1).search(/\D/) != -1) {
                o1IntPart = Number(String(val1).substring(0, String(val1).search(/\D/)));
                o1ChartPart = String(val1).substring(String(val1).search(/\D/), String(val1).length);
            } else {
                o1IntPart = Number(String(val1));
            }
            if (String(val2).search(/\D/) != -1) {
                o2IntPart = Number(String(val2).substring(0, String(val2).search(/\D/)));
                o2ChartPart = String(val2).substring(String(val2).search(/\D/), String(val2).length);
            } else {
                o2IntPart = Number(String(val2));
            }
            if (o1IntPart == o2IntPart) {
                return o1ChartPart.localeCompare(o2ChartPart);
            }
            return o1IntPart - o2IntPart;
        };
        /*
         * Функция обрабатывает выбор документов из которых состоит документ. Сначало в интерфейс добаляется анимация для визуализации процесса загрузки. 
         * Затем файлы отправляются на сервер для парсинга и добавления страниц. 
         * После ответов сервера, обработчик удаляет добавленную анимацию, обновляет информацию о документе и выводит сообщение об успешном добавлении. 
         * @param {type} element
         * @returns {undefined}
         */
        $scope.loadFile = function (element) {
            controller.n = element.files.length;
            controller.f = function () {
                self.findDocument();
                showAlertSuccess("Страницы добавлены");
            };
            var t = $("#addedButton");
            var animateTeg = $('<span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>');
            t.prop('disabled', true);
            t.prepend(animateTeg);
            self.fileIsAdded = true;
            controller.n = element.files.length;
            controller.f = function () {
                self.findDocument();
                t.prop('disabled', false);
                t.find(animateTeg).remove();
                self.fileIsAdded = false;
                showAlertSuccess("Страницы добавлены");
            };
            for (var file = 0; file < element.files.length; file++) {
                console.log("add page to the document start " + file);
                var fileDocument = {fileData: element.files[file], documentId: self.document.id};
                DocumentService.addPagesToTheDocument(fileDocument).then(
                        function () {
                            console.log(" page added to the document  finished " + file);
                        }, function (errResponse) {
                    t.prop('disabled', false);
                    t.find(animateTeg).remove();
                    self.fileIsAdded = false;
                    showAlert('Ошибка добавления страниц');
                    console.error('Error of addition of pages');
                });
            }
            self.resetPage();
        };
        self.viewPage = function (page) {
            $scope.selectFile = page;
        };
        self.resetPage = function () {
            self.newPage = {name: '', pageData: '', fileURL: ''};
        };
        self.findDocument = function () {
            DocumentService.findDocument(self.document).then(function (response) {
                self.document = response;
                $scope.pageList = [];
                self.loadPage(response);
            }, function (errResponse) {
                showAlert("Ошибка загрузки информации об изменении");
                console.error('Error find document');
            });
        };
        /*Функция загрузки страниц для книги. */
        self.loadPage = function (document) {
            DocumentService.loadPageForDocument(document).then(function (response) {
                for (var p in response) {
                    if (response[p].pageState == ADDED) {
                        $scope.pageList.push({id: response[p].id, name: response[p].name, pageData: '', fileURL: "/LibraryManager/document/page?id=" + response[p].id, fileName: 'Добавлен', pageState: response[p].pageState});
                    } else if (response[p].pageState == DELETE) {
                        $scope.pageList.push({id: response[p].id, name: response[p].name, pageData: '', fileURL: "/LibraryManager/document/page?id=" + response[p].id, fileName: 'Аннулирован', pageState: response[p].pageState});
                    }
                }
                $scope.pageList.sort(function(b1,b2){return b1.id-b2.id});
            }, function (errResponse) {
                showAlert("Ошибка при загрузке страниц");
                console.error('Error loading document');
            });
        };

        self.resetSelectFile = function () {
            $scope.selectFile = {name: '', pageData: '', fileURL: '', fileName: ''};
        };

        /*Функция удаляет переданную страницу из перданой колекции если находит ее.*/
        self.deletePageFromList = function (page, collection) {
            var index = collection.indexOf(page);
            if (page.id != null) {
                self.deletePageList.push(page);
            }
            collection.splice(collection.indexOf(page), 1);
            if (index > 0 && $scope.selectFile == page) {
                $scope.selectFile = collection[index - 1].fileURL;
            } else {
                self.resetSelectFile();
            }
            $scope.$apply();
        };
        
        /*
         * Функция сохраняет  страницы на сервер. Для отправки сообщений использует 2 функции унаследованных от предыдущих версий программы.
         *  addPageToTheDocument - для сохранения(обновления) аннулированные страницы, addPagesToTheDocument - сохраняет(обновляет) страниц из файлов.
         *  Перед сохранением страниц проверяет нет ли одинаковых номеров у страниц. Если есть, то выбрасывает исключение.
         *  
         * @param {type} pages
         * @param {type} document
         * @returns {undefined}
         */
        self.sendPages = function (pages, document) {
            var set = new Set();
            for (var p in  $scope.pageList) {
                if (set.has(p.name)) {
                    showAlert("Страницы не могут иметь одинаковые номера");
                    return;
                }
            }
            for (var page in pages) {
                if (pages[page].id == null) {
                    if (pages[page].pageState == 'Delete') {
                        self.addPageToTheDocument(pages[page], document);                   /* стоит переделать эту часть*/
                    } else {
                        self.addPagesToTheDocument(pages[page], document);
                    }
                } else {
                    self.updatePage(pages[page]);
                }
            }
        };

        self.viewDocument = function (document) {
            $scope.selectDocumentPages = document;
        };
        self.addPagesToTheDocument = function (file, document) {
            var fileDocument = {fileData: file.pageData, documentId: document.id};
            DocumentService.addPagesToTheDocument(fileDocument).then(
                    function () {
                        console.log("addPagesToTheDocument");
                    }, function (errResponse) {
                showAlert('Ошибка добавления страниц');
                console.error('Error of addition of pages');
            });
        };
        self.updatePage = function (updatidPage) {
            var page = {name: updatidPage.name, fileData: updatidPage.pageData, id: updatidPage.id};
            DocumentService.updatePage(page).then(function (response) {
            },
                    function (errResponse) {
                        showAlert('Ошибка при обновлении изменения');
                        console.error('Error at change updating');
                    });
        };
        self.addPageToTheDocument = function (newPage, document) {
            var page = {name: newPage.name, fileData: newPage.pageData, pageState: newPage.pageState};
            DocumentService.addPageToTheDocument(page, document).then(function (d) {
                console.log("addPageToTheDocument");
            }, function (errResponse) {
                showAlert("Ошибка добавления страниц");
                console.error('Error while add page to the document');
            });
        };
        self.deletePages = function (pages) {
            for (var page in pages) {
                self.deletePage(pages[page]);
            }
        };
        self.deletePage = function (daletingPage) {
            var page = {name: daletingPage.name, fileData: daletingPage.pageData, id: daletingPage.id, pageState: daletingPage.pageState};
            DocumentService.detetePage(page).then(function (response) {
                console.log("deletePage");
            },
                    function (errResponse) {
                        showAlert("Ошибка при удаление страницы");
                        console.error('Error while delete page ');
                    });
        };
        self.savingChanges = function () {
            var set = new Set();
            for (var p in  $scope.pageList) {
                if (set.has($scope.pageList[p].name)) {
                    throw "Страницы не могут иметь одинаковые номера";
                }
                if($scope.pageList[p].name&&$scope.pageList[p].name!="")
                set.add($scope.pageList[p].name);
            }
            DocumentService.updateDocument(self.document).then(function (response) {       
                self.sendPages($scope.pageList, self.document);
                self.deletePages(self.deletePageList);
                self.deletePageList = [];
            }, function (errResponse) {
                showAlert("Ошибка обновления изменения");
                console.error('Error update document ');
            });
        };

        self.deleteDocument = function (document) {
            if (confirm("Удалить изменение?")) {
                DocumentService.deleteDocument(document).then(
                        function (response) {
                            window.location = "/LibraryManager/book_page";
                        },
                        function (errResponse) {
                            showAlert("Не удалось удалить изменение");
                            console.error('Error while delete Document.');
                        }
                );
            }
        };

        self.deletePageFromBook = function (coollection) {
            self.newPage.fileURL = '';
            self.newPage.fileName = 'Аннулирована';
            self.newPage.pageData = new File([], 'delete.pdf');
            self.newPage.pageState = 'Delete';
            coollection.push(self.newPage);
            $scope.selectFile = self.newPage;
            self.resetPage();
            $scope.$apply();
        };
        self.formidBook = function () {
            $scope.selectFile.fileURL = '';
            $scope.selectFile.fileURL = '/LibraryManager/books/fileFormation?bookId=' + getUrlVars()["bookId"]
                    + '&modification=' + self.document.modification
                    + '&t=' + Math.random() * 666;
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
        self.removeCommentFromDocument = function (comment, document) {
            DocumentService.removeComment(comment).then(function (response) {
                self.removeElementFromCollection(comment, document)
            }, function (errResponse) {
                showAlert("Ошибка добавления коментария");
            })
        }
        self.addCommentToTheDocument = function (comment, document) {
            var date = new Date();
            var c = {};
            c.id = "";
            c.content = comment.content;
            c.date = date.getDate() + '.' + (date.getMonth() + 1) + '.' + date.getFullYear();
            DocumentService.addComment(c, document).then(function (response) {
                self.addAnElementToCollection(response, document.comments);
            }, function (errResponse) {
                showAlert("Ошибка добавления коментария");
            });
            comment.reset();

        };
        self.autonumberingPages = function () {
            var set = new Set();
            for (var p in  $scope.pageList) {
                if (set.has($scope.pageList[p].name)) {
                    showAlert("Страницы не могут иметь одинаковые номера");
                    return;
                }
                if($scope.pageList[p].name&&$scope.pageList[p].name!="")
                set.add($scope.pageList[p].name);
            }
            var i = 1;
            for (var p in  $scope.pageList) {
                if (!$scope.pageList[p].name||$scope.pageList[p].name==="") {
                    while(set.has(i)){
                        i++;
                    }
                    $scope.pageList[p].name = String(i);
                }
            i++;
            }
        };
/* Функция декоратор. Добавляет анимацию в интерфейсе на время выполнение запросов к серверу.
 * elementId - id элемента интерфейса в который будет добавлена анимация.
 * funct - функция выполняющая запрос к серверу.
 * Стоит доработать функцию что бы стало возможным передавать в параметры для controller.n и  controller.f.*/
        self.processingFunction = function (elementId, funct) {
            var t = $("#" + elementId);
            var animateTeg = $('<span class="glyphicon glyphicon-refresh glyphicon-refresh-animate"></span>');
            t.prop('disabled', true);
            t.prepend(animateTeg);
            controller.n = $scope.pageList.length + self.deletePageList.length + 1;
            controller.f = function () {
                self.findDocument();
                t.prop('disabled', false);
                t.find(animateTeg).remove();
                showAlertSuccess("Изменение обновлено");
            };
            try{
            funct.call();
            }catch(e){
                showAlert(e);
                t.prop('disabled', false);
                t.find(animateTeg).remove();
                controller.n = 0;
                controller.f = function(){};
            }
        };
        self.findDocument();
    }]);
