'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerAddressDetailController', function ($scope, $stateParams, BusinessPartnerAddress, BusinessPartner, Territory) {
        $scope.businessPartnerAddress = {};
        $scope.load = function (id) {
            BusinessPartnerAddress.get({id: id}, function(result) {
              $scope.businessPartnerAddress = result;
            });
        };
        $scope.load($stateParams.id);
    });
