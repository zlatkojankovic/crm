'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerDetailsDetailController', function ($scope, $stateParams, BusinessPartnerDetails, BusinessPartner, BusinessPartnerDetailsType) {
        $scope.businessPartnerDetails = {};
        $scope.load = function (id) {
            BusinessPartnerDetails.get({id: id}, function(result) {
              $scope.businessPartnerDetails = result;
            });
        };
        $scope.load($stateParams.id);
    });
