'use strict';

angular.module('crmApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
