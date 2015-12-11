'use strict';

angular.module('dgdtoolApp')
    .factory('License_typeSearch', function ($resource) {
        return $resource('api/_search/license_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
