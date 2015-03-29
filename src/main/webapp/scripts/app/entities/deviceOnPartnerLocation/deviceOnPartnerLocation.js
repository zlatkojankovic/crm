'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('deviceOnPartnerLocation', {
                parent: 'entity',
                url: '/deviceOnPartnerLocation',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deviceOnPartnerLocation/deviceOnPartnerLocations.html',
                        controller: 'DeviceOnPartnerLocationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deviceOnPartnerLocation');
                        return $translate.refresh();
                    }]
                }
            })
            .state('deviceOnPartnerLocationDetail', {
                parent: 'entity',
                url: '/deviceOnPartnerLocation/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deviceOnPartnerLocation/deviceOnPartnerLocation-detail.html',
                        controller: 'DeviceOnPartnerLocationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('deviceOnPartnerLocation');
                        return $translate.refresh();
                    }]
                }
            });
    });
