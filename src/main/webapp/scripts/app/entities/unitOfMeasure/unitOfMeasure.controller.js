'use strict';

angular.module('crmApp')
    .controller('UnitOfMeasureController', function ($scope, UnitOfMeasure) {
        $scope.unitOfMeasures = [];
        $scope.loadAll = function() {
            UnitOfMeasure.query(function(result) {
               $scope.unitOfMeasures = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            UnitOfMeasure.save($scope.unitOfMeasure,
                function () {
                    $scope.loadAll();
                    $('#saveUnitOfMeasureModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.unitOfMeasure = UnitOfMeasure.get({id: id});
            $('#saveUnitOfMeasureModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.unitOfMeasure = UnitOfMeasure.get({id: id});
            $('#deleteUnitOfMeasureConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            UnitOfMeasure.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteUnitOfMeasureConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.unitOfMeasure = {name: null, shortName: null, id: null};
        };
    });
