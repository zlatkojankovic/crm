'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerDetailController', function ($scope, $stateParams, BusinessPartner, BusinessPartnerDetails, BusinessPartnerAddress, BusinessPartnerContactDetails, BankAccount) {
        $scope.businessPartner = {};
        $scope.load = function (id) {
            BusinessPartner.get({id: id}, function(result) {
              $scope.businessPartner = result;
            });
        };
        $scope.load($stateParams.id);
    });
