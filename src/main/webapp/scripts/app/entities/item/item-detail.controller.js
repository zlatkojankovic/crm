'use strict';

angular.module('crmApp')
    .controller('ItemDetailController', function ($scope, $stateParams, Item, UnitOfMeasure) {

        $scope.item = {};
        $scop.item.dateEntry = new Date();
        $scope.load = function (id) {
            Item.get({id: id}, function(result) {
              $scope.item = result;
            });
        };
        $scope.load($stateParams.id);

    });
