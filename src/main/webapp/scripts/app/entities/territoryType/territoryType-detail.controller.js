'use strict';

angular.module('crmApp')
    .controller('TerritoryTypeDetailController', function ($scope, $stateParams, TerritoryType, Territory) {
        $scope.territoryType = {};
        $scope.load = function (id) {
            TerritoryType.get({id: id}, function(result) {
              $scope.territoryType = result;
            });
        };
        $scope.load($stateParams.id);
    });
