'use strict';

angular.module('dgdtoolApp')
    .factory('App_typeSearch', function ($resource) {
        return $resource('api/_search/app_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
