'use strict';

angular.module('dgdtoolApp')
    .factory('DecisionSearch', function ($resource) {
        return $resource('api/_search/decisions/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
