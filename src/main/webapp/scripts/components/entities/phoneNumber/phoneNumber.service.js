'use strict';

angular.module('crmApp')
    .factory('PhoneNumber', function ($resource) {
        return $resource('api/phoneNumbers/:id', {}, {
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
