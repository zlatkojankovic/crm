'use strict';

angular.module('crmApp')
    .factory('Warehouse', function ($resource) {
        return $resource('api/warehouses/:id', {}, {
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
