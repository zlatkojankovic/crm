'use strict';

angular.module('crmApp')
    .factory('BusinessPartner', function ($resource) {
        return $resource('api/businessPartners/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })
    .factory('BusinessPartnerDataTransfer', function ($rootScope) {
        return {
            setPartnerID: function (partnerId) {
                $rootScope.$broadcast('TRANSFER_BPARTNER_ID', partnerId);
            }
    };
})
;
