'use strict';

angular.module('crmApp')
    .controller('ClassificationTypeDetailController', function ($scope, $stateParams, ClassificationType, Classification) {
        $scope.classificationType = {};
        $scope.load = function (id) {
            ClassificationType.get({id: id}, function(result) {
              $scope.classificationType = result;
            });
        };
        $scope.load($stateParams.id);
    });
