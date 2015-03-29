'use strict';

angular.module('crmApp')
    .controller('DeviceOnPartnerLocationController', function ($scope, DeviceOnPartnerLocation, Device, Businesspartner) {
        $scope.deviceOnPartnerLocations = [];
        $scope.devices = Device.query();
        $scope.businesspartners = Businesspartner.query();
        $scope.loadAll = function() {
            DeviceOnPartnerLocation.query(function(result) {
               $scope.deviceOnPartnerLocations = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            DeviceOnPartnerLocation.save($scope.deviceOnPartnerLocation,
                function () {
                    $scope.loadAll();
                    $('#saveDeviceOnPartnerLocationModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.deviceOnPartnerLocation = DeviceOnPartnerLocation.get({id: id});
            $('#saveDeviceOnPartnerLocationModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.deviceOnPartnerLocation = DeviceOnPartnerLocation.get({id: id});
            $('#deleteDeviceOnPartnerLocationConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            DeviceOnPartnerLocation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDeviceOnPartnerLocationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.deviceOnPartnerLocation = {dateFrom: null, dateTo: null, status: null, comment: null, id: null};
        };
    });
