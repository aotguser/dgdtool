'use strict';

angular.module('dgdtoolApp')
    .factory('Support_levelSearch', function ($resource) {
        return $resource('api/_search/support_levels/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
