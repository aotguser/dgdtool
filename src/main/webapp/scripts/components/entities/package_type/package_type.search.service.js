'use strict';

angular.module('dgdtoolApp')
    .factory('Package_typeSearch', function ($resource) {
        return $resource('api/_search/package_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
