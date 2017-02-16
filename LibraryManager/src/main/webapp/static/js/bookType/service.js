'use strict';

App.factory('BookTypeService', ['$http', '$q', '$document', function ($http, $q, $document) {

        self.headers = {};
        self.headers["Content-Type"] = 'application/json';
        self.csrf = $document[0].querySelector("meta[name='_csrf']").getAttribute('content');
        self.csrfHeaderName = $document[0].querySelector("meta[name='_csrf_header']").getAttribute('content');
        self.headers[self.csrfHeaderName] = self.csrf;
        return {
            fetchAllBookTypes: function () {
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
            createBookType: function (bookType) {
                return $http.post('/LibraryManager/book_types/item',
                        JSON.stringify(bookType))
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating bookType');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            updateBookType: function (bookType) {
                return $http.put('/LibraryManager/book_types/item',
                        JSON.stringify(bookType))
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating bookType');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteBookType: function (bookType) {
                return $http({method: 'DELETE',
                    url: '/LibraryManager/book_types/item/',
                    data: JSON.stringify(bookType),
                    headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting bookType');
                                    return $q.reject(errResponse);
                                }
                        );
            }

        };
    }]);


