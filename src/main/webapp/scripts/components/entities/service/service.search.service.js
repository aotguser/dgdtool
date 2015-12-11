'use strict';

angular.module('dgdtoolApp')
    .factory('ServiceSearch', function ($resource) {
        return $resource('api/_search/services/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
