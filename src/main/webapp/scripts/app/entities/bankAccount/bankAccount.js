'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bankAccount', {
                parent: 'entity',
                url: '/bankAccount',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bankAccount/bankAccounts.html',
                        controller: 'BankAccountController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankAccount');
                        return $translate.refresh();
                    }]
                }
            })
            .state('bankAccountDetail', {
                parent: 'entity',
                url: '/bankAccount/:id',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bankAccount/bankAccount-detail.html',
                        controller: 'BankAccountDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('bankAccount');
                        return $translate.refresh();
                    }]
                }
            });
    });
