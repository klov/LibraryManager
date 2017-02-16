'use strict';

App.controller('DeliveryController', ['$scope', 'DeliveryService',
    function ($scope, DeliveryService) {
        var self = this;
        self.users = [];
        self.selectedDel = {};
        self.bookId = getUrlVars()["bookId"];
        self.deliveries = [];
        self.editDelivery = function (delivery) {
            $('#myModal').modal();
            self.selectedDel = angular.copy(delivery);
        };
        self.reset = function () {
            self.selectedDel = {};
        };
        self.updatDelivery = function (delivery) {
            var eid = findEmployeeId(delivery.eployeeName);
            if (eid == "") {
                showAlert("Неверно введено ФИО сотрудника");
            } else {
                var newDelivery = {};
                newDelivery.employeeid = eid;
                newDelivery.book = delivery.book;
                newDelivery.deliverydate = delivery.deliverydate;
                newDelivery.surrenderdate = delivery.surrenderdate;
                newDelivery.id = delivery.id;
                delivery.employeeid = eid;
                DeliveryService.updateDelivery(delivery).then(function (response) {
                    self.fetchAllUser();
                    self.fetchAllDeliveryForBook(self.bookId);
                }, function (errResponse) {
                    showAlert("Ошибка обновления выдачи");
                    console.error('Error while fetching book');
                });
            }
        };
        self.fio = function (employee) {
            return employee.lastname + " " + employee.firstname + " " + employee.secondname;
        };

        var findEmployeeId = function (employeeName) {
            for (var i = 0; i < self.users.length; i++) {
                if (self.fio(self.users[i]) === employeeName) {
                    return self.users[i].id;
                }
            }
            return"";
        };

        self.findEmployeeName = function (employeeId) {
            for (var i = 0; i < self.users.length; i++) {
                if (self.users[i].id == employeeId) {
                    return self.fio(self.users[i]);
                }
            }
            return "";
        };
        self.fetchAllUser = function () {
            DeliveryService.fetchAllUser().then(function (responce) {
                self.users = responce;
            }, function (errResponce) {
                showAlert("Ошибка при загрузке списка пользователей");
                console.error('Error of sample of users ');
            });
        };
        self.fetchAllDeliveryForBook = function (book) {
            DeliveryService.fetchAllDeliveryForBook(book).then(function (response) {
                self.deliveries = response;
            }, function (errResponse) {
                showAlert("Ошибка при загрузке списка выдачи");
                console.error('Error of sample of delivery ');
            });
        };
        self.fetchAllUser();
        self.fetchAllDeliveryForBook(self.bookId);
    }]);

App.filter('startFrom', function () {
    return function (input, start) {
        return input.slice(start);
    };
});