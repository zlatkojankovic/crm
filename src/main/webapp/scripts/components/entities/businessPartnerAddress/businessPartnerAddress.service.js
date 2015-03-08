'use strict';

angular.module('crmApp')
    .factory('BusinessPartnerAddress', function ($resource) {
        return $resource('api/businessPartnerAddresss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
