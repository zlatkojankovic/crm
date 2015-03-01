'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('classificationType', {
                parent: 'entity',
                url: '/classificationType',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/classificationType/classificationTypes.html',
                        controller: 'ClassificationTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('classificationType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('classificationTypeDetail', {
                parent: 'entity',
                url: '/classificationType/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/classificationType/classificationType-detail.html',
                        controller: 'ClassificationTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('classificationType');
                        return $translate.refresh();
                    }]
                }
            });
    });
