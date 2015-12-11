'use strict';

angular.module('dgdtoolApp')
    .factory('PackagesSearch', function ($resource) {
        return $resource('api/_search/packagess/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
