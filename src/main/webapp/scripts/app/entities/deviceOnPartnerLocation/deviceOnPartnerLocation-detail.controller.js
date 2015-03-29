'use strict';

angular.module('crmApp')
    .controller('DeviceOnPartnerLocationDetailController', function ($scope, $stateParams, DeviceOnPartnerLocation, Device, Businesspartner) {
        $scope.deviceOnPartnerLocation = {};
        $scope.load = function (id) {
            DeviceOnPartnerLocation.get({id: id}, function(result) {
              $scope.deviceOnPartnerLocation = result;
            });
        };
        $scope.load($stateParams.id);
    });
