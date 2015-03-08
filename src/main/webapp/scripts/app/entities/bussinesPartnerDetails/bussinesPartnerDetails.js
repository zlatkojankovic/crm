'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bussinesPartnerDetails', {
                parent: 'entity',
                url: '/bussinesPartnerDetails',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bussinesPartnerDetails/bussinesPartnerDetailss.html',
                        controller: 'BussinesPartnerDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bussinesPartnerDetails');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bussinesPartnerDetailsDetail', {
                parent: 'entity',
                url: '/bussinesPartnerDetails/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bussinesPartnerDetails/bussinesPartnerDetails-detail.html',
                        controller: 'BussinesPartnerDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bussinesPartnerDetails');
                        return $translate.refresh();
                    }]
                }
            });
    });
