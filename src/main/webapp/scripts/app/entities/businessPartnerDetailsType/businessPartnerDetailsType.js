'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('businessPartnerDetailsType', {
                parent: 'entity',
                url: '/businessPartnerDetailsType',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/businessPartnerDetailsType/businessPartnerDetailsTypes.html',
                        controller: 'BusinessPartnerDetailsTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartnerDetailsType');
                        return $translate.refresh();
                    }]
                }
            })
            .state('businessPartnerDetailsTypeDetail', {
                parent: 'entity',
                url: '/businessPartnerDetailsType/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/businessPartnerDetailsType/businessPartnerDetailsType-detail.html',
                        controller: 'BusinessPartnerDetailsTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartnerDetailsType');
                        return $translate.refresh();
                    }]
                }
            });
    });
