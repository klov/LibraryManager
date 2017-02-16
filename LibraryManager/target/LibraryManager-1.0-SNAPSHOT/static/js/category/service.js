'use strict';

App.factory('CategoryService', ['$http', '$q', '$document', function ($http, $q, $document) {
        self.headers = {};
        self.csrfHeaderName = $document[0].querySelector("meta[name='_csrf_header']").getAttribute('content');
        self.csrf = $document[0].querySelector("meta[name='_csrf']").getAttribute('content');
        self.headers[self.csrfHeaderName] = self.csrf;
        self.headers["Content-Type"] = 'application/json';
        return {
            fetchAllCategory: function () {
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
            createCategory: function (category) {
                return $http.post('/LibraryManager/categories',
                        JSON.stringify(category),
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
            },
            updateCategory: function (category) {
                return $http.put('/LibraryManager/categories',
                        JSON.stringify(category),
                        {headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating category');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteCategory: function (category) {
                return $http({method: 'DELETE',
                    url: '/LibraryManager/categories',
                    data: JSON.stringify(category),
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