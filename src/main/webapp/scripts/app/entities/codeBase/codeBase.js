'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('codeBase', {
                parent: 'entity',
                url: '/codeBase',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/codeBase/codeBases.html',
                        controller: 'CodeBaseController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('codeBase');
                        return $translate.refresh();
                    }]
                }
            })
            .state('codeBaseDetail', {
                parent: 'entity',
                url: '/codeBase/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/codeBase/codeBase-detail.html',
                        controller: 'CodeBaseDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('codeBase');
                        return $translate.refresh();
                    }]
                }
            });
    });
