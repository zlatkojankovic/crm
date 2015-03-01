'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('classification', {
                parent: 'entity',
                url: '/classification',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/classification/classifications.html',
                        controller: 'ClassificationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('classification');
                        return $translate.refresh();
                    }]
                }
            })
            .state('classificationDetail', {
                parent: 'entity',
                url: '/classification/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/classification/classification-detail.html',
                        controller: 'ClassificationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('classification');
                        return $translate.refresh();
                    }]
                }
            });
    });
