'use strict';

angular.module('crmApp')
    .factory('ClassificationType', function ($resource) {
        return $resource('api/classificationTypes/:id', {}, {
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
