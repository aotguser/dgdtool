'use strict';

angular.module('dgdtoolApp')
    .factory('StatusesSearch', function ($resource) {
        return $resource('api/_search/statusess/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
