'use strict';

angular.module('crmApp')
    .controller('ClassificationDetailController', function ($scope, $stateParams, Classification, CodeBase, ClassificationType) {
        $scope.classification = {};
        $scope.load = function (id) {
            Classification.get({id: id}, function(result) {
              $scope.classification = result;
            });
        };
        $scope.load($stateParams.id);
    });
