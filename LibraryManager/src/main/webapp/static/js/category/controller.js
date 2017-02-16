'use strict';

App.controller('CategoryController', ['$scope', 'CategoryService',
    function ($scope, CategoryService) {
        var self = this;
        self.category = {id: null, name: ''};
        self.categories = [];
        self.categoriesId = '';
        self.currentPage = 0;
        self.pageSize = 5;
        self.pageCount = 0;
        self.pagePanel = {
            prev: false,
            next: true,
            prevLabel: false,
            nextLabel: true,
            prevN: false,
            nextN: true,
            first: false,
            last: true
        }

        self.showHide = function () {
            self.showForm = !self.showForm;
        }

        self.showHideClass = function () {
            return self.showForm ? "lead ui-icon ui-icon-triangle-1-s" : "lead ui-icon ui-icon-triangle-1-e";
        }

        self.defPagePanelAttribute = function (num) {
            self.pagePanel = {
                prev: false,
                next: true,
                prevLabel: false,
                nextLabel: true,
                prevN: false,
                nextN: true,
                first: false,
                last: true
            };
            if (num == 1) {
                self.pagePanel.prev = false;
                self.pagePanel.first = true;
            }
            if (num > 1) {
                self.pagePanel.prev = true;
                self.pagePanel.first = true;
            }
            if (num > 2) {
                self.pagePanel.prevLabel = true;
            }
            if (num >= 5) {
                self.pagePanel.prevN = true;
            }
            var size = Math.ceil(self.groupusers.length / self.pageSize);
            if ((num + 5) >= size) {
                self.pagePanel.nextN = false;
            }
            if ((num + 3) >= size) {
                self.pagePanel.nextLabel = false;
            }
            if (num >= (size - 2)) {
                self.pagePanel.next = false;
            }
            if (num == (size - 1)) {
                self.pagePanel.last = false;
            }
        };

        self.firstPage = function () {
            self.currentPage = 0;
            self.defPagePanelAttribute(self.currentPage);
        }

        self.lastPage = function () {
            self.currentPage = Math.ceil(self.groupusers.length / self.pageSize) - 1;
            self.defPagePanelAttribute(self.currentPage);
        }

        self.nextPage = function (count) {
            if ((self.groupusers.length - 1) >= (self.currentPage + count)) {
                self.currentPage += count;
            }
            self.defPagePanelAttribute(self.currentPage);
        };

        self.prevPage = function (count) {
            if (self.currentPage >= count) {
                self.currentPage -= count;
            }
            self.defPagePanelAttribute(self.currentPage);
        };

        self.numberOfPages = function () {
            self.pageCount = Math.ceil(self.groupusers.length / self.pageSize);
            self.defPagePanelAttribute(self.currentPage);
            return self.pageCount;
        };

        self.fetchAllCategories = function () {
            CategoryService.fetchAllCategory()
                    .then(
                            function (d) {
                                self.categories = d;
                            },
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while fetching Category');
                            }
                    );
        };
        self.fetchAllCategories();
        
        self.createCategory = function (categories) {
            CategoryService.createCategory(categories)
                    .then(
                            self.fetchAllCategories,
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while creating Category.');
                            }
                    );
        };

        self.deleteCategory = function (categoriesId) {
            var category = {}
            category.id = categoriesId.id;
            CategoryService.deleteCategory(category)
                    .then(
                            self.fetchAllCategories,
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while deleting ');
                            }
                    );
        };

        self.submit = function () {
            if (self.category.id === null) {
                self.createCategory(self.category);
            } else {
                self.updateCategory(self.category);
            }
            self.reset();
        };

        self.reset = function () {
            self.category = {id: null, name: ''}
            $scope.myForm.$setPristine(); //reset Form
        };


        self.edit = function (u) {
            self.category.id = u.id;
            self.category.name = u.name;
        };

        self.updateCategory = function (categories) {
            CategoryService.updateCategory(categories)
                    .then(
                            self.fetchAllCategories,
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while updating Category.');
                            }
                    );
        };

        self.removeCurUser = function () {
            console.log('user to be deleted', self.curUserId);
            if (self.user.id === self.curUserId) {//clean form if the organization to be deleted is shown there.
                self.reset();
            }
            self.deleteUser(self.curUserId);
        };

        self.showConfirmDialogThenDelete = function (userId) {
            self.curUserId = userId;
            $('#dialog-confirm').dialog('open');
        };
        self.changeOrder = function (order) {
            self.orderProp = order;
        };
    }]);


var compareTo = function () {
    return {
        require: "ngModel",
        scope: {
            otherModelValue: "=compareTo"
        },
        link: function (scope, element, attributes, ngModel) {
            ngModel.$validators.compareTo = function (modelValue) {
                return modelValue == scope.otherModelValue;
            };
            scope.$watch("otherModelValue", function () {
                ngModel.$validate();
            });
        }
    };
};

App.directive("compareTo", compareTo);
App.filter('startFrom', function () {
    return function (input, start) {
        return input.slice(start);
    };
});