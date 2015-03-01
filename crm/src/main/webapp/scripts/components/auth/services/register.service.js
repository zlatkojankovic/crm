'use strict';

angular.module('crmApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


