'use strict';

angular.module('crmApp')
    .controller('TerritoryController', function ($scope, Territory, TerritoryType) {
        $scope.territorys = [];
        $scope.territoryTypes = TerritoryType.query();
        $scope.loadAll = function() {
            Territory.query(function(result) {
               $scope.territorys = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Territory.save($scope.territory,
                function () {
                    $scope.loadAll();
                    $('#saveTerritoryModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.territory = Territory.get({id: id});
            $('#saveTerritoryModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.territory = Territory.get({id: id});
            $('#deleteTerritoryConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Territory.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTerritoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.territory = {id: null};
        };
    });
