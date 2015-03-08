'use strict';

angular.module('crmApp')
    .factory('BusinessPartnerContactDetails', function ($resource) {
        return $resource('api/businessPartnerContactDetailss/:id', {}, {
            'query': { method: 'GET', isArray: true},
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
    });
