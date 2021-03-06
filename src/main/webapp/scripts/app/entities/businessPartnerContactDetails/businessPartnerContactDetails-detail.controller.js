'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerContactDetailsDetailController', function ($scope, $stateParams, BusinessPartnerContactDetails, BusinessPartner, PhoneNumber) {
        $scope.businessPartnerContactDetails = {};
        $scope.load = function (id) {
            BusinessPartnerContactDetails.get({id: id}, function(result) {
              $scope.businessPartnerContactDetails = result;
            });
        };
        $scope.load($stateParams.id);
    });
