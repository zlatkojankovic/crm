'use strict';

angular.module('crmApp')
    .controller('TerritoryTypeController', function ($scope, TerritoryType, Territory) {
        $scope.territoryTypes = [];
        $scope.territorys = Territory.query();
        $scope.loadAll = function() {
            TerritoryType.query(function(result) {
               $scope.territoryTypes = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            TerritoryType.save($scope.territoryType,
                function () {
                    $scope.loadAll();
                    $('#saveTerritoryTypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.territoryType = TerritoryType.get({id: id});
            $('#saveTerritoryTypeModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.territoryType = TerritoryType.get({id: id});
            $('#deleteTerritoryTypeConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            TerritoryType.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTerritoryTypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.territoryType = {name: null, description: null, dateEntry: null, id: null};
        };
    });
