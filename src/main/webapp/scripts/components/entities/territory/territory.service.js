'use strict';

angular.module('crmApp')
    .factory('Territory', function ($resource) {
        return $resource('api/territorys/:id', {}, {
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
