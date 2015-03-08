'use strict';

angular.module('crmApp')
    .controller('BusinessPartnerDetailsTypeController', function ($scope, BusinessPartnerDetailsType, BusinessPartnerDetails) {
        $scope.businessPartnerDetailsTypes = [];
        $scope.businessPartnerDetailss = BusinessPartnerDetails.query();
        $scope.loadAll = function() {
            BusinessPartnerDetailsType.query(function(result) {
               $scope.businessPartnerDetailsTypes = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            BusinessPartnerDetailsType.save($scope.businessPartnerDetailsType,
                function () {
                    $scope.loadAll();
                    $('#saveBusinessPartnerDetailsTypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.businessPartnerDetailsType = BusinessPartnerDetailsType.get({id: id});
            $('#saveBusinessPartnerDetailsTypeModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.businessPartnerDetailsType = BusinessPartnerDetailsType.get({id: id});
            $('#deleteBusinessPartnerDetailsTypeConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            BusinessPartnerDetailsType.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBusinessPartnerDetailsTypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.businessPartnerDetailsType = {name: null, description: null, dateEntry: null, id: null};
        };
    });
