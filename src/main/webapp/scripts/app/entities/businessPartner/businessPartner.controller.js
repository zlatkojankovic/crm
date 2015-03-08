'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerController', function ($scope, BusinessPartner, BusinessPartnerDetails, BusinessPartnerAddress, BusinessPartnerContactDetails, BankAccounts) {
        $scope.businessPartners = [];
        $scope.businessPartnerDetailss = BusinessPartnerDetails.query();
        $scope.businessPartnerAddresss = BusinessPartnerAddress.query();
        $scope.businessPartnerContactDetailss = BusinessPartnerContactDetails.query();
        $scope.bankAccountss = BankAccounts.query();
        $scope.loadAll = function() {
            BusinessPartner.query(function(result) {
               $scope.businessPartners = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            BusinessPartner.save($scope.businessPartner,
                function () {
                    $scope.loadAll();
                    $('#saveBusinessPartnerModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.businessPartner = BusinessPartner.get({id: id});
            $('#saveBusinessPartnerModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.businessPartner = BusinessPartner.get({id: id});
            $('#deleteBusinessPartnerConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            BusinessPartner.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBusinessPartnerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.businessPartner = {email: null, PIB: null, status: null, registrationNumber: null, id: null};
        };
    });
