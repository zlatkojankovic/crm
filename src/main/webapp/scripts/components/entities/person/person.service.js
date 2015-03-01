'use strict';

angular.module('crmApp')
    .factory('Person', function ($resource) {
        return $resource('api/persons/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateEntry = new Date(data.dateEntry);
                    data.dateValid = new Date(data.dateValid);
                    return data;
                }
            }
        });
    });
