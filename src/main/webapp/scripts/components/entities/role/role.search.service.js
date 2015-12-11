'use strict';

angular.module('dgdtoolApp')
    .factory('RoleSearch', function ($resource) {
        return $resource('api/_search/roles/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
