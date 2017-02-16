'use strict';

App.controller('BookTypeController', ['$scope', 'BookTypeService',
    function ($scope, BookTypeService) {
        var self = this;
        self.bookType = {id: null, name: ''};
        self.currentBookType = {id: null, name: ''};
        self.bookTypes = [];

        self.fetchAllBookTypes = function () {
            BookTypeService.fetchAllBookTypes()
                    .then(
                            function (d) {
                                self.bookTypes = d;
                            },
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while fetching BookTypes');
                            }
                    );
        };

        self.fetchAllBookTypes();

        self.createBookType = function (bookType) {
            BookTypeService.createBookType(bookType)
                    .then(
                            self.fetchAllBookTypes,
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while creating BookType.');
                            }
                    );
        };

        self.updateBookType = function (bookType) {
            BookTypeService.updateBookType(bookType)
                    .then(
                            self.fetchAllBookTypes,
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while updating BookType.');
                            }
                    );
        };

        self.deleteBookType = function (bookType) {
            BookTypeService.deleteBookType(bookType)
                    .then(
                            self.fetchAllBookTypes,
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while deleting BookType.');
                            }
                    );
        };

        self.submit = function () {
            if (self.bookType.id === null) {
                console.log('Saving New BookType', self.bookType);
                self.createBookType(self.bookType);
            } else {
                self.updateBookType(self.bookType);
                console.log('BookType updated with id ', self.bookType.id);
                console.log('BookType updated to name ', self.bookType.name);
            }
            self.reset();
        };

        self.edit = function (bookType) {
            console.log('BookType name to be edited', bookType.name);
            self.bookType.id = bookType.id;
            self.bookType.name = bookType.name;
            $scope.myForm.$setDirty();
        };

        self.reset = function () {
            self.bookType = {id: null, name: ''};
            $scope.myForm.$setPristine(); //reset Form
        };

    }]);