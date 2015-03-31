'use strict';

angular.module('crmApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('entity', {
                abstract: true,
                parent: 'site',
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('businessPartner');
                        $translatePartialLoader.addPart('codeBase');
                        $translatePartialLoader.addPart('businessPartnerContactDetails');
                        return $translate.refresh();
                    }]
                }
            });
    });
