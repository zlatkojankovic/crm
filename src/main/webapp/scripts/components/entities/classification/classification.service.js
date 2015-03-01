'use strict';

angular.module('crmApp')
    .factory('Classification', function ($resource) {
        return $resource('api/classifications/:id', {}, {
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
