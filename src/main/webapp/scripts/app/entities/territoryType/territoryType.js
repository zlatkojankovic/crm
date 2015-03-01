'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('territoryType', {
                parent: 'entity',
                url: '/territoryType',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/territoryType/territoryTypes.html',
                        controller: 'TerritoryTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('territoryType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('territoryTypeDetail', {
                parent: 'entity',
                url: '/territoryType/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/territoryType/territoryType-detail.html',
                        controller: 'TerritoryTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('territoryType');
                        return $translate.refresh();
                    }]
                }
            });
    });
