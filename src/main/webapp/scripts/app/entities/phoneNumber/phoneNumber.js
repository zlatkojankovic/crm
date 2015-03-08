'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('phoneNumber', {
                parent: 'entity',
                url: '/phoneNumber',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/phoneNumber/phoneNumbers.html',
                        controller: 'PhoneNumberController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('phoneNumber');
                        return $translate.refresh();
                    }]
                }
            })
            .state('phoneNumberDetail', {
                parent: 'entity',
                url: '/phoneNumber/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/phoneNumber/phoneNumber-detail.html',
                        controller: 'PhoneNumberDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('phoneNumber');
                        return $translate.refresh();
                    }]
                }
            });
    });
