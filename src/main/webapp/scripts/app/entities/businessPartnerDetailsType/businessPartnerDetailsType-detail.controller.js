'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerDetailsTypeDetailController', function ($scope, $stateParams, BusinessPartnerDetailsType, BusinessPartnerDetails) {
        $scope.businessPartnerDetailsType = {};
        $scope.load = function (id) {
            BusinessPartnerDetailsType.get({id: id}, function(result) {
              $scope.businessPartnerDetailsType = result;
            });
        };
        $scope.load($stateParams.id);
    });
