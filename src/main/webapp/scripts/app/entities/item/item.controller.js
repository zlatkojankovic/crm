'use strict';

angular.module('crmApp')
    .controller('ItemController', function ($scope, Item, UnitOfMeasure) {
        $scope.items = [];
        $scope.unitOfMeasures = UnitOfMeasure.query();
        $scope.loadAll = function() {
            Item.query(function(result) {
               $scope.items = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Item.save($scope.item,
                function () {
                    $scope.loadAll();
                    $('#saveItemModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.item = Item.get({id: id});
            $('#saveItemModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.item = Item.get({id: id});
            $('#deleteItemConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Item.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteItemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.item = {description: null, id: null};
        };
    });
