'use strict';

angular.module('dgdtoolApp')
    .factory('AppSearch', function ($resource) {
        return $resource('api/_search/apps/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
