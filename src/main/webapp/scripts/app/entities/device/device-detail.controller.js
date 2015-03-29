'use strict';

angular.module('crmApp')
    .controller('DeviceDetailController', function ($scope, $stateParams, Device, Item, DeviceOnPartnerLocation) {
        $scope.device = {};
        $scope.load = function (id) {
            Device.get({id: id}, function(result) {
              $scope.device = result;
            });
        };
        $scope.load($stateParams.id);
    });
