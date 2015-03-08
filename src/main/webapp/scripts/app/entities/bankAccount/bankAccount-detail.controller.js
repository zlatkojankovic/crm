'use strict';

angular.module('crmApp')
    .controller('BankAccountDetailController', function ($scope, $stateParams, BankAccount, Businesspartner) {
        $scope.bankAccount = {};
        $scope.load = function (id) {
            BankAccount.get({id: id}, function(result) {
              $scope.bankAccount = result;
            });
        };
        $scope.load($stateParams.id);
    });
