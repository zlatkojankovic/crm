'use strict';

angular.module('crmApp')
    .controller('CodeBaseDetailController', function ($scope, $stateParams, CodeBase, Person, Classification) {
        $scope.codeBase = {};
        $scope.load = function (id) {
            CodeBase.get({id: id}, function(result) {
              $scope.codeBase = result;
            });
        };
        $scope.load($stateParams.id);
    });
