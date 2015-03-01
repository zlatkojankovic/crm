'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('territory', {
                parent: 'entity',
                url: '/territory',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/territory/territorys.html',
                        controller: 'TerritoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('territory');
                        return $translate.refresh();
                    }]
                }
            })
            .state('territoryDetail', {
                parent: 'entity',
                url: '/territory/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/territory/territory-detail.html',
                        controller: 'TerritoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('territory');
                        return $translate.refresh();
                    }]
                }
            });
    });
