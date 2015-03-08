'use strict';

angular.module('crmApp')
    .controller('BussinesPartnerDetailsController', function ($scope, BussinesPartnerDetails, BusinessPartnerDetailsType, BusinessPartner) {
        $scope.bussinesPartnerDetailss = [];
        $scope.businessPartnerDetailsTypes = BusinessPartnerDetailsType.query();
        $scope.businessPartners = BusinessPartner.query();
        $scope.loadAll = function() {
            BussinesPartnerDetails.query(function(result) {
               $scope.bussinesPartnerDetailss = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            BussinesPartnerDetails.save($scope.bussinesPartnerDetails,
                function () {
                    $scope.loadAll();
                    $('#saveBussinesPartnerDetailsModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.bussinesPartnerDetails = BussinesPartnerDetails.get({id: id});
            $('#saveBussinesPartnerDetailsModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.bussinesPartnerDetails = BussinesPartnerDetails.get({id: id});
            $('#deleteBussinesPartnerDetailsConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            BussinesPartnerDetails.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBussinesPartnerDetailsConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.bussinesPartnerDetails = {content: null, dateFrom: null, dateTo: null, description: null, contentVersion: null, n: null, id: null};
        };
    });
