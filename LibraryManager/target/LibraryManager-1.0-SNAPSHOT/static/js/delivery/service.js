'use strict';

App.factory('DeliveryService', ['$http', '$q', '$document', function ($http, $q, $document) {
        self.headers = {};
        self.csrfHeaderName = $document[0].querySelector("meta[name='_csrf_header']").getAttribute('content');
        self.csrf = $document[0].querySelector("meta[name='_csrf']").getAttribute('content');
        self.headers[self.csrfHeaderName] = self.csrf;
        self.headers["Content-Type"] = 'application/json';
        return {
            getBook: function (id) {
                return $http.get('/LibraryManager/books/item/?bookId=' + id,
                        {headers: self.headers})
                        .then(
                                function (response) {
                                     return $http.put('/LibraryManager/books/item',
                        JSON.stringify(book), {headers: self.headers}).then(function (response) {
                    return response.data;
                }, function (errResponse) {
                    return $q.reject(errResponse);
                });
                                },
                                function (errResponse) {
                                    console.error('Error while get Book');
                                    return $q.reject(errResponse);
                                }
                        );
            },updateDelivery: function (delivery) {
                return $http.put('/LibraryManager/delivery',
                        JSON.stringify(delivery), {headers: self.headers}).then(function (response) {
                    return response.data;
                }, function (errResponse) {
                    return $q.reject(errResponse);
                });
            }, fetchAllDeliveryForBook: function (bookId) {
                return $http.get('/LibraryManager/delivery/item/?bookId=' + bookId)
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while get delivery');
                                    return $q.reject(errResponse);
                                }
                        );
            }, fetchAllUser: function () {
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
            }
        };
    }]);