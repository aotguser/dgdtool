'use strict';

angular.module('dgdtoolApp')
    .factory('Server_typeSearch', function ($resource) {
        return $resource('api/_search/server_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
