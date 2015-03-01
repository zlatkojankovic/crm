'use strict';

angular.module('crmApp')
    .controller('TerritoryDetailController', function ($scope, $stateParams, Territory, TerritoryType) {
        $scope.territory = {};
        $scope.load = function (id) {
            Territory.get({id: id}, function(result) {
              $scope.territory = result;
            });
        };
        $scope.load($stateParams.id);
    });
