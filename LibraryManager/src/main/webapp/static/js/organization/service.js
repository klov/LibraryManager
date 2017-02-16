'use strict';

App.factory('OrganizationService', ['$http', '$q','$document', function ($http, $q,$document) {
        self.headers = {};
        self.headers["Content-Type"] = 'application/json';
        self.csrfHeaderName = $document[0].querySelector("meta[name='_csrf_header']").getAttribute('content');
        self.csrf = $document[0].querySelector("meta[name='_csrf']").getAttribute('content');
        self.headers[self.csrfHeaderName] = self.csrf;
        return {
            fetchAllOrganization: function () {
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
            createOrganization: function (organization) {
                return $http.post('/LibraryManager/organizations',
                        JSON.stringify(organization),
                        {headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while creating orgaization');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            updateOrganization: function (organization) {
                return $http.put('/LibraryManager/organizations',
                        JSON.stringify(organization),
                        {headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while updating orgaization');
                                    return $q.reject(errResponse);
                                }
                        );
            },
            deleteOrganization: function (organization) {
                return $http({method: 'DELETE',
                    url: '/LibraryManager/organizations',
                    data: JSON.stringify(organization),
                    headers: self.headers})
                        .then(
                                function (response) {
                                    return response.data;
                                },
                                function (errResponse) {
                                    console.error('Error while deleting orgaization');
                                    return $q.reject(errResponse);
                                }
                        );
            }
        };
    }]);