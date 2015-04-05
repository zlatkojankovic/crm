'use strict';

angular.module('crmApp')
    .factory('BankAccount', function ($resource) {
        return $resource('api/bankAccounts/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateFrom = new Date(data.dateFrom);
                    data.dateTo = new Date(data.dateTo);
                    return data;
                }
            }
        });
    })
    .factory('BankAccountPerPartner', function ($resource) {
        return $resource('api/bankAccountsPerPartner/:partnerId', {}, {
            'query': {
                method: 'GET',
                isArray: true
                }
            });
    });
