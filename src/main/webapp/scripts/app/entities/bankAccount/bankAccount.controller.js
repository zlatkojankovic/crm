'use strict';

angular.module('crmApp')
    .controller('BankAccountController', function ($scope, BankAccount, BusinessPartner) {
        $scope.bankAccounts = [];
        $scope.businessPartners = BusinessPartner.query();
        $scope.loadAll = function() {
            BankAccount.query(function(result) {
               $scope.bankAccounts = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            BankAccount.save($scope.bankAccount,
                function () {
                    console.log("Partner " + $scope.bankAccount.businessPartner.id);
                    $scope.loadAll();
                    $('#saveBankAccountModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.bankAccount = BankAccount.get({id: id});
            $('#saveBankAccountModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.bankAccount = BankAccount.get({id: id});
            $('#deleteBankAccountConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            BankAccount.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBankAccountConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.bankAccount = {accountNumber: null, status: null, dateFrom: null, dateTo: null, type: null, id: null};
        };
    });
