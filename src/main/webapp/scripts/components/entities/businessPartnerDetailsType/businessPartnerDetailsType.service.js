'use strict';

angular.module('crmApp')
    .factory('BusinessPartnerDetailsType', function ($resource) {
        return $resource('api/businessPartnerDetailsTypes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateEntry = new Date(data.dateEntry);
                    return data;
                }
            }
        });
    });
