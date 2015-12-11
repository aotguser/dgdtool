'use strict';

angular.module('dgdtoolApp')
    .factory('StageSearch', function ($resource) {
        return $resource('api/_search/stages/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
