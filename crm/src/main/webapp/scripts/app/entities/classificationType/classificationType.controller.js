'use strict';

angular.module('crmApp')
    .controller('ClassificationTypeController', function ($scope, ClassificationType, Classification) {
        $scope.classificationTypes = [];
        $scope.classifications = Classification.query();
        $scope.loadAll = function() {
            ClassificationType.query(function(result) {
               $scope.classificationTypes = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            ClassificationType.save($scope.classificationType,
                function () {
                    $scope.loadAll();
                    $('#saveClassificationTypeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.classificationType = ClassificationType.get({id: id});
            $('#saveClassificationTypeModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.classificationType = ClassificationType.get({id: id});
            $('#deleteClassificationTypeConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            ClassificationType.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteClassificationTypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.classificationType = {name: null, mandatory: null, objectClassificationType: null, id: null};
        };
    });
