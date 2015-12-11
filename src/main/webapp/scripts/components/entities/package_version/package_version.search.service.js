'use strict';

angular.module('dgdtoolApp')
    .factory('Package_versionSearch', function ($resource) {
        return $resource('api/_search/package_versions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
