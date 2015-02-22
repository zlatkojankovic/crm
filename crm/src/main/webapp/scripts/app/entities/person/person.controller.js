'use strict';

angular.module('crmApp')
    .controller('PersonController', function ($scope, Person) {
        $scope.persons = [];
        $scope.loadAll = function() {
            Person.query(function(result) {
               $scope.persons = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Person.save($scope.person,
                function () {
                    $scope.loadAll();
                    $('#savePersonModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.person = Person.get({id: id});
            $('#savePersonModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.person = Person.get({id: id});
            $('#deletePersonConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Person.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePersonConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.person = {firstName: null, lastName: null, defaultAddress: null, nationalNumber: null, password: null, username: null, dateEntry: null, dateValid: null, id: null};
        };
    });
