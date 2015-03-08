'use strict';

angular.module('crmApp')
    .controller('BussinesPartnerDetailsDetailController', function ($scope, $stateParams, BussinesPartnerDetails, BusinessPartnerDetailsType, BusinessPartner) {
        $scope.bussinesPartnerDetails = {};
        $scope.load = function (id) {
            BussinesPartnerDetails.get({id: id}, function(result) {
              $scope.bussinesPartnerDetails = result;
            });
        };
        $scope.load($stateParams.id);
    });
