'use strict';

angular.module('dgdtoolApp')
    .factory('Initiative_typeSearch', function ($resource) {
        return $resource('api/_search/initiative_types/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
