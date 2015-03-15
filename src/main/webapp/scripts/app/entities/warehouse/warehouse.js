'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('warehouse', {
                parent: 'entity',
                url: '/warehouse',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/warehouse/warehouses.html',
                        controller: 'WarehouseController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('warehouse');
                        return $translate.refresh();
                    }]
                }
            })
            .state('warehouseDetail', {
                parent: 'entity',
                url: '/warehouse/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/warehouse/warehouse-detail.html',
                        controller: 'WarehouseDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('warehouse');
                        return $translate.refresh();
                    }]
                }
            });
    });
