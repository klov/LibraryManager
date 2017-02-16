'use strict';

/*
 * Объект предназначен для выполнения произвольных функций после выполнения запросов к серверу. 
 * Поле f содержит функцию которая будет вызвана как только будут получены ответы от n запросов. 
 * Перед обращениями к серверу нужно установить n и функцию f. f не должна содержать параметров. 
 * Объект контролера содержит функцию inc которая вызывается  ответами сервера в сервисе.
 * После вызова функции сбрасывает счетчик и вызываемую функцию. 
 * @type type
 */
var controller = {
    counter: 0,
    n: 0};
controller.f = function () {
};
controller.inc = function () {
    this.counter++;
    console.log("controller.inc " + this.counter);
    if (this.counter >= this.n) {
        this.f.call();
        this.reset();

    }
};
controller.reset = function () {
    this.counter = 0;
    this.f = function () {
    };
};
App.factory('DocumentService', ['$http', '$q', '$document', function ($http, $q, $document) {
        self.headers = {};
        self.csrfHeaderName = $document[0].querySelector("meta[name='_csrf_header']").getAttribute('content');
        self.csrf = $document[0].querySelector("meta[name='_csrf']").getAttribute('content');
        self.headers[self.csrfHeaderName] = self.csrf;
        self.headers["Content-Type"] = 'application/json';
        self.form_multiplay_header = {};
        self.form_multiplay_header[self.csrfHeaderName] = self.csrf;
        self.form_multiplay_header["Content-Type"] = undefined;
        return {
            findDocument: function (document) {
                return $http.get('/LibraryManager/document/?documentId=' + document.id)
                        .then(function (response) {
                            controller.inc();
                            return response.data;
                        }, function (errResponse) {
                            return $q.reject(errResponse);
                        });
            },
            loadPageForDocument: function (document) {
                return $http.get('/LibraryManager/document/uploadPage?documentId=' + document.id)
                        .then(function (response) {
                            controller.inc();
                            return response.data;
                        }, function (errResponse) {
                            return $q.reject(errResponse);
                        });
            },
            deleteDocument: function (document) {
                return $http({method: 'DELETE',
                    url: '/LibraryManager/document',
                    data: JSON.stringify(document),
                    headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting page');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            updatePage: function (page) {
                return $http.put('/LibraryManager/document/page',
                        JSON.stringify(page), {headers: self.headers}).then(function (response) {
                    controller.inc();
                    return response.data;
                }, function (errResponse) {
                    return $q.reject(errResponse);
                });
            },
            addPageToTheDocument: function (page, document) {
                var fd = new FormData();
                fd.append('documentId', document.id)
                for (var key in page) {
                    fd.append(key, page[key]);
                }
                return $http.post('/LibraryManager/document/page', fd, {
                    transformRequest: angular.identity,
                    headers: self.form_multiplay_header
                })
                        .then(
                                function (response) {
                                    controller.inc();
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating page');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            detetePage: function (page) {
                return $http({method: 'DELETE',
                    url: '/LibraryManager/document/page/',
                    data: JSON.stringify(page),
                    headers: self.headers})
                        .then(
                                function (response) {
                                    controller.inc();
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting page');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            updateDocument: function (document) {
                return $http.put('/LibraryManager/document',
                        JSON.stringify(document),
                        {headers: self.headers}).then(function (response) {
                    controller.inc();
                    return response.data;
                }, function (errResponse) {
                    return $q.reject(errResponse);
                });
            },
            addPagesToTheDocument: function (data) {
                var fd = new FormData();
                for (var key in data) {
                    fd.append(key, data[key]);
                }
                return $http.post('/LibraryManager/document/groupPage', fd, {
                    transformRequest: angular.identity,
                    headers: self.form_multiplay_header
                })
                        .then(
                                function (response) {
                                    controller.inc();
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating document');
                                    return $q.reject(errResponse);
                                }
                        );
            },addComment: function(comment,document){
                return $http.post('/LibraryManager/notice/'+document.id,
                        JSON.stringify(comment),
                        {headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating category');
                                    return $q.reject(errResponse);
                                }
                        );
                
            },removeComment: function(comment){
                  return $http({method: 'DELETE',
                    url: '/LibraryManager/notice',
                    data: JSON.stringify(comment),
                    headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting category');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);