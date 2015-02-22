'use strict';

angular.module('crmApp')
    .controller('PersonDetailController', function ($scope, $stateParams, Person) {
        $scope.person = {};
        $scope.load = function (id) {
            Person.get({id: id}, function(result) {
              $scope.person = result;
            });
        };
        $scope.load($stateParams.id);
    });
