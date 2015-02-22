'use strict';

angular.module('crmApp')
    .controller('ClassificationController', function ($scope, Classification, CodeBase, ClassificationType) {
        $scope.classifications = [];
        $scope.classifications = Classification.query();
        $scope.codeBases = CodeBase.query();
        $scope.classificationTypes = ClassificationType.query();
        $scope.loadAll = function() {
            Classification.query(function(result) {
               $scope.classifications = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Classification.save($scope.classification,
                function () {
                    $scope.loadAll();
                    $('#saveClassificationModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.classification = Classification.get({id: id});
            $('#saveClassificationModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.classification = Classification.get({id: id});
            $('#deleteClassificationConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Classification.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteClassificationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.classification = {name: null, classificationSegment: null, id: null};
        };
    });
