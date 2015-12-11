'use strict';

angular.module('dgdtoolApp')
    .factory('PathSearch', function ($resource) {
        return $resource('api/_search/paths/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
