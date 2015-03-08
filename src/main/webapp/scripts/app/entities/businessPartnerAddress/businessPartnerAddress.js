'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('businessPartnerAddress', {
                parent: 'entity',
                url: '/businessPartnerAddress',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/businessPartnerAddress/businessPartnerAddresss.html',
                        controller: 'BusinessPartnerAddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartnerAddress');
                        return $translate.refresh();
                    }]
                }
            })
            .state('businessPartnerAddressDetail', {
                parent: 'entity',
                url: '/businessPartnerAddress/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/businessPartnerAddress/businessPartnerAddress-detail.html',
                        controller: 'BusinessPartnerAddressDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartnerAddress');
                        return $translate.refresh();
                    }]
                }
            });
    });
