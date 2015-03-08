'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('businessPartnerDetails', {
                parent: 'entity',
                url: '/businessPartnerDetails',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/businessPartnerDetails/businessPartnerDetailss.html',
                        controller: 'BusinessPartnerDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartnerDetails');
                        return $translate.refresh();
                    }]
                }
            })
            .state('businessPartnerDetailsDetail', {
                parent: 'entity',
                url: '/businessPartnerDetails/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/businessPartnerDetails/businessPartnerDetails-detail.html',
                        controller: 'BusinessPartnerDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartnerDetails');
                        return $translate.refresh();
                    }]
                }
            });
    });
