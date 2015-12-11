'use strict';

angular.module('dgdtoolApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


