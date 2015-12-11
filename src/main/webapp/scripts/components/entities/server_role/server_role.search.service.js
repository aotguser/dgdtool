'use strict';

angular.module('dgdtoolApp')
    .factory('Server_roleSearch', function ($resource) {
        return $resource('api/_search/server_roles/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
