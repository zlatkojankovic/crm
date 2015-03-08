'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerDetailsController', function ($scope, BusinessPartnerDetails, BusinessPartner, BusinessPartnerDetailsType) {
        $scope.businessPartnerDetailss = [];
        $scope.businessPartners = BusinessPartner.query();
        $scope.businessPartnerDetailsTypes = BusinessPartnerDetailsType.query();
        $scope.loadAll = function() {
            BusinessPartnerDetails.query(function(result) {
               $scope.businessPartnerDetailss = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            BusinessPartnerDetails.save($scope.businessPartnerDetails,
                function () {
                    $scope.loadAll();
                    $('#saveBusinessPartnerDetailsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.businessPartnerDetails = BusinessPartnerDetails.get({id: id});
            $('#saveBusinessPartnerDetailsModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.businessPartnerDetails = BusinessPartnerDetails.get({id: id});
            $('#deleteBusinessPartnerDetailsConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            BusinessPartnerDetails.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBusinessPartnerDetailsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.businessPartnerDetails = {content: null, dateFrom: null, dateTo: null, contentVersion: null, id: null};
        };
    });
