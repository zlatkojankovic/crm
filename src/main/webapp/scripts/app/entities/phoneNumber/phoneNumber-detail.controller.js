'use strict';

angular.module('crmApp')
    .controller('PhoneNumberDetailController', function ($scope, $stateParams, PhoneNumber, BusinessPartnerContactDetails) {
        $scope.phoneNumber = {};
        $scope.load = function (id) {
            PhoneNumber.get({id: id}, function(result) {
              $scope.phoneNumber = result;
            });
        };
        $scope.load($stateParams.id);
    });
