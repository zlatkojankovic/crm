'use strict';

angular.module('crmApp')
    .controller('CodeBaseController', function ($scope, CodeBase, Person, Classification) {
        $scope.codeBases = [];
        $scope.persons = Person.query();
        $scope.classifications = Classification.query();
        $scope.codeBases = CodeBase.query();
        $scope.loadAll = function() {
            CodeBase.query(function(result) {
               $scope.codeBases = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            CodeBase.save($scope.codeBase,
                function () {
                    $scope.loadAll();
                    $('#saveCodeBaseModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.codeBase = CodeBase.get({id: id});
            $('#saveCodeBaseModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.codeBase = CodeBase.get({id: id});
            $('#deleteCodeBaseConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            CodeBase.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCodeBaseConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.codeBase = {name: null, dateEntry: null, dateValid: null, id: null};
        };
    });
