'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('businessPartner', {
                parent: 'entity',
                url: '/businessPartner',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/businessPartner/businessPartners.html',
                        controller: 'BusinessPartnerController'
                    },
                    'contactDetailsInsert@businessPartner':{
                        templateUrl: 'scripts/app/entities/businessPartnerContactDetails/businessPartnerContactDetailsModal.html'

                    }
                }

            })
            .state('businessPartnerDetail', {
                parent: 'entity',
                url: '/businessPartner/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/businessPartner/businessPartner-detail.html',
                        controller: 'BusinessPartnerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartner');
                        return $translate.refresh();
                    }]
                }
            })
            .state('contacts', {
                parent: 'businessPartner',
                url:'/businessPartnersContactDetailss',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'contactDetails': {
                        templateUrl: 'scripts/app/entities/businessPartnerContactDetails/businessPartnerContactDetailss.html',
                        controller: 'BusinessPartnerContactDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartner');
                        return $translate.refresh();
                    }]
                }
            })
            .state('businessPartnerContactDetailsPerPartner', {
                parent: 'businessPartner',
                url: '/businessPartnersContactDetailssPerPartner',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'contactDetails': {
                        templateUrl: 'scripts/app/entities/businessPartnerContactDetails/businessPartnerContactDetails.html',
                        controller: 'BusinessPartnerContactDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartner');
                        return $translate.refresh();
                    }]
                }
            })
    });
