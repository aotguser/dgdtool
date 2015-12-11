'use strict';

angular.module('dgdtoolApp')
    .factory('Os_versionSearch', function ($resource) {
        return $resource('api/_search/os_versions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
