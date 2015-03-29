'use strict';

angular.module('crmApp')
    .controller('ItemDetailController', function ($scope, $stateParams, Item, UnitOfMeasure) {
        $scope.item = {};
        $scope.load = function (id) {
            Item.get({id: id}, function(result) {
              $scope.item = result;
            });
        };
        $scope.load($stateParams.id);
    });
