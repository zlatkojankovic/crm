'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerController', function ($scope, BusinessPartner, BusinessPartnerDataTransfer, BusinessPartnerDetails, BusinessPartnerAddress,
                                                       BusinessPartnerContactDetails, BankAccount, BankAccountPerPartner) {
        $scope.businessPartners = [];
        $scope.businessPartnerDetailss = BusinessPartnerDetails.query();
        $scope.businessPartnerAddresss = BusinessPartnerAddress.query();
        $scope.businessPartnerContactDetailss = BusinessPartnerContactDetails.query();
        $scope.bankAccountss = [];


        $scope.loadAll = function () {
            BusinessPartner.query(function (result) {
                $scope.businessPartners = result;
                console.log("Prosledujemo "+ $scope.businessPartners[0].id);
                //$scope.BankAccountsPerPartner = BankAccountPerPartner.get({partnerId: $scope.businessPartners[0].id});

                BankAccountPerPartner.query({partnerId: $scope.businessPartners[0].id}, function(result){
                     $scope.BankAccountsPerPartner = result;
                     console.log("ima ovo podatke "+$scope.BankAccountsPerPartner.length);
                });
            });
            BankAccount.query(function (result) {
                $scope.bankAccountss = result;
                for (var i = 0; i < $scope.bankAccountss.length; i++) {
                    console.log($scope.bankAccountss[i]);
                };
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
            $scope.businessPartner = {
                email: null,
                PIB: null,
                status: null,
                registrationNumber: null,
                id: null,
                name: null,
                dateEntry: null,
                person: null
            };
        };

        $scope.transferId = function(partnerId){
            BusinessPartnerDataTransfer.setPartnerID(partnerId);
        }
    });
