'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('unitOfMeasure', {
                parent: 'entity',
                url: '/unitOfMeasure',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/unitOfMeasure/unitOfMeasures.html',
                        controller: 'UnitOfMeasureController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('unitOfMeasure');
                        return $translate.refresh();
                    }]
                }
            })
            .state('unitOfMeasureDetail', {
                parent: 'entity',
                url: '/unitOfMeasure/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/unitOfMeasure/unitOfMeasure-detail.html',
                        controller: 'UnitOfMeasureDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('unitOfMeasure');
                        return $translate.refresh();
                    }]
                }
            });
    });
