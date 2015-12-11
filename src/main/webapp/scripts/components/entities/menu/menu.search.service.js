'use strict';

angular.module('dgdtoolApp')
    .factory('MenuSearch', function ($resource) {
        return $resource('api/_search/menus/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
