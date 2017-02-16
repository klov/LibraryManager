'use strict';

App.factory('BookService', ['$http', '$q','$document', function ($http, $q,$document) {
        
        self.headers = {};
        self.csrf = $document[0].querySelector("meta[name='_csrf']").getAttribute('content');
        self.csrfHeaderName = $document[0].querySelector("meta[name='_csrf_header']").getAttribute('content');
        self.headers[self.csrfHeaderName] = self.csrf;
        self.headers["Content-Type"] = 'application/json';
        self.form_multiplay_header={};
        self.form_multiplay_header[self.csrfHeaderName] = self.csrf;
        self.form_multiplay_header["Content-Type"]=undefined;
        return {
              updateBook: function (book) {
                return $http.put('/LibraryManager/books/item',
                        JSON.stringify(book), {headers: self.headers}).then(function (response) {
                    return response.data;
                }, function (errResponse) {
                    return $q.reject(errResponse);
                });
            },
            fetchAllBook: function () {
                return $http.get('/LibraryManager/books')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching book');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            fetchAllUser: function () {
                return $http.get(urlEmployeeManager + '/employees')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching book');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            addPageToTheDocument: function (page, document) {
                var fd = new FormData();
                fd.append('documentId', document.id)
                for (var key in page) {
                    fd.append(key, page[key]);
                }
                return $http.post('/LibraryManager/document/page', fd, {
                    transformRequest: angular.identity,
                    headers:  self.form_multiplay_header
                })
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating document');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createBook: function (book) {
                return $http.post('/LibraryManager/books',
                        angular.toJson(book),
                        {headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating book');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteCategory: function (book) {
                return $http({method: 'DELETE',
                    url: '/LibraryManager/books',
                    data: JSON.stringify(book),
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
            },
            createDocument: function (document) {
                var fd = new FormData();
                for (var key in document) {
                    console.log(key + " " + document[key]);
                    fd.append(key, document[key]);
                }
                return $http.post('/LibraryManager/books/create_document/' + document.book, angular.toJson(document),
                        {headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating document');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getBookType: function () {
                return $http.get('/LibraryManager/book_types')
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching bookTypes');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getOrganizations: function () {
                return $http.get('/LibraryManager/organizations',
                        {headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching orgaization');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            getBookCategories: function () {
                return $http.get('/LibraryManager/categories',
                        {headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while fetching category');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            formidFile: function (book, document) {
                var url = '/LibraryManager/books/fileFormation?bookId=' + book.id + '&modification=' + document.modification;
                return $http.get(url)
                        .then(
                                function (response) {
                                    return response;
                                },
                                function (errResponse) {
                                    console.error('Error while formated file');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            chengeDocumentState: function (document) {
                return $http.put('/LibraryManager/document/state',
                        JSON.stringify(document), {headers: self.headers}).then(function (response) {
                    return response.data;
                }, function (errResponse) {
                    return $q.reject(errResponse);
                });
            },            
            getLastDelivery: function (book) {
                return $http.get('/LibraryManager/books/delivery?id=' + book.id,{headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while get delivery');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            addPagesToTheDocument: function (data) {
                var fd = new FormData();
                for (var key in data) {
                    fd.append(key, data[key]);
                }
                return $http.post('/LibraryManager/document/groupPage', fd, {
                    transformRequest: angular.identity,
                    headers:  self.form_multiplay_header
                })
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating document');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            createDelivery: function (delivery) {
                return $http.post('/LibraryManager/delivery',
                        angular.toJson(delivery),
                        {headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating delivery');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deliveryClosing: function (delivery) {
                return $http.put('/LibraryManager/delivery',
                        JSON.stringify(delivery), {headers: self.headers}).then(function (response) {
                    return response.data;
                }, function (errResponse) {
                    return $q.reject(errResponse);
                });
            }
        };
    }]);