'use strict';

App.controller('OrganizationController', ['$scope', 'OrganizationService',
    function ($scope, OrganizationService) {
        var self = this;
        self.organization = {id: null, name: '',address: ''};
        self.organizations = [];
        self.organizationId = '';
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

        self.fetchAllOrganization= function () {
            OrganizationService.fetchAllOrganization()
                    .then(
                            function (d) {
                                self.organizations = d;
                            },
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while fetching Category');
                            }
                    );
        };

        self.fetchAllOrganization();

        self.createOrganization = function (categories) {
            OrganizationService.createOrganization(categories)
                    .then(
                            function (rez) {
                                self.fetchAllOrganization();
                            },
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while creating Category.');
                            }
                    );
        };

        self.deleteOrganization = function (organizationId) {
            var organization = {}
            organization.id = organizationId.id;
            OrganizationService.deleteOrganization(organization)
                    .then(
                            self.fetchAllOrganization,
                            function (errResponse) {
                                showAlert(errResponse);
                                console.error('Error while deleting ');
                            }
                    );
        };

        self.submit = function () {
            if (self.organization.id === null) {
                self.createOrganization(self.organization);
            } else {
                self.updateOrganization(self.organization);
            }
            self.reset();
        };

        self.reset = function () {
            self.organization =  {id: null, name: '',address: ''};
            $scope.myForm.$setPristine(); //reset Form
        };


        self.edit = function (u) {
            self.organization.id = u.id;
            self.organization.name = u.name;
            self.organization.address = u.address;
        };

        self.updateOrganization = function (categories) {
            OrganizationService.updateOrganization(categories)
                    .then(
                            self.fetchAllOrganization,
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