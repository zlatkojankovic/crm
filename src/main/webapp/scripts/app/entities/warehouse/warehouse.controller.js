'use strict';

angular.module('crmApp')
    .controller('WarehouseController', function ($scope, Warehouse, Territory, CodeBase) {
        $scope.warehouses = [];
        $scope.territorys = Territory.query();
        $scope.codeBases = CodeBase.query();
        $scope.loadAll = function() {
            Warehouse.query(function(result) {
               $scope.warehouses = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Warehouse.save($scope.warehouse,
                function () {
                    $scope.loadAll();
                    $('#saveWarehouseModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.warehouse = Warehouse.get({id: id});
            $('#saveWarehouseModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.warehouse = Warehouse.get({id: id});
            $('#deleteWarehouseConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Warehouse.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteWarehouseConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.warehouse = {id: null};
        };
    });
