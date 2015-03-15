'use strict';

angular.module('crmApp')
    .controller('WarehouseDetailController', function ($scope, $stateParams, Warehouse, Territory, CodeBase) {
        $scope.warehouse = {};
        $scope.load = function (id) {
            Warehouse.get({id: id}, function(result) {
              $scope.warehouse = result;
            });
        };
        $scope.load($stateParams.id);
    });
