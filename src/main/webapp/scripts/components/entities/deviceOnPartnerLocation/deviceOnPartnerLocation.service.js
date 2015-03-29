'use strict';

angular.module('crmApp')
    .factory('DeviceOnPartnerLocation', function ($resource) {
        return $resource('api/deviceOnPartnerLocations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    var dateFromFrom = data.dateFrom.split("-");
                    data.dateFrom = new Date(new Date(dateFromFrom[0], dateFromFrom[1] - 1, dateFromFrom[2]));
                    data.dateTo = new Date(data.dateTo);
                    return data;
                }
            }
        });
    });
