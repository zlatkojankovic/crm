'use strict';

angular.module('crmApp')
    .controller('PhoneNumberController', function ($scope, PhoneNumber, BusinessPartnerContactDetails) {
        $scope.phoneNumbers = [];
        $scope.businessPartnerContactDetailss = BusinessPartnerContactDetails.query();
        $scope.loadAll = function() {
            PhoneNumber.query(function(result) {
               $scope.phoneNumbers = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            PhoneNumber.save($scope.phoneNumber,
                function () {
                    $scope.loadAll();
                    $('#savePhoneNumberModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.phoneNumber = PhoneNumber.get({id: id});
            $('#savePhoneNumberModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.phoneNumber = PhoneNumber.get({id: id});
            $('#deletePhoneNumberConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            PhoneNumber.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePhoneNumberConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.phoneNumber = {number: null, type: null, id: null};
        };
    });
