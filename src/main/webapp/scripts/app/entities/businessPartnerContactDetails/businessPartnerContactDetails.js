'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('businessPartnerContactDetails', {
                parent: 'entity',
                url: '/businessPartnerContactDetails',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/businessPartnerContactDetails/businessPartnerContactDetailss.html',
                        controller: 'BusinessPartnerContactDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartnerContactDetails');
                        return $translate.refresh();
                    }]
                }
            })
            .state('businessPartnerContactDetailsDetail', {
                parent: 'entity',
                url: '/businessPartnerContactDetails/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/businessPartnerContactDetails/businessPartnerContactDetails-detail.html',
                        controller: 'BusinessPartnerContactDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartnerContactDetails');
                        return $translate.refresh();
                    }]
                }
            });
    });
