'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerAddressController', function ($scope, BusinessPartnerAddress, BusinessPartner, Territory) {
        $scope.businessPartnerAddresss = [];
        $scope.businessPartners = BusinessPartner.query();
        $scope.territorys = Territory.query();
        $scope.loadAll = function() {
            BusinessPartnerAddress.query(function(result) {
               $scope.businessPartnerAddresss = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            BusinessPartnerAddress.save($scope.businessPartnerAddress,
                function () {
                    $scope.loadAll();
                    $('#saveBusinessPartnerAddressModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.businessPartnerAddress = BusinessPartnerAddress.get({id: id});
            $('#saveBusinessPartnerAddressModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.businessPartnerAddress = BusinessPartnerAddress.get({id: id});
            $('#deleteBusinessPartnerAddressConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            BusinessPartnerAddress.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBusinessPartnerAddressConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.businessPartnerAddress = {street: null, number: null, id: null};
        };
    });
