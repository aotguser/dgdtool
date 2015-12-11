'use strict';

angular.module('dgdtoolApp')
    .factory('Status_typeSearch', function ($resource) {
        return $resource('api/_search/status_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
