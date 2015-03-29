'use strict';

angular.module('crmApp')
    .factory('UnitOfMeasure', function ($resource) {
        return $resource('api/unitOfMeasures/:id', {}, {
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
