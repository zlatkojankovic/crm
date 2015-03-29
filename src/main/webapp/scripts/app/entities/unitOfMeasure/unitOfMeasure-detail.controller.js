'use strict';

angular.module('crmApp')
    .controller('UnitOfMeasureDetailController', function ($scope, $stateParams, UnitOfMeasure) {
        $scope.unitOfMeasure = {};
        $scope.load = function (id) {
            UnitOfMeasure.get({id: id}, function(result) {
              $scope.unitOfMeasure = result;
            });
        };
        $scope.load($stateParams.id);
    });
