'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerContactDetailsController', function ($scope, $state, BusinessPartnerContactDetails, BusinessPartner, PhoneNumber) {
        $scope.businessPartnerContactDetailss = [];
        $scope.businessPartners = BusinessPartner.query();
        $scope.phneNumbers = PhoneNumber.query();
        $scope.loadAll = function () {
            BusinessPartnerContactDetails.query(function (result) {
                $scope.businessPartnerContactDetailss = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            BusinessPartnerContactDetails.save($scope.businessPartnerContactDetails,
                function () {
                    $scope.loadAll();
                    $('#saveBusinessPartnerContactDetailsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.businessPartnerContactDetails = BusinessPartnerContactDetails.get({id: id});
            $('#saveBusinessPartnerContactDetailsModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.businessPartnerContactDetails = BusinessPartnerContactDetails.get({id: id});
            $('#deleteBusinessPartnerContactDetailsConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            BusinessPartnerContactDetails.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBusinessPartnerContactDetailsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.businessPartnerContactDetails = {
                firstName: null,
                lastName: null,
                email: null,
                address: null,
                dateFrom: null,
                dateTo: null,
                id: null
            };
        };

        $scope.$on('TRANSFER_BPARTNER_ID', function (event, data) {
            console.log("broadcasting data " + data); // 'Some data'
            $state.go('businessPartnerContactDetailsDetailPerPartner', {partnerId: data});
            console.log("okida mi on on broadcasting ali neki je drugi promblme");

        });
    });
